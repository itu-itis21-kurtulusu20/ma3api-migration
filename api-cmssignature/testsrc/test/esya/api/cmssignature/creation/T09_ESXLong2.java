package test.esya.api.cmssignature.creation;

import bundle.esya.api.cmssignature.validation.ValidationUtil;
import org.junit.Test;
import test.esya.api.cmssignature.CMSSignatureTest;
import tr.gov.tubitak.uekae.esya.api.cmssignature.attribute.EParameters;
import tr.gov.tubitak.uekae.esya.api.cmssignature.signature.BaseSignedData;
import tr.gov.tubitak.uekae.esya.api.cmssignature.signature.ESignatureType;
import tr.gov.tubitak.uekae.esya.api.crypto.alg.SignatureAlg;

import java.util.HashMap;

public class T09_ESXLong2 extends CMSSignatureTest
{

	//create signeddata with one esxlong2 signature
	@Test
	public void testCreateESXLong2()
	throws Exception
	{
		BaseSignedData bs = new BaseSignedData();
		
		bs.addContent(getSimpleContent());
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		//necassary for getting signaturetimestamp and reference timestamp
		params.put(EParameters.P_TSS_INFO, getTSSettings());

		//necessary for validation of signer certificate according to time in signaturetimestamp attribute
		//while validation,references and values are also gathered
		params.put(EParameters.P_CERT_VALIDATION_POLICY,getPolicy());
		
		//add signer
		bs.addSigner(ESignatureType.TYPE_ESXLong_Type2, getSignerCertificate(), getSignerInterface(SignatureAlg.RSA_SHA1), null, params);

		ValidationUtil.checkSignatureIsValid(bs.getEncoded(), null);
	}
	
}
