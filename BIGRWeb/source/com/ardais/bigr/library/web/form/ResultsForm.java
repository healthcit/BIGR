package com.ardais.bigr.library.web.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateRequest;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateResearchRequest;
import com.ardais.bigr.iltds.btx.BtxDetailsCreateTransferRequest;
import com.ardais.bigr.javabeans.RequestDto;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.helper.ResultsHelper;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author JThompson
 *
 * This Form holds data from the results page.  That page is submitted for purposes like
 * adding to a hold lis or a sample request, or paging to the next chunk.
 */
public class ResultsForm extends BigrActionForm {

  private String[] _samples;
  private String[] _id;
  private String[] _amount;
  private String _currentChunk;
  private String _nextChunk;
  private String _productType;
  private String _txType;
  private String _first;
  private String _action;

  //fields for pick list generation
  private String _deliverTo;
  private String _requestType;
  
  private String _resultsFormDefinitionId;

	private String _sortedColumn;
	private boolean _isDescSort;

  public ResultsForm() {
    reset();
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    reset();
  }

  private void reset() {
    _samples = null;
    _id = null;
    _amount = null;
    _currentChunk = null;
    _nextChunk = null;
    _productType = null;
    _txType = null;
    _first = null;
    _action = null;
    _deliverTo = null;
    _requestType = null;
    _resultsFormDefinitionId = null;
  }

