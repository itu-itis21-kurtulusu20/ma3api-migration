/*
 * Copyright 2002-2006 Sun Microsystems, Inc.  All Rights Reserved.
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

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import sune.security.util.DerValue;

/**
 * RDNs are a set of {attribute = value} assertions.  Some of those
 * attributes are "distinguished" (unique w/in context).  Order is
 * never relevant.
 *
 * Some X.500 names include only a single distinguished attribute
 * per RDN.  This style is currently common.
 *
 * Note that DER-encoded RDNs sort AVAs by assertion OID ... so that
 * when we parse this data we don't have to worry about canonicalizing
 * it, but we'll need to sort them when we expose the RDN class more.
 * <p>
 * The ASN.1 for RDNs is:
 * <pre>
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
 * </pre>
 *
 * Note that instances of this class are immutable.
 *
 */
public class RDN {

    // currently not private, accessed directly from X500Name
    final sune.security.x509.AVA[] assertion;

    // cached immutable List of the AVAs
    private volatile List<sune.security.x509.AVA> avaList;

    // cache canonical String form
    private volatile String canonicalString;

    /**
     * Constructs an RDN from its printable representation.
     *
     * An RDN may consist of one or multiple Attribute Value Assertions (AVAs),
     * using '+' as a separator.
     * If the '+' should be considered part of an AVA value, it must be
     * preceded by '\'.
     *
     * @param name String form of RDN
     * @throws IOException on parsing error
     */
    public RDN(String name) throws IOException {
        this(name, Collections.<String, String>emptyMap());
    }

    /**
     * Constructs an RDN from its printable representation.
     *
     * An RDN may consist of one or multiple Attribute Value Assertions (AVAs),
     * using '+' as a separator.
     * If the '+' should be considered part of an AVA value, it must be
     * preceded by '\'.
     *
     * @param name String form of RDN
     * @param keywordMap an additional mapping of keywords to OIDs
     * @throws IOException on parsing error
     */
    public RDN(String name, Map<String, String> keywordMap) throws IOException {
        int quoteCount = 0;
        int searchOffset = 0;
        int avaOffset = 0;
        List<sune.security.x509.AVA> avaVec = new ArrayList<sune.security.x509.AVA>(3);
        int nextPlus = name.indexOf('+');
        while (nextPlus >= 0) {
            quoteCount += X500Name.countQuotes(name, searchOffset, nextPlus);
            /*
             * We have encountered an AVA delimiter (plus sign).
             * If the plus sign in the RDN under consideration is
             * preceded by a backslash (escape), or by a double quote, it
             * is part of the AVA. Otherwise, it is used as a separator, to
             * delimit the AVA under consideration from any subsequent AVAs.
             */
            if (nextPlus > 0 && name.charAt(nextPlus - 1) != '\\'
                && quoteCount != 1) {
                /*
                 * Plus sign is a separator
                 */
                String avaString = name.substring(avaOffset, nextPlus);
                if (avaString.length() == 0) {
                    throw new IOException("empty AVA in RDN \"" + name + "\"");
                }

                // Parse AVA, and store it in vector
                sune.security.x509.AVA ava = new sune.security.x509.AVA(new StringReader(avaString), keywordMap);
                avaVec.add(ava);

                // Increase the offset
                avaOffset = nextPlus + 1;

                // Set quote counter back to zero
                quoteCount = 0;
            }
            searchOffset = nextPlus + 1;
            nextPlus = name.indexOf('+', searchOffset);
        }

        // parse last or only AVA
        String avaString = name.substring(avaOffset);
        if (avaString.length() == 0) {
            throw new IOException("empty AVA in RDN \"" + name + "\"");
        }
        sune.security.x509.AVA ava = new sune.security.x509.AVA(new StringReader(avaString), keywordMap);
        avaVec.add(ava);

        assertion = avaVec.toArray(new sune.security.x509.AVA[avaVec.size()]);
    }

    /*
     * Constructs an RDN from its printable representation.
     *
     * An RDN may consist of one or multiple Attribute Value Assertions (AVAs),
     * using '+' as a separator.
     * If the '+' should be considered part of an AVA value, it must be
     * preceded by '\'.
     *
     * @param name String form of RDN
     * @throws IOException on parsing error
     */
    RDN(String name, String format) throws IOException {
        this(name, format, Collections.<String, String>emptyMap());
    }

