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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sune.misc.BASE64Decoder;
import sune.misc.HexDumpEncoder;
import sune.security.provider.X509Factory;
import sune.security.util.DerEncoder;
import sune.security.util.DerInputStream;
import sune.security.util.DerOutputStream;
import sune.security.util.DerValue;
import sune.security.util.ObjectIdentifier;

import javax.security.auth.x500.X500Principal;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The X509CertImpl class represents an X.509 certificate. These certificates
 * are widely used to support authentication and other functionality in
 * Internet security systems.  Common applications include Privacy Enhanced
 * Mail (PEM), Transport Layer Security (SSL), code signing for trusted
 * software distribution, and Secure Electronic Transactions (SET).  There
 * is a commercial infrastructure ready to manage large scale deployments
 * of X.509 identity certificates.
 *
 * <P>These certificates are managed and vouched for by <em>Certificate
 * Authorities</em> (CAs).  CAs are services which create certificates by
 * placing data in the X.509 standard format and then digitally signing
 * that data.  Such signatures are quite difficult to forge.  CAs act as
 * trusted third parties, making introductions between agents who have no
 * direct knowledge of each other.  CA certificates are either signed by
 * themselves, or by some other CA such as a "root" CA.
 *
 * <P>RFC 1422 is very informative, though it does not describe much
 * of the recent work being done with X.509 certificates.  That includes
 * a 1996 version (X.509v3) and a variety of enhancements being made to
 * facilitate an explosion of personal certificates used as "Internet
 * Drivers' Licences", or with SET for credit card transactions.
 *
 * <P>More recent work includes the IETF PKIX Working Group efforts,
 * especially RFC2459.
 *
 * @author Dave Brownell
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 * @see sune.security.x509.X509CertInfo
 */
public class X509CertImpl extends X509Certificate implements DerEncoder {

    protected static Logger logger = LoggerFactory.getLogger(X509CertImpl.class);

    private static final long serialVersionUID = -3457612960190864406L;

    private static final String DOT = ".";
    /**
     * Public attribute names.
     */
    public static final String NAME = "x509";
    public static final String INFO = sune.security.x509.X509CertInfo.NAME;
    public static final String ALG_ID = "algorithm";
    public static final String SIGNATURE = "signature";
    public static final String SIGNED_CERT = "signed_cert";

    /**
     * The following are defined for ease-of-use. These
     * are the most frequently retrieved attributes.
     */
    // x509.info.subject.dname
    public static final String SUBJECT_DN = NAME + DOT + INFO + DOT +
                               sune.security.x509.X509CertInfo.SUBJECT + DOT +
                               sune.security.x509.CertificateSubjectName.DN_NAME;
    // x509.info.issuer.dname
    public static final String ISSUER_DN = NAME + DOT + INFO + DOT +
                               sune.security.x509.X509CertInfo.ISSUER + DOT +
                               sune.security.x509.CertificateIssuerName.DN_NAME;
    // x509.info.serialNumber.number
    public static final String SERIAL_ID = NAME + DOT + INFO + DOT +
                               sune.security.x509.X509CertInfo.SERIAL_NUMBER + DOT +
                               sune.security.x509.CertificateSerialNumber.NUMBER;
    // x509.info.key.value
    public static final String PUBLIC_KEY = NAME + DOT + INFO + DOT +
                               sune.security.x509.X509CertInfo.KEY + DOT +
                               sune.security.x509.CertificateX509Key.KEY;

    // x509.info.version.value
    public static final String VERSION = NAME + DOT + INFO + DOT +
                               sune.security.x509.X509CertInfo.VERSION + DOT +
                               sune.security.x509.CertificateVersion.VERSION;

    // x509.algorithm
    public static final String SIG_ALG = NAME + DOT + ALG_ID;

    // x509.signature
    public static final String SIG = NAME + DOT + SIGNATURE;

    // when we sign and decode we set this to true
    // this is our means to make certificates immutable
    private boolean readOnly = false;

    // Certificate data, and its envelope
    private byte[]              signedCert = null;
    protected sune.security.x509.X509CertInfo info = null;
    protected sune.security.x509.AlgorithmId algId = null;
    protected byte[]            signature = null;

    // recognized extension OIDS
    private static final String KEY_USAGE_OID = "2.5.29.15";
    private static final String EXTENDED_KEY_USAGE_OID = "2.5.29.37";
    private static final String BASIC_CONSTRAINT_OID = "2.5.29.19";
    private static final String SUBJECT_ALT_NAME_OID = "2.5.29.17";
    private static final String ISSUER_ALT_NAME_OID = "2.5.29.18";
    private static final String AUTH_INFO_ACCESS_OID = "1.3.6.1.5.5.7.1.1";

    // number of standard key usage bits.
    private static final int NUM_STANDARD_KEY_USAGE = 9;

    // SubjectAlterntativeNames cache
    private Collection<List<?>> subjectAlternativeNames;

    // IssuerAlternativeNames cache
    private Collection<List<?>> issuerAlternativeNames;

    // ExtendedKeyUsage cache
    private List<String> extKeyUsage;

    // AuthorityInformationAccess cache
    private Set<AccessDescription> authInfoAccess;

    /**
     * PublicKey that has previously been used to verify
     * the signature of this certificate. Null if the certificate has not
     * yet been verified.
     */
    private PublicKey verifiedPublicKey;
    /**
     * If verifiedPublicKey is not null, name of the provider used to
     * successfully verify the signature of this certificate, or the
     * empty String if no provider was explicitly specified.
     */
    private String verifiedProvider;
    /**
     * If verifiedPublicKey is not null, result of the verification using
     * verifiedPublicKey and verifiedProvider. If true, verification was
     * successful, if false, it failed.
     */
    private boolean verificationResult;

    /**
     * Default constructor.
     */
    public X509CertImpl() { }

    /**
     * Unmarshals a certificate from its encoded form, parsing the
     * encoded bytes.  This form of constructor is used by agents which
     * need to examine and use certificate contents.  That is, this is
     * one of the more commonly used constructors.  Note that the buffer
     * must include only a certificate, and no "garbage" may be left at
     * the end.  If you need to ignore data at the end of a certificate,
     * use another constructor.
     *
     * @param certData the encoded bytes, with no trailing padding.
     * @exception CertificateException on parsing and initialization errors.
     */
    public X509CertImpl(byte[] certData) throws CertificateException {
        try {
            parse(new DerValue(certData));
        } catch (IOException e) {
            signedCert = null;
            CertificateException ce = new
                CertificateException("Unable to initialize, " + e);
            ce.initCause(e);
            throw ce;
        }
    }

    /**
     * unmarshals an X.509 certificate from an input stream.  If the
     * certificate is RFC1421 hex-encoded, then it must begin with
     * the line X509Factory.BEGIN_CERT and end with the line
     * X509Factory.END_CERT.
     *
     * @param in an input stream holding at least one certificate that may
     *        be either DER-encoded or RFC1421 hex-encoded version of the
     *        DER-encoded certificate.
     * @exception CertificateException on parsing and initialization errors.
     */
    public X509CertImpl(InputStream in) throws CertificateException {

        DerValue der = null;

        BufferedInputStream inBuffered = new BufferedInputStream(in);

        // First try reading stream as HEX-encoded DER-encoded bytes,
        // since not mistakable for raw DER
        try {
            inBuffered.mark(Integer.MAX_VALUE);
            der = readRFC1421Cert(inBuffered);
        } catch (IOException ioe) {
            logger.debug("Debug in X509CertImpl", ioe);
            try {
                // Next, try reading stream as raw DER-encoded bytes
                inBuffered.reset();
                der = new DerValue(inBuffered);
            } catch (IOException ioe1) {
                CertificateException ce = new
                    CertificateException("Input stream must be " +
                                         "either DER-encoded bytes " +
                                         "or RFC1421 hex-encoded " +
                                         "DER-encoded bytes: " +
                                         ioe1.getMessage());
                ce.initCause(ioe1);
                throw ce;
            }
        }
        try {
            parse(der);
        } catch (IOException ioe) {
            signedCert = null;
            CertificateException ce = new
                CertificateException("Unable to parse DER value of " +
                                     "certificate, " + ioe);
            ce.initCause(ioe);
            throw ce;
        }
    }

