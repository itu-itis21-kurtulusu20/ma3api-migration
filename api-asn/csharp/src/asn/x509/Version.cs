// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.x509 {

   public class Version : Asn1Integer {
      public const int v1 = 0;
      public const int v2 = 1;
      public const int v3 = 2;

      static Version ()
      {
         Asn1Type.SetKey2 (_ExplicitValues._rtkey);
      }

      public Version () : base()
      {
      }

      public Version (long value_) : base(value_)
      {
      }

   }
}
