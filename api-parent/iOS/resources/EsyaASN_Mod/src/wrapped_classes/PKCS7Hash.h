#ifndef __PKCS7HASH__
#define __PKCS7HASH__

#include "pkcs7.h"
#include "ortak.h"

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
	class Q_DECL_EXPORT PKCS7Hash  : public EASNWrapperTemplate<ASN1T_PKCS7_Hash,ASN1C_PKCS7_Hash>
	{
		QByteArray mData;
		
			
	public:

		PKCS7Hash(void);
		PKCS7Hash( const ASN1T_PKCS7_Hash & iHash);
		PKCS7Hash( const PKCS7Hash &iHash);
		PKCS7Hash( const QByteArray &iHash);

		PKCS7Hash & operator=(const PKCS7Hash&);
		friend bool operator==(const PKCS7Hash& iRHS,const PKCS7Hash& iLHS);
		friend bool operator!=(const PKCS7Hash& iRHS, const PKCS7Hash& iLHS);

		int copyFromASNObject(const ASN1T_PKCS7_Hash & iHash);
		int copyToASNObject(ASN1T_PKCS7_Hash & oHash) const;
		void freeASNObject(ASN1T_PKCS7_Hash& oHash)const;

		virtual ~PKCS7Hash(void);

		// GETTERS AND SETTERS
		
		const QByteArray & getData() const ;	
		void setData(const QByteArray& ) ;	
	
	};

}

#endif

