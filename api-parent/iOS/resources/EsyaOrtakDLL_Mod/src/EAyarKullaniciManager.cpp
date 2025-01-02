#define KERMEN_YEREL_KULLANICI_DB_FILE_NAME "eayarlar.esya" 

#define KERMEN_AYAR_TABLO_ADI_AYARLAR       "TBL_AYARLAR"
#define KERMEN_AYAR_TABLO_ADI_KULLANICILAR  "TBL_KULLANICILAR"
#define KERMEN_AYAR_TABLO_ADI_ACIK_DOSYALAR "TBL_ACIKDOSYALAR"
#define KERMEN_AYAR_TABLO_ADI_DIZIN_BILGISI "TBL_DIZINBILGISI"
#define KERMEN_AYAR_TABLO_ADI_DIZIN_OZNE    "TBL_DIZINOZNE"
#define KERMEN_AYAR_TABLO_ADI_GRUP_OZNE     "TBL_GRUPOZNE"
#define KERMEN_AYAR_TABLO_ADI_OZNE_BILGISI  "TBL_OZNEBILGISI"
#define KERMEN_AYAR_TABLO_ADI_VARSAYILAN_OZNELER "TBL_VARSAYILANOZNELER"

#define KERMEN_KULLANICI_TABLO_ALAN_ADI_VARSAYILAN "Varsayilan"
#define KERMEN_KULLANICI_TABLO_ALAN_ADI_EPOSTA     "EPosta"
#define KERMEN_KULLANICI_TABLO_ALAN_ADI_KulID		"KulID"
#define KERMEN_KULLANICI_TABLO_ALAN_ADI_Varsayilan  "Varsayilan"		

#define KERMEN_KULLANICI_SORGU_SOL "SELECT KulID,EPosta,Varsayilan from TBL_KULLANICILAR"
#define KERMEN_KULLANICI_SORGU_WHERE_STATEMENT " WHERE "

#define KERMEN_KULLANICI_UPDATE_SORGU_SOL "UPDATE TBL_KULLANICILAR set Varsayilan='true' WHERE EPosta='%1'"
#define KERMEN_KULLANICI_UPDATE_DIGERLERINI_PASIF_YAP "UPDATE TBL_KULLANICILAR set Varsayilan='false' where not EPosta='%1'"


#define KERMEN_KULLANICI_EKLE_SORGU "INSERT INTO TBL_KULLANICILAR (EPosta,Varsayilan) values ('%1','false')"


#include "EAyarKullaniciManager.h"
#include "EAyarValueTool.h"
#include "EVeritabani.h"
#include "FileUtil.h"
#include "EsyaOrtak_Ortak.h"
#include "EYerelAyarManager.h"

#define E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN // ESYA_ORTAK_FUNC_BEGIN
#define E_AYAR_KULLANICI_MANAGER_FUNC_END // ESYA_ORTAK_FUNC_END

#define AYAR_KULLANICI_CONNECTION "Ayar_Kullanici_Connection"

NAMESPACE_BEGIN(esya)

EAyarKullanici::EAyarKullanici()
:mKulEPosta(""),mKulID(-1)
{
	mIsNull = true;
}

/**
 * Mevcut bir kullan�c� bilgileri ilk ilklendirilir.
 * \param iKulID 
 * Kullanicinin veritaban�ndaki ID si
 * \param iKulEPosta 
 * Kullan�c�n�n Eposta adresi
 * \param iIsVarsayilan 
 * Varsayilan kullan�c� olup olmad���
 * \return 
 */
EAyarKullanici::EAyarKullanici(int iKulID,const QString & iKulEPosta,bool iIsVarsayilan)
:mKulID(iKulID),mKulEPosta(iKulEPosta),mIsVarsayilan(iIsVarsayilan)
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN
	mIsNull = false;
	E_AYAR_KULLANICI_MANAGER_FUNC_END
}

void EAyarKullanici::setKulID(int iKulID)
{	
	mKulID = iKulID ;
}

