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

package sune.security.pkcs.android;

import java.io.OutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.security.*;
import java.util.ArrayList;

import sune.security.pkcs.PKCS9Attribute;
import sune.security.pkcs.PKCS9Attributes;
import sune.security.pkcs.ParsingException;
import sune.security.util.DerOutputStream;
import sune.security.x509.AlgorithmId;
import sune.security.x509.X500Name;
import sune.security.x509.KeyUsageExtension;
import sune.misc.HexDumpEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A SignerInfo, as defined in PKCS#7's signedData type.
 *
 * @author Benjamin Renaud
 */
public class SignerInfo implements sune.security.util.DerEncoder {

    protected static Logger logger = LoggerFactory.getLogger(SignerInfo.class);

    BigInteger version;
    X500Name issuerName;
    BigInteger certificateSerialNumber;
    AlgorithmId digestAlgorithmId;
    AlgorithmId digestEncryptionAlgorithmId;
    byte[] encryptedDigest;

    PKCS9Attributes authenticatedAttributes;
    PKCS9Attributes unauthenticatedAttributes;

    public SignerInfo(X500Name  issuerName,
                      BigInteger serial,
                      AlgorithmId digestAlgorithmId,
                      AlgorithmId digestEncryptionAlgorithmId,
                      byte[] encryptedDigest) {
        this.version = BigInteger.ONE;
        this.issuerName = issuerName;
        this.certificateSerialNumber = serial;
        this.digestAlgorithmId = digestAlgorithmId;
        this.digestEncryptionAlgorithmId = digestEncryptionAlgorithmId;
        this.encryptedDigest = encryptedDigest;
    }

    public SignerInfo(X500Name  issuerName,
                      BigInteger serial,
                      AlgorithmId digestAlgorithmId,
                      PKCS9Attributes authenticatedAttributes,
                      AlgorithmId digestEncryptionAlgorithmId,
                      byte[] encryptedDigest,
                      PKCS9Attributes unauthenticatedAttributes) {
        this.version = BigInteger.ONE;
        this.issuerName = issuerName;
        this.certificateSerialNumber = serial;
        this.digestAlgorithmId = digestAlgorithmId;
        this.authenticatedAttributes = authenticatedAttributes;
        this.digestEncryptionAlgorithmId = digestEncryptionAlgorithmId;
        this.encryptedDigest = encryptedDigest;
        this.unauthenticatedAttributes = unauthenticatedAttributes;
    }

    /**
     * Parses a PKCS#7 signer info.
     */
    public SignerInfo(sune.security.util.DerInputStream derin)
        throws IOException, ParsingException
    {
        this(derin, false);
    }

    /**
     * Parses a PKCS#7 signer info.
     *
     * <p>This constructor is used only for backwards compatibility with
     * PKCS#7 blocks that were generated using JDK1.1.x.
     *
     * @param derin the ASN.1 encoding of the signer info.
     * @param oldStyle flag indicating whether or not the given signer info
     * is encoded according to JDK1.1.x.
     */
    public SignerInfo(sune.security.util.DerInputStream derin, boolean oldStyle)
        throws IOException, ParsingException
    {
        // version
        version = derin.getBigInteger();

        // issuerAndSerialNumber
        sune.security.util.DerValue[] issuerAndSerialNumber = derin.getSequence(2);
        byte[] issuerBytes = issuerAndSerialNumber[0].toByteArray();
        issuerName = new X500Name(new sune.security.util.DerValue(sune.security.util.DerValue.tag_Sequence,
                                               issuerBytes));
        certificateSerialNumber = issuerAndSerialNumber[1].getBigInteger();

        // digestAlgorithmId
        sune.security.util.DerValue tmp = derin.getDerValue();

        digestAlgorithmId = AlgorithmId.parse(tmp);

        // authenticatedAttributes
        if (oldStyle) {
            // In JDK1.1.x, the authenticatedAttributes are always present,
            // encoded as an empty Set (Set of length zero)
            derin.getSet(0);
        } else {
            // check if set of auth attributes (implicit tag) is provided
            // (auth attributes are OPTIONAL)
            if ((byte)(derin.peekByte()) == (byte)0xA0) {
                authenticatedAttributes = new PKCS9Attributes(derin);
            }
        }

        // digestEncryptionAlgorithmId - little RSA naming scheme -
        // signature == encryption...
        tmp = derin.getDerValue();

        digestEncryptionAlgorithmId = AlgorithmId.parse(tmp);

        // encryptedDigest
        encryptedDigest = derin.getOctetString();

        // unauthenticatedAttributes
        if (oldStyle) {
            // In JDK1.1.x, the unauthenticatedAttributes are always present,
            // encoded as an empty Set (Set of length zero)
            derin.getSet(0);
        } else {
            // check if set of unauth attributes (implicit tag) is provided
            // (unauth attributes are OPTIONAL)
            if (derin.available() != 0
                && (byte)(derin.peekByte()) == (byte)0xA1) {
                unauthenticatedAttributes =
                    new PKCS9Attributes(derin, true);// ignore unsupported attrs
            }
        }

        // all done
        if (derin.available() != 0) {
            throw new ParsingException("extra data at the end");
        }
    }

