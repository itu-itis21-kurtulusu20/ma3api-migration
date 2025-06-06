// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.cms {

   public class EvidenceRecord : Asn1Type {
      public EvidenceRecord_version version;
      public _SeqOfAlgorithmIdentifier digestAlgorithms;
      public CryptoInfos cryptoInfos;  // optional
      public EncryptionInfo encryptionInfo;  // optional
      public ArchiveTimeStampSequence archiveTimeStampSequence;

      static EvidenceRecord ()
      {
         Asn1Type.SetKey2 (_etsi101733Values._rtkey);
      }

      public EvidenceRecord () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public EvidenceRecord (
         EvidenceRecord_version version_,
         _SeqOfAlgorithmIdentifier digestAlgorithms_,
         CryptoInfos cryptoInfos_,
         EncryptionInfo encryptionInfo_,
         ArchiveTimeStampSequence archiveTimeStampSequence_
      )
         : base ()
      {
         version = version_;
         digestAlgorithms = digestAlgorithms_;
         cryptoInfos = cryptoInfos_;
         encryptionInfo = encryptionInfo_;
         archiveTimeStampSequence = archiveTimeStampSequence_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It sets 
      /// all elements to references to the given objects
      /// </summary>
      public EvidenceRecord (
         EvidenceRecord_version version_,
         _SeqOfAlgorithmIdentifier digestAlgorithms_,
         ArchiveTimeStampSequence archiveTimeStampSequence_
      )
         : base ()
      {
         version = version_;
         digestAlgorithms = digestAlgorithms_;
         archiveTimeStampSequence = archiveTimeStampSequence_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public EvidenceRecord (long version_,
         _SeqOfAlgorithmIdentifier digestAlgorithms_,
         CryptoInfos cryptoInfos_,
         EncryptionInfo encryptionInfo_,
         ArchiveTimeStampSequence archiveTimeStampSequence_
      )
         : base ()
      {
         version = new EvidenceRecord_version (version_);
         digestAlgorithms = digestAlgorithms_;
         cryptoInfos = cryptoInfos_;
         encryptionInfo = encryptionInfo_;
         archiveTimeStampSequence = archiveTimeStampSequence_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It allows 
      /// primitive data to be passed for all primitive elements.  
      /// It will create new object wrappers for the primitive data 
      /// and set other elements to references to the given objects. 
      /// </summary>
      public EvidenceRecord (
         long version_,
         _SeqOfAlgorithmIdentifier digestAlgorithms_,
         ArchiveTimeStampSequence archiveTimeStampSequence_
      )
         : base ()
      {
         version = new EvidenceRecord_version (version_);
         digestAlgorithms = digestAlgorithms_;
         archiveTimeStampSequence = archiveTimeStampSequence_;
      }

      public void Init () {
         version = null;
         digestAlgorithms = null;
         cryptoInfos = null;
         encryptionInfo = null;
         archiveTimeStampSequence = null;
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
            version = new EvidenceRecord_version();
            version.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode digestAlgorithms

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            digestAlgorithms = new _SeqOfAlgorithmIdentifier();
            digestAlgorithms.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode cryptoInfos

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 0, elemLen, true)) {
            int offset = buffer.ByteCount;
            cryptoInfos = new CryptoInfos();
            cryptoInfos.Decode (buffer, true, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }

         // decode encryptionInfo

         if (_context.MatchElemTag (Asn1Tag.CTXT, Asn1Tag.CONS, 1, elemLen, true)) {
            int offset = buffer.ByteCount;
            encryptionInfo = new EncryptionInfo();
            encryptionInfo.Decode (buffer, true, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }

         // decode archiveTimeStampSequence

         if (_context.MatchElemTag (Asn1Tag.UNIV, Asn1Tag.CONS, 16, elemLen, false)) {
            archiveTimeStampSequence = new ArchiveTimeStampSequence();
            archiveTimeStampSequence.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode archiveTimeStampSequence

         len = archiveTimeStampSequence.Encode (buffer, true);
         _aal += len;

         // encode encryptionInfo

         if (encryptionInfo != null) {
            len = encryptionInfo.Encode (buffer, true);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 1, len);
            _aal += len;
         }

         // encode cryptoInfos

         if (cryptoInfos != null) {
            len = cryptoInfos.Encode (buffer, true);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
            _aal += len;
         }

         // encode digestAlgorithms

         len = digestAlgorithms.Encode (buffer, true);
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
         if (digestAlgorithms != null) digestAlgorithms.Print (_out, "digestAlgorithms", _level+1);
         if (cryptoInfos != null) cryptoInfos.Print (_out, "cryptoInfos", _level+1);
         if (encryptionInfo != null) encryptionInfo.Print (_out, "encryptionInfo", _level+1);
         if (archiveTimeStampSequence != null) archiveTimeStampSequence.Print (_out, "archiveTimeStampSequence", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
