/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.6, Date: 28-Feb-2014.
 */
#ifndef DERCMS_H
#define DERCMS_H

#include <stdio.h>
#include <stdlib.h>
#include "rtkey.h"
#include "rtbersrc/asn1BerCppTypes.h"
#include "rtsrc/rtPrintToStream.h"

#include "rtsrc/ASN1CSeqOfList.h"
#include "Explicit.h"

/**
 * Header file for ASN.1 module dercms
 */
/**************************************************************/
/*                                                            */
/*  SignatureValue                                            */
/*                                                            */
/**************************************************************/
/*
SignatureValue ::= OCTET STRING
*/
#define TV_DERCMS_SignatureValue	(TM_UNIV|TM_PRIM|4)

typedef ASN1TDynOctStr ASN1T_DERCMS_SignatureValue;

class EXTERN ASN1C_DERCMS_SignatureValue :
public ASN1CType
{
protected:
   ASN1T_DERCMS_SignatureValue& msgData;
public:
   ASN1C_DERCMS_SignatureValue (ASN1T_DERCMS_SignatureValue& data);
   ASN1C_DERCMS_SignatureValue (OSRTMessageBufferIF& msgBuf
      , ASN1T_DERCMS_SignatureValue& data);
   ASN1C_DERCMS_SignatureValue (OSRTContext &context
      , ASN1T_DERCMS_SignatureValue& data);
   ASN1C_DERCMS_SignatureValue (ASN1C_DERCMS_SignatureValue& original);
   virtual ~ASN1C_DERCMS_SignatureValue () {}
   inline ASN1T_DERCMS_SignatureValue& getData () { return msgData; }
   inline const ASN1T_DERCMS_SignatureValue& getData () const { return msgData; }

   // standard encode/decode methods (defined in ASN1CType base class):
   // int Encode ();
   // int Decode ();

   // stream encode/decode methods:
   int EncodeTo (OSRTMessageBufferIF& msgBuf);
   int DecodeFrom (OSRTMessageBufferIF& msgBuf);

   void MemFree ();
   void Print (const char* name = "SignatureValue");
   int toStream (const char* name = "SignatureValue");
   int setPrintStream (rtxPrintCallback strmCallback, void* pStrmInfo);
   OSBOOL Equals (ASN1T_DERCMS_SignatureValue* pCmpValue, 
      char* errBuff, OSSIZE errBuffSize);
   ASN1T_DERCMS_SignatureValue& getCopy (ASN1T_DERCMS_SignatureValue* pDstData = 0);
   ASN1T_DERCMS_SignatureValue* newCopy ();

   inline ASN1C_DERCMS_SignatureValue& operator= (ASN1C_DERCMS_SignatureValue& srcData) {
      srcData.getCopy (&msgData);
      return *this;
   }
} ;

EXTERN int asn1E_DERCMS_SignatureValue (OSCTXT* pctxt,
   ASN1T_DERCMS_SignatureValue *pvalue, ASN1TagType tagging);

EXTERN int asn1D_DERCMS_SignatureValue (OSCTXT* pctxt, 
   ASN1T_DERCMS_SignatureValue* pvalue, ASN1TagType tagging, int length);

EXTERN void asn1Print_DERCMS_SignatureValue 
   (const char* name, const ASN1T_DERCMS_SignatureValue* pvalue);

EXTERN int asn1PrtToStrm_DERCMS_SignatureValue (OSCTXT *pctxt, 
   const char* name, const ASN1T_DERCMS_SignatureValue* pvalue);

EXTERN ASN1T_DERCMS_SignatureValue* 
   new_ASN1T_DERCMS_SignatureValue (ASN1CType& ccobj);

EXTERN OSBOOL asn1Compare_DERCMS_SignatureValue (const char* name, 
   ASN1T_DERCMS_SignatureValue* pValue, 
   ASN1T_DERCMS_SignatureValue* pCmpValue, char* errBuff, OSSIZE errBuffSize);

