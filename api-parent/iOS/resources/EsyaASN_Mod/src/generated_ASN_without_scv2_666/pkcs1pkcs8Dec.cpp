/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.6, Date: 20-Feb-2014.
 */
#include "pkcs1pkcs8.h"
#include "rtxsrc/rtxCommon.h"

EXTERN int asn1D_PKCS18_PrivateKey (OSCTXT* pctxt, 
   ASN1T_PKCS18_PrivateKey* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "PrivateKey");

   stat = xd_octstr (pctxt, &pvalue->data, &pvalue->numocts, tagging, length);
   if (stat != 0) return LOG_RTERR (pctxt, stat);

   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

EXTERN int asn1D_PKCS18_EncryptedData (OSCTXT* pctxt, 
   ASN1T_PKCS18_EncryptedData* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "EncryptedData");

   stat = xd_octstr (pctxt, &pvalue->data, &pvalue->numocts, tagging, length);
   if (stat != 0) return LOG_RTERR (pctxt, stat);

   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

EXTERN int asn1D_PKCS18_PrivateKeyInfo (OSCTXT* pctxt, 
   ASN1T_PKCS18_PrivateKeyInfo* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;
   int reqcnt = 0;
   ASN1CCB ccb;

   RTXCTXTPUSHTYPENAME (pctxt, "PrivateKeyInfo");

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
         /* decode version */
         RTXCTXTPUSHELEMNAME (pctxt, "version");

         stat = asn1D_EXP_Version (pctxt, &pvalue->version, ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      case 1:
         /* decode privateKeyAlgorithm */
         RTXCTXTPUSHELEMNAME (pctxt, "privateKeyAlgorithm");

         stat = asn1D_EXP_AlgorithmIdentifier (pctxt, &pvalue->
            privateKeyAlgorithm, ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      case 2:
         /* decode privateKey */
         RTXCTXTPUSHELEMNAME (pctxt, "privateKey");

         stat = asn1D_PKCS18_PrivateKey (pctxt, &pvalue->privateKey, 
            ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      case 3:
         /* decode attributes */
         if (XD_PEEKTAG (pctxt, 0x80)) {
            int offset, declen, explen;
            stat = xd_Tag1AndLen (pctxt, &length);
            if (stat != 0) return LOG_RTERR (pctxt, stat);

            offset = (int) pctxt->buffer.byteIndex;
            explen = length;

            RTXCTXTPUSHELEMNAME (pctxt, "attributes");

            stat = asn1D_PKCS10_Attributes (pctxt, &pvalue->attributes, 
               ASN1IMPL, length);
            if (stat == 0) {
               pvalue->m.attributesPresent = TRUE;
            }
            else return LOG_RTERR (pctxt, stat);

            RTXCTXTPOPELEMNAME (pctxt);
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
         case (TM_UNIV|TM_PRIM|2):
         case (TM_UNIV|TM_CONS|16):
         case (TM_UNIV|TM_PRIM|4):
         case (TM_CTXT|TM_CONS|0):
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

   if (reqcnt < 3) {
      return LOG_RTERR (pctxt, RTERR_SETMISRQ);
   }
   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

EXTERN int asn1D_PKCS18_EncryptedPrivateKeyInfo (OSCTXT* pctxt, 
   ASN1T_PKCS18_EncryptedPrivateKeyInfo* pvalue, ASN1TagType tagging, 
   int length)
{
   int stat = 0;
   int reqcnt = 0;
   ASN1CCB ccb;

   RTXCTXTPUSHTYPENAME (pctxt, "EncryptedPrivateKeyInfo");

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
         /* decode encryptionAlgorithm */
         RTXCTXTPUSHELEMNAME (pctxt, "encryptionAlgorithm");

         stat = asn1D_EXP_AlgorithmIdentifier (pctxt, &pvalue->
            encryptionAlgorithm, ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      case 1:
         /* decode encryptedData */
         RTXCTXTPUSHELEMNAME (pctxt, "encryptedData");

         stat = asn1D_PKCS18_EncryptedData (pctxt, &pvalue->encryptedData, 
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
         case (TM_UNIV|TM_CONS|16):
         case (TM_UNIV|TM_PRIM|4):
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

EXTERN int asn1D_PKCS18_RSAPublicKey (OSCTXT* pctxt, 
   ASN1T_PKCS18_RSAPublicKey* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;
   int reqcnt = 0;
   ASN1CCB ccb;

   RTXCTXTPUSHTYPENAME (pctxt, "RSAPublicKey");

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
         /* decode modulus */
         RTXCTXTPUSHELEMNAME (pctxt, "modulus");

         stat = xd_bigint (pctxt, &pvalue->modulus, ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      case 1:
         /* decode publicExponent */
         RTXCTXTPUSHELEMNAME (pctxt, "publicExponent");

         stat = xd_bigint (pctxt, &pvalue->publicExponent, ASN1EXPL, length);
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

EXTERN int asn1D_PKCS18_RSAPrivateKey (OSCTXT* pctxt, 
   ASN1T_PKCS18_RSAPrivateKey* pvalue, ASN1TagType tagging, int length)
{
   int stat = 0;
   int reqcnt = 0;
   ASN1CCB ccb;

   RTXCTXTPUSHTYPENAME (pctxt, "RSAPrivateKey");

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
         /* decode version */
         RTXCTXTPUSHELEMNAME (pctxt, "version");

         stat = asn1D_EXP_Version (pctxt, &pvalue->version, ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      case 1:
         /* decode modulus */
         RTXCTXTPUSHELEMNAME (pctxt, "modulus");

         stat = xd_bigint (pctxt, &pvalue->modulus, ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      case 2:
         /* decode publicExponent */
         RTXCTXTPUSHELEMNAME (pctxt, "publicExponent");

         stat = xd_bigint (pctxt, &pvalue->publicExponent, ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      case 3:
         /* decode privateExponent */
         RTXCTXTPUSHELEMNAME (pctxt, "privateExponent");

         stat = xd_bigint (pctxt, &pvalue->privateExponent, ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      case 4:
         /* decode prime1 */
         RTXCTXTPUSHELEMNAME (pctxt, "prime1");

         stat = xd_bigint (pctxt, &pvalue->prime1, ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      case 5:
         /* decode prime2 */
         RTXCTXTPUSHELEMNAME (pctxt, "prime2");

         stat = xd_bigint (pctxt, &pvalue->prime2, ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      case 6:
         /* decode exponent1 */
         RTXCTXTPUSHELEMNAME (pctxt, "exponent1");

         stat = xd_bigint (pctxt, &pvalue->exponent1, ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      case 7:
         /* decode exponent2 */
         RTXCTXTPUSHELEMNAME (pctxt, "exponent2");

         stat = xd_bigint (pctxt, &pvalue->exponent2, ASN1EXPL, length);
         if (stat != 0) return LOG_RTERR (pctxt, stat);

         RTXCTXTPOPELEMNAME (pctxt);

         reqcnt++;
         break;

      case 8:
         /* decode coefficient */
         RTXCTXTPUSHELEMNAME (pctxt, "coefficient");

         stat = xd_bigint (pctxt, &pvalue->coefficient, ASN1EXPL, length);
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

   if (reqcnt < 9) {
      return LOG_RTERR (pctxt, RTERR_SETMISRQ);
   }
   RTXCTXTPOPTYPENAME (pctxt);

   return (stat);
}

