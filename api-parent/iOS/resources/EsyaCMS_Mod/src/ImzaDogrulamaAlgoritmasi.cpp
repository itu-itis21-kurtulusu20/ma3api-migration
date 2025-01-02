#include "ImzaDogrulamaAlgoritmasi.h"
#include "EsyaCMS_DIL.h"

using namespace esya;

/**
* \brief
* ImzaDogrulamaAlgoritmasi constructoru
*
*/
ImzaDogrulamaAlgoritmasi::ImzaDogrulamaAlgoritmasi(const QList<ECertificate> & iCertList)
: mCertList(iCertList)
{
}


/**
* \brief
* Do�rulamada kullan�lacak sertifika listesini d�ner
*
* \return 	const QList<ECertificate> & 
* Sertifika Listesi
*
*/
const QList<ECertificate> & ImzaDogrulamaAlgoritmasi::getCertList() const 
{
	return mCertList;
}

/**
* \brief
* Do�rulamada kullan�lacak sertifika listesini belirler
*
* \param 		const QList<ECertificate> & iCertList
* Sertifika Listesi
*
*/
void ImzaDogrulamaAlgoritmasi::setCertList(const QList<ECertificate> &iCertList)
{
	mCertList = iCertList;
}

/**
* \brief
* Paralel �mza Kontrolc�leri d�ner
*
* \return   	const QList<ImzaKontrolcu*>&
* Paralel �mza Kontrolc�ler
*/
QList<ImzaKontrolcu*>& ImzaDogrulamaAlgoritmasi::getParalelKontrolculer()
{
	return mParalelKontrolculer;
}

/**
* \brief
* Paralel �mza Kontrolc�leri d�ner
*
* \return   	const QList<ImzaKontrolcu*>&
* Paralel �mza Kontrolc�ler
*/
const QList<ImzaKontrolcu*>&  ImzaDogrulamaAlgoritmasi::getParalelKontrolculer() const
{
	return mParalelKontrolculer;
}

/**
* \brief
* �mza Do�rulama Algoritamas�na bir paralel imza kontrolc� ekler.
*
* \param 		ImzaKontrolcu * pIK
* �mza Kontrolc�
*
*/
void ImzaDogrulamaAlgoritmasi::addParalelKontrolcu(ImzaKontrolcu * pIK)
{
	mParalelKontrolculer.append(pIK);
}

/**
* \brief
* Seri �mza Kontrolc�leri d�ner
*
* \return   	const QList<ImzaKontrolcu*>&
* Seri �mza Kontrolc�ler
*/
QList<ImzaKontrolcu*>& ImzaDogrulamaAlgoritmasi::getSeriKontrolculer()
{
	return mParalelKontrolculer;
}

/**
* \brief
* Seri �mza Kontrolc�leri d�ner
*
* \return   	const QList<ImzaKontrolcu*>&
* Seri �mza Kontrolc�ler
*/
const QList<ImzaKontrolcu*>&  ImzaDogrulamaAlgoritmasi::getSeriKontrolculer() const
{
	return mParalelKontrolculer;
}

/**
* \brief
* �mza Do�rulama Algoritamas�na bir seri imza kontrolc� ekler.
*
* \param 		ImzaKontrolcu * pIK
* �mza Kontrolc�
*
*/
void ImzaDogrulamaAlgoritmasi::addSeriKontrolcu(ImzaKontrolcu * pIK)
{
	mSeriKontrolculer.append(pIK);
}


/**
* \brief
* Verilen �mza Kontrolc� listesini bo�alt�r.
*
* \param 		QList<ImzaKontrolcu * > & iKontrolculer
* �mza Kontrolcu Listesi 
*
*/
void ImzaDogrulamaAlgoritmasi::_freeKontrolculer(QList<ImzaKontrolcu*> & iKontrolculer)
{
	for (int i = 0 ; i<iKontrolculer.size(); i++ )
	{
		DELETE_MEMORY(iKontrolculer[i]);
	}
}


