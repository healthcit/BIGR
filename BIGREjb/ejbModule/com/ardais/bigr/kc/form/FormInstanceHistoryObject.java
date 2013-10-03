package com.ardais.bigr.kc.form;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.btx.framework.BtxHistoryAttributes;
import com.ardais.bigr.btx.framework.DescribableAsHistoryObject;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.IcpUtils;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.det.DetAdeElement;
import com.gulfstreambio.kc.det.DetDataElement;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.Ade;
import com.gulfstreambio.kc.form.AdeElement;
import com.gulfstreambio.kc.form.DataElement;
import com.gulfstreambio.kc.form.DataElementValue;
import com.gulfstreambio.kc.form.ElementValue;
import com.gulfstreambio.kc.form.FormInstance;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionDataElement;
import com.gulfstreambio.kc.util.KcUtils;

/**
 * Provides for common BTX history handling of KC form instances.
 */
public class FormInstanceHistoryObject implements DescribableAsHistoryObject {

  private static final String FORM_INSTANCE_ID = "formInstanceId";
  private static final String DOMAIN_OBJECT_ID = "domainObjectId";
  private static final String DOMAIN_OBJECT_TYPE = "domainObjectType";
  private static final String CREATION_DATETIME = "creationDateTime";
  private static final String MODIFICATION_DATETIME = "modificationDateTime";
  private static final String FORM_DEFINITION_ID = "formDefinitionId";
  private static final String FORM_NAME = "formName";
  private static final String DATA_ELEMENTS = "dataElements";
  private static final String DESCRIPTION = "description";
  private static final String NOTE = "note";
  private static final String VALUES = "values";
  private static final String VALUE = "value";
  private static final String ADES = "ades";

  private FormInstance _formInstance;
  private FormDefinition _formDefinition;
  
  private FormDefinition getFormDefinition() {
    return _formDefinition;
  }
  public void setFormDefinition(FormDefinition formDefinition) {
    _formDefinition = formDefinition;
  }
  private FormInstance getFormInstance() {
    return _formInstance;
  }
  public void setFormInstance(FormInstance formInstance) {
    _formInstance = formInstance;
  }