    /*
     * Constructs an RDN from its printable representation.
     *
     * An RDN may consist of one or multiple Attribute Value Assertions (AVAs),
     * using '+' as a separator.
     * If the '+' should be considered part of an AVA value, it must be
     * preceded by '\'.
     *
     * @param name String form of RDN
     * @param keyword an additional mapping of keywords to OIDs
     * @throws IOException on parsing error
     */
    RDN(String name, String format, Map<String, String> keywordMap)
        throws IOException {
        if (format.equalsIgnoreCase("RFC2253") == false) {
            throw new IOException("Unsupported format " + format);
        }
        int searchOffset = 0;
        int avaOffset = 0;
        List<sune.security.x509.AVA> avaVec = new ArrayList<sune.security.x509.AVA>(3);
        int nextPlus = name.indexOf('+');
        while (nextPlus >= 0) {
            /*
             * We have encountered an AVA delimiter (plus sign).
             * If the plus sign in the RDN under consideration is
             * preceded by a backslash (escape), or by a double quote, it
             * is part of the AVA. Otherwise, it is used as a separator, to
             * delimit the AVA under consideration from any subsequent AVAs.
             */
            if (nextPlus > 0 && name.charAt(nextPlus - 1) != '\\' ) {
                /*
                 * Plus sign is a separator
                 */
                String avaString = name.substring(avaOffset, nextPlus);
                if (avaString.length() == 0) {
                    throw new IOException("empty AVA in RDN \"" + name + "\"");
                }

                // Parse AVA, and store it in vector
                sune.security.x509.AVA ava = new sune.security.x509.AVA
                    (new StringReader(avaString), sune.security.x509.AVA.RFC2253, keywordMap);
                avaVec.add(ava);

                // Increase the offset
                avaOffset = nextPlus + 1;
            }
            searchOffset = nextPlus + 1;
            nextPlus = name.indexOf('+', searchOffset);
        }

        // parse last or only AVA
        String avaString = name.substring(avaOffset);
        if (avaString.length() == 0) {
            throw new IOException("empty AVA in RDN \"" + name + "\"");
        }
        sune.security.x509.AVA ava = new sune.security.x509.AVA(new StringReader(avaString), sune.security.x509.AVA.RFC2253, keywordMap);
        avaVec.add(ava);

        assertion = avaVec.toArray(new sune.security.x509.AVA[avaVec.size()]);
    }

    /*
     * Constructs an RDN from an ASN.1 encoded value.  The encoding
     * of the name in the stream uses DER (a BER/1 subset).
     *
     * @param value a DER-encoded value holding an RDN.
     * @throws IOException on parsing error.
     */
    RDN(sune.security.util.DerValue rdn) throws IOException {
        if (rdn.tag != sune.security.util.DerValue.tag_Set) {
            throw new IOException("X500 RDN");
        }
        sune.security.util.DerInputStream dis = new sune.security.util.DerInputStream(rdn.toByteArray());
        sune.security.util.DerValue[] avaset = dis.getSet(5);

        assertion = new sune.security.x509.AVA[avaset.length];
        for (int i = 0; i < avaset.length; i++) {
            assertion[i] = new sune.security.x509.AVA(avaset[i]);
        }
    }

    /*
     * Creates an empty RDN with slots for specified
     * number of AVAs.
     *
     * @param i number of AVAs to be in RDN
     */
    RDN(int i) { assertion = new sune.security.x509.AVA[i]; }

    public RDN(sune.security.x509.AVA ava) {
        if (ava == null) {
            throw new NullPointerException();
        }
        assertion = new sune.security.x509.AVA[] { ava };
    }

    public RDN(sune.security.x509.AVA[] avas) {
        assertion = avas.clone();
        for (int i = 0; i < assertion.length; i++) {
            if (assertion[i] == null) {
                throw new NullPointerException();
            }
        }
    }

    /**
     * Return an immutable List of the AVAs in this RDN.
     */
    public List<sune.security.x509.AVA> avas() {
        List<sune.security.x509.AVA> list = avaList;
        if (list == null) {
            list = Collections.unmodifiableList(Arrays.asList(assertion));
            avaList = list;
        }
        return list;
    }

