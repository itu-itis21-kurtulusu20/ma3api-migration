/**
 * This file was generated by the Objective Systems ASN1C Compiler
 * (http://www.obj-sys.com).  Version: 6.6.6, Date: 20-Feb-2014.
 */
#include <new>
#include "esya.h"
#include "rtxsrc/rtxCommon.h"

ASN1OBJID ESYA_id_uekae = {
   7,
   { 1, 3, 6, 1, 4, 1, 11311 }
} ;
ASN1OBJID ESYA_id_esya = {
   8,
   { 1, 3, 6, 1, 4, 1, 11311, 10 }
} ;
ASN1OBJID ESYA_id_servisler = {
   9,
   { 1, 3, 6, 1, 4, 1, 11311, 10, 1 }
} ;
ASN1OBJID ESYA_id_refno = {
   10,
   { 1, 3, 6, 1, 4, 1, 11311, 10, 1, 1 }
} ;
ASN1OBJID ESYA_id_kartSeriNo = {
   10,
   { 1, 3, 6, 1, 4, 1, 11311, 10, 1, 2 }
} ;
ASN1OBJID ESYA_id_kartUreticiNo = {
   10,
   { 1, 3, 6, 1, 4, 1, 11311, 10, 1, 3 }
} ;
ASN1OBJID ESYA_id_sablonNo = {
   10,
   { 1, 3, 6, 1, 4, 1, 11311, 10, 1, 4 }
} ;
ASN1OBJID ESYA_id_ESYAyonetici = {
   10,
   { 1, 3, 6, 1, 4, 1, 11311, 10, 2, 1 }
} ;
ASN1OBJID ESYA_id_ESYAkayitci = {
   10,
   { 1, 3, 6, 1, 4, 1, 11311, 10, 2, 2 }
} ;
ASN1OBJID ESYA_id_istemci = {
   9,
   { 1, 3, 6, 1, 4, 1, 11311, 10, 3 }
} ;
ASN1OBJID ESYA_id_kripto = {
   10,
   { 1, 3, 6, 1, 4, 1, 11311, 10, 3, 1 }
} ;
ASN1OBJID ESYA_id_esyapwri = {
   11,
   { 1, 3, 6, 1, 4, 1, 11311, 10, 3, 1, 1 }
} ;
ASN1OBJID ESYA_id_esyafiri = {
   11,
   { 1, 3, 6, 1, 4, 1, 11311, 10, 3, 1, 2 }
} ;
ASN1OBJID ESYA_id_esyagrri = {
   11,
   { 1, 3, 6, 1, 4, 1, 11311, 10, 3, 1, 3 }
} ;

ASN1T_ESYA_ESYAGizlilikBilgisi* 
   new_ASN1T_ESYA_ESYAGizlilikBilgisi (ASN1CType& ccobj)
{
   void* pdata = ccobj.memAlloc (sizeof(ASN1T_ESYA_ESYAGizlilikBilgisi));
   if (0 == pdata) return 0;
   else return new (pdata) ASN1T_ESYA_ESYAGizlilikBilgisi;
}

ASN1C_ESYA_ESYAGizlilikBilgisi::ASN1C_ESYA_ESYAGizlilikBilgisi
    (ASN1T_ESYA_ESYAGizlilikBilgisi& data) : ASN1CType(), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}

ASN1C_ESYA_ESYAGizlilikBilgisi::ASN1C_ESYA_ESYAGizlilikBilgisi (
   OSRTMessageBufferIF& msgBuf, ASN1T_ESYA_ESYAGizlilikBilgisi& data) : 
   ASN1CType(msgBuf), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}
ASN1C_ESYA_ESYAGizlilikBilgisi::ASN1C_ESYA_ESYAGizlilikBilgisi (
   OSRTContext &context, ASN1T_ESYA_ESYAGizlilikBilgisi& data) : 
   ASN1CType(context), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}


int ASN1C_ESYA_ESYAGizlilikBilgisi::EncodeTo (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   return asn1E_ESYA_ESYAGizlilikBilgisi (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL);
}

int ASN1C_ESYA_ESYAGizlilikBilgisi::DecodeFrom (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   return asn1D_ESYA_ESYAGizlilikBilgisi (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL, 0);
}