void EAyarKullanici::setKulEPosta(const QString & iKulEPosta)
{
	mKulEPosta = iKulEPosta ;
}

void EAyarKullanici::setIsVarsayilan(bool iIsVarsayilan)
{
	mIsVarsayilan = iIsVarsayilan ;
}

int EAyarKullanici::getKulId() const
{
	return mKulID;
}

QString EAyarKullanici::getKulEPosta() const
{
	return mKulEPosta.toLower();
}
bool EAyarKullanici::getIsVarsayilan() const
{
	return mIsVarsayilan ;
}

EAyarKullanici::~EAyarKullanici()
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN
	E_AYAR_KULLANICI_MANAGER_FUNC_END
}

/**
 * Herhangi bir kullan�c� bilgisi tutup tutmad���n� d�ner.
 * \return 
 * Herhangi bir kullan�c� bilgisi tutup tutmad���n� d�ner.
 */
bool EAyarKullanici::isNull()
{
	return mIsNull;
}

void EAyarKullanici::setIsNull(bool iIsNull)
{
	mIsNull = iIsNull ;
}


QMap<QString,EAyarKullaniciManager *> EAyarKullaniciManager::mInstanceMap=QMap<QString,EAyarKullaniciManager *>();
/**
 * Kullan�c�lar�n tutuldu�u veritaban� ile ilklendirilir.
 * \param iDbPath 
 * Veritaban� dosya yolu 
 */
EAyarKullaniciManager::EAyarKullaniciManager(const QString & iDbPath)
:mDBPath(iDbPath)
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN	
	E_AYAR_KULLANICI_MANAGER_FUNC_END
}

EAyarKullaniciManager::~EAyarKullaniciManager(void)
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN
	E_AYAR_KULLANICI_MANAGER_FUNC_END
}

/**
 * Verilen sorguyu veritaban�nda �al��t�r�r
 * \param iSorguStr 
 * �al��t�r�lak olan sorgu
 * \return 
 * Olu�an Query sonucunun d�ner.
 */
QSqlQuery * EAyarKullaniciManager::_dbSorguYap(const QSqlDatabase & iDb,const QString & iSorguStr)
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN
	//mDb.open();
	QSqlQuery * pQuery = NULL ;
	ParamList params;	
	try
	{
		EVeritabani veritabani(iDb);
		pQuery = veritabani.sorguYap(iSorguStr,params);		
	}
	catch (EVeritabaniException &exc)
	{		
		DELETE_MEMORY(pQuery);				
		QSqlDatabase::removeDatabase(AYAR_KULLANICI_CONNECTION);
		ESYA_ORTAK_FUNC_ERROR("Sorgulama sonucunda hata olustu"+exc.printStackTrace())
		throw exc;
	}			
	E_AYAR_KULLANICI_MANAGER_FUNC_END
	return pQuery;
}

/**
 * Verilen sorgu ile kullan�c�lar� �eker.
 * \param iSorguStr 
 * KUllan�lacan olan sorgu stringi
 * \return 
 * Sorgu sonucunda bulunan kullan�c�lar� d�ner.
 */
QList<EAyarKullanici> EAyarKullaniciManager::_kullaniciAramaSorgusuYap(const QString & iSorguStr)
{	
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN
	QList<EAyarKullanici> retKullaniciList;	
	QSqlQuery * pQuery = NULL ;
	EVeritabani vt=EVeritabani::sqLiteVTUret(mDBPath);	
	try
	{		
		pQuery = vt.sorguYap(iSorguStr,ParamList());		
	}
	catch (EException & exc) 
	{
		DELETE_MEMORY(pQuery);	
		ESYA_ORTAK_FUNC_ERROR("Kullan�c� getirme s�ras�nda hata olustu"+exc.printStackTrace())
		return retKullaniciList;
	}	
	while (pQuery->next()) 
	{
		EAyarKullanici kullaniciAyar(pQuery->value(0).toInt(),pQuery->value(1).toString(),EAyarValueTool::getBoolDeger(pQuery->value(2)));	
		retKullaniciList<<kullaniciAyar;	
	}
	DELETE_MEMORY(pQuery);	
	E_AYAR_KULLANICI_MANAGER_FUNC_END
	return retKullaniciList;		
}

