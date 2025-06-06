package tr.gov.tubitak.uekae.esya.api.smartcard.android.ccid.reader;
import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals.State;

import com.scdroid.ccid.USBReader;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.hardware.usb.UsbManager;

import android.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tubitak.akis.cif.functions.ICommandTransmitter;
import tr.gov.tubitak.uekae.esya.api.common.ESYARuntimeException;
import tr.gov.tubitak.uekae.esya.api.common.lcns.LE;
import tr.gov.tubitak.uekae.esya.api.common.lcns.LV;
import tr.gov.tubitak.uekae.esya.api.smartcard.apdu.TerminalHandler;
;

public class GTITerminalHandler extends TerminalHandler
{
    protected static Logger logger = LoggerFactory.getLogger(GTITerminalHandler.class);
    private static String TAG="EsyaAndroidSignAPI-SCDTerminalHandler";    
    Activity activity;
    UsbManager usbManager;
    USBReader usbReader;

    
    PendingIntent permissionIntent;
    public GTITerminalHandler(Activity activity,PendingIntent permissionIntent){
        this.permissionIntent = permissionIntent;
        this.activity = activity;        
        usbManager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
        usbReader = new USBReader(permissionIntent,usbManager);
    }

    
    public GTITerminalHandler(Activity activity,UsbManager usbManager,USBReader usbReader)
    {
    	this.activity = activity;
        this.usbManager = usbManager;
        this.usbReader = usbReader;
    }


	@Override
    public List<CardTerminal> listCardTerminals(State aState)
            throws CardException
    {

        Log.e(TAG,"listCardTerminal GIRIS.");
		List<CardTerminal> list = new ArrayList<CardTerminal>();
		try {        		

        	//Map<String, File> externalLocations = ExternalStorage.getAllStorageLocations();
			//File sdCard = externalLocations.get(ExternalStorage.SD_CARD);
			//File externalSdCard = externalLocations.get(ExternalStorage.EXTERNAL_SD_CARD);
			//String extCardPath = externalSdCard.getAbsolutePath()+"/";
			
			String extCardPath = "/mnt/extSdCard/";
			
            Log.e(TAG,"listCardTerminals extPath: " +extCardPath );

	        
	        list.add(new GTICardTerminal(extCardPath));
	        
	        Log.e(TAG,"listCardTerminals GTICardTerminal added. path: "+ extCardPath );

	
		} catch (Exception e) {
		    logger.error("Error in GTITerminalHandler", e);
		}
        return list;
    }

    public static void checkLicense()
    {
        try
        {
            boolean isTest = LV.getInstance().isTL(LV.Urunler.ANDROIDIMZA);
            if(isTest)
            {
                Log.d(TAG,"Test lisans�, android imza i�lemlerinde 5 sn gecikme ya�anacak.");
                Thread.sleep(5000);
            }
        }
        catch(LE ex)
        {
            Log.e(TAG,"Lisans kontrolu basarisiz. ",ex);
            throw new ESYARuntimeException("Lisans kontrolu basarisiz. " + ex.getMessage());
        }
        catch (Exception e)
        {
            Log.e(TAG,"Lisans kontrolu sirasinda interrup alindi. ",e);
            throw new ESYARuntimeException("Lisans kontrolu sirasinda interrup alindi.");
        }

    }


    @Override
    public ICommandTransmitter getTransmitter(CardTerminal aCardTerminal)
            throws CardException
    {
        Log.d(TAG,"Before get transmitter");
        checkLicense();
        GTICardTerminal terminal = (GTICardTerminal) aCardTerminal;
        GTICommandTransmitter transmitter = new GTICommandTransmitter( terminal);
        transmitter.setPermissionIntent(permissionIntent);
        transmitter.initialize();
        Log.d(TAG,"After get transmitter");
        return transmitter;
    }
}
