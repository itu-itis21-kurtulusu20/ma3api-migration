// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.attrcert {

   public class _SetOfSecurityCategory : Asn1Type {
      public SecurityCategory[] elements;

      static _SetOfSecurityCategory ()
      {
         Asn1Type.SetKey2 (_attrcertValues._rtkey);
      }

      public _SetOfSecurityCategory () : base()
      {
         elements = null;
      }

      /// <summary>
      /// This constructor initializes the internal array to hold the 
      /// given number of elements.  The element values must be manually 
      /// populated.
      /// </summary>
      public _SetOfSecurityCategory (int numRecords) : base()
      {
         elements = new SecurityCategory [numRecords];
      }

      /// <summary>
      /// This constructor initializes the internal array to hold the 
      /// given the array.  
      /// </summary>
      public _SetOfSecurityCategory (SecurityCategory[] elements_) : base()
      {
         elements = elements_;
      }

      public override void Decode
         (Asn1BerDecodeBuffer buffer, bool explicitTagging, int implicitLength)
      {
         int llen = (explicitTagging) ?
            MatchTag (buffer, Asn1Tag.SET) : implicitLength;

         // decode SEQUENCE OF or SET OF

         System.Collections.ArrayList llist = new System.Collections.ArrayList();
         Asn1BerDecodeContext _context =
             new Asn1BerDecodeContext (buffer, llen);
         SecurityCategory element;
         int elemLen = 0;

         while (!_context.Expired()) {
            element = new SecurityCategory();
            element.Decode (buffer, true, elemLen);
            llist.Add (element);
         }

         elements = new SecurityCategory [llist.Count];
         Asn1Util.ToArray(llist, elements);

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode SEQUENCE OF or SET OF

         Asn1BerEncodeBuffer savedBuffer = buffer;
         buffer = new Asn1DerEncodeBuffer();
         Asn1OctetString[] elems = new Asn1OctetString [elements.Length];

         for (int i = elements.Length - 1; i >= 0; i--) {
            len = elements[i].Encode (buffer, true);
            _aal += len;
            elems[i] = new Asn1OctetString (buffer.MsgCopy);
            buffer.Reset();
         }

         buffer = savedBuffer;
         Array.Sort (elems);

         for (int i = elements.Length - 1; i >= 0; i--) {
            elems[i].Encode (buffer, false);
         }

         if (explicitTagging) {
            _aal += buffer.EncodeTagAndLength (Asn1Tag.SET, _aal);
         }

         return (_aal);
      }

      public override void Print (System.IO.TextWriter _out, 
                                  string _varName, int _level)
      {
         if (elements != null) {
            for (int i = 0; i < elements.Length; i++) {
               string name = _varName + "[" + i + "]";
               elements[i].Print (_out, name, _level);
            }
         }
      }
   }
}
