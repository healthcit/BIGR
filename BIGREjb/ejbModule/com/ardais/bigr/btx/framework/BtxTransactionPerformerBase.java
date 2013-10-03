package com.ardais.bigr.btx.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.iltds.beans.BtxHistoryExceptionRecorder;
import com.ardais.bigr.iltds.beans.BtxHistoryExceptionRecorderHome;
import com.ardais.bigr.iltds.beans.BTXHistoryRecorderBean;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.util.EjbHomes;

/**
 * This is the abstract base class for all objects that perform BTX business transactions. Each
 * business transaction has a corresponding class that contains a method that knows how to perform
 * that transaction. This base class implements a framework that is shared by all classes that
 * perform business transactions. Specifically, it implements a {@link #perform(BTXDetails) perform}
 * method that reflectively calls a transaction-specific method on the transaction object to perform
 * the transaction. In addition to calling that method, the <code>perform</code> method
 * implemented in this class does other tasks such as assigning a transaction id, exception
 * handling, and recording the transaction in the transaction history tables. See the
 * <code>perform</code> method for more details.
 * 
 * <p>
 * All derived objects must have a public zero-argument constructor. Also, they should all adopt a
 * naming convention where the BTX-transaction-performer method names are "perform" followed by the
 * transaction name. The name following the "perform" should be the same as the name following the
 * "BTXDetails" prefix on the corresponding transaction details class. The framework doesn't require
 * this naming convention, but it makes the code easier to understand.
 * 
 * <p>
 * Prior to calling the <code>perform</code> method, the
 * {@link BTXDetails#setTransactionType(String) setTxType}method must be called on the BTXDetails
 * object to indicate which specific transaction is to be performed.
 */
public abstract class BtxTransactionPerformerBase {
  // These constants are used in BtxCache and so have package visibility instead of private.
  // They indicate the various roles that user-defined methods can play in the BTX framework.
  //
  static final int METHOD_ROLE_PERFORM = 0;

  static final int METHOD_ROLE_VALIDATE = 1;

  public BtxTransactionPerformerBase() {
    super();
  }

  /**
   * Perform a business transaction.
   * 
   * <p>
   * Performs the transaction indicated by the supplied {@link BTXDetails}object's
   * {@link BTXDetails#getTransactionType() getTransactionType}method, using the BTXDetails
   * parameter to supply the transaction's input data and return its output data. It does other
   * tasks such as assigning a transaction id, exception handling, and recording the transaction in
   * the transaction history tables.
   * 
   * <p>
   * This method guarantees that the value returned by the <code>getTxType</code> method of the
   * returned BTXDetails object will be the same as the value returned for by <code>getTxType</code>
   * on the btxDetails parameter at the time the perform method was called. It also guarantees that
   * this is the transaction type that the BTXDetails object will have when it is passed to the
   * history-recording method. We guarantee this to avoid confusion that could arise if the
   * transaction-performed method for a specific transaction inadvertently changes the transaction
   * type, for example in the course of passing the same BTXDetails object on to a different
   * transaction internally.
   * 
   * <p>
   * This method is only intended to be used internally by the BTX framework. Clients should always
   * use the {@link Btx}class to invoke BTX business transactions. If BTX transactions are invoked
   * in any other way, parts of the transaction that should always be done may be skipped, such as
   * authorization checks, exception handling, and logging.
   * 
   * @param btxDetails the detailed description of what transaction to perform.
   * @return the transaction's result details.
   */
  public final BTXDetails perform(BTXDetails btxDetails) {

    boolean isHistoryRecordingPhaseStarted = false;

    String originalTxType = null;

    try {
      if (btxDetails == null) {
        throw new NullPointerException(
            "The btxDetails parameter passed to the perform method must not be null.");
      }

      // To ensure correct history recording and make the action of this method generally more
      // predictable, part of this method's contract is that the TxType property passed in
      // originally is the same as the type used throughout the method and returned in the
      // final result object. To ensure this, we need to save away the original transaction type
      // and re-set it into the btxDetails object at various points to ensure that we don't break
      // our contract regardless of what the user-written code that we dispatch to does.
      //
      originalTxType = btxDetails.getTransactionType();

      if (ApiFunctions.isEmpty(originalTxType)) {
        throw new ApiException("The TxType property of the BTXDetails parameter is empty ("
            + btxDetails.getClass().getName() + ").");
      }

      if (btxDetails.getLoggedInUserSecurityInfo() == null) {
        throw new IllegalStateException("The btxDetails object passed to transaction "
            + originalTxType + " has getLoggedInUserSecurityInfo() == null ("
            + btxDetails.getClass().getName() + ").");
      }

      btxDetails = dispatchPerform(btxDetails);

      // Restore the original transaction type, see comments earlier in this method.
      btxDetails.setTransactionType(originalTxType);

      // Recording the transaction in the history must be the last thing
      // done here. Otherwise we could log a transaction as successful
      // but then get an exception from code that comes afterwards that
      // would cause a rollback.
      //
      {
        isHistoryRecordingPhaseStarted = true;
        btxDetails = recordHistory(btxDetails);
      }
    }
    catch (Exception e) {
      // If the history-recording phase has already started when we
      // get the exception, then we had a failure recording the
      // history and we won't attempt to call recordHistory again.
      // However, if the exception didn't come from recordHistory
      // (for example, it came from dispatchPerform), then we set the
      // exception fields in the BTXDetails object and attempt to
      // log the exception before re-throwing the exception to the
      // caller.
      //
      // Regardless of what phase the exception came from, we log
      // it in the application log file for good measure, and then
      // re-throw the original exception to the caller.

      ApiLogger.log(
          "Error while trying to perform a business transaction (" + originalTxType + ")", e);

      try {
        if (!isHistoryRecordingPhaseStarted) {
          // If we get an exception, mark the transaction as
          // incomplete if the exception was thrown prior to
          // entering the history recording phase.
          //
          btxDetails.setTransactionCompleted(false);

          btxDetails.setException(e);

          {
            isHistoryRecordingPhaseStarted = true;
            btxDetails = recordHistory(btxDetails);
          }
        }
      }
      catch (Exception e1) {
        // If we get an exception here (while logging a transaction
        // exception), we just log the exception and let it fall
        // through to the code below that logs and re-throws the
        // original exception. That exception was really the root
        // of the problem so that's the one we want to re-throw to
        // the caller.
        //
        ApiLogger.log("Error while trying to record a failed business transaction", e1);
      }

      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      // Restore the original transaction type, see comments earlier in this method.
      if (btxDetails != null) {
        btxDetails.setTransactionType(originalTxType);
      }
    }

    return btxDetails;
  }

