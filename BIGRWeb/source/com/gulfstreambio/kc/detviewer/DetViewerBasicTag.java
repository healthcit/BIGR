package com.gulfstreambio.kc.detviewer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.gulfstreambio.kc.det.DatabaseElement;
import com.gulfstreambio.kc.det.DetAde;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetCategory;
import com.gulfstreambio.kc.det.DetConcept;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetElement;
import com.gulfstreambio.kc.det.DetUnit;
import com.gulfstreambio.kc.det.DetValue;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.web.support.WebUtils;

public class DetViewerBasicTag extends TagSupport {

  private DetViewerQueryResults _results;
  
  public DetViewerBasicTag() {
    super();
  }

  public void setResults(DetViewerQueryResults results) {
    _results = results;
  }
  
  public int doStartTag() throws JspException {
    
    // Render the basic metdata.
    DatabaseElement databaseElement = null;
    StringBuffer buf = new StringBuffer(1024);
    buf.append("<h1>Basic Metadata</h1>");
    buf.append("<table");
    WebUtils.writeHtmlAttribute(buf, "cellspacing", "0");
    buf.append(">");

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
      renderDataElement(buf, dataElement);
      databaseElement = dataElement.getDatabaseElement();
    }
    else if (ade != null) {
      renderAde(buf, ade);
    }
    else if (adeElement != null) {
      renderAdeElement(buf, adeElement);
      databaseElement = adeElement.getDatabaseElement();
    }
    else if (category != null) {
      renderCategory(buf, category);
    }
    else if (unit != null) {
      renderUnit(buf, unit);
    }
    else if (valueSet != null) {
      renderValueSet(buf, valueSet);
    }
    else if (value != null) {
      renderValue(buf, value);
    }
    else {
      throw new JspException("In detViewerBasic tag: KC concept not specified.");
    }
    buf.append("</table>");
    
    // For data elements and ADE elements, render the database metdata.
    if (databaseElement != null) {
      buf.append("<h1>Database Metadata</h1>");
      buf.append("<table");
      WebUtils.writeHtmlAttribute(buf, "cellspacing", "0");
      buf.append(">");
      renderElementDatabase(buf, databaseElement);
      buf.append("</table>");
    }

    WebUtils.tagWrite(pageContext, buf.toString());
    
