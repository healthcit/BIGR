package com.gulfstreambio.kc.detviewer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.gulfstreambio.kc.det.DetAde;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetCategory;
import com.gulfstreambio.kc.det.DetConcept;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetUnit;
import com.gulfstreambio.kc.det.DetValue;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.det.DetValueSetValue;
import com.gulfstreambio.kc.web.support.WebUtils;

public class DetViewerAssociatedTag extends TagSupport {

  private DetViewerQueryResults _results;
  
  public DetViewerAssociatedTag() {
    super();
  }

  public void setResults(DetViewerQueryResults results) {
    _results = results;
  }

  public int doStartTag() throws JspException {
    StringBuffer buf = new StringBuffer(1024);
    buf.append("<h1>Associated Concepts</h1>");

    DetDataElement dataElement = 
      _results.getDataElements().length > 0 ? _results.getDataElements()[0] : null;
    DetAde ade = _results.getAdes().length > 0 ? _results.getAdes()[0] : null;
    DetAdeElement adeElement = 
      _results.getAdeElements().length > 0 ? _results.getAdeElements()[0] : null;
    DetCategory category = _results.getCategories().length > 0 ? _results.getCategories()[0] : null;
    DetUnit unit = _results.getUnits().length > 0 ? _results.getUnits()[0] : null;
    DetValueSet valueSet = _results.getValueSets().length > 0 ? _results.getValueSets()[0] : null;
    DetValue value = _results.getValues().length > 0 ? _results.getValues()[0] : null;
    
    if (dataElement != null) {
      renderAdeWithAdeElements(buf, dataElement.getAde());
      renderValueSet(buf, dataElement.getBroadValueSet(), "Broad Value Set");
      renderValueSet(buf, dataElement.getNonAdeTriggerValueSet(), "Non ADE Trigger Value Set");
    }
    else if (ade != null) {
      renderAdeElements(buf, ade.getAdeElements(), true);
      renderDataElements(buf, _results.getAssociatedDataElements(), "Data Elements With This ADE");
    }
    else if (adeElement != null) {
      renderAde(buf, adeElement.getAde());
      renderValueSet(buf, adeElement.getBroadValueSet(), "Broad Value Set");
    }
    else if (category != null) {
      renderCategories(buf, category.getCategories(), "Sub-Categories");
      renderDataElements(buf, category.getDataElements(), "Data Elements");
    }
    else if (unit != null) {
      renderDataElements(buf, _results.getAssociatedDataElements(), "Data Elements With This Unit");
      renderAdeElements(buf, _results.getAssociatedAdeElements(), "ADE Elements With This Unit");
    }
    else if (valueSet != null) {
      renderValueSet(buf, valueSet, "Broad Value Set");
      renderDataElements(buf, _results.getAssociatedDataElements(), "Data Elements With This Broad Value Set");
      renderAdeElements(buf, _results.getAssociatedAdeElements(), "ADE Elements With This Broad Value Set");
    }
    else if (value != null) {
      renderValueSets(buf, _results.getAssociatedValueSets(), "Broad Value Sets With This Value");
      renderDataElements(buf, _results.getAssociatedDataElements(), "Data Elements With This Value In Broad Value Set");
      renderAdeElements(buf, _results.getAssociatedAdeElements(), "ADE Elements With This Value In Broad Value Set");
    }
    else {
      throw new JspException("In detViewerBasic tag: KC concept not specified.");
    }
    
    WebUtils.tagWrite(pageContext, buf.toString());
    
    return EVAL_BODY_INCLUDE;
  }    
  
  private void renderAde(StringBuffer buf, DetAde ade) {
    if (ade != null) {
      buf.append("<h2>ADE</h2><ul><li>");
      writeDescriptionAsLink(buf, ade);
      buf.append("</li></ul>");      
    }
  }  

  private void renderAdeElements(StringBuffer buf, DetAdeElement[] adeElements, boolean header) {
    if (adeElements != null) {
      int count = adeElements.length;
      if (header) {
        buf.append("<h2>ADE Elements (");
        buf.append(count);
        buf.append(")</h2>");
      }
      buf.append("<ul>");
      for (int i = 0; i < count; i++) {
        DetAdeElement adeElement = adeElements[i];
        buf.append("<li>");
        writeDescriptionAsLink(buf, adeElement);
        buf.append("</li>");
      }
      buf.append("</ul>");
    }
  }  

