// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.attrcert {

   public class ObjectDigestInfo_digestedObjectType : Asn1Enumerated {
      private static ObjectDigestInfo_digestedObjectType _publicKey = null;
      private static ObjectDigestInfo_digestedObjectType _publicKeyCert = null;
      private static ObjectDigestInfo_digestedObjectType _otherObjectTypes = null;

      static ObjectDigestInfo_digestedObjectType ()
      {
         Asn1Type.SetKey2 (_attrcertValues._rtkey);
      }

      /// <summary>
      /// Enumerated constructor: this object must be initialized with one 
      /// of the following values:
      ///   0
      ///   1
      ///   2
      /// </summary>
      protected ObjectDigestInfo_digestedObjectType (int value_) : base (value_)
      {
      }

      ///
      /// Singleton accessor method for publicKey.
      ///
      public static ObjectDigestInfo_digestedObjectType publicKey() {
         if (_publicKey == null) _publicKey = new ObjectDigestInfo_digestedObjectType (0);

         return _publicKey;
      }

      ///
      /// Singleton accessor method for publicKeyCert.
      ///
      public static ObjectDigestInfo_digestedObjectType publicKeyCert() {
         if (_publicKeyCert == null) _publicKeyCert = new ObjectDigestInfo_digestedObjectType (1);

         return _publicKeyCert;
      }

      ///
      /// Singleton accessor method for otherObjectTypes.
      ///
      public static ObjectDigestInfo_digestedObjectType otherObjectTypes() {
         if (_otherObjectTypes == null) _otherObjectTypes = new ObjectDigestInfo_digestedObjectType (2);

         return _otherObjectTypes;
      }

      ///
      /// Returns the instance associated with the value passed.
      /// If the value passed is invalid, an exception is thrown.
      ///
      public static ObjectDigestInfo_digestedObjectType ValueOf (int _value)
      {
         switch (_value) {
            case 0: return publicKey();
            case 1: return publicKeyCert();
            case 2: return otherObjectTypes();
            default: throw new Asn1InvalidEnumException (_value);
         }
      }

      public override void Decode
         (Asn1BerDecodeBuffer buffer, bool explicitTagging, int implicitLength)
      {
         base.Decode (buffer, explicitTagging, implicitLength);
         if (!(mValue == 0 ||
         mValue == 1 ||
         mValue == 2)) {
            throw new Asn1InvalidEnumException (mValue);
         }

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         if (!(mValue == 0 ||
         mValue == 1 ||
         mValue == 2)) {
            throw new Asn1InvalidEnumException (mValue);
         }

         int _aal = base.Encode (buffer, explicitTagging);

         return (_aal);
      }

      public override string ToString () {
         switch (mValue) {
            case 0: return ("publicKey");
            case 1: return ("publicKeyCert");
            case 2: return ("otherObjectTypes");
            default: return ("UNDEFINED");
         }
      }

      public override void Print (System.IO.TextWriter _out, 
                                  string _varName, int _level)
      {
         Indent (_out, _level);
         _out.WriteLine (_varName + " = " + ToString());
      }
   }
}
