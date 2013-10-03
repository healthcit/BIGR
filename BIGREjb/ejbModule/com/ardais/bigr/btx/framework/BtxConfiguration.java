package com.ardais.bigr.btx.framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;

/**
 * This is a static class that provides access to the application's BTX configuration.
 * For example, this class provides descriptions of all of the BTX transactions that
 * are involved in the application.  The first time this class is used, the BTX
 * configuration information is read in from the btxConfig.xml XML file that is
 * in the {@link ApiFunctions ApiFunctions.CLASSPATH_RESOURCES_PREFIX} folder.
 */
public class BtxConfiguration {
  /**
   * A singleton instance of this class to allow it to be used by reflection-based
   * code such as the Jakarta Digester that can't operate on static classes.
   */
  static BtxConfiguration SINGLETON = new BtxConfiguration();

  /**
   * Maps BTX transaction type strings to BtxTransactionMetaData instances describing those
   * transaction types.
   */
  private static Map _transactions = new HashMap(499);

  private static boolean _immutable = false;

  private static boolean _isInitialized = false;

  private static Properties _properties = null;

  /**
   * Constructor for BtxConfiguration.  This is a static class and can't be instantiated.
   */
  private BtxConfiguration() {
    super();
  }

  /**
   * The first time this is called it initializes the static BTX configuration.
   * Subsequent calls to nothing.
   */
  public static synchronized void initialize() {
    if (_isInitialized)
      return;

    // parseConfiguration calls back into this class, so we need to set _isInitialized
    // to true before calling it even though initialization isn't complete yet.  Therefore,
    // the parseConfiguration call should come last so that any other initialization that
    // the parsing callbacks might rely on will already have been done.

    _isInitialized = true;

    try {
      // Load the general BTX configuration properties (btxConfig.properties).
      //
      initProperties();

      // Parse the BTX configuration, which loads the data that this class provides.
      //
      BtxConfigurationParser parser = new BtxConfigurationParser();
      parser.parseConfiguration();
    }
    catch (Exception e) {
      // Set _isInitialized back to false if there's an exception.
      _isInitialized = false;
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  /**
   * Load the general BTX configuration properties (from btxConfig.properties).
   * This is meant to be called only from {@link #initialize()}, which does the
   * appropriate synchronization.
   */
  private static void initProperties() {
    InputStream is = null;
    try {
      String propertiesPath = ApiFunctions.CLASSPATH_RESOURCES_PREFIX + "btxConfig.properties";
      is = BtxConfiguration.class.getResourceAsStream(propertiesPath);
      _properties = new Properties();
      _properties.load(is);
    }
    catch (IOException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(is);
    }
  }
  
  /**
   * Return the general BTX configuration properties (from btxConfig.properties).
   * Callers should treat the returned properties as read-only.
   * 
   * <p>This is currently package-private as there's no reason to use these properties outside
   * the BTX framework code itself.
   */
  static Properties getProperties() {
    initialize();
    return _properties;
  }

  /**
   * Return the set of all registered transaction types. 
   * 
   * @return the transaction types.
   */
  public static Set getTransactionTypes() {
    initialize();
    synchronized (_transactions) {
      return Collections.unmodifiableSet(_transactions.keySet());
    }
  }

  /**
   * Return the BtxTransactionMetaData instance that describes the indicated transaction type.
   * This throws a runtime exception if the supplied transaction type is empty or unknown.
   * 
   * @param txType the transaction type.
   * @return the BtxTransactionMetaData for the transaction type.
   */
  public static BtxTransactionMetaData getTransaction(String txType) {
    initialize();
    synchronized (_transactions) {
      if (ApiFunctions.isEmpty(txType)) {
        throw new ApiException("The transaction type parameter must not be null or empty.");
      }

      BtxTransactionMetaData instance = (BtxTransactionMetaData) _transactions.get(txType);

      if (instance == null) {
        throw new ApiException("Unknown transaction type '" + txType + "'.");
      }

      return instance;
    }
  }

  /**
   * Return true if there's a BtxTransactionMetaData instance available for the specified
   * transaction type via the {@link #getTransaction(String) getTransaction} method.  This can
   * be used to validate a transaction-type string.  This is better than validating by
   * calling <code>getTransaction</code> and seeing whether it throws an exception or not.
   * Available instances can be registered with this class using
   * {@link #registerTransaction(BtxTransactionMetaData) registerTransaction}.
   * 
   * @param txType the transaction type.
   * @return true if the transaction type has been registered with this class.
   */
  public static boolean isRegisteredTransaction(String txType) {
    initialize();
    synchronized (_transactions) {
      return (_transactions.get(txType) != null);
    }
  }

  /**
   * Register the BtxTransactionMetaData instance to be returned for a specified
   * transaction type via the {@link #getTransaction(String) getTransaction} method.
   * As a side effect, the instance it made immutable if it is not already.
   * A runtime exception will be throw if the instance is null or invalid, or if
   * an instance is already registerd for the same transaction type.
   * 
   * @param txMeta the transaction metadata instance.
   */
  public static void registerTransaction(BtxTransactionMetaData txMeta) {
    initialize();
    synchronized (_transactions) {
      checkMutable();

      // makeImmutable calls checkValid so if we get past here, it is valid.
      //
      txMeta.makeImmutable();

      String txType = txMeta.getTxType();

      // We don't allow existing registry entries to be overwritten.  This helps us catch
      // errors where someone mistakenly assigned the same transaction type to two different
      // transactions.
      //
      if (_transactions.containsKey(txType)) {
        throw new ApiException("An instance with txType = '" + txType + "' is already registered.");
      }

      _transactions.put(txType, txMeta);
    }
  }

  /**
   * Returns true if the configuration is immutable (its state can't be changed).
   * 
   * @return true if the configuration is immutable.
   */
  public static boolean isImmutable() {
    initialize();
    return _immutable;
  }

  /**
   * Makes the configuration immutable.  Once this is called, any attempt to change the object's
   * state will result in a runtime exception being thrown.  Calling this method also calls
   * {@link #checkValid()}, so a runtime exception will be thrown if the configuration is invalid.
   */
  public static void makeImmutable() {
    initialize();
    if (!_immutable) {
      _immutable = true;
      checkValid();
    }
  }

  /**
   * Throw an exception if the configuration is immutable.
   */
  private static void checkMutable() {
    initialize();
    if (_immutable) {
      throw new ApiException("The BtxConfiguration cannot be modified.");
    }
  }

  /**
   * Check whether the configuration is valid, throwing a runtime exception if it is not.
   * See also {@link BtxTransactionMetaData#checkValid()}.
   * 
   * This doesn't detect all possible problems.  Some additional validity checks are done
   * when transactions are actually performed at runtime (for example, checking that there
   * is a validator and/or performer method that is compatible with the BTXDetails subclass
   * that is passed at runtime).  Since methods can be overloaded in Java, we can't do
   * complete method checks prior to runtime, since we don't know which overloaded versions
   * that class author intended to actually be called as BTX performer/validator methods.
   * We don't want to be overzealous and complain about overloaded versions that never were
   * intended to be called by the BTX framework.
   */
  public static void checkValid() {
    initialize();

    // Currently there's nothing for this to do.  The only thing in the configuration is
    // a collection of BtxTransactionMetaData objects that were put there by the
    // registerTransaction method, and registerTransaction makes sure that each transaction
    // is valid when it is added, and that there's not already a transaction of the same
    // type registered.
  }

}
