package com.ardais.bigr.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.web.form.BigrActionForm;

/**
 * A BIGR action that simply forwards to the context-relative URI specified by the parameter
 * attribute of the associated action mapping.  This is analagous to the Struts ForwardAction,
 * but extends BigrAction and thus inherits all of its functionality.
 */
public class BigrForwardAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(com.ardais.bigr.web.action.BigrActionMapping, com.ardais.bigr.web.form.BigrActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    return new ActionForward(mapping.getParameter());
  }

}
