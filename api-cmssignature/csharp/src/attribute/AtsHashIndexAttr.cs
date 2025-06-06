﻿using System;
using Com.Objsys.Asn1.Runtime;
using tr.gov.tubitak.uekae.esya.api.asn.cms;
using Attribute = tr.gov.tubitak.uekae.esya.asn.x509.Attribute;
using tr.gov.tubitak.uekae.esya.api.crypto.alg;
using tr.gov.tubitak.uekae.esya.api.crypto.util;
using tr.gov.tubitak.uekae.esya.api.infra.tsclient;
using tr.gov.tubitak.uekae.esya.asn.cms;

namespace tr.gov.tubitak.uekae.esya.api.cmssignature.attribute
{
    /**
 * <p>
 * The ats-hash-index attribute shall be carried as an attribute of the
 * signature of the archive-time-stamp-v3 Attribute
 * 
 * <p>
 * The ats-hash-index unsigned attribute provides an unambiguous imprint of the
 * essential components of a CAdES signature for use in the archive time-stamp
 * (see 6.4.3). These essential components are elements of the following ASN.1
 * SET OF structures: unsignedAttrs, SignedData.certificates, and
 * SignedData.crls.
 * 
 * (etsi 101733v020201 6.4.2)
 * 
 * @author beytullah.yigit
 * 
 */
    public class AtsHashIndexAttr : AttributeValue
    {
        public static readonly Asn1ObjectIdentifier OID = AttributeOIDs.id_aa_ATSHashIndex;
        private readonly ESignedData sd = null;
        private readonly ESignerInfo si = null;
        private ATSHashIndex atsHashIndex = null;

        public AtsHashIndexAttr(ESignedData signedData, ESignerInfo signerInfo)
        {
            sd = signedData;
            si = signerInfo;
        }