ASN1T_ESYA_ESYASifreliVeri* new_ASN1T_ESYA_ESYASifreliVeri (ASN1CType& ccobj)
{
   void* pdata = ccobj.memAlloc (sizeof(ASN1T_ESYA_ESYASifreliVeri));
   if (0 == pdata) return 0;
   else return new (pdata) ASN1T_ESYA_ESYASifreliVeri;
}

ASN1C_ESYA_ESYASifreliVeri::ASN1C_ESYA_ESYASifreliVeri
    (ASN1T_ESYA_ESYASifreliVeri& data) : ASN1CType(), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}

ASN1C_ESYA_ESYASifreliVeri::ASN1C_ESYA_ESYASifreliVeri (
   OSRTMessageBufferIF& msgBuf, ASN1T_ESYA_ESYASifreliVeri& data) : 
   ASN1CType(msgBuf), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}
ASN1C_ESYA_ESYASifreliVeri::ASN1C_ESYA_ESYASifreliVeri (OSRTContext &context
   , ASN1T_ESYA_ESYASifreliVeri& data) : ASN1CType(context), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}


ASN1T_ESYA_ESYASifreliVeri::ASN1T_ESYA_ESYASifreliVeri ()
{
}

ASN1T_ESYA_ESYASifreliVeri::~ASN1T_ESYA_ESYASifreliVeri ()
{
   OSCTXT* pctxt = mpContext.getCtxtPtr();
   if (pctxt != 0 && !rtxMemHeapIsEmpty (pctxt)) {
      asn1Free_ESYA_ESYASifreliVeri (pctxt, this);
   }
}

int ASN1C_ESYA_ESYASifreliVeri::EncodeTo (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   return asn1E_ESYA_ESYASifreliVeri (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL);
}

int ASN1C_ESYA_ESYASifreliVeri::DecodeFrom (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   new (&msgData) ASN1T_ESYA_ESYASifreliVeri;
   msgData.setContext (msgBuf.getContext());
   return asn1D_ESYA_ESYASifreliVeri (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL, 0);
}

void asn1Init_ESYA_ESYASifreliVeri (ASN1T_ESYA_ESYASifreliVeri* pvalue)
{
   if (0 == pvalue) return;
   new (pvalue) ASN1T_ESYA_ESYASifreliVeri;
}

void asn1Free_ESYA_ESYASifreliVeri (OSCTXT *pctxt, 
   ASN1T_ESYA_ESYASifreliVeri* pvalue)
{
   if (0 == pvalue) return;
   if (pvalue->sifreliAnahtar.numocts > 0) {
      rtxMemFreePtr (pctxt, (void*)pvalue->sifreliAnahtar.data);
      pvalue->sifreliAnahtar.numocts = 0;
      pvalue->sifreliAnahtar.data = 0;
   }
   if (pvalue->sifreliVeri.numocts > 0) {
      rtxMemFreePtr (pctxt, (void*)pvalue->sifreliVeri.data);
      pvalue->sifreliVeri.numocts = 0;
      pvalue->sifreliVeri.data = 0;
   }
}

void ASN1C_ESYA_ESYASifreliVeri::MemFree ()
{
   asn1Free_ESYA_ESYASifreliVeri (getCtxtPtr(), &msgData);
}

ASN1T_ESYA_ESYASimetrikAnahtar* 
   new_ASN1T_ESYA_ESYASimetrikAnahtar (ASN1CType& ccobj)
{
   void* pdata = ccobj.memAlloc (sizeof(ASN1T_ESYA_ESYASimetrikAnahtar));
   if (0 == pdata) return 0;
   else return new (pdata) ASN1T_ESYA_ESYASimetrikAnahtar;
}

ASN1C_ESYA_ESYASimetrikAnahtar::ASN1C_ESYA_ESYASimetrikAnahtar
    (ASN1T_ESYA_ESYASimetrikAnahtar& data) : ASN1CType(), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}

ASN1C_ESYA_ESYASimetrikAnahtar::ASN1C_ESYA_ESYASimetrikAnahtar (
   OSRTMessageBufferIF& msgBuf, ASN1T_ESYA_ESYASimetrikAnahtar& data) : 
   ASN1CType(msgBuf), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}
ASN1C_ESYA_ESYASimetrikAnahtar::ASN1C_ESYA_ESYASimetrikAnahtar (
   OSRTContext &context, ASN1T_ESYA_ESYASimetrikAnahtar& data) : 
   ASN1CType(context), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}


