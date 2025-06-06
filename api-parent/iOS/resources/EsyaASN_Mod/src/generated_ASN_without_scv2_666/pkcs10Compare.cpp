/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.6, Date: 20-Feb-2014.
 */
#include "pkcs10.h"
#include "rtsrc/rtCompare.h"
#include "rtxsrc/rtxCommon.h"

OSBOOL asn1Compare_PKCS10_Attributes (const char* name, 
   ASN1T_PKCS10_Attributes* pValue, ASN1T_PKCS10_Attributes* pCmpValue, 
   char* errBuff, OSSIZE errBuffSize)
{
   ASN1T_EXP_Attribute* pdata;
   ASN1T_EXP_Attribute* pCmpdata;
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
      pdata = (ASN1T_EXP_Attribute*) pnode->data;
      pCmpdata = (ASN1T_EXP_Attribute*) pCmpnode->data;
      { char numbuf[32];
      rtxIntToCharStr (xx1, numbuf, 32, 0);
      rtxStrJoin (namebuf, 512, name, ".elem[", numbuf, "]", 0);
      if (! asn1Compare_EXP_Attribute (namebuf, pdata, pCmpdata, errBuff, errBuffSize)) 
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

OSBOOL ASN1C_PKCS10_Attributes::Equals (ASN1T_PKCS10_Attributes* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_PKCS10_Attributes ("Attributes", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_PKCS10_CertificationRequestInfo (const char* name, 
   ASN1T_PKCS10_CertificationRequestInfo* pValue, 
   ASN1T_PKCS10_CertificationRequestInfo* pCmpValue, char* errBuff, 
   OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   rtxStrJoin (namebuf, 512, name, ".version", 0, 0, 0);
   if ( !rtCmpInteger (namebuf, pValue->version, pCmpValue->version, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   rtxStrJoin (namebuf, 512, name, ".subject", 0, 0, 0);
   if (! asn1Compare_EXP_Name (namebuf, &pValue->subject, &pCmpValue->subject, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   rtxStrJoin (namebuf, 512, name, ".subjectPKInfo", 0, 0, 0);
   if (! asn1Compare_EXP_SubjectPublicKeyInfo (namebuf, &pValue->subjectPKInfo, &pCmpValue->subjectPKInfo, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   rtxStrJoin (namebuf, 512, name, ".attributes", 0, 0, 0);
   if (! asn1Compare_PKCS10_Attributes (namebuf, &pValue->attributes, &pCmpValue->attributes, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   return retval;
}

OSBOOL ASN1C_PKCS10_CertificationRequestInfo::Equals (ASN1T_PKCS10_CertificationRequestInfo* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_PKCS10_CertificationRequestInfo ("CertificationRequestInfo", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

OSBOOL asn1Compare_PKCS10_CertificationRequest (const char* name, 
   ASN1T_PKCS10_CertificationRequest* pValue, 
   ASN1T_PKCS10_CertificationRequest* pCmpValue, char* errBuff, 
   OSSIZE errBuffSize)
{
   char namebuf[512];
   OSBOOL retval = TRUE;

   rtxStrJoin (namebuf, 512, name, ".certificationRequestInfo", 0, 0, 0);
   if (! asn1Compare_PKCS10_CertificationRequestInfo (namebuf, &pValue->certificationRequestInfo, &pCmpValue->certificationRequestInfo, errBuff, errBuffSize)) 
   {
      retval = FALSE;
   }

   rtxStrJoin (namebuf, 512, name, ".signatureAlgorithm", 0, 0, 0);
   if (! asn1Compare_EXP_AlgorithmIdentifier (namebuf, &pValue->signatureAlgorithm, &pCmpValue->signatureAlgorithm, errBuff, errBuffSize)) 
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

OSBOOL ASN1C_PKCS10_CertificationRequest::Equals (ASN1T_PKCS10_CertificationRequest* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_PKCS10_CertificationRequest ("CertificationRequest", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

