package com.ardais.bigr.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Used to create simple lists when processing the systemConfiguration.xml document.  This class
 * is not intended to be used for any other purpose.
 */
public class SimpleList {

  private String _name;
  private List _items;
  private boolean _completed = false;

  public SimpleList() {
    super();
  }

  public String getName() {
    return _name;
  }

  public void setName(String name) {
    _name = name;
  }

  public List getListItems() {
    if (_items == null) {
      _items = new ArrayList();
    }
    return _items;
  }

  public void addListItem(String item) {
    checkNotCompleted();
    getListItems().add(item);
  }

  /**
   * Throw an exception if either the list is incomplete
   * ({@link #setCompleted()} has not yet been called) or is complete
   * but invalid (for example, it has no name or concepts).
   */
  private void checkCompleted() {
    if (!isCompleted()) {
      throw new IllegalStateException("Method cannot be called prior to calling setCompleted().");
    }
  }

  /**
   * Throw an exception if the list is complete
   * ({@link #setCompleted()} has already been called).
   */
  private void checkNotCompleted() {
    if (isCompleted()) {
      throw new IllegalStateException("Method can only be called prior to calling setCompleted().");
    }
  }
  
  /**
   * Returns true if the list has been marked as complete by calling the
   * {@link #setCompleted() method}.
   * @return the completed flag
   */
  public boolean isCompleted() {
    return _completed;
  }

  /**
   * Sets the completed flag to true.  Once a list has been marked 
   * as complete, it cannot be set back to incomplete.
   */
  public void setCompleted() {
    _completed = true;
    _items = Collections.unmodifiableList(getListItems());
  }

  /**
   * Get an iterator over the items in the list
   */
  public Iterator iterator() { 
    return _items.iterator();    
  }

  /**
   * Get the number of items in the list
   */
  public int getSize() {    
    return _items.size();    
  }
}
