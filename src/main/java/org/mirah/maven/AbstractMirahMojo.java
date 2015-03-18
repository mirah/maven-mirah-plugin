package org.mirah.maven;

import org.apache.maven.plugin.CompilerMojo;
import org.apache.maven.plugin.CompilationFailureException;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;

import java.util.List;
import java.util.ArrayList;

import org.codehaus.plexus.util.StringUtils;

import org.mirah.tool.Mirahc;

public abstract class AbstractMirahMojo extends CompilerMojo {
    /**
     * Project classpath.
     *
     * @parameter default-value="${project.compileClasspathElements}"
     * @required
     * @readonly
     */
    protected List<String> classpathElements;
    /**
     * The source directories containing the sources to be compiled.
     *
     * @parameter default-value="${project.compileSourceRoots}"
     * @required
     * @readonly
     */
    protected List<String> compileSourceRoots;
    /**
     * Classes destination directory
     * @parameter expression="${project.build.outputDirectory}"
     */
    protected String outputDirectory;
    /**
     * Classes source directory
     * @parameter expression="${basedir}/src/main/mirah"
     */
    protected String sourceDirectory;
    /**
     * Show log
     * @parameter verbose, default false
     */
    protected boolean verbose;

	protected List<String> getClassPathElements(){
		return classpathElements;
	}
    protected void executeMirahCompiler(String output, String sourceDirectory, boolean verbose, boolean bytecode) throws MojoExecutionException {
        File d = new File(output);
        if (!d.exists()) {
            d.mkdirs();
        }

        List<String> arguments = new ArrayList<String>();
        if (!bytecode)
            arguments.add("--java");
        if (verbose)
            arguments.add("-V");

        arguments.add("-d");
        arguments.add(output);

        /* do I really need this? */
        arguments.add("-cp");
        arguments.add(StringUtils.join(getClassPathElements().iterator(), File.pathSeparator));


	    File file = new File(sourceDirectory);
	    if(!file.exists()){
		    getLog().info("Source directory: " + file.getAbsolutePath() + " does not exists or not accessible. Skip mirahc.");
	    } else {
		    arguments.add(sourceDirectory);
		    mojoCompile(arguments);
	    }

    }

	public static void mojoCompile(List<String> arguments) throws MojoExecutionException {
		try {
			Mirahc mirahc = new Mirahc();
			int result = mirahc.compile(arguments.toArray(new String[arguments.size()]));
			if(result != 0) throw new MojoExecutionException("Compilation failed with arguments: " + arguments);
		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
}
