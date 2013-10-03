package com.ardais.bigr.web.taglib;

import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.ArtsConstants;
import com.gulfstreambio.gboss.GbossValueSet;
/**
 * <code>TissueWithOtherTag</code> is a <code>ListWithOtherSupport</code>.
 * Renders HTML <SELECT> element with options for Tissue list, <INPUT> box along with labels.  
 * 
 * @author: Nagaraja Rao
 */
public class TissueWithOtherTag extends ListWithOtherSupport {
  /**
   * TissueWithOtherTag constructor comment.
   */
  public TissueWithOtherTag() {
    super();

    //Initialize Other Tissue code
    setOtherCode(FormLogic.OTHER_TISSUE);

    //URL for popup Tissue list
    setPopUpURL("/BIGR/orm/Dispatch?op=GetLIMSDxTcHierarchy&type=T");
  }
  
  /**
   * Get list of options for Tissue List
   * 
   * @return GbossValueSet
   */
  public GbossValueSet getOptions() {
    return BigrGbossData.getValueSetAlphabetized(ArtsConstants.VALUE_SET_TISSUE_HIERARCHY);
  }
}
