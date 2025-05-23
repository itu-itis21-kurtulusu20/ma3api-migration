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
package tr.gov.tubitak.uekae.esya.api.xmlsignature.c14n.core.signature;

import org.w3c.dom.Node;

/**
 * An interface to tell to the c14n if a node is included or not in the output
 * @author raul
 *
 */
public interface NodeFilter {
	/**
	 * Tells if a node must be outputed in c14n.
	 * @param n
	 * @return 1 if the node should be outputed.
	 * 		   0 if node must not be outputed, 
	 * 		  -1 if the node and all it's child must not be output.
	 * 			
	 */
	public int isNodeInclude(Node n);
	/**
	 * Tells if a node must be outputed in a c14n.
	 * The caller must assured that this method is always call
	 * in document order. The implementations can use this 
	 * restriction to optimize the transformation.
	 * @param n
	 * @param level the relative level in the tree
	 * @return 1 if the node should be outputed.
	 * 		   0 if node must not be outputed, 
	 * 		  -1 if the node and all it's child must not be output.
	 */
	public int isNodeIncludeDO(Node n, int level);

}
