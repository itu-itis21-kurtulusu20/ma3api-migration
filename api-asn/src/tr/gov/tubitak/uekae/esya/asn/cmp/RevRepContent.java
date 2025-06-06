/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.cmp;

import com.objsys.asn1j.runtime.*;

public class RevRepContent extends Asn1Type {
 
	private static final long serialVersionUID = 55;
	static {
		setKey(_cmpRtkey._rtkey);
	}

   public RevRepContent_status status;
   public RevRepContent_revCerts revCerts;  // optional
   public RevRepContent_crls crls;  // optional

   public RevRepContent () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public RevRepContent (
      RevRepContent_status status_,
      RevRepContent_revCerts revCerts_,
      RevRepContent_crls crls_
   ) {
      super();
      status = status_;
      revCerts = revCerts_;
      crls = crls_;
   }

   /**
    * This constructor is for required elements only.  It sets 
    * all elements to references to the given objects
    */
   public RevRepContent (
      RevRepContent_status status_
   ) {
      super();
      status = status_;
   }

   public void init () {
      status = null;
      revCerts = null;
      crls = null;
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

      // decode status

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         status = new RevRepContent_status();
         status.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "status");

      // decode revCerts

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, false)) {
         revCerts = new RevRepContent_revCerts();
         revCerts.decode (buffer, true, elemLen.value);
      }

      // decode crls

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 1, elemLen, false)) {
         crls = new RevRepContent_crls();
         crls.decode (buffer, true, elemLen.value);
      }

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.CONS, 16) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 1))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode crls

      if (crls != null) {
         len = crls.encode (buffer, true);
         _aal += len;
      }

      // encode revCerts

      if (revCerts != null) {
         len = revCerts.encode (buffer, true);
         _aal += len;
      }

      // encode status

      if (status != null) {
         len = status.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("status");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (status != null) status.print (_out, "status", _level+1);
      if (revCerts != null) revCerts.print (_out, "revCerts", _level+1);
      if (crls != null) crls.print (_out, "crls", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
