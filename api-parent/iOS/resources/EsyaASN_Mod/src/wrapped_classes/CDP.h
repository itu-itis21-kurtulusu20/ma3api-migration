#ifndef __CDP__
#define __CDP__

#include "Name.h"

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
	class Q_DECL_EXPORT CDP
	{

		QString mAdresTipi;
		QString mCDPAdresi;
		Name*	mCRLIssuer;
		QString mReasonFlags;
	
	public:
		CDP(void);
		CDP(const QString& , const QString & , const Name* , const QString &);
		CDP(const CDP& );

		CDP & operator=(const CDP& ) ;
		
		friend bool operator==(const CDP& , const CDP&);
		friend bool operator!=(const CDP& , const CDP&);

		const QString & adresTipiAl()const;
		const QString & cdpAdresiAl()const;
		const Name	* crlIssuerAl()const;
		const QString & reasonFlagsAl()const;

		void adresTipiBelirle(const QString & );
		void cdpAdresiBelirle(const QString & );
		void crlIssuerBelirle(const Name* );
		void reasonFlagsBelirle(const QString & );

		virtual ~CDP(void);
	};

}

#endif