ASN1T_ESYA_ESYASimetrikAnahtar::ASN1T_ESYA_ESYASimetrikAnahtar ()
{
}

ASN1T_ESYA_ESYASimetrikAnahtar::~ASN1T_ESYA_ESYASimetrikAnahtar ()
{
   OSCTXT* pctxt = mpContext.getCtxtPtr();
   if (pctxt != 0 && !rtxMemHeapIsEmpty (pctxt)) {
      asn1Free_ESYA_ESYASimetrikAnahtar (pctxt, this);
   }
}

int ASN1C_ESYA_ESYASimetrikAnahtar::EncodeTo (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   return asn1E_ESYA_ESYASimetrikAnahtar (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL);
}

int ASN1C_ESYA_ESYASimetrikAnahtar::DecodeFrom (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   new (&msgData) ASN1T_ESYA_ESYASimetrikAnahtar;
   msgData.setContext (msgBuf.getContext());
   return asn1D_ESYA_ESYASimetrikAnahtar (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL, 0);
}

void asn1Init_ESYA_ESYASimetrikAnahtar (ASN1T_ESYA_ESYASimetrikAnahtar* pvalue)
{
   if (0 == pvalue) return;
   new (pvalue) ASN1T_ESYA_ESYASimetrikAnahtar;
}

void asn1Free_ESYA_ESYASimetrikAnahtar (OSCTXT *pctxt, 
   ASN1T_ESYA_ESYASimetrikAnahtar* pvalue)
{
   if (0 == pvalue) return;
   asn1Free_EXP_AlgorithmIdentifier (pctxt, &pvalue->algorithm);
   if (pvalue->simetrikAnahtar.numocts > 0) {
      rtxMemFreePtr (pctxt, (void*)pvalue->simetrikAnahtar.data);
      pvalue->simetrikAnahtar.numocts = 0;
      pvalue->simetrikAnahtar.data = 0;
   }
}

void ASN1C_ESYA_ESYASimetrikAnahtar::MemFree ()
{
   asn1Free_ESYA_ESYASimetrikAnahtar (getCtxtPtr(), &msgData);
}

ASN1T_ESYA_ESYASimImzAnahtari* 
   new_ASN1T_ESYA_ESYASimImzAnahtari (ASN1CType& ccobj)
{
   void* pdata = ccobj.memAlloc (sizeof(ASN1T_ESYA_ESYASimImzAnahtari));
   if (0 == pdata) return 0;
   else return new (pdata) ASN1T_ESYA_ESYASimImzAnahtari;
}

ASN1C_ESYA_ESYASimImzAnahtari::ASN1C_ESYA_ESYASimImzAnahtari
    (ASN1T_ESYA_ESYASimImzAnahtari& data) : ASN1CType(), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}

ASN1C_ESYA_ESYASimImzAnahtari::ASN1C_ESYA_ESYASimImzAnahtari (
   OSRTMessageBufferIF& msgBuf, ASN1T_ESYA_ESYASimImzAnahtari& data) : 
   ASN1CType(msgBuf), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}
ASN1C_ESYA_ESYASimImzAnahtari::ASN1C_ESYA_ESYASimImzAnahtari (
   OSRTContext &context, ASN1T_ESYA_ESYASimImzAnahtari& data) : 
   ASN1CType(context), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}


ASN1T_ESYA_ESYASimImzAnahtari::ASN1T_ESYA_ESYASimImzAnahtari ()
{
}

ASN1T_ESYA_ESYASimImzAnahtari::~ASN1T_ESYA_ESYASimImzAnahtari ()
{
   OSCTXT* pctxt = mpContext.getCtxtPtr();
   if (pctxt != 0 && !rtxMemHeapIsEmpty (pctxt)) {
      asn1Free_ESYA_ESYASimImzAnahtari (pctxt, this);
   }
}

int ASN1C_ESYA_ESYASimImzAnahtari::EncodeTo (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   return asn1E_ESYA_ESYASimImzAnahtari (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL);
}

int ASN1C_ESYA_ESYASimImzAnahtari::DecodeFrom (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   new (&msgData) ASN1T_ESYA_ESYASimImzAnahtari;
   msgData.setContext (msgBuf.getContext());
   return asn1D_ESYA_ESYASimImzAnahtari (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL, 0);
}

