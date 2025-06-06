/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 7.0.0, Date: 18-Mar-2016.
 */
package tr.gov.tubitak.uekae.esya.api.smartcard.apdu.asn.pkcs5;

import com.objsys.asn1j.runtime.*;

public class PBEParameter extends Asn1Seq {
   private static final long serialVersionUID = 55;
   static {
      setKey (_pkcs5Rtkey._rtkey);
   }

   public String getAsn1TypeName()  {
      return "PBEParameter";
   }

   public Asn1OctetString salt;
   public Asn1Integer iterationCount;

   public PBEParameter () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public PBEParameter (
      Asn1OctetString salt_,
      Asn1Integer iterationCount_
   ) {
      super();
      salt = salt_;
      iterationCount = iterationCount_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public PBEParameter (byte[] salt_,
      long iterationCount_
   ) {
      super();
      salt = new Asn1OctetString (salt_);
      iterationCount = new Asn1Integer (iterationCount_);
   }

   public void init () {
      salt = null;
      iterationCount = null;
   }

   public int getElementCount() { return 2; }


   public Object getElementValue(int index){
      switch(index)  {
         case 0: return salt;
         case 1: return iterationCount;
         default: return null;
      }
   }


   public String getElementName(int index){
      switch(index)  {
         case 0: return "salt";
         case 1: return "iterationCount";
         default: return null;
      }
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

      // decode salt

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 4, elemLen, false)) {
         salt = new Asn1OctetString();
         salt.decode (buffer, true, elemLen.value);
         if (!(salt.getLength() == 8)) {
            throw new Asn1ConsVioException ("salt.getLength()", salt.getLength());
         }

      }
      else throw new Asn1MissingRequiredException (buffer, "salt");

      // decode iterationCount

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
         iterationCount = new Asn1Integer();
         iterationCount.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "iterationCount");

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 4) ||
             _tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 2))  {
            throw new Asn1UnexpectedElementException();
         }

      }
      if (explicit && llen == Asn1Status.INDEFLEN) {
         matchTag (buffer, Asn1Tag.EOC);
      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode iterationCount

      if (iterationCount != null) {
         len = iterationCount.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("iterationCount");

      // encode salt

      if (salt != null) {
         if (!(salt.getLength() == 8)) {
            throw new Asn1ConsVioException ("salt.getLength()", salt.getLength());
         }

         len = salt.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("salt");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintWriter _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (salt != null) salt.print (_out, "salt", _level+1);
      if (iterationCount != null) iterationCount.print (_out, "iterationCount", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }

}
