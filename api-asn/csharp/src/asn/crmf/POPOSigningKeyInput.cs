// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.crmf {

   using SubjectPublicKeyInfo = tr.gov.tubitak.uekae.esya.asn.x509.SubjectPublicKeyInfo;

   public class POPOSigningKeyInput : Asn1Type {
      public POPOSigningKeyInput_authInfo authInfo;
      public SubjectPublicKeyInfo publicKey;

      static POPOSigningKeyInput ()
      {
         Asn1Type.SetKey2 (_crmfValues._rtkey);
      }

      public POPOSigningKeyInput () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public POPOSigningKeyInput (
         POPOSigningKeyInput_authInfo authInfo_,
         SubjectPublicKeyInfo publicKey_
      )
         : base ()
      {
         authInfo = authInfo_;
         publicKey = publicKey_;
      }

      public void Init () {
         authInfo = null;
         publicKey = null;
      }

      public override void Decode
         (Asn1BerDecodeBuffer buffer, bool explicitTagging, int implicitLength)
      {
         int llen = (explicitTagging) ?
            MatchTag (buffer, Asn1Tag.SEQUENCE) : implicitLength;

         Init ();

         // decode SEQUENCE

         Asn1BerDecodeContext _context =
            new Asn1BerDecodeContext (buffer, llen);

         IntHolder elemLen = new IntHolder();

         // decode authInfo

         if (!_context.Expired()) {
            Asn1Tag tag = buffer.PeekTag ();
            if (tag.Equals (Asn1Tag.UNIV, Asn1Tag.CONS, 16) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0))
            {
               authInfo = new POPOSigningKeyInput_authInfo();
               authInfo.Decode (buffer, true, elemLen.mValue);
            }
            else throw new Asn1MissingRequiredException (buffer);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode publicKey

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            publicKey = new SubjectPublicKeyInfo();
            publicKey.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode publicKey

         len = publicKey.Encode (buffer, true);
         _aal += len;

         // encode authInfo

         len = authInfo.Encode (buffer, true);
         _aal += len;

         if (explicitTagging) {
            _aal += buffer.EncodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
         }

         return (_aal);
      }

      public override void Print (System.IO.TextWriter _out, 
                                  string _varName, int _level)
      {
         Indent (_out, _level);
         _out.WriteLine (_varName + " {");
         if (authInfo != null) authInfo.Print (_out, "authInfo", _level+1);
         if (publicKey != null) publicKey.Print (_out, "publicKey", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
