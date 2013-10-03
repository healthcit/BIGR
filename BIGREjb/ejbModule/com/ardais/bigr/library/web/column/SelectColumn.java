package com.ardais.bigr.library.web.column;

import com.ardais.bigr.query.ViewParams;

/**
 * @author dfeldman
 *
 * This is commonly called the "Select" column.  It is the checkbox to add or remove items in
 * the selection and hold views, and the Delivery Type drop-down control in the confirm request
 * screen.
 */
public class SelectColumn extends CompositeSampleColumn {

  private SampleSelectIsPvIsRestrictedIsBmsIdColumn _sampleSelectIsPvIsRestrictedIsBmsIdColumn =
    null;

  /**
   * see superclass.
   */
  public SelectColumn(ViewParams cp) {
    super(
      "Select (checkbox or delivery type)",
      new SampleColumn[] {
        new SampleSelectIsPvIsRestrictedIsBmsIdColumn(cp),
        new DeliveryTypeColumn(cp)});
    _sampleSelectIsPvIsRestrictedIsBmsIdColumn =
      (SampleSelectIsPvIsRestrictedIsBmsIdColumn) getColumns()[0];
  }
  
  /**
   * By default, this column assumes that it supports selection and the the column header
   * and tooltip should reflect that.  Set this to false if the column does not support
   * selection.  This will alter the column header and tooltip appropriately.
   * Currently this only affects the SampleSelectIsPvIsRestrictedIsBmsIdColumn part of
   * this composite column, not the DeliveryTypeColumn part.
   * 
   * @param supportsSelection The new selection-support indicator.
   */
  public void setColumnSupportsSelection(boolean supportsSelection) {
    _sampleSelectIsPvIsRestrictedIsBmsIdColumn.setColumnSupportsSelection(supportsSelection);
  }

}
