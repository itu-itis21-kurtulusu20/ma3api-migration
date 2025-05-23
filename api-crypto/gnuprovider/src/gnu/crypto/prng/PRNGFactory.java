package gnu.crypto.prng;

// ----------------------------------------------------------------------------
// $Id: PRNGFactory.java,v 1.1 2004/11/02 11:39:41 serdar Exp $
//
// Copyright (C) 2001, 2002, 2003  Free Software Foundation, Inc.
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
import gnu.crypto.mac.HMacFactory;
import gnu.crypto.mac.IMac;
import gnu.crypto.mac.MacFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import tr.gov.tubitak.uekae.esya.api.common.crypto.IRandom;

/**
 * <p>A Factory to instantiate pseudo random number generators.</p>
 *
 * @version $Revision: 1.1 $
 */
public class PRNGFactory implements Registry {

   // Constants and variables
   // -------------------------------------------------------------------------

   // Constructor(s)
   // -------------------------------------------------------------------------

   /** Trivial constructor to enforce <i>Singleton</i> pattern. */
   private PRNGFactory() {
      super();
   }

   // Class methods
   // -------------------------------------------------------------------------

   /**
    * <p>Returns an instance of a padding algorithm given its name.</p>
    *
    * @param prng the case-insensitive name of the PRNG.
    * @return an instance of the pseudo-random number generator.
    * @exception InternalError if the implementation does not pass its self-
    * test.
    */
   public static final IRandom getInstance(String prng) {
      if (prng == null) {
         return null;
      }

      prng = prng.trim();
      IRandom result = null;
      if (prng.equalsIgnoreCase(ARCFOUR_PRNG)
            || prng.equalsIgnoreCase(RC4_PRNG)) {
         result = new ARCFour();
      } else if (prng.equalsIgnoreCase(ICM_PRNG)) {
         result = new ICMGenerator();
      } else if (prng.equalsIgnoreCase(MD_PRNG)) {
         result = new MDGenerator();
      } else if (prng.equalsIgnoreCase(UMAC_PRNG)) {
         result = new UMacGenerator();
      } else if(prng.equalsIgnoreCase(DGKGF_PRNG)){
    	  result = new DGKGF();
      }else if(prng.equalsIgnoreCase(UEKAE_CRYPTO_CARD_PRNG)){
    	  result = new UEKAECryptoCardGenerator();
      }else if(prng.equalsIgnoreCase(SMART_CARD_PRNG)){
    	  try {
			result = (IRandom) Class.forName("tr.gov.tubitak.uekae.esya.api.smartcard.util.SmartCardRandom").newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
      }else if (prng.toLowerCase().startsWith(PBKDF2_PRNG_PREFIX)) {
         String macName = prng.substring(PBKDF2_PRNG_PREFIX.length());
         IMac mac = MacFactory.getInstance(macName);
         if (mac == null) {
            return null;
         }
         result = new PBKDF2(mac);
      }

      return result;
   }

   /**
    * <p>Returns a {@link Set} of names of padding algorithms supported by this
    * <i>Factory</i>.</p>
    *
    * @return a {@link Set} of pseudo-random number generator algorithm names
    * (Strings).
    */
   public static final Set getNames() {
      HashSet hs = new HashSet();
      hs.add(ICM_PRNG);
      hs.add(MD_PRNG);
      hs.add(UMAC_PRNG);
      // add all hmac implementations as candidate PBKDF2 ones too
      for (Iterator it = HMacFactory.getNames().iterator(); it.hasNext(); ) {
         hs.add(PBKDF2_PRNG_PREFIX+((String) it.next()));
      }

      return Collections.unmodifiableSet(hs);
   }

   // Instance methods
   // -------------------------------------------------------------------------
}
