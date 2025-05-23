/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.esya.crlfinder;

import com.objsys.asn1j.runtime.*;
import tr.gov.tubitak.uekae.esya.asn.cms.IssuerAndSerialNumber;
import tr.gov.tubitak.uekae.esya.asn.x509.Name;

public class CRLRequest extends Asn1Type {

	private static final long serialVersionUID = 55;
	static {
		setKey(_crlfinderRtkey._rtkey);
	}

   public IssuerAndSerialNumber issuerSerialOfIssuer;  // optional
   public Name crlIssuer;  // optional
   public CRLRequestType crlRequestType;

   public CRLRequest () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public CRLRequest (
      IssuerAndSerialNumber issuerSerialOfIssuer_,
      Name crlIssuer_,
      CRLRequestType crlRequestType_
   ) {
      super();
      issuerSerialOfIssuer = issuerSerialOfIssuer_;
      crlIssuer = crlIssuer_;
      crlRequestType = crlRequestType_;
   }

   /**
    * This constructor is for required elements only.  It sets 
    * all elements to references to the given objects
    */
   public CRLRequest (
      CRLRequestType crlRequestType_
   ) {
      super();
      crlRequestType = crlRequestType_;
   }

   public void init () {
      issuerSerialOfIssuer = null;
      crlIssuer = null;
      crlRequestType = null;
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

      // decode issuerSerialOfIssuer

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         issuerSerialOfIssuer = new IssuerAndSerialNumber();
         issuerSerialOfIssuer.decode (buffer, true, elemLen.value);
      }

      // decode crlIssuer

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 10, elemLen, true)) {
         int offset = buffer.getByteCount();
         crlIssuer = new Name();
         crlIssuer.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }

      // decode crlRequestType

      if (!_context.expired()) {
         Asn1Tag tag = buffer.peekTag ();
         if (tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 0) ||
             tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 1) ||
             tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 2) ||
             tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 3))
         {
            crlRequestType = new CRLRequestType();
            crlRequestType.decode (buffer, true, elemLen.value);
         }
         else throw new Asn1MissingRequiredException (buffer, "crlRequestType");
      }
      else throw new Asn1MissingRequiredException (buffer, "crlRequestType");

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.CONS, 16) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 10))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode crlRequestType

      if (crlRequestType != null) {
         len = crlRequestType.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("crlRequestType");

      // encode crlIssuer

      if (crlIssuer != null) {
         len = crlIssuer.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 10, len);
         _aal += len;
      }

      // encode issuerSerialOfIssuer

      if (issuerSerialOfIssuer != null) {
         len = issuerSerialOfIssuer.encode (buffer, true);
         _aal += len;
      }

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (issuerSerialOfIssuer != null) issuerSerialOfIssuer.print (_out, "issuerSerialOfIssuer", _level+1);
      if (crlIssuer != null) crlIssuer.print (_out, "crlIssuer", _level+1);
      if (crlRequestType != null) crlRequestType.print (_out, "crlRequestType", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
