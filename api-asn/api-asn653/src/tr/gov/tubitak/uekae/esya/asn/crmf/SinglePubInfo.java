/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 7.0.0, Date: 18-Mar-2016.
 */
package tr.gov.tubitak.uekae.esya.asn.crmf;

import com.objsys.asn1j.runtime.*;
import tr.gov.tubitak.uekae.esya.asn.x509.GeneralName;

public class SinglePubInfo extends Asn1Seq {
   private static final long serialVersionUID = 55;
   static {
      setKey (_crmfRtkey._rtkey);
   }

   public String getAsn1TypeName()  {
      return "SinglePubInfo";
   }

   public SinglePubInfo_pubMethod pubMethod;
   public GeneralName pubLocation;  // optional

   public SinglePubInfo () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public SinglePubInfo (
      SinglePubInfo_pubMethod pubMethod_,
      GeneralName pubLocation_
   ) {
      super();
      pubMethod = pubMethod_;
      pubLocation = pubLocation_;
   }

   /**
    * This constructor is for required elements only.  It sets 
    * all elements to references to the given objects
    */
   public SinglePubInfo (
      SinglePubInfo_pubMethod pubMethod_
   ) {
      super();
      pubMethod = pubMethod_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public SinglePubInfo (long pubMethod_,
      GeneralName pubLocation_
   ) {
      super();
      pubMethod = new SinglePubInfo_pubMethod (pubMethod_);
      pubLocation = pubLocation_;
   }

   /**
    * This constructor is for required elements only.  It allows 
    * primitive data to be passed for all primitive elements.  
    * It will create new object wrappers for the primitive data 
    * and set other elements to references to the given objects. 
    */
   public SinglePubInfo (
      long pubMethod_
   ) {
      super();
      pubMethod = new SinglePubInfo_pubMethod (pubMethod_);
   }

   public void init () {
      pubMethod = null;
      pubLocation = null;
   }

   public int getElementCount() { return 2; }


   public Object getElementValue(int index){
      switch(index)  {
         case 0: return pubMethod;
         case 1: return pubLocation;
         default: return null;
      }
   }


   public String getElementName(int index){
      switch(index)  {
         case 0: return "pubMethod";
         case 1: return "pubLocation";
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

      // decode pubMethod

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
         pubMethod = new SinglePubInfo_pubMethod();
         pubMethod.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "pubMethod");

      // decode pubLocation

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
            pubLocation = new GeneralName();
            pubLocation.decode (buffer, true, elemLen.value);
         }
      }

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 2))  {
            throw new Asn1SeqOrderException ();
         }
         else  {
            throw new Asn1UnexpectedElementException();
         }

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode pubLocation

      if (pubLocation != null) {
         len = pubLocation.encode (buffer, true);
         _aal += len;
      }

      // encode pubMethod

      if (pubMethod != null) {
         len = pubMethod.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("pubMethod");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintWriter _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (pubMethod != null) pubMethod.print (_out, "pubMethod", _level+1);
      if (pubLocation != null) pubLocation.print (_out, "pubLocation", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }

}
