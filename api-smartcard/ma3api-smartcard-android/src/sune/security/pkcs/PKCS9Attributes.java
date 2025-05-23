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

package sune.security.pkcs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import sune.security.util.DerEncoder;
import sune.security.util.DerValue;
import sune.security.util.DerInputStream;
import sune.security.util.DerOutputStream;
import sune.security.util.ObjectIdentifier;

/**
 * A set of attributes of class PKCS9Attribute.
 *
 * @author Douglas Hoover
 */
public class PKCS9Attributes {
    /**
     * Attributes in this set indexed by OID.
     */
    private final Hashtable<ObjectIdentifier, sune.security.pkcs.PKCS9Attribute> attributes =
        new Hashtable<ObjectIdentifier, sune.security.pkcs.PKCS9Attribute>(3);

    /**
     * The keys of this hashtable are the OIDs of permitted attributes.
     */
    private final Hashtable<ObjectIdentifier, ObjectIdentifier> permittedAttributes;

    /**
     * The DER encoding of this attribute set.  The tag byte must be
     * DerValue.tag_SetOf.
     */
    private final byte[] derEncoding;

    /*
     * Contols how attributes, which are not recognized by the PKCS9Attribute
     * class, are handled during parsing.
     */
    private boolean ignoreUnsupportedAttributes = false;

    /**
     * Construct a set of PKCS9 Attributes from its
     * DER encoding on a DerInputStream, accepting only attributes
     * with OIDs on the given
     * list.  If the array is null, accept all attributes supported by
     * class PKCS9Attribute.
     *
     * @param permittedAttributes
     * Array of attribute OIDs that will be accepted.
     * @param in
     * the contents of the DER encoding of the attribute set.
     *
     * @exception IOException
     * on i/o error, encoding syntax error, unacceptable or
     * unsupported attribute, or duplicate attribute.
     *
     * @see sune.security.pkcs.PKCS9Attribute
     */
    public PKCS9Attributes(ObjectIdentifier[] permittedAttributes,
                           DerInputStream in) throws IOException {
        if (permittedAttributes != null) {
            this.permittedAttributes =
                new Hashtable<ObjectIdentifier, ObjectIdentifier>(
                                                permittedAttributes.length);

            for (int i = 0; i < permittedAttributes.length; i++)
                this.permittedAttributes.put(permittedAttributes[i],
                                             permittedAttributes[i]);
        } else {
            this.permittedAttributes = null;
        }

        // derEncoding initialized in <code>decode()</code>
        derEncoding = decode(in);
    }

    /**
     * Construct a set of PKCS9 Attributes from the contents of its
     * DER encoding on a DerInputStream.  Accept all attributes
     * supported by class PKCS9Attribute and reject any unsupported
     * attributes.
     *
     * @param in the contents of the DER encoding of the attribute set.
     * @exception IOException
     * on i/o error, encoding syntax error, or unsupported or
     * duplicate attribute.
     *
     * @see sune.security.pkcs.PKCS9Attribute
     */
    public PKCS9Attributes(DerInputStream in) throws IOException {
        this(in, false);
    }

    /**
     * Construct a set of PKCS9 Attributes from the contents of its
     * DER encoding on a DerInputStream.  Accept all attributes
     * supported by class PKCS9Attribute and ignore any unsupported
     * attributes, if directed.
     *
     * @param in the contents of the DER encoding of the attribute set.
     * @param ignoreUnsupportedAttributes If true then any attributes
     * not supported by the PKCS9Attribute class are ignored. Otherwise
     * unsupported attributes cause an exception to be thrown.
     * @exception IOException
     * on i/o error, encoding syntax error, or unsupported or
     * duplicate attribute.
     *
     * @see sune.security.pkcs.PKCS9Attribute
     */
    public PKCS9Attributes(DerInputStream in,
        boolean ignoreUnsupportedAttributes) throws IOException {

        this.ignoreUnsupportedAttributes = ignoreUnsupportedAttributes;
        // derEncoding initialized in <code>decode()</code>
        derEncoding = decode(in);
        permittedAttributes = null;
    }

    /**
     * Construct a set of PKCS9 Attributes from the given array of
     * PKCS9 attributes.
     * DER encoding on a DerInputStream.  All attributes in
     * <code>attribs</code> must be
     * supported by class PKCS9Attribute.
     *
     * @exception IOException
     * on i/o error, encoding syntax error, or unsupported or
     * duplicate attribute.
     *
     * @see sune.security.pkcs.PKCS9Attribute
     */
    public PKCS9Attributes(sune.security.pkcs.PKCS9Attribute[] attribs)
    throws IllegalArgumentException, IOException {
        ObjectIdentifier oid;
        for (int i=0; i < attribs.length; i++) {
            oid = attribs[i].getOID();
            if (attributes.containsKey(oid))
                throw new IllegalArgumentException(
                          "PKCSAttribute " + attribs[i].getOID() +
                          " duplicated while constructing " +
                          "PKCS9Attributes.");

            attributes.put(oid, attribs[i]);
        }
        derEncoding = generateDerEncoding();
        permittedAttributes = null;
    }


