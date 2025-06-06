/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 7.0.0, Date: 18-Mar-2016.
 */
package tr.gov.tubitak.uekae.esya.asn.cms;

import com.objsys.asn1j.runtime.*;

public class OriginatorInfo extends Asn1Seq {
   private static final long serialVersionUID = 55;
   static {
      setKey (_cmsRtkey._rtkey);
   }

   public String getAsn1TypeName()  {
      return "OriginatorInfo";
   }

   public CertificateSet certs;  // optional
   public RevocationInfoChoices crls;  // optional

   public OriginatorInfo () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public OriginatorInfo (
      CertificateSet certs_,
      RevocationInfoChoices crls_
   ) {
      super();
      certs = certs_;
      crls = crls_;
   }

   public void init () {
      certs = null;
      crls = null;
   }

   public int getElementCount() { return 2; }


   public Object getElementValue(int index){
      switch(index)  {
         case 0: return certs;
         case 1: return crls;
         default: return null;
      }
   }


   public String getElementName(int index){
      switch(index)  {
         case 0: return "certs";
         case 1: return "crls";
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

      // decode certs

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
         int offset = buffer.getByteCount();
         certs = new CertificateSet();
         certs.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

         if (elemLen.value == Asn1Status.INDEFLEN) {
            matchTag (buffer, Asn1Tag.EOC);
         }
      }

      // decode crls

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 1, elemLen, true)) {
         int offset = buffer.getByteCount();
         crls = new RevocationInfoChoices();
         crls.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

         if (elemLen.value == Asn1Status.INDEFLEN) {
            matchTag (buffer, Asn1Tag.EOC);
         }
      }

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 1))  {
            throw new Asn1SeqOrderException ();
         }
         else  {
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

      // encode crls

      if (crls != null) {
         len = crls.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 1, len);
         _aal += len;
      }

      // encode certs

      if (certs != null) {
         len = certs.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
         _aal += len;
      }

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintWriter _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (certs != null) certs.print (_out, "certs", _level+1);
      if (crls != null) crls.print (_out, "crls", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }

}
