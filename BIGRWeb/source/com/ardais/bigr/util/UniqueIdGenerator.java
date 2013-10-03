package com.ardais.bigr.util;

/**
 * Generates simple unique id strings.  These simple id strings are relatively small strings with 
 * an alphabetic prefix and increasing numeric suffix.  The ids have the current time embedded in
 * them (via System.currentTimeMillis()) and can thus be considered unique in time.
 * <p>
 * These unique ids are intended to be used for assigning a unique identifier to the id or name 
 * attribute of an HTML element.  If used in this way, a single instance of this class should be 
 * created for the duration of generating a single HTML page, for example near the top of a JSP.
 */
public class UniqueIdGenerator {

  /**
   * The suffix to be used for all generated ids.
   */
  private int _nextNumber = 0;

  /**
   * Creates a new UniqueIdGenerator.
   */
  public UniqueIdGenerator() {
    super();
  }

  /**
   * Returns a string that can be used as a unique identifier.
   * 
   * @return  The unique identifier.
   */
  public String getUniqueId() {
    return "j" + String.valueOf(System.currentTimeMillis()) + "_" + String.valueOf(_nextNumber++);
  }

}
