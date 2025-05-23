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

import java.io.IOException;
import java.util.Enumeration;

import sune.security.util.BitArray;

/**
 * Represent the CRL Reason Flags.
 *
 * <p>This extension, if present, defines the identifies
 * the reason for the certificate revocation.
 * <p>The ASN.1 syntax for this is:
 * <pre>
 * ReasonFlags ::= BIT STRING {
 *    unused                  (0),
 *    keyCompromise           (1),
 *    cACompromise            (2),
 *    affiliationChanged      (3),
 *    superseded              (4),
 *    cessationOfOperation    (5),
 *    certificateHold         (6),
 *    privilegeWithdrawn      (7),
 *    aACompromise            (8) }
 * </pre>
 *
 * @author Hemma Prafullchandra
 */
public class ReasonFlags {

    /**
     * Reasons
     */
    public static final String UNUSED = "unused";
    public static final String KEY_COMPROMISE = "key_compromise";
    public static final String CA_COMPROMISE = "ca_compromise";
    public static final String AFFILIATION_CHANGED = "affiliation_changed";
    public static final String SUPERSEDED = "superseded";
    public static final String CESSATION_OF_OPERATION
                                   = "cessation_of_operation";
    public static final String CERTIFICATE_HOLD = "certificate_hold";
    public static final String PRIVILEGE_WITHDRAWN = "privilege_withdrawn";
    public static final String AA_COMPROMISE = "aa_compromise";

    private final static String[] NAMES = {
        UNUSED,
        KEY_COMPROMISE,
        CA_COMPROMISE,
        AFFILIATION_CHANGED,
        SUPERSEDED,
        CESSATION_OF_OPERATION,
        CERTIFICATE_HOLD,
        PRIVILEGE_WITHDRAWN,
        AA_COMPROMISE,
    };

    private static int name2Index(String name) throws IOException {
        for( int i=0; i<NAMES.length; i++ ) {
            if( NAMES[i].equalsIgnoreCase(name) ) {
                return i;
            }
        }
        throw new IOException("Name not recognized by ReasonFlags");
    }

    // Private data members
    private boolean[] bitString;

    /**
     * Check if bit is set.
     *
     * @param position the position in the bit string to check.
     */
    private boolean isSet(int position) {
        return bitString[position];
    }

    /**
     * Set the bit at the specified position.
     */
    private void set(int position, boolean val) {
        // enlarge bitString if necessary
        if (position >= bitString.length) {
            boolean[] tmp = new boolean[position+1];
            System.arraycopy(bitString, 0, tmp, 0, bitString.length);
            bitString = tmp;
        }
        bitString[position] = val;
    }

    /**
     * Create a ReasonFlags with the passed bit settings.
     *
     * @param reasons the bits to be set for the ReasonFlags.
     */
    public ReasonFlags(byte[] reasons) {
        bitString = new sune.security.util.BitArray(reasons.length*8, reasons).toBooleanArray();
    }

    /**
     * Create a ReasonFlags with the passed bit settings.
     *
     * @param reasons the bits to be set for the ReasonFlags.
     */
    public ReasonFlags(boolean[] reasons) {
        this.bitString = reasons;
    }

    /**
     * Create a ReasonFlags with the passed bit settings.
     *
     * @param reasons the bits to be set for the ReasonFlags.
     */
    public ReasonFlags(sune.security.util.BitArray reasons) {
        this.bitString = reasons.toBooleanArray();
    }

    /**
     * Create the object from the passed DER encoded value.
     *
     * @param in the DerInputStream to read the ReasonFlags from.
     * @exception IOException on decoding errors.
     */
    public ReasonFlags(sune.security.util.DerInputStream in) throws IOException {
        sune.security.util.DerValue derVal = in.getDerValue();
        this.bitString = derVal.getUnalignedBitString(true).toBooleanArray();
    }

    /**
     * Create the object from the passed DER encoded value.
     *
     * @param derVal the DerValue decoded from the stream.
     * @exception IOException on decoding errors.
     */
    public ReasonFlags(sune.security.util.DerValue derVal) throws IOException {
        this.bitString = derVal.getUnalignedBitString(true).toBooleanArray();
    }

    /**
     * Returns the reason flags as a boolean array.
     */
    public boolean[] getFlags() {
        return bitString;
    }

    /**
     * Set the attribute value.
     */
    public void set(String name, Object obj) throws IOException {
        if (!(obj instanceof Boolean)) {
            throw new IOException("Attribute must be of type Boolean.");
        }
        boolean val = ((Boolean)obj).booleanValue();
        set(name2Index(name), val);
    }

    /**
     * Get the attribute value.
     */
    public Object get(String name) throws IOException {
        return Boolean.valueOf(isSet(name2Index(name)));
    }

    /**
     * Delete the attribute value.
     */
    public void delete(String name) throws IOException {
        set(name, Boolean.FALSE);
    }

    /**
     * Returns a printable representation of the ReasonFlags.
     */
    public String toString() {
        String s = "Reason Flags [\n";

        try {
            if (isSet(0)) s += "  Unused\n";
            if (isSet(1)) s += "  Key Compromise\n";
            if (isSet(2)) s += "  CA Compromise\n";
            if (isSet(3)) s += "  Affiliation_Changed\n";
            if (isSet(4)) s += "  Superseded\n";
            if (isSet(5)) s += "  Cessation Of Operation\n";
            if (isSet(6)) s += "  Certificate Hold\n";
            if (isSet(7)) s += "  Privilege Withdrawn\n";
            if (isSet(8)) s += "  AA Compromise\n";
        } catch (ArrayIndexOutOfBoundsException ex) {}

        s += "]\n";

        return (s);
    }

    /**
     * Write the extension to the DerOutputStream.
     *
     * @param out the DerOutputStream to write the extension to.
     * @exception IOException on encoding errors.
     */
    public void encode(sune.security.util.DerOutputStream out) throws IOException {
        out.putTruncatedUnalignedBitString(new BitArray(this.bitString));
    }

    /**
     * Return an enumeration of names of attributes existing within this
     * attribute.
     */
    public Enumeration<String> getElements () {
        sune.security.x509.AttributeNameEnumeration elements = new AttributeNameEnumeration();
        for( int i=0; i<NAMES.length; i++ ) {
            elements.addElement(NAMES[i]);
        }
        return (elements.elements());
    }
}
