/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.6, Date: 20-Feb-2014.
 */
#include "pkcs10.h"
#include "rtsrc/rtPrintToStream.h"
#include "rtxsrc/rtxCommon.h"

int asn1PrtToStrm_PKCS10_Attributes (OSCTXT *pctxt, 
   const char* name, const ASN1T_PKCS10_Attributes* pvalue)
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

int ASN1C_PKCS10_Attributes::toStream (const char* name)

{
   asn1PrtToStrm_PKCS10_Attributes (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_PKCS10_Attributes::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_PKCS10_CertificationRequestInfo (OSCTXT *pctxt, 
   const char* name, const ASN1T_PKCS10_CertificationRequestInfo* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   rtPrintToStreamIndent (pctxt);
   rtPrintToStreamInteger (pctxt, "version", pvalue->version);

   asn1PrtToStrm_EXP_Name (pctxt, "subject", &pvalue->subject);

   asn1PrtToStrm_EXP_SubjectPublicKeyInfo (pctxt, "subjectPKInfo", &pvalue->subjectPKInfo);

   asn1PrtToStrm_PKCS10_Attributes (pctxt, "attributes", &pvalue->attributes);

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_PKCS10_CertificationRequestInfo::toStream (const char* name)

{
   asn1PrtToStrm_PKCS10_CertificationRequestInfo (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_PKCS10_CertificationRequestInfo::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

int asn1PrtToStrm_PKCS10_CertificationRequest (OSCTXT *pctxt, 
   const char* name, const ASN1T_PKCS10_CertificationRequest* pvalue)
{
   rtPrintToStreamOpenBrace (pctxt, name);

   asn1PrtToStrm_PKCS10_CertificationRequestInfo (pctxt, "certificationRequestInfo", &pvalue->certificationRequestInfo);

   asn1PrtToStrm_EXP_AlgorithmIdentifier (pctxt, "signatureAlgorithm", &pvalue->signatureAlgorithm);

   rtPrintToStreamIndent (pctxt);
   rtPrintToStreamBitStrBraceText (pctxt, "signature_", pvalue->signature_.numbits, pvalue->signature_.data);

   rtPrintToStreamCloseBrace (pctxt);
   return 0;
}

int ASN1C_PKCS10_CertificationRequest::toStream (const char* name)

{
   asn1PrtToStrm_PKCS10_CertificationRequest (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_PKCS10_CertificationRequest::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

