/*
 * Copyright 1997-2007 Sun Microsystems, Inc.  All Rights Reserved.
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
import sune.security.provider.X509Factory;
import sune.security.util.DerInputStream;
import sune.security.util.DerOutputStream;
import sune.security.util.DerValue;
import sune.security.util.ObjectIdentifier;

import javax.security.auth.x500.X500Principal;
import java.io.IOException;
import java.io.InputStream;
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
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * An implmentation for X509 CRL (Certificate Revocation List).
 * <p>
 * The X.509 v2 CRL format is described below in ASN.1:
 * <pre>
 * CertificateList  ::=  SEQUENCE  {
 *     tbsCertList          TBSCertList,
 *     signatureAlgorithm   AlgorithmIdentifier,
 *     signature            BIT STRING  }
 * </pre>
 * More information can be found in
 * <a href="http://www.ietf.org/rfc/rfc3280.txt">RFC 3280: Internet X.509
 * Public Key Infrastructure Certificate and CRL Profile</a>.
 * <p>
 * The ASN.1 definition of <code>tbsCertList</code> is:
 * <pre>
 * TBSCertList  ::=  SEQUENCE  {
 *     version                 Version OPTIONAL,
 *                             -- if present, must be v2
 *     signature               AlgorithmIdentifier,
 *     issuer                  Name,
 *     thisUpdate              ChoiceOfTime,
 *     nextUpdate              ChoiceOfTime OPTIONAL,
 *     revokedCertificates     SEQUENCE OF SEQUENCE  {
 *         userCertificate         CertificateSerialNumber,
 *         revocationDate          ChoiceOfTime,
 *         crlEntryExtensions      Extensions OPTIONAL
 *                                 -- if present, must be v2
 *         }  OPTIONAL,
 *     crlExtensions           [0]  EXPLICIT Extensions OPTIONAL
 *                                  -- if present, must be v2
 *     }
 * </pre>
 *
 * @author Hemma Prafullchandra
 * @see X509CRL
 */
public class X509CRLImpl extends X509CRL {

    protected static Logger logger = LoggerFactory.getLogger(X509CRLImpl.class);

    // CRL data, and its envelope
    private byte[]      signedCRL = null; // DER encoded crl
    private byte[]      signature = null; // raw signature bits
    private byte[]      tbsCertList = null; // DER encoded "to-be-signed" CRL
    private sune.security.x509.AlgorithmId sigAlgId = null; // sig alg in CRL

    // crl information
    private int              version;
    private sune.security.x509.AlgorithmId infoSigAlgId; // sig alg in "to-be-signed" crl
    private sune.security.x509.X500Name issuer = null;
    private X500Principal    issuerPrincipal = null;
    private Date             thisUpdate = null;
    private Date             nextUpdate = null;
    private Map<X509IssuerSerial,X509CRLEntry> revokedCerts = new LinkedHashMap<X509IssuerSerial,X509CRLEntry>();
    private sune.security.x509.CRLExtensions extensions = null;
    private final static boolean isExplicit = true;
    private static final long YR_2050 = 2524636800000L;

    private boolean readOnly = false;

    /**
     * PublicKey that has previously been used to successfully verify
     * the signature of this CRL. Null if the CRL has not
     * yet been verified (successfully).
     */
    private PublicKey verifiedPublicKey;
    /**
     * If verifiedPublicKey is not null, name of the provider used to
     * successfully verify the signature of this CRL, or the
     * empty String if no provider was explicitly specified.
     */
    private String verifiedProvider;

    /**
     * Not to be used. As it would lead to cases of uninitialized
     * CRL objects.
     */
    private X509CRLImpl() { }

    /**
     * Unmarshals an X.509 CRL from its encoded form, parsing the encoded
     * bytes.  This form of constructor is used by agents which
     * need to examine and use CRL contents. Note that the buffer
     * must include only one CRL, and no "garbage" may be left at
     * the end.
     *
     * @param crlData the encoded bytes, with no trailing padding.
     * @exception CRLException on parsing errors.
     */
    public X509CRLImpl(byte[] crlData) throws CRLException {
        try {
            parse(new DerValue(crlData));
        } catch (IOException e) {
            signedCRL = null;
            throw new CRLException("Parsing error: " + e.getMessage(), e);
        }
    }

    /**
     * Unmarshals an X.509 CRL from an DER value.
     *
     * @param val a DER value holding at least one CRL
     * @exception CRLException on parsing errors.
     */
    public X509CRLImpl(DerValue val) throws CRLException {
        try {
            parse(val);
        } catch (IOException e) {
            signedCRL = null;
            throw new CRLException("Parsing error: " + e.getMessage(), e);
        }
    }

    /**
     * Unmarshals an X.509 CRL from an input stream. Only one CRL
     * is expected at the end of the input stream.
     *
     * @param inStrm an input stream holding at least one CRL
     * @exception CRLException on parsing errors.
     */
    public X509CRLImpl(InputStream inStrm) throws CRLException {
        try {
            parse(new DerValue(inStrm));
        } catch (IOException e) {
            signedCRL = null;
            throw new CRLException("Parsing error: " + e.getMessage(), e);
        }
    }

