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
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Enumeration;

import sune.security.util.DerOutputStream;

/**
 * Represent the CRL Number Extension.
 *
 * <p>This extension, if present, conveys a monotonically increasing
 * sequence number for each CRL issued by a given CA through a specific
 * CA X.500 Directory entry or CRL distribution point. This extension
 * allows users to easily determine when a particular CRL supersedes
 * another CRL.
 *
 * @author Hemma Prafullchandra
 * @see sune.security.x509.Extension
 * @see sune.security.x509.CertAttrSet
 */
public class CRLNumberExtension extends Extension
implements CertAttrSet<String> {

    /**
     * Attribute name.
     */
    public static final String NAME = "CRLNumber";
    public static final String NUMBER = "value";

    private static final String LABEL = "CRL Number";

    private BigInteger crlNumber = null;
    private String extensionName;
    private String extensionLabel;

    // Encode this extension value
    private void encodeThis() throws IOException {
        if (crlNumber == null) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream os = new sune.security.util.DerOutputStream();
        os.putInteger(this.crlNumber);
        this.extensionValue = os.toByteArray();
    }

    /**
     * Create a CRLNumberExtension with the integer value .
     * The criticality is set to false.
     *
     * @param crlNum the value to be set for the extension.
     */
    public CRLNumberExtension(int crlNum) throws IOException {
        this(sune.security.x509.PKIXExtensions.CRLNumber_Id, false, BigInteger.valueOf(crlNum),
        NAME, LABEL);
    }

    /**
     * Create a CRLNumberExtension with the BigInteger value .
     * The criticality is set to false.
     *
     * @param crlNum the value to be set for the extension.
     */
    public CRLNumberExtension(BigInteger crlNum) throws IOException {
        this(sune.security.x509.PKIXExtensions.CRLNumber_Id, false, crlNum, NAME, LABEL);
    }

    /**
     * Creates the extension (also called by the subclass).
     */
    protected CRLNumberExtension(sune.security.util.ObjectIdentifier extensionId,
                                 boolean isCritical, BigInteger crlNum, String extensionName,
                                 String extensionLabel) throws IOException {

        this.extensionId = extensionId;
        this.critical = isCritical;
        this.crlNumber = crlNum;
        this.extensionName = extensionName;
        this.extensionLabel = extensionLabel;
        encodeThis();
    }

    /**
     * Create the extension from the passed DER encoded value of the same.
     *
     * @param critical true if the extension is to be treated as critical.
     * @param value an array of DER encoded bytes of the actual value.
     * @exception ClassCastException if value is not an array of bytes
     * @exception IOException on error.
     */
    public CRLNumberExtension(Boolean critical, Object value)
    throws IOException {
        this(sune.security.x509.PKIXExtensions.CRLNumber_Id, critical, value, NAME, LABEL);
    }

    /**
     * Creates the extension (also called by the subclass).
     */
    protected CRLNumberExtension(sune.security.util.ObjectIdentifier extensionId,
                                 Boolean critical, Object value, String extensionName,
                                 String extensionLabel) throws IOException {

        this.extensionId = extensionId;
        this.critical = critical.booleanValue();
        this.extensionValue = (byte[]) value;
        sune.security.util.DerValue val = new sune.security.util.DerValue(this.extensionValue);
        this.crlNumber = val.getBigInteger();
        this.extensionName = extensionName;
        this.extensionLabel = extensionLabel;
    }

    /**
     * Set the attribute value.
     */
    public void set(String name, Object obj) throws IOException {
        if (name.equalsIgnoreCase(NUMBER)) {
            if (!(obj instanceof BigInteger)) {
                throw new IOException("Attribute must be of type BigInteger.");
            }
            crlNumber = (BigInteger)obj;
        } else {
          throw new IOException("Attribute name not recognized by"
                                + " CertAttrSet:" + extensionName + ".");
        }
        encodeThis();
    }

    /**
     * Get the attribute value.
     */
    public Object get(String name) throws IOException {
        if (name.equalsIgnoreCase(NUMBER)) {
            if (crlNumber == null) return null;
            else return crlNumber;
        } else {
          throw new IOException("Attribute name not recognized by"
                                + " CertAttrSet:" + extensionName + ".");
        }
    }

    /**
     * Delete the attribute value.
     */
    public void delete(String name) throws IOException {
        if (name.equalsIgnoreCase(NUMBER)) {
            crlNumber = null;
        } else {
          throw new IOException("Attribute name not recognized by"
                                + " CertAttrSet:" + extensionName + ".");
        }
        encodeThis();
    }

    /**
     * Returns a printable representation of the CRLNumberExtension.
     */
    public String toString() {
        String s = super.toString() + extensionLabel + ": " +
                   ((crlNumber == null) ? "" : sune.security.util.Debug.toHexString(crlNumber))
                   + "\n";
        return (s);
    }

    /**
     * Write the extension to the DerOutputStream.
     *
     * @param out the DerOutputStream to write the extension to.
     * @exception IOException on encoding errors.
     */
    public void encode(OutputStream out) throws IOException {
       sune.security.util.DerOutputStream tmp = new sune.security.util.DerOutputStream();
        encode(out, PKIXExtensions.CRLNumber_Id, true);
    }

    /**
     * Write the extension to the DerOutputStream.
     * (Also called by the subclass)
     */
    protected void encode(OutputStream out, sune.security.util.ObjectIdentifier extensionId,
        boolean isCritical) throws IOException {

       sune.security.util.DerOutputStream tmp = new sune.security.util.DerOutputStream();

       if (this.extensionValue == null) {
           this.extensionId = extensionId;
           this.critical = isCritical;
           encodeThis();
       }
       super.encode(tmp);
       out.write(tmp.toByteArray());
    }

    /**
     * Return an enumeration of names of attributes existing within this
     * attribute.
     */
    public Enumeration<String> getElements() {
        sune.security.x509.AttributeNameEnumeration elements = new AttributeNameEnumeration();
        elements.addElement(NUMBER);
        return (elements.elements());
    }

    /**
     * Return the name of this attribute.
     */
    public String getName() {
        return (extensionName);
    }
}
