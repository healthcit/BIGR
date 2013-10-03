package com.ardais.bigr.web.taglib;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.ArtsConstants;
import com.gulfstreambio.gboss.GbossValueSet;
/**
 * <code>ProcedureWithOtherTag</code> is a <code>ListWithOtherSupport</code>.
 * Renders HTML <SELECT> element with options for Procedure list, <INPUT> box along with labels.  
 * 
 * @author: Nagaraja Rao
 */
public class ProcedureWithOtherTag extends ListWithOtherSupport {
  /**
   * ProcedureWithOtherTag constructor comment.
   */
  public ProcedureWithOtherTag() {
    super();
    //Initialize Other Procedure code
    setOtherCode(FormLogic.OTHER_PX);

    //URL for popup Procedure list
    setPopUpURL("/BIGR/orm/Dispatch?op=GetLIMSDxTcHierarchy&type=P");

  }
  
  /**
   * Get list of options for Procedure List
   * 
   * @return GbossValueSet
   */
  public GbossValueSet getOptions() {
    return BigrGbossData.getValueSetAlphabetized(ArtsConstants.VALUE_SET_PROCEDURE_HIERARCHY);
  }
}
