package com.ardais.bigr.iltds.helpers;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.util.IcpUtils;
import com.ardais.bigr.security.SecurityInfo;
import java.util.*;

/**
 */
public class SampleTableHelper {

	public static final int CHECKBOX_SAMPLEID = 1;
	public static final int CHECKBOX_CARTINFO = 2;
	public static final int CHECKBOX_LINEITEMINFO = 3;
	private static final int CHECKBOX_MAX = 3;
	
	public static final int CHECKBOX_NAME_SELECTI = 1;
	private static final int CHECKBOX_NAME_MAX = 1;

	public static final int CHECKBOX_COND_NOTQCINPROCESS = 1;
	private static final int CHECKBOX_COND_MAX = 1;

	private String _tableHeader;
	private boolean _isCheckboxIncluded = false;
	private String _checkboxColumnHeader;
	private String _checkboxColumnTitle;
	private String _checkboxOnclick;
	private int _checkboxValue = 0;
	private int _checkboxName = 0;
	private int _checkboxCond = 0;
	private String _noSampleText;

	// The number of checkboxes in the sample table.  This may
	// not be the same as the number of samples.
	private int _checkboxCount = 0;
	
	// Holds the samples in this project.  Each item in the list
	// is a SampleHelper.
	private ArrayList _samples;
	
	// Holds the SecurityInfo to be used in this project
	private SecurityInfo _securityInfo;

/**
 * Creates a new <code>SampleTableHelper</code>.
 */
public SampleTableHelper() {
	super();
}
/**
 * Adds a <code>SampleHelper</code>to this <code>SampleTableHelper</code>.
 * The sample is added to the end of an ordered list.
 *
 * @param  helper  the <code>SampleHelper</code>
 */
public void addSample(SampleHelper helper) {
	if (_samples == null) {
		_samples = new ArrayList();
	}
	_samples.add(helper);
}
/**
 * Returns the header for the checkbox column.  If the colum header
 * was not set, then the empty string is returned.
 *
 * @return  The header of the checkbox column.
 */
private String getCheckboxColumnHeader() {
	return (_checkboxColumnHeader == null) ? "" : _checkboxColumnHeader;
}
/**
 * Returns the title for the checkbox column, to be used as a tooltip.
 * If the column header was not set, then the empty string is returned.
 *
 * @return  The title of the checkbox column.
 */
private String getCheckboxColumnTitle() {
	return (_checkboxColumnTitle == null) ? "" : _checkboxColumnTitle;
}
/**
 * Returns the condition under which the checkbox should be displayed.
 *
 * @return  The condition under which the checkbox should be displayed.
 */
private int getCheckboxCondition() {
	return _checkboxCond;
}
/**
 * Returns the number of checkboxes in the sample table.  This should only be
 * called after the sample table is generated, i.e. after <code>getSampleTable()</code>
 * has been called.
 *
 * @return  The number of checkboxes.
 */
public String getCheckboxCount() {
	return String.valueOf(_checkboxCount);
}
/**
 * Returns the value that should be used for the name of the checkbox.
 *
 * @return  The value of the checkbox name.
 */
private int getCheckboxName() {
	return _checkboxName;
}
/**
 * Returns the onclick event handler of the included checkbox.  If the
 * onclick event handler is <code>null</code>, then <code>null</code>
 * is returned.
 *
 * @return 	The onclick event handler of the checkbox, or <code>null</code>.
 */
private String getCheckboxOnclick() {
	return _checkboxOnclick;
}
/**
 * Returns the value(s) that should be used for the value of the checkbox.
 *
 * @return  The value(s) that should be used as the value of the checkbox.
 */
private int getCheckboxValue() {
	return _checkboxValue;
}
/**
 * Returns the text to be displayed if there are no samples.  If the no
 * sample text was not set, then a default message is returned.
 *
 * @return 	The no sample text.
 */
private String getNoSampleText() {
	return (_noSampleText == null) ? "There are no samples" : _noSampleText;
}
/**
 * Returns a <code>List</code> of <code>SampleHelper</code>s
 * that were added to this <code>SampleTableHelper</code>.  If there
 * are no <code>SampleHelper</code>s then an empty <code>List</code>
 * is returned, not <code>null</code>.
 *
 * @return  A <code>List</code> of <code>SampleHelper</code>s.
 */
public List getSamples() {
	if (_samples == null) {
		_samples = new ArrayList();
	}
	return _samples;
}
/**
 * Returns the HTML TABLE that holds all of the samples that have been
 * added to this <code>SampleTableHelper</code>.  The set methods that
 * set the table characteristics should be called before this method
 * is called.
 *
 * @return  The HTML TABLE.
 */
public String getSampleTable() throws ApiException {
	List samples = getSamples();
	int colspan = isCheckboxIncluded() ? 12 : 11;
	boolean includeImages = isIncludeImages(samples);
	if (includeImages) colspan++;

	colspan++;  // 2/24/03 removed the clause checking for api.lims.pathverif.sixth

	StringBuffer buf = new StringBuffer(2048);

	// Start the table and add the header.
	buf.append("<div id=\"sampleTableDiv\" class=\"stScrollingDiv\" onscroll=\"repositionHeader();\">");
	buf.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">");
	