    public void encode(sune.security.util.DerOutputStream out) throws IOException {

        derEncode(out);
    }

    /**
     * DER encode this object onto an output stream.
     * Implements the <code>DerEncoder</code> interface.
     *
     * @param out
     * the output stream on which to write the DER encoding.
     *
     * @exception IOException on encoding error.
     */
    public void derEncode(OutputStream out) throws IOException {
        sune.security.util.DerOutputStream seq = new DerOutputStream();
        seq.putInteger(version);
        sune.security.util.DerOutputStream issuerAndSerialNumber = new sune.security.util.DerOutputStream();
        issuerName.encode(issuerAndSerialNumber);
        issuerAndSerialNumber.putInteger(certificateSerialNumber);
        seq.write(sune.security.util.DerValue.tag_Sequence, issuerAndSerialNumber);

        digestAlgorithmId.encode(seq);

        // encode authenticated attributes if there are any
        if (authenticatedAttributes != null)
            authenticatedAttributes.encode((byte)0xA0, seq);

        digestEncryptionAlgorithmId.encode(seq);

        seq.putOctetString(encryptedDigest);

        // encode unauthenticated attributes if there are any
        if (unauthenticatedAttributes != null)
            unauthenticatedAttributes.encode((byte)0xA1, seq);

        sune.security.util.DerOutputStream tmp = new sune.security.util.DerOutputStream();
        tmp.write(sune.security.util.DerValue.tag_Sequence, seq);

        out.write(tmp.toByteArray());
    }



    /*
     * Returns the (user) certificate pertaining to this SignerInfo.
     */
    public X509Certificate getCertificate(PKCS7 block)
        throws IOException
    {
        return block.getCertificate(certificateSerialNumber, issuerName);
    }

    /*
     * Returns the certificate chain pertaining to this SignerInfo.
     */
    public ArrayList<X509Certificate> getCertificateChain(PKCS7 block)
        throws IOException
    {
        X509Certificate userCert;
        userCert = block.getCertificate(certificateSerialNumber, issuerName);
        if (userCert == null)
            return null;

        ArrayList<X509Certificate> certList = new ArrayList<X509Certificate>();
        certList.add(userCert);

        X509Certificate[] pkcsCerts = block.getCertificates();
        if (pkcsCerts == null
            || userCert.getSubjectDN().equals(userCert.getIssuerDN())) {
            return certList;
        }

        Principal issuer = userCert.getIssuerDN();
        int start = 0;
        while (true) {
            boolean match = false;
            int i = start;
            while (i < pkcsCerts.length) {
                if (issuer.equals(pkcsCerts[i].getSubjectDN())) {
                    // next cert in chain found
                    certList.add(pkcsCerts[i]);
                    // if selected cert is self-signed, we're done
                    // constructing the chain
                    if (pkcsCerts[i].getSubjectDN().equals(
                                            pkcsCerts[i].getIssuerDN())) {
                        start = pkcsCerts.length;
                    } else {
                        issuer = pkcsCerts[i].getIssuerDN();
                        X509Certificate tmpCert = pkcsCerts[start];
                        pkcsCerts[start] = pkcsCerts[i];
                        pkcsCerts[i] = tmpCert;
                        start++;
                    }
                    match = true;
                    break;
                } else {
                    i++;
                }
            }
            if (!match)
                break;
        }

        return certList;
    }

