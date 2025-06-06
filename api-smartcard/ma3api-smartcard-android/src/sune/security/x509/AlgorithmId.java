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
import sune.security.util.DerEncoder;
import sune.security.util.DerInputStream;
import sune.security.util.DerOutputStream;
import sune.security.util.DerValue;
import sune.security.util.ObjectIdentifier;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * This class identifies algorithms, such as cryptographic transforms, each
 * of which may be associated with parameters.  Instances of this base class
 * are used when this runtime environment has no special knowledge of the
 * algorithm type, and may also be used in other cases.  Equivalence is
 * defined according to OID and (where relevant) parameters.
 *
 * <P>Subclasses may be used, for example when when the algorithm ID has
 * associated parameters which some code (e.g. code using public keys) needs
 * to have parsed.  Two examples of such algorithms are Diffie-Hellman key
 * exchange, and the Digital Signature Standard Algorithm (DSS/DSA).
 *
 * <P>The OID constants defined in this class correspond to some widely
 * used algorithms, for which conventional string names have been defined.
 * This class is not a general repository for OIDs, or for such string names.
 * Note that the mappings between algorithm IDs and algorithm names is
 * not one-to-one.
 *
 *
 * @author David Brownell
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 */
public class AlgorithmId implements Serializable, DerEncoder {

    protected static Logger logger = LoggerFactory.getLogger(AlgorithmId.class);

    /** use serialVersionUID from JDK 1.1. for interoperability */
    private static final long serialVersionUID = 7205873507486557157L;

    /**
     * The object identitifer being used for this algorithm.
     */
    private ObjectIdentifier algid;

    // The (parsed) parameters
    private AlgorithmParameters algParams;
    private boolean constructedFromDer = true;

    /**
     * Parameters for this algorithm.  These are stored in unparsed
     * DER-encoded form; subclasses can be made to automaticaly parse
     * them so there is fast access to these parameters.
     */
    protected DerValue          params;


    /**
     * Constructs an algorithm ID which will be initialized
     * separately, for example by deserialization.
     * @deprecated use one of the other constructors.
     */
    @Deprecated
    public AlgorithmId() { }

    /**
     * Constructs a parameterless algorithm ID.
     *
     * @param oid the identifier for the algorithm
     */
    public AlgorithmId(ObjectIdentifier oid) {
        algid = oid;
    }

    /**
     * Constructs an algorithm ID with algorithm parameters.
     *
     * @param oid the identifier for the algorithm.
     * @param algparams the associated algorithm parameters.
     */
    public AlgorithmId(ObjectIdentifier oid, AlgorithmParameters algparams) {
        algid = oid;
        algParams = algparams;
        constructedFromDer = false;
    }

    private AlgorithmId(ObjectIdentifier oid, DerValue params)
            throws IOException {
        this.algid = oid;
        this.params = params;
        if (this.params != null) {
            decodeParams();
        }
    }

    protected void decodeParams() throws IOException {
        String algidString = algid.toString();
        try {
            algParams = AlgorithmParameters.getInstance(algidString);
        } catch (NoSuchAlgorithmException e) {
            logger.warn("Warning in AlgorithmId", e);
        	//**Orcun
        	algParams = null;
//            try {
//                // Try the internal EC code so that we can fully parse EC
//                // keys even if the provider is not registered.
//                // This code can go away once we have EC in the SUN provider.
//                algParams = AlgorithmParameters.getInstance(algidString,
//                                sun.security.ec.ECKeyFactory.ecInternalProvider);
//            } catch (NoSuchAlgorithmException ee) {
//                /*
//                 * This algorithm parameter type is not supported, so we cannot
//                 * parse the parameters.
//                 */
//                algParams = null;
//                return;
//            }
        }
        // Decode (parse) the parameters
        algParams.init(params.toByteArray());
    }

