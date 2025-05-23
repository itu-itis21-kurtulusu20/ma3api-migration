// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.x509 {

   public class PolicyMappings_element : Asn1Type {
      public Asn1ObjectIdentifier issuerDomainPolicy;
      public Asn1ObjectIdentifier subjectDomainPolicy;

      static PolicyMappings_element ()
      {
         Asn1Type.SetKey2 (_ImplicitValues._rtkey);
      }

      public PolicyMappings_element () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public PolicyMappings_element (
         Asn1ObjectIdentifier issuerDomainPolicy_,
         Asn1ObjectIdentifier subjectDomainPolicy_
      )
         : base ()
      {
         issuerDomainPolicy = issuerDomainPolicy_;
         subjectDomainPolicy = subjectDomainPolicy_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public PolicyMappings_element (int[] issuerDomainPolicy_,
         int[] subjectDomainPolicy_
      )
         : base ()
      {
         issuerDomainPolicy = new Asn1ObjectIdentifier (issuerDomainPolicy_);
         subjectDomainPolicy = new Asn1ObjectIdentifier (subjectDomainPolicy_);
      }

      public void Init () {
         issuerDomainPolicy = null;
         subjectDomainPolicy = null;
      }

      public override bool Equals( Object obj ) {
         if ( this == obj ) return true;
         if ( !(obj is PolicyMappings_element) ) return false;

         PolicyMappings_element rhs = (PolicyMappings_element) obj;

         if (issuerDomainPolicy == null) {
            if (rhs.issuerDomainPolicy != null) return false;
         }
         else {
            if (!issuerDomainPolicy.Equals(rhs.issuerDomainPolicy)) {
               return false;
            }
         }

         if (subjectDomainPolicy == null) {
            if (rhs.subjectDomainPolicy != null) return false;
         }
         else {
            if (!subjectDomainPolicy.Equals(rhs.subjectDomainPolicy)) {
               return false;
            }
         }

         return true;
      }

      public override int GetHashCode() {
         int __code = 1;

         if (issuerDomainPolicy != null) __code = 31*__code + issuerDomainPolicy.GetHashCode();
         if (subjectDomainPolicy != null) __code = 31*__code + subjectDomainPolicy.GetHashCode();

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

         // decode issuerDomainPolicy

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 6, elemLen, false)) {
            issuerDomainPolicy = new Asn1ObjectIdentifier();
            issuerDomainPolicy.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode subjectDomainPolicy

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 6, elemLen, false)) {
            subjectDomainPolicy = new Asn1ObjectIdentifier();
            subjectDomainPolicy.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode subjectDomainPolicy

         len = subjectDomainPolicy.Encode (buffer, true);
         _aal += len;

         // encode issuerDomainPolicy

         len = issuerDomainPolicy.Encode (buffer, true);
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
         if (issuerDomainPolicy != null) issuerDomainPolicy.Print (_out, "issuerDomainPolicy", _level+1);
         if (subjectDomainPolicy != null) subjectDomainPolicy.Print (_out, "subjectDomainPolicy", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
