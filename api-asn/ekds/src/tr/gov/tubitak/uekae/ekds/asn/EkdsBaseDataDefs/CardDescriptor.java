/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.2.5-pre5, Date: 24-Mar-2011.
 */
package tr.gov.tubitak.uekae.ekds.asn.EkdsBaseDataDefs;

import com.objsys.asn1j.runtime.*;
import java.io.*;

public class CardDescriptor extends Asn1Type {
   public Asn1UTF8String cardSerialNo;
   public Asn1NumericString cardHolderID;

   public CardDescriptor () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public CardDescriptor (
      Asn1UTF8String cardSerialNo_,
      Asn1NumericString cardHolderID_
   ) {
      super();
      cardSerialNo = cardSerialNo_;
      cardHolderID = cardHolderID_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public CardDescriptor (String cardSerialNo_,
      String cardHolderID_
   ) {
      super();
      cardSerialNo = new Asn1UTF8String (cardSerialNo_);
      cardHolderID = new Asn1NumericString (cardHolderID_);
   }

   public void init () {
      cardSerialNo = null;
      cardHolderID = null;
   }

   public void decode
      (Asn1BerDecodeBuffer buffer, boolean explicit, int implicitLength)
      throws Asn1Exception, IOException
   {
      int llen = (explicit) ?
         matchTag (buffer, Asn1Tag.SEQUENCE) : implicitLength;

      init ();

      // decode SEQUENCE

      Asn1BerDecodeContext _context =
         new Asn1BerDecodeContext (buffer, llen);

      IntHolder elemLen = new IntHolder();

      // decode cardSerialNo

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 0, elemLen, true)) {
         cardSerialNo = new Asn1UTF8String();
         cardSerialNo.decode (buffer, false, elemLen.value);
         if (!((cardSerialNo.getLength() >= 0 && cardSerialNo.getLength() <= 24))) {
            throw new Asn1ConsVioException ("cardSerialNo.getLength()", cardSerialNo.getLength());
         }

      }
      else throw new Asn1MissingRequiredException (buffer);

      // decode cardHolderID

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 1, elemLen, true)) {
         cardHolderID = new Asn1NumericString();
         cardHolderID.decode (buffer, false, elemLen.value);
         if (!((cardHolderID.getLength() >= 1 && cardHolderID.getLength() <= 12))) {
            throw new Asn1ConsVioException ("cardHolderID.getLength()", cardHolderID.getLength());
         }

      }
      else throw new Asn1MissingRequiredException (buffer);

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 0) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 1))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode cardHolderID

      if (!((cardHolderID.getLength() >= 1 && cardHolderID.getLength() <= 12))) {
         throw new Asn1ConsVioException ("cardHolderID.getLength()", cardHolderID.getLength());
      }

      len = cardHolderID.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 1, len);
      _aal += len;

      // encode cardSerialNo

      if (!((cardSerialNo.getLength() >= 0 && cardSerialNo.getLength() <= 24))) {
         throw new Asn1ConsVioException ("cardSerialNo.getLength()", cardSerialNo.getLength());
      }

      len = cardSerialNo.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 0, len);
      _aal += len;

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (cardSerialNo != null) cardSerialNo.print (_out, "cardSerialNo", _level+1);
      if (cardHolderID != null) cardHolderID.print (_out, "cardHolderID", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
