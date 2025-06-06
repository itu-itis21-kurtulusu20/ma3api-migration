/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.2.5-pre5, Date: 24-Mar-2011.
 */
package tr.gov.tubitak.uekae.ekds.asn.EkdsCommonDataObjectDefs;

import com.objsys.asn1j.runtime.*;
import java.io.*;
import tr.gov.tubitak.uekae.ekds.asn.EkdsCommonDataObjectDefs.DataObjectEncryptionInfo_encAlgID;

public class DataObjectEncryptionInfo extends Asn1Type {
   public DataObjectEncryptionInfo_encAlgID encAlgID = null;
   public Asn1Integer encKeyID;

   public DataObjectEncryptionInfo () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public DataObjectEncryptionInfo (
      DataObjectEncryptionInfo_encAlgID encAlgID_,
      Asn1Integer encKeyID_
   ) {
      super();
      encAlgID = encAlgID_;
      encKeyID = encKeyID_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public DataObjectEncryptionInfo (DataObjectEncryptionInfo_encAlgID encAlgID_,
      long encKeyID_
   ) {
      super();
      encAlgID = encAlgID_;
      encKeyID = new Asn1Integer (encKeyID_);
   }

   public void init () {
      encAlgID = null;
      encKeyID = null;
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

      // decode encAlgID

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 0, elemLen, false)) {
         int tval = buffer.decodeEnumValue (DataObjectEncryptionInfo_encAlgID.TAG, true, elemLen.value);
         encAlgID = DataObjectEncryptionInfo_encAlgID.valueOf (tval);
      }
      else throw new Asn1MissingRequiredException (buffer);

      // decode encKeyID

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 1, elemLen, true)) {
         encKeyID = new Asn1Integer();
         encKeyID.decode (buffer, false, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer);

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 0) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 1))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode encKeyID

      len = encKeyID.encode (buffer, false);
      len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 1, len);
      _aal += len;

      // encode encAlgID

      len = encAlgID.encode (buffer, true);
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
      if (encAlgID != null) encAlgID.print (_out, "encAlgID", _level+1);
      if (encKeyID != null) encKeyID.print (_out, "encKeyID", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
