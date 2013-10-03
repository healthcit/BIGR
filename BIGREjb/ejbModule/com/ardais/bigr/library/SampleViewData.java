package com.ardais.bigr.library;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.library.btx.BTXDetailsGetSamples;
import com.ardais.bigr.library.web.column.SampleColumn;
import com.ardais.bigr.library.web.column.SelectColumn;
import com.ardais.bigr.library.web.column.configuration.ColumnWriterList;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.query.ColumnConstants;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.query.sorting.SortByColumn;
import com.ardais.bigr.query.sorting.SortOrder;
import com.ardais.bigr.util.ArtsConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is all the information needed to support display of a view of inventory item details,
 * including data items and columns to show.
 */
public class SampleViewData {

  private static Map _attributeToColumnMap = new HashMap();

   // here is where the front-end / back-end mapping of column keys to the classes that
   // actually render those columns as HTML is made.
   // NOTE in WSAD, browsing references to these classes will not show this usage!
   static {
    
     /*
     Buffer Type           CA11155C
     Cells/ml              CA11157C
     Date of Collection    CA11152C
     Format Detail         CA11149C
     Gross Appearance      CA11147C
     Percent Viability     CA11158C
     Tissue                CA11146C
     Total Number of Cells CA11156C
     Volume                CA11151C
     Weight                CA11745C
     Date of Preservation  CA11153C

     Comment               CA11154C
     Fixative Type         CA11148C
     Prepared By           CA11150C
     Procedure             CA11145C
     */

     String[] bufferTypeColumns = {ColumnConstants._BUFFER_TYPE};
     _attributeToColumnMap.put("CA11155C", bufferTypeColumns);
    
     String cellsPerMlColumns[] = {ColumnConstants._CELLS_PER_ML};
     _attributeToColumnMap.put("CA11157C", cellsPerMlColumns);
    
     String dateOfCollectionColumns[] = {ColumnConstants._DATE_OF_COLLECTION, ColumnConstants._ELAPSED_TIME};
     _attributeToColumnMap.put("CA11152C", dateOfCollectionColumns);
     
     String dateOfPreservationColumns[] = {ColumnConstants._DATE_OF_PRESERVATION};
     _attributeToColumnMap.put("CA11153C", dateOfPreservationColumns);

     String formatDetailColumns[] = {ColumnConstants._FORMAT_DETAIL};
     _attributeToColumnMap.put("CA11149C", formatDetailColumns);

     String fixativeTypeColumns[] = {ColumnConstants._FIXATIVE_TYPE};
     _attributeToColumnMap.put("CA11148C", fixativeTypeColumns);

     String grossAppearanceColumns[] = {ColumnConstants._APPEARANCE};
     _attributeToColumnMap.put("CA11147C", grossAppearanceColumns);
    
     String percentViabilityColumns[] = {ColumnConstants._PERCENT_VIABILITY};
     _attributeToColumnMap.put("CA11158C", percentViabilityColumns);

     String tissueColumns[] = {ColumnConstants._TISSUE};
     _attributeToColumnMap.put("CA11146C", tissueColumns);

     String totalNumOfCellsColumns[] = {ColumnConstants._TOTAL_NUM_OF_CELLS};
     _attributeToColumnMap.put("CA11156C", totalNumOfCellsColumns);

     String volumeColumns[] = {ColumnConstants._VOLUME};
     _attributeToColumnMap.put(ArtsConstants.ATTRIBUTE_VOLUME, volumeColumns);
     
     String weightColumns[] = {ColumnConstants._WEIGHT};
     _attributeToColumnMap.put(ArtsConstants.ATTRIBUTE_WEIGHT, weightColumns);

     String concentrationColumns[] = {ColumnConstants._CONCENTRATION};
     _attributeToColumnMap.put(ArtsConstants.ATTRIBUTE_CONCENTRATION, concentrationColumns);

     String yieldColumns[] = {ColumnConstants._YIELD};
     _attributeToColumnMap.put(ArtsConstants.ATTRIBUTE_YIELD, yieldColumns);
   }

  /**
   * The prefix to use for the id of the HTML element that contains a comma-separated list of
   * all of the inventory item ids in this view, in order. 
   * 
   * @see #getAllItemsElementId()
   */
  public static final String ALL_ITEMS_PREFIX = "allItems";

