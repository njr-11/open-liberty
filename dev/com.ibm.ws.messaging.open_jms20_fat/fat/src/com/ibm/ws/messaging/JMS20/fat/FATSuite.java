/*******************************************************************************
 * Copyright (c) 2013, 2020 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.messaging.JMS20.fat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ibm.ws.messaging.JMS20.fat.ContextInject.JMSContextInjectTest;
import com.ibm.ws.messaging.JMS20.fat.DCFTest.JMSDefaultConnFactoryVariationTest;
import com.ibm.ws.messaging.JMS20.fat.DCFTest.JMSDefaultConnectionFactoryTest;
import com.ibm.ws.messaging.JMS20.fat.DurableUnshared.DurableUnsharedTest;
import com.ibm.ws.messaging.JMS20.fat.JMSConsumerTest.JMSConsumerTest_118076;
import com.ibm.ws.messaging.JMS20.fat.JMSConsumerTest.JMSConsumerTest_118077;
import com.ibm.ws.messaging.JMS20.fat.JMSContextTest.JMSEjbJarXmlMdbTest;
import com.ibm.ws.messaging.JMS20.fat.JMSContextTest.JMSRedeliveryTest_120846;
import com.ibm.ws.messaging.JMS20.fat.JMSProducerTest.JMSProducerTest_118071;
import com.ibm.ws.messaging.JMS20.fat.JMSProducerTest.JMSProducerTest_118073;
import com.ibm.ws.messaging.JMS20.fat.JMSProducerTest.JMSProducer_Test118073;
import com.ibm.ws.messaging.JMS20.fat.JmsMBeanTest.JmsMBeanTest;
import com.ibm.ws.messaging.JMS20.fat.SharedSubscription.SharedSubscriptionTest_129623;
import com.ibm.ws.messaging.JMS20.fat.SharedSubscription.SharedSubscriptionTest_129626;
import com.ibm.ws.messaging.JMS20.fat.SharedSubscription.SharedSubscriptionWithMsgSelTest_129623;
import com.ibm.ws.messaging.JMS20.fat.SharedSubscription.SharedSubscriptionWithMsgSelTest_129626;

@RunWith(Suite.class)
@SuiteClasses({
            // DummyTest.class,
        LiteBucketSet1Test.class,
        LiteBucketSet2Test.class,
            // JmsMBeanTest.class,

        JMSProducerTest_118071.class, //full
        JMSProducerTest_118073.class, //full
            // SharedSubscriptionTest_129623.class,

            // JMSConsumerTest_118077.class, //full
            // JMSConsumerTest_118076.class, //full
            // JMSRedeliveryTest_120846.class,

            // SharedSubscriptionWithMsgSelTest_129623.class,
            // SharedSubscriptionWithMsgSelTest_129626.class, //full 2nd
            // SharedSubscriptionTest_129626.class, //full 2nd
            // JMSProducer_Test118073.class, //full // Uses JMSContext servers

            // DurableUnsharedTest.class,
            // JMSContextInjectTest.class, //full

            // JMSDefaultConnectionFactoryTest.class,
            // JMSEjbJarXmlMdbTest.class,
            // JMSDefaultConnFactoryVariationTest.class //full 2nd
})
public class FATSuite {
    // EMPTY
}
