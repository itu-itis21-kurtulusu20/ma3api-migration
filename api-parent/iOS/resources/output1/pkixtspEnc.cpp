/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.6, Date: 20-Feb-2014.
 */
#include "pkixtsp.h"
#include "rtxsrc/rtxCommon.h"

EXTERN int asn1E_TSP_BIGINTEGER (OSCTXT* pctxt,
   ASN1T_TSP_BIGINTEGER *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "BIGINTEGER");

   ll = xe_bigint (pctxt, *pvalue, tagging);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll0 += ll;

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

EXTERN int asn1E_TSP_TSAPolicyId (OSCTXT* pctxt,
   ASN1T_TSP_TSAPolicyId *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "TSAPolicyId");

   ll = xe_objid (pctxt, (ASN1OBJID*)pvalue, tagging);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll0 += ll;

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

EXTERN int asn1E_TSP_PKIStatus (OSCTXT* pctxt,
   ASN1T_TSP_PKIStatus *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "PKIStatus");

   ll = xe_integer (pctxt, pvalue, tagging);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll0 += ll;

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

EXTERN int asn1E_TSP_PKIFailureInfo (OSCTXT* pctxt,
   ASN1T_TSP_PKIFailureInfo *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "PKIFailureInfo");

   ll = derEncBitString (pctxt, pvalue->data, pvalue->numbits, tagging);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll0 += ll;

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

EXTERN int asn1E_TSP_TimeStampToken (OSCTXT* pctxt,
   ASN1T_TSP_TimeStampToken *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "TimeStampToken");

   ll = asn1E_CMS_ContentInfo (pctxt, pvalue, tagging);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll0 += ll;

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

EXTERN int asn1E_TSP_TimeStampReq_version (OSCTXT* pctxt,
   ASN1T_TSP_TimeStampReq_version *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;

   ll = xe_integer (pctxt, pvalue, tagging);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll0 += ll;

   return (ll0);
}

EXTERN int asn1E_TSP_TSTInfo_version (OSCTXT* pctxt,
   ASN1T_TSP_TSTInfo_version *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;

   ll = xe_integer (pctxt, pvalue, tagging);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll0 += ll;

   return (ll0);
}

