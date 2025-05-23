/*
 * Copyright 1997-2006 Sun Microsystems, Inc.  All Rights Reserved.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sune.misc.HexDumpEncoder;
import sune.security.util.DerInputStream;
import sune.security.util.DerOutputStream;
import sune.security.util.DerValue;

import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * The X509CertInfo class represents X.509 certificate information.
 *
 * <P>X.509 certificates have several base data elements, including:<UL>
 *
 * <LI>The <em>Subject Name</em>, an X.500 Distinguished Name for
 *      the entity (subject) for which the certificate was issued.
 *
 * <LI>The <em>Subject Public Key</em>, the public key of the subject.
 *      This is one of the most important parts of the certificate.
 *
 * <LI>The <em>Validity Period</em>, a time period (e.g. six months)
 *      within which the certificate is valid (unless revoked).
 *
 * <LI>The <em>Issuer Name</em>, an X.500 Distinguished Name for the
 *      Certificate Authority (CA) which issued the certificate.
 *
 * <LI>A <em>Serial Number</em> assigned by the CA, for use in
 *      certificate revocation and other applications.
 *</UL>
 *
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 * @see sune.security.x509.CertAttrSet
 * @see X509CertImpl
 */
public class X509CertInfo implements CertAttrSet<String> {

    protected static Logger logger = LoggerFactory.getLogger(X509CertInfo.class);
    /**
     * Identifier for this attribute, to be used with the
     * get, set, delete methods of Certificate, x509 type.
     */
    public static final String IDENT = "x509.info";
    // Certificate attribute names
    public static final String NAME = "info";
    public static final String VERSION = sune.security.x509.CertificateVersion.NAME;
    public static final String SERIAL_NUMBER = sune.security.x509.CertificateSerialNumber.NAME;
    public static final String ALGORITHM_ID = sune.security.x509.CertificateAlgorithmId.NAME;
    public static final String ISSUER = sune.security.x509.CertificateIssuerName.NAME;
    public static final String VALIDITY = sune.security.x509.CertificateValidity.NAME;
    public static final String SUBJECT = sune.security.x509.CertificateSubjectName.NAME;
    public static final String KEY = sune.security.x509.CertificateX509Key.NAME;
    public static final String ISSUER_ID = CertificateIssuerUniqueIdentity.NAME;
    public static final String SUBJECT_ID = CertificateSubjectUniqueIdentity.NAME;
    public static final String EXTENSIONS = sune.security.x509.CertificateExtensions.NAME;

    // X509.v1 data
    protected sune.security.x509.CertificateVersion version = new sune.security.x509.CertificateVersion();
    protected sune.security.x509.CertificateSerialNumber serialNum = null;
    protected sune.security.x509.CertificateAlgorithmId algId = null;
    protected sune.security.x509.CertificateIssuerName issuer = null;
    protected sune.security.x509.CertificateValidity interval = null;
    protected sune.security.x509.CertificateSubjectName subject = null;
    protected sune.security.x509.CertificateX509Key pubKey = null;

    // X509.v2 & v3 extensions
    protected CertificateIssuerUniqueIdentity   issuerUniqueId = null;
    protected CertificateSubjectUniqueIdentity  subjectUniqueId = null;

    // X509.v3 extensions
    protected sune.security.x509.CertificateExtensions extensions = null;

    // Attribute numbers for internal manipulation
    private static final int ATTR_VERSION = 1;
    private static final int ATTR_SERIAL = 2;
    private static final int ATTR_ALGORITHM = 3;
    private static final int ATTR_ISSUER = 4;
    private static final int ATTR_VALIDITY = 5;
    private static final int ATTR_SUBJECT = 6;
    private static final int ATTR_KEY = 7;
    private static final int ATTR_ISSUER_ID = 8;
    private static final int ATTR_SUBJECT_ID = 9;
    private static final int ATTR_EXTENSIONS = 10;

    // DER encoded CertificateInfo data
    private byte[]      rawCertInfo = null;

