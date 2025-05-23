// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.pkixtsp {

   public class ESYAReqEx : Asn1Type {
      public Asn1Integer userid;
      public Asn1OctetString salt;
      public Asn1Integer iterationCount;
      public Asn1OctetString iv;
      public Asn1OctetString encryptedMessageImprint;

      static ESYAReqEx ()
      {
         Asn1Type.SetKey2 (_pkixtspValues._rtkey);
      }

      public ESYAReqEx () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public ESYAReqEx (
         Asn1Integer userid_,
         Asn1OctetString salt_,
         Asn1Integer iterationCount_,
         Asn1OctetString iv_,
         Asn1OctetString encryptedMessageImprint_
      )
         : base ()
      {
         userid = userid_;
         salt = salt_;
         iterationCount = iterationCount_;
         iv = iv_;
         encryptedMessageImprint = encryptedMessageImprint_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public ESYAReqEx (long userid_,
         byte[] salt_,
         long iterationCount_,
         byte[] iv_,
         byte[] encryptedMessageImprint_
      )
         : base ()
      {
         userid = new Asn1Integer (userid_);
         salt = new Asn1OctetString (salt_);
         iterationCount = new Asn1Integer (iterationCount_);
         iv = new Asn1OctetString (iv_);
         encryptedMessageImprint = new Asn1OctetString (encryptedMessageImprint_);
      }

      public void Init () {
         userid = null;
         salt = null;
         iterationCount = null;
         iv = null;
         encryptedMessageImprint = null;
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

         // decode userid

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
            userid = new Asn1Integer();
            userid.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode salt

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 4, elemLen, false)) {
            salt = new Asn1OctetString();
            salt.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode iterationCount

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
            iterationCount = new Asn1Integer();
            iterationCount.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode iv

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 4, elemLen, false)) {
            iv = new Asn1OctetString();
            iv.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode encryptedMessageImprint

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 4, elemLen, false)) {
            encryptedMessageImprint = new Asn1OctetString();
            encryptedMessageImprint.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode encryptedMessageImprint

         len = encryptedMessageImprint.Encode (buffer, true);
         _aal += len;

         // encode iv

         len = iv.Encode (buffer, true);
         _aal += len;

         // encode iterationCount

         len = iterationCount.Encode (buffer, true);
         _aal += len;

         // encode salt

         len = salt.Encode (buffer, true);
         _aal += len;

         // encode userid

         len = userid.Encode (buffer, true);
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
         if (userid != null) userid.Print (_out, "userid", _level+1);
         if (salt != null) salt.Print (_out, "salt", _level+1);
         if (iterationCount != null) iterationCount.Print (_out, "iterationCount", _level+1);
         if (iv != null) iv.Print (_out, "iv", _level+1);
         if (encryptedMessageImprint != null) encryptedMessageImprint.Print (_out, "encryptedMessageImprint", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