	int size = samples.size();
	if (size > 0) {

		// Add the column header rows.
		buf.append("<tr id=\"headerTR\" style=\"position: relative;\">");
		if (includeImages) buf.append("<td nowrap class=\"stHead\"><img src=\"/BIGR/images/px1.gif\" border=\"0\"></td>");
		if (isCheckboxIncluded()) {
			buf.append("<td nowrap class=\"stHead\" title=\"");
			buf.append(getCheckboxColumnTitle());
			buf.append("\">");
			buf.append(getCheckboxColumnHeader());
			buf.append("</td>");
		}
		buf.append("<td nowrap class=\"stHead\" title=\"The sample's case ID\">Case</td>");
		buf.append("<td nowrap class=\"stHead\" title=\"The sample's ASM ID\">ASM</td>");
		buf.append("<td nowrap class=\"stHead\" title=\"The sample's ID\">Sample</td>");
		buf.append("<td nowrap class=\"stHead\" title=\"The sample's diagnosis (from the case)\">Diagnosis</td>");
		buf.append("<td nowrap class=\"stHead\" title=\"The sample's tissue (from the ASM)\">Tissue</td>");
		buf.append("<td nowrap class=\"stHead\" title=\"The sample's appearance (from the ASM)\">App</td>");
		buf.append("<td nowrap class=\"stHead\" title=\"Tumor\">Tumor</td>");
		buf.append("<td nowrap class=\"stHead\" title=\"Normal\">Normal</td>");

		// 2/24/03 removed the clause checking for api.lims.pathverif.sixth
		buf.append("<td nowrap class=\"stHead\" title=\"Non-neoplastic Lesion\">Lesion</td>");

		buf.append("<td nowrap class=\"stHead\" title=\"Tumor Hypercellular Stroma\">TCS</td>");
		buf.append("<td nowrap class=\"stHead\" title=\"Tumor Hypo-/Acellular Stroma\">TAS</td>");
		buf.append("<td nowrap class=\"stHeadLast\" title=\"Necrosis\">Nec</td>");
		buf.append("</tr>");

		boolean evenCase = true;
		int checkboxCount = 0;
		String currentCaseId = "";
		for (int i = 0; i < size; i++) {
			SampleHelper sampleHelper = (SampleHelper)samples.get(i);
			boolean hasComments = (sampleHelper.hasComments() || sampleHelper.hasInternalComments());

			// Divide each case using a case divider row.
			if (currentCaseId.equals("")) {
				currentCaseId = sampleHelper.getCaseId();
			}
			else if (!currentCaseId.equals(sampleHelper.getCaseId())) {
				buf.append("<tr><td colspan=\"");
				buf.append(colspan);
				buf.append("\" class=\"stCaseDivider\"><img src=\"/BIGR/images/px1.gif\" border=\"0\"></td></tr>");
				currentCaseId = sampleHelper.getCaseId();
				evenCase = !evenCase;
			}

			// Determine the CSS style to use for each cell.
			String cellClass;
			String lastCellClass;
			if (hasComments) {
				cellClass = (evenCase) ? "stCellCommentEven" : "stCellCommentOdd";
				lastCellClass = (evenCase) ? "stCellCommentLastEven" : "stCellCommentLastOdd";
			}
			else {
				cellClass = (evenCase) ? "stCellEven" : "stCellOdd";
				lastCellClass = (evenCase) ? "stCellLastEven" : "stCellLastOdd";
			}

			// Add a row for each sample.
			buf.append("<tr>");

			// Add any images that need to be added.
			if (includeImages) {
				buf.append("<td nowrap valign=\"center\" class=\"");
				if (evenCase) {
					buf.append("stImagesEven\"");
				}
				else {
					buf.append("stImagesOdd\"");
				}
				if (hasComments) buf.append(" rowspan=\"2\"");
				buf.append('>');

				if (sampleHelper.isPathVerified()) {
					buf.append(" <img src=\"/BIGR/images/microscope.gif\" width=\"16\" height=\"16\" style=\"border: solid 1px black\" title=\"The sample is path verified\">");
		            if (sampleHelper.isRestricted()) {
						buf.append(" <img src=\"/BIGR/images/restricted.jpg\" title=\"The sample is restricted\">");
		            }
			    }
	            else if (sampleHelper.isRestricted()) {
					buf.append(" <img src=\"/BIGR/images/px1.gif\" width=\"16\" height=\"16\" border=\"0\">");
					buf.append(" <img src=\"/BIGR/images/restricted.jpg\" title=\"The sample is restricted\">");
	            }
	            else {
					buf.append("<img src=\"/BIGR/images/px1.gif\" border=\"0\">");
	            }
				buf.append("</td>");
			}
			
            // Add the checkbox if it should be included
			boolean isCheckbox = isCheckboxIncluded();
			if (isCheckbox) {
				switch (getCheckboxCondition()) {
					case CHECKBOX_COND_NOTQCINPROCESS:
						String status = sampleHelper.getRawPathStatus();
						if ((status != null) && (status.equals(FormLogic.SMPL_QCINPROCESS))) {
							isCheckbox = false;
						}
						break;
				}
			}

			if (isCheckbox) {
				buf.append("<td nowrap align=\"center\" valign=\"center\" class=\"");
				if (evenCase) {
					buf.append("stCheckboxEven\"");
				}
				else {
					buf.append("stCheckboxOdd\"");
				}
				if (hasComments) buf.append(" rowspan=\"2\"");
				buf.append("><input type=\"checkbox\"");
				String checkboxName = "sample";
				switch (getCheckboxName()) {
					case CHECKBOX_NAME_SELECTI:
    					checkboxName = "select" + String.valueOf(++checkboxCount);
						break;
				}
				buf.append(" name=\"");
				buf.append(checkboxName);
				buf.append("\"");
				String checkboxValue = "";
				switch (getCheckboxValue()) {
					case CHECKBOX_SAMPLEID:
    					checkboxValue = sampleHelper.getRawId();
						break;
					case CHECKBOX_CARTINFO:
    					checkboxValue = sampleHelper.getRawId() + ";" +
      						sampleHelper.getRawCartAccount() + ";" + 
      						sampleHelper.getRawCartUser(); 
						break;
					case CHECKBOX_LINEITEMINFO:
    					checkboxValue = sampleHelper.getRawId() + ";" +
      						sampleHelper.getRawLineItemId();
						break;
				}
				buf.append(" value=\"");
				buf.append(checkboxValue);
				buf.append("\"");
				if (getCheckboxOnclick() != null) {
					buf.append(" onclick=\"");
					buf.append(getCheckboxOnclick());
					buf.append("\"");
				}
				buf.append("></td>");
		 	}  // checkbox
			else if (isCheckboxIncluded()) {
				buf.append("<td class=\"");
				if (evenCase) {
					buf.append("stCheckboxEven\"");
				}
				else {
					buf.append("stCheckboxOdd\"");
				}
				if (hasComments) buf.append(" rowspan=\"2\"");
				buf.append(">&nbsp;</td>");
			}

			// Add the values for the rest of the columns.
			buf.append("<td nowrap align=\"center\" class=\"");
			buf.append(cellClass);
			buf.append("\">");

			// caseId will be an ICP link...
			String value = sampleHelper.getCaseId();
			String caseIcp = IcpUtils.preparePopupLink(value, this.getSecurityInfo());
			buf.append(caseIcp);
			
			buf.append("</td><td nowrap align=\"center\" class=\"");
			buf.append(cellClass);
			buf.append("\">");
            value = sampleHelper.getAsmPosition();
			buf.append(value.equals("") ? "&nbsp;" : value);

			buf.append("</td><td nowrap align=\"center\" class=\"");
			buf.append(cellClass);
			buf.append("\">");
			
			// sampleId will be an ICP link
            value = sampleHelper.getId();
 			String sampleIcp = IcpUtils.preparePopupLink(value, this.getSecurityInfo());
			buf.append(sampleIcp);           


            buf.append("</td><td nowrap align=\"center\" class=\"");
			buf.append(cellClass);
			buf.append("\">");
           	value = sampleHelper.getDiagnosis();
            buf.append(value.equals("") ? "&nbsp;" : value);

            buf.append("</td><td nowrap align=\"center\" class=\"");
			buf.append(cellClass);
			buf.append("\">");
           	value = sampleHelper.getTissue();
            buf.append(value.equals("") ? "&nbsp;" : value);

            buf.append("</td><td nowrap align=\"center\" class=\"");
			buf.append(cellClass);
			buf.append("\">");
           	value = sampleHelper.getAsmAppearance();
            buf.append(value.equals("") ? "&nbsp;" : value);

            buf.append("</td><td nowrap align=\"center\" class=\"");
			buf.append(cellClass);
			buf.append("\">");
            value = sampleHelper.getViableTumorCell();
            buf.append(value.equals("") ? "&nbsp;" : value);

            buf.append("</td><td nowrap align=\"center\" class=\"");
			buf.append(cellClass);
			buf.append("\">");
           	value = sampleHelper.getViableNiContent();
            buf.append(value.equals("") ? "&nbsp;" : value);

            buf.append("</td><td nowrap align=\"center\" class=\"");
			buf.append(cellClass);
			buf.append("\">");

			// 2/24/03 removed the clause checking for api.lims.pathverif.sixth
           	value = sampleHelper.getViableLesionalCell();
            buf.append(value.equals("") ? "&nbsp;" : value);

            buf.append("</td><td nowrap align=\"center\" class=\"");
			buf.append(cellClass);
			buf.append("\">");

           	value = sampleHelper.getCellularStroma();
            buf.append(value.equals("") ? "&nbsp;" : value);

            buf.append("</td><td nowrap align=\"center\" class=\"");
			buf.append(cellClass);
			buf.append("\">");
           	value = sampleHelper.getAcellularStroma();
            buf.append(value.equals("") ? "&nbsp;" : value);

            buf.append("</td><td nowrap align=\"center\" class=\"");
			buf.append(lastCellClass);
			buf.append("\">");
           	value = sampleHelper.getNecrosis();
            buf.append(value.equals("") ? "&nbsp;" : value);
			buf.append("</td></tr>");

			if (hasComments) {
				buf.append("<tr><td colspan=\"");
				buf.append(String.valueOf(colspan));
				buf.append("\" class=\"");
				if (evenCase) {
					buf.append("stCommentEven\"");
				}
				else {
					buf.append("stCommentOdd\"");
				}
				buf.append("\">");
				if (sampleHelper.hasComments()) {
					buf.append("<span style=\"font-weight: bold;\">Ext:</span> ");
					buf.append(sampleHelper.getComments());
				}
				if (sampleHelper.hasInternalComments()) {
					if (sampleHelper.hasComments()) buf.append("&nbsp;&nbsp;");
					buf.append("<span style=\"font-weight: bold;\">Int:</span> ");
					buf.append(sampleHelper.getInternalComments());
				}
				buf.append("</td></tr>");
			}
		}  // for
		_checkboxCount = checkboxCount;
	}  // if sample size > 0

