
#ifndef __BASICCONSTRAINTS__
#define __BASICCONSTRAINTS__



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
	class Q_DECL_EXPORT BasicConstraints : public EASNWrapperTemplate<ASN1T_IMP_BasicConstraints,ASN1C_IMP_BasicConstraints>, public AY_Eklenti
	{
	   bool		mPathLenConstraintPresent;
	   OSBOOL	mCA;
	   OSUINT32 mPathLenConstraint;

	public:

		BasicConstraints(const BasicConstraints &);
		BasicConstraints(const ASN1T_IMP_BasicConstraints & );
		BasicConstraints(const QByteArray & );
		BasicConstraints(const bool &, const OSBOOL &, const OSUINT32 &);
		BasicConstraints(void);
		

		BasicConstraints& operator=(const BasicConstraints&);
		friend bool operator==(const BasicConstraints& iRHS, const BasicConstraints& iLHS);
		friend bool operator!=(const BasicConstraints& iRHS, const BasicConstraints& iLHS);


		int copyFromASNObject(const ASN1T_IMP_BasicConstraints& iBC);
		int copyToASNObject(ASN1T_IMP_BasicConstraints & oBC) const;
		void freeASNObject(ASN1T_IMP_BasicConstraints & oBC)const;

		virtual ~BasicConstraints(void);

		// GETTERS AND SETTERS

		const bool	& getPathLenConstraintPresent()const;
		const OSBOOL & getCA()const;
		const OSUINT32 & getPathLenConstraint()const;

		QStringList toStringList() const;

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

