/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.depo;

import com.objsys.asn1j.runtime.*;
import tr.gov.tubitak.uekae.esya.asn.x509.Name;
import tr.gov.tubitak.uekae.esya.asn.x509.Time;
import tr.gov.tubitak.uekae.esya.asn.x509.KeyUsage;

public class DepoASNEklenecekKokSertifika extends Asn1Type {

	private static final long serialVersionUID = 55;
	static {
		setKey(_depoRtkey._rtkey);
	}

   public Asn1OctetString kokSertifikaValue;
   public Asn1OctetString kokSertifikaHash;
   public Asn1BigInteger kokSerialNumber;
   public Name kokIssuerName;
   public Name kokSubjectName;
   public Time kokStartDate;
   public Time kokEndDate;
   public KeyUsage kokKeyUsage;
   public Asn1OctetString kokSubjectKeyIdentifier;
   public KokSertifikaTipi kokSertifikaTipi = null;
   public KOKGuvenSeviyesi kokGuvenSeviyesi = null;

   public DepoASNEklenecekKokSertifika () {
      super();
      init();
   }

   /**
    * This constructor sets all elements to references to the 
    * given objects
    */
   public DepoASNEklenecekKokSertifika (
      Asn1OctetString kokSertifikaValue_,
      Asn1OctetString kokSertifikaHash_,
      Asn1BigInteger kokSerialNumber_,
      Name kokIssuerName_,
      Name kokSubjectName_,
      Time kokStartDate_,
      Time kokEndDate_,
      KeyUsage kokKeyUsage_,
      Asn1OctetString kokSubjectKeyIdentifier_,
      KokSertifikaTipi kokSertifikaTipi_,
      KOKGuvenSeviyesi kokGuvenSeviyesi_
   ) {
      super();
      kokSertifikaValue = kokSertifikaValue_;
      kokSertifikaHash = kokSertifikaHash_;
      kokSerialNumber = kokSerialNumber_;
      kokIssuerName = kokIssuerName_;
      kokSubjectName = kokSubjectName_;
      kokStartDate = kokStartDate_;
      kokEndDate = kokEndDate_;
      kokKeyUsage = kokKeyUsage_;
      kokSubjectKeyIdentifier = kokSubjectKeyIdentifier_;
      kokSertifikaTipi = kokSertifikaTipi_;
      kokGuvenSeviyesi = kokGuvenSeviyesi_;
   }

   /**
    * This constructor allows primitive data to be passed for all 
    * primitive elements.  It will create new object wrappers for 
    * the primitive data and set other elements to references to 
    * the given objects 
    */
   public DepoASNEklenecekKokSertifika (byte[] kokSertifikaValue_,
      byte[] kokSertifikaHash_,
      Asn1BigInteger kokSerialNumber_,
      Name kokIssuerName_,
      Name kokSubjectName_,
      Time kokStartDate_,
      Time kokEndDate_,
      KeyUsage kokKeyUsage_,
      byte[] kokSubjectKeyIdentifier_,
      KokSertifikaTipi kokSertifikaTipi_,
      KOKGuvenSeviyesi kokGuvenSeviyesi_
   ) {
      super();
      kokSertifikaValue = new Asn1OctetString (kokSertifikaValue_);
      kokSertifikaHash = new Asn1OctetString (kokSertifikaHash_);
      kokSerialNumber = kokSerialNumber_;
      kokIssuerName = kokIssuerName_;
      kokSubjectName = kokSubjectName_;
      kokStartDate = kokStartDate_;
      kokEndDate = kokEndDate_;
      kokKeyUsage = kokKeyUsage_;
      kokSubjectKeyIdentifier = new Asn1OctetString (kokSubjectKeyIdentifier_);
      kokSertifikaTipi = kokSertifikaTipi_;
      kokGuvenSeviyesi = kokGuvenSeviyesi_;
   }

