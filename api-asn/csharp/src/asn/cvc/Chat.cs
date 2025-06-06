// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.cvc {

   public class Chat : Asn1Type {
      public new static readonly Asn1Tag _TAG = new Asn1Tag (Asn1Tag.APPL, Asn1Tag.CONS, 76);

      public Asn1OctetString algId;
      public Asn1OctetString discretionaryDO;

      static Chat ()
      {
         Asn1Type.SetKey2 (_cvcValues._rtkey);
      }

      public Chat () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public Chat (
         Asn1OctetString algId_,
         Asn1OctetString discretionaryDO_
      )
         : base ()
      {
         algId = algId_;
         discretionaryDO = discretionaryDO_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public Chat (byte[] algId_,
         byte[] discretionaryDO_
      )
         : base ()
      {
         algId = new Asn1OctetString (algId_);
         discretionaryDO = new Asn1OctetString (discretionaryDO_);
      }

      public void Init () {
         algId = null;
         discretionaryDO = null;
      }

      public override void Decode
         (Asn1BerDecodeBuffer buffer, bool explicitTagging, int implicitLength)
      {
         int llen = (explicitTagging) ?
            MatchTag (buffer, _TAG) : implicitLength;

         Init ();

         // decode SEQUENCE

         Asn1BerDecodeContext _context =
            new Asn1BerDecodeContext (buffer, llen);

         IntHolder elemLen = new IntHolder();

         // decode algId

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 6, elemLen, true)) {
            int offset = buffer.ByteCount;
            algId = new Asn1OctetString();
            algId.Decode (buffer, false, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode discretionaryDO

         if (_context.MatchElemTag (Asn1Tag.APPL, Asn1Tag.PRIM, 19, elemLen, true)) {
            int offset = buffer.ByteCount;
            discretionaryDO = new Asn1OctetString();
            discretionaryDO.Decode (buffer, false, elemLen.mValue);
            if (!(discretionaryDO.Length == 1)) {
               throw new Asn1ConsVioException ("discretionaryDO.Length", discretionaryDO.Length);
            }

            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }
         else throw new Asn1MissingRequiredException (buffer);

         if (explicitTagging && llen == Asn1Status.INDEFLEN) {
            MatchTag (buffer, Asn1Tag.EOC);
         }
      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode discretionaryDO

         if (!(discretionaryDO.Length == 1)) {
            throw new Asn1ConsVioException ("discretionaryDO.Length", discretionaryDO.Length);
         }

         len = discretionaryDO.Encode (buffer, false);
         len += buffer.EncodeTagAndLength (Asn1Tag.APPL, Asn1Tag.PRIM, 19, len);
         _aal += len;

         // encode algId

         len = algId.Encode (buffer, false);
         len += buffer.EncodeTagAndLength (Asn1Tag.UNIV, Asn1Tag.PRIM, 6, len);
         _aal += len;

         if (explicitTagging) {
            _aal += buffer.EncodeTagAndLength (_TAG, _aal);
         }

         return (_aal);
      }

      public override void Print (System.IO.TextWriter _out, 
                                  string _varName, int _level)
      {
         Indent (_out, _level);
         _out.WriteLine (_varName + " {");
         if (algId != null) algId.Print (_out, "algId", _level+1);
         if (discretionaryDO != null) discretionaryDO.Print (_out, "discretionaryDO", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
