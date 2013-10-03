package com.ardais.bigr.library.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.library.generator.BigrLibraryMetaData;
import com.ardais.bigr.util.SystemNameUtils;

/**
 * Generate the DbAliases file for the Bigr Library application.
 */
public class BigrLibraryGenerateDbAliases extends BigrLibraryCodeGenerator {

  /**
   * Create the DbAliases generator.
   * 
   * @param metaData the BigrLibraryMetaData object containing the info needed for
   * generation.
   * @param rootOutputDirectoryPath the full path of the root output directory under which
   *     all of the generated code will be placed.
   */
  public BigrLibraryGenerateDbAliases(BigrLibraryMetaData metaData, String rootOutputDirectoryPath) {
    super(metaData, false, rootOutputDirectoryPath, false);
  }

  /**
   * @see com.ardais.bigr.cir.generator.BigrLibraryCodeGenerator#doGenerate()
   */
  protected void doGenerate() throws Exception {
    System.out.println("\nGenerating DbAliases...");

    VelocityEngine engine = getSharedVelocityEngine();

    VelocityContext context = getVelocityContext();

    Writer writer = null;
    try {
      writer = openVelocityWriter();

      engine.mergeTemplate(
        getDefaultTemplatePathRoot() + "DbAliasesTemplate.vm",
        context,
        writer);
    }
    finally {
      ApiFunctions.close(writer);
    }
    System.out.println("Completed generating DbAliases.");
  }

  /**
   * Open a writer for the DbAliases file created by this generator.
   * There's only one template for this generator, so we only need one kind of writer.
   * 
   * @return the Writer.
   */
  private Writer openVelocityWriter() throws IOException {
    ApiFunctions.ensureDirectoryPathExists(getRootOutputDirectoryPath());

    File file = new File(getRootOutputDirectoryPath(), "DbAliases.java");

    return new BufferedWriter(new FileWriter(file));
  }

}
