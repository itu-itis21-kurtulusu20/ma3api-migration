#include "DBSession.h"
using namespace esya;
DBSession::DBSession(EVeritabani * ipDb)
:mpDB(ipDb)
{
}

DBSession::~DBSession(void)
{
}

bool DBSession::open()
{
	bool retValue = false;	
	if(!mpDB->isOpen())
	{
		retValue = mpDB->open();		
		if (!retValue)
		{
			throw EException("Veritaban� a�ma hatas� .HATA="+mpDB->getLastError());
		}
	}
	return retValue;
}

bool DBSession::close()
{	
	bool retValue = false;
	mpDB->dbKapat();
	if(mpDB->isOpen())
	{
		retValue = false;
	}	
	return retValue;
}