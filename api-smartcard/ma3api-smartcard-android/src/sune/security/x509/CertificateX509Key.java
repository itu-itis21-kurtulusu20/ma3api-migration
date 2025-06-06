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

import java.security.PublicKey;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import sune.security.util.DerOutputStream;

/**
 * This class defines the X509Key attribute for the Certificate.
 *
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 * @see sune.security.x509.CertAttrSet
 */
public class CertificateX509Key implements CertAttrSet<String> {
    /**
     * Identifier for this attribute, to be used with the
     * get, set, delete methods of Certificate, x509 type.
     */
    public static final String IDENT = "x509.info.key";
    /**
     * Sub attributes name for this CertAttrSet.
     */
    public static final String NAME = "key";
    public static final String KEY = "value";

    // Private data member
    private PublicKey key;

    /**
     * Default constructor for the certificate attribute.
     *
     * @param key the X509Key
     */
    public CertificateX509Key(PublicKey key) {
        this.key = key;
    }

    /**
     * Create the object, decoding the values from the passed DER stream.
     *
     * @param in the DerInputStream to read the X509Key from.
     * @exception IOException on decoding errors.
     */
    public CertificateX509Key(sune.security.util.DerInputStream in) throws IOException {
        sune.security.util.DerValue val = in.getDerValue();
        key = sune.security.x509.X509Key.parse(val);
    }

    /**
     * Create the object, decoding the values from the passed stream.
     *
     * @param in the InputStream to read the X509Key from.
     * @exception IOException on decoding errors.
     */
    public CertificateX509Key(InputStream in) throws IOException {
        sune.security.util.DerValue val = new sune.security.util.DerValue(in);
        key = X509Key.parse(val);
    }

    /**
     * Return the key as printable string.
     */
    public String toString() {
        if (key == null) return "";
        return(key.toString());
    }

    /**
     * Encode the key in DER form to the stream.
     *
     * @param out the OutputStream to marshal the contents to.
     * @exception IOException on errors.
     */
    public void encode(OutputStream out) throws IOException {
        DerOutputStream tmp = new sune.security.util.DerOutputStream();
        tmp.write(key.getEncoded());

        out.write(tmp.toByteArray());
    }

    /**
     * Set the attribute value.
     */
    public void set(String name, Object obj) throws IOException {
        if (name.equalsIgnoreCase(KEY)) {
            this.key = (PublicKey)obj;
        } else {
            throw new IOException("Attribute name not recognized by " +
                                  "CertAttrSet: CertificateX509Key.");
        }
    }

    /**
     * Get the attribute value.
     */
    public Object get(String name) throws IOException {
        if (name.equalsIgnoreCase(KEY)) {
            return(key);
        } else {
            throw new IOException("Attribute name not recognized by " +
                                  "CertAttrSet: CertificateX509Key.");
        }
    }

    /**
     * Delete the attribute value.
     */
    public void delete(String name) throws IOException {
      if (name.equalsIgnoreCase(KEY)) {
        key = null;
      } else {
            throw new IOException("Attribute name not recognized by " +
                                  "CertAttrSet: CertificateX509Key.");
      }
    }

    /**
     * Return an enumeration of names of attributes existing within this
     * attribute.
     */
    public Enumeration<String> getElements() {
        sune.security.x509.AttributeNameEnumeration elements = new AttributeNameEnumeration();
        elements.addElement(KEY);

        return(elements.elements());
    }

    /**
     * Return the name of this attribute.
     */
    public String getName() {
        return(NAME);
    }
}
