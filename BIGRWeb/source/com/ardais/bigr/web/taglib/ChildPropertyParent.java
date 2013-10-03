package com.ardais.bigr.web.taglib;

/**
 * <code>ChildPropertyParent</code> is implemented by tag handlers that
 * can have nested <code>childProperty</code> tags.
 */
public interface ChildPropertyParent {

  /**
   * Adds the child property specified by property to the parent tag handler.
   */
  void addChildProperty(String property);
}
