#ifndef __E_AYARLARDAN_DIL_BELIRLEYICI_H_
#define __E_AYARLARDAN_DIL_BELIRLEYICI_H_

#include "EDilBelirleyici.h"
NAMESPACE_BEGIN(esya)
/**
 * \ingroup EsyaOrtak
 *
 * Kermen �al��ma dilini Kermen ayarlar tablosundan belirlemek i�in kullan�lan s�n�f
 *
 * \date 03-17-2009
 *
 * \author ramazang
 * 
 *
 */
class EAyarlardanDilBelirleyici :
	public EDilBelirleyici
{	
	void _ayarlardanDilOku();
	void _ayarlardanDilBelirle();
public:
	EAyarlardanDilBelirleyici(QApplication * ipApp);
	~EAyarlardanDilBelirleyici(void);
	void calismaDiliBelirle();
};
NAMESPACE_END
#endif