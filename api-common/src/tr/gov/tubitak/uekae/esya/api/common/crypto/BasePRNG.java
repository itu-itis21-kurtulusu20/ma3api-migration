package tr.gov.tubitak.uekae.esya.api.common.crypto;

// ----------------------------------------------------------------------------
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

import java.util.Map;

/**
 * <p>An abstract class to facilitate implementing PRNG algorithms.</p>
 *
 * @version $Revision: 1.2 $
 */
public abstract class BasePRNG implements IRandom {
    public int getBufferSize() {
        return bufferSize;
    }
// Constants and variables
   // -------------------------------------------------------------------------

   /** The canonical name prefix of the PRNG algorithm. */
   protected String name;

   /** Indicate if this instance has already been initialised or not. */
   protected boolean initialised;

   /** A temporary buffer to serve random bytes. */
   protected byte[] buffer;

   /** The index into buffer of where the next byte will come from. */
   protected int ndx;

    protected static final int MIN_BUFFER_SIZE = 2048;
    protected int bufferSize = MIN_BUFFER_SIZE;

   // Constructor(s)
   // -------------------------------------------------------------------------

   /**
    * <p>Trivial constructor for use by concrete subclasses.</p>
    *
    * @param name the canonical name of this instance.
    */
   protected BasePRNG(String name) {
      super();

      this.name = name;
      initialised = false;
      buffer = new byte[0];
   }

   // Class methods
   // -------------------------------------------------------------------------

   // Instance methods
   // -------------------------------------------------------------------------

   // IRandom interface implementation ----------------------------------------

   public String name() {
      return name;
   }

   public void init(Map attributes) {
      this.setup(attributes);
      ndx = buffer.length;
      initialised = true;
   }

    public void setBufferSize(int bufSize){
        this.bufferSize=bufSize;
     /*   if(this.bufferSize<MIN_BUFFER_SIZE){
            this.bufferSize = MIN_BUFFER_SIZE;
        }*/
        buffer = new byte[bufferSize];
        this.ndx = bufferSize;
    }

   public byte nextByte() throws IllegalStateException, LimitReachedException {
      if (!initialised) {
         throw new IllegalStateException();
      }
      return nextByteInternal();
   }

   public void nextBytes(byte[] out, int offset, int length)
   throws IllegalStateException, LimitReachedException {
      if (out == null) {
         return;
      }

      if (!initialised) {
         throw new IllegalStateException();
      }

      if (offset < 0 || offset >= out.length || length < 1) {
         return;
      }

      int limit = ((offset+length) > out.length ? out.length-offset : length);
      for (int i = 0; i < limit; i++) {
         out[offset++] = nextByteInternal();
      }
   }

   // Instance methods
   // -------------------------------------------------------------------------

   public boolean isInitialised() {
      return initialised;
   }

   private byte nextByteInternal() throws LimitReachedException {
      if (ndx >= buffer.length) {
         this.fillBlock();
         ndx = 0;
      }

      return buffer[ndx++];
   }

    public boolean isUseTRSU() {
        return true;
    }

    // abstract methods to implement by subclasses -----------------------------

   public abstract Object clone();

   public abstract void setup(Map attributes);

   public abstract void fillBlock() throws LimitReachedException;
}
