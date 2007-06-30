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
package org.apache.axis2.jaxws.sample.addnumbers;

import org.apache.axis2.jaxws.TestLogger;

import javax.jws.WebService;


@WebService(endpointInterface="org.apache.axis2.jaxws.sample.addnumbers.AddNumbersPortType")
public class AddNumbersPortTypeImpl implements AddNumbersPortType {

	/* (non-Javadoc)
	 * @see org.apache.axis2.jaxws.sample.addnumbers.AddNumbersPortType#addNumbers(int, int)
	 */
	public int addNumbers(int arg0, int arg1) throws AddNumbersFault_Exception {
        TestLogger.logger.debug(">> Received addNumbers request for " + arg0 + " and " + arg1);
        return arg0+arg1;
	}

	/* (non-Javadoc)
	 * @see org.apache.axis2.jaxws.sample.addnumbers.AddNumbersPortType#oneWayInt(int)
	 */
	public void oneWayInt(int arg0) {
        TestLogger.logger.debug(">> Received one-way request.");
        return;
	}

}
