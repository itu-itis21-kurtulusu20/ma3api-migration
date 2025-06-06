/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.6, Date: 20-Feb-2014.
 */
#include "asnaes.h"
#include "rtxsrc/rtxCommon.h"

EXTERN int asn1D_AES_AES_IV (OSCTXT* pctxt, ASN1T_AES_AES_IV* pvalue, 
   ASN1TagType tagging, int length)
{
   int stat = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "AES-IV");

   pvalue->numocts = 16;
   stat = xd_octstr_s (pctxt, pvalue->data, &pvalue->numocts, tagging, length);
   if (stat != 0) return LOG_RTERR (pctxt, stat);

   if (!(pvalue->numocts == 16)) {
      rtxErrAddElemNameParm (pctxt);
      rtxErrAddIntParm (pctxt, (int)pvalue->numocts);
      return LOG_RTERR (pctxt, RTERR_CONSVIO);
   }

   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

EXTERN int asn1D_AES_NumberOfBits (OSCTXT* pctxt, 
   ASN1T_AES_NumberOfBits* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "NumberOfBits");

   stat = xd_uint8 (pctxt, pvalue, tagging, length);
   if (stat != 0) return LOG_RTERR (pctxt, stat);

   if (!((*pvalue >= OSUINTCONST(1) && *pvalue <= OSUINTCONST(128)))) {
      rtxErrAddElemNameParm (pctxt);
      rtxErrAddIntParm (pctxt, (int)*pvalue);
      return LOG_RTERR (pctxt, RTERR_CONSVIO);
   }

   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

EXTERN int asn1D_AES_CFBParameters (OSCTXT* pctxt, 
   ASN1T_AES_CFBParameters* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;
   int reqcnt = 0;
   ASN1CCB ccb;

   RTXCTXTPUSHTYPENAME (pctxt, "CFBParameters");

   if (tagging == ASN1EXPL) {
      stat = xd_match1 (pctxt, 0x10, &length);
      if (stat != 0) return LOG_RTERR (pctxt, stat);
   }

   /* decode SEQUENCE */

   /* default value initialization */
   {
   }

   ccb.len = length;
   ccb.ptr = OSRTBUFPTR(pctxt);
   ccb.seqx = 0;

   while (!XD_CHKEND (pctxt, &ccb)) {
      switch (ccb.seqx) {
      case 0:
         /* decode aes_IV */
         RTXCTXTPUSHELEMNAME (pctxt, "aes-IV");

         stat = asn1D_AES_AES_IV (pctxt, &pvalue->aes_IV, ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      case 1:
         /* decode numberOfBits */
         RTXCTXTPUSHELEMNAME (pctxt, "numberOfBits");

         stat = asn1D_AES_NumberOfBits (pctxt, &pvalue->numberOfBits, 
            ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      default:
         { ASN1TAG tag;
         int __len;
         stat = xd_tag_len (pctxt, &tag, &__len, 0);
         if (stat != 0) return LOG_RTERR (pctxt, stat);
         switch (tag) {
         case (TM_UNIV|TM_PRIM|4):
         case (TM_UNIV|TM_PRIM|2):
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

