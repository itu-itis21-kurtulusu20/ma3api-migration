/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.cms;

import com.objsys.asn1j.runtime.*;

public class OtherRecipientInfo extends Asn1Type {
   private static final long serialVersionUID = 55;
   static {
      setKey (_cmsRtkey._rtkey);
   }

   public Asn1ObjectIdentifier oriType;
   public Asn1OpenType oriValue;

   public OtherRecipientInfo () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public OtherRecipientInfo (
      Asn1ObjectIdentifier oriType_,
      Asn1OpenType oriValue_
   ) {
      super();
      oriType = oriType_;
      oriValue = oriValue_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public OtherRecipientInfo (int[] oriType_,
      Asn1OpenType oriValue_
   ) {
      super();
      oriType = new Asn1ObjectIdentifier (oriType_);
      oriValue = oriValue_;
   }

   public void init () {
      oriType = null;
      oriValue = null;
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

      // decode oriType

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 6, elemLen, false)) {
         oriType = new Asn1ObjectIdentifier();
         oriType.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "oriType");

      // decode oriValue

      if (!_context.expired ()) {
         oriValue = new Asn1OpenType();
         oriValue.decode (buffer, true, 0);
      }
      else throw new Asn1MissingRequiredException (buffer);

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 6))
            throw new Asn1SeqOrderException ();

      }
      if (explicit && llen == Asn1Status.INDEFLEN) {
         matchTag (buffer, Asn1Tag.EOC);
      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode oriValue

      if (oriValue != null) {
         len = oriValue.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("oriValue");

      // encode oriType

      if (oriType != null) {
         len = oriType.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("oriType");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (oriType != null) oriType.print (_out, "oriType", _level+1);
      if (oriValue != null) oriValue.print (_out, "oriValue", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
