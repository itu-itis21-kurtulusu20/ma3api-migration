/*
 * Copyright 1996-2006 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package sune.security.x509;

import java.lang.reflect.*;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

import sune.security.util.DerValue;

import javax.security.auth.x500.X500Principal;

/**
 * Note:  As of 1.4, the public class,
 * javax.security.auth.x500.X500Principal,
 * should be used when parsing, generating, and comparing X.500 DNs.
 * This class contains other useful methods for checking name constraints
 * and retrieving DNs by keyword.
 *
 * <p> X.500 names are used to identify entities, such as those which are
 * identified by X.509 certificates.  They are world-wide, hierarchical,
 * and descriptive.  Entities can be identified by attributes, and in
 * some systems can be searched for according to those attributes.
 * <p>
 * The ASN.1 for this is:
 * <pre>
 * GeneralName ::= CHOICE {
 * ....
 *     directoryName                   [4]     Name,
 * ....
 * Name ::= CHOICE {
 *   RDNSequence }
 *
 * RDNSequence ::= SEQUENCE OF RelativeDistinguishedName
 *
 * RelativeDistinguishedName ::=
 *   SET OF AttributeTypeAndValue
 *
 * AttributeTypeAndValue ::= SEQUENCE {
 *   type     AttributeType,
 *   value    AttributeValue }
 *
 * AttributeType ::= OBJECT IDENTIFIER
 *
 * AttributeValue ::= ANY DEFINED BY AttributeType
 * ....
 * DirectoryString ::= CHOICE {
 *       teletexString           TeletexString (SIZE (1..MAX)),
 *       printableString         PrintableString (SIZE (1..MAX)),
 *       universalString         UniversalString (SIZE (1..MAX)),
 *       utf8String              UTF8String (SIZE (1.. MAX)),
 *       bmpString               BMPString (SIZE (1..MAX)) }
 * </pre>
 * <p>
 * This specification requires only a subset of the name comparison
 * functionality specified in the X.500 series of specifications.  The
 * requirements for conforming implementations are as follows:
 * <ol TYPE=a>
 * <li>attribute values encoded in different types (e.g.,
 *    PrintableString and BMPString) may be assumed to represent
 *    different strings;
 * <p>
 * <li>attribute values in types other than PrintableString are case
 *    sensitive (this permits matching of attribute values as binary
 *    objects);
 * <p>
 * <li>attribute values in PrintableString are not case sensitive
 *    (e.g., "Marianne Swanson" is the same as "MARIANNE SWANSON"); and
 * <p>
 * <li>attribute values in PrintableString are compared after
 *    removing leading and trailing white space and converting internal
 *    substrings of one or more consecutive white space characters to a
 *    single space.
 * </ol>
 * <p>
 * These name comparison rules permit a certificate user to validate
 * certificates issued using languages or encodings unfamiliar to the
 * certificate user.
 * <p>
 * In addition, implementations of this specification MAY use these
 * comparison rules to process unfamiliar attribute types for name
 * chaining. This allows implementations to process certificates with
 * unfamiliar attributes in the issuer name.
 * <p>
 * Note that the comparison rules defined in the X.500 series of
 * specifications indicate that the character sets used to encode data
 * in distinguished names are irrelevant.  The characters themselves are
 * compared without regard to encoding. Implementations of the profile
 * are permitted to use the comparison algorithm defined in the X.500
 * series.  Such an implementation will recognize a superset of name
 * matches recognized by the algorithm specified above.
 * <p>
 * Note that instances of this class are immutable.
 *
 * @author David Brownell
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 * @see GeneralName
 * @see GeneralNames
 * @see sune.security.x509.GeneralNameInterface
 */

public class X500Name implements sune.security.x509.GeneralNameInterface, Principal {

    private String dn; // roughly RFC 1779 DN, or null
    private String rfc1779Dn; // RFC 1779 compliant DN, or null
    private String rfc2253Dn; // RFC 2253 DN, or null
    private String canonicalDn; // canonical RFC 2253 DN or null
    private sune.security.x509.RDN[] names;        // RDNs (never null)
    private X500Principal x500Principal;
    private byte[] encoded;

    // cached immutable list of the RDNs and all the AVAs
    private volatile List<sune.security.x509.RDN> rdnList;
    private volatile List<sune.security.x509.AVA> allAvaList;

    /**
     * Constructs a name from a conventionally formatted string, such
     * as "CN=Dave, OU=JavaSoft, O=Sun Microsystems, C=US".
     * (RFC 1779 or RFC 2253 style).
     *
     * @param dname X.500 Distinguished Name
     */
    public X500Name(String dname) throws IOException {
        this(dname, Collections.<String, String>emptyMap());
    }

    /**
     * Constructs a name from a conventionally formatted string, such
     * as "CN=Dave, OU=JavaSoft, O=Sun Microsystems, C=US".
     * (RFC 1779 or RFC 2253 style).
     *
     * @param dname X.500 Distinguished Name
     * @param keywordMap an additional keyword/OID map
     */
    public X500Name(String dname, Map<String, String> keywordMap)
        throws IOException {
        parseDN(dname, keywordMap);
    }

    /**
     * Constructs a name from a string formatted according to format.
     * Currently, the formats DEFAULT and RFC2253 are supported.
     * DEFAULT is the default format used by the X500Name(String)
     * constructor. RFC2253 is format strictly according to RFC2253
     * without extensions.
     *
     * @param dname X.500 Distinguished Name
     */
    public X500Name(String dname, String format) throws IOException {
        if (dname == null) {
            throw new NullPointerException("Name must not be null");
        }
        if (format.equalsIgnoreCase("RFC2253")) {
            parseRFC2253DN(dname);
        } else if (format.equalsIgnoreCase("DEFAULT")) {
            parseDN(dname, Collections.<String, String>emptyMap());
        } else {
            throw new IOException("Unsupported format " + format);
        }
    }

