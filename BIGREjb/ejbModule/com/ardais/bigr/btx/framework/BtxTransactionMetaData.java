package com.ardais.bigr.btx.framework;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * This class represents various facts about a BTX transaction type, for example what
 * class and method are responsible for performing that type of transaction.
 */
public class BtxTransactionMetaData implements java.io.Serializable {
  private static final long serialVersionUID = 410002670246953025L;

  /**
   * The maximum allowed length of a transaction-type string.  We enforce a maximum
   * because transaction type strings may be stored in database fields that are limited
   * to this length.
   */
  public static final int MAX_TX_TYPE_LENGTH = 80;

  private boolean _immutable = false;

  private String _txType = null;

  private String _performerType = null;
  private transient Class _performerClass = null;

  private String _performerMethodName = null;

  // Holds the validator method name that was explicitly defined by the user by calling
  // setValidatorMethodName.
  //
  private String _explicitValidatorMethodName = null;

  private boolean _validate = true;

  // Holds custom property/value pairs, where the keys are the property names and each value is 
  // a string.
  private Map _properties = new HashMap();
  
  /**
   * Constructor for BtxTransactionMetaData.
   */
  public BtxTransactionMetaData() {
    super();
  }

  /**
   * Returns the transaction type of this transaction.
   * 
   * @return the transaction type.
   */
  public String getTxType() {
    return _txType;
  }

  /**
   * Returns the fully-qualified name of the class that contains the transaction-performer
   * method for this transaction.
   * 
   * @return the performer class name.
   */
  public String getPerformerType() {
    return _performerType;
  }

  /**
   * Returns the class object that is responsible for performing this transaction type.
   * This is the class named by {@link #getPerformerType()}.  You must have already called
   * {@link #setPerformerType(String)} for this to return a valid result.
   * 
   * @return the performer class.
   */
  Class getPerformerClass() {
    // This has package visibility since only the BTX framework code itself should ever
    // be dispatching directly to the performer class.  Making this invisible publicly
    // discourages misuse of the framework.

    return _performerClass;
  }

  /**
   * Returns the name of the method that will perform this transaction.
   * 
   * @return the performer method name.
   */
  public String getPerformerMethodName() {
    return _performerMethodName;
  }

  /**
   * Returns the name of the method that will validate this transaction when the
   * {@link #isValidate()} property is true.  See <code>isValidate</code> for more
   * details on BTX transaction validation methods.
   * 
   * <p>The default validator method name is the performer method name
   * (see {@link #getPerformerMethodName()}) with "validate" prepended and the first character
   * of the performer method name changed to upper case.  For example, if the performer method
   * name is "performMyTransaction", the default validation method name is
   * "validatePerformMyTransaction".  If neither a validator method name nor a performer
   * method name have been set yet, this method returns null.
   * 
   * @return the performer method name.
   */
  public String getValidatorMethodName() {
    String result;
    if (ApiFunctions.isEmpty(_explicitValidatorMethodName)) {
      if (ApiFunctions.isEmpty(getPerformerMethodName())) {
        result = null;
      }
      else {
        result = "validate" + StringUtils.capitalize(getPerformerMethodName());
      }
    }
    else {
      result = _explicitValidatorMethodName;
    }

    return result;
  }

  /**
   * Returns the custom property with the specified name for this transaction.
   * 
   * @param  name  the name of the custom property
   * @return  The custom property's value, or null if such a custom property is not defined.
   */
  public String getProperty(String name) {
    return (String)_properties.get(name);
  }

  /**
   * Sets the transaction type of this transaction.  Transaction types may be stored in
   * database fields that are limited to a maximum of MAX_TX_TYPE_LENGTH characters,
   * so this method will throw a runtime exception if the specified transaction type
   * is longer than that.
   * 
   * @param txType The transaction type.
   */
  public void setTxType(String txType) {
    checkMutable();

    if (ApiFunctions.safeStringLength(txType) > MAX_TX_TYPE_LENGTH) {
      throw new ApiException(
        "Transaction types may not exceed " + MAX_TX_TYPE_LENGTH + " characters.");
    }

    _txType = txType;
  }

