/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.6, Date: 20-Feb-2014.
 */
#include <new>
#include "dercms.h"
#include "rtsrc/rtCopy.h"
#include "rtxsrc/rtxCommon.h"

void asn1Copy_DERCMS_SignatureValue (OSCTXT* pctxt, 
   ASN1T_DERCMS_SignatureValue* pSrcValue, ASN1T_DERCMS_SignatureValue*
    pDstValue)
{
   if (pSrcValue == pDstValue) return;

   rtCopyDynOctStr (pctxt, pSrcValue, pDstValue);
}

ASN1C_DERCMS_SignatureValue::ASN1C_DERCMS_SignatureValue (ASN1C_DERCMS_SignatureValue& original) :
   ASN1CType(original), msgData(original.getCopy())
{
}

ASN1T_DERCMS_SignatureValue& ASN1C_DERCMS_SignatureValue::getCopy (ASN1T_DERCMS_SignatureValue* pDstData)
{
   if (&msgData == pDstData) return *pDstData;

   OSCTXT* pctxt = getCtxtPtr();

   if (pDstData == 0) {
      pDstData = rtxMemAllocType (pctxt, ASN1T_DERCMS_SignatureValue);
      new (pDstData) ASN1T_DERCMS_SignatureValue;
   }

   asn1Copy_DERCMS_SignatureValue (pctxt, &msgData, pDstData);

   return *pDstData;
}

ASN1T_DERCMS_SignatureValue* ASN1C_DERCMS_SignatureValue::newCopy ()
{
   OSCTXT* pctxt = getCtxtPtr();
   ASN1T_DERCMS_SignatureValue* pDstData = new ASN1T_DERCMS_SignatureValue;

   asn1Copy_DERCMS_SignatureValue (pctxt, &msgData, pDstData);

   return pDstData;
}

void asn1Copy_DERCMS_AuthAttributes (OSCTXT* pctxt, 
   ASN1T_DERCMS_AuthAttributes* pSrcValue, ASN1T_DERCMS_AuthAttributes*
    pDstValue)
{
   if (pSrcValue == pDstValue) return;

   {
      OSUINT32 xx1;
      ASN1T_EXP_Attribute* pSrcData;
      OSRTDListNode* pnode;

      rtxDListInit (pDstValue);
      pnode = pSrcValue->head;
      for (xx1 = 0; xx1 < pSrcValue->count; xx1++) {
         ASN1T_EXP_Attribute* pDstData = rtxMemAllocTypeZ (pctxt, ASN1T_EXP_Attribute);
         pSrcData = (ASN1T_EXP_Attribute*) pnode->data;

         asn1Copy_EXP_Attribute (pctxt, pSrcData, pDstData);

         rtxDListAppend (pctxt, pDstValue, (void*)pDstData);
         pnode = pnode->next;
      }
   }
}

ASN1C_DERCMS_AuthAttributes::ASN1C_DERCMS_AuthAttributes (ASN1C_DERCMS_AuthAttributes& original) :
   ASN1CSeqOfList(*original.mpContext, original.getCopy()), msgData (*(ASN1T_DERCMS_AuthAttributes*)pList)
{
}

ASN1C_DERCMS_AuthAttributes& ASN1C_DERCMS_AuthAttributes::operator= (ASN1C_DERCMS_AuthAttributes& srcData)
{
   clear ();
   asn1Copy_DERCMS_AuthAttributes (getCtxtPtr(), &srcData.msgData, &msgData);
   return *this;
}

ASN1T_DERCMS_AuthAttributes& ASN1C_DERCMS_AuthAttributes::getCopy (ASN1T_DERCMS_AuthAttributes* pDstData)
{
   if (&msgData == pDstData) return *pDstData;

   OSCTXT* pctxt = getCtxtPtr();

   if (pDstData == 0) {
      pDstData = rtxMemAllocType (pctxt, ASN1T_DERCMS_AuthAttributes);
      new (pDstData) ASN1T_DERCMS_AuthAttributes;
   }

   asn1Copy_DERCMS_AuthAttributes (pctxt, &msgData, pDstData);

   pDstData->setContext (mpContext);

   return *pDstData;
}

ASN1T_DERCMS_AuthAttributes* ASN1C_DERCMS_AuthAttributes::newCopy ()
{
   OSCTXT* pctxt = getCtxtPtr();
   ASN1T_DERCMS_AuthAttributes* pDstData = new ASN1T_DERCMS_AuthAttributes;

   asn1Copy_DERCMS_AuthAttributes (pctxt, &msgData, pDstData);

   pDstData->setContext (mpContext);

   return pDstData;
}

