#ifndef __SIGNEDATTRIBUTESKONTROLCU__
#define __SIGNEDATTRIBUTESKONTROLCU__

#include "ImzaKontrolcu.h"


namespace esya
{
	class ImzaDogrulamaAlgoritmasi;

	/**
	*	\ingroup EsyaCMS
	*
	*	Imzal� yap� �zerindeki imzal� �zelliklerin do�rulu�unu kontrol eder.
	*
	*/
	class SignedAttributesKontrolcu : public ImzaKontrolcu
	{
	public:
		static const QString ST_KONTROLADI;

		SignedAttributesKontrolcu( ImzaDogrulamaAlgoritmasi * ipDA = NULL);
		SignedAttributesKontrolcu( ImzaDogrulamaAlgoritmasi* ipDA , const ParametreListesi &);

		virtual bool kontrolYap(const SignerInfo & iSI, KontrolcuSonucu & oKS) ;

	public:
		~SignedAttributesKontrolcu(void);
	};

}

#endif