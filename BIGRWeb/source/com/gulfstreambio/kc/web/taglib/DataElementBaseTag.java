package com.gulfstreambio.kc.web.taglib;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.UniqueIdGenerator;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetValueSet;
import com.gulfstreambio.kc.det.DetValueSetValue;
import com.gulfstreambio.kc.form.DataElement;
import com.gulfstreambio.kc.form.DataElementValue;
import com.gulfstreambio.kc.web.support.DataFormDataElementContext;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.KcUiContext;
import com.gulfstreambio.kc.web.support.WebUtils;

public abstract class DataElementBaseTag extends KcBaseTag {

  public DataElementBaseTag() {
    super();
  }

  protected DataFormDataElementContext getDataElementContext() {
    return getFormContext().getDataFormDataElementContext();
  }  
  
  protected void createContainerStart(StringBuffer buf) { 
    // First create the container id and add it to the context.
    HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
    UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
    String containerId = idgen.getUniqueId();
    getDataElementContext().setHtmlIdContainer(containerId);    

    // Create the container DIV start tag, adding it to the StringBuffer.
    buf.append("<div");
    WebUtils.writeHtmlAttribute(buf, "id", containerId);
    WebUtils.writeHtmlAttribute(buf, "class", "kcElement");
    buf.append('>');
  }
  
  protected void createContainerEnd(StringBuffer buf) { 
    buf.append("</div>");
  }

  protected void createJavascriptObject(StringBuffer buf) { 
    // First create the data element id and add it to the context.
    HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
    UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
    String jsDataElementObjId = idgen.getUniqueId();
    DataFormDataElementContext context = getDataElementContext();
    context.setJavascriptObjectId(jsDataElementObjId);    

    // Create the Javascript data element, adding it to the form instance corresponding to the
    // current form context. 
    FormContext formContext = getFormContext();
    String jsFormObjId = formContext.getJavascriptObjectId();
    buf.append("GSBIO.kc.data.FormInstances.getFormInstance(");
    if (jsFormObjId != null) {
      buf.append("'");
      buf.append(jsFormObjId);
      buf.append("'");
    }
    buf.append(").addDataElement(");
    createJavascriptDataElement(buf, request, context, jsDataElementObjId);
    buf.append(");");
  }

