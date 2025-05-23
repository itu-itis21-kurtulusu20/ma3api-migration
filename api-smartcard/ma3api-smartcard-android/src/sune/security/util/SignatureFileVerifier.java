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

package sune.security.util;

import java.security.CodeSigner;
import java.security.cert.CertPath;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.*;
import java.io.*;
import java.util.*;
import java.util.jar.*;

import sune.security.pkcs.PKCS9Attributes;
import sune.security.timestamp.TimestampToken;
import sune.misc.BASE64Decoder;

import sune.security.jca.Providers;
import sune.security.pkcs.android.PKCS7;
import sune.security.pkcs.android.SignerInfo;

public class SignatureFileVerifier {

    /* Are we debugging ? */
    private static final sune.security.util.Debug debug = Debug.getInstance("jar");

    /* cache of CodeSigner objects */
    private ArrayList<CodeSigner[]> signerCache;

    private static final String ATTR_DIGEST =
        ("-DIGEST-" + sune.security.util.ManifestDigester.MF_MAIN_ATTRS).toUpperCase
        (Locale.ENGLISH);

    /** the PKCS7 block for this .DSA/.RSA file */
    private PKCS7 block;

    /** the raw bytes of the .SF file */
    private byte sfBytes[];

    /** the name of the signature block file, uppercased and without
     *  the extension (.DSA/.RSA)
     */
    private String name;

    /** the ManifestDigester */
    private sune.security.util.ManifestDigester md;

    /** cache of created MessageDigest objects */
    private HashMap<String, MessageDigest> createdDigests;

    /* workaround for parsing Netscape jars  */
    private boolean workaround = false;

    /* for generating certpath objects */
    private CertificateFactory certificateFactory = null;

    /**
     * Create the named SignatureFileVerifier.
     *
     * @param name the name of the signature block file (.DSA/.RSA)
     *
     * @param rawBytes the raw bytes of the signature block file
     */
    public SignatureFileVerifier(ArrayList<CodeSigner[]> signerCache,
                                 sune.security.util.ManifestDigester md,
                                 String name,
                                 byte rawBytes[])
        throws IOException, CertificateException
    {
        // new PKCS7() calls CertificateFactory.getInstance()
        // need to use local providers here, see Providers class
        Object obj = null;
        try {
            obj = Providers.startJarVerification();
            block = new PKCS7(rawBytes);
            sfBytes = block.getContentInfo().getData();
            certificateFactory = CertificateFactory.getInstance("X509");
        } finally {
            Providers.stopJarVerification(obj);
        }
        this.name = name.substring(0, name.lastIndexOf("."))
                                                   .toUpperCase(Locale.ENGLISH);
        this.md = md;
        this.signerCache = signerCache;
    }

    /**
     * returns true if we need the .SF file
     */
    public boolean needSignatureFileBytes()
    {

        return sfBytes == null;
    }


    /**
     * returns true if we need this .SF file.
     *
     * @param name the name of the .SF file without the extension
     *
     */
    public boolean needSignatureFile(String name)
    {
        return this.name.equalsIgnoreCase(name);
    }

    /**
     * used to set the raw bytes of the .SF file when it
     * is external to the signature block file.
     */
    public void setSignatureFile(byte sfBytes[])
    {
        this.sfBytes = sfBytes;
    }

    /**
     * Utility method used by JarVerifier and JarSigner
     * to determine the signature file names and PKCS7 block
     * files names that are supported
     *
     * @param s file name
     * @return true if the input file name is a supported
     *          Signature File or PKCS7 block file name
     */
    public static boolean isBlockOrSF(String s) {
        // we currently only support DSA and RSA PKCS7 blocks
        if (s.endsWith(".SF") || s.endsWith(".DSA") || s.endsWith(".RSA")) {
            return true;
        }
        return false;
    }

    /** get digest from cache */

    private MessageDigest getDigest(String algorithm)
    {
        if (createdDigests == null)
            createdDigests = new HashMap<String, MessageDigest>();

        MessageDigest digest = createdDigests.get(algorithm);

        if (digest == null) {
            try {
                digest = MessageDigest.getInstance(algorithm);
                createdDigests.put(algorithm, digest);
            } catch (NoSuchAlgorithmException nsae) {
                // ignore
            }
        }
        return digest;
    }

    /**
     * process the signature block file. Goes through the .SF file
     * and adds code signers for each section where the .SF section
     * hash was verified against the Manifest section.
     *
     *
     */
    public void process(Hashtable<String, CodeSigner[]> signers)
        throws IOException, SignatureException, NoSuchAlgorithmException,
            JarException, CertificateException
    {
        // calls Signature.getInstance() and MessageDigest.getInstance()
        // need to use local providers here, see Providers class
        Object obj = null;
        try {
            obj = Providers.startJarVerification();
            processImpl(signers);
        } finally {
            Providers.stopJarVerification(obj);
        }

    }

