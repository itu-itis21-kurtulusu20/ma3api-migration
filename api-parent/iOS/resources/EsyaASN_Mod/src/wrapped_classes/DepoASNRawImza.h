
#ifndef __DEPOASNRAWIMZA__
#define __DEPOASNRAWIMZA__

#include "ortak.h"
#include "ASN_SertifikaDeposu.h"
#include "ESeqOfList.h"


namespace esya
{
	/**
	* \ingroup EsyaASN
	* 
	* ASN1 wrapper s�n�f�. Detaylar i�in .asn d�k�man�na bak�n�z
	*
	*
	* \author dindaro
	*
	*/
	class Q_DECL_EXPORT DepoASNRawImza : public EASNWrapperTemplate<ASN1T_SD_DepoASNRawImza,ASN1C_SD_DepoASNRawImza>
	{
		QByteArray	mPublicKeyHash;
		QByteArray	mImza;

	public:
		DepoASNRawImza(void);
		DepoASNRawImza(const ASN1T_SD_DepoASNRawImza & iRI);
		DepoASNRawImza(const QByteArray & iRI);
		DepoASNRawImza(const QByteArray &iPKHash ,const QByteArray & iImza);
		DepoASNRawImza(const DepoASNRawImza&iKS);

		DepoASNRawImza & operator=(const DepoASNRawImza & iRI);
		friend bool operator==(const DepoASNRawImza& iRHS, const DepoASNRawImza& iLHS);
		friend bool operator!=(const DepoASNRawImza& iRHS, const DepoASNRawImza& iLHS);


		int copyFromASNObject(const ASN1T_SD_DepoASNRawImza& iRI);
		int copyToASNObject(ASN1T_SD_DepoASNRawImza & oRI) const;
		void freeASNObject(ASN1T_SD_DepoASNRawImza& oRI)const;

		int copyDepoASNRawImzaList(const QList<DepoASNRawImza> iList ,ASN1TPDUSeqOfList & oRIs);
		int	copyDepoASNRawImzaList(const ASN1TPDUSeqOfList & iKSs, QList<DepoASNRawImza>& oList);

		virtual ~DepoASNRawImza(void);

		const QByteArray & getPublicKeyHash() const;
		const QByteArray & getImza() const;

		void setPublicKeyHash(const QByteArray & iPKHash) ;
		void setImza(const QByteArray & iImza) ;

	};

}

#endif

