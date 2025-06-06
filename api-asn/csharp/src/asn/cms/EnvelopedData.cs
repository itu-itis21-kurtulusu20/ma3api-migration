// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.cms {

   public class EnvelopedData : Asn1Type {
      public CMSVersion version;
      public OriginatorInfo originatorInfo;  // optional
      public RecipientInfos recipientInfos;
      public tr.gov.tubitak.uekae.esya.asn.cms.EncryptedContentInfo encryptedContentInfo;
      public UnprotectedAttributes unprotectedAttrs;  // optional

      static EnvelopedData ()
      {
         Asn1Type.SetKey2 (_cmsValues._rtkey);
      }

      public EnvelopedData () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public EnvelopedData (
         CMSVersion version_,
         OriginatorInfo originatorInfo_,
         RecipientInfos recipientInfos_,
         tr.gov.tubitak.uekae.esya.asn.cms.EncryptedContentInfo encryptedContentInfo_,
         UnprotectedAttributes unprotectedAttrs_
      )
         : base ()
      {
         version = version_;
         originatorInfo = originatorInfo_;
         recipientInfos = recipientInfos_;
         encryptedContentInfo = encryptedContentInfo_;
         unprotectedAttrs = unprotectedAttrs_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It sets 
      /// all elements to references to the given objects
      /// </summary>
      public EnvelopedData (
         CMSVersion version_,
         RecipientInfos recipientInfos_,
         tr.gov.tubitak.uekae.esya.asn.cms.EncryptedContentInfo encryptedContentInfo_
      )
         : base ()
      {
         version = version_;
         recipientInfos = recipientInfos_;
         encryptedContentInfo = encryptedContentInfo_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public EnvelopedData (long version_,
         OriginatorInfo originatorInfo_,
         RecipientInfos recipientInfos_,
         tr.gov.tubitak.uekae.esya.asn.cms.EncryptedContentInfo encryptedContentInfo_,
         UnprotectedAttributes unprotectedAttrs_
      )
         : base ()
      {
         version = new CMSVersion (version_);
         originatorInfo = originatorInfo_;
         recipientInfos = recipientInfos_;
         encryptedContentInfo = encryptedContentInfo_;
         unprotectedAttrs = unprotectedAttrs_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It allows 
      /// primitive data to be passed for all primitive elements.  
      /// It will create new object wrappers for the primitive data 
      /// and set other elements to references to the given objects. 
      /// </summary>
      public EnvelopedData (
         long version_,
         RecipientInfos recipientInfos_,
         tr.gov.tubitak.uekae.esya.asn.cms.EncryptedContentInfo encryptedContentInfo_
      )
         : base ()
      {
         version = new CMSVersion (version_);
         recipientInfos = recipientInfos_;
         encryptedContentInfo = encryptedContentInfo_;
      }

      public void Init () {
         version = null;
         originatorInfo = null;
         recipientInfos = null;
         encryptedContentInfo = null;
         unprotectedAttrs = null;
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
            version = new CMSVersion();
            version.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode originatorInfo

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
            int offset = buffer.ByteCount;
            originatorInfo = new OriginatorInfo();
            originatorInfo.Decode (buffer, false, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
            if (elemLen.mValue == Asn1Status.INDEFLEN) {
               MatchTag (buffer, Asn1Tag.EOC);
            }
         }

         // decode recipientInfos

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 17, elemLen, false)) {
            recipientInfos = new RecipientInfos();
            recipientInfos.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode encryptedContentInfo

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            encryptedContentInfo = new EncryptedContentInfo();
            encryptedContentInfo.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode unprotectedAttrs

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 1, elemLen, true)) {
            int offset = buffer.ByteCount;
            unprotectedAttrs = new UnprotectedAttributes();
            unprotectedAttrs.Decode (buffer, false, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
            if (elemLen.mValue == Asn1Status.INDEFLEN) {
               MatchTag (buffer, Asn1Tag.EOC);
            }
         }

         if (explicitTagging && llen == Asn1Status.INDEFLEN) {
            MatchTag (buffer, Asn1Tag.EOC);
         }
      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode unprotectedAttrs

         if (unprotectedAttrs != null) {
            len = unprotectedAttrs.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 1, len);
            _aal += len;
         }

         // encode encryptedContentInfo

         len = encryptedContentInfo.Encode (buffer, true);
         _aal += len;

         // encode recipientInfos

         len = recipientInfos.Encode (buffer, true);
         _aal += len;

         // encode originatorInfo

         if (originatorInfo != null) {
            len = originatorInfo.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
            _aal += len;
         }

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
         if (originatorInfo != null) originatorInfo.Print (_out, "originatorInfo", _level+1);
         if (recipientInfos != null) recipientInfos.Print (_out, "recipientInfos", _level+1);
         if (encryptedContentInfo != null) encryptedContentInfo.Print (_out, "encryptedContentInfo", _level+1);
         if (unprotectedAttrs != null) unprotectedAttrs.Print (_out, "unprotectedAttrs", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
