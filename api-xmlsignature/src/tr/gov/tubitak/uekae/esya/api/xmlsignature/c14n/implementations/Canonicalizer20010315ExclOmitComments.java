/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package tr.gov.tubitak.uekae.esya.api.xmlsignature.c14n.implementations;

import tr.gov.tubitak.uekae.esya.api.xmlsignature.c14n.Canonicalizer;

/**
 *  
 *
 */
public class Canonicalizer20010315ExclOmitComments
        extends Canonicalizer20010315Excl {

   /**
    * 
    */
	public Canonicalizer20010315ExclOmitComments() {
      super(false);
   }
 
   /** {@inheritDoc} */
   public final String engineGetURI() {
      return Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS;
   }

   /** {@inheritDoc} */
   public final boolean engineGetIncludeComments() {
      return false;
   }
}
