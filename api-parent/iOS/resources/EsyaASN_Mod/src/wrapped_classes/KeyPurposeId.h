#ifndef __KEYPURPOSEID__
#define __KEYPURPOSEID__


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
	class Q_DECL_EXPORT KeyPurposeId  : public EASNWrapperTemplate<ASN1T_IMP_KeyPurposeId,ASN1C_IMP_KeyPurposeId>
	{
	protected:		
		ASN1TObjId mKeyPurposeId;

	public:
		KeyPurposeId();
		KeyPurposeId(const ASN1T_IMP_KeyPurposeId & iKPI);
		KeyPurposeId(const KeyPurposeId & iKPI);
		KeyPurposeId(const  QByteArray & iKPI);

		KeyPurposeId& operator=(const KeyPurposeId& iKPI );
		friend bool operator==(const KeyPurposeId & iRHS ,const KeyPurposeId & iLHS);
		friend bool operator!=(const KeyPurposeId & iRHS, const KeyPurposeId & iLHS);

		int copyFromASNObject(const ASN1T_IMP_KeyPurposeId & iKPI);
		int copyToASNObject(ASN1T_IMP_KeyPurposeId & oKPI) const;	
		void freeASNObject(ASN1T_IMP_KeyPurposeId & )const;

		int copyKPIs(const ASN1TPDUSeqOfList & iKPIs, QList<KeyPurposeId>& oList);
		int copyKPIs(const QList<KeyPurposeId> iList ,ASN1TPDUSeqOfList & oKPIs);	

		virtual ~KeyPurposeId(void);

		// GETTERS AND SETTERS

		static bool hasKeyPurpose(const QList<KeyPurposeId> iList , const ASN1TObjId & iKPI );

		const ASN1TObjId& getKeyPurposeId()const;
	};

}

#endif 

