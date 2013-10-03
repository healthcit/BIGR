package com.gulfstreambio.kc.web.taglib;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.UniqueIdGenerator;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.form.AdeElement;
import com.gulfstreambio.kc.form.AdeElementValue;
import com.gulfstreambio.kc.web.support.DataFormAdeElementContext;
import com.gulfstreambio.kc.web.support.DataFormDataElementContext;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.KcUiContext;
import com.gulfstreambio.kc.web.support.WebUtils;

public abstract class AdeElementBaseTag extends KcBaseTag {

  public AdeElementBaseTag() {
    super();
  }

  protected DataFormDataElementContext getDataElementContext() {
    return getFormContext().getDataFormDataElementContext();
  }
    
  protected DataFormAdeElementContext getAdeElementContext() {
    return getFormContext().getDataFormAdeElementContext();
  }

  protected void createContainerStart(StringBuffer buf) { 
    // First create the container id and add it to the context.
    HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
    UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
    String containerId = idgen.getUniqueId();
    getAdeElementContext().setHtmlIdContainer(containerId);    

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
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

    // Create the JavaScript ADE element, adding it to the data element.  Note that this is a bit
    // tricky since ADE elements are actually added to ADEs, which are added to data element values.
    // In addition, there may be multiple data element values, so we have to know with which data 
    // element value this ADE element is associated.  We determine this through a call to the
    // abstract getValueIndex() method, which subclasses must override for this purpose.
    
    // First get the data element.
    FormContext formContext = getFormContext();
    DataFormDataElementContext dataElementContext = getDataElementContext();
    DataFormAdeElementContext adeElementContext = getAdeElementContext();
    String adeSystemName = dataElementContext.getDetDataElement().getAde().getSystemName();
    String jsFormObjId = formContext.getJavascriptObjectId();
    String jsDataElementObjId = dataElementContext.getJavascriptObjectId();
    buf.append("var v;");
    buf.append("var d = GSBIO.kc.data.FormInstances.getFormInstance(");
    if (jsFormObjId != null) {
      buf.append("'");
      buf.append(jsFormObjId);
      buf.append("'");
    }
    buf.append(").getDataElement('");
    buf.append(jsDataElementObjId);
    buf.append("');");

    // If the data element has a value, then add an ADE to it if necessary.  If the data element
    // does not have a value, then create one with an ADE initializer.  After that, add the
    // ADE element to the ADE.
    buf.append("v = d.getValue(");
    buf.append(getValueIndex());
    buf.append(");");
    buf.append("if (v) {");
    buf.append("if (!v.ade) {");
    buf.append("v.ade = GSBIO.kc.data.Ade({'systemName':'");
    buf.append(adeSystemName);
    buf.append("'})");
    buf.append("}} else {");
    buf.append("v = GSBIO.kc.data.DataElementValue({'ade':{'systemName':'");
    buf.append(adeSystemName);
    buf.append("'}});");
    buf.append("d.addValue(v);");
    buf.append("}");
    buf.append("v.ade.addAdeElement(");
    createJavascriptAdeElement(buf, request, adeElementContext);
    buf.append(");");
  }
  
