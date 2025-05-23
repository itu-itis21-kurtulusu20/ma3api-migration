/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.6, Date: 20-Feb-2014.
 */
#include "ASN_SertifikaDeposu.h"
#include "rtxsrc/rtxCommon.h"

EXTERN int asn1D_SD_KokSertifikaTipi (OSCTXT* pctxt, 
   ASN1T_SD_KokSertifikaTipi* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "KokSertifikaTipi");

   stat = xd_enumUnsigned (pctxt, pvalue, tagging, length);
   if (stat != 0) return LOG_RTERR (pctxt, stat);

   if(*pvalue < 1 || *pvalue > 4)
      return LOG_RTERR (pctxt, RTERR_INVENUM);

   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

EXTERN int asn1D_SD_KOKGuvenSeviyesi (OSCTXT* pctxt, 
   ASN1T_SD_KOKGuvenSeviyesi* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "KOKGuvenSeviyesi");

   stat = xd_enumUnsigned (pctxt, pvalue, tagging, length);
   if (stat != 0) return LOG_RTERR (pctxt, stat);

   if(*pvalue < 1 || *pvalue > 3)
      return LOG_RTERR (pctxt, RTERR_INVENUM);

   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

EXTERN int asn1D_SD_DepoASNEklenecekKokSertifika (OSCTXT* pctxt, 
   ASN1T_SD_DepoASNEklenecekKokSertifika* pvalue, ASN1TagType tagging, 
   int length)
{
   int stat = 0;
   int reqcnt = 0;
   ASN1CCB ccb;

   RTXCTXTPUSHTYPENAME (pctxt, "DepoASNEklenecekKokSertifika");

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
         /* decode kokSertifikaValue */
         if (XD_PEEKTAG (pctxt, 0x80)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "kokSertifikaValue");

            stat = xd_octstr (pctxt, &pvalue->kokSertifikaValue.data, &pvalue->kokSertifikaValue.numocts, ASN1IMPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 1:
         /* decode kokSertifikaHash */
         if (XD_PEEKTAG (pctxt, 0x81)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "kokSertifikaHash");

            stat = xd_octstr (pctxt, &pvalue->kokSertifikaHash.data, &pvalue->kokSertifikaHash.numocts, ASN1IMPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 2:
         /* decode kokSerialNumber */
         if (XD_PEEKTAG (pctxt, 0x82)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "kokSerialNumber");

            stat = asn1D_EXP_CertificateSerialNumber (pctxt, &pvalue->
               kokSerialNumber, ASN1IMPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 3:
         /* decode kokIssuerName */
         if (XD_PEEKTAG (pctxt, 0x83)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "kokIssuerName");

            stat = asn1D_EXP_Name (pctxt, &pvalue->kokIssuerName, 
               ASN1IMPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 4:
         /* decode kokSubjectName */
         if (XD_PEEKTAG (pctxt, 0x84)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "kokSubjectName");

            stat = asn1D_EXP_Name (pctxt, &pvalue->kokSubjectName, 
               ASN1IMPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 5:
         /* decode kokStartDate */
         if (XD_PEEKTAG (pctxt, 0x85)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "kokStartDate");

            stat = asn1D_EXP_Time (pctxt, &pvalue->kokStartDate, 
               ASN1IMPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 6:
         /* decode kokEndDate */
         if (XD_PEEKTAG (pctxt, 0x86)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "kokEndDate");

            stat = asn1D_EXP_Time (pctxt, &pvalue->kokEndDate, 
               ASN1IMPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 7:
         /* decode kokKeyUsage */
         if (XD_PEEKTAG (pctxt, 0x87)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "kokKeyUsage");

            stat = asn1D_IMP_KeyUsage (pctxt, &pvalue->kokKeyUsage, 
               ASN1IMPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 8:
         /* decode kokSubjectKeyIdentifier */
         if (XD_PEEKTAG (pctxt, 0x88)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "kokSubjectKeyIdentifier");

            stat = asn1D_IMP_SubjectKeyIdentifier (pctxt, &pvalue->
               kokSubjectKeyIdentifier, ASN1IMPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 9:
         /* decode kokSertifikaTipi */
         if (XD_PEEKTAG (pctxt, 0x89)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "kokSertifikaTipi");

            stat = asn1D_SD_KokSertifikaTipi (pctxt, &pvalue->kokSertifikaTipi
               , ASN1IMPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 10:
         /* decode kokGuvenSeviyesi */
         if (XD_PEEKTAG (pctxt, 0x8a)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "kokGuvenSeviyesi");

            stat = asn1D_SD_KOKGuvenSeviyesi (pctxt, &pvalue->kokGuvenSeviyesi
               , ASN1IMPL, length);
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
         case (TM_CTXT|TM_PRIM|0):
         case (TM_CTXT|TM_PRIM|1):
         case (TM_CTXT|TM_PRIM|2):
         case (TM_CTXT|TM_CONS|3):
         case (TM_CTXT|TM_CONS|4):
         case (TM_CTXT|TM_CONS|5):
         case (TM_CTXT|TM_CONS|6):
         case (TM_CTXT|TM_PRIM|7):
         case (TM_CTXT|TM_PRIM|8):
         case (TM_CTXT|TM_PRIM|9):
         case (TM_CTXT|TM_PRIM|10):
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

   if (reqcnt < 11) {
      return LOG_RTERR (pctxt, RTERR_SETMISRQ);
   }
   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

EXTERN int asn1D_SD_DepoASNSilinecekKokSertifika (OSCTXT* pctxt, 
   ASN1T_SD_DepoASNSilinecekKokSertifika* pvalue, ASN1TagType tagging, 
   int length)
{
   int stat = 0;
   int reqcnt = 0;
   ASN1CCB ccb;

   RTXCTXTPUSHTYPENAME (pctxt, "DepoASNSilinecekKokSertifika");

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
         /* decode kokSertifikaValue */
         if (XD_PEEKTAG (pctxt, 0x80)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "kokSertifikaValue");

            stat = xd_octstr (pctxt, &pvalue->kokSertifikaValue.data, &pvalue->kokSertifikaValue.numocts, ASN1IMPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 1:
         /* decode kokSerialNumber */
         if (XD_PEEKTAG (pctxt, 0x81)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "kokSerialNumber");

            stat = asn1D_EXP_CertificateSerialNumber (pctxt, &pvalue->
               kokSerialNumber, ASN1IMPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 2:
         /* decode kokIssuerName */
         if (XD_PEEKTAG (pctxt, 0x82)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "kokIssuerName");

            stat = asn1D_EXP_Name (pctxt, &pvalue->kokIssuerName, 
               ASN1IMPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 3:
         /* decode kokSubjectName */
         if (XD_PEEKTAG (pctxt, 0x83)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "kokSubjectName");

            stat = asn1D_EXP_Name (pctxt, &pvalue->kokSubjectName, 
               ASN1IMPL, length);
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
         case (TM_CTXT|TM_PRIM|0):
         case (TM_CTXT|TM_PRIM|1):
         case (TM_CTXT|TM_CONS|2):
         case (TM_CTXT|TM_CONS|3):
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

   if (reqcnt < 4) {
      return LOG_RTERR (pctxt, RTERR_SETMISRQ);
   }
   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

EXTERN int asn1D_SD_DepoASNKokSertifika (OSCTXT* pctxt, 
   ASN1T_SD_DepoASNKokSertifika* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;
   ASN1TAG ctag;
   ASN1CCB ccb;

   RTXCTXTPUSHTYPENAME (pctxt, "DepoASNKokSertifika");

   /* decode CHOICE */

   stat = xd_tag_len (pctxt, &ctag, &length, XM_ADVANCE);
   if (stat != 0) return LOG_RTERR (pctxt, stat);

   ccb.len = length;
   ccb.ptr = OSRTBUFPTR(pctxt);

   switch (ctag)
   {
      case (TM_CTXT|TM_CONS|0):
         RTXCTXTPUSHELEMNAME (pctxt, "eklenecekSertifika");

         pvalue->u.eklenecekSertifika = rtxMemAllocType (pctxt, 
            ASN1T_SD_DepoASNEklenecekKokSertifika);

         if (pvalue->u.eklenecekSertifika == NULL)
            return LOG_RTERR (pctxt, RTERR_NOMEM);

         asn1Init_SD_DepoASNEklenecekKokSertifika (pvalue->u.eklenecekSertifika);
         stat = asn1D_SD_DepoASNEklenecekKokSertifika (pctxt, pvalue->
            u.eklenecekSertifika, ASN1IMPL, length);
         pvalue->t = 1;
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);
         break;

      case (TM_CTXT|TM_CONS|1):
         RTXCTXTPUSHELEMNAME (pctxt, "silinecekSertifika");

         pvalue->u.silinecekSertifika = rtxMemAllocType (pctxt, 
            ASN1T_SD_DepoASNSilinecekKokSertifika);

         if (pvalue->u.silinecekSertifika == NULL)
            return LOG_RTERR (pctxt, RTERR_NOMEM);

         asn1Init_SD_DepoASNSilinecekKokSertifika (pvalue->u.silinecekSertifika);
         stat = asn1D_SD_DepoASNSilinecekKokSertifika (pctxt, pvalue->
            u.silinecekSertifika, ASN1IMPL, length);
         pvalue->t = 2;
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);
         break;

      default:
         berErrAddTagParm (pctxt, ctag);
         return LOG_RTERR (pctxt, RTERR_INVOPT);
   }

   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

EXTERN int asn1D_SD_DepoASNKokSertifikalar (OSCTXT* pctxt, 
   ASN1T_SD_DepoASNKokSertifikalar* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;
   ASN1T_SD_DepoASNKokSertifika* pdata1;
   OSRTDListNode* pnode;
   ASN1CCB ccb;

   RTXCTXTPUSHTYPENAME (pctxt, "DepoASNKokSertifikalar");

   if (tagging == ASN1EXPL) {
      stat = xd_match1 (pctxt, 0x10, &length);
      if (stat != 0) return LOG_RTERR (pctxt, stat);
   }

   /* decode SEQUENCE OF or SET OF */

   rtxDListInit (pvalue);

   ccb.len = length;
   ccb.ptr = OSRTBUFPTR(pctxt);

   while (!XD_CHKEND (pctxt, &ccb))
   {
      RTXCTXTPUSHARRAYELEMNAME (pctxt, "DepoASNKokSertifika", pvalue->count);

      rtxDListAllocNodeAndData (pctxt, ASN1T_SD_DepoASNKokSertifika, &pnode, &pdata1);

      if (pnode == NULL)
         return LOG_RTERR (pctxt, RTERR_NOMEM);

      asn1Init_SD_DepoASNKokSertifika (pdata1);

      stat = asn1D_SD_DepoASNKokSertifika (pctxt, pdata1, ASN1EXPL, length);
      if (stat != 0) return LOG_RTERR (pctxt, stat);

      RTXCTXTPOPARRAYELEMNAME (pctxt);
      rtxDListAppendNode (pvalue, pnode);
   }

   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

EXTERN int asn1D_SD_DepoASNRawImza (OSCTXT* pctxt, 
   ASN1T_SD_DepoASNRawImza* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;
   int reqcnt = 0;
   ASN1CCB ccb;

   RTXCTXTPUSHTYPENAME (pctxt, "DepoASNRawImza");

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
         /* decode publicKeyHash */
         if (XD_PEEKTAG (pctxt, 0x80)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "publicKeyHash");

            stat = xd_octstr (pctxt, &pvalue->publicKeyHash.data, &pvalue->publicKeyHash.numocts, ASN1IMPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 1:
         /* decode imza */
         if (XD_PEEKTAG (pctxt, 0x81)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "imza");

            stat = xd_octstr (pctxt, &pvalue->imza.data, &pvalue->imza.numocts, ASN1IMPL, length);
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
         case (TM_CTXT|TM_PRIM|0):
         case (TM_CTXT|TM_PRIM|1):
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

EXTERN int asn1D_SD_DepoASNImza (OSCTXT* pctxt, ASN1T_SD_DepoASNImza* pvalue, 
   ASN1TagType tagging, int length)
{
   int stat = 0;
   int reqcnt = 0;
   ASN1CCB ccb;

   RTXCTXTPUSHTYPENAME (pctxt, "DepoASNImza");

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
         /* decode imzalanan */
         if (XD_PEEKTAG (pctxt, 0x80)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "imzalanan");

            stat = asn1D_SD_DepoASNKokSertifika (pctxt, &pvalue->imzalanan, 
               ASN1IMPL, length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);

            reqcnt++;
            declen = (int)pctxt->buffer.byteIndex - offset;
            if (declen != explen && explen != ASN_K_INDEFLEN)
               return LOG_RTERR (pctxt, ASN_E_INVLEN);
         }
         break;

      case 1:
         /* decode imza */
         if (XD_PEEKTAG (pctxt, 0x81)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "imza");

            stat = asn1D_SD_DepoASNRawImza (pctxt, &pvalue->imza, 
               ASN1IMPL, length);
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
         case (TM_CTXT|TM_CONS|0):
         case (TM_CTXT|TM_CONS|1):
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

EXTERN int asn1D_SD_DepoASNImzalar (OSCTXT* pctxt, 
   ASN1T_SD_DepoASNImzalar* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;
   ASN1T_SD_DepoASNImza* pdata1;
   OSRTDListNode* pnode;
   ASN1CCB ccb;

   RTXCTXTPUSHTYPENAME (pctxt, "DepoASNImzalar");

   if (tagging == ASN1EXPL) {
      stat = xd_match1 (pctxt, 0x10, &length);
      if (stat != 0) return LOG_RTERR (pctxt, stat);
   }

   /* decode SEQUENCE OF or SET OF */

   rtxDListInit (pvalue);

   ccb.len = length;
   ccb.ptr = OSRTBUFPTR(pctxt);

   while (!XD_CHKEND (pctxt, &ccb))
   {
      RTXCTXTPUSHARRAYELEMNAME (pctxt, "DepoASNImza", pvalue->count);

      rtxDListAllocNodeAndData (pctxt, ASN1T_SD_DepoASNImza, &pnode, &pdata1);

      if (pnode == NULL)
         return LOG_RTERR (pctxt, RTERR_NOMEM);

      asn1Init_SD_DepoASNImza (pdata1);

      stat = asn1D_SD_DepoASNImza (pctxt, pdata1, ASN1EXPL, length);
      if (stat != 0) return LOG_RTERR (pctxt, stat);

      RTXCTXTPOPARRAYELEMNAME (pctxt);
      rtxDListAppendNode (pvalue, pnode);
   }

   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

