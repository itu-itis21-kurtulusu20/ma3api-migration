// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.x509 {

   public class FreshestCRL : CRLDistributionPoints {
      static FreshestCRL ()
      {
         Asn1Type.SetKey2 (_ImplicitValues._rtkey);
      }

      public FreshestCRL () : base()
      {
      }

      public FreshestCRL (int numRecords) : base (numRecords)
      {
      }

      public FreshestCRL (DistributionPoint[] elements_) :
         base (elements_)
      {
      }

   }
}