  protected void setJavascriptHtmlIds(StringBuffer buf) {
    FormContext formContext = getFormContext();
    DataFormDataElementContext dataElementContext = getDataElementContext();
    DataFormAdeElementContext adeElementContext = getAdeElementContext();
    String jsFormObjId = formContext.getJavascriptObjectId();
    String jsDataElementObjId = dataElementContext.getJavascriptObjectId();
    buf.append("GSBIO.kc.data.FormInstances.getFormInstance(");
    if (jsFormObjId != null) {
      buf.append("'");
      buf.append(jsFormObjId);
      buf.append("'");
    }
    buf.append(").getDataElement('");
    buf.append(jsDataElementObjId);
    buf.append("').values[");
    buf.append(getValueIndex());
    buf.append("].ade.getAdeElement('");
    buf.append(adeElementContext.getSystemName());
    buf.append("').setHtmlIds(");      
    try {
      JSONObject htmlIds = new JSONObject();
      htmlIds.putOpt("container", adeElementContext.getHtmlIdContainer());
      htmlIds.putOpt("primary", adeElementContext.getHtmlIdPrimary());
      htmlIds.putOpt("otherContainer", adeElementContext.getHtmlIdOtherContainer());
      htmlIds.putOpt("other", adeElementContext.getHtmlIdOther());
      htmlIds.putOpt("otherText", adeElementContext.getHtmlIdOtherText());
      htmlIds.putOpt("otherAdd", adeElementContext.getHtmlIdOtherAdd());
      htmlIds.putOpt("otherRemove", adeElementContext.getHtmlIdOtherRemove());
      htmlIds.putOpt("noteContainer", adeElementContext.getHtmlIdNoteContainer());
      htmlIds.putOpt("note", adeElementContext.getHtmlIdNote());
      htmlIds.putOpt("noteButton", adeElementContext.getHtmlIdNoteButton());
      htmlIds.putOpt("broadValueSetContainer", adeElementContext.getHtmlIdBroadValueSetContainer());
      htmlIds.putOpt("broadValueSet", adeElementContext.getHtmlIdBroadValueSet());
      buf.append(htmlIds.toString());
    }
    catch (JSONException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    buf.append(");");          
  }

  protected void registerJavascriptEventHandler(StringBuffer buf) { 
    FormContext formContext = getFormContext();
    DataFormDataElementContext dataElementContext = getDataElementContext();
    DataFormAdeElementContext adeElementContext = getAdeElementContext();
    String jsFormObjId = formContext.getJavascriptObjectId();
    String jsDataElementObjId = dataElementContext.getJavascriptObjectId();
    buf.append("GSBIO.kc.data.FormInstances.getFormInstance(");
    if (jsFormObjId != null) {
      buf.append("'");
      buf.append(jsFormObjId);
      buf.append("'");
    }
    buf.append(").getDataElement('");
    buf.append(jsDataElementObjId);
    buf.append("').values[");
    buf.append(getValueIndex());
    buf.append("].ade.getAdeElement('");
    buf.append(adeElementContext.getSystemName());
    buf.append("').registerEventHandler();");
  }
  
  private void createJavascriptAdeElement(StringBuffer buf,
                                          HttpServletRequest request,
                                          DataFormAdeElementContext context) {
     
     AdeElement adeElement = context.getAdeElement();
     DetAdeElement detAdeElement = context.getDetAdeElement();
     JSONObject jsonAdeElement = null;
     try {
       jsonAdeElement = new JSONObject();
       jsonAdeElement.put("metadata", createJavascriptAdeElementMetadata(context, request));
       jsonAdeElement.put("systemName", detAdeElement.getSystemName());
       
       if (adeElement != null) {
         AdeElementValue[] values = adeElement.getAdeElementValues();
         int numValues = values.length;
         if (numValues > 0) {
           JSONArray jsonValues = new JSONArray();
           for (int i = 0; i < numValues; i++) {
             AdeElementValue value = values[i];
             JSONObject jsonValue = new JSONObject();
             jsonValue.putOpt("value", value.getValue());
             jsonValue.putOpt("valueOther", value.getValueOther());
             jsonValues.put(jsonValue);
           } // for each ADE element value
           jsonAdeElement.put("values", jsonValues);
         } // if the ADE element has values 
       } // if the ADE element is not null
     }
     catch (JSONException e) {
       ApiFunctions.throwAsRuntimeException(e);
     }
     
     if (jsonAdeElement != null) {
       buf.append("GSBIO.kc.data.AdeElement(");
       buf.append(jsonAdeElement.toString());
       buf.append(")");
     }
   }

   private JSONObject createJavascriptAdeElementMetadata(DataFormAdeElementContext context,
                                                         HttpServletRequest request) { 
     DetAdeElement detAdeElement = context.getDetAdeElement();
     String contextPath = request.getContextPath();
     JSONObject jsonMetadata = new JSONObject();
     try {
       jsonMetadata.put("cui", detAdeElement.getCui());
       jsonMetadata.put("isMultivalued", detAdeElement.isMultivalued());
       jsonMetadata.put("isDatatypeCv", detAdeElement.isDatatypeCv());
       if (detAdeElement.isHasOtherValue()) {
         jsonMetadata.put("otherCui", detAdeElement.getOtherValueCui());        
       }
       if (detAdeElement.isHasNoValue()) {
         jsonMetadata.put("noCui", detAdeElement.getNoValueCui());        
       }
       jsonMetadata.put("imgExpand", WebUtils.getImageSourceExpand(contextPath));
       jsonMetadata.put("imgCollapse", WebUtils.getImageSourceCollapse(contextPath));
     }
     catch (JSONException e) {
       ApiFunctions.throwAsRuntimeException(e);
     }
     return jsonMetadata; 
   }
   
   protected abstract String getValueIndex();
  
}
