package com.gulfstreambio.kc.web.taglib;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts.util.ResponseUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.util.UniqueIdGenerator;
import com.ardais.bigr.web.StrutsProperties;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.det.DetValueSetValue;
import com.gulfstreambio.kc.web.support.DataFormElementContext;
import com.gulfstreambio.kc.web.support.HtmlElementValueSet;
import com.gulfstreambio.kc.web.support.HtmlElementValueSetHierarchyMulti;
import com.gulfstreambio.kc.web.support.HtmlElementValueSetHierarchySingle;
import com.gulfstreambio.kc.web.support.HtmlElementValueSetSingle;
import com.gulfstreambio.kc.web.support.HtmlElementValueSetSingleNarrow;
import com.gulfstreambio.kc.web.support.HtmlElementValueSetSingleStandard;
import com.gulfstreambio.kc.web.support.KcUiContext;
import com.gulfstreambio.kc.web.support.WebUtils;

public class ValueSetTag extends KcBaseTag {

  private String _firstValue;
  private String _firstDisplay;
  
  public ValueSetTag() {
    super();
  }

  public void release() {
    super.release();
    _firstValue = null;
    _firstDisplay = null;
  }

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

  private DataFormElementContext getElementContext() {
    return getFormContext().getDataFormElementContext();
  }

  public int doEndTag() throws JspException {
    DataFormElementContext context = getElementContext();
    boolean isSystemStandardValue = context.isValueStandardValue();
    StringBuffer buf = new StringBuffer(2048);
    
    // Create the three value sets.  Note that createValueSet will return null if the value set 
    // passed in is null.  In addition, add all of the broad value set concepts to the JavaScript
    // ARTS object so we can get their descriptions if necessary.
    DetValueSet broad = context.getValueSetBroad();
    HtmlElementValueSet broadValueSet = createValueSet(broad, "b");
    HtmlElementValueSet standardValueSet = createValueSet(context.getValueSetStandard(), "s");
    HtmlElementValueSet narrowValueSet = createValueSet(context.getValueSetNarrow(), "n");        
    addConcepts(broad.getValues(), WebUtils.getJavaScriptBuffer(pageContext));

    // If the narrow value set should be displayed, then generate the HTML for all 3 value sets,
    // only making the narrow value set visible.
    if (((narrowValueSet != null) && isSystemStandardValue)
        || context.isAllValuesInNarrowValueSet()) {
      generateNarrowValueSet(buf, narrowValueSet, true);
      generateStandardValueSet(buf, standardValueSet, false);
      generateBroadValueSet(buf, broadValueSet, false);
      
      // Since the narrow value set is being shown, the HTML id of the primary control is the
      // HTML id of the narrow value set.
      context.setHtmlIdPrimary(context.getHtmlIdNarrowValueSet());
    }

    // If the standard value set should be displayed, then generate the HTML for the standard and
    // broad 3 value sets, only making the standard value set visible.
    else if (((standardValueSet != null) && isSystemStandardValue)
        || context.isAllValuesInStandardValueSet()) {
      generateStandardValueSet(buf, standardValueSet, true);
      generateBroadValueSet(buf, broadValueSet, false);

      // Since the standard value set is being shown, the HTML id of the primary control is the
      // HTML id of the standard value set.  Also make sure the narrow value set HTML ids are not
      // set, since they may have been set previously when creating the value sets.
      context.setHtmlIdPrimary(context.getHtmlIdStandardValueSet());
      context.setHtmlIdNarrowValueSet(null);    
      context.setHtmlIdNarrowValueSetContainer(null);    
    }

    // If the broad value set should be displayed, then generate the HTML for the broad value
    // value set, making it visible.
    else {
      generateBroadValueSet(buf, broadValueSet, true);

      // Since the broad value set is being shown, the HTML id of the primary control is the
      // HTML id of the broad value set.  Also make sure the narrow and standard value set HTML 
      // ids are not set, since they may have been set previously when creating the value sets.
      context.setHtmlIdPrimary(context.getHtmlIdBroadValueSet());
      context.setHtmlIdNarrowValueSet(null);    
      context.setHtmlIdNarrowValueSetContainer(null);    
      context.setHtmlIdStandardValueSet(null);    
      context.setHtmlIdStandardValueSetContainer(null);    
    }

    ResponseUtils.write(pageContext, buf.toString());

    return EVAL_PAGE;
  }