    /**
     * Return the number of AVAs in this RDN.
     */
    public int size() {
        return assertion.length;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof RDN == false) {
            return false;
        }
        RDN other = (RDN)obj;
        if (this.assertion.length != other.assertion.length) {
            return false;
        }
        String thisCanon = this.toRFC2253String(true);
        String otherCanon = other.toRFC2253String(true);
        return thisCanon.equals(otherCanon);
    }

    /*
     * Calculates a hash code value for the object.  Objects
     * which are equal will also have the same hashcode.
     *
     * @returns int hashCode value
     */
    public int hashCode() {
        return toRFC2253String(true).hashCode();
    }

    /*
     * return specified attribute value from RDN
     *
     * @params oid ObjectIdentifier of attribute to be found
     * @returns DerValue of attribute value; null if attribute does not exist
     */
    sune.security.util.DerValue findAttribute(sune.security.util.ObjectIdentifier oid) {
        for (int i = 0; i < assertion.length; i++) {
            if (assertion[i].oid.equals(oid)) {
                return assertion[i].value;
            }
        }
        return null;
    }

    /*
     * Encode the RDN in DER-encoded form.
     *
     * @param out DerOutputStream to which RDN is to be written
     * @throws IOException on error
     */
    void encode(sune.security.util.DerOutputStream out) throws IOException {
        out.putOrderedSetOf(DerValue.tag_Set, assertion);
    }

    /*
     * Returns a printable form of this RDN, using RFC 1779 style catenation
     * of attribute/value assertions, and emitting attribute type keywords
     * from RFCs 1779, 2253, and 3280.
     */
    public String toString() {
        if (assertion.length == 1) {
            return assertion[0].toString();
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < assertion.length; i++) {
            if (i != 0) {
                sb.append(" + ");
            }
            sb.append(assertion[i].toString());
        }
        return sb.toString();
    }

    /*
     * Returns a printable form of this RDN using the algorithm defined in
     * RFC 1779. Only RFC 1779 attribute type keywords are emitted.
     */
    public String toRFC1779String() {
        return toRFC1779String(Collections.<String, String>emptyMap());
    }

    /*
     * Returns a printable form of this RDN using the algorithm defined in
     * RFC 1779. RFC 1779 attribute type keywords are emitted, as well
     * as keywords contained in the OID/keyword map.
     */
    public String toRFC1779String(Map<String, String> oidMap) {
        if (assertion.length == 1) {
            return assertion[0].toRFC1779String(oidMap);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < assertion.length; i++) {
            if (i != 0) {
                sb.append(" + ");
            }
            sb.append(assertion[i].toRFC1779String(oidMap));
        }
        return sb.toString();
    }

    /*
     * Returns a printable form of this RDN using the algorithm defined in
     * RFC 2253. Only RFC 2253 attribute type keywords are emitted.
     */
    public String toRFC2253String() {
        return toRFC2253StringInternal
            (false, Collections.<String, String>emptyMap());
    }

    /*
     * Returns a printable form of this RDN using the algorithm defined in
     * RFC 2253. RFC 2253 attribute type keywords are emitted, as well as
     * keywords contained in the OID/keyword map.
     */
    public String toRFC2253String(Map<String, String> oidMap) {
        return toRFC2253StringInternal(false, oidMap);
    }

    /*
     * Returns a printable form of this RDN using the algorithm defined in
     * RFC 2253. Only RFC 2253 attribute type keywords are emitted.
     * If canonical is true, then additional canonicalizations
     * documented in X500Principal.getName are performed.
     */
    public String toRFC2253String(boolean canonical) {
        if (canonical == false) {
            return toRFC2253StringInternal
                (false, Collections.<String, String>emptyMap());
        }
        String c = canonicalString;
        if (c == null) {
            c = toRFC2253StringInternal
                (true, Collections.<String, String>emptyMap());
            canonicalString = c;
        }
        return c;
    }

    private String toRFC2253StringInternal
        (boolean canonical, Map<String, String> oidMap) {
        /*
         * Section 2.2: When converting from an ASN.1 RelativeDistinguishedName
         * to a string, the output consists of the string encodings of each
         * AttributeTypeAndValue (according to 2.3), in any order.
         *
         * Where there is a multi-valued RDN, the outputs from adjoining
         * AttributeTypeAndValues are separated by a plus ('+' ASCII 43)
         * character.
         */

        // normally, an RDN only contains one AVA
        if (assertion.length == 1) {
            return canonical ? assertion[0].toRFC2253CanonicalString() :
                               assertion[0].toRFC2253String(oidMap);
        }

        StringBuilder relname = new StringBuilder();
        if (!canonical) {
            for (int i = 0; i < assertion.length; i++) {
                if (i > 0) {
                    relname.append('+');
                }
                relname.append(assertion[i].toRFC2253String(oidMap));
            }
        } else {
            // order the string type AVA's alphabetically,
            // followed by the oid type AVA's numerically
            List<sune.security.x509.AVA> avaList = new ArrayList<AVA>(assertion.length);
            for (int i = 0; i < assertion.length; i++) {
                avaList.add(assertion[i]);
            }
            java.util.Collections.sort(avaList, AVAComparator.getInstance());

            for (int i = 0; i < avaList.size(); i++) {
                if (i > 0) {
                    relname.append('+');
                }
                relname.append(avaList.get(i).toRFC2253CanonicalString());
            }
        }
        return relname.toString();
    }

}

class AVAComparator implements Comparator<sune.security.x509.AVA> {

    private static final Comparator<sune.security.x509.AVA> INSTANCE = new AVAComparator();

    private AVAComparator() {
        // empty
    }

    static Comparator<sune.security.x509.AVA> getInstance() {
        return INSTANCE;
    }

    /**
     * AVA's containing a standard keyword are ordered alphabetically,
     * followed by AVA's containing an OID keyword, ordered numerically
     */
    public int compare(sune.security.x509.AVA a1, sune.security.x509.AVA a2) {
        boolean a1Has2253 = a1.hasRFC2253Keyword();
        boolean a2Has2253 = a2.hasRFC2253Keyword();

        if (a1Has2253 == a2Has2253) {
            return a1.toRFC2253CanonicalString().compareTo
                        (a2.toRFC2253CanonicalString());
        } else {
            if (a1Has2253) {
                return -1;
            } else {
                return 1;
            }
        }
    }

}
