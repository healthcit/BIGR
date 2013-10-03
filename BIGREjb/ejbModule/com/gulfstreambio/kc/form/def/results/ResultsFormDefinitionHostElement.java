package com.gulfstreambio.kc.form.def.results;

import java.io.Serializable;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionHostElementBase;
import com.gulfstreambio.kc.form.def.Tag;

public class ResultsFormDefinitionHostElement extends FormDefinitionHostElementBase 
  implements Serializable {

  private ResultsFormDefinition _form;

  /**
   *  Creates a new ResultsFormDefinitionDataElement. 
   */
  public ResultsFormDefinitionHostElement() {
    super();
  }

  public ResultsFormDefinitionHostElement(ResultsFormDefinitionHostElement hostElement) {
    super(hostElement);
  }

  public FormDefinition getForm() {
    return getResultsForm();
  }
  
  public ResultsFormDefinition getResultsForm() {
    return _form;
  }

  void setResultsForm(ResultsFormDefinition form) {
    _form = form;
  }

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    buf.append("\n  <hostElement");
    BigrXmlUtils.writeAttribute(buf, "hostId", getHostId());
    String displayName = getDisplayName();
    if (!ApiFunctions.isEmpty(displayName)) {
      BigrXmlUtils.writeAttribute(buf, "displayName", displayName);
    }
    buf.append(">");
    Tag[] tags = getTags();
    for (int i = 0; i < tags.length; i++) {
      tags[i].toXml(buf);
    }
    buf.append("\n  </hostElement>\n");
  }

  /**
   * Implements {@link java.lang.Object#equals(java.lang.Object) equals}.
   */
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ResultsFormDefinitionHostElement)) {
      return false;
    }
    
    ResultsFormDefinitionHostElement de = (ResultsFormDefinitionHostElement) obj;
        
    boolean result = true;
    String hostId = getHostId();
    String deHostId = de.getHostId();
    String displayName = getDisplayName();
    String deDisplayName = de.getDisplayName();
    result = result && ((hostId == null) ? deHostId == null : hostId.equals(deHostId));  
    result =
      result && ((displayName == null) ? deDisplayName == null : displayName.equals(deDisplayName));
    result = result && (getTags().equals(de.getTags()));  
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    int result = 17;
    
    String hostId = getHostId();
    String displayName = getDisplayName();
    result = 37*result + ((hostId == null) ? 0 : hostId.hashCode());
    result = 37*result + ((displayName == null) ? 0 : displayName.hashCode());
    result = 37*result + getTags().hashCode();
    
    return result;
  }
}
