/*
 * Copyright 2003-2005 Sun Microsystems, Inc.  All Rights Reserved.
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

import sune.security.util.DerInputStream;

/**
 * @author      Ram Marti
 */

public final class AccessDescription {

    private int myhash = -1;

    private sune.security.util.ObjectIdentifier accessMethod;

    private sune.security.x509.GeneralName accessLocation;

    public static final sune.security.util.ObjectIdentifier Ad_OCSP_Id =
        sune.security.util.ObjectIdentifier.newInternal(new int[] {1, 3, 6, 1, 5, 5, 7, 48, 1});

    public static final sune.security.util.ObjectIdentifier Ad_CAISSUERS_Id =
        sune.security.util.ObjectIdentifier.newInternal(new int[] {1, 3, 6, 1, 5, 5, 7, 48, 2});

    public AccessDescription(sune.security.util.DerValue derValue) throws IOException {
        DerInputStream derIn = derValue.getData();
        accessMethod = derIn.getOID();
        accessLocation = new sune.security.x509.GeneralName(derIn.getDerValue());
    }

    public sune.security.util.ObjectIdentifier getAccessMethod() {
        return accessMethod;
    }

    public GeneralName getAccessLocation() {
        return accessLocation;
    }

    public void encode(sune.security.util.DerOutputStream out) throws IOException {
        sune.security.util.DerOutputStream tmp = new sune.security.util.DerOutputStream();
        tmp.putOID(accessMethod);
        accessLocation.encode(tmp);
        out.write(sune.security.util.DerValue.tag_Sequence, tmp);
    }

    public int hashCode() {
        if (myhash == -1) {
            myhash = accessMethod.hashCode() + accessLocation.hashCode();
        }
        return myhash;
    }

    public boolean equals(Object obj) {
        if (obj == null || (!(obj instanceof AccessDescription))) {
            return false;
        }
        AccessDescription that = (AccessDescription)obj;

        if (this == that) {
            return true;
        }
        return (accessMethod.equals(that.getAccessMethod()) &&
            accessLocation.equals(that.getAccessLocation()));
    }

    public String toString() {
        return ("accessMethod: " + accessMethod.toString() +
                "\n   accessLocation: " + accessLocation.toString());
    }
}
