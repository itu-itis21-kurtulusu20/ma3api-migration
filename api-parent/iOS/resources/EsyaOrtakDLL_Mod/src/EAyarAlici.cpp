#include "EAyarAlici.h"
#include "EsyaOrtak_Ortak.h"
#include "EAyarManagerFactory.h"
#include "FileUtil.h"
#include <QApplication>

NAMESPACE_BEGIN(esya)

//////////////////////////////////////////////////////////////////////////

EAyarCacheManager * EAyarCacheManager::mInstance = NULL ;
QMutex EAyarCacheManager::mInstanceMutex;
QMutex EAyarCacheManager::mReadWriteMutex;

EAyarCacheManager::EAyarCacheManager(bool iStartListenerThread/* =true */)
:mpAyarListener(NULL)
{
	if ((iStartListenerThread) && (qApp != NULL))
	{
		mpAyarListener = new EAyarListenerThread(this);
		//mpAyarListener->start();
	}
}

EAyarCacheManager::~EAyarCacheManager()
{
	DELETE_MEMORY(mpAyarListener);
}

EAyarCacheManager *EAyarCacheManager::getInstance(bool iStartListenerThread/* =true */)
{
	QMutexLocker ml(&mInstanceMutex);

	if (mInstance == NULL )
	{
		mInstance = new EAyarCacheManager(iStartListenerThread);
	}
	return mInstance;
}

EAyar EAyarCacheManager::getAyarFromCache(const QString & iAyarSinifi,const QString & iAyarAdi)
{
	QMutexLocker ml(&mReadWriteMutex);

	EAyar retAyar;
	QPair<QString,QString> keyPair = qMakePair(iAyarSinifi,iAyarAdi);
	if (mAyarMap.contains(keyPair))
	{
		retAyar = mAyarMap.value(keyPair);
	}
	return retAyar ;
}

void EAyarCacheManager::_ayariCacheEkle(const EAyar & iAyar)
{
	QPair<QString,QString> keyPair = qMakePair(iAyar.getSinif(),iAyar.getAd());
	mAyarMap.remove(keyPair);
	mAyarMap.insert(keyPair,iAyar);
}

void  EAyarCacheManager::ayariCacheEkle(const EAyar & iAyar)
{
	QMutexLocker ml(&mReadWriteMutex);

	_ayariCacheEkle(iAyar);
}

void  EAyarCacheManager::updateCache()
{
	QMutexLocker ml(&mReadWriteMutex);
	mAyarMap.clear();
// 	QMutexLocker ml(&mReadWriteMutex);
//
// 	QList<QPair<QString,QString> > keys = mAyarMap.keys();
// 	for(int i= 0 ; i<keys.size();i++)
// 	{
// 		EAyarAlici alici(keys[i].first,keys[i].second);
// 		EAyar ayar = alici.veritabanindanGetir(false); // Mutex lockl� iken ayar� guncelleyemem
// 		_ayariCacheEkle(ayar);						   // Mutexi beklemeyen guncelleme metodunu cag�r�yom.
// 	}
}


QMutex EAyarAlici::msAyarBulMutex;
//////////////////////////////////////////////////////////////////////////

/**
 * Al�nmak istenen ayar�n s�n�f� ve ad� ile ilklendirilir.
 * \param iAyarSinifi
 * Ayar�n s�n�f�
 * \param iAyarAdi
 * Ayar�n ad�
 */
EAyarAlici::EAyarAlici(const QString & iAyarSinifi,const QString & iAyarAdi)
:mAyarSinifi(iAyarSinifi),mAyarAdi(iAyarAdi)
{
	ESYA_ORTAK_FUNC_BEGIN;
	ESYA_ORTAK_FUNC_END;
}

EAyarAlici::~EAyarAlici(void)
{
	ESYA_ORTAK_FUNC_BEGIN;
	ESYA_ORTAK_FUNC_END;
}

