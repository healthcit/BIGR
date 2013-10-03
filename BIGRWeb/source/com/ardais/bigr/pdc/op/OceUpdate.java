package com.ardais.bigr.pdc.op;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.btx.BTXDetailsUpdateComputedData;
import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.javabeans.DataComputationRequestData;
import com.ardais.bigr.pdc.helpers.OceHelper;
import com.ardais.bigr.pdc.javabeans.OceData;
import com.ardais.bigr.pdc.javabeans.OceRowData;
import com.ardais.bigr.pdc.oce.Oce;
import com.ardais.bigr.pdc.oce.OceHome;
import com.ardais.bigr.pdc.oce.util.OceUtil;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;

/**
 * @author Nagraj
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OceUpdate extends StandardOperation {

  /**
   * Creates OceUpdate.
   * @param req
   * @param res
   * @param ctx
   */
  public OceUpdate(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }

  /**
   * @see com.ardais.bigr.pdc.op.StandardOperation#invoke()
   */
  public void invoke() {
    HttpSession session = request.getSession(false);
    Map clonedMap = new HashMap();
    Map nonClonedMap = new HashMap();
    List list = new ArrayList();
    OceRowData rowData;
    OceHelper helper = new OceHelper(request);
    OceData databean = helper.getDataBean();
    databean.setUser((String) session.getAttribute("user"));

    //get number of rows to be from request.
    int rowCount = ApiFunctions.safeInteger(request.getParameter("rowCount"), 0);
    for (int i = 0; i < rowCount; i++) {
      //if update check box is checked read the parameters of that row.
      if (request.getParameter("update" + i) != null) {
        rowData = new OceRowData();
        rowData.setLineId(request.getParameter("otherLineId" + i));
        rowData.setClone(request.getParameter("clone" + i) != null);
        rowData.setOtherText(request.getParameter("otherText" + i));
        String artsCode = request.getParameter("list" + i);
        //make sure arts code is not empty.
        if (!ApiFunctions.isEmpty(artsCode)) {
          rowData.setArtsCode(artsCode);
          //list.add(rowData);
          addToMaps(rowData, clonedMap, nonClonedMap);
        }
      }
      //if hold check box is checked read the parameters of that row
      else if (request.getParameter("hold" + i) != null) {
        rowData = new OceRowData();
        rowData.setLineId(request.getParameter("otherLineId" + i));
        rowData.setClone(request.getParameter("clone" + i) != null);
        rowData.setOtherText(request.getParameter("otherText" + i));
        rowData.setStatus(OceUtil.OCE_STATUS_HOLD_IND);
        //list.add(rowData);
        addToMaps(rowData, clonedMap, nonClonedMap);
      }
      //if Inappropriate check box is checked read the parameters of that row
      else if (request.getParameter("inapp" + i) != null) {
        rowData = new OceRowData();
        rowData.setLineId(request.getParameter("otherLineId" + i));
        rowData.setClone(request.getParameter("clone" + i) != null);
        rowData.setOtherText(request.getParameter("otherText" + i));
        rowData.setStatus(OceUtil.OCE_STATUS_INAPP_IND);
        //list.add(rowData);
        addToMaps(rowData, clonedMap, nonClonedMap);
      }
      //if Concept check box is checked read the parameters of that row
      else if (request.getParameter("concept" + i) != null) {
        rowData = new OceRowData();
        rowData.setLineId(request.getParameter("otherLineId" + i));
        rowData.setClone(request.getParameter("clone" + i) != null);
        rowData.setOtherText(request.getParameter("otherText" + i));
        rowData.setStatus(OceUtil.OCE_STATUS_CONCEPT_IND);
        //list.add(rowData);
        addToMaps(rowData, clonedMap, nonClonedMap);
      }

    }
    //add the entries from both maps to our list
    Iterator iterator = clonedMap.values().iterator();
    while (iterator.hasNext()) {
      list.add(iterator.next());
    }
    //the non-cloned map is a map of key-list pairs, so for
    //each entry in that map iterate over the elements in the list
    iterator = nonClonedMap.values().iterator();
    while (iterator.hasNext()) {
      List entries = (List) iterator.next();
      Iterator entriesIterator = entries.iterator();
      while (entriesIterator.hasNext()) {
        list.add(entriesIterator.next());
      }
    }

    //set the list into the databean we're going to pass to the OceBean, which will
    //do the actual work of updating the database.
    databean.setList(list);

    //invoke the OceBean to update the database.  If any pathology evaluations were updated,
    //recompute the external and internal comments since the OCE data changes should be
    //reflected in that data.
    try {
      OceHome home = (OceHome) EjbHomes.getHome(OceHome.class);
      Oce oceBean = home.create();
      databean = oceBean.updateOce(databean);

      /* If any pathology evaluations features were updated, we need to recompute the
       * internal and external comments on those evaluations.  The text for the new ARTS
       * code could be (and likely is) different from the text for the "other" value that used
       * to be on the evaluation, and that new text needs to be reflected in the comments. */
      if ((!ApiFunctions.isEmpty(databean.getIdList())) && (databean.getIdList().size() > 0)) {
        BTXDetailsUpdateComputedData btxDetails = new BTXDetailsUpdateComputedData();
        DataComputationRequestData requestedData = new DataComputationRequestData();
        requestedData.setIds(databean.getIdList());
        requestedData.setObjectType(Constants.COMPUTED_DATA_OBJECT_TYPE_EVALUATION);
        //set fields to compute
        List fields = new ArrayList();
        fields.add(Constants.COMPUTED_DATA_EVAL_FIELD_COMMENTS);
        requestedData.setFieldsToCompute(fields);
        btxDetails.setBeginTimestamp(btxDetails.getBeginTimestamp());
        btxDetails.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
        Vector requests = new Vector();
        requests.add(requestedData);
        btxDetails.setDataComputationRequests(requests);
        btxDetails.setTransactionType("bigr_update_computed_data");
        Btx.perform(btxDetails);
      }
    }
    catch (Exception ex) {
      ApiLogger.log(ex);
      throw new ApiException(ex);
    }

    //show the user the results of the updates
    helper = new OceHelper(databean);
    request.setAttribute("oceHelper", helper);
    forward("/hiddenJsps/ddc/oce/OceUpdateSummary.jsp");
  }

  public void preInvoke(Collection fileItems) {
    //method for potential file attachment, do nothing for now ;
    
  }
  
  private void addToMaps(OceRowData rowData, Map clonedMap, Map nonClonedMap) {
    //determine if this rowData should be added to either the cloned or uncloned maps
    String key = rowData.getOtherText().trim().toUpperCase();
    //if this action is to be cloned, if it's already in the clone map ignore it
    //since David wants the first clone action to be the one we use.  Otherwise,
    //add it to the clone map and remove it from the nonClone map if it's in there
    //since the clone action should override the nonClone action
    if (rowData.isClone()) {
      OceRowData existingRowData = (OceRowData) clonedMap.get(key);
      if (existingRowData == null) {
        clonedMap.put(key, rowData);
        nonClonedMap.remove(key);
      }
    }
    //if this action is not to be cloned, add it to the nonClone map only if
    //it's not already in the clonedMap, since if it's in the clone map that
    //action should override a nonClone action.  The nonClonedMap must be a
    //key - list pair, since we could have multiple rows with the same key that
    //need to be handled.  So, see if there's an entry in the nonClonedMap for
    //the key - if there is then just add this rowData to that list.  If there's
    //not, create a new list and add it to the nonClonedMap.
    else {
      OceRowData existingRowData = (OceRowData) clonedMap.get(key);
      if (existingRowData == null) {
        List previousMatchingRowDatas = (List)nonClonedMap.get(key);
        if (previousMatchingRowDatas != null) {
          previousMatchingRowDatas.add(rowData);
        }
        else {
          previousMatchingRowDatas = new ArrayList();
          previousMatchingRowDatas.add(rowData);          
          nonClonedMap.put(key, previousMatchingRowDatas);
        }
      }
    }
  }

}
