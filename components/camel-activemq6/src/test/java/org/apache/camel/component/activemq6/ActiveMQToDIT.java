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
package org.apache.camel.component.activemq6;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.infra.core.annotations.ContextFixture;
import org.apache.camel.test.infra.core.annotations.RouteFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActiveMQToDIT extends ActiveMQITSupport {
    private ProducerTemplate template;

    @Test
    public void testToD() throws Exception {
        contextExtension.getMockEndpoint("mock:bar").expectedBodiesReceived("Hello bar");
        contextExtension.getMockEndpoint("mock:beer").expectedBodiesReceived("Hello beer");

        template.sendBodyAndHeader("direct:start", "Hello bar", "where", "bar");
        template.sendBodyAndHeader("direct:start", "Hello beer", "where", "beer");

        MockEndpoint.assertIsSatisfied(contextExtension.getContext());
    }

    @BeforeEach
    void setupTemplate() {
        template = contextExtension.getProducerTemplate();
    }

    @ContextFixture
    public void configureContext(CamelContext camelContext) {
        camelContext.addComponent("activemq6", activeMQComponent(service.defaultEndpoint()));
    }

    @RouteFixture
    public void createRouteBuilder(CamelContext context) throws Exception {
        context.addRoutes(createRouteBuilder());
    }

    private RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                // route message dynamic using toD
                from("direct:start").toD("activemq6:queue:${header.where}");

                from("activemq6:queue:bar").to("mock:bar");
                from("activemq6:queue:beer").to("mock:beer");
            }
        };
    }
}