    /**
     * read input stream as HEX-encoded DER-encoded bytes
     *
     * @param in InputStream to read
     * @returns DerValue corresponding to decoded HEX-encoded bytes
     * @throws IOException if stream can not be interpreted as RFC1421
     *                     encoded bytes
     */
    private DerValue readRFC1421Cert(InputStream in) throws IOException {
        DerValue der = null;
        String line = null;
        BufferedReader certBufferedReader =
            new BufferedReader(new InputStreamReader(in, "ASCII"));
        try {
            line = certBufferedReader.readLine();
        } catch (IOException ioe1) {
            throw new IOException("Unable to read InputStream: " + ioe1.getMessage(), ioe1);
        }
        if (line.equals(X509Factory.BEGIN_CERT)) {
            /* stream appears to be hex-encoded bytes */
            BASE64Decoder         decoder   = new BASE64Decoder();
            ByteArrayOutputStream decstream = new ByteArrayOutputStream();
            try {
                while ((line = certBufferedReader.readLine()) != null) {
                    if (line.equals(X509Factory.END_CERT)) {
                        der = new DerValue(decstream.toByteArray());
                        break;
                    } else {
                        decstream.write(decoder.decodeBuffer(line));
                    }
                }
            } catch (IOException ioe2) {
                throw new IOException("Unable to read InputStream: " + ioe2.getMessage(), ioe2);
            }
        } else {
            throw new IOException("InputStream is not RFC1421 hex-encoded " +
                                  "DER bytes");
        }
        return der;
    }

    /**
     * Construct an initialized X509 Certificate. The certificate is stored
     * in raw form and has to be signed to be useful.
     *
     * @param certInfo the X509CertificateInfo which the Certificate is to be
     *              created from.
     */
    public X509CertImpl(sune.security.x509.X509CertInfo certInfo) {
        this.info = certInfo;
    }

    /**
     * Unmarshal a certificate from its encoded form, parsing a DER value.
     * This form of constructor is used by agents which need to examine
     * and use certificate contents.
     *
     * @param derVal the der value containing the encoded cert.
     * @exception CertificateException on parsing and initialization errors.
     */
    public X509CertImpl(DerValue derVal) throws CertificateException {
        try {
            parse(derVal);
        } catch (IOException e) {
            signedCert = null;
            CertificateException ce = new
                CertificateException("Unable to initialize, " + e);
            ce.initCause(e);
            throw ce;
        }
    }

    /**
     * Appends the certificate to an output stream.
     *
     * @param out an input stream to which the certificate is appended.
     * @exception CertificateEncodingException on encoding errors.
     */
    public void encode(OutputStream out)
    throws CertificateEncodingException {
        if (signedCert == null)
            throw new CertificateEncodingException(
                          "Null certificate to encode");
        try {
            out.write(signedCert.clone());
        } catch (IOException e) {
            throw new CertificateEncodingException(e.toString(), e);
        }
    }

    /**
     * DER encode this object onto an output stream.
     * Implements the <code>DerEncoder</code> interface.
     *
     * @param out the output stream on which to write the DER encoding.
     *
     * @exception IOException on encoding error.
     */
    public void derEncode(OutputStream out) throws IOException {
        if (signedCert == null)
            throw new IOException("Null certificate to encode");
        out.write(signedCert.clone());
    }

    /**
     * Returns the encoded form of this certificate. It is
     * assumed that each certificate type would have only a single
     * form of encoding; for example, X.509 certificates would
     * be encoded as ASN.1 DER.
     *
     * @exception CertificateEncodingException if an encoding error occurs.
     */
    public byte[] getEncoded() throws CertificateEncodingException {
        return getEncodedInternal().clone();
    }

    /**
     * Returned the encoding as an uncloned byte array. Callers must
     * guarantee that they neither modify it nor expose it to untrusted
     * code.
     */
    public byte[] getEncodedInternal() throws CertificateEncodingException {
        if (signedCert == null) {
            throw new CertificateEncodingException(
                          "Null certificate to encode");
        }
        return signedCert;
    }

    /**
     * Throws an exception if the certificate was not signed using the
     * verification key provided.  Successfully verifying a certificate
     * does <em>not</em> indicate that one should trust the entity which
     * it represents.
     *
     * @param key the public key used for verification.
     *
     * @exception InvalidKeyException on incorrect key.
     * @exception NoSuchAlgorithmException on unsupported signature
     * algorithms.
     * @exception NoSuchProviderException if there's no default provider.
     * @exception SignatureException on signature errors.
     * @exception CertificateException on encoding errors.
     */
    public void verify(PublicKey key)
    throws CertificateException, NoSuchAlgorithmException,
        InvalidKeyException, NoSuchProviderException, SignatureException {

        verify(key, "");
    }

    /**
     * Throws an exception if the certificate was not signed using the
     * verification key provided.  Successfully verifying a certificate
     * does <em>not</em> indicate that one should trust the entity which
     * it represents.
     *
     * @param key the public key used for verification.
     * @param sigProvider the name of the provider.
     *
     * @exception NoSuchAlgorithmException on unsupported signature
     * algorithms.
     * @exception InvalidKeyException on incorrect key.
     * @exception NoSuchProviderException on incorrect provider.
     * @exception SignatureException on signature errors.
     * @exception CertificateException on encoding errors.
     */
    public synchronized void verify(PublicKey key, String sigProvider)
            throws CertificateException, NoSuchAlgorithmException,
            InvalidKeyException, NoSuchProviderException, SignatureException {
        if (sigProvider == null) {
            sigProvider = "";
        }
        if ((verifiedPublicKey != null) && verifiedPublicKey.equals(key)) {
            // this certificate has already been verified using
            // this public key. Make sure providers match, too.
            if (sigProvider.equals(verifiedProvider)) {
                if (verificationResult) {
                    return;
                } else {
                    throw new SignatureException("Signature does not match.");
                }
            }
        }
        if (signedCert == null) {
            throw new CertificateEncodingException("Uninitialized certificate");
        }
        // Verify the signature ...
        Signature sigVerf = null;
        if (sigProvider.length() == 0) {
            sigVerf = Signature.getInstance(algId.getName());
        } else {
            sigVerf = Signature.getInstance(algId.getName(), sigProvider);
        }
        sigVerf.initVerify(key);

        byte[] rawCert = info.getEncodedInfo();
        sigVerf.update(rawCert, 0, rawCert.length);

        // verify may throw SignatureException for invalid encodings, etc.
        verificationResult = sigVerf.verify(signature);
        verifiedPublicKey = key;
        verifiedProvider = sigProvider;

        if (verificationResult == false) {
            throw new SignatureException("Signature does not match.");
        }
    }