    private void processImpl(Hashtable<String, CodeSigner[]> signers)
        throws IOException, SignatureException, NoSuchAlgorithmException,
            JarException, CertificateException
    {
        Manifest sf = new Manifest();
        sf.read(new ByteArrayInputStream(sfBytes));

        String version =
            sf.getMainAttributes().getValue(Attributes.Name.SIGNATURE_VERSION);

        if ((version == null) || !(version.equalsIgnoreCase("1.0"))) {
            // XXX: should this be an exception?
            // for now we just ignore this signature file
            return;
        }

        SignerInfo[] infos = block.verify(sfBytes);

        if (infos == null) {
            throw new SecurityException("cannot verify signature block file " +
                                        name);
        }

        BASE64Decoder decoder = new BASE64Decoder();

        CodeSigner[] newSigners = getSigners(infos, block);

        // make sure we have something to do all this work for...
        if (newSigners == null)
            return;

        Iterator<Map.Entry<String,Attributes>> entries =
                                sf.getEntries().entrySet().iterator();

        // see if we can verify the whole manifest first
        boolean manifestSigned = verifyManifestHash(sf, md, decoder);

        // verify manifest main attributes
        if (!manifestSigned && !verifyManifestMainAttrs(sf, md, decoder)) {
            throw new SecurityException
                ("Invalid signature file digest for Manifest main attributes");
        }

        // go through each section in the signature file
        while(entries.hasNext()) {

            Map.Entry<String,Attributes> e = entries.next();
            String name = e.getKey();

            if (manifestSigned ||
                (verifySection(e.getValue(), name, md, decoder))) {

                if (name.startsWith("./"))
                    name = name.substring(2);

                if (name.startsWith("/"))
                    name = name.substring(1);

                updateSigners(newSigners, signers, name);

                if (debug != null) {
                    debug.println("processSignature signed name = "+name);
                }

            } else if (debug != null) {
                debug.println("processSignature unsigned name = "+name);
            }
        }
    }

    /**
     * See if the whole manifest was signed.
     */
    private boolean verifyManifestHash(Manifest sf,
                                       sune.security.util.ManifestDigester md,
                                       BASE64Decoder decoder)
         throws IOException
    {
        Attributes mattr = sf.getMainAttributes();
        boolean manifestSigned = false;

        // go through all the attributes and process *-Digest-Manifest entries
        for (Map.Entry<Object,Object> se : mattr.entrySet()) {

            String key = se.getKey().toString();

            if (key.toUpperCase(Locale.ENGLISH).endsWith("-DIGEST-MANIFEST")) {
                // 16 is length of "-Digest-Manifest"
                String algorithm = key.substring(0, key.length()-16);

                MessageDigest digest = getDigest(algorithm);
                if (digest != null) {
                    byte[] computedHash = md.manifestDigest(digest);
                    byte[] expectedHash =
                        decoder.decodeBuffer((String)se.getValue());

                    if (debug != null) {
                     debug.println("Signature File: Manifest digest " +
                                          digest.getAlgorithm());
                     debug.println( "  sigfile  " + toHex(expectedHash));
                     debug.println( "  computed " + toHex(computedHash));
                     debug.println();
                    }

                    if (MessageDigest.isEqual(computedHash,
                                              expectedHash)) {
                        manifestSigned = true;
                    } else {
                        //XXX: we will continue and verify each section
                    }
                }
            }
        }
        return manifestSigned;
    }

    private boolean verifyManifestMainAttrs(Manifest sf,
                                        sune.security.util.ManifestDigester md,
                                        BASE64Decoder decoder)
         throws IOException
    {
        Attributes mattr = sf.getMainAttributes();
        boolean attrsVerified = true;

        // go through all the attributes and process
        // digest entries for the manifest main attributes
        for (Map.Entry<Object,Object> se : mattr.entrySet()) {
            String key = se.getKey().toString();

            if (key.toUpperCase(Locale.ENGLISH).endsWith(ATTR_DIGEST)) {
                String algorithm =
                        key.substring(0, key.length() - ATTR_DIGEST.length());

                MessageDigest digest = getDigest(algorithm);
                if (digest != null) {
                    sune.security.util.ManifestDigester.Entry mde =
                        md.get(sune.security.util.ManifestDigester.MF_MAIN_ATTRS, false);
                    byte[] computedHash = mde.digest(digest);
                    byte[] expectedHash =
                        decoder.decodeBuffer((String)se.getValue());

                    if (debug != null) {
                     debug.println("Signature File: " +
                                        "Manifest Main Attributes digest " +
                                        digest.getAlgorithm());
                     debug.println( "  sigfile  " + toHex(expectedHash));
                     debug.println( "  computed " + toHex(computedHash));
                     debug.println();
                    }

                    if (MessageDigest.isEqual(computedHash,
                                              expectedHash)) {
                        // good
                    } else {
                        // we will *not* continue and verify each section
                        attrsVerified = false;
                        if (debug != null) {
                            debug.println("Verification of " +
                                        "Manifest main attributes failed");
                            debug.println();
                        }
                        break;
                    }
                }
            }
        }

        // this method returns 'true' if either:
        //      . manifest main attributes were not signed, or
        //      . manifest main attributes were signed and verified
        return attrsVerified;
    }