  // Be careful changing the defaults below.  There is probably code (e.g. Javascript) that
  // depends on things like the default itemSelectorName and viewElementId.
  //
  private List _sampleHelpers = null;
  private ColumnWriterList _columns = null;
  private ViewParams _viewParams;
  private String _viewElementId = "inventoryItemViewElem";
  private int _firstItemIndex = 0;
  private boolean _includeItemSelector = true;
  private String _itemSelectorName = "samples";
  private boolean _groupedByCase = false;
  private String _category = null;
  private String _itemViewElementsStartedCallback = null;
  private Map _categorizedHelpers = null;
  private String _htmlForEmptyDisplay = null;
  private List _urls = null;
  private boolean isSorted = false;

  public SampleViewData(BTXDetailsGetSamples btxDetails) {
    // productDtos: a list of ProductDto objects that will be wrapped by
    // SampleSelectionHelper objects.
    List productDtos = btxDetails.getSampleDetailsResult();
    // colList: the column list for this view.
    List columnDescriptors = btxDetails.getViewProfile().toColumnDescriptors();
    ViewParams viewParams = btxDetails.getViewParams();

    if (viewParams == null) {
      throw new ApiException("A valid ViewParams object must be supplied.");
    }
    _viewParams = viewParams;

    if (columnDescriptors == null) {
      columnDescriptors = new ArrayList(); // empty list, no display.  Use for internal use, like Hold IDs
    }

    _columns = new ColumnWriterList(columnDescriptors, viewParams);
	markSortedColumns(btxDetails.getSortColumns());

    Map helpersMap = new HashMap(1361);
    _sampleHelpers = sampleHelpersForProductDtos(productDtos, helpersMap);
    defineCategories(btxDetails, helpersMap);
    
    _urls = btxDetails.getUrls();
  }

  /**
   * Initialize the item categories from information in the supplied BTXDetails, if it contains
   * categorized items.
   * 
   * @param btxDetails The BTXDetails object.
   * @param helpersMap A map that maps {@link ProductDto} instances to {@link SampleSelectionHelper}
   *   instances that wrap them.  The BTXDetailsGetSamples object we receive as a parameter has
   *   the category members represented as ProductDto objects, but the category members we need
   *   in this class are SampleSelectionHelper objects that wrap ProductDto objects.
   *   To save time and memory, we don't want to wrap the same ProductDto more than once.  So,
   *   every time we need a SampleSelectionHelper for a given ProductDto, we first look in the
   *   map to see if we already have one.  If so, we use it, otherwise we create a new
   *   SampleSelectionHelper and add it to the map.  So, the helpersMap parameter is an
   *   input/output parameter.
   */
  private void defineCategories(BTXDetailsGetSamples btxDetails, Map helpersMap) {
    Map categories = btxDetails.getDetailsResultCategories();
    if (categories == null) {
      return;
    }
    for (Iterator iter = categories.entrySet().iterator(); iter.hasNext();) {
      Map.Entry mapEntry = (Map.Entry) iter.next();
      String categoryName = (String) mapEntry.getKey();
      List categoryContents = (List) mapEntry.getValue();
      setCategoryHelpers(categoryName, sampleHelpersForProductDtos(categoryContents, helpersMap));
    }
  }

  /**
   * Given a list of {@link ProductDto} objects, return a list of {@link SampleSelectionHelper}
   * objects that wrap them, in the same order as the input list.
   * 
   * @param productDtos The list of ProductDto objects.
   * @param helpersMap A map that maps {@link ProductDto} instances to {@link SampleSelectionHelper}
   *   instances that wrap them.   To save time and memory, we don't want to wrap the same
   *   ProductDto more than once.  So, every time we need a SampleSelectionHelper for a
   *   given ProductDto, we first look in the  map to see if we already have one.  If so,
   *   we use it, otherwise we create a new SampleSelectionHelper and add it to the map.
   *   So, the helpersMap parameter is an input/output parameter.
   * @return The list of SampleSelectionHelper objects.
   */
  private List sampleHelpersForProductDtos(List productDtos, Map helpersMap) {
    if (productDtos == null) {
      return Collections.EMPTY_LIST;
    }

    List helpers = new ArrayList(productDtos.size());

    for (Iterator iter = productDtos.iterator(); iter.hasNext();) {
      ProductDto productDto = (ProductDto) iter.next();
      SampleSelectionHelper helper = (SampleSelectionHelper) helpersMap.get(productDto);
      if (helper == null) {
        helper = new SampleSelectionHelper(productDto);
        helpersMap.put(productDto, helper);
      }
      helpers.add(helper);
    }

    return helpers;
  }

  /**
   * Returns the columnList.
   * @return ColumnList
   */
  public ColumnWriterList getColumns() {
    return _columns;
  }

