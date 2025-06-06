/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.6, Date: 28-Feb-2014.
 */
#include "crmf.h"
#include "rtsrc/rtCompare.h"
#include "rtxsrc/rtxCommon.h"

OSBOOL asn1Compare_CRMF_SubsequentMessage (const char* name, 
   ASN1T_CRMF_SubsequentMessage* pValue, 
   ASN1T_CRMF_SubsequentMessage* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   return rtCmpInteger (name, *pValue, *pCmpValue, errBuff, errBuffSize);
}

OSBOOL ASN1C_CRMF_SubsequentMessage::Equals (ASN1T_CRMF_SubsequentMessage* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_SubsequentMessage ("SubsequentMessage", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_PKIPublicationInfo_action (const char* name, 
   ASN1T_CRMF_PKIPublicationInfo_action* pValue, 
   ASN1T_CRMF_PKIPublicationInfo_action* pCmpValue, char* errBuff, 
   OSSIZE errBuffSize)
{
   return rtCmpInteger (name, *pValue, *pCmpValue, errBuff, errBuffSize);
}

OSBOOL ASN1C_CRMF_PKIPublicationInfo_action::Equals (ASN1T_CRMF_PKIPublicationInfo_action* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_PKIPublicationInfo_action ("PKIPublicationInfo_action", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_SinglePubInfo_pubMethod (const char* name, 
   ASN1T_CRMF_SinglePubInfo_pubMethod* pValue, 
   ASN1T_CRMF_SinglePubInfo_pubMethod* pCmpValue, char* errBuff, 
   OSSIZE errBuffSize)
{
   return rtCmpInteger (name, *pValue, *pCmpValue, errBuff, errBuffSize);
}

OSBOOL ASN1C_CRMF_SinglePubInfo_pubMethod::Equals (ASN1T_CRMF_SinglePubInfo_pubMethod* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_SinglePubInfo_pubMethod ("SinglePubInfo_pubMethod", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_OptionalValidity (const char* name, 
   ASN1T_CRMF_OptionalValidity* pValue, 
   ASN1T_CRMF_OptionalValidity* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   rtxStrJoin (namebuf, 512, name, ".notBefore", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.notBeforePresent,
   pCmpValue->m.notBeforePresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.notBeforePresent) 
   {
      if (! asn1Compare_EXP_Time (namebuf, &pValue->notBefore, &pCmpValue->notBefore, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".notAfter", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.notAfterPresent,
   pCmpValue->m.notAfterPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.notAfterPresent) 
   {
      if (! asn1Compare_EXP_Time (namebuf, &pValue->notAfter, &pCmpValue->notAfter, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   return retval;
}

OSBOOL ASN1C_CRMF_OptionalValidity::Equals (ASN1T_CRMF_OptionalValidity* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_OptionalValidity ("OptionalValidity", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_CertTemplate (const char* name, 
   ASN1T_CRMF_CertTemplate* pValue, ASN1T_CRMF_CertTemplate* pCmpValue, 
   char* errBuff, OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   rtxStrJoin (namebuf, 512, name, ".version", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.versionPresent,
   pCmpValue->m.versionPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.versionPresent) 
   {
      if (! asn1Compare_EXP_Version (namebuf, &pValue->version, &pCmpValue->version, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".serialNumber", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.serialNumberPresent,
   pCmpValue->m.serialNumberPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.serialNumberPresent) 
   {
      if (! asn1Compare_EXP_CertificateSerialNumber (namebuf, &pValue->serialNumber, &pCmpValue->serialNumber, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".signingAlg", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.signingAlgPresent,
   pCmpValue->m.signingAlgPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.signingAlgPresent) 
   {
      if (! asn1Compare_EXP_AlgorithmIdentifier (namebuf, &pValue->signingAlg, &pCmpValue->signingAlg, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".issuer", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.issuerPresent,
   pCmpValue->m.issuerPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.issuerPresent) 
   {
      if (! asn1Compare_EXP_Name (namebuf, &pValue->issuer, &pCmpValue->issuer, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".validity", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.validityPresent,
   pCmpValue->m.validityPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.validityPresent) 
   {
      if (! asn1Compare_CRMF_OptionalValidity (namebuf, &pValue->validity, &pCmpValue->validity, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".subject", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.subjectPresent,
   pCmpValue->m.subjectPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.subjectPresent) 
   {
      if (! asn1Compare_EXP_Name (namebuf, &pValue->subject, &pCmpValue->subject, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".publicKey", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.publicKeyPresent,
   pCmpValue->m.publicKeyPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.publicKeyPresent) 
   {
      if (! asn1Compare_EXP_SubjectPublicKeyInfo (namebuf, &pValue->publicKey, &pCmpValue->publicKey, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".issuerUID", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.issuerUIDPresent,
   pCmpValue->m.issuerUIDPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.issuerUIDPresent) 
   {
      if (! asn1Compare_EXP_UniqueIdentifier (namebuf, &pValue->issuerUID, &pCmpValue->issuerUID, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".subjectUID", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.subjectUIDPresent,
   pCmpValue->m.subjectUIDPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.subjectUIDPresent) 
   {
      if (! asn1Compare_EXP_UniqueIdentifier (namebuf, &pValue->subjectUID, &pCmpValue->subjectUID, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".extensions", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.extensionsPresent,
   pCmpValue->m.extensionsPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.extensionsPresent) 
   {
      if (! asn1Compare_EXP_Extensions (namebuf, &pValue->extensions, &pCmpValue->extensions, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   return retval;
}

OSBOOL ASN1C_CRMF_CertTemplate::Equals (ASN1T_CRMF_CertTemplate* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_CertTemplate ("CertTemplate", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_SinglePubInfo (const char* name, 
   ASN1T_CRMF_SinglePubInfo* pValue, ASN1T_CRMF_SinglePubInfo* pCmpValue, 
   char* errBuff, OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   rtxStrJoin (namebuf, 512, name, ".pubMethod", 0, 0, 0);
   if (! asn1Compare_CRMF_SinglePubInfo_pubMethod (namebuf, &pValue->pubMethod, &pCmpValue->pubMethod, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   rtxStrJoin (namebuf, 512, name, ".pubLocation", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.pubLocationPresent,
   pCmpValue->m.pubLocationPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.pubLocationPresent) 
   {
      if (! asn1Compare_IMP_GeneralName (namebuf, &pValue->pubLocation, &pCmpValue->pubLocation, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   return retval;
}

OSBOOL ASN1C_CRMF_SinglePubInfo::Equals (ASN1T_CRMF_SinglePubInfo* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_SinglePubInfo ("SinglePubInfo", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_PKIPublicationInfo_pubInfos (const char* name, 
   ASN1T_CRMF_PKIPublicationInfo_pubInfos* pValue, 
   ASN1T_CRMF_PKIPublicationInfo_pubInfos* pCmpValue, char* errBuff, 
   OSSIZE errBuffSize)
{
   ASN1T_CRMF_SinglePubInfo* pdata;
   ASN1T_CRMF_SinglePubInfo* pCmpdata;
   OSRTDListNode* pnode;
   OSRTDListNode* pCmpnode;
   char namebuf[512];
   OSBOOL retval = TRUE;

   if (!rtCmpSeqOfElements (name, pValue->count, pCmpValue->count, errBuff, errBuffSize))
   {
      return FALSE;
   }

   {
   OSUINT32 xx1;
   pnode = pValue->head;
   pCmpnode = pCmpValue->head;
   for (xx1 = 0; xx1 < pValue->count; xx1++) {
      pdata = (ASN1T_CRMF_SinglePubInfo*) pnode->data;
      pCmpdata = (ASN1T_CRMF_SinglePubInfo*) pCmpnode->data;
      { char numbuf[32];
      rtxIntToCharStr (xx1, numbuf, 32, 0);
      rtxStrJoin (namebuf, 512, name, ".elem[", numbuf, "]", 0);
      if (! asn1Compare_CRMF_SinglePubInfo (namebuf, pdata, pCmpdata, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
      }
      pnode = pnode->next;
      pCmpnode = pCmpnode->next;
   }
   }

   return retval;
}

OSBOOL ASN1C_CRMF_PKIPublicationInfo_pubInfos::Equals (ASN1T_CRMF_PKIPublicationInfo_pubInfos* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_PKIPublicationInfo_pubInfos ("PKIPublicationInfo_pubInfos", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_PKIPublicationInfo (const char* name, 
   ASN1T_CRMF_PKIPublicationInfo* pValue, 
   ASN1T_CRMF_PKIPublicationInfo* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   rtxStrJoin (namebuf, 512, name, ".action", 0, 0, 0);
   if (! asn1Compare_CRMF_PKIPublicationInfo_action (namebuf, &pValue->action, &pCmpValue->action, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   rtxStrJoin (namebuf, 512, name, ".pubInfos", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.pubInfosPresent,
   pCmpValue->m.pubInfosPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.pubInfosPresent) 
   {
      if (! asn1Compare_CRMF_PKIPublicationInfo_pubInfos (namebuf, &pValue->pubInfos, &pCmpValue->pubInfos, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   return retval;
}

OSBOOL ASN1C_CRMF_PKIPublicationInfo::Equals (ASN1T_CRMF_PKIPublicationInfo* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_PKIPublicationInfo ("PKIPublicationInfo", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_EncryptedValue (const char* name, 
   ASN1T_CRMF_EncryptedValue* pValue, ASN1T_CRMF_EncryptedValue* pCmpValue, 
   char* errBuff, OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   rtxStrJoin (namebuf, 512, name, ".intendedAlg", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.intendedAlgPresent,
   pCmpValue->m.intendedAlgPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.intendedAlgPresent) 
   {
      if (! asn1Compare_EXP_AlgorithmIdentifier (namebuf, &pValue->intendedAlg, &pCmpValue->intendedAlg, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".symmAlg", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.symmAlgPresent,
   pCmpValue->m.symmAlgPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.symmAlgPresent) 
   {
      if (! asn1Compare_EXP_AlgorithmIdentifier (namebuf, &pValue->symmAlg, &pCmpValue->symmAlg, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".encSymmKey", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.encSymmKeyPresent,
   pCmpValue->m.encSymmKeyPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.encSymmKeyPresent) 
   {
      if (!rtCmpBitStr (namebuf, pValue->encSymmKey.numbits, 
         pValue->encSymmKey.data, pCmpValue->encSymmKey.numbits, 
         pCmpValue->encSymmKey.data, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".keyAlg", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.keyAlgPresent,
   pCmpValue->m.keyAlgPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.keyAlgPresent) 
   {
      if (! asn1Compare_EXP_AlgorithmIdentifier (namebuf, &pValue->keyAlg, &pCmpValue->keyAlg, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".valueHint", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.valueHintPresent,
   pCmpValue->m.valueHintPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.valueHintPresent) 
   {
      if ( !rtCmpOctStr (namebuf, pValue->valueHint.numocts, 
         pValue->valueHint.data, pCmpValue->valueHint.numocts, 
         pCmpValue->valueHint.data, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".encValue", 0, 0, 0);
   if (!rtCmpBitStr (namebuf, pValue->encValue.numbits, pValue->encValue.data, 
      pCmpValue->encValue.numbits, pCmpValue->encValue.data, 
      errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   return retval;
}

OSBOOL ASN1C_CRMF_EncryptedValue::Equals (ASN1T_CRMF_EncryptedValue* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_EncryptedValue ("EncryptedValue", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_CertId (const char* name, ASN1T_CRMF_CertId* pValue, 
   ASN1T_CRMF_CertId* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   rtxStrJoin (namebuf, 512, name, ".issuer", 0, 0, 0);
   if (! asn1Compare_IMP_GeneralName (namebuf, &pValue->issuer, &pCmpValue->issuer, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   rtxStrJoin (namebuf, 512, name, ".serialNumber", 0, 0, 0);
   if (! asn1Compare_EXP_CertificateSerialNumber (namebuf, &pValue->serialNumber, &pCmpValue->serialNumber, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   return retval;
}

OSBOOL ASN1C_CRMF_CertId::Equals (ASN1T_CRMF_CertId* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_CertId ("CertId", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_Controls (const char* name, 
   ASN1T_CRMF_Controls* pValue, ASN1T_CRMF_Controls* pCmpValue, char* errBuff, 
   OSSIZE errBuffSize)
{
   ASN1T_EXP_AttributeTypeAndValue* pdata;
   ASN1T_EXP_AttributeTypeAndValue* pCmpdata;
   OSRTDListNode* pnode;
   OSRTDListNode* pCmpnode;
   char namebuf[512];
   OSBOOL retval = TRUE;

   if (!rtCmpSeqOfElements (name, pValue->count, pCmpValue->count, errBuff, errBuffSize))
   {
      return FALSE;
   }

   {
   OSUINT32 xx1;
   pnode = pValue->head;
   pCmpnode = pCmpValue->head;
   for (xx1 = 0; xx1 < pValue->count; xx1++) {
      pdata = (ASN1T_EXP_AttributeTypeAndValue*) pnode->data;
      pCmpdata = (ASN1T_EXP_AttributeTypeAndValue*) pCmpnode->data;
      { char numbuf[32];
      rtxIntToCharStr (xx1, numbuf, 32, 0);
      rtxStrJoin (namebuf, 512, name, ".elem[", numbuf, "]", 0);
      if (! asn1Compare_EXP_AttributeTypeAndValue (namebuf, pdata, pCmpdata, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
      }
      pnode = pnode->next;
      pCmpnode = pCmpnode->next;
   }
   }

   return retval;
}

OSBOOL ASN1C_CRMF_Controls::Equals (ASN1T_CRMF_Controls* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_Controls ("Controls", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_CertRequest (const char* name, 
   ASN1T_CRMF_CertRequest* pValue, ASN1T_CRMF_CertRequest* pCmpValue, 
   char* errBuff, OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   rtxStrJoin (namebuf, 512, name, ".certReqId", 0, 0, 0);
   if ( !rtCmpInteger (namebuf, pValue->certReqId, pCmpValue->certReqId, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   rtxStrJoin (namebuf, 512, name, ".certTemplate", 0, 0, 0);
   if (! asn1Compare_CRMF_CertTemplate (namebuf, &pValue->certTemplate, &pCmpValue->certTemplate, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   rtxStrJoin (namebuf, 512, name, ".controls", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.controlsPresent,
   pCmpValue->m.controlsPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.controlsPresent) 
   {
      if (! asn1Compare_CRMF_Controls (namebuf, &pValue->controls, &pCmpValue->controls, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   return retval;
}

OSBOOL ASN1C_CRMF_CertRequest::Equals (ASN1T_CRMF_CertRequest* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_CertRequest ("CertRequest", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_PKMACValue (const char* name, 
   ASN1T_CRMF_PKMACValue* pValue, ASN1T_CRMF_PKMACValue* pCmpValue, 
   char* errBuff, OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   rtxStrJoin (namebuf, 512, name, ".algId", 0, 0, 0);
   if (! asn1Compare_EXP_AlgorithmIdentifier (namebuf, &pValue->algId, &pCmpValue->algId, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   rtxStrJoin (namebuf, 512, name, ".value", 0, 0, 0);
   if (!rtCmpBitStr (namebuf, pValue->value.numbits, pValue->value.data, 
      pCmpValue->value.numbits, pCmpValue->value.data, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   return retval;
}

OSBOOL ASN1C_CRMF_PKMACValue::Equals (ASN1T_CRMF_PKMACValue* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_PKMACValue ("PKMACValue", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_POPOSigningKeyInput_authInfo (const char* name, 
   ASN1T_CRMF_POPOSigningKeyInput_authInfo* pValue, 
   ASN1T_CRMF_POPOSigningKeyInput_authInfo* pCmpValue, char* errBuff, 
   OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   if ( !rtCmpTag (name,
   pValue->t,
   pCmpValue->t, errBuff, errBuffSize))
   {
      return FALSE;
   }
   switch (pValue->t)
   {
      /* publicKeyMAC */
      case 1:
         rtxStrJoin (namebuf, 512, name, ".u.publicKeyMAC", 0, 0, 0);
         if (! asn1Compare_CRMF_PKMACValue (namebuf, pValue->u.publicKeyMAC, pCmpValue->u.publicKeyMAC, errBuff, errBuffSize)) 
         {
            retval = FALSE;
         }
         break;

      /* sender */
      case 2:
         rtxStrJoin (namebuf, 512, name, ".u.sender", 0, 0, 0);
         if (! asn1Compare_IMP_GeneralName (namebuf, pValue->u.sender, pCmpValue->u.sender, errBuff, errBuffSize)) 
         {
            retval = FALSE;
         }
         break;

      default:;
   }
   return retval;
}

OSBOOL ASN1C_CRMF_POPOSigningKeyInput_authInfo::Equals (ASN1T_CRMF_POPOSigningKeyInput_authInfo* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_POPOSigningKeyInput_authInfo ("POPOSigningKeyInput_authInfo", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_POPOSigningKeyInput (const char* name, 
   ASN1T_CRMF_POPOSigningKeyInput* pValue, 
   ASN1T_CRMF_POPOSigningKeyInput* pCmpValue, char* errBuff, 
   OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   rtxStrJoin (namebuf, 512, name, ".authInfo", 0, 0, 0);
   if (! asn1Compare_CRMF_POPOSigningKeyInput_authInfo (namebuf, &pValue->authInfo, &pCmpValue->authInfo, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   rtxStrJoin (namebuf, 512, name, ".publicKey", 0, 0, 0);
   if (! asn1Compare_EXP_SubjectPublicKeyInfo (namebuf, &pValue->publicKey, &pCmpValue->publicKey, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   return retval;
}

OSBOOL ASN1C_CRMF_POPOSigningKeyInput::Equals (ASN1T_CRMF_POPOSigningKeyInput* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_POPOSigningKeyInput ("POPOSigningKeyInput", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_POPOSigningKey (const char* name, 
   ASN1T_CRMF_POPOSigningKey* pValue, ASN1T_CRMF_POPOSigningKey* pCmpValue, 
   char* errBuff, OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   rtxStrJoin (namebuf, 512, name, ".poposkInput", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.poposkInputPresent,
   pCmpValue->m.poposkInputPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.poposkInputPresent) 
   {
      if (! asn1Compare_CRMF_POPOSigningKeyInput (namebuf, &pValue->poposkInput, &pCmpValue->poposkInput, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".algorithmIdentifier", 0, 0, 0);
   if (! asn1Compare_EXP_AlgorithmIdentifier (namebuf, &pValue->algorithmIdentifier, &pCmpValue->algorithmIdentifier, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   rtxStrJoin (namebuf, 512, name, ".signature_", 0, 0, 0);
   if (!rtCmpBitStr (namebuf, pValue->signature_.numbits, 
      pValue->signature_.data, pCmpValue->signature_.numbits, 
      pCmpValue->signature_.data, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   return retval;
}

OSBOOL ASN1C_CRMF_POPOSigningKey::Equals (ASN1T_CRMF_POPOSigningKey* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_POPOSigningKey ("POPOSigningKey", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_POPOPrivKey (const char* name, 
   ASN1T_CRMF_POPOPrivKey* pValue, ASN1T_CRMF_POPOPrivKey* pCmpValue, 
   char* errBuff, OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   if ( !rtCmpTag (name,
   pValue->t,
   pCmpValue->t, errBuff, errBuffSize))
   {
      return FALSE;
   }
   switch (pValue->t)
   {
      /* thisMessage */
      case 1:
         rtxStrJoin (namebuf, 512, name, ".u.thisMessage", 0, 0, 0);
         if (!rtCmpBitStr (namebuf, pValue->u.thisMessage->numbits, 
            pValue->u.thisMessage->data, pCmpValue->u.thisMessage->numbits, 
            pCmpValue->u.thisMessage->data, errBuff, errBuffSize)) 
         {
            retval = FALSE;
         }
         break;

      /* subsequentMessage */
      case 2:
         rtxStrJoin (namebuf, 512, name, ".u.subsequentMessage", 0, 0, 0);
         if (! asn1Compare_CRMF_SubsequentMessage (namebuf, &pValue->u.subsequentMessage, &pCmpValue->u.subsequentMessage, errBuff, errBuffSize)) 
         {
            retval = FALSE;
         }
         break;

      /* dhMAC */
      case 3:
         rtxStrJoin (namebuf, 512, name, ".u.dhMAC", 0, 0, 0);
         if (!rtCmpBitStr (namebuf, pValue->u.dhMAC->numbits, 
            pValue->u.dhMAC->data, pCmpValue->u.dhMAC->numbits, 
            pCmpValue->u.dhMAC->data, errBuff, errBuffSize)) 
         {
            retval = FALSE;
         }
         break;

      default:;
   }
   return retval;
}

OSBOOL ASN1C_CRMF_POPOPrivKey::Equals (ASN1T_CRMF_POPOPrivKey* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_POPOPrivKey ("POPOPrivKey", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_ProofOfPossession (const char* name, 
   ASN1T_CRMF_ProofOfPossession* pValue, 
   ASN1T_CRMF_ProofOfPossession* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   if ( !rtCmpTag (name,
   pValue->t,
   pCmpValue->t, errBuff, errBuffSize))
   {
      return FALSE;
   }
   switch (pValue->t)
   {
      /* raVerified */
      case 1:
         rtxStrJoin (namebuf, 512, name, ".u.raVerified", 0, 0, 0);
         break;

      /* signature */
      case 2:
         rtxStrJoin (namebuf, 512, name, ".u.signature_", 0, 0, 0);
         if (! asn1Compare_CRMF_POPOSigningKey (namebuf, pValue->u.signature_, pCmpValue->u.signature_, errBuff, errBuffSize)) 
         {
            retval = FALSE;
         }
         break;

      /* keyEncipherment */
      case 3:
         rtxStrJoin (namebuf, 512, name, ".u.keyEncipherment", 0, 0, 0);
         if (! asn1Compare_CRMF_POPOPrivKey (namebuf, pValue->u.keyEncipherment, pCmpValue->u.keyEncipherment, errBuff, errBuffSize)) 
         {
            retval = FALSE;
         }
         break;

      /* keyAgreement */
      case 4:
         rtxStrJoin (namebuf, 512, name, ".u.keyAgreement", 0, 0, 0);
         if (! asn1Compare_CRMF_POPOPrivKey (namebuf, pValue->u.keyAgreement, pCmpValue->u.keyAgreement, errBuff, errBuffSize)) 
         {
            retval = FALSE;
         }
         break;

      default:;
   }
   return retval;
}

OSBOOL ASN1C_CRMF_ProofOfPossession::Equals (ASN1T_CRMF_ProofOfPossession* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_ProofOfPossession ("ProofOfPossession", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_CertReqMsg_regInfo (const char* name, 
   ASN1T_CRMF_CertReqMsg_regInfo* pValue, 
   ASN1T_CRMF_CertReqMsg_regInfo* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   ASN1T_EXP_AttributeTypeAndValue* pdata;
   ASN1T_EXP_AttributeTypeAndValue* pCmpdata;
   OSRTDListNode* pnode;
   OSRTDListNode* pCmpnode;
   char namebuf[512];
   OSBOOL retval = TRUE;

   if (!rtCmpSeqOfElements (name, pValue->count, pCmpValue->count, errBuff, errBuffSize))
   {
      return FALSE;
   }

   {
   OSUINT32 xx1;
   pnode = pValue->head;
   pCmpnode = pCmpValue->head;
   for (xx1 = 0; xx1 < pValue->count; xx1++) {
      pdata = (ASN1T_EXP_AttributeTypeAndValue*) pnode->data;
      pCmpdata = (ASN1T_EXP_AttributeTypeAndValue*) pCmpnode->data;
      { char numbuf[32];
      rtxIntToCharStr (xx1, numbuf, 32, 0);
      rtxStrJoin (namebuf, 512, name, ".elem[", numbuf, "]", 0);
      if (! asn1Compare_EXP_AttributeTypeAndValue (namebuf, pdata, pCmpdata, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
      }
      pnode = pnode->next;
      pCmpnode = pCmpnode->next;
   }
   }

   return retval;
}

OSBOOL ASN1C_CRMF_CertReqMsg_regInfo::Equals (ASN1T_CRMF_CertReqMsg_regInfo* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_CertReqMsg_regInfo ("CertReqMsg_regInfo", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_CertReqMsg (const char* name, 
   ASN1T_CRMF_CertReqMsg* pValue, ASN1T_CRMF_CertReqMsg* pCmpValue, 
   char* errBuff, OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   rtxStrJoin (namebuf, 512, name, ".certReq", 0, 0, 0);
   if (! asn1Compare_CRMF_CertRequest (namebuf, &pValue->certReq, &pCmpValue->certReq, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   rtxStrJoin (namebuf, 512, name, ".pop", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.popPresent,
   pCmpValue->m.popPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.popPresent) 
   {
      if (! asn1Compare_CRMF_ProofOfPossession (namebuf, &pValue->pop, &pCmpValue->pop, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   rtxStrJoin (namebuf, 512, name, ".regInfo", 0, 0, 0);
   if (!rtCmpOptional (namebuf,
   pValue->m.regInfoPresent,
   pCmpValue->m.regInfoPresent, errBuff, errBuffSize))
   {
      retval = FALSE;
   }
   else if(pValue->m.regInfoPresent) 
   {
      if (! asn1Compare_CRMF_CertReqMsg_regInfo (namebuf, &pValue->regInfo, &pCmpValue->regInfo, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
   }

   return retval;
}

OSBOOL ASN1C_CRMF_CertReqMsg::Equals (ASN1T_CRMF_CertReqMsg* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_CertReqMsg ("CertReqMsg", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_CRMF_CertReqMessages (const char* name, 
   ASN1T_CRMF_CertReqMessages* pValue, ASN1T_CRMF_CertReqMessages* pCmpValue, 
   char* errBuff, OSSIZE errBuffSize)
{
   ASN1T_CRMF_CertReqMsg* pdata;
   ASN1T_CRMF_CertReqMsg* pCmpdata;
   OSRTDListNode* pnode;
   OSRTDListNode* pCmpnode;
   char namebuf[512];
   OSBOOL retval = TRUE;

   if (!rtCmpSeqOfElements (name, pValue->count, pCmpValue->count, errBuff, errBuffSize))
   {
      return FALSE;
   }

   {
   OSUINT32 xx1;
   pnode = pValue->head;
   pCmpnode = pCmpValue->head;
   for (xx1 = 0; xx1 < pValue->count; xx1++) {
      pdata = (ASN1T_CRMF_CertReqMsg*) pnode->data;
      pCmpdata = (ASN1T_CRMF_CertReqMsg*) pCmpnode->data;
      { char numbuf[32];
      rtxIntToCharStr (xx1, numbuf, 32, 0);
      rtxStrJoin (namebuf, 512, name, ".elem[", numbuf, "]", 0);
      if (! asn1Compare_CRMF_CertReqMsg (namebuf, pdata, pCmpdata, errBuff, errBuffSize)) 
      {
         retval = FALSE;
      }
      }
      pnode = pnode->next;
      pCmpnode = pCmpnode->next;
   }
   }

   return retval;
}

OSBOOL ASN1C_CRMF_CertReqMessages::Equals (ASN1T_CRMF_CertReqMessages* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_CRMF_CertReqMessages ("CertReqMessages", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

