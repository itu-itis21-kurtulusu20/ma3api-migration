#include "EASNException.h"

using namespace esya;

/**
* \brief
* Dosya �stisnas� constructoru
*/
EASNException::EASNException()
:	EException("","",0),	
	mIsNULL(true)
{
}

/**
* \brief
* Dosya �stisnas� constructoru
*
* \param const QString& iErrorDetail
* HataDetay�
*
* \param DosyaHataTipi iHataKodu
* Hata Tipi
*
* \param const QString& iFileName
* Hatan�n olu�tu�u dosya yolu
*
* \param const int iLineNumber
* Hatan�n olu�tu�u sat�r numaras�
*/
EASNException::EASNException(const  QString &iErrorDetail, const ASNHataTipi &iHataKodu   , const QString &iFileName ,int iLineNumber )
:	EException(iErrorDetail,iFileName,iLineNumber),
	mHataKodu(iHataKodu),
	mIsNULL(false)
{
}

/**
* \brief
* Dosya �stisnas� constructoru
*
* \param const EException& exc
* Genel �stisna
*
* \param DosyaHataTipi iHataKodu
* Hata Tipi
*/
EASNException::EASNException(const  EException &exc, ASNHataTipi iHataKodu )
:	EException(exc),
	mHataKodu(iHataKodu),
	mIsNULL(false)
{
}

/**
* \brief
* Dosya �stisnas� Kopya constructoru
*
* \param const EDosyaException& exc
* Kopya Dosya �stisnas�
*/
EASNException::EASNException(const  EASNException &exc )
:	EException("",exc.getFileName(),exc.getLineNumber()),
	mHataKodu(exc.getHataKodu()),
	mIsNULL(false)
{
	mStackTrace = exc.getStackTrace();
}


/**
* \brief
* Hata dizisine yeni bir hata ekler
*
* \param const  QString &iErrorDetail
* Hata Detay�
*
* \param const DosyaHataTipi iHataKodu
* Hata Tipi
*
* \param const QString& iFileName
* Hatan�n olu�tu�u dosya yolu
*
* \param const int iLineNumber
* Hatan�n olu�tu�u sat�r numaras�
*
* \return EDosyaException
* Dosya �stisnas�
*/
EASNException EASNException::append(const  QString &iErrorDetail, const ASNHataTipi iHataKodu , const QString &iFileName ,int iLineNumber )
{
	if (!iFileName.isEmpty())
		mStackTrace.append(QString( "%1 - %2 %3 (%4)").arg(iHataKodu).arg(iErrorDetail).arg(iFileName).arg(iLineNumber));
	else mStackTrace.append(QString( "%1 - %2").arg(iHataKodu).arg(iErrorDetail));
	return (*this);
}

/**
* \brief
* Hata Kodu d�ner
*
* \return		const EDosyaException::DosyaHataTipi & 
* Hata Kodu
*/
const EASNException::ASNHataTipi& EASNException::getHataKodu()const 
{
	return mHataKodu;
}

/**
* \brief
* Hata Kodu belirler
*
* \param 		const EDosyaException::DosyaHataTipi & iHataKodu
* Hata Kodu
*
* \return   	void
*/
void EASNException::setHataKodu(const EASNException::ASNHataTipi& iHataKodu)
{
	mHataKodu = iHataKodu;
}

/**
* \brief
* Objenin NULL olup olmad��� bilgisini d�ner
*
* \return 		const bool &
* Objenin NULL olup olmad��� bilgisi
*/
const bool& EASNException::isNULL() const
{
	return mIsNULL;
}

/**
* \brief
* Objenin NULL olup olmad��� bilgisini belirler
*
* \param 		const bool & iIsNULL
* Objenin NULL olup olmad��� bilgisi
*
* \return   	void
*/
void EASNException::setNULL(const bool & iIsNULL )
{
	mIsNULL = iIsNULL;
}



/**
* \brief
* Dosya �stisnas� destructoru
*
* \return   	
*/
EASNException::~EASNException(void)
{
}
