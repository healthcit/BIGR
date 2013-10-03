/*
 * Created on Dec 20, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.web.taglib;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.RequestUtils;

import com.ardais.bigr.api.ApiFunctions;


/**
 * @author sthomashow
 *
 * the BIGR <code>searchText</code> tag
 * 
 * this can be used to provide search-like capabilities to make it easier to narrow
 * down the choices in a select box.   note that this was implemented in a very specific
 * fashion using tags for diagnosis, tissue, and procedure (by extending ListWithOtherSupport).
 * this tag can easily provide this functionality to any select control
 *
 * usage notes:
 *  must declare common.js when using this tag
 *  creates a new row for this tag
 */
 
public class SearchTextTag extends TagSupport {
  private String _name;
  private String _searchButton;
  private String _length;
  private String _refresh_yn;
  private String _refreshButton;
  private String _searchedField;
  private String _style;
  private String _id;
  private String _tableRow;
 
  /**
   * Creates a new <code>SearchTextTag</code>.
   */
  public SearchTextTag() {
    super();
  }


  public int doStartTag() throws javax.servlet.jsp.JspException {
    JspWriter out = pageContext.getOut();
    if ("false".equals(_tableRow)) {
      try {
        out.print(getHtml());
      }
      catch (IOException ioe) {
        throw new JspException("Exception in SearchTextTag:" + ioe.toString());
      }
    }
    else {
      doBasic(out);      
    }
    return SKIP_BODY;
  }

  private void doBasic(JspWriter out) throws javax.servlet.jsp.JspException {

    try {
      out.print("<tr class=\"white\"");
      if (!ApiFunctions.isEmpty(_style)) {
        out.print(" style=\"" + _style + "\"");
      }
      if (!ApiFunctions.isEmpty(_id)) {
        out.print(" id=\"" + _id + "\"");
      }
      out.print("> \n");
      out.print("<td>");
      out.print(getHtml());
      // close it up...
      out.print("</td></tr>");

    }
    catch (IOException ioe) {
      throw new JspException("Exception in SearchTextTag:" + ioe.toString());
    }

  } // end doBasic


  public String getHtml() throws javax.servlet.jsp.JspException {
    StringBuffer sb = new StringBuffer();

    // Check value for name
    if (_name == null)
      throw new JspException("Error in tag SearchTextTag: value for attribute name cannot be null.");

    // Labels for search and refresh buttons cannot be the same
    if (_searchButton != null) {
      if (_searchButton.equalsIgnoreCase(_refreshButton))
        throw new JspException("Error in tag SearchTextTag: value for search button and refresh button cannot be the same.");
    }

    // Check valid values for length attribute
    if (_length != null) {
      try {
        Integer.parseInt(_length);
      }
      catch (NumberFormatException nfe) {
        throw new JspException("Error in tag SearchTextTag: value for attribute length is not a number.");
      }
    }

    // Check value for refresh_yn
    if (_refresh_yn == null)
      throw new JspException("Error in tag SearchTextTag: value for attribute refresh_yn cannot be null.");

    // Check value for the field to search
    if (_searchedField == null)
      throw new JspException("Error in tag SearchTextTag: value for attribute searchedField cannot be null.");

    // ok, validation is complete, so let's begin processing the tag...

    String searchName = "search" + _searchedField;
    String searchAction =
      "findListOptions" + "(this.form." + searchName + ",this.form." + _searchedField + ");";
    String restoreAction =
      "restoreListOptions" + "(this.form." + searchName + ",this.form." + _searchedField + ");";

    // the input tag...
    sb.append("<input type=\"text\" name=\"" + searchName + "\"");
    sb.append(" size=\"" + Integer.parseInt(_length) + "\"");
    sb.append(" maxlength=\"" + Integer.parseInt(_length) + "\"");
    /* MR 7786 */
    sb.append(" onKeyPress=\"if(event.keyCode == 13){" + searchAction + "return false;}\"");
    sb.append("/>");

    // default search button text
    String searchButton = (_searchButton == null ? "Search" : _searchButton);
    // the search button...
    sb.append(
      "&nbsp;<input type=\"button\" value=\""
        + searchButton
        + "\" name=\"search\" onClick=\""
        + searchAction
        + "\">");

    // default refresh button text
    String refreshButton = (_refreshButton == null ? "Refresh" : _refreshButton);
    // the refresh button...
    if (_refresh_yn.equalsIgnoreCase("Y")) {
      sb.append(
        "&nbsp;<input type=\"button\" value=\""
          + refreshButton
          + "\" name=\"refresh\" onClick=\""
          + restoreAction
          + "\">");
    }

    return sb.toString();

  }

  /**
   *  setters
   */
  public void setName(String string)
  {
    _name = string;
  }
  
  public void setSearchButton(String string)
  {
    _searchButton = string;
  }
  
  public void setLength(String string)
  {
    _length = string;
  }
  
  public void setRefresh_yn(String string)
  {
    _refresh_yn = string;
  }
  
  public void setRefreshButton(String string)
  {
    _refreshButton = string;
  }
  
  public void setSearchedField(String string)
  {
    _searchedField = string;
  }


  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
  }

  /**
   * @param string
   */
  public void setId(String string) {
    _id = string;
  }

  /**
   * @param string
   */
  public void setStyle(String string) {
    _style = string;
  }

  public void setTableRow(String string) {
    _tableRow = string;
  }

}