    /**
     * Creates an X.509 certificate, and signs it using the given key
     * (associating a signature algorithm and an X.500 name).
     * This operation is used to implement the certificate generation
     * functionality of a certificate authority.
     *
     * @param key the private key used for signing.
     * @param algorithm the name of the signature algorithm used.
     *
     * @exception InvalidKeyException on incorrect key.
     * @exception NoSuchAlgorithmException on unsupported signature
     * algorithms.
     * @exception NoSuchProviderException if there's no default provider.
     * @exception SignatureException on signature errors.
     * @exception CertificateException on encoding errors.
     */
    public void sign(PrivateKey key, String algorithm)
    throws CertificateException, NoSuchAlgorithmException,
        InvalidKeyException, NoSuchProviderException, SignatureException {
        sign(key, algorithm, null);
    }

    /**
     * Creates an X.509 certificate, and signs it using the given key
     * (associating a signature algorithm and an X.500 name).
     * This operation is used to implement the certificate generation
     * functionality of a certificate authority.
     *
     * @param key the private key used for signing.
     * @param algorithm the name of the signature algorithm used.
     * @param provider the name of the provider.
     *
     * @exception NoSuchAlgorithmException on unsupported signature
     * algorithms.
     * @exception InvalidKeyException on incorrect key.
     * @exception NoSuchProviderException on incorrect provider.
     * @exception SignatureException on signature errors.
     * @exception CertificateException on encoding errors.
     */
    public void sign(PrivateKey key, String algorithm, String provider)
    throws CertificateException, NoSuchAlgorithmException,
        InvalidKeyException, NoSuchProviderException, SignatureException {
        try {
            if (readOnly)
                throw new CertificateEncodingException(
                              "cannot over-write existing certificate");
            Signature sigEngine = null;
            if ((provider == null) || (provider.length() == 0))
                sigEngine = Signature.getInstance(algorithm);
            else
                sigEngine = Signature.getInstance(algorithm, provider);

            sigEngine.initSign(key);

                                // in case the name is reset
            algId = sune.security.x509.AlgorithmId.get(sigEngine.getAlgorithm());

            DerOutputStream out = new DerOutputStream();
            DerOutputStream tmp = new DerOutputStream();

            // encode certificate info
            info.encode(tmp);
            byte[] rawCert = tmp.toByteArray();

            // encode algorithm identifier
            algId.encode(tmp);

            // Create and encode the signature itself.
            sigEngine.update(rawCert, 0, rawCert.length);
            signature = sigEngine.sign();
            tmp.putBitString(signature);

            // Wrap the signed data in a SEQUENCE { data, algorithm, sig }
            out.write(DerValue.tag_Sequence, tmp);
            signedCert = out.toByteArray();
            readOnly = true;

        } catch (IOException e) {
            throw new CertificateEncodingException(e.toString(), e);
      }
    }

    /**
     * Checks that the certificate is currently valid, i.e. the current
     * time is within the specified validity period.
     *
     * @exception CertificateExpiredException if the certificate has expired.
     * @exception CertificateNotYetValidException if the certificate is not
     * yet valid.
     */
    public void checkValidity()
    throws CertificateExpiredException, CertificateNotYetValidException {
        Date date = new Date();
        checkValidity(date);
    }

    /**
     * Checks that the specified date is within the certificate's
     * validity period, or basically if the certificate would be
     * valid at the specified date/time.
     *
     * @param date the Date to check against to see if this certificate
     *        is valid at that date/time.
     *
     * @exception CertificateExpiredException if the certificate has expired
     * with respect to the <code>date</code> supplied.
     * @exception CertificateNotYetValidException if the certificate is not
     * yet valid with respect to the <code>date</code> supplied.
     */
    public void checkValidity(Date date)
    throws CertificateExpiredException, CertificateNotYetValidException {

        sune.security.x509.CertificateValidity interval = null;
        try {
            interval = (sune.security.x509.CertificateValidity)info.get(sune.security.x509.CertificateValidity.NAME);
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            throw new CertificateNotYetValidException("Incorrect validity period");
        }
        if (interval == null)
            throw new CertificateNotYetValidException("Null validity period");
        interval.valid(date);
    }

    /**
     * Return the requested attribute from the certificate.
     *
     * Note that the X509CertInfo is not cloned for performance reasons.
     * Callers must ensure that they do not modify it. All other
     * attributes are cloned.
     *
     * @param name the name of the attribute.
     * @exception CertificateParsingException on invalid attribute identifier.
     */
    public Object get(String name)
    throws CertificateParsingException {
        sune.security.x509.X509AttributeName attr = new sune.security.x509.X509AttributeName(name);
        String id = attr.getPrefix();
        if (!(id.equalsIgnoreCase(NAME))) {
            throw new CertificateParsingException("Invalid root of "
                          + "attribute name, expected [" + NAME +
                          "], received " + "[" + id + "]");
        }
        attr = new sune.security.x509.X509AttributeName(attr.getSuffix());
        id = attr.getPrefix();

        if (id.equalsIgnoreCase(INFO)) {
            if (info == null) {
                return null;
            }
            if (attr.getSuffix() != null) {
                try {
                    return info.get(attr.getSuffix());
                } catch (IOException e) {
                    throw new CertificateParsingException(e.toString(), e);
                } catch (CertificateException e) {
                    throw new CertificateParsingException(e.toString(), e);
                }
            } else {
                return info;
            }
        } else if (id.equalsIgnoreCase(ALG_ID)) {
            return(algId);
        } else if (id.equalsIgnoreCase(SIGNATURE)) {
            if (signature != null)
                return signature.clone();
            else
                return null;
        } else if (id.equalsIgnoreCase(SIGNED_CERT)) {
            if (signedCert != null)
                return signedCert.clone();
            else
                return null;
        } else {
            throw new CertificateParsingException("Attribute name not "
                 + "recognized or get() not allowed for the same: " + id);
        }
    }

    /**
     * Set the requested attribute in the certificate.
     *
     * @param name the name of the attribute.
     * @param obj the value of the attribute.
     * @exception CertificateException on invalid attribute identifier.
     * @exception IOException on encoding error of attribute.
     */
    public void set(String name, Object obj)
    throws CertificateException, IOException {
        // check if immutable
        if (readOnly)
            throw new CertificateException("cannot over-write existing"
                                           + " certificate");

        sune.security.x509.X509AttributeName attr = new sune.security.x509.X509AttributeName(name);
        String id = attr.getPrefix();
        if (!(id.equalsIgnoreCase(NAME))) {
            throw new CertificateException("Invalid root of attribute name,"
                           + " expected [" + NAME + "], received " + id);
        }
        attr = new sune.security.x509.X509AttributeName(attr.getSuffix());
        id = attr.getPrefix();

        if (id.equalsIgnoreCase(INFO)) {
            if (attr.getSuffix() == null) {
                if (!(obj instanceof sune.security.x509.X509CertInfo)) {
                    throw new CertificateException("Attribute value should"
                                    + " be of type X509CertInfo.");
                }
                info = (sune.security.x509.X509CertInfo)obj;
                signedCert = null;  //reset this as certificate data has changed
            } else {
                info.set(attr.getSuffix(), obj);
                signedCert = null;  //reset this as certificate data has changed
            }
        } else {
            throw new CertificateException("Attribute name not recognized or " +
                              "set() not allowed for the same: " + id);
        }
    }

