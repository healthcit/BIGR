package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * A Data Transfer Object that represents a box layout.
 */
public class BoxLayoutDto implements Serializable {
  static final long serialVersionUID = 6009382839961443278L;

  private String _boxLayoutId;
  private int _numberOfColumns;
  private int _numberOfRows;
  private String _xaxisLabelCid;
  private String _yaxisLabelCid;
  private String _tabIndexCid;

  /**
   * Creates a new BoxLayoutDto.
   */
  public BoxLayoutDto() {
    super();
  }

  /**
   * Creates a new BoxLayoutDto.
   */
  public BoxLayoutDto(BoxLayoutDto boxLayoutDto) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, boxLayoutDto);
  }

  public void populateFromResultSet(Map columns, ResultSet rs) throws SQLException {

    if (columns.containsKey(DbConstants.LY_BOX_LAYOUT_ID)) {
      setBoxLayoutId(rs.getString(DbConstants.LY_BOX_LAYOUT_ID));
    }
    if (columns.containsKey(DbAliases.LY_BOX_LAYOUT_ID)) {
      setBoxLayoutId(rs.getString(DbAliases.LY_BOX_LAYOUT_ID));
    }
    if (columns.containsKey(DbConstants.LY_NUMBER_OF_COLUMNS)) {
      setNumberOfColumns(rs.getInt(DbConstants.LY_NUMBER_OF_COLUMNS));
    }
    if (columns.containsKey(DbAliases.LY_NUMBER_OF_COLUMNS)) {
      setNumberOfColumns(rs.getInt(DbAliases.LY_NUMBER_OF_COLUMNS));
    }
    if (columns.containsKey(DbConstants.LY_NUMBER_OF_ROWS)) {
      setNumberOfRows(rs.getInt(DbConstants.LY_NUMBER_OF_ROWS));
    }
    if (columns.containsKey(DbAliases.LY_NUMBER_OF_ROWS)) {
      setNumberOfRows(rs.getInt(DbAliases.LY_NUMBER_OF_ROWS));
    }
    if (columns.containsKey(DbConstants.LY_X_AXIS_LABEL_CID)) {
      setXaxisLabelCid(rs.getString(DbConstants.LY_X_AXIS_LABEL_CID));
    }
    if (columns.containsKey(DbAliases.LY_X_AXIS_LABEL_CID)) {
      setXaxisLabelCid(rs.getString(DbAliases.LY_X_AXIS_LABEL_CID));
    }
    if (columns.containsKey(DbConstants.LY_Y_AXIS_LABEL_CID)) {
      setYaxisLabelCid(rs.getString(DbConstants.LY_Y_AXIS_LABEL_CID));
    }
    if (columns.containsKey(DbAliases.LY_Y_AXIS_LABEL_CID)) {
      setYaxisLabelCid(rs.getString(DbAliases.LY_Y_AXIS_LABEL_CID));
    }
    if (columns.containsKey(DbConstants.LY_TAB_INDEX_CID)) {
      setTabIndexCid(rs.getString(DbConstants.LY_TAB_INDEX_CID));
    }
    if (columns.containsKey(DbAliases.LY_TAB_INDEX_CID)) {
      setTabIndexCid(rs.getString(DbAliases.LY_TAB_INDEX_CID));
    }
  }
  
  public int getBoxCapacity() {
    return (_numberOfRows * _numberOfColumns);
  }

  /**
   * Get an HTML description of this box layout.  It is commputed from the values of the
   * other box layout attributes.
   * 
   * @return the description.
   */
  public String getDescriptionHtml() {
    StringBuffer sb = new StringBuffer(80);

    sb.append(getNumberOfColumns());
    sb.append("x");
    sb.append(getNumberOfRows());
    sb.append(" x: ");
    Escaper.htmlEscape(GbossFactory.getInstance().getDescription(getXaxisLabelCid()), sb);
    sb.append(", y: ");
    Escaper.htmlEscape(GbossFactory.getInstance().getDescription(getYaxisLabelCid()), sb);
    sb.append(", tab: ");
    Escaper.htmlEscape(GbossFactory.getInstance().getDescription(getTabIndexCid()), sb);

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
  public int getNumberOfColumns() {
    return _numberOfColumns;
  }

  /**
   * @return
   */
  public int getNumberOfRows() {
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
   * @param i
   */
  public void setNumberOfColumns(int i) {
    _numberOfColumns = i;
  }

  /**
   * @param i
   */
  public void setNumberOfRows(int i) {
    _numberOfRows = i;
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