    /**
     * Constructs a name from fields common in enterprise application
     * environments.
     *
     * <P><EM><STRONG>NOTE:</STRONG>  The behaviour when any of
     * these strings contain characters outside the ASCII range
     * is unspecified in currently relevant standards.</EM>
     *
     * @param commonName common name of a person, e.g. "Vivette Davis"
     * @param organizationUnit small organization name, e.g. "Purchasing"
     * @param organizationName large organization name, e.g. "Onizuka, Inc."
     * @param country two letter country code, e.g. "CH"
     */
    public X500Name(String commonName, String organizationUnit,
                     String organizationName, String country)
    throws IOException {
        names = new sune.security.x509.RDN[4];
        /*
         * NOTE:  it's only on output that little-endian
         * ordering is used.
         */
        names[3] = new sune.security.x509.RDN(1);
        names[3].assertion[0] = new sune.security.x509.AVA(commonName_oid,
                new sune.security.util.DerValue(commonName));
        names[2] = new sune.security.x509.RDN(1);
        names[2].assertion[0] = new sune.security.x509.AVA(orgUnitName_oid,
                new sune.security.util.DerValue(organizationUnit));
        names[1] = new sune.security.x509.RDN(1);
        names[1].assertion[0] = new sune.security.x509.AVA(orgName_oid,
                new sune.security.util.DerValue(organizationName));
        names[0] = new sune.security.x509.RDN(1);
        names[0].assertion[0] = new sune.security.x509.AVA(countryName_oid,
                new sune.security.util.DerValue(country));
    }

    /**
     * Constructs a name from fields common in Internet application
     * environments.
     *
     * <P><EM><STRONG>NOTE:</STRONG>  The behaviour when any of
     * these strings contain characters outside the ASCII range
     * is unspecified in currently relevant standards.</EM>
     *
     * @param commonName common name of a person, e.g. "Vivette Davis"
     * @param organizationUnit small organization name, e.g. "Purchasing"
     * @param organizationName large organization name, e.g. "Onizuka, Inc."
     * @param localityName locality (city) name, e.g. "Palo Alto"
     * @param stateName state name, e.g. "California"
     * @param country two letter country code, e.g. "CH"
     */
    public X500Name(String commonName, String organizationUnit,
                    String organizationName, String localityName,
                    String stateName, String country)
    throws IOException {
        names = new sune.security.x509.RDN[6];
        /*
         * NOTE:  it's only on output that little-endian
         * ordering is used.
         */
        names[5] = new sune.security.x509.RDN(1);
        names[5].assertion[0] = new sune.security.x509.AVA(commonName_oid,
                new sune.security.util.DerValue(commonName));
        names[4] = new sune.security.x509.RDN(1);
        names[4].assertion[0] = new sune.security.x509.AVA(orgUnitName_oid,
                new sune.security.util.DerValue(organizationUnit));
        names[3] = new sune.security.x509.RDN(1);
        names[3].assertion[0] = new sune.security.x509.AVA(orgName_oid,
                new sune.security.util.DerValue(organizationName));
        names[2] = new sune.security.x509.RDN(1);
        names[2].assertion[0] = new sune.security.x509.AVA(localityName_oid,
                new sune.security.util.DerValue(localityName));
        names[1] = new sune.security.x509.RDN(1);
        names[1].assertion[0] = new sune.security.x509.AVA(stateName_oid,
                new sune.security.util.DerValue(stateName));
        names[0] = new sune.security.x509.RDN(1);
        names[0].assertion[0] = new sune.security.x509.AVA(countryName_oid,
                new sune.security.util.DerValue(country));
    }

    /**
     * Constructs a name from an array of relative distinguished names
     *
     * @param rdnArray array of relative distinguished names
     * @throws IOException on error
     */
    public X500Name(sune.security.x509.RDN[] rdnArray) throws IOException {
        if (rdnArray == null) {
            names = new sune.security.x509.RDN[0];
        } else {
            names = rdnArray.clone();
            for (int i = 0; i < names.length; i++) {
                if (names[i] == null) {
                    throw new IOException("Cannot create an X500Name");
                }
            }
        }
    }

    /**
     * Constructs a name from an ASN.1 encoded value.  The encoding
     * of the name in the stream uses DER (a BER/1 subset).
     *
     * @param value a DER-encoded value holding an X.500 name.
     */
    public X500Name(sune.security.util.DerValue value) throws IOException {
        //Note that toDerInputStream uses only the buffer (data) and not
        //the tag, so an empty SEQUENCE (OF) will yield an empty DerInputStream
        this(value.toDerInputStream());
    }

    /**
     * Constructs a name from an ASN.1 encoded input stream.  The encoding
     * of the name in the stream uses DER (a BER/1 subset).
     *
     * @param in DER-encoded data holding an X.500 name.
     */
    public X500Name(sune.security.util.DerInputStream in) throws IOException {
        parseDER(in);
    }

    /**
     *  Constructs a name from an ASN.1 encoded byte array.
     *
     * @param name DER-encoded byte array holding an X.500 name.
     */
    public X500Name(byte[] name) throws IOException {
        sune.security.util.DerInputStream in = new sune.security.util.DerInputStream(name);
        parseDER(in);
    }

    /**
     * Return an immutable List of all RDNs in this X500Name.
     */
    public List<sune.security.x509.RDN> rdns() {
        List<sune.security.x509.RDN> list = rdnList;
        if (list == null) {
            list = Collections.unmodifiableList(Arrays.asList(names));
            rdnList = list;
        }
        return list;
    }

    /**
     * Return the number of RDNs in this X500Name.
     */
    public int size() {
        return names.length;
    }

    /**
     * Return an immutable List of the the AVAs contained in all the
     * RDNs of this X500Name.
     */
    public List<sune.security.x509.AVA> allAvas() {
        List<sune.security.x509.AVA> list = allAvaList;
        if (list == null) {
            list = new ArrayList<AVA>();
            for (int i = 0; i < names.length; i++) {
                list.addAll(names[i].avas());
            }
        }
        return list;
    }

