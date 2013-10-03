package com.ardais.bigr.library.btx;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BTXHistoryRecord;
import com.ardais.bigr.library.helpers.ImplicitFilterContext;
import com.ardais.bigr.query.filters.FilterSet;
import com.ardais.bigr.query.filters.RnaFilters;
import com.ardais.bigr.query.filters.SampleFilters;
import com.ardais.bigr.query.sorting.SortByColumn;
import com.ardais.bigr.userprofile.QueryProfile;

/**
 * @author dfeldman
 *
 * Superclass for BTXDetails objects that generate queries from filters.  
 */

/**
 * @deprecated.  subclasses now have different getFilters behavior.  Re-introduce when converting getSQL BTX action.
 */
public abstract class BTXDetailsForSampleSummary extends BTXDetails {

  // The internal codes to represent querys for different products
  public static final String REQUEST_TYPE_RNA = "rna";
  public static final String REQUEST_TYPE_TISSUE = "sample";

  private Map _filters; // filters constructed from the QueryForm object
  // filters constructed from the QueryForm object

  private String _productDescription; // for display and logging

  private ImplicitFilterContext _filterContext = null;

  // filters constructed from the QueryForm object
  private FilterSet _filterSet; // auto generated when filters map is set

  private Set<SortByColumn> _sortColumns;

  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getDirectlyInvolvedObjects()
   */
  public Set getDirectlyInvolvedObjects() {
    // Currently none of the subclasses of this class index their transaction history records
    // by anything.

    return Collections.EMPTY_SET;
  }

  /**
   * Fill a business transaction history record object with information
   * from this transaction details object.  This method will set <b>all</b>
   * fields on the history record, even ones not used by the this type of
   * transaction.  Fields that aren't used by this transaction type will be
   * set to their initial default values.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will have its fields set to
   *    the transaction information.
   */
  public void describeIntoHistoryRecord(BTXHistoryRecord history) {
    super.describeIntoHistoryRecord(history);

    String productDescription = ApiFunctions.safeString(getProductDescription());

    String description =
      productDescription.equals(REQUEST_TYPE_TISSUE)
        ? "Tissue"
        : productDescription.equals(REQUEST_TYPE_RNA)
        ? "RNA"
        : "Unknown query type";
    history.setAttrib2(description);

    // log the summary for whichever product this is a request for
    if (productDescription.equals(REQUEST_TYPE_TISSUE)) {
      history.setClob1(new SampleFilters(getFilterSet()).toString());
    }
    else if (productDescription.equals(REQUEST_TYPE_RNA)) {
      history.setClob2(new RnaFilters(getFilterSet()).toString());
    }
  }

  /**
   * Populate the fields of this object with information contained in a
   * business transaction history record object.  This method must set <b>all</b>
   * fields on this object, as if it had been newly created immediately before
   * this method was called.  A runtime exception is thrown if the transaction type
   * represented by the history record doesn't match the transaction type represented
   * by this object.
   * <p>
   * This method is only meant to be used internally by the business
   * transaction framework implementation.  Please don't use it anywhere else.
   *
   * @param history the history record object that will be used as the
   *    information source.  A runtime exception is thrown if this is null.
   */
  public void populateFromHistoryRecord(BTXHistoryRecord history) {
    super.populateFromHistoryRecord(history);

    setProductDescription(history.getAttrib2());

    // These fields don't correspond to anything in the history record
    // but we must set them anyways.
    //
    _filterContext = null;
    _filters = null;
    _filterSet = null;
  }

  /**
   * Sets the filters.
   * @param filters The filters to set
   */
  public void setFilters(Map filterMap) {
    _filters = filterMap;
    _filterSet = new QueryProfile(filterMap);
  }

  /**
  	 * Method setProductDescription.  Currently either "RNA" or "Tissue"
  	 * @param string
  	 */
  public void setProductDescription(String productDesc) {
    _productDescription = productDesc;
  }

  public String getProductDescription() {
    return _productDescription;
  }

  public FilterSet getFilterSet() {
    return _filterSet;
  }

  public void setFilterSet(FilterSet fs) {
    _filterSet = fs;
  }

  /**
   * @see com.ardais.bigr.query.filters.RnaFilters#isCriteriaSelected()
   */
  public boolean isRnaCriteriaSelected() {
    boolean isLegacyRNAEnabled = ApiProperties.isEnabledLegacy();
    if (! isLegacyRNAEnabled) {
      return false;
    }
    return !new RnaFilters(_filterSet).isEmpty(); // @todo slow.  just use isCritSel()?
  }

  /**
   * @see com.ardais.bigr.query.filters.SampleFilters#isCriteriaSelected()
   */
  public boolean isSampleCriteriaSelected() {
    return !new SampleFilters(_filterSet).isEmpty();
  }

  /**
   * @return
   */
  public ImplicitFilterContext getFilterContext() {
    return _filterContext;
  }

  /**
   * @param context
   */
  public void setFilterContext(ImplicitFilterContext context) {
    _filterContext = context;
  }

	public void setSortColumns(Set<SortByColumn> _sortColumns)
	{
		this._sortColumns = _sortColumns;
	}

	public Set<SortByColumn> getSortColumns()
	{
		return this._sortColumns;
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
