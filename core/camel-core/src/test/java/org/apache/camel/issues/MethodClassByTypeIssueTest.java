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
package org.apache.camel.issues;

import org.apache.camel.ContextTestSupport;
import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class MethodClassByTypeIssueTest extends ContextTestSupport {

    private final Object methodClass = MyTransformBean.class;

    @Test
    public void testMethodClassByTypeAIssue() throws Exception {
        getMockEndpoint("mock:a").expectedBodiesReceived("Hello World");

        template.sendBody("direct:a", "World");

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testMethodClassByTypeBIssue() throws Exception {
        getMockEndpoint("mock:b").expectedBodiesReceived("Hello World");

        template.sendBody("direct:b", "World");

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:a").transform().method(MyTransformBean.class, "transformMe").to("mock:a");

                from("direct:b").transform().method(methodClass, "transformMe").to("mock:b");
            }
        };
    }
}
