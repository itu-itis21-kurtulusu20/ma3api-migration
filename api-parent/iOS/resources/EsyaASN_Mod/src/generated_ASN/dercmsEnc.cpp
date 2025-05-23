/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.6, Date: 28-Feb-2014.
 */
#include "dercms.h"
#include "rtxsrc/rtxCommon.h"

EXTERN int asn1E_DERCMS_SignatureValue (OSCTXT* pctxt,
   ASN1T_DERCMS_SignatureValue *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "SignatureValue");

   ll = xe_octstr (pctxt, pvalue->data, pvalue->numocts, tagging);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll0 += ll;

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

EXTERN int asn1E_DERCMS_AuthAttributes (OSCTXT* pctxt,
   ASN1T_DERCMS_AuthAttributes *pvalue, ASN1TagType tagging)
{
   OSRTDListNode* pnode;
   int xx1;
   Asn1BufLocDescr* pbuf;
   OSRTSList elemList;
   int ll;
   int ll0 = 0;
   int ll1 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "AuthAttributes");

   if (!(pvalue->count >= 1)) {
      rtxErrAddElemNameParm (pctxt);
      rtxErrAddIntParm (pctxt, (int)pvalue->count);
      return LOG_RTERR (pctxt, RTERR_CONSVIO);
   }

   rtxSListInitEx (pctxt, &elemList);

   xx1 = (int)pvalue->count;
   pnode = pvalue->tail;
   while (0 != pnode) {
      RTXCTXTPUSHARRAYELEMNAME (pctxt, "Attribute", xx1);

      ll = asn1E_EXP_Attribute (pctxt, ((ASN1T_EXP_Attribute*)pnode->data), ASN1EXPL);
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPARRAYELEMNAME (pctxt);
      xx1--;

      pbuf = rtxMemAllocType (pctxt, Asn1BufLocDescr);
      xe_getBufLocDescr (pctxt, ll, pbuf);
      rtxSListAppend (&elemList, pbuf);

      pnode = pnode->prev;
   }

   ll1 = xe_derCanonicalSort (pctxt, &elemList);
   if (ll1 < 0) return LOG_RTERR (pctxt, ll1);

   ll0 += ll1;

   if (tagging == ASN1EXPL)
      ll0 = xe_tag_len (pctxt, TM_UNIV|TM_CONS|17, ll0);

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

EXTERN int asn1E_DERCMS_SignedAttributes (OSCTXT* pctxt,
   ASN1T_DERCMS_SignedAttributes *pvalue, ASN1TagType tagging)
{
   OSRTDListNode* pnode;
   int xx1;
   Asn1BufLocDescr* pbuf;
   OSRTSList elemList;
   int ll;
   int ll0 = 0;
   int ll1 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "SignedAttributes");

   if (!(pvalue->count >= 1)) {
      rtxErrAddElemNameParm (pctxt);
      rtxErrAddIntParm (pctxt, (int)pvalue->count);
      return LOG_RTERR (pctxt, RTERR_CONSVIO);
   }

   rtxSListInitEx (pctxt, &elemList);

   xx1 = (int)pvalue->count;
   pnode = pvalue->tail;
   while (0 != pnode) {
      RTXCTXTPUSHARRAYELEMNAME (pctxt, "Attribute", xx1);

      ll = asn1E_EXP_Attribute (pctxt, ((ASN1T_EXP_Attribute*)pnode->data), ASN1EXPL);
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPARRAYELEMNAME (pctxt);
      xx1--;

      pbuf = rtxMemAllocType (pctxt, Asn1BufLocDescr);
      xe_getBufLocDescr (pctxt, ll, pbuf);
      rtxSListAppend (&elemList, pbuf);

      pnode = pnode->prev;
   }

   ll1 = xe_derCanonicalSort (pctxt, &elemList);
   if (ll1 < 0) return LOG_RTERR (pctxt, ll1);

   ll0 += ll1;

   if (tagging == ASN1EXPL)
      ll0 = xe_tag_len (pctxt, TM_UNIV|TM_CONS|17, ll0);

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

EXTERN int asn1E_DERCMS_ECC_CMS_SharedInfo (OSCTXT* pctxt,
   ASN1T_DERCMS_ECC_CMS_SharedInfo *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;
   int ll1 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "ECC_CMS_SharedInfo");

   /* encode suppPubInfo */

   RTXCTXTPUSHELEMNAME (pctxt, "suppPubInfo");

   ll = xe_tag_len (pctxt, TM_CTXT|TM_CONS|2,
      xe_octstr (pctxt, pvalue->suppPubInfo.data, pvalue->suppPubInfo.numocts, ASN1EXPL));
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   /* encode entityUInfo */

   if (pvalue->m.entityUInfoPresent) {
      RTXCTXTPUSHELEMNAME (pctxt, "entityUInfo");

      ll = xe_tag_len (pctxt, TM_CTXT|TM_CONS|0,
         xe_octstr (pctxt, pvalue->entityUInfo.data, pvalue->entityUInfo.numocts, ASN1EXPL));
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   /* encode keyInfo */

   RTXCTXTPUSHELEMNAME (pctxt, "keyInfo");

   ll = asn1E_EXP_AlgorithmIdentifier (pctxt, &pvalue->keyInfo, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   ll0 += ll1;

   if (tagging == ASN1EXPL)
      ll0 = xe_tag_len (pctxt, TM_UNIV|TM_CONS|16, ll0);

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

