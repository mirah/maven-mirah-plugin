package org.mirah.maven;

import org.apache.maven.plugin.CompilationFailureException;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Compiles Mirah source files
 *
 * @goal generate-sources
 * @phase generate-sources
 * @threadSafe
 * @requiresDependencyResolution compile
 */
public class MirahSourcesMojo extends AbstractMirahMojo {
    /**
     * @parameter default-value="${project.build.directory}/generated-sources/mirah"
     * @required
     * @readonly
     */
    private String generatedSrc;

    public void execute() throws MojoExecutionException, CompilationFailureException {
        compileSourceRoots.add(generatedSrc);
        executeMirahCompiler(generatedSrc, outputDirectory, verbose, false);
    }
}
