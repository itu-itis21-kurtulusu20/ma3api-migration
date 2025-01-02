#include "EASNStreamingUtils.h"
#include "EASNException.h"

#include "ESynchroniseManager.h"
#include "ELogger.h"


using namespace esya;


FILE* EASNFileInputStream::unicodeFILEPtrOlustur(const QString& iFileName, const QString & iOpenMode)
{
	FILE * fp = NULL;

#if defined(WIN32)

	QString stMode(iOpenMode);
	const wchar_t* wbytes = (const wchar_t*)iFileName.utf16();
	const wchar_t* wmode = (const wchar_t*)stMode.utf16();

	int res = _wfopen_s(&fp,wbytes,wmode);

#else
	fp = fopen(iFileName.toUtf8().constData(),"rb");
#endif

	return fp;
}

EASNFileInputStream::EASNFileInputStream(OSRTFileInputStream* ipIN)
: mFP(NULL) ,prtIN(ipIN),prtINmemory(NULL),_pIN(NULL)
{
	if (!ipIN)
		throw EException("Buffer stream olamaz");

	_pIN = new ASN1BERDecodeStream(prtIN);
}

EASNFileInputStream::EASNFileInputStream(FILE* iFile)
: mFP(iFile),prtIN(NULL),prtINmemory(NULL),_pIN(NULL)
{
	prtIN =  new OSRTFileInputStream( iFile);
	if (prtIN->getStatus() != ASN_OK)
	{
		prtIN->printErrorInfo();
		DELETE_MEMORY(prtIN);
		throw EException(CMS_ASN_BUFFER_INITIALIZATION_ERROR,__FILE__,__LINE__);
	}

	_pIN = new ASN1BERDecodeStream(prtIN);
}


EASNFileInputStream::EASNFileInputStream(const QByteArray&  iData)
: mFP(NULL) ,prtIN(NULL),prtINmemory(NULL),_pIN(NULL)
{
	if (iData.isEmpty())
		throw EException("Buffer bos olamaz");

	prtINmemory = new OSRTMemoryInputStream((const OSOCTET* )iData.constData(),iData.size());
	_pIN = new ASN1BERDecodeStream(prtINmemory);
}


EASNFileInputStream::~EASNFileInputStream()
{
	if (mFP)
	{
		#ifdef WIN32
			fclose(mFP);
		#endif
	}
	if ( prtIN) 
	{
		prtIN->close();
	}
	if ( _pIN ) 
	{
		_pIN->close();
	}

	if (prtINmemory)
	{
		prtINmemory->close();
	}
	DELETE_MEMORY(_pIN)
}


int EASNFileInputStream::byteIndex()const
{	
	if (!_pIN)
		return -1;

	return _pIN->byteIndex();
}

int EASNFileInputStream::close()
{	
	if (!prtIN)
		return FAILURE;

	if (_pIN)
		return pIN()->close();
	
	return prtIN->close();
}

void EASNStreamingUtils::CHECK_BUFFER(OSRTOutputStream *pOUT)
{
	if ( !pOUT )
	{
		throw EException( "Buffer ilklendirilmesi do�ru yap�lmam�� ",__FILE__,__LINE__);
	}
}


/**
* \brief
* Verilen dosya i�in bir asn file input stream a�ar
* 
* \param iFileName
* okunacak dosya ad�
*/
EASNFileInputStream * EASNStreamingUtils::createFileInputStream(const QString& iFileName)
{
	FILE* fp = EASNFileInputStream::unicodeFILEPtrOlustur(iFileName,"rb");
	if (!fp)
		throw EException("File Input Stream could not be created");
	
	return createFileInputStream(fp);
}







/**
* \brief
* Verilen dosya i�in bir asn file input stream a�ar
* 
* \param iFile
* okunacak dosyaya i�aret�i
*/
EASNFileInputStream * EASNStreamingUtils::createFileInputStream(FILE* iFile)
{
	return new EASNFileInputStream(iFile);
}