void asn1Init_ESYA_ESYASimImzAnahtari (ASN1T_ESYA_ESYASimImzAnahtari* pvalue)
{
   if (0 == pvalue) return;
   new (pvalue) ASN1T_ESYA_ESYASimImzAnahtari;
}

void asn1Free_ESYA_ESYASimImzAnahtari (OSCTXT *pctxt, 
   ASN1T_ESYA_ESYASimImzAnahtari* pvalue)
{
   if (0 == pvalue) return;
   asn1Free_EXP_AlgorithmIdentifier (pctxt, &pvalue->hashAlg);
   asn1Free_EXP_AlgorithmIdentifier (pctxt, &pvalue->simetrikAlg);
   if (pvalue->anahtarBytes.numocts > 0) {
      rtxMemFreePtr (pctxt, (void*)pvalue->anahtarBytes.data);
      pvalue->anahtarBytes.numocts = 0;
      pvalue->anahtarBytes.data = 0;
   }
}

void ASN1C_ESYA_ESYASimImzAnahtari::MemFree ()
{
   asn1Free_ESYA_ESYASimImzAnahtari (getCtxtPtr(), &msgData);
}

ASN1C_ESYA_ESYASimImzAnahtarlari::ASN1C_ESYA_ESYASimImzAnahtarlari
    (ASN1T_ESYA_ESYASimImzAnahtarlari& data) : ASN1CSeqOfList(data)
   , msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}

ASN1C_ESYA_ESYASimImzAnahtarlari::ASN1C_ESYA_ESYASimImzAnahtarlari (
   OSRTMessageBufferIF& msgBuf, ASN1T_ESYA_ESYASimImzAnahtarlari& data) : 
   ASN1CSeqOfList(msgBuf, data), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}
ASN1C_ESYA_ESYASimImzAnahtarlari::ASN1C_ESYA_ESYASimImzAnahtarlari (
   OSRTContext &context, ASN1T_ESYA_ESYASimImzAnahtarlari& data) : 
   ASN1CSeqOfList(context, data), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}


ASN1C_ESYA_ESYASimImzAnahtarlari::
   ASN1C_ESYA_ESYASimImzAnahtarlari (ASN1CType& ccobj
   , ASN1T_ESYA_ESYASimImzAnahtarlari& data) :
   ASN1CSeqOfList(ccobj, data), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}

void ASN1C_ESYA_ESYASimImzAnahtarlari::Append (ASN1T_ESYA_ESYASimImzAnahtari* elem)
{
   append ((void*)elem);
}

ASN1T_ESYA_ESYASimImzAnahtari* ASN1C_ESYA_ESYASimImzAnahtarlari::NewElement ()
{
   void* pdata = memAlloc (sizeof(ASN1T_ESYA_ESYASimImzAnahtari));

   if (0 == pdata) return 0;
   else return new (pdata) ASN1T_ESYA_ESYASimImzAnahtari;
}

ASN1T_ESYA_ESYASimImzAnahtari* ASN1C_ESYA_ESYASimImzAnahtarlari::AppendNewElement ()
{
   ASN1T_ESYA_ESYASimImzAnahtari* pdata = NewElement();
   if (0 != pdata) {
      Append (pdata);
   }
   return pdata;
}

ASN1T_ESYA_ESYASimImzAnahtarlari::~ASN1T_ESYA_ESYASimImzAnahtarlari ()
{
   OSCTXT* pctxt = mpContext.getCtxtPtr();
   if (pctxt != 0 && !rtxMemHeapIsEmpty (pctxt)) {
      asn1Free_ESYA_ESYASimImzAnahtarlari (pctxt, this);
   }
}

int ASN1C_ESYA_ESYASimImzAnahtarlari::EncodeTo (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   return asn1E_ESYA_ESYASimImzAnahtarlari (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL);
}

int ASN1C_ESYA_ESYASimImzAnahtarlari::DecodeFrom (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   new (&msgData) ASN1T_ESYA_ESYASimImzAnahtarlari;
   msgData.setContext (msgBuf.getContext());
   return asn1D_ESYA_ESYASimImzAnahtarlari (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL, 0);
}

void asn1Init_ESYA_ESYASimImzAnahtarlari (
   ASN1T_ESYA_ESYASimImzAnahtarlari* pvalue)
{
   if (0 == pvalue) return;
   new (pvalue) ASN1T_ESYA_ESYASimImzAnahtarlari;
   rtxDListFastInit (pvalue);
}

