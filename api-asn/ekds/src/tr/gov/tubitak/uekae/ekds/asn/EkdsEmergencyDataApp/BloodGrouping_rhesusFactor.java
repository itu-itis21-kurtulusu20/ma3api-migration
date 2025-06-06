/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.2.5-pre5, Date: 24-Mar-2011.
 */
package tr.gov.tubitak.uekae.ekds.asn.EkdsEmergencyDataApp;

import com.objsys.asn1j.runtime.*;
import java.io.*;

public class BloodGrouping_rhesusFactor extends Asn1Enumerated {
   public final static Asn1Tag TAG =
      new Asn1Tag (Asn1Tag.CTXT, Asn1Tag.PRIM, 1);

   // Integer constants for switch-case
   public static final int _POSITIVE = 0;
   public static final int _NEGATIVE = 1;
   public static final int _NOTINITIALIZED = 2;

   // Singleton instances of BloodGrouping_rhesusFactor
   protected static BloodGrouping_rhesusFactor _positive = null;
   protected static BloodGrouping_rhesusFactor _negative = null;
   protected static BloodGrouping_rhesusFactor _notInitialized = null;

   /**
    * Enumerated constructor: this object must be initialized with one 
    * of the following values:
    *   0
    *   1
    *   2
    */
   protected BloodGrouping_rhesusFactor (int value_) {
      super (value_);
   }

   /**
    * Singleton accessor method for positive.
    */
   public static BloodGrouping_rhesusFactor positive() {
      if (_positive == null) _positive = new BloodGrouping_rhesusFactor (0);

      return _positive;
   }

   /**
    * Singleton accessor method for negative.
    */
   public static BloodGrouping_rhesusFactor negative() {
      if (_negative == null) _negative = new BloodGrouping_rhesusFactor (1);

      return _negative;
   }

   /**
    * Singleton accessor method for notInitialized.
    */
   public static BloodGrouping_rhesusFactor notInitialized() {
      if (_notInitialized == null) _notInitialized = new BloodGrouping_rhesusFactor (2);

      return _notInitialized;
   }

   /**
    * Returns the instance associated with the value passed.
    * If the value passed is invalid, an exception is thrown.
    */
   public static BloodGrouping_rhesusFactor valueOf (int _value)
      throws Asn1InvalidEnumException
   {
      switch ((int)_value) {
         case 0: return positive();
         case 1: return negative();
         case 2: return notInitialized();
         default: throw new Asn1InvalidEnumException (_value);
      }
   }

   public void decode
      (Asn1BerDecodeBuffer buffer, boolean explicit, int implicitLength)
      throws Asn1Exception, IOException
   {
      int llen = matchTag (buffer, Asn1Tag.CTXT, Asn1Tag.PRIM, 1);

      super.decode (buffer, false, llen);

      if (!(value == 0 ||
            value == 1 ||
            value == 2))
      {
         throw new Asn1InvalidEnumException (value);
      }

   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      if (!(value == 0 ||
            value == 1 ||
            value == 2))
      {
         throw new Asn1InvalidEnumException (value);
      }

      int len;
      len = super.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 1, len);
      return (len);
   }

   public String toString () {
      switch ((int)value) {
         case 0: return ("positive");
         case 1: return ("negative");
         case 2: return ("notInitialized");
         default: return ("UNDEFINED");
      }
   }

   public void print (PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " = " + toString());
   }
}
