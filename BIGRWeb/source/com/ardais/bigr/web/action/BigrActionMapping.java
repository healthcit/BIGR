package com.ardais.bigr.web.action;

import java.security.InvalidParameterException;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ForwardingActionForward;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.BtxConfiguration;
import com.ardais.bigr.iltds.btx.BTXActionForward;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails;

/**
 * BIGR-specific extensions to the standard Struts ActionMapping class.
 * It includes:
 * <ul>
 * <li>Extensions for integrating the Ardais BTX business transaction framework
 *     with Struts.  Developers can add BTX-specific custom properties
 *     to a struts-config.xml file.  For many transactions, these additional
 *     properties are enough to allow the Ardais-extended Struts controller
 *     to process business transactions in the Struts framework without
 *     requiring any BTX-specific glue code.</li>
 * <li>Support for declaring in a struts-config.xml file which actions
 *     require the user to be logged in to perform them.</li>
 * </ul>
 */
public class BigrActionMapping extends ActionMapping {

  public final static String BTX_ACTION_FORWARD_RETRY = "retry";
  public final static String BTX_GLOBAL_ACTION_FORWARD_RETRY = "defaultRetry";

  private ActionForward _retryActionForward = null;

  private boolean _loginRequired = true;

  private String _btxDetailsType = null;
  private Class _btxDetailsClass = null;

  private String _btxTransactionType = null;

  private String _btxResultPropertyName = null;

  private String _btxResultPropertyType = null;
  private Class _btxResultPropertyClass = null;

  private boolean _btxCopyResultsToActionForm = false;

  private boolean _btxPreservePriorMessages = false;
  
  private String _tag = null;
  
  private boolean _populatesRequestFromBtxDetails = false;

  /**
   * Constructor for BigrActionMapping.
   */
  public BigrActionMapping() {
    super();
  }

  /**
   * Return the fully qualified class name of the BTXDetails subclass that
   * should be used for the BTX transaction that this Struts action invokes.
   * 
   * @return the Java class name
   */
  public String getBtxDetailsType() {
    return _btxDetailsType;
  }

  /**
   * Return the BTXDetails subclass that should be used for the BTX
   * transaction that this Struts action invokes.
   * 
   * @return the Java Class object
   */
  public Class getBtxDetailsClass() {
    return _btxDetailsClass;
  }

  /**
   * Return the BTX transaction type to invoke for this Struts action.
   * 
   * @return the BTX transaction type.
   */
  public String getBtxTransactionType() {
    return _btxTransactionType;
  }

  /**
   * Return the name of the HTTPServletRequest attribute that will
   * contain the object that will represent a BTX transaction's results.
   * There is no default value for this property.  When you use this property,
   * you must also use {@link #setBtxResultPropertyType(String)} to define
   * the class of the object that the transaction results will be copied to.
   * 
   * <p>This property and the {@link #setBtxCopyResultsToActionForm(boolean)}
   * property must not both be specified on the same action.
   * 
   * @return the request attribute name
   * 
   * @see #getBtxResultPropertyType()
   */
  public String getBtxResultPropertyName() {
    return _btxResultPropertyName;
  }

  /**
   * Return the fully qualified class name of the object that will
   * represent a BTX transaction's results in the HTTPServletRequest
   * object following the transaction's completion.  When you use this
   * property, you must also use {@link #setBtxResultPropertyName(String)}
   * to define name of the request attribute that the result object will
   * be stored under.
   * 
   * <p>This property and the {@link #setBtxCopyResultsToActionForm(boolean)}
   * property must not both be specified on the same action.
   * 
   * @return the Java class name
   * 
   * @see #getBtxResultPropertyClass()
   * @see #getBtxResultPropertyName()
   */
  public String getBtxResultPropertyType() {
    return _btxResultPropertyType;
  }

  /**
   * Return the Java Class of the object that will
   * represent a BTX transaction's results in the HTTPServletRequest
   * object following the transaction's completion.
   * 
   * @return the Java Class object
   * 
   * @see #getBtxResultPropertyType()
   * @see #getBtxResultPropertyName()
   */
  public Class getBtxResultPropertyClass() {
    return _btxResultPropertyClass;
  }

  /**
   * When this is true, properties from the transaction's result BTXDetails
   * object will be copied to the ActionForm object instance that was
   * passed to the action's <code>perform</code> method.
   * 
   * <p>When this property is true, the
   * {@link #setBtxResultPropertyName(String)} and
   * {@link #setBtxResultPropertyType(String)} properties must not also
   * be specified on the same action.
   * 
   * @return boolean
   */
  public boolean isBtxCopyResultsToActionForm() {
    return _btxCopyResultsToActionForm;
  }

  /**
   * Returns true if this action requires the user to be logged in.
   */
  public boolean isLoginRequired() {
    return _loginRequired;
  }