    /**
     * Delete the requested attribute from the certificate.
     *
     * @param name the name of the attribute.
     * @exception CertificateException on invalid attribute identifier.
     * @exception IOException on other errors.
     */
    public void delete(String name)
    throws CertificateException, IOException {
        // check if immutable
        if (readOnly)
            throw new CertificateException("cannot over-write existing"
                                           + " certificate");

        sune.security.x509.X509AttributeName attr = new sune.security.x509.X509AttributeName(name);
        String id = attr.getPrefix();
        if (!(id.equalsIgnoreCase(NAME))) {
            throw new CertificateException("Invalid root of attribute name,"
                                   + " expected ["
                                   + NAME + "], received " + id);
        }
        attr = new X509AttributeName(attr.getSuffix());
        id = attr.getPrefix();

        if (id.equalsIgnoreCase(INFO)) {
            if (attr.getSuffix() != null) {
                info = null;
            } else {
                info.delete(attr.getSuffix());
            }
        } else if (id.equalsIgnoreCase(ALG_ID)) {
            algId = null;
        } else if (id.equalsIgnoreCase(SIGNATURE)) {
            signature = null;
        } else if (id.equalsIgnoreCase(SIGNED_CERT)) {
            signedCert = null;
        } else {
            throw new CertificateException("Attribute name not recognized or " +
                              "delete() not allowed for the same: " + id);
        }
    }

    /**
     * Return an enumeration of names of attributes existing within this
     * attribute.
     */
    public Enumeration<String> getElements() {
        sune.security.x509.AttributeNameEnumeration elements = new AttributeNameEnumeration();
        elements.addElement(NAME + DOT + INFO);
        elements.addElement(NAME + DOT + ALG_ID);
        elements.addElement(NAME + DOT + SIGNATURE);
        elements.addElement(NAME + DOT + SIGNED_CERT);

        return elements.elements();
    }

    /**
     * Return the name of this attribute.
     */
    public String getName() {
        return(NAME);
    }

    /**
     * Returns a printable representation of the certificate.  This does not
     * contain all the information available to distinguish this from any
     * other certificate.  The certificate must be fully constructed
     * before this function may be called.
     */
    public String toString() {
        if (info == null || algId == null || signature == null)
            return "";

        StringBuilder sb = new StringBuilder();

        sb.append("[\n");
        sb.append(info.toString() + "\n");
        sb.append("  Algorithm: [" + algId.toString() + "]\n");

        HexDumpEncoder encoder = new HexDumpEncoder();
        sb.append("  Signature:\n" + encoder.encodeBuffer(signature));
        sb.append("\n]");

        return sb.toString();
    }

    // the strongly typed gets, as per java.security.cert.X509Certificate

