// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.pkcs12 {

   public class SafeBag : Asn1Type {
      public Asn1ObjectIdentifier bagId;
      public Asn1OpenType bagValue;
      public _SetOfPKCS12Attribute bagAttributes;  // optional

      static SafeBag ()
      {
         Asn1Type.SetKey2 (_pkcs12Values._rtkey);
      }

      public SafeBag () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public SafeBag (
         Asn1ObjectIdentifier bagId_,
         Asn1OpenType bagValue_,
         _SetOfPKCS12Attribute bagAttributes_
      )
         : base ()
      {
         bagId = bagId_;
         bagValue = bagValue_;
         bagAttributes = bagAttributes_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It sets 
      /// all elements to references to the given objects
      /// </summary>
      public SafeBag (
         Asn1ObjectIdentifier bagId_,
         Asn1OpenType bagValue_
      )
         : base ()
      {
         bagId = bagId_;
         bagValue = bagValue_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public SafeBag (int[] bagId_,
         Asn1OpenType bagValue_,
         _SetOfPKCS12Attribute bagAttributes_
      )
         : base ()
      {
         bagId = new Asn1ObjectIdentifier (bagId_);
         bagValue = bagValue_;
         bagAttributes = bagAttributes_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It allows 
      /// primitive data to be passed for all primitive elements.  
      /// It will create new object wrappers for the primitive data 
      /// and set other elements to references to the given objects. 
      /// </summary>
      public SafeBag (
         int[] bagId_,
         Asn1OpenType bagValue_
      )
         : base ()
      {
         bagId = new Asn1ObjectIdentifier (bagId_);
         bagValue = bagValue_;
      }

      public void Init () {
         bagId = null;
         bagValue = null;
         bagAttributes = null;
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

         // decode bagId

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 6, elemLen, false)) {
            bagId = new Asn1ObjectIdentifier();
            bagId.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode bagValue

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
            bagValue = new Asn1OpenType();
            bagValue.Decode (buffer, true, 0);
            if (elemLen.mValue == Asn1Status.INDEFLEN) {
               MatchTag (buffer, Asn1Tag.EOC);
            }
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode bagAttributes

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 17, elemLen, false)) {
            bagAttributes = new _SetOfPKCS12Attribute();
            bagAttributes.Decode (buffer, true, elemLen.mValue);
         }

         if (explicitTagging && llen == Asn1Status.INDEFLEN) {
            MatchTag (buffer, Asn1Tag.EOC);
         }
      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode bagAttributes

         if (bagAttributes != null) {
            len = bagAttributes.Encode (buffer, true);
            _aal += len;
         }

         // encode bagValue

         len = bagValue.Encode (buffer, true);
         len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
         _aal += len;

         // encode bagId

         len = bagId.Encode (buffer, true);
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
         if (bagId != null) bagId.Print (_out, "bagId", _level+1);
         if (bagValue != null) bagValue.Print (_out, "bagValue", _level+1);
         if (bagAttributes != null) bagAttributes.Print (_out, "bagAttributes", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
