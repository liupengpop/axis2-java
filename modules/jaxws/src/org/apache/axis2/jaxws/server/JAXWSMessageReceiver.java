/*
 * Copyright 2004,2005 The Apache Software Foundation.
 * Copyright 2006 International Business Machines Corp.
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

package org.apache.axis2.jaxws.server;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.OperationContext;
import org.apache.axis2.description.AxisOperation;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.engine.AxisEngine;
import org.apache.axis2.engine.MessageReceiver;
import org.apache.axis2.jaxws.ExceptionFactory;
import org.apache.axis2.jaxws.core.InvocationContext;
import org.apache.axis2.jaxws.core.InvocationContextImpl;
import org.apache.axis2.jaxws.core.MessageContext;
import org.apache.axis2.jaxws.message.Message;
import org.apache.axis2.wsdl.WSDLConstants.WSDL20_2004Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * The JAXWSMessageReceiver is the entry point, from the server's perspective,
 * to the JAX-WS code.  This will be called by the Axis Engine and is the end
 * of the chain from an Axis2 perspective.
 */
public class JAXWSMessageReceiver implements MessageReceiver {

private static final Log log = LogFactory.getLog(JAXWSMessageReceiver.class);
    
    private static String PARAM_SERVICE_CLASS = "ServiceClass";
    
    /**
     * We should have already determined which AxisService we're targetting at
     * this point.  So now, just get the service implementation and invoke
     * the appropriate method.
     */
    public void receive(org.apache.axis2.context.MessageContext axisRequestMsgCtx) 
        throws AxisFault {
    	if (log.isDebugEnabled()) {
            log.debug("new request received");
        }
    	
    	//Get the name of the service impl that was stored as a parameter
        // inside of the services.xml.
        AxisService service = axisRequestMsgCtx.getAxisService();
        AxisOperation operation = axisRequestMsgCtx.getAxisOperation();
        String mep = operation.getMessageExchangePattern();
        if (log.isDebugEnabled()){
            log.debug("MEP: "+ mep);
        }
        
        org.apache.axis2.description.Parameter svcClassParam = service.getParameter(PARAM_SERVICE_CLASS);
        
        try {
            if (svcClassParam == null) { 
                throw new RuntimeException("No service class was found for this AxisService");
            }
            
            //Get the appropriate endpoint dispatcher for this service
        	EndpointController endpointCtlr = new EndpointController();
          	
            InvocationContext ic = new InvocationContextImpl();
            MessageContext requestMsgCtx = new MessageContext(axisRequestMsgCtx);
            ic.setRequestMessageContext(requestMsgCtx);
            
            if (isMepInOnly(mep)) {
            	endpointCtlr.invoke(ic);
            }
            else{
	            ic = endpointCtlr.invoke(ic);
                
                // If this is a two-way exchange, there should already be a 
                // JAX-WS MessageContext for the response.  We need to pull 
                // the Message data out of there and set it on the Axis2 
                // MessageContext.
                MessageContext responseMsgCtx = ic.getResponseMessageContext();
                org.apache.axis2.context.MessageContext axisResponseMsgCtx = 
                    responseMsgCtx.getAxisMessageContext();                
                
                Message responseMsg = responseMsgCtx.getMessage();
                SOAPEnvelope responseEnv = (SOAPEnvelope) responseMsg.getAsOMElement();
                axisResponseMsgCtx.setEnvelope(responseEnv);
                
                OperationContext opCtx = axisResponseMsgCtx.getOperationContext();
                opCtx.addMessageContext(axisResponseMsgCtx);
                
                //Create the AxisEngine for the reponse and send it.
                AxisEngine engine = new AxisEngine(axisResponseMsgCtx.getConfigurationContext());
                engine.send(axisResponseMsgCtx);
            }
            
        } catch (Exception e) {
        	//TODO: This temp code for alpha till we add fault processing on client code.
        	// TODO NLS
            throw ExceptionFactory.makeWebServiceException(e);
        } 
    }
    
    private boolean isMepInOnly(String mep){
    	return mep.equals(WSDL20_2004Constants.MEP_URI_ROBUST_IN_ONLY) || mep.equals(WSDL20_2004Constants.MEP_URI_IN_ONLY) || mep.equals(WSDL20_2004Constants.MEP_CONSTANT_ROBUST_IN_ONLY) || mep.equals(WSDL20_2004Constants.MEP_CONSTANT_IN_ONLY);
    }
}
