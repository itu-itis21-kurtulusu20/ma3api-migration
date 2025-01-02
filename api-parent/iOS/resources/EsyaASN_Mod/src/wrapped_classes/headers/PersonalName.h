
#ifndef __PERSONALNAME__
#define __PERSONALNAME__

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
	class Q_DECL_EXPORT PersonelName  : public EASNWrapperTemplate<ASN1T_EXP_PersonalName,ASN1C_EXP_PersonalName>
	{
		QString mSurName;
		QString mGivenName;
		QString mInitials;
		QString mGenerationQualifier;

		bool mGivenNamePresent;
		bool mInitialsPresent;
		bool mGenerationQualifierPresent;

	public:

		PersonelName(void);
		PersonelName( const ASN1T_EXP_PersonalName &);
		PersonelName( const PersonelName &);
		PersonelName( const QByteArray &);

		PersonelName & operator=(const PersonelName&);

		Q_DECL_EXPORT friend bool operator==(const PersonelName& iRHS,const PersonelName& iLHS);
		Q_DECL_EXPORT friend bool operator!=(const PersonelName& iRHS, const PersonelName& iLHS);

		int copyFromASNObject(const ASN1T_EXP_PersonalName & iPN);
		int copyToASNObject(ASN1T_EXP_PersonalName & oPN) const;
		void freeASNObject(ASN1T_EXP_PersonalName& oPN)const;

		virtual ~PersonelName(void);

		// GETTERS AND SETTERS

		const QString& getSurName() const ;	
		const QString& getGivenName() const ;	
		const QString& getInitials() const ;	
		const QString& getGenerationQualifier() const ;	

		bool isGivenNamePresent()const;
		bool isInitialsPresent()const;
		bool isGenerationQualifierPresent()const;


		void setSurName(const QString& ) ;	
		void setGivenName(const QString& );	
		void setInitials(const QString& ) ;	
		void setGenerationQualifier(const QString& ) ;	

		void setGivenNamePresent(bool );
		void setInitialsPresent(bool );
		void setGenerationQualifierPresent(bool );



	};

}

#endif

