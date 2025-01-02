
#ifndef __SUBJECTKEYIDENTIFIER__
#define __SUBJECTKEYIDENTIFIER__



#include "EException.h"
#include "ESeqOfList.h"
#include "ortak.h"
#include "GeneralName.h"
#include "AY_Eklenti.h"


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
	class Q_DECL_EXPORT SubjectKeyIdentifier  : public EASNWrapperTemplate<ASN1T_IMP_SubjectKeyIdentifier,ASN1C_IMP_SubjectKeyIdentifier> , public AY_Eklenti
	{
		QByteArray mKeyIdentifier;
		
	public:

		SubjectKeyIdentifier(const SubjectKeyIdentifier &);
		SubjectKeyIdentifier(const ASN1T_IMP_SubjectKeyIdentifier & );
		SubjectKeyIdentifier(const QByteArray & );
		SubjectKeyIdentifier(void);
		
		SubjectKeyIdentifier & operator=(const SubjectKeyIdentifier&);
		Q_DECL_EXPORT friend bool operator==(const SubjectKeyIdentifier& iRHS, const SubjectKeyIdentifier& iLHS);
		Q_DECL_EXPORT friend bool operator!=(const SubjectKeyIdentifier& iRHS, const SubjectKeyIdentifier& iLHS);

		int copyFromASNObject(const ASN1T_IMP_SubjectKeyIdentifier& iSKI);
		int copyToASNObject(ASN1T_IMP_SubjectKeyIdentifier & oSKI) const;
		void freeASNObject(ASN1T_IMP_SubjectKeyIdentifier & oSKI)const;

		virtual ~SubjectKeyIdentifier(void);
		
		// GETTERS AND SETTERS

		const QByteArray & getKeyIdentifier() const;

		/************************************************************************/
		/*					AY_EKLENTI FONKSIYONLARI                            */
		/************************************************************************/

		virtual QString eklentiAdiAl()			const ;
		virtual QString eklentiKisaDegerAl()	const ;
		virtual QString eklentiUzunDegerAl()	const ;

		virtual AY_Eklenti* kendiniKopyala() const ;
	};

}

#endif

