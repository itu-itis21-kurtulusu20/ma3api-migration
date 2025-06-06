/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.6, Date: 28-Feb-2014.
 */
#include "attrcert.h"
#include "rtsrc/rtPrintToStream.h"
#include "rtxsrc/rtxCommon.h"

int asn1PrtToStrm_ATTRCERT_AttCertVersion (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_AttCertVersion* pvalue)
{
   {
      char namebuf[512];
      OSBOOL printNamebuf = TRUE;

      rtPrintToStreamIndent (pctxt);
      rtxStrcpy (namebuf, 512, name);
      rtxStrcat (namebuf, 512, " = ");
      switch (*pvalue) {
         case 1: rtxStrcat (namebuf, 512, "v2\n");break;
         default:
            rtPrintToStreamInteger (pctxt, name, *pvalue);
            printNamebuf = FALSE;
      }

      if (printNamebuf) rtxPrintToStream (pctxt, namebuf);
   }
   return 0;
}

int ASN1C_ATTRCERT_AttCertVersion::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_AttCertVersion (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_AttCertVersion::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_ClassList (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_ClassList* pvalue)
{
   rtPrintToStreamIndent (pctxt);
   rtPrintToStreamBitStrBraceText (pctxt, name, pvalue->numbits, pvalue->data);
   return 0;
}

int ASN1C_ATTRCERT_ClassList::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_ClassList (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_ClassList::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_AttCertVersionV1 (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_AttCertVersionV1* pvalue)
{
   {
      char namebuf[512];
      OSBOOL printNamebuf = TRUE;

      rtPrintToStreamIndent (pctxt);
      rtxStrcpy (namebuf, 512, name);
      rtxStrcat (namebuf, 512, " = ");
      switch (*pvalue) {
         case 0: rtxStrcat (namebuf, 512, "v1\n");break;
         default:
            rtPrintToStreamInteger (pctxt, name, *pvalue);
            printNamebuf = FALSE;
      }

      if (printNamebuf) rtxPrintToStream (pctxt, namebuf);
   }
   return 0;
}

int ASN1C_ATTRCERT_AttCertVersionV1::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_AttCertVersionV1 (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_AttCertVersionV1::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_ObjectDigestInfo_digestedObjectType (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_ObjectDigestInfo_digestedObjectType* pvalue)
{
   rtPrintToStreamIndent (pctxt);
   rtxPrintToStream (pctxt, name);
   switch (*pvalue) {
      case 0:
         rtxPrintToStream (pctxt, " = publicKey\n");
         break;
      case 1:
         rtxPrintToStream (pctxt, " = publicKeyCert\n");
         break;
      case 2:
         rtxPrintToStream (pctxt, " = otherObjectTypes\n");
         break;
      default:
         rtxPrintToStream (pctxt," = ???\n");
   }

   return 0;
}

int ASN1C_ATTRCERT_ObjectDigestInfo_digestedObjectType::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_ObjectDigestInfo_digestedObjectType (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_ObjectDigestInfo_digestedObjectType::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_IssuerSerial (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_IssuerSerial* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   asn1PrtToStrm_IMP_GeneralNames (pctxt, "issuer", &pvalue->issuer);

   asn1PrtToStrm_EXP_CertificateSerialNumber (pctxt, "serial", &pvalue->serial);

   if (pvalue->m.issuerUIDPresent) {
      asn1PrtToStrm_EXP_UniqueIdentifier (pctxt, "issuerUID", &pvalue->issuerUID);
   }

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_IssuerSerial::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_IssuerSerial (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_IssuerSerial::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_ObjectDigestInfo (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_ObjectDigestInfo* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   asn1PrtToStrm_ATTRCERT_ObjectDigestInfo_digestedObjectType (pctxt, "digestedObjectType", &pvalue->digestedObjectType);

   if (pvalue->m.otherObjectTypeIDPresent) {
      rtPrintToStreamIndent (pctxt);
      rtPrintToStreamOID (pctxt, "otherObjectTypeID", (ASN1OBJID*)&pvalue->otherObjectTypeID);
   }

   asn1PrtToStrm_EXP_AlgorithmIdentifier (pctxt, "digestAlgorithm", &pvalue->digestAlgorithm);

   rtPrintToStreamIndent (pctxt);
   rtPrintToStreamBitStrBraceText (pctxt, "objectDigest", pvalue->objectDigest.numbits, pvalue->objectDigest.data);

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_ObjectDigestInfo::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_ObjectDigestInfo (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_ObjectDigestInfo::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_Holder (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_Holder* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   if (pvalue->m.baseCertificateIDPresent) {
      asn1PrtToStrm_ATTRCERT_IssuerSerial (pctxt, "baseCertificateID", &pvalue->baseCertificateID);
   }

   if (pvalue->m.entityNamePresent) {
      asn1PrtToStrm_IMP_GeneralNames (pctxt, "entityName", &pvalue->entityName);
   }

   if (pvalue->m.objectDigestInfoPresent) {
      asn1PrtToStrm_ATTRCERT_ObjectDigestInfo (pctxt, "objectDigestInfo", &pvalue->objectDigestInfo);
   }

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_Holder::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_Holder (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_Holder::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_V2Form (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_V2Form* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   if (pvalue->m.issuerNamePresent) {
      asn1PrtToStrm_IMP_GeneralNames (pctxt, "issuerName", &pvalue->issuerName);
   }

   if (pvalue->m.baseCertificateIDPresent) {
      asn1PrtToStrm_ATTRCERT_IssuerSerial (pctxt, "baseCertificateID", &pvalue->baseCertificateID);
   }

   if (pvalue->m.objectDigestInfoPresent) {
      asn1PrtToStrm_ATTRCERT_ObjectDigestInfo (pctxt, "objectDigestInfo", &pvalue->objectDigestInfo);
   }

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_V2Form::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_V2Form (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_V2Form::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_AttCertIssuer (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_AttCertIssuer* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   switch (pvalue->t)
   {
      /* v1Form */
      case 1:
      {
         asn1PrtToStrm_IMP_GeneralNames (pctxt, "v1Form", pvalue->u.v1Form);
         break;
      }
      /* v2Form */
      case 2:
      {
         asn1PrtToStrm_ATTRCERT_V2Form (pctxt, "v2Form", pvalue->u.v2Form);
         break;
      }
      default:;
   }

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_AttCertIssuer::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_AttCertIssuer (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_AttCertIssuer::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_AttCertValidityPeriod (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_AttCertValidityPeriod* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   rtPrintToStreamIndent (pctxt);
   rtPrintToStreamCharStr (pctxt, "notBeforeTime", pvalue->notBeforeTime);

   rtPrintToStreamIndent (pctxt);
   rtPrintToStreamCharStr (pctxt, "notAfterTime", pvalue->notAfterTime);

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_AttCertValidityPeriod::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_AttCertValidityPeriod (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_AttCertValidityPeriod::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT__SeqOfATTRCERT_Attribute (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT__SeqOfATTRCERT_Attribute* pvalue)
{
   ASN1T_EXP_Attribute* pdata0;
   OSRTDListNode* pnode;
   char namebuf[512], numbuf[32];
   OSUINT32 xx1;

   pnode = pvalue->head;
   for (xx1 = 0; xx1 < pvalue->count; xx1++) {
      pdata0 = (ASN1T_EXP_Attribute*) pnode->data;
      rtxUIntToCharStr (xx1, numbuf, sizeof(numbuf), 0);
      rtxStrJoin (namebuf, sizeof(namebuf), name, "[", numbuf, "]", 0);
      asn1PrtToStrm_EXP_Attribute (pctxt, namebuf, pdata0);
      pnode = pnode->next;
   }
   return 0;
}

int ASN1C_ATTRCERT__SeqOfATTRCERT_Attribute::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT__SeqOfATTRCERT_Attribute (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT__SeqOfATTRCERT_Attribute::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_AttributeCertificateInfo (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_AttributeCertificateInfo* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   asn1PrtToStrm_ATTRCERT_AttCertVersion (pctxt, "version", &pvalue->version);

   asn1PrtToStrm_ATTRCERT_Holder (pctxt, "holder", &pvalue->holder);

   asn1PrtToStrm_ATTRCERT_AttCertIssuer (pctxt, "issuer", &pvalue->issuer);

   asn1PrtToStrm_EXP_AlgorithmIdentifier (pctxt, "signature_", &pvalue->signature_);

   asn1PrtToStrm_EXP_CertificateSerialNumber (pctxt, "serialNumber", &pvalue->serialNumber);

   asn1PrtToStrm_ATTRCERT_AttCertValidityPeriod (pctxt, "attrCertValidityPeriod", &pvalue->attrCertValidityPeriod);

   asn1PrtToStrm_ATTRCERT__SeqOfATTRCERT_Attribute (pctxt, "attributes", &pvalue->attributes);

   if (pvalue->m.issuerUniqueIDPresent) {
      asn1PrtToStrm_EXP_UniqueIdentifier (pctxt, "issuerUniqueID", &pvalue->issuerUniqueID);
   }

   if (pvalue->m.extensionsPresent) {
      asn1PrtToStrm_EXP_Extensions (pctxt, "extensions", &pvalue->extensions);
   }

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_AttributeCertificateInfo::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_AttributeCertificateInfo (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_AttributeCertificateInfo::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_AttributeCertificate (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_AttributeCertificate* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   asn1PrtToStrm_ATTRCERT_AttributeCertificateInfo (pctxt, "acinfo", &pvalue->acinfo);

   asn1PrtToStrm_EXP_AlgorithmIdentifier (pctxt, "signatureAlgorithm", &pvalue->signatureAlgorithm);

   rtPrintToStreamIndent (pctxt);
   rtPrintToStreamBitStrBraceText (pctxt, "signatureValue", pvalue->signatureValue.numbits, pvalue->signatureValue.data);

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_AttributeCertificate::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_AttributeCertificate (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_AttributeCertificate::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_TargetCert (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_TargetCert* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   asn1PrtToStrm_ATTRCERT_IssuerSerial (pctxt, "targetCertificate", &pvalue->targetCertificate);

   if (pvalue->m.targetNamePresent) {
      asn1PrtToStrm_IMP_GeneralName (pctxt, "targetName", &pvalue->targetName);
   }

   if (pvalue->m.certDigestInfoPresent) {
      asn1PrtToStrm_ATTRCERT_ObjectDigestInfo (pctxt, "certDigestInfo", &pvalue->certDigestInfo);
   }

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_TargetCert::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_TargetCert (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_TargetCert::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_Target (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_Target* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   switch (pvalue->t)
   {
      /* targetName */
      case 1:
      {
         asn1PrtToStrm_IMP_GeneralName (pctxt, "targetName", pvalue->u.targetName);
         break;
      }
      /* targetGroup */
      case 2:
      {
         asn1PrtToStrm_IMP_GeneralName (pctxt, "targetGroup", pvalue->u.targetGroup);
         break;
      }
      /* targetCert */
      case 3:
      {
         asn1PrtToStrm_ATTRCERT_TargetCert (pctxt, "targetCert", pvalue->u.targetCert);
         break;
      }
      default:;
   }

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_Target::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_Target (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_Target::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_Targets (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_Targets* pvalue)
{
   ASN1T_ATTRCERT_Target* pdata0;
   OSRTDListNode* pnode;
   char namebuf[512], numbuf[32];
   OSUINT32 xx1;

   pnode = pvalue->head;
   for (xx1 = 0; xx1 < pvalue->count; xx1++) {
      pdata0 = (ASN1T_ATTRCERT_Target*) pnode->data;
      rtxUIntToCharStr (xx1, numbuf, sizeof(numbuf), 0);
      rtxStrJoin (namebuf, sizeof(namebuf), name, "[", numbuf, "]", 0);
      asn1PrtToStrm_ATTRCERT_Target (pctxt, namebuf, pdata0);
      pnode = pnode->next;
   }
   return 0;
}

int ASN1C_ATTRCERT_Targets::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_Targets (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_Targets::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_IetfAttrSyntax_values_element (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_IetfAttrSyntax_values_element* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   switch (pvalue->t)
   {
      /* octets */
      case 1:
      {
         rtPrintToStreamIndent (pctxt);
         rtPrintToStreamHexStr (pctxt, "octets", pvalue->u.octets->numocts, pvalue->u.octets->data);
         break;
      }
      /* oid */
      case 2:
      {
         rtPrintToStreamIndent (pctxt);
         rtPrintToStreamOID (pctxt, "oid", (ASN1OBJID*)pvalue->u.oid);
         break;
      }
      /* string */
      case 3:
      {
         rtPrintToStreamIndent (pctxt);
         rtxPrintToStreamUTF8CharStr (pctxt, "string", pvalue->u.string);
         break;
      }
      default:;
   }

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_IetfAttrSyntax_values_element::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_IetfAttrSyntax_values_element (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_IetfAttrSyntax_values_element::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT__SeqOfATTRCERT_IetfAttrSyntax_values_element (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT__SeqOfATTRCERT_IetfAttrSyntax_values_element* pvalue)
{
   ASN1T_ATTRCERT_IetfAttrSyntax_values_element* pdata0;
   OSRTDListNode* pnode;
   char namebuf[512], numbuf[32];
   OSUINT32 xx1;

   pnode = pvalue->head;
   for (xx1 = 0; xx1 < pvalue->count; xx1++) {
      pdata0 = (ASN1T_ATTRCERT_IetfAttrSyntax_values_element*) pnode->data;
      rtxUIntToCharStr (xx1, numbuf, sizeof(numbuf), 0);
      rtxStrJoin (namebuf, sizeof(namebuf), name, "[", numbuf, "]", 0);
      asn1PrtToStrm_ATTRCERT_IetfAttrSyntax_values_element (pctxt, namebuf, pdata0);
      pnode = pnode->next;
   }
   return 0;
}

int ASN1C_ATTRCERT__SeqOfATTRCERT_IetfAttrSyntax_values_element::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT__SeqOfATTRCERT_IetfAttrSyntax_values_element (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT__SeqOfATTRCERT_IetfAttrSyntax_values_element::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_IetfAttrSyntax (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_IetfAttrSyntax* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   if (pvalue->m.policyAuthorityPresent) {
      asn1PrtToStrm_IMP_GeneralNames (pctxt, "policyAuthority", &pvalue->policyAuthority);
   }

   asn1PrtToStrm_ATTRCERT__SeqOfATTRCERT_IetfAttrSyntax_values_element (pctxt, "values", &pvalue->values);

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_IetfAttrSyntax::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_IetfAttrSyntax (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_IetfAttrSyntax::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_SvceAuthInfo (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_SvceAuthInfo* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   asn1PrtToStrm_IMP_GeneralName (pctxt, "service", &pvalue->service);

   asn1PrtToStrm_IMP_GeneralName (pctxt, "ident", &pvalue->ident);

   if (pvalue->m.authInfoPresent) {
      rtPrintToStreamIndent (pctxt);
      rtPrintToStreamHexStr (pctxt, "authInfo", pvalue->authInfo.numocts, pvalue->authInfo.data);
   }

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_SvceAuthInfo::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_SvceAuthInfo (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_SvceAuthInfo::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_RoleSyntax (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_RoleSyntax* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   if (pvalue->m.roleAuthorityPresent) {
      asn1PrtToStrm_IMP_GeneralNames (pctxt, "roleAuthority", &pvalue->roleAuthority);
   }

   asn1PrtToStrm_IMP_GeneralName (pctxt, "roleName", &pvalue->roleName);

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_RoleSyntax::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_RoleSyntax (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_RoleSyntax::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_SecurityCategory (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_SecurityCategory* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   rtPrintToStreamIndent (pctxt);
   rtPrintToStreamOID (pctxt, "type", (ASN1OBJID*)&pvalue->type);

   rtPrintToStreamIndent (pctxt);
   rtPrintToStreamHexStr (pctxt, "value",
      pvalue->value.numocts, pvalue->value.data);

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_SecurityCategory::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_SecurityCategory (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_SecurityCategory::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT__SetOfATTRCERT_SecurityCategory (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT__SetOfATTRCERT_SecurityCategory* pvalue)
{
   ASN1T_ATTRCERT_SecurityCategory* pdata0;
   OSRTDListNode* pnode;
   char namebuf[512], numbuf[32];
   OSUINT32 xx1;

   pnode = pvalue->head;
   for (xx1 = 0; xx1 < pvalue->count; xx1++) {
      pdata0 = (ASN1T_ATTRCERT_SecurityCategory*) pnode->data;
      rtxUIntToCharStr (xx1, numbuf, sizeof(numbuf), 0);
      rtxStrJoin (namebuf, sizeof(namebuf), name, "[", numbuf, "]", 0);
      asn1PrtToStrm_ATTRCERT_SecurityCategory (pctxt, namebuf, pdata0);
      pnode = pnode->next;
   }
   return 0;
}

int ASN1C_ATTRCERT__SetOfATTRCERT_SecurityCategory::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT__SetOfATTRCERT_SecurityCategory (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT__SetOfATTRCERT_SecurityCategory::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_Clearance (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_Clearance* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   rtPrintToStreamIndent (pctxt);
   rtPrintToStreamOID (pctxt, "policyId", (ASN1OBJID*)&pvalue->policyId);

   if (pvalue->m.classListPresent) {
      asn1PrtToStrm_ATTRCERT_ClassList (pctxt, "classList", &pvalue->classList);
   }

   if (pvalue->m.securityCategoriesPresent) {
      asn1PrtToStrm_ATTRCERT__SetOfATTRCERT_SecurityCategory (pctxt, "securityCategories", &pvalue->securityCategories);
   }

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_Clearance::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_Clearance (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_Clearance::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_AttrSpec (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_AttrSpec* pvalue)
{
   ASN1TObjId* pdata0;
   OSRTDListNode* pnode;
   char namebuf[512], numbuf[32];
   OSUINT32 xx1;

   pnode = pvalue->head;
   for (xx1 = 0; xx1 < pvalue->count; xx1++) {
      pdata0 = (ASN1TObjId*) pnode->data;
      rtxUIntToCharStr (xx1, numbuf, sizeof(numbuf), 0);
      rtxStrJoin (namebuf, sizeof(namebuf), name, "[", numbuf, "]", 0);
      rtPrintToStreamIndent (pctxt);
      rtPrintToStreamOID (pctxt, namebuf, (ASN1OBJID*)pdata0);
      pnode = pnode->next;
   }
   return 0;
}

int ASN1C_ATTRCERT_AttrSpec::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_AttrSpec (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_AttrSpec::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_AAControls (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_AAControls* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   if (pvalue->m.pathLenConstraintPresent) {
      rtPrintToStreamIndent (pctxt);
      rtPrintToStreamUnsigned (pctxt, "pathLenConstraint", pvalue->pathLenConstraint);
   }

   if (pvalue->m.permittedAttrsPresent) {
      asn1PrtToStrm_ATTRCERT_AttrSpec (pctxt, "permittedAttrs", &pvalue->permittedAttrs);
   }

   if (pvalue->m.excludedAttrsPresent) {
      asn1PrtToStrm_ATTRCERT_AttrSpec (pctxt, "excludedAttrs", &pvalue->excludedAttrs);
   }

   rtPrintToStreamIndent (pctxt);
   rtPrintToStreamBoolean (pctxt, "permitUnSpecified", pvalue->permitUnSpecified);

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_AAControls::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_AAControls (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_AAControls::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_ACClearAttrs (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_ACClearAttrs* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   asn1PrtToStrm_IMP_GeneralName (pctxt, "acIssuer", &pvalue->acIssuer);

   rtPrintToStreamIndent (pctxt);
   rtPrintToStreamInteger (pctxt, "acSerial", pvalue->acSerial);

   asn1PrtToStrm_ATTRCERT__SeqOfATTRCERT_Attribute (pctxt, "attrs", &pvalue->attrs);

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_ACClearAttrs::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_ACClearAttrs (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_ACClearAttrs::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_ProxyInfo (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_ProxyInfo* pvalue)
{
   ASN1T_ATTRCERT_Targets* pdata0;
   OSRTDListNode* pnode;
   char namebuf[512], numbuf[32];
   OSUINT32 xx1;

   pnode = pvalue->head;
   for (xx1 = 0; xx1 < pvalue->count; xx1++) {
      pdata0 = (ASN1T_ATTRCERT_Targets*) pnode->data;
      rtxUIntToCharStr (xx1, numbuf, sizeof(numbuf), 0);
      rtxStrJoin (namebuf, sizeof(namebuf), name, "[", numbuf, "]", 0);
      asn1PrtToStrm_ATTRCERT_Targets (pctxt, namebuf, pdata0);
      pnode = pnode->next;
   }
   return 0;
}

int ASN1C_ATTRCERT_ProxyInfo::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_ProxyInfo (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_ProxyInfo::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_AttributeCertificateInfoV1_subject (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_AttributeCertificateInfoV1_subject* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   switch (pvalue->t)
   {
      /* baseCertificateID */
      case 1:
      {
         asn1PrtToStrm_ATTRCERT_IssuerSerial (pctxt, "baseCertificateID", pvalue->u.baseCertificateID);
         break;
      }
      /* subjectName */
      case 2:
      {
         asn1PrtToStrm_IMP_GeneralNames (pctxt, "subjectName", pvalue->u.subjectName);
         break;
      }
      default:;
   }

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_AttributeCertificateInfoV1_subject::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_AttributeCertificateInfoV1_subject (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_AttributeCertificateInfoV1_subject::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_AttributeCertificateInfoV1 (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_AttributeCertificateInfoV1* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   asn1PrtToStrm_ATTRCERT_AttCertVersionV1 (pctxt, "version", &pvalue->version);

   asn1PrtToStrm_ATTRCERT_AttributeCertificateInfoV1_subject (pctxt, "subject", &pvalue->subject);

   asn1PrtToStrm_IMP_GeneralNames (pctxt, "issuer", &pvalue->issuer);

   asn1PrtToStrm_EXP_AlgorithmIdentifier (pctxt, "signature_", &pvalue->signature_);

   asn1PrtToStrm_EXP_CertificateSerialNumber (pctxt, "serialNumber", &pvalue->serialNumber);

   asn1PrtToStrm_ATTRCERT_AttCertValidityPeriod (pctxt, "attCertValidityPeriod", &pvalue->attCertValidityPeriod);

   asn1PrtToStrm_ATTRCERT__SeqOfATTRCERT_Attribute (pctxt, "attributes", &pvalue->attributes);

   if (pvalue->m.issuerUniqueIDPresent) {
      asn1PrtToStrm_EXP_UniqueIdentifier (pctxt, "issuerUniqueID", &pvalue->issuerUniqueID);
   }

   if (pvalue->m.extensionsPresent) {
      asn1PrtToStrm_EXP_Extensions (pctxt, "extensions", &pvalue->extensions);
   }

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_AttributeCertificateInfoV1::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_AttributeCertificateInfoV1 (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_AttributeCertificateInfoV1::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_ATTRCERT_AttributeCertificateV1 (OSCTXT *pctxt, 
   const char* name, const ASN1T_ATTRCERT_AttributeCertificateV1* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   asn1PrtToStrm_ATTRCERT_AttributeCertificateInfoV1 (pctxt, "acInfo", &pvalue->acInfo);

   asn1PrtToStrm_EXP_AlgorithmIdentifier (pctxt, "signatureAlgorithm", &pvalue->signatureAlgorithm);

   rtPrintToStreamIndent (pctxt);
   rtPrintToStreamBitStrBraceText (pctxt, "signature_", pvalue->signature_.numbits, pvalue->signature_.data);

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_ATTRCERT_AttributeCertificateV1::toStream (const char* name)

{
   asn1PrtToStrm_ATTRCERT_AttributeCertificateV1 (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ATTRCERT_AttributeCertificateV1::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

