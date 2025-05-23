using System.Collections.Generic;
using System.Xml;

namespace tr.gov.tubitak.uekae.esya.api.xmlsignature.model.xades.timestamp
{

	using Context = tr.gov.tubitak.uekae.esya.api.xmlsignature.Context;
	using C14nMethod = tr.gov.tubitak.uekae.esya.api.xmlsignature.C14nMethod;

	using Constants = tr.gov.tubitak.uekae.esya.api.xmlsignature.core.Constants;
	using XmlCommonUtil = tr.gov.tubitak.uekae.esya.api.common.util.XmlUtil;
    using Any = tr.gov.tubitak.uekae.esya.api.xmlsignature.model.Any;
	using Element = XmlElement;


	/// <summary>
	/// The abstract base container type for time-stamp tokens specified by the
	/// present document does have the following features:
	/// <ul>
	/// <li>It may contain encapsulated RFC 3161 [10] time-stamp tokens as well
	/// as XML time-stamp tokens.
	/// <li>It may contain more than one time-stamp token generated for the same
	/// XAdES data objects (each one issued by different TSAs, for instance).
	/// <li>It provides means for managing time-stamp tokens computed on
	/// XAdES data objects (as for the aforementioned XAdES properties) or
	/// time-stamp tokens computed on external data.
	/// <li>It may use specific elements for identifying what is time-stamped and
	/// how to generate the input data for the computation of the digest value
	/// to be sent to the TSA. For certain XAdES data objects under certain
	/// circumstances this information may be implicit.
	/// </ul>
	/// 
	/// <p>Below follows the schema definition for the data type.
	/// 
	/// <pre>
	/// &lt;xsd:complexType name="IncludeType">
	///   &lt;xsd:attribute name="URI" type="xsd:anyURI" use="required"/>
	///   &lt;xsd:attribute name="referencedData" type="xsd:boolean" use="optional"/>
	/// &lt;/xsd:complexType>
	/// 
	/// &lt;xsd:element name="ReferenceInfo" type="ReferenceInfoType"/>
	/// 
	/// &lt;xsd:complexType name="ReferenceInfoType">
	///   &lt;xsd:sequence>
	///     &lt;xsd:element ref="ds:DigestMethod"/>
	///     &lt;xsd:element ref="ds:DigestValue"/>
	///   &lt;/xsd:sequence>
	///   &lt;xsd:attribute name="Id" type="xsd:ID" use="optional"/>
	///   &lt;xsd:attribute name="URI" type="xsd:anyURI" use="optional"/>
	/// &lt;/xsd:complexType>
	/// 
	/// &lt;xsd:complexType name="GenericTimeStampType" abstract="true">
	///   &lt;xsd:sequence>
	///     &lt;xsd:choice minOccurs="0">
	///       &lt;xsd:element ref="Include" minOccurs="0" maxOccurs="unbounded"/>
	///       &lt;xsd:element ref="ReferenceInfo" maxOccurs="unbounded"/>
	///     &lt;/xsd:choice>
	///     &lt;xsd:element ref="ds:CanonicalizationMethod" minOccurs="0"/>
	///     &lt;xsd:choice maxOccurs="unbounded">
	///       &lt;xsd:element name="EncapsulatedTimeStamp" type="EncapsulatedPKIDataType"/>
	///       &lt;xsd:element name="XMLTimeStamp" type="AnyType"/>
	///     &lt;/xsd:choice>
	///   &lt;/xsd:sequence>
	///   &lt;xsd:attribute name="Id" type="xsd:ID" use="optional"/>
	/// &lt;/xsd:complexType>
	/// </pre>
	/// 
	/// <p>When present, the optional <code>ds:CanonicalizationMethod</code> element
	/// will indicate the canonicalization method used for canonicalizing XML node
	/// sets resulting after retrieving (and processing when required) the data
	/// objects covered by the time-stamp token(s). When not present, the standard
	/// canonicalization method as specified by XMLDSIG MUST be used.
	/// 
	/// <p>The time-stamp token generated by the TSA can be either an ASN.1 data
	/// object (as defined in [10], use <code>EncapsulatedTimeStamp</code>), or it
	/// can be encoded as XML (use <code>XMLTimeStamp</code>).
	/// 
	/// @author ahmety
	/// date: Sep 28, 2009
	/// </summary>
	public abstract class GenericTimeStamp : tr.gov.tubitak.uekae.esya.api.xmlsignature.model.xades.XAdESBaseElement
	{
		//
		protected internal IList<Include> mIncludes = new List<Include>(0);
		protected internal IList<ReferenceInfo> mReferenceInfos = new List<ReferenceInfo>(0);

		protected internal C14nMethod mCanonicalizationMethod;

		// either one of below
		protected internal IList<EncapsulatedTimeStamp> mEncapsulatedTimeStamps = new List<EncapsulatedTimeStamp>(0);
		protected internal IList<Any> mXMLTimeStamps = new List<Any>(0);


		protected internal GenericTimeStamp(Context aContext) : base(aContext)
		{
			Id = aContext.IdGenerator.uret(LocalName);
		}

