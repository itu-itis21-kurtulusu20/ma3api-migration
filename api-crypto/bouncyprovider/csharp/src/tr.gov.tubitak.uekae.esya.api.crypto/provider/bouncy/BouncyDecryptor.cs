﻿using System.IO;
using Org.BouncyCastle.Crypto;
using Org.BouncyCastle.Crypto.Parameters;
using Org.BouncyCastle.Security;
using tr.gov.tubitak.uekae.esya.api.crypto.alg;
using tr.gov.tubitak.uekae.esya.api.crypto.parameters;
using tr.gov.tubitak.uekae.esya.api.crypto.provider.core.baseTypes;
using Org.BouncyCastle.Asn1;
using Com.Objsys.Asn1.Runtime;
namespace tr.gov.tubitak.uekae.esya.api.crypto.provider.bouncy
{
    public class BouncyDecryptor : Decryptor
    {
        /**
         * Verilen algoritmayi kullanan bir instance olusturur.
         * @param aCipherAlg Kullanilacak algoritmanin parametreleri.         
         */
        public BouncyDecryptor(CipherAlg aCipherAlg)
            : base(aCipherAlg)
        {
            _mCipher = CipherUtilities.GetCipher(BouncyProviderUtil.resolveCipher(aCipherAlg));
        }

        public override void init(IKey aKey, IAlgorithmParams aParams)
        {
            init(aKey.getEncoded(), aParams);
        }

        public override void init(byte[] aKey, IAlgorithmParams aParams)
        {
            byte[] iv = null;
            DerObjectIdentifier oid = BouncyProviderUtil.resolveCipher(_mCipherAlg);
            ICipherParameters cipherParameters = null;
            KeyParameter keyParameter = ParameterUtilities.CreateKeyParameter(oid, aKey);

            if (aParams is ParamsWithGCMSpec)
            {
                int tagLength=128; //in bits
                byte[] aadBytes = null;
                byte[] tag =((ParamsWithGCMSpec) aParams).getTag();
    
                iv = ((ParamsWithGCMSpec)aParams).getIV();
                MemoryStream aadStream = ((ParamsWithGCMSpec)aParams).getAAD();
                if (tag != null)
                   tagLength= ((ParamsWithGCMSpec)aParams).getTag().Length*8;          
                if (aadStream != null)
                   aadBytes = aadStream.ToArray();

                cipherParameters = new AeadParameters(keyParameter, tagLength, iv, aadBytes);
            }
            else if (aParams is ParamsWithIV)
            {
                iv = ((ParamsWithIV)aParams).getIV();
                Asn1OpenType asn1openType = _mCipherAlg.toAlgorithmIdentifier(iv).getParameters();
                if (asn1openType != null)
                    cipherParameters = ParameterUtilities.GetCipherParameters(oid, keyParameter, Asn1Object.FromByteArray(asn1openType.mValue));
                else
                    cipherParameters = keyParameter;
            }
            else
            {
                cipherParameters = keyParameter;
            }
            _mCipher.Init(false, cipherParameters);
        }
        public override int getBlockSize()
        {
            return _mCipherAlg.getBlockSize();
        }
    }
}