  /**
   * Returns a BtxHistoryAttributes that describes the KC form instance.  Make sure that both
   * the form instance and form definition have been set before calling this method.
   */
  public Object describeAsHistoryObject() {
    BtxHistoryAttributes attributes = new BtxHistoryAttributes();
    FormInstance form = getFormInstance();
    FormDefinition formDef = getFormDefinition();
    if ((form != null) && (formDef != null)) {
      //copy the simple attributes of the form
      String id = form.getFormInstanceId();
      if (!ApiFunctions.isEmpty(id)) {
        attributes.setAttribute(FORM_INSTANCE_ID, id);
      }
      String doId = form.getDomainObjectId();
      if (!ApiFunctions.isEmpty(doId)) {
        attributes.setAttribute(DOMAIN_OBJECT_ID, doId);
      }
      String doType = form.getDomainObjectType();
      if (!ApiFunctions.isEmpty(doType)) {
        attributes.setAttribute(DOMAIN_OBJECT_TYPE, doType);
      }
      Timestamp createTime = form.getCreationDateTime();
      if (createTime != null) {
        attributes.setAttribute(CREATION_DATETIME, String.valueOf(createTime.getTime()));
      }
      Timestamp modifyTime = form.getModificationDateTime();
      if (modifyTime != null) {
        attributes.setAttribute(MODIFICATION_DATETIME, String.valueOf(modifyTime.getTime()));
      }      
      id = form.getFormDefinitionId();
      if (!ApiFunctions.isEmpty(id)) {
        attributes.setAttribute(FORM_DEFINITION_ID, id);
      }
      String formName = formDef.getName();
      if (!ApiFunctions.isEmpty(formName)) {
        attributes.setAttribute(FORM_NAME, formName);
      }

      //now handle the data elements. For each data element on the form we need to store:
      // 1) it's description
      // 2) it's note
      // 3) it's value(s) 
      //for each value we need to store each associated ADE description and value(s)
      List dataElements = new ArrayList();
      attributes.setAttribute(DATA_ELEMENTS, dataElements);
      DataElementTaxonomy det = DetService.SINGLETON.getDataElementTaxonomy();
      FormDefinitionDataElement[] dataElementDefs = formDef.getDataElements();
      for (int i = 0; i < dataElementDefs.length; i++) {
        FormDefinitionDataElement dataElementDef = dataElementDefs[i];
        String cui = dataElementDef.getCui();
        DataElement dataElement = form.getDataElement(cui);
        DetDataElement dataElementMetaData = det.getDataElement(cui);
        //if the form has a data element for this cui, add it's information to the history record
        if (dataElement != null) {
          //add a record for this data element's information to the history record
          BtxHistoryAttributes dataElementHistory = new BtxHistoryAttributes();
          dataElements.add(dataElementHistory);

          //get the description.
          dataElementHistory.setAttribute(DESCRIPTION, KcUtils.getDescription(dataElement, formDef));
          
          //get the note if any
          dataElementHistory.setAttribute(NOTE, dataElement.getValueNote());
            
          //iterate over the data element values, getting the text to display along with
          //any associated ADE information
          List values = new ArrayList();
          dataElementHistory.setAttribute(VALUES, values);
          String otherCui = dataElementMetaData.getOtherValueCui();
          DataElementValue[] dataElementValues = dataElement.getDataElementValues();
          for (int j = 0; j < dataElementValues.length; j++) {
            DataElementValue dataElementValue = dataElementValues[j];
            BtxHistoryAttributes dataElementValueHistory = new BtxHistoryAttributes();
            values.add(dataElementValueHistory);
            
            //get the value
            String dataElementValueString = dataElementValue.getValue();
            if (ApiFunctions.isEmpty(dataElementValueString)) {
              dataElementValueString = "";
            }
            else {
              // If this is a system standard value, then translate its CUI to its description.
              if (det.getSystemStandardValues().containsValue(dataElementValueString)) {
                dataElementValueString = det.getCuiDescription(dataElementValueString);          
              }
              // otherwise if this is a CV element, translate its CUI to its description
              else if (dataElementMetaData.isDatatypeCv()) {
                if (!dataElementValueString.equals(otherCui)) {
                  //TODO MC: get description from form definition if overridden
                  dataElementValueString = det.getCuiDescription(dataElementValueString);
                }
                else {
                  dataElementValueString = dataElementValue.getValueOther();
                }
              }
            }
            dataElementValueHistory.setAttribute(VALUE, dataElementValueString);
            
            //get any associated ADE information
            if (dataElementMetaData.isHasAde()) {
              List adeList = new ArrayList();
              dataElementValueHistory.setAttribute(ADES, adeList);
              DetAdeElement[] adeElements = dataElementMetaData.getAde().getAdeElements();
              for (int n = 0; n < adeElements.length; n++) {
                BtxHistoryAttributes adeElementHistory = new BtxHistoryAttributes();
                adeList.add(adeElementHistory);
                
                //get description of ade element from meta data
                DetAdeElement adeElementMetaData = adeElements[n];
                adeElementHistory.setAttribute(DESCRIPTION, adeElementMetaData.getDescription());

                //get values from the data element's ade by passing cui from meta data.  Note that the 
                //values could be cv's so same translation as above is necessary
                List adeValues = new ArrayList();
                adeElementHistory.setAttribute(VALUES, adeValues);
                String adeOtherCui = adeElementMetaData.getOtherValueCui();
                Ade ade = dataElementValue.getAde();
                if (ade == null) {
                  adeValues.add("");
                }
                else {
                  AdeElement adeElement = ade.getAdeElement(adeElementMetaData.getCui());
                  if (adeElement == null) {
                    adeValues.add("");
                  }
                  else {
                    ElementValue[] adeElementValues = ade.getAdeElement(adeElementMetaData.getCui()).getElementValues();
                    for (int k = 0; k < adeElementValues.length; k++) {
                      ElementValue adeValue = adeElementValues[k];
                      
                      //get the value
                      String adeValueString = adeValue.getValue();
                      if (ApiFunctions.isEmpty(adeValueString)) {
                        adeValueString = "";
                      }
                      else {
                        // If this is a system standard value, then translate its CUI to its description.
                        if (det.getSystemStandardValues().containsValue(adeValueString)) {
                          adeValueString = det.getCuiDescription(adeValueString);          
                        }
                        // otherwise if this is a CV element, translate its CUI to its description
                        else if (adeElementMetaData.isDatatypeCv()) {
                          if (!adeValueString.equals(adeOtherCui)) {
                            //TODO MC: get description from form definition if overridden
                            adeValueString = det.getCuiDescription(adeValueString);
                          }
                          else {
                            adeValueString = adeValue.getValueOther();
                          }
                        }
                      }
                      
                      adeValues.add(adeValueString);
                    }                  
                  }
                }
              }
            }
          }
        }
      }
    }
    return attributes;
  }

