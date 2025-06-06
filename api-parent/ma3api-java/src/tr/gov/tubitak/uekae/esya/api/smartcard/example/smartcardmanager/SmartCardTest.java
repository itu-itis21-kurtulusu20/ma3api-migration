package tr.gov.tubitak.uekae.esya.api.smartcard.example.smartcardmanager;

import org.junit.Test;
import sun.security.pkcs11.wrapper.CK_ATTRIBUTE;
import sun.security.pkcs11.wrapper.CK_SLOT_INFO;
import sun.security.pkcs11.wrapper.PKCS11Constants;
import sun.security.pkcs11.wrapper.PKCS11Exception;
import tr.gov.tubitak.uekae.esya.api.SampleBase;
import tr.gov.tubitak.uekae.esya.api.asn.x509.ECertificate;
import tr.gov.tubitak.uekae.esya.api.common.util.StringUtil;
import tr.gov.tubitak.uekae.esya.api.common.util.bag.Pair;
import tr.gov.tubitak.uekae.esya.api.crypto.alg.SignatureAlg;
import tr.gov.tubitak.uekae.esya.api.smartcard.pkcs11.CardType;
import tr.gov.tubitak.uekae.esya.api.smartcard.pkcs11.SmartCard;
import tr.gov.tubitak.uekae.esya.api.smartcard.pkcs11.SmartCardException;
import tr.gov.tubitak.uekae.esya.api.smartcard.pkcs11.SmartOp;
import tr.gov.tubitak.uekae.esya.api.smartcard.pkcs11.cardobject.P11Object;
import tr.gov.tubitak.uekae.esya.api.smartcard.util.SCSignerWithCertSerialNo;

import java.io.IOException;
import java.util.List;

/**
 * Several smart card operations are shown.
 */
public class SmartCardTest extends SampleBase {

    Long akisSlot = null;
    Long dirakSlot = null;

    @Test
    public void testGetObjectsAkis() throws Exception {
        getAndPrintObjects(CardType.AKIS, akisSlot);
    }

    @Test
    public void testGetObjectsDirak() throws Exception {
        getAndPrintObjects(CardType.DIRAKHSM, dirakSlot);
    }

    public void getAndPrintObjects(CardType cardType, Long slot) throws Exception {
        SmartCard sc = new SmartCard(cardType);
        long session = sc.openSession(slot != null ? slot : sc.getSlotList()[0]);

        P11Object[] p11Objects = sc.getAllObjectInfos(session);

        for (P11Object p11Object : p11Objects) {
            System.out.println(p11Object.getLabel() + " " + p11Object.getType());
        }
    }

    /**
     * Certificates in smart card are read and the common names of certificates are printed to the standard output.
     *
     * @throws Exception
     */
    @Test
    public void testListCertInSmartCard() throws Exception {

        SmartCard smartCard = new SmartCard(CardType.AKIS);
        long[] slots = smartCard.getSlotList();
        long session = smartCard.openSession(slots[0]);
        smartCard.login(session, getPin());
        List<byte[]> certBytes = smartCard.getSignatureCertificates(session);
        for (byte[] bs : certBytes) {
            ECertificate cert = new ECertificate(bs);
            System.out.println(cert.getSubject().getCommonNameAttribute());
        }
        smartCard.logout(session);
        smartCard.closeSession(session);
    }

    /**
     * Key labels of signature keys are printed to standard output.
     *
     * @throws PKCS11Exception
     * @throws IOException
     * @throws SmartCardException
     */
    @Test
    public void testListKeyLabels() throws Exception {

        SmartCard smartCard = new SmartCard(CardType.AKIS);
        long slot = smartCard.getSlotList()[0];
        long session = smartCard.openSession(slot);
        smartCard.login(session, getPin());
        String[] labels = smartCard.getSignatureKeyLabels(session);
        for (String label : labels) {
            System.out.println(label);
        }
        smartCard.logout(session);
    }

