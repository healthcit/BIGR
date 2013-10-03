package com.ardais.bigr.web.taglib;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.RequestUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.gboss.GbossValue;
import com.gulfstreambio.gboss.GbossValueSet;
import com.ardais.bigr.library.web.form.QueryForm;
/**
 * Base class for the class which renders HTML <SELECT> element, <INPUT> box along with labels.  
 * 
 * Creation date: (6/24/2002 3:53:10 PM)
 * @author: Nagaraja Rao
 */
public class ListWithOtherSupport extends TagSupport {
  private String _name;
  private String _property;
  private String _otherProperty;
  private String _propertyLabel;
  private String _otherPropertyLabel;
  private String _otherPropertySize;
  private String _otherId;
  private String _required;
  private String _colspan;
  private String _popUpURL;
  private String _otherCode;
  private String _onChange;
  private String _firstValue;
  private String _firstDisplayValue;
  private String _includeAlphaLookup;
  private String _mode;
  private String _cssClass;
  /**
  * Creates a new <code>ListWithOtherSupport</code>.
  */
  public ListWithOtherSupport() {
    super();
  }
  /**
   * Writes the HTML code for SELECT, INPUT element with labels to the HTTP response.
   *
   * @return int
   * @exception javax.servlet.jsp.JspException The exception description.
   */

  public int doStartTag() throws javax.servlet.jsp.JspException {
    JspWriter out = pageContext.getOut();
    GbossValueSet options = getOptions();
    //Make full mode as default	
    if ((_mode == null) || (_mode.equals("full")))
      doFullMode(out, options);
    else if (_mode.equals("basic"))
      doBasicMode(out, options);
    else if (_mode.equals("nolayout"))
      doNoLayoutMode(out, options);
    else
      throw new JspException("Error in tag ListWithOtherSupport: value for attribute mode is not valid. Use basic/full");
    return SKIP_BODY;
  }

