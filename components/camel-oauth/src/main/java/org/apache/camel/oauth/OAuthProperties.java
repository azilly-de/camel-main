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
package org.apache.camel.oauth;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.camel.CamelContext;

public final class OAuthProperties {

    public static Optional<String> getProperty(CamelContext ctx, String key) {
        return ctx.getPropertiesComponent().resolveProperty(key);
    }

    public static String getRequiredProperty(CamelContext ctx, String key) {
        Optional<String> optval = getProperty(ctx, key);
        return optval.orElseThrow(() -> new NoSuchElementException(key));
    }
}