  protected void setJavascriptHtmlIds(StringBuffer buf) { 
    // Set the HTML ids of all of the input controls used for this data element on the 
    // JavaScript data element.  The HTML ids were set into the element context as they were  
    // created in the various JSPs and tags that are used to render the input controls for the 
    // data element.
    FormContext formContext = getFormContext();
    DataFormDataElementContext context = getDataElementContext();
    String jsFormObjId = formContext.getJavascriptObjectId();
    String jsDataElementObjId = context.getJavascriptObjectId();
    buf.append("GSBIO.kc.data.FormInstances.getFormInstance(");
    if (jsFormObjId != null) {
      buf.append("'");
      buf.append(jsFormObjId);
      buf.append("'");
    }
    buf.append(").getDataElement('");
    buf.append(jsDataElementObjId);
    buf.append("').setHtmlIds(");      
    try {
      JSONObject htmlIds = new JSONObject();
      htmlIds.putOpt("container", context.getHtmlIdContainer());
      htmlIds.putOpt("primary", context.getHtmlIdPrimary());
      htmlIds.putOpt("std", context.getHtmlIdStandard());
      htmlIds.putOpt("otherContainer", context.getHtmlIdOtherContainer());
      htmlIds.putOpt("other", context.getHtmlIdOther());
      htmlIds.putOpt("otherText", context.getHtmlIdOtherText());
      htmlIds.putOpt("otherAdd", context.getHtmlIdOtherAdd());
      htmlIds.putOpt("otherRemove", context.getHtmlIdOtherRemove());
      htmlIds.putOpt("noteContainer", context.getHtmlIdNoteContainer());
      htmlIds.putOpt("note", context.getHtmlIdNote());
      htmlIds.putOpt("noteButton", context.getHtmlIdNoteButton());
      htmlIds.putOpt("adeContainer", context.getHtmlIdAdeContainer());
      htmlIds.putOpt("adeTable", context.getHtmlIdAdeTable());
      htmlIds.putOpt("adeLink", context.getHtmlIdAdeLink());
      htmlIds.putOpt("narrowValueSetContainer", context.getHtmlIdNarrowValueSetContainer());
      htmlIds.putOpt("narrowValueSet", context.getHtmlIdNarrowValueSet());
      htmlIds.putOpt("standardValueSetContainer", context.getHtmlIdStandardValueSetContainer());
      htmlIds.putOpt("standardValueSet", context.getHtmlIdStandardValueSet());
      htmlIds.putOpt("standardLink", context.getHtmlIdStandardLink());
      htmlIds.putOpt("broadValueSetContainer", context.getHtmlIdBroadValueSetContainer());
      htmlIds.putOpt("broadValueSet", context.getHtmlIdBroadValueSet());
      String[] links = context.getHtmlIdBroadLinks();
      if (!ApiFunctions.isAllEmpty(links)) {
        JSONArray jsonLinks = new JSONArray();
        for (int i = 0; i < links.length; i++) {
          jsonLinks.put(links[i]);          
        }
        htmlIds.putOpt("broadLinks", jsonLinks);
      }
      buf.append(htmlIds.toString());
    }
    catch (JSONException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    buf.append(");");      
  }

  protected void registerJavascriptEventHandler(StringBuffer buf) { 
    FormContext formContext = getFormContext();
    DataFormDataElementContext context = getDataElementContext();
    String jsFormObjId = formContext.getJavascriptObjectId();
    String jsDataElementObjId = context.getJavascriptObjectId();
    buf.append("GSBIO.kc.data.FormInstances.getFormInstance(");
    if (jsFormObjId != null) {
      buf.append("'");
      buf.append(jsFormObjId);
      buf.append("'");
    }
    buf.append(").getDataElement('");
    buf.append(jsDataElementObjId);
    buf.append("').registerEventHandler();");
  }
  
  private void createJavascriptDataElement(StringBuffer buf,
                                          HttpServletRequest request,
                                          DataFormDataElementContext context,
                                          String dataElementId) {
    
    DataElement dataElement = context.getDataElement();
    DetDataElement detDataElement = context.getDetDataElement();
    JSONObject jsonDataElement = null;
    try {
      jsonDataElement = new JSONObject();
      jsonDataElement.put("metadata", createJavascriptDataElementMetadata(context, request));      
      jsonDataElement.put("systemName", detDataElement.getSystemName());
      jsonDataElement.put("id", dataElementId);
      if (dataElement != null) {
        jsonDataElement.putOpt("valueNote", dataElement.getValueNote());                
        DataElementValue[] values = dataElement.getDataElementValues();
        int numValues = values.length;
        if (numValues > 0) {
          JSONArray jsonValues = new JSONArray();
          for (int i = 0; i < numValues; i++) {
            DataElementValue value = values[i];
            JSONObject jsonValue = new JSONObject();
            jsonValue.putOpt("value", value.getValue());
            jsonValue.putOpt("valueOther", value.getValueOther());
            jsonValues.put(jsonValue);
          } // for each data element value
          jsonDataElement.put("values", jsonValues);
        } // if the data element has values 
      } // if the data element is not null
    }
    catch (JSONException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    
    if (jsonDataElement != null) {
      buf.append("GSBIO.kc.data.DataElement(");
      buf.append(jsonDataElement.toString());
      buf.append(")");
    }
  }

  private JSONObject createJavascriptDataElementMetadata(DataFormDataElementContext context,
                                                         HttpServletRequest request) { 
    DetDataElement detDataElement = context.getDetDataElement();
    String contextPath = request.getContextPath();
    JSONObject jsonMetadata = new JSONObject();
    try {
      jsonMetadata.put("cui", detDataElement.getCui());
      jsonMetadata.put("isMultivalued", 
          detDataElement.isMultivalued() && !context.isTreatAsSinglevalued());
      jsonMetadata.put("isHasAde", detDataElement.isHasAde());
      jsonMetadata.put("isDatatypeCv", detDataElement.isDatatypeCv());
      jsonMetadata.put("isMultilevelValueSet", detDataElement.isMultilevelValueSet());
      if (detDataElement.isHasOtherValue()) {
        jsonMetadata.put("otherCui", detDataElement.getOtherValueCui());        
      }
      if (detDataElement.isHasNoValue()) {
        jsonMetadata.put("noCui", detDataElement.getNoValueCui());        
      }
      jsonMetadata.put("seeNoteCui", DataElementTaxonomy.SYSTEM_STANDARD_VALUE_SEE_NOTE);
      jsonMetadata.put("noteButtonAdd", WebUtils.NOTE_BUTTON_VALUE_ADD);
      jsonMetadata.put("noteButtonDelete", WebUtils.NOTE_BUTTON_VALUE_DELETE);
      jsonMetadata.put("imgExpand", WebUtils.getImageSourceExpand(contextPath));
      jsonMetadata.put("imgCollapse", WebUtils.getImageSourceCollapse(contextPath));
      jsonMetadata.put("adePopup", getAdePopupUrl());
      jsonMetadata.put("standardVsValue", WebUtils.STANDARD_VS_VALUE);
      jsonMetadata.put("broadVsValue", WebUtils.BROAD_VS_VALUE);
      
      DetValueSet valueSet = context.getValueSetBroad();
      if (valueSet != null) {
        jsonMetadata.put("broadVsLeafCount", getCountLeafValues(valueSet.getValues()));
      }

      DetValueSet nonAtvValueSet = detDataElement.getNonAdeTriggerValueSet();
      if (nonAtvValueSet != null) {
        JSONArray jsonValues = new JSONArray();
        DetValueSetValue[] values = nonAtvValueSet.getValues();
        populateNonAtvValues(values, jsonValues);
        jsonMetadata.put("nonAtvVs", jsonValues);
      }
    }
    catch (JSONException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    return jsonMetadata; 
  }
  
  private int getCountLeafValues(DetValueSetValue[] values) {
    int count = 0;
    for (int i = 0; i < values.length; i++) {
      DetValueSetValue value = values[i];
      DetValueSetValue[] childValues = value.getValues(); 
      if (childValues.length > 0) {
        count = count + getCountLeafValues(childValues);
      }
      else {
        count++;
      }
    }
    return count;
  }
  
  
  
  private void populateNonAtvValues(DetValueSetValue[] values, JSONArray jsonValues) {
    if (values == null) {
      return;
    }
    for (int i = 0; i < values.length; i++) {
      DetValueSetValue value = values[i];
      jsonValues.put(value.getCui());
      populateNonAtvValues(value.getValues(), jsonValues); 
    }    
  }
  
}