  public String doGetDetailsAsHTMLFull(BtxHistoryAttributes historyObject, 
                                          SecurityInfo securityInfo, String btxType) {
    StringBuffer sb = new StringBuffer(1000);
    
    if (BTXDetails.BTX_TYPE_KC_FORM_CREATE.equals(btxType)) {
      sb.append("Created");      
    }
    else if (BTXDetails.BTX_TYPE_KC_FORM_UPDATE.equals(btxType)) {
      sb.append("Modified");      
    }
    sb.append(" form");
    String formName = (String)historyObject.getAttribute(FORM_NAME);
    if (!ApiFunctions.isEmpty(formName)) {
      sb.append(" ");
      String creationDateTime = (String)historyObject.getAttribute(CREATION_DATETIME);
      StringBuffer displayValue = new StringBuffer(100);
      displayValue.append(formName);
      if (!ApiFunctions.isEmpty(creationDateTime)) {
        displayValue.append(" (");
        displayValue.append((new Date(new Long(creationDateTime).longValue())).toString());
        displayValue.append(")");
      }
      //do not escape form name, since prepareLink does that
      sb.append(IcpUtils.prepareLink((String)historyObject.getAttribute(FORM_DEFINITION_ID), displayValue.toString(), securityInfo));
      String formInstanceId = (String)historyObject.getAttribute(FORM_INSTANCE_ID);
      if (!ApiFunctions.isEmpty(formInstanceId)) {
        sb.append(" (");
        sb.append(formInstanceId);
        sb.append(")");
      }
    }
    String objectId = (String)historyObject.getAttribute(DOMAIN_OBJECT_ID);
    String objectType = (String)historyObject.getAttribute(DOMAIN_OBJECT_TYPE);
    if (!ApiFunctions.isEmpty(objectType) && !ApiFunctions.isEmpty(objectId)) {
      sb.append(" for ");
      sb.append(Escaper.htmlEscapeAndPreserveWhitespace(objectType));
      sb.append(" ");
      sb.append(IcpUtils.prepareLink(objectId, securityInfo));
    }
    sb.append(". The form has the following attributes:");
    sb.append("<ul>");
    doGetDetailsAsHTMLDataElementsOnly(historyObject, sb);
    sb.append("</ul>");
    return sb.toString();
  }

