// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.attrcert {

   public class IetfAttrSyntax_values_element : Asn1Choice {
      // Choice element identifier constants
      public const byte _OCTETS = 1;
      public const byte _OID = 2;
      public const byte _STRING_ = 3;

      static IetfAttrSyntax_values_element ()
      {
         Asn1Type.SetKey2 (_attrcertValues._rtkey);
      }

      public IetfAttrSyntax_values_element () : base()
      {
      }

      public IetfAttrSyntax_values_element (byte choiceId_, Asn1Type element_) : base()
      {
         SetElement (choiceId_, element_);
      }

      public override string ElemName {
         get {
            switch (choiceID) {
            case _OCTETS: return "octets";
            case _OID: return "oid";
            case _STRING_: return "string_";
            default: return "UNDEFINED";
            }
         }
      }

      public void Set_octets (Asn1OctetString value) {
         SetElement (_OCTETS, value);
      }

      public void Set_oid (Asn1ObjectIdentifier value) {
         SetElement (_OID, value);
      }

      public void Set_string_ (Asn1UTF8String value) {
         SetElement (_STRING_, value);
      }

      public override void Decode
         (Asn1BerDecodeBuffer buffer, bool explicitTagging, int implicitLength)
      {
         int llen = implicitLength;

         // decode CHOICE

         Asn1Tag tag = new Asn1Tag ();
         buffer.Mark ();
         int len = buffer.DecodeTagAndLength (tag);

         if (tag.Equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 4))
         {
            buffer.Reset();
            Asn1OctetString octets;
            octets = new Asn1OctetString();
            octets.Decode (buffer, true, len);
            SetElement (_OCTETS, octets);
         }
         else if (tag.Equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 6))
         {
            buffer.Reset();
            Asn1ObjectIdentifier oid;
            oid = new Asn1ObjectIdentifier();
            oid.Decode (buffer, true, len);
            SetElement (_OID, oid);
         }
         else if (tag.Equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 12))
         {
            buffer.Reset();
            Asn1UTF8String string_;
            string_ = new Asn1UTF8String();
            string_.Decode (buffer, true, len);
            SetElement (_STRING_, string_);
         }
         else {
            throw new Asn1InvalidChoiceOptionException (buffer, tag);
         }
      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;
         switch (choiceID) {
         // encode octets
         case _OCTETS:
            Asn1OctetString octets = (Asn1OctetString) GetElement();
            len = octets.Encode (buffer, true);
            _aal += len;
            break;

         // encode oid
         case _OID:
            Asn1ObjectIdentifier oid = (Asn1ObjectIdentifier) GetElement();
            len = oid.Encode (buffer, true);
            _aal += len;
            break;

         // encode string_
         case _STRING_:
            Asn1UTF8String string_ = (Asn1UTF8String) GetElement();
            len = string_.Encode (buffer, true);
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
