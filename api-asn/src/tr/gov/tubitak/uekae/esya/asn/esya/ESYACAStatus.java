/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.esya;

import com.objsys.asn1j.runtime.*;

public class ESYACAStatus extends Asn1Type {
   
	private static final long serialVersionUID = 55;
	static {
		setKey(_esyaRtkey._rtkey);
	}

   public ESYACAStatus_initializationStatus initializationStatus = null;
   public ESYACAStatus_certificationServiceStatus certificationServiceStatus = null;
   public ESYACAStatus_crlServiceStatus crlServiceStatus = null;

   public ESYACAStatus () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public ESYACAStatus (
      ESYACAStatus_initializationStatus initializationStatus_,
      ESYACAStatus_certificationServiceStatus certificationServiceStatus_,
      ESYACAStatus_crlServiceStatus crlServiceStatus_
   ) {
      super();
      initializationStatus = initializationStatus_;
      certificationServiceStatus = certificationServiceStatus_;
      crlServiceStatus = crlServiceStatus_;
   }

   public void init () {
      initializationStatus = null;
      certificationServiceStatus = null;
      crlServiceStatus = null;
   }

   public void decode
      (Asn1BerDecodeBuffer buffer, boolean explicit, int implicitLength)
      throws Asn1Exception, java.io.IOException
   {
      int llen = (explicit) ?
         matchTag (buffer, Asn1Tag.SEQUENCE) : implicitLength;

      init ();

      // decode SEQUENCE

      Asn1BerDecodeContext _context =
         new Asn1BerDecodeContext (buffer, llen);

      IntHolder elemLen = new IntHolder();

      // decode initializationStatus

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 10, elemLen, false)) {
         int tval = buffer.decodeEnumValue (ESYACAStatus_initializationStatus.TAG, true, elemLen.value);
         initializationStatus = ESYACAStatus_initializationStatus.valueOf (tval);
      }
      else throw new Asn1MissingRequiredException (buffer, "initializationStatus");

      // decode certificationServiceStatus

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 10, elemLen, false)) {
         int tval = buffer.decodeEnumValue (ESYACAStatus_certificationServiceStatus.TAG, true, elemLen.value);
         certificationServiceStatus = ESYACAStatus_certificationServiceStatus.valueOf (tval);
      }
      else throw new Asn1MissingRequiredException (buffer, "certificationServiceStatus");

      // decode crlServiceStatus

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 10, elemLen, false)) {
         int tval = buffer.decodeEnumValue (ESYACAStatus_crlServiceStatus.TAG, true, elemLen.value);
         crlServiceStatus = ESYACAStatus_crlServiceStatus.valueOf (tval);
      }
      else throw new Asn1MissingRequiredException (buffer, "crlServiceStatus");

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 10))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode crlServiceStatus

      if (crlServiceStatus != null) {
         len = crlServiceStatus.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("crlServiceStatus");

      // encode certificationServiceStatus

      if (certificationServiceStatus != null) {
         len = certificationServiceStatus.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("certificationServiceStatus");

      // encode initializationStatus

      if (initializationStatus != null) {
         len = initializationStatus.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("initializationStatus");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (initializationStatus != null) initializationStatus.print (_out, "initializationStatus", _level+1);
      if (certificationServiceStatus != null) certificationServiceStatus.print (_out, "certificationServiceStatus", _level+1);
      if (crlServiceStatus != null) crlServiceStatus.print (_out, "crlServiceStatus", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
