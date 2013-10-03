package com.ardais.bigr.iltds.web.form;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.assistants.Address;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateRequest;
import com.ardais.bigr.iltds.btx.BtxDetailsFindRequestDetails;
import com.ardais.bigr.iltds.btx.BtxDetailsFulfillRequest;
import com.ardais.bigr.iltds.btx.BtxDetailsRequestOperation;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.RequestSelect;
import com.ardais.bigr.iltds.helpers.RequestType;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.javabeans.RequestBoxDto;
import com.ardais.bigr.javabeans.RequestDto;
import com.ardais.bigr.javabeans.RequestItemDto;
import com.ardais.bigr.javabeans.ShippingPartnerDto;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.orm.helpers.BoxScanData;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * A Struts ActionForm that represents a request.
 */
public class RequestForm extends BigrActionForm {

  private String _requestId = null;
  private String _requestType = null;
  private String _createDate = null;
  private String _state = null;
  private String _status = null;
  private String _requesterId = null;
  private String _requesterName = null;
  private String _deliverToId = null;
  private String _deliverToName = null;
  private String _requesterComments = null;
  private String _destinationId = null;
  private String _destinationName = null;
  private String _requestManagerComments = null;
  private HashMap _requestItemForms = new HashMap();
  private HashMap _requestBoxForms = new HashMap();

  private boolean _getPickListInfo = false;
  private int _itemDetailLevel = 0;
  private int _boxDetailLevel = 0;
  private String _newBoxId = null;
  private String _confirmFlag = null;
  private String[] _fulfilledItems = null;
  private String[] _unfulfilledItems = null;
  private String _confirmRequestManagerComments = null;

  private ResultsHelper _resultsHelper;
  
  private LegalValueSet _itemSortOptions;
  private String _itemSort;
  
  private BoxScanData _boxScanData;
  private String _newBoxLayoutId;
  
  private List _shippingPartners;
  private Address _shippingAddress;
  
  private String _resultsFormDefinitionId;
  private List _resultsFormDefinitions;

  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _requestId = null;
    _requestType = null;
    _createDate = null;
    _state = null;
    _status = null;
    _requesterId = null;
    _requesterName = null;
    _deliverToId = null;
    _deliverToName = null;
    _requesterComments = null;
    _destinationId = null;
    _destinationName = null;
    _requestManagerComments = null;
    _requestItemForms.clear();
    _requestBoxForms.clear();
    _itemDetailLevel = 0;
    _boxDetailLevel = 0;
    _getPickListInfo = false;

    _newBoxId = null;
    _confirmFlag = null;
    _fulfilledItems = null;
    _unfulfilledItems = null;
    _confirmRequestManagerComments = null;

    _resultsHelper = null;
    _itemSortOptions = populateItemSortOptions();
    _itemSort = FormLogic.ITEM_SORT_ORDER_ADDED;
   
    _boxScanData = null;
    _newBoxLayoutId = null;
    
    _shippingPartners = null;
    _shippingAddress = null;
    
