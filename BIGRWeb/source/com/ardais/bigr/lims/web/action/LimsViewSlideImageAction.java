package com.ardais.bigr.lims.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.lims.javabeans.ImageData;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * 
 */
public class LimsViewSlideImageAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    ImageData image = new ImageData();
    image.setImageFilename(request.getParameter("imageFilename"));
    image.setMagnification(request.getParameter("magnification"));
    image.setSlideId(request.getParameter("slideId"));

    request.setAttribute("image", image);

    return (mapping.findForward("success"));
  }

}
