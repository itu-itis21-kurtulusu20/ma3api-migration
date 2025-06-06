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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sune.security.util.DerOutputStream;

/**
 * This class implements the URIName as required by the GeneralNames
 * ASN.1 object.
 * <p>
 * [RFC3280] When the subjectAltName extension contains a URI, the name MUST be
 * stored in the uniformResourceIdentifier (an IA5String). The name MUST
 * be a non-relative URL, and MUST follow the URL syntax and encoding
 * rules specified in [RFC 1738].  The name must include both a scheme
 * (e.g., "http" or "ftp") and a scheme-specific-part.  The scheme-
 * specific-part must include a fully qualified domain name or IP
 * address as the host.
 * <p>
 * As specified in [RFC 1738], the scheme name is not case-sensitive
 * (e.g., "http" is equivalent to "HTTP").  The host part is also not
 * case-sensitive, but other components of the scheme-specific-part may
 * be case-sensitive. When comparing URIs, conforming implementations
 * MUST compare the scheme and host without regard to case, but assume
 * the remainder of the scheme-specific-part is case sensitive.
 * <p>
 * [RFC1738] In general, URLs are written as follows:
 * <pre>
 * {@literal <scheme>:<scheme-specific-part>}
 * </pre>
 * A URL contains the name of the scheme being used ({@literal <scheme>}) followed
 * by a colon and then a string (the {@literal <scheme-specific-part>}) whose
 * interpretation depends on the scheme.
 * <p>
 * While the syntax for the rest of the URL may vary depending on the
 * particular scheme selected, URL schemes that involve the direct use
 * of an IP-based protocol to a specified host on the Internet use a
 * common syntax for the scheme-specific data:
 * <pre>
 * //{@literal <user>:<password>@<host>:<port>/<url-path>}
 * </pre>
 * [RFC2732] specifies that an IPv6 address contained inside a URL
 * must be enclosed in square brackets (to allow distinguishing the
 * colons that separate IPv6 components from the colons that separate
 * scheme-specific data.
 * <p>
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 * @author Sean Mullan
 * @author Steve Hanna
 * @see GeneralName
 * @see GeneralNames
 * @see sune.security.x509.GeneralNameInterface
 */
public class URIName implements sune.security.x509.GeneralNameInterface {

    protected static Logger logger = LoggerFactory.getLogger(URIName.class);

    // private attributes
    private URI uri;
    private String host;
    private sune.security.x509.DNSName hostDNS;
    private sune.security.x509.IPAddressName hostIP;

    /**
     * Create the URIName object from the passed encoded Der value.
     *
     * @param derValue the encoded DER URIName.
     * @exception IOException on error.
     */
    public URIName(sune.security.util.DerValue derValue) throws IOException {
        this(derValue.getIA5String());
    }

    /**
     * Create the URIName object with the specified name.
     *
     * @param name the URIName.
     * @throws IOException if name is not a proper URIName
     */
    public URIName(String name) throws IOException {
        try {
            uri = new URI(name);
        } catch (URISyntaxException use) {
            throw (IOException) new IOException
                ("invalid URI name:" + name).initCause(use);
        }
        if (uri.getScheme() == null) {
            throw new IOException("URI name must include scheme:" + name);
        }

        host = uri.getHost();
        // RFC 3280 says that the host should be non-null, but we allow it to
        // be null because some widely deployed certificates contain CDP
        // extensions with URIs that have no hostname (see bugs 4802236 and
        // 5107944).
        if (host != null) {
            if (host.charAt(0) == '[') {
                // Verify host is a valid IPv6 address name
                String ipV6Host = host.substring(1, host.length()-1);
                try {
                    hostIP = new sune.security.x509.IPAddressName(ipV6Host);
                } catch (IOException ioe) {
                    logger.warn("Warning in URIName", ioe);
                    throw new IOException("invalid URI name (host " +
                        "portion is not a valid IPv6 address):" + name);
                }
            } else {
                try {
                    hostDNS = new sune.security.x509.DNSName(host);
                } catch (IOException ioe) {
                    logger.warn("Warning in URIName", ioe);
                    // Not a valid DNS Name; see if it is a valid IPv4
                    // IPAddressName
                    try {
                        hostIP = new IPAddressName(host);
                    } catch (Exception ioe2) {
                        logger.warn("Warning in URIName", ioe2);
                        throw new IOException("invalid URI name (host " +
                            "portion is not a valid DNS name, IPv4 address," +
                            " or IPv6 address):" + name);
                    }
                }
            }
        }
    }

