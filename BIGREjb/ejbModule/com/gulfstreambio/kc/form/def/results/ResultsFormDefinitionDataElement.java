package com.gulfstreambio.kc.form.def.results;

import java.io.Serializable;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionDataElementBase;
import com.gulfstreambio.kc.form.def.Tag;

public class ResultsFormDefinitionDataElement 
extends FormDefinitionDataElementBase implements Serializable {

  private ResultsFormDefinition _form;

  /**
   *  Creates a new ResultsFormDefinitionDataElement. 
   */
  public ResultsFormDefinitionDataElement() {
    super();
  }

  public ResultsFormDefinitionDataElement(ResultsFormDefinitionDataElement dataElement) {
    super(dataElement);
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
    buf.append("\n  <dataElement");
    BigrXmlUtils.writeAttribute(buf, "cui", getCui());
    String displayName = getDisplayName();
    if (!ApiFunctions.isEmpty(displayName)) {
      BigrXmlUtils.writeAttribute(buf, "displayName", displayName);
    }
    buf.append(">");
    Tag[] tags = getTags();
    for (int i = 0; i < tags.length; i++) {
      tags[i].toXml(buf);
    }
    buf.append("\n  </dataElement>\n");
  }

  /**
   * Implements {@link java.lang.Object#equals(java.lang.Object) equals}.
   */
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ResultsFormDefinitionDataElement)) {
      return false;
    }
    
    ResultsFormDefinitionDataElement de = (ResultsFormDefinitionDataElement) obj;
    String thisCui = getCui();
    String otherCui = de.getCui();
    String thisDisplayName = getDisplayName();        
    String otherDisplayName = de.getDisplayName();
        
    boolean result = true;
    result = result && ((thisCui == null) ? otherCui == null : thisCui.equals(otherCui));  
    result =
      result && ((thisDisplayName == null) 
          ? otherDisplayName == null : thisDisplayName.equals(otherDisplayName));
    result = result && (getTags().equals(de.getTags()));  
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    String cui = getCui();
    String displayName = getDisplayName();        

    int result = 17;
    
    result = 37*result + ((cui == null) ? 0 : cui.hashCode());
    result = 37*result + ((displayName == null) ? 0 : displayName.hashCode());
    result = 37*result + (getTags().hashCode());
    
    return result;
  }
}
