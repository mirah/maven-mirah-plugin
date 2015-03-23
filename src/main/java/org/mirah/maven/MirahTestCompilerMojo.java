package org.mirah.maven;

import org.apache.maven.plugin.CompilationFailureException;
import org.apache.maven.plugin.TestCompilerMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;

import org.mirah.tool.Mirahc;

/**
 * Compiles Mirah source files
 *
 * @phase test-compile
 * @goal testCompile
 * @requiresDependencyResolution test
 * @threadSafe
 */
public class MirahTestCompilerMojo extends AbstractMirahMojo {

    /**
     * Set this to 'true' to bypass unit tests entirely.
     * Its use is NOT RECOMMENDED, but quite convenient on occasion.
     *
     * @parameter expression="${maven.test.skip}"
     */
    private boolean skip;

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
    private String outputDirectory;
    /**
     * Classes source directory
     * @parameter expression="${basedir}/src/test/mirah"
     */
    private String sourceDirectory;

    /**
     * Show log
     * @parameter verbose, default false
     */
    private boolean verbose;

    /**
     * Show log
     *
     * @parameter newClosures, default false
     */
    protected boolean newClosures;

    protected List<String> getClassPathElements() {
        return classpathElements;
    }

    public void execute() throws MojoExecutionException, CompilationFailureException {
        if (skip) {
            getLog().info("skiping mirah tests compilation");
        } else {
            executeMirahCompiler(outputDirectory, sourceDirectory, verbose, newClosures, true);
        }
    }

}