    /**
     * Get card type and slot number of the connected smart cards and prints them.
     *
     * @throws Exception
     */
    @Test
    public void testprintSmartCardsJava6_1() throws Exception {

        List<Pair<Long, CardType>> terminals = SmartOp.findCardTypesAndSlots();
        for (Pair<Long, CardType> objects : terminals) {
            Long slot1 = objects.getObject1();
            CardType cardType = objects.getObject2();
            System.out.println(slot1 + ":" + cardType);
        }
    }

    /**
     * The name of card readers, the slot of the card and the type of the card are printed.
     *
     * @throws Exception
     */
    @Test
    public void testselectSmartCardJava6_2() throws Exception {

        //terminal names are taken
        String[] terminals = SmartOp.getCardTerminals();
        for (String terminal : terminals) {
            //card type and slot number is taken of the terminal
            Pair<Long, CardType> slotAndCardType = SmartOp.getSlotAndCardType(terminal);
            System.out.println("Terminal: " + terminal + " Slot: " + slotAndCardType.getObject1()
                    + " CardType: " + slotAndCardType.getObject2());
        }
    }

    /**
     * If there are more than one connected smart cards to the system, it wants user to select the card.
     * A GUI appears.
     *
     * @throws Exception
     */
    @Test
    public void testSelectSmartCardJava6_3() throws Exception {

        Pair<Long, CardType> card = SmartOp.findCardTypeAndSlot();
        Long slot = card.getObject1();
        CardType cardType = card.getObject2();
        System.out.println("Slot: " + slot + "Card Type: " + cardType);

        SmartCard smartCard = new SmartCard(cardType);
        long session = smartCard.openSession(slot);
        smartCard.login(session, getPin());
        ECertificate cert = new ECertificate(smartCard.getSignatureCertificates(session).get(0));

        SCSignerWithCertSerialNo signer = new SCSignerWithCertSerialNo(smartCard, session, slot, cert.getSerialNumber().toByteArray(),
                SignatureAlg.RSA_SHA1.getName());

        smartCard.logout(session);
    }

    /**
     * Akis card selection can list the all card terminals. But the card type can not be detected.
     * For this purpose use Java 6
     *
     * @throws PKCS11Exception
     * @throws IOException
     */
    @Test
    public void testGetCardReadersJava5() throws PKCS11Exception, IOException {

        SmartCard smartCard = new SmartCard(CardType.AKIS);
        long[] slots = smartCard.getSlotList();
        for (long slot : slots) {
            CK_SLOT_INFO slotInfo = smartCard.getSlotInfo(slot);
            System.out.println(new String(slotInfo.slotDescription).trim());
        }
    }

    @Test
    public void getCertsSerialNumbersFromCardTest() throws PKCS11Exception, IOException {
        SmartCard sc = new SmartCard(CardType.AKIS);
        long slot = sc.getSlotList()[0];
        long session = sc.openSession(slot);
        sc.login(session, "12345");

        CK_ATTRIBUTE[] certFinderAttribute = new CK_ATTRIBUTE[1];
        certFinderAttribute[0] = new CK_ATTRIBUTE(PKCS11Constants.CKA_CLASS, PKCS11Constants.CKO_CERTIFICATE);

        long[] ids = sc.findObjects(session, certFinderAttribute);
        System.out.println("Found Certificate Count: " + ids.length);

        CK_ATTRIBUTE[] serialAttr = new CK_ATTRIBUTE[1];
        serialAttr[0] = new CK_ATTRIBUTE(PKCS11Constants.CKA_SERIAL_NUMBER);

        for (int i = 0; i < ids.length; i++) {
            sc.getAttributeValue(session, ids[i], serialAttr);
            System.out.println(i + " : " + StringUtil.toHexString((byte[])serialAttr[0].pValue));
        }

        sc.logout(session);
        sc.closeSession(session);
    }
}
