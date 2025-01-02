#include "ImzaDogrulamaSonucu.h"
#include <QRegExp>
#include "aciklamalar.h"
#include "EsyaCMS_DIL.h"
using namespace esya;

/**
* \brief
* ImzaDogrulamaSonucu constructoru. ��aret�i memberlar NULL olarak ilklendirilir.
*/
ImzaDogrulamaSonucu::ImzaDogrulamaSonucu(void)
:	mDogrulamaSonucu(KONTROL_TAMAMLANAMADI),
	pSID(NULL),
	pSignerCert(NULL)
{
}

/**
* \brief
* ImzaDogrulamaSonucu kopya constructoru. 
*
* \param const ImzaDogrulamaSonucu &iIDS
* �mza Do�rulama Sonucu
*/
ImzaDogrulamaSonucu::ImzaDogrulamaSonucu(const ImzaDogrulamaSonucu &iIDS)
:	mKontrolAdi( iIDS.getKontrolAdi()),
	mAciklama( iIDS.getAciklama()),
	mDogrulamaSonucu(iIDS.getDogrulamaSonucu()),
	mKontrolDetaylari( iIDS.getKontrolDetaylari()),
	mAltDogrulamaSonuclari( iIDS.getAltDogrulamaSonuclari()),
	pSID(NULL),
	pSignerCert(NULL)
{
	if (pSID && pSID!=iIDS.getSID())
		DELETE_MEMORY(pSID);
	if (pSignerCert && pSignerCert!=iIDS.getSignerCert())
		DELETE_MEMORY(pSID);

	if (iIDS.getSID()  )
		pSID = new SignerIdentifier(*iIDS.getSID());

	if (iIDS.getSignerCert())
		pSignerCert = new ECertificate(*iIDS.getSignerCert());
}

/**
* \brief
* ImzaDogrulamaSonucu kopya constructoru. 
* 
* \param 		const QString & iKontrolAdi
* Kontrol Ad�
*
* \param 		const QString & iAciklama
* A��klama
*
* \param 		const IDS_Type iDS
* �mza Ge�erlilik Durumu
*
* \param 		const QList<KontrolcuSonucu> & iKD
* Kontrol Detaylar�
*
* \param 		const QList<ImzaDogrulamaSonucu> & iADS
* Alt �mza Do�rulama Sonu�lar�
*
* \param 		const SignerIdentifier * ipSID
* �mzac� Tan�mlay�c�s�
*
* \param 		const ECertificate * ipSignerCert
* �mzac� Serttifikas�	
*/
ImzaDogrulamaSonucu::ImzaDogrulamaSonucu(	const QString & iKontrolAdi ,
											const QString & iAciklama,
											const IDS_Type iDS ,
											const QList<KontrolcuSonucu>  & iKD,
											const QList<ImzaDogrulamaSonucu>  & iADS,
											const SignerIdentifier* ipSID ,
											const ECertificate* ipSignerCert)
:	mKontrolAdi( iKontrolAdi ),
	mAciklama( iAciklama),
	mDogrulamaSonucu( iDS ),
	mKontrolDetaylari( iKD),
	mAltDogrulamaSonuclari( iADS ),
	pSID(NULL),
	pSignerCert(NULL)
{
	if (ipSID)
		pSID = new SignerIdentifier(*ipSID);

	if (ipSignerCert)
		pSignerCert = new ECertificate(*ipSignerCert);
}

ImzaDogrulamaSonucu& ImzaDogrulamaSonucu::operator=(const ImzaDogrulamaSonucu &iIDS)
{
	mKontrolAdi			= iIDS.getKontrolAdi();
	mAciklama			= iIDS.getAciklama();
	mDogrulamaSonucu	= iIDS.getDogrulamaSonucu();
	mKontrolDetaylari	= iIDS.getKontrolDetaylari();
	mAltDogrulamaSonuclari = iIDS.getAltDogrulamaSonuclari();
	
	if (pSID && pSID!=iIDS.getSID())
		DELETE_MEMORY(pSID);
	if (pSignerCert && pSignerCert!=iIDS.getSignerCert())
		DELETE_MEMORY(pSID);

	if (iIDS.getSID()  )
		pSID = new SignerIdentifier(*iIDS.getSID());

	if (iIDS.getSignerCert())
		pSignerCert = new ECertificate(*iIDS.getSignerCert());

	return *this;
}


