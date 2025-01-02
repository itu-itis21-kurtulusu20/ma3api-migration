#include "EAyarLdap.h"
#include "EsyaOrtak_Ortak.h"
#include "OrtakDil.h"
#include "PersistenceFacade.h"
#include "FileUtil.h"
#include "EGenelAyarManager.h"
using namespace esya;
NAMESPACE_BEGIN(esya)

/**
 * Kullanilan LDAP �zellikleri ile ilklendirilir.
 * \param iLdapID 
 * LDAP ayar�n veritaban�ndaki ID si
 * \param &irIP 
 * LDAP IP numaras�
 * \param iPort 
 * LPAP port numaras�	
 * \param iBaglantiTipi 
 * LDAP ba�lant� tipi
 * \param iSizeLimit 
 * LDAP b�y�kl�k limiti
 * \param iTimeLimit 
 * LDAP sorgu zaman limiti
 * \param &irSearchBase 
 * Kullan�lacak olan arama taban�	
 * \param iLdapTipi 
 * LDAP tipi
 * \param iVarsayilan 
 * Varsay�lan LDAP olup olmad���
 * \return 
 */
EAyarLdap::EAyarLdap(int iLdapID,
		  const QString &irIP,
		  int iPort,
		  int iBaglantiTipi,
		  int iSizeLimit,
		  int iTimeLimit,
		  const QString &irSearchBase,
		  EAyarLdap::LDAP_TIPI iLdapTipi,
		  bool iVarsayilan,
		  QObject * parent
		  )
		  :
		  QObject(parent),
		  mLdapID(iLdapID),
		  mIP(irIP),
		  mPort(iPort),
		  mBaglantiTipi(iBaglantiTipi),
		  mSizeLimit(iSizeLimit),
		  mTimeLimit(iTimeLimit),
		  mSearchBase(irSearchBase),
		  mLdapTipi(iLdapTipi),
		  mVarsayilan(iVarsayilan)
{
	ESYA_ORTAK_FUNC_BEGIN;
	ESYA_ORTAK_FUNC_END;
}

EAyarLdap::EAyarLdap(const EAyarLdap & iAyarLdap)
{
	mLdapID=iAyarLdap.getLdapID();
	mIP=iAyarLdap.getIP();
	mPort=iAyarLdap.getPort();
	mBaglantiTipi=iAyarLdap.getBaglantiTipi();
	mSizeLimit=iAyarLdap.getSizeLimit();
	mTimeLimit=iAyarLdap.getTimeLimit();
	mSearchBase=iAyarLdap.getSearchBase();
	mLdapTipi=iAyarLdap.getLdapTipi();
	mVarsayilan=iAyarLdap.isVarsayilan();
}

EAyarLdap & EAyarLdap::operator =(const EAyarLdap & iAyarLdap)
{
	mLdapID=iAyarLdap.getLdapID();
	mIP=iAyarLdap.getIP();
	mPort=iAyarLdap.getPort();
	mBaglantiTipi=iAyarLdap.getBaglantiTipi();
	mSizeLimit=iAyarLdap.getSizeLimit();
	mTimeLimit=iAyarLdap.getTimeLimit();
	mSearchBase=iAyarLdap.getSearchBase();
	mLdapTipi=iAyarLdap.getLdapTipi();
	mVarsayilan=iAyarLdap.isVarsayilan();
	return *this;
}

bool EAyarLdap::operator ==(const EAyarLdap & iAyarLdap)
{
	bool retValue = false;
	retValue = (mIP==iAyarLdap.getIP())&& 
				(mPort==iAyarLdap.getPort())&&
				(mBaglantiTipi==iAyarLdap.getBaglantiTipi())&&
				(mSizeLimit==iAyarLdap.getSizeLimit())&&
				(mTimeLimit=iAyarLdap.getTimeLimit())&&
				(mSearchBase==iAyarLdap.getSearchBase())&&
				(mLdapTipi==iAyarLdap.getLdapTipi());
	return retValue;
}

bool EAyarLdap::operator<(const EAyarLdap & iAyarLdap)
{
	return (mLdapID<iAyarLdap.getLdapID());
}

EAyarLdap::~EAyarLdap()
{
	ESYA_ORTAK_FUNC_BEGIN;
	ESYA_ORTAK_FUNC_END;
}

int EAyarLdap::getLdapID() const
{
	return mLdapID;
}

const QString &EAyarLdap::getIP() const
{
	return mIP;
}

int EAyarLdap::getPort() const
{
	return mPort;
}

int EAyarLdap::getBaglantiTipi() const
{
	return mBaglantiTipi;
}

