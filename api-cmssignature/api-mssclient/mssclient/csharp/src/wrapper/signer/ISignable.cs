﻿using System;

namespace tr.gov.tubitak.uekae.esya.api.webservice.mssclient.wrapper.signer
{
    /**
 * Basic signable interface used in web service request generation
 */

    public interface ISignable
    {
        String getValueToBeDisplayed();
        String getValueToBeSigned();
        String getEncoding();
        String getMimeType();
        SignatureType getSignatureType();

        String getHashURI();
        void setHashURI(String hashURI);
    }
}