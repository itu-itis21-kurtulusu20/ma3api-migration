/*
 * Copyright  1999-2004 The Apache Software Foundation.
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
 * Class Canonicalizer20010315ExclWithComments
 *
 * @version $Revision: 351115 $
 */
public class Canonicalizer20010315ExclWithComments
        extends Canonicalizer20010315Excl {

   /**
    * Constructor Canonicalizer20010315ExclWithComments
    *
    */
   public Canonicalizer20010315ExclWithComments() {
      super(true);
   }

   /** {@inheritDoc} */
   public final String engineGetURI() {
      return Canonicalizer.ALGO_ID_C14N_EXCL_WITH_COMMENTS;
   }

   /** {@inheritDoc} */
   public final boolean engineGetIncludeComments() {
      return true;
   }
}
