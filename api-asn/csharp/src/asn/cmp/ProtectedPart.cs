// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.cmp {

   public class ProtectedPart : Asn1Type {
      public PKIHeader header;
      public PKIBody body;

      static ProtectedPart ()
      {
         Asn1Type.SetKey2 (_cmpValues._rtkey);
      }

      public ProtectedPart () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public ProtectedPart (
         PKIHeader header_,
         PKIBody body_
      )
         : base ()
      {
         header = header_;
         body = body_;
      }

      public void Init () {
         header = null;
         body = null;
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

         // decode header

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            header = new PKIHeader();
            header.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode body

         if (!_context.Expired()) {
            Asn1Tag tag = buffer.PeekTag ();
            if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 1) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 2) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 3) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 4) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 5) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 6) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 7) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 8) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 9) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 10) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 11) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 12) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 13) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 14) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 15) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 16) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 17) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 18) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 19) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 20) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 21) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 22) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 23) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 24) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 25) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 26) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 27))
            {
               body = new PKIBody();
               body.Decode (buffer, true, elemLen.mValue);
            }
            else throw new Asn1MissingRequiredException (buffer);
         }
         else throw new Asn1MissingRequiredException (buffer);

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode body

         len = body.Encode (buffer, true);
         _aal += len;

         // encode header

         len = header.Encode (buffer, true);
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
         if (header != null) header.Print (_out, "header", _level+1);
         if (body != null) body.Print (_out, "body", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
