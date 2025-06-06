// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.cmp {

   public class RevReqContent : Asn1Type {
      public RevDetails[] elements;

      static RevReqContent ()
      {
         Asn1Type.SetKey2 (_cmpValues._rtkey);
      }

      public RevReqContent () : base()
      {
         elements = null;
      }

      /// <summary>
      /// This constructor initializes the internal array to hold the 
      /// given number of elements.  The element values must be manually 
      /// populated.
      /// </summary>
      public RevReqContent (int numRecords) : base()
      {
         elements = new RevDetails [numRecords];
      }

      /// <summary>
      /// This constructor initializes the internal array to hold the 
      /// given the array.  
      /// </summary>
      public RevReqContent (RevDetails[] elements_) : base()
      {
         elements = elements_;
      }

      /// <summary> Returns the number of elements in the sequence. </summary>
      public int getLength() {
         return elements.Length;
      }

      public override void Decode
         (Asn1BerDecodeBuffer buffer, bool explicitTagging, int implicitLength)
      {
         int llen = (explicitTagging) ?
            MatchTag (buffer, Asn1Tag.SEQUENCE) : implicitLength;

         // decode SEQUENCE OF or SET OF

         System.Collections.ArrayList llist = new System.Collections.ArrayList();
         Asn1BerDecodeContext _context =
             new Asn1BerDecodeContext (buffer, llen);
         RevDetails element;
         int elemLen = 0;

         while (!_context.Expired()) {
            element = new RevDetails();
            element.Decode (buffer, true, elemLen);
            llist.Add (element);
         }

         elements = new RevDetails [llist.Count];
         Asn1Util.ToArray(llist, elements);

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode SEQUENCE OF or SET OF

         for (int i = elements.Length - 1; i >= 0; i--) {
            len = elements[i].Encode (buffer, true);
            _aal += len;
         }

         if (explicitTagging) {
            _aal += buffer.EncodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
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