    /**
     * Initial CRL constructor, no revoked certs, and no extensions.
     *
     * @param issuer the name of the CA issuing this CRL.
     * @param thisDate the Date of this issue.
     * @param nextDate the Date of the next CRL.
     */
    public X509CRLImpl(sune.security.x509.X500Name issuer, Date thisDate, Date nextDate) {
        this.issuer = issuer;
        this.thisUpdate = thisDate;
        this.nextUpdate = nextDate;
    }

    /**
     * CRL constructor, revoked certs, no extensions.
     *
     * @param issuer the name of the CA issuing this CRL.
     * @param thisDate the Date of this issue.
     * @param nextDate the Date of the next CRL.
     * @param badCerts the array of CRL entries.
     *
     * @exception CRLException on parsing/construction errors.
     */
    public X509CRLImpl(sune.security.x509.X500Name issuer, Date thisDate, Date nextDate,
                       X509CRLEntry[] badCerts)
        throws CRLException
    {
        this.issuer = issuer;
        this.thisUpdate = thisDate;
        this.nextUpdate = nextDate;
        if (badCerts != null) {
            X500Principal crlIssuer = getIssuerX500Principal();
            X500Principal badCertIssuer = crlIssuer;
            for (int i = 0; i < badCerts.length; i++) {
                sune.security.x509.X509CRLEntryImpl badCert = (sune.security.x509.X509CRLEntryImpl)badCerts[i];
                try {
                    badCertIssuer = getCertIssuer(badCert, badCertIssuer);
                } catch (IOException ioe) {
                    throw new CRLException(ioe);
                }
                badCert.setCertificateIssuer(crlIssuer, badCertIssuer);
                X509IssuerSerial issuerSerial = new X509IssuerSerial
                    (badCertIssuer, badCert.getSerialNumber());
                this.revokedCerts.put(issuerSerial, badCert);
                if (badCert.hasExtensions()) {
                    this.version = 1;
                }
            }
        }
    }

    /**
     * CRL constructor, revoked certs and extensions.
     *
     * @param issuer the name of the CA issuing this CRL.
     * @param thisDate the Date of this issue.
     * @param nextDate the Date of the next CRL.
     * @param badCerts the array of CRL entries.
     * @param crlExts the CRL extensions.
     *
     * @exception CRLException on parsing/construction errors.
     */
    public X509CRLImpl(sune.security.x509.X500Name issuer, Date thisDate, Date nextDate,
                       X509CRLEntry[] badCerts, sune.security.x509.CRLExtensions crlExts)
        throws CRLException
    {
        this(issuer, thisDate, nextDate, badCerts);
        if (crlExts != null) {
            this.extensions = crlExts;
            this.version = 1;
        }
    }

    /**
     * Returned the encoding as an uncloned byte array. Callers must
     * guarantee that they neither modify it nor expose it to untrusted
     * code.
     */
    public byte[] getEncodedInternal() throws CRLException {
        if (signedCRL == null) {
            throw new CRLException("Null CRL to encode");
        }
        return signedCRL;
    }

    /**
     * Returns the ASN.1 DER encoded form of this CRL.
     *
     * @exception CRLException if an encoding error occurs.
     */
    public byte[] getEncoded() throws CRLException {
        return getEncodedInternal().clone();
    }

    /**
     * Encodes the "to-be-signed" CRL to the OutputStream.
     *
     * @param out the OutputStream to write to.
     * @exception CRLException on encoding errors.
     */
    public void encodeInfo(OutputStream out) throws CRLException {
        try {
            DerOutputStream tmp = new DerOutputStream();
            DerOutputStream rCerts = new DerOutputStream();
            DerOutputStream seq = new DerOutputStream();

            if (version != 0) // v2 crl encode version
                tmp.putInteger(version);
            infoSigAlgId.encode(tmp);
            if ((version == 0) && (issuer.toString() == null))
                throw new CRLException("Null Issuer DN not allowed in v1 CRL");
            issuer.encode(tmp);

            if (thisUpdate.getTime() < YR_2050)
                tmp.putUTCTime(thisUpdate);
            else
                tmp.putGeneralizedTime(thisUpdate);

            if (nextUpdate != null) {
                if (nextUpdate.getTime() < YR_2050)
                    tmp.putUTCTime(nextUpdate);
                else
                    tmp.putGeneralizedTime(nextUpdate);
            }

            if (!revokedCerts.isEmpty()) {
                for (X509CRLEntry entry : revokedCerts.values()) {
                    ((sune.security.x509.X509CRLEntryImpl)entry).encode(rCerts);
                }
                tmp.write(DerValue.tag_Sequence, rCerts);
            }

            if (extensions != null)
                extensions.encode(tmp, isExplicit);

            seq.write(DerValue.tag_Sequence, tmp);

            tbsCertList = seq.toByteArray();
            out.write(tbsCertList);
        } catch (IOException e) {
             throw new CRLException("Encoding error: " + e.getMessage(), e);
        }
    }

