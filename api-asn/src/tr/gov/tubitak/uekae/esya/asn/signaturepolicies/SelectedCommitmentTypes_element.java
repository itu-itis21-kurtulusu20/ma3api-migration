/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.4, Date: 04-Oct-2013.
 */
package tr.gov.tubitak.uekae.esya.asn.signaturepolicies;

import com.objsys.asn1j.runtime.*;

public class SelectedCommitmentTypes_element extends Asn1Choice {
   private static final long serialVersionUID = 55;
   static {
      setKey (_signaturepoliciesRtkey._rtkey);
   }

   // Choice element identifier constants
   public final static byte _EMPTY = 1;
   public final static byte _RECOGNIZEDCOMMITMENTTYPE = 2;

   public SelectedCommitmentTypes_element () {
      super();
   }

   public SelectedCommitmentTypes_element (byte choiceId_, Asn1Type element_) {
      super();
      setElement (choiceId_, element_);
   }

   public String getElemName () {
      switch (choiceID) {
      case _EMPTY: return "empty";
      case _RECOGNIZEDCOMMITMENTTYPE: return "recognizedCommitmentType";
      default: return "UNDEFINED";
      }
   }

   public void set_empty () {
      setElement (_EMPTY, Asn1Null.NULL_VALUE);
   }

   public void set_recognizedCommitmentType (CommitmentType value) {
      setElement (_RECOGNIZEDCOMMITMENTTYPE, value);
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
         Asn1Null empty;
         empty = new Asn1Null();
         empty.decode (buffer, true, len);
         setElement (_EMPTY, empty);
      }
      else if (tag.equals (Asn1Tag.UNIV, Asn1Tag.CONS, 16))
      {
         buffer.reset();
         CommitmentType recognizedCommitmentType;
         recognizedCommitmentType = new CommitmentType();
         recognizedCommitmentType.decode (buffer, true, len);
         setElement (_RECOGNIZEDCOMMITMENTTYPE, recognizedCommitmentType);
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
      // encode empty
      case _EMPTY:
         Asn1Null empty = (Asn1Null) getElement();
         len = empty.encode (buffer, true);
         _aal += len;
         break;

      // encode recognizedCommitmentType
      case _RECOGNIZEDCOMMITMENTTYPE:
         CommitmentType recognizedCommitmentType = (CommitmentType) getElement();
         len = recognizedCommitmentType.encode (buffer, true);
         _aal += len;
         break;

      default:
         throw new Asn1InvalidChoiceOptionException();
      }

      return _aal;
   }

   public void print (java.io.PrintStream _out, String _varName, int _level)
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
