package tr.gov.tubitak.uekae.esya.cmpapi.mobil;

import tr.gov.tubitak.uekae.esya.api.asn.x509.EName;
import tr.gov.tubitak.uekae.esya.cmpapi.base.BaseCertificationProtocol;
import tr.gov.tubitak.uekae.esya.cmpapi.base.common.CMPProtocolException;
import tr.gov.tubitak.uekae.esya.cmpapi.base.common.ICertificationAcceptanceStrategy;
import tr.gov.tubitak.uekae.esya.cmpapi.base.common.ICertificationParam;
import tr.gov.tubitak.uekae.esya.cmpapi.base.common.ProtocolType;
import tr.gov.tubitak.uekae.esya.cmpapi.base.protection.IProtectionTrustProvider;
import tr.gov.tubitak.uekae.esya.cmpapi.base.tcplayer.IConnection;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zeldal.ozdemir
 * Date: Dec 2, 2010
 * Time: 11:20:34 AM
 * To change this template use File | Settings | File Templates.
 */

public class KeyUpdateProtocol extends BaseCertificationProtocol {
    public KeyUpdateProtocol(IConnection connection,
                             EName sender, EName recipient,
                             List<ICertificationParam> certificationParams,
                             IProtectionTrustProvider protectionTrustProvider,
                             ICertificationAcceptanceStrategy acceptanceStrategy) throws CMPProtocolException {
        super(  connection,
                new MsgBuilder(ProtocolType.KEYUPDATEPROTOCOL, sender, recipient),
                certificationParams, protectionTrustProvider, acceptanceStrategy);
    }
}
