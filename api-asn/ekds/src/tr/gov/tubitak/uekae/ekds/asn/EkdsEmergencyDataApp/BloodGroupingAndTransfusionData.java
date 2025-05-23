/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.2.5-pre5, Date: 24-Mar-2011.
 */
package tr.gov.tubitak.uekae.ekds.asn.EkdsEmergencyDataApp;

import com.objsys.asn1j.runtime.*;
import java.io.*;

public class BloodGroupingAndTransfusionData extends Asn1Type {
   public BloodGrouping bloodGrouping;
   public BloodTransfusionData bloodTransfusionData;

   public BloodGroupingAndTransfusionData () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public BloodGroupingAndTransfusionData (
      BloodGrouping bloodGrouping_,
      BloodTransfusionData bloodTransfusionData_
   ) {
      super();
      bloodGrouping = bloodGrouping_;
      bloodTransfusionData = bloodTransfusionData_;
   }

   public void init () {
      bloodGrouping = null;
      bloodTransfusionData = null;
   }

   public void decode
      (Asn1BerDecodeBuffer buffer, boolean explicit, int implicitLength)
      throws Asn1Exception, IOException
   {
      int llen = (explicit) ?
         matchTag (buffer, Asn1Tag.SET) : implicitLength;

      init ();

      // decode SET

      Asn1BerDecodeContext _context =
         new Asn1BerDecodeContext (buffer, llen);

      Asn1Tag tag = new Asn1Tag ();

      while (!_context.expired()) {
         buffer.mark (8);

         int len = buffer.decodeTagAndLength (tag);

         // decode bloodGrouping

         if (tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0))
         {
            if (null == bloodGrouping) {
               bloodGrouping = new BloodGrouping();
               bloodGrouping.decode (buffer, false, len);
            }
            else throw new Asn1SetDuplicateException (buffer, tag);
         }
         // decode bloodTransfusionData

         else if (tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 1))
         {
            if (null == bloodTransfusionData) {
               bloodTransfusionData = new BloodTransfusionData();
               bloodTransfusionData.decode (buffer, false, len);
            }
            else throw new Asn1SetDuplicateException (buffer, tag);
         }
         else throw new Asn1NotInSetException (buffer, tag);
      }

      if (null == bloodGrouping ||
          null == bloodTransfusionData)
         throw new Asn1MissingRequiredException (buffer);

   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode bloodTransfusionData

      len = bloodTransfusionData.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 1, len);
      _aal += len;

      // encode bloodGrouping

      len = bloodGrouping.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
      _aal += len;

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SET, _aal);
      }

      return (_aal);
   }

   public void print (PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (bloodGrouping != null) bloodGrouping.print (_out, "bloodGrouping", _level+1);
      if (bloodTransfusionData != null) bloodTransfusionData.print (_out, "bloodTransfusionData", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