    /**
     * Verifies that this CRL was signed using the
     * private key that corresponds to the given public key.
     *
     * @param key the PublicKey used to carry out the verification.
     *
     * @exception NoSuchAlgorithmException on unsupported signature
     * algorithms.
     * @exception InvalidKeyException on incorrect key.
     * @exception NoSuchProviderException if there's no default provider.
     * @exception SignatureException on signature errors.
     * @exception CRLException on encoding errors.
     */
    public void verify(PublicKey key)
    throws CRLException, NoSuchAlgorithmException, InvalidKeyException,
           NoSuchProviderException, SignatureException {
        verify(key, "");
    }

    /**
     * Verifies that this CRL was signed using the
     * private key that corresponds to the given public key,
     * and that the signature verification was computed by
     * the given provider.
     *
     * @param key the PublicKey used to carry out the verification.
     * @param sigProvider the name of the signature provider.
     *
     * @exception NoSuchAlgorithmException on unsupported signature
     * algorithms.
     * @exception InvalidKeyException on incorrect key.
     * @exception NoSuchProviderException on incorrect provider.
     * @exception SignatureException on signature errors.
     * @exception CRLException on encoding errors.
     */
    public synchronized void verify(PublicKey key, String sigProvider)
            throws CRLException, NoSuchAlgorithmException, InvalidKeyException,
            NoSuchProviderException, SignatureException {

        if (sigProvider == null) {
            sigProvider = "";
        }
        if ((verifiedPublicKey != null) && verifiedPublicKey.equals(key)) {
            // this CRL has already been successfully verified using
            // this public key. Make sure providers match, too.
            if (sigProvider.equals(verifiedProvider)) {
                return;
            }
        }
        if (signedCRL == null) {
            throw new CRLException("Uninitialized CRL");
        }
        Signature   sigVerf = null;
        if (sigProvider.length() == 0) {
            sigVerf = Signature.getInstance(sigAlgId.getName());
        } else {
            sigVerf = Signature.getInstance(sigAlgId.getName(), sigProvider);
        }
        sigVerf.initVerify(key);

        if (tbsCertList == null) {
            throw new CRLException("Uninitialized CRL");
        }

        sigVerf.update(tbsCertList, 0, tbsCertList.length);

        if (!sigVerf.verify(signature)) {
            throw new SignatureException("Signature does not match.");
        }
        verifiedPublicKey = key;
        verifiedProvider = sigProvider;
    }

    /**
     * Encodes an X.509 CRL, and signs it using the given key.
     *
     * @param key the private key used for signing.
     * @param algorithm the name of the signature algorithm used.
     *
     * @exception NoSuchAlgorithmException on unsupported signature
     * algorithms.
     * @exception InvalidKeyException on incorrect key.
     * @exception NoSuchProviderException on incorrect provider.
     * @exception SignatureException on signature errors.
     * @exception CRLException if any mandatory data was omitted.
     */
    public void sign(PrivateKey key, String algorithm)
    throws CRLException, NoSuchAlgorithmException, InvalidKeyException,
        NoSuchProviderException, SignatureException {
        sign(key, algorithm, null);
    }

    /**
     * Encodes an X.509 CRL, and signs it using the given key.
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
     * @exception CRLException if any mandatory data was omitted.
     */
    public void sign(PrivateKey key, String algorithm, String provider)
    throws CRLException, NoSuchAlgorithmException, InvalidKeyException,
        NoSuchProviderException, SignatureException {
        try {
            if (readOnly)
                throw new CRLException("cannot over-write existing CRL");
            Signature sigEngine = null;
            if ((provider == null) || (provider.length() == 0))
                sigEngine = Signature.getInstance(algorithm);
            else
                sigEngine = Signature.getInstance(algorithm, provider);

            sigEngine.initSign(key);

                                // in case the name is reset
            sigAlgId = sune.security.x509.AlgorithmId.get(sigEngine.getAlgorithm());
            infoSigAlgId = sigAlgId;

            DerOutputStream out = new DerOutputStream();
            DerOutputStream tmp = new DerOutputStream();

            // encode crl info
            encodeInfo(tmp);

            // encode algorithm identifier
            sigAlgId.encode(tmp);

            // Create and encode the signature itself.
            sigEngine.update(tbsCertList, 0, tbsCertList.length);
            signature = sigEngine.sign();
            tmp.putBitString(signature);

            // Wrap the signed data in a SEQUENCE { data, algorithm, sig }
            out.write(DerValue.tag_Sequence, tmp);
            signedCRL = out.toByteArray();
            readOnly = true;

        } catch (IOException e) {
            throw new CRLException("Error while encoding data: " + e.getMessage(), e);
        }
    }

