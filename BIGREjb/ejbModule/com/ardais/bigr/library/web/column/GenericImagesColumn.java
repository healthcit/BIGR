package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;

/**
 */
public class GenericImagesColumn extends ImagesColumn {

  /* 
   * Show on all screens except for RNA on hold, since the fixed image column alone is
   * sufficient (MR6428)
   */
  public GenericImagesColumn(ViewParams cp) {
    super(
      "library.ss.result.header.images.label",
      "library.ss.result.header.images.comment",
      37,
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_CUST | ColumnPermissions.ROLE_DI,
      ColumnPermissions.SCRN_CONFIRM_REQ | ColumnPermissions.SCRN_HOLD_LIST |
      ColumnPermissions.SCRN_ORDER_DET | ColumnPermissions.SCRN_SELECTION | ColumnPermissions.SCRN_ICP | ColumnPermissions.SCRN_DERIV_OPS_SUMMARY,
      cp);
  }

}
