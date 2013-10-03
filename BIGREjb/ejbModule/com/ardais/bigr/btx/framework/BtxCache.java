package com.ardais.bigr.btx.framework;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Maps fully-qualified method names to the Method object that represents
 * the BTX method to call to play a particular role (e.g. perform, validate)
 * for a business transaction given a btxDetails object of a particular type.
 * The lookup key for the mapping includes the class of the btxDetails parameter
 * and the fully-qualified method name to look up, including package, class and method name,
 * as well as an indicator of the method's role.
 * 
 * <p>The BTX business-transaction framework reflectively dispatches to the
 * transaction methods that play various roles in implementing the business transactions.
 * To make the reflective dispatching faster, we use this class to maintain a static
 * cache of the Method objects that are used to invoke the transaction methods.
 */
final class BtxCache {
  // This class has package visibility.

  /**
   * Maps fully-qualified method names to the Method object that represents
   * the BTX method to call to play a particular role for a business transaction given
   * a btxDetails object of a particular type.  The lookup key for the mapping
   * includes the class of the btxDetails parameter and the fully-qualified
   * method name to look up, including package, class and method name,
   * as well as an indicator of the method's role.
   */
  private static Map _methodCache = new HashMap(499);

  /**
   * This class should not be instantiated, all of its methods and data
   * are static, so we make the constructor private.
   */
  private BtxCache() {
    super();
  }

  /**
   * Return the key to use in cache lookups for a BTX transaction methods that play the
   * specified role in the specified transaction that takes a parameter of the specified
   * BTXDetails subclass.
   * 
   * @param txType the transaction metadata for the BTX transaction of interest.
   * @param btxDetailsClass the class of the BTXDetails instance that will
   *     be passed to the method as a parameter.
   * @param methodRole the role the method plays in the BTX framework.  This must be one of
   *   the METHOD_ROLE_* contants defined in the BtxTransactionPerformerBase class.
   * 
   * @return the method cache key
   */
  public static String getMethodCacheKey(
    BtxTransactionMetaData txMeta,
    Class btxDetailsClass,
    int methodRole) {

    String className = txMeta.getPerformerClass().getName();

    String methodName = null;
    if (methodRole == BtxTransactionPerformerBase.METHOD_ROLE_PERFORM) {
      methodName = txMeta.getPerformerMethodName();
    }
    else if (methodRole == BtxTransactionPerformerBase.METHOD_ROLE_VALIDATE) {
      methodName = txMeta.getValidatorMethodName();
    }
    else {
      throw new IllegalArgumentException("Unknown methodRole: " + methodRole);
    }

    return className + '#' + methodName + '#' + btxDetailsClass.getName() + '#' + methodRole;
  }

  /**
   * Return the cached Method object corresponding to the specified method
   * key.  Use {@link #getMethodCacheKey(BtxTransactionMetaData, Class, int)} to get a
   * properly-formatted cache key.
   * 
   * @param methodCacheKey the cache key indicating which method to return.
   * 
   * @return the Method object, or null if no such method is currently
   *     cached.
   */
  public static Method getMethod(String methodCacheKey) {
    synchronized (_methodCache) {
      return (Method) _methodCache.get(methodCacheKey);
    }
  }

  /**
   * Cache a Method object, associating it with the specified method
   * key.  Use {@link #getMethodCacheKey(BtxTransactionMetaData, Class, int)} to get a
   * properly-formatted cache key.
   * 
   * @param methodCacheKey the cache key to associated with the method.
   * @param method the Method object to associate with the specified key.
   */
  public static void putMethod(String methodCacheKey, Method method) {
    synchronized (_methodCache) {
      _methodCache.put(methodCacheKey, method);
    }
  }

}
