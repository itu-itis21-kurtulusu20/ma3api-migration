#ifndef __SMIMECAPABILITIES__
#define __SMIMECAPABILITIES__

#include "SMIMECapability.h"

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
	class Q_DECL_EXPORT SMIMECapabilities  : public EASNWrapperTemplate<ASN1T_CMS_SMIMECapabilities,ASN1C_CMS_SMIMECapabilities>
	{
		QList<SMIMECapability> mList;

	public:
		SMIMECapabilities(void);
		SMIMECapabilities(const ASN1T_CMS_SMIMECapabilities &);
		SMIMECapabilities(const QByteArray &);
		SMIMECapabilities(const QList<SMIMECapability>& iList);
		SMIMECapabilities(const SMIMECapabilities&);

		SMIMECapabilities & operator=(const SMIMECapabilities&);
		friend bool operator==(const SMIMECapabilities & ,const SMIMECapabilities & );
		friend bool operator!=(const SMIMECapabilities & iRHS, const SMIMECapabilities & iLHS);

		int copyFromASNObject(const ASN1T_CMS_SMIMECapabilities&);
		int copyToASNObject(ASN1T_CMS_SMIMECapabilities& )const;
		void freeASNObject(ASN1T_CMS_SMIMECapabilities & )const;

		virtual ~SMIMECapabilities(void);

		// GETTERS AND SETTERS

		const QList<SMIMECapability> &getList() const;
	
		void setList(const QList<SMIMECapability> & iList);
		void appendCapability( const SMIMECapability & iCapability );
	};

}

#endif