    /**
     * Returns a printable string of this CRL.
     *
     * @return value of this CRL in a printable form.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("X.509 CRL v" + (version+1) + "\n");
        if (sigAlgId != null)
            sb.append("Signature Algorithm: " + sigAlgId.toString() +
                  ", OID=" + (sigAlgId.getOID()).toString() + "\n");
        if (issuer != null)
            sb.append("Issuer: " + issuer.toString() + "\n");
        if (thisUpdate != null)
            sb.append("\nThis Update: " + thisUpdate.toString() + "\n");
        if (nextUpdate != null)
            sb.append("Next Update: " + nextUpdate.toString() + "\n");
        if (revokedCerts.isEmpty())
            sb.append("\nNO certificates have been revoked\n");
        else {
            sb.append("\nRevoked Certificates: " + revokedCerts.size());
            int i = 1;
            for (Iterator<X509CRLEntry> iter = revokedCerts.values().iterator();
                                             iter.hasNext(); i++)
                sb.append("\n[" + i + "] " + iter.next().toString());
        }
        if (extensions != null) {
            Collection<sune.security.x509.Extension> allExts = extensions.getAllExtensions();
            Object[] objs = allExts.toArray();
            sb.append("\nCRL Extensions: " + objs.length);
            for (int i = 0; i < objs.length; i++) {
                sb.append("\n[" + (i+1) + "]: ");
                sune.security.x509.Extension ext = (sune.security.x509.Extension)objs[i];
                try {
                   if (sune.security.x509.OIDMap.getClass(ext.getExtensionId()) == null) {
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
                       sb.append(ext.toString()); // sub-class exists
                } catch (Exception e) {
                    logger.warn("Warning in X509CRLImpl", e);
                    sb.append(", Error parsing this extension");
                }
            }
        }
        if (signature != null) {
            HexDumpEncoder encoder = new HexDumpEncoder();
            sb.append("\nSignature:\n" + encoder.encodeBuffer(signature)
                      + "\n");
        } else
            sb.append("NOT signed yet\n");
        return sb.toString();
    }

    /**
     * Checks whether the given certificate is on this CRL.
     *
     * @param cert the certificate to check for.
     * @return true if the given certificate is on this CRL,
     * false otherwise.
     */
    public boolean isRevoked(Certificate cert) {
        if (revokedCerts.isEmpty() || (!(cert instanceof X509Certificate))) {
            return false;
        }
        X509Certificate xcert = (X509Certificate) cert;
        X509IssuerSerial issuerSerial = new X509IssuerSerial(xcert);
        return revokedCerts.containsKey(issuerSerial);
    }

    /**
     * Gets the version number from this CRL.
     * The ASN.1 definition for this is:
     * <pre>
     * Version  ::=  INTEGER  {  v1(0), v2(1), v3(2)  }
     *             -- v3 does not apply to CRLs but appears for consistency
     *             -- with definition of Version for certs
     * </pre>
     * @return the version number, i.e. 1 or 2.
     */
    public int getVersion() {
        return version+1;
    }

    /**
     * Gets the issuer distinguished name from this CRL.
     * The issuer name identifies the entity who has signed (and
     * issued the CRL). The issuer name field contains an
     * X.500 distinguished name (DN).
     * The ASN.1 definition for this is:
     * <pre>
     * issuer    Name
     *
     * Name ::= CHOICE { RDNSequence }
     * RDNSequence ::= SEQUENCE OF RelativeDistinguishedName
     * RelativeDistinguishedName ::=
     *     SET OF AttributeValueAssertion
     *
     * AttributeValueAssertion ::= SEQUENCE {
     *                               AttributeType,
     *                               AttributeValue }
     * AttributeType ::= OBJECT IDENTIFIER
     * AttributeValue ::= ANY
     * </pre>
     * The Name describes a hierarchical name composed of attributes,
     * such as country name, and corresponding values, such as US.
     * The type of the component AttributeValue is determined by the
     * AttributeType; in general it will be a directoryString.
     * A directoryString is usually one of PrintableString,
     * TeletexString or UniversalString.
     * @return the issuer name.
     */
    public Principal getIssuerDN() {
        return (Principal)issuer;
    }

    /**
     * Return the issuer as X500Principal. Overrides method in X509CRL
     * to provide a slightly more efficient version.
     */
    public X500Principal getIssuerX500Principal() {
        if (issuerPrincipal == null) {
            issuerPrincipal = issuer.asX500Principal();
        }
        return issuerPrincipal;
    }

    /**
     * Gets the thisUpdate date from the CRL.
     * The ASN.1 definition for this is:
     *
     * @return the thisUpdate date from the CRL.
     */
    public Date getThisUpdate() {
        return (new Date(thisUpdate.getTime()));
    }

    /**
     * Gets the nextUpdate date from the CRL.
     *
     * @return the nextUpdate date from the CRL, or null if
     * not present.
     */
    public Date getNextUpdate() {
        if (nextUpdate == null)
            return null;
        return (new Date(nextUpdate.getTime()));
    }

