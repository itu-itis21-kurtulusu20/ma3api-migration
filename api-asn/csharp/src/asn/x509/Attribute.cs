// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.x509 {

   public class Attribute : Asn1Type {
      public Asn1ObjectIdentifier type;
      public _SetOfAttributeValue values;

      static Attribute ()
      {
         Asn1Type.SetKey2 (_ExplicitValues._rtkey);
      }

      public Attribute () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public Attribute (
         Asn1ObjectIdentifier type_,
         _SetOfAttributeValue values_
      )
         : base ()
      {
         type = type_;
         values = values_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public Attribute (int[] type_,
         _SetOfAttributeValue values_
      )
         : base ()
      {
         type = new Asn1ObjectIdentifier (type_);
         values = values_;
      }

      public void Init () {
         type = null;
         values = null;
      }

      public override bool Equals( Object obj ) {
         if ( this == obj ) return true;
         if ( !(obj is Attribute) ) return false;

         Attribute rhs = (Attribute) obj;

         if (type == null) {
            if (rhs.type != null) return false;
         }
         else {
            if (!type.Equals(rhs.type)) {
               return false;
            }
         }

         if (values == null) {
            if (rhs.values != null) return false;
         }
         else {
            if (!values.Equals(rhs.values)) {
               return false;
            }
         }

         return true;
      }

      public override int GetHashCode() {
         int __code = 1;

         if (type != null) __code = 31*__code + type.GetHashCode();
         if (values != null) __code = 31*__code + values.GetHashCode();

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

         // decode type

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 6, elemLen, false)) {
            type = new Asn1ObjectIdentifier();
            type.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode values

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 17, elemLen, false)) {
            values = new _SetOfAttributeValue();
            values.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode values

         len = values.Encode (buffer, true);
         _aal += len;

         // encode type

         len = type.Encode (buffer, true);
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
         if (type != null) type.Print (_out, "type", _level+1);
         if (values != null) values.Print (_out, "values", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
