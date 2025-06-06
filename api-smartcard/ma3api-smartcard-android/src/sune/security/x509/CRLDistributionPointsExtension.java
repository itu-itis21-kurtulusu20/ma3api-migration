/*
 * Copyright 2002-2006 Sun Microsystems, Inc.  All Rights Reserved.
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

import java.util.*;

import sune.security.util.DerOutputStream;
import sune.security.util.DerValue;
import sune.security.util.ObjectIdentifier;

/**
 * Represent the CRL Distribution Points Extension (OID = 2.5.29.31).
 * <p>
 * The CRL distribution points extension identifies how CRL information
 * is obtained.  The extension SHOULD be non-critical, but the PKIX profile
 * recommends support for this extension by CAs and applications.
 * <p>
 * For PKIX, if the cRLDistributionPoints extension contains a
 * DistributionPointName of type URI, the following semantics MUST be
 * assumed: the URI is a pointer to the current CRL for the associated
 * reasons and will be issued by the associated cRLIssuer.  The
 * expected values for the URI conform to the following rules.  The
 * name MUST be a non-relative URL, and MUST follow the URL syntax and
 * encoding rules specified in [RFC 1738].  The name must include both
 * a scheme (e.g., "http" or "ftp") and a scheme-specific-part.  The
 * scheme- specific-part must include a fully qualified domain name or
 * IP address as the host.  As specified in [RFC 1738], the scheme
 * name is not case-sensitive (e.g., "http" is equivalent to "HTTP").
 * The host part is also not case-sensitive, but other components of
 * the scheme-specific-part may be case-sensitive. When comparing
 * URIs, conforming implementations MUST compare the scheme and host
 * without regard to case, but assume the remainder of the
 * scheme-specific-part is case sensitive.  Processing rules for other
 * values are not defined by this specification.  If the
 * distributionPoint omits reasons, the CRL MUST include revocations
 * for all reasons. If the distributionPoint omits cRLIssuer, the CRL
 * MUST be issued by the CA that issued the certificate.
 * <p>
 * The ASN.1 definition for this is:
 * <pre>
 * id-ce-cRLDistributionPoints OBJECT IDENTIFIER ::=  { id-ce 31 }
 *
 * cRLDistributionPoints ::= {
 *      CRLDistPointsSyntax }
 *
 * CRLDistPointsSyntax ::= SEQUENCE SIZE (1..MAX) OF DistributionPoint
 * </pre>
 * <p>
 * @author Anne Anderson
 * @author Andreas Sterbenz
 * @since 1.4.2
 * @see sune.security.x509.DistributionPoint
 * @see sune.security.x509.Extension
 * @see sune.security.x509.CertAttrSet
 */
