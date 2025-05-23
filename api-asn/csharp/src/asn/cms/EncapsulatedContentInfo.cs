// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.cms {

   public class EncapsulatedContentInfo : Asn1Type {
      public Asn1ObjectIdentifier eContentType;
      public Asn1OctetString eContent;  // optional

      static EncapsulatedContentInfo ()
      {
         Asn1Type.SetKey2 (_cmsValues._rtkey);
      }

      public EncapsulatedContentInfo () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public EncapsulatedContentInfo (
         Asn1ObjectIdentifier eContentType_,
         Asn1OctetString eContent_
      )
         : base ()
      {
         eContentType = eContentType_;
         eContent = eContent_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It sets 
      /// all elements to references to the given objects
      /// </summary>
      public EncapsulatedContentInfo (
         Asn1ObjectIdentifier eContentType_
      )
         : base ()
      {
         eContentType = eContentType_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public EncapsulatedContentInfo (int[] eContentType_,
         byte[] eContent_
      )
         : base ()
      {
         eContentType = new Asn1ObjectIdentifier (eContentType_);
         eContent = new Asn1OctetString (eContent_);
      }

      /// <summary>
      /// This constructor is for required elements only.  It allows 
      /// primitive data to be passed for all primitive elements.  
      /// It will create new object wrappers for the primitive data 
      /// and set other elements to references to the given objects. 
      /// </summary>
      public EncapsulatedContentInfo (
         int[] eContentType_
      )
         : base ()
      {
         eContentType = new Asn1ObjectIdentifier (eContentType_);
      }

      public void Init () {
         eContentType = null;
         eContent = null;
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

         // decode eContentType

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 6, elemLen, false)) {
            eContentType = new Asn1ObjectIdentifier();
            eContentType.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode eContent

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
            int offset = buffer.ByteCount;
            eContent = new Asn1OctetString();
            eContent.Decode (buffer, true, elemLen.mValue);
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

         // encode eContent

         if (eContent != null) {
            len = eContent.Encode (buffer, true);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
            _aal += len;
         }

         // encode eContentType

         len = eContentType.Encode (buffer, true);
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
         if (eContentType != null) eContentType.Print (_out, "eContentType", _level+1);
         if (eContent != null) eContent.Print (_out, "eContent", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
