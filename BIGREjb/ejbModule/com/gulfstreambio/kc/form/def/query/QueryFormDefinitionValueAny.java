package com.gulfstreambio.kc.form.def.query;

import java.io.Serializable;

import com.ardais.bigr.util.BigrXmlUtils;

public class QueryFormDefinitionValueAny extends QueryFormDefinitionValueBase 
  implements Serializable {

  /**
   * Creates a new <code>QueryFormDefinitionValueAny</code>.
   */
  public QueryFormDefinitionValueAny() {
    super();
  }

  public QueryFormDefinitionValueAny(QueryFormDefinitionValueAny value) {
    this();
    // nothing else to do
  }

  public String getValueType() {
    return QueryFormDefinitionValueTypes.ANY;
  }

  public void toXml(StringBuffer buf) {
    BigrXmlUtils.writeElementStartTag(buf, "valueAny", getXmlIndentLevel());
    buf.append("/>");
  }

}
