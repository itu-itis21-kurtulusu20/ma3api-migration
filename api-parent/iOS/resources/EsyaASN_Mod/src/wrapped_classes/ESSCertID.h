#ifndef __ESSCERTID__
#define __ESSCERTID__

#include "PKCS7IssuerSerial.h"
#include "PKCS7Hash.h"

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
	class Q_DECL_EXPORT ESSCertID  : public EASNWrapperTemplate<ASN1T_PKCS7_ESSCertID,ASN1C_PKCS7_ESSCertID>
	{
		bool mIssuerSerialPresent;
		PKCS7IssuerSerial mIssuerSerial;
		PKCS7Hash mHash;
			
	public:

		ESSCertID(void);
		ESSCertID( const ASN1T_PKCS7_ESSCertID & iESSCertID);
		ESSCertID( const ESSCertID &iESSCertID);
		ESSCertID( const QByteArray &iESSCertID);

		ESSCertID & operator=(const ESSCertID&);
		friend bool operator==(const ESSCertID& iRHS,const ESSCertID& iLHS);
		friend bool operator!=(const ESSCertID& iRHS, const ESSCertID& iLHS);

		int copyFromASNObject(const ASN1T_PKCS7_ESSCertID & iESSCertID);
		int copyToASNObject(ASN1T_PKCS7_ESSCertID & oESSCertID) const;
		void freeASNObject(ASN1T_PKCS7_ESSCertID& oESSCertID)const;

		virtual ~ESSCertID(void);

		// GETTERS AND SETTERS
		
		bool isIssuerSerialPresent()const;
		const PKCS7IssuerSerial & getIssuerSerial() const ;	
		const PKCS7Hash& getHash() const ;	

		void setIssuerSerial(const PKCS7IssuerSerial & );	
		void setHash(const PKCS7Hash & ) ;	
	
	};

}

#endif