  /**
   * Sets the fully-qualified name of the class that contains the transaction-performer
   * method for this transaction.  A runtime exception is thrown if there's no such class or
   * if the class doesn't extend {@link BtxTransactionPerformerBase}.  By convention,
   * performer class names should start with "BtxPerformer".
   * 
   * @param performerType The performerType to set
   */
  public void setPerformerType(String performerType) {
    checkMutable();

    Class performerClass = null;

    // Look up and store the Class object corresponding to the
    // specified class name.  Throw an exception if:
    //   - the class can't be found
    //   - the class doesn't extend BtxTransactionPerformerBase
    //   - the class doesn't have a public zero-argument constructor
    //
    {
      try {
        performerClass = Class.forName(performerType);
      }
      catch (ClassNotFoundException e) {
        throw new ApiException(
          "For BTX transaction type '"
            + getTxType()
            + "': "
            + "Could not find the class specified in the "
            + "performerType property: "
            + performerType,
          e);
      }

      if (!BtxTransactionPerformerBase.class.isAssignableFrom(performerClass)) {
        throw new ApiException(
          "For BTX transaction type '"
            + getTxType()
            + "': "
            + "The class specified in the performerType "
            + "property must extend BtxTransactionPerformerBase: "
            + performerType);
      }

      try {
        performerClass.getConstructor(new Class[0]);
      }
      catch (NoSuchMethodException e) {
        throw new ApiException(
          "For BTX transaction type '"
            + getTxType()
            + "': "
            + "The class specified in the performerType "
            + "property must have a public zero-argument constructor: "
            + performerType,
          e);
      }
    }

    _performerType = performerType;
    _performerClass = performerClass;
  }

  /**
   * Sets the name of the method that will perform this transaction.  This method must
   * satisy a variety of constraints as described in {@link #checkValid()}.  By convention,
   * performer method names should start with "perform".
   * 
   * @param performerMethodName The performer method name.
   */
  public void setPerformerMethodName(String performerMethodName) {
    checkMutable();

    _performerMethodName = performerMethodName;
  }

  /**
   * Sets the name of the method that will validate this transaction if
   * {@link #isValidate()} is true.  This method must
   * satisy a variety of constraints as described in {@link #checkValid()}.  By convention,
   * performer method names should start with "validate".
   * 
   * <p>The default validator method name is the performer method name
   * (see {@link #getPerformerMethodName()}) with "validate" prepended and the first character
   * of the performer method name changed to upper case.  For example, if the performer method
   * name is "performMyTransaction", the default validation method name is
   * "validatePerformMyTransaction".
   * 
   * @param validatorMethodName The validator method name.
   */
  public void setValidatorMethodName(String validatorMethodName) {
    checkMutable();

    _explicitValidatorMethodName = validatorMethodName;
  }

  /**
   * Sets a custom property for this transaction.  If the property with the specified name is 
   * already set, then its value will be overwritten. 
   * 
   * @param  name  the name of the custom property
   * @param  value  its value
   */
  public void setProperty(String name, String value) {
    _properties.put(name, value);
  }

  /**
   * Return true if this transaction type should call a validation method before calling
   * its performer method.  When true, the performer class (see {@link #getPerformerType()})
   * must have a method whose name is the value returned by {@link #getValidatorMethodName()}.
   * Like the performer method, the validation method must take a single parameter whose type
   * is a subclass of {@link BTXDetails} and that returns a value whose type is a subclass
   * of BTXDetails.
   * 
   * <p>Prior to calling the performer method, the BTX framework calls the validation method,
   * passing it the transaction's BTXDetails instance.  If the BTXDetails instance that is
   * returned from the validation method has a non-empty errors collection (as returned by
   * BTXDetails' {@link BTXDetails#getActionErrors() getActionErrors} method), validation fails
   * and the framework does not call the performer method.  In this case, it calls
   * {@link BTXDetails#setActionForwardRetry() setActionForwardRetry()} on the BTXDetails instance
   * that the validation method returned, and returns this BTXDetails instance as the transaction's
   * result.
   * 
   * <p>If the ActionErrors collection returned by the validation method is empty, the BTX
   * framework proceeds to call the transaction's performer method, passing it the BTXDetails
   * instance that was returned from the validation method as a parameter.  Passing this
   * BTXDetails instance rather than the transaction's original BTXDetails instance can be
   * useful when the validation method does an expensive operation whose outcome is needed
   * by the performer method.  For example, a common situation is when the input BTXDetails object
   * contains some data in an external form that needs to be validated (for example, a user's
   * name).  The validation method, in checking that the data is valid, must do a database lookup,
   * during which it can conveniently also retrieve the unique back-end key that identifies
   * the user in the database.  To make the performer method simpler and more efficient,
   * the validation method can store this key on the BTXDetails object that is returns so that
   * the performer method doesn't need to repeat the database lookup.
   * 
   * <p>The BTX framework is intended to be stateless, with all state being passed in and out
   * of transactions via BTXDetails objects.  To help enforce this statelessness, the framework
   * ensures that no state can be passed from the validation method to the performer method via a
   * stateful performer class.  It does this by using two different, newly created instances
   * of the performer class for calling the validation and performer methods, respectively.
   * 
   * <p>This property is set to true by default (this is consistent with
   * the default for the validate property on Struts 1.1 action mappings).
   * 
   * <p>The validation method must satisy a variety of constraints as described in
   * {@link #checkValid()}.
   * 
   * @return true if the validation method should be called.
   */
  public boolean isValidate() {
    return _validate;
  }