    /**
     * Marshal a DER-encoded "AlgorithmID" sequence on the DER stream.
     */
    public final void encode(DerOutputStream out) throws IOException {
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
    public void derEncode (OutputStream out) throws IOException {
        DerOutputStream bytes = new DerOutputStream();
        DerOutputStream tmp = new DerOutputStream();

        bytes.putOID(algid);
        // Setup params from algParams since no DER encoding is given
        if (constructedFromDer == false) {
            if (algParams != null) {
                params = new DerValue(algParams.getEncoded());
            } else {
                params = null;
            }
        }
        if (params == null) {
            // Changes backed out for compatibility with Solaris

            // Several AlgorithmId should omit the whole parameter part when
            // it's NULL. They are ---
            // rfc3370 2.1: Implementations SHOULD generate SHA-1
            // AlgorithmIdentifiers with absent parameters.
            // rfc3447 C1: When id-sha1, id-sha256, id-sha384 and id-sha512
            // are used in an AlgorithmIdentifier the parameters (which are
            // optional) SHOULD be omitted.
            // rfc3279 2.3.2: The id-dsa algorithm syntax includes optional
            // domain parameters... When omitted, the parameters component
            // MUST be omitted entirely
            // rfc3370 3.1: When the id-dsa-with-sha1 algorithm identifier
            // is used, the AlgorithmIdentifier parameters field MUST be absent.
            /*if (
                algid.equals((Object)SHA_oid) ||
                algid.equals((Object)SHA256_oid) ||
                algid.equals((Object)SHA384_oid) ||
                algid.equals((Object)SHA512_oid) ||
                algid.equals((Object)DSA_oid) ||
                algid.equals((Object)sha1WithDSA_oid)) {
                ; // no parameter part encoded
            } else {
                bytes.putNull();
            }*/
            bytes.putNull();
        } else {
            bytes.putDerValue(params);
        }
        tmp.write(DerValue.tag_Sequence, bytes);
        out.write(tmp.toByteArray());
    }


    /**
     * Returns the DER-encoded X.509 AlgorithmId as a byte array.
     */
    public final byte[] encode() throws IOException {
        DerOutputStream out = new DerOutputStream();
        derEncode(out);
        return out.toByteArray();
    }

    /**
     * Returns the ISO OID for this algorithm.  This is usually converted
     * to a string and used as part of an algorithm name, for example
     * "OID.1.3.14.3.2.13" style notation.  Use the <code>getName</code>
     * call when you do not need to ensure cross-system portability
     * of algorithm names, or need a user friendly name.
     */
    public final ObjectIdentifier getOID () {
        return algid;
    }

    /**
     * Returns a name for the algorithm which may be more intelligible
     * to humans than the algorithm's OID, but which won't necessarily
     * be comprehensible on other systems.  For example, this might
     * return a name such as "MD5withRSA" for a signature algorithm on
     * some systems.  It also returns names like "OID.1.2.3.4", when
     * no particular name for the algorithm is known.
     */
    public String getName() {
        String algName = nameTable.get(algid);
        if (algName != null) {
            return algName;
        }
        if ((params != null) && algid.equals(specifiedWithECDSA_oid)) {
            try {
                AlgorithmId paramsId =
                        AlgorithmId.parse(new DerValue(getEncodedParams()));
                String paramsName = paramsId.getName();
                if (paramsName.equals("SHA")) {
                    paramsName = "SHA1";
                }
                algName = paramsName + "withECDSA";
            } catch (IOException e) {
                // ignore
                logger.warn("Warning in AlgorithmId", e);
            }
        }
        return (algName == null) ? algid.toString() : algName;
    }

    public AlgorithmParameters getParameters() {
        return algParams;
    }

    /**
     * Returns the DER encoded parameter, which can then be
     * used to initialize java.security.AlgorithmParamters.
     *
     * @return DER encoded parameters, or null not present.
     */
    public byte[] getEncodedParams() throws IOException {
        return (params == null) ? null : params.toByteArray();
    }

    /**
     * Returns true iff the argument indicates the same algorithm
     * with the same parameters.
     */
    public boolean equals(AlgorithmId other) {
        boolean paramsEqual =
          (params == null ? other.params == null : params.equals(other.params));
        return (algid.equals(other.algid) && paramsEqual);
    }

    /**
     * Compares this AlgorithmID to another.  If algorithm parameters are
     * available, they are compared.  Otherwise, just the object IDs
     * for the algorithm are compared.
     *
     * @param other preferably an AlgorithmId, else an ObjectIdentifier
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof AlgorithmId) {
            return equals((AlgorithmId) other);
        } else if (other instanceof ObjectIdentifier) {
            return equals((ObjectIdentifier) other);
        } else {
            return false;
        }
    }

    /**
     * Compares two algorithm IDs for equality.  Returns true iff
     * they are the same algorithm, ignoring algorithm parameters.
     */
    public final boolean equals(ObjectIdentifier id) {
        return algid.equals(id);
    }

    /**
     * Returns a hashcode for this AlgorithmId.
     *
     * @return a hashcode for this AlgorithmId.
     */
    public int hashCode() {
        StringBuilder sbuf = new StringBuilder();
        sbuf.append(algid.toString());
        sbuf.append(paramsToString());
        return sbuf.toString().hashCode();
    }

    /**
     * Provides a human-readable description of the algorithm parameters.
     * This may be redefined by subclasses which parse those parameters.
     */
    protected String paramsToString() {
        if (params == null) {
            return "";
        } else if (algParams != null) {
            return algParams.toString();
        } else {
            return ", params unparsed";
        }
    }

    /**
     * Returns a string describing the algorithm and its parameters.
     */
    public String toString() {
        return getName() + paramsToString();
    }

    /**
     * Parse (unmarshal) an ID from a DER sequence input value.  This form
     * parsing might be used when expanding a value which has already been
     * partially unmarshaled as a set or sequence member.
     *
     * @exception IOException on error.
     * @param val the input value, which contains the algid and, if
     *          there are any parameters, those parameters.
     * @return an ID for the algorithm.  If the system is configured
     *          appropriately, this may be an instance of a class
     *          with some kind of special support for this algorithm.
     *          In that case, you may "narrow" the type of the ID.
     */
    public static AlgorithmId parse(DerValue val) throws IOException {
        if (val.tag != DerValue.tag_Sequence) {
            throw new IOException("algid parse error, not a sequence");
        }

        /*
         * Get the algorithm ID and any parameters.
         */
        ObjectIdentifier        algid;
        DerValue                params;
        DerInputStream          in = val.toDerInputStream();

        algid = in.getOID();
        if (in.available() == 0) {
            params = null;
        } else {
            params = in.getDerValue();
            if (params.tag == DerValue.tag_Null) {
                if (params.length() != 0) {
                    throw new IOException("invalid NULL");
                }
                params = null;
            }
            if (in.available() != 0) {
                throw new IOException("Invalid AlgorithmIdentifier: extra data");
            }
        }

        return new AlgorithmId(algid, params);
    }

    /**
     * Returns one of the algorithm IDs most commonly associated
     * with this algorithm name.
     *
     * @param algname the name being used
     * @deprecated use the short get form of this method.
     * @exception NoSuchAlgorithmException on error.
     */
    @Deprecated
    public static AlgorithmId getAlgorithmId(String algname)
            throws NoSuchAlgorithmException {
        return get(algname);
    }

    /**
     * Returns one of the algorithm IDs most commonly associated
     * with this algorithm name.
     *
     * @param algname the name being used
     * @exception NoSuchAlgorithmException on error.
     */
    public static AlgorithmId get(String algname)
            throws NoSuchAlgorithmException {
        ObjectIdentifier oid;
        try {
            oid = algOID(algname);
        } catch (IOException e) {
            throw new NoSuchAlgorithmException("Invalid ObjectIdentifier " + algname, e);
        }

        if (oid == null) {
            throw new NoSuchAlgorithmException
                ("unrecognized algorithm name: " + algname);
        }
        return new AlgorithmId(oid);
    }

    /**
     * Returns one of the algorithm IDs most commonly associated
     * with this algorithm parameters.
     *
     * @param algparams the associated algorithm parameters.
     * @exception NoSuchAlgorithmException on error.
     */
    public static AlgorithmId get(AlgorithmParameters algparams)
            throws NoSuchAlgorithmException {
        ObjectIdentifier oid;
        String algname = algparams.getAlgorithm();
        try {
            oid = algOID(algname);
        } catch (IOException e) {
            throw new NoSuchAlgorithmException("Invalid ObjectIdentifier " + algname, e);
        }
        if (oid == null) {
            throw new NoSuchAlgorithmException
                ("unrecognized algorithm name: " + algname);
        }
        return new AlgorithmId(oid, algparams);
    }

    /*
     * Translates from some common algorithm names to the
     * OID with which they're usually associated ... this mapping
     * is the reverse of the one below, except in those cases
     * where synonyms are supported or where a given algorithm
     * is commonly associated with multiple OIDs.
     *
     * XXX This method needs to be enhanced so that we can also pass the
     * scope of the algorithm name to it, e.g., the algorithm name "DSA"
     * may have a different OID when used as a "Signature" algorithm than when
     * used as a "KeyPairGenerator" algorithm.
     */
    private static ObjectIdentifier algOID(String name) throws IOException {
        // See if algname is in printable OID ("dot-dot") notation
        if (name.indexOf('.') != -1) {
            if (name.startsWith("OID.")) {
                return new ObjectIdentifier(name.substring("OID.".length()));
            } else {
                return new ObjectIdentifier(name);
            }
        }

        // Digesting algorithms
        if (name.equalsIgnoreCase("MD5")) {
            return AlgorithmId.MD5_oid;
        }
        if (name.equalsIgnoreCase("MD2")) {
            return AlgorithmId.MD2_oid;
        }
        if (name.equalsIgnoreCase("SHA") || name.equalsIgnoreCase("SHA1")
            || name.equalsIgnoreCase("SHA-1")) {
            return AlgorithmId.SHA_oid;
        }
        if (name.equalsIgnoreCase("SHA-256") ||
            name.equalsIgnoreCase("SHA256")) {
            return AlgorithmId.SHA256_oid;
        }
        if (name.equalsIgnoreCase("SHA-384") ||
            name.equalsIgnoreCase("SHA384")) {
            return AlgorithmId.SHA384_oid;
        }
        if (name.equalsIgnoreCase("SHA-512") ||
            name.equalsIgnoreCase("SHA512")) {
            return AlgorithmId.SHA512_oid;
        }


        // Various public key algorithms
        if (name.equalsIgnoreCase("RSA")) {
            return AlgorithmId.RSAEncryption_oid;
        }
        if (name.equalsIgnoreCase("Diffie-Hellman")
            || name.equalsIgnoreCase("DH")) {
            return AlgorithmId.DH_oid;
        }
        if (name.equalsIgnoreCase("DSA")) {
            return AlgorithmId.DSA_oid;
        }
        if (name.equalsIgnoreCase("EC")) {
            return EC_oid;
        }

        // Common signature types
        if (name.equalsIgnoreCase("MD5withRSA")
            || name.equalsIgnoreCase("MD5/RSA")) {
            return AlgorithmId.md5WithRSAEncryption_oid;
        }
        if (name.equalsIgnoreCase("MD2withRSA")
            || name.equalsIgnoreCase("MD2/RSA")) {
            return AlgorithmId.md2WithRSAEncryption_oid;
        }
        if (name.equalsIgnoreCase("SHAwithDSA")
            || name.equalsIgnoreCase("SHA1withDSA")
            || name.equalsIgnoreCase("SHA/DSA")
            || name.equalsIgnoreCase("SHA1/DSA")
            || name.equalsIgnoreCase("DSAWithSHA1")
            || name.equalsIgnoreCase("DSS")
            || name.equalsIgnoreCase("SHA-1/DSA")) {
            return AlgorithmId.sha1WithDSA_oid;
        }
        if (name.equalsIgnoreCase("SHA1WithRSA")
            || name.equalsIgnoreCase("SHA1/RSA")) {
            return AlgorithmId.sha1WithRSAEncryption_oid;
        }
        if (name.equalsIgnoreCase("SHA1withECDSA")
                || name.equalsIgnoreCase("ECDSA")) {
            return AlgorithmId.sha1WithECDSA_oid;
        }

        // See if any of the installed providers supply a mapping from
        // the given algorithm name to an OID string
        String oidString;
        if (!initOidTable) {
            Provider[] provs = Security.getProviders();
            for (int i=0; i<provs.length; i++) {
                for (Enumeration<Object> enum_ = provs[i].keys();
                     enum_.hasMoreElements(); ) {
                    String alias = (String)enum_.nextElement();
                    int index;
                    if (alias.toUpperCase().startsWith("ALG.ALIAS") &&
                        (index=alias.toUpperCase().indexOf("OID.", 0)) != -1) {
                        index += "OID.".length();
                        if (index == alias.length()) {
                            // invalid alias entry
                            break;
                        }
                        if (oidTable == null) {
                            oidTable = new HashMap<String,ObjectIdentifier>();
                        }
                        oidString = alias.substring(index);
                        String stdAlgName
                            = provs[i].getProperty(alias).toUpperCase();
                        if (oidTable.get(stdAlgName) == null) {
                            oidTable.put(stdAlgName,
                                         new ObjectIdentifier(oidString));
                        }
                    }
                }
            }
            initOidTable = true;
        }

        return oidTable.get(name.toUpperCase());
    }

    private static ObjectIdentifier oid(int ... values) {
        return ObjectIdentifier.newInternal(values);
    }

    private static boolean initOidTable = false;
    private static Map<String,ObjectIdentifier> oidTable;
    private static final Map<ObjectIdentifier,String> nameTable;

    /*****************************************************************/

    /*
     * HASHING ALGORITHMS
     */

    /**
     * Algorithm ID for the MD2 Message Digest Algorthm, from RFC 1319.
     * OID = 1.2.840.113549.2.2
     */
    public static final ObjectIdentifier MD2_oid =
    ObjectIdentifier.newInternal(new int[] {1, 2, 840, 113549, 2, 2});

    /**
     * Algorithm ID for the MD5 Message Digest Algorthm, from RFC 1321.
     * OID = 1.2.840.113549.2.5
     */
    public static final ObjectIdentifier MD5_oid =
    ObjectIdentifier.newInternal(new int[] {1, 2, 840, 113549, 2, 5});

    /**
     * Algorithm ID for the SHA1 Message Digest Algorithm, from FIPS 180-1.
     * This is sometimes called "SHA", though that is often confusing since
     * many people refer to FIPS 180 (which has an error) as defining SHA.
     * OID = 1.3.14.3.2.26. Old SHA-0 OID: 1.3.14.3.2.18.
     */
    public static final ObjectIdentifier SHA_oid =
    ObjectIdentifier.newInternal(new int[] {1, 3, 14, 3, 2, 26});

    public static final ObjectIdentifier SHA256_oid =
    ObjectIdentifier.newInternal(new int[] {2, 16, 840, 1, 101, 3, 4, 2, 1});

    public static final ObjectIdentifier SHA384_oid =
    ObjectIdentifier.newInternal(new int[] {2, 16, 840, 1, 101, 3, 4, 2, 2});

    public static final ObjectIdentifier SHA512_oid =
    ObjectIdentifier.newInternal(new int[] {2, 16, 840, 1, 101, 3, 4, 2, 3});

    /*
     * COMMON PUBLIC KEY TYPES
     */
    private static final int DH_data[] = { 1, 2, 840, 113549, 1, 3, 1 };
    private static final int DH_PKIX_data[] = { 1, 2, 840, 10046, 2, 1 };
    private static final int DSA_OIW_data[] = { 1, 3, 14, 3, 2, 12 };
    private static final int DSA_PKIX_data[] = { 1, 2, 840, 10040, 4, 1 };
    private static final int RSA_data[] = { 1, 2, 5, 8, 1, 1 };
    private static final int RSAEncryption_data[] =
                                 { 1, 2, 840, 113549, 1, 1, 1 };

    public static final ObjectIdentifier DH_oid;
    public static final ObjectIdentifier DH_PKIX_oid;
    public static final ObjectIdentifier DSA_oid;
    public static final ObjectIdentifier DSA_OIW_oid;
    public static final ObjectIdentifier EC_oid = oid(1, 2, 840, 10045, 2, 1);
    public static final ObjectIdentifier RSA_oid;
    public static final ObjectIdentifier RSAEncryption_oid;

    /*
     * COMMON SIGNATURE ALGORITHMS
     */
    private static final int md2WithRSAEncryption_data[] =
                                       { 1, 2, 840, 113549, 1, 1, 2 };
    private static final int md5WithRSAEncryption_data[] =
                                       { 1, 2, 840, 113549, 1, 1, 4 };
    private static final int sha1WithRSAEncryption_data[] =
                                       { 1, 2, 840, 113549, 1, 1, 5 };
    private static final int sha1WithRSAEncryption_OIW_data[] =
                                       { 1, 3, 14, 3, 2, 29 };
    private static final int sha256WithRSAEncryption_data[] =
                                       { 1, 2, 840, 113549, 1, 1, 11 };
    private static final int sha384WithRSAEncryption_data[] =
                                       { 1, 2, 840, 113549, 1, 1, 12 };
    private static final int sha512WithRSAEncryption_data[] =
                                       { 1, 2, 840, 113549, 1, 1, 13 };
    private static final int shaWithDSA_OIW_data[] =
                                       { 1, 3, 14, 3, 2, 13 };
    private static final int sha1WithDSA_OIW_data[] =
                                       { 1, 3, 14, 3, 2, 27 };
    private static final int dsaWithSHA1_PKIX_data[] =
                                       { 1, 2, 840, 10040, 4, 3 };

    public static final ObjectIdentifier md2WithRSAEncryption_oid;
    public static final ObjectIdentifier md5WithRSAEncryption_oid;
    public static final ObjectIdentifier sha1WithRSAEncryption_oid;
    public static final ObjectIdentifier sha1WithRSAEncryption_OIW_oid;
    public static final ObjectIdentifier sha256WithRSAEncryption_oid;
    public static final ObjectIdentifier sha384WithRSAEncryption_oid;
    public static final ObjectIdentifier sha512WithRSAEncryption_oid;
    public static final ObjectIdentifier shaWithDSA_OIW_oid;
    public static final ObjectIdentifier sha1WithDSA_OIW_oid;
    public static final ObjectIdentifier sha1WithDSA_oid;

    public static final ObjectIdentifier sha1WithECDSA_oid =
                                            oid(1, 2, 840, 10045, 4, 1);
    public static final ObjectIdentifier sha224WithECDSA_oid =
                                            oid(1, 2, 840, 10045, 4, 3, 1);
    public static final ObjectIdentifier sha256WithECDSA_oid =
                                            oid(1, 2, 840, 10045, 4, 3, 2);
    public static final ObjectIdentifier sha384WithECDSA_oid =
                                            oid(1, 2, 840, 10045, 4, 3, 3);
    public static final ObjectIdentifier sha512WithECDSA_oid =
                                            oid(1, 2, 840, 10045, 4, 3, 4);
    public static final ObjectIdentifier specifiedWithECDSA_oid =
                                            oid(1, 2, 840, 10045, 4, 3);

    /**
     * Algorithm ID for the PBE encryption algorithms from PKCS#5 and
     * PKCS#12.
     */
    public static final ObjectIdentifier pbeWithMD5AndDES_oid =
        ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 1, 5, 3});
    public static final ObjectIdentifier pbeWithMD5AndRC2_oid =
        ObjectIdentifier.newInternal(new int[] {1, 2, 840, 113549, 1, 5, 6});
    public static final ObjectIdentifier pbeWithSHA1AndDES_oid =
        ObjectIdentifier.newInternal(new int[] {1, 2, 840, 113549, 1, 5, 10});
    public static final ObjectIdentifier pbeWithSHA1AndRC2_oid =
        ObjectIdentifier.newInternal(new int[] {1, 2, 840, 113549, 1, 5, 11});
    public static ObjectIdentifier pbeWithSHA1AndDESede_oid =
        ObjectIdentifier.newInternal(new int[] {1, 2, 840, 113549, 1, 12, 1, 3});
    public static ObjectIdentifier pbeWithSHA1AndRC2_40_oid =
        ObjectIdentifier.newInternal(new int[] {1, 2, 840, 113549, 1, 12, 1, 6});


    static {
    /*
     * Note the preferred OIDs are named simply with no "OIW" or
     * "PKIX" in them, even though they may point to data from these
     * specs; e.g. SHA_oid, DH_oid, DSA_oid, SHA1WithDSA_oid...
     */
    /**
     * Algorithm ID for Diffie Hellman Key agreement, from PKCS #3.
     * Parameters include public values P and G, and may optionally specify
     * the length of the private key X.  Alternatively, algorithm parameters
     * may be derived from another source such as a Certificate Authority's
     * certificate.
     * OID = 1.2.840.113549.1.3.1
     */
        DH_oid = ObjectIdentifier.newInternal(DH_data);

    /**
     * Algorithm ID for the Diffie Hellman Key Agreement (DH), from RFC 3279.
     * Parameters may include public values P and G.
     * OID = 1.2.840.10046.2.1
     */
        DH_PKIX_oid = ObjectIdentifier.newInternal(DH_PKIX_data);

    /**
     * Algorithm ID for the Digital Signing Algorithm (DSA), from the
     * NIST OIW Stable Agreements part 12.
     * Parameters may include public values P, Q, and G; or these may be
     * derived from
     * another source such as a Certificate Authority's certificate.
     * OID = 1.3.14.3.2.12
     */
        DSA_OIW_oid = ObjectIdentifier.newInternal(DSA_OIW_data);

    /**
     * Algorithm ID for the Digital Signing Algorithm (DSA), from RFC 3279.
     * Parameters may include public values P, Q, and G; or these may be
     * derived from another source such as a Certificate Authority's
     * certificate.
     * OID = 1.2.840.10040.4.1
     */
        DSA_oid = ObjectIdentifier.newInternal(DSA_PKIX_data);

    /**
     * Algorithm ID for RSA keys used for any purpose, as defined in X.509.
     * The algorithm parameter is a single value, the number of bits in the
     * public modulus.
     * OID = 1.2.5.8.1.1
     */
        RSA_oid = ObjectIdentifier.newInternal(RSA_data);

    /**
     * Algorithm ID for RSA keys used with RSA encryption, as defined
     * in PKCS #1.  There are no parameters associated with this algorithm.
     * OID = 1.2.840.113549.1.1.1
     */
        RSAEncryption_oid = ObjectIdentifier.newInternal(RSAEncryption_data);

    /**
     * Identifies a signing algorithm where an MD2 digest is encrypted
     * using an RSA private key; defined in PKCS #1.  Use of this
     * signing algorithm is discouraged due to MD2 vulnerabilities.
     * OID = 1.2.840.113549.1.1.2
     */
        md2WithRSAEncryption_oid =
            ObjectIdentifier.newInternal(md2WithRSAEncryption_data);

    /**
     * Identifies a signing algorithm where an MD5 digest is
     * encrypted using an RSA private key; defined in PKCS #1.
     * OID = 1.2.840.113549.1.1.4
     */
        md5WithRSAEncryption_oid =
            ObjectIdentifier.newInternal(md5WithRSAEncryption_data);

    /**
     * Identifies a signing algorithm where a SHA1 digest is
     * encrypted using an RSA private key; defined by RSA DSI.
     * OID = 1.2.840.113549.1.1.5
     */
        sha1WithRSAEncryption_oid =
            ObjectIdentifier.newInternal(sha1WithRSAEncryption_data);

    /**
     * Identifies a signing algorithm where a SHA1 digest is
     * encrypted using an RSA private key; defined in NIST OIW.
     * OID = 1.3.14.3.2.29
     */
        sha1WithRSAEncryption_OIW_oid =
            ObjectIdentifier.newInternal(sha1WithRSAEncryption_OIW_data);

    /**
     * Identifies a signing algorithm where a SHA256 digest is
     * encrypted using an RSA private key; defined by PKCS #1.
     * OID = 1.2.840.113549.1.1.11
     */
        sha256WithRSAEncryption_oid =
            ObjectIdentifier.newInternal(sha256WithRSAEncryption_data);

    /**
     * Identifies a signing algorithm where a SHA384 digest is
     * encrypted using an RSA private key; defined by PKCS #1.
     * OID = 1.2.840.113549.1.1.12
     */
        sha384WithRSAEncryption_oid =
            ObjectIdentifier.newInternal(sha384WithRSAEncryption_data);

    /**
     * Identifies a signing algorithm where a SHA512 digest is
     * encrypted using an RSA private key; defined by PKCS #1.
     * OID = 1.2.840.113549.1.1.13
     */
        sha512WithRSAEncryption_oid =
            ObjectIdentifier.newInternal(sha512WithRSAEncryption_data);

    /**
     * Identifies the FIPS 186 "Digital Signature Standard" (DSS), where a
     * SHA digest is signed using the Digital Signing Algorithm (DSA).
     * This should not be used.
     * OID = 1.3.14.3.2.13
     */
        shaWithDSA_OIW_oid = ObjectIdentifier.newInternal(shaWithDSA_OIW_data);

    /**
     * Identifies the FIPS 186 "Digital Signature Standard" (DSS), where a
     * SHA1 digest is signed using the Digital Signing Algorithm (DSA).
     * OID = 1.3.14.3.2.27
     */
        sha1WithDSA_OIW_oid = ObjectIdentifier.newInternal(sha1WithDSA_OIW_data);

    /**
     * Identifies the FIPS 186 "Digital Signature Standard" (DSS), where a
     * SHA1 digest is signed using the Digital Signing Algorithm (DSA).
     * OID = 1.2.840.10040.4.3
     */
        sha1WithDSA_oid = ObjectIdentifier.newInternal(dsaWithSHA1_PKIX_data);

        nameTable = new HashMap<ObjectIdentifier,String>();
        nameTable.put(MD5_oid, "MD5");
        nameTable.put(MD2_oid, "MD2");
        nameTable.put(SHA_oid, "SHA");
        nameTable.put(SHA256_oid, "SHA256");
        nameTable.put(SHA384_oid, "SHA384");
        nameTable.put(SHA512_oid, "SHA512");
        nameTable.put(RSAEncryption_oid, "RSA");
        nameTable.put(RSA_oid, "RSA");
        nameTable.put(DH_oid, "Diffie-Hellman");
        nameTable.put(DH_PKIX_oid, "Diffie-Hellman");
        nameTable.put(DSA_oid, "DSA");
        nameTable.put(DSA_OIW_oid, "DSA");
        nameTable.put(EC_oid, "EC");
        nameTable.put(sha1WithECDSA_oid, "SHA1withECDSA");
        nameTable.put(sha224WithECDSA_oid, "SHA224withECDSA");
        nameTable.put(sha256WithECDSA_oid, "SHA256withECDSA");
        nameTable.put(sha384WithECDSA_oid, "SHA384withECDSA");
        nameTable.put(sha512WithECDSA_oid, "SHA512withECDSA");
        nameTable.put(md5WithRSAEncryption_oid, "MD5withRSA");
        nameTable.put(md2WithRSAEncryption_oid, "MD2withRSA");
        nameTable.put(sha1WithDSA_oid, "SHA1withDSA");
        nameTable.put(sha1WithDSA_OIW_oid, "SHA1withDSA");
        nameTable.put(shaWithDSA_OIW_oid, "SHA1withDSA");
        nameTable.put(sha1WithRSAEncryption_oid, "SHA1withRSA");
        nameTable.put(sha1WithRSAEncryption_OIW_oid, "SHA1withRSA");
        nameTable.put(sha256WithRSAEncryption_oid, "SHA256withRSA");
        nameTable.put(sha384WithRSAEncryption_oid, "SHA384withRSA");
        nameTable.put(sha512WithRSAEncryption_oid, "SHA512withRSA");
        nameTable.put(pbeWithMD5AndDES_oid, "PBEWithMD5AndDES");
        nameTable.put(pbeWithMD5AndRC2_oid, "PBEWithMD5AndRC2");
        nameTable.put(pbeWithSHA1AndDES_oid, "PBEWithSHA1AndDES");
        nameTable.put(pbeWithSHA1AndRC2_oid, "PBEWithSHA1AndRC2");
        nameTable.put(pbeWithSHA1AndDESede_oid, "PBEWithSHA1AndDESede");
        nameTable.put(pbeWithSHA1AndRC2_40_oid, "PBEWithSHA1AndRC2_40");
    }
}
