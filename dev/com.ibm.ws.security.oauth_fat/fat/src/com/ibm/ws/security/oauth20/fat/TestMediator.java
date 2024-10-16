/*******************************************************************************
 * Copyright (c) 1997, 2020 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package com.ibm.ws.security.oauth20.fat;

import com.ibm.oauth.core.api.attributes.AttributeList;
import com.ibm.oauth.core.api.config.OAuthComponentConfiguration;
import com.ibm.oauth.core.api.error.OAuthException;
import com.ibm.oauth.core.api.error.oauth20.OAuth20MediatorException;
import com.ibm.oauth.core.api.oauth20.mediator.OAuth20Mediator;
import com.ibm.websphere.ras.Tr;
import com.ibm.websphere.ras.TraceComponent;
import com.ibm.websphere.security.WSSecurityException;

public class TestMediator implements OAuth20Mediator {
    private static final TraceComponent tc = Tr.register(TestMediator.class,
                                                         "OAUTH", null);
    private static final String ATTRTYPE_RESPONSE_ATTRIBUTE = "urn:ibm:names:oauth:response:attribute";

    // private ResourceBundle resBundle =
    // ResourceBundle.getBundle(Constants.RESOURCE_BUNDLE, Locale.getDefault());

    @Override
    public void init(OAuthComponentConfiguration config) {
        try {
            com.ibm.wsspi.security.registry.RegistryHelper.getUserRegistry(null);
        } catch (WSSecurityException e) {
            Tr.error(tc,
                     "Fail to get UserRegistry for resource owner validation", e);
        }
    }

    @Override
    public void mediateAuthorize(AttributeList attributeList) throws OAuth20MediatorException {
        // TODO Auto-generated method stub
    }

    @Override
    public void mediateAuthorizeException(AttributeList attributeList,
                                          OAuthException exception) throws OAuth20MediatorException {
        // TODO Auto-generated method stub
    }

    @Override
    public void mediateResource(AttributeList attributeList) throws OAuth20MediatorException {
        // TODO Auto-generated method stub
    }

    @Override
    public void mediateResourceException(AttributeList attributeList,
                                         OAuthException exception) throws OAuth20MediatorException {
        // TODO Auto-generated method stub
    }

    @Override
    public void mediateToken(AttributeList attributeList) throws OAuth20MediatorException {
        final String methodName = "mediateToken";
        if (tc.isEntryEnabled()) {
            Tr.entry(tc, methodName);
        }
        throw new OAuth20MediatorException("test deliberate fail", new Exception("test deliberate fail"));
/*
 * if (FLOW_PASSWORD.equals(attributeList
 * .getAttributeValueByName("grant_type"))) {
 * String username = attributeList.getAttributeValueByName("username");
 * String password = attributeList.getAttributeValueByName("password");
 * try {
 * reg.checkPassword(username, password);
 * } catch (PasswordCheckFailedException e) {
 * throw new OAuth20MediatorException(INVALID, e);
 * } catch (CustomRegistryException e) {
 * throw new OAuth20MediatorException(INVALID, e);
 * } catch (RemoteException e) {
 * throw new OAuth20MediatorException(INVALID, e);
 * }
 * }
 *
 * if (tc.isEntryEnabled()) {
 * Tr.exit(tc, methodName);
 * }
 */
    }

    @Override
    public void mediateTokenException(AttributeList attributeList,
                                      OAuthException exception) throws OAuth20MediatorException {
        final String methodName = "mediateTokenException";

        if (tc.isEntryEnabled()) {
            Tr.entry(tc, methodName);
        }

        if ("password".equals(attributeList.getAttributeValueByName("grant_type"))) {
            // clear sensitive data
            attributeList.setAttribute("access_token",
                                       ATTRTYPE_RESPONSE_ATTRIBUTE,
                                       new String[0]);
            attributeList.setAttribute("refresh_token",
                                       ATTRTYPE_RESPONSE_ATTRIBUTE,
                                       new String[0]);
        }

        if (tc.isEntryEnabled()) {
            Tr.exit(tc, methodName);
        }
    }
}
