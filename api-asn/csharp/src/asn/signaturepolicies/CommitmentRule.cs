// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.signaturepolicies {

   public class CommitmentRule : Asn1Type {
      public SelectedCommitmentTypes selCommitmentTypes;
      public SignerAndVerifierRules signerAndVeriferRules;  // optional
      public SigningCertTrustCondition signingCertTrustCondition;  // optional
      public TimestampTrustCondition timeStampTrustCondition;  // optional
      public AttributeTrustCondition attributeTrustCondition;  // optional
      public AlgorithmConstraintSet algorithmConstraintSet;  // optional
      public SignPolExtensions signPolExtensions;  // optional

      static CommitmentRule ()
      {
         Asn1Type.SetKey2 (_signaturepoliciesValues._rtkey);
      }

      public CommitmentRule () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public CommitmentRule (
         SelectedCommitmentTypes selCommitmentTypes_,
         SignerAndVerifierRules signerAndVeriferRules_,
         SigningCertTrustCondition signingCertTrustCondition_,
         TimestampTrustCondition timeStampTrustCondition_,
         AttributeTrustCondition attributeTrustCondition_,
         AlgorithmConstraintSet algorithmConstraintSet_,
         SignPolExtensions signPolExtensions_
      )
         : base ()
      {
         selCommitmentTypes = selCommitmentTypes_;
         signerAndVeriferRules = signerAndVeriferRules_;
         signingCertTrustCondition = signingCertTrustCondition_;
         timeStampTrustCondition = timeStampTrustCondition_;
         attributeTrustCondition = attributeTrustCondition_;
         algorithmConstraintSet = algorithmConstraintSet_;
         signPolExtensions = signPolExtensions_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It sets 
      /// all elements to references to the given objects
      /// </summary>
      public CommitmentRule (
         SelectedCommitmentTypes selCommitmentTypes_
      )
         : base ()
      {
         selCommitmentTypes = selCommitmentTypes_;
      }

      public void Init () {
         selCommitmentTypes = null;
         signerAndVeriferRules = null;
         signingCertTrustCondition = null;
         timeStampTrustCondition = null;
         attributeTrustCondition = null;
         algorithmConstraintSet = null;
         signPolExtensions = null;
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

         // decode selCommitmentTypes

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            selCommitmentTypes = new SelectedCommitmentTypes();
            selCommitmentTypes.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode signerAndVeriferRules

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
            int offset = buffer.ByteCount;
            signerAndVeriferRules = new SignerAndVerifierRules();
            signerAndVeriferRules.Decode (buffer, true, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }

         // decode signingCertTrustCondition

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 1, elemLen, true)) {
            int offset = buffer.ByteCount;
            signingCertTrustCondition = new SigningCertTrustCondition();
            signingCertTrustCondition.Decode (buffer, true, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }

         // decode timeStampTrustCondition

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 2, elemLen, true)) {
            int offset = buffer.ByteCount;
            timeStampTrustCondition = new TimestampTrustCondition();
            timeStampTrustCondition.Decode (buffer, true, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }

         // decode attributeTrustCondition

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 3, elemLen, true)) {
            int offset = buffer.ByteCount;
            attributeTrustCondition = new AttributeTrustCondition();
            attributeTrustCondition.Decode (buffer, true, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }

         // decode algorithmConstraintSet

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 4, elemLen, true)) {
            int offset = buffer.ByteCount;
            algorithmConstraintSet = new AlgorithmConstraintSet();
            algorithmConstraintSet.Decode (buffer, true, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }

         // decode signPolExtensions

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 5, elemLen, true)) {
            int offset = buffer.ByteCount;
            signPolExtensions = new SignPolExtensions();
            signPolExtensions.Decode (buffer, true, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode signPolExtensions

         if (signPolExtensions != null) {
            len = signPolExtensions.Encode (buffer, true);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 5, len);
            _aal += len;
         }

         // encode algorithmConstraintSet

         if (algorithmConstraintSet != null) {
            len = algorithmConstraintSet.Encode (buffer, true);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 4, len);
            _aal += len;
         }

         // encode attributeTrustCondition

         if (attributeTrustCondition != null) {
            len = attributeTrustCondition.Encode (buffer, true);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 3, len);
            _aal += len;
         }

         // encode timeStampTrustCondition

         if (timeStampTrustCondition != null) {
            len = timeStampTrustCondition.Encode (buffer, true);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 2, len);
            _aal += len;
         }

         // encode signingCertTrustCondition

         if (signingCertTrustCondition != null) {
            len = signingCertTrustCondition.Encode (buffer, true);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 1, len);
            _aal += len;
         }

         // encode signerAndVeriferRules

         if (signerAndVeriferRules != null) {
            len = signerAndVeriferRules.Encode (buffer, true);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
            _aal += len;
         }

         // encode selCommitmentTypes

         len = selCommitmentTypes.Encode (buffer, true);
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
         if (selCommitmentTypes != null) selCommitmentTypes.Print (_out, "selCommitmentTypes", _level+1);
         if (signerAndVeriferRules != null) signerAndVeriferRules.Print (_out, "signerAndVeriferRules", _level+1);
         if (signingCertTrustCondition != null) signingCertTrustCondition.Print (_out, "signingCertTrustCondition", _level+1);
         if (timeStampTrustCondition != null) timeStampTrustCondition.Print (_out, "timeStampTrustCondition", _level+1);
         if (attributeTrustCondition != null) attributeTrustCondition.Print (_out, "attributeTrustCondition", _level+1);
         if (algorithmConstraintSet != null) algorithmConstraintSet.Print (_out, "algorithmConstraintSet", _level+1);
         if (signPolExtensions != null) signPolExtensions.Print (_out, "signPolExtensions", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
