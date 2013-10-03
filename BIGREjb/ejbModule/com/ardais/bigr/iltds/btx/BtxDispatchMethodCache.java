package com.ardais.bigr.iltds.btx;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Maps fully-qualified method names to the Method object that represents
 * the BTX method to call to perform a business transaction given a
 * btxDetails object of a particular type.  The lookup key for the mapping
 * includes the class of the btxDetails parameter and the fully-qualified
 * method name to look up, including package, class and method name.
 * 
 * <p>The BTX business-transaction framework reflectively dispatches to the
 * session bean methods that implement the business transactions.  To make
 * the reflective dispatching faster, we use this class to maintain a static
 * cache of the Method objects that are used to invoke the transaction methods.
 */
final class BtxDispatchMethodCache {

    /**
     * Maps fully-qualified method names to the Method object that represents
     * the BTX method to call to perform a business transaction given a
     * btxDetails object of a particular type.  The lookup key for the mapping
     * includes the class of the btxDetails parameter and the fully-qualified
     * method name to look up, including package, class and method name.
     */
    private static Map _dispatchMethodCache = new HashMap(499);

    /**
     * This class should not be instantiated, all of its methods and data
     * are static, so we make the constructor private.
     */
    private BtxDispatchMethodCache() {
        super();
    }

    /**
     * Return the key to use in cache lookups for a method with the
     * specified name on the specified class that takes the specified
     * BTXDetails subclass as a parameter.
     * 
     * @param clazz the class that contains the method
     * @param methodName the method name
     * @param btxDetailsClass the class of the BTXDetails instance that will
     *     be passed to the method as a parameter.
     * @return the method cache key
     */
    public static String getMethodCacheKey(
        Class clazz,
        String methodName,
        Class btxDetailsClass) {
            
        return clazz.getName()
            + "#"
            + methodName
            + "#"
            + btxDetailsClass.getName();
    }

    /**
     * Return the cached Method object corresponding to the specified method
     * key.  Use {@link #getMethodCacheKey(Class, String, Class)} to get a
     * properly-formatted cache key.
     * 
     * @param methodCacheKey the cache key indicating which method to return
     * @return the Method object, or null if no such method is currently
     *     cached.
     */
    public static synchronized Method get(String methodCacheKey) {
        return (Method) _dispatchMethodCache.get(methodCacheKey);
    }

    /**
     * Cached a Method object, associating it with the specified method
     * key.  Use {@link #getMethodCacheKey(Class, String, Class)} to get a
     * properly-formatted cache key.
     * 
     * @param methodCacheKey the cache key to associated with the method
     * @param method the Method object to associate with the specified key
     */
    public static synchronized void put(String methodCacheKey, Method method) {
        _dispatchMethodCache.put(methodCacheKey, method);
    }

}