  private HtmlElementValueSet createValueSet(DetValueSet valueSet, String type) {
    if (valueSet == null) {
      return null;
    }
    ServletRequest request = pageContext.getRequest();
    DataFormElementContext context = getElementContext();
    String selectedValue = context.getValue();
    String[] selectedValues = context.getValues();
    String[] excludedValues = context.getExcludedValues();
    HtmlElementValueSet htmlValueSet = null;

    if (context.isMultivalued() && !context.isTreatAsSinglevalued()) {
      htmlValueSet = new HtmlElementValueSetHierarchyMulti(request, context, valueSet, 
          selectedValues, false);
    }
    else {
      if (context.isMlvs()) {
        htmlValueSet = new HtmlElementValueSetHierarchySingle(request, context, valueSet, 
            selectedValue, excludedValues, true);
      }
      else {
        if (type.equals("b")) {
          htmlValueSet = new HtmlElementValueSetSingle(request, context, valueSet, 
              selectedValue, excludedValues, true);
        }
        else if (type.equals("s")) {
          htmlValueSet = new HtmlElementValueSetSingleStandard(request, context, valueSet, 
              selectedValue, excludedValues, true);
        }
        else if (type.equals("n")) {
          htmlValueSet = new HtmlElementValueSetSingleNarrow(request, context, valueSet, 
              selectedValue, excludedValues, true);
        }
        UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
        String firstValue = getFirstValue();
        if (firstValue != null) {
          htmlValueSet.addElementValue(0, firstValue, getFirstDisplay(), 
              context.getHtmlNameValueSet(), idgen.getUniqueId(), 
              ApiFunctions.isEmpty(selectedValue));
        }
      }
    }

    if (type.equals("b")) {
      context.setHtmlIdBroadValueSet(context.getHtmlIdValueSet());
    }
    else if (type.equals("s")) {
      context.setHtmlIdStandardValueSet(context.getHtmlIdValueSet());    
    }
    else if (type.equals("n")) {
      context.setHtmlIdNarrowValueSet(context.getHtmlIdValueSet());    
    }
    return htmlValueSet;
  }
  
  private void generateNarrowValueSet(StringBuffer buf,
                                      HtmlElementValueSet valueSet,
                                      boolean makeVisible) {
    DataFormElementContext context = getElementContext();
    boolean isMultivalued = context.isMultivalued();
    boolean isMlvs = context.isMlvs();

    UniqueIdGenerator idgen = KcUiContext.getKcUiContext(pageContext.getRequest()).getUniqueIdGenerator();    
    String containerId = idgen.getUniqueId();
    context.setHtmlIdNarrowValueSetContainer(containerId);
    
    buf.append("<div");
    WebUtils.writeHtmlAttribute(buf, "id", containerId);
    if (!makeVisible) {
      WebUtils.writeHtmlAttribute(buf, "style", "display:none;");      
    }
    if ((isMultivalued && !context.isTreatAsSinglevalued()) || isMlvs) {
      WebUtils.writeHtmlAttribute(buf, "class", "kcCvList");
    }
    buf.append(">");
    valueSet.toHtml(getContextPath(), buf);
    if ((isMultivalued && !context.isTreatAsSinglevalued()) || isMlvs) {
      generateBroadAndStandardLink(buf);
    }
    buf.append("</div>");    
  }

  private void generateStandardValueSet(StringBuffer buf,
                                        HtmlElementValueSet valueSet,
                                        boolean makeVisible) {
    DataFormElementContext context = getElementContext();
    boolean isMultivalued = context.isMultivalued();
    boolean isMlvs = context.isMlvs();

    UniqueIdGenerator idgen = KcUiContext.getKcUiContext(pageContext.getRequest()).getUniqueIdGenerator();    
    String containerId = idgen.getUniqueId();
    context.setHtmlIdStandardValueSetContainer(containerId);
    
    buf.append("<div");
    WebUtils.writeHtmlAttribute(buf, "id", containerId);
    if (!makeVisible) {
      WebUtils.writeHtmlAttribute(buf, "style", "display:none;");      
    }
    if ((isMultivalued && !context.isTreatAsSinglevalued()) || isMlvs) {
      WebUtils.writeHtmlAttribute(buf, "class", "kcCvList");
    }
    buf.append(">");
    valueSet.toHtml(getContextPath(), buf);
    if ((isMultivalued && !context.isTreatAsSinglevalued()) || isMlvs) {
      generateBroadLink(buf);
    }
    buf.append("</div>");    
  }

