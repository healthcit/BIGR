package com.ardais.bigr.pdc.btx;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.pdc.javabeans.PathReportData;

public abstract class BTXDetailsPathReport extends BTXDetails {
  private PathReportData _pathReportData = null;

  /**
   * @return
   */
  public PathReportData getPathReportData() {
    return _pathReportData;
  }

  /**
   * @param data
   */
  public void setPathReportData(PathReportData data) {
    _pathReportData = data;
  }

}