/**
 * Varsay�lan kullan�c� (�u anda sistemdeki aktif kullan�c�) 'y� almak i�in kullan�lr.
 * \return 
 * Kullan�c� bilgilerini d�ner.
 */
EAyarKullanici EAyarKullaniciManager::varsayilanKullaniciGetir(bool iMutlakaDBdenOku/* =false */)
{
	 E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN;
	if(!iMutlakaDBdenOku)
	{
		if (!mAktifKullanici.isNull())
		{
			return mAktifKullanici;
		}
	}	

	EAyarKullanici retKullanici;
	QList<EAyarKullanici> retUsers = _kullaniciAramaSorgusuYap(_varsayilanKullaniciQuery());	
	if (retUsers.size()>1)
	{
		ESYA_ORTAK_FUNC_ERROR("Birden fazla Varsay�lan kullan�c� olamaz")
		throw EException("Birden fazla Varsay�lan kullan�c� olamaz",__FILE__,__LINE__);
	}
	if (retUsers.size() == 0)
	{
		ESYA_ORTAK_FUNC_ERROR("Hi�bir kullan�c� bulunamad�")
		return EAyarKullanici();
	}
	retKullanici = retUsers.at(0);
	retUsers.clear();
	mAktifKullanici = retKullanici ;
	mAktifKullanici.setIsNull(false);
	E_AYAR_KULLANICI_MANAGER_FUNC_END
	return retKullanici;	
}

/**
 * Sistemdeki t�m ilklendirilmi� kullan�c�lar� almak i�in kullan�l�r
 *
 * \return 
 * Bulunan kullan�c�lar�n listesini d�ner.
 */
QList<EAyarKullanici> EAyarKullaniciManager::tumKullanicilariGetir()
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN
	QList<EAyarKullanici> retKulList=_kullaniciAramaSorgusuYap(_tumKullanicilarQuery());
	E_AYAR_KULLANICI_MANAGER_FUNC_END
	return retKulList;
}

/**
 * Sistemdeki varsay�lan kullan�c� d���ndaki t�m kullan�c�lar� almak icin kullan�l�r.
 *
 * \return 
 * Kullan�c� listesini d�ner.
 */
QList<EAyarKullanici> EAyarKullaniciManager::varsayilanOlmayanKullanicilariGetir()
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN
	QList<EAyarKullanici> retKulList = _kullaniciAramaSorgusuYap(_varsayilanOlmayanKullanicilarQuery());
	E_AYAR_KULLANICI_MANAGER_FUNC_END
	return retKulList;
}

/**
 * Belirtilen Eposta ya sahip kullan�c�y� aktif kullan�c� yapmak icin kullan�l�r.
 * Yani aktif kullan�c�y� de�i�tirmek i�in kullan�l�r.
 * \param iKulEPosta 
 * Aktif yap�lacak kullan�c� eposta adresi
 */
