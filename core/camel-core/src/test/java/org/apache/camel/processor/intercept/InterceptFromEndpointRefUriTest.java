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
package org.apache.camel.processor.intercept;

import org.apache.camel.ContextTestSupport;
import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Test;

/**
 * Testing intercept from can intercept when endpoint is an instance
 */
public class InterceptFromEndpointRefUriTest extends ContextTestSupport {

    @Test
    public void testIntercept() throws Exception {
        getMockEndpoint("mock:intercepted").expectedMessageCount(1);
        getMockEndpoint("mock:first").expectedMessageCount(1);
        getMockEndpoint("mock:result").expectedMessageCount(1);

        template.sendBody("direct:start", "Hello World");

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                context.getRegistry().bind("start", context.getEndpoint("direct:start"));
                context.getRegistry().bind("bar", context.getEndpoint("seda:bar"));

                interceptFrom("direct*").to("mock:intercepted");

                from("ref:start").to("mock:first").to("ref:bar");

                from("ref:bar").to("mock:result");
            }
        };
    }

}
