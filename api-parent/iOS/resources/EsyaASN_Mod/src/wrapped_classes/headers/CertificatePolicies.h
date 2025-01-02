#ifndef __CERTIFICATEPOLICIES__
#define __CERTIFICATEPOLICIES__

#include "PolicyInformation.h"
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
	class Q_DECL_EXPORT CertificatePolicies  : public EASNWrapperTemplate<ASN1T_IMP_CertificatePolicies,ASN1C_IMP_CertificatePolicies> , public AY_Eklenti
	{
		QList<PolicyInformation> mList;

	public:
		CertificatePolicies(void);
		CertificatePolicies(const ASN1T_IMP_CertificatePolicies &);
		CertificatePolicies(const QByteArray &);
		CertificatePolicies(const CertificatePolicies&);
		CertificatePolicies(const QList<PolicyInformation>&);

		CertificatePolicies & operator=(const CertificatePolicies&);
		friend bool operator==(const CertificatePolicies & iRHS, const CertificatePolicies& iLHS);
		friend bool operator!=(const CertificatePolicies & iRHS, const CertificatePolicies& iLHS);

		int copyFromASNObject(const ASN1T_IMP_CertificatePolicies &);
		int copyToASNObject(ASN1T_IMP_CertificatePolicies& )const;
		void freeASNObject(ASN1T_IMP_CertificatePolicies & )const;

		virtual ~CertificatePolicies(void);

		// GETTERS AND SETTERS

		const QList<PolicyInformation> &getList() const;

		QByteArray getEncodedBytes()const ;

		virtual QString toString() const;
		QStringList toStringList() const ;

		int indexOf(const ASN1TObjId iPolicyId)const;

		void  appendPolicy(const ASN1TObjId iPolicyId);
		void  setList(const QList<PolicyInformation>& iList);

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