	// No samples, so display the no sample text.
	else {
	  	buf.append("<tr class=\"green\">");
		buf.append("<td colspan=\"");
		buf.append(String.valueOf(colspan));
		buf.append("\" align=\"center\">");
		buf.append(getNoSampleText());
	    buf.append("</td></tr>");
	}
  	buf.append("</table></div>");
  	return buf.toString();
}
/**
 * Returns the header for the table.  If the table header was not set,
 * then a default table header string is returned.
 *
 * @return  The table header.
 */
private String getTableHeader() {
	return (_tableHeader == null) ? "Samples" : _tableHeader;
}
/**
 * Returns the SecurityInfo block.
 *
 * @return  the Security Info block.
 */
private SecurityInfo getSecurityInfo() {
	return _securityInfo;
}
/**
 * Returns whether the checkbox column is to be included in the table or not.
 *
 * @return  <code>true</code> if the checkbox column is to be included; 
 *		<code>false</code> otherwise.
 */
private boolean isCheckboxIncluded() {
	return _isCheckboxIncluded;
}
/**
 * Returns whether any images are to be included in the sample table.
 *
 * @param  <code>true</code> if at least 1 image is to be returned;
 *		<code>false</code> otherwise
 */
private boolean isIncludeImages(List samples) {
	Iterator sampleIterator = samples.iterator();
	while (sampleIterator.hasNext()) {
		SampleHelper sampleHelper = (SampleHelper)sampleIterator.next();
		if (sampleHelper.isPathVerified() || sampleHelper.isRestricted()) {
			return true;
		}
	}
	return false;
}
/**
 * Sets the header for the checkbox column.  If the checkbox column is not
 * to be included, then this setting is ignored.
 *
 * @param  columnHeader  the header of the checkbox column
 */
