package com.ardais.bigr.iltds.helpers;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * These are the valid BIGR request types.  These represent the values that
 * are stored in the REQUEST_TYPE column in the ILTDS_REQUEST
 * table (and possibly other places).
 * 
 * Each type is represented by a singleton object.  You can safely compare two type
 * instances using the "==" operator.  This class guarantees that there will never be two
 * object instances that have the same type name.  
 */
public final class RequestType implements Serializable {
  // NOTE: If this class is ever made non-final and we create subclasses, make sure
  // to change the visibility of the readResolve method appropriately.  See the JavaDoc
  // description of readResolve in the Serializable interface for details.

  private static Map _types = new HashMap(19);

  public final static RequestType RESEARCH = getOrCreateInstance("Research");
  public final static RequestType TRANSFER = getOrCreateInstance("Transfer");

  private final String _type;

  /**
   * Prevent external instantiation.  We only want the limited set of types that
   * we create here and expose through type constants to ever exist.
   */
  private RequestType(String type) {
    _type = type;
  }

  /**
   * Get the singleton type instance with the specified name.  If there is no such instance,
   * throw a runtime exception.  See also {@link #getInstance(String, boolean)} which returns
   * null when the instance does not exist.
   *  
   * @param type The type name.
   * @return The type instance.
   */
  public static RequestType getInstance(String type) {
    return getInstance(type, true);
  }

  /**
   * Get the singleton type instance with the specified name.  If there is no such instance,
   * throw a runtime exception if the <code>exceptionIfNotFound</code> parameter is true,
   * otherwise return null.  See also {@link #getInstance(String)} which always throws an
   * exception when the instance does not exist.
   *  
   * @param type The type name.
   * @param exceptionIfNotFound If true, throw a runtime exception when there is no type
   *    with the specified name.
   * @return The type instance.
   */
  public static RequestType getInstance(String type, boolean exceptionIfNotFound) {
    synchronized (_types) {
      RequestType result = (RequestType) _types.get(type);
      if (result == null && exceptionIfNotFound) {
        throw new IllegalArgumentException("Invalid type: " + type);
      }
      return result;
    }
  }

  /**
   * Get an instance with the specified name, creating one if it doesn't already exist.
   * We support a fixed set of valid types, so we don't expose the method that allows
   * new instances to be created.
   * 
   * @param type The type name.
   * @return The type instance.
   */
  private static RequestType getOrCreateInstance(String type) {
    synchronized (_types) {
      RequestType result = (RequestType) _types.get(type);
      if (result == null) {
        result = new RequestType(type);
        _types.put(type, result);
      }
      return result;
    }
  }

  /**
   * Return the string representation of the type.  This return value is the value that
   * should be stored in database fields that represent these types.
   */
  public String toString() {
    return _type;
  }
  
  /**
   * Implement the serialization readResolve method so that deserialized instances resolve
   * to the correct canonical object instance.
   */
  private Object readResolve() throws ObjectStreamException {
    return getOrCreateInstance(_type);
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
