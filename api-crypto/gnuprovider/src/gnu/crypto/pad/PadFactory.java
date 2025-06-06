package gnu.crypto.pad;

// ----------------------------------------------------------------------------
// $Id: PadFactory.java,v 1.2 2006/10/06 08:55:35 serdar Exp $
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>A Factory to instantiate padding schemes.</p>
 *
 * @version $Revision: 1.2 $
 */
public class PadFactory implements Registry {

   // Constants and variables
   // -------------------------------------------------------------------------

   // Constructor(s)
   // -------------------------------------------------------------------------

   /** Trivial constructor to enforce Singleton pattern. */
   private PadFactory() {
      super();
   }

   // Class methods
   // -------------------------------------------------------------------------

   /**
    * <p>Returns an instance of a padding algorithm given its name.</p>
    *
    * @param pad the case-insensitive name of the padding algorithm.
    * @return an instance of the padding algorithm, operating with a given
    * block size, or <code>null</code> if none found.
    * @throws InternalError if the implementation does not pass its self-test.
    */
   public static final IPad getInstance(String pad) {
      if (pad == null) {
         return null;
      }

      pad = pad.trim();
      IPad result = null;
      if (pad.equalsIgnoreCase(PKCS7_PAD)) {
         result = new PKCS7();
      } else if (pad.equalsIgnoreCase(TBC_PAD)) {
         result = new TBC();
      } else if (pad.equalsIgnoreCase(EME_PKCS1_V1_5_PAD)) {
         result = new PKCS1_V1_5();
      }else if(pad.equalsIgnoreCase(NONE_PAD)){
         result = new NoPadding();
      }

      if (padSelfTest &&  (result != null) && (!result.selfTest()) ) {
         throw new InternalError(result.name());
      }

      return result;
   }

   /**
    * <p>Returns a {@link java.util.Set} of names of padding algorithms
    * supported by this <i>Factory</i>.</p>
    *
    * @return a {@link Set} of padding algorithm names (Strings).
    */
   public static final Set getNames() {
      HashSet hs = new HashSet();
      hs.add(PKCS7_PAD);
      hs.add(TBC_PAD);
      hs.add(EME_PKCS1_V1_5_PAD);
      hs.add(NONE_PAD);

      return Collections.unmodifiableSet(hs);
   }

   // Instance methods
   // -------------------------------------------------------------------------
}
