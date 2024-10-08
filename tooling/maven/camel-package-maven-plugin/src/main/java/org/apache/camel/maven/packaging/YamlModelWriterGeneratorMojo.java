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
package org.apache.camel.maven.packaging;

import java.io.File;
import java.nio.file.Path;

import javax.inject.Inject;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.build.BuildContext;

/**
 * Generate Model lightweight YAML Writer source code.
 */
@Mojo(name = "generate-yaml-writer", threadSafe = true, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME,
      defaultPhase = LifecyclePhase.PROCESS_CLASSES)
public class YamlModelWriterGeneratorMojo extends ModelWriterGeneratorMojo {

    public static final String WRITER_PACKAGE = "org.apache.camel.yaml.out";

    @Parameter(defaultValue = "${camel-generate-yaml-writer}")
    protected boolean generateYamlWriter;

    @Inject
    public YamlModelWriterGeneratorMojo(MavenProjectHelper projectHelper, BuildContext buildContext) {
        super(projectHelper, buildContext);
    }

    @Override
    public void execute(MavenProject project) throws MojoFailureException, MojoExecutionException {
        sourcesOutputDir = new File(project.getBasedir(), "src/generated/java");
        generateYamlWriter = Boolean.parseBoolean(project.getProperties().getProperty("camel-generate-yaml-writer", "false"));
        super.execute(project);
    }

    @Override
    public void execute() throws MojoExecutionException {
        if (!generateYamlWriter) {
            return;
        }
        Path javaDir = sourcesOutputDir.toPath();
        String parser = generateWriter();
        updateResource(javaDir, (getWriterPackage() + ".ModelWriter").replace('.', '/') + ".java", parser);
    }

    @Override
    String getWriterPackage() {
        return WRITER_PACKAGE;
    }

}
