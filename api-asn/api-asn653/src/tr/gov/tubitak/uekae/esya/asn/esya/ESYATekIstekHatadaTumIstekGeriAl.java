/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 7.0.0, Date: 18-Mar-2016.
 */
package tr.gov.tubitak.uekae.esya.asn.esya;

import com.objsys.asn1j.runtime.*;

public class ESYATekIstekHatadaTumIstekGeriAl extends Asn1Boolean {
   private static final long serialVersionUID = 55;
   static {
      setKey (_esyaRtkey._rtkey);
   }

   public String getAsn1TypeName()  {
      return "ESYATekIstekHatadaTumIstekGeriAl";
   }

   public ESYATekIstekHatadaTumIstekGeriAl () {
      super();
   }

   public ESYATekIstekHatadaTumIstekGeriAl (boolean value_) {
      super (value_);
   }

   public void decode
      (Asn1BerDecodeBuffer buffer, boolean explicit, int implicitLength)
      throws Asn1Exception, java.io.IOException
   {

      super.decode (buffer, explicit, implicitLength);

   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int len;
      len = super.encode (buffer, explicit);
      return (len);
   }

}
