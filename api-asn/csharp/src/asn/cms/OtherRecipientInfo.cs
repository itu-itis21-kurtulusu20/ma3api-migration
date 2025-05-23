// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.cms {

   public class OtherRecipientInfo : Asn1Type {
      public Asn1ObjectIdentifier oriType;
      public Asn1OpenType oriValue;

      static OtherRecipientInfo ()
      {
         Asn1Type.SetKey2 (_cmsValues._rtkey);
      }

      public OtherRecipientInfo () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public OtherRecipientInfo (
         Asn1ObjectIdentifier oriType_,
         Asn1OpenType oriValue_
      )
         : base ()
      {
         oriType = oriType_;
         oriValue = oriValue_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public OtherRecipientInfo (int[] oriType_,
         Asn1OpenType oriValue_
      )
         : base ()
      {
         oriType = new Asn1ObjectIdentifier (oriType_);
         oriValue = oriValue_;
      }

      public void Init () {
         oriType = null;
         oriValue = null;
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

         // decode oriType

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 6, elemLen, false)) {
            oriType = new Asn1ObjectIdentifier();
            oriType.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode oriValue

         if (!_context.Expired ()) {
            oriValue = new Asn1OpenType();
            oriValue.Decode (buffer, true, 0);
         }
         else throw new Asn1MissingRequiredException (buffer);

         if (explicitTagging && llen == Asn1Status.INDEFLEN) {
            MatchTag (buffer, Asn1Tag.EOC);
         }
      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode oriValue

         len = oriValue.Encode (buffer, true);
         _aal += len;

         // encode oriType

         len = oriType.Encode (buffer, true);
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
         if (oriType != null) oriType.Print (_out, "oriType", _level+1);
         if (oriValue != null) oriValue.Print (_out, "oriValue", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
