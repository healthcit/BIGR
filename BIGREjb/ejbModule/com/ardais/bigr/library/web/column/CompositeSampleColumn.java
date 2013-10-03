package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.library.web.column.configuration.ColumnManager;
import com.ardais.bigr.query.ViewParams;

/**
 * @author dfeldman
 *
 * This is a grouping of columns for convenience.
 */
public class CompositeSampleColumn implements SampleColumn {

  private SampleColumn[] _columns;
  private String _description;
  
  /**
   * Creates a SampleColumn that groups together other columns under a single name.
   * Protected access -- it is intended to be called by subclasses.
   */
  protected CompositeSampleColumn(String description, SampleColumn[] cols) {
    this._columns = cols;
    this._description = description;
  }
  
  protected SampleColumn[] getColumns() {
    return _columns;
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumn#getHeader()
   */
  public String getHeader() {
    StringBuffer sb = new StringBuffer();
    for (int i=0; i<_columns.length; i++) {
      sb.append(_columns[i].getHeader());
    }
    return sb.toString();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumn#getColumnDescription()
   */
  public String getColumnDescription() {
    return _description;
  }
  
  public String getRawHeaderText() {
    return _description;
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumn#getSummaryHeader()
   */
  public String getSummaryHeader() {
    StringBuffer sb = new StringBuffer();
    for (int i=0; i<_columns.length; i++) {
      sb.append(_columns[i].getSummaryHeader());
    }
    return sb.toString();

  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumn#getWidthHeader()
   */
  public String getWidthHeader() {
    StringBuffer sb = new StringBuffer();
    for (int i=0; i<_columns.length; i++) {
      sb.append(_columns[i].getWidthHeader());
    }
    return sb.toString();

  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumn#getWidthBody()
   */
  public String getWidthBody() {
    StringBuffer sb = new StringBuffer();
    for (int i=0; i<_columns.length; i++) {
      sb.append(_columns[i].getWidthBody());
    }
    return sb.toString();

  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  public String getRawBody(SampleRowParams rp) throws IOException {
    StringBuffer sb = new StringBuffer();
    for (int i=0; i<_columns.length; i++) {
      sb.append(_columns[i].getRawBody(rp));
    }
    return sb.toString();
  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumn#getBody(SampleRowParams)
   */
  public String getBody(SampleRowParams rp) throws IOException {
    StringBuffer sb = new StringBuffer();
    for (int i=0; i<_columns.length; i++) {
      sb.append(_columns[i].getBody(rp));
    }
    return sb.toString();

  }

  /**
   * @see com.ardais.bigr.library.web.column.SampleColumn#getHeaderTooltip()
   */
  public String getHeaderTooltip() {
    StringBuffer sb = new StringBuffer();
    for (int i=0; i<_columns.length; i++) {
      sb.append(_columns[i].getHeaderTooltip());
      sb.append("<p>\n");
    }
    return sb.toString();
  }

  /**
   * This is always shown, but its components may all not be shown, in which case nothing
   * will happen when this is rendered.
   */
  public boolean isShown() {
    return true;
  }

  public void setShown(ViewParams vp) {
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumn#getKey()
   */
  public String getKey() {
    try {
      return ColumnManager.instance().getKey(this.getClass());
    } catch (ClassNotFoundException e) {
      throw new ApiException("Column not registered with ColumnManager",e);
    }
  }

	@Override
	public Boolean getDesc()
	{
		return null;
	}

	@Override
	public void setDesc(Boolean isDesc)
	{
	}

	@Override
	public boolean sortable()
	{
		return false;
	}

	public String getBodyForCSV(SampleRowParams rp) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<_columns.length; i++)
		{
			if (i != 0)
			{
				sb.append(",");
			}
			sb.append("\"").append(_columns[i].getRawBody(rp)).append("\"");
		}
		return sb.toString();
	}

	public String getHeaderForCSV()
	{
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<_columns.length; i++)
		{
			if (i != 0)
			{
				sb.append(",");
			}
			sb.append("\"").append(_columns[i].getRawHeaderText()).append("\"");
		}
		return sb.toString();
	}

	@Override
	public String getColumnMetadataKey()
	{
		return null;
	}
}
