package com.ardais.bigr.web.taglib;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts.util.ResponseUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.util.UniqueIdGenerator;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.det.DetValueSetValue;
import com.gulfstreambio.kc.web.support.DataFormElementContext;
import com.gulfstreambio.kc.web.support.HtmlElementValueSet;
import com.gulfstreambio.kc.web.support.HtmlElementValueSetSingle;
import com.gulfstreambio.kc.web.support.KcUiContext;
import com.gulfstreambio.kc.web.support.WebUtils;
import com.gulfstreambio.kc.web.taglib.DataElementBaseTag;

/**
 * Tag handler for the <code>dataElementInDerivativeOperation</code> tag.  This tag
 * generates the html for a data element for a child sample in a derivative operation.
 * 
 * <p>The context in which this tag is used must satisfy several requirements:
 * <ul>
 * <li>The page must include common.js.</li>
 * <li>The page must set the variable elementContext for the data element.</li>
 * </ul>
 */
public class DataElementInDerivativeOperationTag extends DataElementBaseTag {
  private String _firstValue;
  private String _firstDisplay;

  private String getFirstValue() {
    return _firstValue;
  }

  public void setFirstValue(String value) {
    _firstValue = value;
  }

  private String getFirstDisplay() {
    return _firstDisplay;
  }

  public void setFirstDisplay(String display) {
    _firstDisplay = display;
  }

  public void release() {
    super.release();
    _firstValue = null;
    _firstDisplay = null;
  }
  
  /**
   * Creates the appropriate JavaScript object for the data element.
   *
   * @return  EVAL_BODY_INCLUDE
   */
  public int doStartTag() throws JspException {    
    StringBuffer scriptBuf = WebUtils.getJavaScriptBuffer(pageContext);
    createJavascriptObject(scriptBuf);
    return EVAL_BODY_INCLUDE;
  }

  public int doEndTag() throws JspException {
    StringBuffer buf = new StringBuffer(2048);
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest(); 
    DataFormElementContext context = getDataElementContext();
    UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();

    String inputPage = null;
    String jspPath = WebUtils.getContextRelativePath();
    if (context.isDatatypeInt()) {
      inputPage = jspPath + "/data/element/elementSingleInt.jsp";
    }
    else if (context.isDatatypeFloat()) {
      inputPage = jspPath + "/data/element/elementSingleFloat.jsp";
    }
    else if (context.isDatatypeText()) {
      inputPage = jspPath + "/data/element/elementSingleText.jsp";
    }
    else if (context.isDatatypeDate()) {
      inputPage = jspPath + "/data/element/elementSingleDate.jsp";
    }
    else if (context.isDatatypeVpd()) {
      inputPage = jspPath + "/data/element/elementSingleVpd.jsp";
    }
    else if (context.isDatatypeReport()) {
      String name = idgen.getUniqueId();
      String id = idgen.getUniqueId();
      context.setHtmlIdPrimary(id);
      buf.append("<input");
      WebUtils.writeHtmlAttribute(buf, "type", "text");
      WebUtils.writeHtmlAttribute(buf, "id", id);
      WebUtils.writeHtmlAttribute(buf, "name", name);
      WebUtils.writeHtmlAttribute(buf, "size", "25");
      WebUtils.writeHtmlAttribute(buf, "maxlength", "4000");
      WebUtils.writeHtmlAttribute(buf, "value", ApiFunctions.safeString(context.getValue()));
      buf.append(">");
    }
    else if (context.isDatatypeCv()) {

      // Get the broad value set.  For now we're only going to be using the broad value set,
      // since there would be some issues to be resolved with copying/defaulting functionality
      // if narrow and/or standard value sets were used (the value to be copied or defaulted
      // may not be in the narrow or standard set, so that functionality would need to change 
      // the value set in use on the target control(s) to the broad one before setting the value).
      DetValueSet broad = context.getValueSetBroad();
      HtmlElementValueSet broadValueSet = createValueSet(broad);
      addConcepts(broad.getValues(), WebUtils.getJavaScriptBuffer(pageContext));
      broadValueSet.toHtml(getContextPath(), buf);
      context.setHtmlIdPrimary(context.getHtmlIdBroadValueSet());
      
      //if this element is other-enabled, show the other control
      if (context.isHasOtherValue()) {
        boolean disabled = !context.getOtherValueCui().equals(context.getValue());
        String valueOther = (disabled) ? "N/A" : context.getValueOther();
        valueOther = (valueOther == null) ? "" : valueOther;
        String name = idgen.getUniqueId();
        String id = idgen.getUniqueId();
        context.setHtmlIdOther(id);
        buf.append("&nbsp;");
        buf.append("<input");
        WebUtils.writeHtmlAttribute(buf, "type", "text");
        WebUtils.writeHtmlAttribute(buf, "id", id);
        WebUtils.writeHtmlAttribute(buf, "name", name);
        WebUtils.writeHtmlAttribute(buf, "value", valueOther);
        WebUtils.writeHtmlAttribute(buf, "size", "15");
        WebUtils.writeHtmlAttribute(buf, "maxlength", "200");
        if (disabled) {
          buf.append(" disabled");
        }
        buf.append(">");
      }
    }
    else {
      //we should never get here, but in case we encounter an unknown data type
      //handle it gracefully
      buf.append("Control for ");
      buf.append(context.getLabel());
      buf.append(" (");
      buf.append(context.getDatatype());
      buf.append(")");
    }
    
    if (inputPage != null) {
      try {
        pageContext.include(inputPage);          
      }
      catch (IOException e) {
        throw new JspException(e);
      }
      catch (ServletException e) {
        throw new JspException(e);
      }
    }
    else {
      ResponseUtils.write(pageContext, buf.toString());
    }

    StringBuffer scriptBuf = WebUtils.getJavaScriptBuffer(pageContext);
    setJavascriptHtmlIds(scriptBuf);
    registerJavascriptEventHandler(scriptBuf);
    WebUtils.writeJavaScriptBuffer(pageContext);

    return EVAL_PAGE;
  }
  
  private HtmlElementValueSet createValueSet(DetValueSet valueSet) {
    if (valueSet == null) {
      return null;
    }
    ServletRequest request = pageContext.getRequest();
    DataFormElementContext context = getDataElementContext();
    String selectedValue = context.getValue();
    String[] excludedValues = context.getExcludedValues();

    HtmlElementValueSet htmlValueSet = new HtmlElementValueSetSingle(request, context, valueSet, 
        selectedValue, excludedValues, true);
    UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
    String firstValue = getFirstValue();
    if (firstValue != null) {
      htmlValueSet.addElementValue(0, firstValue, getFirstDisplay(), 
          context.getHtmlNameValueSet(), idgen.getUniqueId(), 
          ApiFunctions.isEmpty(selectedValue));
    }

    context.setHtmlIdBroadValueSet(context.getHtmlIdValueSet());
    return htmlValueSet;
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
