/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 7.0.0, Date: 10-Sep-2018.
 */
package tr.gov.tubitak.uekae.esya.asn.cms;

import com.objsys.asn1j.runtime.*;
import tr.gov.tubitak.uekae.esya.asn.cms.AuthAttributes;

public class AuthEnvelopedData extends Asn1Seq {
   private static final long serialVersionUID = 55;
   static {
      setKey (_cmsRtkey._rtkey);
   }

   public String getAsn1TypeName()  {
      return "AuthEnvelopedData";
   }

   public CMSVersion version;
   public OriginatorInfo originatorInfo;  // optional
   public RecipientInfos recipientInfos;
   public EncryptedContentInfo authEncryptedContentInfo;
   public AuthAttributes authAttrs;  // optional
   public Asn1OctetString mac;
   public UnauthAttributes unauthAttrs;  // optional

   public AuthEnvelopedData () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public AuthEnvelopedData (
      CMSVersion version_,
      OriginatorInfo originatorInfo_,
      RecipientInfos recipientInfos_,
      EncryptedContentInfo authEncryptedContentInfo_,
      AuthAttributes authAttrs_,
      Asn1OctetString mac_,
      UnauthAttributes unauthAttrs_
   ) {
      super();
      version = version_;
      originatorInfo = originatorInfo_;
      recipientInfos = recipientInfos_;
      authEncryptedContentInfo = authEncryptedContentInfo_;
      authAttrs = authAttrs_;
      mac = mac_;
      unauthAttrs = unauthAttrs_;
   }

   /**
    * This constructor is for required elements only.  It sets 
    * all elements to references to the given objects
    */
   public AuthEnvelopedData (
      CMSVersion version_,
      RecipientInfos recipientInfos_,
      EncryptedContentInfo authEncryptedContentInfo_,
      Asn1OctetString mac_
   ) {
      super();
      version = version_;
      recipientInfos = recipientInfos_;
      authEncryptedContentInfo = authEncryptedContentInfo_;
      mac = mac_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public AuthEnvelopedData (long version_,
      OriginatorInfo originatorInfo_,
      RecipientInfos recipientInfos_,
      EncryptedContentInfo authEncryptedContentInfo_,
      AuthAttributes authAttrs_,
      byte[] mac_,
      UnauthAttributes unauthAttrs_
   ) {
      super();
      version = new CMSVersion (version_);
      originatorInfo = originatorInfo_;
      recipientInfos = recipientInfos_;
      authEncryptedContentInfo = authEncryptedContentInfo_;
      authAttrs = authAttrs_;
      mac = new Asn1OctetString (mac_);
      unauthAttrs = unauthAttrs_;
   }

   /**
    * This constructor is for required elements only.  It allows 
    * primitive data to be passed for all primitive elements.  
    * It will create new object wrappers for the primitive data 
    * and set other elements to references to the given objects. 
    */
   public AuthEnvelopedData (
      long version_,
      RecipientInfos recipientInfos_,
      EncryptedContentInfo authEncryptedContentInfo_,
      byte[] mac_
   ) {
      super();
      version = new CMSVersion (version_);
      recipientInfos = recipientInfos_;
      authEncryptedContentInfo = authEncryptedContentInfo_;
      mac = new Asn1OctetString (mac_);
   }

   public void init () {
      version = null;
      originatorInfo = null;
      recipientInfos = null;
      authEncryptedContentInfo = null;
      authAttrs = null;
      mac = null;
      unauthAttrs = null;
   }

   public int getElementCount() { return 7; }


   public Object getElementValue(int index){
      switch(index)  {
         case 0: return version;
         case 1: return originatorInfo;
         case 2: return recipientInfos;
         case 3: return authEncryptedContentInfo;
         case 4: return authAttrs;
         case 5: return mac;
         case 6: return unauthAttrs;
         default: return null;
      }
   }


   public String getElementName(int index){
      switch(index)  {
         case 0: return "version";
         case 1: return "originatorInfo";
         case 2: return "recipientInfos";
         case 3: return "authEncryptedContentInfo";
         case 4: return "authAttrs";
         case 5: return "mac";
         case 6: return "unauthAttrs";
         default: return null;
      }
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

      // decode authEncryptedContentInfo

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         authEncryptedContentInfo = new EncryptedContentInfo();
         authEncryptedContentInfo.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "authEncryptedContentInfo");

      // decode authAttrs

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 1, elemLen, true)) {
         int offset = buffer.getByteCount();
         authAttrs = new AuthAttributes();
         authAttrs.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

         if (elemLen.value == Asn1Status.INDEFLEN) {
            matchTag (buffer, Asn1Tag.EOC);
         }
      }

      // decode mac

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 4, elemLen, false)) {
         mac = new Asn1OctetString();
         mac.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "mac");

      // decode unauthAttrs

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 2, elemLen, true)) {
         int offset = buffer.getByteCount();
         unauthAttrs = new UnauthAttributes();
         unauthAttrs.decode (buffer, false, elemLen.value);

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
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 1) ||
             _tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 4) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 2))  {
            throw new Asn1SeqOrderException ();
         }
         else  {
            throw new Asn1UnexpectedElementException();
         }

      }
      if (explicit && llen == Asn1Status.INDEFLEN) {
         matchTag (buffer, Asn1Tag.EOC);
      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode unauthAttrs

      if (unauthAttrs != null) {
         len = unauthAttrs.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 2, len);
         _aal += len;
      }

      // encode mac

      if (mac != null) {
         len = mac.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("mac");

      // encode authAttrs

      if (authAttrs != null) {
         len = authAttrs.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 1, len);
         _aal += len;
      }

      // encode authEncryptedContentInfo

      if (authEncryptedContentInfo != null) {
         len = authEncryptedContentInfo.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("authEncryptedContentInfo");

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

   public void print (java.io.PrintWriter _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (version != null) version.print (_out, "version", _level+1);
      if (originatorInfo != null) originatorInfo.print (_out, "originatorInfo", _level+1);
      if (recipientInfos != null) recipientInfos.print (_out, "recipientInfos", _level+1);
      if (authEncryptedContentInfo != null) authEncryptedContentInfo.print (_out, "authEncryptedContentInfo", _level+1);
      if (authAttrs != null) authAttrs.print (_out, "authAttrs", _level+1);
      if (mac != null) mac.print (_out, "mac", _level+1);
      if (unauthAttrs != null) unauthAttrs.print (_out, "unauthAttrs", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }

}
