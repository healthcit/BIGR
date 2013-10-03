package com.ardais.bigr.generator.btx;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.BtxConfiguration;
import com.ardais.bigr.generator.BigrCodeGenerator;
import com.ardais.bigr.tld.generator.TldGenerator;
import com.ardais.bigr.tld.generator.TldParser;

/**
 * Generates documentation for all registered BTX transactions. 
 */
public class BtxDocGenerator extends BigrCodeGenerator {

  private List _transactions;
  
  /**
   * Creates a new <code>BtxDocGenerator</code>.
   */
  public BtxDocGenerator() {
    super();
  }

  /**
   * Creates a new <code>BtxDocGenerator</code> with the specified root output directory path.
   * 
   * @param rootOutputDirectoryPath the full path of the root output directory under which
   *     all of the generated documentation will be placed.
   */
  public BtxDocGenerator(String rootOutputDirectoryPath) {
    super(rootOutputDirectoryPath, false);
  }

  protected void doGenerate() throws Exception {
    System.out.println("Generating documentation for BTX...");

    // Get the registered BTX transaction types and sort them.
    SortedSet txTypes = new TreeSet(BtxConfiguration.getTransactionTypes());
    List transactions = new ArrayList();
    Iterator i = txTypes.iterator();
    while (i.hasNext()) {
      String txType = (String) i.next();
      transactions.add(BtxConfiguration.getTransaction(txType));
    }
    setTransactions(transactions);
    
    VelocityEngine engine = getSharedVelocityEngine();
    VelocityContext context = getVelocityContext();
    Writer writer = null;
    try {
      writer = openVelocityWriter();

      engine.mergeTemplate(
        getDefaultTemplatePathRoot() + "BtxDocTemplate.vm",
        context,
        writer);
    }
    finally {
      ApiFunctions.close(writer);
    }
    System.out.println("Completed generating documentation for BTX.");
  }

  /**
   * Create and configure a default VelocityContext.  The returned context contains these keys:
   * <ul>
   * <li>txs: the list of registered BTX transaction types sorted by transaction type
   * </ul>
   * 
   * @return the VelocityContext.
   */
  protected VelocityContext getVelocityContext() {
    VelocityContext context = super.getVelocityContext();
    context.put("txs", getTransactions());
    context.put("helper", new BtxDocGeneratorHelper());
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
   * Opens a writer for the btx.html file that is created by this generator.
   * 
   * @return the Writer.
   */
  private Writer openVelocityWriter() throws IOException {
    File dir = new File(getRootOutputDirectoryPath());
    return openVelocityWriter(dir, "btx.html");
  }

  private List getTransactions() {
    return _transactions;
  }

  private void setTransactions(List transactions) {
    _transactions = transactions;
  }

  /**
   * Generates the documentation for BTX transactions. This throws any exceptions rather than 
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

    if (args == null || args.length != 1) {
      throw new IllegalArgumentException("Usage: BtxDocGenerator rootOutputDir");
    }

    BtxDocGenerator generator = new BtxDocGenerator(args[0]);
    generator.generate();
  }
}
