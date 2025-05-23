// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.x509 {

   public class SubjectPublicKeyInfo : Asn1Type {
      public AlgorithmIdentifier algorithm;
      public Asn1BitString subjectPublicKey;

      static SubjectPublicKeyInfo ()
      {
         Asn1Type.SetKey2 (_ExplicitValues._rtkey);
      }

      public SubjectPublicKeyInfo () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public SubjectPublicKeyInfo (
         AlgorithmIdentifier algorithm_,
         Asn1BitString subjectPublicKey_
      )
         : base ()
      {
         algorithm = algorithm_;
         subjectPublicKey = subjectPublicKey_;
      }

      public void Init () {
         algorithm = null;
         subjectPublicKey = null;
      }

      public override bool Equals( Object obj ) {
         if ( this == obj ) return true;
         if ( !(obj is SubjectPublicKeyInfo) ) return false;

         SubjectPublicKeyInfo rhs = (SubjectPublicKeyInfo) obj;

         if (algorithm == null) {
            if (rhs.algorithm != null) return false;
         }
         else {
            if (!algorithm.Equals(rhs.algorithm)) {
               return false;
            }
         }

         if (subjectPublicKey == null) {
            if (rhs.subjectPublicKey != null) return false;
         }
         else {
            if (!subjectPublicKey.Equals(rhs.subjectPublicKey)) {
               return false;
            }
         }

         return true;
      }

      public override int GetHashCode() {
         int __code = 1;

         if (algorithm != null) __code = 31*__code + algorithm.GetHashCode();
         if (subjectPublicKey != null) __code = 31*__code + subjectPublicKey.GetHashCode();

         return __code;
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

         // decode algorithm

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            algorithm = new AlgorithmIdentifier();
            algorithm.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode subjectPublicKey

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 3, elemLen, false)) {
            subjectPublicKey = new Asn1BitString();
            subjectPublicKey.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode subjectPublicKey

         len = subjectPublicKey.Encode (buffer, true);
         _aal += len;

         // encode algorithm

         len = algorithm.Encode (buffer, true);
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
         if (algorithm != null) algorithm.Print (_out, "algorithm", _level+1);
         if (subjectPublicKey != null) subjectPublicKey.Print (_out, "subjectPublicKey", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
