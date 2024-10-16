/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
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
package com.ibm.ws.beanvalidation;

import java.lang.annotation.Annotation;

import javax.annotation.Resource;
import javax.naming.spi.ObjectFactory;
import javax.validation.ValidatorFactory;

import org.osgi.service.component.annotations.Component;

import com.ibm.websphere.ras.annotation.Trivial;
import com.ibm.wsspi.injectionengine.ObjectFactoryInfo;

/**
 * Provides the data to register an ObjectFactory override for
 * the ValidatorFactory data type. <p>
 * 
 * This supports injection and lookup of an instance of ValidatorFactory
 * using the Resource annotation or resource-env xml stanza. <p>
 */
@Component(service = ObjectFactoryInfo.class)
@Trivial
public class ValidatorFactoryObjectFactoryInfo extends ObjectFactoryInfo {

    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return Resource.class;
    }

    @Override
    public Class<?> getType() {
        return ValidatorFactory.class;
    }

    @Override
    public boolean isOverrideAllowed() {
        return false;
    }

    @Override
    public Class<? extends ObjectFactory> getObjectFactoryClass() {
        return ValidatorFactoryObjectFactory.class;
    }

}