/**
* \brief
* Kontrol Ad� alan�n� d�ner
*
* \return const QString & 
* Kontrol Ad�
*/
const QString &ImzaDogrulamaSonucu::getKontrolAdi() const 
{
	return mKontrolAdi;
}

/**
* \brief
* Kontrol Ad� alan�n� d�ner
*
* \return QString & 
* Kontrol Ad�
*/
QString &ImzaDogrulamaSonucu::getKontrolAdi()  
{
	return mKontrolAdi;
}

/**
* \brief
* Kontrol Ad� alan�n� belirler
*
* \param 		const QString & iKontrolAdi
* Kontrol Ad�
*/
void ImzaDogrulamaSonucu::setKontrolAdi(const QString & iKontrolAdi)
{
	mKontrolAdi =iKontrolAdi ;
}

/**
* \brief
* A��klama alan�n� d�ner
*
* \return const QString &
* A��klama
*/
const QString &ImzaDogrulamaSonucu::getAciklama() const 
{
	return mAciklama;
}

/**
* \brief
* A��klama alan�n� d�ner
*
* \return QString &
* A��klama
*/
QString &ImzaDogrulamaSonucu::getAciklama()  
{
	return mAciklama;
}

/**
* \brief
* �mzac� Tan�mlay�c�s�n� �dner
*
* \return	const SignerIdentifier * 
* �mzac� Tan�mlay�c�s�
*
*/
const SignerIdentifier * ImzaDogrulamaSonucu::getSID() const
{
	return pSID;
}

/**
* \brief
* �mzac� Tan�mlay�c�s�n� �dner
*
* \return SignerIdentifier * 
* �mzac� Tan�mlay�c�s�
*
*/
SignerIdentifier * ImzaDogrulamaSonucu::getSID() 
{
	return pSID;
}

/**
* \brief
* �mzac� Tan�mlay�c�s�n� belirler
*
* \param 		const SignerIdentifier * ipSID
* �mzac� Tan�mlay�c�s�
*
*/
void ImzaDogrulamaSonucu::setSID( const SignerIdentifier * ipSID)   
{
	if ( pSID != ipSID)
	{
		if (pSID)
			DELETE_MEMORY(pSID);
		if (ipSID)
			pSID = new SignerIdentifier(*ipSID);
	}
}

/**
* \brief
* �mzac� Sertifikas�n� d�ner
*
* \return const ECertificate * 
* �mzac� Sertifikas�
*/
const ECertificate * ImzaDogrulamaSonucu::getSignerCert() const  
{
	return pSignerCert;
}

/**
* \brief
* �mzac� Sertifikas�n� d�ner
*
* \return ECertificate * 
* �mzac� Sertifikas�
*/
ECertificate * ImzaDogrulamaSonucu::getSignerCert() 
{
	return pSignerCert;
}

/**
* \brief
* �mzac� Sertifikas�n� belirler
*
* \param 		const ECertificate * ipSignerCert
* �mzac� Sertifikas�
*/
void ImzaDogrulamaSonucu::setSignerCert( const ECertificate * ipSignerCert)   
{
	if ( pSignerCert != ipSignerCert)
	{
		if (pSignerCert)
			DELETE_MEMORY(pSignerCert);
		if (ipSignerCert)
			pSignerCert = new ECertificate(*ipSignerCert);
	}
}

/**
* \brief
* A��klama alan�n� belirler
*
* \param 		const QString & iAciklama
* A��klama
*
*/
void ImzaDogrulamaSonucu::setAciklama(const QString & iAciklama)
{
	mAciklama =iAciklama;
}

/**
* \brief
* �mza ge�erlilik durumunu d�ner
*
* \return  		const ImzaDogrulamaSonucu::IDS_Type & 
* �mza ge�erlilik durumu
*
*/
const ImzaDogrulamaSonucu::IDS_Type & ImzaDogrulamaSonucu::getDogrulamaSonucu() const
{
	return mDogrulamaSonucu;
}