    // The certificate attribute name to integer mapping stored here
    private static final Map<String,Integer> map = new HashMap<String,Integer>();
    static {
        map.put(VERSION, Integer.valueOf(ATTR_VERSION));
        map.put(SERIAL_NUMBER, Integer.valueOf(ATTR_SERIAL));
        map.put(ALGORITHM_ID, Integer.valueOf(ATTR_ALGORITHM));
        map.put(ISSUER, Integer.valueOf(ATTR_ISSUER));
        map.put(VALIDITY, Integer.valueOf(ATTR_VALIDITY));
        map.put(SUBJECT, Integer.valueOf(ATTR_SUBJECT));
        map.put(KEY, Integer.valueOf(ATTR_KEY));
        map.put(ISSUER_ID, Integer.valueOf(ATTR_ISSUER_ID));
        map.put(SUBJECT_ID, Integer.valueOf(ATTR_SUBJECT_ID));
        map.put(EXTENSIONS, Integer.valueOf(ATTR_EXTENSIONS));
    }

    /**
     * Construct an uninitialized X509CertInfo on which <a href="#decode">
     * decode</a> must later be called (or which may be deserialized).
     */
    public X509CertInfo() { }

    /**
     * Unmarshals a certificate from its encoded form, parsing the
     * encoded bytes.  This form of constructor is used by agents which
     * need to examine and use certificate contents.  That is, this is
     * one of the more commonly used constructors.  Note that the buffer
     * must include only a certificate, and no "garbage" may be left at
     * the end.  If you need to ignore data at the end of a certificate,
     * use another constructor.
     *
     * @param cert the encoded bytes, with no trailing data.
     * @exception CertificateParsingException on parsing errors.
     */
    public X509CertInfo(byte[] cert) throws CertificateParsingException {
        try {
            DerValue    in = new DerValue(cert);

            parse(in);
        } catch (IOException e) {
            CertificateParsingException parseException =
                        new CertificateParsingException(e.toString());
            parseException.initCause(e);
            throw parseException;
        }
    }

    /**
     * Unmarshal a certificate from its encoded form, parsing a DER value.
     * This form of constructor is used by agents which need to examine
     * and use certificate contents.
     *
     * @param derVal the der value containing the encoded cert.
     * @exception CertificateParsingException on parsing errors.
     */
    public X509CertInfo(DerValue derVal) throws CertificateParsingException {
        try {
            parse(derVal);
        } catch (IOException e) {
            CertificateParsingException parseException =
                        new CertificateParsingException(e.toString());
            parseException.initCause(e);
            throw parseException;
        }
    }

    /**
     * Appends the certificate to an output stream.
     *
     * @param out an output stream to which the certificate is appended.
     * @exception CertificateException on encoding errors.
     * @exception IOException on other errors.
     */
    public void encode(OutputStream out)
    throws CertificateException, IOException {
        if (rawCertInfo == null) {
            DerOutputStream tmp = new DerOutputStream();
            emit(tmp);
            rawCertInfo = tmp.toByteArray();
        }
        out.write(rawCertInfo.clone());
    }

    /**
     * Return an enumeration of names of attributes existing within this
     * attribute.
     */
    public Enumeration<String> getElements() {
        sune.security.x509.AttributeNameEnumeration elements = new AttributeNameEnumeration();
        elements.addElement(VERSION);
        elements.addElement(SERIAL_NUMBER);
        elements.addElement(ALGORITHM_ID);
        elements.addElement(ISSUER);
        elements.addElement(VALIDITY);
        elements.addElement(SUBJECT);
        elements.addElement(KEY);
        elements.addElement(ISSUER_ID);
        elements.addElement(SUBJECT_ID);
        elements.addElement(EXTENSIONS);

        return elements.elements();
    }

    /**
     * Return the name of this attribute.
     */
    public String getName() {
        return(NAME);
    }

    /**
     * Returns the encoded certificate info.
     *
     * @exception CertificateEncodingException on encoding information errors.
     */
    public byte[] getEncodedInfo() throws CertificateEncodingException {
        try {
            if (rawCertInfo == null) {
                DerOutputStream tmp = new DerOutputStream();
                emit(tmp);
                rawCertInfo = tmp.toByteArray();
            }
            return rawCertInfo.clone();
        } catch (IOException e) {
            throw new CertificateEncodingException(e.toString(), e);
        } catch (CertificateException e) {
            throw new CertificateEncodingException(e.toString(), e);
        }
    }

    /**
     * Compares two X509CertInfo objects.  This is false if the
     * certificates are not both X.509 certs, otherwise it
     * compares them as binary data.
     *
     * @param other the object being compared with this one
     * @return true iff the certificates are equivalent
     */
    public boolean equals(Object other) {
        if (other instanceof X509CertInfo) {
            return equals((X509CertInfo) other);
        } else {
            return false;
        }
    }

