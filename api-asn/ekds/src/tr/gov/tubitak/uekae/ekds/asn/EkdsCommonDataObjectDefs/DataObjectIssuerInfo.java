/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.2.5-pre5, Date: 24-Mar-2011.
 */
package tr.gov.tubitak.uekae.ekds.asn.EkdsCommonDataObjectDefs;

import com.objsys.asn1j.runtime.*;
import java.io.*;

public class DataObjectIssuerInfo extends Asn1Type {
   public Asn1NumericString issuerID;
   public Asn1UTF8String issuerFirstName;
   public Asn1UTF8String issuerSurname;
   public Asn1UTF8String issuerEmployment;

   public DataObjectIssuerInfo () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public DataObjectIssuerInfo (
      Asn1NumericString issuerID_,
      Asn1UTF8String issuerFirstName_,
      Asn1UTF8String issuerSurname_,
      Asn1UTF8String issuerEmployment_
   ) {
      super();
      issuerID = issuerID_;
      issuerFirstName = issuerFirstName_;
      issuerSurname = issuerSurname_;
      issuerEmployment = issuerEmployment_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public DataObjectIssuerInfo (String issuerID_,
      String issuerFirstName_,
      String issuerSurname_,
      String issuerEmployment_
   ) {
      super();
      issuerID = new Asn1NumericString (issuerID_);
      issuerFirstName = new Asn1UTF8String (issuerFirstName_);
      issuerSurname = new Asn1UTF8String (issuerSurname_);
      issuerEmployment = new Asn1UTF8String (issuerEmployment_);
   }

   public void init () {
      issuerID = null;
      issuerFirstName = null;
      issuerSurname = null;
      issuerEmployment = null;
   }

   public void decode
      (Asn1BerDecodeBuffer buffer, boolean explicit, int implicitLength)
      throws Asn1Exception, IOException
   {
      int llen = (explicit) ?
         matchTag (buffer, Asn1Tag.SEQUENCE) : implicitLength;

      init ();

      // decode SEQUENCE

      Asn1BerDecodeContext _context =
         new Asn1BerDecodeContext (buffer, llen);

      IntHolder elemLen = new IntHolder();

      // decode issuerID

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 0, elemLen, true)) {
         issuerID = new Asn1NumericString();
         issuerID.decode (buffer, false, elemLen.value);
         if (!((issuerID.getLength() >= 1 && issuerID.getLength() <= 12))) {
            throw new Asn1ConsVioException ("issuerID.getLength()", issuerID.getLength());
         }

      }
      else throw new Asn1MissingRequiredException (buffer);

      // decode issuerFirstName

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 1, elemLen, true)) {
         issuerFirstName = new Asn1UTF8String();
         issuerFirstName.decode (buffer, false, elemLen.value);
         if (!((issuerFirstName.getLength() >= 2 && issuerFirstName.getLength() <= 64))) {
            throw new Asn1ConsVioException ("issuerFirstName.getLength()", issuerFirstName.getLength());
         }

      }
      else throw new Asn1MissingRequiredException (buffer);

      // decode issuerSurname

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 2, elemLen, true)) {
         issuerSurname = new Asn1UTF8String();
         issuerSurname.decode (buffer, false, elemLen.value);
         if (!((issuerSurname.getLength() >= 2 && issuerSurname.getLength() <= 64))) {
            throw new Asn1ConsVioException ("issuerSurname.getLength()", issuerSurname.getLength());
         }

      }
      else throw new Asn1MissingRequiredException (buffer);

      // decode issuerEmployment

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 3, elemLen, true)) {
         issuerEmployment = new Asn1UTF8String();
         issuerEmployment.decode (buffer, false, elemLen.value);
         if (!((issuerEmployment.getLength() >= 2 && issuerEmployment.getLength()
             <= 64))) {
            throw new Asn1ConsVioException ("issuerEmployment.getLength()", issuerEmployment.getLength());
         }

      }
      else throw new Asn1MissingRequiredException (buffer);

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 0) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 1) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 2) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 3))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode issuerEmployment

      if (!((issuerEmployment.getLength() >= 2 && issuerEmployment.getLength() <= 64))) {
         throw new Asn1ConsVioException ("issuerEmployment.getLength()", issuerEmployment.getLength());
      }

      len = issuerEmployment.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 3, len);
      _aal += len;

      // encode issuerSurname

      if (!((issuerSurname.getLength() >= 2 && issuerSurname.getLength() <= 64))) {
         throw new Asn1ConsVioException ("issuerSurname.getLength()", issuerSurname.getLength());
      }

      len = issuerSurname.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 2, len);
      _aal += len;

      // encode issuerFirstName

      if (!((issuerFirstName.getLength() >= 2 && issuerFirstName.getLength() <= 64))) {
         throw new Asn1ConsVioException ("issuerFirstName.getLength()", issuerFirstName.getLength());
      }

      len = issuerFirstName.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 1, len);
      _aal += len;

      // encode issuerID

      if (!((issuerID.getLength() >= 1 && issuerID.getLength() <= 12))) {
         throw new Asn1ConsVioException ("issuerID.getLength()", issuerID.getLength());
      }

      len = issuerID.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 0, len);
      _aal += len;

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (issuerID != null) issuerID.print (_out, "issuerID", _level+1);
      if (issuerFirstName != null) issuerFirstName.print (_out, "issuerFirstName", _level+1);
      if (issuerSurname != null) issuerSurname.print (_out, "issuerSurname", _level+1);
      if (issuerEmployment != null) issuerEmployment.print (_out, "issuerEmployment", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