    /**
     * Create the URIName object with the specified name constraint. URI
     * name constraints syntax is different than SubjectAltNames, etc. See
     * 4.2.1.11 of RFC 3280.
     *
     * @param value the URI name constraint
     * @throws IOException if name is not a proper URI name constraint
     */
    public static URIName nameConstraint(sune.security.util.DerValue value) throws IOException {
        URI uri;
        String name = value.getIA5String();
        try {
            uri = new URI(name);
        } catch (URISyntaxException use) {
            throw (IOException) new IOException
                ("invalid URI name constraint:" + name).initCause(use);
        }
        if (uri.getScheme() == null) {
            String host = uri.getSchemeSpecificPart();
            try {
                sune.security.x509.DNSName hostDNS;
                if (host.charAt(0) == '.') {
                    hostDNS = new sune.security.x509.DNSName(host.substring(1));
                } else {
                    hostDNS = new sune.security.x509.DNSName(host);
                }
                return new URIName(uri, host, hostDNS);
            } catch (IOException ioe) {
                throw (IOException) new IOException
                    ("invalid URI name constraint:" + name).initCause(ioe);
            }
        } else {
            throw new IOException("invalid URI name constraint (should not " +
                "include scheme):" + name);
        }
    }

    URIName(URI uri, String host, sune.security.x509.DNSName hostDNS) {
        this.uri = uri;
        this.host = host;
        this.hostDNS = hostDNS;
    }

    /**
     * Return the type of the GeneralName.
     */
    public int getType() {
        return sune.security.x509.GeneralNameInterface.NAME_URI;
    }

    /**
     * Encode the URI name into the DerOutputStream.
     *
     * @param out the DER stream to encode the URIName to.
     * @exception IOException on encoding errors.
     */
    public void encode(DerOutputStream out) throws IOException {
        out.putIA5String(uri.toASCIIString());
    }

    /**
     * Convert the name into user readable string.
     */
    public String toString() {
        return "URIName: " + uri.toString();
    }

    /**
     * Compares this name with another, for equality.
     *
     * @return true iff the names are equivalent according to RFC2459.
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof URIName)) {
            return false;
        }

        URIName other = (URIName) obj;

        return uri.equals(other.getURI());
    }

    /**
     * Returns the URIName as a java.net.URI object
     */
    public URI getURI() {
        return uri;
    }

    /**
     * Returns this URI name.
     */
    public String getName() {
        return uri.toString();
    }

    /**
     * Return the scheme name portion of a URIName
     *
     * @return scheme portion of full name
     */
    public String getScheme() {
        return uri.getScheme();
    }

    /**
     * Return the host name or IP address portion of the URIName
     *
     * @return host name or IP address portion of full name
     */
    public String getHost() {
        return host;
    }

    /**
     * Return the host object type; if host name is a
     * DNSName, then this host object does not include any
     * initial "." on the name.
     *
     * @return host name as DNSName or IPAddressName
     */
    public Object getHostObject() {
        if (hostIP != null) {
            return hostIP;
        } else {
            return hostDNS;
        }
    }

    /**
     * Returns the hash code value for this object.
     *
     * @return a hash code value for this object.
     */
    public int hashCode() {
        return uri.hashCode();
    }

