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

import sune.security.util.DerOutputStream;
import sune.security.util.DerValue;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * This class represents the Authority Key Identifier Extension.
 *
 * <p>The authority key identifier extension provides a means of
 * identifying the particular public key used to sign a certificate.
 * This extension would be used where an issuer has multiple signing
 * keys (either due to multiple concurrent key pairs or due to
 * changeover).
 * <p>
 * The ASN.1 syntax for this is:
 * <pre>
 * AuthorityKeyIdentifier ::= SEQUENCE {
 *    keyIdentifier             [0] KeyIdentifier           OPTIONAL,
 *    authorityCertIssuer       [1] GeneralNames            OPTIONAL,
 *    authorityCertSerialNumber [2] CertificateSerialNumber OPTIONAL
 * }
 * KeyIdentifier ::= OCTET STRING
 * </pre>
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 * @see sune.security.x509.Extension
 * @see sune.security.x509.CertAttrSet
 */
public class AuthorityKeyIdentifierExtension extends Extension
implements CertAttrSet<String> {
    /**
     * Identifier for this attribute, to be used with the
     * get, set, delete methods of Certificate, x509 type.
     */
    public static final String IDENT =
                         "x509.info.extensions.AuthorityKeyIdentifier";
    /**
     * Attribute names.
     */
    public static final String NAME = "AuthorityKeyIdentifier";
    public static final String KEY_ID = "key_id";
    public static final String AUTH_NAME = "auth_name";
    public static final String SERIAL_NUMBER = "serial_number";

    // Private data members
    private static final byte TAG_ID = 0;
    private static final byte TAG_NAMES = 1;
    private static final byte TAG_SERIAL_NUM = 2;

    private sune.security.x509.KeyIdentifier id = null;
    private sune.security.x509.GeneralNames names = null;
    private sune.security.x509.SerialNumber serialNum = null;

    // Encode only the extension value
    private void encodeThis() throws IOException {
        if (id == null && names == null && serialNum == null) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream seq = new DerOutputStream();
        DerOutputStream tmp = new DerOutputStream();
        if (id != null) {
            DerOutputStream tmp1 = new DerOutputStream();
            id.encode(tmp1);
            tmp.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT,
                              false, TAG_ID), tmp1);
        }
        try {
            if (names != null) {
                DerOutputStream tmp1 = new DerOutputStream();
                names.encode(tmp1);
                tmp.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT,
                                  true, TAG_NAMES), tmp1);
            }
        } catch (Exception e) {
            throw new IOException(e.toString(), e);
        }
        if (serialNum != null) {
            DerOutputStream tmp1 = new DerOutputStream();
            serialNum.encode(tmp1);
            tmp.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT,
                              false, TAG_SERIAL_NUM), tmp1);
        }
        seq.write(DerValue.tag_Sequence, tmp);
        this.extensionValue = seq.toByteArray();
    }

    /**
     * The default constructor for this extension.  Null parameters make
     * the element optional (not present).
     *
     * @param kid the KeyIdentifier associated with this extension.
     * @param name the GeneralNames associated with this extension
     * @param sn the CertificateSerialNumber associated with
     *         this extension.
     * @exception IOException on error.
     */
    public AuthorityKeyIdentifierExtension(sune.security.x509.KeyIdentifier kid, sune.security.x509.GeneralNames name,
                                           sune.security.x509.SerialNumber sn)
    throws IOException {
        this.id = kid;
        this.names = name;
        this.serialNum = sn;

        this.extensionId = sune.security.x509.PKIXExtensions.AuthorityKey_Id;
        this.critical = false;
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
    public AuthorityKeyIdentifierExtension(Boolean critical, Object value)
    throws IOException {
        this.extensionId = sune.security.x509.PKIXExtensions.AuthorityKey_Id;
        this.critical = critical.booleanValue();

        this.extensionValue = (byte[]) value;
        DerValue val = new DerValue(this.extensionValue);
        if (val.tag != DerValue.tag_Sequence) {
            throw new IOException("Invalid encoding for " +
                                  "AuthorityKeyIdentifierExtension.");
        }

        // Note that all the fields in AuthorityKeyIdentifier are defined as
        // being OPTIONAL, i.e., there could be an empty SEQUENCE, resulting
        // in val.data being null.
        while ((val.data != null) && (val.data.available() != 0)) {
            DerValue opt = val.data.getDerValue();

            // NB. this is always encoded with the IMPLICIT tag
            // The checks only make sense if we assume implicit tagging,
            // with explicit tagging the form is always constructed.
            if (opt.isContextSpecific(TAG_ID) && !opt.isConstructed()) {
                if (id != null)
                    throw new IOException("Duplicate KeyIdentifier in " +
                                          "AuthorityKeyIdentifier.");
                opt.resetTag(DerValue.tag_OctetString);
                id = new sune.security.x509.KeyIdentifier(opt);

            } else if (opt.isContextSpecific(TAG_NAMES) &&
                       opt.isConstructed()) {
                if (names != null)
                    throw new IOException("Duplicate GeneralNames in " +
                                          "AuthorityKeyIdentifier.");
                opt.resetTag(DerValue.tag_Sequence);
                names = new sune.security.x509.GeneralNames(opt);

            } else if (opt.isContextSpecific(TAG_SERIAL_NUM) &&
                       !opt.isConstructed()) {
                if (serialNum != null)
                    throw new IOException("Duplicate SerialNumber in " +
                                          "AuthorityKeyIdentifier.");
                opt.resetTag(DerValue.tag_Integer);
                serialNum = new sune.security.x509.SerialNumber(opt);
            } else
                throw new IOException("Invalid encoding of " +
                                      "AuthorityKeyIdentifierExtension.");
        }
    }

    /**
     * Return the object as a string.
     */
    public String toString() {
        String s = super.toString() + "AuthorityKeyIdentifier [\n";
        if (id != null) {
            s += id.toString() + "\n";
        }
        if (names != null) {
            s += names.toString() + "\n";
        }
        if (serialNum != null) {
            s += serialNum.toString() + "\n";
        }
        return (s + "]\n");
    }

    /**
     * Write the extension to the OutputStream.
     *
     * @param out the OutputStream to write the extension to.
     * @exception IOException on error.
     */
    public void encode(OutputStream out) throws IOException {
        DerOutputStream tmp = new DerOutputStream();
        if (this.extensionValue == null) {
            extensionId = PKIXExtensions.AuthorityKey_Id;
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
        if (name.equalsIgnoreCase(KEY_ID)) {
            if (!(obj instanceof sune.security.x509.KeyIdentifier)) {
              throw new IOException("Attribute value should be of " +
                                    "type KeyIdentifier.");
            }
            id = (KeyIdentifier)obj;
        } else if (name.equalsIgnoreCase(AUTH_NAME)) {
            if (!(obj instanceof sune.security.x509.GeneralNames)) {
              throw new IOException("Attribute value should be of " +
                                    "type GeneralNames.");
            }
            names = (GeneralNames)obj;
        } else if (name.equalsIgnoreCase(SERIAL_NUMBER)) {
            if (!(obj instanceof sune.security.x509.SerialNumber)) {
              throw new IOException("Attribute value should be of " +
                                    "type SerialNumber.");
            }
            serialNum = (SerialNumber)obj;
        } else {
          throw new IOException("Attribute name not recognized by " +
                        "CertAttrSet:AuthorityKeyIdentifier.");
        }
        encodeThis();
    }

    /**
     * Get the attribute value.
     */
    public Object get(String name) throws IOException {
        if (name.equalsIgnoreCase(KEY_ID)) {
            return (id);
        } else if (name.equalsIgnoreCase(AUTH_NAME)) {
            return (names);
        } else if (name.equalsIgnoreCase(SERIAL_NUMBER)) {
            return (serialNum);
        } else {
          throw new IOException("Attribute name not recognized by " +
                        "CertAttrSet:AuthorityKeyIdentifier.");
        }
    }

    /**
     * Delete the attribute value.
     */
    public void delete(String name) throws IOException {
        if (name.equalsIgnoreCase(KEY_ID)) {
            id = null;
        } else if (name.equalsIgnoreCase(AUTH_NAME)) {
            names = null;
        } else if (name.equalsIgnoreCase(SERIAL_NUMBER)) {
            serialNum = null;
        } else {
          throw new IOException("Attribute name not recognized by " +
                        "CertAttrSet:AuthorityKeyIdentifier.");
        }
        encodeThis();
    }

    /**
     * Return an enumeration of names of attributes existing within this
     * attribute.
     */
    public Enumeration<String> getElements() {
        sune.security.x509.AttributeNameEnumeration elements = new AttributeNameEnumeration();
        elements.addElement(KEY_ID);
        elements.addElement(AUTH_NAME);
        elements.addElement(SERIAL_NUMBER);

        return (elements.elements());
    }

    /**
     * Return the name of this attribute.
     */
    public String getName() {
        return (NAME);
    }
}
