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
package org.apache.camel.component.etcd3.cloud;

import java.util.List;

import org.apache.camel.cloud.ServiceDefinition;
import org.apache.camel.component.etcd3.Etcd3Configuration;

/**
 * An implementation of a {@link Etcd3ServiceDiscovery} that retrieves the service definitions from etcd at each call.
 */
@Deprecated
public class Etcd3OnDemandServiceDiscovery extends Etcd3ServiceDiscovery {
    public Etcd3OnDemandServiceDiscovery(Etcd3Configuration configuration) {
        super(configuration);
    }

    @Override
    public List<ServiceDefinition> getServices(String name) {
        return findServices(s -> name.equalsIgnoreCase(s.getName())).getServices();
    }
}
