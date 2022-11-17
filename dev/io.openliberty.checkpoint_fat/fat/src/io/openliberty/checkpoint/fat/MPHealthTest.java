/*******************************************************************************
 * Copyright (c) 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package io.openliberty.checkpoint.fat;

import static io.openliberty.checkpoint.fat.FATSuite.getTestMethod;
import static io.openliberty.checkpoint.fat.FATSuite.getTestMethodNameOnly;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ibm.websphere.simplicity.ShrinkHelper;
import com.ibm.websphere.simplicity.config.ServerConfiguration;
import com.ibm.websphere.simplicity.config.Variable;
import com.ibm.websphere.simplicity.log.Log;

import componenttest.annotation.Server;
import componenttest.annotation.SkipIfCheckpointNotSupported;
import componenttest.custom.junit.runner.FATRunner;
import componenttest.custom.junit.runner.Mode.TestMode;
import componenttest.rules.repeater.MicroProfileActions;
import componenttest.rules.repeater.RepeatTests;
import componenttest.topology.impl.LibertyServer;
import componenttest.topology.utils.FATServletClient;
import componenttest.topology.utils.HttpUtils;
import io.openliberty.checkpoint.spi.CheckpointPhase;

@RunWith(FATRunner.class)
@SkipIfCheckpointNotSupported
public class MPHealthTest extends FATServletClient {

    @Server("checkpointMPHealth")
    public static LibertyServer server;

    private static final String APP_NAME = "mphealth";

    private final String HEALTH_ENDPOINT = "/health";
    private final String READY_ENDPOINT = "/health/ready";
    private final String STARTED_ENDPOINT = "/health/started";
    private static final String MESSAGE_LOG = "logs/messages.log";

    private final int SUCCESS_RESPONSE_CODE = 200;
    private final int FAILED_RESPONSE_CODE = 503;

    public TestMethod testMethod;

    @ClassRule
    public static RepeatTests repeatTest = MicroProfileActions.repeat("checkpointMPHealth", TestMode.FULL,
                                                                      MicroProfileActions.MP41, // first test in LITE mode
                                                                      MicroProfileActions.MP50, MicroProfileActions.MP60); // rest are FULL mode

    @BeforeClass
    public static void copyAppToDropins() throws Exception {
        ShrinkHelper.defaultApp(server, APP_NAME, APP_NAME);
        FATSuite.copyAppsAppToDropins(server, APP_NAME);
    }

    @Before
    public void setUp() throws Exception {
        testMethod = getTestMethod(TestMethod.class, testName);
        server.setCheckpoint(CheckpointPhase.DEPLOYMENT, true,
                             server -> {
                                 configureBeforeRestore();
                             });
        server.setConsoleLogName(getTestMethod(TestMethod.class, testName) + ".log");
        server.startServer(true, false); // Do not validate apps since we have a delayed startup.
    }

    @Test
    public void testDefaultHealthChecks() throws Exception {
        String name = getTestMethodNameOnly(testName);

        // /health/ready - By default it returns 503
        Log.info(getClass(), name, "Testing the /health/ready endpoint, before application has started.");
        HttpURLConnection conReady = HttpUtils.getHttpConnectionWithAnyResponseCode(server, READY_ENDPOINT);
        assertEquals("The Response Code was not 503 for the following endpoint: " + conReady.getURL().toString(), FAILED_RESPONSE_CODE, conReady.getResponseCode());

        // /health/started - By default it returns 503
        Log.info(getClass(), name, "Testing the /health/started endpoint, before application has started.");
        HttpURLConnection conStarted = HttpUtils.getHttpConnectionWithAnyResponseCode(server, STARTED_ENDPOINT);
        assertEquals("The Response Code was not 503 for the following endpoint: " + conStarted.getURL().toString(), FAILED_RESPONSE_CODE, conReady.getResponseCode());

        // /health - By default it returns 503
        Log.info(getClass(), name, "Testing the /health endpoint, before application has started.");
        HttpURLConnection conHealth = HttpUtils.getHttpConnectionWithAnyResponseCode(server, HEALTH_ENDPOINT);
        assertEquals("The Response Code was not 503 for the following endpoint: " + conHealth.getURL().toString(), FAILED_RESPONSE_CODE, conHealth.getResponseCode());

        JsonObject jsonResponse = getJSONPayload(conHealth);
        JsonArray checks = (JsonArray) jsonResponse.get("checks");
        assertEquals("The status of the health check was not DOWN.", jsonResponse.getString("status"), "DOWN");

        List<String> lines = server.findStringsInFileInLibertyServerRoot("CWMMH0053W:", MESSAGE_LOG);
        assertEquals("The CWMMH0053W warning did not appear in messages.log", 1, lines.size());

        Thread.sleep(30000);
        lines = server.findStringsInFileInLibertyServerRoot("CWWKZ0001I:", MESSAGE_LOG);
        assertEquals("The CWWKZ0001I Application started message did not appear in messages.log", 1, lines.size());

        // /health- It will return 503 since /health/ready is down
        Log.info(getClass(), name, "Testing the /health endpoint, after application has started.");
        conHealth = HttpUtils.getHttpConnectionWithAnyResponseCode(server, HEALTH_ENDPOINT);
        assertEquals("The Response Code was not 503 for the following endpoint: " + conHealth.getURL().toString(), FAILED_RESPONSE_CODE, conHealth.getResponseCode());

        jsonResponse = getJSONPayload(conHealth);
        checks = (JsonArray) jsonResponse.get("checks");
        assertEquals("The status of the health check was not DOWN.", jsonResponse.getString("status"), "DOWN");
    }

    @Test
    public void testUpdateHealthChecks() throws Exception {
        String name = getTestMethodNameOnly(testName);
        // /health/ready - Returns 200 since "mp.health.default.readiness.empty.response" is set to "UP"
        Log.info(getClass(), name, "Testing the /health/ready endpoint, before application has started.");
        HttpURLConnection conReady = HttpUtils.getHttpConnectionWithAnyResponseCode(server, READY_ENDPOINT);
        assertEquals("The Response Code was not 200 for the following endpoint: " + conReady.getURL().toString(), SUCCESS_RESPONSE_CODE, conReady.getResponseCode());

        // /health/started - Returns 200 since "mp.health.default.startup.empty.response" is set to "UP"
        Log.info(getClass(), name, "Testing the /health/started endpoint, before application has started.");
        HttpURLConnection conStarted = HttpUtils.getHttpConnectionWithAnyResponseCode(server, STARTED_ENDPOINT);
        assertEquals("The Response Code was not 200 for the following endpoint: " + conStarted.getURL().toString(), SUCCESS_RESPONSE_CODE, conReady.getResponseCode());

        // /health
        Log.info(getClass(), name, "Testing the /health endpoint, before application has started.");
        HttpURLConnection conHealth = HttpUtils.getHttpConnectionWithAnyResponseCode(server, HEALTH_ENDPOINT);
        assertEquals("The Response Code was not 200 for the following endpoint: " + conHealth.getURL().toString(), SUCCESS_RESPONSE_CODE, conHealth.getResponseCode());

        JsonObject jsonResponse = getJSONPayload(conHealth);
        JsonArray checks = (JsonArray) jsonResponse.get("checks");
        assertEquals("The status of the health check was not UP.", jsonResponse.getString("status"), "UP");

        Thread.sleep(30000);
        List<String> lines = server.findStringsInFileInLibertyServerRoot("CWWKZ0001I:", MESSAGE_LOG);
        assertEquals("The CWWKZ0001I Application started message did not appear in messages.log", 1, lines.size());

        // /health- It will return 503 since /health/ready is down
        Log.info(getClass(), name, "Testing the /health endpoint, after application has started.");
        conHealth = HttpUtils.getHttpConnectionWithAnyResponseCode(server, HEALTH_ENDPOINT);
        assertEquals("The Response Code was not 503 for the following endpoint: " + conHealth.getURL().toString(), FAILED_RESPONSE_CODE, conHealth.getResponseCode());

        jsonResponse = getJSONPayload(conHealth);
        checks = (JsonArray) jsonResponse.get("checks");
        assertEquals("The status of the health check was not DOWN.", jsonResponse.getString("status"), "DOWN");
    }

    public JsonObject getJSONPayload(HttpURLConnection con) throws Exception {
        BufferedReader br = HttpUtils.getResponseBody(con, "UTF-8");
        Json.createReader(br);
        JsonObject jsonResponse = Json.createReader(br).readObject();
        br.close();

        Log.info(getClass(), "getJSONPayload", "Response: jsonResponse= " + jsonResponse.toString());
        assertNotNull("The contents of the health endpoint must not be null.", jsonResponse.getString("status"));

        return jsonResponse;
    }

    private void configureBeforeRestore() {
        try {
            server.saveServerConfiguration();
            Log.info(getClass(), testName.getMethodName(), "Configuring: " + testMethod);
            switch (testMethod) {
                case testUpdateHealthChecks:
                    Map<String, String> checks = new HashMap<>();
                    checks.put("mp.health.default.readiness.empty.response", "UP");
                    checks.put("mp.health.default.startup.empty.response", "UP");
                    updateVariableConfig(checks);
                    break;
                default:
                    Log.info(getClass(), testName.getMethodName(), "No configuration required: " + testMethod);
                    break;
            }

        } catch (Exception e) {
            throw new AssertionError("Unexpected error configuring test.", e);
        }
    }

    private void updateVariableConfig(Map<String, String> configMap) throws Exception {
        // change config of variable for restore
        ServerConfiguration config = server.getServerConfiguration();
        for (Map.Entry<String, String> entry : configMap.entrySet()) {
            config = removeTestKeyVar(config, entry.getKey());
            config.getVariables().add(new Variable(entry.getKey(), entry.getValue()));
            server.updateServerConfiguration(config);
        }
    }

    private ServerConfiguration removeTestKeyVar(ServerConfiguration config, String key) {
        for (Iterator<Variable> iVars = config.getVariables().iterator(); iVars.hasNext();) {
            Variable var = iVars.next();
            if (var.getName().equals(key)) {
                iVars.remove();
            }
        }
        return config;
    }

    @After
    public void tearDown() throws Exception {
        server.stopServer("CWMMH0052W", "CWMMH0053W");
        server.restoreServerConfiguration();
    }

    static enum TestMethod {
        testDefaultHealthChecks,
        testUpdateHealthChecks,
        unknown
    }
}