void asn1Free_ESYA_ESYASimImzAnahtarlari (OSCTXT *pctxt, 
   ASN1T_ESYA_ESYASimImzAnahtarlari* pvalue)
{
   if (0 == pvalue) return;
   { ASN1T_ESYA_ESYASimImzAnahtari* pdata;
   OSRTDListNode* pnode = pvalue->head;
   while (0 != pnode) {
      pdata = (ASN1T_ESYA_ESYASimImzAnahtari*)pnode->data;
      asn1Free_ESYA_ESYASimImzAnahtari (pctxt, pdata);
      pnode = pnode->next;
   }
   rtxDListFreeAll (pctxt, pvalue);
   }
}

void ASN1C_ESYA_ESYASimImzAnahtarlari::MemFree ()
{
   asn1Free_ESYA_ESYASimImzAnahtarlari (getCtxtPtr(), &msgData);
}

ASN1T_ESYA_ESYAVTImza* new_ASN1T_ESYA_ESYAVTImza (ASN1CType& ccobj)
{
   void* pdata = ccobj.memAlloc (sizeof(ASN1T_ESYA_ESYAVTImza));
   if (0 == pdata) return 0;
   else return new (pdata) ASN1T_ESYA_ESYAVTImza;
}

ASN1C_ESYA_ESYAVTImza::ASN1C_ESYA_ESYAVTImza (ASN1T_ESYA_ESYAVTImza& data) : 
   ASN1CType(), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}

ASN1C_ESYA_ESYAVTImza::ASN1C_ESYA_ESYAVTImza (OSRTMessageBufferIF& msgBuf
   , ASN1T_ESYA_ESYAVTImza& data) : ASN1CType(msgBuf), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}
ASN1C_ESYA_ESYAVTImza::ASN1C_ESYA_ESYAVTImza (OSRTContext &context
   , ASN1T_ESYA_ESYAVTImza& data) : ASN1CType(context), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}


ASN1T_ESYA_ESYAVTImza::ASN1T_ESYA_ESYAVTImza ()
{
}

ASN1T_ESYA_ESYAVTImza::~ASN1T_ESYA_ESYAVTImza ()
{
   OSCTXT* pctxt = mpContext.getCtxtPtr();
   if (pctxt != 0 && !rtxMemHeapIsEmpty (pctxt)) {
      asn1Free_ESYA_ESYAVTImza (pctxt, this);
   }
}

int ASN1C_ESYA_ESYAVTImza::EncodeTo (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   return asn1E_ESYA_ESYAVTImza (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL);
}

int ASN1C_ESYA_ESYAVTImza::DecodeFrom (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   new (&msgData) ASN1T_ESYA_ESYAVTImza;
   msgData.setContext (msgBuf.getContext());
   return asn1D_ESYA_ESYAVTImza (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL, 0);
}

void asn1Init_ESYA_ESYAVTImza (ASN1T_ESYA_ESYAVTImza* pvalue)
{
   if (0 == pvalue) return;
   new (pvalue) ASN1T_ESYA_ESYAVTImza;
}

void asn1Free_ESYA_ESYAVTImza (OSCTXT *pctxt, ASN1T_ESYA_ESYAVTImza* pvalue)
{
   if (0 == pvalue) return;
   if (pvalue->imzaliVeri.numocts > 0) {
      rtxMemFreePtr (pctxt, (void*)pvalue->imzaliVeri.data);
      pvalue->imzaliVeri.numocts = 0;
      pvalue->imzaliVeri.data = 0;
   }
}

void ASN1C_ESYA_ESYAVTImza::MemFree ()
{
   asn1Free_ESYA_ESYAVTImza (getCtxtPtr(), &msgData);
}

ASN1T_ESYA_ESYAPasswordRecipientInfo* 
   new_ASN1T_ESYA_ESYAPasswordRecipientInfo (ASN1CType& ccobj)
{
   void* pdata = ccobj.memAlloc (sizeof(ASN1T_ESYA_ESYAPasswordRecipientInfo));
   if (0 == pdata) return 0;
   else return new (pdata) ASN1T_ESYA_ESYAPasswordRecipientInfo;
}

ASN1C_ESYA_ESYAPasswordRecipientInfo::ASN1C_ESYA_ESYAPasswordRecipientInfo
    (ASN1T_ESYA_ESYAPasswordRecipientInfo& data) : ASN1CType(), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}