    /**
     * Gets the CRL entry with the given serial number from this CRL.
     *
     * @return the entry with the given serial number, or <code>null</code> if
     * no such entry exists in the CRL.
     * @see X509CRLEntry
     */
    public X509CRLEntry getRevokedCertificate(BigInteger serialNumber) {
        if (revokedCerts.isEmpty()) {
            return null;
        }
        // assume this is a direct CRL entry (cert and CRL issuer are the same)
        X509IssuerSerial issuerSerial = new X509IssuerSerial
            (getIssuerX500Principal(), serialNumber);
        return revokedCerts.get(issuerSerial);
    }

    /**
     * Gets the CRL entry for the given certificate.
     */
    public X509CRLEntry getRevokedCertificate(X509Certificate cert) {
        if (revokedCerts.isEmpty()) {
            return null;
        }
        X509IssuerSerial issuerSerial = new X509IssuerSerial(cert);
        return revokedCerts.get(issuerSerial);
    }

    /**
     * Gets all the revoked certificates from the CRL.
     * A Set of X509CRLEntry.
     *
     * @return all the revoked certificates or <code>null</code> if there are
     * none.
     * @see X509CRLEntry
     */
    public Set<X509CRLEntry> getRevokedCertificates() {
        if (revokedCerts.isEmpty()) {
            return null;
        } else {
            return new HashSet<X509CRLEntry>(revokedCerts.values());
        }
    }

    /**
     * Gets the DER encoded CRL information, the
     * <code>tbsCertList</code> from this CRL.
     * This can be used to verify the signature independently.
     *
     * @return the DER encoded CRL information.
     * @exception CRLException on encoding errors.
     */
    public byte[] getTBSCertList() throws CRLException {
        if (tbsCertList == null)
            throw new CRLException("Uninitialized CRL");
        byte[] dup = new byte[tbsCertList.length];
        System.arraycopy(tbsCertList, 0, dup, 0, dup.length);
        return dup;
    }

    /**
     * Gets the raw Signature bits from the CRL.
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
     * Gets the signature algorithm name for the CRL
     * signature algorithm. For example, the string "SHA1withDSA".
     * The ASN.1 definition for this is:
     * <pre>
     * AlgorithmIdentifier  ::=  SEQUENCE  {
     *     algorithm               OBJECT IDENTIFIER,
     *     parameters              ANY DEFINED BY algorithm OPTIONAL  }
     *                             -- contains a value of the type
     *                             -- registered for use with the
     *                             -- algorithm object identifier value
     * </pre>
     *
     * @return the signature algorithm name.
     */
    public String getSigAlgName() {
        if (sigAlgId == null)
            return null;
        return sigAlgId.getName();
    }

    /**
     * Gets the signature algorithm OID string from the CRL.
     * An OID is represented by a set of positive whole number separated
     * by ".", that means,<br>
     * {@literal <positive whole number>.<positive whole number>.<...>}
     * For example, the string "1.2.840.10040.4.3" identifies the SHA-1
     * with DSA signature algorithm defined in
     * <a href="http://www.ietf.org/rfc/rfc3279.txt">RFC 3279: Algorithms and
     * Identifiers for the Internet X.509 Public Key Infrastructure Certificate
     * and CRL Profile</a>.
     *
     * @return the signature algorithm oid string.
     */
    public String getSigAlgOID() {
        if (sigAlgId == null)
            return null;
        ObjectIdentifier oid = sigAlgId.getOID();
        return oid.toString();
    }

    /**
     * Gets the DER encoded signature algorithm parameters from this
     * CRL's signature algorithm. In most cases, the signature
     * algorithm parameters are null, the parameters are usually
     * supplied with the Public Key.
     *
     * @return the DER encoded signature algorithm parameters, or
     *         null if no parameters are present.
     */
    public byte[] getSigAlgParams() {
        if (sigAlgId == null)
            return null;
        try {
            return sigAlgId.getEncodedParams();
        } catch (IOException e) {
            logger.warn("Warning in X509CRLImpl", e);
            return null;
        }
    }

    /**
     * return the AuthorityKeyIdentifier, if any.
     *
     * @return AuthorityKeyIdentifier or null
     *          (if no AuthorityKeyIdentifierExtension)
     * @throws IOException on error
     */
    public sune.security.x509.KeyIdentifier getAuthKeyId() throws IOException {
        sune.security.x509.AuthorityKeyIdentifierExtension aki = getAuthKeyIdExtension();
        if (aki != null) {
            sune.security.x509.KeyIdentifier keyId = (KeyIdentifier)aki.get(aki.KEY_ID);
            return keyId;
        } else {
            return null;
        }
    }

    /**
     * return the AuthorityKeyIdentifierExtension, if any.
     *
     * @return AuthorityKeyIdentifierExtension or null (if no such extension)
     * @throws IOException on error
     */
    public sune.security.x509.AuthorityKeyIdentifierExtension getAuthKeyIdExtension()
        throws IOException {
        Object obj = getExtension(sune.security.x509.PKIXExtensions.AuthorityKey_Id);
        return (AuthorityKeyIdentifierExtension)obj;
    }

