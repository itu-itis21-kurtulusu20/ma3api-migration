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
import java.util.Enumeration;

import sune.security.util.DerValue;

/**
 * This represents the Issuer Alternative Name Extension.
 *
 * This extension, if present, allows the issuer to specify multiple
 * alternative names.
 *
 * <p>Extensions are represented as a sequence of the extension identifier
 * (Object Identifier), a boolean flag stating whether the extension is to
 * be treated as being critical and the extension value itself (this is again
 * a DER encoding of the extension value).
 *
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 * @see sune.security.x509.Extension
 * @see sune.security.x509.CertAttrSet
 */
public class IssuerAlternativeNameExtension
extends Extension implements CertAttrSet<String> {
    /**
     * Identifier for this attribute, to be used with the
     * get, set, delete methods of Certificate, x509 type.
     */
    public static final String IDENT =
                         "x509.info.extensions.IssuerAlternativeName";
    /**
     * Attribute names.
     */
    public static final String NAME = "IssuerAlternativeName";
    public static final String ISSUER_NAME = "issuer_name";

    // private data members
    sune.security.x509.GeneralNames names = null;

    // Encode this extension
    private void encodeThis() throws IOException {
        if (names == null || names.isEmpty()) {
            this.extensionValue = null;
            return;
        }
        sune.security.util.DerOutputStream os = new sune.security.util.DerOutputStream();
        names.encode(os);
        this.extensionValue = os.toByteArray();
    }

    /**
     * Create a IssuerAlternativeNameExtension with the passed GeneralNames.
     *
     * @param names the GeneralNames for the issuer.
     * @exception IOException on error.
     */
    public IssuerAlternativeNameExtension(sune.security.x509.GeneralNames names)
    throws IOException {
        this.names = names;
        this.extensionId = sune.security.x509.PKIXExtensions.IssuerAlternativeName_Id;
        this.critical = false;
        encodeThis();
    }

    /**
     * Create a default IssuerAlternativeNameExtension.
     */
    public IssuerAlternativeNameExtension() {
        extensionId = sune.security.x509.PKIXExtensions.IssuerAlternativeName_Id;
        critical = false;
        names = new sune.security.x509.GeneralNames();
    }

    /**
     * Create the extension from the passed DER encoded value.
     *
     * @param critical true if the extension is to be treated as critical.
     * @param value an array of DER encoded bytes of the actual value.
     * @exception ClassCastException if value is not an array of bytes
     * @exception IOException on error.
     */
    public IssuerAlternativeNameExtension(Boolean critical, Object value)
    throws IOException {
        this.extensionId = sune.security.x509.PKIXExtensions.IssuerAlternativeName_Id;
        this.critical = critical.booleanValue();
        this.extensionValue = (byte[]) value;
        sune.security.util.DerValue val = new DerValue(this.extensionValue);
        if (val.data == null) {
            names = new sune.security.x509.GeneralNames();
            return;
        }

        names = new sune.security.x509.GeneralNames(val);
    }

    /**
     * Returns a printable representation of the IssuerAlternativeName.
     */
    public String toString() {

        String result = super.toString() + "IssuerAlternativeName [\n";
        if(names == null) {
            result += "  null\n";
        } else {
            for(GeneralName name: names.names()) {
                result += "  "+name+"\n";
            }
        }
        result += "]\n";
        return result;
    }

    /**
     * Write the extension to the OutputStream.
     *
     * @param out the OutputStream to write the extension to.
     * @exception IOException on encoding error.
     */
    public void encode(OutputStream out) throws IOException {
        sune.security.util.DerOutputStream tmp = new sune.security.util.DerOutputStream();
        if (extensionValue == null) {
            extensionId = PKIXExtensions.IssuerAlternativeName_Id;
            critical = false;
            encodeThis();
        }
        super.encode(tmp);
        out.write(tmp.toByteArray());
    }

    /**
     * Set the attribute value.
     */
    public void set(String name, Object obj) throws IOException {
        if (name.equalsIgnoreCase(ISSUER_NAME)) {
            if (!(obj instanceof sune.security.x509.GeneralNames)) {
              throw new IOException("Attribute value should be of" +
                                    " type GeneralNames.");
            }
            names = (GeneralNames)obj;
        } else {
          throw new IOException("Attribute name not recognized by " +
                        "CertAttrSet:IssuerAlternativeName.");
        }
        encodeThis();
    }

    /**
     * Get the attribute value.
     */
    public Object get(String name) throws IOException {
        if (name.equalsIgnoreCase(ISSUER_NAME)) {
            return (names);
        } else {
          throw new IOException("Attribute name not recognized by " +
                        "CertAttrSet:IssuerAlternativeName.");
        }
    }

    /**
     * Delete the attribute value.
     */
    public void delete(String name) throws IOException {
        if (name.equalsIgnoreCase(ISSUER_NAME)) {
            names = null;
        } else {
          throw new IOException("Attribute name not recognized by " +
                        "CertAttrSet:IssuerAlternativeName.");
        }
        encodeThis();
    }

    /**
     * Return an enumeration of names of attributes existing within this
     * attribute.
     */
    public Enumeration<String> getElements() {
        sune.security.x509.AttributeNameEnumeration elements = new AttributeNameEnumeration();
        elements.addElement(ISSUER_NAME);

        return (elements.elements());
    }

    /**
     * Return the name of this attribute.
     */
    public String getName() {
        return (NAME);
    }
}
