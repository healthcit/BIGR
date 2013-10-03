package com.ardais.bigr.iltds.helpers;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.assistants.SelectList;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.util.EjbHomes;

import java.util.*;
import java.text.SimpleDateFormat;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.servlet.*;


/**
 * A <code>JSPHelper</code> is used to aid in the generation
 * of JSPs.  It's subclasses are intended to be used in 1 of 4 ways:
 * <ul>
 * <li>
 * To use a <code>JSPHelper</code> to generate a JSP to create
 * a new object, instantiate it with its no-arg constructor.
 * The <code>JSPHelper</code>'s <code>display*</code> methods can 
 * be called to help populate dropdown lists, and the other
 * <code>display*</code> methods that display the value of a single
 * field will correctly return the empty string.  In general, no
 * other methods of the <code>JSPHelper</code> need be called in
 * this case to initialize the <code>JSPHelper</code>.
 * </li>
 * <li>
 * To use a <code>JSPHelper</code> to create a new object or update
 * an existing one, instantiate it with its constructor that takes a
 * <code>ServletRequest</code> as an argument.  Next, call the
 * <code>isValid</code> method to validate the request parameters.
 * Finally, call the <code>getDataBean</code> method to get the data 
 * bean that is initialized from the request parameters, which can then
 * be used as a parameter to an EJB method that takes that type of
 * data bean as a parameter.  If validation fails, assign the helper
 * to a request attribute, forward to the submitting JSP, and have
 * the submitting JSP call the helper's <code>getMessages</code>
 * method to display the validation problems to the user.  All of the
 * request parameters will be preserved in the helper as entered by the
 * user.
 * </li>
 * <li>
 * To use a <code>JSPHelper</code> to display information about an
 * object to be viewed and/or edited, use one of the constructors
 * that takes either an AccessBean or data bean as input.  This will
 * initialize the <code>JSPHelper</code> with data from the bean.
 * </li>
 * <li>
 * To use a <code>JSPHelper</code> as a container for other <code>JSPHelper</code>s
 * (e.g. to represent a list of helpers), construct a data bean with
 * the necessary helper information (e.g. the underlying object's id
 * and/or name), and use one of the <code>add*</code> methods to add
 * the dependent helpers to the container helper.
 * </li>
 * </ul>
 */
public class JspHelper {

	// Constants that specify common request parameter names that 
	// are used im multiple pages.
	public static final String ID_CONTEXT = "ctx";
	public static final String ID_DATA_BEAN = "dataBean";
	public static final String ID_FOR_OP = "forOp";
	public static final String ID_FOR_OP_TEXT = "forOpText";
	public static final String ID_HELPER = "helper";
	public static final String ID_HELPERS = "helpers";
	public static final String ID_MESSAGE = "msg";
  public static final String ID_IMPORTED_YN = "importedYN";
  public static final String ID_ARDAIS_ID = "ardaisId";
  public static final String ID_CUSTOMER_ID = "customerId";

	// Search context values.
	// Add Samples to Project.
	public static final String SEARCH_CONTEXT_ADD = "add";

	// Place Samples on Hold.
	public static final String SEARCH_CONTEXT_HOLD = "hld";

	// Manage Project.
	public static final String SEARCH_CONTEXT_MANAGE = "mng";

	// Remove Samples from Project.
	public static final String SEARCH_CONTEXT_REMOVE = "rmv";
	
	// Remove Samples from Hold.
	public static final String SEARCH_CONTEXT_RHOLD = "rhd";

	// DDC Donor Profile.
	public static final String SEARCH_CONTEXT_DONOR_PROFILE = "dpr";
	
	// DDC Case Profile Entry.
	public static final String SEARCH_CONTEXT_CASE_PROFILE_ENTRY= "cpr";

	// DDC Path Report Full Text.
	public static final String SEARCH_CONTEXT_PATH_FULL = "pfl";

	// DDC Path Report Abstract.
	public static final String SEARCH_CONTEXT_PATH_ABSTRACT = "pab";

	// DDC Path Report Abstract.
	public static final String SEARCH_CONTEXT_CLINICAL_DATA_EXTRACTION = "cde";

	// Holds messages for the user, such as validation problems.
	private MessageHelper _msgHelper;	

