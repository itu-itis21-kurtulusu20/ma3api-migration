#include "SignedData.h"
#include "ExtensionGenerator.h"
#include "cmslibrary_global.h"
#include "FileUtil.h"
#include "EASNException.h"
#include "ECMSException.h"
#include "KriptoUtils.h"
#include "Logger.h"
#include "AlgorithmList.h"

using namespace esya;


#define IMZALI_HEADER "\x06\x09\x2A\x86\x48\x86\xF7\x0D\x01\x07\x02"
#define IMZALI_HEADERLEN 11

const ASN1T_CMS_CMSVersion SignedData::DEFAULT_VERSION	= SD_DEFAULT_VERSION;
const ASN1TObjId SignedData::DEFAULT_DIGEST_ALGORITHM	= SD_DEFAULT_DIGEST_ALGORITHM;

SignedData::SignedData(void)
:	mVersion( DEFAULT_VERSION ) , mIsStreamed(false),mAyrikImza(false)
{
}

SignedData::SignedData(const QByteArray & iSD)
:	mIsStreamed(false), mAyrikImza(false)
{
	constructObject(iSD);
}

SignedData::SignedData(const ASN1T_CMS_SignedData & iSD)
:	mIsStreamed(false),mAyrikImza(false)
{
	copyFromASNObject(iSD);
}

SignedData::SignedData(const SignedData& iSD)
:	mIsStreamed(false),
	mVersion(iSD.getVersion()),
	mEncapContentInfo(iSD.getEncapContentInfo()),
	mCertificates(iSD.getCertificates()),
	mCRLs(iSD.getCRLs()),
	mDigestAlgorithms(iSD.getDigestAlgorithms()),
	mSignerInfos(iSD.getSignerInfos())
{
	mAyrikImza = iSD.isAyrik();
}

SignedData& SignedData::operator=(const SignedData& iSD)
{
	mIsStreamed			= iSD.isStreamed();
	mVersion			= iSD.getVersion();
	mEncapContentInfo	= iSD.getEncapContentInfo();
	mCertificates		= iSD.getCertificates();
	mCRLs				= iSD.getCRLs();
	mDigestAlgorithms	= iSD.getDigestAlgorithms();
	mSignerInfos		= iSD.getSignerInfos();
	mAyrikImza			= iSD.isAyrik();
	return (*this);
}

SignedData::SignedData(const ContentInfo & iCI)
:	mIsStreamed(false)
{
	ContentInfo cInfo(iCI);
	if ( cInfo.getContentType() != (ASN1TObjId)CMS_id_signedData)
	{
		ERRORLOGYAZ(ESYACMS_MOD,"Imzal� veri de�il.");
		throw ECMSException("Imzal� veri de�il.",__FILE__,__LINE__);
	}
	constructObject(cInfo.getContent());
}

bool esya::operator==(const SignedData & iRHS , const SignedData & iLHS)
{
	/* Not Implemented Yet */
	return false;
}

bool esya::operator!=(const SignedData & iRHS ,const SignedData & iLHS )
{
	return ( !( iRHS == iLHS ) );
}

int SignedData::copyFromASNObject(const ASN1T_CMS_SignedData &iSD)
{
	mVersion			= iSD.version;
	mEncapContentInfo.copyFromASNObject(iSD.encapContentInfo);

	AlgorithmIdentifier().copyAIs( iSD.digestAlgorithms, mDigestAlgorithms );
	SignerInfo().copySignerInfos( iSD.signerInfos , mSignerInfos,NULL,this );
	if (iSD.m.certificatesPresent)
		ECertChoices().copyCS( iSD.certificates , mCertificates );
	if (iSD.m.crlsPresent)
		RevocationInfoChoice().copyRICs( iSD.crls, mCRLs );

	mAyrikImza = !mEncapContentInfo.getEContentPresent();

	return SUCCESS;
}

int SignedData::copyToASNObject(ASN1T_CMS_SignedData & oSD)const
{
	oSD.version = mVersion;
	
	mEncapContentInfo.copyToASNObject(oSD.encapContentInfo);

	AlgorithmIdentifier().copyAIs( mDigestAlgorithms ,  oSD.digestAlgorithms);
	SignerInfo().copySignerInfos(  mSignerInfos , oSD.signerInfos );

	oSD.m.certificatesPresent	= ( mCertificates.size()	> 0 );
	oSD.m.crlsPresent			= ( mCRLs.size()			> 0 );
	
	if (oSD.m.certificatesPresent)
		ECertChoices().copyCS( mCertificates , oSD.certificates );
	if (oSD.m.crlsPresent)
		RevocationInfoChoice().copyRICs( mCRLs , oSD.crls );

	return SUCCESS;
}

