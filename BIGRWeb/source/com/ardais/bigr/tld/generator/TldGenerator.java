package com.ardais.bigr.tld.generator;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.generator.BigrCodeGenerator;

/**
 * Generates taglib documentation from a TLD file. 
 */
public class TldGenerator extends BigrCodeGenerator {

  private String _tldFileName;
  private JspTaglib _taglib;

  /**
   * Creates a new TldGenerator.
   */
  public TldGenerator() {
    super();
  }

  /**
   * Creates a new TldGenerator with the specified TLD file and root output directory path.
   * 
   * @param tldFileName  the full path of the TLD file
   * @param rootOutputDirectoryPath the full path of the root output directory under which
   *     all of the generated code will be placed.
   * @param allowNullRootOutputDirectoryPath if false, throw a runtime exception if
   *     the root output directory path is null or an empty string.
   */
  public TldGenerator(
    String tldFileName,
    String rootOutputDirectoryPath) {

    super(rootOutputDirectoryPath, false);
    if (ApiFunctions.isEmpty(tldFileName)) {
      throw new IllegalArgumentException("tldFileName parameter must not be empty");
    }
    setTldFileName(tldFileName);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.generator.BigrCodeGenerator#doGenerate()
   */
  protected void doGenerate() throws Exception {
    String tldFileName = getTldFileName();
    System.out.println("Generating documentation for " + tldFileName + "...");

    // Parse the TLD and save it.
    TldParser digester = new TldParser(tldFileName);
    setTaglib(digester.performParse());

    VelocityEngine engine = getSharedVelocityEngine();
    VelocityContext context = getVelocityContext();
    Writer writer = null;
    try {
      writer = openVelocityWriter();

      engine.mergeTemplate(
        getDefaultTemplatePathRoot() + "TaglibDocTemplate.vm",
        context,
        writer);
    }
    finally {
      ApiFunctions.close(writer);
    }
    System.out.println("Completed generating documentation for " + tldFileName + ".");
  }

  /**
   * Create and configure a default VelocityContext.  The returned context contains these keys:
   * <ul>
   * <li>taglib: the parsed taglib returned by {@link #getTaglib()}.
   * </ul>
   * 
   * @return the VelocityContext.
   */
  protected VelocityContext getVelocityContext() {
    VelocityContext context = super.getVelocityContext();
    context.put("taglib", getTaglib());
    return context;
  }

  /**
   * The path prefix to use by default as the root for locating templates.  Callers may assume
   * that this string ends with a '/' character.
   * 
   * @return The default template path prefix.
   */
  protected String getDefaultTemplatePathRoot() {
    return "com/ardais/bigr/generator/templates/";
  }

  /**
   * Opens a writer for the bigrtld.html file that is created by this generator.
   * 
   * @return the Writer.
   */
  private Writer openVelocityWriter() throws IOException {
    File dir = new File(getRootOutputDirectoryPath());
    return openVelocityWriter(dir, "bigrtld.html");
  }

  protected JspTaglib getTaglib() {
    return _taglib;
  }

  protected void setTaglib(JspTaglib taglib) {
    _taglib = taglib;
  }

  /**
   * Returns the file name of the TLD file, including the full path to the file.
   *   
   * @return  The filename. 
   */
  public String getTldFileName() {
    return _tldFileName;
  }

  /**
   * Sets the file name of the TLD file, including the full path to the file.
   *   
   * @param  filename  the filename 
   */
  public void setTldFileName(String filename) {
    _tldFileName = filename;
  }

  /**
   * Generates the documentation for a taglib. This throws any exceptions rather than 
   * logging them or printing them to the console since it is meant to be invoked through
   * {@link MainRunner#main(String[])} rather than being invoked directly.  MainRunner takes
   * care of exception handling in a standard way.
   */
  public static void main(String[] args) throws Exception {
    // Initialize the application.  MainRunner does this, but it doesn't hurt to do it here
    // too in case this gets called directly.  If it has already been called, it just won't
    // do anything.
    //
    ApiFunctions.initialize();

    if (args == null || args.length != 2) {
      throw new IllegalArgumentException("Usage: TaglibGenerator taglibFileName rootOutputDir");
    }

    TldGenerator generator = new TldGenerator(args[0], args[1]);
    generator.generate();
  }

}
