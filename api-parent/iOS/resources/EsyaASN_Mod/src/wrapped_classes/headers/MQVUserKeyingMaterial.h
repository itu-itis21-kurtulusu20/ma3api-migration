#ifndef __MQVUSERKEYINGMATERIAL__
#define __MQVUSERKEYINGMATERIAL__

#include "cms.h"
#include "ortak.h"
#include "SubjectPublicKeyInfo.h"
#include "ESeqOfList.h"
#include "algorithms.h"
#include "myasndefs.h"


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
	class Q_DECL_EXPORT MQVUserKeyingMaterial : public EASNWrapperTemplate<ASN1T_CMS_MQVuserKeyingMaterial,ASN1C_CMS_MQVuserKeyingMaterial>
	{
		QByteArray mAddedUKM;
		SubjectPublicKeyInfo mEphemeralPublicKey; 
		bool mAddedUKMPresent;


	public:
		MQVUserKeyingMaterial(void);
		MQVUserKeyingMaterial(const QByteArray & );
		MQVUserKeyingMaterial(const QByteArray & , const SubjectPublicKeyInfo &);
		MQVUserKeyingMaterial(const ASN1T_CMS_MQVuserKeyingMaterial & );
		MQVUserKeyingMaterial(const MQVUserKeyingMaterial& );
		

		MQVUserKeyingMaterial& operator=(const MQVUserKeyingMaterial& );
		Q_DECL_EXPORT friend bool operator==(const MQVUserKeyingMaterial & iRHS, const MQVUserKeyingMaterial& iLHS);
		Q_DECL_EXPORT friend bool operator!=(const MQVUserKeyingMaterial & iRHS, const MQVUserKeyingMaterial& iLHS);

		int copyFromASNObject(const ASN1T_CMS_MQVuserKeyingMaterial & ) ;
		int copyToASNObject(ASN1T_CMS_MQVuserKeyingMaterial & oUKM) const;
		void freeASNObject(ASN1T_CMS_MQVuserKeyingMaterial & oUKM)const;

		virtual ~MQVUserKeyingMaterial(void);

		// GETTERS AND SETTERS

		bool isAddedUKMPresent()const ;
		const QByteArray &getAddedUKM()const ;
		const SubjectPublicKeyInfo &getEphemeralPublicKey() const;

		void setAddedUKM(const QByteArray& );
		void setEphemeralPublicKey(const SubjectPublicKeyInfo&) ;

	
	};

}

#endif
