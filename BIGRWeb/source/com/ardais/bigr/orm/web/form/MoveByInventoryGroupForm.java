package com.ardais.bigr.orm.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class MoveByInventoryGroupForm extends BigrActionForm {
  
  private String _action;
  private String[] _sampleIds;
  private String _inventoryGroup;
  private String _note;
  private LegalValueSet _inventoryGroupChoices;

  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _action = null;
    _sampleIds = null;
    _inventoryGroup = null;
    _note = null;
    _inventoryGroupChoices = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    // Nothing to validate here.  All validation takes place in the BTX performer.
    return null;
  }

  /**
   * @return
   */
  public String[] getSampleIds() {
    return _sampleIds;
  }

  /**
   * @param strings
   */
  public void setSampleIds(String[] strings) {
    _sampleIds = strings;
  }

  /**
   * @return
   */
  public String getInventoryGroup() {
    return _inventoryGroup;
  }

  /**
   * @param string
   */
  public void setInventoryGroup(String string) {
    _inventoryGroup = string;
  }

  /**
   * @return
   */
  public String getAction() {
    return _action;
  }

  /**
   * @param string
   */
  public void setAction(String string) {
    _action = string;
  }

  /**
   * @return
   */
  public LegalValueSet getInventoryGroupChoices() {
    return _inventoryGroupChoices;
  }

  /**
   * @param set
   */
  public void setInventoryGroupChoices(LegalValueSet set) {
    _inventoryGroupChoices = set;
  }

  /**
   * @return
   */
  public String getNote() {
    return _note;
  }

  /**
   * @param string
   */
  public void setNote(String string) {
    _note = string;
  }
}
