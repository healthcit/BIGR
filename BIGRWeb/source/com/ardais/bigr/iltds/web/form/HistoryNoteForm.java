package com.ardais.bigr.iltds.web.form;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.BTXDetailsHistoryNote;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * Struts ActionForm class for a box layout.
 */
public class HistoryNoteForm extends BigrActionForm {

  private String _note;
  private String[] _objectIds;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {

    _note = null;
    _objectIds = null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {

    String myTag = ApiFunctions.safeString(mapping.getTag());
    // If the tag on the action = "AddHistoryNoteSuccess", then we've successfully
    // added a history notee and now want to return to an empty Add History Note page (so
    // the user can add another history note without having to erase the data from
    // the history note we just created).  So, reset the form.  We can also skip 
    // validation since there is nothing to validate in this case 
    if ("ResetForm".equalsIgnoreCase(myTag)) {
      doReset(mapping,request);
    }

    // Nothing to validate here.
    return null;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void describeIntoBtxDetails(
    BTXDetails btxDetails0,
    BigrActionMapping mapping,
    HttpServletRequest request) {

    super.describeIntoBtxDetails(btxDetails0, mapping, request);

    BTXDetailsHistoryNote btx = (BTXDetailsHistoryNote) btxDetails0;
    
    Set involvedObjects = new HashSet();
    if (!ApiFunctions.isEmpty(_objectIds)) {
      for (int i = 0; i < _objectIds.length; i++) {
        involvedObjects.add(_objectIds[i]);
      }
    }
    btx.setInvolvedObjects(involvedObjects);
  }

  /**
   * @return
   */
  public String getNote() {
    return _note;
  }

  /**
   * @return
   */
  public String[] getObjectIds() {
    return _objectIds;
  }

  /**
   * @param string
   */
  public void setNote(String string) {
    _note = string;
  }

  /**
   * @param strings
   */
  public void setObjectIds(String[] strings) {
    _objectIds = strings;
  }
}
