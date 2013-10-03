package com.ardais.bigr.lims.web.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.javabeans.IdList;
import com.ardais.bigr.lims.beans.LimsOperation;
import com.ardais.bigr.lims.beans.LimsOperationHome;
import com.ardais.bigr.lims.btx.BTXDetailsPrintSlides;
import com.ardais.bigr.lims.javabeans.SlideData;
import com.ardais.bigr.lims.web.form.LimsLabelGenForm;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * 
 */
public class LimsPrintSlideLabelAction extends BigrAction {

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, 
   * BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    LimsLabelGenForm labelForm = (LimsLabelGenForm) form;

    List labelDescriptions = new ArrayList();
    String[] slides = labelForm.getSlideLabelList();
    String[] hasBeenPrinted = labelForm.getHasBeenPrinted();
    IdList slidesList = new IdList();

    LimsOperation operation = null;
    SlideData databean = null;
    //Check for Print All slides
    if (!ApiFunctions.isEmpty(labelForm.getPrintAllSlides())) {
      if (slides != null) {
        for (int i = 0; i < slides.length; i++) {
          Map labelInfo = new HashMap();
          if (operation == null) {
            LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
            operation = home.create();
          }
          databean = operation.getPrintLabelData(slides[i]);
          labelInfo.put("slide", databean.getSlideId());
          labelInfo.put("block", databean.getSampleId());
          labelInfo.put("slideNumber", String.valueOf(databean.getSlideNumber()));
          labelInfo.put("asmPos", databean.getSampleASMPosition());
          labelInfo.put("caseid", databean.getCaseId());
          labelDescriptions.add(labelInfo);

          hasBeenPrinted[i] = "true";
          //Add to slides list
          slidesList.add(slides[i]);
        }
        labelForm.setPrintAllSlides(null);
      }

    } //Check for print one slide
    else if (!ApiFunctions.isEmpty(labelForm.getPrintOneSlide())) {
      //Find the index of slide to be printed
      for (int i = 0; i < slides.length; i++) {
        if (labelForm.getPrintOneSlide().equals(slides[i])) {
          hasBeenPrinted[i] = "true";
          //Add to slides list.
          slidesList.add(slides[i]);
          break;
        }
      }
      if (operation == null) {
        LimsOperationHome home = (LimsOperationHome) EjbHomes.getHome(LimsOperationHome.class);
        operation = home.create();
      }
      databean = operation.getPrintLabelData(labelForm.getPrintOneSlide());
      Map labelInfo = new HashMap();
      labelInfo.put("slide", databean.getSlideId());
      labelInfo.put("block", databean.getSampleId());
      labelInfo.put("slideNumber", String.valueOf(databean.getSlideNumber()));
      labelInfo.put("asmPos", databean.getSampleASMPosition());
      labelInfo.put("caseid", databean.getCaseId());
      labelDescriptions.add(labelInfo);

    }

    //Send list of slides to be printed to printer.
    if (labelDescriptions.size() > 0) {
      String success = ApiFunctions.printLabels(labelDescriptions);
      //Check label printing status pass/fail
      //While printing label for slides ZPL code for all the slides is written
      //into slide_label_slidefro.txt(FROZEN) or 
      //slide_label_slideprn.txt(PARAFFIN). UNIX print command 
      //"lp -d printername filename" is executed only once per each file.
      //It is rare case "lp -d" throws some IO errors. 
      //If any of labels not printed correctly, send message. success message
      //will be like passpass/passfail/failpass/failfail.
      if ((success != null)
        && (StringUtils.countMatches(success, ApiFunctions.LABELPRINTING_FAIL) > 0)) {
        labelForm.setMessage(getResources(request).getMessage("lims.message.printSlides.failed"));
      }
      //Assuming all labels printed correctly.
      else {
        //Call BTX framework to log printing info.
        BTXDetailsPrintSlides printSlides = new BTXDetailsPrintSlides();
        printSlides.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
        printSlides.setLimsUserId(labelForm.getLimsUserId());
        printSlides.setSlidesList(slidesList);
        printSlides.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
        printSlides.setTransactionType("lims_print_slide_label");
        printSlides =
          (BTXDetailsPrintSlides) Btx.perform(printSlides);
        //Construct message for single slide
        String message = null;
        if ((labelDescriptions.size() == 1)
          && (!ApiFunctions.isEmpty(labelForm.getPrintOneSlide()))) {
          message =
            getResources(request).getMessage(
              "lims.message.printSlidesSingle.printed",
              labelForm.getPrintOneSlide());
        }
        else {
          //Construct message for more than one slide
          message =
            getResources(request).getMessage(
              "lims.message.printSlides.printed",
              String.valueOf(labelDescriptions.size()));
        }
        //Set confirmation message form bean
        labelForm.setMessage(message);
      }
    }

    return (mapping.findForward("success"));
  }

}
