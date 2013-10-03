package com.ardais.bigr.web.taglib;

import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.ArtsConstants;
import com.gulfstreambio.gboss.GbossValueSet;
//import com.ardais.bigr.iltds.helpers;
/**
 * <code>DiagnosisWithOtherTag</code> is a <code>ListWithOtherSupport</code>. 
 * Renders HTML <SELECT> element with options for Diagnosis list, <INPUT> box along with labels.  
 * 
 * @author: Nagaraja Rao
 */
public class DiagnosisWithOtherTag extends ListWithOtherSupport {
  /**
   * Creates new <code>DiagnosisWithOtherTag</code>.
   */

  public DiagnosisWithOtherTag() {
    super();

    //Initialize Other Diagnosis code
    setOtherCode(FormLogic.OTHER_DX);

    //URL for popup Diagnosis list
    setPopUpURL("/BIGR/orm/Dispatch?op=GetLIMSDxTcHierarchy&type=D");

  }
  
  /**
   * Get list of options for Diagnosis List
   * 
   * @return GbossValueSet
   */
  public GbossValueSet getOptions() {
    return BigrGbossData.getValueSetAlphabetized(ArtsConstants.VALUE_SET_DIAGNOSIS_HIERARCHY);
  }
}
