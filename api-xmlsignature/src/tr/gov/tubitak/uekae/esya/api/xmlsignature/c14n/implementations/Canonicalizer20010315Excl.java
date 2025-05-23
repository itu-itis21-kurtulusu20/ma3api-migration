
/*
 * Copyright 1999-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *  
 */
package tr.gov.tubitak.uekae.esya.api.xmlsignature.c14n.implementations;

import java.util.*;

import tr.gov.tubitak.uekae.esya.api.xmlsignature.c14n.CanonicalizationException;
import tr.gov.tubitak.uekae.esya.api.xmlsignature.c14n.helper.C14nHelper;
import tr.gov.tubitak.uekae.esya.api.xmlsignature.c14n.core.utils.Constants;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Implements &quot; <A
 * HREF="http://www.w3.org/TR/2002/REC-xml-exc-c14n-20020718/">Exclusive XML
 * Canonicalization, Version 1.0 </A>&quot; <br>
 * Credits: During restructuring of the Canonicalizer framework, Ren??
 * Kollmorgen from Software AG submitted an implementation of ExclC14n which
 * fitted into the old architecture and which based heavily on my old (and slow)
 * implementation of "Canonical XML". A big "thank you" to Ren?? for this.
 * <br>
 * <i>THIS </i> implementation is a complete rewrite of the algorithm.
 * 
 * @author Christian Geuer-Pollmann &lt;geuerp@apache.org&gt;
 * @version $Revision: 428439 $ 
 * @see <a href="http://www.w3.org/TR/2002/REC-xml-exc-c14n-20020718/Exclusive#">
 *          XML Canonicalization, Version 1.0</a>
 */
public abstract class Canonicalizer20010315Excl extends CanonicalizerBase {
    /**
      * This Set contains the names (Strings like "xmlns" or "xmlns:foo") of
      * the inclusive namespaces.
      */
    TreeSet _inclusiveNSSet = new TreeSet();
    static final String XMLNS_URI=Constants.NamespaceSpecNS;
    final SortedSet result = new TreeSet(COMPARE);
	/**
	 * Constructor Canonicalizer20010315Excl
	 * 
	 * @param includeComments
	 */
	public Canonicalizer20010315Excl(boolean includeComments) {
		super(includeComments);
	}

	/**
	 * Method engineCanonicalizeSubTree
	 {@inheritDoc}
	 * @param rootNode
	 * 
	 * @throws CanonicalizationException
	 */
	public byte[] engineCanonicalizeSubTree(Node rootNode)
			throws CanonicalizationException {
		return this.engineCanonicalizeSubTree(rootNode, "",null);
	}
	/**
	 * Method engineCanonicalizeSubTree
	 {@inheritDoc}
	 * @param rootNode
	 * @param inclusiveNamespaces
	 * 
	 * @throws CanonicalizationException
	 */
	public byte[] engineCanonicalizeSubTree(Node rootNode,
			String inclusiveNamespaces) throws CanonicalizationException {
		return this.engineCanonicalizeSubTree(rootNode, inclusiveNamespaces,null);
	}
	/**
	 * Method engineCanonicalizeSubTree  
	 * @param rootNode
     * @param inclusiveNamespaces   
     * @param excl A element to exclude from the c14n process. 
	 * @return the rootNode c14n.
	 * @throws CanonicalizationException
	 */
	public byte[] engineCanonicalizeSubTree(Node rootNode,
			String inclusiveNamespaces,Node excl) throws CanonicalizationException {
			this._inclusiveNSSet = (TreeSet)prefixStr2Set(inclusiveNamespaces);
			return super.engineCanonicalizeSubTree(rootNode,excl);
	}

	/**
	 * Method handleAttributesSubtree
	 * @inheritDoc
	 * @param E
	 * @throws CanonicalizationException
	 */
	Iterator handleAttributesSubtree(Element E,NameSpaceSymbTable ns)
			throws CanonicalizationException {
		// System.out.println("During the traversal, I encountered " +
		// XMLUtils.getXPath(E));
		// result will contain the attrs which have to be outputted
		SortedSet result = this.result;       
	    result.clear();
		NamedNodeMap attrs=null;
        
		int attrsLength = 0;
        if (E.hasAttributes()) {
            attrs = E.getAttributes();
        	attrsLength = attrs.getLength();
        }
		//The prefix visibly utilized(in the attribute or in the name) in the element
		SortedSet visiblyUtilized =(SortedSet) _inclusiveNSSet.clone();
					
		for (int i = 0; i < attrsLength; i++) {
			Attr N = (Attr) attrs.item(i);
					
			if (XMLNS_URI!=N.getNamespaceURI()) {
				//Not a namespace definition.
				//The Element is output element, add his prefix(if used) to visibyUtilized
				String prefix = N.getPrefix();
				if ( (prefix != null) && (!prefix.equals(XML) && !prefix.equals(XMLNS)) ) {
						visiblyUtilized.add(prefix);
				}					
				//Add to the result.
				 result.add(N);				
				continue;
			}
			String NName=N.getLocalName();
			String NNodeValue=N.getNodeValue();
		
			if (ns.addMapping(NName, NNodeValue,N)) {
				//New definition check if it is relative.
                if (C14nHelper.namespaceIsRelative(NNodeValue)) {
                    Object exArgs[] = {E.getTagName(), NName,
                            N.getNodeValue()};
                    throw new CanonicalizationException(
                            "c14n.Canonicalizer.RelativeNamespace", exArgs);
                }
            }
		}		
		String prefix;
		if (E.getNamespaceURI() != null) {
			prefix = E.getPrefix();
			if ((prefix == null) || (prefix.length() == 0)) {
				prefix=XMLNS;
			}
						
		} else {
			prefix=XMLNS;
		}
		visiblyUtilized.add(prefix);
									
		//This can be optimezed by I don't have time
		Iterator it=visiblyUtilized.iterator();
		while (it.hasNext()) {
			String s=(String)it.next();									
			Attr key=ns.getMapping(s);
			if (key==null) {
				continue;
			}
			result.add(key);
		}
		
		return result.iterator(); 		
	}