    /**
     * given the .SF digest header, and the data from the
     * section in the manifest, see if the hashes match.
     * if not, throw a SecurityException.
     *
     * @return true if all the -Digest headers verified
     * @exception SecurityException if the hash was not equal
     */

    private boolean verifySection(Attributes sfAttr,
                                  String name,
                                  sune.security.util.ManifestDigester md,
                                  BASE64Decoder decoder)
         throws IOException
    {
        boolean oneDigestVerified = false;
        ManifestDigester.Entry mde = md.get(name,block.isOldStyle());

        if (mde == null) {
            throw new SecurityException(
                  "no manifiest section for signature file entry "+name);
        }

        if (sfAttr != null) {

            //sun.misc.HexDumpEncoder hex = new sun.misc.HexDumpEncoder();
            //hex.encodeBuffer(data, System.out);

            // go through all the attributes and process *-Digest entries
            for (Map.Entry<Object,Object> se : sfAttr.entrySet()) {
                String key = se.getKey().toString();

                if (key.toUpperCase(Locale.ENGLISH).endsWith("-DIGEST")) {
                    // 7 is length of "-Digest"
                    String algorithm = key.substring(0, key.length()-7);

                    MessageDigest digest = getDigest(algorithm);

                    if (digest != null) {
                        boolean ok = false;

                        byte[] expected =
                            decoder.decodeBuffer((String)se.getValue());
                        byte[] computed;
                        if (workaround) {
                            computed = mde.digestWorkaround(digest);
                        } else {
                            computed = mde.digest(digest);
                        }

                        if (debug != null) {
                          debug.println("Signature Block File: " +
                                   name + " digest=" + digest.getAlgorithm());
                          debug.println("  expected " + toHex(expected));
                          debug.println("  computed " + toHex(computed));
                          debug.println();
                        }

                        if (MessageDigest.isEqual(computed, expected)) {
                            oneDigestVerified = true;
                            ok = true;
                        } else {
                            // attempt to fallback to the workaround
                            if (!workaround) {
                               computed = mde.digestWorkaround(digest);
                               if (MessageDigest.isEqual(computed, expected)) {
                                   if (debug != null) {
                                       debug.println("  re-computed " + toHex(computed));
                                       debug.println();
                                   }
                                   workaround = true;
                                   oneDigestVerified = true;
                                   ok = true;
                               }
                            }
                        }
                        if (!ok){
                            throw new SecurityException("invalid " +
                                       digest.getAlgorithm() +
                                       " signature file digest for " + name);
                        }
                    }
                }
            }
        }
        return oneDigestVerified;
    }

    /**
     * Given the PKCS7 block and SignerInfo[], create an array of
     * CodeSigner objects. We do this only *once* for a given
     * signature block file.
     */
    private CodeSigner[] getSigners(SignerInfo infos[], PKCS7 block)
        throws IOException, NoSuchAlgorithmException, SignatureException,
            CertificateException {

        ArrayList<CodeSigner> signers = null;

        for (int i = 0; i < infos.length; i++) {

            SignerInfo info = infos[i];
            ArrayList<X509Certificate> chain = info.getCertificateChain(block);
            CertPath certChain = certificateFactory.generateCertPath(chain);
            if (signers == null) {
                signers = new ArrayList<CodeSigner>();
            }
            // Append the new code signer
            signers.add(new CodeSigner(certChain, getTimestamp(info)));

            if (debug != null) {
                debug.println("Signature Block Certificate: " +
                    chain.get(0));
            }
        }

        if (signers != null) {
            return signers.toArray(new CodeSigner[signers.size()]);
        } else {
            return null;
        }
    }