  private void generateBroadValueSet(StringBuffer buf,
                                     HtmlElementValueSet valueSet,
                                     boolean makeVisible) {
    DataFormElementContext context = getElementContext();
    boolean isMultivalued = context.isMultivalued();
    boolean isMlvs = context.isMlvs();

    UniqueIdGenerator idgen = KcUiContext.getKcUiContext(pageContext.getRequest()).getUniqueIdGenerator();    
    String containerId = idgen.getUniqueId();
    context.setHtmlIdBroadValueSetContainer(containerId);

    buf.append("<div");
    WebUtils.writeHtmlAttribute(buf, "id", containerId);
    if (!makeVisible) {
      WebUtils.writeHtmlAttribute(buf, "style", "display:none;");      
    }
    if ((isMultivalued && !context.isTreatAsSinglevalued()) || isMlvs) {
      WebUtils.writeHtmlAttribute(buf, "class", "kcCvList");
    }
    buf.append(">");
    valueSet.toHtml(getContextPath(), buf);
    buf.append("</div>");    
  }

  private void generateBroadLink(StringBuffer buf) {
    DataFormElementContext context = getElementContext();
    UniqueIdGenerator idgen = KcUiContext.getKcUiContext(pageContext.getRequest()).getUniqueIdGenerator();    
    String linkId = idgen.getUniqueId();
    context.addHtmlIdBroadLink(linkId);

    buf.append("<table");
    WebUtils.writeHtmlAttribute(buf, "cellspacing", "0");
    WebUtils.writeHtmlAttribute(buf, "cellpadding", "0");
    WebUtils.writeHtmlAttribute(buf, "border", "0");
    WebUtils.writeHtmlAttribute(buf, "width", "100%");
    buf.append('>');
    buf.append("<tr><td><span");
    WebUtils.writeHtmlAttribute(buf, "id", linkId);
    WebUtils.writeHtmlAttribute(buf, "class", "kcFakeLink");
    buf.append(">");
    buf.append(StrutsProperties.getInstance().getProperty("ddc.valueset.broad"));
    buf.append("</span></td></tr>");    
    buf.append("</table>");
  }

  private void generateBroadAndStandardLink(StringBuffer buf) {
    DataFormElementContext context = getElementContext();
    UniqueIdGenerator idgen = KcUiContext.getKcUiContext(pageContext.getRequest()).getUniqueIdGenerator();    
    String standardLinkId = idgen.getUniqueId();
    String broadLinkId = idgen.getUniqueId();
    context.setHtmlIdStandardLink(standardLinkId);
    context.addHtmlIdBroadLink(broadLinkId);

    buf.append("<table");
    WebUtils.writeHtmlAttribute(buf, "cellspacing", "0");
    WebUtils.writeHtmlAttribute(buf, "cellpadding", "0");
    WebUtils.writeHtmlAttribute(buf, "border", "0");
    WebUtils.writeHtmlAttribute(buf, "width", "100%");
    buf.append('>');
    buf.append("<tr><td><span");
    WebUtils.writeHtmlAttribute(buf, "id", standardLinkId);
    WebUtils.writeHtmlAttribute(buf, "class", "kcFakeLink");
    buf.append(">");
    buf.append(StrutsProperties.getInstance().getProperty("ddc.valueset.standard"));
    buf.append("</span></td></tr>");

    buf.append("<tr><td><span");
    WebUtils.writeHtmlAttribute(buf, "id", broadLinkId);
    WebUtils.writeHtmlAttribute(buf, "class", "kcFakeLink");
    buf.append(">");
    buf.append(StrutsProperties.getInstance().getProperty("ddc.valueset.broad"));
    buf.append("</span></td></tr>");

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
