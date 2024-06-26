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
package org.apache.camel.generator.openapi;

import io.swagger.v3.oas.models.PathItem;
import org.apache.camel.util.ObjectHelper;

class PathVisitor<T> {

    private final DestinationGenerator destinationGenerator;
    private final CodeEmitter<T> emitter;
    private final OperationFilter filter;
    private final String dtoPackageName;

    PathVisitor(final String basePath, final CodeEmitter<T> emitter, final OperationFilter filter,
                final DestinationGenerator destinationGenerator, final String dtoPackageName) {
        this.emitter = emitter;
        this.filter = filter;
        this.destinationGenerator = destinationGenerator;
        this.dtoPackageName = dtoPackageName;

        if (ObjectHelper.isEmpty(basePath)) {
            emitter.emit("rest");
        } else {
            emitter.emit("rest", basePath);
        }
    }

    void visit(final String path, final PathItem definition) {
        final OperationVisitor<T> restDslOperation
                = new OperationVisitor<>(emitter, filter, path, destinationGenerator, dtoPackageName);
        definition.readOperationsMap().forEach((method, op) -> restDslOperation.visit(method, op, definition));
    }

}
