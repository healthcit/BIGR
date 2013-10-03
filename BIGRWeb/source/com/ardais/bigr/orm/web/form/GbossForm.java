package com.ardais.bigr.orm.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.gboss.Gboss;

public class GbossForm extends BigrActionForm {

  //holds the Gboss object to display
  private Gboss _gboss;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    _gboss = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
     return null; 
  }

  /**
   * @return
   */
  public Gboss getGboss() {
    return _gboss;
  }

  /**
   * @param dto
   */
  public void setGboss(Gboss gboss) {
    _gboss = gboss;
  }

}
