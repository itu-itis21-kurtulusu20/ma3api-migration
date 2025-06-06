// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.pkcs12 {

   public class PKCS12PbeParams : Asn1Type {
      public Asn1OctetString salt;
      public Asn1Integer iterations;

      static PKCS12PbeParams ()
      {
         Asn1Type.SetKey2 (_pkcs12Values._rtkey);
      }

      public PKCS12PbeParams () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public PKCS12PbeParams (
         Asn1OctetString salt_,
         Asn1Integer iterations_
      )
         : base ()
      {
         salt = salt_;
         iterations = iterations_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public PKCS12PbeParams (byte[] salt_,
         long iterations_
      )
         : base ()
      {
         salt = new Asn1OctetString (salt_);
         iterations = new Asn1Integer (iterations_);
      }

      public void Init () {
         salt = null;
         iterations = null;
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

         // decode salt

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 4, elemLen, false)) {
            salt = new Asn1OctetString();
            salt.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode iterations

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
            iterations = new Asn1Integer();
            iterations.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         if (explicitTagging && llen == Asn1Status.INDEFLEN) {
            MatchTag (buffer, Asn1Tag.EOC);
         }
      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode iterations

         len = iterations.Encode (buffer, true);
         _aal += len;

         // encode salt

         len = salt.Encode (buffer, true);
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
         if (salt != null) salt.Print (_out, "salt", _level+1);
         if (iterations != null) iterations.Print (_out, "iterations", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
