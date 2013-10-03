package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.query.ViewParams;

/**
 * @author dfeldman
 *
 * The interface for any object that outputs itself into a results table for 
 * Sample Selection.
 * 
 */
public interface SampleColumn {

  /**
   * @return the unique String identifier for this column.
   */
  String getKey() ;
  
  /**
   * @return the HTML that forms the header table cell.
   */
  String getHeader();

  /**
   * @return the human readable description of this column.
   * This is for accessing and manipulating the column in ColumnConfigurations, not for 
   * displaying sample results.
   */
  String getColumnDescription();

  /**
   * @returns a non-escaped version of the header text.
   */
  String getRawHeaderText();

  /**
   * Seems to be used for column arrange screen (old version) but always the same as getheader.
   */
  String getSummaryHeader();

  /**
   * @return the col element with the width for the header.  May have different 
   * alignment than getWidthBody() but has the same numeric width
   */
  String getWidthHeader();

  /**
   * @return the col element with the width and col attributes for the body column
   */
  String getWidthBody();
  
  /**
   * @returns a non-escaped, non-html wrapped version of the body.
   */
  String getRawBody(SampleRowParams rp) throws IOException;

  /**
   * @return a table data (td) element with the displayed data for the Sample being
   * shown on a particular row.
   * @param rp  the RowParameters object representing the sample, row index and any
   * other data needed to render a particular row
   */
  String getBody(SampleRowParams rp) throws IOException;

  /**
   * @return the raw tooltip text, without javascript to make it a mouseover value
   */
  String getHeaderTooltip();

  /**
   * @return true if the column should be shown.  Usually depends on the ColumnParameters
   * that the instance was created with
   */
  boolean isShown() ;
  

  void setShown(ViewParams vp);

	/**
	 * true - desc, false - asc, null - not sorted
	 *
	 * @return sorting order
	 */
  	Boolean getDesc();

	void setDesc(Boolean isDesc);

	boolean sortable();

	String getColumnMetadataKey();
}
