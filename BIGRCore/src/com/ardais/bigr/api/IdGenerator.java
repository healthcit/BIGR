package com.ardais.bigr.api;

/**
 * A static generator of simple unique ids.  The ids generated will be unique for the life
 * of this class within the scope of its class loader, but callers must not assume that
 * the ids will be globally unique beyond that.  For example, this is good for generating
 * element ids on a web page but not useful for generating ids that would be persisted
 * to the database, or that would be used in a context where they would might be mixed
 * with ids from other generators or other instances of this class (for example, across
 * client/server boundaries).
 */
public class IdGenerator {

  // This counter is used as part of generating unique ids.
  // This should only be used by genUniqueId.
  //
  private static long _counter = 0;

  // This is used to synchronized access to _counter.
  // This should only be used by genUniqueId.
  //
  private static Object _counterLock = new Object();

  /**
   * Prevent instantiation.
   */
  private IdGenerator() {
    super();
  }

  // This method is used to get and increment _counter in
  // a synchronized, thread-safe way.
  //
  private static long getNextCounter() {
    synchronized (_counterLock) {
      _counter++;
      return _counter;
    }
  }

  /**
   * A static generator of simple unique ids.  The ids generated will be unique for the life
   * of this class within the scope of its class loader, but callers must not assume that
   * the ids will be globally unique beyond that.  For example, this is good for generating
   * element ids on a web page but not useful for generating ids that would be persisted
   * to the database, or that would be used in a context where they would might be mixed
   * with ids from other generators or other instances of this class (for example, across
   * client/server boundaries).  The generated part of the id will include only characters
   * that are commonly permitted in identifiers, but is not guaranteed to begin with a letter.
   * 
   * @param prefix An optional prefix for the id.
  */
  public static String genUniqueId(String prefix) {
    long n = getNextCounter();
    if (ApiFunctions.isEmpty(prefix)) {
      return String.valueOf(n);
    }
    else {
      return prefix + String.valueOf(n);
    }
  }

}
