package com.ardais.bigr.orm.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Struts ActionForm class for a box layout.
 */
public class BoxLayoutForm extends BigrActionForm {

  private String _boxLayoutId;
  private String _numberOfColumns;
  private String _numberOfRows;
  private String _xaxisLabelCid;
  private String _yaxisLabelCid;
  private String _tabIndexCid;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {

    _boxLayoutId = null;
    _numberOfColumns = null;
    _numberOfRows = null;
    _xaxisLabelCid = null;
    _yaxisLabelCid = null;
    _tabIndexCid = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    // Nothing to validate here.
    return null;
  }
  
  public String getBoxLayoutAttributes() {
    StringBuffer sb = new StringBuffer(80);

    sb.append(getNumberOfColumns());
    sb.append("x");
    sb.append(getNumberOfRows());
    sb.append(" x: ");
    sb.append(GbossFactory.getInstance().getDescription(getXaxisLabelCid()));
    sb.append(", y: ");
    sb.append(GbossFactory.getInstance().getDescription(getYaxisLabelCid()));
    sb.append(", tab: ");
    sb.append(GbossFactory.getInstance().getDescription(getTabIndexCid()));

    return sb.toString();
  }

  /**
   * @return
   */
  public String getBoxLayoutId() {
    return _boxLayoutId;
  }

  /**
   * @return
   */
  public String getNumberOfColumns() {
    return _numberOfColumns;
  }

  /**
   * @return
   */
  public String getNumberOfRows() {
    return _numberOfRows;
  }

  /**
   * @return
   */
  public String getTabIndexCid() {
    return _tabIndexCid;
  }

  /**
   * @return
   */
  public String getXaxisLabelCid() {
    return _xaxisLabelCid;
  }

  /**
   * @return
   */
  public String getYaxisLabelCid() {
    return _yaxisLabelCid;
  }

  /**
   * @param string
   */
  public void setBoxLayoutId(String string) {
    _boxLayoutId = string;
  }

  /**
   * @param string
   */
  public void setNumberOfColumns(String string) {
    _numberOfColumns = string;
  }

  /**
   * @param string
   */
  public void setNumberOfRows(String string) {
    _numberOfRows = string;
  }

  /**
   * @param string
   */
  public void setTabIndexCid(String string) {
    _tabIndexCid = string;
  }

  /**
   * @param string
   */
  public void setXaxisLabelCid(String string) {
    _xaxisLabelCid = string;
  }

  /**
   * @param string
   */
  public void setYaxisLabelCid(String string) {
    _yaxisLabelCid = string;
  }
}