    return EVAL_BODY_INCLUDE;
  }

  private void renderDataElement(StringBuffer buf, DetDataElement dataElement) {
    writeBasicRow(buf, "concept type", "Data Element");
    renderConcept(buf, dataElement);
    renderElement(buf, dataElement);
    writeBasicRow(buf, "has ADE?", dataElement.isHasAde() ? "yes" : "no");
  }
  
  private void renderAde(StringBuffer buf, DetAde ade) {
    writeBasicRow(buf, "concept type", "Ade");
    renderConcept(buf, ade);
    writeBasicRow(buf, "system name", ade.getSystemName());
  }
  
  private void renderAdeElement(StringBuffer buf, DetAdeElement adeElement) {
    writeBasicRow(buf, "concept type", "Ade Element");
    renderConcept(buf, adeElement);
    renderElement(buf, adeElement);
  }
  
  private void renderCategory(StringBuffer buf, DetCategory category) {
    writeBasicRow(buf, "concept type", "Category");
    renderConcept(buf, category);
    writeBasicRow(buf, "system name", category.getSystemName());
  }
  
  private void renderUnit(StringBuffer buf, DetUnit unit) {
    writeBasicRow(buf, "concept type", "Unit");
    renderConcept(buf, unit);
  }
  
  private void renderValueSet(StringBuffer buf, DetValueSet valueSet) {
    writeBasicRow(buf, "concept type", "Value Set");
    renderConcept(buf, valueSet);
    writeBasicRow(buf, "has other value?", valueSet.getOtherValue() != null ? "yes" : "no");
    writeBasicRow(buf, "has no value?", valueSet.getNoValue() != null ? "yes" : "no");
  }
  
  private void renderValue(StringBuffer buf, DetValue value) {
    writeBasicRow(buf, "concept type", "Value");
    renderConcept(buf, value);
    writeBasicRow(buf, "is other value?", value.isOtherValue() ? "yes" : "no");
    writeBasicRow(buf, "is no value?", value.isNoValue() ? "yes" : "no");
  }

  private void renderConcept(StringBuffer buf, DetConcept concept) {
    writeBasicRow(buf, "description", concept.getDescription());
    writeBasicRow(buf, "CUI", concept.getCui());
  }
  
  private void renderElement(StringBuffer buf, DetElement element) {
    writeBasicRow(buf, "system name", element.getSystemName());
    writeBasicRow(buf, "datatype", element.getDatatype());
    writeBasicRow(buf, "multivalued?", element.isMultivalued() ? "yes" : "no");
    writeBasicRow(buf, "has unit?", element.isHasUnit() ? "yes" : "no");
    if (element.isHasUnit()) {
      writeBasicRowValueAsLink(buf, "unit", element.getUnitDescription(), element.getUnitCui());
      writeBasicRow(buf, "unit CUI", element.getUnitCui());
    }
    if (element.isDatatypeCv()) {
      DetValueSet broad = element.getBroadValueSet();
      writeBasicRowValueAsLink(buf, "broad value set", broad.getDescription(), broad.getCui());
      writeBasicRow(buf, "broad value set CUI", broad.getCui());
      writeBasicRow(buf, "has other value?", element.isHasOtherValue() ? "yes" : element.isDatatypeCv() ? "no" : "n/a");
      if (element.isHasOtherValue()) {
        writeBasicRowValueAsLink(buf, "other value", element.getOtherValueDescription(), element.getOtherValueCui());
        writeBasicRow(buf, "other value CUI", element.getOtherValueCui());
      }
      writeBasicRow(buf, "has no value?", element.isHasNoValue() ? "yes" : element.isDatatypeCv() ? "no" : "n/a");
      if (element.isHasNoValue()) {
        writeBasicRowValueAsLink(buf, "no value", element.getNoValueDescription(), element.getNoValueCui());
        writeBasicRow(buf, "no value CUI", element.getNoValueCui());
      }
      writeBasicRow(buf, "multi-level value set?", element.isMultilevelValueSet() ? "yes" : element.isDatatypeCv() ? "no" : "n/a");
    }
    if (!(element.isDatatypeCv() || element.isDatatypeReport())) {
      writeBasicRow(buf, "min value", ApiFunctions.isEmpty(element.getMinValue()) ? "none" : element.getMinValue() + (element.isMinInclusive() ? " (inclusive)" : " (exclusive)"));
      writeBasicRow(buf, "max value", ApiFunctions.isEmpty(element.getMaxValue()) ? "none" : element.getMaxValue() + (element.isMaxInclusive() ? " (inclusive)" : " (exclusive)"));
    }
  }
  
  private void renderElementDatabase(StringBuffer buf, DatabaseElement databaseElement) {
    writeBasicRow(buf, "table name", databaseElement.getTableName());
    writeBasicRow(buf, "column name", databaseElement.getColumnDataValue());
    String otherColumn = databaseElement.getColumnDataValueOther();
    if (!ApiFunctions.isEmpty(otherColumn)) {
      writeBasicRow(buf, "other column name", otherColumn);      
    }
    String dpcColumn = databaseElement.getColumnDataValueDpc();
    if (!ApiFunctions.isEmpty(dpcColumn)) {
      writeBasicRow(buf, "DPC column name", dpcColumn);
    }
    String standardColumn = databaseElement.getColumnDataValueSystemStandard();
    if (!ApiFunctions.isEmpty(standardColumn)) {
      writeBasicRow(buf, "standard column name", standardColumn);      
    }
    writeBasicRow(buf, "EAV/conventional?", databaseElement.isTableEav() ? "EAV" : "conventional");
  }
  
  private void writeBasicRow(StringBuffer buf, String metadata, String value) {
    buf.append("<tr><td");
    WebUtils.writeHtmlAttribute(buf, "class", "metadataConcept");
    buf.append(">");
    buf.append(Escaper.htmlEscape(metadata));
    buf.append("</td>");
    buf.append("<td>");
    buf.append(Escaper.htmlEscape(value));
    buf.append("</td></tr>");    
  }
  
  private void writeBasicRowValueAsLink(StringBuffer buf, String metadata, String value, String cui) {
    buf.append("<tr><td");
    WebUtils.writeHtmlAttribute(buf, "class", "metadataConcept");
    buf.append(">");
    buf.append(Escaper.htmlEscape(metadata));
    buf.append("</td>");
    buf.append("<td><a");
    WebUtils.writeHtmlAttribute(buf, "href", "#");
    WebUtils.writeHtmlAttribute(buf, "id", cui);
    buf.append(">");
    buf.append(Escaper.htmlEscape(value));
    buf.append("</a></td></tr>");    
  }

  public void release() {
    super.release();
    _results = null;
  }
}