EXTERN void asn1Copy_DERCMS_SignatureValue (OSCTXT* pctxt, 
   ASN1T_DERCMS_SignatureValue* pSrcValue, ASN1T_DERCMS_SignatureValue*
    pDstValue);

EXTERN void asn1Init_DERCMS_SignatureValue (ASN1T_DERCMS_SignatureValue* pvalue);

EXTERN void asn1Free_DERCMS_SignatureValue (OSCTXT *pctxt, 
   ASN1T_DERCMS_SignatureValue* pvalue);

/**************************************************************/
/*                                                            */
/*  AuthAttributes                                            */
/*                                                            */
/**************************************************************/
/*
 DER Cryptographic Message Syntax

AuthAttributes ::= SET (SIZE (1..MAX)) OF Attribute
*/
#define TV_DERCMS_AuthAttributes	(TM_UNIV|TM_CONS|17)

class EXTERN ASN1C_DERCMS_AuthAttributes;

/* List of ASN1T_EXP_Attribute */
typedef struct EXTERN ASN1T_DERCMS_AuthAttributes : public ASN1TPDUSeqOfList {
   ~ASN1T_DERCMS_AuthAttributes();
} ASN1T_DERCMS_AuthAttributes;

class EXTERN ASN1C_DERCMS_AuthAttributes :
public ASN1CSeqOfList
{
protected:
   ASN1T_DERCMS_AuthAttributes& msgData;
public:
   ASN1C_DERCMS_AuthAttributes (ASN1T_DERCMS_AuthAttributes& data);
   ASN1C_DERCMS_AuthAttributes (OSRTMessageBufferIF& msgBuf
      , ASN1T_DERCMS_AuthAttributes& data);
   ASN1C_DERCMS_AuthAttributes (ASN1CType& ccobj
      , ASN1T_DERCMS_AuthAttributes& data);
   ASN1C_DERCMS_AuthAttributes (OSRTContext &context
      , ASN1T_DERCMS_AuthAttributes& data);
   ASN1C_DERCMS_AuthAttributes (ASN1C_DERCMS_AuthAttributes& original);
   virtual ~ASN1C_DERCMS_AuthAttributes () {}
   inline ASN1T_DERCMS_AuthAttributes& getData () { return msgData; }
   inline const ASN1T_DERCMS_AuthAttributes& getData () const { return msgData; }

   // standard encode/decode methods (defined in ASN1CType base class):
   // int Encode ();
   // int Decode ();

   // stream encode/decode methods:
   int EncodeTo (OSRTMessageBufferIF& msgBuf);
   int DecodeFrom (OSRTMessageBufferIF& msgBuf);

   void MemFree ();
   void Print (const char* name = "AuthAttributes");
   int toStream (const char* name = "AuthAttributes");
   int setPrintStream (rtxPrintCallback strmCallback, void* pStrmInfo);
   OSBOOL Equals (ASN1T_DERCMS_AuthAttributes* pCmpValue, 
      char* errBuff, OSSIZE errBuffSize);
   ASN1T_DERCMS_AuthAttributes& getCopy (ASN1T_DERCMS_AuthAttributes* pDstData = 0);
   ASN1T_DERCMS_AuthAttributes* newCopy ();

   ASN1C_DERCMS_AuthAttributes& operator= (ASN1C_DERCMS_AuthAttributes& srcData);
   void Append (ASN1T_EXP_Attribute* elem);
   ASN1T_EXP_Attribute* NewElement ();
   ASN1T_EXP_Attribute* AppendNewElement ();
} ;

EXTERN int asn1E_DERCMS_AuthAttributes (OSCTXT* pctxt,
   ASN1T_DERCMS_AuthAttributes *pvalue, ASN1TagType tagging);

EXTERN int asn1D_DERCMS_AuthAttributes (OSCTXT* pctxt, 
   ASN1T_DERCMS_AuthAttributes* pvalue, ASN1TagType tagging, int length);

