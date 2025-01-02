#include "KontrolcuSonucu.h"
#include "aciklamalar.h"
#include "EsyaCMS_DIL.h"

using namespace esya;

/**
* \brief
* KontrolcuSonucu constructoru
*/
KontrolcuSonucu::KontrolcuSonucu(void)
{
}

/**
* \brief
* KontrolcuSonucu kopya constructoru
*
* \param 		const KontrolcuSonucu& iKS
* KontrolcuSonucu
*/
KontrolcuSonucu::KontrolcuSonucu(const KontrolcuSonucu& iKS)
:	mKontrolAdi(iKS.getKontrolAdi()),
	mAciklama(iKS.getAciklama()),
	mKontrolSonucu(iKS.getKontrolSonucu())
{

}
/**
* \brief
* KontrolcuSonucu destructoru
*
* \param 		const QString & iKontrolAdi
* Kontrol Ad�
*
* \param 		const QString & iAciklama
* A��klama
*
* \param 		const KS_Type & iKS
* Kontrol Sonucu
*/
KontrolcuSonucu::KontrolcuSonucu(	const QString & iKontrolAdi ,
									const QString & iAciklama,
									const KS_Type &iKS	)
:	mKontrolAdi(iKontrolAdi),
	mAciklama(iAciklama),
	mKontrolSonucu(iKS)
{

}


/**
* \brief
* Kontrol Ad� alan�n� d�ner
*
* \return const QString & 
* Kontrol Ad�
*/
const	QString &	KontrolcuSonucu::getKontrolAdi() const 
{
	return mKontrolAdi;
}

/**
* \brief
* Kontrol Ad� alan�n� d�ner
*
* \return QString & 
* Kontrol Ad�
*/
QString &	KontrolcuSonucu::getKontrolAdi() 
{
	return mKontrolAdi;
}

/**
* \brief
* Kontrol Ad� alan�n� belirler
*
* \param  const QString & iKA
* Kontrol Ad�
*/
void	KontrolcuSonucu::setKontrolAdi(const QString & iKA)
{
	mKontrolAdi = iKA;
}

/**
* \brief
* A��klama alan�n� d�ner
*
* \return const QString & 
* A��klama
*/
const	QString & KontrolcuSonucu::getAciklama() const 
{
	return mAciklama ;
}

/**
* \brief
* A��klama alan�n� d�ner
*
* \return QString & 
* A��klama
*/
QString & KontrolcuSonucu::getAciklama() 
{
	return mAciklama;
}

/**
* \brief
* A��klama alan�n� belirler
*
* \param  const QString & iAciklama
* A��klama
*
*/
void KontrolcuSonucu::setAciklama(const QString & iAciklama)
{
	mAciklama = iAciklama;
}

/**
* \brief
* Kontrol Sonucunu d�ner
*
* \return const KontrolcuSonucu::KS_Type & 
* Kontrol Sonucu
*
*/
const KontrolcuSonucu::KS_Type & KontrolcuSonucu::getKontrolSonucu() const
{
	return mKontrolSonucu;
}

/**
* \brief
* Kontrol Sonucunu d�ner
*
* \return KontrolcuSonucu::KS_Type & 
* Kontrol Sonucu
*
*/
KontrolcuSonucu::KS_Type & KontrolcuSonucu::getKontrolSonucu()
{
	return mKontrolSonucu;
}


/**
* \brief
* Kontrol Sonucunu belirler
*
* \param 		const KontrolcuSonucu::KS_Type & iKS
* Kontrol Sonucu
*
*/
void KontrolcuSonucu::setKontrolSonucu(const KontrolcuSonucu::KS_Type & iKS)
{
	mKontrolSonucu = iKS;
}


/**
* \brief
* Kontrol Sonucunu yaz�ya �evirir.
*
* \return   	QT_NAMESPACE::QString
*/
QString KontrolcuSonucu::getKontrolSonucuAsString() const
{
	switch (mKontrolSonucu)
	{
	case BASARILI: 
		{
			return A_BASARILI;
		}
	case BASARISIZ: 
		{
			return A_BASARISIZ;
		}
	case KONTROL_TAMAMLANAMADI: 
		{
			return A_KONTROL_TAMAMLANAMADI;
		}
	}
}


/**
* \brief
* Kontrol Detay�n� yaz�ya �evirir.
*
* \return   	QT_NAMESPACE::QString
*/
QString KontrolcuSonucu::toString() const
{
	QString st_sonuc;
	
	st_sonuc += QString("%1 [%2]\n").arg(LBL_KONTROLADI).arg(getKontrolAdi());
	st_sonuc += QString("%1 %2\n").arg(LBL_KONTROLSONUCU).arg(getKontrolSonucuAsString());
	st_sonuc += QString("%1 %2\n").arg(LBL_ACIKLAMA).arg(getAciklama());

	return st_sonuc;
}


/**
* \brief
* KontrolcuSonucu  destructoru
*/
KontrolcuSonucu::~KontrolcuSonucu(void)
{
}
