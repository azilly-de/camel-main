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
package org.apache.camel.component.asterisk;

import java.util.Map;

import org.apache.camel.Category;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.EndpointServiceLocation;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;
import org.apache.camel.support.DefaultEndpoint;
import org.apache.camel.util.ObjectHelper;

/**
 * Interact with Asterisk PBX Server (VoIP).
 */
@UriEndpoint(firstVersion = "2.18.0", scheme = "asterisk", title = "Asterisk", syntax = "asterisk:name",
             category = { Category.MOBILE }, headersClass = AsteriskConstants.class)
public class AsteriskEndpoint extends DefaultEndpoint implements EndpointServiceLocation {
    @UriPath(description = "Name of component")
    @Metadata(required = true)
    private String name;

    @UriParam
    @Metadata(required = true)
    private String hostname;

    @UriParam(label = "producer")
    private AsteriskAction action;

    @UriParam(secret = true)
    @Metadata(required = true)
    private String username;

    @UriParam(secret = true)
    @Metadata(required = true)
    private String password;

    public AsteriskEndpoint(String uri, AsteriskComponent component) {
        super(uri, component);
    }

    @Override
    protected void doInit() throws Exception {
        super.doInit();
        // Validate mandatory option
        ObjectHelper.notNull(hostname, "hostname");
        ObjectHelper.notNull(username, "username");
        ObjectHelper.notNull(password, "password");
    }

    @Override
    public Producer createProducer() {
        return new AsteriskProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return new AsteriskConsumer(this, processor);
    }

    @Override
    public String getServiceUrl() {
        return hostname;
    }

    @Override
    public String getServiceProtocol() {
        return "voip";
    }

    @Override
    public Map<String, String> getServiceMetadata() {
        if (username != null) {
            return Map.of("username", username);
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Login username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Login password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public AsteriskAction getAction() {
        return action;
    }

    /**
     * What action to perform such as getting queue status, sip peers or extension state.
     */
    public void setAction(AsteriskAction action) {
        this.action = action;
    }

    public String getHostname() {
        return hostname;
    }

    /**
     * The hostname of the asterisk server
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * Logical name
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
