#include "EASNWrapper.h"

using namespace esya;

EASNWrapper::EASNWrapper(void)
{
}

/**
* \brief
* Nesneyi ASN1-encoded �ekilde dosyaya yazar
*
* \param 		const QString & iFileName
* Dosya Yolu
*
*/
int EASNWrapper::write2File(const QString &iFileName) const
{
	QByteArray data = getEncodedBytes();
	if ( data.size() == 0 )
	{
		return FAILURE;
	}
	QFile file(iFileName);
	if ( !file.open(QIODevice::WriteOnly))
		throw EException(" write2File() : Dosya a��lamad�",__FILE__,__LINE__);
	file.write(data);
	file.close();
	return SUCCESS;
}

/**
* \brief
* Nesneyi ASN1-encoded dosyadan y�kler
*
* \param 		const QString & iFileName
* Dosya Yoku
*
* \return   	int
*/
int  EASNWrapper::loadFromFile(const QString & iFileName)
{
	QByteArray bytes;
	QFile file(iFileName);
	if (!file.open(QIODevice::ReadOnly))
	{		
		throw EException(" loadFromFile() : Dosya bulunamad�.",__FILE__,__LINE__);
	}
	bytes = file.readAll();
	file.close();
	
	try
	{
		return constructObject(bytes);
	}
	catch (EException&  exc)
	{
		bytes = bytes.trimmed();	
		if (bytes.left(2) == "--" )
			bytes.remove(0,bytes.indexOf("\n"));

		bytes = QByteArray::fromBase64(bytes);
		try
		{
			return constructObject(bytes);
		}
		catch (EException exc2)
		{
			throw exc.append(exc2.printStackTrace());		
		}
	}
}


EASNWrapper::~EASNWrapper(void)
{
}


//////////////////////////////////////////////////////////////////////////
/*                        DUMMY FONKSIYONLAR                            */
//////////////////////////////////////////////////////////////////////////

/**
* \brief
* Nesne kendisini ASN nesnesine kopyalar
*/
void EASNWrapper::copyToASNObject() const
{
	return ;
}


/**
* \brief
* Nesne kendisini ASN nesnesinden kopyalar
*/
void EASNWrapper::copyFromASNObject()
{
	return;
}

/**
* \brief
* Nesnenin kopyas�n� ��kar�r.
*/
void * EASNWrapper::getASNCopy(QByteArray) const
{
	return NULL;
}

/**
* \brief
* Verilen bir ASN nesnesinin kopyas�n� ��kar�r
*/
void * EASNWrapper::getASNCopyOf(QByteArray)
{
	return NULL;
}

/**
* \brief
* Verilen bir ASN nesnesinin i�in al�nm�� haf�zay� geri verir 
*/
void EASNWrapper::freeASNObject(void * )
{
	return;
}