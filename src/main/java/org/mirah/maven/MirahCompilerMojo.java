package org.mirah.maven;

import org.apache.maven.plugin.CompilationFailureException;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Compiles Mirah source files
 *
 * @goal compile
 * @phase compile
 * @threadSafe
 * @requiresDependencyResolution compile
 */
public class MirahCompilerMojo extends AbstractMirahMojo {
   public void execute() throws MojoExecutionException, CompilationFailureException {
       executeMirahCompiler(outputDirectory, true);
   }
}
