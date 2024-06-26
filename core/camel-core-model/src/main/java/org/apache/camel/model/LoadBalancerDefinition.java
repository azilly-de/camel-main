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
package org.apache.camel.model;

import jakarta.xml.bind.annotation.XmlTransient;

/**
 * Balances message processing among a number of nodes
 */
public abstract class LoadBalancerDefinition extends IdentifiedType implements CopyableDefinition<LoadBalancerDefinition> {

    @XmlTransient
    private String loadBalancerTypeName;

    public LoadBalancerDefinition() {
    }

    protected LoadBalancerDefinition(LoadBalancerDefinition source) {
        this.loadBalancerTypeName = source.loadBalancerTypeName;
    }

    protected LoadBalancerDefinition(String loadBalancerTypeName) {
        this.loadBalancerTypeName = loadBalancerTypeName;
    }

    /**
     * Maximum number of outputs, as some load balancers only support 1 processor
     */
    public int getMaximumNumberOfOutputs() {
        return Integer.MAX_VALUE;
    }

    public String getLoadBalancerTypeName() {
        return loadBalancerTypeName;
    }

    @Override
    public String toString() {
        return loadBalancerTypeName;
    }
}