  /**
   * Record a business transaction in the transaction history log. If the
   * {@link BTXDetails#isLogged() isLogged}method on <code>btxDetails</code> returns false,
   * nothing is logged. Otherwise, if either the transaction was completed or an exception occurred,
   * log the transaction (or exception).
   * 
   * <p>
   * See the {@link BTXDetails}class for a description or what it means for a transaction to be
   * incomplete to get an understanding of why we don't log incomplete transactions. Incomplete
   * transactions are a very ordinary and expected situation, for example they occur when a
   * transaction determines that additional user input is needed before it can be performed. We
   * don't want to log these, we only want to log a transaction once it finally is completed.
   * 
   * @param btxDetails the transaction details to record.
   * @return a modified BTXDetails object with its transaction id field filled in.
   */
  private BTXDetails recordHistory(BTXDetails btxDetails) throws Exception {

    if (!btxDetails.isLogged()) {
      return btxDetails;
    }

    //if this BTXDetails object is for validation only, do not record any history
    if (btxDetails.isValidationOnly()) {
      return btxDetails;
    }

    boolean hasException = btxDetails.isHasException();

    if (hasException || btxDetails.isTransactionCompleted()) {
      // When there's an exception we need to use a different session bean to
      // do the recording, see BtxHistoryExceptionRecorder for details
      // about why. In brief, the transaction needs to be logged in a different
      // transaction context in this case, so that when the main transaction rolls
      // back it doesn't also roll back the exception record in the history log.
      //
      if (hasException) {
        BtxHistoryExceptionRecorderHome home = (BtxHistoryExceptionRecorderHome) EjbHomes.getHome(BtxHistoryExceptionRecorderHome.class);
        BtxHistoryExceptionRecorder recorder = home.create();
        btxDetails = recorder.record(btxDetails);
      }
      else {
        // As a bit of a performance enhancement, we bypass making
        // a session bean call here and directly call the underlying
        // bean class. This is ok since we know that we're in a
        // session bean already, and that both have the same
        // transactional properties set (TX Required, Read Committed).
        // For some operations that store a lot of data on the
        // BTXDetails object this save noticeable time by avoiding
        // serializing the data two more times (once on the call, once
        // on the return). Sample selection transactions with large
        // result sets are once such transactions where the difference
        // in nontrivial.
        //
        BTXHistoryRecorderBean recorder = new BTXHistoryRecorderBean();

        btxDetails = recorder.record(btxDetails);
      }
    }

    return btxDetails;
  }