    _resultsFormDefinitionId = null;
    _resultsFormDefinitions = null;
  }
  
  //private method to populate a legal value set of the allowed sort options
  //for the items on a request
  private LegalValueSet populateItemSortOptions() {
    //populate the sorting choices for the items on this request
    LegalValueSet itemSortOptions = new LegalValueSet();
    itemSortOptions.addLegalValue(FormLogic.ITEM_SORT_ORDER_ADDED,FormLogic.ITEM_SORT_ORDER_ADDED_TEXT);
    itemSortOptions.addLegalValue(FormLogic.ITEM_SORT_CASE_ID,FormLogic.ITEM_SORT_CASE_ID_TEXT);
    itemSortOptions.addLegalValue(FormLogic.ITEM_SORT_SAMPLE_ID,FormLogic.ITEM_SORT_SAMPLE_ID_TEXT);
    itemSortOptions.addLegalValue(FormLogic.ITEM_SORT_LOCATION,FormLogic.ITEM_SORT_LOCATION_TEXT);
    return itemSortOptions;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    return null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void describeIntoBtxDetails(
    BTXDetails btxDetails0,
    BigrActionMapping mapping,
    HttpServletRequest request) {

    super.describeIntoBtxDetails(btxDetails0, mapping, request);

    // Create the request dto and populate it with the request id and
    // any information the user could've changed.
    RequestDto requestDto = new RequestDto();
    BigrBeanUtilsBean.SINGLETON.copyProperties(requestDto, this);
    requestDto.setId(getRequestId());

    // The fields that could've changed (_deliverToId, _requesterComments, 
    // _destinationId, and _requestManagerComments) are populated via the 
    // BigrBeanUtilsBean.copyProperties call.

    // Now create a list of the individual items.
    for (int i = 0; i < _requestItemForms.size(); i++) {
      RequestItemDto rid = new RequestItemDto();
      RequestItemForm rif = getRequestItemForm(i);
      rif.describeIntoDto(rid);
      requestDto.addItem(rid);
    }

    // Now create a list of the individual boxes.
    for (int i = 0; i < _requestBoxForms.size(); i++) {
      RequestBoxDto rbd = new RequestBoxDto();
      RequestBoxForm rbf = getRequestBoxForm(i);
      rbf.describeIntoDto(rbd);
      requestDto.addBox(rbd);
    }

    // Add the input request dto to the btx details.
    BtxDetailsRequestOperation btx = (BtxDetailsRequestOperation) btxDetails0;
    btx.setInputRequestDto(requestDto);

    // If this is a FindRequestDetails operation, set up some additional fields
    // used to indicate the level of detail to retrieve
    if (btx instanceof BtxDetailsFindRequestDetails) {
      BtxDetailsFindRequestDetails btxFind = (BtxDetailsFindRequestDetails) btx;
      btxFind.setRequestSelect(
        new RequestSelect(false, true, getItemDetailLevel(), getBoxDetailLevel()));
      btxFind.setGetPickListInfo(isGetPickListInfo());
    }

    // If this is a FulfillRequest operation, check if the confirm flag is set. Please
    // note that this check cannot be placed in an else if block because BtxDetailsFulfillRequest
    // is a super class of BtxDetailsFindRequestDetails.
    if (btx instanceof BtxDetailsFulfillRequest) {
      BtxDetailsFulfillRequest btxFulfill = (BtxDetailsFulfillRequest) btx;

      // Set a request select that returns the detailed item information so we can display the alias
      //values for samples on the request.
      btxFulfill.setRequestSelect(RequestSelect.BASIC_PLUS_ITEM_DETAILS);

      // Check for the confirm flag. This is set to true if a partial request was confirmed.
      String confirmFlag = getConfirmFlag();
      if (!ApiFunctions.isEmpty(confirmFlag)) {
        if (confirmFlag.equalsIgnoreCase("true")) {
          btxFulfill.setPartialFulfillConfirmed(true);
        }
        else {
          btxFulfill.setPartialFulfillConfirmed(false);
        }
      }
      else {
        btxFulfill.setPartialFulfillConfirmed(false);
      }
    }

    if (btx instanceof BtxDetailsCreateRequest) {
      // The results helper object is stored in the form because the populateFromBtxDetails method
      // does not have access to the request where this object resides (within the session). The
      // populateFromBtxDetails method will clear the hold list using the results helper object
      // when no errors have been detected and when the results helper object is not null. The result
      // helper object can be null when an action that has 2 form classes (input and result form).
      setResultsHelper(ResultsHelper.get(ResultsHelper.TX_TYPE_SAMP_REQUEST, request));
    }
  }

  /**
   * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails)
   */
  public void populateFromBtxDetails(BTXDetails btxDetails) {
    super.populateFromBtxDetails(btxDetails);

    // Get the request dto and populate this form from it.
    BtxDetailsRequestOperation btx = (BtxDetailsRequestOperation) btxDetails;
    RequestDto requestDto = btx.getOutputRequestDto();
    if (requestDto == null) {
      requestDto = btx.getInputRequestDto();
    }

    // Populate from the request dto.
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, requestDto);

    // Handle any data members that don't map exactly from dto to form
    setRequestId(requestDto.getId());
    if (requestDto.getType() != null) {
      setRequestType(requestDto.getType().toString());
    }
    if (requestDto.getState() != null) {
      setState(requestDto.getState().toString());
    }
    if (requestDto.isClosed()) {
      setStatus("Closed");
    }
    else {
      setStatus("Open");
    }
    if (requestDto.getCreateDate() != null) {
      SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:mm aaa");
      setCreateDate(formatter.format(requestDto.getCreateDate()));
    }

    List items = requestDto.getItems();
    if (!ApiFunctions.isEmpty(items)) {
      for (int i = 0; i < items.size(); i++) {
        RequestItemDto rid = (RequestItemDto) items.get(i);
        String itemId = rid.getItemId();
        RequestItemForm rif = new RequestItemForm();
        rif.populateFromDto(rid);
        setRequestItemForm(i, rif);
      }
    }

    List boxes = requestDto.getBoxes();
    if (!ApiFunctions.isEmpty(boxes)) {
      for (int i = 0; i < boxes.size(); i++) {
        RequestBoxDto rbd = (RequestBoxDto) boxes.get(i);
        RequestBoxForm rbf = new RequestBoxForm();
        rbf.populateFromDto(rbd);
        setRequestBoxForm(i, rbf);
      }
    }

    // If this is a FindRequestDetails operation, set up some additional fields
    // used to indicate the level of detail to retrieve.
    if (btx instanceof BtxDetailsFindRequestDetails) {
      BtxDetailsFindRequestDetails btxFind = (BtxDetailsFindRequestDetails) btx;
      setItemDetailLevel(btxFind.getRequestSelect().getItemInfo());
      setBoxDetailLevel(btxFind.getRequestSelect().getBoxInfo());
      setGetPickListInfo(btxFind.isGetPickListInfo());
    }

    // If this is a FulfillRequest operation, check if the confirm flag is set. Please
    // note that this check cannot be placed in an else if block because BtxDetailsFulfillRequest
    // is a super class of BtxDetailsFindRequestDetails.
    if (btx instanceof BtxDetailsFulfillRequest) {
      BtxDetailsFulfillRequest btxFulfill = (BtxDetailsFulfillRequest) btx;

      // Populate the unfulfilled items array.
      if (!IdList.isEmpty(btxFulfill.getUnfulfilledItems())) {
        String unfulfilledItems[] =
          ApiFunctions.toStringArray(btxFulfill.getUnfulfilledItems().getList());
        setUnfulfilledItems(unfulfilledItems);

        // Populate the confirm request manager comment field.
        Map itemForms = getRequestItemsFormsByItemId();
        StringBuffer comment = new StringBuffer(128);
        comment.append(ApiFunctions.safeString(getRequestManagerComments()));
        comment.append("\n\nThe following expected items were not scanned: ");
        for (int i = 0; i < unfulfilledItems.length; i++) {
          comment.append(unfulfilledItems[i]);
          RequestItemForm unfulfilledItemForm = (RequestItemForm)itemForms.get(unfulfilledItems[i]);
          if (unfulfilledItemForm != null) {
            String alias = unfulfilledItemForm.getItemAlias();
            if (!ApiFunctions.isEmpty(alias)) {
              comment.append(" (");
              comment.append(alias);
              comment.append(")");
            }
          }
          comment.append(" ");
        }
        comment.append("\n\n<ENTER REASON HERE>");
        setConfirmRequestManagerComments(comment.toString());
      }
    }

    if (btx instanceof BtxDetailsCreateRequest) {
      BtxDetailsCreateRequest btxCreate = (BtxDetailsCreateRequest) btx;

      // Check if any errors have occured. BtxAction allows for the result form (using the
      // btxResultPropertyType property) to be called regardless of forward state (error or
      // success). We do not want to clear the hold list if an error has occured. This is one
      // those cases where btx shouldn't call this method on an error, but the current
      // architecture doesn't allow for this so "skip" if an error has been detected.
      if (btx.getActionErrors().empty()) {
        // If this is a transfer request, clear the "hold" list.
        if (RequestType.TRANSFER.equals(btxCreate.getRequestType())) {
          // Check if the helper object has been set.
          ResultsHelper helper = getResultsHelper();
          if (helper != null) {
            synchronized (helper) {
              helper.clearSelectedIds();
              // Now that we succeeded, clear the picklist
              helper.getSelectedSamples().removeIds(helper.getSelectedSamples().getSampleIds());
            }
          }
        }
      }
    }
  }

  /**
   * @return
   */
  public String getRequestId() {
    return _requestId;
  }

  /**
   * @param string
   */
  public void setRequestId(String string) {
    _requestId = string;
  }

  /**
   * @return
   */
  public String getRequestType() {
    return _requestType;
  }

  /**
   * @param string
   */
  public void setRequestType(String string) {
    _requestType = string;
  }

  /**
   * @return
   */
  public String getCreateDate() {
    return _createDate;
  }

  /**
   * @param string
   */
  public void setCreateDate(String string) {
    _createDate = string;
  }

  /**
   * @return
   */
  public String getState() {
    return _state;
  }

  /**
   * @param string
   */
  public void setState(String string) {
    _state = string;
  }

  /**
   * @return
   */
  public String getStatus() {
    return _status;
  }

  /**
   * @param string
   */
  public void setStatus(String string) {
    _status = string;
  }

  /**
   * @return
   */
  public String getRequesterId() {
    return _requesterId;
  }

  /**
   * @param string
   */
  public void setRequesterId(String string) {
    _requesterId = string;
  }

  /**
   * @return
   */
  public String getRequesterName() {
    return _requesterName;
  }

  /**
   * @param string
   */
  public void setRequesterName(String string) {
    _requesterName = string;
  }

  /**
   * @return
   */
  public String getDeliverToId() {
    return _deliverToId;
  }

  /**
   * @param string
   */
  public void setDeliverToId(String string) {
    _deliverToId = string;
  }

  /**
   * @return
   */
  public String getDeliverToName() {
    return _deliverToName;
  }

  /**
   * @param string
   */
  public void setDeliverToName(String string) {
    _deliverToName = string;
  }

  /**
   * @return
   */
  public String getRequesterComments() {
    return _requesterComments;
  }

  /**
   * @param string
   */
  public void setRequesterComments(String string) {
    _requesterComments = string;
  }

  /**
   * @return
   */
  public String getDestinationId() {
    return _destinationId;
  }

  /**
   * @param string
   */
  public void setDestinationId(String string) {
    _destinationId = string;
  }

  /**
   * @return
   */
  public String getDestinationName() {
    return _destinationName;
  }

  /**
   * @param string
   */
  public void setDestinationName(String string) {
    _destinationName = string;
  }

  /**
   * @return
   */
  public String getRequestManagerComments() {
    return _requestManagerComments;
  }

  /**
   * @param string
   */
  public void setRequestManagerComments(String string) {
    _requestManagerComments = string;
  }

  /**
   * @return
   */
  public HashMap getRequestItemForms() {
    return _requestItemForms;
  }
  
  public HashMap getRequestItemsFormsByItemId () {
    HashMap returnValue = new HashMap();
    if (_requestItemForms != null && !_requestItemForms.isEmpty()) {
      Iterator keyIterator = _requestItemForms.keySet().iterator();
      while (keyIterator.hasNext()) {
        RequestItemForm form = (RequestItemForm)_requestItemForms.get(keyIterator.next());
        returnValue.put(form.getItemId(), form);
      }
    }
    return returnValue;
  }

  /**
   * @param map
   */
  public void setRequestItemForms(HashMap map) {
    _requestItemForms = map;
  }

  /**
   * @return
   */
  public List getRequestItemFormsAsList() {
    List result = null;
    if (!_requestItemForms.isEmpty()) {
      result = new ArrayList();
      for (int i = 0; i < _requestItemForms.size(); i++) {
        Integer index = new Integer(i);
        result.add(_requestItemForms.get(index));
      }
    }
    return result;
  }

  /**
   * @return
   */
  public RequestItemForm getRequestItemForm(int index) {
    Integer key = new Integer(index);
    RequestItemForm rif = (RequestItemForm) _requestItemForms.get(key);
    if (rif == null) {
      rif = new RequestItemForm();
      _requestItemForms.put(key, rif);
    }
    return rif;
  }

  /**
   * 
   * @param index
   * @param form
   */
  public void setRequestItemForm(int index, RequestItemForm form) {
    _requestItemForms.put(new Integer(index), form);
  }

  /**
   * @return
   */
  public HashMap getRequestBoxForms() {
    return _requestBoxForms;
  }

  /**
   * @param map
   */
  public void setRequestBoxForms(HashMap map) {
    _requestBoxForms = map;
  }

  /**
   * @return
   */
  public List getRequestBoxFormsAsList() {
    List result = null;
    if (!_requestBoxForms.isEmpty()) {
      result = new ArrayList();
      for (int i = 0; i < _requestBoxForms.size(); i++) {
        Integer index = new Integer(i);
        result.add(_requestBoxForms.get(index));
      }
    }
    return result;
  }

  /**
   * @return
   */
  public RequestBoxForm getRequestBoxForm(int index) {
    Integer key = new Integer(index);
    RequestBoxForm rbf = (RequestBoxForm) _requestBoxForms.get(key);
    if (rbf == null) {
      rbf = new RequestBoxForm();
      _requestBoxForms.put(key, rbf);
    }
    return rbf;
  }

  /**
   * 
   * @param index
   * @param form
   */
  public void setRequestBoxForm(int index, RequestBoxForm form) {
    _requestBoxForms.put(new Integer(index), form);
  }

  /**
   * @return
   */
  public boolean isGetPickListInfo() {
    return _getPickListInfo;
  }

  /**
   * @param b
   */
  public void setGetPickListInfo(boolean b) {
    _getPickListInfo = b;
  }

  /**
   * @return
   */
  public int getItemDetailLevel() {
    return _itemDetailLevel;
  }

  /**
   * @param i
   */
  public void setItemDetailLevel(int i) {
    _itemDetailLevel = i;
  }

  /**
   * @return
   */
  public int getBoxDetailLevel() {
    return _boxDetailLevel;
  }

  /**
   * @param i
   */
  public void setBoxDetailLevel(int i) {
    _boxDetailLevel = i;
  }

  /**
   * @return
   */
  public String getNewBoxId() {
    return _newBoxId;
  }

  /**
   * @param string
   */
  public void setNewBoxId(String string) {
    _newBoxId = string;
  }

  /**
   * @return
   */
  public String getConfirmFlag() {
    return _confirmFlag;
  }

  /**
   * @param string
   */
  public void setConfirmFlag(String string) {
    _confirmFlag = string;
  }

  /**
   * @return
   */
  public String[] getFulfilledItems() {
    return _fulfilledItems;
  }

  /**
   * @param strings
   */
  public void setFulfilledItems(String[] strings) {
    _fulfilledItems = strings;
  }

  /**
   * @return
   */
  public String[] getUnfulfilledItems() {
    return _unfulfilledItems;
  }

  /**
   * @param strings
   */
  public void setUnfulfilledItems(String[] strings) {
    _unfulfilledItems = strings;
  }

  /**
   * @return
   */
  public String getConfirmRequestManagerComments() {
    return _confirmRequestManagerComments;
  }

  /**
   * @param string
   */
  public void setConfirmRequestManagerComments(String string) {
    _confirmRequestManagerComments = string;
  }
  
  /**
   * @return
   */
  public ResultsHelper getResultsHelper() {
    return _resultsHelper;
  }

  /**
   * @param helper
   */
  public void setResultsHelper(ResultsHelper helper) {
    _resultsHelper = helper;
  }

  /**
   * @return
   */
  public String getItemSort() {
    return _itemSort;
  }

  /**
   * @return
   */
  public LegalValueSet getItemSortOptions() {
    return _itemSortOptions;
  }

  /**
   * @param string
   */
  public void setItemSort(String string) {
    _itemSort = string;
  }

  /**
   * @param set
   */
  public void setItemSortOptions(LegalValueSet set) {
    _itemSortOptions = set;
  }

  /**
   * @return
   */
  public BoxScanData getBoxScanData() {
    return _boxScanData;
  }

  /**
   * @param data
   */
  public void setBoxScanData(BoxScanData data) {
    _boxScanData = data;
  }

  /**
   * @return
   */
  public String getNewBoxLayoutId() {
    return _newBoxLayoutId;
  }

  /**
   * @param string
   */
  public void setNewBoxLayoutId(String string) {
    _newBoxLayoutId = string;
  }
  /**
   * @return
   */
  public List getShippingPartners() {
    return _shippingPartners;
  }

  /**
   * @param list
   */
  public void setShippingPartners(List list) {
    _shippingPartners = list;
  }
  
  public LegalValueSet getShippingPartnersAsLegalValueSet() {
    LegalValueSet lvs = new LegalValueSet();
    if (!ApiFunctions.isEmpty(getShippingPartners())) {
      Iterator iterator = getShippingPartners().iterator();
      while (iterator.hasNext()) {
        ShippingPartnerDto partner = (ShippingPartnerDto)iterator.next();
        lvs.addLegalValue(partner.getShippingPartnerId(), partner.getShippingPartnerName());
      }
    }
    return lvs;
  }

  
  public Address getShippingAddress() {
    if (_shippingAddress == null) {
      setShippingAddress(new Address());
    }
    return _shippingAddress;
  }

  /**
   * @param address
   */
  public void setShippingAddress(Address address) {
    _shippingAddress = address;
  }

  public String getResultsFormDefinitionId() {
    return _resultsFormDefinitionId;
  }
  
  public List getResultsFormDefinitions() {
    return _resultsFormDefinitions;
  }
  
  public void setResultsFormDefinitionId(String resultsFormDefinitionId) {
    _resultsFormDefinitionId = resultsFormDefinitionId;
  }
  
  public void setResultsFormDefinitions(List resultsFormDefinitions) {
    _resultsFormDefinitions = resultsFormDefinitions;
  }
}
