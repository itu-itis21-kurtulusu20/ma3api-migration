#ifndef __CRLNUMBER__
#define __CRLNUMBER__

#include "EASNWrapperTemplate.h"
#include "Implicit.h"
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
	class Q_DECL_EXPORT CRLNumber  : public EASNWrapperTemplate<ASN1T_IMP_CRLNumber,ASN1C_IMP_CRLNumber> ,public AY_Eklenti
	{
		QString mValue;

	public:
		CRLNumber(void);
		CRLNumber(const QByteArray & );
		CRLNumber(const ASN1T_IMP_CRLNumber & );
		CRLNumber(const CRLNumber& );
		CRLNumber(const QString & );

		CRLNumber & operator=(const CRLNumber& iCRLNumber);
		Q_DECL_EXPORT friend bool operator==( const CRLNumber& iRHS, const CRLNumber& iLHS);
		Q_DECL_EXPORT friend bool operator!=( const CRLNumber& iRHS, const CRLNumber& iLHS);

		int copyFromASNObject(const ASN1T_IMP_CRLNumber & iCRLNumber);
		int copyToASNObject(ASN1T_IMP_CRLNumber &oCRLNumber)const;
		void freeASNObject(ASN1T_IMP_CRLNumber& )const;	

		~CRLNumber(void);

		const QString& getValue() const ;

		virtual QString toString()const;

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

