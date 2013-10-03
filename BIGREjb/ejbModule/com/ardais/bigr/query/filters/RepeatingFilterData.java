package com.ardais.bigr.query.filters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;

public class RepeatingFilterData implements Serializable {

  private List _values = new ArrayList();
  
  public RepeatingFilterData() {
    super();
  }

  public void add(RepeatingSingleData data) {
    _values.add(data);
  }
  
  /**
   * @return
   */
  public List getValues() {
    return _values;
  }

  public boolean isEmpty() {
    Iterator iter = getValues().iterator();
    while (iter.hasNext()) {
      RepeatingSingleData repeatData = (RepeatingSingleData) iter.next();
      if (!repeatData.isEmpty()) {
        return false;
      }
    }
    return true;
  }
  
  public int size() {
    if (!isEmpty()) {
      return getValues().size();
    }
    else {
      return 0;
    }
  }
  
  public String[] getParameterValues() {
    List values = new ArrayList();
    Iterator iter = getValues().iterator();
    while (iter.hasNext()) {
      RepeatingSingleData repeatData = (RepeatingSingleData) iter.next();
      if (!repeatData.isEmpty()) {
        values.addAll(ApiFunctions.safeToList(repeatData.getPrefixValues())) ;
        values.addAll(ApiFunctions.safeToList(repeatData.getRepeatValues())) ;
        values.addAll(ApiFunctions.safeToList(repeatData.getSuffixValues())) ;
      }
    }
    if (!values.isEmpty()) {
      return (String[]) values.toArray(new String[0]);
    }
    else {
      return null;
    }
  }
  
}
