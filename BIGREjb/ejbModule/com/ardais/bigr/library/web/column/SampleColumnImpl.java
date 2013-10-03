package com.ardais.bigr.library.web.column;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.library.web.column.configuration.ColumnManager;
import com.ardais.bigr.query.ColumnConstants;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.web.StrutsProperties;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class SampleColumnImpl implements SampleColumn, Serializable {
  
  private int _width;
  private String _headerText;
  private String _rawHeaderText;
  private String _headerTooltip;
  private int _permissions = ColumnPermissions.NONE;
  private int _screen = ColumnPermissions.NONE;
  private int _transactions = ColumnPermissions.NONE;
  private ViewParams _vp;
  private Boolean _desc;

  // We make this private for now so that we can be confident that no subclass uses this
  // in a way that could bypass security checks.
  //
  private boolean _isShown;

  private String _bodyColAlign;

  /**
   * Constructor for all SampleColumn subclasses.
   * @param width the HTML width of the column
   * @param headerText  the struts key for the column header label
   * @param headerTooltip   the struts key for the column header toolTip mouseover
   * @param aRTOH_ardOnly_show4Rna_show4Tiss_show4Order_show4Hold
   *            a flag mask, such as "aRH" that specifies which situations this column
   *              will display in.  the four masks are: a-Ardais Only?, R-display for RNA?
   *              T-display for Tissue?, O-display on order screen, H - display on hold list
   * Note that lowercase mask letter, 'a', restricts output when added.  Capital 
   * letters, R, T, O and H increase output when added.
   */
  protected SampleColumnImpl(
        int width,
        String headerTextProperty,
        String headerTooltipProperty,
        int permissions,
        int screens,
        ViewParams vp) {
    this(width, headerTextProperty, headerTooltipProperty, permissions, screens, ColumnPermissions.ALL, vp);    
  }
  
  protected SampleColumnImpl(
      int width,
      String headerTextProperty,
      String headerTooltipProperty,
      int permissions,
      int screens,
      int transaction, // Pick List vs. Sample Selection
      ViewParams vp) {
    this(width, headerTextProperty, headerTooltipProperty, null, null, permissions, screens, transaction, vp);
  }
  
  protected SampleColumnImpl(
    int width,
    String headerTextProperty,
    String headerTooltipProperty,
    String headerText,
    String headerTooltip,
    int permissions,
    int screens,
    int transaction, // Pick List vs. Sample Selection
    ViewParams vp) {

    _width = width;
    _screen = screens;
    _permissions = permissions;      
    _transactions = transaction;
    if ((_permissions & (ColumnPermissions.ROLE_DI| ColumnPermissions.ROLE_CUST)) != 0) 
      if (!ColumnConstants.externallyVisibleColumns().contains(getKey()))
        throw new ApiException("Attempt to set internal-only column to external permission");
        
    _vp = vp;
    setShown(vp);
    //determine if we need to look up the header text or if it has been provided
    if (ApiFunctions.isEmpty(headerText)) {
      Properties prop = StrutsProperties.getInstance();
      _rawHeaderText = prop.getProperty(headerTextProperty);
    }
    else {
      _rawHeaderText = headerText;
    }
    setHeaderText(Escaper.htmlEscape(_rawHeaderText));
    //determine if we need to look up the header text or if it has been provided
    if (ApiFunctions.isEmpty(headerTooltip)) {
      Properties prop = StrutsProperties.getInstance();
      setHeaderTooltip(prop.getProperty(headerTooltipProperty));
    }
    else {
      setHeaderTooltip(headerTooltip);
    }
  }

  /**
   * get the text into the header table.  We don't use HTML headers, we use two linked
   * tables, so this actually gets into the small header table above the main data 
   * table.  It typically gets a cell of the 
   * form <td><span onmouseover=TOOLTIPTEXT>HEADERTEXT</span></td>
   */
  public String getHeader() {
    if (!isShown())
      return "";
    return formatHeaderHelper();
  }
  
  protected String formatHeaderHelper() {
    StringBuffer result = new StringBuffer(128);
    result
		.append("<td class=\"")
		.append("hasTip");
	  if (getDesc() != null)
	  {
		  result
			  .append(" ")
			  .append(getOrderClass());
	  }
	result
		.append("\"")
		.append(" tip=\"");
    Escaper.htmlEscape(getHeaderTooltip(), result);
    result.append("\"");
	if (sortable())
	{
		result.append(getSortSampleColumnFunc())
			.append(" style=\"cursor:pointer;font-weight:bold;\"");
	}
	result.append(">")
		.append(getHeaderText())
		.append("</td>");
    return result.toString();
  }

	protected String getSortSampleColumnFunc()
	{
		if (getColumnMetadataKey() == null)
		{
			return "";
		}
		else
		{
			return String.format(" onclick=\"sortSampleColumn('%s','%s')\"", getColumnMetadataKey(), getNextSortOrder());
		}
	}

	protected String getOrderClass()
	{
		return getDesc() ? "descSort" : "ascSort";
	}

  // by default, use the header text as the description
  public String getColumnDescription() {
    return getHeaderText();
  }  

  public String getSummaryHeader() {
    return getHeader();
  }

  /**
   * get the width HTML for the header table.  Of the form <col width=xxx/>
   * @param cp  a data bundle representing the context, including if this is
   * an internal/external user page, if this is RNA or Tissue or Hold List display.
   */
  public String getWidthHeader() {
    if (!isShown())
      return "";
    return getWidthPrivate(false); // no alignment configurable for heading
  }

  /**
   * Similar to getWidhtHeader(), but often includes a different alignment
   * parameter for the data in the body table.
   */
  public String getWidthBody() {
    if (!isShown())
      return "";
    return getWidthPrivate(_bodyColAlign != null);
  }

  // get the <col width...> for the body, with alignment if present
  private String getWidthPrivate(boolean addAlign) {
    StringBuffer result = new StringBuffer(128);
    result.append("<col width=\"");
    result.append(Integer.toString(getWidth()));
    if (addAlign) {
      result.append("\" align=\"");
      result.append(_bodyColAlign);
    }
    result.append("\">");
    return result.toString();
  }

  /**
   * gets the data for a this column for a particular row into the body table.
   * @param cp  the page-wide Column Paramters, including the output stream to get into.
   * @param rp  the row-specific paramters, including the SampleSelectionHelper that
   * represents sample data for the current row.
   */
  public final String getRawBody(SampleRowParams rp) throws IOException {
    if (!isShown())
      return "";
    return getRawBodyText(rp);
  }

  /**
   * gets the data for a this column for a particular row into the body table.
   * @param cp  the page-wide Column Paramters, including the output stream to get into.
   * @param rp  the row-specific paramters, including the SampleSelectionHelper that
   * represents sample data for the current row.
   */
  public final String getBody(SampleRowParams rp) throws IOException {
    if (!isShown())
      return "";
    return getBodyText(rp);
  }

  /**
   * Override this method to have subclasses get the proper data into the main
   * data table.
   * This method should get a cell (of the form <td>....</td>) to the Jspgetr which
   * is a member variable of the SampleColummParameters.
   * This method does not need tow check isShown() because that will be done by the caller.
   * @param cp   SampleColumnParameters, including the Jspgetr out
   * @param rp   SampleRowParameters, including the SampleSelectionHelper ssh
   */
  protected abstract String getRawBodyText(SampleRowParams rp) throws IOException;

  /**
   * Override this method to have subclasses get the proper data into the main
   * data table.
   * This method should get a cell (of the form <td>....</td>) to the Jspgetr which
   * is a member variable of the SampleColummParameters.
   * This method does not need tow check isShown() because that will be done by the caller.
   * @param cp   SampleColumnParameters, including the Jspgetr out
   * @param rp   SampleRowParameters, including the SampleSelectionHelper ssh
   */
  protected abstract String getBodyText(SampleRowParams rp) throws IOException;

  protected ViewParams getViewParams() {
    return _vp;
  }
  
  /**
   * Sets isShown flag to true if this column is displayable in the context specified by the 
   * SampleColumnParameters <code>cp</code>
   * @param cp  the SampleColumnParemeters that indicates things like if this is an
   * internal or external user and what type of data is being displayed.
   */
  public final void setShown(ViewParams vp) {
    // null view means don't show anything, but allow access to headers, etc. for config screens
    if (vp==null) {
      _isShown = false;
      return;
    }
    // We make this final for now so that we can be confident that no subclass overrides this
    // in a way that could bypass security checks.
    if (((_permissions & vp.getRole()) != 0)
      && ((_screen & vp.getScreen()) != 0)
      && ((_transactions & vp.getTransaction())!= 0))
      _isShown = true;
    else
      _isShown = false;
  }

  /**
   * @return true if this column is shown, based on the flags and context
   * it was configured with.
   */
  public final boolean isShown() {
    // We make this private for now so that we can be confident that no subclass overrides this
    // in a way that could bypass security checks.
    return _isShown;
  }

  /**
   * Sets the headerText.
   * @param headerText The struts property name of the headerText to set
   */
  protected void setHeaderText(String headerText) {
    _headerText = headerText;
  }

  /**
   * Sets the headerTooltip.
   * @param headerTooltip The struts property name of the headerTooltip to set
   */
  protected void setHeaderTooltip(String headerTooltip) {
    _headerTooltip = headerTooltip;
  }

  /**
   * Gets the width.
   * @return The width.
   */
  protected int getWidth() {
    return _width;
  }

  /**
   * Sets the width.
   * @param width The width to set
   */
  protected void setWidth(int width) {
    _width = width;
  }

  /**
   * Sets the alignment of the column in the body section.  Valid values are
   * left, right, middle.  Default is left.
   * 
   * @param align   the alignment string.
   */
  protected void setBodyColAlign(String align) {
    if ("right".equals(align) || "center".equals(align))
      _bodyColAlign = align;
    else if (align == null || "left".equals(align))
      _bodyColAlign = null;
    else
      throw new IllegalArgumentException(align);
  }

  /**
   * Returns the headerText.
   * @return String
   */
  public String getHeaderText() {
    return _headerText;
  }

  /**
   * Returns the headerTooltip.
   * @return String
   */
  public String getHeaderTooltip() {
    return _headerTooltip;
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
  
  public String getRawHeaderText() {
    return _rawHeaderText;
  }

	public Boolean getDesc()
	{
		return _desc;
	}

	public void setDesc(Boolean desc)
	{
		this._desc = desc;
	}

	protected String getNextSortOrder()
	{
		if (getDesc() == null)
		{
			return "desc";
		}
		else
		{
			return getDesc() ? "asc" : "none";
		}
	}

	@Override
	public boolean sortable()
	{
		return false;
	}

	@Override
	public String getColumnMetadataKey()
	{
		return null;
	}
}
