/*
 * Copyright 1997-2004 Sun Microsystems, Inc.  All Rights Reserved.
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
import sune.security.util.DerOutputStream;
import sune.security.util.DerValue;

import java.io.IOException;

/**
 * This class implements the ASN.1 GeneralName object class.
 * <p>
 * The ASN.1 syntax for this is:
 * <pre>
 * GeneralName ::= CHOICE {
 *    otherName                       [0]     OtherName,
 *    rfc822Name                      [1]     IA5String,
 *    dNSName                         [2]     IA5String,
 *    x400Address                     [3]     ORAddress,
 *    directoryName                   [4]     Name,
 *    ediPartyName                    [5]     EDIPartyName,
 *    uniformResourceIdentifier       [6]     IA5String,
 *    iPAddress                       [7]     OCTET STRING,
 *    registeredID                    [8]     OBJECT IDENTIFIER
 * }
 * </pre>
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 */
public class GeneralName {

    protected static Logger logger = LoggerFactory.getLogger(GeneralName.class);

    // Private data members
    private sune.security.x509.GeneralNameInterface name = null;

    /**
     * Default constructor for the class.
     *
     * @param name the selected CHOICE from the list.
     * @throws NullPointerException if name is null
     */
    public GeneralName(sune.security.x509.GeneralNameInterface name) {
        if (name == null) {
            throw new NullPointerException("GeneralName must not be null");
        }
        this.name = name;
    }

    /**
     * Create the object from its DER encoded value.
     *
     * @param encName the DER encoded GeneralName.
     */
    public GeneralName(DerValue encName) throws IOException {
        this(encName, false);
    }

    /**
     * Create the object from its DER encoded value.
     *
     * @param encName the DER encoded GeneralName.
     * @param nameConstraint true if general name is a name constraint
     */
    public GeneralName(DerValue encName, boolean nameConstraint)
        throws IOException {
        short tag = (byte)(encName.tag & 0x1f);

        // All names except for NAME_DIRECTORY should be encoded with the
        // IMPLICIT tag.
        switch (tag) {
        case sune.security.x509.GeneralNameInterface.NAME_ANY:
            if (encName.isContextSpecific() && encName.isConstructed()) {
                encName.resetTag(DerValue.tag_Sequence);
                name = new OtherName(encName);
            } else {
                throw new IOException("Invalid encoding of Other-Name");
            }
            break;

        case sune.security.x509.GeneralNameInterface.NAME_RFC822:
            if (encName.isContextSpecific() && !encName.isConstructed()) {
                encName.resetTag(DerValue.tag_IA5String);
                name = new RFC822Name(encName);
            } else {
                throw new IOException("Invalid encoding of RFC822 name");
            }
            break;

        case sune.security.x509.GeneralNameInterface.NAME_DNS:
            if (encName.isContextSpecific() && !encName.isConstructed()) {
                encName.resetTag(DerValue.tag_IA5String);
                name = new DNSName(encName);
            } else {
                throw new IOException("Invalid encoding of DNS name");
            }
            break;

        case sune.security.x509.GeneralNameInterface.NAME_URI:
            if (encName.isContextSpecific() && !encName.isConstructed()) {
                encName.resetTag(DerValue.tag_IA5String);
                name = (nameConstraint ? sune.security.x509.URIName.nameConstraint(encName) :
                        new URIName(encName));
            } else {
                throw new IOException("Invalid encoding of URI");
            }
            break;

        case sune.security.x509.GeneralNameInterface.NAME_IP:
            if (encName.isContextSpecific() && !encName.isConstructed()) {
                encName.resetTag(DerValue.tag_OctetString);
                name = new IPAddressName(encName);
            } else {
                throw new IOException("Invalid encoding of IP address");
            }
            break;

        case sune.security.x509.GeneralNameInterface.NAME_OID:
            if (encName.isContextSpecific() && !encName.isConstructed()) {
                encName.resetTag(DerValue.tag_ObjectId);
                name = new OIDName(encName);
            } else {
                throw new IOException("Invalid encoding of OID name");
            }
            break;

        case sune.security.x509.GeneralNameInterface.NAME_DIRECTORY:
            if (encName.isContextSpecific() && encName.isConstructed()) {
                name = new X500Name(encName.getData());
            } else {
                throw new IOException("Invalid encoding of Directory name");
            }
            break;

        case sune.security.x509.GeneralNameInterface.NAME_EDI:
            if (encName.isContextSpecific() && encName.isConstructed()) {
                encName.resetTag(DerValue.tag_Sequence);
                name = new EDIPartyName(encName);
            } else {
                throw new IOException("Invalid encoding of EDI name");
            }
            break;

        default:
            throw new IOException("Unrecognized GeneralName tag, ("
                                  + tag +")");
        }
    }

    /**
     * Return the type of the general name.
     */
    public int getType() {
        return name.getType();
    }

    /**
     * Return the GeneralNameInterface name.
     */
    public sune.security.x509.GeneralNameInterface getName() {
        //XXXX May want to consider cloning this
        return name;
    }

    /**
     * Return the name as user readable string
     */
    public String toString() {
        return name.toString();
    }

    /**
     * Compare this GeneralName with another
     *
     * @param other GeneralName to compare to this
     * @return true if match
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GeneralName))
            return false;
        sune.security.x509.GeneralNameInterface otherGNI = ((GeneralName)other).name;
        try {
            return name.constrains(otherGNI) == sune.security.x509.GeneralNameInterface.NAME_MATCH;
        } catch (UnsupportedOperationException e) {
            logger.warn("Warning in GeneralName", e);
            return false;
        }
    }

    /**
     * Returns the hash code for this GeneralName.
     *
     * @return a hash code value.
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Encode the name to the specified DerOutputStream.
     *
     * @param out the DerOutputStream to encode the the GeneralName to.
     * @exception IOException on encoding errors.
     */
    public void encode(DerOutputStream out) throws IOException {
        DerOutputStream tmp = new DerOutputStream();
        name.encode(tmp);
        int nameType = name.getType();
        if (nameType == sune.security.x509.GeneralNameInterface.NAME_ANY ||
            nameType == sune.security.x509.GeneralNameInterface.NAME_X400 ||
            nameType == sune.security.x509.GeneralNameInterface.NAME_EDI) {

            // implicit, constructed form
            out.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT,
                              true, (byte)nameType), tmp);
        } else if (nameType == GeneralNameInterface.NAME_DIRECTORY) {
            // explicit, constructed form since underlying tag is CHOICE
            // (see X.680 section 30.6, part c)
            out.write(DerValue.createTag(DerValue.TAG_CONTEXT,
                                         true, (byte)nameType), tmp);
        } else {
            // implicit, primitive form
            out.writeImplicit(DerValue.createTag(DerValue.TAG_CONTEXT,
                              false, (byte)nameType), tmp);
        }
    }
}
