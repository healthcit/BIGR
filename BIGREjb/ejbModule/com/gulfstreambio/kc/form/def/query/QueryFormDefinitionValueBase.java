package com.gulfstreambio.kc.form.def.query;

import java.io.Serializable;

import com.gulfstreambio.kc.form.def.FormDefinition;

public abstract class QueryFormDefinitionValueBase 
  implements QueryFormDefinitionValue, Serializable {

  private QueryFormDefinition _form;
  private int _xmlIndentLevel = 0;

  /**
   * Creates a new <code>QueryFormDefinitionValueBase</code>.
   */
  public QueryFormDefinitionValueBase() {
    super();
  }

  public FormDefinition getForm() {
    return getQueryForm();
  }

  public QueryFormDefinition getQueryForm() {
    return _form;
  }

  protected void setQueryForm(QueryFormDefinition form) {
    _form = form;
  }

  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }

  protected int getXmlIndentLevel() {
    return _xmlIndentLevel;
  }

  protected void setXmlIndentLevel(int indentLevel) {
    _xmlIndentLevel = indentLevel;
  }
}
