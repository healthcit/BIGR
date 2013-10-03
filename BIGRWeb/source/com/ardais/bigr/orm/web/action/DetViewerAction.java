package com.ardais.bigr.orm.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.action.BigrForwardAction;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.kc.web.support.KcUiContext;

public class DetViewerAction extends BigrForwardAction {

  protected ActionForward doPerform(BigrActionMapping mapping, BigrActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
      throws Exception {

    // First set the DET Viewer results URL, which is in a tag in this mapping in the 
    // struts-config. 
    KcUiContext context = KcUiContext.getKcUiContext(request);
    context.setDetViewerResultsUrl(request.getContextPath() + mapping.getTag());
    
    // Forward to the main DET Viewer page.
    return super.doPerform(mapping, form, request, response);
  }
}
