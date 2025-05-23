// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.depo {

   public class DepoASNRawImza : Asn1Type {
      public Asn1OctetString publicKeyHash;
      public Asn1OctetString imza;

      static DepoASNRawImza ()
      {
         Asn1Type.SetKey2 (_depoValues._rtkey);
      }

      public DepoASNRawImza () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public DepoASNRawImza (
         Asn1OctetString publicKeyHash_,
         Asn1OctetString imza_
      )
         : base ()
      {
         publicKeyHash = publicKeyHash_;
         imza = imza_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public DepoASNRawImza (byte[] publicKeyHash_,
         byte[] imza_
      )
         : base ()
      {
         publicKeyHash = new Asn1OctetString (publicKeyHash_);
         imza = new Asn1OctetString (imza_);
      }

      public void Init () {
         publicKeyHash = null;
         imza = null;
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

         // decode publicKeyHash

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 0, elemLen, true)) {
            int offset = buffer.ByteCount;
            publicKeyHash = new Asn1OctetString();
            publicKeyHash.Decode (buffer, false, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode imza

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 1, elemLen, true)) {
            int offset = buffer.ByteCount;
            imza = new Asn1OctetString();
            imza.Decode (buffer, false, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }
         else throw new Asn1MissingRequiredException (buffer);

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode imza

         len = imza.Encode (buffer, false);
         len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 1, len);
         _aal += len;

         // encode publicKeyHash

         len = publicKeyHash.Encode (buffer, false);
         len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 0, len);
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
         if (publicKeyHash != null) publicKeyHash.Print (_out, "publicKeyHash", _level+1);
         if (imza != null) imza.Print (_out, "imza", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