void asn1Copy_DERCMS_SignedAttributes (OSCTXT* pctxt, 
   ASN1T_DERCMS_SignedAttributes* pSrcValue, ASN1T_DERCMS_SignedAttributes*
    pDstValue)
{
   if (pSrcValue == pDstValue) return;

   {
      OSUINT32 xx1;
      ASN1T_EXP_Attribute* pSrcData;
      OSRTDListNode* pnode;

      rtxDListInit (pDstValue);
      pnode = pSrcValue->head;
      for (xx1 = 0; xx1 < pSrcValue->count; xx1++) {
         ASN1T_EXP_Attribute* pDstData = rtxMemAllocTypeZ (pctxt, ASN1T_EXP_Attribute);
         pSrcData = (ASN1T_EXP_Attribute*) pnode->data;

         asn1Copy_EXP_Attribute (pctxt, pSrcData, pDstData);

         rtxDListAppend (pctxt, pDstValue, (void*)pDstData);
         pnode = pnode->next;
      }
   }
}

ASN1C_DERCMS_SignedAttributes::ASN1C_DERCMS_SignedAttributes (ASN1C_DERCMS_SignedAttributes& original) :
   ASN1CSeqOfList(*original.mpContext, original.getCopy()), msgData (*(ASN1T_DERCMS_SignedAttributes*)pList)
{
}

ASN1C_DERCMS_SignedAttributes& ASN1C_DERCMS_SignedAttributes::operator= (ASN1C_DERCMS_SignedAttributes& srcData)
{
   clear ();
   asn1Copy_DERCMS_SignedAttributes (getCtxtPtr(), &srcData.msgData, &msgData);
   return *this;
}

ASN1T_DERCMS_SignedAttributes& ASN1C_DERCMS_SignedAttributes::getCopy (ASN1T_DERCMS_SignedAttributes* pDstData)
{
   if (&msgData == pDstData) return *pDstData;

   OSCTXT* pctxt = getCtxtPtr();

   if (pDstData == 0) {
      pDstData = rtxMemAllocType (pctxt, ASN1T_DERCMS_SignedAttributes);
      new (pDstData) ASN1T_DERCMS_SignedAttributes;
   }

   asn1Copy_DERCMS_SignedAttributes (pctxt, &msgData, pDstData);

   pDstData->setContext (mpContext);

   return *pDstData;
}

ASN1T_DERCMS_SignedAttributes* ASN1C_DERCMS_SignedAttributes::newCopy ()
{
   OSCTXT* pctxt = getCtxtPtr();
   ASN1T_DERCMS_SignedAttributes* pDstData = new ASN1T_DERCMS_SignedAttributes;

   asn1Copy_DERCMS_SignedAttributes (pctxt, &msgData, pDstData);

   pDstData->setContext (mpContext);

   return pDstData;
}

void asn1Copy_DERCMS_ECC_CMS_SharedInfo (OSCTXT* pctxt, 
   ASN1T_DERCMS_ECC_CMS_SharedInfo* pSrcValue, ASN1T_DERCMS_ECC_CMS_SharedInfo*
    pDstValue)
{
   if (pSrcValue == pDstValue) return;

   OSCRTLMEMCPY (&pDstValue->m, &pSrcValue->m, sizeof (pDstValue->m));
   asn1Copy_EXP_AlgorithmIdentifier (pctxt, &pSrcValue->keyInfo, &pDstValue->keyInfo);

   if (pSrcValue->m.entityUInfoPresent) {
      rtCopyDynOctStr (pctxt, &pSrcValue->entityUInfo, &pDstValue->entityUInfo
         );
   }

   rtCopyDynOctStr (pctxt, &pSrcValue->suppPubInfo, &pDstValue->suppPubInfo);

}

ASN1C_DERCMS_ECC_CMS_SharedInfo::ASN1C_DERCMS_ECC_CMS_SharedInfo (ASN1C_DERCMS_ECC_CMS_SharedInfo& original) :
   ASN1CType(original), msgData(original.getCopy())
{
}

ASN1T_DERCMS_ECC_CMS_SharedInfo::ASN1T_DERCMS_ECC_CMS_SharedInfo (ASN1C_DERCMS_ECC_CMS_SharedInfo& original)
{
   original.getCopy (this);
}

ASN1T_DERCMS_ECC_CMS_SharedInfo& ASN1C_DERCMS_ECC_CMS_SharedInfo::getCopy (ASN1T_DERCMS_ECC_CMS_SharedInfo* pDstData)
{
   if (&msgData == pDstData) return *pDstData;

   OSCTXT* pctxt = getCtxtPtr();

   if (pDstData == 0) {
      pDstData = rtxMemAllocType (pctxt, ASN1T_DERCMS_ECC_CMS_SharedInfo);
      new (pDstData) ASN1T_DERCMS_ECC_CMS_SharedInfo;
   }

   asn1Copy_DERCMS_ECC_CMS_SharedInfo (pctxt, &msgData, pDstData);

   pDstData->setContext (mpContext);

   return *pDstData;
}

ASN1T_DERCMS_ECC_CMS_SharedInfo* ASN1C_DERCMS_ECC_CMS_SharedInfo::newCopy ()
{
   OSCTXT* pctxt = getCtxtPtr();
   ASN1T_DERCMS_ECC_CMS_SharedInfo* pDstData = new ASN1T_DERCMS_ECC_CMS_SharedInfo;

   asn1Copy_DERCMS_ECC_CMS_SharedInfo (pctxt, &msgData, pDstData);

   pDstData->setContext (mpContext);

   return pDstData;
}

