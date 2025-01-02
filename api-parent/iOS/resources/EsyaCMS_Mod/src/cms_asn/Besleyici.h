#ifndef __BESLEYICI__
#define __BESLEYICI__

#include "BlokIsleyici.h"
#include "TAlg_Paralel_Adaptor.h"

namespace esya
{
	/**
	* \ingroup EsyaASN
	* 
	* Kripto i�leme nesnelerini birbirine ba�layabilece�imiz ve verileri blok blok paralel alarak 
	* besleyebilece�imiz bir ba�lant� s�n�f�. Blok i�leyici  aray�z�n� destekler.
	*
	* \author dindaro
	*
	*/
	class Besleyici : public BlokIsleyici
	{
		
		TAlg_Paralel_Adaptor * pBeslenecekler;
		
	public:
		Besleyici(TAlg_Paralel_Adaptor * ipBeslenecekler);
		
		virtual void blokIsle(const QByteArray& iBlok);

	public:
		~Besleyici(void);
	};
}

#endif

