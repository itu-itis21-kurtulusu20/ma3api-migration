// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.attrcert {

   using GeneralName = tr.gov.tubitak.uekae.esya.asn.x509.GeneralName;

   public class TargetCert : Asn1Type {
      public tr.gov.tubitak.uekae.esya.asn.attrcert.IssuerSerial targetCertificate;
      public GeneralName targetName;  // optional
      public ObjectDigestInfo certDigestInfo;  // optional

      static TargetCert ()
      {
         Asn1Type.SetKey2 (_attrcertValues._rtkey);
      }

      public TargetCert () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public TargetCert (
         tr.gov.tubitak.uekae.esya.asn.attrcert.IssuerSerial targetCertificate_,
         GeneralName targetName_,
         ObjectDigestInfo certDigestInfo_
      )
         : base ()
      {
         targetCertificate = targetCertificate_;
         targetName = targetName_;
         certDigestInfo = certDigestInfo_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It sets 
      /// all elements to references to the given objects
      /// </summary>
      public TargetCert (
         tr.gov.tubitak.uekae.esya.asn.attrcert.IssuerSerial targetCertificate_
      )
         : base ()
      {
         targetCertificate = targetCertificate_;
      }

      public void Init () {
         targetCertificate = null;
         targetName = null;
         certDigestInfo = null;
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

         // decode targetCertificate

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            targetCertificate = new IssuerSerial();
            targetCertificate.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode targetName

         if (!_context.Expired()) {
            Asn1Tag tag = buffer.PeekTag ();
            if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 1) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 2) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 3) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 4) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 5) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 6) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 7) ||
                tag.Equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 8))
            {
               targetName = new GeneralName();
               targetName.Decode (buffer, true, elemLen.mValue);
            }
         }

         // decode certDigestInfo

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            certDigestInfo = new ObjectDigestInfo();
            certDigestInfo.Decode (buffer, true, elemLen.mValue);
         }

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode certDigestInfo

         if (certDigestInfo != null) {
            len = certDigestInfo.Encode (buffer, true);
            _aal += len;
         }

         // encode targetName

         if (targetName != null) {
            len = targetName.Encode (buffer, true);
            _aal += len;
         }

         // encode targetCertificate

         len = targetCertificate.Encode (buffer, true);
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
         if (targetCertificate != null) targetCertificate.Print (_out, "targetCertificate", _level+1);
         if (targetName != null) targetName.Print (_out, "targetName", _level+1);
         if (certDigestInfo != null) certDigestInfo.Print (_out, "certDigestInfo", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
