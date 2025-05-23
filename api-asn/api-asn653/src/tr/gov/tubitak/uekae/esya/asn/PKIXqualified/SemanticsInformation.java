/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 7.0.0, Date: 18-Mar-2016.
 */
package tr.gov.tubitak.uekae.esya.asn.PKIXqualified;

import com.objsys.asn1j.runtime.*;

public class SemanticsInformation extends Asn1Seq {
   private static final long serialVersionUID = 55;
   static {
      setKey (_PKIXqualifiedRtkey._rtkey);
   }

   public String getAsn1TypeName()  {
      return "SemanticsInformation";
   }

   public Asn1ObjectIdentifier semanticsIndentifier;  // optional
   public NameRegistrationAuthorities nameRegistrationAuthorities;  // optional

   public SemanticsInformation () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public SemanticsInformation (
      Asn1ObjectIdentifier semanticsIndentifier_,
      NameRegistrationAuthorities nameRegistrationAuthorities_
   ) {
      super();
      semanticsIndentifier = semanticsIndentifier_;
      nameRegistrationAuthorities = nameRegistrationAuthorities_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public SemanticsInformation (int[] semanticsIndentifier_,
      NameRegistrationAuthorities nameRegistrationAuthorities_
   ) {
      super();
      semanticsIndentifier = new Asn1ObjectIdentifier (semanticsIndentifier_);
      nameRegistrationAuthorities = nameRegistrationAuthorities_;
   }

   public void init () {
      semanticsIndentifier = null;
      nameRegistrationAuthorities = null;
   }

   public int getElementCount() { return 2; }


   public Object getElementValue(int index){
      switch(index)  {
         case 0: return semanticsIndentifier;
         case 1: return nameRegistrationAuthorities;
         default: return null;
      }
   }


   public String getElementName(int index){
      switch(index)  {
         case 0: return "semanticsIndentifier";
         case 1: return "nameRegistrationAuthorities";
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

      // decode semanticsIndentifier

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 6, elemLen, false)) {
         semanticsIndentifier = new Asn1ObjectIdentifier();
         semanticsIndentifier.decode (buffer, true, elemLen.value);
      }

      // decode nameRegistrationAuthorities

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         nameRegistrationAuthorities = new NameRegistrationAuthorities();
         nameRegistrationAuthorities.decode (buffer, true, elemLen.value);
      }

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 6) ||
             _tag.equals (Asn1Tag.UNIV, Asn1Tag.CONS, 16))  {
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

      // encode nameRegistrationAuthorities

      if (nameRegistrationAuthorities != null) {
         len = nameRegistrationAuthorities.encode (buffer, true);
         _aal += len;
      }

      // encode semanticsIndentifier

      if (semanticsIndentifier != null) {
         len = semanticsIndentifier.encode (buffer, true);
         _aal += len;
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
      if (semanticsIndentifier != null) semanticsIndentifier.print (_out, "semanticsIndentifier", _level+1);
      if (nameRegistrationAuthorities != null) nameRegistrationAuthorities.print (_out, "nameRegistrationAuthorities", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }

}