/**
* \brief
* �mza ge�erlilik durumunu d�ner
*
* \return ImzaDogrulamaSonucu::IDS_Type & 
* �mza ge�erlilik durumu
*
*/
ImzaDogrulamaSonucu::IDS_Type & ImzaDogrulamaSonucu::getDogrulamaSonucu()
{
	return mDogrulamaSonucu ;
}

/**
* \brief
* �mza ge�erlilik durumunu belirler
*
* \param const ImzaDogrulamaSonucu::IDS_Type & iDS
* �mza ge�erlilik durumu
*
*/
void  ImzaDogrulamaSonucu::setDogrulamaSonucu(const ImzaDogrulamaSonucu::IDS_Type & iDS)
{
	mDogrulamaSonucu= iDS;
}

/**
* \brief
* Alt �mza Do�rulama Sonu�lar�n� d�ner
*
* \return	const QList<ImzaDogrulamaSonucu>& 
* Alt imza do�rulama sonu�lar�
*
*/
const	QList<ImzaDogrulamaSonucu>&	ImzaDogrulamaSonucu::getAltDogrulamaSonuclari() const 
{
	return mAltDogrulamaSonuclari;
}


/**
* \brief
* Alt �mza Do�rulama Sonu�lar�n� d�ner
*
* \return 	QList<ImzaDogrulamaSonucu>& 
* Alt imza do�rulama sonu�lar�
*
*/
QList<ImzaDogrulamaSonucu>&	ImzaDogrulamaSonucu::getAltDogrulamaSonuclari()  
{
	return mAltDogrulamaSonuclari;
}

/**
* \brief
* Alt �mza Do�rulama Sonu�lar�n� belirler
*
* \param 		const QList<ImzaDogrulamaSonucu>& iADS
* Alt imza do�rulama sonu�lar�
*
*/
void ImzaDogrulamaSonucu::setAltDogrulamaSonuclari(const QList<ImzaDogrulamaSonucu>& iADS)
{
	mAltDogrulamaSonuclari = iADS;
}


/**
* \brief
* Yeni bir alt imza do�rulama sonucu ekler
*
* \param 		const ImzaDogrulamaSonucu & iADS
* Alt imza do�rulama sonucu
*
*/
void ImzaDogrulamaSonucu::addAltDogrulamaSonucu(const ImzaDogrulamaSonucu & iADS)
{
	mAltDogrulamaSonuclari.append(iADS);
}

/**
* \brief
* Kontrol detaylar�n� d�ner
*
* \return const QList<KontrolcuSonucu> & 
* Kontrol Detaylar� 
*/
const QList<KontrolcuSonucu>& ImzaDogrulamaSonucu::getKontrolDetaylari() const 
{
	return mKontrolDetaylari;
}

/**
* \brief
* Kontrol detaylar�n� d�ner
*
* \return QList<KontrolcuSonucu> & 
* Kontrol Detaylar� 
*/
QList<KontrolcuSonucu>&	ImzaDogrulamaSonucu::getKontrolDetaylari() 
{
	return mKontrolDetaylari;
}

/**
* \brief
* Kontrol detaylar�n� belirler
*
* \param 		const QList<KontrolcuSonucu> & iKD
* Kontrol Detaylar� 
*/
void ImzaDogrulamaSonucu::setKontrolDetaylari(const QList<KontrolcuSonucu>& iKD )
{
	mKontrolDetaylari = iKD;
}
/**
* \brief
* Yeni bir kontrol detay� ekler.
*
* \param 		const KontrolcuSonucu & iKD
* Kontrol Detay�
*
*/
void ImzaDogrulamaSonucu::addKontrolDetayi(const KontrolcuSonucu& iKD)
{
	mKontrolDetaylari.append(iKD);
}

/**
* \brief
* Verilen metni verilen uzuluk kadar sa�a hizaland�r�r.
*
* \param 		const QString & iBlock
* Metin
*
* \param 		int iLen
* Hizalama miktar� 
*
* \return   	QT_NAMESPACE::QString
*/
QString ImzaDogrulamaSonucu::_indent(const QString &iBlock, int iLen)
{
	QString res = iBlock;
	QString after;
	after.fill(QChar(' '),iLen);
	res.replace(QRegExp("\n"),"\n" + after);
	res = after+res;
	return res;
}

