package gnu.crypto.prng;

// ----------------------------------------------------------------------------
// $Id: MDGenerator.java,v 1.1 2004/11/02 11:39:41 serdar Exp $
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
import gnu.crypto.hash.HashFactory;
import gnu.crypto.hash.IMessageDigest;

import java.util.Map;

import tr.gov.tubitak.uekae.esya.api.common.crypto.BasePRNG;
import tr.gov.tubitak.uekae.esya.api.common.crypto.ISeed;
import tr.gov.tubitak.uekae.esya.api.common.crypto.LimitReachedException;

/**
 * <p>A simple pseudo-random number generator that relies on a hash algorithm,
 * that (a) starts its operation by hashing a <code>seed</code>, and then (b)
 * continuously re-hashing its output. If no hash algorithm name is specified
 * in the {@link Map} of attributes used to initialise the instance then the
 * SHA-160 algorithm is used as the underlying hash function. Also, if no
 * <code>seed</code> is given, an empty octet sequence is used.</p>
 *
 * @version $Revision: 1.1 $
 */
public class MDGenerator extends BasePRNG {

   // Constants and variables
   // -------------------------------------------------------------------------

   /** Property name of underlying hash algorithm for this generator. */
   public static final String MD_NAME = "gnu.crypto.prng.md.hash.name";

   /** Property name of seed material. */
   public static final String SEEED_GENERATOR = "gnu.crypto.prng.md.seedgenerator";
   
   
   public static final String SEEED_LENGTH = "gnu.crypto.prng.md.seedlength";
   
   private static final int DEFAULT_SEED_LENGTH = 64;

   /** The underlying hash instance. */
   private IMessageDigest md;

   // Constructor(s)
   // -------------------------------------------------------------------------

   /** Trivial 0-arguments constructor. */
   public MDGenerator() {
      super(Registry.MD_PRNG);
   }

   /**
    *
    * <p>Private constructor for cloning purposes.</p>
    *
    * @param that the instance to clone.
    */
   private MDGenerator(MDGenerator that) {
      this();

      this.md = (that.md == null ? null : (IMessageDigest) that.md.clone());
      this.buffer = (byte[]) that.buffer.clone();
      this.ndx = that.ndx;
      this.initialised = that.initialised;
   }

   // Class methods
   // -------------------------------------------------------------------------

   // Instance methods
   // -------------------------------------------------------------------------

   // java.lang.Cloneable interface implementation ----------------------------

   public Object clone() {
      return new MDGenerator(this);
   }

   // Implementation of abstract methods in BaseRandom ------------------------

   public void setup(Map attributes) {
      // find out which hash to use
      String underlyingMD = (String) attributes.get(MD_NAME);
      if (underlyingMD == null) {
         if (md == null) { // happy birthday
            // ensure we have a reliable implementation of this hash
            md = HashFactory.getInstance(Registry.SHA160_HASH);
         } else { // a clone. reset it for reuse
            md.reset();
         }
      } else { // ensure we have a reliable implementation of this hash
         md = HashFactory.getInstance(underlyingMD);
      }

      // get the seeed
      ISeed seedG = (ISeed) attributes.get(SEEED_GENERATOR);
      if (seedG == null) {
         seedG = new MemorySeed();
      }

      Integer seedLength = (Integer) attributes.get(SEEED_LENGTH);
      if(seedLength == null)
      {
	  seedLength = DEFAULT_SEED_LENGTH;
      }
      
      byte[] seed = seedG.getSeed(seedLength);
      md.update(seed, 0, seed.length);
   }

   public void fillBlock() throws LimitReachedException {
      IMessageDigest mdc = (IMessageDigest) md.clone();
      buffer= mdc.digest();
      md.update(buffer, 0, buffer.length);
   }
}
