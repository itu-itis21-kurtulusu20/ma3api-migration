/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.cms;

import com.objsys.asn1j.runtime.*;

public class EnvelopedData extends Asn1Type {
   private static final long serialVersionUID = 55;
   static {
      setKey (_cmsRtkey._rtkey);
   }

   public CMSVersion version;
   public OriginatorInfo originatorInfo;  // optional
   public RecipientInfos recipientInfos;
   public EncryptedContentInfo encryptedContentInfo;
   public UnprotectedAttributes unprotectedAttrs;  // optional

   public EnvelopedData () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public EnvelopedData (
      CMSVersion version_,
      OriginatorInfo originatorInfo_,
      RecipientInfos recipientInfos_,
      EncryptedContentInfo encryptedContentInfo_,
      UnprotectedAttributes unprotectedAttrs_
   ) {
      super();
      version = version_;
      originatorInfo = originatorInfo_;
      recipientInfos = recipientInfos_;
      encryptedContentInfo = encryptedContentInfo_;
      unprotectedAttrs = unprotectedAttrs_;
   }

   /**
    * This constructor is for required elements only.  It sets 
    * all elements to references to the given objects
    */
   public EnvelopedData (
      CMSVersion version_,
      RecipientInfos recipientInfos_,
      EncryptedContentInfo encryptedContentInfo_
   ) {
      super();
      version = version_;
      recipientInfos = recipientInfos_;
      encryptedContentInfo = encryptedContentInfo_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public EnvelopedData (long version_,
      OriginatorInfo originatorInfo_,
      RecipientInfos recipientInfos_,
      EncryptedContentInfo encryptedContentInfo_,
      UnprotectedAttributes unprotectedAttrs_
   ) {
      super();
      version = new CMSVersion (version_);
      originatorInfo = originatorInfo_;
      recipientInfos = recipientInfos_;
      encryptedContentInfo = encryptedContentInfo_;
      unprotectedAttrs = unprotectedAttrs_;
   }

   /**
    * This constructor is for required elements only.  It allows 
    * primitive data to be passed for all primitive elements.  
    * It will create new object wrappers for the primitive data 
    * and set other elements to references to the given objects. 
    */
   public EnvelopedData (
      long version_,
      RecipientInfos recipientInfos_,
      EncryptedContentInfo encryptedContentInfo_
   ) {
      super();
      version = new CMSVersion (version_);
      recipientInfos = recipientInfos_;
      encryptedContentInfo = encryptedContentInfo_;
   }

   public void init () {
      version = null;
      originatorInfo = null;
      recipientInfos = null;
      encryptedContentInfo = null;
      unprotectedAttrs = null;
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

      // decode originatorInfo

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
         int offset = buffer.getByteCount();
         originatorInfo = new OriginatorInfo();
         originatorInfo.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

         if (elemLen.value == Asn1Status.INDEFLEN) {
            matchTag (buffer, Asn1Tag.EOC);
         }
      }

      // decode recipientInfos

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 17, elemLen, false)) {
         recipientInfos = new RecipientInfos();
         recipientInfos.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "recipientInfos");

      // decode encryptedContentInfo

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         encryptedContentInfo = new EncryptedContentInfo();
         encryptedContentInfo.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "encryptedContentInfo");

      // decode unprotectedAttrs

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 1, elemLen, true)) {
         int offset = buffer.getByteCount();
         unprotectedAttrs = new UnprotectedAttributes();
         unprotectedAttrs.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

         if (elemLen.value == Asn1Status.INDEFLEN) {
            matchTag (buffer, Asn1Tag.EOC);
         }
      }

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 2) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0) ||
             _tag.equals (Asn1Tag.UNIV, Asn1Tag.CONS, 17) ||
             _tag.equals (Asn1Tag.UNIV, Asn1Tag.CONS, 16) ||
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

      // encode unprotectedAttrs

      if (unprotectedAttrs != null) {
         len = unprotectedAttrs.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 1, len);
         _aal += len;
      }

      // encode encryptedContentInfo

      if (encryptedContentInfo != null) {
         len = encryptedContentInfo.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("encryptedContentInfo");

      // encode recipientInfos

      if (recipientInfos != null) {
         len = recipientInfos.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("recipientInfos");

      // encode originatorInfo

      if (originatorInfo != null) {
         len = originatorInfo.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
         _aal += len;
      }

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
      if (originatorInfo != null) originatorInfo.print (_out, "originatorInfo", _level+1);
      if (recipientInfos != null) recipientInfos.print (_out, "recipientInfos", _level+1);
      if (encryptedContentInfo != null) encryptedContentInfo.print (_out, "encryptedContentInfo", _level+1);
      if (unprotectedAttrs != null) unprotectedAttrs.print (_out, "unprotectedAttrs", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