  /**
   * Perform the transaction-specific aspects of a business transaction by invoking the appropriate
   * transaction-performer class/method on the supplied BTXDetails object. The appropriate
   * transaction-performer class and method are determined by looking up the transaction metadata
   * for the transaction type returned by {@link BTXDetails#getTxType() getTxType}on the BTXDetails
   * object.
   * 
   * The methods that are invoked by this method must perform the tasks that are specific to that
   * transaction. These methods should <b>NOT </b> do several things that are common to all
   * transactions and that are performed by the {@link #perform(BTXDetails) perform}method. The
   * things that should <b>NOT </b> be done are:
   * <ul>
   * <li>Setting any of the fields defined on the {@link BTXDetails BTXDetails}base class, such as
   * the transaction id, end timestamp, or user id (the begin timestamp should be set as early as
   * possible, before <code>perform</code> is called).</li>
   * <li>Exception handling or logging. The invoked method should be defined to throw
   * <code>Exception</code> and should simply throw exceptions as-is without ignoring, wrapping,
   * or logging them.</li>
   * <li>Recording the transaction in the transaction history tables.</li>
   * </ul>
   * 
   * @param btxDetails the detailed description of what transaction to perform.
   * @return the BTXDetails object returned by the invoked method.
   */
  private BTXDetails dispatchPerform(BTXDetails btxDetails) throws Exception {
    BTXDetails resultDetails = btxDetails;

    BtxTransactionMetaData txMeta = BtxConfiguration
        .getTransaction(btxDetails.getTransactionType());

    if (txMeta.isValidate()) {
      Method method = getMethod(resultDetails, txMeta, METHOD_ROLE_VALIDATE);

      resultDetails = callMethod(method, resultDetails);

      if (!resultDetails.getActionErrors().empty()) {
        // Validation failed, set the result details to indicate an imcomplete
        // transaction that forwards to the retry action. BTXDetails.setActionForwardRetry
        // takes care of doing both of these things.
        //
        resultDetails.setActionForwardRetry();
        return resultDetails;
      }
    }

    //if this BTXDetails object is for validation only, do not proceed any further
    if (btxDetails.isValidationOnly()) {
      return resultDetails;
    }

    // If we get here, dispatch to the transaction-performer method.
    // When txMeta.isValidate() is true, the BTXDetails object that should be passed
    // to the performer method is the one that the validator method returned, not
    // the one that was originally passed in to dispatchPerform. Also, it is part of
    // out stateless contract that the validator and performer methods will get called
    // on two different, newly-created instances of the performer class. See the
    // documentation of BtxTransactionMetaData.isValidate() for more information on these topics.
    //
    {
      Method method = getMethod(resultDetails, txMeta, METHOD_ROLE_PERFORM);

      resultDetails = callMethod(method, resultDetails);
    }

    return resultDetails;
  }

  /**
   * Find the Method object to use for the specified role of the specified transaction. See
   * {@link #findMostSpecificMethod(BtxTransactionMetaData, Class, int)}and
   * {@link #checkMethodSuitability(Method, int)}for details of how we search for the method and
   * what characteristics we expect such a method to have.
   * 
   * @param btxDetails the detailed description of what transaction to perform.
   * @param txMeta the transaction metadata for the btxDetails' transaction type.
   * @param methodRole the role the method plays in the BTX framework. This must be one of the
   *          METHOD_ROLE_* contants defined in this class.
   * 
   * @return the best-matching method
   */
  private Method getMethod(BTXDetails btxDetails, BtxTransactionMetaData txMeta, int methodRole)
      throws Exception {

    Class btxDetailsClass = btxDetails.getClass();

    // First see if we already have the Method in our dispatch method cache.
    // We can't precompute the Method object in the BtxTransactionMetaData class
    // because the method to use depends on what specific BTXDetails subclass is passed
    // at runtime (the performer class may contain multiple overloaded methods with the
    // same name, each taking a different kind of BTXDetails as a parameter).

    String methodCacheKey = BtxCache.getMethodCacheKey(txMeta, btxDetailsClass, methodRole);

    Method method = BtxCache.getMethod(methodCacheKey);

    if (method == null) {
      method = findMostSpecificMethod(txMeta, btxDetailsClass, methodRole);

      // This will check to see if the method we found meets various
      // expectations we have about what a method that plays the specified
      // role in the BTX framework dispatches to will be like. If any problems
      // are found, checkValidatorMethodSuitability will throw an exception
      // describing the problem.
      //
      checkMethodSuitability(method, methodRole);

      // If we get here we didn't find any problems with the
      // method, so we cache it in our Method cache for future
      // use before returning it.
      //
      BtxCache.putMethod(methodCacheKey, method);
    }

    return method;
  }