ASN1C_ESYA_ESYAPasswordRecipientInfo::ASN1C_ESYA_ESYAPasswordRecipientInfo (
   OSRTMessageBufferIF& msgBuf, ASN1T_ESYA_ESYAPasswordRecipientInfo& data) : 
   ASN1CType(msgBuf), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}
ASN1C_ESYA_ESYAPasswordRecipientInfo::ASN1C_ESYA_ESYAPasswordRecipientInfo (
   OSRTContext &context, ASN1T_ESYA_ESYAPasswordRecipientInfo& data) : 
   ASN1CType(context), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}


ASN1T_ESYA_ESYAPasswordRecipientInfo::ASN1T_ESYA_ESYAPasswordRecipientInfo ()
{
   m.keyDerivationAlgorithmPresent = 0;
}

ASN1T_ESYA_ESYAPasswordRecipientInfo::~ASN1T_ESYA_ESYAPasswordRecipientInfo ()
{
   OSCTXT* pctxt = mpContext.getCtxtPtr();
   if (pctxt != 0 && !rtxMemHeapIsEmpty (pctxt)) {
      asn1Free_ESYA_ESYAPasswordRecipientInfo (pctxt, this);
   }
}

int ASN1C_ESYA_ESYAPasswordRecipientInfo::EncodeTo (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   return asn1E_ESYA_ESYAPasswordRecipientInfo (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL);
}

int ASN1C_ESYA_ESYAPasswordRecipientInfo::DecodeFrom (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   new (&msgData) ASN1T_ESYA_ESYAPasswordRecipientInfo;
   msgData.setContext (msgBuf.getContext());
   return asn1D_ESYA_ESYAPasswordRecipientInfo (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL, 0);
}

void asn1Init_ESYA_ESYAPasswordRecipientInfo (
   ASN1T_ESYA_ESYAPasswordRecipientInfo* pvalue)
{
   if (0 == pvalue) return;
   new (pvalue) ASN1T_ESYA_ESYAPasswordRecipientInfo;
}

void asn1Free_ESYA_ESYAPasswordRecipientInfo (OSCTXT *pctxt, 
   ASN1T_ESYA_ESYAPasswordRecipientInfo* pvalue)
{
   if (0 == pvalue) return;
   if (pvalue->m.keyDerivationAlgorithmPresent) {
      asn1Free_EXP_AlgorithmIdentifier (pctxt, &pvalue->keyDerivationAlgorithm);
   }
   asn1Free_EXP_AlgorithmIdentifier (pctxt, &pvalue->keyEncryptionAlgorithm);
   if (pvalue->encryptedKey.numocts > 0) {
      rtxMemFreePtr (pctxt, (void*)pvalue->encryptedKey.data);
      pvalue->encryptedKey.numocts = 0;
      pvalue->encryptedKey.data = 0;
   }
   asn1Free_PKCS7_DigestInfo (pctxt, &pvalue->keyHash);
}

void ASN1C_ESYA_ESYAPasswordRecipientInfo::MemFree ()
{
   asn1Free_ESYA_ESYAPasswordRecipientInfo (getCtxtPtr(), &msgData);
}

ASN1T_ESYA_EsyaGroupRecipientInfo* 
   new_ASN1T_ESYA_EsyaGroupRecipientInfo (ASN1CType& ccobj)
{
   void* pdata = ccobj.memAlloc (sizeof(ASN1T_ESYA_EsyaGroupRecipientInfo));
   if (0 == pdata) return 0;
   else return new (pdata) ASN1T_ESYA_EsyaGroupRecipientInfo;
}

ASN1C_ESYA_EsyaGroupRecipientInfo::ASN1C_ESYA_EsyaGroupRecipientInfo
    (ASN1T_ESYA_EsyaGroupRecipientInfo& data) : ASN1CType(), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}

ASN1C_ESYA_EsyaGroupRecipientInfo::ASN1C_ESYA_EsyaGroupRecipientInfo (
   OSRTMessageBufferIF& msgBuf, ASN1T_ESYA_EsyaGroupRecipientInfo& data) : 
   ASN1CType(msgBuf), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}
ASN1C_ESYA_EsyaGroupRecipientInfo::ASN1C_ESYA_EsyaGroupRecipientInfo (
   OSRTContext &context, ASN1T_ESYA_EsyaGroupRecipientInfo& data) : 
   ASN1CType(context), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}


