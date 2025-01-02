#ifndef __ANOTHERNAME__
#define __ANOTHERNAME__


#include "Implicit.h"
#include "EASNWrapperTemplate.h"


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
	class Q_DECL_EXPORT AnotherName  : public EASNWrapperTemplate<ASN1T_IMP_AnotherName,ASN1C_IMP_AnotherName>
	{
		ASN1TObjId		mTypeID;
		QByteArray		mValue;

	public:
		AnotherName();
		AnotherName(const ASN1T_IMP_AnotherName & );
		AnotherName(const AnotherName & );
		AnotherName(const  QByteArray &);
		AnotherName(const ASN1TObjId& , const  QByteArray &);

		AnotherName& operator=(const AnotherName& );
		friend bool operator==(const AnotherName & ,const AnotherName & );
		friend bool operator!=(const AnotherName & iRHS, const AnotherName & iLHS);

		int copyFromASNObject(const ASN1T_IMP_AnotherName & iAN);
		int copyToASNObject(ASN1T_IMP_AnotherName & ) const;	
		void freeASNObject(ASN1T_IMP_AnotherName & )const;

		virtual ~AnotherName(void);

		// GETTERS AND SETTERS

		const ASN1TObjId& getTypeID()const;
		const QByteArray& getValue()const;

	public:

	};

}

#endif 