EXTERN int asn1E_TSP_MessageImprint (OSCTXT* pctxt,
   ASN1T_TSP_MessageImprint *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;
   int ll1 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "MessageImprint");

   /* encode hashedMessage */

   RTXCTXTPUSHELEMNAME (pctxt, "hashedMessage");

   ll = xe_octstr (pctxt, pvalue->hashedMessage.data, pvalue->hashedMessage.numocts, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   /* encode hashAlgorithm */

   RTXCTXTPUSHELEMNAME (pctxt, "hashAlgorithm");

   ll = asn1E_EXP_AlgorithmIdentifier (pctxt, &pvalue->hashAlgorithm, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   ll0 += ll1;

   if (tagging == ASN1EXPL)
      ll0 = xe_tag_len (pctxt, TM_UNIV|TM_CONS|16, ll0);

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

EXTERN int asn1E_TSP_TimeStampReq (OSCTXT* pctxt,
   ASN1T_TSP_TimeStampReq *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;
   int ll1 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "TimeStampReq");

   /* encode extensions */

   if (pvalue->m.extensionsPresent) {
      RTXCTXTPUSHELEMNAME (pctxt, "extensions");

      ll = xe_tag_len (pctxt, TM_CTXT|TM_CONS|0,
         asn1E_EXP_Extensions (pctxt, &pvalue->extensions, ASN1IMPL));
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   /* encode certReq */

   if (pvalue->certReq != FALSE) {
      RTXCTXTPUSHELEMNAME (pctxt, "certReq");

      ll = xe_boolean (pctxt, &pvalue->certReq, ASN1EXPL);
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   /* encode nonce */

   if (pvalue->m.noncePresent) {
      RTXCTXTPUSHELEMNAME (pctxt, "nonce");

      ll = asn1E_TSP_BIGINTEGER (pctxt, &pvalue->nonce, ASN1EXPL);
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   /* encode reqPolicy */

   if (pvalue->m.reqPolicyPresent) {
      RTXCTXTPUSHELEMNAME (pctxt, "reqPolicy");

      ll = asn1E_TSP_TSAPolicyId (pctxt, &pvalue->reqPolicy, ASN1EXPL);
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   /* encode messageImprint */

   RTXCTXTPUSHELEMNAME (pctxt, "messageImprint");

   ll = asn1E_TSP_MessageImprint (pctxt, &pvalue->messageImprint, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   /* encode version */

   RTXCTXTPUSHELEMNAME (pctxt, "version");

   ll = asn1E_TSP_TimeStampReq_version (pctxt, &pvalue->version, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   ll0 += ll1;

   if (tagging == ASN1EXPL)
      ll0 = xe_tag_len (pctxt, TM_UNIV|TM_CONS|16, ll0);

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

EXTERN int asn1E_TSP_PKIStatusInfo (OSCTXT* pctxt,
   ASN1T_TSP_PKIStatusInfo *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;
   int ll1 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "PKIStatusInfo");

   /* encode failInfo */

   if (pvalue->m.failInfoPresent) {
      RTXCTXTPUSHELEMNAME (pctxt, "failInfo");

      ll = asn1E_TSP_PKIFailureInfo (pctxt, &pvalue->failInfo, ASN1EXPL);
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   /* encode statusString */

   if (pvalue->m.statusStringPresent) {
      RTXCTXTPUSHELEMNAME (pctxt, "statusString");

      ll = asn1E_CMP_PKIFreeText (pctxt, &pvalue->statusString, ASN1EXPL);
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   /* encode status */

   RTXCTXTPUSHELEMNAME (pctxt, "status");

   ll = asn1E_TSP_PKIStatus (pctxt, &pvalue->status, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   ll0 += ll1;

   if (tagging == ASN1EXPL)
      ll0 = xe_tag_len (pctxt, TM_UNIV|TM_CONS|16, ll0);

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

EXTERN int asn1E_TSP_TimeStampResp (OSCTXT* pctxt,
   ASN1T_TSP_TimeStampResp *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;
   int ll1 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "TimeStampResp");

   /* encode timeStampToken */

   if (pvalue->m.timeStampTokenPresent) {
      RTXCTXTPUSHELEMNAME (pctxt, "timeStampToken");

      ll = asn1E_TSP_TimeStampToken (pctxt, &pvalue->timeStampToken, ASN1EXPL);
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   /* encode status */

   RTXCTXTPUSHELEMNAME (pctxt, "status");

   ll = asn1E_TSP_PKIStatusInfo (pctxt, &pvalue->status, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   ll0 += ll1;

   if (tagging == ASN1EXPL)
      ll0 = xe_tag_len (pctxt, TM_UNIV|TM_CONS|16, ll0);

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

EXTERN int asn1E_TSP_Accuracy (OSCTXT* pctxt,
   ASN1T_TSP_Accuracy *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;
   int ll1 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "Accuracy");

   /* encode micros */

   if (pvalue->m.microsPresent) {
      RTXCTXTPUSHELEMNAME (pctxt, "micros");

      if (!((pvalue->micros >= OSUINTCONST(1) && pvalue->micros <= OSUINTCONST(999)))) {
         rtxErrAddElemNameParm (pctxt);
         rtxErrAddIntParm (pctxt, (int)pvalue->micros);
         return LOG_RTERR (pctxt, RTERR_CONSVIO);
      }

      ll = xe_tag_len (pctxt, TM_CTXT|TM_PRIM|1,
         xe_uint16 (pctxt, &pvalue->micros, ASN1IMPL));
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   /* encode millis */

   if (pvalue->m.millisPresent) {
      RTXCTXTPUSHELEMNAME (pctxt, "millis");

      if (!((pvalue->millis >= OSUINTCONST(1) && pvalue->millis <= OSUINTCONST(999)))) {
         rtxErrAddElemNameParm (pctxt);
         rtxErrAddIntParm (pctxt, (int)pvalue->millis);
         return LOG_RTERR (pctxt, RTERR_CONSVIO);
      }

      ll = xe_tag_len (pctxt, TM_CTXT|TM_PRIM|0,
         xe_uint16 (pctxt, &pvalue->millis, ASN1IMPL));
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   /* encode seconds */

   if (pvalue->m.secondsPresent) {
      RTXCTXTPUSHELEMNAME (pctxt, "seconds");

      ll = xe_integer (pctxt, &pvalue->seconds, ASN1EXPL);
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   ll0 += ll1;

   if (tagging == ASN1EXPL)
      ll0 = xe_tag_len (pctxt, TM_UNIV|TM_CONS|16, ll0);

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

EXTERN int asn1E_TSP_TSTInfo (OSCTXT* pctxt,
   ASN1T_TSP_TSTInfo *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;
   int ll1 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "TSTInfo");

   /* encode extensions */

   if (pvalue->m.extensionsPresent) {
      RTXCTXTPUSHELEMNAME (pctxt, "extensions");

      ll = xe_tag_len (pctxt, TM_CTXT|TM_CONS|1,
         asn1E_EXP_Extensions (pctxt, &pvalue->extensions, ASN1IMPL));
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   /* encode tsa */

   if (pvalue->m.tsaPresent) {
      RTXCTXTPUSHELEMNAME (pctxt, "tsa");

      ll = xe_tag_len (pctxt, TM_CTXT|TM_CONS|0,
         asn1E_IMP_GeneralName (pctxt, &pvalue->tsa, ASN1IMPL));
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   /* encode nonce */

   if (pvalue->m.noncePresent) {
      RTXCTXTPUSHELEMNAME (pctxt, "nonce");

      ll = asn1E_TSP_BIGINTEGER (pctxt, &pvalue->nonce, ASN1EXPL);
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   /* encode ordering */

   if (pvalue->ordering != FALSE) {
      RTXCTXTPUSHELEMNAME (pctxt, "ordering");

      ll = xe_boolean (pctxt, &pvalue->ordering, ASN1EXPL);
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   /* encode accuracy */

   if (pvalue->m.accuracyPresent) {
      RTXCTXTPUSHELEMNAME (pctxt, "accuracy");

      ll = asn1E_TSP_Accuracy (pctxt, &pvalue->accuracy, ASN1EXPL);
      if (ll < 0) return LOG_RTERR (pctxt, ll);
      ll1 += ll;

      RTXCTXTPOPELEMNAME (pctxt);
   }
   /* encode genTime */

   RTXCTXTPUSHELEMNAME (pctxt, "genTime");

   ll = xe_charstr (pctxt, pvalue->genTime, ASN1EXPL, TM_UNIV|TM_PRIM|24);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   /* encode serialNumber */

   RTXCTXTPUSHELEMNAME (pctxt, "serialNumber");

   ll = asn1E_TSP_BIGINTEGER (pctxt, &pvalue->serialNumber, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   /* encode messageImprint */

   RTXCTXTPUSHELEMNAME (pctxt, "messageImprint");

   ll = asn1E_TSP_MessageImprint (pctxt, &pvalue->messageImprint, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   /* encode policy */

   RTXCTXTPUSHELEMNAME (pctxt, "policy");

   ll = asn1E_TSP_TSAPolicyId (pctxt, &pvalue->policy, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   /* encode version */

   RTXCTXTPUSHELEMNAME (pctxt, "version");

   ll = asn1E_TSP_TSTInfo_version (pctxt, &pvalue->version, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   ll0 += ll1;

   if (tagging == ASN1EXPL)
      ll0 = xe_tag_len (pctxt, TM_UNIV|TM_CONS|16, ll0);

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

EXTERN int asn1E_TSP_ESYAReqEx (OSCTXT* pctxt,
   ASN1T_TSP_ESYAReqEx *pvalue, ASN1TagType tagging)
{
   int ll;
   int ll0 = 0;
   int ll1 = 0;

   RTXCTXTPUSHTYPENAME (pctxt, "ESYAReqEx");

   /* encode encryptedMessageImprint */

   RTXCTXTPUSHELEMNAME (pctxt, "encryptedMessageImprint");

   ll = xe_octstr (pctxt, pvalue->encryptedMessageImprint.data, pvalue->encryptedMessageImprint.numocts, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   /* encode iv */

   RTXCTXTPUSHELEMNAME (pctxt, "iv");

   ll = xe_octstr (pctxt, pvalue->iv.data, pvalue->iv.numocts, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   /* encode iterationCount */

   RTXCTXTPUSHELEMNAME (pctxt, "iterationCount");

   ll = xe_integer (pctxt, &pvalue->iterationCount, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   /* encode salt */

   RTXCTXTPUSHELEMNAME (pctxt, "salt");

   ll = xe_octstr (pctxt, pvalue->salt.data, pvalue->salt.numocts, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   /* encode userid */

   RTXCTXTPUSHELEMNAME (pctxt, "userid");

   ll = xe_integer (pctxt, &pvalue->userid, ASN1EXPL);
   if (ll < 0) return LOG_RTERR (pctxt, ll);
   ll1 += ll;

   RTXCTXTPOPELEMNAME (pctxt);

   ll0 += ll1;

   if (tagging == ASN1EXPL)
      ll0 = xe_tag_len (pctxt, TM_UNIV|TM_CONS|16, ll0);

   RTXCTXTPOPTYPENAME (pctxt);

   return (ll0);
}

