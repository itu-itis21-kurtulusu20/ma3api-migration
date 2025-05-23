/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.6, Date: 20-Feb-2014.
 */
#include "dercms.h"
#include "rtxsrc/rtxCommon.h"

EXTERN int asn1D_DERCMS_SignatureValue (OSCTXT* pctxt, 
   ASN1T_DERCMS_SignatureValue* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "SignatureValue");

   stat = xd_octstr (pctxt, &pvalue->data, &pvalue->numocts, tagging, length);
   if (stat != 0) return LOG_RTERR (pctxt, stat);

   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

EXTERN int asn1D_DERCMS_AuthAttributes (OSCTXT* pctxt, 
   ASN1T_DERCMS_AuthAttributes* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;
   ASN1T_EXP_Attribute* pdata1;
   OSRTDListNode* pnode;
   ASN1CCB ccb;

   RTXCTXTPUSHTYPENAME (pctxt, "AuthAttributes");

   if (tagging == ASN1EXPL) {
      stat = xd_match1 (pctxt, 0x11, &length);
      if (stat != 0) return LOG_RTERR (pctxt, stat);
   }

   /* decode SEQUENCE OF or SET OF */

   rtxDListInit (pvalue);

   ccb.len = length;
   ccb.ptr = OSRTBUFPTR(pctxt);

   while (!XD_CHKEND (pctxt, &ccb))
   {
      RTXCTXTPUSHARRAYELEMNAME (pctxt, "Attribute", pvalue->count);

      rtxDListAllocNodeAndData (pctxt, ASN1T_EXP_Attribute, &pnode, &pdata1);

      if (pnode == NULL)
         return LOG_RTERR (pctxt, RTERR_NOMEM);

      asn1Init_EXP_Attribute (pdata1);

      stat = asn1D_EXP_Attribute (pctxt, pdata1, ASN1EXPL, length);
      if (stat != 0) return LOG_RTERR (pctxt, stat);

      RTXCTXTPOPARRAYELEMNAME (pctxt);
      rtxDListAppendNode (pvalue, pnode);
   }

   if (!(pvalue->count >= 1)) {
      rtxErrAddElemNameParm (pctxt);
      rtxErrAddIntParm (pctxt, (int)pvalue->count);
      return LOG_RTERR (pctxt, RTERR_CONSVIO);
   }

   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

EXTERN int asn1D_DERCMS_SignedAttributes (OSCTXT* pctxt, 
   ASN1T_DERCMS_SignedAttributes* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;
   ASN1T_EXP_Attribute* pdata1;
   OSRTDListNode* pnode;
   ASN1CCB ccb;

   RTXCTXTPUSHTYPENAME (pctxt, "SignedAttributes");

   if (tagging == ASN1EXPL) {
      stat = xd_match1 (pctxt, 0x11, &length);
      if (stat != 0) return LOG_RTERR (pctxt, stat);
   }

   /* decode SEQUENCE OF or SET OF */

   rtxDListInit (pvalue);

   ccb.len = length;
   ccb.ptr = OSRTBUFPTR(pctxt);

   while (!XD_CHKEND (pctxt, &ccb))
   {
      RTXCTXTPUSHARRAYELEMNAME (pctxt, "Attribute", pvalue->count);

      rtxDListAllocNodeAndData (pctxt, ASN1T_EXP_Attribute, &pnode, &pdata1);

      if (pnode == NULL)
         return LOG_RTERR (pctxt, RTERR_NOMEM);

      asn1Init_EXP_Attribute (pdata1);

      stat = asn1D_EXP_Attribute (pctxt, pdata1, ASN1EXPL, length);
      if (stat != 0) return LOG_RTERR (pctxt, stat);

      RTXCTXTPOPARRAYELEMNAME (pctxt);
      rtxDListAppendNode (pvalue, pnode);
   }

   if (!(pvalue->count >= 1)) {
      rtxErrAddElemNameParm (pctxt);
      rtxErrAddIntParm (pctxt, (int)pvalue->count);
      return LOG_RTERR (pctxt, RTERR_CONSVIO);
   }

   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

EXTERN int asn1D_DERCMS_ECC_CMS_SharedInfo (OSCTXT* pctxt, 
   ASN1T_DERCMS_ECC_CMS_SharedInfo* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;
   int reqcnt = 0;
   ASN1CCB ccb;

   RTXCTXTPUSHTYPENAME (pctxt, "ECC_CMS_SharedInfo");

   if (tagging == ASN1EXPL) {
      stat = xd_match1 (pctxt, 0x10, &length);
      if (stat != 0) return LOG_RTERR (pctxt, stat);
   }

   /* decode SEQUENCE */

   OSCRTLMEMSET (&pvalue->m, 0, sizeof(pvalue->m));

   /* default value initialization */
   {
   }

   ccb.len = length;
   ccb.ptr = OSRTBUFPTR(pctxt);
   ccb.seqx = 0;

   while (!XD_CHKEND (pctxt, &ccb)) {
      switch (ccb.seqx) {
      case 0:
         /* decode keyInfo */
         RTXCTXTPUSHELEMNAME (pctxt, "keyInfo");

         stat = asn1D_EXP_AlgorithmIdentifier (pctxt, &pvalue->keyInfo, 
            ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      case 1:
         /* decode entityUInfo */
         if (XD_PEEKTAG (pctxt, 0x80)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "entityUInfo");

            stat = xd_octstr (pctxt, &pvalue->entityUInfo.data, &pvalue->entityUInfo.numocts, ASN1EXPL, length);
            if (stat == 0) {
               pvalue->m.entityUInfoPresent = TRUE;
            }
            else return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 2:
         /* decode suppPubInfo */
         if (XD_PEEKTAG (pctxt, 0x82)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "suppPubInfo");

            stat = xd_octstr (pctxt, &pvalue->suppPubInfo.data, &pvalue->suppPubInfo.numocts, ASN1EXPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      default:
         { ASN1TAG tag;
         int __len;
         stat = xd_tag_len (pctxt, &tag, &__len, 0);
         if (stat != 0) return LOG_RTERR (pctxt, stat);
         switch (tag) {
         case (TM_UNIV|TM_CONS|16):
         case (TM_CTXT|TM_CONS|0):
         case (TM_CTXT|TM_CONS|2):
            stat = RTERR_SEQORDER;
            break;
         default:
            berErrAddTagParm (pctxt, tag);
            stat = ASN_E_NOTINSEQ;
            break;
         }}
      }
      if (stat != 0) return LOG_RTERR (pctxt, stat);
      else ccb.seqx++;
   }

   if (reqcnt < 2) {
      return LOG_RTERR (pctxt, RTERR_SETMISRQ);
   }
   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

