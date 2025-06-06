/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.x509;

import com.objsys.asn1j.runtime.*;

public class IssuingDistributionPoint extends Asn1Type {
   private static final long serialVersionUID = 55;
   static {
      setKey (_ImplicitRtkey._rtkey);
   }

   public DistributionPointName distributionPoint;  // optional
   public Asn1Boolean onlyContainsUserCerts;  // default = false()
   public Asn1Boolean onlyContainsCACerts;  // default = false()
   public ReasonFlags onlySomeReasons;  // optional
   public Asn1Boolean indirectCRL;  // default = false()
   public Asn1Boolean onlyContainsAttributeCerts;  // default = false()

   public IssuingDistributionPoint () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public IssuingDistributionPoint (
      DistributionPointName distributionPoint_,
      Asn1Boolean onlyContainsUserCerts_,
      Asn1Boolean onlyContainsCACerts_,
      ReasonFlags onlySomeReasons_,
      Asn1Boolean indirectCRL_,
      Asn1Boolean onlyContainsAttributeCerts_
   ) {
      super();
      distributionPoint = distributionPoint_;
      onlyContainsUserCerts = onlyContainsUserCerts_;
      onlyContainsCACerts = onlyContainsCACerts_;
      onlySomeReasons = onlySomeReasons_;
      indirectCRL = indirectCRL_;
      onlyContainsAttributeCerts = onlyContainsAttributeCerts_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public IssuingDistributionPoint (DistributionPointName distributionPoint_,
      boolean onlyContainsUserCerts_,
      boolean onlyContainsCACerts_,
      ReasonFlags onlySomeReasons_,
      boolean indirectCRL_,
      boolean onlyContainsAttributeCerts_
   ) {
      super();
      distributionPoint = distributionPoint_;
      onlyContainsUserCerts = new Asn1Boolean (onlyContainsUserCerts_);
      onlyContainsCACerts = new Asn1Boolean (onlyContainsCACerts_);
      onlySomeReasons = onlySomeReasons_;
      indirectCRL = new Asn1Boolean (indirectCRL_);
      onlyContainsAttributeCerts = new Asn1Boolean (onlyContainsAttributeCerts_);
   }

   public void init () {
      distributionPoint = null;
      onlyContainsUserCerts = new Asn1Boolean (false);
      onlyContainsCACerts = new Asn1Boolean (false);
      onlySomeReasons = null;
      indirectCRL = new Asn1Boolean (false);
      onlyContainsAttributeCerts = new Asn1Boolean (false);
   }

   public boolean equals( Object obj ) {
      if ( this == obj ) return true;
      if ( !(obj instanceof IssuingDistributionPoint) ) return false;

      IssuingDistributionPoint rhs = (IssuingDistributionPoint) obj;

      if (distributionPoint == null) {
         if (rhs.distributionPoint != null) return false;
      }
      else {
         if (!distributionPoint.equals(rhs.distributionPoint)) {
            return false;
         }
      }

      if (onlyContainsUserCerts == null) {
         if (rhs.onlyContainsUserCerts != null) return false;
      }
      else {
         if (!onlyContainsUserCerts.equals(rhs.onlyContainsUserCerts)) {
            return false;
         }
      }

      if (onlyContainsCACerts == null) {
         if (rhs.onlyContainsCACerts != null) return false;
      }
      else {
         if (!onlyContainsCACerts.equals(rhs.onlyContainsCACerts)) {
            return false;
         }
      }

      if (onlySomeReasons == null) {
         if (rhs.onlySomeReasons != null) return false;
      }
      else {
         if (!onlySomeReasons.equals(rhs.onlySomeReasons)) {
            return false;
         }
      }

      if (indirectCRL == null) {
         if (rhs.indirectCRL != null) return false;
      }
      else {
         if (!indirectCRL.equals(rhs.indirectCRL)) {
            return false;
         }
      }

      if (onlyContainsAttributeCerts == null) {
         if (rhs.onlyContainsAttributeCerts != null) return false;
      }
      else {
         if (!onlyContainsAttributeCerts.equals(rhs.onlyContainsAttributeCerts)) {
            return false;
         }
      }

      return true;
   }

   public int hashCode() {
      int __code = 1;

      if (distributionPoint != null) __code = 31*__code + distributionPoint.hashCode();
      if (onlyContainsUserCerts != null) __code = 31*__code + onlyContainsUserCerts.hashCode();
      if (onlyContainsCACerts != null) __code = 31*__code + onlyContainsCACerts.hashCode();
      if (onlySomeReasons != null) __code = 31*__code + onlySomeReasons.hashCode();
      if (indirectCRL != null) __code = 31*__code + indirectCRL.hashCode();
      if (onlyContainsAttributeCerts != null) __code = 31*__code + onlyContainsAttributeCerts.hashCode();

      return __code;
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

      // decode distributionPoint

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
         int offset = buffer.getByteCount();
         distributionPoint = new DistributionPointName();
         distributionPoint.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }

      // decode onlyContainsUserCerts

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 1, elemLen, true)) {
         int offset = buffer.getByteCount();
         onlyContainsUserCerts = new Asn1Boolean();
         onlyContainsUserCerts.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }

      // decode onlyContainsCACerts

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 2, elemLen, true)) {
         int offset = buffer.getByteCount();
         onlyContainsCACerts = new Asn1Boolean();
         onlyContainsCACerts.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }

      // decode onlySomeReasons

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 3, elemLen, true)) {
         int offset = buffer.getByteCount();
         onlySomeReasons = new ReasonFlags();
         onlySomeReasons.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }

      // decode indirectCRL

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 4, elemLen, true)) {
         int offset = buffer.getByteCount();
         indirectCRL = new Asn1Boolean();
         indirectCRL.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }

      // decode onlyContainsAttributeCerts

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 5, elemLen, true)) {
         int offset = buffer.getByteCount();
         onlyContainsAttributeCerts = new Asn1Boolean();
         onlyContainsAttributeCerts.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 1) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 2) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 3) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 4) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 5))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode onlyContainsAttributeCerts

      if (onlyContainsAttributeCerts != null) {
         if (!onlyContainsAttributeCerts.equals (false)) {
            len = onlyContainsAttributeCerts.encode (buffer, false);
            len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 5, len);
            _aal += len;
         }
      }

      // encode indirectCRL

      if (indirectCRL != null) {
         if (!indirectCRL.equals (false)) {
            len = indirectCRL.encode (buffer, false);
            len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 4, len);
            _aal += len;
         }
      }

      // encode onlySomeReasons

      if (onlySomeReasons != null) {
         len = onlySomeReasons.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 3, len);
         _aal += len;
      }

      // encode onlyContainsCACerts

      if (onlyContainsCACerts != null) {
         if (!onlyContainsCACerts.equals (false)) {
            len = onlyContainsCACerts.encode (buffer, false);
            len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 2, len);
            _aal += len;
         }
      }

      // encode onlyContainsUserCerts

      if (onlyContainsUserCerts != null) {
         if (!onlyContainsUserCerts.equals (false)) {
            len = onlyContainsUserCerts.encode (buffer, false);
            len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 1, len);
            _aal += len;
         }
      }

      // encode distributionPoint

      if (distributionPoint != null) {
         len = distributionPoint.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
         _aal += len;
      }

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (distributionPoint != null) distributionPoint.print (_out, "distributionPoint", _level+1);
      if (onlyContainsUserCerts != null) onlyContainsUserCerts.print (_out, "onlyContainsUserCerts", _level+1);
      if (onlyContainsCACerts != null) onlyContainsCACerts.print (_out, "onlyContainsCACerts", _level+1);
      if (onlySomeReasons != null) onlySomeReasons.print (_out, "onlySomeReasons", _level+1);
      if (indirectCRL != null) indirectCRL.print (_out, "indirectCRL", _level+1);
      if (onlyContainsAttributeCerts != null) onlyContainsAttributeCerts.print (_out, "onlyContainsAttributeCerts", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
