// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.cms {

   public class CMSVersion : Asn1Integer {
      public const int v0 = 0;
      public const int v1 = 1;
      public const int v2 = 2;
      public const int v3 = 3;
      public const int v4 = 4;
      public const int v5 = 5;

      static CMSVersion ()
      {
         Asn1Type.SetKey2 (_cmsValues._rtkey);
      }

      public CMSVersion () : base()
      {
      }

      public CMSVersion (long value_) : base(value_)
      {
      }

   }
}
