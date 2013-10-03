package com.ardais.bigr.query.filters;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class Filters {

  /**
   *  Check all fields for valid values, and validate other conditions on the filter input data.
   *  Gather up BtxActionError classes inside a composite BtxActionErrors container and return it.
   */
  public abstract void validate(BTXDetails btx) ;


}
