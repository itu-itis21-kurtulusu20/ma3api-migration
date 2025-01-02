#include "EException.h"
#include <QTextStream>
#include <QFileInfo>


using namespace esya;

/**
 * \brief
 * EException i�in constructor.
 * 
 * \param iErrorDetail
 * Hata Mesaj�.
 * 
 * \param iFileName
 * �stisnan�n olu�tu�u kaynak dosyas� ad�.
 * 
 * \param iLineNumber
 * �stisnan�n olu�tu�u sat�r numaras�.
 * 
 */
EException::EException(const  QString &iErrorDetail, const QString &iFileName,int iLineNumber)
: mLineNumber(iLineNumber), mFileName(iFileName), mErrorText(iErrorDetail)
{
	append(toString(),iFileName,iLineNumber);
}

/**
 * \brief
 * EException i�in copy constructor.
 * 
 * \param exc
 * Kopayalanacak istisna.
 * 
 * 
 */
EException::EException(const  EException &exc)
: mLineNumber(exc.getLineNumber()), mFileName(exc.getFileName())
{
	mStackTrace = exc.getStackTrace();
}



/**
 * \brief
 * �stisnan�n mesaj listesini d�ner.
 * 
 * \returns
 * �stisnan�n mesaj listesini d�ner.
 * 
 */
QList<QString> EException::getStackTrace() const
{
	return mStackTrace;
}

/**
 * \brief
 * �stisnan�n olu�tu�u sat�r numaras�n� d�ner.
 * 
 * \returns
 * �stisnan�n olu�tu�u sat�r numaras�n� d�ner.
 * 
 */
int EException::getLineNumber()const 
{
	return mLineNumber;
}


/**
 * \brief
 * �stisnan�n olu�tu�u sat�r numaras�n� belirler.
 * 
 * \param iLineNumber
 *  �stisnan�n olu�tu�u sat�r numaras�.
 * 
 * 
 * 
 */
void EException::setLineNumber(int iLineNumber)
{
	mLineNumber = iLineNumber;
}

/**
 * \brief
 * mesaj listesini temizler.
 * 
 */
void EException::clearStack(void)
{
	mStackTrace.clear();
}

/**
 * \brief
 * �stisnaya son eklenen hata mesaj�n� d�ner
 * 
 * \returns
 * �stisnaya son eklenen hata mesaj�.
 * 
 */
QString EException::getErrStr(void) const 
{
	return mStackTrace.last();
}

/**
 * \brief
 * �stisnan�n olu�tu�u kaynak dosyas� ad�n� d�ner
 * 
 * \returns
 * �stisnan�n olu�tu�u kaynak dosyas� ad�.
 */
QString EException::getFileName(void) const 
{
	return mFileName;
}

/**
 * \brief
 * �stisnan�n olu�tu�u kaynak dosyas� ad�n� belirler.
 * 
 * \param 
 * �stisnan�n olu�tu�u kaynak dosyas� ad� .
 * 
 */
void EException::setFileName(const QString & iFileName)
{
	mFileName = iFileName;
}


/**
 * \brief
 * Mesaj listesinin sonuna yeni bir hata mesaj� ekler.
 * 
 * \param iErrorText
 * Ekelenecek hata mesaj�.
 * 
 * \param iFileName
 * mesaj�n  eklendi�i kaynak dosyas�n�n ad�.
 * 
 * \param iLineNumber
 * mesaj�n  eklendi�i sat�r numaras�n�n ad�.
 * 
 * \returns
 * Mesaj�n eklenmi� oldu�u istisna objesini d�ner.
 * 
 */
EException EException::append(const QString &iErrorText,const QString & iFileName , int iLineNumber )
{
	if (!iFileName.isEmpty() && QFile::exists(iFileName))
	{
		mStackTrace.append(QString(iErrorText+QString(" %1 (%2) ").arg(QFileInfo(iFileName).fileName()).arg(iLineNumber)));
	}
	else mStackTrace.append(iErrorText);
	return (*this);
}

/**
 * \brief
 * Mesaj listesindeki b�t�n mesajlar alt alta listeler.
 * 
 * \returns
 * Mesaj listesindeki mesajlar�n alt alta listelenmi� �ekli.
 * 
 */
QString EException::printStackTrace()const
{
	QString errText;
	int indent = 0 , size = mStackTrace.size() ;
	QString stIndent;
	for (int i = 0 ; i < size ; i++)
	{
		stIndent.fill(QChar(' '), indent);
		errText += (stIndent+LINE_START + mStackTrace[size-i-1] + "\n");
		indent += QString(LINE_START).length();
	}
	errText = QString("%1%2%3%4").arg(HEADER).arg(LINE_SEPERATOR).arg(errText).arg(LINE_SEPERATOR);
	return errText;
}


/**
 * \brief
 * Mesaj listesini varsay�lan bir log dosyas�na yazar.
 * 
 */
int EException::writeLog()
{				
	return writeLog(DEFAULT_FILE_PATH);
}


/**
 * \brief
 * Mesaj listesini verilen log dosyas�na yazar.
 * 
 * \param logFilePath
 * Mesaj listesinin yaz�laca�� log dosyas� adresi
 */
int EException::writeLog(const QString &logFilePath)
{

		QString	data;
		QString	caption = "<< EException  >> " ;  
		data	=  caption + QTime::currentTime().toString("hh:mm:ss.zzz   :   ");
		data	+= printStackTrace();
		data	+= LINE_SEPERATOR;


		QFile logFile(logFilePath);

		if(!logFile.open(QFile::Append | QIODevice::Text)) 
		{
			return ERROR_FILE_OPEN;
		}

		QTextStream out(&logFile);

		out<<data<<endl;

		logFile.close();

		return 0;
}