  /**
   * @return The list of sample helpers for this view.  What items this returns is influenced
   *    by the value of {@link #getCategory()}.  If getCategory() is null or empty,
   *    getSampleHelpers() will return the same thing as {@link #getAllSampleHelpers()}.
   *    Otherwise getSampleHelpers() will return the same thing as 
   *    getCategoryHelpers(getCategory()).  This is guaranteed not to return null.
   * 
   * @see #getCategory()
   * @see #getCategoryHelpers(String)
   */
  public List getSampleHelpers() {
    List result = null;

    String category = getCategory();

    if (ApiFunctions.isEmpty(category)) {
      result = getAllSampleHelpers();
    }
    else {
      result = getCategoryHelpers(getCategory());
    }

    if (result == null) {
      result = Collections.EMPTY_LIST;
    }

    return result;
  }

  /**
   * @return The list of all sample helpers, regardless of category.
   *   This is guaranteed not to return null.
   * 
   * @see #getSampleHelpers()
   * @see #getCategoryHelpers(String)
   */
  public List getAllSampleHelpers() {
    return _sampleHelpers;
  }

  /**
   * @return The list of all sample helpers for the specified category.
   *   This is guaranteed not to return null.
   * 
   * @see #getSampleHelpers()
   * @see #getAllSampleHelpers()
   */
  public List getCategoryHelpers(String categoryName) {
    List result = null;

    if (_categorizedHelpers == null) {
      result = null;
    }
    else {
      result = (List) _categorizedHelpers.get(categoryName);
    }

    if (result == null) {
      result = Collections.EMPTY_LIST;
    }

    return result;
  }

  /**
   * Define the list of {@link SampleSelectionHelper} instances that represent a particular
   * category of inventory item data.  This is used in views where we show samples in groups,
   * for example the multi-category hold list view.
   * 
   * @param categoryName The category name to associate the list of helpers with.
   * @param helpers The list of helpers.
   */
  private void setCategoryHelpers(String categoryName, List helpers) {
    if (_categorizedHelpers == null) {
      _categorizedHelpers = new HashMap();
    }
    _categorizedHelpers.put(categoryName, helpers);
  }

  /**
   * Method addHoldAmounts.
   * @param m
   */
  public void addHoldAmounts(Map m) {
    int len = _sampleHelpers.size();
    for (int i = 0; i < len; i++) {
      SampleSelectionHelper helper = (SampleSelectionHelper) _sampleHelpers.get(i);
      if (helper.isRna()) {
        Integer amt = (Integer) m.get(helper.getRnaVialId());
        helper.setAmountOnHold(amt);
      }
    }
  }

  /**
   * Method sortByDonorCaseAsm.
   */
  public void sortByDonorCaseAsm() {
    // Sort both the main list of helpers and the list in any categories.
    SampleSelectionHelper.sortByDonorCaseAsm(_sampleHelpers);

    if (_categorizedHelpers != null) {
      for (Iterator iter = _categorizedHelpers.values().iterator(); iter.hasNext();) {
        List categoryItems = (List) iter.next();
        SampleSelectionHelper.sortByDonorCaseAsm(categoryItems);
      }
    }
  }

  /**
   * @return The ViewParams for this view.
   */
  public ViewParams getViewParams() {
    return _viewParams;
  }

  /**
   * @return The zero-based index of the first item (e.g. sample) in this chunk
   *   relative to the entire result set.  For example, if we have a 5000-item
   *   result set and the chunk size is 1000, then this index for the first
   *   item in the third chunk would be 2000.  The default is 0.
   */
  public int getFirstItemIndex() {
    return _firstItemIndex;
  }

  /**
   * @return True if the items to include in this view are grouped by case id.  When this is
   *   true, the generated view HTML will include elements that visually separate items in
   *   different cases.  The default is false. 
   */
  public boolean isGroupedByCase() {
    return _groupedByCase;
  }

  /**
   * @return True if each item row should include a checkbox to select the row.  The default
   *   is true.
   * @see #getItemSelectorName() 
   */
  public boolean isIncludeItemSelector() {
    return _includeItemSelector;
  }

  /**
   * @return The name for the checkbox element that is used to select detail rows.  If
   *   {@link #isIncludeItemSelector()} is false, item selector elements won't be generated
   *   so this is ignored in that case.  The default is "samples".  
   */
  public String getItemSelectorName() {
    return _itemSelectorName;
  }

