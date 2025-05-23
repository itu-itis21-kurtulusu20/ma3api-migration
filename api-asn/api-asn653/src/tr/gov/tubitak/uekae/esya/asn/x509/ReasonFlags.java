/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 7.0.0, Date: 18-Mar-2016.
 */
package tr.gov.tubitak.uekae.esya.asn.x509;

import com.objsys.asn1j.runtime.*;

public class ReasonFlags extends Asn1BitString {
   private static final long serialVersionUID = 55;
   static {
      setKey (_ImplicitRtkey._rtkey);
   }

   public String getAsn1TypeName()  {
      return "ReasonFlags";
   }

   public final static int unused = 0;
   public final static int keyCompromise = 1;
   public final static int cACompromise = 2;
   public final static int affiliationChanged = 3;
   public final static int superseded = 4;
   public final static int cessationOfOperation = 5;
   public final static int certificateHold = 6;
   public final static int privilegeWithdrawn = 7;
   public final static int aACompromise = 8;

   /**
    * This constructor creates an empty bit string that can be used in 
    * a decode method call to receive a bit string value.
    */
   public ReasonFlags () {
      super();
      value = new byte [2];
      trimZeroBits = true;
   }

   /**
    * This constructor initializes the bit string value with the 
    * given bytes, using all 8 bits of every byte.
    *
    * @param data       Binary bit string contents
    */
   public ReasonFlags (byte[] data) {
      this( data.length * 8, data);
   }

   /**
    * This constructor initializes the bit string value with the 
    * given number of bits and data.
    *
    * @param numbits_   Number of bits
    * @param data       Binary bit string contents
    */
   public ReasonFlags (int numbits_, byte[] data) {
      super (numbits_, data);
      trimZeroBits = true;
   }

   /**
    * This constructor initializes the bit string value from the given 
    * boolean array.  Each array position corresponds to a bit in the 
    * bit string.
    *
    * @param bitValues  The boolean array
    */
   public ReasonFlags (boolean[] bitValues) {
      super (bitValues);
      trimZeroBits = true;
   }

   /**
    * This constructor parses the given ASN.1 value text (either a 
    * binary or hex data string) and assigns the values to the internal
    * bit string.
    *
    * Examples of valid value formats are as follows:
    * Binary string:    "'11010010111001'B"
    * Hex string:       "'0fa56920014abc'H"
    *
    */
   public ReasonFlags (String value_) throws Asn1ValueParseException {
      super (value_);
      trimZeroBits = true;
   }

   /**
    * This constructor initializes the bit string value from the given 
    * BitSet object.  The logical length of the BitSet is used 
    * (i.e. position of last set bit).
    *
    * @param bitSet  Java BitSet object
    */
   public ReasonFlags (java.util.BitSet bitSet) {
      super (bitSet);
      trimZeroBits = true;
   }

}