public void setCheckboxColumnHeader(String columnHeader) {
	_checkboxColumnHeader = columnHeader;
}
/**
 * Sets the title for the checkbox column, to be used as a tooltip.
 * If the checkbox column is not to be included, then this setting is ignored.
 *
 * @param  title  the title of the checkbox column
 */
public void setCheckboxColumnTitle(String title) {
	_checkboxColumnTitle = title;
}
/**
 * Sets the condition under which a checkbox should be displayed.  This should
 * be used when checkboxes are to be displayed for at least some samples but not
 * necessarily all.  This must be set to one of the <code>CHECKBOX_COND_*</code>
 * constants in this class.  The setting will be interpreted by the table generation
 * code to determine whether a checkbox should be displayed.  If the checkbox column
 * is not to be included, then this setting is ignored.
 *
 * @param  condition  the condition
 */
public void setCheckboxCondition(int condition) throws ApiException {
	if ((condition > 0) && (condition <= CHECKBOX_COND_MAX)) {
		_checkboxCond = condition;
	}
	else {
		throw new ApiException("Illegal argument to setCheckboxCondition(): " + condition);
	}
}
/**
 * Sets whether the checkbox column is to be included in the table or not.  By
 * default, a checkbox is not included, so call this method with parameter
 * <code>true</code> to have checkbox included.
 *
 * @param  included  whether to include a checkbox or not
 */