bool EAyarKullaniciManager::varsayilanYap(const QString & iKulEPosta)
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN;
	EVeritabani vt=EVeritabani::sqLiteVTUret(mDBPath);			
	QSqlQuery * pQuery = NULL ;
	try
	{
		pQuery = vt.sorguYap(_varsayilanYapQuery(iKulEPosta),ParamList());
		if (!pQuery)
		{
			DELETE_MEMORY(pQuery);		
			ESYA_ORTAK_FUNC_ERROR("%1 kullanicisi varsayilan kullanici yapilamadi-Sorgu s�ras�nda hata olu�tu")
				throw EException(QString("%1 kullanicisi varsayilan kullanici yapilamadi-Sorgu s�ras�nda hata olu�tu").arg(iKulEPosta),__FILE__,__LINE__);		
			return false;
		}
		int rows = pQuery->numRowsAffected();
		DELETE_MEMORY(pQuery);	
		if(rows != 1) 
		{
			ESYA_ORTAK_FUNC_ERROR("%1 kullanicisi varsayilan kullanici yapilamadi-Etkilenen kay�t say�s� 0")
			throw EException(QString("%1 kullanicisi varsayilan kullanici yapilamadi").arg(iKulEPosta),__FILE__,__LINE__);				
			return false;
		}
		pQuery = vt.sorguYap(_digerleriniPasifYapQuery(iKulEPosta),ParamList());		
		rows = pQuery->numRowsAffected();
		DELETE_MEMORY(pQuery);	

	}
	catch (EException &exc)
	{	
		DELETE_MEMORY(pQuery);	
	}
	
	mAktifKullanici.setKulEPosta(iKulEPosta);
	mAktifKullanici.setIsNull(false);
	mAktifKullanici.setIsVarsayilan(true);
	return true;
	E_AYAR_KULLANICI_MANAGER_FUNC_END
}

/**
 * Kullan�c� y�neticisini almak i�in kullan�l�r.
 * \return 
 * Y�neticiyi d�ner.
 */
EAyarKullaniciManager * EAyarKullaniciManager::getInstance(const QString & iDbPath/* =NULL */)
{
	QString dbPath = iDbPath;
	if (dbPath.isEmpty())
	{
		dbPath = FileUtil::yerelAyarPath()+"/"+KERMEN_YEREL_KULLANICI_DB_FILE_NAME;
	}
	if (!mInstanceMap.contains(dbPath))
	{
		EAyarKullaniciManager * pAyarManager = new EAyarKullaniciManager(dbPath);
		mInstanceMap.insert(dbPath,pAyarManager);
		return pAyarManager;
	}
	else
	{
		return mInstanceMap.value(dbPath);
	}
	//ESYA_ORTAK_FUNC_END	
}

/**
 * Kullan�c�n� aktif kullan�c� yapmak icin kullan�lacak sorguyu olusturur
 * \param iKulEPosta 
 * Aktif hale getirilmek istenen kullan�c� eposta adresi
 * \return 
 * Olusan sorguyu d�ner
 */
QString EAyarKullaniciManager::_varsayilanYapQuery(const QString & iKulEPosta)
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN
	QString retSorgu = QString(KERMEN_KULLANICI_UPDATE_SORGU_SOL).arg(iKulEPosta);
	E_AYAR_KULLANICI_MANAGER_FUNC_END
	return retSorgu;
}

/**
 * Belirtilen kullan�c�lar d���ndaki t�m kullan�c�lar� pasif yapar.
 * \param iKulEPosta 
 * Aktif hale gelmi� olan kullan�c�
 * \return 
 * Sorguyu d�ner
 */
QString EAyarKullaniciManager::_digerleriniPasifYapQuery(const QString & iKulEPosta)
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN
	QString retSorgu = QString(KERMEN_KULLANICI_UPDATE_DIGERLERINI_PASIF_YAP).arg(iKulEPosta);
	E_AYAR_KULLANICI_MANAGER_FUNC_END
	return retSorgu ;
}


QString EAyarKullaniciManager::_ePostadanKullaniciAramaQuery(const QString & iKulEposta)
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN;	
	QString retSorgu = QString(KERMEN_KULLANICI_SORGU_SOL)+QString(KERMEN_KULLANICI_SORGU_WHERE_STATEMENT)+QString("%1='%2'").arg(KERMEN_KULLANICI_TABLO_ALAN_ADI_EPOSTA).arg(iKulEposta);
	E_AYAR_KULLANICI_MANAGER_FUNC_END;
	return retSorgu;
}
/**
 * Aktif kullan�c�y� almak icin kullan�lan sorguyu olusturur.
 *
 * \return 
 * Sorguyu d�ner.
 */
