package gnu.crypto.key.dss;

// ----------------------------------------------------------------------------
// $Id: DSSKey.java,v 1.1 2005/12/22 09:48:38 serdar Exp $
//
// Copyright (C) 2001, 2002, 2003 Free Software Foundation, Inc.
//
// This file is part of GNU Crypto.
//
// GNU Crypto is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2, or (at your option)
// any later version.
//
// GNU Crypto is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; see the file COPYING.  If not, write to the
//
//    Free Software Foundation Inc.,
//    59 Temple Place - Suite 330,
//    Boston, MA 02111-1307
//    USA
//
// Linking this library statically or dynamically with other modules is
// making a combined work based on this library.  Thus, the terms and
// conditions of the GNU General Public License cover the whole
// combination.
//
// As a special exception, the copyright holders of this library give
// you permission to link this library with independent modules to
// produce an executable, regardless of the license terms of these
// independent modules, and to copy and distribute the resulting
// executable under terms of your choice, provided that you also meet,
// for each linked independent module, the terms and conditions of the
// license of that module.  An independent module is a module which is
// not derived from or based on this library.  If you modify this
// library, you may extend this exception to your version of the
// library, but you are not obligated to do so.  If you do not wish to
// do so, delete this exception statement from your version.
// ----------------------------------------------------------------------------

import gnu.crypto.Registry;
import gnu.crypto.key.IKeyPairCodec;

import java.math.BigInteger;
import java.security.Key;
import java.security.interfaces.DSAKey;
import java.security.interfaces.DSAParams;
import java.security.spec.DSAParameterSpec;

/**
 * <p>A base asbtract class for both public and private DSS (Digital Signature
 * Standard) keys. It encapsulates the three DSS numbers: <code>p</code>,
 * <code>q</code> and <code>g</code>.</p>
 *
 * <p>According to the JDK, cryptographic <i>Keys</i> all have a <i>format</i>.
 * The format used in this implementation is called <i>Raw</i>, and basically
 * consists of the raw byte sequences of algorithm parameters. The exact order
 * of the byte sequences and the implementation details are given in each of
 * the relevant <code>getEncoded()</code> methods of each of the private and
 * public keys.</p>
 *
 * @version $Revision: 1.1 $
 * @see DSSPrivateKey#getEncoded
 * @see DSSPublicKey#getEncoded
 */
public abstract class DSSKey implements Key, DSAKey {

   // Constants and variables
   // -------------------------------------------------------------------------

   /**
    * A prime modulus, where <code>2<sup>L-1</sup> {@literal < p <} 2<sup>L</sup></code>
    * for <code>512 {@literal <= L <=} 1024</code> and <code>L</code> a multiple of
    * <code>64</code>.
    */
   protected final BigInteger p;

   /**
    * A prime divisor of <code>p - 1</code>, where <code>2<sup>159</sup> {@literal <} q
    * {@literal <} 2<sup>160</sup></code>.
    */
   protected final BigInteger q;

   /**
    * <code>g = h<sup>(p-1)</sup>/q mod p</code>, where <code>h</code> is any
    * integer with <code>1 {@literal < h <} p - 1</code> such that <code>h<sup>
    * (p-1)</sup>/q mod p {@literal >} 1</code> (<code>g</code> has order <code>q mod p
    * </code>).
    */
   protected final BigInteger g;

   // Constructor(s)
   // -------------------------------------------------------------------------

   /**
    * <p>Trivial protected constructor.</p>
    *
    * @param p the DSS parameter <code>p</code>.
    * @param q the DSS parameter <code>q</code>.
    * @param g the DSS parameter <code>g</code>.
    */
   protected DSSKey(BigInteger p, BigInteger q, BigInteger g) {
      super();

      this.p = p;
      this.q = q;
      this.g = g;
   }

   // Class methods
   // -------------------------------------------------------------------------

   // Instance methods
   // -------------------------------------------------------------------------

   // java.security.interfaces.DSAKey interface implementation ----------------

   public DSAParams getParams() {
      return new DSAParameterSpec(p, q, g);
   }

   // java.security.Key interface implementation ------------------------------

   public String getAlgorithm() {
      return Registry.DSS_KPG;
   }

   /** @deprecated see getEncoded(int). */
   public byte[] getEncoded() {
      return getEncoded(IKeyPairCodec.X509_FORMAT);
   }

   public String getFormat() {
      return null;
   }

   // Other instance methods --------------------------------------------------

   /**
    * <p>Returns <code>true</code> if the designated object is an instance of
    * {@link DSAKey} and has the same DSS (Digital Signature Standard) parameter
    * values as this one.</p>
    *
    * @param obj the other non-null DSS key to compare to.
    * @return <code>true</code> if the designated object is of the same type and
    * value as this one.
    */
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (!(obj instanceof DSAKey)) {
         return false;
      }
      DSAKey that = (DSAKey) obj;
      return   p.equals(that.getParams().getP())
            && q.equals(that.getParams().getQ())
            && g.equals(that.getParams().getG());
   }

   // abstract methods to be implemented by subclasses ------------------------

   public abstract byte[] getEncoded(int format);
}