/**
* \brief
* File Output Stream olu�turur
* 
* \param aFileName
* Streamin yazaca�� dosya.
* 
*/
OutputStream* EASNStreamingUtils::createFileOutputStream(const  QString & aFileName)
{
	QIODevice* pFOUT =  new QFile(aFileName);
	if (! pFOUT->open(QIODevice::WriteOnly))
	{
		DELETE_MEMORY(pFOUT)
		throw EException("G/� Ayg�t� Olu�turulamad�",__FILE__,__LINE__);
	}
	return new OutputStream(pFOUT);
}

/**
* \brief
* verilen output streami kapat�p haf�zas�n� geri verir
* 
* \param pOUT
* stream objesine pointer
*/
void EASNStreamingUtils::releaseOutputStream(OutputStream & oOUT)
{
	oOUT.releaseStream();
}

/**
* \brief
* verilen input streami kapat�p haf�zas�n� geri verir
* 
* \param pOUT
* stream objesine pointer
*/
void EASNStreamingUtils::releaseInputStream(OSRTInputStream * pIN)
{
	if ( pIN ) 
	{
		pIN->close();
		DELETE_MEMORY(pIN)
	}
}

/**
* \brief
* streamin pointerinin bulundu�u yerdeki tag ve len de�erini okur.
* !!! POINTERI ILERLETMEZ !!! 
*/
void EASNFileInputStream::peekTagLen(ASN1TAG &oTag, int &oLen)
{
	KERMEN_WORK_SYNCRONIZED;
	int retCode = _pIN->peekTagAndLen(oTag,oLen);
	if ( retCode != ASN_OK )
	{
		throw EASNException(QString::fromLocal8Bit("Tag-Len ikilisi okunamad�. RetCode = %1").arg(retCode),EASNException::AHT_DecodeError,__FILE__,__LINE__);
	}
}


/**
* \brief
* streamin pointerinin bulundu�u yerdeki tag ve len de�erini okur.
* !!! POINTERI ILERLETIR !!! 
*
* e�er output parametreleri NULL verilirse bu fonksiyon sadece pointeri ilerletmeye yarar
*/
void EASNFileInputStream::decodeTagLen(ASN1TAG *oTag, int *oLen)
{
	KERMEN_WORK_SYNCRONIZED;		

	ASN1TAG tag;
	int len;

	if (_pIN->decodeTagAndLen(tag,len) != ASN_OK )
	{
		throw EASNException("Tag-Len ikilisi okunamad�",EASNException::AHT_DecodeError,__FILE__,__LINE__);
	}

	if (oTag) *oTag = tag;
	if (oLen) *oLen = len;
}

/**
* \brief
* streamin pointerinin bulundu�u yerdeverilen tag olup olmad���n� kontrol eder
* !!! POINTERI ILERLETIR !!! 
*
*/
void EASNFileInputStream::skipTag(ASN1TAG iTag)
{
	KERMEN_WORK_SYNCRONIZED;		

	ASN1TAG tag; 
	int len;

	try
	{
		_pIN->mark(1000);
		decodeTagLen(&tag,&len);
		if ( tag != iTag)
		{
			_pIN->reset();
			throw EASNException("Okunan Tag de�eri e�le�miyor ",EASNException::AHT_DecodeError,__FILE__,__LINE__);
		}
	}
	catch (EException &exc) 
	{
		_pIN->reset();
		throw EASNException(exc.append("skipTag()"));
	}
}

/**
* \brief
* streamin pointerinin bulundu�u yerdeki ASNUINT de�erini okur
* !!! POINTERI ILERLETIR !!! 
*
* e�er output parametreleri NULL verilirse bu fonksiyon sadece pointeri ilerletmeye yarar
*/
void EASNFileInputStream::decodeUInt(ASN1UINT * oIntVal)
{
	_pIN->mark(1000);
	ASN1UINT val;
	if ( _pIN->decodeUInt(val,ASN1EXPL,0) != ASN_OK )
	{
		_pIN->reset();
		throw EASNException("UINT de�eri okunamad�",EASNException::AHT_DecodeError,__FILE__,__LINE__);
	}
	if (oIntVal) *oIntVal = val;
}