        public override void setValue()
        {
            /*
             * The field hashIndAlgorithm contains an identifier of the hash
             * algorithm used to compute the hash values contained in
             * certificatesHashIndex, crlsHashIndex, and unsignedAttrsHashIndex.
             * This algorithm shall be the same as the hash algorithm used for
             * computing the archive time-stamp’s message imprint.
             */
            DigestAlg digestAlg = getDigestAlg();
            SignedData aSignedData = sd.getObject();
            SignerInfo aSI = si.getObject();

            Asn1DerEncodeBuffer encBuf = new Asn1DerEncodeBuffer();
            Asn1OctetString[] certificatesHashIndex = null;
            if (aSignedData.certificates != null && aSignedData.certificates.elements != null && aSignedData.certificates.elements.Length != 0)
                certificatesHashIndex = new Asn1OctetString[aSignedData.certificates.elements.Length];

            Asn1OctetString[] crlsHashIndex = null;
            if (aSignedData.crls != null && aSignedData.crls.elements != null && aSignedData.crls.elements.Length != 0)
                crlsHashIndex = new Asn1OctetString[aSignedData.crls.elements.Length];

            Asn1OctetString[] unsignedAttrHashIndex = null;
            if (aSI.unsignedAttrs != null && aSI.unsignedAttrs.elements != null && aSI.unsignedAttrs.elements.Length != 0)
                unsignedAttrHashIndex = new Asn1OctetString[aSI.unsignedAttrs.elements.Length];

            try
            {
                /*
                 * The field certificatesHashIndex is a sequence of octet strings.
                 * Each one contains the hash value of one instance of
                 * CertificateChoices within certificates field of the root
                 * SignedData. A hash value for every instance of
                 * CertificateChoices, as present at the time when the corresponding
                 * archive time-stamp is requested, shall be included in
                 * certificatesHashIndex. No other hash value shall be included in
                 * this field.
                 */
                int i = 0;
                if (certificatesHashIndex != null)
                {
                    foreach (CertificateChoices cert in aSignedData.certificates.elements)
                    {
                        if (cert != null)
                        {
                            cert.Encode(encBuf);
                            certificatesHashIndex[i] = new Asn1OctetString(DigestUtil.digest(digestAlg, encBuf.MsgCopy));
                            encBuf.Reset();
                            i++;
                        }
                    }
                }
                /*
                 * The field crlsHashIndex is a sequence of octet strings. Each one
                 * contains the hash value of one instance of RevocationInfoChoice
                 * within crls field of the root SignedData. A hash value for every
                 * instance of RevocationInfoChoice, as present at the time when the
                 * corresponding archive time-stamp is requested, shall be included
                 * in crlsHashIndex. No other hash values shall be included in this
                 * field.
                 */
                i = 0;
                if (crlsHashIndex != null)
                {
                    foreach (RevocationInfoChoice crl in aSignedData.crls.elements)
                    {
                        if (crl != null)
                        {
                            crl.Encode(encBuf);
                            crlsHashIndex[i] = new Asn1OctetString(DigestUtil.digest(digestAlg, encBuf.MsgCopy));
                            encBuf.Reset();
                            i++;
                        }
                    }
                }
                /*
                 * The field unsignedAttrsHashIndex is a sequence of octet strings.
                 * Each one contains the hash value of one instance of Attribute
                 * within unsignedAttrs field of the SignerInfo. A hash value for
                 * every instance of Attribute, as present at the time when the
                 * corresponding archive time-stamp is requested, shall be included
                 * in unsignedAttrsHashIndex. No other hash values shall be included
                 * in this field.
                 */
                i = 0;
                if (unsignedAttrHashIndex != null)
                {
                    foreach (Attribute attr in aSI.unsignedAttrs.elements)
                    {
                        if (attr != null)
                        {
                            attr.Encode(encBuf);
                            unsignedAttrHashIndex[i] = new Asn1OctetString(DigestUtil.digest(digestAlg, encBuf.MsgCopy));
                            encBuf.Reset();
                            i++;
                        }
                    }
                }

                ATSHashIndex_certificatesHashIndex aTSHashIndex_certificatesHashIndex = null;
                if (certificatesHashIndex == null)
                {
                    aTSHashIndex_certificatesHashIndex = new ATSHashIndex_certificatesHashIndex(0); // ??
                }
                else
                {
                    aTSHashIndex_certificatesHashIndex = new ATSHashIndex_certificatesHashIndex(certificatesHashIndex);
                }

                ATSHashIndex_crlsHashIndex aTSHashIndex_crlsHashIndex = null;
                if (crlsHashIndex == null)
                {
                    aTSHashIndex_crlsHashIndex = new ATSHashIndex_crlsHashIndex(0);
                }
                else
                {
                    aTSHashIndex_crlsHashIndex = new ATSHashIndex_crlsHashIndex(crlsHashIndex);
                }

                ATSHashIndex_unsignedAttrsHashIndex aTSHashIndex_unsignedAttrsHashIndex = null;
                if (unsignedAttrHashIndex == null)
                {
                    aTSHashIndex_unsignedAttrsHashIndex = new ATSHashIndex_unsignedAttrsHashIndex(0);
                }
                else
                {
                    aTSHashIndex_unsignedAttrsHashIndex = new ATSHashIndex_unsignedAttrsHashIndex(unsignedAttrHashIndex);
                }

                if (digestAlg != DigestAlg.SHA256)
                {
                    atsHashIndex = new ATSHashIndex(digestAlg.toAlgorithmIdentifier().getObject(),
                            aTSHashIndex_certificatesHashIndex,
                            aTSHashIndex_crlsHashIndex,
                            aTSHashIndex_unsignedAttrsHashIndex);
                }
                else
                {
                    atsHashIndex = new ATSHashIndex(
                            aTSHashIndex_certificatesHashIndex,
                            aTSHashIndex_crlsHashIndex,
                            aTSHashIndex_unsignedAttrsHashIndex);
                }

                _setValue(atsHashIndex);
            }
            catch (Exception aEx)
            {
                throw new CMSSignatureException("Error while setting ats-hash-index attribute", aEx);
            }
        }

        public ATSHashIndex getAtsHashIndex()
        {
            return atsHashIndex;
        }

        private DigestAlg getDigestAlg()
        {
            TSSettings tsSettings = null;
            try
            {
                Object tssInfo = mAttParams[AllEParameters.P_TSS_INFO];
                tsSettings = (TSSettings)tssInfo;
            }
            catch (Exception aEx)
            {
                throw new CMSSignatureException("P_TSS_INFO parameter  is not of type TSSettings", aEx);
            }
            return tsSettings.getDigestAlg();
        }
        /**
        * Checks whether attribute is signed or not.
        * @return false 
        */
        public override bool isSigned()
        {
            return false;
        }
        /**
         * Returns AttributeOID of ArchiveTimeStampAttr attribute
         * @return
         */
        public override Asn1ObjectIdentifier getAttributeOID()
        {
            return OID;
        }
    }
}
