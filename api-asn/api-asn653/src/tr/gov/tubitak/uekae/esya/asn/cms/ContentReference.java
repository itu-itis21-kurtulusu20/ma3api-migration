/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 7.0.0, Date: 18-Mar-2016.
 */
package tr.gov.tubitak.uekae.esya.asn.cms;

import com.objsys.asn1j.runtime.*;

public class ContentReference extends Asn1Seq {
   private static final long serialVersionUID = 55;
   static {
      setKey (_etsi101733Rtkey._rtkey);
   }

   public String getAsn1TypeName()  {
      return "ContentReference";
   }

   public Asn1ObjectIdentifier contentType;
   public Asn1OctetString signedContentIdentifier;
   public Asn1OctetString originatorSignatureValue;

   public ContentReference () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public ContentReference (
      Asn1ObjectIdentifier contentType_,
      Asn1OctetString signedContentIdentifier_,
      Asn1OctetString originatorSignatureValue_
   ) {
      super();
      contentType = contentType_;
      signedContentIdentifier = signedContentIdentifier_;
      originatorSignatureValue = originatorSignatureValue_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public ContentReference (int[] contentType_,
      byte[] signedContentIdentifier_,
      byte[] originatorSignatureValue_
   ) {
      super();
      contentType = new Asn1ObjectIdentifier (contentType_);
      signedContentIdentifier = new Asn1OctetString (signedContentIdentifier_);
      originatorSignatureValue = new Asn1OctetString (originatorSignatureValue_);
   }

   public void init () {
      contentType = null;
      signedContentIdentifier = null;
      originatorSignatureValue = null;
   }

   public int getElementCount() { return 3; }


   public Object getElementValue(int index){
      switch(index)  {
         case 0: return contentType;
         case 1: return signedContentIdentifier;
         case 2: return originatorSignatureValue;
         default: return null;
      }
   }


   public String getElementName(int index){
      switch(index)  {
         case 0: return "contentType";
         case 1: return "signedContentIdentifier";
         case 2: return "originatorSignatureValue";
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

      // decode contentType

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 6, elemLen, false)) {
         contentType = new Asn1ObjectIdentifier();
         contentType.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "contentType");

      // decode signedContentIdentifier

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 4, elemLen, false)) {
         signedContentIdentifier = new Asn1OctetString();
         signedContentIdentifier.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "signedContentIdentifier");

      // decode originatorSignatureValue

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 4, elemLen, false)) {
         originatorSignatureValue = new Asn1OctetString();
         originatorSignatureValue.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "originatorSignatureValue");

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 6) ||
             _tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 4))  {
            throw new Asn1UnexpectedElementException();
         }

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode originatorSignatureValue

      if (originatorSignatureValue != null) {
         len = originatorSignatureValue.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("originatorSignatureValue");

      // encode signedContentIdentifier

      if (signedContentIdentifier != null) {
         len = signedContentIdentifier.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("signedContentIdentifier");

      // encode contentType

      if (contentType != null) {
         len = contentType.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("contentType");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintWriter _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (contentType != null) contentType.print (_out, "contentType", _level+1);
      if (signedContentIdentifier != null) signedContentIdentifier.print (_out, "signedContentIdentifier", _level+1);
      if (originatorSignatureValue != null) originatorSignatureValue.print (_out, "originatorSignatureValue", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }

}