EXTERN void asn1Print_DERCMS_AuthAttributes 
   (const char* name, const ASN1T_DERCMS_AuthAttributes* pvalue);

EXTERN int asn1PrtToStrm_DERCMS_AuthAttributes (OSCTXT *pctxt, 
   const char* name, const ASN1T_DERCMS_AuthAttributes* pvalue);

EXTERN OSBOOL asn1Compare_DERCMS_AuthAttributes (const char* name, 
   ASN1T_DERCMS_AuthAttributes* pValue, 
   ASN1T_DERCMS_AuthAttributes* pCmpValue, char* errBuff, OSSIZE errBuffSize);

EXTERN void asn1Copy_DERCMS_AuthAttributes (OSCTXT* pctxt, 
   ASN1T_DERCMS_AuthAttributes* pSrcValue, ASN1T_DERCMS_AuthAttributes*
    pDstValue);

EXTERN void asn1Init_DERCMS_AuthAttributes (ASN1T_DERCMS_AuthAttributes* pvalue);

EXTERN void asn1Free_DERCMS_AuthAttributes (OSCTXT *pctxt, 
   ASN1T_DERCMS_AuthAttributes* pvalue);

/**************************************************************/
/*                                                            */
/*  SignedAttributes                                          */
/*                                                            */
/**************************************************************/
/*
SignedAttributes ::= SET (SIZE (1..MAX)) OF Attribute
*/
#define TV_DERCMS_SignedAttributes	(TM_UNIV|TM_CONS|17)

class EXTERN ASN1C_DERCMS_SignedAttributes;

/* List of ASN1T_EXP_Attribute */
typedef struct EXTERN ASN1T_DERCMS_SignedAttributes : public ASN1TPDUSeqOfList {
   ~ASN1T_DERCMS_SignedAttributes();
} ASN1T_DERCMS_SignedAttributes;

class EXTERN ASN1C_DERCMS_SignedAttributes :
public ASN1CSeqOfList
{
protected:
   ASN1T_DERCMS_SignedAttributes& msgData;
public:
   ASN1C_DERCMS_SignedAttributes (ASN1T_DERCMS_SignedAttributes& data);
   ASN1C_DERCMS_SignedAttributes (OSRTMessageBufferIF& msgBuf
      , ASN1T_DERCMS_SignedAttributes& data);
   ASN1C_DERCMS_SignedAttributes (ASN1CType& ccobj
      , ASN1T_DERCMS_SignedAttributes& data);
   ASN1C_DERCMS_SignedAttributes (OSRTContext &context
      , ASN1T_DERCMS_SignedAttributes& data);
   ASN1C_DERCMS_SignedAttributes (ASN1C_DERCMS_SignedAttributes& original);
   virtual ~ASN1C_DERCMS_SignedAttributes () {}
   inline ASN1T_DERCMS_SignedAttributes& getData () { return msgData; }
   inline const ASN1T_DERCMS_SignedAttributes& getData () const { return msgData; }

   // standard encode/decode methods (defined in ASN1CType base class):
   // int Encode ();
   // int Decode ();

   // stream encode/decode methods:
   int EncodeTo (OSRTMessageBufferIF& msgBuf);
   int DecodeFrom (OSRTMessageBufferIF& msgBuf);

   void MemFree ();
   void Print (const char* name = "SignedAttributes");
   int toStream (const char* name = "SignedAttributes");
   int setPrintStream (rtxPrintCallback strmCallback, void* pStrmInfo);
   OSBOOL Equals (ASN1T_DERCMS_SignedAttributes* pCmpValue, 
      char* errBuff, OSSIZE errBuffSize);
   ASN1T_DERCMS_SignedAttributes& getCopy (ASN1T_DERCMS_SignedAttributes* pDstData = 0);
   ASN1T_DERCMS_SignedAttributes* newCopy ();

   ASN1C_DERCMS_SignedAttributes& operator= (ASN1C_DERCMS_SignedAttributes& srcData);
   void Append (ASN1T_EXP_Attribute* elem);
   ASN1T_EXP_Attribute* NewElement ();
   ASN1T_EXP_Attribute* AppendNewElement ();
} ;

