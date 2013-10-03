package com.ardais.bigr.library.generator;

import org.apache.velocity.VelocityContext;

import com.ardais.bigr.generator.BigrCodeGenerator;

/**
 * This is the abstract base class for BigrLibrary code generators.  Concrete classes based
 * on this must implement the {@link #doGenerate()} method.
 */
public abstract class BigrLibraryCodeGenerator extends BigrCodeGenerator {

  /**
   * The BigrLibraryMetaData used by BigrLibraryCirCodeGenerator subclasses to generate
   * their artifacts.
   */
  private BigrLibraryMetaData _metaData = null;

  /**
   * Creates a new BigrLibraryCodeGenerator.
   */
  public BigrLibraryCodeGenerator() {
    super();
  }

  /**
   * Creates a new BigrLibraryCodeGenerator with the specified meta data and root output
   * directory path.
   * 
   * @param metaData the metaData object containing the info used in generation.
   * @param allowNullMetaData if false, throw a runtime exception if metaData is null.
   * @param rootOutputDirectoryPath the full path of the root output directory under which
   *     all of the generated code will be placed.
   * @param allowNullRootOutputDirectoryPath if false, throw a runtime exception if
   *     the root output directory path is null or an empty string.
   */
  public BigrLibraryCodeGenerator(
    BigrLibraryMetaData metaData,
    boolean allowNullMetaData,
    String rootOutputDirectoryPath,
    boolean allowNullRootOutputDirectoryPath) {

    super(rootOutputDirectoryPath, allowNullRootOutputDirectoryPath);

    if (!allowNullMetaData && metaData == null) {
      throw new IllegalArgumentException("metaData parameter must not be null");
    }
    setMetaData(metaData);
  }

  /**
   * Create and configure a default VelocityContext.  Subclasses may use this method to create
   * a new VelocityContext object that is initialized with some broadly-used properties, and then
   * extend this default object with additional subclass-specific properties.
   * <p>
   * The default context contains these keys:
   * <ul>
   * <li>metaData: the BIGR library meta data returned by {@link #getMetaData()}.  This may be null
   *     for some generators.</li>
   * </ul>
   * 
   * @return the VelocityContext.
   */
  protected VelocityContext getVelocityContext() {
    VelocityContext context = new VelocityContext();
    context.put("metaData", getMetaData());
    return context;
  }

  /**
   * Returns the list of macro libraries used by generators that subclass this generator.  This 
   * includes:
   * <p>
   * <ul>
   * <li>GlobalMacros.vm</li>
   * </ul>
   * 
   * @return  A comma-separated list of Velocity macro libraries.
   */
  protected String getMacroLibraries() {
    StringBuffer buf = new StringBuffer(128);
    buf.append("com/ardais/bigr/cir/generator/templates/GlobalMacros.vm");
    return buf.toString(); 
  }

  /**
   * The path prefix to use by default as the root for locating templates.  Callers may assume
   * that this string ends with a '/' character.
   * 
   * @return The default template path prefix.
   */
  protected String getDefaultTemplatePathRoot() {
    return "com/ardais/bigr/library/generator/templates/";
  }

  /**
   * Returns the BigrLibraryMetaData.  Many generator subclasses use this so we define
   * it here for convenience, but subclasses are not required to set this.
   * 
   * @return the BigrLibraryMetaData
   */
  public BigrLibraryMetaData getMetaData() {
    return _metaData;
  }

  /**
   * Sets the metaData.  Many generator subclasses use this so we define
   * it here for convenience, but subclasses are not required to set this.
   * 
   * @param metaData the BigrLibraryMetaData to set
   */
  public void setMetaData(BigrLibraryMetaData metaData) {
    _metaData = metaData;
  }

}
