package gnu.crypto.prng;

// ----------------------------------------------------------------------------
// $Id: IPBE.java,v 1.1 2004/11/02 11:39:41 serdar Exp $
//
// Copyright (C) 2003 Free Software Foundation, Inc.
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

/**
 * <p>Trivial interface to group Password-based encryption property names.</p>
 *
 * @version $Revision: 1.1 $
 */
public interface IPBE {

   // Constants
   // -------------------------------------------------------------------------

   /**
    * Property name for the iteration count in a PBE algorithm. The property
    * associated with this is expected to be an {@link Integer}.
    */
   public static final String ITERATION_COUNT = "gnu.crypto.pbe.iteration.count";

   /**
    * Property name for the password in a PBE algorithm. The property associated
    * with this is expected to be a char array.
    */
   public static final String PASSWORD = "gnu.crypto.pbe.password";

   /**
    * Property name for the salt in a PBE algorithm. The property associated
    * with this is expected to be a byte array.
    */
   public static final String SALT = "gnu.crypto.pbe.salt";
}