/**
* \brief
* �mzal� veriyi do�rular. B�t�n imzac�lar i�in �mza Do�rulama Algoritmas�ndaki b�t�n �mza Kontrolc�lerin _kontrolYap() metodunu s�rayla �al��t�r�r.
*
* \param 		const SignedData & iSD
* �mzal� Veri
*
* \param 		ImzaDogrulamaSonucu & oIDS
* �mza Do�rulama sonucu
*
* \throws 		
*
* \remark
*
* \return   	bool
*/
bool ImzaDogrulamaAlgoritmasi::kontrolYap( const SignedData & iSD, ImzaDogrulamaSonucu & oIDS)
{
	oIDS.setKontrolAdi(A_IMZALIVERI_KONTROLU);

	bool verified = true;
	for (int i = 0 ; i<iSD.getSignerInfos().size(); i++ )
	{
		ImzaDogrulamaSonucu ids;
		bool res = _kontrolYap(PARALEL,iSD.getSignerInfos()[i],ids);
		oIDS.getAltDogrulamaSonuclari().append(ids);
		if (verified)  verified = res; 
	}

	if(!verified)
	{
		oIDS.setDogrulamaSonucu(ImzaDogrulamaSonucu::ALT_IMZACI_KONTROLLERI_SORUNLU);
		oIDS.setAciklama(A_ALT_IMZACI_KONTROLLERI_SORUNLU);
	}
	else
	{
		oIDS.setDogrulamaSonucu(ImzaDogrulamaSonucu::GECERLI);
		oIDS.setAciklama(A_IMZALIVERI_KONTROLU_GECERLI);
	}
	return verified;
}


/**
* \brief
* �mzac� imzas�n� do�rular. �mza Do�rulama Algoritmas�ndaki b�t�n �mza Kontrolc�lerin _kontrolYap() metodunu s�rayla �al��t�r�r.
*
* \param 		ImzaDogrulamaAlgoritmasi::K_Type iType
* �mza Tipi
*
* \param 		const SignerInfo & iSI
* �mzac�
*
* \param 		ImzaDogrulamaSonucu & oIDS
* �mza Do�rulama Sonucu
*
* \return   	bool
* true: Do�rulama Ba�ar�l� false : Do�rulama Ba�ar�s�z
*/
bool ImzaDogrulamaAlgoritmasi::_kontrolYap( ImzaDogrulamaAlgoritmasi::K_Type iType, const SignerInfo& iSI, ImzaDogrulamaSonucu & oIDS)
{
	bool verified = true;
	const SignerIdentifier & sid = iSI.getSID();

	oIDS.setKontrolAdi(QString(A_IMZACI_KONTROLU_1).arg(iSI.getSID().toString()));
	oIDS.setSID(&sid);

	ECertificate cert;
	if (( iSI.getParentData()->getCertFromCertificates(sid,cert) || SignedData::getCertFromCertificates(mCertList,sid,cert) ) )
	{
		// Signing Cert Bulundu;
		oIDS.setSignerCert(&cert);
	}


	const QList<ImzaKontrolcu*> & kontrolculer = (iType == PARALEL) ? mParalelKontrolculer : mSeriKontrolculer;

	for (int i = 0 ; i<kontrolculer.size(); i++ )
	{
		KontrolcuSonucu ks;
		bool res = kontrolculer[i]->kontrolYap(iSI,ks);
		oIDS.getKontrolDetaylari().append(ks);
		if (verified) verified = res;
	}
	if(!verified)
	{
		oIDS.setDogrulamaSonucu(ImzaDogrulamaSonucu::KONTROLLER_SORUNLU);
		oIDS.setAciklama(A_KONTROLLER_SORUNLU);
	}
	for (int i = 0 ; i<iSI.getSigners().size(); i++ )
	{
		ImzaDogrulamaSonucu ids;
		bool res = _kontrolYap(SERI,iSI.getSigners()[i],ids);
		oIDS.getAltDogrulamaSonuclari().append(ids);
		if (verified) verified = res; 
	}
	if(!verified && oIDS.getDogrulamaSonucu() != ImzaDogrulamaSonucu::KONTROLLER_SORUNLU)
	{
		oIDS.setDogrulamaSonucu(ImzaDogrulamaSonucu::ALT_IMZACI_KONTROLLERI_SORUNLU);
		oIDS.setAciklama(A_ALT_IMZACI_KONTROLLERI_SORUNLU);
	}
	
	if (verified)
	{
		oIDS.setDogrulamaSonucu(ImzaDogrulamaSonucu::GECERLI);
		oIDS.setAciklama(A_GECERLI);
	}

	return verified;
}

/**
* \brief
* ImzaDogrulamaAlgoritmasi destructoru
*
*/
ImzaDogrulamaAlgoritmasi::~ImzaDogrulamaAlgoritmasi(void)
{
	_freeKontrolculer(mParalelKontrolculer);
	_freeKontrolculer(mSeriKontrolculer);
}