public void setCheckboxIncluded(boolean included) {
	_isCheckboxIncluded = included;
}
/**
 * Sets the value that should be used for the name of the checkbox.  This
 * must be set to one of the <code>CHECKBOX_NAME_*</code> constants in this class.
 * The setting will be interpreted by the table generation code which will set
 * the checkbox name appropriately.  If the checkbox column is not to be included, 
 * then this setting is ignored.
 *
 * @param  value  the value 
 */
public void setCheckboxName(int value) throws ApiException {
	if ((value > 0) && (value <= CHECKBOX_NAME_MAX)) {
		_checkboxName = value;
	}
	else {
		throw new ApiException("Illegal argument to setCheckboxName(): " + value);
	}
}
/**
 * Sets the onclick event handler of the included checkbox.  If the checkbox column is not
 * to be included, then this setting is ignored.
 *
 * @param  onclick  the onclick event handler of the checkbox
 */
public void setCheckboxOnclick(String onclick) {
	_checkboxOnclick = onclick;
}
/**
 * Sets the value(s) that should be used for the value of the checkbox.  This
 * must be set to one of the <code>CHECKBOX_*</code> constants in this class.
 * The setting will be interpreted by the table generation code which will get
 * the correct value(s) from each <code>SampleHelper</code> and set it as the
 * value of the checkbox. If the checkbox column is not to be included, then 
 * this setting is ignored.
 *
 * @param  value  the value 
 */
public void setCheckboxValue(int value) throws ApiException {
	if ((value > 0) && (value <= CHECKBOX_MAX)) {
		_checkboxValue = value;
	}
	else {
		throw new ApiException("Illegal argument to setCheckboxValue(): " + value);
	}
}
/**
 * Sets the text to be displayed if there are no samples.
 *
 * @param  text  the no sample text
 */
public void setNoSampleText(String text) {
	_noSampleText = text;
}
/**
 * Sets the SecurityInfo block.
 *
 * @param  the Security Info block.
 */
public void setSecurityInfo(SecurityInfo securityInfo) {
	_securityInfo = securityInfo;
}
/**
 * Sets the header for the table.
 *
 * @param  tableHeader  the table header
 */
public void setTableHeader(String tableHeader) {
	_tableHeader = tableHeader;
}
}
