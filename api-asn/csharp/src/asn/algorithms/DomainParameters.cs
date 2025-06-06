// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.algorithms {

   public class DomainParameters : Asn1Type {
      public Asn1Integer p;
      public Asn1Integer g;
      public Asn1Integer q;
      public Asn1Integer j;  // optional
      public ValidationParms validationParms;  // optional

      static DomainParameters ()
      {
         Asn1Type.SetKey2 (_algorithmsValues._rtkey);
      }

      public DomainParameters () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public DomainParameters (
         Asn1Integer p_,
         Asn1Integer g_,
         Asn1Integer q_,
         Asn1Integer j_,
         ValidationParms validationParms_
      )
         : base ()
      {
         p = p_;
         g = g_;
         q = q_;
         j = j_;
         validationParms = validationParms_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It sets 
      /// all elements to references to the given objects
      /// </summary>
      public DomainParameters (
         Asn1Integer p_,
         Asn1Integer g_,
         Asn1Integer q_
      )
         : base ()
      {
         p = p_;
         g = g_;
         q = q_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public DomainParameters (long p_,
         long g_,
         long q_,
         long j_,
         ValidationParms validationParms_
      )
         : base ()
      {
         p = new Asn1Integer (p_);
         g = new Asn1Integer (g_);
         q = new Asn1Integer (q_);
         j = new Asn1Integer (j_);
         validationParms = validationParms_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It allows 
      /// primitive data to be passed for all primitive elements.  
      /// It will create new object wrappers for the primitive data 
      /// and set other elements to references to the given objects. 
      /// </summary>
      public DomainParameters (
         long p_,
         long g_,
         long q_
      )
         : base ()
      {
         p = new Asn1Integer (p_);
         g = new Asn1Integer (g_);
         q = new Asn1Integer (q_);
      }

      public void Init () {
         p = null;
         g = null;
         q = null;
         j = null;
         validationParms = null;
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

         // decode p

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
            p = new Asn1Integer();
            p.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode g

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
            g = new Asn1Integer();
            g.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode q

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
            q = new Asn1Integer();
            q.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode j

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
            j = new Asn1Integer();
            j.Decode (buffer, true, elemLen.mValue);
         }

         // decode validationParms

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            validationParms = new ValidationParms();
            validationParms.Decode (buffer, true, elemLen.mValue);
         }

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode validationParms

         if (validationParms != null) {
            len = validationParms.Encode (buffer, true);
            _aal += len;
         }

         // encode j

         if (j != null) {
            len = j.Encode (buffer, true);
            _aal += len;
         }

         // encode q

         len = q.Encode (buffer, true);
         _aal += len;

         // encode g

         len = g.Encode (buffer, true);
         _aal += len;

         // encode p

         len = p.Encode (buffer, true);
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
         if (p != null) p.Print (_out, "p", _level+1);
         if (g != null) g.Print (_out, "g", _level+1);
         if (q != null) q.Print (_out, "q", _level+1);
         if (j != null) j.Print (_out, "j", _level+1);
         if (validationParms != null) validationParms.Print (_out, "validationParms", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
