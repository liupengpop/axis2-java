package org.apache.axis2.deployment;

import junit.framework.TestCase;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.description.AxisMessage;
import org.apache.axis2.description.AxisOperation;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.description.Parameter;
import org.apache.axis2.engine.AxisConfiguration;

import javax.xml.namespace.QName;
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
*
*/

public class AxisMessageTest extends TestCase {

    public void testAxisMessage() throws Exception {
        String filename = "./test-resources/deployment/AxisMessageTestRepo";
        ConfigurationContextFactory builder = new ConfigurationContextFactory();
        AxisConfiguration er = builder.createConfigurationContextFromFileSystem(filename)
                .getAxisConfiguration();

        assertNotNull(er);
        AxisService service = er.getService("MessagetestService");
        assertNotNull(service);
        AxisOperation op = service.getOperation(new QName("echoString"));
        assertNotNull(op);
        AxisMessage message = op.getMessage("In");
        assertNotNull(message);
        Parameter para = message.getParameter("messageIN");
        assertNotNull(para);
        assertEquals(para.getValue(), "messageIN");


        op = service.getOperation(new QName("echoStringArray"));
        assertNotNull(op);
        message = op.getMessage("In");
        assertNotNull(message);
        para = message.getParameter("messageIN");
        assertNotNull(para);
        assertEquals(para.getValue(), "messageIN");

        message = op.getMessage("Out");
        assertNotNull(message);
        para = message.getParameter("messageOut");
        assertNotNull(para);
        assertEquals(para.getValue(), "messageOut");

    }
}