ASN1T_ESYA_EsyaGroupRecipientInfo::ASN1T_ESYA_EsyaGroupRecipientInfo ()
{
}

ASN1T_ESYA_EsyaGroupRecipientInfo::~ASN1T_ESYA_EsyaGroupRecipientInfo ()
{
   OSCTXT* pctxt = mpContext.getCtxtPtr();
   if (pctxt != 0 && !rtxMemHeapIsEmpty (pctxt)) {
      asn1Free_ESYA_EsyaGroupRecipientInfo (pctxt, this);
   }
}

int ASN1C_ESYA_EsyaGroupRecipientInfo::EncodeTo (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   return asn1E_ESYA_EsyaGroupRecipientInfo (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL);
}

int ASN1C_ESYA_EsyaGroupRecipientInfo::DecodeFrom (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   new (&msgData) ASN1T_ESYA_EsyaGroupRecipientInfo;
   msgData.setContext (msgBuf.getContext());
   return asn1D_ESYA_EsyaGroupRecipientInfo (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL, 0);
}

void asn1Init_ESYA_EsyaGroupRecipientInfo (
   ASN1T_ESYA_EsyaGroupRecipientInfo* pvalue)
{
   if (0 == pvalue) return;
   new (pvalue) ASN1T_ESYA_EsyaGroupRecipientInfo;
}

void asn1Free_ESYA_EsyaGroupRecipientInfo (OSCTXT *pctxt, 
   ASN1T_ESYA_EsyaGroupRecipientInfo* pvalue)
{
   if (0 == pvalue) return;
   asn1Free_CMS_RecipientIdentifier (pctxt, &pvalue->rid);
   asn1Free_PKCS7_KeyEncryptionAlgorithmIdentifier (pctxt, &pvalue->keyEncryptionAlgorithm);
   asn1Free_PKCS7_EncryptedKey (pctxt, &pvalue->encryptedKey);
}

void ASN1C_ESYA_EsyaGroupRecipientInfo::MemFree ()
{
   asn1Free_ESYA_EsyaGroupRecipientInfo (getCtxtPtr(), &msgData);
}

ASN1T_ESYA_ESYAFileInfoRecipientInfo* 
   new_ASN1T_ESYA_ESYAFileInfoRecipientInfo (ASN1CType& ccobj)
{
   void* pdata = ccobj.memAlloc (sizeof(ASN1T_ESYA_ESYAFileInfoRecipientInfo));
   if (0 == pdata) return 0;
   else return new (pdata) ASN1T_ESYA_ESYAFileInfoRecipientInfo;
}

ASN1C_ESYA_ESYAFileInfoRecipientInfo::ASN1C_ESYA_ESYAFileInfoRecipientInfo
    (ASN1T_ESYA_ESYAFileInfoRecipientInfo& data) : ASN1CType(), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}

ASN1C_ESYA_ESYAFileInfoRecipientInfo::ASN1C_ESYA_ESYAFileInfoRecipientInfo (
   OSRTMessageBufferIF& msgBuf, ASN1T_ESYA_ESYAFileInfoRecipientInfo& data) : 
   ASN1CType(msgBuf), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}
ASN1C_ESYA_ESYAFileInfoRecipientInfo::ASN1C_ESYA_ESYAFileInfoRecipientInfo (
   OSRTContext &context, ASN1T_ESYA_ESYAFileInfoRecipientInfo& data) : 
   ASN1CType(context), msgData(data)
{
   setRunTimeKey (_rtkey, sizeof(_rtkey));
}


ASN1T_ESYA_ESYAFileInfoRecipientInfo::ASN1T_ESYA_ESYAFileInfoRecipientInfo ()
{
}

int ASN1C_ESYA_ESYAFileInfoRecipientInfo::EncodeTo (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   return asn1E_ESYA_ESYAFileInfoRecipientInfo (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL);
}

int ASN1C_ESYA_ESYAFileInfoRecipientInfo::DecodeFrom (OSRTMessageBufferIF& msgBuf)
{
   int stat = setMsgBuf (msgBuf, TRUE);
   if (stat != 0) return (stat);

   msgData.setContext (msgBuf.getContext());
   return asn1D_ESYA_ESYAFileInfoRecipientInfo (msgBuf.getCtxtPtr(), &msgData, ASN1EXPL, 0);
}

