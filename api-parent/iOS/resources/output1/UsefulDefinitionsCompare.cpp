/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.6, Date: 20-Feb-2014.
 */
#include "UsefulDefinitions.h"
#include "rtsrc/rtCompare.h"
#include "rtxsrc/rtxCommon.h"

OSBOOL asn1Compare_ID (const char* name, ASN1T_ID* pValue, 
   ASN1T_ID* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   return rtCmpOID (name, (ASN1OBJID*)pValue, (ASN1OBJID*)pCmpValue, errBuff, errBuffSize);
}

OSBOOL ASN1C_ID::Equals (ASN1T_ID* pCmpValue, char* errBuff, OSSIZE errBuffSize)
{
   if (asn1Compare_ID ("ID", &msgData, pCmpValue, errBuff, errBuffSize))
   {
      return TRUE;
   }
   return FALSE;
}

