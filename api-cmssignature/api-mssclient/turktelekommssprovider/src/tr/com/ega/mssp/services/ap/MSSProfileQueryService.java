
package tr.com.ega.mssp.services.ap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "MSS_ProfileQueryService", targetNamespace = "http://ap.services.mssp.ega.com.tr", wsdlLocation = "https://mobilimza.turktelekom.com.tr/EGAMsspWSAP2/MSS_ProfileQueryService")
public class MSSProfileQueryService
    extends Service
{

    private final static URL MSSPROFILEQUERYSERVICE_WSDL_LOCATION;
    private static Logger logger = LoggerFactory.getLogger(MSSProfileQueryService.class);

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = tr.com.ega.mssp.services.ap.MSSProfileQueryService.class.getResource(".");
            url = new URL(baseUrl, "https://mobilimza.turktelekom.com.tr/EGAMsspWSAP2/MSS_ProfileQueryService");
        } catch (MalformedURLException e) {
            logger.warn("Failed to create URL for the wsdl Location: 'https://mobilimza.turktelekom.com.tr/EGAMsspWSAP2/MSS_ProfileQueryService', retrying as a local file", e);
        }
        MSSPROFILEQUERYSERVICE_WSDL_LOCATION = url;
    }

    public MSSProfileQueryService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MSSProfileQueryService() {
        super(MSSPROFILEQUERYSERVICE_WSDL_LOCATION, new QName("http://ap.services.mssp.ega.com.tr", "MSS_ProfileQueryService"));
    }

    /**
     * 
     * @return
     *     returns MSSProfileQueryPortType
     */
    @WebEndpoint(name = "MSS_ProfileQuery")
    public MSSProfileQueryPortType getMSSProfileQuery() {
        return super.getPort(new QName("http://ap.services.mssp.ega.com.tr", "MSS_ProfileQuery"), MSSProfileQueryPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MSSProfileQueryPortType
     */
    @WebEndpoint(name = "MSS_ProfileQuery")
    public MSSProfileQueryPortType getMSSProfileQuery(WebServiceFeature... features) {
        return super.getPort(new QName("http://ap.services.mssp.ega.com.tr", "MSS_ProfileQuery"), MSSProfileQueryPortType.class, features);
    }

}
