/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.6, Date: 28-Feb-2014.
 */
#include "UsefulDefinitions.h"
#include "rtsrc/rtPrintToStream.h"
#include "rtxsrc/rtxCommon.h"

int asn1PrtToStrm_ID (OSCTXT *pctxt, const char* name, const ASN1T_ID* pvalue)
{
   rtPrintToStreamIndent (pctxt);
   rtPrintToStreamOID (pctxt, name, (ASN1OBJID*)pvalue);
   return 0;
}

int ASN1C_ID::toStream (const char* name)

{
   asn1PrtToStrm_ID (getCtxtPtr(), name, &msgData);
   return 0;
}

int ASN1C_ID::setPrintStream
(rtxPrintCallback strmCallback, void * pStrmInfo)

{
   return rtxSetPrintStream (getCtxtPtr(), strmCallback, pStrmInfo);
}

