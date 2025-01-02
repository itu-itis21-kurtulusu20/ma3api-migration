#ifndef __E_LOKALDEN_DIL_BELIRLEYICI_H_
#define __E_LOKALDEN_DIL_BELIRLEYICI_H_
#include "EDilBelirleyici.h"
NAMESPACE_BEGIN(esya)
/**
 * \ingroup EsyaOrtak
 *
 * Yerel i�letim sistemi ayarlar�ndan program�n �al��ma dilini belirlemek i�in kullan�lan s�n�f
 * 
 * \date 03-17-2009
 *
 * \author ramazang
 * 
 *
 */
class ELokaldenDilBelirleyici :
	public EDilBelirleyici
{
	void _lokaldenDilBelirle();
public:
	ELokaldenDilBelirleyici(QApplication * ipApp);
	~ELokaldenDilBelirleyici(void);
	void calismaDiliBelirle();
};
NAMESPACE_END
#endif