    /**
     * Compares two certificates, returning false if any data
     * differs between the two.
     *
     * @param other the object being compared with this one
     * @return true iff the certificates are equivalent
     */
    public boolean equals(X509CertInfo other) {
        if (this == other) {
            return(true);
        } else if (rawCertInfo == null || other.rawCertInfo == null) {
            return(false);
        } else if (rawCertInfo.length != other.rawCertInfo.length) {
            return(false);
        }
        for (int i = 0; i < rawCertInfo.length; i++) {
            if (rawCertInfo[i] != other.rawCertInfo[i]) {
                return(false);
            }
        }
        return(true);
    }

    /**
     * Calculates a hash code value for the object.  Objects
     * which are equal will also have the same hashcode.
     */
    public int hashCode() {
        int     retval = 0;

        for (int i = 1; i < rawCertInfo.length; i++) {
            retval += rawCertInfo[i] * i;
        }
        return(retval);
    }

    /**
     * Returns a printable representation of the certificate.
     */
    public String toString() {

        if (subject == null || pubKey == null || interval == null
            || issuer == null || algId == null || serialNum == null) {
                throw new NullPointerException("X.509 cert is incomplete");
        }
        StringBuilder sb = new StringBuilder();

        sb.append("[\n");
        sb.append("  " + version.toString() + "\n");
        sb.append("  Subject: " + subject.toString() + "\n");
        sb.append("  Signature Algorithm: " + algId.toString() + "\n");
        sb.append("  Key:  " + pubKey.toString() + "\n");
        sb.append("  " + interval.toString() + "\n");
        sb.append("  Issuer: " + issuer.toString() + "\n");
        sb.append("  " + serialNum.toString() + "\n");

        // optional v2, v3 extras
        if (issuerUniqueId != null) {
            sb.append("  Issuer Id:\n" + issuerUniqueId.toString() + "\n");
        }
        if (subjectUniqueId != null) {
            sb.append("  Subject Id:\n" + subjectUniqueId.toString() + "\n");
        }
        if (extensions != null) {
            Collection allExts = extensions.getAllExtensions();
            Object[] objs = allExts.toArray();
            sb.append("\nCertificate Extensions: " + objs.length);
            for (int i = 0; i < objs.length; i++) {
                sb.append("\n[" + (i+1) + "]: ");
                sune.security.x509.Extension ext = (sune.security.x509.Extension)objs[i];
                try {
                    if (OIDMap.getClass(ext.getExtensionId()) == null) {
                        sb.append(ext.toString());
                        byte[] extValue = ext.getExtensionValue();
                        if (extValue != null) {
                            DerOutputStream out = new DerOutputStream();
                            out.putOctetString(extValue);
                            extValue = out.toByteArray();
                            HexDumpEncoder enc = new HexDumpEncoder();
                            sb.append("Extension unknown: "
                                      + "DER encoded OCTET string =\n"
                                      + enc.encodeBuffer(extValue) + "\n");
                        }
                    } else
                        sb.append(ext.toString()); //sub-class exists
                } catch (Exception e) {
                    logger.warn("Warning in X509CertInfo", e);
                    sb.append(", Error parsing this extension");
                }
            }
            Map<String, sune.security.x509.Extension> invalid = extensions.getUnparseableExtensions();
            if (invalid.isEmpty() == false) {
                sb.append("\nUnparseable certificate extensions: " + invalid.size());
                int i = 1;
                for (Extension ext : invalid.values()) {
                    sb.append("\n[" + (i++) + "]: ");
                    sb.append(ext);
                }
            }
        }
        sb.append("\n]");
        return sb.toString();
    }

