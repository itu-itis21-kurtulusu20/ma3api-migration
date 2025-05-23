﻿using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using Org.BouncyCastle.Crypto;

namespace tr.gov.tubitak.uekae.esya.api.crypto.sig.rsa
{
  
    public class NullDigest : IDigest
    {
        private readonly MemoryStream bOut = new MemoryStream();

        public string AlgorithmName
        {
            get { return "NULL"; }
        }

        public int GetByteLength()
        {
            // TODO Is this okay?
            return 0;
        }

        public int GetDigestSize()
        {
            return (int)bOut.Length;
        }

        public void Update(byte b)
        {
            bOut.WriteByte(b);
        }

        public void BlockUpdate(byte[] inBytes, int inOff, int len)
        {
            bOut.Write(inBytes, inOff, len);
        }

        public int DoFinal(byte[] outBytes, int outOff)
        {
            byte[] res = bOut.ToArray();
            res.CopyTo(outBytes, outOff);
            Reset();
            return res.Length;
        }

        public void Reset()
        {
            bOut.SetLength(0);
        }
    }
}
