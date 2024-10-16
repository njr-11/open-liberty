/*******************************************************************************
 * Copyright (c) 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package com.ibm.ws.jaxws.client;

import javax.xml.ws.WebFault;

/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.6
 * Generated source version: 2.2
 *
 */
@WebFault(name = "LargeNumbersException", targetNamespace = "http://provider.jaxws.ws.ibm.com/")
public class LargeNumbersException_Exception extends Exception {

    /**
     * Java type that goes as soapenv:Fault detail element.
     *
     */
    private final LargeNumbersException faultInfo;

    /**
     *
     * @param faultInfo
     * @param message
     */
    public LargeNumbersException_Exception(String message, LargeNumbersException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     *
     * @param faultInfo
     * @param cause
     * @param message
     */
    public LargeNumbersException_Exception(String message, LargeNumbersException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     *
     * @return
     *         returns fault bean: com.ibm.ws.jaxws.client.LargeNumbersException
     */
    public LargeNumbersException getFaultInfo() {
        return faultInfo;
    }

}
