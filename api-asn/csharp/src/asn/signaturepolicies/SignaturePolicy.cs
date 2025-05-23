// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.signaturepolicies {

   using AlgorithmIdentifier = tr.gov.tubitak.uekae.esya.asn.x509.AlgorithmIdentifier;

   public class SignaturePolicy : Asn1Type {
      public AlgorithmIdentifier signPolicyHashAlg;
      public SignPolicyInfo signPolicyInfo;
      public Asn1OctetString signPolicyHash;  // optional

      static SignaturePolicy ()
      {
         Asn1Type.SetKey2 (_signaturepoliciesValues._rtkey);
      }

      public SignaturePolicy () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public SignaturePolicy (
         AlgorithmIdentifier signPolicyHashAlg_,
         SignPolicyInfo signPolicyInfo_,
         Asn1OctetString signPolicyHash_
      )
         : base ()
      {
         signPolicyHashAlg = signPolicyHashAlg_;
         signPolicyInfo = signPolicyInfo_;
         signPolicyHash = signPolicyHash_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It sets 
      /// all elements to references to the given objects
      /// </summary>
      public SignaturePolicy (
         AlgorithmIdentifier signPolicyHashAlg_,
         SignPolicyInfo signPolicyInfo_
      )
         : base ()
      {
         signPolicyHashAlg = signPolicyHashAlg_;
         signPolicyInfo = signPolicyInfo_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public SignaturePolicy (AlgorithmIdentifier signPolicyHashAlg_,
         SignPolicyInfo signPolicyInfo_,
         byte[] signPolicyHash_
      )
         : base ()
      {
         signPolicyHashAlg = signPolicyHashAlg_;
         signPolicyInfo = signPolicyInfo_;
         signPolicyHash = new Asn1OctetString (signPolicyHash_);
      }

      public void Init () {
         signPolicyHashAlg = null;
         signPolicyInfo = null;
         signPolicyHash = null;
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

         // decode signPolicyHashAlg

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            signPolicyHashAlg = new AlgorithmIdentifier();
            signPolicyHashAlg.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode signPolicyInfo

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            signPolicyInfo = new SignPolicyInfo();
            signPolicyInfo.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode signPolicyHash

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 4, elemLen, false)) {
            signPolicyHash = new Asn1OctetString();
            signPolicyHash.Decode (buffer, true, elemLen.mValue);
         }

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode signPolicyHash

         if (signPolicyHash != null) {
            len = signPolicyHash.Encode (buffer, true);
            _aal += len;
         }

         // encode signPolicyInfo

         len = signPolicyInfo.Encode (buffer, true);
         _aal += len;

         // encode signPolicyHashAlg

         len = signPolicyHashAlg.Encode (buffer, true);
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
         if (signPolicyHashAlg != null) signPolicyHashAlg.Print (_out, "signPolicyHashAlg", _level+1);
         if (signPolicyInfo != null) signPolicyInfo.Print (_out, "signPolicyInfo", _level+1);
         if (signPolicyHash != null) signPolicyHash.Print (_out, "signPolicyHash", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