    /**
     * Decode this set of PKCS9 attributes from the contents of its
     * DER encoding. Ignores unsupported attributes when directed.
     *
     * @param in
     * the contents of the DER encoding of the attribute set.
     *
     * @exception IOException
     * on i/o error, encoding syntax error, unacceptable or
     * unsupported attribute, or duplicate attribute.
     */
    private byte[] decode(DerInputStream in) throws IOException {

        DerValue val = in.getDerValue();

        // save the DER encoding with its proper tag byte.
        byte[] derEncoding = val.toByteArray();
        derEncoding[0] = DerValue.tag_SetOf;

        DerInputStream derIn = new DerInputStream(derEncoding);
        DerValue[] derVals = derIn.getSet(3,true);

        sune.security.pkcs.PKCS9Attribute attrib;
        ObjectIdentifier oid;
        boolean reuseEncoding = true;

        for (int i=0; i < derVals.length; i++) {

            try {
                attrib = new sune.security.pkcs.PKCS9Attribute(derVals[i]);

            } catch (ParsingException e) {
                if (ignoreUnsupportedAttributes) {
                    reuseEncoding = false; // cannot reuse supplied DER encoding
                    continue; // skip
                } else {
                    throw e;
                }
            }
            oid = attrib.getOID();

            if (attributes.get(oid) != null)
                throw new IOException("Duplicate PKCS9 attribute: " + oid);

            if (permittedAttributes != null &&
                !permittedAttributes.containsKey(oid))
                throw new IOException("Attribute " + oid +
                                      " not permitted in this attribute set");

            attributes.put(oid, attrib);
        }
        return reuseEncoding ? derEncoding : generateDerEncoding();
    }

    /**
     * Put the DER encoding of this PKCS9 attribute set on an
     * DerOutputStream, tagged with the given implicit tag.
     *
     * @param tag the implicit tag to use in the DER encoding.
     * @param out the output stream on which to put the DER encoding.
     *
     * @exception IOException  on output error.
     */
    public void encode(byte tag, OutputStream out) throws IOException {
        out.write(tag);
        out.write(derEncoding, 1, derEncoding.length -1);
    }

    private byte[] generateDerEncoding() throws IOException {
        DerOutputStream out = new DerOutputStream();
        Object[] attribVals = attributes.values().toArray();

        out.putOrderedSetOf(DerValue.tag_SetOf,
                            castToDerEncoder(attribVals));
        return out.toByteArray();
    }

    /**
     * Return the DER encoding of this attribute set, tagged with
     * DerValue.tag_SetOf.
     */
    public byte[] getDerEncoding() throws IOException {
        return derEncoding.clone();

    }

    /**
     * Get an attribute from this set.
     */
    public sune.security.pkcs.PKCS9Attribute getAttribute(ObjectIdentifier oid) {
        return attributes.get(oid);
    }

    /**
     * Get an attribute from this set.
     */
    public sune.security.pkcs.PKCS9Attribute getAttribute(String name) {
        return attributes.get(sune.security.pkcs.PKCS9Attribute.getOID(name));
    }


    /**
     * Get an array of all attributes in this set, in order of OID.
     */
    public sune.security.pkcs.PKCS9Attribute[] getAttributes() {
        sune.security.pkcs.PKCS9Attribute[] attribs = new sune.security.pkcs.PKCS9Attribute[attributes.size()];
        ObjectIdentifier oid;

        int j = 0;
        for (int i=1; i < sune.security.pkcs.PKCS9Attribute.PKCS9_OIDS.length &&
                      j < attribs.length; i++) {
            attribs[j] = getAttribute(sune.security.pkcs.PKCS9Attribute.PKCS9_OIDS[i]);

            if (attribs[j] != null)
                j++;
        }
        return attribs;
    }

    /**
     * Get an attribute value by OID.
     */
    public Object getAttributeValue(ObjectIdentifier oid)
    throws IOException {
        try {
            Object value = getAttribute(oid).getValue();
            return value;
        } catch (NullPointerException ex) {
            throw new IOException("No value found for attribute " + oid);
        }

    }

    /**
     *  Get an attribute value by type name.
     */
    public Object getAttributeValue(String name) throws IOException {
        ObjectIdentifier oid = sune.security.pkcs.PKCS9Attribute.getOID(name);

        if (oid == null)
            throw new IOException("Attribute name " + name +
                                  " not recognized or not supported.");

        return getAttributeValue(oid);
    }


    /**
     * Returns the PKCS9 block in a printable string form.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer(200);
        buf.append("PKCS9 Attributes: [\n\t");

        ObjectIdentifier oid;
        sune.security.pkcs.PKCS9Attribute value;

        boolean first = true;
        for (int i = 1; i < sune.security.pkcs.PKCS9Attribute.PKCS9_OIDS.length; i++) {
            value = getAttribute(PKCS9Attribute.PKCS9_OIDS[i]);

            if (value == null) continue;

            // we have a value; print it
            if (first)
                first = false;
            else
                buf.append(";\n\t");

            buf.append(value.toString());
        }

        buf.append("\n\t] (end PKCS9 Attributes)");

        return buf.toString();
    }

    /**
     * Cast an object array whose components are
     * <code>DerEncoder</code>s to <code>DerEncoder[]</code>.
     */
    static DerEncoder[] castToDerEncoder(Object[] objs) {

        DerEncoder[] encoders = new DerEncoder[objs.length];

        for (int i=0; i < encoders.length; i++)
            encoders[i] = (DerEncoder) objs[i];

        return encoders;
    }
}
