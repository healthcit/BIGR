package com.ardais.bigr.library.btx;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.javabeans.DiagnosticTestFilterDto;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.query.sorting.SortByColumn;
import com.ardais.bigr.userprofile.ViewProfile;

/**
 * @author dfeldman
 *
 * BTX request object to get samples (both tissue and RNA)
 * 
 */
public class BTXDetailsGetSamples extends BTXDetails {

  // These HOLD_CATEGORY_* constants are category names that gets used as elements of
  // category sets that are passed to the setCategoriesToDetermine method.  These are useful
  // for operating on just parts of a hold list in a multi-category hold list view.  
  // NOTE: What you add HOLD_CATEGORY_* constants, also insert them into HOLD_CATEGORY_ALL
  // and possibly other sets in the static initializer block in this class. 
  //
  public static final String HOLD_CATEGORY_ARDAIS = "HoldArdais";
  public static final String HOLD_CATEGORY_BMS_AVAILABLE = "HoldBmsAvail";
  public static final String HOLD_CATEGORY_BMS_ON_PENDING_REQUEST = "HoldBmsUnavail";
  public static final String HOLD_CATEGORY_BMS_UNAVAILABLE = "HoldBmsPending";
  
  /**
   * A set containing all of the HOLD_CATEGORY_* values defined above, for use with the
   * {@link #setCategoriesToDetermine(Set)} method.
   */
  public static final Set HOLD_CATEGORY_ALL;
  /**
   * A set containing only the HOLD_CATEGORY_ARDAIS value defined above, for use with the
   * {@link #setCategoriesToDetermine(Set)} method.
   */
  public static Set HOLD_CATEGORY_ARDAIS_ONLY;

  private String[] _sampleIds;
  private String _orderNumber;
  private ViewParams _viewParams;
  private int _currentChunk = -1;
  private boolean _retrieveAllChunks = false;
  private String _productType; // tissue or rna, if any

  private List _sampleDetailsResult;
  private ViewProfile _viewProfile;
  private DiagnosticTestFilterDto _diagnosticTestFilterDto;
  private Set _categoriesToDetermine = null;
  private Map _detailsResultCategories = null;
  private List _urls;
  
  private Set _sampleTypes;
  
  //id of any specified results form definition to use for retrieving/displaying data.  If no value is
  //specified then the view to use will be determined from the user profile data.
  private String _resultsFormDefinitionId;
  
  //results form definition to use to retrieve/display data.  If this is specified it takes
  //precedence over any value in resultsFormDefinitionId.  This is currently only used by
  //label printing functionality, since the results form definitions used there are not
  //persisted and thus cannot be identified by an id (they are maintained in a config file)
  private BigrFormDefinition _resultsFormDefinition = null;

  private Set<SortByColumn> _sortColumns;

  // static initializer
  static {
    Set tempSet = new HashSet();
    tempSet.add(HOLD_CATEGORY_ARDAIS);
    tempSet.add(HOLD_CATEGORY_BMS_AVAILABLE);
    tempSet.add(HOLD_CATEGORY_BMS_ON_PENDING_REQUEST);
    tempSet.add(HOLD_CATEGORY_BMS_UNAVAILABLE);
    HOLD_CATEGORY_ALL = Collections.unmodifiableSet(tempSet);

    tempSet = new HashSet();
    tempSet.add(HOLD_CATEGORY_ARDAIS);
    HOLD_CATEGORY_ARDAIS_ONLY = Collections.unmodifiableSet(tempSet);
  }
  
  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_GET_SAMPLES;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    return null;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#doGetDetailsAsHTML()
   */
  protected String doGetDetailsAsHTML() {
    String msg = "BTXDetailsGetSamples.doGetDetailsAsHTML() method called in error!";
    return msg;
  }

  /**
   * Returns the sampleIds.
   * @return String[]
   */
  public String[] getSampleIds() {
    return _sampleIds;
  }

  /**
   * Sets the sampleIds.
   * @param sampleIds The sampleIds to set
   */
  public void setSampleIds(String[] sampleIds) {
    this._sampleIds = sampleIds;
  }

  /**
   * Returns the sampleDetailsResult.
   * @return List
   */
  public List getSampleDetailsResult() {
    return _sampleDetailsResult;
  }

  /**
   * Sets the sampleDetailsResult.
   * @param sampleDetailsResult The sampleDetailsResult to set
   */
  public void setSampleDetailsResult(List sampleDetailsResult) {
    this._sampleDetailsResult = sampleDetailsResult;
  }

  // =========   Single Sample/RNA usage ============================
  // -- this is convenient when using introspection to set the rnaVialId
  // common id names are supported so introspection will "find" them
  // rnaVialId and sampleId, currently
  //=================================================================

  public void setRnaVialId(String id) {
    setSampleIds(new String[] { id });
  }

  /**
   * Get the vial ID.  assumes this object is configured with one (single) id.
   */
  public String getRnaVialId() {
    return getSampleIds()[0];
  }

  public void setSampleId(String id) {
    setSampleIds(new String[] { id });
  }

  /**
   * Get the vial ID.  assumes this object is configured with one (single) id.
   */
  public String getSampleId() {
    return getSampleIds()[0];
  }

