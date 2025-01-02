#ifndef __GENERALNAMES__
#define __GENERALNAMES__

#include "GeneralName.h"

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
	class Q_DECL_EXPORT GeneralNames  : public EASNWrapperTemplate<ASN1T_IMP_GeneralNames,ASN1C_IMP_GeneralNames>
	{
		QList<GeneralName> mList;

	public:
		GeneralNames(void);
		GeneralNames(const ASN1T_IMP_GeneralNames &);
		GeneralNames(const QByteArray &);
		GeneralNames(const QList<GeneralName>& iList);
		GeneralNames(const GeneralNames&);

		GeneralNames & operator=(const GeneralNames&);
		friend bool operator==(const GeneralNames & ,const GeneralNames & );
		friend bool operator!=(const GeneralNames & iRHS, const GeneralNames & iLHS);

		int copyFromASNObject(const ASN1T_IMP_GeneralNames&);
		int copyToASNObject(ASN1T_IMP_GeneralNames& )const;
		void freeASNObject(ASN1T_IMP_GeneralNames & )const;

		virtual ~GeneralNames(void);

		/////////////////////////////////////////////////////////////////

		const QList<GeneralName> &getList() const;
	
		void setList(const QList<GeneralName> & iList);
		void appendGeneralName( const GeneralName & iGN );

	};

}

#endif

