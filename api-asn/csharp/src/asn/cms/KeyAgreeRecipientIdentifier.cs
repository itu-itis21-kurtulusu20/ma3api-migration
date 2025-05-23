// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.cms {

   public class KeyAgreeRecipientIdentifier : Asn1Choice {
      // Choice element identifier constants
      public const byte _ISSUERANDSERIALNUMBER = 1;
      public const byte _RKEYID = 2;

      static KeyAgreeRecipientIdentifier ()
      {
         Asn1Type.SetKey2 (_cmsValues._rtkey);
      }

      public KeyAgreeRecipientIdentifier () : base()
      {
      }

      public KeyAgreeRecipientIdentifier (byte choiceId_, Asn1Type element_) : base()
      {
         SetElement (choiceId_, element_);
      }

      public override string ElemName {
         get {
            switch (choiceID) {
            case _ISSUERANDSERIALNUMBER: return "issuerAndSerialNumber";
            case _RKEYID: return "rKeyId";
            default: return "UNDEFINED";
            }
         }
      }

      public void Set_issuerAndSerialNumber (IssuerAndSerialNumber value) {
         SetElement (_ISSUERANDSERIALNUMBER, value);
      }

      public void Set_rKeyId (RecipientKeyIdentifier value) {
         SetElement (_RKEYID, value);
      }

      public override void Decode
         (Asn1BerDecodeBuffer buffer, bool explicitTagging, int implicitLength)
      {
         int llen = implicitLength;

         // decode CHOICE

         Asn1Tag tag = new Asn1Tag ();
         buffer.Mark ();
         int len = buffer.DecodeTagAndLength (tag);

         if (tag.Equals (Asn1Tag.UNIV, Asn1Tag.CONS, 16))
         {
            buffer.Reset();
            IssuerAndSerialNumber issuerAndSerialNumber;
            issuerAndSerialNumber = new IssuerAndSerialNumber();
            issuerAndSerialNumber.Decode (buffer, true, len);
            SetElement (_ISSUERANDSERIALNUMBER, issuerAndSerialNumber);
         }
         else if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0))
         {
            RecipientKeyIdentifier rKeyId;
            rKeyId = new RecipientKeyIdentifier();
            rKeyId.Decode (buffer, false, len);
            SetElement (_RKEYID, rKeyId);
            if (len == Asn1Status.INDEFLEN) {
               MatchTag (buffer, Asn1Tag.EOC);
            }
         }
         else {
            throw new Asn1InvalidChoiceOptionException (buffer, tag);
         }
      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;
         switch (choiceID) {
         // encode issuerAndSerialNumber
         case _ISSUERANDSERIALNUMBER:
            IssuerAndSerialNumber issuerAndSerialNumber = (IssuerAndSerialNumber) GetElement();
            len = issuerAndSerialNumber.Encode (buffer, true);
            _aal += len;
            break;

         // encode rKeyId
         case _RKEYID:
            RecipientKeyIdentifier rKeyId = (RecipientKeyIdentifier) GetElement();
            len = rKeyId.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
            _aal += len;
            break;

         default:
            throw new Asn1InvalidChoiceOptionException();
         }

         return _aal;
      }

      public override void Print (System.IO.TextWriter _out, 
                                  string _varName, int _level)
      {
         Indent (_out, _level);
         _out.WriteLine (_varName + " {");
         if (element != null) {
            element.Print (_out, ElemName, _level+1);
         }
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
