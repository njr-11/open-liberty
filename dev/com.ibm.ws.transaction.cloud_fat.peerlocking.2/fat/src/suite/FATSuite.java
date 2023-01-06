/*******************************************************************************
 * Copyright (c) 2017, 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package suite;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ibm.ws.transaction.fat.util.TxTestContainerSuite;

import componenttest.rules.repeater.FeatureReplacementAction;
import componenttest.rules.repeater.RepeatTests;
import tests.DualServerPeerLockingTest;
import tests.DualServerPeerLockingTest2;

@RunWith(Suite.class)
@SuiteClasses({
                DualServerPeerLockingTest2.class,
})
public class FATSuite extends TxTestContainerSuite {
    @ClassRule
    public static RepeatTests r = RepeatTests.withoutModification()
                    .andWith(FeatureReplacementAction.EE8_FEATURES().fullFATOnly().forServers(DualServerPeerLockingTest.serverNames))
                    .andWith(FeatureReplacementAction.EE9_FEATURES().fullFATOnly().forServers(DualServerPeerLockingTest.serverNames))
                    .andWith(FeatureReplacementAction.EE10_FEATURES().fullFATOnly().forServers(DualServerPeerLockingTest.serverNames));
}