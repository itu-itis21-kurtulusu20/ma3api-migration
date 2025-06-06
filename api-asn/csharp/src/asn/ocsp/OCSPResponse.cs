// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.ocsp {

   public class OCSPResponse : Asn1Type {
      public OCSPResponseStatus responseStatus;
      public ResponseBytes responseBytes;  // optional

      static OCSPResponse ()
      {
         Asn1Type.SetKey2 (_ocspValues._rtkey);
      }

      public OCSPResponse () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public OCSPResponse (
         OCSPResponseStatus responseStatus_,
         ResponseBytes responseBytes_
      )
         : base ()
      {
         responseStatus = responseStatus_;
         responseBytes = responseBytes_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It sets 
      /// all elements to references to the given objects
      /// </summary>
      public OCSPResponse (
         OCSPResponseStatus responseStatus_
      )
         : base ()
      {
         responseStatus = responseStatus_;
      }

      public void Init () {
         responseStatus = null;
         responseBytes = null;
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

         // decode responseStatus

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 10, elemLen, false)) {
            int tval = buffer.DecodeEnumValue (OCSPResponseStatus._TAG, true, elemLen.mValue);
            responseStatus = OCSPResponseStatus.ValueOf (tval);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode responseBytes

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
            int offset = buffer.ByteCount;
            responseBytes = new ResponseBytes();
            responseBytes.Decode (buffer, true, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode responseBytes

         if (responseBytes != null) {
            len = responseBytes.Encode (buffer, true);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
            _aal += len;
         }

         // encode responseStatus

         len = responseStatus.Encode (buffer, true);
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
         if (responseStatus != null) responseStatus.Print (_out, "responseStatus", _level+1);
         if (responseBytes != null) responseBytes.Print (_out, "responseBytes", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
