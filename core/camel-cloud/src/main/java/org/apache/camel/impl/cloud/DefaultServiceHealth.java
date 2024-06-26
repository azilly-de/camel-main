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
package org.apache.camel.impl.cloud;

import java.util.Map;

import org.apache.camel.cloud.ServiceHealth;
import org.apache.camel.util.CollectionHelper;

/**
 * @deprecated since 4.7
 */
@Deprecated(since = "4.7")
public class DefaultServiceHealth implements ServiceHealth {
    private final boolean healthy;
    private final Map<String, String> meta;

    public DefaultServiceHealth() {
        this(true, null);
    }

    public DefaultServiceHealth(boolean healthy) {
        this(healthy, null);
    }

    public DefaultServiceHealth(Map<String, String> meta) {
        this(true, meta);
    }

    public DefaultServiceHealth(boolean healthy, Map<String, String> meta) {
        this.healthy = healthy;
        this.meta = CollectionHelper.unmodifiableMap(meta);
    }

    @Override
    public boolean isHealthy() {
        return this.healthy;
    }

    @Override
    public Map<String, String> getMetadata() {
        return this.meta;
    }
}
