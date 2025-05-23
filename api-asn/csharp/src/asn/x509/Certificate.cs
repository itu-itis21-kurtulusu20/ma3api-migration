﻿// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.x509 {

   public class Certificate : Asn1Type {
      public TBSCertificate tbsCertificate;
      public AlgorithmIdentifier signatureAlgorithm;
      public Asn1BitString signature;

      private object encodeLock = new object();

      static Certificate ()
      {
         Asn1Type.SetKey2 (_ExplicitValues._rtkey);
      }

      public Certificate () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public Certificate (
         TBSCertificate tbsCertificate_,
         AlgorithmIdentifier signatureAlgorithm_,
         Asn1BitString signature_
      )
         : base ()
      {
         tbsCertificate = tbsCertificate_;
         signatureAlgorithm = signatureAlgorithm_;
         signature = signature_;
      }

      public void Init () {
         tbsCertificate = null;
         signatureAlgorithm = null;
         signature = null;
      }

      public override bool Equals( Object obj ) {
         if ( this == obj ) return true;
         if ( !(obj is Certificate) ) return false;

         Certificate rhs = (Certificate) obj;

         if (tbsCertificate == null) {
            if (rhs.tbsCertificate != null) return false;
         }
         else {
            if (!tbsCertificate.Equals(rhs.tbsCertificate)) {
               return false;
            }
         }

         if (signatureAlgorithm == null) {
            if (rhs.signatureAlgorithm != null) return false;
         }
         else {
            if (!signatureAlgorithm.Equals(rhs.signatureAlgorithm)) {
               return false;
            }
         }

         if (signature == null) {
            if (rhs.signature != null) return false;
         }
         else {
            if (!signature.Equals(rhs.signature)) {
               return false;
            }
         }

         return true;
      }

      public override int GetHashCode() {
         int __code = 1;

         if (tbsCertificate != null) __code = 31*__code + tbsCertificate.GetHashCode();
         if (signatureAlgorithm != null) __code = 31*__code + signatureAlgorithm.GetHashCode();
         if (signature != null) __code = 31*__code + signature.GetHashCode();

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

         // decode tbsCertificate

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            tbsCertificate = new TBSCertificate();
            tbsCertificate.Decode (buffer, true, elemLen.mValue);
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

      }


      /* Mobil imza çoklu doküman imzalama işleminde bütün imzalar için aynı sertifika nesnesi kullanılıyor. Paralel bir şekilde sertifikalar
       * encode edilmeye çalışıldığında Asn1Time field'ı hatalı bir şekilde encode ediliyor. synchronized keyword'ü eklenerek sertifika'nın encode
       * işleminin paralel yapılması engellendi.
       * Certificate nesnesinden türeyen WrappedCertificate sınıfı yapılması ve lock kullanılarak encode işleminin override edilmesi değerlendirildi.
       * ECertificate getObject'inin bunu dönmesi sağlandı ama bu durum memory açısından dezavantaj oluşturuyordu.
       * */

        public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
          lock (encodeLock)
          {
              int _aal = 0, len;

              // encode signature

              len = signature.Encode(buffer, true);
              _aal += len;

              // encode signatureAlgorithm

              len = signatureAlgorithm.Encode(buffer, true);
              _aal += len;

              // encode tbsCertificate

              len = tbsCertificate.Encode(buffer, true);
              _aal += len;

              if (explicitTagging)
              {
                  _aal += buffer.EncodeTagAndLength(Asn1Tag.SEQUENCE, _aal);
              }

              return (_aal);
          }
      }

      public override void Print (System.IO.TextWriter _out, 
                                  string _varName, int _level)
      {
         Indent (_out, _level);
         _out.WriteLine (_varName + " {");
         if (tbsCertificate != null) tbsCertificate.Print (_out, "tbsCertificate", _level+1);
         if (signatureAlgorithm != null) signatureAlgorithm.Print (_out, "signatureAlgorithm", _level+1);
         if (signature != null) signature.Print (_out, "signature", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
