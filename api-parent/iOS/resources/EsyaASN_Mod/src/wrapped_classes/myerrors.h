
#ifndef __MYERRORS__
#define __MYERRORS__

#define FAILURE -1  // Generic Failure
#define SUCCESS  0	// Generic Success

#define ERROR_FILE_OPEN 1001




#define CMS_INTERNAL_ERROR				"Bir hata olu�tu."
#define CMS_FILE_OPEN_ERROR				"Dosya a��lamad�."
#define CMS_NOT_A_SIGNED_CONTENT		"Bu bir imzal� dosya de�il."
#define CMS_NOT_A_ENVELOPED_CONTENT		"Bu bir imzal� dosya de�il."
#define CMS_NOT_A_SIGNED_DATA			"ContentInfo'nun tipi signedData de�il"
#define CMS_NOT_A_DATA					"ge�erli b,r Data tipi de�il"
#define CMS_VERIFICATION_FAILED			"�mzal� dosya imzas� do�rulanamad�!"
#define CMS_SIGNER_VERIFICATION_FAILED	"�mzac� do�rulanamad�!"
#define CMS_MESSAGEDIGEST_NOT_FOUND     "messageDigest �zniteli�i bulunamad�"
#define CMS_SIGNEDATTRIBUTES_NOT_FOUND  "signedAttributes bulunamad�"
#define CMS_MESSAGEDIGEST_NOT_SINGLE	"messageDigest �zniteli�inin say�s� 1 de�il"
#define CMS_CERT_SUBJECT_NOT_FOUND	    "sertifikada  subject alan� bulunamad�"
#define CMS_SIGNINGTIME_NOT_FOUND	    "signingTime �zniteli�i bulunamad�"
#define CMS_WRONG_SID_TYPE				"SID tipi yanl�� atanm��"
#define CMS_ENCRYPTEDCONTENT_NOT_FOUND	"encryptedContent bulunamad�"
#define CMS_STREAM_DATA					"Bu bir streamed veri"
#define CMS_NON_STREAM_DATA				"Bu bir non streamed veri"
#define CMS_PROTOKOL_ALREADY_DEFINED	"Daha �nce bir protokol belirlenmi�"



#define CMS_NO_DIGEST_ALG_FOUND			"Hi� bir Digest Algoritmas� bulunamad� "
#define CMS_NO_ENCAPCONTENTINFO_FOUND	"Hi� bir EncapContentInfo bulunamad� "
#define CMS_NO_SIGNERINFO_ADDED			"Hi� signerInfo eklenmemi� "
#define CMS_NO_CERTIFICATE_ADDED		"Hi� sertifika eklenmemi� "
#define CMS_NO_EXTENSION_FOUND			"Hi� eklenti bulunamad� "
#define CMS_NO_PARALLEL_SIGN_FOUND		"Hi� paralel imza bulunamad� "
#define CMS_NO_ORIGINATOR_INFO_ADDED    "Hi� originator info bulunamad�."
#define CMS_NO_UNPROTECTEDATTRIBUTES_ADDED    "Hi� unprotectedAttribute bulunamad�."
#define CMS_NO_RECIPIENTINFO_ADDED		"Hi� recipientInfo bulunamad�."

#define CMS_SIGNERTREES_DIFFERENT			"BesSignerTree ile BasicSignerTree ayn� de�il"
#define CMS_PREDECESSOR_NOT_FOUND			"Verilen imzac�n�n atas� bulunamad�"
#define CMS_SIGNATUREDIGESTS_DIFFERENT		"�mzan�n  �zeti ile imza-�zeti �zniteli�i uyu�muyor"
#define CMS_SIGNER_CERTIFICATE_NOT_FOUND	"�mzac�n�n sertifikas� bulunamad�"


#define CMS_ASN_BUFFER_INITIALIZATION_ERROR "Buffer ilklendirme s�ras�nda hata olu�tu"
#define CMS_ASN_BUFFER_NOT_INITIALIZED		"Buffer do�ru bir �ekilde ilklendirilmemi�"




