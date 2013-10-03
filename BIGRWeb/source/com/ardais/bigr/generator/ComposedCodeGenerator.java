package com.ardais.bigr.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A code generator that runs a list of other code generators in order.
 * To use it, create an instance of this class and call {@link #addGenerator(BigrCodeGenerator)
 * addGenerator} for each of the generators you want to run.  Finally, call the
 * {@link #generate()} method.
 */
public class ComposedCodeGenerator extends BigrCodeGenerator {

  /**
   * The list of sub-generators.  See {@link #addGenerator(BigrCodeGenerator) addGenerator}.
   */
  private List _generators = new ArrayList();

  /**
   * Creates a new ComposedCodeGenerator.
   */
  public ComposedCodeGenerator() {
    super();
  }

  /**
   * Creates a new ComposedCodeGenerator with a root output directory path.
   * 
   * @param rootOutputDirectoryPath the full path of the root output directory under which
   *     all of the generated code will be placed.
   * @param allowNullRootOutputDirectoryPath if false, throw a runtime exception if
   *     the root output directory path is null or an empty string.
   */
  public ComposedCodeGenerator(
    String rootOutputDirectoryPath,
    boolean allowNullRootOutputDirectoryPath) {

    super(rootOutputDirectoryPath, allowNullRootOutputDirectoryPath);
  }

  /**
   * Append a generator to the list of generators that this class will run.
   * 
   * @param generator the generator to add
   */
  public void addGenerator(BigrCodeGenerator generator) {
    _generators.add(generator);
  }

  /**
   * Generate code by running each of the added sub-generators in order.
   * @see com.ardais.bigr.cir.generator.BigrCodeGenerator#doGenerate()
   */
  protected void doGenerate() throws Exception {
    Iterator iter = _generators.iterator();
    while (iter.hasNext()) {
      BigrCodeGenerator generator = (BigrCodeGenerator) iter.next();
      generator.generate();
    }
  }

}
