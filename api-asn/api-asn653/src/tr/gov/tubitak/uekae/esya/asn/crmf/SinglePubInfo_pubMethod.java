/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 7.0.0, Date: 18-Mar-2016.
 */
package tr.gov.tubitak.uekae.esya.asn.crmf;

import com.objsys.asn1j.runtime.*;

public class SinglePubInfo_pubMethod extends Asn1Integer {
   private static final long serialVersionUID = 55;
   static {
      setKey (_crmfRtkey._rtkey);
   }

   public String getAsn1TypeName()  {
      return "INTEGER";
   }

   public final static int dontCare = 0;
   public final static int x500 = 1;
   public final static int web = 2;
   public final static int ldap = 3;

   public SinglePubInfo_pubMethod () {
      super();
   }

   public SinglePubInfo_pubMethod (long value_) {
      super (value_);
   }

   public void print (java.io.PrintWriter _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " = " + toString());
   }

}
