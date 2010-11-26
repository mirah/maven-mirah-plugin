package org.mirah.maven;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.mirah.MirahCommand;

/**
 * Goal which touches a timestamp file.
 *
 * @goal compile
 *
 * @phase compile
 */
public class MirahCompilerMojo extends AbstractMojo {
    /**
     * Classes destination directory
     * @parameter expression="${project.build.outputDirectory}"
     */
    private File outputDirectory;
    /**
     * Classes source directory
     * @parameter expression="src/main/mirah"
     */
    private File sourceDirectory;
    /**
     * Whether produce bytecode or java source
     * @parameter bytecode, default true
     */
    private boolean bytecode = true;
    /**
     * Show log
     * @parameter verbose, default false
     */
    private boolean verbose;

    public void execute() throws MojoExecutionException {
        if ( !outputDirectory.exists() ) {
            outputDirectory.mkdirs();
        }

        List arguments = new ArrayList();
        if (!bytecode)
          arguments.add("--java");
        if (verbose)
          arguments.add("-V");

        arguments.add("-d");
        arguments.add(outputDirectory.getAbsolutePath());

        arguments.add(sourceDirectory.getAbsolutePath());

        try {
            MirahCommand.compile(arguments);
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }
}
