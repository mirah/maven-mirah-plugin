package org.mirah.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;

import org.mirah.MirahCommand;

/**
 * Goal which compiles Mirah source files
 *
 * @goal compile
 * @phase compile
 * @requiresDependencyResolution
 */
public class MirahCompilerMojo extends AbstractMojo {
    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;
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

        try {
            List<String> compileClasspath = project.getCompileClasspathElements();
            compileClasspath.addAll(project.getRuntimeClasspathElements());
            String classpath = StringUtils.join(compileClasspath.iterator(), File.pathSeparator);

            arguments.add("-c");
            arguments.add(classpath);

            arguments.add(sourceDirectory.getAbsolutePath());

            MirahCommand.compile(arguments);
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }
}