/**
* \brief
* �mza ge�erlilik durumunu yaz�ya �evirir
*
* \return   	QT_NAMESPACE::QString
*/
QString ImzaDogrulamaSonucu::getDogrulamaSonucuAsString() const
{
	switch (mDogrulamaSonucu)
	{
	case GECERLI: 
		{
			return A_GECERLI;
		}
	case ALT_IMZACI_KONTROLLERI_SORUNLU: 
		{
			return A_ALT_IMZACI_KONTROLLERI_SORUNLU;
		}
	case KONTROLLER_SORUNLU: 
		{
			return A_KONTROLLER_SORUNLU;
		}
	case KONTROL_TAMAMLANAMADI: 
		{
			return A_KONTROL_TAMAMLANAMADI;
		}
	}
}


/**
* \brief
* �mza do�rulama sonucunu yaz�ya �evirir
* 
* \return   	QT_NAMESPACE::QString
*/
QString ImzaDogrulamaSonucu::toString() const
{
	QString st_sonuc;

	st_sonuc += QString("%1 [%2]\n").arg(LBL_KONTROLADI).arg(getKontrolAdi());
	st_sonuc += QString("%1 %2\n").arg(LBL_DOGRULAMASONUCU).arg(getDogrulamaSonucuAsString());
	st_sonuc += QString("%1 %2\n").arg(LBL_ACIKLAMA).arg(getAciklama());
	
	const QList<KontrolcuSonucu> & kontrolDetaylari = getKontrolDetaylari();
	if (kontrolDetaylari.size() > 0 )
	{
		st_sonuc += LBL_KONTROL_DETAYLARI;
		st_sonuc += LBL_EMPTY_LINE;
		for (int i = 0 ; i<kontrolDetaylari.size(); i++)
		{
			st_sonuc += _indent(kontrolDetaylari[i].toString(),INDENT_COUNT);
			st_sonuc += LBL_EMPTY_LINE;
		}
		st_sonuc += LBL_LINE;
	}

	const QList<ImzaDogrulamaSonucu> & ads = getAltDogrulamaSonuclari();
	if (ads.size() > 0 )
	{
		st_sonuc += LBL_LINE;
		for (int i = 0 ; i<ads.size(); i++)
		{
			st_sonuc += _indent(ads[i].toString(),INDENT_COUNT);
			st_sonuc += LBL_EMPTY_LINE;
		}
		st_sonuc += LBL_LINE;
	}
	return st_sonuc;
}


/**
* \brief
* ImzaDogrulamaSonucu destructoru
*/
ImzaDogrulamaSonucu::~ImzaDogrulamaSonucu(void)
{
	DELETE_MEMORY(pSID);
	DELETE_MEMORY(pSignerCert);
}

/**
* \brief
* �mza a�ac�nda yer alan ve do�rulanmas� gereken sertifikalar�n listesini d�ner.
*
* \param 		QList<ECertificate> & oTBVCerts
* Sertifika Listesi
*
*/
QList<ECertificate> ImzaDogrulamaSonucu::getTBVCerts()
{
	QList<ECertificate> tbvCerts;

	_collectTBVCerts(tbvCerts);

	return tbvCerts;
}

/**
* \brief
* �mza a�ac�nda yer alan ve do�rulanmas� gereken sertifikalar�n listesini olu�turur.
*
* \param 		QList<ECertificate> & oTBVCerts
* Sertifika Listesi
*
*/
void ImzaDogrulamaSonucu::_collectTBVCerts(QList<ECertificate>  & oTBVCerts)
{
	if (pSignerCert && oTBVCerts.indexOf(*pSignerCert)<0 )
		oTBVCerts.append(*pSignerCert);

	for (int i = 0 ; i < mAltDogrulamaSonuclari.size() ; i++ )
	{
		mAltDogrulamaSonuclari[i]._collectTBVCerts(oTBVCerts);
	}
}

