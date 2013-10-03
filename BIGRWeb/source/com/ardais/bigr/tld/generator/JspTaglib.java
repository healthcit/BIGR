package com.ardais.bigr.tld.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a taglib element in a TLD file. 
 */
public class JspTaglib {

  private String _tlibversion;
  private String _jspversion;
  private String _shortname;
  private String _info;

  private List _tags = new ArrayList();
  private List _sortedTags;

  /**
   * Creates a new Jsptaglib.
   */
  public JspTaglib() {
    super();
  }

  public void addTag(JspTag tag) {
    _tags.add(tag);
  }

  public List getTags() {
    return _tags;
  }

  public List getSortedTags() {
    if (_sortedTags == null) {
      _sortedTags = new ArrayList(getTags());
      Collections.sort(_sortedTags);
    }
    return _sortedTags;
  }

  public String getJspversion() {
    return _jspversion;
  }

  public String getShortname() {
    return _shortname;
  }

  public String getTlibversion() {
    return _tlibversion;
  }

  public String getInfo() {
    return _info;
  }

  public void setJspversion(String string) {
    _jspversion = string;
  }

  public void setShortname(String string) {
    _shortname = string;
  }

  public void setTlibversion(String string) {
    _tlibversion = string;
  }

  public void setInfo(String string) {
    _info = string;
  }

}
