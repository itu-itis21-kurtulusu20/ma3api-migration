#ifndef __EKLENTIFABRIKASI__
#define __EKLENTIFABRIKASI__


#include "AY_Eklenti.h"
#include "Extension.h"

using namespace esya;

/**
* \ingroup EsyaASN
* 
* Extension s�n�f�ndan AY_Eklenti aray�z�n� destekleyen uygun ASN1 wrapper s�n�f�n� t�reten fabrika s�n�f�
*
*
* \author dindaro
*
*/
class Q_DECL_EXPORT EklentiFabrikasi
{
public:

	static AY_Eklenti * eklentiUret(const Extension &);

};

#endif

