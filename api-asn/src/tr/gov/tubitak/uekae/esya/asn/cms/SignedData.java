/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.cms;

import com.objsys.asn1j.runtime.*;

public class SignedData extends Asn1Type {
   private static final long serialVersionUID = 55;
   static {
      setKey (_cmsRtkey._rtkey);
   }

   public CMSVersion version;
   public DigestAlgorithmIdentifiers digestAlgorithms;
   public EncapsulatedContentInfo encapContentInfo;
   public CertificateSet certificates;  // optional
   public RevocationInfoChoices crls;  // optional
   public SignerInfos signerInfos;

   public SignedData () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public SignedData (
      CMSVersion version_,
      DigestAlgorithmIdentifiers digestAlgorithms_,
      EncapsulatedContentInfo encapContentInfo_,
      CertificateSet certificates_,
      RevocationInfoChoices crls_,
      SignerInfos signerInfos_
   ) {
      super();
      version = version_;
      digestAlgorithms = digestAlgorithms_;
      encapContentInfo = encapContentInfo_;
      certificates = certificates_;
      crls = crls_;
      signerInfos = signerInfos_;
   }

   /**
    * This constructor is for required elements only.  It sets 
    * all elements to references to the given objects
    */
   public SignedData (
      CMSVersion version_,
      DigestAlgorithmIdentifiers digestAlgorithms_,
      EncapsulatedContentInfo encapContentInfo_,
      SignerInfos signerInfos_
   ) {
      super();
      version = version_;
      digestAlgorithms = digestAlgorithms_;
      encapContentInfo = encapContentInfo_;
      signerInfos = signerInfos_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public SignedData (long version_,
      DigestAlgorithmIdentifiers digestAlgorithms_,
      EncapsulatedContentInfo encapContentInfo_,
      CertificateSet certificates_,
      RevocationInfoChoices crls_,
      SignerInfos signerInfos_
   ) {
      super();
      version = new CMSVersion (version_);
      digestAlgorithms = digestAlgorithms_;
      encapContentInfo = encapContentInfo_;
      certificates = certificates_;
      crls = crls_;
      signerInfos = signerInfos_;
   }

   /**
    * This constructor is for required elements only.  It allows 
    * primitive data to be passed for all primitive elements.  
    * It will create new object wrappers for the primitive data 
    * and set other elements to references to the given objects. 
    */
   public SignedData (
      long version_,
      DigestAlgorithmIdentifiers digestAlgorithms_,
      EncapsulatedContentInfo encapContentInfo_,
      SignerInfos signerInfos_
   ) {
      super();
      version = new CMSVersion (version_);
      digestAlgorithms = digestAlgorithms_;
      encapContentInfo = encapContentInfo_;
      signerInfos = signerInfos_;
   }

   public void init () {
      version = null;
      digestAlgorithms = null;
      encapContentInfo = null;
      certificates = null;
      crls = null;
      signerInfos = null;
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
         version = new CMSVersion();
         version.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "version");

      // decode digestAlgorithms

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 17, elemLen, false)) {
         digestAlgorithms = new DigestAlgorithmIdentifiers();
         digestAlgorithms.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "digestAlgorithms");

      // decode encapContentInfo

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         encapContentInfo = new EncapsulatedContentInfo();
         encapContentInfo.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "encapContentInfo");

      // decode certificates

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
         int offset = buffer.getByteCount();
         certificates = new CertificateSet();
         certificates.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

         if (elemLen.value == Asn1Status.INDEFLEN) {
            matchTag (buffer, Asn1Tag.EOC);
         }
      }

      // decode crls

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 1, elemLen, true)) {
         int offset = buffer.getByteCount();
         crls = new RevocationInfoChoices();
         crls.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

         if (elemLen.value == Asn1Status.INDEFLEN) {
            matchTag (buffer, Asn1Tag.EOC);
         }
      }

      // decode signerInfos

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 17, elemLen, false)) {
         signerInfos = new SignerInfos();
         signerInfos.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "signerInfos");

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 2) ||
             _tag.equals (Asn1Tag.UNIV, Asn1Tag.CONS, 17) ||
             _tag.equals (Asn1Tag.UNIV, Asn1Tag.CONS, 16) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 1))
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

      // encode signerInfos

      if (signerInfos != null) {
         len = signerInfos.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("signerInfos");

      // encode crls

      if (crls != null) {
         len = crls.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 1, len);
         _aal += len;
      }

      // encode certificates

      if (certificates != null) {
         len = certificates.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
         _aal += len;
      }

      // encode encapContentInfo

      if (encapContentInfo != null) {
         len = encapContentInfo.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("encapContentInfo");

      // encode digestAlgorithms

      if (digestAlgorithms != null) {
         len = digestAlgorithms.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("digestAlgorithms");

      // encode version

      if (version != null) {
         len = version.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("version");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (version != null) version.print (_out, "version", _level+1);
      if (digestAlgorithms != null) digestAlgorithms.print (_out, "digestAlgorithms", _level+1);
      if (encapContentInfo != null) encapContentInfo.print (_out, "encapContentInfo", _level+1);
      if (certificates != null) certificates.print (_out, "certificates", _level+1);
      if (crls != null) crls.print (_out, "crls", _level+1);
      if (signerInfos != null) signerInfos.print (_out, "signerInfos", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