  /**
   * Find the transaction method for the specified methd role that takes a BTXDetails parameter that
   * is the closest match to the class of the btxDetails object we're actually going pass as a
   * parameter to the method. If we don't find a method that exactly matches that class, keep trying
   * superclasses until we get all the way up to the BTXDetails base class itself. If we still
   * haven't found a method by that point throw an exception -- don't continue searching all the way
   * up to Object.
   * 
   * <p>
   * The appropriate transaction-performer class and method name to search for are determined by
   * information in the supplied transaction metadata.
   * 
   * @param txMeta the transaction metadata describing the BTX transaction type to be performed.
   * @param btxDetailsClass the class of the BTXDetails instance that we'll pass as a parameter to
   *          the method
   * @param methodRole the role the method plays in the BTX framework. This must be one of the
   *          METHOD_ROLE_* contants defined in this class.
   * 
   * @return the best-matching method
   */
  private Method findMostSpecificMethod(BtxTransactionMetaData txMeta, Class btxDetailsClass,
                                        int methodRole) {

    // The code below assumes that the BTXDetails base is a class, not an
    // an interface. It would break if that's not the case, since the
    // loop that traverses up the superclass hierarchy looks for
    // BTXDetails as a stopping point. We do a quick check here and
    // throw an exception if BTXDetails is an interface, so that we'll
    // know right away that this needs to be recoded if we ever change
    // BTXDetails to be an interface.
    //
    if (BTXDetails.class.isInterface()) {
      throw new ApiException("The code in findMostSpecificMethod was written to "
          + "assume that BTXDetails is a class, not an interface, "
          + "but it is an interface.  Please see the comments in "
          + "the findMostSpecificMethod method.");
    }

    // To avoid an infinite loop below, make sure that the specified
    // btxDetailsClass is actually a subclass of BTXDetails.
    //
    if (!BTXDetails.class.isAssignableFrom(btxDetailsClass)) {
      throw new ApiException("Class does not extend BTXDetails: " + btxDetailsClass.getName());
    }

    String methodName = null;
    if (methodRole == METHOD_ROLE_PERFORM) {
      methodName = txMeta.getPerformerMethodName();
    }
    else if (methodRole == METHOD_ROLE_VALIDATE) {
      methodName = txMeta.getValidatorMethodName();
    }
    else {
      throw new IllegalArgumentException("Unknown methodRole: " + methodRole);
    }

    Method method = null;
    Class performerClass = txMeta.getPerformerClass();
    Class currentClass = btxDetailsClass;
    while (method == null) {
      try {
        method = performerClass.getDeclaredMethod(methodName, new Class[] { currentClass });
      }
      catch (NoSuchMethodException e) {
        // Method not found, do nothing and fall through to the
        // statement after this try block.
      }
      catch (SecurityException e1) {
        ApiFunctions.throwAsRuntimeException(e1);
      }

      if (method == null) {
        if (BTXDetails.class.equals(currentClass)) {
          // We looped up through superclasses all the way to
          // BTXDetails and still didn't find the method. Throw
          // an exception and give up.
          //
          throw new ApiException("There is no method named " + methodName + " in class "
              + performerClass.getName() + " that takes " + btxDetailsClass.getName()
              + " as an argument.");
        }
        else {
          currentClass = currentClass.getSuperclass();
        }
      }
    }

    return method;
  }

  /**
   * Check to see if the supplied method has the characteristics we expect a BTX transaction method
   * that plays the specified role to have. Currently supported roles are METHOD_ROLE_PERFORM and
   * METHOD_ROLE_VALIDATE and they both have the same validation rules as described below.
   * 
   * <p>
   * Things that we check for:
   * <ul>
   * <li>The method's return type is BTXDetails or a subclass of it.</li>
   * <li>The method's is not static.</li>
   * <li>The method's is not public. To ensure that BTX transaction methods are only called by the
   * BTX framework, and are not called directly, we require that such methods be non-public. This
   * ensures that BTX framework code that should always be executed for a transaction is not
   * bypassed.</li>
   * </ul>
   * 
   * <p>
   * Things that we assume are true but don't check for:
   * <ul>
   * <li>The method is a method in the runtime class of this instance.</li>
   * <li>The method takes exactly one argument, a BTXDetails object.</li>
   * </ul>
   * 
   * @param method the method to check
   * @param methodRole the role the method plays in the BTX framework. This must be one of the
   *          METHOD_ROLE_* contants defined in this class.
   */
  private void checkMethodSuitability(Method method, int methodRole) throws Exception {
    // TODO: Question: Does the non-public constraint really add any value for
    // validation methods? Also, rethink why we have the non-static constraint.

    Class returnType = method.getReturnType();

    if (!BTXDetails.class.isAssignableFrom(returnType)) {
      throw createMethodSuitabilityException(method, "Its return type (" + returnType.getName()
          + ") is not assignable to BTXDetails.", methodRole);
    }

    int modifiers = method.getModifiers();

    if (Modifier.isStatic(modifiers)) {
      throw createMethodSuitabilityException(method, "It is a static method but must not be.",
          methodRole);
    }

    if (Modifier.isPublic(modifiers)) {
      throw createMethodSuitabilityException(method, "It is a public method but must not be.",
          methodRole);
    }
  }