EAyar EAyarAlici::veritabanindanGetir(bool iCacheGuncelle)
{
	EAyar retYerelAyar;
	EAyar retGenelAyar;
	if(_genelAyarAl(&retGenelAyar))
	{
		if (retGenelAyar.isGenel() && !retGenelAyar.isDegistirilebilir())
		{//E�er ayar genel ve de�i�tirilemez ise geneldekinin d�n.
			if(iCacheGuncelle)
			{
				EAyarCacheManager::getInstance()->ayariCacheEkle(retGenelAyar);
			}
			return retGenelAyar;
		}
		if (_yerelAyarAl(&retYerelAyar))
		{
			//Ayar yerelde de bulundu o zaman yereli d�n.
			//<TODO> Burda yerelle genel aras�nda belki kar��la�t�rma yapmak gerekebilir
			ESYA_ORTAK_FUNC_END;
			if(iCacheGuncelle)
			{
				EAyarCacheManager::getInstance()->ayariCacheEkle(retYerelAyar);
			}
			return retYerelAyar;
		}
		//Ayar genelde oldu�u halde yerelde yoksa genel ayar� d�ner
		ESYA_ORTAK_FUNC_END;
		if(iCacheGuncelle)
		{
			EAyarCacheManager::getInstance()->ayariCacheEkle(retGenelAyar);
		}
		return retGenelAyar;
	}
	//<TODO> Bu k�s�m ayar�n genelde bulunamama durumunu ifade eder.
	if (_yerelAyarAl(&retYerelAyar))
	{
		//Ayar yerelde de bulundu o zaman yereli d�n.
		//<TODO> Burda yerelle genel aras�nda belki kar��la�t�rma yapmak gerekebilir
		ESYA_ORTAK_FUNC_END;
		if(iCacheGuncelle)
		{
			EAyarCacheManager::getInstance()->ayariCacheEkle(retYerelAyar);
		}
		return retYerelAyar;
	}
	//E�er ayar hem yerelde hem de genelde bulunamad� ise exception at
	ESYA_ORTAK_FUNC_ERROR(QString("S�n�f = %1 , Ad = %2 olan ayar hem genelde hem de yerelde bulunamad�").arg(mAyarSinifi).arg(mAyarAdi))
		throw EAyarException(EAyarException::AyarBulunamadi,"Ayar hem genelde hem de yerelde bulunamad�",__FILE__,__LINE__)	;

}

/**
 * �stenilen s�n�f ve isimdeki ayar� ilk �nce genel ayarlardan daha sonra da yerel ayarlardan arar.
 *
 * \return
 * Bulunan ayar� d�ner. I�lem s�ras�nda hata olu�ursa Exception atar.
 */
EAyar EAyarAlici::ayarBul(bool iAyariCachedeSakla/* =false */)
{
	ESYA_ORTAK_FUNC_BEGIN;
	QMutexLocker ml(&EAyarAlici::msAyarBulMutex);
	EAyar retAyar = EAyarCacheManager::getInstance()->getAyarFromCache(mAyarSinifi,mAyarAdi);
	if (retAyar.getDeger().isValid())
	{
		return retAyar;
	}
	return veritabanindanGetir(iAyariCachedeSakla);
}

/**
 * Genel ayarlardan ayar� almak i�in kullan�l�r.
 * \param oAyar
 * D�n�� tipi olarak kullan�lacak ayar
 * \return
 * Ayar bulunmu�sa true,bulunanam��sa false d�ner.
 */
bool EAyarAlici::_genelAyarAl(EAyar * oAyar)
{
	ESYA_ORTAK_FUNC_BEGIN;
	EAyar retAyar;
	try
	{
		EAyarManager * genelAyarMng = EAyarManagerFactory::getAyarManager(EAyarManagerFactory::Ayar_Tipi_Genel);
		if (!genelAyarMng)
		{
			ESYA_ORTAK_FUNC_ERROR("Genel ayarlar�n al�naca�� ayar manager olusturulamad�")
			throw EException("Genel ayarlar�n al�naca�� ayar manager olusturulamad�");
		}
		retAyar = genelAyarMng->getAyar(mAyarSinifi,mAyarAdi);
	}
	catch (EAyarException & exc)
	{
		//ESYA_ORTAK_FUNC_ERROR("Genel ayar�n al�nmas� s�ras�nda hata olustu"+exc.printStackTrace())
		return false;
	}
	catch (EVeritabaniException & exc)
	{
			//<TODO> Burda exception e�er veritaban�n�n a��lmamas�ndan yada sorgu hatas�ndan kaynaklan�yor ise exception'u ilet
		ESYA_ORTAK_FUNC_ERROR("Veritaban� i�lemi s�ras�nda hata olu�tu"+exc.printStackTrace())
		throw exc;
	}
	*oAyar = retAyar ;
	ESYA_ORTAK_FUNC_END;
	return true;
}

