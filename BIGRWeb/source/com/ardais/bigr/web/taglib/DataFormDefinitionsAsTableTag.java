package com.ardais.bigr.web.taglib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.ResponseUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.form.def.TagTypes;
import com.ardais.bigr.kc.web.form.def.ResultsFormDefinitionForm;
import com.ardais.bigr.library.web.column.SampleColumn;
import com.ardais.bigr.util.BigrXmlUtils;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.UniqueIdGenerator;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.def.Tag;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionElement;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinition;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement;
import com.gulfstreambio.kc.util.KcUtils;

public class DataFormDefinitionsAsTableTag extends TagSupport {

  private String _tableId = null;
  private List _dataFormDefinitions = null;
  private List _bigrColumns = null;
  private ResultsFormDefinition _resultsFormDefinition;
  private UniqueIdGenerator _idGen;
  private Map _resultsFormDataElements = new HashMap();
  
  public DataFormDefinitionsAsTableTag() {
    super();
  }

  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
    _tableId = null;
    _dataFormDefinitions = null;
    _bigrColumns = null;
    _idGen = null;
  }

  private UniqueIdGenerator getIdGen() {
    if (_idGen == null) {
      _idGen = (UniqueIdGenerator) pageContext.getRequest().getAttribute("idgen");
      if (_idGen == null) {
        _idGen = new UniqueIdGenerator();
      }
    }
    return _idGen;
  }
  
  /**
   * @return Returns the bigrColumns.
   */
  public List getBigrColumns() {
    if (_bigrColumns == null) {
      _bigrColumns = new ArrayList();
    }
    return _bigrColumns;
  }
  
  /**
   * @return Returns the dataFormDefinitions.
   */
  public List getDataFormDefinitions() {
    if (_dataFormDefinitions == null) {
      _dataFormDefinitions = new ArrayList();
    }
    return _dataFormDefinitions;
  }
  
  /**
   * @return Returns the tableId.
   */
  public String getTableId() {
    return _tableId;
  }
  
  public ResultsFormDefinition getResultsFormDefinition() {
    return _resultsFormDefinition;
  }
  
  /**
   * @param bigrColumns The bigrColumns to set.
   */
  public void setBigrColumns(List bigrColumns) {
    _bigrColumns = bigrColumns;
  }
  
  /**
   * @param dataFormDefinitions The dataFormDefinitions to set.
   */
  public void setDataFormDefinitions(List dataFormDefinitions) {
    _dataFormDefinitions = dataFormDefinitions;
  }
  
  /**
   * @param tableId The tableId to set.
   */
  public void setTableId(String tableId) {
    _tableId = tableId;
  }
  
  public void setResultsFormDefinition(ResultsFormDefinition resultsFormDefinition) {
    _resultsFormDefinition = resultsFormDefinition;
  }
  
  private void cacheResultsFormDataElements() {
    ResultsFormDefinition resultsForm = getResultsFormDefinition();
    ResultsFormDefinitionDataElement[] dataElements = (ResultsFormDefinitionDataElement[])resultsForm.getDataElements();
    for (int i=0; i< dataElements.length; i++) {
      ResultsFormDefinitionDataElement dataElement = dataElements[i];
      Tag[] tags = dataElement.getTags();
      String dataFormDefinitionId = null; 
      for (int j = 0; j < tags.length; j++) {
        Tag tag = tags[j];
        if (TagTypes.PARENT.equalsIgnoreCase(tag.getType())) {
          dataFormDefinitionId = tag.getValue();
        }
      }
      String id = dataFormDefinitionId + ResultsFormDefinitionForm.SEPERATOR + dataElement.getCui();
      _resultsFormDataElements.put(id, id);
    }
  }

  /**
   */
  public int doStartTag() throws JspException {
    cacheResultsFormDataElements();
    UniqueIdGenerator idGen = getIdGen();
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest(); 
    String contextPath = request.getContextPath(); 
    String imgSource =  contextPath + "/images/MenuClosed.gif";
    ResultsFormDefinition resultsForm = getResultsFormDefinition();
    StringBuffer buf = new StringBuffer(1024);

    // If any BIGR columns have been specified, process them first.
    List bigrColumns = getBigrColumns();
    buf.append("<ul>");
    if (!ApiFunctions.isEmpty(bigrColumns)) {
      String formId = Constants.BIGR;
      buf.append("<li");
      BigrXmlUtils.writeAttribute(buf, "etype", ResultsFormDefinitionForm.ELEMENT_TYPE_HOST);
      buf.append(">");
      buf.append("<span>");
      buf.append("<img");
      BigrXmlUtils.writeAttribute(buf, "src", imgSource);
      buf.append("/>");
      buf.append("</span>");
      buf.append("<span");
      BigrXmlUtils.writeAttribute(buf, "id", idGen.getUniqueId());
      BigrXmlUtils.writeAttribute(buf, "tooltip", "List of BIGR core data elements");
      buf.append(">");
      BigrXmlUtils.writeElementValue(buf, "BIGR Core Data Elements");
      buf.append("</span>");
      buf.append("<ul");
      BigrXmlUtils.writeAttribute(buf, "style", "display:none;");
      buf.append(">");
      Iterator bigrColumnIterator = getBigrColumns().iterator();
      while (bigrColumnIterator.hasNext()) {
        SampleColumn col = (SampleColumn) bigrColumnIterator.next();
        String key = col.getKey();
        String dataElementId = formId + ResultsFormDefinitionForm.SEPERATOR + key;
        buf.append("<li");
        BigrXmlUtils.writeAttribute(buf, "class", "leaf");
        // If the element is on the results form, then hide it since it will be displayed
        // in the selected list.
        if (resultsForm.getResultsHostElement(key) != null) {
          BigrXmlUtils.writeAttribute(buf, "style", "display:none;");          
        }
        buf.append(">");
        buf.append("<span");
        BigrXmlUtils.writeAttribute(buf, "id", dataElementId);
        BigrXmlUtils.writeAttribute(buf, "tooltip", col.getHeaderTooltip());
        buf.append(">");
        BigrXmlUtils.writeElementValue(buf, col.getRawHeaderText());
        buf.append("</span>");
        buf.append("</li>");
      }
      buf.append("</ul></li>");
    }

    // Now, process each data form definition.
    Iterator dataFormIterator = getDataFormDefinitions().iterator();
    while (dataFormIterator.hasNext()) {
      BigrFormDefinition dataForm = (BigrFormDefinition)dataFormIterator.next();
      //if the form has no kc data elements, skip it.
      DataFormDefinitionDataElement[] dataElements = ((DataFormDefinition)dataForm.getKcFormDefinition()).getDataDataElements();
      if (dataElements == null || dataElements.length <= 0) {
        continue;
      }
      String formName = dataForm.getName();
      buf.append("<li");
      BigrXmlUtils.writeAttribute(buf, "etype", ResultsFormDefinitionForm.ELEMENT_TYPE_DATA);
      BigrXmlUtils.writeAttribute(buf, "otype", dataForm.getObjectType());
      buf.append(">");
      buf.append("<span>");
      buf.append("<img");
      BigrXmlUtils.writeAttribute(buf, "src", imgSource);
      buf.append("/>");
      buf.append("</span>");
      buf.append("<span");
      BigrXmlUtils.writeAttribute(buf, "id", idGen.getUniqueId());
      BigrXmlUtils.writeAttribute(buf, "tooltip", "The " + formName + " data form");
      buf.append(">");
      BigrXmlUtils.writeElementValue(buf, formName);
      buf.append(" (");
      BigrXmlUtils.writeElementValue(buf, dataForm.getObjectType());
      buf.append(")");
      buf.append("</span>");
      buf.append("<ul");
      BigrXmlUtils.writeAttribute(buf, "style", "display:none;");
      buf.append(">");
      renderFormElements(buf, dataForm.getFormDefinitionId(), 
          ((DataFormDefinition)dataForm.getKcFormDefinition()).getDataFormElements().getDataFormElements());
      buf.append("</ul>");
    }
    buf.append("</ul>");
    ResponseUtils.write(pageContext, buf.toString());    
    return SKIP_BODY;
  }
  
  private void renderFormElements(StringBuffer buf, String formId, DataFormDefinitionElement[] elements) {
    UniqueIdGenerator idGen = getIdGen();
    DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest(); 
    String contextPath = request.getContextPath(); 
    String imgSource =  contextPath + "/images/MenuClosed.gif";
    ResultsFormDefinition resultsForm = getResultsFormDefinition();
    
    for (int i = 0; i < elements.length; i++) {
      DataFormDefinitionElement element = elements[i];
      if (element.isCategory()) {
        DataFormDefinitionCategory category = element.getDataCategory();
        String displayName = category.getDisplayName();
        buf.append("<li>");
        buf.append("<span>");
        buf.append("<img");
        BigrXmlUtils.writeAttribute(buf, "src", imgSource);
        buf.append("/>");
        buf.append("</span>");
        buf.append("<span");
        BigrXmlUtils.writeAttribute(buf, "id", idGen.getUniqueId());
        BigrXmlUtils.writeAttribute(buf, "tooltip", "The group of " + displayName + " data elements");
        buf.append(">");
        BigrXmlUtils.writeElementValue(buf, displayName);
        buf.append("</span>");
        buf.append("<ul");
        BigrXmlUtils.writeAttribute(buf, "style", "display:none;");
        buf.append(">");
        
        // Process children.
        renderFormElements(buf, formId, category.getDataFormElements());
        buf.append("</ul>");
      }
      else if (element.isDataElement()) {
        DataFormDefinitionDataElement definition = element.getDataDataElement();
        String cui = definition.getCui();
        String dataElementId = formId + ResultsFormDefinitionForm.SEPERATOR + cui;
        String displayName = KcUtils.getDescription(definition);
        buf.append("<li");
        BigrXmlUtils.writeAttribute(buf, "class", "leaf");
        // If the element is on the results form, then hide it since it will be displayed
        // in the selected list.
        if (_resultsFormDataElements.get(dataElementId) != null) {
          BigrXmlUtils.writeAttribute(buf, "style", "display:none;");                      
        }
        buf.append(">");
        buf.append("<span");
        BigrXmlUtils.writeAttribute(buf, "id", dataElementId);
        buf.append(">");
        BigrXmlUtils.writeElementValue(buf, displayName);
        buf.append("</span>");
        buf.append("</li>");
      }
    }
  }

}
