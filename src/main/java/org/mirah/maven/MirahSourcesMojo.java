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
    public void execute() throws MojoExecutionException, CompilationFailureException {
        String javaSourceRoot = compileSourceRoots.get(0);
        executeMirahCompiler(javaSourceRoot, false);
    }
}