int EAyarLdap::getSizeLimit() const
{
	return mSizeLimit;
}

int EAyarLdap::getTimeLimit() const
{
	return mTimeLimit;
}

const QString &EAyarLdap::getSearchBase() const
{
	return mSearchBase;
}

EAyarLdap::LDAP_TIPI EAyarLdap::getLdapTipi() const
{
	return mLdapTipi;
}

bool EAyarLdap::isVarsayilan() const
{
	return mVarsayilan;
}

/**
* LDAP tpini strin olarak geri d�ner.
*
* \param iLDAPTipi 
* LDAP tipi
* \return
String olarak ldap tipini d�ner.
*/
QString EAyarLdap::lDAPTipiToStr(EAyarLdap::LDAP_TIPI iLDAPTipi)
{			   
	switch(iLDAPTipi)
	{
	case EAyarLdap::LDAP_TIPI_BILINMEYEN:
		return DIL_LDAP_TIPI_GENERIC;
		break;
	case  EAyarLdap::LDAP_TIPI_AD:  
		return DIL_LDAP_TIPI_ACTIVE_DIRECTORY;
		break;
	case EAyarLdap::LDAP_TIPI_CP:
		return DIL_LDAP_TIPI_CRITICAL_PATH;
		break;
	case EAyarLdap::LDAP_TIPI_NS:
		return DIL_LDAP_TIPI_NETSCAPE;
		break;
	
	default:
		return DIL_LDAP_TIPI_BILINMEYEN;
		break;
	}
}

/**
 * Kullan�labilecek LDAP'lar�n isimlerinin listesini d�ner.
 * \return 
 * �sim listesi d�ner.
 */
QStringList EAyarLdap::lDAPTipiStrList()
{
	QStringList retItems;
	retItems<<DIL_LDAP_TIPI_BILINMEYEN
		<<DIL_LDAP_TIPI_ACTIVE_DIRECTORY
		<<DIL_LDAP_TIPI_CRITICAL_PATH
		<<DIL_LDAP_TIPI_NETSCAPE
		<<DIL_LDAP_TIPI_GENERIC;
	return retItems;
}

/**
*  Ba�lant� tipini string olarak d�ner.
*/
QString EAyarLdap::lDAPBaglantiTipiToStr(EAyarLdap::LDAP_TIPI iLDAPBaglantiTipi)
{
	switch(iLDAPBaglantiTipi)
	{
	case 0:
		return DIL_LDAP_BAGLANTI_NORMAL;
		break;
	case 1:
		return DIL_LDAP_BAGLANTI_GUVENLI;
		break;
	default:
		return DIL_LDAP_BAGLANTI_BILINMEYEN;
		break;
	}
}



/**
 * LDAP baglant� tipinin listesini d�ner.
 * \return 
 * Listeyi d�ner
 */
QStringList EAyarLdap::lDAPBaglantiTipiStrList()
{
	QStringList retList;
	retList<<DIL_LDAP_BAGLANTI_NORMAL
		  <<DIL_LDAP_BAGLANTI_GUVENLI
		  <<DIL_LDAP_BAGLANTI_BILINMEYEN;
	return retList;
}

QList<EAyarLdap> EAyarLdap::tumLDAPlariAl()
{
	QList<EAyarLdap> retLDAPList;
	EVeritabani vt=EVeritabani::sqLiteVTUret(FileUtil::genelAyarPath()+"/"+KERMEN_GENEL_AYAR_DB_FILE_NAME);	
	const QSqlDatabase & db = vt.database();
	IMapper * pLDAPMapper = PersistenceFacade::getInstance()->getMapper((QSqlDatabase *)&db,KERMEN_CLASS_EAYAR_LDAP);
	QList<QObject *> objListesi = pLDAPMapper->getAll();	
	bool isFirst=true;
	Q_FOREACH(QObject * pObj,objListesi)
	{
		EAyarLdap * pLDAP = qobject_cast<EAyarLdap *>(pObj);
		if (pLDAP)
		{/*
			if (isFirst)
			{	
				EAyarLdap domainLDAP(-1,"",pLDAP->getPort(),pLDAP->getBaglantiTipi(),pLDAP->getSizeLimit(),pLDAP->getTimeLimit(),pLDAP->getSearchBase(),pLDAP->getLdapTipi(),pLDAP->isVarsayilan());
				retLDAPList<<domainLDAP;
			}*/
			retLDAPList<<EAyarLdap(*pLDAP);
			DELETE_MEMORY(pLDAP);
		}
	}
	DELETE_MEMORY(pLDAPMapper);	
	return retLDAPList;
}
NAMESPACE_END