    /**
     * Set the certificate attribute.
     *
     * @param name the name of the Certificate attribute.
     * @param val the value of the Certificate attribute.
     * @exception CertificateException on invalid attributes.
     * @exception IOException on other errors.
     */
    public void set(String name, Object val)
    throws CertificateException, IOException {
        sune.security.x509.X509AttributeName attrName = new sune.security.x509.X509AttributeName(name);

        int attr = attributeMap(attrName.getPrefix());
        if (attr == 0) {
            throw new CertificateException("Attribute name not recognized: "
                                           + name);
        }
        // set rawCertInfo to null, so that we are forced to re-encode
        rawCertInfo = null;
        String suffix = attrName.getSuffix();

        switch (attr) {
        case ATTR_VERSION:
            if (suffix == null) {
                setVersion(val);
            } else {
                version.set(suffix, val);
            }
            break;

        case ATTR_SERIAL:
            if (suffix == null) {
                setSerialNumber(val);
            } else {
                serialNum.set(suffix, val);
            }
            break;

        case ATTR_ALGORITHM:
            if (suffix == null) {
                setAlgorithmId(val);
            } else {
                algId.set(suffix, val);
            }
            break;

        case ATTR_ISSUER:
            if (suffix == null) {
                setIssuer(val);
            } else {
                issuer.set(suffix, val);
            }
            break;

        case ATTR_VALIDITY:
            if (suffix == null) {
                setValidity(val);
            } else {
                interval.set(suffix, val);
            }
            break;

        case ATTR_SUBJECT:
            if (suffix == null) {
                setSubject(val);
            } else {
                subject.set(suffix, val);
            }
            break;

        case ATTR_KEY:
            if (suffix == null) {
                setKey(val);
            } else {
                pubKey.set(suffix, val);
            }
            break;

        case ATTR_ISSUER_ID:
            if (suffix == null) {
                setIssuerUniqueId(val);
            } else {
                issuerUniqueId.set(suffix, val);
            }
            break;

        case ATTR_SUBJECT_ID:
            if (suffix == null) {
                setSubjectUniqueId(val);
            } else {
                subjectUniqueId.set(suffix, val);
            }
            break;

        case ATTR_EXTENSIONS:
            if (suffix == null) {
                setExtensions(val);
            } else {
                if (extensions == null)
                    extensions = new sune.security.x509.CertificateExtensions();
                extensions.set(suffix, val);
            }
            break;
        }
    }

    /**
     * Delete the certificate attribute.
     *
     * @param name the name of the Certificate attribute.
     * @exception CertificateException on invalid attributes.
     * @exception IOException on other errors.
     */
    public void delete(String name)
    throws CertificateException, IOException {
        sune.security.x509.X509AttributeName attrName = new sune.security.x509.X509AttributeName(name);

        int attr = attributeMap(attrName.getPrefix());
        if (attr == 0) {
            throw new CertificateException("Attribute name not recognized: "
                                           + name);
        }
        // set rawCertInfo to null, so that we are forced to re-encode
        rawCertInfo = null;
        String suffix = attrName.getSuffix();

        switch (attr) {
        case ATTR_VERSION:
            if (suffix == null) {
                version = null;
            } else {
                version.delete(suffix);
            }
            break;
        case (ATTR_SERIAL):
            if (suffix == null) {
                serialNum = null;
            } else {
                serialNum.delete(suffix);
            }
            break;
        case (ATTR_ALGORITHM):
            if (suffix == null) {
                algId = null;
            } else {
                algId.delete(suffix);
            }
            break;
        case (ATTR_ISSUER):
            if (suffix == null) {
                issuer = null;
            } else {
                issuer.delete(suffix);
            }
            break;
        case (ATTR_VALIDITY):
            if (suffix == null) {
                interval = null;
            } else {
                interval.delete(suffix);
            }
            break;
        case (ATTR_SUBJECT):
            if (suffix == null) {
                subject = null;
            } else {
                subject.delete(suffix);
            }
            break;
        case (ATTR_KEY):
            if (suffix == null) {
                pubKey = null;
            } else {
                pubKey.delete(suffix);
            }
            break;
        case (ATTR_ISSUER_ID):
            if (suffix == null) {
                issuerUniqueId = null;
            } else {
                issuerUniqueId.delete(suffix);
            }
            break;
        case (ATTR_SUBJECT_ID):
            if (suffix == null) {
                subjectUniqueId = null;
            } else {
                subjectUniqueId.delete(suffix);
            }
            break;
        case (ATTR_EXTENSIONS):
            if (suffix == null) {
                extensions = null;
            } else {
                if (extensions != null)
                   extensions.delete(suffix);
            }
            break;
        }
    }

