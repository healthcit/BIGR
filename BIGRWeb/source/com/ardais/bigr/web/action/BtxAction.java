package com.ardais.bigr.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.iltds.btx.BTXActionForward;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails;
import com.ardais.bigr.util.StrutsUtils;
import com.ardais.bigr.web.form.BigrActionForm;
import com.ardais.bigr.web.form.PopulatesRequestFromBtxDetails;
import com.ardais.bigr.dataImport.web.form.CaseForm;
import com.ardais.bigr.dataImport.web.form.SampleForm;
import com.ardais.bigr.pdc.attachment.AttachmentDB;

/**
 * BTX-specific extensions to the BigrAction Struts base class.
 */
public class BtxAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping,
   *     BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected final ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    // Create and initialize the appropriate BTXDetails instance for
    // this business transaction.
    //
    BTXDetails btxDetails = createBtxDetails(mapping, form, request);

    // Make the the BTX details available (as a request attribute) to the
    // resource we forward to, in case it wants it.  We replace this later
    // with progressively more up-to-date versions of the BTXDetails
    // object as the transaction proceeds.
    //
    request.setAttribute("btxDetails", btxDetails);

    // Call doBtxPerform to do all of the transaction-specific work.
    //
    BTXDetails resultBtxDetails = doBtxPerform(btxDetails, mapping, form, request, response);

    // Make the result details available to the resource we forward to,
    // in case it wants it.
    //
    request.setAttribute("btxDetails", resultBtxDetails);

    saveErrorsAndMessages(mapping, request, resultBtxDetails);

    // Transform the results from the BTXDetails object to a
    // developer-specified class (in struts-config.xml) and make that
    // object available as an attribute of the HTTP request object.
    //
    transferResultDetailsToRequest(resultBtxDetails, mapping, form, request);
    
  
    //for SWP-1110, 
    AttachmentDB dbAccessor = new AttachmentDB();
    boolean isDeletingAttachment =((request.getParameter("deleteAttachId") != null && !"".equals(request.getParameter("deleteAttachId")))?true:false);
  
    //for SWP-1110, retrieve attachments for caseform if any, the best place to do this is inside 
    //BTX frame, however, due to the lack of understanding about BTX at now, do the retrieve here
    if(form instanceof  CaseForm ) {
    System.out.println("I am in consent ="+ ((CaseForm)form).getConsentId());
    
    //delete attachment if asked
    if(isDeletingAttachment) {
      dbAccessor.deleteAttachment(request.getParameter("deleteAttachId")); 
     }
     
    
    List attachments = dbAccessor.getAttachments(form);
    ((CaseForm)form).setAttachments(dbAccessor.convertAttachmentDataToHelper(attachments));
    }
    
    if(form instanceof  SampleForm ) {
      System.out.println("I am in sample ="+ ((SampleForm)form).getSampleData().getSampleId());
      
      //delete attachment if asked
      if(isDeletingAttachment) {
        dbAccessor.deleteAttachment(request.getParameter("deleteAttachId")); 
       }
       
      
      List attachments = dbAccessor.getAttachments(form);
      ((SampleForm)form).getSampleData().setAttachments(dbAccessor.convertAttachmentDataToHelper(attachments));
    }
    
    // Look up the Struts ActionForward corresponding to the information
    // in the BtxActionForward objected that was returned by the BTX
    // transaction.
    //
    ActionForward actionForward =
      findActionForward(mapping, resultBtxDetails, resultBtxDetails.getActionForward());
      
    if(isDeletingAttachment) {
     actionForward = mapping.findForward("deleteSuccess");
  
     }
             
    return actionForward;
  }

  /**
   * Create, initialize and return the BTXDetails instance that will be
   * passed to the BTX transaction method that performs this business
   * transaction.
   */
  private BTXDetails createBtxDetails(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request) {

    BTXDetails btxDetails = newBtxDetailsInstance(mapping, form, request);

    initializeAllBtxDetails(btxDetails, mapping, form, request);

    return btxDetails;
  }

  /**
   * Create an instance of the appropriate BTXDetails subclass for use
   * with this business transaction.  BTX-specific properties in the
   * BigrActionMapping specify what class to instantiate.  Specifically,
   * the class is determined by the BtxDetailsType property that was
   * defined in the struts-config.xml file for this action.
   */
  private BTXDetails newBtxDetailsInstance(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request) {

    BTXDetails btxDetails = null;

    Class clazz = mapping.getBtxDetailsClass();

    if (clazz == null) {
      throw new IllegalStateException(
        "Cannot create BTXDetails instance, no btxDetailsType "
          + "is defined for this transaction.");
    }

    try {
      btxDetails = (BTXDetails) clazz.newInstance();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    return btxDetails;
  }

  /**
   * Initialize the supplied BTXDetails object.  This sets the
   * transaction-begin timestamp and the user id of the user who is
   * performing the transaction.  It then calls the overridable
   * initializeBtxDetails method to complete the initialization.
   */
  private void initializeAllBtxDetails(
    BTXDetails btxDetails,
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request) {

    btxDetails.setBeginTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
   
    btxDetails.setLoggedInUserSecurityInfo(getSecurityInfo(request));
    
    btxDetails.setSubActionType(request.getParameter("subActionType"));
    
    initializeBtxDetails(btxDetails, mapping, form, request);
  }

  /**
   * Perform action-specific initialization of the supplied BTXDetails
   * object.  When the BTX framework calls this, the BTXDetails object
   * will already have had its user id, account id, and begin-timestamp
   * properties set.
   * 
   * <p>By default, this method behaves as follows: if the specified
   * ActionForm is not null and it implements the
   * {@link DescribableIntoBtxDetails} interface (which all subclasses of
   * BigrActionForm do), then it calls the form's describeIntoBtxDetails
   * method.  In situations where that isn't sufficient to completely
   * initialize the BTXDetails instance, users may override this method.
   * The default implementation of
   * {@link BigrActionForm#describeIntoBtxDetails(BTXDetails, BigrActionMapping, HttpServletRequest)}
   * copies JavaBean properties from the form to corresponding properties on
   * the BTXDetails object, performing basic type conversions if necessary
   * (such as converting strings to integers or booleans).  See the Javadoc
   * for that method for more details.
   */
  protected void initializeBtxDetails(
    BTXDetails btxDetails0,
    BigrActionMapping mapping,
    BigrActionForm form0,
    HttpServletRequest request) {

    if ((form0 != null)) {
      form0.describeIntoBtxDetails(btxDetails0, mapping, request);
    }
  }

  /**
   * Invoke the business transaction, passing it the supplied BTXDetails
   * instance.  BTX-specific properties in the BigrActionMapping specify
   * what class/method to call to perform the BTX transaction.
   * Specifically, the class/method are determined by either the
   * BtxTransactionType property or the
   * BtxPerformerAccessBeanType/BtxPerformerMethodName properties
   * that are defined in the specified mapping (the
   * BtxPerformerAccessBeanType/BtxPerformerMethodName properties should
   * be considered deprecated).
   */
  protected static final BTXDetails invokeBusinessTransaction(
    BTXDetails btxDetails,
    BigrActionMapping mapping) {

    String txType = mapping.getBtxTransactionType();

    if (ApiFunctions.isEmpty(txType)) {
      throw new IllegalStateException(
        "Cannot invoke business transaction, no "
          + "btxTransactionType is defined for this transaction.");
    }

    btxDetails.setTransactionType(txType);

    BTXDetails resultBtxDetails = Btx.perform(btxDetails);

    return resultBtxDetails;
  }

  /**
   * Do the transaction-specific work of performing the business transaction.
   * This default implementation may be enough for many transactions, and
   * if so there's no need to create a subclass of this for each business
   * transaction.  Simply set the action type to this class in your
   * struts-config.xml.  If you do find yourself needing to subclass
   * this class for your business transaction, this method and/or the
   * {@link #initializeBtxDetails(BTXDetails, BigrActionMapping, BigrActionForm, HttpServletRequest) initializeBtxDetails}
   * method are the ones you are most likely to need to override.
   * 
   * <p>If you need to override this method, call
   * {@link #invokeBusinessTransaction(BTXDetails, BigrActionMapping) invokeBusinessTransaction}
   * at the point where you need to invoke the BTX business transaction
   * session bean method that is configured in the BigrActionMapping for
   * this action.
   */
  protected BTXDetails doBtxPerform(
    BTXDetails btxDetails0,
    BigrActionMapping mapping,
    BigrActionForm form0,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    // Perform the transaction.
    //
    BTXDetails resultBtxDetails = invokeBusinessTransaction(btxDetails0, mapping);
   
    return resultBtxDetails;
  }

  private void saveErrorsAndMessages(
    BigrActionMapping mapping,
    HttpServletRequest request,
    BTXDetails resultBtxDetails) {

    boolean preservePrior = mapping.isBtxPreservePriorMessages();

    // Convert errors/messages from the BTX transaction to Struts
    // ActionErrors and save them to the request object in the standard
    // Struts way.
    //
    saveErrors(
      request,
      StrutsUtils.convertBtxActionErrorsToStruts(resultBtxDetails.getActionErrors()),
      preservePrior);

    //save any messages returned from the Btx transaction
    saveMessages(request, resultBtxDetails.getActionMessages(), preservePrior);
  }

  /**
   * Transform the result BTXDetails into another object and make that
   * object available as an attribute of the HTTP request.  There are
   * two ways to do this:
   * <ul>
   * <li>The object type and request attribute name can be specified in the
   * struts-config.xml file using the custom action properties
   * BtxResultPropertyType and BtxResultPropertyName, respectively.  Neither
   * property has a default, and if one of them is specified, they must both
   * be specified.  The class specified by BtxResultPropertyType must
   * implement the {@link PopulatableFromBtxDetails} interface and have a
   * zero-argument constructor.  The BTX framework will create an instance
   * of the class and call its populateFromBtxDetails method, passing the
   * result BTXDetails object as a parameter, and then set the specified HTTP
   * request attribute to this object.  To assist with implementing
   * populateFromBtxDetails, you may want to use the 
   * {@link BigrBeanUtilsBean#copyProperties(Object, Object)} method.
   * </li>
   * <li>In struts-config.xml, specify the custom action property
   * BtxCopyResultsToActionForm with a value of "true".  The BTX framework
   * will call the populateFromBtxDetails method on the form instance that
   * is involved in the action.  The default implementation of this method
   * on {@link BigrActionForm} calls
   * {@link BigrBeanUtilsBean#copyProperties(Object, Object)}, which uses
   * reflection to copy properties from the result BTXDetails object to
   * the form object.
   * </li>
   * </ul>
   * 
   * These two approaches are mutually exclusive.  If neither is used,
   * the result BTXDetails object isn't copied anywhere.  However, regardless
   * of what properties are set, the result BTXDetails object is always
   * made available as-is in a request attribute named "btxDetails".
   * 
   * @param resultBtxDetails the transaction's result BTXDetails
   * @param mapping the action mapping
   * @param request the HTTP request
   */
  private void transferResultDetailsToRequest(
    BTXDetails resultBtxDetails,
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request) {

    if (mapping.isBtxCopyResultsToActionForm()) {
      // First see if we're supposed to copy the result details to the
      // form instance that was passed to the doPerform method for
      // this action.

      if ((form != null) && (form instanceof PopulatableFromBtxDetails)) {
        form.populateFromBtxDetails(resultBtxDetails);
      }
    }
    else {
      // If we get here, we weren't supposed to copy the result details
      // to the ActionForm.  Next, see if we're supposed to copy the
      // result details to a user-specified class that implements
      // PopulatableFromBtxDetails.

      String btxResultPropertyName = mapping.getBtxResultPropertyName();
      Class btxResultPropertyClass = mapping.getBtxResultPropertyClass();

      if ((!ApiFunctions.isEmpty(btxResultPropertyName) && (btxResultPropertyClass == null))
        || (ApiFunctions.isEmpty(btxResultPropertyName) && (btxResultPropertyClass != null))) {
        throw new IllegalStateException(
          "When one of the btxResultPropertyName and "
            + "btxResultPropertyType properties is specified, they must "
            + "both be specified: "
            + btxResultPropertyName
            + "/"
            + mapping.getBtxResultPropertyType());
      }

      if (btxResultPropertyClass != null) {
        PopulatableFromBtxDetails resultObject = null;
        try {
          resultObject = (PopulatableFromBtxDetails) btxResultPropertyClass.newInstance();
        }
        catch (Exception e) {
          ApiFunctions.throwAsRuntimeException(e);
        }

        resultObject.populateFromBtxDetails(resultBtxDetails);
        request.setAttribute(btxResultPropertyName, resultObject);
      }
    }
    //if there is request-based information to be stored, do that now
    if (mapping.isPopulatesRequestFromBtxDetails()) {
      if (form instanceof PopulatesRequestFromBtxDetails) {
        ((PopulatesRequestFromBtxDetails)form).populateRequestFromBtxDetails(resultBtxDetails, mapping, request);
      }
      else {
        throw new IllegalStateException("When a mapping has it's populatesRequestFromBtxDetails"
        + " value set to true, the form used by that mapping must implement"
        + " the PopulatesRequestFromBtxDetails interface. Mapping path is: "
        + mapping.getPath());
      }
    }
  }

  /**
   * Finds and returns the Struts ActionForward for this action.  By default, this looks up the
   * Struts ActionForward corresponding to the information in the BTXActionForward object that 
   * passed to this method, which is the one returned by the BTX transaction.  Subclasses can
   * override this to implement a more complicated algorithm for determining the ActionForward,
   * but are encouraged to use the simple mechanism based on struts-config entries when possible.  
   */
  protected ActionForward findActionForward(
    BigrActionMapping mapping,
    BTXDetails btxDetails,
    BTXActionForward btxForward) {

    return mapping.findForward(btxForward);
  }

}
