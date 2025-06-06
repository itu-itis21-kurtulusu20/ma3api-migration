/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 7.0.0, Date: 18-Mar-2016.
 */
package tr.gov.tubitak.uekae.esya.asn.PKIXqualified;

import com.objsys.asn1j.runtime.*;

public class QCStatement extends Asn1Seq {
   private static final long serialVersionUID = 55;
   static {
      setKey (_PKIXqualifiedRtkey._rtkey);
   }

   public String getAsn1TypeName()  {
      return "QCStatement";
   }

   public Asn1ObjectIdentifier statementId;
   public Asn1OpenType statementInfo;  // optional

   public QCStatement () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public QCStatement (
      Asn1ObjectIdentifier statementId_,
      Asn1OpenType statementInfo_
   ) {
      super();
      statementId = statementId_;
      statementInfo = statementInfo_;
   }

   /**
    * This constructor is for required elements only.  It sets 
    * all elements to references to the given objects
    */
   public QCStatement (
      Asn1ObjectIdentifier statementId_
   ) {
      super();
      statementId = statementId_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public QCStatement (int[] statementId_,
      Asn1OpenType statementInfo_
   ) {
      super();
      statementId = new Asn1ObjectIdentifier (statementId_);
      statementInfo = statementInfo_;
   }

   /**
    * This constructor is for required elements only.  It allows 
    * primitive data to be passed for all primitive elements.  
    * It will create new object wrappers for the primitive data 
    * and set other elements to references to the given objects. 
    */
   public QCStatement (
      int[] statementId_
   ) {
      super();
      statementId = new Asn1ObjectIdentifier (statementId_);
   }

   public void init () {
      statementId = null;
      statementInfo = null;
   }

   public int getElementCount() { return 2; }


   public Object getElementValue(int index){
      switch(index)  {
         case 0: return statementId;
         case 1: return statementInfo;
         default: return null;
      }
   }


   public String getElementName(int index){
      switch(index)  {
         case 0: return "statementId";
         case 1: return "statementInfo";
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

      // decode statementId

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 6, elemLen, false)) {
         statementId = new Asn1ObjectIdentifier();
         statementId.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "statementId");

      // decode statementInfo

      if (!_context.expired ()) {
         statementInfo = new Asn1OpenType();
         statementInfo.decode (buffer, true, 0);
      }

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 6))  {
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

      // encode statementInfo

      if (statementInfo != null) {
         len = statementInfo.encode (buffer, true);
         _aal += len;
      }

      // encode statementId

      if (statementId != null) {
         len = statementId.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("statementId");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintWriter _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (statementId != null) statementId.print (_out, "statementId", _level+1);
      if (statementInfo != null) statementInfo.print (_out, "statementInfo", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }

}