  private void doNoLayoutMode(JspWriter out, GbossValueSet options) throws JspException {
    // Check for otherProperty and otherPropertyLabel 
    if (_otherProperty == null) {
      throw new JspException("Error in tag ListWithOtherSupport: value for attribute otherProperty must be specified.");
    }
    if (_otherPropertyLabel == null) {
      throw new JspException("Error in tag ListWithOtherSupport: value for attribute otherPropertyLabel must be specified.");
    }
    try {
    
      //when an alphanumeric search (or refresh) is done, the corresponding SELECT control's 
      //options are removed and then replaced with options that match the search (or with
      //all options, in the case of a refresh).  When this happens, we want the SELECT control's
      //onChange event to fire, since the option selected in the SELECT control has changed.  
      //However, that event never fires.  So when the "Search" or "Refresh" buttons are clicked
      //we must expressly call the same code that would've been executed for that event.
      String searchDiagName = "searchDiag" + _property;
      if ("true".equals(_includeAlphaLookup)) {
        StringBuffer sb = new StringBuffer(250);
        sb.append("(this.form['");
        sb.append(searchDiagName);
        sb.append("'], this.form['");
        sb.append(_property);
        sb.append("']);");
        sb.append("showOtherForList(this.form['");
        sb.append(_property);
        sb.append("'].value,'");
        sb.append(_otherProperty);
        sb.append("','");
        sb.append(_otherCode);
        sb.append("');");
        if (_onChange != null) {
          sb.append(_onChange);
        }
        String searchAction = "findListOptions" + sb.toString();
        String restoreAction = "restoreListOptions" + sb.toString();
        out.print(
          "<input type=\"text\" size=\"40\" name=\""
            + searchDiagName
            + "\" maxlength=\"50\" onKeyPress=\"if(event.keyCode == 13){"
            + searchAction
            + "return false;}\">");
        out.print("&nbsp;<input type=\"button\" value=\"Search\" name=\"search\" onClick=\"" + searchAction + "\">");
        out.print("&nbsp;<input type=\"button\" value=\"Refresh\" name=\"refresh\" onClick=\"" + restoreAction + "\">");
        out.print("<p style=\"margin: 3px 0 0 0; border: 0; padding: 0;\">");
      }

      //determine what should be done for the onChange event.  At a minimum we need to enable
      //the "Other X" text field so do that, and if there is additional functionality that needs
      //to be invoked (i.e. _onChange is not null) include that as well.  
      //NOTE: if this code is changed, update the code being placed into the StringBuffer above!
      String onChangeString = "showOtherForList(this.value,'" + _otherProperty + "','" + _otherCode + "');";

      if (_onChange != null) {
        onChangeString = onChangeString + _onChange;
      }

      if (_cssClass == null) {
        out.print("<select id=\"" + _property + "\" name=\"" + _property + "\" onChange=\"" + onChangeString + "\">\n");
      }
      else {
        out.print(
          "<select class=\"" + _cssClass + "\" id=\"" + _property + "\" name=\"" + _property + "\" onChange=\"" + onChangeString + "\">\n");
      }

      //Insert first value, display value for <SELECT>
      if ((_firstValue != null) || (_firstDisplayValue != null)) {
        out.print(
          "<option value=\""
            + ((_firstValue == null) ? "" : _firstValue)
            + "\">"
            + ((_firstDisplayValue == null) ? "" : _firstDisplayValue)
            + "</option>\n");
      }
  
      
      //parsing the appendix for the fields
      if(_property.indexOf("&")>-1) {
        _property = _property.substring(0, _property.indexOf("&"));
      }
   
      Object propertyValue = RequestUtils.lookup(pageContext, _name, _property, null);

      //Generates list of options for drop down list. 
      generateOptions(propertyValue, options, out);

      out.print("</select>");
      if (_popUpURL != null && !QueryForm.CONTROL_OTHER.equals(_otherProperty)) {
        if ("true".equals(_includeAlphaLookup)) {
          out.print(
            "&nbsp;<input type=\"Button\"  name=\"show"+ _property +"Hierarchy\" value=\"...\" onClick=\" showHierarchyWithSearch('"
              + _property
              + "','"
              + _popUpURL
              + "','"
              + searchDiagName
              + "');\">\n");
        } else {
          out.print(
            "&nbsp;<input type=\"Button\"  name=\"show"+ _property +"Hierarchy\" value=\"...\" onClick=\" showHierarchy('"
              + _property
              + "','"
              + _popUpURL
              + "');\">\n");
        }
      }
    
      //SWP-1114, don't draw Other fields in queryForm
     if(!QueryForm.CONTROL_OTHER.equals(_otherProperty)) {  
      //Generate HTML for other <INPUT> 
      out.print("<p style=\"margin: 3px 0 0 0; border: 0; padding: 0;\">");
      out.print(_otherPropertyLabel);
      out.print(":&nbsp;");
      String otherPropertySize = _otherPropertySize;
      if (ApiFunctions.isEmpty(otherPropertySize)) {
        otherPropertySize = "30";
      }
     
  
      out.print("<input type=\"text\" name=\"" + _otherProperty + "\" size=\"" + otherPropertySize + "\" maxlength=\"200\" value = \"");

      Object value = RequestUtils.lookup(pageContext, _name, _otherProperty, null);

      //If there is no value for otherValue it should be disabled.
      if ((value != null) || (_otherCode.equals(propertyValue))) {
        out.print((value != null) ? value.toString() : ApiFunctions.EMPTY_STRING);
        out.print("\">\n");
      } else {
        out.print("N/A\" disabled>\n");
      }
     }
    
    } 
    catch (IOException ioe) {
      throw new JspException("Exception in ListWithOtherSupport:" + ioe.toString());
    }
    
  }