    /* Returns null if verify fails, this signerInfo if
       verify succeeds. */
    SignerInfo verify(PKCS7 block, byte[] data)
    throws NoSuchAlgorithmException, SignatureException {

        try {

            ContentInfo content = block.getContentInfo();
            if (data == null) {
                data = content.getContentBytes();
            }

            String digestAlgname = getDigestAlgorithmId().getName();
            if (digestAlgname.equalsIgnoreCase("SHA"))
                digestAlgname = "SHA1";
            else if(digestAlgname.equalsIgnoreCase("SHA-1"))
                digestAlgname = "SHA1";

            byte[] dataSigned;

            // if there are authenticate attributes, get the message
            // digest and compare it with the digest of data
            if (authenticatedAttributes == null) {
                dataSigned = data;
            } else {

                // first, check content type
                sune.security.util.ObjectIdentifier contentType = (sune.security.util.ObjectIdentifier)
                       authenticatedAttributes.getAttributeValue(
                         PKCS9Attribute.CONTENT_TYPE_OID);
                if (contentType == null ||
                    !contentType.equals(content.contentType))
                    return null;  // contentType does not match, bad SignerInfo

                // now, check message digest
                byte[] messageDigest = (byte[])
                    authenticatedAttributes.getAttributeValue(
                         PKCS9Attribute.MESSAGE_DIGEST_OID);

                if (messageDigest == null) // fail if there is no message digest
                    return null;

                MessageDigest md = MessageDigest.getInstance(digestAlgname);
                byte[] computedMessageDigest = md.digest(data);

                if (messageDigest.length != computedMessageDigest.length)
                    return null;
                for (int i = 0; i < messageDigest.length; i++) {
                    if (messageDigest[i] != computedMessageDigest[i])
                        return null;
                }

                // message digest attribute matched
                // digest of original data

                // the data actually signed is the DER encoding of
                // the authenticated attributes (tagged with
                // the "SET OF" tag, not 0xA0).
                dataSigned = authenticatedAttributes.getDerEncoding();
            }

            // put together digest algorithm and encryption algorithm
            // to form signing algorithm
            String encryptionAlgname =
                getDigestEncryptionAlgorithmId().getName();

            if (encryptionAlgname.equalsIgnoreCase("SHA1withDSA"))
                encryptionAlgname = "DSA";
            String algname = digestAlgname + "with" + encryptionAlgname;

            Signature sig = Signature.getInstance(algname);
            X509Certificate cert = getCertificate(block);

            if (cert == null) {
                return null;
            }
            if (cert.hasUnsupportedCriticalExtension()) {
                throw new SignatureException("Certificate has unsupported "
                                             + "critical extension(s)");
            }

            // Make sure that if the usage of the key in the certificate is
            // restricted, it can be used for digital signatures.
            // XXX We may want to check for additional extensions in the
            // future.
            boolean[] keyUsageBits = cert.getKeyUsage();
            if (keyUsageBits != null) {
                KeyUsageExtension keyUsage;
                try {
                    // We don't care whether or not this extension was marked
                    // critical in the certificate.
                    // We're interested only in its value (i.e., the bits set)
                    // and treat the extension as critical.
                    keyUsage = new KeyUsageExtension(keyUsageBits);
                } catch (IOException ioe) {
                    logger.warn("Warning in SignerInfo", ioe);
                    throw new SignatureException("Failed to parse keyUsage "
                                                 + "extension");
                }

                boolean digSigAllowed = ((Boolean)keyUsage.get(
                        KeyUsageExtension.DIGITAL_SIGNATURE)).booleanValue();

                boolean nonRepuAllowed = ((Boolean)keyUsage.get(
                        KeyUsageExtension.NON_REPUDIATION)).booleanValue();

                if (!digSigAllowed && !nonRepuAllowed) {
                    throw new SignatureException("Key usage restricted: "
                                                 + "cannot be used for "
                                                 + "digital signatures");
                }
            }

            PublicKey key = cert.getPublicKey();
            sig.initVerify(key);

            sig.update(dataSigned);

            if (sig.verify(encryptedDigest)) {
                return this;
            }

        } catch (IOException e) {
            logger.warn("Warning in SignerInfo", e);
            throw new SignatureException("IO error verifying signature:\n" +
                                         e.getMessage());

        } catch (InvalidKeyException e) {
            logger.warn("Warning in SignerInfo", e);
            throw new SignatureException("InvalidKey: " + e.getMessage());

        }
        return null;
    }

    /* Verify the content of the pkcs7 block. */
    SignerInfo verify(PKCS7 block)
    throws NoSuchAlgorithmException, SignatureException {
        return verify(block, null);
    }


    public BigInteger getVersion() {
            return version;
    }

    public X500Name getIssuerName() {
        return issuerName;
    }

    public BigInteger getCertificateSerialNumber() {
        return certificateSerialNumber;
    }

    public AlgorithmId getDigestAlgorithmId() {
        return digestAlgorithmId;
    }

    public PKCS9Attributes getAuthenticatedAttributes() {
        return authenticatedAttributes;
    }

    public AlgorithmId getDigestEncryptionAlgorithmId() {
        return digestEncryptionAlgorithmId;
    }

    public byte[] getEncryptedDigest() {
        return encryptedDigest;
    }

    public PKCS9Attributes getUnauthenticatedAttributes() {
        return unauthenticatedAttributes;
    }

    public String toString() {
        HexDumpEncoder hexDump = new HexDumpEncoder();

        String out = "";

        out += "Signer Info for (issuer): " + issuerName + "\n";
        out += "\tversion: " + sune.security.util.Debug.toHexString(version) + "\n";
        out += "\tcertificateSerialNumber: " +
               sune.security.util.Debug.toHexString(certificateSerialNumber) + "\n";
        out += "\tdigestAlgorithmId: " + digestAlgorithmId + "\n";
        if (authenticatedAttributes != null) {
            out += "\tauthenticatedAttributes: " + authenticatedAttributes +
                   "\n";
        }
        out += "\tdigestEncryptionAlgorithmId: " + digestEncryptionAlgorithmId +
            "\n";

        out += "\tencryptedDigest: " + "\n" +
            hexDump.encodeBuffer(encryptedDigest) + "\n";
        if (unauthenticatedAttributes != null) {
            out += "\tunauthenticatedAttributes: " +
                   unauthenticatedAttributes + "\n";
        }
        return out;
    }

}
