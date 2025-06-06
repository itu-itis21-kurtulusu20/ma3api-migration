// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.signaturepolicies {

   public class SignerAndVerifierRules : Asn1Type {
      public SignerRules signerRules;
      public VerifierRules verifierRules;

      static SignerAndVerifierRules ()
      {
         Asn1Type.SetKey2 (_signaturepoliciesValues._rtkey);
      }

      public SignerAndVerifierRules () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public SignerAndVerifierRules (
         SignerRules signerRules_,
         VerifierRules verifierRules_
      )
         : base ()
      {
         signerRules = signerRules_;
         verifierRules = verifierRules_;
      }

      public void Init () {
         signerRules = null;
         verifierRules = null;
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

         // decode signerRules

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            signerRules = new SignerRules();
            signerRules.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode verifierRules

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            verifierRules = new VerifierRules();
            verifierRules.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode verifierRules

         len = verifierRules.Encode (buffer, true);
         _aal += len;

         // encode signerRules

         len = signerRules.Encode (buffer, true);
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
         if (signerRules != null) signerRules.Print (_out, "signerRules", _level+1);
         if (verifierRules != null) verifierRules.Print (_out, "verifierRules", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
