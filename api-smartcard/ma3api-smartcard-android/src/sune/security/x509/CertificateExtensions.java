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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sune.misc.HexDumpEncoder;
import sune.security.util.Debug;
import sune.security.util.DerInputStream;
import sune.security.util.DerOutputStream;
import sune.security.util.DerValue;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * This class defines the Extensions attribute for the Certificate.
 *
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 * @see sune.security.x509.CertAttrSet
 */
public class CertificateExtensions implements sune.security.x509.CertAttrSet<sune.security.x509.Extension> {

    protected static Logger logger = LoggerFactory.getLogger(CertificateExtensions.class);
    /**
     * Identifier for this attribute, to be used with the
     * get, set, delete methods of Certificate, x509 type.
     */
    public static final String IDENT = "x509.info.extensions";
    /**
     * name
     */
    public static final String NAME = "extensions";

    private static final Debug debug = Debug.getInstance("x509");

    private Hashtable<String, sune.security.x509.Extension> map = new Hashtable<String, sune.security.x509.Extension>();
    private boolean unsupportedCritExt = false;

    private Map<String, sune.security.x509.Extension> unparseableExtensions;

    /**
     * Default constructor.
     */
    public CertificateExtensions() { }

    /**
     * Create the object, decoding the values from the passed DER stream.
     *
     * @param in the DerInputStream to read the Extension from.
     * @exception IOException on decoding errors.
     */
    public CertificateExtensions(DerInputStream in) throws IOException {
        init(in);
    }

    // helper routine
    private void init(DerInputStream in) throws IOException {

        DerValue[] exts = in.getSequence(5);

        for (int i = 0; i < exts.length; i++) {
            sune.security.x509.Extension ext = new sune.security.x509.Extension(exts[i]);
            parseExtension(ext);
        }
    }

    private static Class[] PARAMS = {Boolean.class, Object.class};

    // Parse the encoded extension
    private void parseExtension(sune.security.x509.Extension ext) throws IOException {
        try {
            Class extClass = sune.security.x509.OIDMap.getClass(ext.getExtensionId());
            if (extClass == null) {   // Unsupported extension
                if (ext.isCritical()) {
                    unsupportedCritExt = true;
                }
                if (map.put(ext.getExtensionId().toString(), ext) == null) {
                    return;
                } else {
                    throw new IOException("Duplicate extensions not allowed");
                }
            }
            Constructor cons = ((Class<?>)extClass).getConstructor(PARAMS);

            Object[] passed = new Object[] {Boolean.valueOf(ext.isCritical()),
                    ext.getExtensionValue()};
                    sune.security.x509.CertAttrSet certExt = (sune.security.x509.CertAttrSet)cons.newInstance(passed);
                    if (map.put(certExt.getName(), (sune.security.x509.Extension)certExt) != null) {
                        throw new IOException("Duplicate extensions not allowed");
                    }
        } catch (InvocationTargetException x) {
            logger.warn("Warning in CertificateExtensions", x);
            Throwable e = x.getTargetException();
            if (ext.isCritical() == false) {
                // ignore errors parsing non-critical extensions
                if (unparseableExtensions == null) {
                    unparseableExtensions = new HashMap<String, sune.security.x509.Extension>();
                }
                unparseableExtensions.put(ext.getExtensionId().toString(),
                        new UnparseableExtension(ext, e));
                if (debug != null) {
                    debug.println("Error parsing extension: " + ext);
                    logger.error("Error in CertificateExtensions", e);
                    HexDumpEncoder h = new HexDumpEncoder();
                    System.err.println(h.encodeBuffer(ext.getExtensionValue()));
                }
                return;
            }
            if (e instanceof IOException) {
                throw (IOException)e;
            } else {
                throw (IOException)new IOException(e.toString()).initCause(e);
            }
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw (IOException)new IOException(e.toString()).initCause(e);
        }
    }

    /**
     * Encode the extensions in DER form to the stream, setting
     * the context specific tag as needed in the X.509 v3 certificate.
     *
     * @param out the DerOutputStream to marshal the contents to.
     * @exception CertificateException on encoding errors.
     * @exception IOException on errors.
     */
    public void encode(OutputStream out)
    throws CertificateException, IOException {
        encode(out, false);
    }

