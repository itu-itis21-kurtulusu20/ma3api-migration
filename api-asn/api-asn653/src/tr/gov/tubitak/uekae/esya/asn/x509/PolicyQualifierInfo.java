/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 7.0.0, Date: 18-Mar-2016.
 */
package tr.gov.tubitak.uekae.esya.asn.x509;

import com.objsys.asn1j.runtime.*;

public class PolicyQualifierInfo extends Asn1Seq {
   private static final long serialVersionUID = 55;
   static {
      setKey (_ImplicitRtkey._rtkey);
   }

   public String getAsn1TypeName()  {
      return "PolicyQualifierInfo";
   }

   public Asn1ObjectIdentifier policyQualifierId;
   public Asn1OpenType qualifier;

   public PolicyQualifierInfo () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public PolicyQualifierInfo (
      Asn1ObjectIdentifier policyQualifierId_,
      Asn1OpenType qualifier_
   ) {
      super();
      policyQualifierId = policyQualifierId_;
      qualifier = qualifier_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public PolicyQualifierInfo (int[] policyQualifierId_,
      Asn1OpenType qualifier_
   ) {
      super();
      policyQualifierId = new Asn1ObjectIdentifier (policyQualifierId_);
      qualifier = qualifier_;
   }

   public void init () {
      policyQualifierId = null;
      qualifier = null;
   }

   public boolean equals( Object obj ) {
      if ( this == obj ) return true;
      if ( !(obj instanceof PolicyQualifierInfo) ) return false;

      PolicyQualifierInfo rhs = (PolicyQualifierInfo) obj;

      if (policyQualifierId == null) {
         if (rhs.policyQualifierId != null) return false;
      }
      else {
         if (!policyQualifierId.equals(rhs.policyQualifierId)) {
            return false;
         }
      }

      if (qualifier == null) {
         if (rhs.qualifier != null) return false;
      }
      else {
         if (!qualifier.equals(rhs.qualifier)) {
            return false;
         }
      }

      return true;
   }

   public int hashCode() {
      int __code = 1;

      if (policyQualifierId != null) __code = 31*__code + policyQualifierId.hashCode();
      if (qualifier != null) __code = 31*__code + qualifier.hashCode();

      return __code;
   }

   public int getElementCount() { return 2; }


   public Object getElementValue(int index){
      switch(index)  {
         case 0: return policyQualifierId;
         case 1: return qualifier;
         default: return null;
      }
   }


   public String getElementName(int index){
      switch(index)  {
         case 0: return "policyQualifierId";
         case 1: return "qualifier";
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

      // decode policyQualifierId

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 6, elemLen, false)) {
         policyQualifierId = new Asn1ObjectIdentifier();
         policyQualifierId.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "policyQualifierId");

      // decode qualifier

      if (!_context.expired ()) {
         qualifier = new Asn1OpenType();
         qualifier.decode (buffer, true, 0);
      }
      else throw new Asn1MissingRequiredException (buffer);

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 6))  {
            throw new Asn1UnexpectedElementException();
         }

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode qualifier

      if (qualifier != null) {
         len = qualifier.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("qualifier");

      // encode policyQualifierId

      if (policyQualifierId != null) {
         len = policyQualifierId.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("policyQualifierId");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintWriter _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (policyQualifierId != null) policyQualifierId.print (_out, "policyQualifierId", _level+1);
      if (qualifier != null) qualifier.print (_out, "qualifier", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }

}
