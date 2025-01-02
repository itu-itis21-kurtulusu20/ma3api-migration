#include "EAyarException.h"
#include "EsyaOrtak_Ortak.h"

NAMESPACE_BEGIN(esya)

EAyarException::EAyarException(Sebep neden, const QString &iErrorDetail, const QString &iFileName, int iLineNumber):
EException(iErrorDetail, iFileName, iLineNumber),mSebep(neden)
{
	ESYA_ORTAK_FUNC_BEGIN;
	ESYA_ORTAK_FUNC_END;
}


EAyarException::~EAyarException()
{
	ESYA_ORTAK_FUNC_BEGIN;
	ESYA_ORTAK_FUNC_END;
}

/**
 * Olu�an exception sebebini d�ner. 
 */
EAyarException::Sebep EAyarException::sebep()
{
	return mSebep;
}

NAMESPACE_END

