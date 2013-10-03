package com.ardais.bigr.query.filters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
public class RepeatingSingleData implements Serializable {

  private String[] _selectedValues;
  private String _filterName;
  private String[] _prefixValues;
  private String[] _repeatValues;
  private String[] _suffixValues;

  public RepeatingSingleData(String[] repeatValues) {
    this(null, repeatValues, null);
  }

  public RepeatingSingleData(String[] prefixValues, String[] repeatValues) {
    this(prefixValues, repeatValues, null);
  }

  public RepeatingSingleData(String[] prefixValues, String[] repeatValues, String[] suffixValues) {
    super();
    _prefixValues = prefixValues;
    _repeatValues = repeatValues;
    _suffixValues = suffixValues;
  }

  /**
   * @return
   */
  public String[] getPrefixValues() {
    return _prefixValues;
  }

  /**
   * @param strings
   */
  public void setPrefixValues(String[] strings) {
    _prefixValues = strings;
  }

  /**
   * @return
   */
  public String[] getRepeatValues() {
    return _repeatValues;
  }

  /**
   * @return
   */
  public String[] getSuffixValues() {
    return _suffixValues;
  }

  /**
   * @param strings
   */
  public void setRepeatValues(String[] strings) {
    _repeatValues = strings;
  }

  /**
   * @param strings
   */
  public void setSuffixValues(String[] strings) {
    _suffixValues = strings;
  }

  public boolean isEmpty() {
    return (
      ApiFunctions.isEmpty(getPrefixValues())
        && ApiFunctions.isEmpty(getRepeatValues())
        && ApiFunctions.isEmpty(getSuffixValues()));
  }

  /**
   * @return
   */
  public String getFilterName() {
    return _filterName;
  }

  /**
   * @return
   */
  public String[] getSelectedValues() {
    return _selectedValues;
  }

  /**
   * @param string
   */
  public void setFilterName(String string) {
    _filterName = string;
  }

  /**
   * @param strings
   */
  public void setSelectedValues(String[] strings) {
    _selectedValues = strings;
  }

  public int prefixSize() {
    if (ApiFunctions.isEmpty(getPrefixValues())) {
      return 0;
    }
    else {
      return getPrefixValues().length;
    }
  }

  public int repeatSize() {
    if (ApiFunctions.isEmpty(getRepeatValues())) {
      return 0;
    }
    else {
      return getRepeatValues().length;
    }
  }

  public int suffixSize() {
    if (ApiFunctions.isEmpty(getSuffixValues())) {
      return 0;
    }
    else {
      return getSuffixValues().length;
    }
  }

}