/**
* \brief
* streamin pointerinin bulundu�u yerdeki ASNUINT de�erini okur
* !!! POINTERI ILERLETIR !!! 
*
* e�er output parametreleri NULL verilirse bu fonksiyon sadece pointeri ilerletmeye yarar
*/
void EASNFileInputStream::decodeEoc()
{
	_pIN->mark(1000);
	if ( _pIN->decodeEoc() != ASN_OK )
	{
		_pIN->reset();
		throw EASNException("Eoc de�eri okunamad�",EASNException::AHT_DecodeError,__FILE__,__LINE__);
	}
}


/**
* \brief
* streamin pointerinin bulundu�u yerdeki ASN1TObjId de�erini okur.
* !!! POINTERI ILERLETIR !!! 
*
* e�er output parametreleri NULL verilirse bu fonksiyon sadece pointeri ilerletmeye yarar
*/
void EASNFileInputStream::decodeObjID( ASN1TObjId * oObjID)
{
	_pIN->mark(1000);
	ASN1TObjId objID;

	int res = _pIN->decodeObjId(objID,ASN1EXPL,0);

	if ( res != ASN_OK )
	{
		_pIN->reset();
		throw EASNException("UINT de�eri okunamad�",EASNException::AHT_DecodeError,__FILE__,__LINE__);
	}
	if (oObjID) *oObjID = objID ;
}

/**
* \brief
* streamin pointerinin bulundu�u yerdeki OpenType de�eri okur.
* !!! POINTERI ILERLETIR !!! 
*
* e�er output parametreleri NULL verilirse bu fonksiyon sadece pointeri ilerletmeye yarar
*/
void EASNFileInputStream::decodeOpenType(QByteArray * oBytes)
{
// 	size_t buflen = 0;
// 	OSOCTET buf[100000];
// 
// 	buflen = _pIN->readTLV(buf,100000);
// 
// 	if (buflen < 0 )
// 	{
// 		throw EASNException("OpenType Okunamad�",EASNException::AHT_DecodeError,__FILE__,__LINE__);
// 	}
// 
// 	QByteArray bufBytes((const char*)buf,buflen);

	DEBUGLOGYAZ(ESYAASN_MOD,"EASNFileInputStream::decodeOpenType() openType okunacak")

	ASN1TOpenType ot;
	int res = _pIN->decodeOpenType(ot);

	if (res != ASN_OK)
	{
		throw EASNException("Open Type de�eri okunamad�",EASNException::AHT_DecodeError,__FILE__,__LINE__);
	}

	DEBUGLOGYAZ(ESYAASN_MOD,"EASNFileInputStream::decodeOpenType() openType okundu")

	QByteArray bufBytes((char*)ot.data,ot.numocts);

	//rtxMemFreeArray(_pIN->getCtxtPtr(),ot.data);

	DEBUGLOGYAZ(ESYAASN_MOD,"EASNFileInputStream::decodeOpenType() openType context Free Edildi")

	if(oBytes) oBytes->append(bufBytes);

	DEBUGLOGYAZ(ESYAASN_MOD,"EASNFileInputStream::decodeOpenType() bitti")
}

/**
* \brief
* streamin pointerinin bulundu�u yerdeki PrimitveString de�erini okur.
* !!! POINTERI ILERLETIR !!! 
*
* e�er output parametreleri NULL verilirse bu fonksiyon sadece pointeri ilerletmeye yarar
*/
void EASNFileInputStream::decodePrimitiveOctetString(ASN1TagType tagging ,QByteArray* oBytes )
{
	ASN1TAG tag; 
	int len = 0 ;
	const ASN1UINT MAXLEN = 1000000;
	ASN1UINT numocts = MAXLEN;
	ASN1OCTET  * buf = new ASN1OCTET[MAXLEN];	

	if ( tagging ==  ASN1IMPL ) _pIN->decodeTagAndLen(tag,len);
	int retCode = _pIN->decodeOctStr(buf,numocts,tagging,len);

	if ( retCode != ASN_OK)
	{
		DEBUGLOGYAZ(ESYAASN_MOD,QString::fromLocal8Bit("ASN OctetString yap�s� d�zg�n de�il. Hata Kodu = %1").arg(retCode));
		DELETE_MEMORY_ARRAY(buf)
		_pIN->reset();
		throw EASNException("ASN OctetString yap�s� d�zg�n de�il.",EASNException::AHT_DecodeError,__FILE__,__LINE__);
	}
	if(oBytes) 
	{
		oBytes->clear();
		oBytes->append(QByteArray((const char*)buf,numocts));
	}
	DELETE_MEMORY_ARRAY(buf);
}