    /**
     * Get the certificate attribute.
     *
     * @param name the name of the Certificate attribute.
     *
     * @exception CertificateException on invalid attributes.
     * @exception IOException on other errors.
     */
    public Object get(String name)
    throws CertificateException, IOException {
        sune.security.x509.X509AttributeName attrName = new X509AttributeName(name);

        int attr = attributeMap(attrName.getPrefix());
        if (attr == 0) {
            throw new CertificateParsingException(
                          "Attribute name not recognized: " + name);
        }
        String suffix = attrName.getSuffix();

        switch (attr) { // frequently used attributes first
        case (ATTR_EXTENSIONS):
            if (suffix == null) {
                return(extensions);
            } else {
                if (extensions == null) {
                    return null;
                } else {
                    return(extensions.get(suffix));
                }
            }
        case (ATTR_SUBJECT):
            if (suffix == null) {
                return(subject);
            } else {
                return(subject.get(suffix));
            }
        case (ATTR_ISSUER):
            if (suffix == null) {
                return(issuer);
            } else {
                return(issuer.get(suffix));
            }
        case (ATTR_KEY):
            if (suffix == null) {
                return(pubKey);
            } else {
                return(pubKey.get(suffix));
            }
        case (ATTR_ALGORITHM):
            if (suffix == null) {
                return(algId);
            } else {
                return(algId.get(suffix));
            }
        case (ATTR_VALIDITY):
            if (suffix == null) {
                return(interval);
            } else {
                return(interval.get(suffix));
            }
        case (ATTR_VERSION):
            if (suffix == null) {
                return(version);
            } else {
                return(version.get(suffix));
            }
        case (ATTR_SERIAL):
            if (suffix == null) {
                return(serialNum);
            } else {
                return(serialNum.get(suffix));
            }
        case (ATTR_ISSUER_ID):
            if (suffix == null) {
                return(issuerUniqueId);
            } else {
                if (issuerUniqueId == null)
                    return null;
                else
                    return(issuerUniqueId.get(suffix));
            }
        case (ATTR_SUBJECT_ID):
            if (suffix == null) {
                return(subjectUniqueId);
            } else {
                if (subjectUniqueId == null)
                    return null;
                else
                    return(subjectUniqueId.get(suffix));
            }
        }
        return null;
    }

    /*
     * This routine unmarshals the certificate information.
     */
    private void parse(DerValue val)
    throws CertificateParsingException, IOException {
        DerInputStream  in;
        DerValue        tmp;

        if (val.tag != DerValue.tag_Sequence) {
            throw new CertificateParsingException("signed fields invalid");
        }
        rawCertInfo = val.toByteArray();

        in = val.data;

        // Version
        tmp = in.getDerValue();
        if (tmp.isContextSpecific((byte)0)) {
            version = new sune.security.x509.CertificateVersion(tmp);
            tmp = in.getDerValue();
        }

        // Serial number ... an integer
        serialNum = new sune.security.x509.CertificateSerialNumber(tmp);

        // Algorithm Identifier
        algId = new sune.security.x509.CertificateAlgorithmId(in);

        // Issuer name
        issuer = new sune.security.x509.CertificateIssuerName(in);
        sune.security.x509.X500Name issuerDN = (sune.security.x509.X500Name)issuer.get(sune.security.x509.CertificateIssuerName.DN_NAME);
        if (issuerDN.isEmpty()) {
            throw new CertificateParsingException(
                "Empty issuer DN not allowed in X509Certificates");
        }

        // validity:  SEQUENCE { start date, end date }
        interval = new sune.security.x509.CertificateValidity(in);

        // subject name
        subject = new sune.security.x509.CertificateSubjectName(in);
        sune.security.x509.X500Name subjectDN = (sune.security.x509.X500Name)subject.get(sune.security.x509.CertificateSubjectName.DN_NAME);
        if ((version.compare(sune.security.x509.CertificateVersion.V1) == 0) &&
                subjectDN.isEmpty()) {
            throw new CertificateParsingException(
                      "Empty subject DN not allowed in v1 certificate");
        }

        // public key
        pubKey = new sune.security.x509.CertificateX509Key(in);

        // If more data available, make sure version is not v1.
        if (in.available() != 0) {
            if (version.compare(sune.security.x509.CertificateVersion.V1) == 0) {
                throw new CertificateParsingException(
                          "no more data allowed for version 1 certificate");
            }
        } else {
            return;
        }

        // Get the issuerUniqueId if present
        tmp = in.getDerValue();
        if (tmp.isContextSpecific((byte)1)) {
            issuerUniqueId = new CertificateIssuerUniqueIdentity(tmp);
            if (in.available() == 0)
                return;
            tmp = in.getDerValue();
        }

        // Get the subjectUniqueId if present.
        if (tmp.isContextSpecific((byte)2)) {
            subjectUniqueId = new CertificateSubjectUniqueIdentity(tmp);
            if (in.available() == 0)
                return;
            tmp = in.getDerValue();
        }

        // Get the extensions.
        if (version.compare(sune.security.x509.CertificateVersion.V3) != 0) {
            throw new CertificateParsingException(
                      "Extensions not allowed in v2 certificate");
        }
        if (tmp.isConstructed() && tmp.isContextSpecific((byte)3)) {
            extensions = new sune.security.x509.CertificateExtensions(tmp.data);
        }

        // verify X.509 V3 Certificate
        verifyCert(subject, extensions);

    }