	/**
	 * Method engineCanonicalizeXPathNodeSet
	 {@inheritDoc}
	 * @param xpathNodeSet
	 * @param inclusiveNamespaces
	 * @throws CanonicalizationException
	 */
	public byte[] engineCanonicalizeXPathNodeSet(Set xpathNodeSet,
			String inclusiveNamespaces) throws CanonicalizationException {
		
		
			this._inclusiveNSSet = (TreeSet)prefixStr2Set(inclusiveNamespaces);
			return super.engineCanonicalizeXPathNodeSet(xpathNodeSet);
		
	}

    private static SortedSet prefixStr2Set(String inclusiveNamespaces) {

       SortedSet prefixes = new TreeSet();

       if ((inclusiveNamespaces == null)
               || (inclusiveNamespaces.length() == 0)) {
          return prefixes;
       }

       StringTokenizer st = new StringTokenizer(inclusiveNamespaces, " \t\r\n");

       while (st.hasMoreTokens()) {
          String prefix = st.nextToken();

          if (prefix.equals("#default")) {
             prefixes.add("xmlns" );
          } else {
             prefixes.add( prefix);
          }
       }

       return prefixes;
    }


	/**
     * @inheritDoc
	 * @param E
	 * @throws CanonicalizationException
	 */
	final Iterator handleAttributes(Element E, NameSpaceSymbTable ns)
			throws CanonicalizationException {
		// result will contain the attrs which have to be outputted
		SortedSet result = this.result;       
	    result.clear();
		NamedNodeMap attrs = null;
		int attrsLength = 0;
        if (E.hasAttributes()) {
            attrs = E.getAttributes();           
        	attrsLength = attrs.getLength();
        }
		//The prefix visibly utilized(in the attribute or in the name) in the element
		Set visiblyUtilized =null;
		//It's the output selected.
		boolean isOutputElement=isVisibleDO(E,ns.getLevel())==1;			
		if (isOutputElement) {
			visiblyUtilized =  (Set) this._inclusiveNSSet.clone();
		}
		
		for (int i = 0; i < attrsLength; i++) {
			Attr N = (Attr) attrs.item(i);
						
						
			if (XMLNS_URI!=N.getNamespaceURI()) {
				if ( !isVisible(N) )  {
					//The node is not in the nodeset(if there is a nodeset)
					continue;
				}
				//Not a namespace definition.
				if (isOutputElement) {
					//The Element is output element, add his prefix(if used) to visibyUtilized
					String prefix = N.getPrefix();
					if ((prefix != null) && (!prefix.equals(XML) && !prefix.equals(XMLNS)) ){ 
							visiblyUtilized.add(prefix);
					}					
					//Add to the result.
				    result.add(N);
				}
				continue;
			}
			String NName=N.getLocalName();
			if (isOutputElement && !isVisible(N) && NName!=XMLNS) {
    			ns.removeMappingIfNotRender(NName);
    			continue;
    		}
			String NNodeValue=N.getNodeValue();
			
			if (!isOutputElement && isVisible(N) && _inclusiveNSSet.contains(NName) && !ns.removeMappingIfRender(NName)) {
				Node n=ns.addMappingAndRender(NName,NNodeValue,N);
			 	if (n!=null) {
			 	 		result.add(n);
	                    if (C14nHelper.namespaceIsRelative(N)) {
	                       Object exArgs[] = { E.getTagName(), NName, N.getNodeValue() };
	                       throw new CanonicalizationException(
	                          "c14n.Canonicalizer.RelativeNamespace", exArgs);
	                   }
			 	 }
			}
						
			
			
			if (ns.addMapping(NName, NNodeValue,N)) {
                //New definiton check if it is relative
                if (C14nHelper.namespaceIsRelative(NNodeValue)) {
                    Object exArgs[] = {E.getTagName(), NName,
                            N.getNodeValue()};
                    throw new CanonicalizationException(
                            "c14n.Canonicalizer.RelativeNamespace", exArgs);
                }    
            }
		}

		if (isOutputElement) {	               
           //The element is visible, handle the xmlns definition    
           Attr xmlns = E.getAttributeNodeNS(XMLNS_URI, XMLNS);
           if ((xmlns!=null) &&  (!isVisible(xmlns))) {
              //There is a definition but the xmlns is not selected by the xpath.
              //then xmlns=""
              ns.addMapping(XMLNS,"",nullNode);                               
            }

			if (E.getNamespaceURI() != null) {
				String prefix = E.getPrefix();
				if ((prefix == null) || (prefix.length() == 0)) {
					visiblyUtilized.add(XMLNS);
				} else {
					visiblyUtilized.add( prefix);
				}
			} else {
				visiblyUtilized.add(XMLNS);
			}									
			//This can be optimezed by I don't have time
			//visiblyUtilized.addAll(this._inclusiveNSSet);
			Iterator it=visiblyUtilized.iterator();
			while (it.hasNext()) {
				String s=(String)it.next();										
				Attr key=ns.getMapping(s);
				if (key==null) {
					continue;
				}
				result.add(key);
			}
		} 

		return result.iterator(); 
	}
}
