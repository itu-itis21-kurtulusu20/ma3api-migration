// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.cms {

   public class OriginatorInfo : Asn1Type {
      public CertificateSet certs;  // optional
      public RevocationInfoChoices crls;  // optional

      static OriginatorInfo ()
      {
         Asn1Type.SetKey2 (_cmsValues._rtkey);
      }

      public OriginatorInfo () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public OriginatorInfo (
         CertificateSet certs_,
         RevocationInfoChoices crls_
      )
         : base ()
      {
         certs = certs_;
         crls = crls_;
      }

      public void Init () {
         certs = null;
         crls = null;
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

         // decode certs

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
            int offset = buffer.ByteCount;
            certs = new CertificateSet();
            certs.Decode (buffer, false, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
            if (elemLen.mValue == Asn1Status.INDEFLEN) {
               MatchTag (buffer, Asn1Tag.EOC);
            }
         }

         // decode crls

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 1, elemLen, true)) {
            int offset = buffer.ByteCount;
            crls = new RevocationInfoChoices();
            crls.Decode (buffer, false, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
            if (elemLen.mValue == Asn1Status.INDEFLEN) {
               MatchTag (buffer, Asn1Tag.EOC);
            }
         }

         if (explicitTagging && llen == Asn1Status.INDEFLEN) {
            MatchTag (buffer, Asn1Tag.EOC);
         }
      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode crls

         if (crls != null) {
            len = crls.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 1, len);
            _aal += len;
         }

         // encode certs

         if (certs != null) {
            len = certs.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
            _aal += len;
         }

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
         if (certs != null) certs.Print (_out, "certs", _level+1);
         if (crls != null) crls.Print (_out, "crls", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