	// An ListGenerator for getting contents of dropdown 
	// lists and performing code lookups.
	ListGenerator _listGenerator;
/**
 * JspHelper constructor.
 */
public JspHelper() {
	super();
}
/**
 * Generates the HTML for a SELECT element with OPTION elements,
 * based on the input <code>LegalValueSet</code>.  The list
 * is given the name <code>listName</list>.  The first item in 
 * the list is selected.
 *
 * @param  listName  the value of the SELECT element's name attribute
 * @param  values  the set of legal values to be added as OPTION elements
 * @return  The HMTL that represents the SELECT element with its OPTION elements.
 */
String generateSelectList(String listName, LegalValueSet values) {
	return generateSelectList(listName, values, null);
}
/**
 * Generates the HTML for a SELECT element with OPTION elements,
 * based on the input <code>LegalValueSet</code>.  The list
 * is given the name <code>listName</list>.  The item that 
 * matches <code>selectedValue</code> is selected if it is not
 * <code>null</code>.  Otherwise, the first item in the list is
 * selected.
 *
 * @param  listName  the value of the SELECT element's name attribute
 * @param  values  the set of legal values to be added as OPTION elements
 * @param  selectedValue  the item that should be selected in the list
 * @return  The HMTL that represents the SELECT element with its OPTION elements.
 */
String generateSelectList(String listName, LegalValueSet values, String selectedValue) {
	SelectList list = new SelectList(listName, values);
	if (selectedValue != null) {
		list.setSelectedValue(selectedValue);
	}
	else {
		list.setSelectedIndex(0);
	}
	return list.generate();
}
/**
 * Returns HTML that contains a TR with a single TD that can be 
 * used to divide rows in a table.  The divider is meant to be 
 * used in tables with a cellspacing of 1, and it is relatively 
 * positioned so that it eliminates the appearance of the background 
 * behind the cellspacing.
 *
 * @param  colspan  the colspan to be used for the TD
 * @return  The HTML containing the divider..
 */
public static String getBlankDivider(int colspan) {
	StringBuffer buf = new StringBuffer(128);
	buf.append("<tr>");
    buf.append("<td style=\"margin: 0; padding: 0;\" colspan=\"");
    buf.append(String.valueOf(colspan));
    buf.append("\"><div style=\"background-color: white; margin: 0; padding: 0; border-width: 0; position: relative; left: -1px; width: 105%;\">");
	buf.append("</div></td></tr>");
	return (buf.toString());
}
/**
 * Returns a <code>ListGenerator</code>, which can be
 * used for creating dropdown lists and performing code lookups.
 * 
 * @return  A <code>ListGenerator</code>.
 */
ListGenerator getListGenerator() throws ClassNotFoundException, RemoteException, CreateException, NamingException {
	if (_listGenerator == null) {
    ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
    _listGenerator = home.create();
	}
	return _listGenerator;
}
/**
 * Returns a <code>MessageHelper</code>.
 *
 * @return  The <code>MessageHelper</code>.
 */
public MessageHelper getMessageHelper() {
	if (_msgHelper == null) {
		_msgHelper = new MessageHelper();
	}
	return _msgHelper;
}
/**
 * Returns HTML that contains all messages set in the helper,
 * appropriate for display in BIGR. If there are no messages, then
 * the empty string is returned.
 *
 * @return  The HTML containing the messages.
 */
public String getMessages() {
	return (_msgHelper == null) ? "" : _msgHelper.getMessages();
}

public String getTestMessages() {
	return (_msgHelper == null) ? "" : _msgHelper.getMessages();
}
/**
 * Returns HTML that contains all messages set in the helper,
 * appropriate for display in BIGR.  <code>colspan</code> specifies 
 * the colspan  of the single TD within the TR that displays the messages.
 * If colspan is less than or equal to zero, then the colspan attribute
 * is not used in the TD.  If there are no messages, then the
 * empty string is returned.
 *
 * @return  The HTML containing the messages.
 */
public String getMessagesForColspan(int colspan) {
	return (_msgHelper == null) ? "" : _msgHelper.getMessages(colspan);
}
/**
 * Returns HTML that contains a TR with a single TD with the 
 * text "OR" that can be used to indicate multiple choices.
 *
 * @param  colspan  the colspan to be used for the TD
 * @return  The HTML containing the OR row.
 */
public static String getOrDivider(int colspan) {
	StringBuffer buf = new StringBuffer(128);
	buf.append("<tr class=\"green\">");
    buf.append("<td align=\"center\" colspan=\"");
    buf.append(String.valueOf(colspan));
    buf.append("\"><b>OR</b></td></tr>");
	return (buf.toString());
}
/**
 * Returns <code>true</code> if the string is either <code>null</code>
 * or the empty string (""); <code>false</code> otherwise.
 *
 * @param  s  the <code>String</code>
 * @return  <code>true</code> if the string is empty
 */
public static boolean isEmpty(String s) {
    return ApiFunctions.isEmpty(s);
}
/**
 * Returns <code>true</code> if the data associated 
 * with this helper is valid; otherwise returns <code>false</code>.
 * Generally this method is called after calling the constructor
 * that takes a <code>ServletRequest</code> as input, since it
 * is desirable to validate the request parameters.  If validation
 * fails, the <code>getMessages</code> method can be called to
 * display a message to the user indicating the problems.
 *
 * @return  <code>true</code> if all parameters are valid;
 *			<code>false</code> otherwise.
 */
public boolean isValid() throws ApiException {
	return true;
}

/**
 * Returns a trimmed version of the input <code>String</code>.  If the
 * input <code>String</code> is <code>null</code>, then return
 * <code>null</code>.
 *
 * @param  s  the <code>String</code>
 * @return  a trimmed version of the <code>String</code>, or <code>null</code> 
 */
public static String safeTrim(String s) {
	return ApiFunctions.safeTrim(s);    
}
}
