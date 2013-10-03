package com.ardais.bigr.library.web.column;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.library.SampleViewData;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;

public class SampleRowParams {

  private int _itemIndexInView = 0;

  private SampleSelectionHelper _itemHelper = null;

  private SampleViewData _itemViewData = null;

  /**
   * Set the row-dependent parameters.
   * 
   * @param itemIndexInView   the zero-based index of the sample in the current result view chunk.
   * @param itemHelper   the sample selection helper with data about the sample to display
   * @param includeSelector   true if this row will contain a row selector.  This doesn't actually
   *   generate the selector control (SampleSelectIsPvIsRestrictedIsBmsIdColumn does that, if the
   *   corresponding column is in the set of result columns), but it is needed to specify
   *   whether additional row-specific support for selection needs to be generated.
   */
  public SampleRowParams(
    int itemIndexInView,
    SampleSelectionHelper itemHelper,
    SampleViewData itemViewData) {

    _itemIndexInView = itemIndexInView;
    _itemHelper = itemHelper;
    _itemViewData = itemViewData;
  }

  /**
   * @return The zero-based index of the item in the current result view chunk.  To get the
   *   item's zero-based index in the overall result set, call
   *   {@link #getItemIndexInOverallResultSet()}.
   */
  public int getItemIndexInView() {
    return _itemIndexInView;
  }

  /**
   * @return The zero-based index of the item in the overall result set.  This returns
   *   <code>getItemIndexInView() + getItemViewData().getFirstItemIndex()</code>.
   *   To get the item's zero-based index in the current result view chunk, call
   *   {@link #getItemIndexInView()}
   */
  public int getItemIndexInOverallResultSet() {
    return _itemIndexInView + _itemViewData.getFirstItemIndex();
  }

  /**
   * @return
   */
  public SampleSelectionHelper getItemHelper() {
    return _itemHelper;
  }

  /**
   * @return
   */
  public SampleViewData getItemViewData() {
    return _itemViewData;
  }

  /**
   * Return the HTML for the opening TR tag for this inventory item row.
   */
  public String getRowTagHtml() {
    SampleViewData viewData = getItemViewData();
    SampleSelectionHelper ssh = getItemHelper();
    StringBuffer sb = new StringBuffer(80);

    String rowClass = ssh.getRowClass();
    String rowClassUnselected = ssh.getRowClassUnselected();
    if (ApiFunctions.isEmpty(rowClass)) {
      rowClass = "white";
    }
    if (ApiFunctions.isEmpty(rowClassUnselected)) {
      rowClassUnselected = "white";
    }

    sb.append("\n<tr class=\"");
    sb.append(rowClass);
    sb.append('"');
    if (!"white".equals(rowClassUnselected)) {
      sb.append(" unselClass=\"");
      sb.append(rowClassUnselected);
      sb.append('"');
    }
    if (viewData.isIncludeItemSelector()) {
      sb.append(" selId=\"");
      sb.append(ssh.getSelectorId());
      sb.append('"');
    }
    sb.append('>');

    return sb.toString();
  }

}
