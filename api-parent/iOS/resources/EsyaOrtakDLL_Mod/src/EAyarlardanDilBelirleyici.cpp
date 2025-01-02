#include "EAyarlardanDilBelirleyici.h"
#include "EGenelAyarManager.h"
#include "EAyarTanimlari.h"
#include "EAyarAlici.h"
NAMESPACE_BEGIN(esya)

EAyarlardanDilBelirleyici::EAyarlardanDilBelirleyici(QApplication * ipApp)
:EDilBelirleyici(ipApp)
{
	E_DIL_BELIRLEYICI_ESYA_ORTAK_FUNC_BEGIN	
	E_DIL_BELIRLEYICI_ESYA_ORTAK_FUNC_END
}

EAyarlardanDilBelirleyici::~EAyarlardanDilBelirleyici(void)
{
	E_DIL_BELIRLEYICI_ESYA_ORTAK_FUNC_BEGIN
	E_DIL_BELIRLEYICI_ESYA_ORTAK_FUNC_END
}

/**
 * Ayarlardan sistemin �al��aca�� dil ad�n� al�r.
 */
void EAyarlardanDilBelirleyici::calismaDiliBelirle()
{
	E_DIL_BELIRLEYICI_ESYA_ORTAK_FUNC_BEGIN
	_ayarlardanDilBelirle();
	E_DIL_BELIRLEYICI_ESYA_ORTAK_FUNC_END
}

/**
 * Ayarlardan dil ad�n� okur. IslemOzellikleri s�n�f�n�n i�indeki ProgramDili de�erinden al�r.
 */
void EAyarlardanDilBelirleyici::_ayarlardanDilOku()
{
	E_DIL_BELIRLEYICI_ESYA_ORTAK_FUNC_BEGIN;	
	try
	{
		EAyarAlici ayarAlici(AYAR_SNF_ISLEMOZELLIKLERI,AYAR_ISLEMOZELLIKLERI_PROGRAM_DILI);
		EAyar dilAyar = ayarAlici.ayarBul(false);
		mDilSecenek = dilAyar.getStringDeger();
	}
	catch (EException &exc)
	{
		ESYA_ORTAK_FUNC_ERROR("Ayarlardan G�venli dizin yolu okunmaya �al���l�rken hata olu�tu"+exc.printStackTrace());		
	}	
	E_DIL_BELIRLEYICI_ESYA_ORTAK_FUNC_END	
}

void EAyarlardanDilBelirleyici::_ayarlardanDilBelirle()
{
	E_DIL_BELIRLEYICI_ESYA_ORTAK_FUNC_BEGIN	
	_ayarlardanDilOku();
	E_DIL_BELIRLEYICI_ESYA_ORTAK_FUNC_END
}
NAMESPACE_END