/**
* \brief
* InDefiniteLength OctetString okur. streamin pointerinin bulundu�u yerden ba�layarak
* blok blok veri okur ve okudu�u veriyi blok i�leyiciye g�nderir.
* !!! POINTERI ILERLETIR !!! 
*
*/

void EASNFileInputStream::processConstructedIndefLenOctetString(BlokIsleyici& iBP )
{
	ASN1TAG tag; 
	int len;
	QByteArray bytes;
	_pIN->decodeTagAndLen(tag,len);
	_pIN->peekTagAndLen(tag,len);

	while ( tag != TAG_ENDOF_OCTETSTR )
	{
		decodePrimitiveOctetString(ASN1EXPL,&bytes);
		iBP.blokIsle(bytes);
		_pIN->peekTagAndLen(tag,len);
	}
}

/**
* \brief
* DefiniteLength OctetString okur. streamin pointerinin bulundu�u yerden ba�layarak
* blok blok veri okur ve okudu�u veriyi blok i�leyiciye g�nderir.
* !!! POINTERI ILERLETIR !!! 
*
*/
void EASNFileInputStream::processConstructedDefLenOctetString(BlokIsleyici& iBP )
{
	ASN1TAG tag;
	QByteArray bytes;
	int len,totallen = 0 ;
	_pIN->decodeTagAndLen(tag,len);
	while ( totallen<len )	
	{
		decodePrimitiveOctetString(ASN1EXPL,&bytes);
		totallen += bytes.size(); 
		iBP.blokIsle(bytes);
	}

}

void EASNFileInputStream::processInDefLenOctetString( BlokIsleyici& iBP )
{
	ASN1TAG tag;
	int len;
	QByteArray bytes;
	peekTagLen(tag,len);

	while (tag != TAG_ENDOF_OCTETSTR )
	{
		if (tag == TAG_PRIMITIVE_OCTETSTR)
		{
			decodePrimitiveOctetString(ASN1EXPL,&bytes);
			iBP.blokIsle(bytes);

		}
		else if (tag == TAG_CONSTRUCTED_OCTETSTR)  // tag : 24   
		{
			if ( len  == ASN_K_INDEFLEN ) // TAG_24 with INDEFINITE length
			{
				processConstructedIndefLenOctetString(iBP);
				_pIN->decodeEoc();
			}
			else // TAG_24 with DEFINITE length
			{
				processConstructedDefLenOctetString(iBP);
			}
		} 
		peekTagLen(tag,len);
	}

}

/**
* \brief
* OctetString okur. streamin pointerinin bulundu�u yerden ba�layarak
* blok blok veri okur ve okudu�u veriyi blok i�leyiciye g�nderir.
* !!! POINTERI ILERLETIR !!! 
*
*/
void EASNFileInputStream::processDefLenOctetString(BlokIsleyici& iBP )
{
	ASN1TAG tag; 
	ASN1UINT numocts;
	int len,eocCount = 0, buflen = 0 ;
	QByteArray bytes;

	_pIN->mark(1000);
	
	peekTagLen(tag,len);
	if (tag == TAG_PRIMITIVE_OCTETSTR)
	{
		decodePrimitiveOctetString(ASN1EXPL,&bytes);
		iBP.blokIsle(bytes);
	}
	else 
	{
		processConstructedDefLenOctetString(iBP);
	}
}



/************************************************************************/
/*                            WRITE                                     */
/************************************************************************/


/**
* \brief
* streame octet yazar
* 
* \param aTag
* yaz�lacak tag
*
* \param aLen
* yaz�lacak len
*
*/
void EASNStreamingUtils::write(OutputStream& pOUT,const ASN1OCTET* data, int len)
{
	if (pOUT.write((const char*)data,len) < 0 )
	{
		throw EASNException("ASN Output Stream Yaz�lamad�",EASNException::AHT_IOError,__FILE__,__LINE__);	
	}
}