    /*
     * Verify if X.509 V3 Certificate is compliant with RFC 3280.
     */
    private void verifyCert(sune.security.x509.CertificateSubjectName subject,
                            sune.security.x509.CertificateExtensions extensions)
        throws CertificateParsingException, IOException {

        // if SubjectName is empty, check for SubjectAlternativeNameExtension
        sune.security.x509.X500Name subjectDN = (X500Name)subject.get(sune.security.x509.CertificateSubjectName.DN_NAME);
        if (subjectDN.isEmpty()) {
            if (extensions == null) {
                throw new CertificateParsingException("X.509 Certificate is " +
                        "incomplete: subject field is empty, and certificate " +
                        "has no extensions");
            }
            sune.security.x509.SubjectAlternativeNameExtension subjectAltNameExt = null;
            sune.security.x509.SubjectAlternativeNameExtension extValue = null;
            sune.security.x509.GeneralNames names = null;
            try {
                subjectAltNameExt = (sune.security.x509.SubjectAlternativeNameExtension)
                        extensions.get(sune.security.x509.SubjectAlternativeNameExtension.NAME);
                names = (GeneralNames) subjectAltNameExt.get
                        (SubjectAlternativeNameExtension.SUBJECT_NAME);
            } catch (IOException e) {
                throw new CertificateParsingException("X.509 Certificate is " +
                        "incomplete: subject field is empty, and " +
                        "SubjectAlternativeName extension is absent", e);
            }

            // SubjectAlternativeName extension is empty or not marked critical
            if (names == null || names.isEmpty()) {
                throw new CertificateParsingException("X.509 Certificate is " +
                        "incomplete: subject field is empty, and " +
                        "SubjectAlternativeName extension is empty");
            } else if (subjectAltNameExt.isCritical() == false) {
                throw new CertificateParsingException("X.509 Certificate is " +
                        "incomplete: SubjectAlternativeName extension MUST " +
                        "be marked critical when subject field is empty");
            }
        }
    }

    /*
     * Marshal the contents of a "raw" certificate into a DER sequence.
     */
    private void emit(DerOutputStream out)
    throws CertificateException, IOException {
        DerOutputStream tmp = new DerOutputStream();

        // version number, iff not V1
        version.encode(tmp);

        // Encode serial number, issuer signing algorithm, issuer name
        // and validity
        serialNum.encode(tmp);
        algId.encode(tmp);

        if ((version.compare(sune.security.x509.CertificateVersion.V1) == 0) &&
            (issuer.toString() == null))
            throw new CertificateParsingException(
                      "Null issuer DN not allowed in v1 certificate");

        issuer.encode(tmp);
        interval.encode(tmp);

        // Encode subject (principal) and associated key
        if ((version.compare(sune.security.x509.CertificateVersion.V1) == 0) &&
            (subject.toString() == null))
            throw new CertificateParsingException(
                      "Null subject DN not allowed in v1 certificate");
        subject.encode(tmp);
        pubKey.encode(tmp);

        // Encode issuerUniqueId & subjectUniqueId.
        if (issuerUniqueId != null) {
            issuerUniqueId.encode(tmp);
        }
        if (subjectUniqueId != null) {
            subjectUniqueId.encode(tmp);
        }

        // Write all the extensions.
        if (extensions != null) {
            extensions.encode(tmp);
        }

        // Wrap the data; encoding of the "raw" cert is now complete.
        out.write(DerValue.tag_Sequence, tmp);
    }