    /**
     * Return type of constraint inputName places on this name:<ul>
     *   <li>NAME_DIFF_TYPE = -1: input name is different type from name
     *       (i.e. does not constrain).
     *   <li>NAME_MATCH = 0: input name matches name.
     *   <li>NAME_NARROWS = 1: input name narrows name (is lower in the naming
     *       subtree)
     *   <li>NAME_WIDENS = 2: input name widens name (is higher in the naming
     *       subtree)
     *   <li>NAME_SAME_TYPE = 3: input name does not match or narrow name, but
     *       is same type.
     * </ul>.
     * These results are used in checking NameConstraints during
     * certification path verification.
     * <p>
     * RFC3280: For URIs, the constraint applies to the host part of the name.
     * The constraint may specify a host or a domain.  Examples would be
     * "foo.bar.com";  and ".xyz.com".  When the the constraint begins with
     * a period, it may be expanded with one or more subdomains.  That is,
     * the constraint ".xyz.com" is satisfied by both abc.xyz.com and
     * abc.def.xyz.com.  However, the constraint ".xyz.com" is not satisfied
     * by "xyz.com".  When the constraint does not begin with a period, it
     * specifies a host.
     * <p>
     * @param inputName to be checked for being constrained
     * @return constraint type above
     * @throws UnsupportedOperationException if name is not exact match, but
     *  narrowing and widening are not supported for this name type.
     */
    public int constrains(GeneralNameInterface inputName)
        throws UnsupportedOperationException {
        int constraintType;
        if (inputName == null) {
            constraintType = NAME_DIFF_TYPE;
        } else if (inputName.getType() != NAME_URI) {
            constraintType = NAME_DIFF_TYPE;
        } else {
            // Assuming from here on that one or both of these is
            // actually a URI name constraint (not a URI), so we
            // only need to compare the host portion of the name

            String otherHost = ((URIName)inputName).getHost();

            // Quick check for equality
            if (otherHost.equalsIgnoreCase(host)) {
                constraintType = NAME_MATCH;
            } else {
                Object otherHostObject = ((URIName)inputName).getHostObject();

                if ((hostDNS == null) ||
                    !(otherHostObject instanceof sune.security.x509.DNSName)) {
                    // If one (or both) is an IP address, only same type
                    constraintType = NAME_SAME_TYPE;
                } else {
                    // Both host portions are DNS names. Are they domains?
                    boolean thisDomain = (host.charAt(0) == '.');
                    boolean otherDomain = (otherHost.charAt(0) == '.');
                    sune.security.x509.DNSName otherDNS = (sune.security.x509.DNSName) otherHostObject;

                    // Run DNSName.constrains.
                    constraintType = hostDNS.constrains(otherDNS);
                    // If neither one is a domain, then they can't
                    // widen or narrow. That's just SAME_TYPE.
                    if ((!thisDomain && !otherDomain) &&
                        ((constraintType == NAME_WIDENS) ||
                         (constraintType == NAME_NARROWS))) {
                        constraintType = NAME_SAME_TYPE;
                    }

                    // If one is a domain and the other isn't,
                    // then they can't match. The one that's a
                    // domain doesn't include the one that's
                    // not a domain.
                    if ((thisDomain != otherDomain) &&
                        (constraintType == NAME_MATCH)) {
                        if (thisDomain) {
                            constraintType = NAME_WIDENS;
                        } else {
                            constraintType = NAME_NARROWS;
                        }
                    }
                }
            }
        }
        return constraintType;
    }

    /**
     * Return subtree depth of this name for purposes of determining
     * NameConstraints minimum and maximum bounds and for calculating
     * path lengths in name subtrees.
     *
     * @return distance of name from root
     * @throws UnsupportedOperationException if not supported for this name type
     */
    public int subtreeDepth() throws UnsupportedOperationException {
        sune.security.x509.DNSName dnsName = null;
        try {
            dnsName = new DNSName(host);
        } catch (IOException ioe) {
            logger.warn("Warning in URIName", ioe);
            throw new UnsupportedOperationException(ioe.getMessage());
        }
        return dnsName.subtreeDepth();
    }
}
