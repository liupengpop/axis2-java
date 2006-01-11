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

package org.apache.axis2.mail;


import junit.framework.TestCase;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.client.async.AsyncResult;
import org.apache.axis2.client.async.Callback;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.AxisOperation;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.description.OutInAxisOperation;
import org.apache.axis2.engine.Echo;
import org.apache.axis2.engine.MessageReceiver;
import org.apache.axis2.om.OMAbstractFactory;
import org.apache.axis2.om.OMElement;
import org.apache.axis2.om.OMFactory;
import org.apache.axis2.om.OMNamespace;
import org.apache.axis2.soap.SOAPEnvelope;
import org.apache.axis2.util.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

/**
 * This will use the In out functionality to test the mailet functionality. This code was
 * based on the code on the MailRequestResponseRawXMLTest.java.
 */
public class MailetRequestResponseRawXMLTest extends TestCase {
    private EndpointReference targetEPR =
            new EndpointReference("axis2-server@127.0.0.1" +
                    "/axis/services/EchoXMLService/echoOMElement");
    private Log log = LogFactory.getLog(getClass());
    private QName serviceName = new QName("EchoXMLService");
    private QName operationName = new QName("echoOMElement");

    private SOAPEnvelope envelope;

    private boolean finish = false;

    public MailetRequestResponseRawXMLTest() {
        super(MailetRequestResponseRawXMLTest.class.getName());
    }

    public MailetRequestResponseRawXMLTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        ConfigurationContext configContext = UtilsMailServer.start();

//        configContext.getAxisConfiguration().engageModule(
//                new QName(Constants.MODULE_ADDRESSING));
        AxisService service =
                Utils.createSimpleService(serviceName,
                        Echo.class.getName(),
                        operationName);
        configContext.getAxisConfiguration().addService(service);
    }

    protected void tearDown() throws Exception {
        UtilsMailServer.stop();
    }

    private OMElement createEnvelope() {
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace omNs = fac.createOMNamespace("http://localhost/my", "my");
        OMElement method = fac.createOMElement("echoOMElement", omNs);
        OMElement value = fac.createOMElement("myValue", omNs);
        value.addChild(
                fac.createText(value, "Isaac Asimov, The Foundation Trilogy"));
        method.addChild(value);

        return method;
    }

    public void testEchoXMLCompleteASync() throws Exception {

        ConfigurationContext configContext = UtilsMailServer.createClientConfigurationContext();

        AxisService service = new AxisService(serviceName.getLocalPart());
        AxisOperation axisOperation = new OutInAxisOperation();
        axisOperation.setName(operationName);
        axisOperation.setMessageReceiver(new MessageReceiver() {
            public void receive(MessageContext messageCtx) {
                envelope = messageCtx.getEnvelope();
            }
        });
        service.addOperation(axisOperation);
        configContext.getAxisConfiguration().addService(service);

        Options options = new Options();
        options.setTo(targetEPR);
        options.setTransportInProtocol(Constants.TRANSPORT_MAIL);
        options.setUseSeparateListener(true);
        Callback callback = new Callback() {
            public void onComplete(AsyncResult result) {
                try {
                    result.getResponseEnvelope().serializeAndConsume(
                            XMLOutputFactory.newInstance()
                                    .createXMLStreamWriter(System.out));
                } catch (XMLStreamException e) {
                    onError(e);
                } finally {
                    finish = true;
                }
            }

            public void onError(Exception e) {
                log.info(e.getMessage());
                finish = true;
            }
        };
        ServiceClient sender = new ServiceClient(configContext, service);
        sender.setOptions(options);
        options.setTo(targetEPR);
        sender.sendReceiveNonBlocking(operationName, createEnvelope(), callback);
        int index = 0;
        while (!finish) {
            Thread.sleep(1000);
            index++;
            if (index > 10) {
                throw new AxisFault(
                        "Async response is taking too long[10s+]. Server is being shut down.");
            }
        }

    }
}
