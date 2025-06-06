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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import sune.security.util.DerInputStream;

/**
 * This class defines the AlgorithmId for the Certificate.
 *
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 */
public class CertificateAlgorithmId implements CertAttrSet<String> {
    private sune.security.x509.AlgorithmId algId;

    /**
     * Identifier for this attribute, to be used with the
     * get, set, delete methods of Certificate, x509 type.
     */
    public static final String IDENT = "x509.info.algorithmID";
    /**
     * Sub attributes name for this CertAttrSet.
     */
    public static final String NAME = "algorithmID";

    /**
     * Identifier to be used with get, set, and delete methods. When
     * using this identifier the associated object being passed in or
     * returned is an instance of AlgorithmId.
     * @see sune.security.x509.AlgorithmId
     */
    public static final String ALGORITHM = "algorithm";

    /**
     * Default constructor for the certificate attribute.
     *
     * @param algId the Algorithm identifier
     */
    public CertificateAlgorithmId(sune.security.x509.AlgorithmId algId) {
        this.algId = algId;
    }

    /**
     * Create the object, decoding the values from the passed DER stream.
     *
     * @param in the DerInputStream to read the serial number from.
     * @exception IOException on decoding errors.
     */
    public CertificateAlgorithmId(DerInputStream in) throws IOException {
        sune.security.util.DerValue val = in.getDerValue();
        algId = sune.security.x509.AlgorithmId.parse(val);
    }

    /**
     * Create the object, decoding the values from the passed stream.
     *
     * @param in the InputStream to read the serial number from.
     * @exception IOException on decoding errors.
     */
    public CertificateAlgorithmId(InputStream in) throws IOException {
        sune.security.util.DerValue val = new sune.security.util.DerValue(in);
        algId = sune.security.x509.AlgorithmId.parse(val);
    }

    /**
     * Return the algorithm identifier as user readable string.
     */
    public String toString() {
        if (algId == null) return "";
        return (algId.toString() +
                ", OID = " + (algId.getOID()).toString() + "\n");
    }

    /**
     * Encode the algorithm identifier in DER form to the stream.
     *
     * @param out the DerOutputStream to marshal the contents to.
     * @exception IOException on errors.
     */
    public void encode(OutputStream out) throws IOException {
        sune.security.util.DerOutputStream tmp = new sune.security.util.DerOutputStream();
        algId.encode(tmp);

        out.write(tmp.toByteArray());
    }

    /**
     * Set the attribute value.
     */
    public void set(String name, Object obj) throws IOException {
        if (!(obj instanceof sune.security.x509.AlgorithmId)) {
            throw new IOException("Attribute must be of type AlgorithmId.");
        }
        if (name.equalsIgnoreCase(ALGORITHM)) {
            algId = (AlgorithmId)obj;
        } else {
            throw new IOException("Attribute name not recognized by " +
                              "CertAttrSet:CertificateAlgorithmId.");
        }
    }

    /**
     * Get the attribute value.
     */
    public Object get(String name) throws IOException {
        if (name.equalsIgnoreCase(ALGORITHM)) {
            return (algId);
        } else {
            throw new IOException("Attribute name not recognized by " +
                               "CertAttrSet:CertificateAlgorithmId.");
        }
    }

    /**
     * Delete the attribute value.
     */
    public void delete(String name) throws IOException {
        if (name.equalsIgnoreCase(ALGORITHM)) {
            algId = null;
        } else {
            throw new IOException("Attribute name not recognized by " +
                               "CertAttrSet:CertificateAlgorithmId.");
        }
    }

    /**
     * Return an enumeration of names of attributes existing within this
     * attribute.
     */
    public Enumeration<String> getElements() {
        sune.security.x509.AttributeNameEnumeration elements = new AttributeNameEnumeration();
        elements.addElement(ALGORITHM);
        return (elements.elements());
    }

   /**
    * Return the name of this attribute.
    */
   public String getName() {
      return (NAME);
   }
}