    /**
     * return the CRLNumberExtension, if any.
     *
     * @return CRLNumberExtension or null (if no such extension)
     * @throws IOException on error
     */
    public sune.security.x509.CRLNumberExtension getCRLNumberExtension() throws IOException {
        Object obj = getExtension(sune.security.x509.PKIXExtensions.CRLNumber_Id);
        return (sune.security.x509.CRLNumberExtension)obj;
    }

    /**
     * return the CRL number from the CRLNumberExtension, if any.
     *
     * @return number or null (if no such extension)
     * @throws IOException on error
     */
    public BigInteger getCRLNumber() throws IOException {
        CRLNumberExtension numExt = getCRLNumberExtension();
        if (numExt != null) {
            BigInteger num = (BigInteger)numExt.get(numExt.NUMBER);
            return num;
        } else {
            return null;
        }
    }

    /**
     * return the DeltaCRLIndicatorExtension, if any.
     *
     * @return DeltaCRLIndicatorExtension or null (if no such extension)
     * @throws IOException on error
     */
    public sune.security.x509.DeltaCRLIndicatorExtension getDeltaCRLIndicatorExtension()
        throws IOException {

        Object obj = getExtension(sune.security.x509.PKIXExtensions.DeltaCRLIndicator_Id);
        return (sune.security.x509.DeltaCRLIndicatorExtension)obj;
    }

    /**
     * return the base CRL number from the DeltaCRLIndicatorExtension, if any.
     *
     * @return number or null (if no such extension)
     * @throws IOException on error
     */
    public BigInteger getBaseCRLNumber() throws IOException {
        DeltaCRLIndicatorExtension dciExt = getDeltaCRLIndicatorExtension();
        if (dciExt != null) {
            BigInteger num = (BigInteger)dciExt.get(dciExt.NUMBER);
            return num;
        } else {
            return null;
        }
    }

    /**
     * return the IssuerAlternativeNameExtension, if any.
     *
     * @return IssuerAlternativeNameExtension or null (if no such extension)
     * @throws IOException on error
     */
    public sune.security.x509.IssuerAlternativeNameExtension getIssuerAltNameExtension()
        throws IOException {
        Object obj = getExtension(sune.security.x509.PKIXExtensions.IssuerAlternativeName_Id);
        return (IssuerAlternativeNameExtension)obj;
    }

    /**
     * return the IssuingDistributionPointExtension, if any.
     *
     * @return IssuingDistributionPointExtension or null
     *          (if no such extension)
     * @throws IOException on error
     */
    public sune.security.x509.IssuingDistributionPointExtension
        getIssuingDistributionPointExtension() throws IOException {

        Object obj = getExtension(PKIXExtensions.IssuingDistributionPoint_Id);
        return (IssuingDistributionPointExtension) obj;
    }

    /**
     * Return true if a critical extension is found that is
     * not supported, otherwise return false.
     */
    public boolean hasUnsupportedCriticalExtension() {
        if (extensions == null)
            return false;
        return extensions.hasUnsupportedCriticalExtension();
    }

    /**
     * Gets a Set of the extension(s) marked CRITICAL in the
     * CRL. In the returned set, each extension is represented by
     * its OID string.
     *
     * @return a set of the extension oid strings in the
     * CRL that are marked critical.
     */
    public Set<String> getCriticalExtensionOIDs() {
        if (extensions == null) {
            return null;
        }
        Set<String> extSet = new HashSet<String>();
        for (sune.security.x509.Extension ex : extensions.getAllExtensions()) {
            if (ex.isCritical()) {
                extSet.add(ex.getExtensionId().toString());
            }
        }
        return extSet;
    }

    /**
     * Gets a Set of the extension(s) marked NON-CRITICAL in the
     * CRL. In the returned set, each extension is represented by
     * its OID string.
     *
     * @return a set of the extension oid strings in the
     * CRL that are NOT marked critical.
     */
    public Set<String> getNonCriticalExtensionOIDs() {
        if (extensions == null) {
            return null;
        }
        Set<String> extSet = new HashSet<String>();
        for (sune.security.x509.Extension ex : extensions.getAllExtensions()) {
            if (!ex.isCritical()) {
                extSet.add(ex.getExtensionId().toString());
            }
        }
        return extSet;
    }

