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
package org.apache.camel.component.printer;

import org.apache.camel.Category;
import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.EndpointServiceLocation;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.support.DefaultEndpoint;

/**
 * Send print jobs to printers.
 *
 * Obviously the payload has to be a formatted piece of payload in order for the component to appropriately print it.
 * The objective is to be able to direct specific payloads as jobs to a line printer in a camel flow.
 *
 * The functionality allows for the payload to be printed on a default printer, named local, remote or wirelessly linked
 * printer using the javax printing API under the covers.
 */
@UriEndpoint(firstVersion = "2.1.0", scheme = "lpr", title = "Printer", syntax = "lpr:hostname:port/printername",
             producerOnly = true, category = { Category.DOCUMENT }, headersClass = PrinterEndpoint.class)
public class PrinterEndpoint extends DefaultEndpoint implements EndpointServiceLocation {

    @Metadata(label = "producer", description = "The name of the job", javaType = "String")
    public static final String JOB_NAME = "PrinterJobName";

    @UriParam
    private PrinterConfiguration config;

    public PrinterEndpoint() {
    }

    public PrinterEndpoint(String endpointUri, Component component, PrinterConfiguration config) {
        super(endpointUri, component);
        this.config = config;
    }

    @Override
    public String getServiceUrl() {
        return config.getHostname() + ":" + config.getPort();
    }

    @Override
    public String getServiceProtocol() {
        return "printer";
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        throw new UnsupportedOperationException("You cannot create a consumer for a Printer endpoint");
    }

    @Override
    public Producer createProducer() throws Exception {
        return new PrinterProducer(this, config);
    }

    public PrinterConfiguration getConfig() {
        return config;
    }

    public void setConfig(PrinterConfiguration config) {
        this.config = config;
    }

}
