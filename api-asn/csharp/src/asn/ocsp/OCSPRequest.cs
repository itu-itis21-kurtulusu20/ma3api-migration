// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.ocsp {

   public class OCSPRequest : Asn1Type {
      public TBSRequest tbsRequest;
      public tr.gov.tubitak.uekae.esya.asn.ocsp.Signature optionalSignature;  // optional

      static OCSPRequest ()
      {
         Asn1Type.SetKey2 (_ocspValues._rtkey);
      }

      public OCSPRequest () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public OCSPRequest (
         TBSRequest tbsRequest_,
         tr.gov.tubitak.uekae.esya.asn.ocsp.Signature optionalSignature_
      )
         : base ()
      {
         tbsRequest = tbsRequest_;
         optionalSignature = optionalSignature_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It sets 
      /// all elements to references to the given objects
      /// </summary>
      public OCSPRequest (
         TBSRequest tbsRequest_
      )
         : base ()
      {
         tbsRequest = tbsRequest_;
      }

      public void Init () {
         tbsRequest = null;
         optionalSignature = null;
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

         // decode tbsRequest

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            tbsRequest = new TBSRequest();
            tbsRequest.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode optionalSignature

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
            int offset = buffer.ByteCount;
            optionalSignature = new Signature();
            optionalSignature.Decode (buffer, true, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode optionalSignature

         if (optionalSignature != null) {
            len = optionalSignature.Encode (buffer, true);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
            _aal += len;
         }

         // encode tbsRequest

         len = tbsRequest.Encode (buffer, true);
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
         if (tbsRequest != null) tbsRequest.Print (_out, "tbsRequest", _level+1);
         if (optionalSignature != null) optionalSignature.Print (_out, "optionalSignature", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