/**
* \brief
* streame octet yazar
* 
* \param aTag
* yaz�lacak tag
*
* \param aLen
* yaz�lacak len
*
*/
void EASNStreamingUtils::write(OutputStream& pOUT,const QByteArray& iData)
{
	if (pOUT.write(iData) < 0 )
	{
		throw EASNException("ASN Output Stream Yaz�lamad�",EASNException::AHT_IOError,__FILE__,__LINE__);	
	}
}


/**
* \brief
* streame tag ve len de�eriyazar
* 
* \param aTag
* yaz�lacak tag
*
* \param aLen
* yaz�lacak len
*
*/
void EASNStreamingUtils::writeTagLen(OutputStream& pOUT, ASN1TAG aTag, ASN1INT aLen)
{
	ASN1OCTET tag = aTag;
	QVector<ASN1OCTET> octets = ASNUtils::_getLenOctet(aLen);

	if (pOUT.write((const char*)&tag,1) < 0 )
	{
		throw EASNException("ASN Output Stream Yaz�lamad�",EASNException::AHT_IOError,__FILE__,__LINE__);	
	}

	for (int i = 0 ; i< octets.size(); i++)
	{
		if (pOUT.write((const char*)&octets[i],1)<0) 
		{
			throw EASNException("ASN Output Stream Yaz�lamad�",EASNException::AHT_IOError,__FILE__,__LINE__);	
		}
	}
}

/**
* \brief
* streame ASN1OBJID de�eriyazar
* 
* \param iObjId
* yaz�lacak objID
*
*
*/
void EASNStreamingUtils::writeObjID(OutputStream& pOUT, const ASN1OBJID & iObjId )
{
	ASN1BEREncodeBuffer encBuf;

	int stat  = xe_objid (encBuf.getCtxtPtr(), (ASN1OBJID*)&iObjId, ASN1EXPL);
	if (stat < 0) 
	{
		throw EASNException(CMS_ASNE_SIGNEDDATA_OBJID,EASNException::AHT_EncodeError,__FILE__,__LINE__);
	}
	if (pOUT.write((const char*)encBuf.getMsgPtr(),stat)<0)
	{
		throw EASNException("ASN Output Stream Yaz�lamad�",EASNException::AHT_IOError,__FILE__,__LINE__);	
	}

}



/**
* \brief
* streame PrimitiveIctetString de�eriyazar
* 
* \param iOctets
* yaz�lacak string
*
*
*/
void EASNStreamingUtils::writePrimitiveOctetString(OutputStream& pOUT, const QByteArray & iOctets)
{
	writeTagLen(pOUT,TAG_PRIMITIVE_OCTETSTR,iOctets.size());
	if (pOUT.write(iOctets)<0)
	{
		throw EASNException("ASN Output Stream Yaz�lamad�",EASNException::AHT_IOError,__FILE__,__LINE__);	
	}
}

/**
* \brief
* streame OctetString de�eriyazar
* 
* \param iOctets
* yaz�lacak string
*
*
*/
void EASNStreamingUtils::writeOctetString(OutputStream& pOUT, const QByteArray & iOctets )
{
	writePrimitiveOctetString(pOUT,iOctets);
}

/**
* \brief
* streame PrimitiveIctetString de�eriyazar
* 
* \param iIN
* yaz�lacak stringin okunaca�� inputdevice
*
*
*/
void EASNStreamingUtils::writeOctetString(OutputStream& pOUT, QIODevice & iIN , ASN1TagType tagging)
{
	if (tagging == ASN1EXPL )	
		writeTagLen(pOUT,TAG_24,INDEFLEN); 

	if ( !iIN.open(QIODevice::ReadOnly))
	{    
		throw EASNException("IODevice a��lamad�.",EASNException::AHT_IOError,__FILE__,__LINE__);
	}
	while (!iIN.atEnd()) 
	{
		QByteArray line = iIN.read(OCTETSTR_BLOCK_SIZE);
		writePrimitiveOctetString(pOUT,line);
	}	
	
	/* INDEFLEN Sonland�r�lmal�*/
	if (tagging == ASN1EXPL )	
		writeTagLen(pOUT,TAG_ENDOF_OCTETSTR,0);
}