  /**
   * See {@link #getBtxDetailsType()}.  Setting this also sets the value
   * that will be returned by {@link #getBtxDetailsClass()}.
   * 
   * @param btxDetailsType The fully qualified name of the BTXDetails
   *     subclass.
   */
  public void setBtxDetailsType(String btxDetailsType) {
    Class btxDetailsClass = null;

    try {
      btxDetailsClass = Class.forName(btxDetailsType);
    }
    catch (ClassNotFoundException e) {
      throw new ApiException(
        getPath()
          + ": Could not find the class specified in the "
          + "btxDetailsType property: "
          + btxDetailsType,
        e);
    }

    if (!BTXDetails.class.isAssignableFrom(btxDetailsClass)) {
      throw new InvalidParameterException(
        getPath()
          + ": The class specified in the btxDetailsType property "
          + "must be assignable to BTXDetails: "
          + btxDetailsType);
    }

    _btxDetailsType = btxDetailsType;
    _btxDetailsClass = btxDetailsClass;
  }

  /**
   * Set the BTX transaction type to invoke for this Struts action.
   * 
   * @param btxTransactionType the BTX transaction type.
   */
  public void setBtxTransactionType(String btxTransactionType) {
    if (!BtxConfiguration.isRegisteredTransaction(btxTransactionType)) {
      throw new ApiException(
        getPath() + ": Unknown BTX transaction type '" + btxTransactionType + "'.");
    }

    _btxTransactionType = btxTransactionType;
  }

  /**
   * Set the name of the HTTPServletRequest attribute that will
   * contain the object that will represent a BTX transaction's results.
   * There is no default value for this property.
   * 
   * <p>This property and the {@link #setBtxCopyResultsToActionForm(boolean)}
   * property must not both be specified on the same action.
   * 
   * @param btxResultPropertyName The request attribute name
   * 
   * @see #getBtxResultPropertyType()
   */
  public void setBtxResultPropertyName(String btxResultPropertyName) {
    if (isBtxCopyResultsToActionForm() && !ApiFunctions.isEmpty(btxResultPropertyName)) {
      throw new IllegalStateException(
        getPath()
          + ": The btxCopyResultsToActionForm property cannot be used "
          + "together with the btxResultPropertyName/Type "
          + "properties: "
          + btxResultPropertyName
          + "/"
          + getBtxResultPropertyType());
    }

    _btxResultPropertyName = btxResultPropertyName;
  }

  /**
   * See {@link #getBtxResultPropertyType()}.  Setting this also sets the
   * value that will be returned by {@link #getBtxResultPropertyClass()}.
   * 
   * <p>This property and the {@link #setBtxCopyResultsToActionForm(boolean)}
   * property must not both be specified on the same action.
   * 
   * @param btxResultPropertyType The fully qualified class name
   */
  public void setBtxResultPropertyType(String btxResultPropertyType) {
    if (isBtxCopyResultsToActionForm() && !ApiFunctions.isEmpty(btxResultPropertyType)) {
      throw new IllegalStateException(
        getPath()
          + ": The btxCopyResultsToActionForm property cannot be used "
          + "together with the btxResultPropertyName/Type "
          + "properties: "
          + getBtxResultPropertyName()
          + "/"
          + btxResultPropertyType);
    }

    Class btxResultPropertyClass = null;

    // Look up and store the Class object corresponding to the
    // specified class name.  Throw an exception if:
    //   - the class can't be found
    //   - the class doesn't implement PopulatableFromBtxDetails
    //
    {
      try {
        btxResultPropertyClass = Class.forName(btxResultPropertyType);
      }
      catch (ClassNotFoundException e) {
        throw new ApiException(
          getPath()
            + ": Could not find the class specified in the "
            + "btxResultPropertyType property: "
            + btxResultPropertyType,
          e);
      }

      if (!PopulatableFromBtxDetails.class.isAssignableFrom(btxResultPropertyClass)) {
        throw new InvalidParameterException(
          getPath()
            + ": The class specified in the btxResultPropertyType "
            + "property must implement PopulatableFromBtxDetails: "
            + btxResultPropertyType);
      }
    }

    _btxResultPropertyType = btxResultPropertyType;
    _btxResultPropertyClass = btxResultPropertyClass;
  }

  /**
   * When this is true, properties from the transaction's result BTXDetails
   * object will be copied to the ActionForm object instance that was
   * passed to the action's <code>perform</code> method.
   * 
   * <p>When this property is true, the
   * {@link #setBtxResultPropertyName(String)} and
   * {@link #setBtxResultPropertyType(String)} properties must not also
   * be specified on the same action.
   * 
   * @param btxCopyResultsToActionForm the new value
   */
  public void setBtxCopyResultsToActionForm(boolean btxCopyResultsToActionForm) {
    if (btxCopyResultsToActionForm) {
      if ((!ApiFunctions.isEmpty(getBtxResultPropertyName()))
        || (!ApiFunctions.isEmpty(getBtxResultPropertyType()))) {
        throw new IllegalStateException(
          getPath()
            + ": The btxCopyResultsToActionForm property cannot be used "
            + "together with the btxResultPropertyName/Type "
            + "properties: "
            + getBtxResultPropertyName()
            + "/"
            + getBtxResultPropertyType());
      }

      if (ApiFunctions.isEmpty(getName())) {
        throw new IllegalStateException(
          getPath()
            + ": The action 'name' property (form name) must be specified "
            + " when the btxCopyResultsToActionForm is true.");
      }
    }

    _btxCopyResultsToActionForm = btxCopyResultsToActionForm;
  }