  /**
   * Set to true if this transaction type should call a validation method before calling
   * its performer method.  See {@link #isValidate()} for details on how validation is performed
   * when this is set to true.  This property is set to true by default (this is consistent with
   * the default for the validate property on Struts 1.1 action mappings).
   * 
   * @param validate The validate to set
   */
  public void setValidate(boolean validate) {
    checkMutable();

    _validate = validate;
  }

  /**
   * Returns true if this instance is immutable (its state can't be changed).
   * 
   * @return true if this instance is immutable.
   */
  public boolean isImmutable() {
    return _immutable;
  }

  /**
   * Makes this instance immutable.  Once this is called, any attempt to change the object's
   * state will result in a runtime exception being thrown.  Calling this method also calls
   * {@link #checkValid()}, so a runtime exception will be thrown if the instance is invalid.
   */
  public void makeImmutable() {
    if (!_immutable) {
      _immutable = true;
      checkValid();
    }
  }

  /**
   * Throw an exception if this instance is immutable.
   */
  private void checkMutable() {
    if (_immutable) {
      throw new ApiException("This instance cannot be modified (txType=" + getTxType() + ").");
    }
  }

  /**
   * Check whether this is a valid instance, throwing a runtime exception if any of
   * the following are violated:
   * <ul>
   * <li>{@link #getTransactionType()}, {@link #getPerformerType()}, and
   *     {@link #getPerformerMethodName()} are all non-empty.</li>
   * <li>{@link #isValidate()} is true when a validator method has been explicitly defined
   *     by calling {@link #setValidatorMethodName(String) setValidatorMethodName}.
   * <li>The class named by {@link #getPerformerType()} has at least one method whose name
   *     is the value of {@link #getPerformerMethodName()} that has all of the following
   *     properties:
   *     <ul>
   *     <li>It takes exactly one parameter, and the parameter is of type BTXDetails or one
   *         of its subclasses.</li>
   *     <li>Its return type is BTXDetails or one of its subclasses.</li>
   *     <li>It is not static.</li>
   *     <li>It is not public.  To ensure that BTX-transaction-performer methods are only
   *         called by the BTX framework, and are not called directly, we require that such
   *         methods be non-public.  This ensures that BTX framework code that should always
   *         be executed for a transaction (such as security checks) is not bypassed.</li>
   *     </ul>
   * </li>
   * <li>If {@link #isValidate()} is true, the class named by {@link #getPerformerType()} has at
   *     least one method with the correct name (see {@link #isValidate()}) and that has all
   *     of the following properties:
   *     <ul>
   *     <li>It takes exactly one parameter, and the parameter is of type BTXDetails or one
   *         of its subclasses.</li>
   *     <li>Its return type is BTXDetails or one of its subclasses.</li>
   *     <li>It is not static.</li>
   *     <li>It is not public.  To ensure that BTX-transaction-validator methods are only
   *         called by the BTX framework, and are not called directly, we require that such
   *         methods be non-public.  This ensures that BTX framework code that should always
   *         be executed for a transaction is not bypassed.</li>
   *     </ul>
   * </li>
   * </ul>
   * 
   * This doesn't detect all possible problems.  Some additional validity checks are done
   * when transactions are actually performed at runtime (for example, checking that there
   * is a validator and/or performer method that is compatible with the BTXDetails subclass
   * that it passed at runtime).  Since methods can be overloaded in Java, we can't do
   * complete method checks prior to runtime, since we don't know which overloaded versions
   * that class author intended to actually be called as BTX performer/validator methods.
   * We don't want to be overzealous and complain about overloaded versions that never were
   * intended to be called by the BTX framework.
   */
  public void checkValid() {
    // TODO: Question: Does the non-public constraint really add any value for
    // validation methods?  Also, rethink why we have the non-static constraint.

    String txType = getTxType();

    if (ApiFunctions.isEmpty(txType)) {
      throw new ApiException("The TxType property must not be empty.");
    }

    if (ApiFunctions.isEmpty(getPerformerType())) {
      throw new ApiException(
        "For transaction type '" + txType + "', the PerformerType property must not be empty.");
    }

    if (ApiFunctions.isEmpty(getPerformerMethodName())) {
      throw new ApiException(
        "For transaction type '"
          + txType
          + "', the PerformerMethodName property must not be empty.");
    }

    if (!ApiFunctions.isEmpty(_explicitValidatorMethodName) && !isValidate()) {
      throw new ApiException(
        "For transaction type '"
          + txType
          + "', a validator method name is defined but the validate property is false: "
          + _explicitValidatorMethodName
          + ".");
    }

    checkPerformerMethod();

    if (isValidate()) {
      checkValidatorMethod();
    }
  }

