﻿using tr.gov.tubitak.uekae.esya.api.asn.x509;

namespace tr.gov.tubitak.uekae.esya.api.certificate.validation.match.deltacrl
{
    /**
     * Matches the complete CRL with the delta-CRL according to the following
     * condition;
     *
     * <p>The complete CRL and delta CRL have the same scope.
     * The two CRLs have the same scope if either of the following conditions are
     * met:
     * <ol>
     * <li>The issuingDistributionPoint extension is omitted from both the complete
     * CRL and the delta CRL.
     * <li>The issuingDistributionPoint extension is present in both
     * the complete CRL and the delta CRL, and the values for each of
     * the fields in the extensions are the same in both CRLs.
     * </ol>
     *
     * @author IH
     */
    public class ScopeMatcher : DeltaCRLMatcher
    {
        /**
        * Verilen Base SİL ile delta-SİL IssuingDistribtionPoint eklentisine göre eşleştirir
        */
        protected override bool _macthDeltaCRL(ECRL aCRL, ECRL aDeltaCRL)
        {
            EIssuingDistributionPoint crlIDP = aCRL.getCRLExtensions().getIssuingDistributionPoint();
            EIssuingDistributionPoint deltaCrlIDP = aDeltaCRL.getCRLExtensions().getIssuingDistributionPoint();

            if (crlIDP == null)
            {
                return deltaCrlIDP == null;
            }

            return crlIDP.Equals(deltaCrlIDP);//(2)
        }

    }
}
