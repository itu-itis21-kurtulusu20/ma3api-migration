/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.cms;

import com.objsys.asn1j.runtime.*;

public class OtherRevVals extends Asn1Type {
   private static final long serialVersionUID = 55;
   static {
      setKey (_etsi101733Rtkey._rtkey);
   }

   public Asn1ObjectIdentifier otherRevValType;
   public OtherRevVals_otherRevVals otherRevVals;

   public OtherRevVals () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public OtherRevVals (
      Asn1ObjectIdentifier otherRevValType_,
      OtherRevVals_otherRevVals otherRevVals_
   ) {
      super();
      otherRevValType = otherRevValType_;
      otherRevVals = otherRevVals_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public OtherRevVals (int[] otherRevValType_,
      OtherRevVals_otherRevVals otherRevVals_
   ) {
      super();
      otherRevValType = new Asn1ObjectIdentifier (otherRevValType_);
      otherRevVals = otherRevVals_;
   }

   public void init () {
      otherRevValType = null;
      otherRevVals = null;
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

      // decode otherRevValType

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 6, elemLen, false)) {
         otherRevValType = new Asn1ObjectIdentifier();
         otherRevValType.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "otherRevValType");

      // decode otherRevVals

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         otherRevVals = new OtherRevVals_otherRevVals();
         otherRevVals.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "otherRevVals");

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 6) ||
             _tag.equals (Asn1Tag.UNIV, Asn1Tag.CONS, 16))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode otherRevVals

      if (otherRevVals != null) {
         len = otherRevVals.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("otherRevVals");

      // encode otherRevValType

      if (otherRevValType != null) {
         len = otherRevValType.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("otherRevValType");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (otherRevValType != null) otherRevValType.print (_out, "otherRevValType", _level+1);
      if (otherRevVals != null) otherRevVals.print (_out, "otherRevVals", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