  /**
   * @return The name of a javascript function that will be called when
   *   the HTML element that contains the item detail rows has had its
   *   start tag generated.  The supplied function must not assume that
   *   the element has been completely generated yet.  The supplied function
   *   will be passed a single argument when it is called: the HTML element
   *   that contains the item detail rows.  The supplied function may assume
   *   that the element will have its id attribute defined.  The default is null, meaning
   *   that there is no callback is needed.
   */
  public String getItemViewElementsStartedCallback() {
    return _itemViewElementsStartedCallback;
  }

  /**
   * @return The id to give the HTML element that contains the entire inventory item
   *   details view.  The default is "inventoryItemViewElem".
   */
  public String getViewElementId() {
    return _viewElementId;
  }

  /**
   * @return The id to give the HTML element that contains a comma-separated list of
   *   all of the inventory item ids in this view, in order.  This is
   *   {@link #ALL_ITEMS_PREFIX} prepended to {@link #getViewElementId()}.
   */
  public String getAllItemsElementId() {
    return ALL_ITEMS_PREFIX + ApiFunctions.safeString(getViewElementId());
  }

  /**
   * @param i
   */
  public void setFirstItemIndex(int i) {
    _firstItemIndex = i;
  }

  /**
   * @param b
   */
  public void setGroupedByCase(boolean b) {
    _groupedByCase = b;
  }

  /**
   * @param newValue
   */
  public void setIncludeItemSelector(boolean newValue) {
    boolean oldValue = _includeItemSelector;
    _includeItemSelector = newValue;

    // When this changes, we may need to call a special method on the
    // SelectColumn column if it is among the column in out column
    // list.  This method adjusts the columns header and tooltip.  For this to be effective,
    // this must be called before column headers are generated.  For now we assume there's
    // at most one copy of this column and stop searching as soon as we find it.
    //
    if (oldValue != newValue) {
      List columns = getColumns().getColumns();
      if (columns != null) {
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
          SampleColumn column = (SampleColumn) iter.next();
          if (column instanceof SelectColumn) {
            SelectColumn col = (SelectColumn) column;
            col.setColumnSupportsSelection(newValue);
            break;
          }
        }
      }
    }
  }

  /**
   * @param string
   */
  public void setItemSelectorName(String string) {
    _itemSelectorName = string;
  }

  /**
   * @param string
   */
  public void setItemViewElementsStartedCallback(String string) {
    _itemViewElementsStartedCallback = string;
  }

  /**
   * @param string
   */
  public void setViewElementId(String string) {
    _viewElementId = string;
  }

  /**
   * @return The name of the item category for this view.  This influences what set of inventory
   *    items is returned by {@link #getSampleHelpers()}.  If this is null or empty,
   *    getSampleHelpers() will return the same thing as {@link #getAllSampleHelpers()}.
   *    Otherwise getSampleHelpers() will return the same thing as 
   *    getCategoryHelpers(getCategory()).
   * 
   * @see #getCategoryHelpers(String)
   */
  public String getCategory() {
    return _category;
  }

  /**
   * @see #getCategory()
   */
  public void setCategory(String string) {
    _category = string;
  }

  /**
   * @return The HTML to display instead of the usual item table when there are no items
   *    to display.
   */
  public String getHtmlForEmptyDisplay() {
    return _htmlForEmptyDisplay;
  }

  /**
   * @param The HTML to display instead of the usual item table when there are no items
   *    to display.
   */
  public void setHtmlForEmptyDisplay(String string) {
    _htmlForEmptyDisplay = string;
  }

  /**
   * @return
   */
  public List getUrls() {
    return _urls;
  }

	public boolean getIsSorted()
	{
		return this.isSorted;
	}

  private static List getColumnNamesBySampleAttributeSet(Set sampleAttributeSet) {
    List result = new ArrayList();
    
    Iterator iterator = sampleAttributeSet.iterator();
    while (iterator.hasNext()) {
      String sampleAttributeCui = (String)iterator.next();
      String[] columnNames = (String[])_attributeToColumnMap.get(sampleAttributeCui);
      
      if (!ApiFunctions.isEmpty(columnNames)) {
        for (int i = 0; i < columnNames.length; i++) {
          result.add(columnNames[i]);
        }
      }
    }
    return result;
  }

	private void markSortedColumns(Set<SortByColumn> sorted)
	{
		if (sorted != null)
		{
			for (SortByColumn sortCol : sorted)
			{
				for (Object col : _columns.getColumns())
				{
					final SampleColumn column = (SampleColumn) col;
					if (sortCol.getColumn().equals(column.getColumnMetadataKey()))
					{
						column.setDesc(SortOrder.DESC.equals(sortCol.getOrder()));
						this.isSorted = true;
						break;
					}
				}
			}
		}
	}
}
