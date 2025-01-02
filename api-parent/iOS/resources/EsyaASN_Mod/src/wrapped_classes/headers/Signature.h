
#ifndef __SIGNATURE__
#define __SIGNATURE__

#include "ECertificate.h"
#include "EBitString.h"
#include "ocsp.h"

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
	class Q_DECL_EXPORT Signature  : public EASNWrapperTemplate<ASN1T_OCSP_Signature,ASN1C_OCSP_Signature>
	{
	   AlgorithmIdentifier	mSignatureAlgorithm;
	   EBitString			mSignature;
	   QList<ECertificate>	mCerts;
		

	public:
		Signature(const Signature&);
		Signature(const ASN1T_OCSP_Signature & iSignature);
		Signature(const QByteArray & iSignature);
		Signature(void);

		Signature& operator=(const Signature&);
		Q_DECL_EXPORT friend bool operator==(const Signature& iRHS,const Signature& iLHS);
		Q_DECL_EXPORT friend bool operator!=(const Signature& iRHS, const Signature& iLHS);

		int copyFromASNObject(const ASN1T_OCSP_Signature& iSignature);
		int copyToASNObject(ASN1T_OCSP_Signature & oSignature) const;
		void freeASNObject(ASN1T_OCSP_Signature & oSignature)const;

		virtual ~Signature(void);

		// GETTERS AND SETTERS	

		const AlgorithmIdentifier& getSignatureAlgorithm() const;
		const QList<ECertificate>& getCerts()const;
		const EBitString & getSignature() const ;

		void setSignatureAlgorithm(const AlgorithmIdentifier &iSignatureAlg);
		void setSignature(const EBitString & iSignature);
		void addCert(const ECertificate&);
	};
}

#endif

