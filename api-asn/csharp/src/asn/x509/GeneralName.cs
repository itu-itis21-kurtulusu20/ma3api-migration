// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.x509 {

   //using ORAddress = tr.gov.tubitak.uekae.esya.asn.x509.ORAddress;
   //using Name = tr.gov.tubitak.uekae.esya.asn.x509.Name;

   public class GeneralName : Asn1Choice {
      // Choice element identifier constants
      public const byte _OTHERNAME = 1;
      public const byte _RFC822NAME = 2;
      public const byte _DNSNAME = 3;
      public const byte _X400ADDRESS = 4;
      public const byte _DIRECTORYNAME = 5;
      public const byte _EDIPARTYNAME = 6;
      public const byte _UNIFORMRESOURCEIDENTIFIER = 7;
      public const byte _IPADDRESS = 8;
      public const byte _REGISTEREDID = 9;

      static GeneralName ()
      {
         Asn1Type.SetKey2 (_ImplicitValues._rtkey);
      }

      public GeneralName () : base()
      {
      }

      public GeneralName (byte choiceId_, Asn1Type element_) : base()
      {
         SetElement (choiceId_, element_);
      }

      public override string ElemName {
         get {
            switch (choiceID) {
            case _OTHERNAME: return "otherName";
            case _RFC822NAME: return "rfc822Name";
            case _DNSNAME: return "dNSName";
            case _X400ADDRESS: return "x400Address";
            case _DIRECTORYNAME: return "directoryName";
            case _EDIPARTYNAME: return "ediPartyName";
            case _UNIFORMRESOURCEIDENTIFIER: return "uniformResourceIdentifier";
            case _IPADDRESS: return "iPAddress";
            case _REGISTEREDID: return "registeredID";
            default: return "UNDEFINED";
            }
         }
      }

      public void Set_otherName (AnotherName value) {
         SetElement (_OTHERNAME, value);
      }

      public void Set_rfc822Name (Asn1IA5String value) {
         SetElement (_RFC822NAME, value);
      }

      public void Set_dNSName (Asn1IA5String value) {
         SetElement (_DNSNAME, value);
      }

      public void Set_x400Address (ORAddress value) {
         SetElement (_X400ADDRESS, value);
      }

      public void Set_directoryName (Name value) {
         SetElement (_DIRECTORYNAME, value);
      }

      public void Set_ediPartyName (EDIPartyName value) {
         SetElement (_EDIPARTYNAME, value);
      }

      public void Set_uniformResourceIdentifier (Asn1IA5String value) {
         SetElement (_UNIFORMRESOURCEIDENTIFIER, value);
      }

      public void Set_iPAddress (Asn1OctetString value) {
         SetElement (_IPADDRESS, value);
      }

      public void Set_registeredID (Asn1ObjectIdentifier value) {
         SetElement (_REGISTEREDID, value);
      }

      public override void Decode
         (Asn1BerDecodeBuffer buffer, bool explicitTagging, int implicitLength)
      {
         int llen = implicitLength;

         // decode CHOICE

         Asn1Tag tag = new Asn1Tag ();
         buffer.Mark ();
         int len = buffer.DecodeTagAndLength (tag);

         if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 0))
         {
            AnotherName otherName;
            otherName = new AnotherName();
            otherName.Decode (buffer, false, len);
            SetElement (_OTHERNAME, otherName);
         }
         else if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 1))
         {
            Asn1IA5String rfc822Name;
            rfc822Name = new Asn1IA5String();
            rfc822Name.Decode (buffer, false, len);
            SetElement (_RFC822NAME, rfc822Name);
         }
         else if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 2))
         {
            Asn1IA5String dNSName;
            dNSName = new Asn1IA5String();
            dNSName.Decode (buffer, false, len);
            SetElement (_DNSNAME, dNSName);
         }
         else if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 3))
         {
            ORAddress x400Address;
            x400Address = new ORAddress();
            x400Address.Decode (buffer, false, len);
            SetElement (_X400ADDRESS, x400Address);
         }
         else if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 4))
         {
            Name directoryName;
            directoryName = new Name();
            directoryName.Decode (buffer, false, len);
            SetElement (_DIRECTORYNAME, directoryName);
         }
         else if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.CONS, 5))
         {
            EDIPartyName ediPartyName;
            ediPartyName = new EDIPartyName();
            ediPartyName.Decode (buffer, false, len);
            SetElement (_EDIPARTYNAME, ediPartyName);
         }
         else if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 6))
         {
            Asn1IA5String uniformResourceIdentifier;
            uniformResourceIdentifier = new Asn1IA5String();
            uniformResourceIdentifier.Decode (buffer, false, len);
            SetElement (_UNIFORMRESOURCEIDENTIFIER, uniformResourceIdentifier);
         }
         else if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 7))
         {
            Asn1OctetString iPAddress;
            iPAddress = new Asn1OctetString();
            iPAddress.Decode (buffer, false, len);
            SetElement (_IPADDRESS, iPAddress);
         }
         else if (tag.Equals (Asn1Tag.CTXT, Asn1Tag.PRIM, 8))
         {
            Asn1ObjectIdentifier registeredID;
            registeredID = new Asn1ObjectIdentifier();
            registeredID.Decode (buffer, false, len);
            SetElement (_REGISTEREDID, registeredID);
         }
         else {
            throw new Asn1InvalidChoiceOptionException (buffer, tag);
         }
      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;
         switch (choiceID) {
         // encode otherName
         case _OTHERNAME:
            AnotherName otherName = (AnotherName) GetElement();
            len = otherName.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 0, len);
            _aal += len;
            break;

         // encode rfc822Name
         case _RFC822NAME:
            Asn1IA5String rfc822Name = (Asn1IA5String) GetElement();
            len = rfc822Name.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 1, len);
            _aal += len;
            break;

         // encode dNSName
         case _DNSNAME:
            Asn1IA5String dNSName = (Asn1IA5String) GetElement();
            len = dNSName.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 2, len);
            _aal += len;
            break;

         // encode x400Address
         case _X400ADDRESS:
            ORAddress x400Address = (ORAddress) GetElement();
            len = x400Address.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 3, len);
            _aal += len;
            break;

         // encode directoryName
         case _DIRECTORYNAME:
            Name directoryName = (Name) GetElement();
            len = directoryName.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 4, len);
            _aal += len;
            break;

         // encode ediPartyName
         case _EDIPARTYNAME:
            EDIPartyName ediPartyName = (EDIPartyName) GetElement();
            len = ediPartyName.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.CONS, 5, len);
            _aal += len;
            break;

         // encode uniformResourceIdentifier
         case _UNIFORMRESOURCEIDENTIFIER:
            Asn1IA5String uniformResourceIdentifier = (Asn1IA5String) GetElement();
            len = uniformResourceIdentifier.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 6, len);
            _aal += len;
            break;

         // encode iPAddress
         case _IPADDRESS:
            Asn1OctetString iPAddress = (Asn1OctetString) GetElement();
            len = iPAddress.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 7, len);
            _aal += len;
            break;

         // encode registeredID
         case _REGISTEREDID:
            Asn1ObjectIdentifier registeredID = (Asn1ObjectIdentifier) GetElement();
            len = registeredID.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.CTXT, Asn1Tag.PRIM, 8, len);
            _aal += len;
            break;

         default:
            throw new Asn1InvalidChoiceOptionException();
         }

         return _aal;
      }

      public override void Print (System.IO.TextWriter _out, 
                                  string _varName, int _level)
      {
         Indent (_out, _level);
         _out.WriteLine (_varName + " {");
         if (element != null) {
            element.Print (_out, ElemName, _level+1);
         }
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
