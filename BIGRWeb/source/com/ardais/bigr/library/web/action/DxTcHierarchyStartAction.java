package com.ardais.bigr.library.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.library.web.form.DxTcHierarchyForm;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

public class DxTcHierarchyStartAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    String type = ((DxTcHierarchyForm) form).getType();

    if ("D".equals(type)) {
      request.setAttribute("hierarchy", ((DxTcHierarchyForm) form).getDxHierarchy());
    }
    else if ("T".equals(type)) {
      request.setAttribute("hierarchy", ((DxTcHierarchyForm) form).getTcHierarchy());
    }
    return (mapping.findForward("success"));
  }

}