  public void doGetDetailsAsHTMLDataElementsOnly(BtxHistoryAttributes historyObject, StringBuffer sb) {
    Iterator dataElements = ApiFunctions.safeList((List)historyObject.getAttribute(DATA_ELEMENTS)).iterator();
    while (dataElements.hasNext()) {
      BtxHistoryAttributes dataElement = (BtxHistoryAttributes)dataElements.next();
      boolean hasAnyValue = hasAnyValues(dataElement);
      boolean hasSingleValue = false;
      if (hasAnyValue) {
        sb.append("<li><b>");
        sb.append(Escaper.htmlEscapeAndPreserveWhitespace((String)dataElement.getAttribute(DESCRIPTION)));
        sb.append("</b>: ");
        List valueList = ApiFunctions.safeList((List)dataElement.getAttribute(VALUES));
        if (valueList.size() == 1) {
          hasSingleValue = true;
          processDataElementValue((BtxHistoryAttributes)valueList.get(0), sb);
        }
        else {
          Iterator valueIterator = valueList.iterator();
          sb.append("<ul>");
          while (valueIterator.hasNext()) {
            sb.append("<li>");
            processDataElementValue((BtxHistoryAttributes)valueIterator.next(), sb);
            sb.append("</li>");
          }          
          sb.append("</ul>");
        }
      }
      String note = (String)dataElement.getAttribute(NOTE);
      if (!ApiFunctions.isEmpty(note)) {
        if (!hasAnyValue) {
          sb.append("<li><b>");
          sb.append(Escaper.htmlEscapeAndPreserveWhitespace((String)dataElement.getAttribute(DESCRIPTION)));
          sb.append("</b>: ");
          sb.append("<br>");
        }
        if (hasSingleValue) {
          sb.append("<br>");
        }
        sb.append("Note: ");
        sb.append(Escaper.htmlEscapeAndPreserveWhitespace(note));
      }
      sb.append("</li>");
    }
  }

  private void processDataElementValue(BtxHistoryAttributes value, StringBuffer sb) {
    String valueValue = (String)value.getAttribute(VALUE);
    if (!ApiFunctions.isEmpty(valueValue)) {
      sb.append(Escaper.htmlEscapeAndPreserveWhitespace(valueValue));
      Iterator adeIterator = ApiFunctions.safeList((List)value.getAttribute(ADES)).iterator();
      if (adeIterator.hasNext()) {
        boolean includeAdeSeperator = false;
        boolean includeBeginParen = true;
        while (adeIterator.hasNext()) {
          BtxHistoryAttributes ade = (BtxHistoryAttributes)adeIterator.next();
          if (hasAnyValuesAde(ade)) {
            if (includeBeginParen) {
              sb.append(" (");
              includeBeginParen = false;
            }           
            if (includeAdeSeperator) {
              sb.append("; ");
            }
            includeAdeSeperator = true;

            sb.append("<b>");
            sb.append(Escaper.htmlEscapeAndPreserveWhitespace((String)ade.getAttribute(DESCRIPTION)));
            sb.append("</b>: ");

            Iterator adeValueIterator = ApiFunctions.safeList((List)ade.getAttribute(VALUES)).iterator();
            boolean includeAdeValueSeperator = false;
            while (adeValueIterator.hasNext()) {
              String adeValue = (String)adeValueIterator.next();
              if (!ApiFunctions.isEmpty(adeValue)) {
                if (includeAdeValueSeperator) {
                  sb.append(", ");
                }
                includeAdeValueSeperator = true;
                sb.append(Escaper.htmlEscapeAndPreserveWhitespace(adeValue));
              }
            }
          }
        }
        if (!includeBeginParen) {
          sb.append(")");
        }
      }
    }
  }
  
  private boolean hasAnyValues(BtxHistoryAttributes dataElement) {
    List valueList = ApiFunctions.safeList((List)dataElement.getAttribute(VALUES));
    if (!valueList.isEmpty()) {
      Iterator i = valueList.iterator();
      while (i.hasNext()) {
        BtxHistoryAttributes value = (BtxHistoryAttributes)i.next();
        String valueValue = (String)value.getAttribute(VALUE);
        if (!ApiFunctions.isEmpty(valueValue)) {
          return true;
        }      
      }          
    }
    return false;
  }

  private boolean hasAnyValuesAde(BtxHistoryAttributes ade) {
    List valueList = ApiFunctions.safeList((List)ade.getAttribute(VALUES));
    if (!valueList.isEmpty()) {
      Iterator i = valueList.iterator();
      while (i.hasNext()) {
        String adeValue = (String)i.next();
        if (!ApiFunctions.isEmpty(adeValue)) {
          return true;
        }      
      }          
    }
    return false;
  }
}

