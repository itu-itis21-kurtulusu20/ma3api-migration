// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.crmf {

   public class CertReqMsg : Asn1Type {
      public CertRequest certReq;
      public ProofOfPossession pop;  // optional
      public CertReqMsg_regInfo regInfo;  // optional

      static CertReqMsg ()
      {
         Asn1Type.SetKey2 (_crmfValues._rtkey);
      }

      public CertReqMsg () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public CertReqMsg (
         CertRequest certReq_,
         ProofOfPossession pop_,
         CertReqMsg_regInfo regInfo_
      )
         : base ()
      {
         certReq = certReq_;
         pop = pop_;
         regInfo = regInfo_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It sets 
      /// all elements to references to the given objects
      /// </summary>
      public CertReqMsg (
         CertRequest certReq_
      )
         : base ()
      {
         certReq = certReq_;
      }

      public void Init () {
         certReq = null;
         pop = null;
         regInfo = null;
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

         // decode certReq

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            certReq = new CertRequest();
            certReq.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode pop

         if (!_context.Expired()) {
            Asn1Tag tag = buffer.PeekTag ();
            if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 1) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 2) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 3))
            {
               pop = new ProofOfPossession();
               pop.Decode (buffer, true, elemLen.mValue);
            }
         }

         // decode regInfo

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            regInfo = new CertReqMsg_regInfo();
            regInfo.Decode (buffer, true, elemLen.mValue);
         }

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode regInfo

         if (regInfo != null) {
            len = regInfo.Encode (buffer, true);
            _aal += len;
         }

         // encode pop

         if (pop != null) {
            len = pop.Encode (buffer, true);
            _aal += len;
         }

         // encode certReq

         len = certReq.Encode (buffer, true);
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
         if (certReq != null) certReq.Print (_out, "certReq", _level+1);
         if (pop != null) pop.Print (_out, "pop", _level+1);
         if (regInfo != null) regInfo.Print (_out, "regInfo", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
