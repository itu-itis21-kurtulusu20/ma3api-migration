/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.2.5-pre5, Date: 24-Mar-2011.
 */
package tr.gov.tubitak.uekae.ekds.asn.EkdsElectronicIdentityCardApp;

import com.objsys.asn1j.runtime.*;
import java.io.*;
import tr.gov.tubitak.uekae.ekds.asn.EkdsCommonDataObjectDefs.DataObjectAttributes;

public class FingerPrintDataObject extends Asn1Type {
   public final static Asn1Tag TAG =
      new Asn1Tag (Asn1Tag.APPL, Asn1Tag.CONS, 3);

   public FPData fingerPrintData;
   public DataObjectAttributes dataObjectAttributes;
   public Asn1Integer errDetectionCode;

   public FingerPrintDataObject () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public FingerPrintDataObject (
      FPData fingerPrintData_,
      DataObjectAttributes dataObjectAttributes_,
      Asn1Integer errDetectionCode_
   ) {
      super();
      fingerPrintData = fingerPrintData_;
      dataObjectAttributes = dataObjectAttributes_;
      errDetectionCode = errDetectionCode_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public FingerPrintDataObject (FPData fingerPrintData_,
      DataObjectAttributes dataObjectAttributes_,
      long errDetectionCode_
   ) {
      super();
      fingerPrintData = fingerPrintData_;
      dataObjectAttributes = dataObjectAttributes_;
      errDetectionCode = new Asn1Integer (errDetectionCode_);
   }

   public void init () {
      fingerPrintData = null;
      dataObjectAttributes = null;
      errDetectionCode = null;
   }

   public void decode
      (Asn1BerDecodeBuffer buffer, boolean explicit, int implicitLength)
      throws Asn1Exception, IOException
   {
      int llen = (explicit) ?
         matchTag (buffer, TAG) : implicitLength;

      init ();

      // decode SEQUENCE

      Asn1BerDecodeContext _context =
         new Asn1BerDecodeContext (buffer, llen);

      IntHolder elemLen = new IntHolder();

      // decode fingerPrintData

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
         fingerPrintData = new FPData();
         fingerPrintData.decode (buffer, false, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer);

      // decode dataObjectAttributes

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 1, elemLen, true)) {
         dataObjectAttributes = new DataObjectAttributes();
         dataObjectAttributes.decode (buffer, false, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer);

      // decode errDetectionCode

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 2, elemLen, true)) {
         errDetectionCode = new Asn1Integer();
         errDetectionCode.decode (buffer, false, elemLen.value);
         if (!((errDetectionCode.value >= 0 && errDetectionCode.value <= 255))) {
            throw new Asn1ConsVioException ("errDetectionCode.value", errDetectionCode.value);
         }

      }
      else throw new Asn1MissingRequiredException (buffer);

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 1) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 2))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode errDetectionCode

      if (!((errDetectionCode.value >= 0 && errDetectionCode.value <= 255))) {
         throw new Asn1ConsVioException ("errDetectionCode.value", errDetectionCode.value);
      }

      len = errDetectionCode.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 2, len);
      _aal += len;

      // encode dataObjectAttributes

      len = dataObjectAttributes.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 1, len);
      _aal += len;

      // encode fingerPrintData

      len = fingerPrintData.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
      _aal += len;

      if (explicit) {
         _aal += buffer.encodeTagAndLength (TAG, _aal);
      }

      return (_aal);
   }

   public void print (PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (fingerPrintData != null) fingerPrintData.print (_out, "fingerPrintData", _level+1);
      if (dataObjectAttributes != null) dataObjectAttributes.print (_out, "dataObjectAttributes", _level+1);
      if (errDetectionCode != null) errDetectionCode.print (_out, "errDetectionCode", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