  /**
   * Set this to true if this action requires the user to be logged in.
   *
   * @param loginRequired the new setting
   */
  public void setLoginRequired(boolean loginRequired) {
    _loginRequired = loginRequired;
  }

  /**
   * Return the Struts <code>ActionForward</code> corresponding to the
   * specified {@link BTXActionForward} if any; otherwise return
   * <code>null</code>.
   *
   * @param btxActionForward the BTX action-forward description for which
   *     to return a corresponding Struts ActionForward.  This argument
   *     must not be null.
   */
  public ActionForward findForward(BTXActionForward btxActionForward) {
    ActionForward actionForward;

    if (btxActionForward == null) {
      throw new IllegalArgumentException(
        getPath()
          + ": A null btxActionForward argument was passed to "
          + "BigrActionMapping.findForward.");
    }

    boolean isRetryActionForward = btxActionForward.isRetryActionForward();

    if (isRetryActionForward) {
      actionForward = getRetryActionForward();

      if (actionForward == null) {
        throw new IllegalStateException(
          getPath()
            + ": Cannot forward to a retry target, this action must"
            + "have either an input property or an ActionForward named '"
            + BTX_ACTION_FORWARD_RETRY
            + "'.");
      }
    }
    else {
      // When isRetryActionForward is false, the btxActionForward's
      // targetName property specified the name of the mapping to
      // forward control to.

      actionForward = findForward(btxActionForward.getTargetName());
    }

    return actionForward;
  }

  /**
   * Return the ActionForward that we'll use to retry getting user input in
   * the case of a validation error detected by a BTX session bean.
   * First look for a mapping whose name is the value of the constant
   * BTX_ACTION_FORWARD_RETRY, and if it is present, return that.  Next,
   * look at the mapping's input property.  Finally look at a global forward whose
   * name is the value of the constant BTX_GLOBAL_ACTION_FORWARD_RETRY.
   */
  public ActionForward getRetryActionForward() {

    if (_retryActionForward != null) {
      return _retryActionForward;
    }

    _retryActionForward = findForward(BTX_ACTION_FORWARD_RETRY);

    if (_retryActionForward == null) {
      String input = getInput();
      if (input != null) {
        _retryActionForward = new ForwardingActionForward(getInput());
      }
    }

    if (_retryActionForward == null) {
      _retryActionForward = findForward(BTX_GLOBAL_ACTION_FORWARD_RETRY);
    }

    return _retryActionForward;
  }

  /**
   * If true, then when action messages are saved following a BTX action, they will be merged
   * with any messages that already are stored on the request instead of overwriting them.
   * 
   * @return true if prior messages should be preserved.
   */
  public boolean isBtxPreservePriorMessages() {
    return _btxPreservePriorMessages;
  }

  /**
   * If set to true, then when action messages are saved following a BTX action, they will be
   * merged with any messages that already are stored on the request instead of overwriting them.
   * 
   * @param newValue The new setting value.
   */
  public void setBtxPreservePriorMessages(boolean newValue) {
    _btxPreservePriorMessages = newValue;
  }

  /**
   * @return An arbitrary string that is attached to an action.  A common use could be to associate
   * a symbolic name with an action that could be used inside various aspects of an action's
   * implementation to vary behavior (for example, to vary the way a form is reset or validated).
   * In such situations, it is more robust for the code to test for a symbolic name in the Tag
   * property rather than testing the action Path, which could conceivably change.
   */
  public String getTag() {
    return _tag;
  }

  /**
   * An arbitrary string that can be attached to an action.  A common use could be to associate
   * a symbolic name with an action that could be used inside various aspects of an action's
   * implementation to vary behavior (for example, to vary the way a form is reset or validated).
   * In such situations, it is more robust for the code to test for a symbolic name in the Tag
   * property rather than testing the action Path, which could conceivably change.
   * 
   * @param tag the action tag.
   */
  public void setTag(String tag) {
    _tag = tag;
  }

  /**
   * @return
   */
  public boolean isPopulatesRequestFromBtxDetails() {
    return _populatesRequestFromBtxDetails;
  }

  /**
   * @param b
   */
  public void setPopulatesRequestFromBtxDetails(boolean b) {
    _populatesRequestFromBtxDetails = b;
  }

}
