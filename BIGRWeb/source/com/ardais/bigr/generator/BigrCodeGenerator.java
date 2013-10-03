package com.ardais.bigr.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;

/**
 * This is the abstract base class for BIGR code generators that use Velocity.  Concrete classes 
 * based on this must implement the {@link #doGenerate()} method.  This class provides a method
 * to get an initialized Velocity engine, and provides other general purpose utility methods. 
 */
public abstract class BigrCodeGenerator {

  /**
   * Many BigrCodeGenerator subclasses may use the root output directory path so
   * we define it here for convenience, but it is not required.
   */
  private String _rootOutputDirectoryPath = null;

  /**
   * Holds the shared VelocityEngine instance that is returned by
   * {@link #getSharedVelocityEngine()}.
   */
  private static VelocityEngine _sharedVelocityEngine = null;
  private static Object _sharedVelocityEngineLock = new Object();

  /**
   * Creates a new BigrCodeGenerator.
   */
  public BigrCodeGenerator() {
    super();
  }

  /**
   * Creates a new BigrCodeGenerator with a root output directory path.
   * 
   * @param rootOutputDirectoryPath the full path of the root output directory under which
   *     all of the generated code will be placed.
   * @param allowNullRootOutputDirectoryPath if false, throw a runtime exception if
   *     the root output directory path is null or an empty string.
   */
  public BigrCodeGenerator(
    String rootOutputDirectoryPath,
    boolean allowNullRootOutputDirectoryPath) {

    this();

    if (!allowNullRootOutputDirectoryPath && ApiFunctions.isEmpty(rootOutputDirectoryPath)) {
      throw new IllegalArgumentException("rootOutputDirectoryPath parameter must not be empty");
    }

    setRootOutputDirectoryPath(rootOutputDirectoryPath);
  }

  /**
   * Generates an artifact.  This calls the protected abstract method {@link #doGenerate()} to do
   * the subclass-specific generation work.
   */
  public void generate() {
    try {
      doGenerate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * Generates an artifact.  Subclasses must implement this to perform generation.
   * Subclasses will typically have subclass-specific means of configuring the generator
   * prior to calling this (for example, input file names and output locations may be
   * specified via constructor arguments and/or property setters).
   * <p>
   * Implementation of this method shouldn't handle exceptions except to free resources
   * that aren't freed automatically (such as open files and database connections).  The
   * {@link #generate()} method that calls this will take care of exception handling in
   * a standard way.
   */
  protected abstract void doGenerate() throws Exception;

  /**
   * Return a {@link VelocityEngine} configured with default properties (not necessarily the
   * same as the defaults that Velocity ships with).  This returns the same initialized object
   * each time it is called.  Callers must not  modify the returned engine in any way, as the
   * could have bad effects on other callers of this method.
   */
  protected VelocityEngine getSharedVelocityEngine() {
    // TODO: Provide a way for subclasses to easily override some of the default engine settings
    //       without affecting the shared engine instance.

    synchronized (_sharedVelocityEngineLock) {
      if (_sharedVelocityEngine == null) {
        // Create, configure and initialize a new engine

        try {
          Properties p = new Properties();
          //tell the Velocity Engine to use log4j
          p.setProperty(
            VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,
            "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
          //tell Velocity to use the BIGR Velocity log4j logger
          p.setProperty("runtime.log.logsystem.log4j.category", ApiLogger.BIGR_LOGNAME_VELOCITY);
          //tell Velocity to load resources (e.g. template files) from the classpath
          p.setProperty(VelocityEngine.RESOURCE_LOADER, "class");
          p.setProperty("class.resource.loader.description", "Velocity Classpath Resource Loader");
          p.setProperty(
            "class.resource.loader.class",
            "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
          //tell Velocity to log where it finds resources when they are loaded
          p.setProperty(VelocityEngine.RESOURCE_MANAGER_LOGWHENFOUND, "true");
          //tell Velocity that where the shared macro libraries are
          String libraries = getMacroLibraries();
          if (!ApiFunctions.isEmpty(libraries)) {
            p.setProperty(VelocityEngine.VM_LIBRARY, libraries);
          }
          //tell Velocity that inline macros have local scope
          p.setProperty(VelocityEngine.VM_PERM_INLINE_LOCAL, "true");
          //tell Velocity that reference access (get/set) within a Velocimacro 
          //is of local scope (i.e. once the call to the macro is finished
          //the context is unchanged)
          p.setProperty(VelocityEngine.VM_CONTEXT_LOCALSCOPE, "true");

          //initialize the VelocityEngine
          VelocityEngine engine = new VelocityEngine();
          engine.init(p);
          _sharedVelocityEngine = new ImmutableVelocityEngine(engine);
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }
      }

      return _sharedVelocityEngine;
    }
  }

  /**
   * Create a default uninitialized VelocityContext.  Subclasses should call this method to create
   * a new VelocityContext object and then add objects to the context as necessary.
   * 
   * @return the VelocityContext.
   */
  protected VelocityContext getVelocityContext() {
    return new VelocityContext();
  }

  /**
   * Returns a new writer for the specified file in the specified directory, creating the 
   * directories in the specified directory path if necessary.
   * 
   * @param  dir  the File representing the directory
   * @param  filename  the name of the file
   * @return  The Writer.
   */
  protected Writer openVelocityWriter(File dir, String filename) throws IOException {
    ApiFunctions.ensureDirectoryPathExists(dir);
    File file = new File(dir, filename);
    return new BufferedWriter(new FileWriter(file));
  }

  /**
   * The list of macro libraries to set into the Velocity engine when it is created.  The
   * implementation in this base class returns an empty string.  Subclasses should override this
   * method to return libraries that they use.  
   * 
   * @return  A string containing a comma-separated list of Velocity macro libraries.
   */
  protected String getMacroLibraries() {
    return ApiFunctions.EMPTY_STRING;
  }

  /**
   * The path prefix to use by default as the root for locating templates.  The implementation
   * in this base class returns an empty string.  Subclasses should override this to provide the
   * proper root and ensure that this string ends with a '/' character so that callers can assume 
   * this.
   * 
   * @return The default template path prefix.
   */
  protected String getDefaultTemplatePathRoot() {
    return ApiFunctions.EMPTY_STRING;
  }

  /**
   * Returns the rootOutputDirectoryPath.  Many generator subclasses use this so we define
   * it here for convenience, but subclasses are not required to set this.
   * 
   * @return the root directory under which all generated output is placed.
   */
  public String getRootOutputDirectoryPath() {
    return _rootOutputDirectoryPath;
  }

  /**
   * Sets the rootOutputDirectoryPath.  Many generator subclasses use this so we define
   * it here for convenience, but subclasses are not required to set this.
   * 
   * @param rootOutputDirectoryPath the root directory under which all generated output is placed.
   */
  public void setRootOutputDirectoryPath(String rootOutputDirectoryPath) {
    _rootOutputDirectoryPath = rootOutputDirectoryPath;
  }

}