		/// <summary>
		/// Construct GenericTimeStamp from existing </summary>
		/// <param name="aElement"> xml element </param>
		/// <param name="aContext"> according to context </param>
		/// <exception cref="tr.gov.tubitak.uekae.esya.api.xmlsignature.XMLSignatureException">
		///          when structure is invalid or can not be
		///          resolved appropriately </exception>
		protected internal GenericTimeStamp(Element aElement, Context aContext) : base(aElement, aContext)
		{

            Element[] includeElements = selectChildren(Constants.NS_XADES_1_3_2, Constants.TAGX_INCLUDE);
			if (includeElements != null)
			{
				foreach (Element includeElement in includeElements)
				{
					mIncludes.Add(new Include(includeElement, mContext));
				}
			}
            Element[] referenceInfoElements = selectChildren(Constants.NS_XADES_1_3_2, Constants.TAGX_REFERENCEINFO);
			if (referenceInfoElements != null)
			{
				foreach (Element referenceInfoElement in referenceInfoElements)
				{
					mReferenceInfos.Add(new ReferenceInfo(referenceInfoElement, mContext));
				}
			}

            Element c14nElement = selectChildElement(Constants.NS_XMLDSIG, Constants.TAG_C14NMETHOD);
			if (c14nElement != null)
			{
                string c14nAlg = getAttribute(c14nElement, Constants.ATTR_ALGORITHM);
				mCanonicalizationMethod = C14nMethod.resolve(c14nAlg);
			}

            Element[] encapsulatedTSElm = selectChildren(Constants.NS_XADES_1_3_2, Constants.TAGX_ENCAPSULATEDTIMESTAMP);
			if (encapsulatedTSElm != null)
			{
				foreach (Element anEncapsulatedTSElm in encapsulatedTSElm)
				{
					mEncapsulatedTimeStamps.Add(new EncapsulatedTimeStamp(anEncapsulatedTSElm, this, mContext));
				}
			}
            Element[] xmlTSElm = selectChildren(Constants.NS_XADES_1_3_2, Constants.TAGX_XMLTIMESTAMP);
			if (xmlTSElm != null)
			{
				foreach (Element aXmlTSElm in xmlTSElm)
				{
					mXMLTimeStamps.Add(new XMLTimeStamp(aXmlTSElm, mContext));
				}
			}

			if (mId != null)
			{
                if (mElement.HasAttribute(Constants.ATTR_ID))
                {
                    mElement.RemoveAttribute(Constants.ATTR_ID);
                }
                mElement.SetAttribute(Constants.ATTR_ID,null, mId);
			}

		}

		private void setupChildren()
		{
            XmlCommonUtil.removeChildren(mElement);

			addLineBreak();

			if (mIncludes != null)
			{
				foreach (Include mInclude in mIncludes)
				{
					mElement.AppendChild(mInclude.Element);
					addLineBreak();
				}
			}
			if (mReferenceInfos != null)
			{
				foreach (ReferenceInfo mReferenceInfo in mReferenceInfos)
				{
					mElement.AppendChild(mReferenceInfo.Element);
					addLineBreak();
				}
			}
			if (mCanonicalizationMethod != null)
			{
                Element c14Element = insertElement(Constants.NS_XMLDSIG, Constants.TAG_C14NMETHOD);
                c14Element.SetAttribute(Constants.ATTR_ALGORITHM, null,mCanonicalizationMethod.URL);
			}
			if (mEncapsulatedTimeStamps != null)
			{
				foreach (EncapsulatedTimeStamp mEncapsulatedTimeStamp in mEncapsulatedTimeStamps)
				{
					mElement.AppendChild(mEncapsulatedTimeStamp.Element);
					addLineBreak();
				}
			}
			if (mXMLTimeStamps != null)
			{
				foreach (Any mXMLTimeStamp in mXMLTimeStamps)
				{
					mElement.AppendChild(mXMLTimeStamp.Element);
					addLineBreak();
				}
			}
			if (mId != null)
			{
                if (mElement.HasAttribute(Constants.ATTR_ID))
                {
                    mElement.RemoveAttribute(Constants.ATTR_ID);
                }
                mElement.SetAttribute(Constants.ATTR_ID,null, mId);
			}
		}

		public virtual int IncludeCount
		{
			get
			{
				return mIncludes.Count;
			}
		}

		public virtual Include getInclude(int aIndex)
		{
			return mIncludes[aIndex];
		}

		public virtual void addInclude(Include aInclude)
		{
			mIncludes.Add(aInclude);
			setupChildren();
		}

		public virtual int ReferenceInfoCount
		{
			get
			{
				return mReferenceInfos.Count;
			}
		}

		public virtual ReferenceInfo getReferenceInfo(int aIndex)
		{
			return mReferenceInfos[aIndex];
		}

		public virtual void addReferenceInfo(ReferenceInfo aReferenceInfo)
		{
			mReferenceInfos.Add(aReferenceInfo);
			setupChildren();
		}

		public virtual C14nMethod CanonicalizationMethod
		{
			get
			{
				return mCanonicalizationMethod;
			}
			set
			{
				mCanonicalizationMethod = value;
				setupChildren();
			}
		}


		public virtual int EncapsulatedTimeStampCount
		{
			get
			{
				return mEncapsulatedTimeStamps.Count;
			}
		}

		public virtual EncapsulatedTimeStamp getEncapsulatedTimeStamp(int aIndex)
		{
			return mEncapsulatedTimeStamps[aIndex];
		}

		public virtual void addEncapsulatedTimeStamp(EncapsulatedTimeStamp aETS)
		{
			mEncapsulatedTimeStamps.Add(aETS);
			setupChildren();
		}


		public virtual IList<Any> XMLTimeStamps
		{
			get
			{
				return mXMLTimeStamps;
			}
			set
			{
				mXMLTimeStamps = value;
				setupChildren();
			}
		}


	}

}