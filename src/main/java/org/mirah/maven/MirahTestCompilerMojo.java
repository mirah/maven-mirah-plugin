package org.mirah.maven;

import org.apache.maven.plugin.CompilationFailureException;
import org.apache.maven.plugin.TestCompilerMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;

import org.mirah.MirahCommand;

/**
 * Compiles Mirah source files
 *
 * @extendsPlugin compiler
 * @goal testCompile
 * @phase testCompile
 * @threadSafe
 * @requiresDependencyResolution test
 */
public class MirahTestCompilerMojo extends TestCompilerMojo {
    /**
     * Project classpath.
     *
     * @parameter default-value="${project.testClasspathElements}"
     * @required
     * @readonly
     */
    private List<String> classpathElements;
    /**
     * The source directories containing the sources to be compiled.
     *
     * @parameter default-value="${project.compileSourceRoots}"
     * @required
     * @readonly
     */
    private List<String> compileSourceRoots;
    /**
     * Classes destination directory
     * @parameter expression="${project.build.testOutputDirectory}"
     */
    private File outputDirectory;
    /**
     * Classes source directory
     * @parameter expression="src/test/mirah"
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

    public void execute() throws MojoExecutionException, CompilationFailureException {
       if (bytecode) {
          super.execute();
          executeMirahCompiler(outputDirectory.getAbsolutePath());
       } else {
          String javaSourceRoot = compileSourceRoots.get(0);
          executeMirahCompiler(javaSourceRoot);
          super.execute();
       }
    }

    private void executeMirahCompiler(String output) throws MojoExecutionException {
       if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        List arguments = new ArrayList();
        if (!bytecode)
          arguments.add("--java");
        if (verbose)
          arguments.add("-V");

        arguments.add("-d");
        arguments.add(output);

        try {
            arguments.add("-c");
            arguments.add(StringUtils.join(classpathElements.iterator(), File.pathSeparator));

            arguments.add(sourceDirectory.getAbsolutePath());

            MirahCommand.compile(arguments);
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }
}