  public ProductDto getSingleData() {
    List res = getSampleDetailsResult();
    if (res.isEmpty())
      throw new ApiException("No matching RNA sample could be found");
    return (ProductDto) res.get(0);
  }

  /**
   * @return Returns the viewProfile.
   */
  public ViewProfile getViewProfile() {
    return _viewProfile;
  }
  
  /**
   * @param viewProfile The viewProfile to set.
   */
  public void setViewProfile(ViewProfile viewProfile) {
    _viewProfile = viewProfile;
  }

  /**
   * Returns the (String) orderNumber for Order Line data retreival.
   * @return String
   */
  public String getOrderNumber() {
    return _orderNumber;
  }

  /**
   * Sets the (String) orderNumber for requests for order line data.
   * @param orderNumber The orderNumber to set
   */
  public void setOrderNumber(String orderNumber) {
    _orderNumber = orderNumber;
  }

  /**
   * Method getViewParams.
   * @return ViewParams
   */
  public ViewParams getViewParams() {
    return _viewParams;
  }

  /**
   * Sets the viewParams.
   * @param viewParams The viewParams to set
   */
  public void setViewParams(ViewParams viewParams) {
    _viewParams = viewParams;
  }

  /**
   * Returns the currentChunk.
   * @return int
   */
  public int getCurrentChunk() {
    return _currentChunk;
  }

  /**
   * Sets the currentChunk.
   * @param currentChunk The currentChunk to set
   */
  public void setCurrentChunk(int currentChunk) {
    _currentChunk = currentChunk;
  }

	public boolean getRetrieveAllChunks()
	{
		return _retrieveAllChunks;
	}

	public void setRetrieveAllChunks(boolean allChunks)
	{
		this._retrieveAllChunks = allChunks;
  }

  // REMOVED product type.  CURRENTLY product type is set early to put ResHelper in the right state
  // setting later, along the way, from the BTX makes it hard to keep the state of the RH right.

  /**
   * Returns the productType.
   * @return String
   */
  //  public String getProductType() {
  //    return _productType;
  //  }

  /**
   * Sets the productType.
   * @param productType The productType to set
   */
  //  public void setProductType(String productType) {
  //    _productType = productType;
  //  }

  /**
   * @return
   */
  public DiagnosticTestFilterDto getDiagnosticTestFilterDto() {
    return _diagnosticTestFilterDto;
  }

  /**
   * @param dto
   */
  public void setDiagnosticTestFilterDto(DiagnosticTestFilterDto dto) {
    _diagnosticTestFilterDto = dto;
  }

  /**
   * @see #setCategoriesToDetermine(Set)
   */
  public Set getCategoriesToDetermine() {
    return _categoriesToDetermine;
  }

  /**
   * Define the set of inventory item category names that will be populated in the results of
   * a transaction.  Currently this is only used by the performGetHoldListData performer method
   * in BtxPerformerHoldListManager, and the items in the set must be zero or more of the
   * HOLD_CATEGORY_* constants defined in this class.
   * 
   * @param set The set of category names.
   * @see #setDetailsResultCategories(Map)
   */
  public void setCategoriesToDetermine(Set set) {
    _categoriesToDetermine = set;
  }

  /**
   * @see #setDetailsResultCategories(Map)
   */
  public Map getDetailsResultCategories() {
    return _detailsResultCategories;
  }

  /**
   * @param map The map of category names to lists of inventory items in those categories.
   *   The items in the lists are ProductDto objects.
   * @see #setCategoriesToDetermine(Set)
   */
  public void setDetailsResultCategories(Map map) {
    _detailsResultCategories = map;
  }

  /**
   * @return
   */
  public List getUrls() {
    return _urls;
  }

  /**
   * @param list
   */
  public void setUrls(List list) {
    _urls = list;
  }

  /**
   * @return
   */
  public Set getSampleTypes() {
    return _sampleTypes;
  }

  /**
   * @param set
   */
  public void setSampleTypes(Set set) {
    _sampleTypes = set;
  }
  
  public String getResultsFormDefinitionId() {
    return _resultsFormDefinitionId;
  }
  
  public void setResultsFormDefinitionId(String resultsFormDefinitionId) {
    _resultsFormDefinitionId = resultsFormDefinitionId;
  }
  
  /**
   * @return Returns the resultsFormDefinition.
   */
  public BigrFormDefinition getResultsFormDefinition() {
    return _resultsFormDefinition;
  }
  
  /**
   * @param resultsFormDefinition The resultsFormDefinition to set.
   */
  public void setResultsFormDefinition(BigrFormDefinition resultsFormDefinition) {
    _resultsFormDefinition = resultsFormDefinition;
  }

	public Set<SortByColumn> getSortColumns()
	{
		return _sortColumns;
	}

	public void setSortColumns(Set<SortByColumn> _sortColumns)
	{
		this._sortColumns = _sortColumns;
	}

	public void addSortColumn(SortByColumn column)
	{
		if (_sortColumns == null)
		{
			_sortColumns = new HashSet<SortByColumn>();
		}
		_sortColumns.add(column);
	}

	public void removeSorting()
	{
		this._sortColumns = null;
	}
}
