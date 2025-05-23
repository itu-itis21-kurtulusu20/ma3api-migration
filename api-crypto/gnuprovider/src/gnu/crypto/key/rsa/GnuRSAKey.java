package gnu.crypto.key.rsa;

// ----------------------------------------------------------------------------
// $Id: GnuRSAKey.java,v 1.2 2005/12/30 11:24:48 serdar Exp $
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
import java.security.interfaces.RSAKey;

/**
 * <p>A base asbtract class for both public and private RSA keys.</p>
 *
 * @version $Revision: 1.2 $
 */
public abstract class GnuRSAKey implements Key, RSAKey {

   // Constants and variables
   // -------------------------------------------------------------------------

   /** The public modulus of an RSA key pair. */
   private final BigInteger n;
   /** The public exponent of an RSA key pair. */
   private final BigInteger e;

   // Constructor(s)
   // -------------------------------------------------------------------------

   /**
    * <p>Trivial protected constructor.</p>
    *
    * @param n the public modulus <code>n</code>.
    * @param e the public exponent <code>e</code>.
    */
//   protected GnuRSAKey(BigInteger n) {
   protected GnuRSAKey(final BigInteger n, final BigInteger e) {
      super();

      this.n = n;
      this.e = e;
   }

   // Class methods
   // -------------------------------------------------------------------------

   // Instance methods
   // -------------------------------------------------------------------------

   // java.security.interfaces.RSAKey interface implementation ----------------

   public BigInteger getModulus() {
      return getN();
   }

   // java.security.Key interface implementation ------------------------------

   public String getAlgorithm() {
      return Registry.RSA_KPG;
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
    * <p>Returns the modulus <code>n</code>.</p>
    *
    * @return the modulus <code>n</code>.
    */
   public BigInteger getN() {
      return n;
   }

   /**
    * <p>Returns the public exponent <code>e</code>.</p>
    *
    * @return the public exponent <code>e</code>.
    */
   public BigInteger getPublicExponent() {
      return getE();
   }

   /**
    * <p>Same as {@link #getPublicExponent()}.</p>
    *
    * @return the public exponent <code>e</code>.
    */
   public BigInteger getE() {
      return e;
   }

   /**
    * <p>Returns <code>true</code> if the designated object is an instance of
    * {@link RSAKey} and has the same RSA parameter values as this one.</p>
    *
    * @param obj the other non-null RSA key to compare to.
    * @return <code>true</code> if the designated object is of the same type and
    * value as this one.
    */
   public boolean equals(final Object obj) {
      if (obj == null) {
         return false;
      }
      if (!(obj instanceof RSAKey)) {
         return false;
      }
      final RSAKey that = (RSAKey) obj;
      return n.equals(that.getModulus());
   }

   // abstract methods to be implemented by subclasses ------------------------

   public abstract byte[] getEncoded(int format);
}
