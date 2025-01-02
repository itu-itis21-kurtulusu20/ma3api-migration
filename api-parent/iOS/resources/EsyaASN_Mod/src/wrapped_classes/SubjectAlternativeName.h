#ifndef __SUBJECTALTERNATIVENAME__
#define __SUBJECTALTERNATIVENAME__

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
	class Q_DECL_EXPORT SubjectAlternativeName  : public EASNWrapperTemplate<ASN1T_IMP_SubjectAltName,ASN1C_IMP_SubjectAltName> ,public AY_Eklenti
	{
		QList<GeneralName> mList;

	public:
		SubjectAlternativeName(void);
		SubjectAlternativeName(const ASN1T_IMP_SubjectAltName &);
		SubjectAlternativeName(const QByteArray &);
		SubjectAlternativeName(const SubjectAlternativeName&);
		SubjectAlternativeName(const QList<GeneralName>&);

		SubjectAlternativeName & operator=(const SubjectAlternativeName&);
		Q_DECL_EXPORT friend bool operator==(const SubjectAlternativeName & iRHS, const SubjectAlternativeName& iLHS);
		Q_DECL_EXPORT friend bool operator!=(const SubjectAlternativeName & iRHS, const SubjectAlternativeName& iLHS);

		int copyFromASNObject(const ASN1T_IMP_SubjectAltName &);
		int copyToASNObject(ASN1T_IMP_SubjectAltName& )const;
		void freeASNObject(ASN1T_IMP_SubjectAltName & )const;

		virtual ~SubjectAlternativeName(void);
		
		// GETTERS AND SETTERS

		const QList<GeneralName> &getList() const;

		void setList(const QList<GeneralName> &) ;
		void appendGN(const GeneralName &);

		virtual QString toString() const;
		QStringList toStringList() const;

		QString getEPosta() const;
		ORAddress getX400Address() const;

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