    /**
     * Return the total number of AVAs contained in all the RDNs of
     * this X500Name.
     */
    public int avaSize() {
        return allAvas().size();
    }

    /**
     * Return whether this X500Name is empty. An X500Name is not empty
     * if it has at least one RDN containing at least one AVA.
     */
    public boolean isEmpty() {
        int n = names.length;
        if (n == 0) {
            return true;
        }
        for (int i = 0; i < n; i++) {
            if (names[i].assertion.length != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates a hash code value for the object.  Objects
     * which are equal will also have the same hashcode.
     */
    public int hashCode() {
        return getRFC2253CanonicalName().hashCode();
    }

    /**
     * Compares this name with another, for equality.
     *
     * @return true iff the names are identical.
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        X500Name other = null;
        if(obj instanceof javax.security.auth.x500.X500Principal)
        {
            try
            {
                javax.security.auth.x500.X500Principal sunObj = (javax.security.auth.x500.X500Principal)obj;
                byte[] encoded = sunObj.getEncoded();
                other = new X500Name(encoded);
            }
            catch (Exception ex)
            {
                throw new RuntimeException("Error converting javax.security.auth.x500.X500Principal to sune.security.x509.X500Name", ex);
            }
        }
        else if (obj instanceof X500Name == false)
        {
            other = (X500Name)obj;
        }
        else {
            return false;
        }

        // if we already have the canonical forms, compare now
        if ((this.canonicalDn != null) && (other.canonicalDn != null)) {
            return this.canonicalDn.equals(other.canonicalDn);
        }
        // quick check that number of RDNs and AVAs match before canonicalizing
        int n = this.names.length;
        if (n != other.names.length) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            sune.security.x509.RDN r1 = this.names[i];
            sune.security.x509.RDN r2 = other.names[i];
            if (r1.assertion.length != r2.assertion.length) {
                return false;
            }
        }
        // definite check via canonical form
        String thisCanonical = this.getRFC2253CanonicalName();
        String otherCanonical = other.getRFC2253CanonicalName();
        return thisCanonical.equals(otherCanonical);
    }

    /*
     * Returns the name component as a Java string, regardless of its
     * encoding restrictions.
     */
    private String getString(sune.security.util.DerValue attribute) throws IOException {
        if (attribute == null)
            return null;
        String  value = attribute.getAsString();

        if (value == null)
            throw new IOException("not a DER string encoding, "
                    + attribute.tag);
        else
            return value;
    }

    /**
     * Return type of GeneralName.
     */
    public int getType() {
        return (sune.security.x509.GeneralNameInterface.NAME_DIRECTORY);
    }

    /**
     * Returns a "Country" name component.  If more than one
     * such attribute exists, the topmost one is returned.
     *
     * @return "C=" component of the name, if any.
     */
    public String getCountry() throws IOException {
        sune.security.util.DerValue attr = findAttribute(countryName_oid);

        return getString(attr);
    }


    /**
     * Returns an "Organization" name component.  If more than
     * one such attribute exists, the topmost one is returned.
     *
     * @return "O=" component of the name, if any.
     */
    public String getOrganization() throws IOException {
        sune.security.util.DerValue attr = findAttribute(orgName_oid);

        return getString(attr);
    }


    /**
     * Returns an "Organizational Unit" name component.  If more
     * than one such attribute exists, the topmost one is returned.
     *
     * @return "OU=" component of the name, if any.
     */
    public String getOrganizationalUnit() throws IOException {
        sune.security.util.DerValue attr = findAttribute(orgUnitName_oid);

        return getString(attr);
    }


    /**
     * Returns a "Common Name" component.  If more than one such
     * attribute exists, the topmost one is returned.
     *
     * @return "CN=" component of the name, if any.
     */
    public String getCommonName() throws IOException {
        sune.security.util.DerValue attr = findAttribute(commonName_oid);

        return getString(attr);
    }


    /**
     * Returns a "Locality" name component.  If more than one
     * such component exists, the topmost one is returned.
     *
     * @return "L=" component of the name, if any.
     */
    public String getLocality() throws IOException {
        sune.security.util.DerValue attr = findAttribute(localityName_oid);

        return getString(attr);
    }

    /**
     * Returns a "State" name component.  If more than one
     * such component exists, the topmost one is returned.
     *
     * @return "S=" component of the name, if any.
     */
    public String getState() throws IOException {
      sune.security.util.DerValue attr = findAttribute(stateName_oid);

        return getString(attr);
    }

    /**
     * Returns a "Domain" name component.  If more than one
     * such component exists, the topmost one is returned.
     *
     * @return "DC=" component of the name, if any.
     */
    public String getDomain() throws IOException {
        sune.security.util.DerValue attr = findAttribute(DOMAIN_COMPONENT_OID);

        return getString(attr);
    }

    /**
     * Returns a "DN Qualifier" name component.  If more than one
     * such component exists, the topmost one is returned.
     *
     * @return "DNQ=" component of the name, if any.
     */
    public String getDNQualifier() throws IOException {
        sune.security.util.DerValue attr = findAttribute(DNQUALIFIER_OID);

        return getString(attr);
    }

    /**
     * Returns a "Surname" name component.  If more than one
     * such component exists, the topmost one is returned.
     *
     * @return "SURNAME=" component of the name, if any.
     */
    public String getSurname() throws IOException {
        sune.security.util.DerValue attr = findAttribute(SURNAME_OID);

        return getString(attr);
    }

    /**
     * Returns a "Given Name" name component.  If more than one
     * such component exists, the topmost one is returned.
     *
     * @return "GIVENNAME=" component of the name, if any.
     */
    public String getGivenName() throws IOException {
       sune.security.util.DerValue attr = findAttribute(GIVENNAME_OID);

       return getString(attr);
    }

    /**
     * Returns an "Initials" name component.  If more than one
     * such component exists, the topmost one is returned.
     *
     * @return "INITIALS=" component of the name, if any.
     */
    public String getInitials() throws IOException {
        sune.security.util.DerValue attr = findAttribute(INITIALS_OID);

        return getString(attr);
     }

     /**
      * Returns a "Generation Qualifier" name component.  If more than one
      * such component exists, the topmost one is returned.
      *
      * @return "GENERATION=" component of the name, if any.
      */
    public String getGeneration() throws IOException {
        sune.security.util.DerValue attr = findAttribute(GENERATIONQUALIFIER_OID);

        return getString(attr);
    }

    /**
     * Returns an "IP address" name component.  If more than one
     * such component exists, the topmost one is returned.
     *
     * @return "IP=" component of the name, if any.
     */
    public String getIP() throws IOException {
        sune.security.util.DerValue attr = findAttribute(ipAddress_oid);

        return getString(attr);
    }

    /**
     * Returns a string form of the X.500 distinguished name.
     * The format of the string is from RFC 1779. The returned string
     * may contain non-standardised keywords for more readability
     * (keywords from RFCs 1779, 2253, and 3280).
     */
    public String toString() {
        if (dn == null) {
            generateDN();
        }
        return dn;
    }

    /**
     * Returns a string form of the X.500 distinguished name
     * using the algorithm defined in RFC 1779. Only standard attribute type
     * keywords defined in RFC 1779 are emitted.
     */
    public String getRFC1779Name() {
        return getRFC1779Name(Collections.<String, String>emptyMap());
    }

    /**
     * Returns a string form of the X.500 distinguished name
     * using the algorithm defined in RFC 1779. Attribute type
     * keywords defined in RFC 1779 are emitted, as well as additional
     * keywords contained in the OID/keyword map.
     */
    public String getRFC1779Name(Map<String, String> oidMap)
        throws IllegalArgumentException {
        if (oidMap.isEmpty()) {
            // return cached result
            if (rfc1779Dn != null) {
                return rfc1779Dn;
            } else {
                rfc1779Dn = generateRFC1779DN(oidMap);
                return rfc1779Dn;
            }
        }
        return generateRFC1779DN(oidMap);
    }

    /**
     * Returns a string form of the X.500 distinguished name
     * using the algorithm defined in RFC 2253. Only standard attribute type
     * keywords defined in RFC 2253 are emitted.
     */
    public String getRFC2253Name() {
        return getRFC2253Name(Collections.<String, String>emptyMap());
    }

    /**
     * Returns a string form of the X.500 distinguished name
     * using the algorithm defined in RFC 2253. Attribute type
     * keywords defined in RFC 2253 are emitted, as well as additional
     * keywords contained in the OID/keyword map.
     */
    public String getRFC2253Name(Map<String, String> oidMap) {
        /* check for and return cached name */
        if (oidMap.isEmpty()) {
            if (rfc2253Dn != null) {
                return rfc2253Dn;
            } else {
                rfc2253Dn = generateRFC2253DN(oidMap);
                return rfc2253Dn;
            }
        }
        return generateRFC2253DN(oidMap);
    }

    private String generateRFC2253DN(Map<String, String> oidMap) {
        /*
         * Section 2.1 : if the RDNSequence is an empty sequence
         * the result is the empty or zero length string.
         */
        if (names.length == 0) {
            return "";
        }

        /*
         * 2.1 (continued) : Otherwise, the output consists of the string
         * encodings of each RelativeDistinguishedName in the RDNSequence
         * (according to 2.2), starting with the last element of the sequence
         * and moving backwards toward the first.
         *
         * The encodings of adjoining RelativeDistinguishedNames are separated
         * by a comma character (',' ASCII 44).
         */
        StringBuilder fullname = new StringBuilder(48);
        for (int i = names.length - 1; i >= 0; i--) {
            if (i < names.length - 1) {
                fullname.append(',');
            }
            fullname.append(names[i].toRFC2253String(oidMap));
        }
        return fullname.toString();
    }

    public String getRFC2253CanonicalName() {
        /* check for and return cached name */
        if (canonicalDn != null) {
            return canonicalDn;
        }
        /*
         * Section 2.1 : if the RDNSequence is an empty sequence
         * the result is the empty or zero length string.
         */
        if (names.length == 0) {
            canonicalDn = "";
            return canonicalDn;
        }

        /*
         * 2.1 (continued) : Otherwise, the output consists of the string
         * encodings of each RelativeDistinguishedName in the RDNSequence
         * (according to 2.2), starting with the last element of the sequence
         * and moving backwards toward the first.
         *
         * The encodings of adjoining RelativeDistinguishedNames are separated
         * by a comma character (',' ASCII 44).
         */
        StringBuilder fullname = new StringBuilder(48);
        for (int i = names.length - 1; i >= 0; i--) {
            if (i < names.length - 1) {
                fullname.append(',');
            }
            fullname.append(names[i].toRFC2253String(true));
        }
        canonicalDn = fullname.toString();
        return canonicalDn;
    }

    /**
     * Returns the value of toString().  This call is needed to
     * implement the java.security.Principal interface.
     */
    public String getName() { return toString(); }

    /**
     * Find the first instance of this attribute in a "top down"
     * search of all the attributes in the name.
     */
    private sune.security.util.DerValue findAttribute(sune.security.util.ObjectIdentifier attribute) {
        if (names != null) {
            for (int i = 0; i < names.length; i++) {
                sune.security.util.DerValue value = names[i].findAttribute(attribute);
                if (value != null) {
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * Find the most specific ("last") attribute of the given
     * type.
     */
    public sune.security.util.DerValue findMostSpecificAttribute(sune.security.util.ObjectIdentifier attribute) {
        if (names != null) {
            for (int i = names.length - 1; i >= 0; i--) {
                sune.security.util.DerValue value = names[i].findAttribute(attribute);
                if (value != null) {
                    return value;
                }
            }
        }
        return null;
    }

    /****************************************************************/

    private void parseDER(sune.security.util.DerInputStream in) throws IOException {
        //
        // X.500 names are a "SEQUENCE OF" RDNs, which means zero or
        // more and order matters.  We scan them in order, which
        // conventionally is big-endian.
        //
        sune.security.util.DerValue[] nameseq = null;
        byte[] derBytes = in.toByteArray();

        try {
            nameseq = in.getSequence(5);
        } catch (IOException ioe) {
            if (derBytes == null) {
                nameseq = null;
            } else {
                sune.security.util.DerValue derVal = new sune.security.util.DerValue(sune.security.util.DerValue.tag_Sequence,
                                           derBytes);
                derBytes = derVal.toByteArray();
                nameseq = new sune.security.util.DerInputStream(derBytes).getSequence(5);
            }
        }

        if (nameseq == null) {
            names = new sune.security.x509.RDN[0];
        } else {
            names = new sune.security.x509.RDN[nameseq.length];
            for (int i = 0; i < nameseq.length; i++) {
                names[i] = new sune.security.x509.RDN(nameseq[i]);
            }
        }
    }

    /**
     * Encodes the name in DER-encoded form.
     *
     * @deprecated Use encode() instead
     * @param out where to put the DER-encoded X.500 name
     */
    @Deprecated
    public void emit(sune.security.util.DerOutputStream out) throws IOException {
        encode(out);
    }

    /**
     * Encodes the name in DER-encoded form.
     *
     * @param out where to put the DER-encoded X.500 name
     */
    public void encode(sune.security.util.DerOutputStream out) throws IOException {
        sune.security.util.DerOutputStream tmp = new sune.security.util.DerOutputStream();
        for (int i = 0; i < names.length; i++) {
            names[i].encode(tmp);
        }
        out.write(sune.security.util.DerValue.tag_Sequence, tmp);
    }

    /**
     * Returned the encoding as an uncloned byte array. Callers must
     * guarantee that they neither modify it not expose it to untrusted
     * code.
     */
    public byte[] getEncodedInternal() throws IOException {
        if (encoded == null) {
            sune.security.util.DerOutputStream out = new sune.security.util.DerOutputStream();
            sune.security.util.DerOutputStream tmp = new sune.security.util.DerOutputStream();
            for (int i = 0; i < names.length; i++) {
                names[i].encode(tmp);
            }
            out.write(DerValue.tag_Sequence, tmp);
            encoded = out.toByteArray();
        }
        return encoded;
    }

    /**
     * Gets the name in DER-encoded form.
     *
     * @return the DER encoded byte array of this name.
     */
    public byte[] getEncoded() throws IOException {
        return getEncodedInternal().clone();
    }

    /*
     * Parses a Distinguished Name (DN) in printable representation.
     *
     * According to RFC 1779, RDNs in a DN are separated by comma.
     * The following examples show both methods of quoting a comma, so that it
     * is not considered a separator:
     *
     *     O="Sue, Grabbit and Runn" or
     *     O=Sue\, Grabbit and Runn
     *
     * This method can parse 1779 or 2253 DNs and non-standard 3280 keywords.
     * Additional keywords can be specified in the keyword/OID map.
     */
    private void parseDN(String input, Map<String, String> keywordMap)
        throws IOException {
        if (input == null || input.length() == 0) {
            names = new sune.security.x509.RDN[0];
            return;
        }

        List<sune.security.x509.RDN> dnVector = new ArrayList<sune.security.x509.RDN>();
        int dnOffset = 0;
        int rdnEnd;
        String rdnString;
        int quoteCount = 0;

        String dnString = input;

        int searchOffset = 0;
        int nextComma = dnString.indexOf(',');
        int nextSemiColon = dnString.indexOf(';');
        while (nextComma >=0 || nextSemiColon >=0) {

            if (nextSemiColon < 0) {
                rdnEnd = nextComma;
            } else if (nextComma < 0) {
                rdnEnd = nextSemiColon;
            } else {
                rdnEnd = Math.min(nextComma, nextSemiColon);
            }
            quoteCount += countQuotes(dnString, searchOffset, rdnEnd);

            /*
             * We have encountered an RDN delimiter (comma or a semicolon).
             * If the comma or semicolon in the RDN under consideration is
             * preceded by a backslash (escape), or by a double quote, it
             * is part of the RDN. Otherwise, it is used as a separator, to
             * delimit the RDN under consideration from any subsequent RDNs.
             */
            if (rdnEnd >= 0 && quoteCount != 1 &&
                !escaped(rdnEnd, searchOffset, dnString)) {

                /*
                 * Comma/semicolon is a separator
                 */
                rdnString = dnString.substring(dnOffset, rdnEnd);

                // Parse RDN, and store it in vector
                sune.security.x509.RDN rdn = new sune.security.x509.RDN(rdnString, keywordMap);
                dnVector.add(rdn);

                // Increase the offset
                dnOffset = rdnEnd + 1;

                // Set quote counter back to zero
                quoteCount = 0;
            }

            searchOffset = rdnEnd + 1;
            nextComma = dnString.indexOf(',', searchOffset);
            nextSemiColon = dnString.indexOf(';', searchOffset);
        }

        // Parse last or only RDN, and store it in vector
        rdnString = dnString.substring(dnOffset);
        sune.security.x509.RDN rdn = new sune.security.x509.RDN(rdnString, keywordMap);
        dnVector.add(rdn);

        /*
         * Store the vector elements as an array of RDNs
         * NOTE: It's only on output that little-endian ordering is used.
         */
        Collections.reverse(dnVector);
        names = dnVector.toArray(new sune.security.x509.RDN[dnVector.size()]);
    }

    private void parseRFC2253DN(String dnString) throws IOException {
        if (dnString.length() == 0) {
            names = new sune.security.x509.RDN[0];
            return;
        }

        List<sune.security.x509.RDN> dnVector = new ArrayList<sune.security.x509.RDN>();
        int dnOffset = 0;
        String rdnString;

        int searchOffset = 0;
        int rdnEnd = dnString.indexOf(',');
        while (rdnEnd >=0) {
            /*
             * We have encountered an RDN delimiter (comma).
             * If the comma in the RDN under consideration is
             * preceded by a backslash (escape), it
             * is part of the RDN. Otherwise, it is used as a separator, to
             * delimit the RDN under consideration from any subsequent RDNs.
             */
            if (rdnEnd > 0 && !escaped(rdnEnd, searchOffset, dnString)) {

                /*
                 * Comma is a separator
                 */
                rdnString = dnString.substring(dnOffset, rdnEnd);

                // Parse RDN, and store it in vector
                sune.security.x509.RDN rdn = new sune.security.x509.RDN(rdnString, "RFC2253");
                dnVector.add(rdn);

                // Increase the offset
                dnOffset = rdnEnd + 1;
            }

            searchOffset = rdnEnd + 1;
            rdnEnd = dnString.indexOf(',', searchOffset);
        }

        // Parse last or only RDN, and store it in vector
        rdnString = dnString.substring(dnOffset);
        sune.security.x509.RDN rdn = new sune.security.x509.RDN(rdnString, "RFC2253");
        dnVector.add(rdn);

        /*
         * Store the vector elements as an array of RDNs
         * NOTE: It's only on output that little-endian ordering is used.
         */
        Collections.reverse(dnVector);
        names = dnVector.toArray(new sune.security.x509.RDN[dnVector.size()]);
    }

    /*
     * Counts double quotes in string.
     * Escaped quotes are ignored.
     */
    static int countQuotes(String string, int from, int to) {
        int count = 0;

        for (int i = from; i < to; i++) {
            if ((string.charAt(i) == '"' && i == from) ||
                (string.charAt(i) == '"' && string.charAt(i-1) != '\\')) {
                count++;
            }
        }

        return count;
    }

    private static boolean escaped
                (int rdnEnd, int searchOffset, String dnString) {

        if (rdnEnd == 1 && dnString.charAt(rdnEnd - 1) == '\\') {

            //  case 1:
            //  \,

            return true;

        } else if (rdnEnd > 1 && dnString.charAt(rdnEnd - 1) == '\\' &&
                dnString.charAt(rdnEnd - 2) != '\\') {

            //  case 2:
            //  foo\,

            return true;

        } else if (rdnEnd > 1 && dnString.charAt(rdnEnd - 1) == '\\' &&
                dnString.charAt(rdnEnd - 2) == '\\') {

            //  case 3:
            //  foo\\\\\,

            int count = 0;
            rdnEnd--;   // back up to last backSlash
            while (rdnEnd >= searchOffset) {
                if (dnString.charAt(rdnEnd) == '\\') {
                    count++;    // count consecutive backslashes
                }
                rdnEnd--;
            }

            // if count is odd, then rdnEnd is escaped
            return (count % 2) != 0 ? true : false;

        } else {
            return false;
        }
    }

    /*
     * Dump the printable form of a distinguished name.  Each relative
     * name is separated from the next by a ",", and assertions in the
     * relative names have "label=value" syntax.
     *
     * Uses RFC 1779 syntax (i.e. little-endian, comma separators)
     */
    private void generateDN() {
        if (names.length == 1) {
            dn = names[0].toString();
            return;
        }

        StringBuilder sb = new StringBuilder(48);
        if (names != null) {
            for (int i = names.length - 1; i >= 0; i--) {
                if (i != names.length - 1) {
                    sb.append(", ");
                }
                sb.append(names[i].toString());
            }
        }
        dn = sb.toString();
    }

    /*
     * Dump the printable form of a distinguished name.  Each relative
     * name is separated from the next by a ",", and assertions in the
     * relative names have "label=value" syntax.
     *
     * Uses RFC 1779 syntax (i.e. little-endian, comma separators)
     * Valid keywords from RFC 1779 are used. Additional keywords can be
     * specified in the OID/keyword map.
     */
    private String generateRFC1779DN(Map<String, String> oidMap) {
        if (names.length == 1) {
            return names[0].toRFC1779String(oidMap);
        }

        StringBuilder sb = new StringBuilder(48);
        if (names != null) {
            for (int i = names.length - 1; i >= 0; i--) {
                if (i != names.length - 1) {
                    sb.append(", ");
                }
                sb.append(names[i].toRFC1779String(oidMap));
            }
        }
        return sb.toString();
    }

    /****************************************************************/

    /*
     * Maybe return a preallocated OID, to reduce storage costs
     * and speed recognition of common X.500 attributes.
     */
    static sune.security.util.ObjectIdentifier intern(sune.security.util.ObjectIdentifier oid) {
        sune.security.util.ObjectIdentifier interned = internedOIDs.get(oid);
        if (interned != null) {
            return interned;
        }
        internedOIDs.put(oid, oid);
        return oid;
    }

    private static final Map<sune.security.util.ObjectIdentifier, sune.security.util.ObjectIdentifier> internedOIDs
                        = new HashMap<sune.security.util.ObjectIdentifier, sune.security.util.ObjectIdentifier>();

    /*
     * Selected OIDs from X.520
     * Includes all those specified in RFC 3280 as MUST or SHOULD
     * be recognized
     */
    private static final int commonName_data[] = { 2, 5, 4, 3 };
    private static final int SURNAME_DATA[] = { 2, 5, 4, 4 };
    private static final int SERIALNUMBER_DATA[] = { 2, 5, 4, 5 };
    private static final int countryName_data[] = { 2, 5, 4, 6 };
    private static final int localityName_data[] = { 2, 5, 4, 7 };
    private static final int stateName_data[] = { 2, 5, 4, 8 };
    private static final int streetAddress_data[] = { 2, 5, 4, 9 };
    private static final int orgName_data[] = { 2, 5, 4, 10 };
    private static final int orgUnitName_data[] = { 2, 5, 4, 11 };
    private static final int title_data[] = { 2, 5, 4, 12 };
    private static final int GIVENNAME_DATA[] = { 2, 5, 4, 42 };
    private static final int INITIALS_DATA[] = { 2, 5, 4, 43 };
    private static final int GENERATIONQUALIFIER_DATA[] = { 2, 5, 4, 44 };
    private static final int DNQUALIFIER_DATA[] = { 2, 5, 4, 46 };

    private static final int ipAddress_data[] = { 1, 3, 6, 1, 4, 1, 42, 2, 11, 2, 1 };
    private static final int DOMAIN_COMPONENT_DATA[] =
        { 0, 9, 2342, 19200300, 100, 1, 25 };
    private static final int userid_data[] =
        { 0, 9, 2342, 19200300, 100, 1, 1 };


    public static final sune.security.util.ObjectIdentifier commonName_oid;
    public static final sune.security.util.ObjectIdentifier countryName_oid;
    public static final sune.security.util.ObjectIdentifier localityName_oid;
    public static final sune.security.util.ObjectIdentifier orgName_oid;
    public static final sune.security.util.ObjectIdentifier orgUnitName_oid;
    public static final sune.security.util.ObjectIdentifier stateName_oid;
    public static final sune.security.util.ObjectIdentifier streetAddress_oid;
    public static final sune.security.util.ObjectIdentifier title_oid;
    public static final sune.security.util.ObjectIdentifier DNQUALIFIER_OID;
    public static final sune.security.util.ObjectIdentifier SURNAME_OID;
    public static final sune.security.util.ObjectIdentifier GIVENNAME_OID;
    public static final sune.security.util.ObjectIdentifier INITIALS_OID;
    public static final sune.security.util.ObjectIdentifier GENERATIONQUALIFIER_OID;
    public static final sune.security.util.ObjectIdentifier ipAddress_oid;
    public static final sune.security.util.ObjectIdentifier DOMAIN_COMPONENT_OID;
    public static final sune.security.util.ObjectIdentifier userid_oid;
    public static final sune.security.util.ObjectIdentifier SERIALNUMBER_OID;

    static {
    /** OID for the "CN=" attribute, denoting a person's common name. */
        commonName_oid = intern(sune.security.util.ObjectIdentifier.newInternal(commonName_data));

    /** OID for the "SERIALNUMBER=" attribute, denoting a serial number for.
        a name. Do not confuse with PKCS#9 issuerAndSerialNumber or the
        certificate serial number. */
        SERIALNUMBER_OID = intern(sune.security.util.ObjectIdentifier.newInternal(SERIALNUMBER_DATA));

    /** OID for the "C=" attribute, denoting a country. */
        countryName_oid = intern(sune.security.util.ObjectIdentifier.newInternal(countryName_data));

    /** OID for the "L=" attribute, denoting a locality (such as a city) */
        localityName_oid = intern(sune.security.util.ObjectIdentifier.newInternal(localityName_data));

    /** OID for the "O=" attribute, denoting an organization name */
        orgName_oid = intern(sune.security.util.ObjectIdentifier.newInternal(orgName_data));

    /** OID for the "OU=" attribute, denoting an organizational unit name */
        orgUnitName_oid = intern(sune.security.util.ObjectIdentifier.newInternal(orgUnitName_data));

    /** OID for the "S=" attribute, denoting a state (such as Delaware) */
        stateName_oid = intern(sune.security.util.ObjectIdentifier.newInternal(stateName_data));

    /** OID for the "STREET=" attribute, denoting a street address. */
        streetAddress_oid = intern(sune.security.util.ObjectIdentifier.newInternal(streetAddress_data));

    /** OID for the "T=" attribute, denoting a person's title. */
        title_oid = intern(sune.security.util.ObjectIdentifier.newInternal(title_data));

    /** OID for the "DNQUALIFIER=" or "DNQ=" attribute, denoting DN
        disambiguating information.*/
        DNQUALIFIER_OID = intern(sune.security.util.ObjectIdentifier.newInternal(DNQUALIFIER_DATA));

    /** OID for the "SURNAME=" attribute, denoting a person's surname.*/
        SURNAME_OID = intern(sune.security.util.ObjectIdentifier.newInternal(SURNAME_DATA));

    /** OID for the "GIVENNAME=" attribute, denoting a person's given name.*/
        GIVENNAME_OID = intern(sune.security.util.ObjectIdentifier.newInternal(GIVENNAME_DATA));

    /** OID for the "INITIALS=" attribute, denoting a person's initials.*/
        INITIALS_OID = intern(sune.security.util.ObjectIdentifier.newInternal(INITIALS_DATA));

    /** OID for the "GENERATION=" attribute, denoting Jr., II, etc.*/
        GENERATIONQUALIFIER_OID =
            intern(sune.security.util.ObjectIdentifier.newInternal(GENERATIONQUALIFIER_DATA));

    /*
     * OIDs from other sources which show up in X.500 names we
     * expect to deal with often
     */
    /** OID for "IP=" IP address attributes, used with SKIP. */
        ipAddress_oid = intern(sune.security.util.ObjectIdentifier.newInternal(ipAddress_data));

    /*
     * Domain component OID from RFC 1274, RFC 2247, RFC 3280
     */

    /*
     * OID for "DC=" domain component attributes, used with DNS names in DN
     * format
     */
        DOMAIN_COMPONENT_OID =
            intern(sune.security.util.ObjectIdentifier.newInternal(DOMAIN_COMPONENT_DATA));

    /** OID for "UID=" denoting a user id, defined in RFCs 1274 & 2798. */
        userid_oid = intern(sune.security.util.ObjectIdentifier.newInternal(userid_data));
    }

    /**
     * Return constraint type:<ul>
     *   <li>NAME_DIFF_TYPE = -1: input name is different type from this name
     *       (i.e. does not constrain)
     *   <li>NAME_MATCH = 0: input name matches this name
     *   <li>NAME_NARROWS = 1: input name narrows this name
     *   <li>NAME_WIDENS = 2: input name widens this name
     *   <li>NAME_SAME_TYPE = 3: input name does not match or narrow this name,
     {@literal &}      but is same type
     * </ul>.  These results are used in checking NameConstraints during
     * certification path verification.
     *
     * @param inputName to be checked for being constrained
     * @return constraint type above
     * @throws UnsupportedOperationException if name is not exact match, but
     *         narrowing and widening are not supported for this name type.
     */
    public int constrains(GeneralNameInterface inputName)
            throws UnsupportedOperationException {
        int constraintType;
        if (inputName == null) {
            constraintType = NAME_DIFF_TYPE;
        } else if (inputName.getType() != NAME_DIRECTORY) {
            constraintType = NAME_DIFF_TYPE;
        } else { // type == NAME_DIRECTORY
            X500Name inputX500 = (X500Name)inputName;
            if (inputX500.equals(this)) {
                constraintType = NAME_MATCH;
            } else if (inputX500.names.length == 0) {
                constraintType = NAME_WIDENS;
            } else if (this.names.length == 0) {
                constraintType = NAME_NARROWS;
            } else if (inputX500.isWithinSubtree(this)) {
                constraintType = NAME_NARROWS;
            } else if (isWithinSubtree(inputX500)) {
                constraintType = NAME_WIDENS;
            } else {
                constraintType = NAME_SAME_TYPE;
            }
        }
        return constraintType;
    }

    /**
     * Compares this name with another and determines if
     * it is within the subtree of the other. Useful for
     * checking against the name constraints extension.
     *
     * @return true iff this name is within the subtree of other.
     */
    private boolean isWithinSubtree(X500Name other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.names.length == 0) {
            return true;
        }
        if (this.names.length == 0) {
            return false;
        }
        if (names.length < other.names.length) {
            return false;
        }
        for (int i = 0; i < other.names.length; i++) {
            if (!names[i].equals(other.names[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return subtree depth of this name for purposes of determining
     * NameConstraints minimum and maximum bounds and for calculating
     * path lengths in name subtrees.
     *
     * @return distance of name from root
     * @throws UnsupportedOperationException if not supported for this name type
     */
    public int subtreeDepth() throws UnsupportedOperationException {
        return names.length;
    }

    /**
     * Return lowest common ancestor of this name and other name
     *
     * @param other another X500Name
     * @return X500Name of lowest common ancestor; null if none
     */
    public X500Name commonAncestor(X500Name other) {

        if (other == null) {
            return null;
        }
        int otherLen = other.names.length;
        int thisLen = this.names.length;
        if (thisLen == 0 || otherLen == 0) {
            return null;
        }
        int minLen = (thisLen < otherLen) ? thisLen: otherLen;

        //Compare names from highest RDN down the naming tree
        //Note that these are stored in RDN[0]...
        int i=0;
        for (; i < minLen; i++) {
            if (!names[i].equals(other.names[i])) {
                if (i == 0) {
                    return null;
                } else {
                    break;
                }
            }
        }

        //Copy matching RDNs into new RDN array
        sune.security.x509.RDN[] ancestor = new RDN[i];
        for (int j=0; j < i; j++) {
            ancestor[j] = names[j];
        }

        X500Name commonAncestor = null;
        try {
            commonAncestor = new X500Name(ancestor);
        } catch (IOException ioe) {
            return null;
        }
        return commonAncestor;
    }

    /**
     * Constructor object for use by asX500Principal().
     */
    private static final Constructor principalConstructor = null;

    /**
     * Field object for use by asX500Name().
     */
    private static final Field principalField = null;

    /**
     * Retrieve the Constructor and Field we need for reflective access
     * and make them accessible.
     */
    static {
//        PrivilegedExceptionAction<Object[]> pa =
//                new PrivilegedExceptionAction<Object[]>() {
//            public Object[] run() throws Exception {
//                Class pClass = X500Principal.class;
//                Class[] args = new Class[] {X500Name.class};
//                Constructor cons = ((Class<?>)pClass).getDeclaredConstructor(args);
//                cons.setAccessible(true);
//                Field field = pClass.getDeclaredField("thisX500Name");
//                field.setAccessible(true);
//                return new Object[] {cons, field};
//            }
//        };
//        try {
//            Object[] result = AccessController.doPrivileged(pa);
//            principalConstructor = (Constructor)result[0];
//            principalField = (Field)result[1];
//        } catch (Exception e) {
//            throw (InternalError)new InternalError("Could not obtain "
//                + "X500Principal access").initCause(e);
//        }
    }

    /**
     * Get an X500Principal backed by this X500Name.
     *
     * Note that we are using privileged reflection to access the hidden
     * package private constructor in X500Principal.
     */
    public X500Principal asX500Principal() {
        if (x500Principal == null) {
            try {
                Object[] args = new Object[] {this};
                x500Principal =
                        (X500Principal)principalConstructor.newInstance(args);
            } catch (Exception e) {
                throw new RuntimeException("Unexpected exception", e);
            }
        }
        return x500Principal;
    }

    /**
     * Get the X500Name contained in the given X500Principal.
     *
     * Note that the X500Name is retrieved using reflection.
     */
    public static X500Name asX500Name(X500Principal p) {
        try {
            X500Name name = (X500Name)principalField.get(p);
            name.x500Principal = p;
            return name;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

}
