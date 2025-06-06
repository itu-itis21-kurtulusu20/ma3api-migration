/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.algorithms;

import com.objsys.asn1j.runtime.*;

public class ValidationParms extends Asn1Type {
	private static final long serialVersionUID = 55;
	static {
		setKey(_algorithmsRtkey._rtkey);
	}

   public Asn1BitString seed;
   public Asn1Integer pgenCounter;

   public ValidationParms () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public ValidationParms (
      Asn1BitString seed_,
      Asn1Integer pgenCounter_
   ) {
      super();
      seed = seed_;
      pgenCounter = pgenCounter_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public ValidationParms (Asn1BitString seed_,
      long pgenCounter_
   ) {
      super();
      seed = seed_;
      pgenCounter = new Asn1Integer (pgenCounter_);
   }

   public void init () {
      seed = null;
      pgenCounter = null;
   }

   public void decode
      (Asn1BerDecodeBuffer buffer, boolean explicit, int implicitLength)
      throws Asn1Exception, java.io.IOException
   {
      int llen = (explicit) ?
         matchTag (buffer, Asn1Tag.SEQUENCE) : implicitLength;

      init ();

      // decode SEQUENCE

      Asn1BerDecodeContext _context =
         new Asn1BerDecodeContext (buffer, llen);

      IntHolder elemLen = new IntHolder();

      // decode seed

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 3, elemLen, false)) {
         seed = new Asn1BitString();
         seed.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "seed");

      // decode pgenCounter

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
         pgenCounter = new Asn1Integer();
         pgenCounter.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "pgenCounter");

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 3) ||
             _tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 2))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode pgenCounter

      if (pgenCounter != null) {
         len = pgenCounter.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("pgenCounter");

      // encode seed

      if (seed != null) {
         len = seed.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("seed");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (seed != null) seed.print (_out, "seed", _level+1);
      if (pgenCounter != null) pgenCounter.print (_out, "pgenCounter", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}