/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.ocsp;

import com.objsys.asn1j.runtime.*;
import tr.gov.tubitak.uekae.esya.asn.x509.AlgorithmIdentifier;

public class PreferredSignatureAlgorithm extends Asn1Type {

	private static final long serialVersionUID = 55;
	static {
		setKey(_ocspRtkey._rtkey);
	}

   public AlgorithmIdentifier sigIdentifier;
   public AlgorithmIdentifier certIdentifier;  // optional

   public PreferredSignatureAlgorithm () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public PreferredSignatureAlgorithm (
      AlgorithmIdentifier sigIdentifier_,
      AlgorithmIdentifier certIdentifier_
   ) {
      super();
      sigIdentifier = sigIdentifier_;
      certIdentifier = certIdentifier_;
   }

   /**
    * This constructor is for required elements only.  It sets 
    * all elements to references to the given objects
    */
   public PreferredSignatureAlgorithm (
      AlgorithmIdentifier sigIdentifier_
   ) {
      super();
      sigIdentifier = sigIdentifier_;
   }

   public void init () {
      sigIdentifier = null;
      certIdentifier = null;
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

      // decode sigIdentifier

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         sigIdentifier = new AlgorithmIdentifier();
         sigIdentifier.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "sigIdentifier");

      // decode certIdentifier

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         certIdentifier = new AlgorithmIdentifier();
         certIdentifier.decode (buffer, true, elemLen.value);
      }

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.CONS, 16))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode certIdentifier

      if (certIdentifier != null) {
         len = certIdentifier.encode (buffer, true);
         _aal += len;
      }

      // encode sigIdentifier

      if (sigIdentifier != null) {
         len = sigIdentifier.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("sigIdentifier");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (sigIdentifier != null) sigIdentifier.print (_out, "sigIdentifier", _level+1);
      if (certIdentifier != null) certIdentifier.print (_out, "certIdentifier", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