#define CMS_ASND_CONTENTINFO_TAGLEN			"contentInfo Tag ve Len okunamad�"
#define CMS_ASND_SIGNEDDATA_OBJID			"signed_data object identifier okunamad�"
#define CMS_ASND_CONTENT_TAGLEN				"content Tag ve Len okunamad�"
#define CMS_ASND_SIGNEDDATA_TAGLEN			"signed_data Tag ve Len okunamad�"
#define CMS_ASND_SIGNEDDATA_VERSION			"signed_data->version okunamad�"
#define CMS_ASND_ENCAPCONTENTINFO_TAGLEN	"encapcontentinfo Tag ve Len okunamad�.."
#define CMS_ASND_ENCRYPTCONTENTINFO_TAGLEN	"enccryptedcontentinfo Tag ve Len okunamad�.."
#define CMS_ASND_IDDATA_OBJID				"id_data object identifier okunamad�"
#define CMS_ASND_DATA_PARSE_ERROR			"Veri parse edilemedi"
#define CMS_ASND_CERTIFICATESET				"certificateSet okunamad�"
#define	CMS_ASND_CERTIFICATESET_1			"certificateSet okunamad�. Hata : %1"
#define CMS_ASND_SIGNERINFO					"signerInfo okunamad�"
#define CMS_ASND_SIGNERINFOS				"signerInfos okunamad�"
#define CMS_ASND_DIGESTALGORITHMS			"DigestAlgorithms okunamad�"
#define CMS_ASND_DIGESTALGORITHMS_1			"DigestAlgorithms okunamad�. Hata : %1"
#define CMS_ASND_CERTIFICATES_TAGLEN		"certficates Tag ve Len okunamad�"
#define CMS_ASND_SIGNEDDATA_1				"Veri SignedData olarak okunamad�. Hata : %1"
#define CMS_ASND_CONTENTINFO_1				"contentInfo okunamad�. Hata : %1"
#define CMS_ASND_CERTIFICATE_1				"certificate okunamad�. Hata : %1"
#define CMS_ASND_SUBJECTKEYIDENTIFIER_1		"SubjectKeyIdentifier okunamad�. Hata : %1"
#define CMS_ASND_CONTENTTYPE				"contentType �zniteli�i okunamad�."
#define CMS_ASND_MESSAGEDIGEST_1			"messageDigest okunamad�. Hata : %1"
#define CMS_ASND_SIGNINGCERTIFICATE_1		"signingCertificate okunamad�. Hata : %1"
#define CMS_ASND_SIGNINGTIME_1				"time attribute okunamad�. Hata : %1"
#define CMS_ASND_COUNTERSIGNATURE_1			"counterSignature attribute okunamad�. Hata : %1"
// ENVELOPEDDATA
#define CMS_ASND_ENVELOPEDDATA_1				"envelopedData okunamad�. Hata : %1"
#define CMS_ASND_ORIGINATORINFO_1				"originatorInfo okunamad�. Hata : %1"
#define CMS_ASND_ORIGINATORINFO_TAGLEN			"originatorInfo Tag ve Len okunamad�. Hata : %1"
#define CMS_ASND_ENCRYPTEDCONTENTINFO_1			"encryptedContentInfo okunamad�. Hata : %1"
#define CMS_ASND_UNPROTECTEDATTRIBUTES_1		"unprotectedAttributes okunamad�. Hata : %1"
#define CMS_ASND_CONTENTENCRYPTIONALGORITHM_1	"contentEncryptionAlgorithm okunamad�. Hata : %1"
#define CMS_ASND_ENVELOPEDDATA_OBJID			"enveloped_data object identifier okunamad�"
#define CMS_ASND_ENVELOPEDDATA_TAGLEN			"enveloped_data Tag ve Len okunamad�"
#define CMS_ASND_ENVELOPEDDATA_VERSION			"enveloped_data->version okunamad�"
#define CMS_ASND_RECIPIENTINFO_1				"recipientInfo okunamad�. Hata : %1"
#define CMS_ASND_KEYENCRYPTIONALGORITHM_1		"keyEncryptionAlgorithm okunamad�. Hata : %1"





#define CMS_ASNE_DIGESTALGORITHMS			"digestAlgorithms encode edilemedi"
#define CMS_ASNE_CERTIFICATE				"certificate encode edilemedi"
#define CMS_ASNE_SIGNEDDATA					"signedData encode edilemedi"
#define CMS_ASNE_CONTENTINFO				"contentInfo encode edilemedi"
#define CMS_ASNE_SIGNEDDATA_OBJID			"signedData object identifier encode edilemedi"
#define CMS_ASNE_CERTIFICATESET				"certificateSet encode edilemedi"
#define CMS_ASNE_SIGNERINFO					"signerInfos encode edilemedi"
#define CMS_ASNE_SIGNERINFOS				"signerInfos encode edilemedi"
#define CMS_ASNE_SIGNEDDATA_VERSION			"signed_data->version encode edilemedi"
#define CMS_ASNE_SIGNEDATTRIBUTES			"signedAttributes encode edilemedi"
#define CMS_ASNE_ISSUERSID					"IssuerSID encode edilemedi"
#define CMS_ASNE_ISSUERCERT					"Issuer certificate encode edilemedi"
#define CMS_ASNE_SUBJECTKEYIDENTIFIER		"SubjectKeyIdentifier encode edilemedi."
#define CMS_ASNE_CONTENTTYPE				"contentType encode edilemedi."
#define CMS_ASNE_MESSAGEDIGEST				"messageDigest encode edilemedi."
#define CMS_ASNE_SIGNINGCERTIFICATE			"signingCertificate encode edilemedi."
#define CMS_ASNE_SIGNINGTIME				"time attribute encode edilemedi."
// ENVELOPEDDATA
#define CMS_ASNE_ORIGINATORINFO				"sriginatorInfo attribute encode edilemedi."
#define CMS_ASNE_ENCRYPTEDCONTENTINFO		"encryptedContentInfo encode edilemedi"
#define CMS_ASNE_UNPROTECTEDATTRIBUTES		"unprotectedAttributes encode edilemedi"
#define CMS_ASNE_CONTENTENCRYPTIONALGORITHM	"contentEncryptionAlgorithm encode edilemedi"
#define CMS_ASNE_ENVELOPEDDATA				"envelopedData encode edilemedi"
#define CMS_ASNE_ENVELOPEDDATA_OBJID		"envelopedData object identifier encode edilemedi"
#define CMS_ASNE_ENVELOPEDDATA_VERSION		"envelopedData version encode edilemedi"
#define CMS_ASNE_RECIPIENTINFO				"recipientInfo encode edilemedi"
#define CMS_ASNE_IDDATA_OBJID				"id_data object identifier encode edilemedi"
#define CMS_ASNE_KEYENCRYPTIONALGORITHM		"keyEncryptionAlgorithm encode edilemedi"


#endif

