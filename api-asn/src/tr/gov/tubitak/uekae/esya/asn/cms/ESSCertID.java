/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.cms;

import com.objsys.asn1j.runtime.*;

public class ESSCertID extends Asn1Type {
   private static final long serialVersionUID = 55;
   static {
      setKey (_cmsRtkey._rtkey);
   }

   public Asn1OctetString certHash;
   public IssuerSerial issuerSerial;  // optional

   public ESSCertID () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public ESSCertID (
      Asn1OctetString certHash_,
      IssuerSerial issuerSerial_
   ) {
      super();
      certHash = certHash_;
      issuerSerial = issuerSerial_;
   }

   /**
    * This constructor is for required elements only.  It sets 
    * all elements to references to the given objects
    */
   public ESSCertID (
      Asn1OctetString certHash_
   ) {
      super();
      certHash = certHash_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public ESSCertID (byte[] certHash_,
      IssuerSerial issuerSerial_
   ) {
      super();
      certHash = new Asn1OctetString (certHash_);
      issuerSerial = issuerSerial_;
   }

   /**
    * This constructor is for required elements only.  It allows 
    * primitive data to be passed for all primitive elements.  
    * It will create new object wrappers for the primitive data 
    * and set other elements to references to the given objects. 
    */
   public ESSCertID (
      byte[] certHash_
   ) {
      super();
      certHash = new Asn1OctetString (certHash_);
   }

   public void init () {
      certHash = null;
      issuerSerial = null;
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

      // decode certHash

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 4, elemLen, false)) {
         certHash = new Asn1OctetString();
         certHash.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "certHash");

      // decode issuerSerial

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         issuerSerial = new IssuerSerial();
         issuerSerial.decode (buffer, true, elemLen.value);
      }

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 4) ||
             _tag.equals (Asn1Tag.UNIV, Asn1Tag.CONS, 16))
            throw new Asn1SeqOrderException ();

      }
      if (explicit && llen == Asn1Status.INDEFLEN) {
         matchTag (buffer, Asn1Tag.EOC);
      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode issuerSerial

      if (issuerSerial != null) {
         len = issuerSerial.encode (buffer, true);
         _aal += len;
      }

      // encode certHash

      if (certHash != null) {
         len = certHash.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("certHash");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (certHash != null) certHash.print (_out, "certHash", _level+1);
      if (issuerSerial != null) issuerSerial.print (_out, "issuerSerial", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
