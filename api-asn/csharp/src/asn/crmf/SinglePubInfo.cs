// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.crmf {

   using GeneralName = tr.gov.tubitak.uekae.esya.asn.x509.GeneralName;

   public class SinglePubInfo : Asn1Type {
      public SinglePubInfo_pubMethod pubMethod;
      public GeneralName pubLocation;  // optional

      static SinglePubInfo ()
      {
         Asn1Type.SetKey2 (_crmfValues._rtkey);
      }

      public SinglePubInfo () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public SinglePubInfo (
         SinglePubInfo_pubMethod pubMethod_,
         GeneralName pubLocation_
      )
         : base ()
      {
         pubMethod = pubMethod_;
         pubLocation = pubLocation_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It sets 
      /// all elements to references to the given objects
      /// </summary>
      public SinglePubInfo (
         SinglePubInfo_pubMethod pubMethod_
      )
         : base ()
      {
         pubMethod = pubMethod_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public SinglePubInfo (long pubMethod_,
         GeneralName pubLocation_
      )
         : base ()
      {
         pubMethod = new SinglePubInfo_pubMethod (pubMethod_);
         pubLocation = pubLocation_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It allows 
      /// primitive data to be passed for all primitive elements.  
      /// It will create new object wrappers for the primitive data 
      /// and set other elements to references to the given objects. 
      /// </summary>
      public SinglePubInfo (
         long pubMethod_
      )
         : base ()
      {
         pubMethod = new SinglePubInfo_pubMethod (pubMethod_);
      }

      public void Init () {
         pubMethod = null;
         pubLocation = null;
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

         // decode pubMethod

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
            pubMethod = new SinglePubInfo_pubMethod();
            pubMethod.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode pubLocation

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
               pubLocation = new GeneralName();
               pubLocation.Decode (buffer, true, elemLen.mValue);
            }
         }

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode pubLocation

         if (pubLocation != null) {
            len = pubLocation.Encode (buffer, true);
            _aal += len;
         }

         // encode pubMethod

         len = pubMethod.Encode (buffer, true);
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
         if (pubMethod != null) pubMethod.Print (_out, "pubMethod", _level+1);
         if (pubLocation != null) pubLocation.Print (_out, "pubLocation", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