public class CRLDistributionPointsExtension extends Extension
        implements CertAttrSet<String> {

    /**
     * Identifier for this attribute, to be used with the
     * get, set, delete methods of Certificate, x509 type.
     */
    public static final String IDENT =
                                "x509.info.extensions.CRLDistributionPoints";

    /**
     * Attribute name.
     */
    public static final String NAME = "CRLDistributionPoints";
    public static final String POINTS = "points";

    /**
     * The List of DistributionPoint objects.
     */
    private List<sune.security.x509.DistributionPoint> distributionPoints;

    private String extensionName;

    /**
     * Create a CRLDistributionPointsExtension from a List of
     * DistributionPoint; the criticality is set to false.
     *
     * @param distributionPoints the list of distribution points
     * @throws IOException on error
     */
    public CRLDistributionPointsExtension(
        List<sune.security.x509.DistributionPoint> distributionPoints) throws IOException {

        this(false, distributionPoints);
    }

    /**
     * Create a CRLDistributionPointsExtension from a List of
     * DistributionPoint.
     *
     * @param isCritical the criticality setting.
     * @param distributionPoints the list of distribution points
     * @throws IOException on error
     */
    public CRLDistributionPointsExtension(boolean isCritical,
        List<sune.security.x509.DistributionPoint> distributionPoints) throws IOException {

        this(sune.security.x509.PKIXExtensions.CRLDistributionPoints_Id, isCritical,
            distributionPoints, NAME);
    }

    /**
     * Creates the extension (also called by the subclass).
     */
    protected CRLDistributionPointsExtension(ObjectIdentifier extensionId,
        boolean isCritical, List<sune.security.x509.DistributionPoint> distributionPoints,
            String extensionName) throws IOException {

        this.extensionId = extensionId;
        this.critical = isCritical;
        this.distributionPoints = distributionPoints;
        encodeThis();
        this.extensionName = extensionName;
    }

    /**
     * Create the extension from the passed DER encoded value of the same.
     *
     * @param critical true if the extension is to be treated as critical.
     * @param value Array of DER encoded bytes of the actual value.
     * @exception IOException on error.
     */
    public CRLDistributionPointsExtension(Boolean critical, Object value)
            throws IOException {
        this(sune.security.x509.PKIXExtensions.CRLDistributionPoints_Id, critical, value, NAME);
    }

    /**
     * Creates the extension (also called by the subclass).
     */
    protected CRLDistributionPointsExtension(ObjectIdentifier extensionId,
        Boolean critical, Object value, String extensionName)
            throws IOException {

        this.extensionId = extensionId;
        this.critical = critical.booleanValue();

        if (!(value instanceof byte[])) {
            throw new IOException("Illegal argument type");
        }

        extensionValue = (byte[])value;
        DerValue val = new DerValue(extensionValue);
        if (val.tag != DerValue.tag_Sequence) {
            throw new IOException("Invalid encoding for " + extensionName +
                                  " extension.");
        }
        distributionPoints = new ArrayList<sune.security.x509.DistributionPoint>();
        while (val.data.available() != 0) {
            DerValue seq = val.data.getDerValue();
            sune.security.x509.DistributionPoint point = new sune.security.x509.DistributionPoint(seq);
            distributionPoints.add(point);
        }
        this.extensionName = extensionName;
    }

    /**
     * Return the name of this attribute.
     */
    public String getName() {
        return extensionName;
    }

    /**
     * Write the extension to the DerOutputStream.
     *
     * @param out the DerOutputStream to write the extension to.
     * @exception IOException on encoding errors.
     */
    public void encode(OutputStream out) throws IOException {
        encode(out, PKIXExtensions.CRLDistributionPoints_Id, false);
    }

    /**
     * Write the extension to the DerOutputStream.
     * (Also called by the subclass)
     */
    protected void encode(OutputStream out, ObjectIdentifier extensionId,
        boolean isCritical) throws IOException {

        DerOutputStream tmp = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = extensionId;
            this.critical = isCritical;
            encodeThis();
        }
        super.encode(tmp);
        out.write(tmp.toByteArray());
    }

    /**
     * Set the attribute value.
     */
    public void set(String name, Object obj) throws IOException {
        if (name.equalsIgnoreCase(POINTS)) {
            if (!(obj instanceof List)) {
                throw new IOException("Attribute value should be of type List.");
            }
            distributionPoints = (List<sune.security.x509.DistributionPoint>)obj;
        } else {
            throw new IOException("Attribute name [" + name +
                                "] not recognized by " +
                                "CertAttrSet:" + extensionName + ".");
        }
        encodeThis();
    }

    /**
     * Get the attribute value.
     */
    public Object get(String name) throws IOException {
        if (name.equalsIgnoreCase(POINTS)) {
            return distributionPoints;
        } else {
            throw new IOException("Attribute name [" + name +
                                "] not recognized by " +
                                "CertAttrSet:" + extensionName + ".");
        }
    }

    /**
     * Delete the attribute value.
     */
    public void delete(String name) throws IOException {
        if (name.equalsIgnoreCase(POINTS)) {
            distributionPoints = new ArrayList<sune.security.x509.DistributionPoint>();
        } else {
            throw new IOException("Attribute name [" + name +
                                "] not recognized by " +
                                "CertAttrSet:" + extensionName + ".");
        }
        encodeThis();
    }

    /**
     * Return an enumeration of names of attributes existing within this
     * attribute.
     */
    public Enumeration<String> getElements() {
        sune.security.x509.AttributeNameEnumeration elements = new AttributeNameEnumeration();
        elements.addElement(POINTS);
        return elements.elements();
    }

     // Encode this extension value
    private void encodeThis() throws IOException {
        if (distributionPoints.isEmpty()) {
            this.extensionValue = null;
        } else {
            DerOutputStream pnts = new DerOutputStream();
            for (DistributionPoint point : distributionPoints) {
                point.encode(pnts);
            }
            DerOutputStream seq = new DerOutputStream();
            seq.write(DerValue.tag_Sequence, pnts);
            this.extensionValue = seq.toByteArray();
        }
    }

    /**
     * Return the extension as user readable string.
     */
    public String toString() {
        return super.toString() + extensionName + " [\n  "
               + distributionPoints + "]\n";
    }

}