QString EAyarKullaniciManager::_varsayilanKullaniciQuery()
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN
	QString retSorgu = QString(KERMEN_KULLANICI_SORGU_SOL)+QString(KERMEN_KULLANICI_SORGU_WHERE_STATEMENT)+EAyarValueTool::getEsitlikStatementBool(KERMEN_KULLANICI_TABLO_ALAN_ADI_VARSAYILAN,true);
	E_AYAR_KULLANICI_MANAGER_FUNC_END;
	return retSorgu;
}

/**
 * T�m kullan�c�lar� almak icin kullan�lan sorguyu getirir.
 * \return 
 * Sorguyu d�ner
 */
QString EAyarKullaniciManager::_tumKullanicilarQuery()
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN
	QString retSorgu = QString(KERMEN_KULLANICI_SORGU_SOL);
	E_AYAR_KULLANICI_MANAGER_FUNC_END
	return retSorgu;
}

/**
 * Varsay�lan kullan�c� d���ndaki kullan�c�lar� almak icin kullan�lan sorguyu olusturur.
 * \return 
 * Sorguyu d�ner.
 */
QString EAyarKullaniciManager::_varsayilanOlmayanKullanicilarQuery()
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN
	QString retSorgu = QString(KERMEN_KULLANICI_SORGU_SOL)+QString(KERMEN_KULLANICI_SORGU_WHERE_STATEMENT)+EAyarValueTool::getEsitlikStatementBool(KERMEN_KULLANICI_TABLO_ALAN_ADI_VARSAYILAN,false);
	E_AYAR_KULLANICI_MANAGER_FUNC_END
	return retSorgu;
}

bool EAyarKullaniciManager::kullaniciSil(const QString &iKulEPosta)
{
	//////////////////////////////////////////////////////////////////////////
	QList<EAyarKullanici> buluananKullanicilar = _kullaniciAramaSorgusuYap(_ePostadanKullaniciAramaQuery(iKulEPosta));
	if (buluananKullanicilar.isEmpty())
	{
		throw EException(QString("Kullan�c�lar tablosunda %1 Epostaya sahip kullan�c� bulunamad���ndan silme i�lemi yap�lamad�.").arg(iKulEPosta));
	}

	Q_FOREACH(EAyarKullanici kullanici,buluananKullanicilar)
	{
		QString lKulIdStr = QString("%1").arg(kullanici.getKulId());
		//Ayarlar tablosundan kullan�c�n�n bilgileri siliniyor
		EAyarManager * pYerelAyarManager = EYerelAyarManager::getInstance();
		pYerelAyarManager->tablodanIlgiliKayitlariSil(KERMEN_AYAR_TABLO_ADI_AYARLAR,KERMEN_KULLANICI_TABLO_ALAN_ADI_KulID,lKulIdStr);		
	
		//A��k dosyalardan kullan�c�ya ait bilgiler siliniyor.
		pYerelAyarManager->tablodanIlgiliKayitlariSil(KERMEN_AYAR_TABLO_ADI_ACIK_DOSYALAR,KERMEN_KULLANICI_TABLO_ALAN_ADI_KulID,lKulIdStr);

		//GDM ile ilgili bilgiler siliniyor
		pYerelAyarManager->tablodanIlgiliKayitlariSil(KERMEN_AYAR_TABLO_ADI_GRUP_OZNE,KERMEN_KULLANICI_TABLO_ALAN_ADI_KulID,lKulIdStr);
		pYerelAyarManager->tablodanIlgiliKayitlariSil(KERMEN_AYAR_TABLO_ADI_DIZIN_OZNE,KERMEN_KULLANICI_TABLO_ALAN_ADI_KulID,lKulIdStr);
		pYerelAyarManager->tablodanIlgiliKayitlariSil(KERMEN_AYAR_TABLO_ADI_VARSAYILAN_OZNELER,KERMEN_KULLANICI_TABLO_ALAN_ADI_KulID,lKulIdStr);
		pYerelAyarManager->tablodanIlgiliKayitlariSil(KERMEN_AYAR_TABLO_ADI_DIZIN_BILGISI,KERMEN_KULLANICI_TABLO_ALAN_ADI_KulID,lKulIdStr);
		pYerelAyarManager->tablodanIlgiliKayitlariSil(KERMEN_AYAR_TABLO_ADI_OZNE_BILGISI,KERMEN_KULLANICI_TABLO_ALAN_ADI_KulID,lKulIdStr);

		//Haric Tablosundan siliniyor.
		pYerelAyarManager->tablodanIlgiliKayitlariSil(KERMEN_YEREL_AYAR_TABLO_ADI_GRUP_HARIC_OZNELER,KERMEN_KULLANICI_TABLO_ALAN_ADI_KulID,lKulIdStr);


		//Son olarak kullan�c�lar tablosundan siliniyor
		pYerelAyarManager->tablodanIlgiliKayitlariSil(KERMEN_AYAR_TABLO_ADI_KULLANICILAR,KERMEN_KULLANICI_TABLO_ALAN_ADI_KulID,lKulIdStr);
		//E�er varsay�lansa herhangi bir kullan�c� varsay�lan olarak atan�yor
		if (kullanici.getIsVarsayilan())
		{
			QList<EAyarKullanici> tumKullanicilar = tumKullanicilariGetir();
			if (!tumKullanicilar.isEmpty())
			{
				EAyarKullanici kullanici=tumKullanicilar.at(0);
				varsayilanYap(kullanici.getKulEPosta());
			}			
		}
	}
	return true;
}

