/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 7.0.0, Date: 18-Mar-2016.
 */
package tr.gov.tubitak.uekae.esya.asn.algorithms;

import com.objsys.asn1j.runtime.*;

public class EcpkParameters extends Asn1Choice {
   private static final long serialVersionUID = 55;
   static {
      setKey (_algorithmsRtkey._rtkey);
   }

   public String getAsn1TypeName()  {
      return "EcpkParameters";
   }

   // Choice element identifier constants
   public final static byte _IMPLICITLYCA = 1;
   public final static byte _NAMEDCURVE = 2;
   public final static byte _ECPARAMETERS = 3;

   public EcpkParameters () {
      super();
   }

   public EcpkParameters (byte choiceId_, Asn1Type element_) {
      super();
      setElement (choiceId_, element_);
   }

   public String getElemName () {
      switch (choiceID) {
      case _IMPLICITLYCA: return "implicitlyCA";
      case _NAMEDCURVE: return "namedCurve";
      case _ECPARAMETERS: return "ecParameters";
      default: return "UNDEFINED";
      }
   }

   public void set_implicitlyCA () {
      setElement (_IMPLICITLYCA, Asn1Null.NULL_VALUE);
   }

   public void set_namedCurve (Asn1ObjectIdentifier value) {
      setElement (_NAMEDCURVE, value);
   }

   public void set_ecParameters (ECParameters value) {
      setElement (_ECPARAMETERS, value);
   }

   public void decode
      (Asn1BerDecodeBuffer buffer, boolean explicit, int implicitLength)
      throws Asn1Exception, java.io.IOException
   {
      int llen = implicitLength;

      // decode CHOICE

      Asn1Tag tag = new Asn1Tag ();
      buffer.mark (8);
      int len = buffer.decodeTagAndLength (tag);
      final int choiceLen = len;

      int offset = buffer.getByteCount(), declen;

      if (tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 5))
      {
         buffer.reset();
         Asn1Null implicitlyCA;
         implicitlyCA = new Asn1Null();
         implicitlyCA.decode (buffer, true, len);
         setElement (_IMPLICITLYCA, implicitlyCA);
      }
      else if (tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 6))
      {
         buffer.reset();
         Asn1ObjectIdentifier namedCurve;
         namedCurve = new Asn1ObjectIdentifier();
         namedCurve.decode (buffer, true, len);
         setElement (_NAMEDCURVE, namedCurve);
      }
      else if (tag.equals (Asn1Tag.UNIV, Asn1Tag.CONS, 16))
      {
         buffer.reset();
         ECParameters ecParameters;
         ecParameters = new ECParameters();
         ecParameters.decode (buffer, true, len);
         setElement (_ECPARAMETERS, ecParameters);
      }
      else {
         throw new Asn1InvalidChoiceOptionException (buffer, tag);
      }

      declen = buffer.getByteCount() - offset;
      if (choiceLen != Asn1Status.INDEFLEN && choiceLen != declen)
         throw new Asn1InvalidLengthException();
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;
      switch (choiceID) {
      // encode implicitlyCA
      case _IMPLICITLYCA:
         Asn1Null implicitlyCA = (Asn1Null) getElement();
         len = implicitlyCA.encode (buffer, true);
         _aal += len;
         break;

      // encode namedCurve
      case _NAMEDCURVE:
         Asn1ObjectIdentifier namedCurve = (Asn1ObjectIdentifier) getElement();
         len = namedCurve.encode (buffer, true);
         _aal += len;
         break;

      // encode ecParameters
      case _ECPARAMETERS:
         ECParameters ecParameters = (ECParameters) getElement();
         len = ecParameters.encode (buffer, true);
         _aal += len;
         break;

      default:
         throw new Asn1InvalidChoiceOptionException();
      }

      return _aal;
   }

   public void print (java.io.PrintWriter _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (element != null) {
         element.print (_out, getElemName(), _level+1);
      }
      indent (_out, _level);
      _out.println ("}");
   }

}