/**
 * Yerel  ayar�n al�nmas� i�in kullan�l�r.
 * \param oAyar
 * Bulunan ayar bunun �zerinden geri d�ner.
 * \return
 * Ayar al�nm��sa true, al�nmam��sa false d�ner.
 */
bool EAyarAlici::_yerelAyarAl(EAyar * oAyar)
{
	ESYA_ORTAK_FUNC_BEGIN;
	EAyar retAyar;
	try
	{
		EAyarManager * yerelAyarMng = EAyarManagerFactory::getAyarManager(EAyarManagerFactory::Ayar_Tipi_Yerel);
		if (!yerelAyarMng)
		{
			ESYA_ORTAK_FUNC_ERROR("Yerel ayarlar�n al�naca�� ayar manager olusturulamad�")
			throw EException("Yerel ayarlar�n al�naca�� ayar manager olusturulamad�");
		}
		retAyar = yerelAyarMng->getAyar(mAyarSinifi,mAyarAdi);
	}
	catch (EAyarException & exc)
	{
		//ESYA_ORTAK_FUNC_ERROR("Yerel ayar�n al�nmas� s�ras�nda hata olustu"+exc.printStackTrace());
		return false;
	}
	catch (EVeritabaniException & exc)
	{
		ESYA_ORTAK_FUNC_ERROR("Veritaban� i�lemi s�ras�nda hata olu�tu"+exc.printStackTrace());
		throw exc;
	}
	*oAyar = retAyar ;
	ESYA_ORTAK_FUNC_END;
	return true;
}

QList<EAyar> EAyarAlici::tumAyarlariAl()
{
	QList<EAyar> retAyarList;
	QList<QPair<QString,QString> > tumYerelAyarAdSinifList=EAyarManagerFactory::getAyarManager(EAyarManagerFactory::Ayar_Tipi_Yerel)->tumAyarlarSinifAdAl();
	QList<QPair<QString,QString> > tumGenelyarAdSinifList=EAyarManagerFactory::getAyarManager(EAyarManagerFactory::Ayar_Tipi_Genel)->tumAyarlarSinifAdAl();

	//genel listeye yerel ayar adlar�n� ekliyoruz.
	QList<QPair<QString,QString> > tumAyarSinifAdList;
	for (int k=0;k<tumYerelAyarAdSinifList.size();k++)
	{
		QPair<QString,QString> sinifAdPair=tumYerelAyarAdSinifList.at(k);
		if (!tumAyarSinifAdList.contains(sinifAdPair))
		{
			tumAyarSinifAdList.append(sinifAdPair);
		}
	}

	//genel listeye genel ayar adlar�n� ekliyoruz.
	for (int k=0;k<tumGenelyarAdSinifList.size();k++)
	{
		QPair<QString,QString> sinifAdPair=tumGenelyarAdSinifList.at(k);
		if (!tumAyarSinifAdList.contains(sinifAdPair))
		{
			tumAyarSinifAdList.append(sinifAdPair);
		}
	}

	//Sonra herbiri icin ayar� bulup genel listeye ekliyoruz.
	for (int k=0;k<tumAyarSinifAdList.size();k++)
	{
		QPair<QString,QString> sinifAdPair=tumAyarSinifAdList.at(k);
		try
		{
			EAyarAlici ayarAlici(sinifAdPair.first,sinifAdPair.second);
			EAyar bulunanAyar = ayarAlici.ayarBul();
			retAyarList.append(bulunanAyar);
		}
		catch (EException &exc)
		{
		}
	}
	return retAyarList;
}

QMap<QString,QList<EAyar> > EAyarAlici::tumGenelAyarlariAl()
{
	QMap<QString,QList<EAyar> > retMap;
	EAyarManager * pGenelAyarMng = EAyarManagerFactory::getAyarManager(EAyarManagerFactory::Ayar_Tipi_Genel);
	QStringList sinifList = pGenelAyarMng->tumAyarlarSinifAl();
	Q_FOREACH(QString sinif,sinifList)
	{
		QList<EAyar> ayarList = pGenelAyarMng->siniflaIliskiliAyarlariAl(sinif);
		retMap.insert(sinif,ayarList);
	}
	return retMap;
}
NAMESPACE_END
