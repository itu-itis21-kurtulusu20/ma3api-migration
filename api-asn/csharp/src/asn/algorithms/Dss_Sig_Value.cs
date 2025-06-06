// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.algorithms {

   public class Dss_Sig_Value : Asn1Type {
      public Asn1Integer r;
      public Asn1Integer s;

      static Dss_Sig_Value ()
      {
         Asn1Type.SetKey2 (_algorithmsValues._rtkey);
      }

      public Dss_Sig_Value () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public Dss_Sig_Value (
         Asn1Integer r_,
         Asn1Integer s_
      )
         : base ()
      {
         r = r_;
         s = s_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public Dss_Sig_Value (long r_,
         long s_
      )
         : base ()
      {
         r = new Asn1Integer (r_);
         s = new Asn1Integer (s_);
      }

      public void Init () {
         r = null;
         s = null;
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

         // decode r

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
            r = new Asn1Integer();
            r.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode s

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
            s = new Asn1Integer();
            s.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode s

         len = s.Encode (buffer, true);
         _aal += len;

         // encode r

         len = r.Encode (buffer, true);
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
         if (r != null) r.Print (_out, "r", _level+1);
         if (s != null) s.Print (_out, "s", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