EXTERN int asn1E_DERCMS_SignedAttributes (OSCTXT* pctxt,
   ASN1T_DERCMS_SignedAttributes *pvalue, ASN1TagType tagging);

EXTERN int asn1D_DERCMS_SignedAttributes (OSCTXT* pctxt, 
   ASN1T_DERCMS_SignedAttributes* pvalue, ASN1TagType tagging, int length);

EXTERN void asn1Print_DERCMS_SignedAttributes 
   (const char* name, const ASN1T_DERCMS_SignedAttributes* pvalue);

EXTERN int asn1PrtToStrm_DERCMS_SignedAttributes (OSCTXT *pctxt, 
   const char* name, const ASN1T_DERCMS_SignedAttributes* pvalue);

EXTERN OSBOOL asn1Compare_DERCMS_SignedAttributes (const char* name, 
   ASN1T_DERCMS_SignedAttributes* pValue, 
   ASN1T_DERCMS_SignedAttributes* pCmpValue, char* errBuff, OSSIZE errBuffSize);

EXTERN void asn1Copy_DERCMS_SignedAttributes (OSCTXT* pctxt, 
   ASN1T_DERCMS_SignedAttributes* pSrcValue, ASN1T_DERCMS_SignedAttributes*
    pDstValue);

EXTERN void asn1Init_DERCMS_SignedAttributes (ASN1T_DERCMS_SignedAttributes* pvalue);

EXTERN void asn1Free_DERCMS_SignedAttributes (OSCTXT *pctxt, 
   ASN1T_DERCMS_SignedAttributes* pvalue);

/**************************************************************/
/*                                                            */
/*  ECC_CMS_SharedInfo                                        */
/*                                                            */
/**************************************************************/
/*
 ECC CMS yapisi. 04.09.2009 da eklendi

ECC_CMS_SharedInfo ::= SEQUENCE {
   keyInfo AlgorithmIdentifier,
   entityUInfo [0] EXPLICIT OCTET STRING OPTIONAL,
   suppPubInfo [2] EXPLICIT OCTET STRING
}
*/
#define TV_DERCMS_ECC_CMS_SharedInfo	(TM_UNIV|TM_CONS|16)

class EXTERN ASN1C_DERCMS_ECC_CMS_SharedInfo;

struct EXTERN ASN1T_DERCMS_ECC_CMS_SharedInfo : public ASN1TPDU {
   struct {
      unsigned entityUInfoPresent : 1;
   } m;
   ASN1T_EXP_AlgorithmIdentifier keyInfo;
   ASN1TDynOctStr entityUInfo;
   ASN1TDynOctStr suppPubInfo;
   ASN1T_DERCMS_ECC_CMS_SharedInfo ();
   ASN1T_DERCMS_ECC_CMS_SharedInfo (ASN1C_DERCMS_ECC_CMS_SharedInfo& srcData);
   ~ASN1T_DERCMS_ECC_CMS_SharedInfo ();
} ;

