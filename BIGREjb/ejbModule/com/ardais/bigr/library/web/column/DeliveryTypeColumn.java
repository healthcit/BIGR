package com.ardais.bigr.library.web.column;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.query.ColumnPermissions;
import com.ardais.bigr.query.ViewParams;
import com.ardais.bigr.util.ArtsConstants;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.gboss.GbossValue;
import com.gulfstreambio.gboss.GbossValueSet;

/**
 * Writes a select box for Delivery Type to the screen.  Used in the Request Confirm screen.
 */
public class DeliveryTypeColumn extends SampleColumnImpl {

  /**
   * A Map of delivery types, keyed by sample type.  Each Map entry is a List of delivery types.
   * If a sample type does not have any delivery types (e.g. it is delivered "as is", and cannot
   * be converted to another sample type), then the List is empty.
   */
  private static Map _deliveryTypes;
  
  static {
    _deliveryTypes = new HashMap();

    GbossValueSet deliveryTypeValueSet = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_REQUEST_DELIVERY_TYPE);

    LegalValueSet sampleTypes = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_SAMPLE_TYPE);
    for (int i = 0; i < sampleTypes.getCount(); i++) {
      List delivTypes = new ArrayList();
      String sampleTypeCid = sampleTypes.getValue(i);
      Iterator valueSetIterator = deliveryTypeValueSet.getValues().iterator();
      GbossValue deliveryTypesForSample = null;
      while (valueSetIterator.hasNext() && deliveryTypesForSample == null) {
        GbossValue value = (GbossValue)valueSetIterator.next();
        if (sampleTypeCid.equalsIgnoreCase(value.getCui())) {
          deliveryTypesForSample = value;
        }
      }
      if (deliveryTypesForSample != null) {
        Iterator iterator = deliveryTypesForSample.getValues().iterator();
        while (iterator.hasNext()) {
          delivTypes.add(((GbossValue)iterator.next()).getDescription());
        }
      }
      else {
        // Intentionally do nothing.  It is OK for a sample type to not be represented in the
        // delivery types value set, since this indicates that it is delivered "as is". 
      }
      _deliveryTypes.put(sampleTypeCid, delivTypes);
    } 
  }

  public DeliveryTypeColumn(ViewParams cp) {
    super(
      115,
      "library.ss.result.header.select.label",
      "library.ss.result.header.select.comment",
      ColumnPermissions.ROLE_SYSTEM_OWNER | ColumnPermissions.ROLE_DI | ColumnPermissions.ROLE_CUST,
      ColumnPermissions.SCRN_CONFIRM_REQ,
      cp);
  }
  
  /**
   * @see com.ardais.bigr.library.web.column.SampleColumnImpl#getRawBodyText(SampleRowParams)
   */
  protected String getRawBodyText(SampleRowParams rp) throws IOException {
    //nothing to return here, since the body is all html
    return "";
  }

  protected String getBodyText(SampleRowParams rp) throws IOException {
    StringBuffer result = new StringBuffer();

    SampleSelectionHelper ssh = rp.getItemHelper();
    String sampleId = ssh.getId();
    String sampleType = ssh.getSampleType();

    result.append("<td>");
    
    List delivTypes = getDeliveryTypes(sampleType);
    if (delivTypes.isEmpty()) {
      result.append("<input name=\"delivType\" type=\"hidden\" value=\"");
      result.append(sampleId);
      result.append('$');
      result.append(GbossFactory.getInstance().getDescription(sampleType));
      result.append("\"/>");
      result.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
    }
    else {
      result.append("<select name=\"delivType\">");
      appendOptionsHtml(result, sampleId, delivTypes);
      result.append("</select>");
    }

    if (ssh.isSampleRestricted()) {
      result.append(" <span class=\"restricted\">R</span>");
    }
    if (ssh.isBms()) {
      result.append(" <span class=\"bms\">B</span>");
    }
    result.append("</td>");
    return result.toString();
  }

  private void appendOptionsHtml(StringBuffer buf, String sampleId, List delivTypes) {
    Iterator i = delivTypes.iterator();
    while (i.hasNext()) {
      String delivType = (String) i.next();
      buf.append("<option value='");
      buf.append(sampleId);
      buf.append('$');
      buf.append(delivType);
      buf.append("'>");
      buf.append(delivType);
      buf.append("</option>");
    }
  }

  private static List getDeliveryTypes(String sampleType) {
    return (List) _deliveryTypes.get(sampleType);
  }
  
}
