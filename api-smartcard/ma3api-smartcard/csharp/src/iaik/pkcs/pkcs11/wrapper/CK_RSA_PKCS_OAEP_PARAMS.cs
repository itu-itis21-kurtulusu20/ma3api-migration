/* Copyright  (c) 2002 Graz University of Technology. All rights reserved.
*
* Redistribution and use in  source and binary forms, with or without 
* modification, are permitted  provided that the following conditions are met:
*
* 1. Redistributions of  source code must retain the above copyright notice,
*    this list of conditions and the following disclaimer.
*
* 2. Redistributions in  binary form must reproduce the above copyright notice,
*    this list of conditions and the following disclaimer in the documentation
*    and/or other materials provided with the distribution.
*  
* 3. The end-user documentation included with the redistribution, if any, must
*    include the following acknowledgment:
* 
*    "This product includes software developed by IAIK of Graz University of
*     Technology."
* 
*    Alternately, this acknowledgment may appear in the software itself, if 
*    and wherever such third-party acknowledgments normally appear.
*  
* 4. The names "Graz University of Technology" and "IAIK of Graz University of
*    Technology" must not be used to endorse or promote products derived from 
*    this software without prior written permission.
*  
* 5. Products derived from this software may not be called 
*    "IAIK PKCS Wrapper", nor may "IAIK" appear in their name, without prior 
*    written permission of Graz University of Technology.
*  
*  THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
*  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
*  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
*  PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE LICENSOR BE
*  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
*  OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
*  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
*  OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
*  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
*  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
*  OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
*  POSSIBILITY  OF SUCH DAMAGE.
*/
using System;
namespace iaik.pkcs.pkcs11.wrapper
{
	
	
	
	/// <summary> class CK_RSA_PKCS_OAEP_PARAMS provides the parameters to the
	/// CKM_RSA_PKCS_OAEP mechanism.<p>
	/// <B>PKCS#11 structure:</B>
	/// <PRE>
	/// typedef struct CK_RSA_PKCS_OAEP_PARAMS {
	/// CK_MECHANISM_TYPE hashAlg;
	/// CK_RSA_PKCS_OAEP_MGF_TYPE mgf;
	/// CK_RSA_PKCS_OAEP_SOURCE_TYPE source;
	/// CK_VOID_PTR pSourceData;
	/// CK_ULONG ulSourceDataLen;
	/// } CK_RSA_PKCS_OAEP_PARAMS;
	/// </PRE>
	/// 
	/// </summary>
	/// <author>  Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
	/// </author>
	/// <author>  Martin Schl�ffer <schlaeff@sbox.tugraz.at>
	/// </author>
	public class CK_RSA_PKCS_OAEP_PARAMS
	{
		
		/// <summary> <B>PKCS#11:</B>
		/// <PRE>
		/// CK_MECHANISM_TYPE hashAlg;
		/// </PRE>
		/// </summary>
		public long hashAlg;
		
		/// <summary> <B>PKCS#11:</B>
		/// <PRE>
		/// CK_RSA_PKCS_OAEP_MGF_TYPE mgf;
		/// </PRE>
		/// </summary>
		public long mgf;
		
		/// <summary> <B>PKCS#11:</B>
		/// <PRE>
		/// CK_RSA_PKCS_OAEP_SOURCE_TYPE source;
		/// </PRE>
		/// </summary>
		public long source;
		
		/// <summary> <B>PKCS#11:</B>
		/// <PRE>
		/// CK_VOID_PTR pSourceData;
		/// CK_ULONG ulSourceDataLen;
		/// </PRE>
		/// </summary>
		public byte[] pSourceData;
		
		//CK_ULONG ulSourceDataLen;
		// ulSourceDataLen == pSourceData.length
		
		/// <summary> Returns the string representation of CK_RSA_PKCS_OAEP_PARAMS.
		/// 
		/// </summary>
		/// <returns> the string representation of CK_RSA_PKCS_OAEP_PARAMS
		/// </returns>
		public override System.String ToString()
		{
			System.Text.StringBuilder buffer = new System.Text.StringBuilder();
			
			buffer.Append(Constants.INDENT);
			buffer.Append("hashAlg: ");
			buffer.Append(hashAlg);
			buffer.Append(Constants.NEWLINE);
			
			buffer.Append(Constants.INDENT);
			buffer.Append("mgf: ");
			buffer.Append(mgf);
			buffer.Append(Constants.NEWLINE);
			
			buffer.Append(Constants.INDENT);
			buffer.Append("source: ");
			buffer.Append(source);
			buffer.Append(Constants.NEWLINE);
			
			buffer.Append(Constants.INDENT);
			buffer.Append("pSourceData: ");
			//UPGRADE_TODO: The equivalent in .NET for method 'java.lang.Object.toString' may return a different value. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1043'"
			buffer.Append(pSourceData.ToString());
			buffer.Append(Constants.NEWLINE);
			
			buffer.Append(Constants.INDENT);
			buffer.Append("pSourceDataLen: ");
			buffer.Append(Functions.toHexString(pSourceData));
			//buffer.append(Constants.NEWLINE);
			
			return buffer.ToString();
		}
	}
}