  /**
   * Helper method to {@link #checkValid()} to see if there is at least a possibility that
   * there's a suitable transaction-performer method on the specified class with the
   * specified method name.  See {@link #checkValid()} for an explanation of why we
   * can't completely validate that the performer method is valid until runtime.
   */
  private void checkPerformerMethod() {
    Class clazz = getPerformerClass();
    String methodName = getPerformerMethodName();
    boolean found = false;

    while (clazz != null) {
      found = declaresPotentiallyValidPerformerOrValidatorMethod(clazz, methodName);
      if (found)
        break;
      clazz = clazz.getSuperclass();
    }

    if (!found) {
      throw new ApiException(
        "For transaction type '"
          + getTxType()
          + "', there are no methods named "
          + methodName
          + " in class "
          + getPerformerClass().getName()
          + " that meet the requirements of a BTX transaction performer method.");
    }
  }

  /**
   * Helper method to {@link #checkValid()} to see if there is at least a possibility that
   * there's a suitable transaction-validator method on the specified class with the
   * specified method name.  See {@link #checkValid()} for an explanation of why we
   * can't completely validate that the validator method is valid until runtime.
   */
  private void checkValidatorMethod() {
    Class clazz = getPerformerClass();
    String methodName = getValidatorMethodName();
    boolean found = false;

    while (clazz != null) {
      found = declaresPotentiallyValidPerformerOrValidatorMethod(clazz, methodName);
      if (found)
        break;
      clazz = clazz.getSuperclass();
    }

    if (!found) {
      throw new ApiException(
        "For transaction type '"
          + getTxType()
          + "', there are no methods named "
          + methodName
          + " in class "
          + getPerformerClass().getName()
          + " that meet the requirements of a BTX transaction validator method.");
    }
  }

  /**
   * Helper method to {@link #checkValid()} to see if there is at least a possibility that
   * there's a suitable transaction-performer or transaction-validator method declared directly
   * on the specified class with the specified method name.  See {@link #checkValid()} for an
   * explanation of why we can't completely validate that the performer/validator method is
   * valid until runtime.
   * 
   * <p>Currently the validation rules are the same for both performer and validator methods
   * so we can use this single method to check both.
   */
  private boolean declaresPotentiallyValidPerformerOrValidatorMethod(
    Class clazz,
    String methodName) {

    Method[] methods = clazz.getDeclaredMethods();

    for (int i = 0; i < methods.length; i++) {
      // Check all of the things about each method that go into making it a potentially suitable
      // performer/validator method.  As soon as we find something that make a method unsuitable,
      // do a "continue" to jump to the next loop iteration.  If we get past all of the checks,
      // for some method, we immediately return true.

      Method method = methods[i];

      // Is the method name right?
      if (!method.getName().equals(methodName))
        continue;

      int modifiers = method.getModifiers();

      // Is it non-static?
      if (Modifier.isStatic(modifiers))
        continue;

      // Is it non-public?
      if (Modifier.isPublic(modifiers))
        continue;

      Class[] parameterTypes = method.getParameterTypes();

      // Does it take exactly one parameter?
      if (parameterTypes.length != 1)
        continue;

      // Is the parameter of type BTXDetails or one of its subclasses?
      if (!BTXDetails.class.isAssignableFrom(parameterTypes[0]))
        continue;

      // Is the return type BTXDetails or one of its subclasses?
      if (!BTXDetails.class.isAssignableFrom(method.getReturnType()))
        continue;

      // The current method passed all of the tests, return true.
      return true;
    }

    // None of the declared methods on this class passed all of the tests, return false.
    return false;
  }

}
