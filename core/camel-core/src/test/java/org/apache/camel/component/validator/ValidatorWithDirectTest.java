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
package org.apache.camel.component.validator;

import org.apache.camel.ContextTestSupport;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorWithDirectTest extends ContextTestSupport {

    final String wrongBody = "<user2><name>Federico</name><surname>Mariani</surname></user2>";

    @Test
    public void testValidatorWithDirect() throws Exception {
        MockEndpoint valid = resolveMandatoryEndpoint("mock:valid", MockEndpoint.class);
        MockEndpoint fail = resolveMandatoryEndpoint("mock:fail", MockEndpoint.class);
        fail.expectedMessageCount(1);
        valid.expectedMessageCount(0);

        assertThrows(Exception.class, () -> template.sendBody("direct:start", wrongBody),
                "Should throw exception");
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {

                onException(Exception.class)
                        .to("mock:fail");

                validator()
                        .type("xml:schemaValidator")
                        .withUri("validator:org/apache/camel/impl/validate.xsd?failOnNullBody=false");

                from("direct:start")
                        .inputTypeWithValidate("xml:schemaValidator")
                        .to("mock:valid");
            }
        };
    }
}