  /**
   * Writes code to out put stream in full mode.  In this mode code will be generated based on the all the attributes.
   * @param out  JspWriter
   * @param options GbossValueSet
   * @exception javax.servlet.jsp.JspException The exception description.
   */
  private void doFullMode(JspWriter out, GbossValueSet options) throws javax.servlet.jsp.JspException {
    try {
      //Check valid values for required attribute.
      if ((_required != null) && (!_required.equals("true")))
        throw new JspException("Error in tag ListWithOtherSupport: value for attribute required is not valid.");

      // Check valid values for colspan attribute
      if (_colspan != null) {
        try {
          Integer.parseInt(_colspan);
        } catch (NumberFormatException nfe) {
          throw new JspException("Error in tag ListWithOtherSupport: value for attribute colspan is not a number.");
        }
      }
      //Check for otherProperty
      if (_otherProperty == null)
        throw new JspException("Error in tag ListWithOtherSupport: value for attribute otherProperty must be specified.");

      //Check for otherPropertyLabel
      if (_otherPropertyLabel == null)
        throw new JspException("Error in tag ListWithOtherSupport: value for attribute otherPropertyLabel must be specified.");

      out.print("<tr class=\"white\"");
      if (!ApiFunctions.isEmpty(getId())) {
        out.print(" id=\"");
        out.print(getId());
        out.print("\"");
      }
      out.print(">\n");
      out.print("<td class=\"grey\" align = \"right\">\n");
      out.print("<b>" + _propertyLabel + "</b>\n");

      out.print("<span id=\"" + getId() + "Required" + "\">");
      //Insert red asterisk, which indicates required field
      if ((_required != null) && (_required.equals("true"))) {
        out.print("<font color = \"red\">*</font>");
      }
      out.print("</span>\n");
      //Insert colspan attribute for <SELECT> if needed
      if (_colspan == null) {
        out.print("</td><td>\n");
      } else {
        out.print("</td><td colspan=" + _colspan + ">\n");
      }

      //when an alphanumeric search (or refresh) is done, the corresponding SELECT control's 
      //options are removed and then replaced with options that match the search (or with
      //all options, in the case of a refresh).  When this happens, we want the SELECT control's
      //onChange event to fire, since the option selected in the SELECT control has changed.  
      //However, that event never fires.  So when the "Search" or "Refresh" buttons are clicked
      //we must expressly call the same code that would've been executed for that event.
      String searchDiagName = null;
      if ("true".equals(_includeAlphaLookup)) {
        searchDiagName = "searchDiag" + _property;
        StringBuffer sb = new StringBuffer(250);
        sb.append("(this.form['");
        sb.append(searchDiagName);
        sb.append("'], this.form['");
        sb.append(_property);
        sb.append("']);");
        sb.append("showOtherForList(this.form['");
        sb.append(_property);
        sb.append("'].value,'");
        sb.append(_otherProperty);
        sb.append("','");
        sb.append(_otherCode);
        sb.append("');");
        if (_onChange != null) {
          sb.append(_onChange);
        }
        String searchAction = "findListOptions" + sb.toString();
        String restoreAction = "restoreListOptions" + sb.toString();
        out.print(
          "<input type=\"text\" name=\""
            + searchDiagName
            + "\" maxlength=\"20\" onKeyPress=\"if(event.keyCode == 13){"
            + searchAction
            + "return false;}\">");
        out.print("&nbsp;<input type=\"button\" value=\"Search\" name=\"search\" onClick=\"" + searchAction + "\">");
        out.print("&nbsp;<input type=\"button\" value=\"Refresh\" name=\"refresh\" onClick=\"" + restoreAction + "\">");
        out.print("<p style=\"margin: 3px 0 0 0; border: 0; padding: 0;\">");
      }

      //determine what should be done for the onChange event.  At a minimum we need to enable
      //the "Other X" text field so do that, and if there is additional functionality that needs
      //to be invoked (i.e. _onChange is not null) include that as well.  
      //NOTE: if this code is changed, update the code being placed into the StringBuffer above!
      String onChangeString = "showOtherForList(this.value,'" + _otherProperty + "','" + _otherCode + "');";

      if (_onChange != null)
        onChangeString = onChangeString + _onChange;

      if (_cssClass == null)
        out.print("<select name=\"" + _property + "\" onChange=\"" + onChangeString + "\">\n");
      else
        out.print(
          "<select class=\"" + _cssClass + "\"  name=\"" + _property + "\" onChange=\"" + onChangeString + "\">\n");

      //Insert first value, display value for <SELECT>
      if ((_firstValue != null) || (_firstDisplayValue != null)) {
        out.print(
          "<option value=\""
            + ((_firstValue == null) ? "" : _firstValue)
            + "\">"
            + ((_firstDisplayValue == null) ? "" : _firstDisplayValue)
            + "</option>\n");
      }

      Object propertyValue = RequestUtils.lookup(pageContext, _name, _property, null);

      //Generates list of options for drop down list. 
      generateOptions(propertyValue, options, out);

      out.print("</select>");
      if (_popUpURL != null) {
        if ("true".equals(_includeAlphaLookup)) {
          out.print(
            "&nbsp;<input type=\"Button\"  name=\"show"+ _property +"Hierarchy\" value=\"...\" onClick=\" showHierarchyWithSearch('"
              + _property
              + "','"
              + _popUpURL
              + "','"
              + searchDiagName
              + "');\">\n");
        } else {
          out.print(
            "&nbsp;<input type=\"Button\"  name=\"show"+ _property +"Hierarchy\" value=\"...\" onClick=\" showHierarchy('"
              + _property
              + "','"
              + _popUpURL
              + "');\">\n");
        }
      }
      out.print("</td></tr>\n");
      out.print("<tr class=\"white\"");
      if (!ApiFunctions.isEmpty(_otherId)) {
        out.print(" id=\"");
        out.print(_otherId);
        out.print("\"");
      }
      out.print(">\n");
      out.print("<td class=\"grey\" align = \"right\"> <b>" + _otherPropertyLabel + "</b>\n");
      out.print("<span id=\"" + _otherId + "Required" + "\">");
      if ((_required != null) && (_required.equals("true"))) {
        out.print("<font color = \"red\">*</font>");
      }
      out.print("</span>\n");
      out.print("</td>\n");
      
      //Insert colspan attribute for <INPUT> if needed
      if (_colspan == null) {
        out.print("<td>\n");
      } else {
        out.print("<td colspan=" + _colspan + ">\n");
      }

      //Generate HTML for other <INPUT> 
      String otherPropertySize = _otherPropertySize;
      if (ApiFunctions.isEmpty(otherPropertySize)) {
        otherPropertySize = "30";
      }
      out.print("<input type=\"text\" name=\"" + _otherProperty + "\" size=\"" + otherPropertySize + "\" maxlength=\"200\" value = \"");

      Object value = RequestUtils.lookup(pageContext, _name, _otherProperty, null);

      //If there is no value for otherValue it should be disabled.
      if ((value != null) || (_otherCode.equals(propertyValue))) {
        out.print((value != null) ? value.toString() : ApiFunctions.EMPTY_STRING);
        out.print("\">\n");
      } else {
        out.print("N/A\" disabled>\n");
      }

      out.print("</td></tr>");
    } catch (IOException ioe) {
      throw new JspException("Exception in ListWithOtherSupport:" + ioe.toString());
    }
  }
  /**
   * Writes code to out put stream in basic mode. Code is generated for dropdown list and 
   * ellipsis only without <tr> and <td> tags.
   * @param out  JspWriter
   * @param options GbossValueSet
   * @exception javax.servlet.jsp.JspException The exception description.
   */
  private void doBasicMode(JspWriter out, GbossValueSet options) throws javax.servlet.jsp.JspException {
    try {

      String temp = "<select ";
      //Add CSS class name to <SELECT> if specified.
      if (_cssClass != null)
        temp = temp + "class=\"" + _cssClass + "\"";

      //determine what should be done for the onChange event.  At a minimum we need to enable
      //the "Other X" text field so do that, and if there is additional functionality that needs
      //to be invoked (i.e. _onChange is not null) include that as well.  
      //NOTE: if this code is changed, update the code being placed into the StringBuffer above!

      if (_onChange == null)
        out.print(temp + "  name=\"" + _property + "\">\n");
      else
        out.print(temp + "  name=\"" + _property + "\" onChange=\"" + _onChange + "\">\n");

      //Insert first value, display value for <SELECT>
      if ((_firstValue != null) || (_firstDisplayValue != null)) {
        out.print(
          "<option value=\""
            + ((_firstValue == null) ? "" : _firstValue)
            + "\">"
            + ((_firstDisplayValue == null) ? "" : _firstDisplayValue)
            + "</option>\n");
      }

      Object propertyValue = RequestUtils.lookup(pageContext, _name, _property, null);

      //Generates list of options for drop down list. 
      generateOptions(propertyValue, options, out);

      out.print("</select>");
      if (_popUpURL != null) {
        out.print(
          "&nbsp;<input type=\"Button\"  name=\"show"+ _property +"Hierarchy\" value=\"...\" onClick=\" showHierarchy('"
            + _property
            + "','"
            + _popUpURL
            + "');\">\n");
      }
    } catch (IOException ioe) {
      throw new JspException("Exception in ListWithOtherSupport:" + ioe.toString());
    }
  }