    /**
     * Gets the DER encoded OCTET string for the extension value
     * (<code>extnValue</code>) identified by the passed in oid String.
     * The <code>oid</code> string is
     * represented by a set of positive whole number separated
     * by ".", that means,<br>
     * {@literal <positive whole number>.<positive whole number>.<...>}
     *
     * @param oid the Object Identifier value for the extension.
     * @return the der encoded octet string of the extension value.
     */
    public byte[] getExtensionValue(String oid) {
        if (extensions == null)
            return null;
        try {
            String extAlias = sune.security.x509.OIDMap.getName(new ObjectIdentifier(oid));
            sune.security.x509.Extension crlExt = null;

            if (extAlias == null) { // may be unknown
                ObjectIdentifier findOID = new ObjectIdentifier(oid);
                sune.security.x509.Extension ex = null;
                ObjectIdentifier inCertOID;
                for (Enumeration<Extension> e = extensions.getElements();
                     e.hasMoreElements();) {
                    ex = e.nextElement();
                    inCertOID = ex.getExtensionId();
                    if (inCertOID.equals(findOID)) {
                        crlExt = ex;
                        break;
                    }
                }
            } else
                crlExt = extensions.get(extAlias);
            if (crlExt == null)
                return null;
            byte[] extData = crlExt.getExtensionValue();
            if (extData == null)
                return null;
            DerOutputStream out = new DerOutputStream();
            out.putOctetString(extData);
            return out.toByteArray();
        } catch (Exception e) {
            logger.warn("Warning in X509CRLImpl", e);
            return null;
        }
    }

    /**
     * get an extension
     *
     * @param oid ObjectIdentifier of extension desired
     * @return Object of type {@literal <extension>} or null, if not found
     */
    public Object getExtension(ObjectIdentifier oid) {
        if (extensions == null)
            return null;

        // XXX Consider cloning this
        return extensions.get(OIDMap.getName(oid));
    }

    /*
     * Parses an X.509 CRL, should be used only by constructors.
     */
    private void parse(DerValue val) throws CRLException, IOException {
        // check if can over write the certificate
        if (readOnly)
            throw new CRLException("cannot over-write existing CRL");

        if ( val.getData() == null || val.tag != DerValue.tag_Sequence)
            throw new CRLException("Invalid DER-encoded CRL data");

        signedCRL = val.toByteArray();
        DerValue seq[] = new DerValue[3];

        seq[0] = val.data.getDerValue();
        seq[1] = val.data.getDerValue();
        seq[2] = val.data.getDerValue();

        if (val.data.available() != 0)
            throw new CRLException("signed overrun, bytes = "
                                     + val.data.available());

        if (seq[0].tag != DerValue.tag_Sequence)
            throw new CRLException("signed CRL fields invalid");

        sigAlgId = sune.security.x509.AlgorithmId.parse(seq[1]);
        signature = seq[2].getBitString();

        if (seq[1].data.available() != 0)
            throw new CRLException("AlgorithmId field overrun");

        if (seq[2].data.available() != 0)
            throw new CRLException("Signature field overrun");

        // the tbsCertsList
        tbsCertList = seq[0].toByteArray();

        // parse the information
        DerInputStream derStrm = seq[0].data;
        DerValue       tmp;
        byte           nextByte;

        // version (optional if v1)
        version = 0;   // by default, version = v1 == 0
        nextByte = (byte)derStrm.peekByte();
        if (nextByte == DerValue.tag_Integer) {
            version = derStrm.getInteger();
            if (version != 1)  // i.e. v2
                throw new CRLException("Invalid version");
        }
        tmp = derStrm.getDerValue();

        // signature
        sune.security.x509.AlgorithmId tmpId = AlgorithmId.parse(tmp);

        // the "inner" and "outer" signature algorithms must match
        if (! tmpId.equals(sigAlgId))
            throw new CRLException("Signature algorithm mismatch");
        infoSigAlgId = tmpId;

        // issuer
        issuer = new sune.security.x509.X500Name(derStrm);
        if (issuer.isEmpty()) {
            throw new CRLException("Empty issuer DN not allowed in X509CRLs");
        }

        // thisUpdate
        // check if UTCTime encoded or GeneralizedTime

        nextByte = (byte)derStrm.peekByte();
        if (nextByte == DerValue.tag_UtcTime) {
            thisUpdate = derStrm.getUTCTime();
        } else if (nextByte == DerValue.tag_GeneralizedTime) {
            thisUpdate = derStrm.getGeneralizedTime();
        } else {
            throw new CRLException("Invalid encoding for thisUpdate"
                                   + " (tag=" + nextByte + ")");
        }

        if (derStrm.available() == 0)
           return;     // done parsing no more optional fields present

        // nextUpdate (optional)
        nextByte = (byte)derStrm.peekByte();
        if (nextByte == DerValue.tag_UtcTime) {
            nextUpdate = derStrm.getUTCTime();
        } else if (nextByte == DerValue.tag_GeneralizedTime) {
            nextUpdate = derStrm.getGeneralizedTime();
        } // else it is not present

        if (derStrm.available() == 0)
            return;     // done parsing no more optional fields present

        // revokedCertificates (optional)
        nextByte = (byte)derStrm.peekByte();
        if ((nextByte == DerValue.tag_SequenceOf)
            && (! ((nextByte & 0x0c0) == 0x080))) {
            DerValue[] badCerts = derStrm.getSequence(4);

            X500Principal crlIssuer = getIssuerX500Principal();
            X500Principal badCertIssuer = crlIssuer;
            for (int i = 0; i < badCerts.length; i++) {
                sune.security.x509.X509CRLEntryImpl entry = new sune.security.x509.X509CRLEntryImpl(badCerts[i]);
                badCertIssuer = getCertIssuer(entry, badCertIssuer);
                entry.setCertificateIssuer(crlIssuer, badCertIssuer);
                X509IssuerSerial issuerSerial = new X509IssuerSerial
                    (badCertIssuer, entry.getSerialNumber());
                revokedCerts.put(issuerSerial, entry);
            }
        }

        if (derStrm.available() == 0)
            return;     // done parsing no extensions

        // crlExtensions (optional)
        tmp = derStrm.getDerValue();
        if (tmp.isConstructed() && tmp.isContextSpecific((byte)0)) {
            extensions = new CRLExtensions(tmp.data);
        }
        readOnly = true;
    }

