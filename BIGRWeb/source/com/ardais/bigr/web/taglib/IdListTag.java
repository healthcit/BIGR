package com.ardais.bigr.web.taglib;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.struts.action.Action;
import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;

public final class IdListTag extends BaseHandlerTag {
	protected String property = null;
	protected String type = null;
	protected String idlist = null;
	protected String idfrom = null;
	protected String showRemoveAll = null;
  protected boolean limitValidation = false; // do not invoke validation; will be done in ValidateIds

	public String getProperty() {
		return property;
	}

	public String getType() {
		return type;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int doStartTag() throws JspException {
		// Do nothing until doEndTag() is called
		return EVAL_BODY_BUFFERED;
	}

	/**
	 * Process the end of this tag.
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doEndTag() throws JspException {
		// Generate an HTML element
		StringBuffer results = new StringBuffer();
		genAddBtn(results);
		results.append("<br>");
		genRemBtn(results);
		if(getShowRemoveAll() == null || getShowRemoveAll().equals("")){
			results.append("<br>");
			genRemAllBtn(results);
		}
		results.append("<br>");
		genClipBtn(results);

		// Render this element to our writer
		JspWriter writer = pageContext.getOut();
		try {
			writer.print(results.toString());
		} catch (IOException e) {
			throw new JspException(messages.getMessage("common.io", e.toString()));
		}
		return (EVAL_PAGE);
	}

	private void genClipBtn(StringBuffer results) throws JspException {
		String label = "Add From Clipboard";

		//onclick="addClipboardToList('id.donorIds', DONOR_TYPE);"

		results.append("<input type=\"button\"");
		if (property != null) {
			results.append(" name=\"");
			results.append(property);
			results.append("\"");
		}
		results.append(" value=\"");
		results.append(label);
		results.append("\"");
		results.append(" style=\"margin-top: 2px;\" onclick=\"");
		results.append("addClipboardToList('" + idlist + "', " + type + ");");
		results.append("\"");
		results.append(prepareEventHandlers());
		results.append(prepareStyles());
		results.append(">");
	}

	private void genAddBtn(StringBuffer results) throws JspException {
		String label = "Add &gt;";

		results.append("<input type=\"button\"");
		if (property != null) {
			results.append(" name=\"");
			results.append(property);
			results.append("\"");
		}
		results.append(" value=\"");
		results.append(label);
		results.append("\"");
		results.append(" style=\"margin-top: 2px;\" onclick=\"");
    if (limitValidation)  // if validating ids using ValidateIds class
		  results.append("addItemToListPrefix('" + idfrom + "','" + idlist + "');");
    else  // otherwise, do validation...
      results.append("addItemToList('" + idfrom + "','" + idlist + "', " + type + ");");
		results.append("\"");
		results.append(prepareEventHandlers());
		results.append(prepareStyles());
		results.append(">");
	}

	private void genRemBtn(StringBuffer results) throws JspException {
		String label = "&lt; Remove";

		results.append("<input type=\"button\"");
		if (property != null) {
			results.append(" name=\"");
			results.append(property);
			results.append("\"");
		}
		results.append(" value=\"");
		results.append(label);
		results.append("\"");
		results.append(" style=\"margin-top: 2px;\" onclick=\"");
		results.append("removeItemFromList('" + idlist + "');document.all['" + idfrom + "'].focus();");
		results.append("\"");
		results.append(prepareEventHandlers());
		results.append(prepareStyles());
		results.append(">");
	}

	private void genRemAllBtn(StringBuffer results) throws JspException {
		String label = "&lt;&lt; Remove All";

		results.append("<input type=\"button\"");
		if (property != null) {
			results.append(" name=\"");
			results.append(property);
			results.append("\"");
		}
		results.append(" value=\"");
		results.append(label);
		results.append("\"");
		results.append(" style=\"margin-top: 2px;\" onclick=\"");
		results.append("clearList('" + idlist + "');document.all['" + idfrom + "'].focus();");
		results.append("\"");
		results.append(prepareEventHandlers());
		results.append(prepareStyles());
		results.append(">");
	}

	/**
	 * Release any acquired resources.
	 */
	public void release() {
		super.release();
		property = null;
		type = null;
		idlist = null;
		idfrom = null;
		showRemoveAll = null;
    limitValidation = false;
	}
	/**
	 * Returns the idfrom.
	 * @return String
	 */
	public String getIdfrom() {
		return idfrom;
	}

	/**
	 * Returns the idlist.
	 * @return String
	 */
	public String getIdlist() {
		return idlist;
	}
  
	/**
	 * Sets the idfrom.
	 * @param idfrom The idfrom to set
	 */
	public void setIdfrom(String idfrom) {
		this.idfrom = idfrom;
	}

	/**
	 * Sets the idlist.
	 * @param idlist The idlist to set
	 */
	public void setIdlist(String idlist) {
		this.idlist = idlist;
	}

  /**
   * Sets the limitValidation
   * @param idlist The idlist to set
   */
  public void setLimitValidation(boolean val) {
    this.limitValidation = val;
  }



	/**
	 * Returns the showRemoveAll.
	 * @return String
	 */
	public String getShowRemoveAll() {
		return showRemoveAll;
	}

	/**
	 * Sets the showRemoveAll.
	 * @param showRemoveAll The showRemoveAll to set
	 */
	public void setShowRemoveAll(String showRemoveAll) {
		this.showRemoveAll = showRemoveAll;
	}

  /**
   * Returns the limitValidation.
   * @return boolean
   */
  public boolean isLimitValidation() {
    return limitValidation;
  }

}
