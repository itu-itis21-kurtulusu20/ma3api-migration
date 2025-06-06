﻿using System;
using System.Collections.Generic;

namespace tr.gov.tubitak.uekae.esya.api.infra.certstore.model
{
    public class DepoSertifika
    {
        private long? mSertifikaNo;
        private DateTime? mEklenmeTarihi;
        private byte[] mValue;
        //private byte[] mHash;
        //private Long mHashNo;
        private byte[] mSerialNumber;
        private byte[] mIssuerName;
        private DateTime? mStartDate;
        private DateTime? mEndDate;
        private byte[] mSubjectName;
        private String mSubjectNameStr;
        private String mKeyUsageStr;
        private byte[] mSubjectKeyID;
        private String mEPosta;
        private byte[] mPKCS11ID;
        private String mPKCS11Lib;
        private byte[] mPrivateKey;
        private String mCardSerialNumber;
        private String mX400Address;
        private Dictionary<int, byte[]> mOzetTablo;

        public long? getSertifikaNo()
        {
            return mSertifikaNo;
        }
        public void setSertifikaNo(long? aSertifikaNo)
        {
            mSertifikaNo = aSertifikaNo;
        }

        public DateTime? getEklenmeTarihi()
        {
            return mEklenmeTarihi;
        }
        public void setEklenmeTarihi(DateTime? aEklenmeTarihi)
        {
            mEklenmeTarihi = aEklenmeTarihi;
        }

        public byte[] getValue()
        {
            return mValue;
        }
        public void setValue(byte[] aValue)
        {
            mValue = aValue;
        }

        /*public Long getmHashNo()
        {
            return mHashNo;
        }
        public void setmHashNo(Long aHashNo)
        {
            mHashNo = aHashNo;
        }*/

        public byte[] getSerialNumber()
        {
            return mSerialNumber;
        }
        public void setSerialNumber(byte[] aSerialNumber)
        {
            mSerialNumber = aSerialNumber;
        }

        public byte[] getNormalizedIssuerName()
        {
            return mIssuerName;
        }
        public void setIssuerName(byte[] aIssuerName)
        {
            mIssuerName = aIssuerName;
        }

        public DateTime? getStartDate()
        {
            return mStartDate;
        }
        public void setStartDate(DateTime? aStartDate)
        {
            mStartDate = aStartDate;
        }

        public DateTime? getEndDate()
        {
            return mEndDate;
        }
        public void setEndDate(DateTime? aEndDate)
        {
            mEndDate = aEndDate;
        }

        public byte[] getNormalizedSubjectName()
        {
            return mSubjectName;
        }
        public void setSubjectName(byte[] aSubjectName)
        {
            mSubjectName = aSubjectName;
        }

        public String getSubjectNameStr()
        {
            return mSubjectNameStr;
        }

        public void setSubjectNameStr(String aSubjectNameStr)
        {
            mSubjectNameStr = aSubjectNameStr;
        }

        public String getKeyUsageStr()
        {
            return mKeyUsageStr;
        }
        public void setKeyUsageStr(String aKeyUsageStr)
        {
            mKeyUsageStr = aKeyUsageStr;
        }

        public byte[] getSubjectKeyID()
        {
            return mSubjectKeyID;
        }

        public void setSubjectKeyID(byte[] aSubjectKeyID)
        {
            mSubjectKeyID = aSubjectKeyID;
        }
        public String getEPosta()
        {
            return mEPosta;
        }
        public void setEPosta(String aEPosta)
        {
            mEPosta = aEPosta;
        }

        public byte[] getPKCS11ID()
        {
            return mPKCS11ID;
        }

        public void setPKCS11ID(byte[] aPKCS11ID)
        {
            mPKCS11ID = aPKCS11ID;
        }

        public String getPKCS11Lib()
        {
            return mPKCS11Lib;
        }

        public void setPKCS11Lib(String aPKCS11Lib)
        {
            mPKCS11Lib = aPKCS11Lib;
        }

        public byte[] getPrivateKey()
        {
            return mPrivateKey;
        }

        public void setPrivateKey(byte[] aPrivateKey)
        {
            mPrivateKey = aPrivateKey;
        }

        public String getCardSerialNumber()
        {
            return mCardSerialNumber;
        }

        public void setCardSerialNumber(String aCardSerialNumber)
        {
            mCardSerialNumber = aCardSerialNumber;
        }
        public String getX400Address()
        {
            return mX400Address;
        }

        public void setX400Address(String aX400Address)
        {
            mX400Address = aX400Address;
        }
        public Dictionary<int, byte[]> getOzetTablo()
        {
            return mOzetTablo;
        }

        public void setOzetTablo(Dictionary<int, byte[]> aOzetTablo)
        {
            mOzetTablo = aOzetTablo;
        }

    }
}
