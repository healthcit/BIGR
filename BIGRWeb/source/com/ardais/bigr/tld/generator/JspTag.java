package com.ardais.bigr.tld.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a tag element in a TLD file. 
 */
public class JspTag implements Comparable {

  private String _name;
  private String _tagclass;
  private String _teiclass;
  private String _bodycontent = "JSP";
  private String _info;

  private List _attrs = new ArrayList();
  private List _sortedAttrs;

  /**
   * Creates a new JspTag.  
   */
  public JspTag() {
    super();
  }

  public void addAttribute(JspTagAttribute attr) {
    _attrs.add(attr);
  }

  public List getAttributes() {
    return _attrs;
  }

  public List getSortedAttributes() {
    if (_sortedAttrs == null) {
      _sortedAttrs = new ArrayList(getAttributes());
      Collections.sort(_sortedAttrs);
    }
    return _sortedAttrs;
  }

  public String getBodycontent() {
    return _bodycontent;
  }

  public String getInfo() {
    return _info;
  }

  public String getName() {
    return _name;
  }

  public String getTagclass() {
    return _tagclass;
  }

  public String getTeiclass() {
    return _teiclass;
  }
  
  public void setBodycontent(String string) {
    _bodycontent = string;
  }

  public void setInfo(String string) {
    _info = string;
  }

  public void setName(String string) {
    _name = string;
  }

  public void setTagclass(String string) {
    _tagclass = string;
  }

  public void setTeiclass(String string) {
    _teiclass = string;
  }

  /**
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Object o) {
    JspTag tag = (JspTag)o;
    return (getName().compareTo(tag.getName()));
  }

}
