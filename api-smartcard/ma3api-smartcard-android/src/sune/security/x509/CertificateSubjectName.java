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

import javax.security.auth.x500.X500Principal;

import sune.security.util.DerOutputStream;

/**
 * This class defines the X500Name attribute for the Certificate.
 *
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 * @see sune.security.x509.CertAttrSet
 */
public class CertificateSubjectName implements CertAttrSet<String> {
    /**
     * Identifier for this attribute, to be used with the
     * get, set, delete methods of Certificate, x509 type.
     */
    public static final String IDENT = "x509.info.subject";
    /**
     * Sub attributes name for this CertAttrSet.
     */
    public static final String NAME = "subject";
    public static final String DN_NAME = "dname";

    // accessor name for cached X500Principal only
    // do not allow a set() of this value, do not advertise with getElements()
    public static final String DN_PRINCIPAL = "x500principal";

    // Private data member
    private sune.security.x509.X500Name dnName;

    // cached X500Principal version of the name
    private X500Principal dnPrincipal;

    /**
     * Default constructor for the certificate attribute.
     *
     * @param name the X500Name
     */
    public CertificateSubjectName(sune.security.x509.X500Name name) {
        this.dnName = name;
    }

    /**
     * Create the object, decoding the values from the passed DER stream.
     *
     * @param in the DerInputStream to read the X500Name from.
     * @exception IOException on decoding errors.
     */
    public CertificateSubjectName(sune.security.util.DerInputStream in) throws IOException {
        dnName = new sune.security.x509.X500Name(in);
    }

    /**
     * Create the object, decoding the values from the passed stream.
     *
     * @param in the InputStream to read the X500Name from.
     * @exception IOException on decoding errors.
     */
    public CertificateSubjectName(InputStream in) throws IOException {
        sune.security.util.DerValue derVal = new sune.security.util.DerValue(in);
        dnName = new sune.security.x509.X500Name(derVal);
    }

    /**
     * Return the name as user readable string.
     */
    public String toString() {
        if (dnName == null) return "";
        return(dnName.toString());
    }

    /**
     * Encode the name in DER form to the stream.
     *
     * @param out the DerOutputStream to marshal the contents to.
     * @exception IOException on errors.
     */
    public void encode(OutputStream out) throws IOException {
        sune.security.util.DerOutputStream tmp = new DerOutputStream();
        dnName.encode(tmp);

        out.write(tmp.toByteArray());
    }

    /**
     * Set the attribute value.
     */
    public void set(String name, Object obj) throws IOException {
        if (!(obj instanceof sune.security.x509.X500Name)) {
            throw new IOException("Attribute must be of type X500Name.");
        }
        if (name.equalsIgnoreCase(DN_NAME)) {
            this.dnName = (X500Name)obj;
            this.dnPrincipal = null;
        } else {
            throw new IOException("Attribute name not recognized by " +
                                  "CertAttrSet:CertificateSubjectName.");
        }
    }

    /**
     * Get the attribute value.
     */
    public Object get(String name) throws IOException {
        if (name.equalsIgnoreCase(DN_NAME)) {
            return(dnName);
        } else if (name.equalsIgnoreCase(DN_PRINCIPAL)) {
            if ((dnPrincipal == null) && (dnName != null)) {
                dnPrincipal = dnName.asX500Principal();
            }
            return dnPrincipal;
        } else {
            throw new IOException("Attribute name not recognized by " +
                                  "CertAttrSet:CertificateSubjectName.");
        }
    }

    /**
     * Delete the attribute value.
     */
    public void delete(String name) throws IOException {
        if (name.equalsIgnoreCase(DN_NAME)) {
            dnName = null;
            dnPrincipal = null;
        } else {
            throw new IOException("Attribute name not recognized by " +
                                  "CertAttrSet:CertificateSubjectName.");
        }
    }

    /**
     * Return an enumeration of names of attributes existing within this
     * attribute.
     */
    public Enumeration<String> getElements() {
        sune.security.x509.AttributeNameEnumeration elements = new AttributeNameEnumeration();
        elements.addElement(DN_NAME);

        return(elements.elements());
    }

    /**
     * Return the name of this attribute.
     */
    public String getName() {
        return(NAME);
    }
}