  private void renderAdeWithAdeElements(StringBuffer buf, DetAde ade) {
    if (ade != null) {
      buf.append("<h2>ADE</h2><ul><li>");
      writeDescriptionAsLink(buf, ade);
      renderAdeElements(buf, ade.getAdeElements(), false);
      buf.append("</li></ul>");          
    }
  }

  private void renderDataElements(StringBuffer buf, DetDataElement[] dataElements, String heading) {
    if (dataElements != null) {
      int count = dataElements.length;
      if (count > 0) {
        buf.append("<h2>");
        buf.append(heading);
        buf.append(" (");
        buf.append(count);
        buf.append(")</h2><ul>");
        for (int i = 0; i < count; i++) {
          DetDataElement dataElement = dataElements[i];
          buf.append("<li>");
          writeDescriptionAsLink(buf, dataElement);
          buf.append("</li>");
        }
        buf.append("</ul>");
      }
    }
  }
  
  private void renderAdeElements(StringBuffer buf, DetAdeElement[] adeElements, String heading) {
    if (adeElements != null) {
      int count = adeElements.length;
      if (count > 0) {
        buf.append("<h2>");
        buf.append(heading);
        buf.append(" (");
        buf.append(count);
        buf.append(")</h2><ul>");
        for (int i = 0; i < count; i++) {
          DetAdeElement adeElement = adeElements[i];
          buf.append("<li>");
          writeDescriptionAsLink(buf, adeElement);
          buf.append("</li>");
        }
        buf.append("</ul>");
      }
    }
  }

  private void renderValueSets(StringBuffer buf, DetValueSet[] valueSets, String heading) {
    if (valueSets != null) {
      int count = valueSets.length;
      if (count > 0) {
        buf.append("<h2>");
        buf.append(heading);
        buf.append(" (");
        buf.append(count);
        buf.append(")</h2><ul>");
        for (int i = 0; i < count; i++) {
          DetValueSet valueSet = valueSets[i];
          buf.append("<li>");
          writeDescriptionAsLink(buf, valueSet);
          buf.append("</li>");
        }
        buf.append("</ul>");
      }
    }
  }

  private void renderCategories(StringBuffer buf, DetCategory[] categories, String heading) {
    if (categories != null) {
      int count = categories.length;
      if (count > 0) {
        buf.append("<h2>");
        buf.append(heading);
        buf.append(" (");
        buf.append(count);
        buf.append(")</h2><ul>");
        for (int i = 0; i < count; i++) {
          DetCategory category = categories[i];
          buf.append("<li>");
          writeDescriptionAsLink(buf, category);
          buf.append("</li>");
        }
        buf.append("</ul>");
      }
    }
  }

  private void renderValueSet(StringBuffer buf, DetValueSet valueSet, String heading) {
    int totalCount = 0;
    if (valueSet != null) {
      DetValueSetValue[] values = valueSet.getValues();
      int count = values.length;
      if (count > 0) {
        StringBuffer buf1 = new StringBuffer(1024);
        totalCount += renderValueSet(buf1, values);
        buf.append("<h2 class=\"valueSet\">");
        buf.append(heading);
        buf.append(" (");
        buf.append(totalCount);
        buf.append(")</h2>");
        buf.append(buf1);
      }      
    }
  }
  
  private int renderValueSet(StringBuffer buf, DetValueSetValue[] values) {
    int count = values.length;
    int totalCount = count;
    if (count > 0) {
      buf.append("<ul>");
      for (int i = 0; i < count; i++) {
        DetValueSetValue value = values[i];
        buf.append("<li>");
        writeDescriptionAsLink(buf, value);
        if (value.isNoValue()) {
          buf.append("<span");
          WebUtils.writeHtmlAttribute(buf, "class", "metadataConcept");
          buf.append(">&nbsp;[no value]</span>");
        }
        if (value.isOtherValue()) {
          buf.append("<span");
          WebUtils.writeHtmlAttribute(buf, "class", "metadataConcept");
          buf.append(">&nbsp;[other value]</span>");
        }
        totalCount += renderValueSet(buf, value.getValues());
        buf.append("</li>");
      }
      buf.append("</ul>");
    }
    return totalCount;
  }
  
  private void writeDescriptionAsLink(StringBuffer buf, DetConcept concept) {
    buf.append("<a");
    WebUtils.writeHtmlAttribute(buf, "href", "#");
    WebUtils.writeHtmlAttribute(buf, "id", concept.getCui());
    buf.append(">");
    buf.append(concept.getDescription());    
    buf.append("</a>");
  }
  
  public void release() {
    super.release();
    _results = null;
  }
}
