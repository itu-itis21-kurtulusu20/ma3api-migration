#include "OzetKontrolcu.h"
#include "KriptoUtils.h"
#include "EsyaCMS_DIL.h"

#include "Logger.h"

using namespace esya;

const QString OzetKontrolcu::ST_KONTROLADI = "OZET KONTROL�";

OzetKontrolcu::OzetKontrolcu( ImzaDogrulamaAlgoritmasi * ipDA )
: ImzaKontrolcu(ipDA)
{
}

OzetKontrolcu::OzetKontrolcu( ImzaDogrulamaAlgoritmasi * ipDA ,const ParametreListesi & iKP)
: ImzaKontrolcu(ipDA,iKP)
{
}


/**
* \brief
* Imzal� yap� �zerindeki �zet de�erini kriptografik olarak do�rular.
*
* \param 		const SignerInfo & iSI
* �mzac�
*
* \param 		KontrolcuSonucu & oKS
* Kontrol Detay�
*
* \return   	bool
*/
bool OzetKontrolcu::kontrolYap(const SignerInfo & iSI, KontrolcuSonucu & oKS)
{
	oKS.setKontrolAdi(DIL_IMZ_OZET_KONTROLU);

	QByteArray digest;
	const SignedData* pSD = iSI.getParentData();
	const SignerInfo *parentSigner = iSI.getParent();

	// Ata �mzac� tan�ml� ise  bu bir seri imzad�r
	if (parentSigner)
	{
        Logger::log("Parent signer var");
        digest = KriptoUtils::calculateDigest(parentSigner->getSignature(),"SHA-1"/*iSI.getDigestAlgorithm()*/);
	}
	else if ( pSD->getEncapContentInfo().getEContentPresent() ) // Imzal� veri de EContent Var m�?
	{
        Logger::log("Encap content info var");
		if (pSD->isStreamed()) // Imzal� Veri bir Stream objesi mi
		{
            Logger::log("Imzali veri bir stream objesi");
            /*
			int res = ((StreamedSignedData*)pSD)->getDigest(iSI.getDigestAlgorithm(),digest);
			if (res!= SUCCESS )
			{
				DEBUGLOGYAZ(ESYACMS_MOD,A_DIGEST_NOT_FOUND);		
				oKS.setKontrolSonucu(KontrolcuSonucu::BASARISIZ);
				oKS.setAciklama(A_DIGEST_NOT_FOUND);
				return false;			
			}
            //*/
		}
		else  // Stream de�ilse �zet veri i�indeki econtent de�eri �zerinden hesaplanabilir.
		{
            Logger::log("Imzali veri stream degil");
            digest = KriptoUtils::calculateDigest(pSD->getEncapContentInfo().getEContent(),"SHA-1"/*iSI.getDigestAlgorithm()*/);
		}
	}
	else // Ayr�k Imza ise plain data parametre olarak verilmi� olmal�
	{
        /*
		QVariant pdParam = mKontrolParams.parametreDegeriAl(PN_PLAINDATA); 
		if (!pdParam.isNull()) //PlanData ByteArray olarak verilmi� mi?
		{
			QByteArray pdBytes = pdParam.toByteArray();
			digest = KriptoUtils::calculateDigest(pdBytes,iSI.getDigestAlgorithm());
		}
		else // Plain Data Dosya olarak verilmi� olabilir.
		{
			pdParam = mKontrolParams.parametreDegeriAl(PN_PLAINDATAFILENAME);
			if (!pdParam.isNull())
			{
				QString pdFileName = pdParam.toString();
				QFile pdFile(pdFileName);
				if (!pdFile.open(QIODevice::ReadOnly))
				{
					DEBUGLOGYAZ(ESYACMS_MOD,A_DIGEST_NOT_FOUND);		
					oKS.setKontrolSonucu(KontrolcuSonucu::KONTROL_TAMAMLANAMADI);
					oKS.setAciklama(A_PDFILE_NOT_FOUND);
					return false;			
				}
				digest = KriptoUtils::calculateStreamDigest(pdFile,iSI.getDigestAlgorithm());
			}
			else // Plain Data verilmemi�se do�rulama hatas� d�n
			{
				DEBUGLOGYAZ(ESYACMS_MOD,A_DIGEST_NOT_FOUND);		
				oKS.setKontrolSonucu(KontrolcuSonucu::KONTROL_TAMAMLANAMADI);
				oKS.setAciklama(A_PD_NOT_FOUND);
				return false;			
			}
		}
        //*/
	}

	if ( iSI.hasDigest(digest)) // �zetler uyu�uyor mu?
	{
		DEBUGLOGYAZ(ESYACMS_MOD,QString("%1 %2").arg(ST_KONTROLADI).arg(A_BASARILI));		
		oKS.setKontrolSonucu(KontrolcuSonucu::BASARILI);
		oKS.setAciklama(QString("%1 %2").arg(ST_KONTROLADI).arg(A_BASARILI));
		return true;			
	}
	else // �zetler uyu�muyorsa do�rulama hatas� d�n
	{
		DEBUGLOGYAZ(ESYACMS_MOD,A_DIGEST_NOT_FOUND);		
		oKS.setKontrolSonucu(KontrolcuSonucu::BASARISIZ);
		oKS.setAciklama(A_DIGEST_NOT_MATCH);
		return false;			
	}
}

OzetKontrolcu::~OzetKontrolcu(void)
{
}
