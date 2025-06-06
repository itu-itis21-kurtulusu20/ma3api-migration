// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.cvc {

   public class CertContentTemplate : Asn1Type {
      public new static readonly Asn1Tag _TAG = new Asn1Tag (Asn1Tag.APPL, Asn1Tag.CONS, 78);

      public Asn1OctetString cpi;
      public Asn1OctetString car;
      public ElcPuK puk;
      public Asn1OctetString chr;
      public Chat chat;
      public Asn1OctetString ced;  // optional
      public Asn1OctetString cxd;  // optional

      static CertContentTemplate ()
      {
         Asn1Type.SetKey2 (_cvcValues._rtkey);
      }

      public CertContentTemplate () : base()
      {
         Init();
      }

      /// <summary>
      /// This constructor sets all elements to references to the 
      /// given objects
      /// </summary>
      public CertContentTemplate (
         Asn1OctetString cpi_,
         Asn1OctetString car_,
         ElcPuK puk_,
         Asn1OctetString chr_,
         Chat chat_,
         Asn1OctetString ced_,
         Asn1OctetString cxd_
      )
         : base ()
      {
         cpi = cpi_;
         car = car_;
         puk = puk_;
         chr = chr_;
         chat = chat_;
         ced = ced_;
         cxd = cxd_;
      }

      /// <summary>
      /// This constructor is for required elements only.  It sets 
      /// all elements to references to the given objects
      /// </summary>
      public CertContentTemplate (
         Asn1OctetString cpi_,
         Asn1OctetString car_,
         ElcPuK puk_,
         Asn1OctetString chr_,
         Chat chat_
      )
         : base ()
      {
         cpi = cpi_;
         car = car_;
         puk = puk_;
         chr = chr_;
         chat = chat_;
      }

      /// <summary>
      /// This constructor allows primitive data to be passed for all 
      /// primitive elements.  It will create new object wrappers for 
      /// the primitive data and set other elements to references to 
      /// the given objects 
      /// </summary>
      public CertContentTemplate (byte[] cpi_,
         byte[] car_,
         ElcPuK puk_,
         byte[] chr_,
         Chat chat_,
         byte[] ced_,
         byte[] cxd_
      )
         : base ()
      {
         cpi = new Asn1OctetString (cpi_);
         car = new Asn1OctetString (car_);
         puk = puk_;
         chr = new Asn1OctetString (chr_);
         chat = chat_;
         ced = new Asn1OctetString (ced_);
         cxd = new Asn1OctetString (cxd_);
      }

      /// <summary>
      /// This constructor is for required elements only.  It allows 
      /// primitive data to be passed for all primitive elements.  
      /// It will create new object wrappers for the primitive data 
      /// and set other elements to references to the given objects. 
      /// </summary>
      public CertContentTemplate (
         byte[] cpi_,
         byte[] car_,
         ElcPuK puk_,
         byte[] chr_,
         Chat chat_
      )
         : base ()
      {
         cpi = new Asn1OctetString (cpi_);
         car = new Asn1OctetString (car_);
         puk = puk_;
         chr = new Asn1OctetString (chr_);
         chat = chat_;
      }

      public void Init () {
         cpi = null;
         car = null;
         puk = null;
         chr = null;
         chat = null;
         ced = null;
         cxd = null;
      }

      public override void Decode
         (Asn1BerDecodeBuffer buffer, bool explicitTagging, int implicitLength)
      {
         int llen = (explicitTagging) ?
            MatchTag (buffer, _TAG) : implicitLength;

         Init ();

         // decode SEQUENCE

         Asn1BerDecodeContext _context =
            new Asn1BerDecodeContext (buffer, llen);

         IntHolder elemLen = new IntHolder();

         // decode cpi

         if (_context.MatchElemTag (Asn1Tag.APPL, Asn1Tag.PRIM, 41, elemLen, true)) {
            int offset = buffer.ByteCount;
            cpi = new Asn1OctetString();
            cpi.Decode (buffer, false, elemLen.mValue);
            if (!(cpi.Length == 1)) {
               throw new Asn1ConsVioException ("cpi.Length", cpi.Length);
            }

            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode car

         if (_context.MatchElemTag (Asn1Tag.APPL, Asn1Tag.PRIM, 2, elemLen, true)) {
            int offset = buffer.ByteCount;
            car = new Asn1OctetString();
            car.Decode (buffer, false, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode puk

         if (_context.MatchElemTag (Asn1Tag.APPL, Asn1Tag.CONS, 73, elemLen, false)) {
            puk = new ElcPuK();
            puk.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode chr

         if (_context.MatchElemTag (Asn1Tag.APPL, Asn1Tag.PRIM, 32, elemLen, true)) {
            int offset = buffer.ByteCount;
            chr = new Asn1OctetString();
            chr.Decode (buffer, false, elemLen.mValue);
            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode chat

         if (_context.MatchElemTag (Asn1Tag.APPL, Asn1Tag.CONS, 76, elemLen, false)) {
            chat = new Chat();
            chat.Decode (buffer, true, elemLen.mValue);
         }
         else throw new Asn1MissingRequiredException (buffer);

         // decode ced

         if (_context.MatchElemTag (Asn1Tag.APPL, Asn1Tag.PRIM, 37, elemLen, true)) {
            int offset = buffer.ByteCount;
            ced = new Asn1OctetString();
            ced.Decode (buffer, false, elemLen.mValue);
            if (!(ced.Length == 6)) {
               throw new Asn1ConsVioException ("ced.Length", ced.Length);
            }

            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }

         // decode cxd

         if (_context.MatchElemTag (Asn1Tag.APPL, Asn1Tag.PRIM, 36, elemLen, true)) {
            int offset = buffer.ByteCount;
            cxd = new Asn1OctetString();
            cxd.Decode (buffer, false, elemLen.mValue);
            if (!(cxd.Length == 6)) {
               throw new Asn1ConsVioException ("cxd.Length", cxd.Length);
            }

            int declen = buffer.ByteCount - offset;
            if (declen != elemLen.mValue && elemLen.mValue != Asn1Status.INDEFLEN)
               throw new Asn1InvalidLengthException();
         }

         if (explicitTagging && llen == Asn1Status.INDEFLEN) {
            MatchTag (buffer, Asn1Tag.EOC);
         }
      }

      public override int Encode (Asn1BerEncodeBuffer buffer, bool explicitTagging)
      {
         int _aal = 0, len;

         // encode cxd

         if (cxd != null) {
            if (!(cxd.Length == 6)) {
               throw new Asn1ConsVioException ("cxd.Length", cxd.Length);
            }

            len = cxd.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.APPL, Asn1Tag.PRIM, 36, len);
            _aal += len;
         }

         // encode ced

         if (ced != null) {
            if (!(ced.Length == 6)) {
               throw new Asn1ConsVioException ("ced.Length", ced.Length);
            }

            len = ced.Encode (buffer, false);
            len += buffer.EncodeTagAndLength (Asn1Tag.APPL, Asn1Tag.PRIM, 37, len);
            _aal += len;
         }

         // encode chat

         len = chat.Encode (buffer, true);
         _aal += len;

         // encode chr

         len = chr.Encode (buffer, false);
         len += buffer.EncodeTagAndLength (Asn1Tag.APPL, Asn1Tag.PRIM, 32, len);
         _aal += len;

         // encode puk

         len = puk.Encode (buffer, true);
         _aal += len;

         // encode car

         len = car.Encode (buffer, false);
         len += buffer.EncodeTagAndLength (Asn1Tag.APPL, Asn1Tag.PRIM, 2, len);
         _aal += len;

         // encode cpi

         if (!(cpi.Length == 1)) {
            throw new Asn1ConsVioException ("cpi.Length", cpi.Length);
         }

         len = cpi.Encode (buffer, false);
         len += buffer.EncodeTagAndLength (Asn1Tag.APPL, Asn1Tag.PRIM, 41, len);
         _aal += len;

         if (explicitTagging) {
            _aal += buffer.EncodeTagAndLength (_TAG, _aal);
         }

         return (_aal);
      }

      public override void Print (System.IO.TextWriter _out, 
                                  string _varName, int _level)
      {
         Indent (_out, _level);
         _out.WriteLine (_varName + " {");
         if (cpi != null) cpi.Print (_out, "cpi", _level+1);
         if (car != null) car.Print (_out, "car", _level+1);
         if (puk != null) puk.Print (_out, "puk", _level+1);
         if (chr != null) chr.Print (_out, "chr", _level+1);
         if (chat != null) chat.Print (_out, "chat", _level+1);
         if (ced != null) ced.Print (_out, "ced", _level+1);
         if (cxd != null) cxd.Print (_out, "cxd", _level+1);
         Indent (_out, _level);
         _out.WriteLine ("}");
      }
   }
}