void SignedData::freeASNObject(ASN1T_CMS_SignedData & oSD)const
{
	EncapContentInfo().freeASNObject(oSD.encapContentInfo);
	AlgorithmIdentifier().freeASNObjects(oSD.digestAlgorithms);
	SignerInfo().freeASNObjects(oSD.signerInfos);

	if ( oSD.m.certificatesPresent )
	{
		ECertChoices().freeASNObjects(oSD.certificates);
	}
	if ( oSD.m.crlsPresent )
	{
		RevocationInfoChoice().freeASNObjects(oSD.crls);
	}
}

bool SignedData::isStreamed()const
{
	return mIsStreamed;
}


const ASN1T_CMS_CMSVersion	& SignedData::getVersion()const
{
	return mVersion;
}

const EncapContentInfo	& SignedData::getEncapContentInfo()const
{
	return mEncapContentInfo;
}

const QList<AlgorithmIdentifier> & SignedData::getDigestAlgorithms()const
{
	return mDigestAlgorithms;
}

const QList<ECertChoices> & SignedData::getCertificates()const
{
	return mCertificates;
}

const QList<RevocationInfoChoice> & SignedData::getCRLs()const
{
	return mCRLs;
}

const QList<SignerInfo>	& SignedData::getSignerInfos()const
{
	return mSignerInfos;
}

QList<SignerInfo> & SignedData::getSignerInfos()
{
	return mSignerInfos;
}


void SignedData::setVersion(const ASN1T_CMS_CMSVersion & iVersion)
{
	mVersion = iVersion;
}

void SignedData::setEncapContenInfo( const EncapContentInfo & iECI)
{
	mEncapContentInfo = iECI;
	mAyrikImza = !mEncapContentInfo.getEContentPresent();
}

void SignedData::addDigestAlgorithm(const AlgorithmIdentifier & iDigestAlgorithm)
{
	mDigestAlgorithms.append(iDigestAlgorithm);
}

void SignedData::addCertificate(const ECertChoices & iCertificate)
{
	if ( mCertificates.indexOf(iCertificate) < 0 )
		mCertificates.append(iCertificate);
}

void SignedData::addCRL(const RevocationInfoChoice & iCRL)
{
	mCRLs.append(iCRL);
}

void SignedData::addSignerInfo(const SignerInfo& iSignerInfo)
{
	mSignerInfos.append(iSignerInfo);
}

const QByteArray SignedData::getPlainData()
{
	return mEncapContentInfo.getEContent();
}

SignedData::~SignedData(void)
{
}


/**
* \brief
* Verilen SignerIdentifier yap�s� ile imzac�n�n sertifikas�n� bulur
* 
* \param iCertificates 
* sertifikalar�n aranaca�� liste
*
* \param iSID
* SignerIdentifier
*
*
* \param oCertificate
* �mzalayan Sertifika
* 
* 
*/
bool SignedData::getCertFromCertificates(const QList<ECertificate>& iCertificates,const SignerIdentifier & iSID , ECertificate & oCertificate)
{
	for (int i = 0; i < iCertificates.size(); i++)
	{
		const TBSCertificate &  tbsCertificate = iCertificates[i].getTBSCertificate();
		
		if (iSID.getType() == SignerIdentifier::issuerAndSerialNumber )
		{
			if ( tbsCertificate.getIssuer() != iSID.getIssuer()  
				|| tbsCertificate.getSerialNumber() != iSID.getSerialNumber() )
			{
				continue;		
			}
			
			oCertificate = iCertificates[i];
			return true;

		}
		else if ( iSID.getType() == SignerIdentifier::subjectKeyIdentifier )	//SubjectKeyIdentifier geldi
		{
			//iPsid->u.subjectKeyIdentifier.
			//sertifikan�n i�inde extensionlarda SubjectKeyIdentifier var m� bak!
			SubjectKeyIdentifier ski1,ski2(iSID.getSubjectKeyIdentifier());
			if (ExtensionGenerator::getSKIExtension(tbsCertificate.getExtensions(),ski1) != SUCCESS)
				continue;
			if ( ski1 == ski2)
			{
				oCertificate = iCertificates[i];
				return true;
			}
		}
		
	}

	//hi�bir sertifikay� bulamazsa false d�nd�r�r.
	return false;
}

/**
* \brief
* Verilen SignerIdentifier yap�s� ile imzac�n�n sertifikas�n� bulur
* 
*
* \param iSID
* SignerIdentifier
*
*
* \param oCertificate
* �mzalayan Sertifika
* 
* 
*/
bool SignedData::getCertFromCertificates(const SignerIdentifier & iSID, ECertificate & oCertificate) const
{	
	QList<ECertificate> certs;
	for (int i = 0; i < mCertificates.size(); i++)
	{
		if ( mCertificates[i].getType() != T_Certificate) continue;

		certs.append(mCertificates[i].getCertificate());
	}
	return getCertFromCertificates(certs,iSID,oCertificate);
}


