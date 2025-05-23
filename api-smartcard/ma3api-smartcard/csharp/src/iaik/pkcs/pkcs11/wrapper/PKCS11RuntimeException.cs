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
using TokenRuntimeException = iaik.pkcs.pkcs11.TokenRuntimeException;
namespace iaik.pkcs.pkcs11.wrapper
{
	
	
	
	/// <summary> This is the superclass of all runtime exception used by this library.
	/// For instance, Runtime exceptions occur, if an internal error in the native
	/// part of the wrapper occurs.
	/// 
	/// </summary>
	/// <author>  <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
	/// </author>
	/// <version>  1.0
	/// </version>
	/// <invariants>  </invariants>
	[Serializable]
	public class PKCS11RuntimeException:TokenRuntimeException
	{
		
		/// <summary> Empty constructor.
		/// 
		/// </summary>
		/// <preconditions>  </preconditions>
		/// <postconditions>  </postconditions>
		public PKCS11RuntimeException():base()
		{
		}
		
		/// <summary> Constructor taking a string that describes the reason of the exception
		/// in more detail.
		/// 
		/// </summary>
		/// <param name="message">A descrption of the reason for this exception.
		/// </param>
		/// <preconditions>  </preconditions>
		/// <postconditions>  </postconditions>
		public PKCS11RuntimeException(System.String message):base(message)
		{
		}
		
		/// <summary> Constructor taking an other exception to wrap.
		/// 
		/// </summary>
		/// <param name="encapsulatedException">The other exception the wrap into this.
		/// </param>
		/// <preconditions>  </preconditions>
		/// <postconditions>  </postconditions>
		public PKCS11RuntimeException(System.Exception encapsulatedException):base(encapsulatedException)
		{
		}
		
		/// <summary> Constructor taking a message for this exception and an other exception to
		/// wrap.
		/// 
		/// </summary>
		/// <param name="message">The message giving details about the exception to ease
		/// debugging.
		/// </param>
		/// <param name="encapsulatedException">The other exception the wrap into this.
		/// </param>
		/// <preconditions>  </preconditions>
		/// <postconditions>  </postconditions>
		public PKCS11RuntimeException(System.String message, System.Exception encapsulatedException):base(message, encapsulatedException)
		{
		}
	}
}