    /*
     * Examines a signature timestamp token to generate a timestamp object.
     *
     * Examines the signer's unsigned attributes for a
     * <tt>signatureTimestampToken</tt> attribute. If present,
     * then it is parsed to extract the date and time at which the
     * timestamp was generated.
     *
     * @param info A signer information element of a PKCS 7 block.
     *
     * @return A timestamp token or null if none is present.
     * @throws IOException if an error is encountered while parsing the
     *         PKCS7 data.
     * @throws NoSuchAlgorithmException if an error is encountered while
     *         verifying the PKCS7 object.
     * @throws SignatureException if an error is encountered while
     *         verifying the PKCS7 object.
     * @throws CertificateException if an error is encountered while generating
     *         the TSA's certpath.
     */
    private Timestamp getTimestamp(SignerInfo info)
        throws IOException, NoSuchAlgorithmException, SignatureException,
            CertificateException {

        Timestamp timestamp = null;

        // Extract the signer's unsigned attributes
        PKCS9Attributes unsignedAttrs = info.getUnauthenticatedAttributes();
        if (unsignedAttrs != null) {
            sune.security.pkcs.PKCS9Attribute timestampTokenAttr =
                unsignedAttrs.getAttribute("signatureTimestampToken");
            if (timestampTokenAttr != null) {
                PKCS7 timestampToken =
                    new PKCS7((byte[])timestampTokenAttr.getValue());
                // Extract the content (an encoded timestamp token info)
                byte[] encodedTimestampTokenInfo =
                    timestampToken.getContentInfo().getData();
                // Extract the signer (the Timestamping Authority)
                // while verifying the content
                SignerInfo[] tsa =
                    timestampToken.verify(encodedTimestampTokenInfo);
                // Expect only one signer
                ArrayList<X509Certificate> chain =
                                tsa[0].getCertificateChain(timestampToken);
                CertPath tsaChain = certificateFactory.generateCertPath(chain);
                // Create a timestamp token info object
                TimestampToken timestampTokenInfo =
                    new TimestampToken(encodedTimestampTokenInfo);
                // Create a timestamp object
                timestamp =
                    new Timestamp(timestampTokenInfo.getDate(), tsaChain);
            }
        }
        return timestamp;
    }

    // for the toHex function
    private static final char[] hexc =
            {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    /**
     * convert a byte array to a hex string for debugging purposes
     * @param data the binary data to be converted to a hex string
     * @return an ASCII hex string
     */

    static String toHex(byte[] data) {

        StringBuffer sb = new StringBuffer(data.length*2);

        for (int i=0; i<data.length; i++) {
            sb.append(hexc[(data[i] >>4) & 0x0f]);
            sb.append(hexc[data[i] & 0x0f]);
        }
        return sb.toString();
    }

    // returns true if set contains signer
    static boolean contains(CodeSigner[] set, CodeSigner signer)
    {
        for (int i = 0; i < set.length; i++) {
            if (set[i].equals(signer))
                return true;
        }
        return false;
    }

    // returns true if subset is a subset of set
    static boolean isSubSet(CodeSigner[] subset, CodeSigner[] set)
    {
        // check for the same object
        if (set == subset)
            return true;

        boolean match;
        for (int i = 0; i < subset.length; i++) {
            if (!contains(set, subset[i]))
                return false;
        }
        return true;
    }

    /**
     * returns true if signer contains exactly the same code signers as
     * oldSigner and newSigner, false otherwise. oldSigner
     * is allowed to be null.
     */
    static boolean matches(CodeSigner[] signers, CodeSigner[] oldSigners,
        CodeSigner[] newSigners) {

        // special case
        if ((oldSigners == null) && (signers == newSigners))
            return true;

        boolean match;

        // make sure all oldSigners are in signers
        if ((oldSigners != null) && !isSubSet(oldSigners, signers))
            return false;

        // make sure all newSigners are in signers
        if (!isSubSet(newSigners, signers)) {
            return false;
        }

        // now make sure all the code signers in signers are
        // also in oldSigners or newSigners

        for (int i = 0; i < signers.length; i++) {
            boolean found =
                ((oldSigners != null) && contains(oldSigners, signers[i])) ||
                contains(newSigners, signers[i]);
            if (!found)
                return false;
        }
        return true;
    }

    void updateSigners(CodeSigner[] newSigners,
        Hashtable<String, CodeSigner[]> signers, String name) {

        CodeSigner[] oldSigners = signers.get(name);

        // search through the cache for a match, go in reverse order
        // as we are more likely to find a match with the last one
        // added to the cache

        CodeSigner[] cachedSigners;
        for (int i = signerCache.size() - 1; i != -1; i--) {
            cachedSigners = signerCache.get(i);
            if (matches(cachedSigners, oldSigners, newSigners)) {
                signers.put(name, cachedSigners);
                return;
            }
        }

        if (oldSigners == null) {
            cachedSigners = newSigners;
        } else {
            cachedSigners =
                new CodeSigner[oldSigners.length + newSigners.length];
            System.arraycopy(oldSigners, 0, cachedSigners, 0,
                oldSigners.length);
            System.arraycopy(newSigners, 0, cachedSigners, oldSigners.length,
                newSigners.length);
        }
        signerCache.add(cachedSigners);
        signers.put(name, cachedSigners);
    }
}
