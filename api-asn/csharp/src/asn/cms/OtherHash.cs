// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.cms {

   public class OtherHash : Asn1Choice {
      // Choice element identifier constants
      public const byte _SHA1HASH = 1;
      public const byte _OTHERHASH = 2;

      static OtherHash ()
      {
         Asn1Type.SetKey2 (_etsi101733Values._rtkey);
      }

      public OtherHash () : base()
      {
      }

      public OtherHash (byte choiceId_, Asn1Type element_) : base()
      {
         SetElement (choiceId_, element_);
      }

      public override string ElemName {
         get {
            switch (choiceID) {
            case _SHA1HASH: return "sha1Hash";
            case _OTHERHASH: return "otherHash";
            default: return "UNDEFINED";
            }
         }
      }

      public void Set_sha1Hash (Asn1OctetString value) {
         SetElement (_SHA1HASH, value);
      }

      public void Set_otherHash (OtherHashAlgAndValue value) {
         SetElement (_OTHERHASH, value);
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
            Asn1OctetString sha1Hash;
            sha1Hash = new Asn1OctetString();
            sha1Hash.Decode (buffer, true, len);
            SetElement (_SHA1HASH, sha1Hash);
         }
         else if (tag.Equals (Asn1Tag.UNIV, Asn1Tag.CONS, 16))
         {
            buffer.Reset();
            OtherHashAlgAndValue otherHash;
            otherHash = new OtherHashAlgAndValue();
            otherHash.Decode (buffer, true, len);
            SetElement (_OTHERHASH, otherHash);
         }
         else {
            throw new Asn1InvalidChoiceOptionException (buffer, tag);
         }
      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;
         switch (choiceID) {
         // encode sha1Hash
         case _SHA1HASH:
            Asn1OctetString sha1Hash = (Asn1OctetString) GetElement();
            len = sha1Hash.Encode (buffer, true);
            _aal += len;
            break;

         // encode otherHash
         case _OTHERHASH:
            OtherHashAlgAndValue otherHash = (OtherHashAlgAndValue) GetElement();
            len = otherHash.Encode (buffer, true);
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
