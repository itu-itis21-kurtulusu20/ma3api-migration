// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.attrcert {

   public class ClassList : Asn1BitString {
      public static readonly int unmarked = 0;
      public static readonly int unclassified = 1;
      public static readonly int restricted = 2;
      public static readonly int confidential = 3;
      public static readonly int secret = 4;
      public static readonly int topSecret = 5;

      static ClassList ()
      {
         Asn1Type.SetKey2 (_attrcertValues._rtkey);
      }

      /// <summary>
      /// This constructor creates an empty bit string that can be used in 
      /// a decode method call to receive a bit string value.
      /// </summary>
      public ClassList () : base()
      {
         mValue = new byte [1];
         trimZeroBits = true;
         Asn1Type.SetKey2 (_attrcertValues._rtkey);
      }

      /// <summary>
      /// This constructor initializes the bit string value with the 
      /// given bytes, using all 8 bits of each byte.
      /// </summary>
      /// <param name="data"> Binary bit string contents </param>
      public ClassList (byte[] data) :
         this (data.Length * 8, data)
      {}

      /// <summary>
      /// This constructor initializes the bit string value with the 
      /// given number of bits and data.
      /// </summary>
      /// <param name="numbits_"> Number of bits </param>
      /// <param name="data"> Binary bit string contents </param>
      public ClassList (int numbits_, byte[] data) :
         base (numbits_, data)
      {
         trimZeroBits = true;
         Asn1Type.SetKey2 (_attrcertValues._rtkey);
      }

      /// <summary>
      /// This constructor initializes the bit string value from the given 
      /// bool array.  Each array position corresponds to a bit in the 
      /// bit string.
      /// </summary>
      /// <param name="bitValues"> The bool array </param>
      public ClassList (bool[] bitValues) :
         base (bitValues)
      {
         trimZeroBits = true;
         Asn1Type.SetKey2 (_attrcertValues._rtkey);
      }

      /// <summary>
      /// This constructor parses the given ASN.1 value text (either a 
      /// binary or hex data string) and assigns the values to the internal
      /// bit string.
      ///
      /// Examples of valid value formats are as follows:
      /// Binary string:    "'11010010111001'B"
      /// Hex string:       "'0fa56920014abc'H"
      /// </summary>
      /// <param name="value"> The ASN.1 value specification text </param>
      public ClassList (string value_) :
         base (value_)
      {
         trimZeroBits = true;
         Asn1Type.SetKey2 (_attrcertValues._rtkey);
      }

      /// <summary>
      /// This constructor initializes the bit string value from the given 
      /// BitArray object.  The logical length of the BitSet is used 
      /// (i.e. position of last set bit).
      /// </summary>
      /// <param name="bitArray"> Csharp BitArray object </param>
      public ClassList (System.Collections.BitArray bitArray) :
         base (bitArray)
      {
         trimZeroBits = true;
         Asn1Type.SetKey2 (_attrcertValues._rtkey);
      }

   }
}
