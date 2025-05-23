/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.cms;

import com.objsys.asn1j.runtime.*;
import tr.gov.tubitak.uekae.esya.asn.cms.CertificateSet;
import tr.gov.tubitak.uekae.esya.asn.cms.RevocationInfoChoices;

public class LongTermValidation extends Asn1Type {
   private static final long serialVersionUID = 55;
   static {
      setKey (_etsi101733Rtkey._rtkey);
   }

   public Asn1GeneralizedTime poeDate;
   public LongTermValidation_poeValue poeValue;  // optional
   public CertificateSet extraCertificates;  // optional
   public RevocationInfoChoices extraRevocation;  // optional

   public LongTermValidation () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public LongTermValidation (
      Asn1GeneralizedTime poeDate_,
      LongTermValidation_poeValue poeValue_,
      CertificateSet extraCertificates_,
      RevocationInfoChoices extraRevocation_
   ) {
      super();
      poeDate = poeDate_;
      poeValue = poeValue_;
      extraCertificates = extraCertificates_;
      extraRevocation = extraRevocation_;
   }

   /**
    * This constructor is for required elements only.  It sets 
    * all elements to references to the given objects
    */
   public LongTermValidation (
      Asn1GeneralizedTime poeDate_
   ) {
      super();
      poeDate = poeDate_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public LongTermValidation (String poeDate_,
      LongTermValidation_poeValue poeValue_,
      CertificateSet extraCertificates_,
      RevocationInfoChoices extraRevocation_
   ) {
      super();
      poeDate = new Asn1GeneralizedTime (poeDate_);
      poeValue = poeValue_;
      extraCertificates = extraCertificates_;
      extraRevocation = extraRevocation_;
   }

   /**
    * This constructor is for required elements only.  It allows 
    * primitive data to be passed for all primitive elements.  
    * It will create new object wrappers for the primitive data 
    * and set other elements to references to the given objects. 
    */
   public LongTermValidation (
      String poeDate_
   ) {
      super();
      poeDate = new Asn1GeneralizedTime (poeDate_);
   }

   public void init () {
      poeDate = null;
      poeValue = null;
      extraCertificates = null;
      extraRevocation = null;
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

      // decode poeDate

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 24, elemLen, false)) {
         poeDate = new Asn1GeneralizedTime (true);
         poeDate.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "poeDate");

      // decode poeValue

      if (!_context.expired()) {
         Asn1Tag tag = buffer.peekTag ();
         if (tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0) ||
             tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 1))
         {
            poeValue = new LongTermValidation_poeValue();
            poeValue.decode (buffer, true, elemLen.value);
         }
      }

      // decode extraCertificates

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
         int offset = buffer.getByteCount();
         extraCertificates = new CertificateSet();
         extraCertificates.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }

      // decode extraRevocation

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 1, elemLen, true)) {
         int offset = buffer.getByteCount();
         extraRevocation = new RevocationInfoChoices();
         extraRevocation.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 24) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 1))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode extraRevocation

      if (extraRevocation != null) {
         len = extraRevocation.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 1, len);
         _aal += len;
      }

      // encode extraCertificates

      if (extraCertificates != null) {
         len = extraCertificates.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
         _aal += len;
      }

      // encode poeValue

      if (poeValue != null) {
         len = poeValue.encode (buffer, true);
         _aal += len;
      }

      // encode poeDate

      if (poeDate != null) {
         len = poeDate.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("poeDate");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (poeDate != null) poeDate.print (_out, "poeDate", _level+1);
      if (poeValue != null) poeValue.print (_out, "poeValue", _level+1);
      if (extraCertificates != null) extraCertificates.print (_out, "extraCertificates", _level+1);
      if (extraRevocation != null) extraRevocation.print (_out, "extraRevocation", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
