/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.x509;

import com.objsys.asn1j.runtime.*;

public class SubjectPublicKeyInfo extends Asn1Type {
   private static final long serialVersionUID = 55;
   static {
      setKey (_ExplicitRtkey._rtkey);
   }

   public AlgorithmIdentifier algorithm;
   public Asn1BitString subjectPublicKey;

   public SubjectPublicKeyInfo () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public SubjectPublicKeyInfo (
      AlgorithmIdentifier algorithm_,
      Asn1BitString subjectPublicKey_
   ) {
      super();
      algorithm = algorithm_;
      subjectPublicKey = subjectPublicKey_;
   }

   public void init () {
      algorithm = null;
      subjectPublicKey = null;
   }

   public boolean equals( Object obj ) {
      if ( this == obj ) return true;
      if ( !(obj instanceof SubjectPublicKeyInfo) ) return false;

      SubjectPublicKeyInfo rhs = (SubjectPublicKeyInfo) obj;

      if (algorithm == null) {
         if (rhs.algorithm != null) return false;
      }
      else {
         if (!algorithm.equals(rhs.algorithm)) {
            return false;
         }
      }

      if (subjectPublicKey == null) {
         if (rhs.subjectPublicKey != null) return false;
      }
      else {
         if (!subjectPublicKey.equals(rhs.subjectPublicKey)) {
            return false;
         }
      }

      return true;
   }

   public int hashCode() {
      int __code = 1;

      if (algorithm != null) __code = 31*__code + algorithm.hashCode();
      if (subjectPublicKey != null) __code = 31*__code + subjectPublicKey.hashCode();

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

      // decode algorithm

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
         algorithm = new AlgorithmIdentifier();
         algorithm.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "algorithm");

      // decode subjectPublicKey

      if (_context.matchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 3, elemLen, false)) {
         subjectPublicKey = new Asn1BitString();
         subjectPublicKey.decode (buffer, true, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "subjectPublicKey");

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.UNIV, Asn1Tag.CONS, 16) ||
             _tag.equals (Asn1Tag.UNIV, Asn1Tag.PRIM, 3))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode subjectPublicKey

      if (subjectPublicKey != null) {
         len = subjectPublicKey.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("subjectPublicKey");

      // encode algorithm

      if (algorithm != null) {
         len = algorithm.encode (buffer, true);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("algorithm");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (algorithm != null) algorithm.print (_out, "algorithm", _level+1);
      if (subjectPublicKey != null) subjectPublicKey.print (_out, "subjectPublicKey", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