    /**
     * Returns the integer attribute number for the passed attribute name.
     */
    private int attributeMap(String name) {
        Integer num = map.get(name);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    /**
     * Set the version number of the certificate.
     *
     * @params val the Object class value for the Extensions
     * @exception CertificateException on invalid data.
     */
    private void setVersion(Object val) throws CertificateException {
        if (!(val instanceof sune.security.x509.CertificateVersion)) {
            throw new CertificateException("Version class type invalid.");
        }
        version = (sune.security.x509.CertificateVersion)val;
    }

    /**
     * Set the serial number of the certificate.
     *
     * @params val the Object class value for the CertificateSerialNumber
     * @exception CertificateException on invalid data.
     */
    private void setSerialNumber(Object val) throws CertificateException {
        if (!(val instanceof sune.security.x509.CertificateSerialNumber)) {
            throw new CertificateException("SerialNumber class type invalid.");
        }
        serialNum = (CertificateSerialNumber)val;
    }

    /**
     * Set the algorithm id of the certificate.
     *
     * @params val the Object class value for the AlgorithmId
     * @exception CertificateException on invalid data.
     */
    private void setAlgorithmId(Object val) throws CertificateException {
        if (!(val instanceof sune.security.x509.CertificateAlgorithmId)) {
            throw new CertificateException(
                             "AlgorithmId class type invalid.");
        }
        algId = (CertificateAlgorithmId)val;
    }

    /**
     * Set the issuer name of the certificate.
     *
     * @params val the Object class value for the issuer
     * @exception CertificateException on invalid data.
     */
    private void setIssuer(Object val) throws CertificateException {
        if (!(val instanceof sune.security.x509.CertificateIssuerName)) {
            throw new CertificateException(
                             "Issuer class type invalid.");
        }
        issuer = (CertificateIssuerName)val;
    }

    /**
     * Set the validity interval of the certificate.
     *
     * @params val the Object class value for the CertificateValidity
     * @exception CertificateException on invalid data.
     */
    private void setValidity(Object val) throws CertificateException {
        if (!(val instanceof sune.security.x509.CertificateValidity)) {
            throw new CertificateException(
                             "CertificateValidity class type invalid.");
        }
        interval = (CertificateValidity)val;
    }

    /**
     * Set the subject name of the certificate.
     *
     * @params val the Object class value for the Subject
     * @exception CertificateException on invalid data.
     */
    private void setSubject(Object val) throws CertificateException {
        if (!(val instanceof sune.security.x509.CertificateSubjectName)) {
            throw new CertificateException(
                             "Subject class type invalid.");
        }
        subject = (CertificateSubjectName)val;
    }

    /**
     * Set the public key in the certificate.
     *
     * @params val the Object class value for the PublicKey
     * @exception CertificateException on invalid data.
     */
    private void setKey(Object val) throws CertificateException {
        if (!(val instanceof sune.security.x509.CertificateX509Key)) {
            throw new CertificateException(
                             "Key class type invalid.");
        }
        pubKey = (CertificateX509Key)val;
    }

    /**
     * Set the Issuer Unique Identity in the certificate.
     *
     * @params val the Object class value for the IssuerUniqueId
     * @exception CertificateException
     */
    private void setIssuerUniqueId(Object val) throws CertificateException {
        if (version.compare(sune.security.x509.CertificateVersion.V2) < 0) {
            throw new CertificateException("Invalid version");
        }
        if (!(val instanceof CertificateIssuerUniqueIdentity)) {
            throw new CertificateException(
                             "IssuerUniqueId class type invalid.");
        }
        issuerUniqueId = (CertificateIssuerUniqueIdentity)val;
    }

    /**
     * Set the Subject Unique Identity in the certificate.
     *
     * @params val the Object class value for the SubjectUniqueId
     * @exception CertificateException
     */
    private void setSubjectUniqueId(Object val) throws CertificateException {
        if (version.compare(sune.security.x509.CertificateVersion.V2) < 0) {
            throw new CertificateException("Invalid version");
        }
        if (!(val instanceof CertificateSubjectUniqueIdentity)) {
            throw new CertificateException(
                             "SubjectUniqueId class type invalid.");
        }
        subjectUniqueId = (CertificateSubjectUniqueIdentity)val;
    }

    /**
     * Set the extensions in the certificate.
     *
     * @params val the Object class value for the Extensions
     * @exception CertificateException
     */
    private void setExtensions(Object val) throws CertificateException {
        if (version.compare(CertificateVersion.V3) < 0) {
            throw new CertificateException("Invalid version");
        }
        if (!(val instanceof sune.security.x509.CertificateExtensions)) {
          throw new CertificateException(
                             "Extensions class type invalid.");
        }
        extensions = (CertificateExtensions)val;
    }
}
