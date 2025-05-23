/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.2.5-pre5, Date: 24-Mar-2011.
 */
package tr.gov.tubitak.uekae.ekds.asn.EkdsRemoteAuthenticationApp;

import com.objsys.asn1j.runtime.*;
import java.io.*;
import tr.gov.tubitak.uekae.ekds.asn.EkdsCommonDataObjectDefs.DataObjectDigitalSignature;

public class IdentityVerificationAssertionDataObject extends Asn1Type {
   public final static Asn1Tag TAG =
      new Asn1Tag (Asn1Tag.APPL, Asn1Tag.CONS, 9);

   public IVAData ivaData;
   public DataObjectDigitalSignature samSignature;
   public DataObjectDigitalSignature cardSignature;

   public IdentityVerificationAssertionDataObject () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public IdentityVerificationAssertionDataObject (
      IVAData ivaData_,
      DataObjectDigitalSignature samSignature_,
      DataObjectDigitalSignature cardSignature_
   ) {
      super();
      ivaData = ivaData_;
      samSignature = samSignature_;
      cardSignature = cardSignature_;
   }

   public void init () {
      ivaData = null;
      samSignature = null;
      cardSignature = null;
   }

   public void decode
      (Asn1BerDecodeBuffer buffer, boolean explicit, int implicitLength)
      throws Asn1Exception, IOException
   {
      int llen = (explicit) ?
         matchTag (buffer, TAG) : implicitLength;

      init ();

      // decode SEQUENCE

      Asn1BerDecodeContext _context =
         new Asn1BerDecodeContext (buffer, llen);

      IntHolder elemLen = new IntHolder();

      // decode ivaData

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
         ivaData = new IVAData();
         ivaData.decode (buffer, false, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer);

      // decode samSignature

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 1, elemLen, true)) {
         samSignature = new DataObjectDigitalSignature();
         samSignature.decode (buffer, false, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer);

      // decode cardSignature

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 2, elemLen, true)) {
         cardSignature = new DataObjectDigitalSignature();
         cardSignature.decode (buffer, false, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer);

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 1) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 2))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode cardSignature

      len = cardSignature.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 2, len);
      _aal += len;

      // encode samSignature

      len = samSignature.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 1, len);
      _aal += len;

      // encode ivaData

      len = ivaData.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
      _aal += len;

      if (explicit) {
         _aal += buffer.encodeTagAndLength (TAG, _aal);
      }

      return (_aal);
   }

   public void print (PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (ivaData != null) ivaData.print (_out, "ivaData", _level+1);
      if (samSignature != null) samSignature.print (_out, "samSignature", _level+1);
      if (cardSignature != null) cardSignature.print (_out, "cardSignature", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
