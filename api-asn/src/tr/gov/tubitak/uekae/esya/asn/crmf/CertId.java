/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.crmf;

import com.objsys.asn1j.runtime.*;
import tr.gov.tubitak.uekae.esya.asn.x509.GeneralName;

public class CertId extends Asn1Type {

	private static final long serialVersionUID = 55;
	static {
		setKey(_crmfRtkey._rtkey);
	}

   public GeneralName issuer;
   public Asn1BigInteger serialNumber;

   public CertId () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public CertId (
      GeneralName issuer_,
      Asn1BigInteger serialNumber_
   ) {
      super();
      issuer = issuer_;
      serialNumber = serialNumber_;
   }

   public void init () {
      issuer = null;
      serialNumber = null;
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

      // decode issuer

      if (!_context.expired()) {
         Asn1Tag tag = buffer.peekTag ();
         if (tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0) ||
             tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 1) ||
             tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 2) ||
             tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 3) ||
             tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 4) ||
             tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 5) ||
             tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 6) ||
             tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 7) ||
             tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 8))
         {
            issuer = new GeneralName();
            issuer.decode (buffer, true, elemLen.value);
         }
         else throw new Asn1MissingRequiredException (buffer, "issuer");
      }
      else throw new Asn1MissingRequiredException (buffer, "issuer");

      // decode serialNumber

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
         serialNumber = new Asn1BigInteger();
         serialNumber.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "serialNumber");

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 2))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode serialNumber

      if (serialNumber != null) {
         len = serialNumber.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("serialNumber");

      // encode issuer

      if (issuer != null) {
         len = issuer.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("issuer");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (issuer != null) issuer.print (_out, "issuer", _level+1);
      if (serialNumber != null) serialNumber.print (_out, "serialNumber", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
