#include "ECMSException.h"



using namespace esya;

/**
 * \brief
 * ECMSException i�in constructor.
 * 
 * \param errSTR
 * Hata mesaj�.
 * 
 * \param iFileName
 * �stisnan�n olu�tu�u kaynak dosyas� ad�.
 * 
 * \param iLineNumber
 * �stisnan�n olu�tu�u sat�r numaras�.
 * 
 * 
 */
ECMSException::ECMSException(const QString & errSTR,const QString & iFileName , int iLineNumber)
:EException(errSTR,iFileName,iLineNumber), mHataKodu(CHT_Diger)
{

}

/**
* \brief
* ECMSException i�in constructor.
* 
* \param errSTR
* Hata mesaj�.
* 
* \param iHataKodu
* �stisnan�n tipi
*
* \param iFileName
* �stisnan�n olu�tu�u kaynak dosyas� ad�.
* 
* \param iLineNumber
* �stisnan�n olu�tu�u sat�r numaras�.
* 
* 
*/
ECMSException::ECMSException(const QString & errSTR,const CMSHataTipi & iHataKodu, const QString & iFileName , int iLineNumber)
:EException(errSTR,iFileName,iLineNumber), mHataKodu(iHataKodu)
{

}

/**
 * \brief
 * ECMSException i�in constructor.
 * 
 * \param exc
 * �stisnan�n kendisinden kopyalanaca�� istisna.
 * 
 */
ECMSException::ECMSException(const EException & exc)
:EException(exc),mHataKodu(CHT_Diger)
{

}

/**
 * \brief
 * �stisnan�n mesaj listesinin sonuna yeni bir mesaj ekler.
 * 
 * \param iErrorText
 * Eklenecek hata mesaj�.
 * 
 * \param iFileName
 * �stisnan�n olu�tu�u kaynak dosyas� ad�.
 * 
 * \param iLineNumber
 * �stisnan�n olu�tu�u sat�r numaras�.
 * 
 * \returns
 * �stisnan�n kendisini d�ner.
 * 
 */
ECMSException  ECMSException::append(const QString &iErrorText,const QString & iFileName  , int iLineNumber  )
{
	EException::append(iErrorText,iFileName,iLineNumber);
	return *this;
}

/**
 * \brief
 * ECMSException i�in Destructor.
 */
ECMSException::~ECMSException(void)
{
}


/**
* \brief
* Hata Kodunu d�ner
*
* \return   	const  CMSHataTipi &
*/
const  ECMSException::CMSHataTipi & ECMSException::getHataKodu()const
{
	return mHataKodu;
}