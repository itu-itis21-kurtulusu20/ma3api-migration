/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 7.0.0, Date: 18-Mar-2016.
 */
package tr.gov.tubitak.uekae.esya.asn.pkcs1pkcs8;

import com.objsys.asn1j.runtime.*;

public class ECPrivateKey_version extends Asn1Integer {
   private static final long serialVersionUID = 55;
   static {
      setKey (_pkcs1pkcs8Rtkey._rtkey);
   }

   public String getAsn1TypeName()  {
      return "INTEGER";
   }

   public final static int ecPrivkeyVer1 = 1;

   public ECPrivateKey_version () {
      super();
   }

   public ECPrivateKey_version (long value_) {
      super (value_);
   }

   public void decode
      (Asn1BerDecodeBuffer buffer, boolean explicit, int implicitLength)
      throws Asn1Exception, java.io.IOException
   {
      int llen = implicitLength;

      super.decode (buffer, explicit, llen);

      if (!(value == 1)) {
         throw new Asn1ConsVioException ("value", value);
      }

   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      if (!(value == 1)) {
         throw new Asn1ConsVioException ("value", value);
      }

      int len;
      len = super.encode (buffer, explicit);
      return (len);
   }

   public void print (java.io.PrintWriter _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " = " + toString());
   }

}
