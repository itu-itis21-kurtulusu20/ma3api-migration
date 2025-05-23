// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.cmp {

   using Certificate = tr.gov.tubitak.uekae.esya.asn.x509.Certificate;
   using SelfDescCVC = tr.gov.tubitak.uekae.esya.asn.cvc.SelfDescCVC;
   using NonSelfDescCVCwithHeader = tr.gov.tubitak.uekae.esya.asn.cvc.NonSelfDescCVCwithHeader;

   public class CMPCertificate : Asn1Choice {
      // Choice element identifier constants
      public const byte _X509V3PKCERT = 1;
      public const byte _SELFDESCCVC = 2;
      public const byte _NONSELFDESCCVCWITHHEADER = 3;

      static CMPCertificate ()
      {
         Asn1Type.SetKey2 (_cmpValues._rtkey);
      }

      public CMPCertificate () : base()
      {
      }

      public CMPCertificate (byte choiceId_, Asn1Type element_) : base()
      {
         SetElement (choiceId_, element_);
      }

      public override string ElemName {
         get {
            switch (choiceID) {
            case _X509V3PKCERT: return "x509v3PKCert";
            case _SELFDESCCVC: return "selfDescCVC";
            case _NONSELFDESCCVCWITHHEADER: return "nonSelfDescCVCwithHeader";
            default: return "UNDEFINED";
            }
         }
      }

      public void Set_x509v3PKCert (Certificate value) {
         SetElement (_X509V3PKCERT, value);
      }

      public void Set_selfDescCVC (SelfDescCVC value) {
         SetElement (_SELFDESCCVC, value);
      }

      public void Set_nonSelfDescCVCwithHeader (NonSelfDescCVCwithHeader value) {
         SetElement (_NONSELFDESCCVCWITHHEADER, value);
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
            Certificate x509v3PKCert;
            x509v3PKCert = new Certificate();
            x509v3PKCert.Decode (buffer, true, len);
            SetElement (_X509V3PKCERT, x509v3PKCert);
         }
         else if (tag.Equals (Asn1Tag.APPL, Asn1Tag.CONS, 33))
         {
            buffer.Reset();
            SelfDescCVC selfDescCVC;
            selfDescCVC = new SelfDescCVC();
            selfDescCVC.Decode (buffer, true, len);
            SetElement (_SELFDESCCVC, selfDescCVC);
         }
         else if (tag.Equals (Asn1Tag.APPL, Asn1Tag.CONS, 34))
         {
            buffer.Reset();
            NonSelfDescCVCwithHeader nonSelfDescCVCwithHeader;
            nonSelfDescCVCwithHeader = new NonSelfDescCVCwithHeader();
            nonSelfDescCVCwithHeader.Decode (buffer, true, len);
            SetElement (_NONSELFDESCCVCWITHHEADER, nonSelfDescCVCwithHeader);
         }
         else {
            throw new Asn1InvalidChoiceOptionException (buffer, tag);
         }
      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;
         switch (choiceID) {
         // encode x509v3PKCert
         case _X509V3PKCERT:
            Certificate x509v3PKCert = (Certificate) GetElement();
            len = x509v3PKCert.Encode (buffer, true);
            _aal += len;
            break;

         // encode selfDescCVC
         case _SELFDESCCVC:
            SelfDescCVC selfDescCVC = (SelfDescCVC) GetElement();
            len = selfDescCVC.Encode (buffer, true);
            _aal += len;
            break;

         // encode nonSelfDescCVCwithHeader
         case _NONSELFDESCCVCWITHHEADER:
            NonSelfDescCVCwithHeader nonSelfDescCVCwithHeader = (NonSelfDescCVCwithHeader) GetElement();
            len = nonSelfDescCVCwithHeader.Encode (buffer, true);
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
