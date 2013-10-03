/*
 * Created on Nov 26, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.library.generator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sislam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Filters {

  private List _filters = new ArrayList();


  public void addFilter(Object filter) {
    _filters.add(filter);  
  }


  /**
   * @return
   */
  public List getFilters() {
    return _filters;
  }}
