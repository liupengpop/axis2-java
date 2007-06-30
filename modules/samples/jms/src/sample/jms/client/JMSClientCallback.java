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
package sample.jms.client;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.util.StAXUtils;
import org.apache.axis2.client.async.AsyncResult;
import org.apache.axis2.client.async.Callback;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class JMSClientCallback extends Callback {
        private static final Log log = LogFactory.getLog(JMSClientCallback.class);
        private boolean finish = false;
        
        public boolean isFinish() {
            return finish;
        }
        
        public void onComplete(AsyncResult result) {
            try {
                result.getResponseEnvelope().serialize(StAXUtils
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
        
    }