   public void init () {
      kokSertifikaValue = null;
      kokSertifikaHash = null;
      kokSerialNumber = null;
      kokIssuerName = null;
      kokSubjectName = null;
      kokStartDate = null;
      kokEndDate = null;
      kokKeyUsage = null;
      kokSubjectKeyIdentifier = null;
      kokSertifikaTipi = null;
      kokGuvenSeviyesi = null;
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

      // decode kokSertifikaValue

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 0, elemLen, true)) {
         int offset = buffer.getByteCount();
         kokSertifikaValue = new Asn1OctetString();
         kokSertifikaValue.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }
      else throw new Asn1MissingRequiredException (buffer, "kokSertifikaValue");

      // decode kokSertifikaHash

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 1, elemLen, true)) {
         int offset = buffer.getByteCount();
         kokSertifikaHash = new Asn1OctetString();
         kokSertifikaHash.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }
      else throw new Asn1MissingRequiredException (buffer, "kokSertifikaHash");

      // decode kokSerialNumber

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 2, elemLen, true)) {
         kokSerialNumber = new Asn1BigInteger();
         kokSerialNumber.decode (buffer, false, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "kokSerialNumber");

      // decode kokIssuerName

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 3, elemLen, true)) {
         int offset = buffer.getByteCount();
         kokIssuerName = new Name();
         kokIssuerName.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }
      else throw new Asn1MissingRequiredException (buffer, "kokIssuerName");

      // decode kokSubjectName

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 4, elemLen, true)) {
         int offset = buffer.getByteCount();
         kokSubjectName = new Name();
         kokSubjectName.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }
      else throw new Asn1MissingRequiredException (buffer, "kokSubjectName");

      // decode kokStartDate

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 5, elemLen, true)) {
         int offset = buffer.getByteCount();
         kokStartDate = new Time();
         kokStartDate.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }
      else throw new Asn1MissingRequiredException (buffer, "kokStartDate");

      // decode kokEndDate

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 6, elemLen, true)) {
         int offset = buffer.getByteCount();
         kokEndDate = new Time();
         kokEndDate.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }
      else throw new Asn1MissingRequiredException (buffer, "kokEndDate");

      // decode kokKeyUsage

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 7, elemLen, true)) {
         int offset = buffer.getByteCount();
         kokKeyUsage = new KeyUsage();
         kokKeyUsage.decode (buffer, false, elemLen.value);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }
      else throw new Asn1MissingRequiredException (buffer, "kokKeyUsage");

      // decode kokSubjectKeyIdentifier

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 8, elemLen, true)) {
         kokSubjectKeyIdentifier = new Asn1OctetString();
         kokSubjectKeyIdentifier.decode (buffer, false, elemLen.value);
      }
      else throw new Asn1MissingRequiredException (buffer, "kokSubjectKeyIdentifier");

      // decode kokSertifikaTipi

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 9, elemLen, true)) {
         int offset = buffer.getByteCount();
         int tval = buffer.decodeEnumValue (KokSertifikaTipi.TAG, false, elemLen.value);
         kokSertifikaTipi = KokSertifikaTipi.valueOf (tval);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }
      else throw new Asn1MissingRequiredException (buffer, "kokSertifikaTipi");

      // decode kokGuvenSeviyesi

      if (_context.matchElemTag (Asn1Tag.CTXT, Asn1Tag.PRIM, 10, elemLen, true)) {
         int offset = buffer.getByteCount();
         int tval = buffer.decodeEnumValue (KOKGuvenSeviyesi.TAG, false, elemLen.value);
         kokGuvenSeviyesi = KOKGuvenSeviyesi.valueOf (tval);

         int declen = buffer.getByteCount() - offset;
         if (declen != elemLen.value && elemLen.value != Asn1Status.INDEFLEN)
            throw new Asn1InvalidLengthException();

      }
      else throw new Asn1MissingRequiredException (buffer, "kokGuvenSeviyesi");

      if (!_context.expired()) {
         Asn1Tag _tag = buffer.peekTag ();
         if (_tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 0) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 1) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 2) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 3) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 4) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 5) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.CONS, 6) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 7) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 8) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 9) ||
             _tag.equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 10))
            throw new Asn1SeqOrderException ();

      }
   }

   public int encode (Asn1BerEncodeBuffer buffer, boolean explicit)
      throws Asn1Exception
   {
      int _aal = 0, len;

      // encode kokGuvenSeviyesi

      if (kokGuvenSeviyesi != null) {
         len = kokGuvenSeviyesi.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 10, len);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("kokGuvenSeviyesi");

      // encode kokSertifikaTipi

      if (kokSertifikaTipi != null) {
         len = kokSertifikaTipi.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 9, len);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("kokSertifikaTipi");

      // encode kokSubjectKeyIdentifier

      if (kokSubjectKeyIdentifier != null) {
         len = kokSubjectKeyIdentifier.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 8, len);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("kokSubjectKeyIdentifier");

      // encode kokKeyUsage

      if (kokKeyUsage != null) {
         len = kokKeyUsage.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 7, len);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("kokKeyUsage");

      // encode kokEndDate

      if (kokEndDate != null) {
         len = kokEndDate.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 6, len);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("kokEndDate");

      // encode kokStartDate

      if (kokStartDate != null) {
         len = kokStartDate.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 5, len);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("kokStartDate");

      // encode kokSubjectName

      if (kokSubjectName != null) {
         len = kokSubjectName.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 4, len);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("kokSubjectName");

      // encode kokIssuerName

      if (kokIssuerName != null) {
         len = kokIssuerName.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 3, len);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("kokIssuerName");

      // encode kokSerialNumber

      if (kokSerialNumber != null) {
         len = kokSerialNumber.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 2, len);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("kokSerialNumber");

      // encode kokSertifikaHash

      if (kokSertifikaHash != null) {
         len = kokSertifikaHash.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 1, len);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("kokSertifikaHash");

      // encode kokSertifikaValue

      if (kokSertifikaValue != null) {
         len = kokSertifikaValue.encode (buffer, false);
         len += buffer.encodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 0, len);
         _aal += len;
      }
      else throw new Asn1MissingRequiredException ("kokSertifikaValue");

      if (explicit) {
         _aal += buffer.encodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
      }

      return (_aal);
   }

   public void print (java.io.PrintStream _out, String _varName, int _level)
   {
      indent (_out, _level);
      _out.println (_varName + " {");
      if (kokSertifikaValue != null) kokSertifikaValue.print (_out, "kokSertifikaValue", _level+1);
      if (kokSertifikaHash != null) kokSertifikaHash.print (_out, "kokSertifikaHash", _level+1);
      if (kokSerialNumber != null) kokSerialNumber.print (_out, "kokSerialNumber", _level+1);
      if (kokIssuerName != null) kokIssuerName.print (_out, "kokIssuerName", _level+1);
      if (kokSubjectName != null) kokSubjectName.print (_out, "kokSubjectName", _level+1);
      if (kokStartDate != null) kokStartDate.print (_out, "kokStartDate", _level+1);
      if (kokEndDate != null) kokEndDate.print (_out, "kokEndDate", _level+1);
      if (kokKeyUsage != null) kokKeyUsage.print (_out, "kokKeyUsage", _level+1);
      if (kokSubjectKeyIdentifier != null) kokSubjectKeyIdentifier.print (_out, "kokSubjectKeyIdentifier", _level+1);
      if (kokSertifikaTipi != null) kokSertifikaTipi.print (_out, "kokSertifikaTipi", _level+1);
      if (kokGuvenSeviyesi != null) kokGuvenSeviyesi.print (_out, "kokGuvenSeviyesi", _level+1);
      indent (_out, _level);
      _out.println ("}");
   }
}
