package com.ardais.bigr.library.helpers;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * These are the valid contexts that identify sets of implicit inventory item query filters.
 * 
 * Each context is represented by a singleton object.  You can safely compare two 
 * instances using the "==" operator.  This class guarantees that there will never be two
 * object instances that have the same name.  
 */
public final class ImplicitFilterContext implements Serializable {
  // NOTE: If this class is ever made non-final and we create subclasses, make sure
  // to change the visibility of the readResolve method appropriately.  See the JavaDoc
  // description of readResolve in the Serializable interface for details.

  private static Map _values = new HashMap(19);

  /**
   * This specifies a default set of implicit filters that are appropriate in most situations.
   */
  public final static ImplicitFilterContext DEFAULT = getOrCreateInstance("Default");
  /**
   * This specifies a default set of implicit filters that are appropriate for the sample selection
   * transaction.
   */
  public final static ImplicitFilterContext SAMPLE_SELECTION = getOrCreateInstance("TxSampSel");
  /**
   * This specifies a default set of implicit filters that are appropriate for the request samples
   * transaction.
   */
  public final static ImplicitFilterContext REQUEST_SAMPLES = getOrCreateInstance("TxRequestSamples");
  /**
   * This specifies a set of implicit filters to use when querying sublists of samples that
   * are known to already be on a specific user's hold list.
   */
  public final static ImplicitFilterContext ON_HOLD = getOrCreateInstance("On Hold");

  private final String _name;

  /**
   * Prevent external instantiation.  We only want the limited set of values that
   * we create here and expose through constants to ever exist.
   */
  private ImplicitFilterContext(String name) {
    _name = name;
  }

  /**
   * Get the singleton instance with the specified name.  If there is no such instance,
   * throw a runtime exception.  See also {@link #getInstance(String, boolean)} which returns
   * null when the instance does not exist.
   *  
   * @param type The name.
   * @return The instance.
   */
  public static ImplicitFilterContext getInstance(String name) {
    return getInstance(name, true);
  }

  /**
   * Get the singleton instance with the specified name.  If there is no such instance,
   * throw a runtime exception if the <code>exceptionIfNotFound</code> parameter is true,
   * otherwise return null.  See also {@link #getInstance(String)} which always throws an
   * exception when the instance does not exist.
   *  
   * @param type The name.
   * @param exceptionIfNotFound If true, throw a runtime exception when there is no instance
   *    with the specified name.
   * @return The instance.
   */
  public static ImplicitFilterContext getInstance(String name, boolean exceptionIfNotFound) {
    synchronized (_values) {
      ImplicitFilterContext result = (ImplicitFilterContext) _values.get(name);
      if (result == null && exceptionIfNotFound) {
        throw new IllegalArgumentException("Invalid name: " + name);
      }
      return result;
    }
  }

  /**
   * Get an instance with the specified name, creating one if it doesn't already exist.
   * We support a fixed set of valid values, so we don't expose the method that allows
   * new instances to be created.
   * 
   * @param type The name.
   * @return The instance.
   */
  private static ImplicitFilterContext getOrCreateInstance(String name) {
    synchronized (_values) {
      ImplicitFilterContext result = (ImplicitFilterContext) _values.get(name);
      if (result == null) {
        result = new ImplicitFilterContext(name);
        _values.put(name, result);
      }
      return result;
    }
  }

  /**
   * Return the string representation of the value.  This return value is the value that
   * should be stored in database fields that represent these values.
   */
  public String toString() {
    return _name;
  }

  /**
   * Implement the serialization readResolve method so that deserialized instances resolve
   * to the correct canonical object instance.
   */
  private Object readResolve() throws ObjectStreamException {
    return getOrCreateInstance(_name);
  }

  // Prevent subclasses from overriding Object.equals and Object.hashCode.
  // Currently this class is final so that won't happen, these are just here for future safety.
  //
  public final boolean equals(Object that) {
    return super.equals(that);
  }
  public final int hashCode() {
    return super.hashCode();
  }

}