class EXTERN ASN1C_DERCMS_ECC_CMS_SharedInfo :
public ASN1CType
{
protected:
   ASN1T_DERCMS_ECC_CMS_SharedInfo& msgData;
public:
   ASN1C_DERCMS_ECC_CMS_SharedInfo (ASN1T_DERCMS_ECC_CMS_SharedInfo& data);
   ASN1C_DERCMS_ECC_CMS_SharedInfo (OSRTMessageBufferIF& msgBuf
      , ASN1T_DERCMS_ECC_CMS_SharedInfo& data);
   ASN1C_DERCMS_ECC_CMS_SharedInfo (OSRTContext &context
      , ASN1T_DERCMS_ECC_CMS_SharedInfo& data);
   ASN1C_DERCMS_ECC_CMS_SharedInfo (ASN1C_DERCMS_ECC_CMS_SharedInfo& original);
   virtual ~ASN1C_DERCMS_ECC_CMS_SharedInfo () {}
   inline ASN1T_DERCMS_ECC_CMS_SharedInfo& getData () { return msgData; }
   inline const ASN1T_DERCMS_ECC_CMS_SharedInfo& getData () const { return msgData; }

   // standard encode/decode methods (defined in ASN1CType base class):
   // int Encode ();
   // int Decode ();

   // stream encode/decode methods:
   int EncodeTo (OSRTMessageBufferIF& msgBuf);
   int DecodeFrom (OSRTMessageBufferIF& msgBuf);

   void MemFree ();
   void Print (const char* name = "ECC_CMS_SharedInfo");
   int toStream (const char* name = "ECC_CMS_SharedInfo");
   int setPrintStream (rtxPrintCallback strmCallback, void* pStrmInfo);
   OSBOOL Equals (ASN1T_DERCMS_ECC_CMS_SharedInfo* pCmpValue, 
      char* errBuff, OSSIZE errBuffSize);
   ASN1T_DERCMS_ECC_CMS_SharedInfo& getCopy (ASN1T_DERCMS_ECC_CMS_SharedInfo* pDstData = 0);
   ASN1T_DERCMS_ECC_CMS_SharedInfo* newCopy ();

   inline ASN1C_DERCMS_ECC_CMS_SharedInfo& operator= (ASN1C_DERCMS_ECC_CMS_SharedInfo& srcData) {
      srcData.getCopy (&msgData);
      return *this;
   }
} ;

EXTERN int asn1E_DERCMS_ECC_CMS_SharedInfo (OSCTXT* pctxt,
   ASN1T_DERCMS_ECC_CMS_SharedInfo *pvalue, ASN1TagType tagging);

EXTERN int asn1D_DERCMS_ECC_CMS_SharedInfo (OSCTXT* pctxt, 
   ASN1T_DERCMS_ECC_CMS_SharedInfo* pvalue, ASN1TagType tagging, int length);

EXTERN void asn1Print_DERCMS_ECC_CMS_SharedInfo 
   (const char* name, const ASN1T_DERCMS_ECC_CMS_SharedInfo* pvalue);

EXTERN int asn1PrtToStrm_DERCMS_ECC_CMS_SharedInfo (OSCTXT *pctxt, 
   const char* name, const ASN1T_DERCMS_ECC_CMS_SharedInfo* pvalue);

EXTERN ASN1T_DERCMS_ECC_CMS_SharedInfo* 
   new_ASN1T_DERCMS_ECC_CMS_SharedInfo (ASN1CType& ccobj);

EXTERN OSBOOL asn1Compare_DERCMS_ECC_CMS_SharedInfo (const char* name, 
   ASN1T_DERCMS_ECC_CMS_SharedInfo* pValue, 
   ASN1T_DERCMS_ECC_CMS_SharedInfo* pCmpValue, char* errBuff, 
   OSSIZE errBuffSize);

EXTERN void asn1Copy_DERCMS_ECC_CMS_SharedInfo (OSCTXT* pctxt, 
   ASN1T_DERCMS_ECC_CMS_SharedInfo* pSrcValue, ASN1T_DERCMS_ECC_CMS_SharedInfo*
    pDstValue);

EXTERN void asn1Init_DERCMS_ECC_CMS_SharedInfo (
   ASN1T_DERCMS_ECC_CMS_SharedInfo* pvalue);

EXTERN void asn1Free_DERCMS_ECC_CMS_SharedInfo (OSCTXT *pctxt, 
   ASN1T_DERCMS_ECC_CMS_SharedInfo* pvalue);

#endif