    /**
     * Extract the issuer X500Principal from an X509CRL. Parses the encoded
     * form of the CRL to preserve the principal's ASN.1 encoding.
     *
     * Called by java.security.cert.X509CRL.getIssuerX500Principal().
     */
    public static X500Principal getIssuerX500Principal(X509CRL crl) {
        try {
            byte[] encoded = crl.getEncoded();
            DerInputStream derIn = new DerInputStream(encoded);
            DerValue tbsCert = derIn.getSequence(3)[0];
            DerInputStream tbsIn = tbsCert.data;

            DerValue tmp;
            // skip version number if present
            byte nextByte = (byte)tbsIn.peekByte();
            if (nextByte == DerValue.tag_Integer) {
                tmp = tbsIn.getDerValue();
            }

            tmp = tbsIn.getDerValue();  // skip signature
            tmp = tbsIn.getDerValue();  // issuer
            byte[] principalBytes = tmp.toByteArray();
            return new X500Principal(principalBytes);
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
    public static byte[] getEncodedInternal(X509CRL crl) throws CRLException {
        if (crl instanceof X509CRLImpl) {
            return ((X509CRLImpl)crl).getEncodedInternal();
        } else {
            return crl.getEncoded();
        }
    }

    /**
     * Utility method to convert an arbitrary instance of X509CRL
     * to a X509CRLImpl. Does a cast if possible, otherwise reparses
     * the encoding.
     */
    public static X509CRLImpl toImpl(X509CRL crl)
            throws CRLException {
        if (crl instanceof X509CRLImpl) {
            return (X509CRLImpl)crl;
        } else {
            return X509Factory.intern(crl);
        }
    }

    /**
     * Returns the X500 certificate issuer DN of a CRL entry.
     *
     * @param entry the entry to check
     * @param prevCertIssuer the previous entry's certificate issuer
     * @return the X500Principal in a CertificateIssuerExtension, or
     *   prevCertIssuer if it does not exist
     */
    private X500Principal getCertIssuer(X509CRLEntryImpl entry,
                                        X500Principal prevCertIssuer) throws IOException {

        sune.security.x509.CertificateIssuerExtension ciExt =
            entry.getCertificateIssuerExtension();
        if (ciExt != null) {
            sune.security.x509.GeneralNames names = (GeneralNames)
                ciExt.get(CertificateIssuerExtension.ISSUER);
            sune.security.x509.X500Name issuerDN = (X500Name) names.get(0).getName();
            return issuerDN.asX500Principal();
        } else {
            return prevCertIssuer;
        }
    }

    /**
     * Immutable X.509 Certificate Issuer DN and serial number pair
     */
    private final static class X509IssuerSerial {
        final X500Principal issuer;
        final BigInteger serial;
        volatile int hashcode = 0;

        /**
         * Create an X509IssuerSerial.
         *
         * @param issuer the issuer DN
         * @param serial the serial number
         */
        X509IssuerSerial(X500Principal issuer, BigInteger serial) {
            this.issuer = issuer;
            this.serial = serial;
        }

        /**
         * Construct an X509IssuerSerial from an X509Certificate.
         */
        X509IssuerSerial(X509Certificate cert) {
            this(cert.getIssuerX500Principal(), cert.getSerialNumber());
        }

        /**
         * Returns the issuer.
         *
         * @return the issuer
         */
        X500Principal getIssuer() {
            return issuer;
        }

        /**
         * Returns the serial number.
         *
         * @return the serial number
         */
        BigInteger getSerial() {
            return serial;
        }

        /**
         * Compares this X509Serial with another and returns true if they
         * are equivalent.
         *
         * @param o the other object to compare with
         * @return true if equal, false otherwise
         */
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (!(o instanceof X509IssuerSerial)) {
                return false;
            }

            X509IssuerSerial other = (X509IssuerSerial) o;
            if (serial.equals(other.getSerial()) &&
                issuer.equals(other.getIssuer())) {
                return true;
            }
            return false;
        }

        /**
         * Returns a hash code value for this X509IssuerSerial.
         *
         * @return the hash code value
         */
        public int hashCode() {
            if (hashcode == 0) {
                int result = 17;
                result = 37*result + issuer.hashCode();
                result = 37*result + serial.hashCode();
                hashcode = result;
            }
            return hashcode;
        }
    }
}
