#include "EDilBelirleyiciFactory.h"
#include "EAyarlardanDilBelirleyici.h"
#include "ELokaldenDilBelirleyici.h"
NAMESPACE_BEGIN(esya)

EDilBelirleyiciFactory::EDilBelirleyiciFactory(void)
{
}

EDilBelirleyiciFactory::~EDilBelirleyiciFactory(void)
{
}

EDilBelirleyici * EDilBelirleyiciFactory::dilBelirleyiciGetir(QApplication * ipApp)
{
	//<TODO> Burda neye g�re localden yada ayarlardan dilin belirlenebilece�ini se�ebiliriz
	return new EAyarlardanDilBelirleyici(ipApp);
}
NAMESPACE_END