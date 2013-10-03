package com.gulfstreambio.kc.generator;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.generator.BigrCodeGenerator;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.util.KcNamingUtils;

/**
 * Generates the KnowledgeCapture database schema. 
 */
public class DatabaseSchemaGenerator extends BigrCodeGenerator {

  private static final String CURRENT_DET_VERSION = "19";
  
  /**
   * Creates the database schema generator.
   * 
   * @param rootOutputDirectoryPath the full path of the root output directory under which
   *     the generated database schema file will be placed.
   */
  public DatabaseSchemaGenerator(String rootOutputDirectoryPath) {
    super(rootOutputDirectoryPath, false);
  }

  protected void doGenerate() throws Exception {
    System.out.println("\nGenerating database schema...");

    VelocityEngine engine = getSharedVelocityEngine();
    VelocityContext context = getVelocityContext();
    Writer writer = null;
    try {
      writer = openVelocityWriterSql("kc_generated_schema_personal.sql");
      context.put("environment", "personal");

      engine.mergeTemplate(
        getDefaultTemplatePathRoot() + "DatabaseSchemaTemplate.vm",
        context,
        writer);
    }
    finally {
      ApiFunctions.close(writer);
    }

    try {
      writer = openVelocityWriterSql("kc_generated_schema.sql");
      context.put("environment", "shared");

      engine.mergeTemplate(
        getDefaultTemplatePathRoot() + "DatabaseSchemaTemplate.vm",
        context,
        writer);
    }
    finally {
      ApiFunctions.close(writer);
    }

    try {
      writer = openVelocityWriterSql("kc_generated_views.sql");

      engine.mergeTemplate(
        getDefaultTemplatePathRoot() + "DatabaseViewsTemplate.vm",
        context,
        writer);
    }
    finally {
      ApiFunctions.close(writer);
    }

    System.out.println("Completed generating database schema.");
  }

  protected VelocityContext getVelocityContext() {
    VelocityContext context = super.getVelocityContext();
    context.put("gboss", GbossFactory.getInstance());
    context.put("det", DetService.SINGLETON.getDataElementTaxonomy());
    context.put("KcNamingUtils", KcNamingUtils.SINGLETON);
    context.put("detversion", CURRENT_DET_VERSION);
    return context;
  }

  private Writer openVelocityWriterSql(String fileName)
    throws IOException {
    File dir = new File(getRootOutputDirectoryPath());
    return openVelocityWriter(dir, fileName);
  }

  /**
   * The path prefix to use by default as the root for locating templates.  Callers may assume
   * that this string ends with a '/' character.
   * 
   * @return The default template path prefix.
   */
  protected String getDefaultTemplatePathRoot() {
    return "com/gulfstreambio/kc/generator/";
  }
  
  public static void main(String[] args) throws Exception {
    // Initialize the application.  MainRunner does this, but it doesn't hurt to do it here
    // too in case this gets called directly.  If it has already been called, it just won't
    // do anything.
    //
    ApiFunctions.initialize();

    if (args == null || args.length != 1) {
      throw new IllegalArgumentException("Usage: DatabaseSchemaGenerator rootOutputDir");
    }

    DatabaseSchemaGenerator generator = new DatabaseSchemaGenerator(args[0]);
    generator.generate();
  }
}