    /**
     * Gets the publickey from this certificate.
     *
     * @return the publickey.
     */
    public PublicKey getPublicKey() {
        if (info == null)
            return null;
        try {
            PublicKey key = (PublicKey)info.get(sune.security.x509.CertificateX509Key.NAME
                                + DOT + CertificateX509Key.KEY);
            return key;
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }

    /**
     * Gets the version number from the certificate.
     *
     * @return the version number, i.e. 1, 2 or 3.
     */
    public int getVersion() {
        if (info == null)
            return -1;
        try {
            int vers = ((Integer)info.get(sune.security.x509.CertificateVersion.NAME
                        + DOT + CertificateVersion.VERSION)).intValue();
            return vers+1;
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return -1;
        }
    }

    /**
     * Gets the serial number from the certificate.
     *
     * @return the serial number.
     */
    public BigInteger getSerialNumber() {
        sune.security.x509.SerialNumber ser = getSerialNumberObject();

        return ser != null ? ser.getNumber() : null;
    }

    /**
     * Gets the serial number from the certificate as
     * a SerialNumber object.
     *
     * @return the serial number.
     */
    public sune.security.x509.SerialNumber getSerialNumberObject() {
        if (info == null)
            return null;
        try {
            sune.security.x509.SerialNumber ser = (SerialNumber)info.get(
                              sune.security.x509.CertificateSerialNumber.NAME + DOT +
                              CertificateSerialNumber.NUMBER);
           return ser;
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }


    /**
     * Gets the subject distinguished name from the certificate.
     *
     * @return the subject name.
     */
    public Principal getSubjectDN() {
        if (info == null)
            return null;
        try {
            Principal subject = (Principal)info.get(
                                 sune.security.x509.CertificateSubjectName.NAME + DOT +
                                 sune.security.x509.CertificateSubjectName.DN_NAME);
            return subject;
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }

    /**
     * Get subject name as X500Principal. Overrides implementation in
     * X509Certificate with a slightly more efficient version that is
     * also aware of X509CertImpl mutability.
     */
    public X500Principal getSubjectX500Principal() {
        if (info == null) {
            return null;
        }
        try {
            X500Principal subject = (X500Principal)info.get(
                                sune.security.x509.CertificateSubjectName.NAME + DOT +
                                CertificateSubjectName.DN_PRINCIPAL);
            return subject;
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }

    /**
     * Gets the issuer distinguished name from the certificate.
     *
     * @return the issuer name.
     */
    public Principal getIssuerDN() {
        if (info == null)
            return null;
        try {
            Principal issuer = (Principal)info.get(
                                sune.security.x509.CertificateIssuerName.NAME + DOT +
                                sune.security.x509.CertificateIssuerName.DN_NAME);
            return issuer;
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }

    /**
     * Get issuer name as X500Principal. Overrides implementation in
     * X509Certificate with a slightly more efficient version that is
     * also aware of X509CertImpl mutability.
     */
    public X500Principal getIssuerX500Principal() {
        if (info == null) {
            return null;
        }
        try {
            X500Principal issuer = (X500Principal)info.get(
                                sune.security.x509.CertificateIssuerName.NAME + DOT +
                                CertificateIssuerName.DN_PRINCIPAL);
            return issuer;
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }

    /**
     * Gets the notBefore date from the validity period of the certificate.
     *
     * @return the start date of the validity period.
     */
    public Date getNotBefore() {
        if (info == null)
            return null;
        try {
            Date d = (Date) info.get(sune.security.x509.CertificateValidity.NAME + DOT +
                                        sune.security.x509.CertificateValidity.NOT_BEFORE);
            return d;
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }

    /**
     * Gets the notAfter date from the validity period of the certificate.
     *
     * @return the end date of the validity period.
     */
    public Date getNotAfter() {
        if (info == null)
            return null;
        try {
            Date d = (Date) info.get(sune.security.x509.CertificateValidity.NAME + DOT +
                                     CertificateValidity.NOT_AFTER);
            return d;
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }

    /**
     * Gets the DER encoded certificate informations, the
     * <code>tbsCertificate</code> from this certificate.
     * This can be used to verify the signature independently.
     *
     * @return the DER encoded certificate information.
     * @exception CertificateEncodingException if an encoding error occurs.
     */
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        if (info != null) {
            return info.getEncodedInfo();
        } else
            throw new CertificateEncodingException("Uninitialized certificate");
    }

    /**
     * Gets the raw Signature bits from the certificate.
     *
     * @return the signature.
     */
    public byte[] getSignature() {
        if (signature == null)
            return null;
        byte[] dup = new byte[signature.length];
        System.arraycopy(signature, 0, dup, 0, dup.length);
        return dup;
    }

    /**
     * Gets the signature algorithm name for the certificate
     * signature algorithm.
     * For example, the string "SHA-1/DSA" or "DSS".
     *
     * @return the signature algorithm name.
     */
    public String getSigAlgName() {
        if (algId == null)
            return null;
        return (algId.getName());
    }

    /**
     * Gets the signature algorithm OID string from the certificate.
     * For example, the string "1.2.840.10040.4.3"
     *
     * @return the signature algorithm oid string.
     */
    public String getSigAlgOID() {
        if (algId == null)
            return null;
        ObjectIdentifier oid = algId.getOID();
        return (oid.toString());
    }

    /**
     * Gets the DER encoded signature algorithm parameters from this
     * certificate's signature algorithm.
     *
     * @return the DER encoded signature algorithm parameters, or
     *         null if no parameters are present.
     */
    public byte[] getSigAlgParams() {
        if (algId == null)
            return null;
        try {
            return algId.getEncodedParams();
        } catch (IOException e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }

    /**
     * Gets the Issuer Unique Identity from the certificate.
     *
     * @return the Issuer Unique Identity.
     */
    public boolean[] getIssuerUniqueID() {
        if (info == null)
            return null;
        try {
            sune.security.x509.UniqueIdentity id = (sune.security.x509.UniqueIdentity)info.get(
                                 CertificateIssuerUniqueIdentity.NAME
                            + DOT + CertificateIssuerUniqueIdentity.ID);
            if (id == null)
                return null;
            else
                return (id.getId());
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }

    /**
     * Gets the Subject Unique Identity from the certificate.
     *
     * @return the Subject Unique Identity.
     */
    public boolean[] getSubjectUniqueID() {
        if (info == null)
            return null;
        try {
            sune.security.x509.UniqueIdentity id = (UniqueIdentity)info.get(
                                 CertificateSubjectUniqueIdentity.NAME
                            + DOT + CertificateSubjectUniqueIdentity.ID);
            if (id == null)
                return null;
            else
                return (id.getId());
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }

    /**
     * Get AuthorityKeyIdentifier extension
     * @return AuthorityKeyIdentifier object or null (if no such object
     * in certificate)
     */
    public sune.security.x509.AuthorityKeyIdentifierExtension getAuthorityKeyIdentifierExtension()
    {
        return (AuthorityKeyIdentifierExtension)
            getExtension(sune.security.x509.PKIXExtensions.AuthorityKey_Id);
    }

    /**
     * Get BasicConstraints extension
     * @return BasicConstraints object or null (if no such object in
     * certificate)
     */
    public sune.security.x509.BasicConstraintsExtension getBasicConstraintsExtension() {
        return (sune.security.x509.BasicConstraintsExtension)
            getExtension(sune.security.x509.PKIXExtensions.BasicConstraints_Id);
    }

    /**
     * Get CertificatePoliciesExtension
     * @return CertificatePoliciesExtension or null (if no such object in
     * certificate)
     */
    public sune.security.x509.CertificatePoliciesExtension getCertificatePoliciesExtension() {
        return (CertificatePoliciesExtension)
            getExtension(sune.security.x509.PKIXExtensions.CertificatePolicies_Id);
    }

    /**
     * Get ExtendedKeyUsage extension
     * @return ExtendedKeyUsage extension object or null (if no such object
     * in certificate)
     */
    public sune.security.x509.ExtendedKeyUsageExtension getExtendedKeyUsageExtension() {
        return (sune.security.x509.ExtendedKeyUsageExtension)
            getExtension(sune.security.x509.PKIXExtensions.ExtendedKeyUsage_Id);
    }

    /**
     * Get IssuerAlternativeName extension
     * @return IssuerAlternativeName object or null (if no such object in
     * certificate)
     */
    public sune.security.x509.IssuerAlternativeNameExtension getIssuerAlternativeNameExtension() {
        return (sune.security.x509.IssuerAlternativeNameExtension)
            getExtension(sune.security.x509.PKIXExtensions.IssuerAlternativeName_Id);
    }

    /**
     * Get NameConstraints extension
     * @return NameConstraints object or null (if no such object in certificate)
     */
    public sune.security.x509.NameConstraintsExtension getNameConstraintsExtension() {
        return (NameConstraintsExtension)
            getExtension(sune.security.x509.PKIXExtensions.NameConstraints_Id);
    }

    /**
     * Get PolicyConstraints extension
     * @return PolicyConstraints object or null (if no such object in
     * certificate)
     */
    public sune.security.x509.PolicyConstraintsExtension getPolicyConstraintsExtension() {
        return (PolicyConstraintsExtension)
            getExtension(sune.security.x509.PKIXExtensions.PolicyConstraints_Id);
    }

    /**
     * Get PolicyMappingsExtension extension
     * @return PolicyMappingsExtension object or null (if no such object
     * in certificate)
     */
    public sune.security.x509.PolicyMappingsExtension getPolicyMappingsExtension() {
        return (PolicyMappingsExtension)
            getExtension(sune.security.x509.PKIXExtensions.PolicyMappings_Id);
    }

    /**
     * Get PrivateKeyUsage extension
     * @return PrivateKeyUsage object or null (if no such object in certificate)
     */
    public sune.security.x509.PrivateKeyUsageExtension getPrivateKeyUsageExtension() {
        return (PrivateKeyUsageExtension)
            getExtension(sune.security.x509.PKIXExtensions.PrivateKeyUsage_Id);
    }

    /**
     * Get SubjectAlternativeName extension
     * @return SubjectAlternativeName object or null (if no such object in
     * certificate)
     */
    public sune.security.x509.SubjectAlternativeNameExtension getSubjectAlternativeNameExtension()
    {
        return (sune.security.x509.SubjectAlternativeNameExtension)
            getExtension(sune.security.x509.PKIXExtensions.SubjectAlternativeName_Id);
    }

    /**
     * Get SubjectKeyIdentifier extension
     * @return SubjectKeyIdentifier object or null (if no such object in
     * certificate)
     */
    public sune.security.x509.SubjectKeyIdentifierExtension getSubjectKeyIdentifierExtension() {
        return (SubjectKeyIdentifierExtension)
            getExtension(sune.security.x509.PKIXExtensions.SubjectKey_Id);
    }

    /**
     * Get CRLDistributionPoints extension
     * @return CRLDistributionPoints object or null (if no such object in
     * certificate)
     */
    public sune.security.x509.CRLDistributionPointsExtension getCRLDistributionPointsExtension() {
        return (CRLDistributionPointsExtension)
            getExtension(sune.security.x509.PKIXExtensions.CRLDistributionPoints_Id);
    }

    /**
     * Return true if a critical extension is found that is
     * not supported, otherwise return false.
     */
    public boolean hasUnsupportedCriticalExtension() {
        if (info == null)
            return false;
        try {
            sune.security.x509.CertificateExtensions exts = (sune.security.x509.CertificateExtensions)info.get(
                                         sune.security.x509.CertificateExtensions.NAME);
            if (exts == null)
                return false;
            return exts.hasUnsupportedCriticalExtension();
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return false;
        }
    }

    /**
     * Gets a Set of the extension(s) marked CRITICAL in the
     * certificate. In the returned set, each extension is
     * represented by its OID string.
     *
     * @return a set of the extension oid strings in the
     * certificate that are marked critical.
     */
    public Set<String> getCriticalExtensionOIDs() {
        if (info == null) {
            return null;
        }
        try {
            sune.security.x509.CertificateExtensions exts = (sune.security.x509.CertificateExtensions)info.get(
                                         sune.security.x509.CertificateExtensions.NAME);
            if (exts == null) {
                return null;
            }
            Set<String> extSet = new HashSet<String>();
            for (sune.security.x509.Extension ex : exts.getAllExtensions()) {
                if (ex.isCritical()) {
                    extSet.add(ex.getExtensionId().toString());
                }
            }
            return extSet;
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }

    /**
     * Gets a Set of the extension(s) marked NON-CRITICAL in the
     * certificate. In the returned set, each extension is
     * represented by its OID string.
     *
     * @return a set of the extension oid strings in the
     * certificate that are NOT marked critical.
     */
    public Set<String> getNonCriticalExtensionOIDs() {
        if (info == null) {
            return null;
        }
        try {
            sune.security.x509.CertificateExtensions exts = (sune.security.x509.CertificateExtensions)info.get(
                                         sune.security.x509.CertificateExtensions.NAME);
            if (exts == null) {
                return null;
            }
            Set<String> extSet = new HashSet<String>();
            for (sune.security.x509.Extension ex : exts.getAllExtensions()) {
                if (!ex.isCritical()) {
                    extSet.add(ex.getExtensionId().toString());
                }
            }
            extSet.addAll(exts.getUnparseableExtensions().keySet());
            return extSet;
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }

    /**
     * Gets the extension identified by the given ObjectIdentifier
     *
     * @param oid the Object Identifier value for the extension.
     * @return Extension or null if certificate does not contain this
     *         extension
     */
    public sune.security.x509.Extension getExtension(ObjectIdentifier oid) {
        if (info == null) {
            return null;
        }
        try {
            sune.security.x509.CertificateExtensions extensions;
            try {
                extensions = (sune.security.x509.CertificateExtensions)info.get(sune.security.x509.CertificateExtensions.NAME);
            } catch (CertificateException ce) {
                logger.warn("Warning in X509CertImpl", ce);
                return null;
            }
            if (extensions == null) {
                return null;
            } else {
                for (sune.security.x509.Extension ex : extensions.getAllExtensions()) {
                    if (ex.getExtensionId().equals(oid)) {
                        //XXXX May want to consider cloning this
                        return ex;
                    }
                }
                /* no such extension in this certificate */
                return null;
            }
        } catch (IOException ioe) {
            logger.warn("Warning in X509CertImpl", ioe);
            return null;
        }
    }

    public sune.security.x509.Extension getUnparseableExtension(ObjectIdentifier oid) {
        if (info == null) {
            return null;
        }
        try {
            sune.security.x509.CertificateExtensions extensions;
            try {
                extensions = (sune.security.x509.CertificateExtensions)info.get(sune.security.x509.CertificateExtensions.NAME);
            } catch (CertificateException ce) {
                logger.warn("Warning in X509CertImpl", ce);
                return null;
            }
            if (extensions == null) {
                return null;
            } else {
                return extensions.getUnparseableExtensions().get(oid.toString());
            }
        } catch (IOException e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }

    /**
     * Gets the DER encoded extension identified by the given
     * oid String.
     *
     * @param oid the Object Identifier value for the extension.
     */
    public byte[] getExtensionValue(String oid) {
        try {
            ObjectIdentifier findOID = new ObjectIdentifier(oid);
            String extAlias = sune.security.x509.OIDMap.getName(findOID);
            sune.security.x509.Extension certExt = null;
            sune.security.x509.CertificateExtensions exts = (sune.security.x509.CertificateExtensions)info.get(
                                     CertificateExtensions.NAME);

            if (extAlias == null) { // may be unknown
                // get the extensions, search thru' for this oid
                if (exts == null) {
                    return null;
                }

                for (sune.security.x509.Extension ex : exts.getAllExtensions()) {
                    ObjectIdentifier inCertOID = ex.getExtensionId();
                    if (inCertOID.equals(findOID)) {
                        certExt = ex;
                        break;
                    }
                }
            } else { // there's sub-class that can handle this extension
                try {
                    certExt = (Extension)this.get(extAlias);
                } catch (CertificateException e) {
                    logger.warn("Warning in X509CertImpl", e);
                    // get() throws an Exception instead of returning null, ignore
                }
            }
            if (certExt == null) {
                if (exts != null) {
                    certExt = exts.getUnparseableExtensions().get(oid);
                }
                if (certExt == null) {
                    return null;
                }
            }
            byte[] extData = certExt.getExtensionValue();
            if (extData == null) {
                return null;
            }
            DerOutputStream out = new DerOutputStream();
            out.putOctetString(extData);
            return out.toByteArray();
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }

    /**
     * Get a boolean array representing the bits of the KeyUsage extension,
     * (oid = 2.5.29.15).
     * @return the bit values of this extension as an array of booleans.
     */
    public boolean[] getKeyUsage() {
        try {
            String extAlias = sune.security.x509.OIDMap.getName(sune.security.x509.PKIXExtensions.KeyUsage_Id);
            if (extAlias == null)
                return null;

            sune.security.x509.KeyUsageExtension certExt = (KeyUsageExtension)this.get(extAlias);
            if (certExt == null)
                return null;

            boolean[] ret = certExt.getBits();
            if (ret.length < NUM_STANDARD_KEY_USAGE) {
                boolean[] usageBits = new boolean[NUM_STANDARD_KEY_USAGE];
                System.arraycopy(ret, 0, usageBits, 0, ret.length);
                ret = usageBits;
            }
            return ret;
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return null;
        }
    }

    /**
     * This method are the overridden implementation of
     * getExtendedKeyUsage method in X509Certificate in the Sun
     * provider. It is better performance-wise since it returns cached
     * values.
     */
    public synchronized List<String> getExtendedKeyUsage()
        throws CertificateParsingException {
        if (readOnly && extKeyUsage != null) {
            return extKeyUsage;
        } else {
            sune.security.x509.ExtendedKeyUsageExtension ext = getExtendedKeyUsageExtension();
            if (ext == null) {
                return null;
            }
            extKeyUsage =
                Collections.unmodifiableList(ext.getExtendedKeyUsage());
            return extKeyUsage;
        }
    }

    /**
     * This static method is the default implementation of the
     * getExtendedKeyUsage method in X509Certificate. A
     * X509Certificate provider generally should overwrite this to
     * provide among other things caching for better performance.
     */
    public static List<String> getExtendedKeyUsage(X509Certificate cert)
        throws CertificateParsingException {
        try {
            byte[] ext = cert.getExtensionValue(EXTENDED_KEY_USAGE_OID);
            if (ext == null)
                return null;
            DerValue val = new DerValue(ext);
            byte[] data = val.getOctetString();

            sune.security.x509.ExtendedKeyUsageExtension ekuExt =
                new ExtendedKeyUsageExtension(Boolean.FALSE, data);
            return Collections.unmodifiableList(ekuExt.getExtendedKeyUsage());
        } catch (IOException ioe) {
            CertificateParsingException cpe =
                new CertificateParsingException();
            cpe.initCause(ioe);
            throw cpe;
        }
    }

    /**
     * Get the certificate constraints path length from the
     * the critical BasicConstraints extension, (oid = 2.5.29.19).
     * @return the length of the constraint.
     */
    public int getBasicConstraints() {
        try {
            String extAlias = OIDMap.getName(sune.security.x509.PKIXExtensions.BasicConstraints_Id);
            if (extAlias == null)
                return -1;
            sune.security.x509.BasicConstraintsExtension certExt =
                        (sune.security.x509.BasicConstraintsExtension)this.get(extAlias);
            if (certExt == null)
                return -1;

            if (((Boolean)certExt.get(sune.security.x509.BasicConstraintsExtension.IS_CA)
                 ).booleanValue() == true)
                return ((Integer)certExt.get(
                        BasicConstraintsExtension.PATH_LEN)).intValue();
            else
                return -1;
        } catch (Exception e) {
            logger.warn("Warning in X509CertImpl", e);
            return -1;
        }
    }

    /**
     * Converts a GeneralNames structure into an immutable Collection of
     * alternative names (subject or issuer) in the form required by
     * {@link #getSubjectAlternativeNames} or
     * {@link #getIssuerAlternativeNames}.
     *
     * @param names the GeneralNames to be converted
     * @return an immutable Collection of alternative names
     */
    private static Collection<List<?>> makeAltNames(sune.security.x509.GeneralNames names) {
        if (names.isEmpty()) {
            return Collections.<List<?>>emptySet();
        }
        Set<List<?>> newNames = new HashSet<List<?>>();
        for (GeneralName gname : names.names()) {
            sune.security.x509.GeneralNameInterface name = gname.getName();
            List<Object> nameEntry = new ArrayList<Object>(2);
            nameEntry.add(Integer.valueOf(name.getType()));
            switch (name.getType()) {
            case sune.security.x509.GeneralNameInterface.NAME_RFC822:
                nameEntry.add(((RFC822Name) name).getName());
                break;
            case sune.security.x509.GeneralNameInterface.NAME_DNS:
                nameEntry.add(((DNSName) name).getName());
                break;
            case sune.security.x509.GeneralNameInterface.NAME_DIRECTORY:
                nameEntry.add(((X500Name) name).getRFC2253Name());
                break;
            case sune.security.x509.GeneralNameInterface.NAME_URI:
                nameEntry.add(((URIName) name).getName());
                break;
            case sune.security.x509.GeneralNameInterface.NAME_IP:
                try {
                    nameEntry.add(((IPAddressName) name).getName());
                } catch (Exception e) {
                    // IPAddressName in cert is bogus
                    throw new RuntimeException("IPAddress cannot be parsed", e);
                }
                break;
            case GeneralNameInterface.NAME_OID:
                nameEntry.add(((OIDName) name).getOID().toString());
                break;
            default:
                // add DER encoded form
                DerOutputStream derOut = new DerOutputStream();
                try {
                    name.encode(derOut);
                } catch (Exception ioe) {
                    // should not occur since name has already been decoded
                    // from cert (this would indicate a bug in our code)
                    throw new RuntimeException("name cannot be encoded", ioe);
                }
                nameEntry.add(derOut.toByteArray());
                break;
            }
            newNames.add(Collections.unmodifiableList(nameEntry));
        }
        return Collections.unmodifiableCollection(newNames);
    }

    /**
     * Checks a Collection of altNames and clones any name entries of type
     * byte [].
     */ // only partially generified due to javac bug
    private static Collection<List<?>> cloneAltNames(Collection<List<?>> altNames) {
        boolean mustClone = false;
        for (List<?> nameEntry : altNames) {
            if (nameEntry.get(1) instanceof byte[]) {
                // must clone names
                mustClone = true;
            }
        }
        if (mustClone) {
            Set<List<?>> namesCopy = new HashSet<List<?>>();
            for (List<?> nameEntry : altNames) {
                Object nameObject = nameEntry.get(1);
                if (nameObject instanceof byte[]) {
                    List<Object> nameEntryCopy =
                                        new ArrayList<Object>(nameEntry);
                    nameEntryCopy.set(1, ((byte[])nameObject).clone());
                    namesCopy.add(Collections.unmodifiableList(nameEntryCopy));
                } else {
                    namesCopy.add(nameEntry);
                }
            }
            return Collections.unmodifiableCollection(namesCopy);
        } else {
            return altNames;
        }
    }

    /**
     * This method are the overridden implementation of
     * getSubjectAlternativeNames method in X509Certificate in the Sun
     * provider. It is better performance-wise since it returns cached
     * values.
     */
    public synchronized Collection<List<?>> getSubjectAlternativeNames()
        throws CertificateParsingException {
        // return cached value if we can
        if (readOnly && subjectAlternativeNames != null)  {
            return cloneAltNames(subjectAlternativeNames);
        }
        sune.security.x509.SubjectAlternativeNameExtension subjectAltNameExt =
            getSubjectAlternativeNameExtension();
        if (subjectAltNameExt == null) {
            return null;
        }
        sune.security.x509.GeneralNames names;
        try {
            names = (sune.security.x509.GeneralNames) subjectAltNameExt.get
                (sune.security.x509.SubjectAlternativeNameExtension.SUBJECT_NAME);
        } catch (IOException ioe) {
            logger.warn("Warning in X509CertImpl", ioe);
            // should not occur
            return Collections.<List<?>>emptySet();
        }
        subjectAlternativeNames = makeAltNames(names);
        return subjectAlternativeNames;
    }

    /**
     * This static method is the default implementation of the
     * getSubjectAlternaitveNames method in X509Certificate. A
     * X509Certificate provider generally should overwrite this to
     * provide among other things caching for better performance.
     */
    public static Collection<List<?>> getSubjectAlternativeNames(X509Certificate cert)
        throws CertificateParsingException {
        try {
            byte[] ext = cert.getExtensionValue(SUBJECT_ALT_NAME_OID);
            if (ext == null) {
                return null;
            }
            DerValue val = new DerValue(ext);
            byte[] data = val.getOctetString();

            sune.security.x509.SubjectAlternativeNameExtension subjectAltNameExt =
                new sune.security.x509.SubjectAlternativeNameExtension(Boolean.FALSE,
                                                    data);

            sune.security.x509.GeneralNames names;
            try {
                names = (sune.security.x509.GeneralNames) subjectAltNameExt.get
                    (SubjectAlternativeNameExtension.SUBJECT_NAME);
            }  catch (IOException ioe) {
                logger.warn("Warning in X509CertImpl", ioe);
                // should not occur
                return Collections.<List<?>>emptySet();
            }
            return makeAltNames(names);
        } catch (IOException ioe) {
            CertificateParsingException cpe =
                new CertificateParsingException();
            cpe.initCause(ioe);
            throw cpe;
        }
    }

    /**
     * This method are the overridden implementation of
     * getIssuerAlternativeNames method in X509Certificate in the Sun
     * provider. It is better performance-wise since it returns cached
     * values.
     */
    public synchronized Collection<List<?>> getIssuerAlternativeNames()
        throws CertificateParsingException {
        // return cached value if we can
        if (readOnly && issuerAlternativeNames != null) {
            return cloneAltNames(issuerAlternativeNames);
        }
        sune.security.x509.IssuerAlternativeNameExtension issuerAltNameExt =
            getIssuerAlternativeNameExtension();
        if (issuerAltNameExt == null) {
            return null;
        }
        sune.security.x509.GeneralNames names;
        try {
            names = (sune.security.x509.GeneralNames) issuerAltNameExt.get
                (sune.security.x509.IssuerAlternativeNameExtension.ISSUER_NAME);
        } catch (IOException ioe) {
            logger.warn("Warning in X509CertImpl", ioe);
            // should not occur
            return Collections.<List<?>>emptySet();
        }
        issuerAlternativeNames = makeAltNames(names);
        return issuerAlternativeNames;
    }

    /**
     * This static method is the default implementation of the
     * getIssuerAlternaitveNames method in X509Certificate. A
     * X509Certificate provider generally should overwrite this to
     * provide among other things caching for better performance.
     */
    public static Collection<List<?>> getIssuerAlternativeNames(X509Certificate cert)
        throws CertificateParsingException {
        try {
            byte[] ext = cert.getExtensionValue(ISSUER_ALT_NAME_OID);
            if (ext == null) {
                return null;
            }

            DerValue val = new DerValue(ext);
            byte[] data = val.getOctetString();

            sune.security.x509.IssuerAlternativeNameExtension issuerAltNameExt =
                new sune.security.x509.IssuerAlternativeNameExtension(Boolean.FALSE,
                                                    data);
            sune.security.x509.GeneralNames names;
            try {
                names = (GeneralNames) issuerAltNameExt.get
                    (IssuerAlternativeNameExtension.ISSUER_NAME);
            }  catch (IOException ioe) {
                logger.warn("Warning in X509CertImpl", ioe);
                // should not occur
                return Collections.<List<?>>emptySet();
            }
            return makeAltNames(names);
        } catch (IOException ioe) {
            CertificateParsingException cpe =
                new CertificateParsingException();
            cpe.initCause(ioe);
            throw cpe;
        }
    }

    public sune.security.x509.AuthorityInfoAccessExtension getAuthorityInfoAccessExtension() {
        return (AuthorityInfoAccessExtension)
            getExtension(PKIXExtensions.AuthInfoAccess_Id);
    }

    /************************************************************/

    /*
     * Cert is a SIGNED ASN.1 macro, a three elment sequence:
     *
     *  - Data to be signed (ToBeSigned) -- the "raw" cert
     *  - Signature algorithm (SigAlgId)
     *  - The signature bits
     *
     * This routine unmarshals the certificate, saving the signature
     * parts away for later verification.
     */
    private void parse(DerValue val)
    throws CertificateException, IOException {
        // check if can over write the certificate
        if (readOnly)
            throw new CertificateParsingException(
                      "cannot over-write existing certificate");

        if (val.data == null || val.tag != DerValue.tag_Sequence)
            throw new CertificateParsingException(
                      "invalid DER-encoded certificate data");

        signedCert = val.toByteArray();
        DerValue[] seq = new DerValue[3];

        seq[0] = val.data.getDerValue();
        seq[1] = val.data.getDerValue();
        seq[2] = val.data.getDerValue();

        if (val.data.available() != 0) {
            throw new CertificateParsingException("signed overrun, bytes = "
                                     + val.data.available());
        }
        if (seq[0].tag != DerValue.tag_Sequence) {
            throw new CertificateParsingException("signed fields invalid");
        }

        algId = sune.security.x509.AlgorithmId.parse(seq[1]);
        signature = seq[2].getBitString();

        if (seq[1].data.available() != 0) {
            throw new CertificateParsingException("algid field overrun");
        }
        if (seq[2].data.available() != 0)
            throw new CertificateParsingException("signed fields overrun");

        // The CertificateInfo
        info = new X509CertInfo(seq[0]);

        // the "inner" and "outer" signature algorithms must match
        sune.security.x509.AlgorithmId infoSigAlg = (AlgorithmId)info.get(
                                              sune.security.x509.CertificateAlgorithmId.NAME
                                              + DOT +
                                              CertificateAlgorithmId.ALGORITHM);
        if (! algId.equals(infoSigAlg))
            throw new CertificateException("Signature algorithm mismatch");
        readOnly = true;
    }

    /**
     * Extract the subject or issuer X500Principal from an X509Certificate.
     * Parses the encoded form of the cert to preserve the principal's
     * ASN.1 encoding.
     */
    private static X500Principal getX500Principal(X509Certificate cert,
            boolean getIssuer) throws Exception {
        byte[] encoded = cert.getEncoded();
        DerInputStream derIn = new DerInputStream(encoded);
        DerValue tbsCert = derIn.getSequence(3)[0];
        DerInputStream tbsIn = tbsCert.data;
        DerValue tmp;
        tmp = tbsIn.getDerValue();
        // skip version number if present
        if (tmp.isContextSpecific((byte)0)) {
          tmp = tbsIn.getDerValue();
        }
        // tmp always contains serial number now
        tmp = tbsIn.getDerValue();              // skip signature
        tmp = tbsIn.getDerValue();              // issuer
        if (getIssuer == false) {
            tmp = tbsIn.getDerValue();          // skip validity
            tmp = tbsIn.getDerValue();          // subject
        }
        byte[] principalBytes = tmp.toByteArray();
        return new X500Principal(principalBytes);
    }

    /**
     * Extract the subject X500Principal from an X509Certificate.
     * Called from java.security.cert.X509Certificate.getSubjectX500Principal().
     */
    public static X500Principal getSubjectX500Principal(X509Certificate cert) {
        try {
            return getX500Principal(cert, false);
        } catch (Exception e) {
            throw new RuntimeException("Could not parse subject", e);
        }
    }

    /**
     * Extract the issuer X500Principal from an X509Certificate.
     * Called from java.security.cert.X509Certificate.getIssuerX500Principal().
     */
    public static X500Principal getIssuerX500Principal(X509Certificate cert) {
        try {
            return getX500Principal(cert, true);
        } catch (Exception e) {
            throw new RuntimeException("Could not parse issuer", e);
        }
    }

    /**
     * Returned the encoding of the given certificate for internal use.
     * Callers must guarantee that they neither modify it nor expose it
     * to untrusted code. Uses getEncodedInternal() if the certificate
     * is instance of X509CertImpl, getEncoded() otherwise.
     */
    public static byte[] getEncodedInternal(Certificate cert)
            throws CertificateEncodingException {
        if (cert instanceof X509CertImpl) {
            return ((X509CertImpl)cert).getEncodedInternal();
        } else {
            return cert.getEncoded();
        }
    }

    /**
     * Utility method to convert an arbitrary instance of X509Certificate
     * to a X509CertImpl. Does a cast if possible, otherwise reparses
     * the encoding.
     */
    public static X509CertImpl toImpl(X509Certificate cert)
            throws CertificateException {
        if (cert instanceof X509CertImpl) {
            return (X509CertImpl)cert;
        } else {
            return X509Factory.intern(cert);
        }
    }

    /**
     * Utility method to test if a certificate is self-issued. This is
     * the case iff the subject and issuer X500Principals are equal.
     */
    public static boolean isSelfIssued(X509Certificate cert) {
        X500Principal subject = cert.getSubjectX500Principal();
        X500Principal issuer = cert.getIssuerX500Principal();
        return subject.equals(issuer);
    }

    /**
     * Utility method to test if a certificate is self-signed. This is
     * the case iff the subject and issuer X500Principals are equal
     * AND the certificate's subject public key can be used to verify
     * the certificate. In case of exception, returns false.
     */
    public static boolean isSelfSigned(X509Certificate cert,
        String sigProvider) {
        if (isSelfIssued(cert)) {
            try {
                if (sigProvider == null) {
                    cert.verify(cert.getPublicKey());
                } else {
                    cert.verify(cert.getPublicKey(), sigProvider);
                }
                return true;
            } catch (Exception e) {
                logger.warn("Warning in X509CertImpl", e);
                // In case of exception, return false
            }
        }
        return false;
    }
}
