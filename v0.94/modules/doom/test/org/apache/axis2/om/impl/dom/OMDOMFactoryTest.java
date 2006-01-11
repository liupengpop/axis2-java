/*
 * Copyright 2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.axis2.om.impl.dom;

import junit.framework.TestCase;
import org.apache.axis2.om.OMElement;
import org.apache.axis2.om.impl.dom.factory.OMDOMFactory;

import javax.xml.namespace.QName;

public class OMDOMFactoryTest extends TestCase{

	public OMDOMFactoryTest() {
		super();
	}
	
	public OMDOMFactoryTest(String name) {
		super(name);
	}
	
	public void testCreateElement() {
		OMDOMFactory factory = new OMDOMFactory();
		String localName = "TestLocalName";
		String namespace = "http://ws.apache.org/axis2/ns";
		String prefix = "axis2";
		OMElement elem = factory.createOMElement(localName,namespace,prefix);
		QName qname = elem.getQName();
		
		assertEquals("Localname mismatch",localName,qname.getLocalPart());
		assertEquals("Namespace mismatch",namespace,qname.getNamespaceURI());
		assertEquals("namespace prefix mismatch", prefix, qname.getPrefix());
	}

}
