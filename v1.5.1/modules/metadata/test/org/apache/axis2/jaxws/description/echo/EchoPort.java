/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.axis2.jaxws.description.echo;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebService;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/** This class was generated by the JAXWS SI. JAX-WS RI 2.0-b26-ea3 Generated source version: 2.0 */
@WebService(name = "EchoPort", targetNamespace = "http://ws.apache.org/axis2/tests",
            wsdlLocation = "\\work\\apps\\eclipse\\workspace\\axis2-live\\modules\\jaxws\\test-resources\\wsdl\\WSDLTests.wsdl")
public interface EchoPort {


    /** @param text  */
    @WebMethod(operationName = "Echo", action = "http://ws.apache.org/axis2/tests/echo")
    @RequestWrapper(localName = "Echo", targetNamespace = "http://ws.apache.org/axis2/tests",
                    className = "org.apache.ws.axis2.tests.Echo")
    @ResponseWrapper(localName = "EchoResponse",
                     targetNamespace = "http://ws.apache.org/axis2/tests",
                     className = "org.apache.ws.axis2.tests.EchoResponse")
    public void echo(
            @WebParam(name = "text", targetNamespace = "", mode = Mode.INOUT) Holder<String> text);

}
