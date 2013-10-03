package com.ardais.bigr.util;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiLogger;

/**
 * @author dfeldman
 *
 * Manages the global list of available columns and associated classes.  Front-end or other
 * external components can update this list, since we don't know here what columns are
 * available.
 * 
 * responsibilities:  Registers instances, builds unique keys, enforces uniqueness, creates 
 * instances via introspection.
 * 
 */
public class ClassLookupManager {

  private Map _classes = new HashMap();
  private Map _keyLookup= new HashMap();
  
  /**
   * Subclasses are Singletons, do not directly instantiate
   */
  protected ClassLookupManager() {
  }
  
  /**
   * When the class registering is registering itself with a pre-defined key
   * because that key has meaning to the back end, or another component needs to know about this
   * key, use this method, rather than register(key).  For example if a column is managed by 
   * back-end business logic, the back end will explicitly define the key for it and the column
   * that renders that value will register itself as the handler for that data.
   * 
   * @param specialKey  a predefined key
   * @param clazz  the class that handles the key
   */
  public void register(String specialKey, Class clazz) {
    if (_classes.containsKey(specialKey))
      throw new ApiException("Duplicate class key found: " + specialKey);
    if (_keyLookup.containsKey(clazz))
      throw new ApiException("Duplicate value entered for key: " + specialKey + ".  value="+clazz);
      
    _classes.put(specialKey, clazz);
    _keyLookup.put(clazz, specialKey);
  }

  /**
   * Method get.
   * @param key  - the unique identifier to get the class for
   * @return Class corresponding to the key
   */
  protected Class get(String key) throws ClassNotFoundException {
    Class clazz = (Class) _classes.get(key);
    if (clazz==null)      
      throw new ClassNotFoundException("No class entry for key: " + key);
    return clazz;
  }
  
  /**
   * Method getKey.
   * @param clazz  - the subclass to return the key for
   * @return String - the unique identifier representing the class type.
   */
  public String getKey(Class clazz) throws ClassNotFoundException {
    String key = (String) _keyLookup.get(clazz);
    if (key==null) {
      throw new ClassNotFoundException("No key for internal type: " + clazz);
    }
    return key;
  }
  
  /**
   * Method newInstance.
   * @param key   the unique identifier for the column subclass to create
   * @param params   input params needed to create the column instance
   * @param types   if some params are null, the types can be overridden.  null types is ok
   * and will cause types to default to type of params
   * @return Object  the new column
   */
  public Object getInstance(String key, Object[] params, Class[] types) {
    if (types==null) // only use types if it is in there as Explicit Override of params
      types = getTypes(params); 
    try {
      Class clazz = get(key);
      if (clazz == null) {
        return null;
      }
      else {
        Constructor con = clazz.getConstructor(types);
        return con.newInstance(params);
      }
    } catch (Exception e) {
      throw new ApiException("Could not instantiate object for " + key, e);
    }
  }

  protected Class[] getTypes(Object[] args) {
    Class[] types = new Class[args.length];
    for (int i=0; i<args.length; i++) {
      types[i] = args[i].getClass();
    }
    return types; 
  }
  
}
