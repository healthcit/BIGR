package com.ardais.bigr.library.generator;

import java.io.File;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.generator.ComposedCodeGenerator;

/**
 * This is the main code generator for BIGR Library artifacts.  It invokes all of the individual
 * BIGR Library code generators.
 */
public class BigrLibraryGenerateAll extends ComposedCodeGenerator {

  /**
   * Create the main code generator for the Bigr Library project.
   * 
   * @param rootOutputDirectoryPath the full path of the root output directory under which
   *     all of the generated code will be placed.
   */
  public BigrLibraryGenerateAll(String rootOutputDirectoryPath) {
    super(rootOutputDirectoryPath, false);
  }

  /**
   * Generate the BIGR Library code.  This throws any exceptions rather than logging them or
   * printing them to the console since it is meant to be invoked through
   * {@link MainRunner#main(String[])} rather than being invoked directly.  MainRunner takes
   * care of exception handling in a standard way.
   */
  public static void main(String[] args) throws Exception {
    // Initialize the application.  MainRunner does this, but it doesn't hurt to do it here
    // too in case this gets called directly.  If it has already been called, it just won't
    // do anything.
    //
    ApiFunctions.initialize();
    
    if (args == null || args.length != 1) {
      throw new IllegalArgumentException("Usage: BigrLibraryGenerateAll rootOutputDir");
    }
    
    BigrLibraryGenerateAll generator = new BigrLibraryGenerateAll(args[0]);
    generator.generate();
    
    System.out.println("\nBigrLibraryGenerateAll code generation completed successfully.");
  }

  /**
   * Invokes all of the individual Bigr Library code generators.
   */
  protected void doGenerate() throws Exception {
    BigrLibraryMetaDataParser digester = new BigrLibraryMetaDataParser();
    BigrLibraryMetaData metaData = digester.performParse();
    String rootOutDir = getRootOutputDirectoryPath();

    addGenerator(new BigrLibraryGenerateDbConstants(metaData, rootOutDir));
    addGenerator(new BigrLibraryGenerateDbAliases(metaData, rootOutDir));
    addGenerator(new BigrLibraryGenerateTableMetaData(metaData, rootOutDir));
    addGenerator(new BigrLibraryGenerateColumnMetaData(metaData, rootOutDir));
    addGenerator(new BigrLibraryGenerateFilterMetaData(metaData, rootOutDir));

    super.doGenerate();
  }
}
