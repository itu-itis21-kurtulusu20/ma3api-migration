// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.pkixtsp {

   using Extensions = tr.gov.tubitak.uekae.esya.asn.x509.Extensions;

   public class TimeStampReq : Asn1Type {
      public TimeStampReq_version version;
      public MessageImprint messageImprint;
      public Asn1ObjectIdentifier reqPolicy;  // optional
      public Asn1BigInteger nonce;  // optional
      public Asn1Boolean certReq;  // default = false
      public Extensions extensions;  // optional

      static TimeStampReq ()
      {
         Asn1Type.SetKey2 (_pkixtspValues._rtkey);
      }

      public TimeStampReq () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public TimeStampReq (
         TimeStampReq_version version_,
         MessageImprint messageImprint_,
         Asn1ObjectIdentifier reqPolicy_,
         Asn1BigInteger nonce_,
         Asn1Boolean certReq_,
         Extensions extensions_
      )
         : base ()
      {
         version = version_;
         messageImprint = messageImprint_;
         reqPolicy = reqPolicy_;
         nonce = nonce_;
         certReq = certReq_;
         extensions = extensions_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It sets 
      /// all elements to references to the given objects
      /// </summary>
      public TimeStampReq (
         TimeStampReq_version version_,
         MessageImprint messageImprint_
      )
         : base ()
      {
         version = version_;
         messageImprint = messageImprint_;
         certReq = new Asn1Boolean (false);
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public TimeStampReq (long version_,
         MessageImprint messageImprint_,
         int[] reqPolicy_,
         Asn1BigInteger nonce_,
         bool certReq_,
         Extensions extensions_
      )
         : base ()
      {
         version = new TimeStampReq_version (version_);
         messageImprint = messageImprint_;
         reqPolicy = new Asn1ObjectIdentifier (reqPolicy_);
         nonce = nonce_;
         certReq = new Asn1Boolean (certReq_);
         extensions = extensions_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It allows 
      /// primitive data to be passed for all primitive elements.  
      /// It will create new object wrappers for the primitive data 
      /// and set other elements to references to the given objects. 
      /// </summary>
      public TimeStampReq (
         long version_,
         MessageImprint messageImprint_
      )
         : base ()
      {
         version = new TimeStampReq_version (version_);
         messageImprint = messageImprint_;
         certReq = new Asn1Boolean (false);
      }

      public void Init () {
         version = null;
         messageImprint = null;
         reqPolicy = null;
         nonce = null;
         certReq = new Asn1Boolean (false);
         extensions = null;
      }

      public override void Decode
         (Asn1BerDecodeBuffer buffer, bool explicitTagging, int implicitLength)
      {
         int llen = (explicitTagging) ?
            MatchTag (buffer, Asn1Tag.SEQUENCE) : implicitLength;

         Init ();

         // decode SEQUENCE

         Asn1BerDecodeContext _context =
            new Asn1BerDecodeContext (buffer, llen);

         IntHolder elemLen = new IntHolder();

         // decode version

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
            version = new TimeStampReq_version();
            version.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode messageImprint

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            messageImprint = new MessageImprint();
            messageImprint.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode reqPolicy

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 6, elemLen, false)) {
            reqPolicy = new Asn1ObjectIdentifier();
            reqPolicy.Decode (buffer, true, elemLen.mValue);
         }

         // decode nonce

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 2, elemLen, false)) {
            nonce = new Asn1BigInteger();
            nonce.Decode (buffer, true, elemLen.mValue);
         }

         // decode certReq

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.PRIM, 1, elemLen, false)) {
            certReq = new Asn1Boolean();
            certReq.Decode (buffer, true, elemLen.mValue);
         }

         // decode extensions

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
            int offset = buffer.ByteCount;
            extensions = new Extensions();
            extensions.Decode (buffer, false, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode extensions

         if (extensions != null) {
            len = extensions.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
            _aal += len;
         }

         // encode certReq

         if (!certReq.Equals (false)) {
            len = certReq.Encode (buffer, true);
            _aal += len;
         }

         // encode nonce

         if (nonce != null) {
            len = nonce.Encode (buffer, true);
            _aal += len;
         }

         // encode reqPolicy

         if (reqPolicy != null) {
            len = reqPolicy.Encode (buffer, true);
            _aal += len;
         }

         // encode messageImprint

         len = messageImprint.Encode (buffer, true);
         _aal += len;

         // encode version

         len = version.Encode (buffer, true);
         _aal += len;

         if (explicitTagging) {
            _aal += buffer.EncodeTagAndLength (Asn1Tag.SEQUENCE, _aal);
         }

         return (_aal);
      }

      public override void Print (System.IO.TextWriter _out, 
                                  string _varName, int _level)
      {
         Indent (_out, _level);
         _out.WriteLine (_varName + " {");
         if (version != null) version.Print (_out, "version", _level+1);
         if (messageImprint != null) messageImprint.Print (_out, "messageImprint", _level+1);
         if (reqPolicy != null) reqPolicy.Print (_out, "reqPolicy", _level+1);
         if (nonce != null) nonce.Print (_out, "nonce", _level+1);
         if (certReq != null) certReq.Print (_out, "certReq", _level+1);
         if (extensions != null) extensions.Print (_out, "extensions", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
