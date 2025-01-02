
#ifndef __SERIALNUMBER__
#define __SERIALNUMBER__

#include "Explicit.h"
#include "ortak.h"
#include "EException.h"


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
	class Q_DECL_EXPORT SerialNumber  : public EASNWrapperTemplate<ASN1T_EXP_CertificateSerialNumber,ASN1C_EXP_CertificateSerialNumber>
	{
		QString mValue;

	public:

		SerialNumber(void);
		SerialNumber( const ASN1T_EXP_CertificateSerialNumber &);
		SerialNumber( const SerialNumber &);
		SerialNumber( const QByteArray &);
		SerialNumber( const QString &);

		SerialNumber & operator=(const SerialNumber&);

		Q_DECL_EXPORT friend bool operator==(const SerialNumber& iRHS,const SerialNumber& iLHS);
		Q_DECL_EXPORT friend bool operator!=(const SerialNumber& iRHS, const SerialNumber& iLHS);

		bool operator<(const SerialNumber& iRHS)const;		
		
		int copyFromASNObject(const ASN1T_EXP_CertificateSerialNumber & iSN);
		int copyToASNObject(ASN1T_EXP_CertificateSerialNumber & oECI) const;
		void freeASNObject(ASN1T_EXP_CertificateSerialNumber& oSN)const;

		virtual ~SerialNumber(void);

		// GETTERS AND SETTERS

		QString getValue() const ;	

		void setValue(const QString & )  ;	

		//long toLong()const;
	};

}

#endif