  public BTXDetailsGetSamples getBtxGetSamples(
    SecurityInfo secInfo,
    int screenBits,
    String productType) {
    BTXDetailsGetSamples btx = new BTXDetailsGetSamples();
    btx.setLoggedInUserSecurityInfo(secInfo, true);
    int prod = ResultsHelper.getProductBits(productType);
    int tx = ResultsHelper.getTxBits(_txType);
    ViewParams vp = new ViewParams(secInfo, prod, screenBits, tx);
    btx.setViewParams(vp);
    //if a results form definition to use for querying/rendering the details
    //was specified, pass it along to the code that will get the results
    btx.setResultsFormDefinitionId(_resultsFormDefinitionId);

    return btx;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  public ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {

    ActionErrors errors = new ActionErrors();

    // if we have any amounts specified, they must all be valid entries
    // particularly, the user cannot have left them blank with "ANY" as the value
    if (!ApiFunctions.isEmpty(_amount)) {
      for (int i = 0; i < _amount.length; i++) {
        if (_amount[i] == null || _amount[i].equals(Constants.ANY)) {
          ActionError e = new ActionError("library.ss.error.noamountspecified");
          errors.add(null, e);
        }
      }
    }
    return errors;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void describeIntoBtxDetails(
    BTXDetails btxDetails0,
    BigrActionMapping mapping,
    HttpServletRequest request) {

    super.describeIntoBtxDetails(btxDetails0, mapping, request);

    // Exactly how to populate the BTXDetail depends on what kind of request we're processing
    // (e.g. Transfer vs. Research).

    if (btxDetails0 instanceof BtxDetailsCreateTransferRequest) {
      // Create the request dto and populate it.
      RequestDto dto = new RequestDto();

      dto.setDestinationId(this.getDeliverTo());

      ResultsHelper helper = ResultsHelper.get(ResultsHelper.TX_TYPE_SAMP_REQUEST, request);
      synchronized (helper) {
        //MR7105 - do not use helper.getSelectedSamples().getSampleIds() to populate the dto
        //items, as this method does not return the sample ids in the order in which they
        //were selected.
        List samples = helper.getHelpers().getSampleHelpers();
        for (int i = 0; i < samples.size(); i++) {
          SampleSelectionHelper ssHelper = (SampleSelectionHelper) samples.get(i);
          dto.addItem(ssHelper.getId());
        }
      }

      // Add the manifest dto to the btx details.
      BtxDetailsCreateRequest btx = (BtxDetailsCreateRequest) btxDetails0;
      btx.setInputRequestDto(dto);
    }
    else if (btxDetails0 instanceof BtxDetailsCreateResearchRequest) {
      // Create the request dto and populate it.  For research requests, all we need to
      // populate at this point is the InputRequestDto property, setting it to a RequestDto
      // that only has its items property set (to a list of RequestItemDto objects).  Each
      // RequestItemDto object only needs to have its itemId property set.  Unlike Transfer
      // requests, which get the set of samples to request from the ResultsHelper, Research
      // requests get the list of items ids to request from the form's "samples" property.
      // The order of ids in the samples property (an array) is the order that the items are
      // placed on the request.

      RequestDto dto = new RequestDto();

      String[] itemsIds = getSamples();
      for (int i = 0; i < itemsIds.length; i++) {
        dto.addItem(itemsIds[i]);
      }

      // Add the manifest dto to the btx details.
      BtxDetailsCreateRequest btx = (BtxDetailsCreateRequest) btxDetails0;
      btx.setInputRequestDto(dto);
    }
  }

  /**
   * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails)
   */
  public void populateFromBtxDetails(BTXDetails btxDetails) {

    super.populateFromBtxDetails(btxDetails);

    if (btxDetails instanceof BtxDetailsCreateRequest) {
      // Get the manifest dto and populate this form from it.
      BtxDetailsCreateRequest btx = (BtxDetailsCreateRequest) btxDetails;
      RequestDto dto = btx.getOutputRequestDto();
      BigrBeanUtilsBean.SINGLETON.copyProperties(this, dto);
    }
  }

  /**
   * Returns the array of IDs for the selected samples (via checkboxes on GUI, for instance)
   * @return String[]
   */
  public String[] getSamples() {
    if (_samples == null)
      _samples = new String[0];
    return _samples;
  }

  /**
   * Sets the samples.
   * @param samples The samples to set
   */
  public void setSamples(String[] samples) {
    _samples = samples;
  }

  /**
   * Returns the currentChunk.
   * @return int
   */
  public int getCurrentChunkAsInt() {
    if (_currentChunk == null)
      return 0;
    try {
      return Integer.parseInt(_currentChunk);
    }
    catch (NumberFormatException e) {
      return 0;
    }
  }

  public String getCurrentChunk() {
    return _currentChunk;
  }

  /**
   * Returns the nextChunk.
   * @return String
   */
  public String getNextChunk() {
    return _nextChunk;
  }

  /**
   *  Returns the next chunk.
   * @return int index of next chunk selected 
   */
  public int getNextChunkAsInt() {
    if ("true".equals(getFirst())) {
      return 0;
    }
    else {
      try {
        return Integer.parseInt(_nextChunk);
      }
      catch (NumberFormatException e) {
        return 0;
      }
    }
  }

  /**
   * Returns the productType.
   * @return String
   */
  public String getProductType() {
    return _productType;
  }

  /**
   * Sets the currentChunk.
   * @param currentChunk The currentChunk to set
   */
  public void setCurrentChunk(String currentChunk) {
    _currentChunk = currentChunk;
  }

  /**
   * Sets the nextChunk.
   * @param nextChunk The nextChunk to set
   */
  public void setNextChunk(String nextChunk) {
    _nextChunk = nextChunk;
  }

  /**
   * Sets the productType.
   * @param productType The productType to set
   */
  public void setProductType(String productType) {
    _productType = productType;
  }

  /**
   * @return the String "true" if we should view the first page. 
   */
  public String getFirst() {
    return _first;
  }

  /**
   * Sets the flag indicating if this is the first view of the samples.  
   * The flag is used in chunking and paging through chunks.
   * @param first the value of the flag.
   */
  public void setFirst(String first) {
    _first = first;
  }

  /**
   * Returns the txType.
   * @return String
   */
  public String getTxType() {
    return _txType;
  }

  /**
   * Sets the txType.
   * @param txType The txType to set
   */
  public void setTxType(String txType) {
    _txType = txType;
  }

  /**
   * Create a map of selected ids and amounts for those IDs.  Generally, we either just have IDs
   * or we have both ID's and amounts.  ID's and amounts are sent in via the 
   * attributes: "id" and "amount" respectively.
   * Whereas ids alone are sent in via the attribute "samples"
   * 
   * If both are sent in, it will mean that the Samples are added without amounts, and the ids
   * will be added with amounts.  IDs with amounts will override IDs without amounts.
   */
  public Map getIdsAndAmounts() {
    Map result = new HashMap();
    String[] selIds = getSamples();
    for (int i = 0; i < selIds.length; i++) {
      result.put(selIds[i], null);
    }

    if (getId() != null) {
      String[] ids = getId();
      String[] amts = getAmount();
      for (int i = 0; i < ids.length; i++) {
        Integer amtint = null;
        try {
          amtint = new Integer(amts[i]);
        }
        catch (NumberFormatException e) {
          // leave it null if there is "any" or blank amount
        }
        result.put(ids[i], amtint); // replace ids if we have an amt
      }
    }

    return result;
  }

  /**
   * Returns the amounts that positionally correspond to the ID's (for RNA products).
   * @return String[]
   */
  public String[] getAmount() {
    return _amount;
  }

  /**
   * Returns the ids of the RNA's ordered with corresponding amounts.
   * @return String[]
   */
  public String[] getId() {
    return _id;
  }

  /**
   * Sets the amount for an RNA item (corresponds positionally to rnavialid array).
   * @param amount The amount to set
   */
  public void setAmount(String[] amount) {
    _amount = amount;
  }

  /**
   * Sets the id for RNA that also has amounts in the other array (in positional correspondence).
   * @param id The id to set
   */
  public void setId(String[] id) {
    _id = id;
  }

  /**
   * Returns the deliverTo.
   * @return String
   */
  public String getDeliverTo() {
    return _deliverTo;
  }

  /**
   * Returns the requestType.
   * @return String
   */
  public String getRequestType() {
    return _requestType;
  }

  /**
   * Sets the deliverTo.
   * @param deliverTo The deliverTo to set
   */
  public void setDeliverTo(String deliverTo) {
    _deliverTo = deliverTo;
  }

  /**
   * Sets the requestType.
   * @param requestType The requestType to set
   */
  public void setRequestType(String requestType) {
    _requestType = requestType;
  }

  /**
   * @return The specific action that the user request (for example, from the Hold List page,
   *   users can perform several actions such as removing items from the hold list or requesting
   *   items for delivery).  This is used by HoldDispatchAction and possibly other places.
   */
  public String getAction() {
    return _action;
  }

  /**
   * @param action The specific action that the user request (for example, from the Hold List page,
   *   users can perform several actions such as removing items from the hold list or requesting
   *   items for delivery).  This is used by HoldDispatchAction and possibly other places.
   */
  public void setAction(String action) {
    _action = action;
  }
  
  public String getResultsFormDefinitionId() {
    return _resultsFormDefinitionId;
  }
      
  public void setResultsFormDefinitionId(String resultsFormDefinitionId) {
    _resultsFormDefinitionId = resultsFormDefinitionId;
  }

	public boolean getIsDescSort()
	{
		return _isDescSort;
	}

	public void setIsDescSort(boolean _isDescSort)
	{
		this._isDescSort = _isDescSort;
	}

	public String getSortedColumn()
	{
		return _sortedColumn;
	}

	public void setSortedColumn(String _sortedColumn)
	{
		this._sortedColumn = _sortedColumn;
	}
}
