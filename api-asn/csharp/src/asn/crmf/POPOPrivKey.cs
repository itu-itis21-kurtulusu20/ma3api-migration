// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.crmf {

   public class POPOPrivKey : Asn1Choice {
      // Choice element identifier constants
      public const byte _THISMESSAGE = 1;
      public const byte _SUBSEQUENTMESSAGE = 2;
      public const byte _DHMAC = 3;

      static POPOPrivKey ()
      {
         Asn1Type.SetKey2 (_crmfValues._rtkey);
      }

      public POPOPrivKey () : base()
      {
      }

      public POPOPrivKey (byte choiceId_, Asn1Type element_) : base()
      {
         SetElement (choiceId_, element_);
      }

      public override string ElemName {
         get {
            switch (choiceID) {
            case _THISMESSAGE: return "thisMessage";
            case _SUBSEQUENTMESSAGE: return "subsequentMessage";
            case _DHMAC: return "dhMAC";
            default: return "UNDEFINED";
            }
         }
      }

      public void Set_thisMessage (Asn1BitString value) {
         SetElement (_THISMESSAGE, value);
      }

      public void Set_subsequentMessage (SubsequentMessage value) {
         SetElement (_SUBSEQUENTMESSAGE, value);
      }

      public void Set_dhMAC (Asn1BitString value) {
         SetElement (_DHMAC, value);
      }

      public override void Decode
         (Asn1BerDecodeBuffer buffer, bool explicitTagging, int implicitLength)
      {
         int llen = implicitLength;

         // decode CHOICE

         Asn1Tag tag = new Asn1Tag ();
         buffer.Mark ();
         int len = buffer.DecodeTagAndLength (tag);

         if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 0))
         {
            Asn1BitString thisMessage;
            thisMessage = new Asn1BitString();
            thisMessage.Decode (buffer, false, len);
            SetElement (_THISMESSAGE, thisMessage);
         }
         else if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 1))
         {
            SubsequentMessage subsequentMessage;
            subsequentMessage = new SubsequentMessage();
            subsequentMessage.Decode (buffer, false, len);
            SetElement (_SUBSEQUENTMESSAGE, subsequentMessage);
         }
         else if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 2))
         {
            Asn1BitString dhMAC;
            dhMAC = new Asn1BitString();
            dhMAC.Decode (buffer, false, len);
            SetElement (_DHMAC, dhMAC);
         }
         else {
            throw new Asn1InvalidChoiceOptionException (buffer, tag);
         }
      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;
         switch (choiceID) {
         // encode thisMessage
         case _THISMESSAGE:
            Asn1BitString thisMessage = (Asn1BitString) GetElement();
            len = thisMessage.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 0, len);
            _aal += len;
            break;

         // encode subsequentMessage
         case _SUBSEQUENTMESSAGE:
            SubsequentMessage subsequentMessage = (SubsequentMessage) GetElement();
            len = subsequentMessage.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 1, len);
            _aal += len;
            break;

         // encode dhMAC
         case _DHMAC:
            Asn1BitString dhMAC = (Asn1BitString) GetElement();
            len = dhMAC.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 2, len);
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
