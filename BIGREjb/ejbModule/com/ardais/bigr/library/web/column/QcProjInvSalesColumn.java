package com.ardais.bigr.library.web.column;

import com.ardais.bigr.query.ViewParams;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class QcProjInvSalesColumn extends CompositeSampleColumn {

  /**
   * Constructor for CompositionValuesColumn.
   * @param description
   * @param cols
   */
  public QcProjInvSalesColumn(ViewParams cp) {
    super(
      "Internal Statuses (inv, qc, sales, proj, qc post)",
      new SampleColumn[] {
        new InventoryStatusColumn(cp),
        new QcStatusColumn(cp),
        new SalesStatusColumn(cp),
        new ProjectStatusColumn(cp),
        new QcPostedColumn(cp)});
  }

}
