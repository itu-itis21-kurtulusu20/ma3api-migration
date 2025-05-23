// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.ocsp {

   using AlgorithmIdentifier = tr.gov.tubitak.uekae.esya.asn.x509.AlgorithmIdentifier;

   public class BasicOCSPResponse : Asn1Type {
      public ResponseData tbsResponseData;
      public tr.gov.tubitak.uekae.esya.asn.x509.AlgorithmIdentifier signatureAlgorithm;
      public Asn1BitString signature;
      public _SeqOfCertificate certs;  // optional

      static BasicOCSPResponse ()
      {
         Asn1Type.SetKey2 (_ocspValues._rtkey);
      }

      public BasicOCSPResponse () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public BasicOCSPResponse (
         ResponseData tbsResponseData_,
         tr.gov.tubitak.uekae.esya.asn.x509.AlgorithmIdentifier signatureAlgorithm_,
         Asn1BitString signature_,
         _SeqOfCertificate certs_
      )
         : base ()
      {
         tbsResponseData = tbsResponseData_;
         signatureAlgorithm = signatureAlgorithm_;
         signature = signature_;
         certs = certs_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It sets 
      /// all elements to references to the given objects
      /// </summary>
      public BasicOCSPResponse (
         ResponseData tbsResponseData_,
         tr.gov.tubitak.uekae.esya.asn.x509.AlgorithmIdentifier signatureAlgorithm_,
         Asn1BitString signature_
      )
         : base ()
      {
         tbsResponseData = tbsResponseData_;
         signatureAlgorithm = signatureAlgorithm_;
         signature = signature_;
      }

      public void Init () {
         tbsResponseData = null;
         signatureAlgorithm = null;
         signature = null;
         certs = null;
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

         // decode tbsResponseData

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            tbsResponseData = new ResponseData();
            tbsResponseData.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode signatureAlgorithm

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            signatureAlgorithm = new AlgorithmIdentifier();
            signatureAlgorithm.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode signature

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 3, elemLen, false)) {
            signature = new Asn1BitString();
            signature.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode certs

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
            int offset = buffer.ByteCount;
            certs = new _SeqOfCertificate();
            certs.Decode (buffer, true, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode certs

         if (certs != null) {
            len = certs.Encode (buffer, true);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
            _aal += len;
         }

         // encode signature

         len = signature.Encode (buffer, true);
         _aal += len;

         // encode signatureAlgorithm

         len = signatureAlgorithm.Encode (buffer, true);
         _aal += len;

         // encode tbsResponseData

         len = tbsResponseData.Encode (buffer, true);
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
         if (tbsResponseData != null) tbsResponseData.Print (_out, "tbsResponseData", _level+1);
         if (signatureAlgorithm != null) signatureAlgorithm.Print (_out, "signatureAlgorithm", _level+1);
         if (signature != null) signature.Print (_out, "signature", _level+1);
         if (certs != null) certs.Print (_out, "certs", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
