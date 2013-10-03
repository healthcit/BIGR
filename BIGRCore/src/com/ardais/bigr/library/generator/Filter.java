package com.ardais.bigr.library.generator;

public abstract class Filter {

  private String _keyName;
  private String _ordering;
  private String _multiOperator;
  public abstract String getSqlFragment();
  public abstract boolean validate(BigrLibraryMetaData data);
  private int _hintPriority;
  private String _hintText;
  /**
   * @return
   */
  public String getMultiOperator() {
    return _multiOperator;
  }

  /**
   * @param string
   */
  public void setMultiOperator(String string) {
    _multiOperator = string;
  }


  /**
   * @return
   */
  public String getKeyName() {
    return _keyName;
  }

  /**
   * @return
   */
  public String getOrdering() {
    return _ordering;
  }

  /**
   * @param string
   */
  public void setKeyName(String string) {
    _keyName = string;
  }

  /**
   * @param string
   */
  public void setOrdering(String string) {
    _ordering = string;
  }

  /**
   * @return
   */
  public String getHintText() {
    return _hintText;
  }

  /**
   * @param string
   */
  public void setHintText(String string) {
    _hintText = string;
  }

  /**
   * @return
   */
  public int getHintPriority() {
    return _hintPriority;
  }

  /**
   * @param i
   */
  public void setHintPriority(int i) {
    _hintPriority = i;
  }

}
