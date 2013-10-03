package com.gulfstreambio.kc.web.taglib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts.util.ResponseUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.det.DetValueSetValue;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionRollupValueSet;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValue;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueTypes;
import com.gulfstreambio.kc.web.support.HtmlElementValue;
import com.gulfstreambio.kc.web.support.HtmlElementValueHierarchyMulti;
import com.gulfstreambio.kc.web.support.QueryFormElementContext;
import com.gulfstreambio.kc.web.support.WebUtils;

public class QueryValueSetTag extends KcBaseTag {

  public QueryValueSetTag() {
    super();
  }

  private QueryFormElementContext getElementContext() {
    return getFormContext().getQueryFormElementContext();
  }
  
  public int doEndTag() throws JspException {
    QueryFormElementContext context = getElementContext();
    String contextPath = getContextPath();

    QueryFormDefinitionRollupValueSet rollupValueSet = context.getValueSetRollup();
    DetValueSet broadValueSet = 
      (rollupValueSet != null) ? new DetValueSet(rollupValueSet) : context.getValueSetBroad();
    
    // Make an HTML value set from the broad value set and the selected values and write it to 
    // the response.
    String property = context.getPropertyValue();
    QueryFormDefinitionValue[] values = context.getValues();
    String[] selectedValues = new String[values.length];
    for (int i = 0; i < values.length; i++) {
      QueryFormDefinitionValue value = values[i];
      if (value.getValueType().equals(QueryFormDefinitionValueTypes.COMPARISON)) {
        selectedValues[i] = ((QueryFormDefinitionValueComparison) value).getValue();
      }
    }
    
    StringBuffer buf = new StringBuffer(1024);
    buf.append("<div");
    WebUtils.writeHtmlAttribute(buf, "class", "kcCvList");
    buf.append(">");

    List htmlValues = new ArrayList();
    addHtmlValues(htmlValues, broadValueSet.getValues(), null, property, selectedValues);
    writeValueSet(htmlValues, buf);
    
    buf.append("</div>");
    ResponseUtils.write(pageContext, buf.toString());
    
    // Write script that will create JavaScript ARTS concepts for each value to the script buffer.
    buf = WebUtils.getJavaScriptBuffer(pageContext);
    addConcepts(broadValueSet.getValues(), buf);
    
    return EVAL_PAGE;
  }
  

  private void addHtmlValues(List htmlValues, DetValueSetValue[] values, 
                             HtmlElementValue parent, String property, String[] selectedValues) { 
    for (int i = 0; i < values.length; i++) {
      DetValueSetValue value = values[i];
      String cui = value.getCui();
      if (!value.isOtherValue()) {
        boolean selected = ApiFunctions.safeArrayContains(selectedValues, cui);
        HtmlElementValue htmlValue = null; 
        if (parent == null) {          
          htmlValue = new HtmlElementValueHierarchyMulti(value, property, "", selected, null);
          htmlValues.add(htmlValue);
        }
        else {
          htmlValue = parent.addElementValue(value, property, "", selected);
        }
        
        DetValueSetValue[] children = value.getValues();
        if (children.length > 0) {
          addHtmlValues(htmlValues, children, htmlValue, property, selectedValues);
        }
      }
    }
  }

  private void writeValueSet(List htmlValues, StringBuffer buf) {
    String contextPath = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
    buf.append("<table");
    WebUtils.writeHtmlAttribute(buf, "cellspacing", "0");
    WebUtils.writeHtmlAttribute(buf, "cellpadding", "0");
    WebUtils.writeHtmlAttribute(buf, "border", "0");
    buf.append('>');

    Iterator i = htmlValues.iterator();
    while (i.hasNext()) {
      HtmlElementValueHierarchyMulti value = (HtmlElementValueHierarchyMulti) i.next();
      value.toHtml(contextPath, buf);
    }

    buf.append("</table>");
  }
  
  private void addConcepts(DetValueSetValue[] values, StringBuffer buf) {
    for (int i = 0; i < values.length; i++) {
      DetValueSetValue value = values[i];
      buf.append("GSBIO.ARTS.addConcept(\"");
      Escaper.jsEscapeInScriptTag(value.getCui(), buf);
      buf.append("\", \"");
      Escaper.jsEscapeInScriptTag(value.getDescription(), buf);
      buf.append("\");");
      addConcepts(value.getValues(), buf);
    }
  }
}
