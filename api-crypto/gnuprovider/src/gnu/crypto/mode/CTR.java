package gnu.crypto.mode;

// ----------------------------------------------------------------------------
// $Id: CTR.java,v 1.1 2004/11/19 07:31:33 serdar Exp $
//
// Copyright (C) 2001, 2002, Free Software Foundation, Inc.
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
import gnu.crypto.cipher.IBlockCipher;

import java.math.BigInteger;

/**
 * <p>The implementation of the Counter Mode.</p>
 *
 * <p>The algorithm steps are formally described as follows:</p>
 *
 * <pre>
 *    CTR Encryption: O[j] = E(K)(T[j]); for j = 1, 2...n;
 *                    C[j] = P[j] ^ O[j]; for j = 1, 2...n.
 *    CTR Decryption: O[j] = E(K)(T[j]); for j = 1, 2...n;
 *                    P[j] = C[j] ^ O[j]; for j = 1, 2...n.
 * </pre>
 *
 * <p>where <code>P</code> is the plaintext, <code>C</code> is the ciphertext,
 * <code>E(K)</code> is the underlying block cipher encryption function
 * parametrised with the session key <code>K</code>, and <code>T</code> is the
 * <i>Counter</i>.</p>
 *
 * <p>This implementation, uses a standard incrementing function with a step of
 * 1, and an initial value similar to that described in the NIST document.</p>
 *
 * <p>References:</p>
 *
 * <ol>
 *    <li><a href="http://csrc.nist.gov/encryption/modes/Recommendation/Modes01.pdf">
 *    Recommendation for Block Cipher Modes of Operation Methods and Techniques</a>,
 *    Morris Dworkin.</li>
 * </ol>
 *
 * @version $Revision: 1.1 $
 */
public class CTR extends BaseMode implements Cloneable {

   // Constants and variables
   // -------------------------------------------------------------------------

   /** The current counter. */
   private BigInteger T;

   // Constructor(s)
   // -------------------------------------------------------------------------

   /**
    * <p>Trivial package-private constructor for use by the Factory class.</p>
    *
    * @param underlyingCipher the underlying cipher implementation.
    * @param cipherBlockSize the underlying cipher block size to use.
    */
   CTR(IBlockCipher underlyingCipher, int cipherBlockSize) {
      super(Registry.CTR_MODE, underlyingCipher, cipherBlockSize);
   }

   /**
    * <p>Private constructor for cloning purposes.</p>
    *
    * @param that the instance to clone.
    */
   private CTR(CTR that) {
      this((IBlockCipher) that.cipher.clone(), that.cipherBlockSize);
   }

   // Class methods
   // -------------------------------------------------------------------------

   // Cloneable interface implementation
   // -------------------------------------------------------------------------

   public Object clone() {
      return new CTR(this);
   }

   // Implementation of abstract methods in BaseMode
   // -------------------------------------------------------------------------

   public void setup() {
      if (modeBlockSize != cipherBlockSize) {
         throw new IllegalArgumentException();
      }

      byte[] tBytes = new byte[modeBlockSize+1];
      tBytes[0] = (byte) 0x80;
      for (int i = 0; i < modeBlockSize; i++) {
         tBytes[i+1] = (byte)(256 - modeBlockSize + i);
      }

      T = new BigInteger(1, tBytes);
   }

   public void teardown() {
      T = null;
   }

   public void encryptBlock(byte[] in, int i, byte[] out, int o) {
      ctr(in, i, out, o);
   }

   public void decryptBlock(byte[] in, int i, byte[] out, int o) {
      ctr(in, i, out, o);
   }

   // own methods
   // -------------------------------------------------------------------------

   private void ctr(byte[] in, int inOffset, byte[] out, int outOffset) {
      T = T.add(BigInteger.ONE);
      byte[] O = T.toByteArray();
      int ndx = O.length - modeBlockSize;
      cipher.encryptBlock(O, ndx, O, ndx);
      for (int i = 0; i < modeBlockSize; i++) {
         out[outOffset++] = (byte)(in[inOffset++] ^ O[ndx++]);
      }
   }
}
