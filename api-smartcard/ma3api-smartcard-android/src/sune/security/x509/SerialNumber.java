/*
 * Copyright 1997-2002 Sun Microsystems, Inc.  All Rights Reserved.
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
import java.io.InputStream;
import java.math.BigInteger;

import sune.security.util.DerValue;

/**
 * This class defines the SerialNumber class used by certificates.
 *
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 */
public class SerialNumber {
    private BigInteger  serialNum;

    // Construct the class from the DerValue
    private void construct(sune.security.util.DerValue derVal) throws IOException {
        serialNum = derVal.getBigInteger();
        if (derVal.data.available() != 0) {
            throw new IOException("Excess SerialNumber data");
        }
    }

    /**
     * The default constructor for this class using BigInteger.
     *
     * @param num the BigInteger number used to create the serial number.
     */
    public SerialNumber(BigInteger num) {
        serialNum = num;
    }

    /**
     * The default constructor for this class using int.
     *
     * @param num the BigInteger number used to create the serial number.
     */
    public SerialNumber(int num) {
        serialNum = BigInteger.valueOf(num);
    }

    /**
     * Create the object, decoding the values from the passed DER stream.
     *
     * @param in the DerInputStream to read the SerialNumber from.
     * @exception IOException on decoding errors.
     */
    public SerialNumber(sune.security.util.DerInputStream in) throws IOException {
        DerValue derVal = in.getDerValue();
        construct(derVal);
    }

    /**
     * Create the object, decoding the values from the passed DerValue.
     *
     * @param val the DerValue to read the SerialNumber from.
     * @exception IOException on decoding errors.
     */
    public SerialNumber(sune.security.util.DerValue val) throws IOException {
        construct(val);
    }

    /**
     * Create the object, decoding the values from the passed stream.
     *
     * @param in the InputStream to read the SerialNumber from.
     * @exception IOException on decoding errors.
     */
    public SerialNumber(InputStream in) throws IOException {
        sune.security.util.DerValue derVal = new sune.security.util.DerValue(in);
        construct(derVal);
    }

    /**
     * Return the SerialNumber as user readable string.
     */
    public String toString() {
        return ("SerialNumber: [" + sune.security.util.Debug.toHexString(serialNum) + "]");
    }

    /**
     * Encode the SerialNumber in DER form to the stream.
     *
     * @param out the DerOutputStream to marshal the contents to.
     * @exception IOException on errors.
     */
    public void encode(sune.security.util.DerOutputStream out) throws IOException {
        out.putInteger(serialNum);
    }

    /**
     * Return the serial number.
     */
    public BigInteger getNumber() {
        return serialNum;
    }
}
