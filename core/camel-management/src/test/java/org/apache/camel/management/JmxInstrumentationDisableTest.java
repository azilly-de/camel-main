/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.management;

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A unit test which verifies disabling of JMX instrumentation.
 */
@DisabledOnOs(OS.AIX)
public class JmxInstrumentationDisableTest extends JmxInstrumentationUsingPropertiesTest {

    @Override
    protected boolean useJmx() {
        return false;
    }

    @Override
    protected MBeanServerConnection getMBeanConnection() {
        return ManagementFactory.getPlatformMBeanServer();
    }

    @Override
    @Test
    public void testMBeansRegistered() throws Exception {
        assertDefaultDomain();

        resolveMandatoryEndpoint("mock:end", MockEndpoint.class);

        Set<ObjectName> s = mbsc.queryNames(new ObjectName(domainName + ":type=endpoints,*"), null);
        assertEquals(0, s.size(), "Could not find 0 endpoints: " + s);

        s = mbsc.queryNames(new ObjectName(domainName + ":type=contexts,*"), null);
        assertEquals(0, s.size(), "Could not find 0 context: " + s);

        s = mbsc.queryNames(new ObjectName(domainName + ":type=processors,*"), null);
        assertEquals(0, s.size(), "Could not find 0 processor: " + s);

        s = mbsc.queryNames(new ObjectName(domainName + ":type=routes,*"), null);
        assertEquals(0, s.size(), "Could not find 0 route: " + s);
    }

    @Override
    protected void verifyCounter(MBeanServerConnection beanServer, ObjectName name) throws Exception {
        Set<ObjectName> s = beanServer.queryNames(name, null);
        assertEquals(0, s.size(), "Found mbeans: " + s);
    }
}