  /**
   * This is a utility method used create exception objects describing unsuitable methods.
   * 
   * @param method the unsuitable method
   * @param problemDetails a description of why the method is unsuitable
   * @param methodRole the role the method plays in the BTX framework. This must be one of the
   *          METHOD_ROLE_* contants defined in this class.
   * 
   * @return the exception
   */
  private Exception createMethodSuitabilityException(Method method, String problemDetails,
                                                     int methodRole) {

    String role = null;
    if (methodRole == METHOD_ROLE_PERFORM) {
      role = "perform";
    }
    else if (methodRole == METHOD_ROLE_VALIDATE) {
      role = "validate";
    }
    else {
      throw new IllegalArgumentException("Unknown methodRole: " + methodRole);
    }

    return new ApiException("Method " + method.getName() + " in class "
        + method.getDeclaringClass().getName() + " cannot be used to " + role
        + " a BTX transaction.  " + problemDetails);
  }

  /**
   * Invoke the given method on the given BTXDetails instance and return the result. If an exception
   * occurs, we make our best effort to throw the exception that invoked method threw, rather than
   * always returning an InvocationTargetException.
   * 
   * @param method the method to invoke
   * @param btxDetails the BTXDetails instance to pass to the method
   * @return the BTXDetails object that is returned by the invoked method
   */
  private BTXDetails callMethod(Method method, BTXDetails btxDetails) throws Exception {

    BTXDetails resultBtxDetails = null;

    try {
      resultBtxDetails = invokeBtxEntryPoint(method, btxDetails);
    }
    catch (InvocationTargetException e) {
      // When the invoked method throws an exception, Method.invoke
      // wraps it in an InvocationTargetException. Here we make an
      // effort to unwrap it and throw the original exception if
      // possible. This is complicated by the fact that
      // InvocationTargetException.getTargetException returns a
      // Throwable rather than an Exception.

      Throwable wrappedThrowable = e.getTargetException();

      if (wrappedThrowable == null) {
        // Nothing wrapped, so throw the InvocationTargetException
        // itself. This shouldn't happen.

        throw e;
      }
      else if (wrappedThrowable instanceof Exception) {
        throw (Exception) wrappedThrowable;
      }
      else if (wrappedThrowable instanceof Error) {
        throw (Error) wrappedThrowable;
      }
      else {
        // The wrapped Throwable isn't something we can unwrap and
        // throw directly, so we throw the InvocationTargetException
        // itself.

        throw e;
      }
    }

    return resultBtxDetails;
  }

  /**
   * Every subclass of this class must define this method to allow the BTX framework to dispatch
   * calls to private methods on the objects that perform BTX transactions. This allows the private
   * methods to be called without getting Java security exceptions.
   * 
   * <p>
   * Every subclass of BtxTransactionPerformerBase must have exactly the same implementation of this
   * method:
   * 
   * <pre>
   * protected BTXDetails invokeBtxEntryPoint(java.lang.reflect.Method method, BTXDetails btxDetails)
   *     throws Exception {
   * 
   *   // **** DO NOT EDIT THIS METHOD, see the Javadoc comment above.
   *   return (BTXDetails) method.invoke(this, new Object[] { btxDetails });
   * }
   * </pre>
   * 
   * <p>
   * In addition, please include the following text as a Javadoc comment on the method in the
   * subclass: <br>
   * <br>
   * 
   * Invoke the specified method on this class. This is only meant to be called from
   * BtxTransactionPerformerBase, please don't call it from anywhere else as a mechanism to gain
   * access to private methods in this class. Every object that the BTX framework dispatches to must
   * contain this method definition, and its implementation should be exactly the same in each
   * class. Please don't alter this method or its implementation in any way.
   * 
   * @param method the method to call
   * @param btxDetails the BTXDetails object to pass to the method
   * @return the BTXDetails object that was returned by the called method
   */
  protected abstract BTXDetails invokeBtxEntryPoint(Method method, BTXDetails btxDetails)
      throws Exception;

}