/**
* \brief
* Paralel imzac� ekler.
* 
* \param iSigner 
* �mzalac� objesi
*
*
* \param iCert
* �mzalayan Sertifika
* 
* 
*/
int SignedData::addParallelSigner(const SignerInfo& iSigner , const ECertificate & iSignerCert )
{

	
	SignerInfo signer(iSigner); 
	signer.setParent(NULL);
	signer.setParentData(this);
	mSignerInfos.append(iSigner);
	mDigestAlgorithms.append(iSigner.getDigestAlgorithm());
	addCertificate(ECertChoices(iSignerCert));


	return SUCCESS;
}

/**
* \brief
* Paralel BES imzac�s� ekler.
* 
* \param aData 
* �mzalanacak Veri
*
* \param iAB 
* �mzalama s�ras�nda kullan�lacak Kapal� Anahtar Bilgisi
*
* \param iCert
* �mzalayan Sertifika
* 
* \param iVersion
* �mza Yap�s� Versionu
* 
*/
int SignedData::addParallelBESSigner(	const SignerParam & iSP, const QByteArray & iPlainData )
{

    Logger::log("SignedData::addParallelBESSigner - calculate digest");
    QByteArray digest = KriptoUtils::calculateDigest( iPlainData, "SHA-256");
	SignerParam sp(iSP);
	sp.setDigest(digest);
    Logger::log("SignedData::addParallelBESSigner - construct bes signer");
	SignerInfo*  pSigner = SignerInfo::constructBESSigner(sp,false,false,NULL,this);
	pSigner->addUnsignedAttributes(sp.getAdditionalUnsignedAttributes());
    Logger::log("SignedData::addParallelBESSigner - add parallel signer");
	addParallelSigner(*pSigner,iSP.getCert());
	DELETE_MEMORY(pSigner);


	return SUCCESS;
}

int SignedData::addParallelBESSigners(	const SignParam & iSP,const QByteArray& iPlainData )
{


	const QList<SignerParam> & signers = iSP.getSignerParams();
	for (int i = 0 ; i < signers.size(); i++)
	{
		addParallelBESSigner(signers[i],iPlainData);
	}

	return SUCCESS;


}



/**
* \brief
* imzal� datay� do�rular
* 
* \param iCertificates
* Do�rulamada kullan�lacak sertifika listesi
*
* 
*/
bool SignedData::verifyData(const QList<ECertificate> & iCertificates) const
{
	for (int i = 0 ; i < mSignerInfos.size(); i++ )
	{
		if ( !mSignerInfos[i].verifySigner(iCertificates,false,true) ) 
		{	
			ERRORLOGYAZ(ESYACMS_MOD,QString("�mzac� : %1 i�in do�rulama ger�ekle�tirilemedi.").arg(mSignerInfos[i].getSID().toString()));
			return false;
		}
	}

	return true;
}

/**
* \brief
* ContentInfo yap�s� i�inden SignedData y� al�r ve geri d�ner
* 
* \param iCI
* Content Info yap�s�
*
*
*/ 
SignedData SignedData::fromContentInfo(const QByteArray& iCI)
{
	ContentInfo cInfo(iCI);
	if ( cInfo.getContentType() != (ASN1TObjId)CMS_id_signedData)
	{
		ERRORLOGYAZ(ESYACMS_MOD,"Imzal� veri de�il.");
		throw ECMSException("Imzal� veri de�il.",__FILE__,__LINE__);
	}
	return SignedData(cInfo.getContent());
}

/**
* \brief
* �mzal� data n�n imzac� a�ac�n�n en alt�ndaki seri imzac�y� d�ner
* 
* \param iSigner
* �mzac� a�ac�n�n en alttaki seri imzac�s� bulunacak dal�
*
* \return 
* En alttaki seri imzac�
*/ 
SignerInfo * SignedData::lastSerialSigner(SignerInfo * iSigner)
{
	QList<SignerInfo> *signers = &mSignerInfos;
	if (iSigner && iSigner->getSigners().isEmpty())
		return iSigner;
	
	if (iSigner)
		signers = &(iSigner->mSigners);

	if (signers->isEmpty()) return iSigner;

	return  lastSerialSigner(&(*signers)[0]);
}

/**
* \brief
* Verilen byte dizisinin bir imzal� veri olup olmad���n�n kontrol eder
*
* \param iData 
* �mzal� olup olmad��� kontrol edilecek veri
*
* \return 
* true : �mzal� false : �mzal� de�il
*/ 
bool SignedData::imzaliMi(const QByteArray &iData)
{
	return (iData.indexOf(QByteArray(IMZALI_HEADER,IMZALI_HEADERLEN)) >= 0);
}

const AlgorithmIdentifier defaultDigestAlgorithm()
{
	return  AlgorithmIdentifier(SignedData::DEFAULT_DIGEST_ALGORITHM);
}

const ASN1T_CMS_CMSVersion defaultVersion()
{
	return  SignedData::DEFAULT_VERSION;
}