bool EAyarKullaniciManager::kullaniciEkle(const QString & iKulEPosta)
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN;
	QList<EAyarKullanici> tumKullanicilar = tumKullanicilariGetir();
	Q_FOREACH(EAyarKullanici kullanici,tumKullanicilar)
	{
		if (kullanici.getKulEPosta() == iKulEPosta)
		{
			return true;			
		}
	}	
	EVeritabani vt=EVeritabani::sqLiteVTUret(mDBPath);			
	QSqlQuery * pQuery = NULL ;
	try
	{
		pQuery = vt.sorguYap(_kullaniciEkleQuery(iKulEPosta),ParamList());
		if (!pQuery)
		{
			DELETE_MEMORY(pQuery);		
			ESYA_ORTAK_FUNC_ERROR("%1 kullanicisi eklenemedi-Sorgu s�ras�nda hata olu�tu");
			throw EException(QString("%1 kullanicisi eklenemedi-Sorgu s�ras�nda hata olu�tu").arg(iKulEPosta),__FILE__,__LINE__);		
			return false;
		}
		int rows = pQuery->numRowsAffected();
		DELETE_MEMORY(pQuery);	
		if(rows != 1) 
		{
			ESYA_ORTAK_FUNC_ERROR("%1 kullanicisi eklenemedi.-Etkilenen kay�t say�s� 0")			
			return false;
		}		

	}
	catch (EException &exc)
	{	
		DELETE_MEMORY(pQuery);	
		ESYA_ORTAK_FUNC_ERROR(QString("%1 kullanicisi eklenemedi. I�lemler s�ras�nda hata olu�tu. Hata = %1").arg(exc.printStackTrace()));
		return false;
	}
	return true;	
}

QString EAyarKullaniciManager::_kullaniciEkleQuery(const QString & iKulEPosta)
{
	E_AYAR_KULLANICI_MANAGER_FUNC_BEGIN
	QString retSorgu = QString(KERMEN_KULLANICI_EKLE_SORGU).arg(iKulEPosta);
	E_AYAR_KULLANICI_MANAGER_FUNC_END
	return retSorgu;
}

void EAyarKullaniciManager::cacheTemizle()
{
	mAktifKullanici=EAyarKullanici();
}

static void removeKullaniciManager(const QString & iDbPath);
NAMESPACE_END
