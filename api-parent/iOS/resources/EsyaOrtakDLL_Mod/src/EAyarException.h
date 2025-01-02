
#ifndef __EAYAREXCEPTION_H__
#define __EAYAREXCEPTION_H__

#include "EException.h"
#include <QString>

NAMESPACE_BEGIN(esya)
#define YENIAYAREXCEPTION(neden,mesaj) EAyarException(neden,mesaj,__E_FILE__,__LINE__)

/**
 * \ingroup EsyaOrtakDLL
 *
 * Ayar eri�im ya ekleme i�lemleri s�ras�nda hata olu�mu�sa bunlar� iletmek i�in kullan�l�r. 
 *
 * \version 1.0
 * first version
 *
 * \date 05-15-2009
 *
 * \author ramazang
 * 
 * 
 * \todo 
 *
 * \bug 
 *
 */
class Q_DECL_EXPORT EAyarException:
	public EException
{
public:
	enum Sebep {AyarBulunamadi, 
		DegistirilemezAyar, 
		GenelYerelleUyumsuz, 
		VTAcilamadi,
		OzneTipiHatali,
		DizinTipiHatali,
		DizinYoluZatenVar,
		LdapAyariHatali,
		KullaniciHatali,
		OzneBulunamadi,
		OzneZatenVar,
		OzneKullanilmis,
		DizinBulunamadi,
		AcikDosyaBulunamadi
	}; 

	EAyarException(Sebep neden, const QString &iErrorDetail, const QString &iFileName = "", int iLineNumber = 0);
	~EAyarException();

	Sebep sebep();

private:
	Sebep mSebep;
};

NAMESPACE_END
#endif