  /**
   * Writes list of options to out put stream.
   * @param list Vector
   * @param out JspWriter 
   * @exception javax.servlet.jsp.JspException The exception description.
   */
  private void generateOptions(Object propertyValue, GbossValueSet options, JspWriter out) throws JspException {
    try {
     
      //Generate options
      Iterator optionIterator = options.getValues().iterator();
      while (optionIterator.hasNext() ){
        GbossValue option = (GbossValue)optionIterator.next();
        //for SWP-1114, don't draw Other tissue when the formbean is queryForm
        if(option.getDescription().equalsIgnoreCase(_otherPropertyLabel) && QueryForm.CONTROL_OTHER.equalsIgnoreCase(_otherProperty))
        continue;
        
        if (propertyValue != null && propertyValue.toString().equals(option.getCui())) {
          out.print("<option value=\"" + option.getCui() + "\" selected>" + option.getDescription() + "</option>");
        } else {
          out.print("<option value=\"" + option.getCui() + "\">" + option.getDescription() + "</option>\n");
        }
      }
    } catch (IOException ioe) {
      throw new JspException("Exception in ListWithOtherSupport:" + ioe.toString());
    }
  }
  /**
   * Protected method to be implemented by sub class. Returns GbossValueSet of options for <SELECT>
   * Creation date: (6/24/2002 4:03:40 PM)
   * @return java.util.Vector
   */
  protected GbossValueSet getOptions() {
    return null;
  }
  /**
   * Sets the <code>colspan</code> 
   * 
   * @param new_propertyColspan java.lang.String
   */
  public void setColspan(java.lang.String new_propertyColspan) {
    _colspan = new_propertyColspan;
  }
  /**
   * Sets the <code>FirstDisplayValue</code> 
   * 
   * @param <code>FirstDisplayValue</code> 
   */
  public void setFirstDisplayValue(java.lang.String newFirstDisplayValue) {
    _firstDisplayValue = newFirstDisplayValue;
  }
  /**
   * Sets the <code>FirstValue</code> 
   * 
   * @param newFirstValue java.lang.String
   */
  public void setFirstValue(java.lang.String newFirstValue) {
    _firstValue = newFirstValue;
  }
  /**
   * Sets the <code>includeAlphaLookup</code> field
   * 
   * @param newPropertyIncludeAlphaLookup java.lang.String
   */
  public void setIncludeAlphaLookup(String newPropertyIncludeAlphaLookup) {
    _includeAlphaLookup = newPropertyIncludeAlphaLookup;
  }
  /**
   * Sets the <code>name</code> 
   * 
   * @param newName java.lang.String
   */
  public void setName(java.lang.String newName) {
    _name = newName;
  }
  /**
   * Sets the <code>onChange</code> 
   * Creation date: (6/28/2002 11:27:04 AM)
   * @param newPropertyOnChange java.lang.String
   */
  public void setOnChange(java.lang.String newPropertyOnChange) {
    _onChange = newPropertyOnChange;
  }
  /**
   * Sets the <code>otherCode</code>
   * 
   * @param newOtherCode java.lang.String
   */
  public void setOtherCode(java.lang.String newOtherCode) {
    _otherCode = newOtherCode;
  }
  /**
   * Sets the <code>otherProperty</code> 
   * 
   * @param newOtherProperty java.lang.String
   */
  public void setOtherProperty(java.lang.String newOtherProperty) {
    _otherProperty = newOtherProperty;
  }
  /**
   * Sets the <code>otherPropertyLabel</code> 
   * 
   * @param newOtherPropertyLabel java.lang.String
   */
  public void setOtherPropertyLabel(java.lang.String newOtherPropertyLabel) {
    _otherPropertyLabel = newOtherPropertyLabel;
  }
  /**
   * Sets the <code>otherPropertySize</code> 
   * 
   * @param otherPropertySize java.lang.String
   */
  public void setOtherPropertySize(java.lang.String otherPropertySize) {
    _otherPropertySize = otherPropertySize;
  }
  /**
   * Sets the <code>popUpURL</code> 
   * 
   * @param newPopUpURL java.lang.String
   */
  public void setPopUpURL(java.lang.String newPopUpURL) {
    _popUpURL = newPopUpURL;
  }
  /**
   * Sets the <code>property</code> 
   * 
   * @param newProperty java.lang.String
   */
  public void setProperty(java.lang.String newProperty) {
    _property = newProperty;
  }
  /**
   * Sets the <code>propertyLabel</code> 
   * 
   * @param newPropertyLabel java.lang.String
   */
  public void setPropertyLabel(java.lang.String newPropertyLabel) {
    _propertyLabel = newPropertyLabel;
  }
  /**
   * Sets the <code>required</code> 
   * 
   * @param newPropertyRequired java.lang.String
   */
  public void setRequired(java.lang.String newPropertyRequired) {
    _required = newPropertyRequired;
  }
  /**
   * Returns the mode.
   * @return String
   */
  public String getMode() {
    return _mode;
  }

  /**
   * Sets the mode.
   * @param mode The mode to set
   */
  public void setMode(String mode) {
    _mode = mode;
  }

  /**
   * Returns the c.
   * @return String
   */
  public String getCssClass() {
    return _cssClass;
  }

  /**
   * Sets the c.
   * @param c The c to set
   */
  public void setCssClass(String c) {
    _cssClass = c;
  }

  /**
   * @param string
   */
  public void setOtherId(String string) {
    _otherId = string;
  }

}
