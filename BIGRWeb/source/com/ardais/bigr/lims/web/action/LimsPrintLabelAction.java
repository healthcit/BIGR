package com.ardais.bigr.lims.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * 
 */
public class LimsPrintLabelAction extends BigrAction {

  public static final String LABEL_INFO_SMALL_LABEL_VOID = "void";

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

    List labelDescriptions = new ArrayList();
    String printAll = request.getParameter("printAll");
    String printSingle = request.getParameter("printSingle");

    if (!ApiFunctions.isEmpty(printAll)) {
      String[] sampleIds = request.getParameterValues("sampleIdList");
      String[] asmPositions = request.getParameterValues("asmPositionList");

      if ((sampleIds != null) && (asmPositions != null)) {
        for (int i = 0; i < sampleIds.length; i++) {
          Map labelInfo = new HashMap();

          String sampleId = sampleIds[i].trim();
          labelInfo.put(ApiFunctions.LABEL_INFO_SAMPLE_ID, sampleId);

          // If the sample format is PARAFFIN then set the small label
          // with the asm position. If the sample formate is FROZEN then
          // set the small label with the last 4 characters of the sample id.
          if (sampleId.startsWith(ValidateIds.PREFIX_SAMPLE_PARAFFIN)) {
            labelInfo.put(ApiFunctions.LABEL_INFO_SMALL_LABEL, asmPositions[i].trim());
          }
          else if (sampleId.startsWith(ValidateIds.PREFIX_SAMPLE_FROZEN)) {
            int beginIndex = sampleId.length() - 4;
            labelInfo.put(ApiFunctions.LABEL_INFO_SMALL_LABEL, sampleId.substring(beginIndex));
          }
          else {
            labelInfo.put(ApiFunctions.LABEL_INFO_SMALL_LABEL, LABEL_INFO_SMALL_LABEL_VOID);
          }
          labelDescriptions.add(labelInfo);
        }
      }
    }
    else if (!ApiFunctions.isEmpty(printSingle)) {

      String sampleId = printSingle.trim();
      String asmPosition = request.getParameter("asmPosition");

      Map labelInfo = new HashMap();
      labelInfo.put(ApiFunctions.LABEL_INFO_SAMPLE_ID, sampleId);

      // If the sample format is PARAFFIN then set the small label
      // with the asm position. If the sample formate is FROZEN then
      // set the small label with the last 4 characters of the sample id.
      if (sampleId.startsWith(ValidateIds.PREFIX_SAMPLE_PARAFFIN)) {
        labelInfo.put(ApiFunctions.LABEL_INFO_SMALL_LABEL, asmPosition.trim());
      }
      else if (sampleId.startsWith(ValidateIds.PREFIX_SAMPLE_FROZEN)) {
        int beginIndex = sampleId.length() - 4;
        labelInfo.put(ApiFunctions.LABEL_INFO_SMALL_LABEL, sampleId.substring(beginIndex));
      }
      else {
        labelInfo.put(ApiFunctions.LABEL_INFO_SMALL_LABEL, LABEL_INFO_SMALL_LABEL_VOID);
      }
      labelDescriptions.add(labelInfo);
    }

    //Send list of samples to be printed to printer.
    if (!labelDescriptions.isEmpty()) {
      String status =
        ApiFunctions.printLabels(
          ApiFunctions.SAMPLE_LABEL_PRINTER,
          ApiFunctions.SAMPLE_FILENAME_PREFIX,
          labelDescriptions,
          ApiFunctions.SAMPLE_LABEL_TYPE);

      if (status.equalsIgnoreCase(ApiFunctions.LABELPRINTING_FAIL)) {
        ActionErrors errors = new ActionErrors();
        ActionError error = new ActionError("lims.message.printSample.failed");
        errors.add("inputError", error);

        request.setAttribute(Globals.ERROR_KEY, errors);
      }

    }

    return (mapping.findForward(LimsConstants.BTX_ACTION_FORWARD_SUCCESS));
  }
}
