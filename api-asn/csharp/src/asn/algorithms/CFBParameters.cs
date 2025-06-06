// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.algorithms {

   public class CFBParameters : Asn1Type {
      public Asn1OctetString aes_IV;
      public Asn1Integer numberOfBits;

      static CFBParameters ()
      {
         Asn1Type.SetKey2 (_aesValues._rtkey);
      }

      public CFBParameters () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public CFBParameters (
         Asn1OctetString aes_IV_,
         Asn1Integer numberOfBits_
      )
         : base ()
      {
         aes_IV = aes_IV_;
         numberOfBits = numberOfBits_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public CFBParameters (byte[] aes_IV_,
         long numberOfBits_
      )
         : base ()
      {
         aes_IV = new Asn1OctetString (aes_IV_);
         numberOfBits = new Asn1Integer (numberOfBits_);
      }

      public void Init () {
         aes_IV = null;
         numberOfBits = null;
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

         // decode aes_IV

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 4, elemLen, false)) {
            aes_IV = new Asn1OctetString();
            aes_IV.Decode (buffer, true, elemLen.mValue);
            if (!(aes_IV.Length == 16)) {
               throw new Asn1ConsVioException ("aes_IV.Length", aes_IV.Length);
            }

         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode numberOfBits

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
            numberOfBits = new Asn1Integer();
            numberOfBits.Decode (buffer, true, elemLen.mValue);
            if (!((numberOfBits.mValue >= 1 && numberOfBits.mValue <= 128))) {
               throw new Asn1ConsVioException ("numberOfBits.mValue", numberOfBits.mValue);
            }

         }
         else throw new Asn1MissingRequiredException (buffer);

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode numberOfBits

         if (!((numberOfBits.mValue >= 1 && numberOfBits.mValue <= 128))) {
            throw new Asn1ConsVioException ("numberOfBits.mValue", numberOfBits.mValue);
         }

         len = numberOfBits.Encode (buffer, true);
         _aal += len;

         // encode aes_IV

         if (!(aes_IV.Length == 16)) {
            throw new Asn1ConsVioException ("aes_IV.Length", aes_IV.Length);
         }

         len = aes_IV.Encode (buffer, true);
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
         if (aes_IV != null) aes_IV.Print (_out, "aes_IV", _level+1);
         if (numberOfBits != null) numberOfBits.Print (_out, "numberOfBits", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
