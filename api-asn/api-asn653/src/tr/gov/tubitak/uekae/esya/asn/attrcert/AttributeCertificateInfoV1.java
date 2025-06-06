/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.5.3, Date: 26-Jul-2012.
 */
package tr.gov.tubitak.uekae.esya.asn.attrcert;

import com.objsys.asn1j.runtime.*;
import tr.gov.tubitak.uekae.esya.asn.x509.AlgorithmIdentifier;
import tr.gov.tubitak.uekae.esya.asn.x509.Extensions;
import tr.gov.tubitak.uekae.esya.asn.x509.GeneralNames;

public class AttributeCertificateInfoV1 extends Asn1Type {
   private static final long serialVersionUID = 55;
   static {
      setKey (_attrcertRtkey._rtkey);
   }

   public AttCertVersionV1 version;  // default = AttCertVersionV1.v1()
   public AttributeCertificateInfoV1_subject subject;
   public GeneralNames issuer;
   public AlgorithmIdentifier signature;
   public Asn1BigInteger serialNumber;
   public AttCertValidityPeriod attCertValidityPeriod;
   public _SeqOfAttribute attributes;
   public Asn1BitString issuerUniqueID;  // optional
   public Extensions extensions;  // optional

   public AttributeCertificateInfoV1 () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public AttributeCertificateInfoV1 (
      AttCertVersionV1 version_,
      AttributeCertificateInfoV1_subject subject_,
      GeneralNames issuer_,
      AlgorithmIdentifier signature_,
      Asn1BigInteger serialNumber_,
      AttCertValidityPeriod attCertValidityPeriod_,
      _SeqOfAttribute attributes_,
      Asn1BitString issuerUniqueID_,
      Extensions extensions_
   ) {
      super();
      version = version_;
      subject = subject_;
      issuer = issuer_;
      signature = signature_;
      serialNumber = serialNumber_;
      attCertValidityPeriod = attCertValidityPeriod_;
      attributes = attributes_;
      issuerUniqueID = issuerUniqueID_;
      extensions = extensions_;
   }

   /**
    * This constructor is for required elements only.  It sets 
    * all elements to references to the given objects
    */
   public AttributeCertificateInfoV1 (
      AttributeCertificateInfoV1_subject subject_,
      GeneralNames issuer_,
      AlgorithmIdentifier signature_,
      Asn1BigInteger serialNumber_,
      AttCertValidityPeriod attCertValidityPeriod_,
      _SeqOfAttribute attributes_
   ) {
      super();
      version = new AttCertVersionV1 (AttCertVersionV1.v1);
      subject = subject_;
      issuer = issuer_;
      signature = signature_;
      serialNumber = serialNumber_;
      attCertValidityPeriod = attCertValidityPeriod_;
      attributes = attributes_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public AttributeCertificateInfoV1 (long version_,
      AttributeCertificateInfoV1_subject subject_,
      GeneralNames issuer_,
      AlgorithmIdentifier signature_,
      Asn1BigInteger serialNumber_,
      AttCertValidityPeriod attCertValidityPeriod_,
      _SeqOfAttribute attributes_,
      Asn1BitString issuerUniqueID_,
      Extensions extensions_
   ) {
      super();
      version = new AttCertVersionV1 (version_);
      subject = subject_;
      issuer = issuer_;
      signature = signature_;
      serialNumber = serialNumber_;
      attCertValidityPeriod = attCertValidityPeriod_;
      attributes = attributes_;
      issuerUniqueID = issuerUniqueID_;
      extensions = extensions_;
   }

   public void init () {
      version = new AttCertVersionV1 (AttCertVersionV1.v1);
      subject = null;
      issuer = null;
      signature = null;
      serialNumber = null;
      attCertValidityPeriod = null;
      attributes = null;
      issuerUniqueID = null;
      extensions = null;
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

      // decode version

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
         version = new AttCertVersionV1();
         version.decode (buffer, true, elemLen.value);
      }

      // decode subject

      if (!_context.expired()) {
         Asn1Tag tag = buffer.peekTag ();
         if (tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0) ||
             tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 1))
         {
            subject = new AttributeCertificateInfoV1_subject();
            subject.decode (buffer, true, elemLen.value);
         }
         else throw new Asn1MissingRequiredException (buffer, "subject");
      }
      else throw new Asn1MissingRequiredException (buffer, "subject");

      // decode issuer

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         issuer = new GeneralNames();
         issuer.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "issuer");

      // decode signature

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         signature = new AlgorithmIdentifier();
         signature.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "signature");

      // decode serialNumber

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
         serialNumber = new Asn1BigInteger();
         serialNumber.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "serialNumber");

      // decode attCertValidityPeriod

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         attCertValidityPeriod = new AttCertValidityPeriod();
         attCertValidityPeriod.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "attCertValidityPeriod");

      // decode attributes

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         attributes = new _SeqOfAttribute();
         attributes.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "attributes");

      // decode issuerUniqueID

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 3, elemLen, false)) {
         issuerUniqueID = new Asn1BitString();
         issuerUniqueID.decode (buffer, true, elemLen.value);
      }

      // decode extensions

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         extensions = new Extensions();
         extensions.decode (buffer, true, elemLen.value);
      }

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 2) ||
             _tag.equals (Asn1Tag.UNIV, Asn1Tag.CONS, 16) ||
             _tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 3))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode extensions

      if (extensions != null) {
         len = extensions.encode (buffer, true);
         _aal += len;
      }

      // encode issuerUniqueID

      if (issuerUniqueID != null) {
         len = issuerUniqueID.encode (buffer, true);
         _aal += len;
      }

      // encode attributes

      if (attributes != null) {
         len = attributes.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("attributes");

      // encode attCertValidityPeriod

      if (attCertValidityPeriod != null) {
         len = attCertValidityPeriod.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("attCertValidityPeriod");

      // encode serialNumber

      if (serialNumber != null) {
         len = serialNumber.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("serialNumber");

      // encode signature

      if (signature != null) {
         len = signature.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("signature");

      // encode issuer

      if (issuer != null) {
         len = issuer.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("issuer");

      // encode subject

      if (subject != null) {
         len = subject.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("subject");

      // encode version

      if (version != null) {
         if (!version.equals (AttCertVersionV1.v1)) {
            len = version.encode (buffer, true);
            _aal += len;
         }
      }

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintWriter _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (version != null) version.print (_out, "version", _level+1);
      if (subject != null) subject.print (_out, "subject", _level+1);
      if (issuer != null) issuer.print (_out, "issuer", _level+1);
      if (signature != null) signature.print (_out, "signature", _level+1);
      if (serialNumber != null) serialNumber.print (_out, "serialNumber", _level+1);
      if (attCertValidityPeriod != null) attCertValidityPeriod.print (_out, "attCertValidityPeriod", _level+1);
      if (attributes != null) attributes.print (_out, "attributes", _level+1);
      if (issuerUniqueID != null) issuerUniqueID.print (_out, "issuerUniqueID", _level+1);
      if (extensions != null) extensions.print (_out, "extensions", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
