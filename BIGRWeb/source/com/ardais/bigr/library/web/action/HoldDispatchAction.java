package com.ardais.bigr.library.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.helpers.RequestType;
import com.ardais.bigr.library.SampleViewData;
import com.ardais.bigr.library.web.form.ResultsForm;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.ardais.bigr.web.servlet.BigrRequestProcessor;

public class HoldDispatchAction extends BigrAction {

  // The values of the action constants must match up to names of ActionForward targets
  // on the /library/holdDispatch action in struts-config.xml.
  //
  private static final String ACTION_HOLD_REMOVE = "holdRemove";
  private static final String ACTION_REQUEST_ITEMS = "requestItems";

  private static final DynaClass HOLD_REMOVE_PARAMS_CLASS =
    new BasicDynaClass(
      "HoldRemoveParams",
      null,
      new DynaProperty[] {
        new DynaProperty("txType", String.class),
        new DynaProperty("samples", String[].class)});

  private static final DynaClass REQUEST_ITEMS_PARAMS_CLASS =
    new BasicDynaClass(
      "RequestItemsParams",
      null,
      new DynaProperty[] {
        new DynaProperty("txType", String.class),
        new DynaProperty("requestType", String.class),
        new DynaProperty("samples", String[].class)});

  /**
   * @see com.ardais.bigr.web.action.BigrAction#doPerform(BigrActionMapping, BigrActionForm, HttpServletRequest, HttpServletResponse)
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    ResultsForm resultsForm = (ResultsForm) form;

    String txType = resultsForm.getTxType();
    String action = resultsForm.getAction();

    DynaBean populatorSource;

    if (ACTION_HOLD_REMOVE.equals(action)) {
      populatorSource = HOLD_REMOVE_PARAMS_CLASS.newInstance();
      populatorSource.set("txType", txType);

      // Removing things from a hold list applies across categories in a multi-category hold list.
      // The name of the request parameter for the selected items is different for each category:
      //   - cAi for the Ardais category
      //   - cBAi for the "BMS Available" category
      //   - cBUi for the "BMS Unavailable" category
      // The fourth category, "BMS Pending", is ignored here since items can't be removed from
      // that category.

      String[] cAi = ApiFunctions.safeStringArray(request.getParameterValues("cAi"));
      String[] cBAi = ApiFunctions.safeStringArray(request.getParameterValues("cBAi"));
      String[] cBUi = ApiFunctions.safeStringArray(request.getParameterValues("cBUi"));
      String[] items = new String[cAi.length + cBAi.length + cBUi.length];
      System.arraycopy(cAi, 0, items, 0, cAi.length);
      System.arraycopy(cBAi, 0, items, cAi.length, cBAi.length);
      System.arraycopy(cBUi, 0, items, cAi.length + cBAi.length, cBUi.length);

      populatorSource.set("samples", items);
    }
    else if (ACTION_REQUEST_ITEMS.equals(action)) {
      populatorSource = REQUEST_ITEMS_PARAMS_CLASS.newInstance();
      populatorSource.set("txType", txType);
      populatorSource.set("requestType", RequestType.RESEARCH.toString());
      populatorSource.set("samples", getRequestedItemIds(request));
    }
    else {
      throw new ApiException("Unsupported action: " + action);
    }

    request.setAttribute(BigrRequestProcessor.ACTION_FORM_POPULATOR_SOURCE_KEY, populatorSource);

    return mapping.findForward(action);
  }

  /**
   * Return an array of item ids corresponding to the items in the
   * "BMS Available" hold list category.  All of the items in this category are included
   * in the resulting array, not just the items whose checkboxes are checked.  (Currently, the
   * checkboxes are only used to indicate which items should be removed from the hold list
   * when the "Remove Selected Items" button is clicked.)
   * 
   * <p>The request parameter named "allItemsholdCatBmsAvailView" must contain a comma-separated
   * list of the ids of all of the items in the "BMS Available" category, in the order
   * that the items are to appear in the resulting item id array.  The ids must be
   * separated only by commas (for example, no whitespace).
   * 
   * @param request The HTTP request.
   * @return The array of item ids.
   */
  private String[] getRequestedItemIds(HttpServletRequest request) {
    String allItemsParameterName = SampleViewData.ALL_ITEMS_PREFIX + "holdCatBmsAvailView";
    String itemIdsString = ApiFunctions.safeString(request.getParameter(allItemsParameterName));
    return StringUtils.split(itemIdsString, ',');
  }

}
