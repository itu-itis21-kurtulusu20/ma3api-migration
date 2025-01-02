#include "EOrtamDegiskeni.h"
#include <QProcess>
#include <QRegExp>

#ifdef WIN32
#include <ShlObj.h>
#include <WinNT.h>
#include <WinDef.h>
#endif

#define KERMEN_AYAR_ORTAM_DEGISKEN_BELIRTEC_BASLANGIC "[$"
#define KERMEN_AYAR_ORTAM_DEGISKEN_BELIRTEC_BITIS "]"

/**
 * Bo� olu�turur. 
 */
EOrtamDegiskeni::EOrtamDegiskeni(void)
:mIsNull(true)
{
}

/**
 * Y�k�c� metod
 */
EOrtamDegiskeni::~EOrtamDegiskeni(void)
{
}


/**
 * Ortam de�i�keni ad� ve de�eri ile ilklendirilir.
 * \param iDegiskenAdi 
 * Ortam de�i�keni ad�
 * \param iDegiskenDegeri 
 * De�i�ken de�eri
 * \return 
 */
EOrtamDegiskeni::EOrtamDegiskeni(const QString & iDegiskenAdi,const QString & iDegiskenDegeri)
:mIsNull(false),mDegiskenAdi(iDegiskenAdi),mDegiskenDegeri(iDegiskenDegeri)
{
}

/**
 * Verilen ortam de�i�keni de�erini bulur.
 * \param iDegiskenAdi 
 * Istenen ortam de�i�keni
 * \return 
 * EOrtamDe�i�keni d�ner.
 */
EOrtamDegiskeni EOrtamDegiskeni::degiskenDegerBul(const QString & iDegiskenAdi)
{
	QMap<QString,QString> ortamDegiskenMap=EOrtamDegiskeni::ortamDegiskenMapGetir();
	if (ortamDegiskenMap.contains(iDegiskenAdi))
	{
		return EOrtamDegiskeni(iDegiskenAdi,ortamDegiskenMap.value(iDegiskenAdi));
	}
	else
	{
		return EOrtamDegiskeni();
	}
}

/**
 * Ayarlarda tutulan,i�inde ortam de�i�keni ge�en text i�indeki ortam de�i�kenlerinin de�erlerini yerle�tirip geri d�ner
 * "[$SYSTEMDIR]\\test" �eklinde verilen metni "c:\\windows\\sytem32\\test" olarak d�ner
 * \param iAyarTamDegeri 
 * Ayarlardan okunan tam metin
 * \return 
 * Ortam de�i�keni bulunup metine yerle�tirilmi� halini d�ner
 */
QString EOrtamDegiskeni::degiskenliStrCoz(const QString & iAyarTamDegeri)
{
	QString ayarDegeri=iAyarTamDegeri;
	int ilkBas = ayarDegeri.indexOf(KERMEN_AYAR_ORTAM_DEGISKEN_BELIRTEC_BASLANGIC);
	if (ilkBas!=-1)
	{
		int paternSon = ayarDegeri.indexOf(KERMEN_AYAR_ORTAM_DEGISKEN_BELIRTEC_BITIS);
		if (paternSon!=-1)
		{
			QString degisken = ayarDegeri.mid(ilkBas+2,paternSon-ilkBas-2);
			EOrtamDegiskeni ortamDegiskeni = EOrtamDegiskeni::degiskenDegerBul(degisken);
			if (!ortamDegiskeni.isNull())
			{
				ayarDegeri.replace(ilkBas,paternSon-ilkBas+1,ortamDegiskeni.getDegiskenDegeri()); 				
				ayarDegeri = degiskenliStrCoz(ayarDegeri);
			}
		}
	}
	return ayarDegeri;	
}

/**
 * Ortam de�i�kenlerinin de�i�ken ad�, de�er map ini d�ner
 * \return 
 * De�er map'i d�ner.
 */
QMap<QString,QString>  EOrtamDegiskeni::ortamDegiskenMapGetir()
{
	QMap<QString,QString> retOrtamDegiskenMap;
	QStringList systemDegiskenleri = QProcess::systemEnvironment();
	Q_FOREACH(QString ortamDegiskenDeger,systemDegiskenleri)
	{
		QStringList degiskenDeger = ortamDegiskenDeger.split("=");
		if (degiskenDeger.size() == 2)
		{
			retOrtamDegiskenMap.insert(degiskenDeger.at(0),degiskenDeger.at(1));
		}		
	}

#ifdef WIN32
	TCHAR szPath[MAX_PATH];
	if(SUCCEEDED(SHGetFolderPath(NULL,CSIDL_PERSONAL,NULL,0,szPath)))
	{
		QString personalFolderPath;
#ifdef UNICODE
		personalFolderPath = QString::fromUtf16((ushort *)szPath);
#else
		personalFolderPath = QString::fromLocal8Bit(szPath);
#endif
		retOrtamDegiskenMap.insert("PERSONAL",personalFolderPath);
	}
#endif
		
	return retOrtamDegiskenMap;
}

/**
 * Bo� olup olmad���n� d�ner
 * \return
 * Bo�sa true bo� de�ilse false d�ner.
 */
bool EOrtamDegiskeni::isNull()
{
	return mIsNull;
}

/**
 * De�i�ken ad�n� almak i�in kullan�l�r.
 * \return 
 * De�i�ken ad�n� d�ner
 */
QString EOrtamDegiskeni::getDegiskenAdi() const
{
	return mDegiskenAdi;
}

/**
 * De�i�ken de�erini almak i�in kullan�l�r.
 * \return 
 * De�i�ken de�erini d�ner.
 */
QString EOrtamDegiskeni::getDegiskenDegeri() const
{
	return mDegiskenDegeri;
}