    /**
     * Encode the extensions in DER form to the stream.
     *
     * @param out the DerOutputStream to marshal the contents to.
     * @param isCertReq if true then no context specific tag is added.
     * @exception CertificateException on encoding errors.
     * @exception IOException on errors.
     */
    public void encode(OutputStream out, boolean isCertReq)
    throws CertificateException, IOException {
        DerOutputStream extOut = new DerOutputStream();
        Collection<sune.security.x509.Extension> allExts = map.values();
        Object[] objs = allExts.toArray();

        for (int i = 0; i < objs.length; i++) {
            if (objs[i] instanceof sune.security.x509.CertAttrSet)
                ((sune.security.x509.CertAttrSet)objs[i]).encode(extOut);
            else if (objs[i] instanceof sune.security.x509.Extension)
                ((sune.security.x509.Extension)objs[i]).encode(extOut);
            else
                throw new CertificateException("Illegal extension object");
        }

        DerOutputStream seq = new DerOutputStream();
        seq.write(DerValue.tag_Sequence, extOut);

        DerOutputStream tmp;
        if (!isCertReq) { // certificate
            tmp = new DerOutputStream();
            tmp.write(DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte)3),
                    seq);
        } else
            tmp = seq; // pkcs#10 certificateRequest

        out.write(tmp.toByteArray());
    }

    /**
     * Set the attribute value.
     * @param name the extension name used in the cache.
     * @param obj the object to set.
     * @exception IOException if the object could not be cached.
     */
    public void set(String name, Object obj) throws IOException {
        if (obj instanceof sune.security.x509.Extension) {
            map.put(name, (sune.security.x509.Extension)obj);
        } else {
            throw new IOException("Unknown extension type.");
        }
    }

    /**
     * Get the attribute value.
     * @param name the extension name used in the lookup.
     * @exception IOException if named extension is not found.
     */
    public Object get(String name) throws IOException {
        Object obj = map.get(name);
        if (obj == null) {
            throw new IOException("No extension found with name " + name);
        }
        return (obj);
    }

    /**
     * Delete the attribute value.
     * @param name the extension name used in the lookup.
     * @exception IOException if named extension is not found.
     */
    public void delete(String name) throws IOException {
        Object obj = map.get(name);
        if (obj == null) {
            throw new IOException("No extension found with name " + name);
        }
        map.remove(name);
    }

    /**
     * Return an enumeration of names of attributes existing within this
     * attribute.
     */
    public Enumeration<sune.security.x509.Extension> getElements() {
        return map.elements();
    }

    /**
     * Return a collection view of the extensions.
     * @return a collection view of the extensions in this Certificate.
     */
    public Collection<sune.security.x509.Extension> getAllExtensions() {
        return map.values();
    }

    public Map<String, sune.security.x509.Extension> getUnparseableExtensions() {
        if (unparseableExtensions == null) {
            return Collections.emptyMap();
        } else {
            return unparseableExtensions;
        }
    }

    /**
     * Return the name of this attribute.
     */
    public String getName() {
        return NAME;
    }

    /**
     * Return true if a critical extension is found that is
     * not supported, otherwise return false.
     */
    public boolean hasUnsupportedCriticalExtension() {
        return unsupportedCritExt;
    }

    /**
     * Compares this CertificateExtensions for equality with the specified
     * object. If the <code>other</code> object is an
     * <code>instanceof</code> <code>CertificateExtensions</code>, then
     * all the entries are compared with the entries from this.
     *
     * @param other the object to test for equality with this
     * CertificateExtensions.
     * @return true iff all the entries match that of the Other,
     * false otherwise.
     */
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof CertificateExtensions))
            return false;
        Collection<sune.security.x509.Extension> otherC =
                ((CertificateExtensions)other).getAllExtensions();
        Object[] objs = otherC.toArray();

        int len = objs.length;
        if (len != map.size())
            return false;

        sune.security.x509.Extension otherExt, thisExt;
        String key = null;
        for (int i = 0; i < len; i++) {
            if (objs[i] instanceof sune.security.x509.CertAttrSet)
                key = ((CertAttrSet)objs[i]).getName();
            otherExt = (sune.security.x509.Extension)objs[i];
            if (key == null)
                key = otherExt.getExtensionId().toString();
            thisExt = map.get(key);
            if (thisExt == null)
                return false;
            if (! thisExt.equals(otherExt))
                return false;
        }
        return this.getUnparseableExtensions().equals(
                ((CertificateExtensions)other).getUnparseableExtensions());
    }

    /**
     * Returns a hashcode value for this CertificateExtensions.
     *
     * @return the hashcode value.
     */
    public int hashCode() {
        return map.hashCode() + getUnparseableExtensions().hashCode();
    }

    /**
     * Returns a string representation of this <tt>CertificateExtensions</tt>
     * object in the form of a set of entries, enclosed in braces and separated
     * by the ASCII characters "<tt>,&nbsp;</tt>" (comma and space).
     * <p>Overrides to <tt>toString</tt> method of <tt>Object</tt>.
     *
     * @return  a string representation of this CertificateExtensions.
     */
    public String toString() {
        return map.toString();
    }

}

class UnparseableExtension extends sune.security.x509.Extension {
    protected static Logger logger = LoggerFactory.getLogger(UnparseableExtension.class);
    private String name;
    private Throwable why;

    public UnparseableExtension(Extension ext, Throwable why) {
        super(ext);

        name = "";
        try {
            Class extClass = OIDMap.getClass(ext.getExtensionId());
            if (extClass != null) {
                Field field = extClass.getDeclaredField("NAME");
                name = (String)(field.get(null)) + " ";
            }
        } catch (Exception e) {
            logger.warn("Warning in UnparseableExtension", e);
            // If we cannot find the name, just ignore it
        }

        this.why = why;
    }

    @Override public String toString() {
        return super.toString() +
                "Unparseable " + name + "extension due to\n" + why + "\n\n" +
                new HexDumpEncoder().encodeBuffer(getExtensionValue());
    }
}
