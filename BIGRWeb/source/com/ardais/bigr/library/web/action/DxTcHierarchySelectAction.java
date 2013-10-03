package com.ardais.bigr.library.web.action;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.library.web.form.DxTcHierarchyForm;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.web.action.BigrAction;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.ardais.bigr.library.web.helper.ResultsHelper;

public class DxTcHierarchySelectAction extends BigrAction {

  /**
   * Sets two values in the session:  (id).selected and (id).label, where (id) is any identifier
   * passed in here. <p>
   * 
   * Input is in the form of request parameters:
   * 
   * clear - true if we clear the form.  
   * 
   * id - where to put the results in the session for later retrieval (id.selected and id.label).
   *        Also the jsp will look for this id in the request and use it to figure out what control 
   *        to update with the diagnosis display list.
   * 
   * type - "D": diagnosis, "T": tissue.
   * 
   * process - "true" means to store the results into the session, empty means to not store.
   * 
   * <p><p>
   * 
   * Effectively, a sparse two dimensional array is passed in by putting String[] values at 
   * some attributes representing positions in a (conceptual) array.
   * 
   * i - the width of the sparse array.
   * j - the height of the sparse array.
   * number_number - a String[] if there is data for this row, col in the sparse array.
   * (for example 3_9 might hold an array like this:   
   *    <code> String[] {"126736007^33377007^",  "126736007^33377007^"} </code>.
   * 
   */
  protected ActionForward doPerform(
    BigrActionMapping mapping,
    BigrActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    String clear = ((DxTcHierarchyForm) form).getClear(); // anything but empty means clear
    String id = ((DxTcHierarchyForm) form).getId(); // which value set (builds session key)
    String type = ((DxTcHierarchyForm) form).getType(); // "D" - diagnosis, "T" - Tissue
    String txType = ((DxTcHierarchyForm) form).getTxType(); // what tx (builds session key)
    String process = ((DxTcHierarchyForm) form).getProcess();  // "true" or empty - toggles setting vals in session

    Hashtable codeHash = null;
    codeHash = (Hashtable)request.getSession().getAttribute(id + ".selected." + txType);
    if(codeHash == null) codeHash = new Hashtable(); 
    ArrayList labelList = null;
    labelList = (ArrayList)request.getSession().getAttribute(id + ".label." + txType);
    if(labelList == null) labelList = new ArrayList();
   

    // clear -- remove both values from session, and set whole hierarchy in request
    if (!ApiFunctions.isEmpty(clear)) {
      
      //clear the filterMap in resultHelper if it exist (if the user open "View Query Summary", fileter map will be generated)
      ResultsHelper rh = ResultsHelper.get(txType, request);
      Map m = rh.getFilterMap();
      
      if(m != null && !m.isEmpty()) rh.setFilters(new Hashtable());
      
        request.getSession().removeAttribute(id + ".selected." + txType);
        request.getSession().removeAttribute(id + ".label." + txType);
      if ("D".equals(((DxTcHierarchyForm) form).getType())) {
        request.setAttribute(
          "hierarchy",
          ((DxTcHierarchyForm) form).getDxHierarchy());
      } else if ("T".equals(((DxTcHierarchyForm) form).getType())) {
        request.setAttribute(
          "hierarchy",
          ((DxTcHierarchyForm) form).getTcHierarchy());
      }
      return (new ActionForward(mapping.getInput()));
    }

    Integer intI =
      new Integer(
        (request.getParameter("i") != null) ? request.getParameter("i") : "0");
    Integer intJ =
      new Integer(
        (request.getParameter("j") != null) ? request.getParameter("j") : "0");

    // set up the code hashtable, based on values of i and j
    if (intJ.intValue() > 0) { // has j set as well as i?
      for (int i = 0; i < intI.intValue(); i++) {
        for (int j = 0; j < intJ.intValue(); j++) {
          String codes[] = request.getParameterValues(i + "_" + j);
          if (codes != null) {
            for (int k = 0; k < codes.length; k++) {
              codeHash.put(codes[k], codes[k]);  // accumulate all matching codes 
            
            }
          }
        }
      }
    } else { // only i is set
      for (int i = 0; i < intI.intValue(); i++) {
        String codes[] = request.getParameterValues(i + "");
        if (codes != null) {
          for (int k = 0; k < codes.length; k++) {
            codeHash.put(codes[k], codes[k]);

          }
        }
      }
    }

    // set up labelList with descriptions based on parameters i and j
    if (intJ.intValue() > 0) {
      for (int i = 0; i < intI.intValue(); i++) {
        for (int j = 0; j < intJ.intValue(); j++) {
          String codes[] = request.getParameterValues(i + "_" + j);
          if (request.getParameter(i + "_" + j + "_p") != null) {
            if(!labelList.contains(request.getParameter(i + "_" + j + "_p")))
            labelList.add(request.getParameter(i + "_" + j + "_p"));
          } else if (codes != null) {
            for (int k = 0; k < codes.length; k++) {
              if (type.equals("D")) {
                if(!labelList.contains(BigrGbossData.getDiagnosisDescription(codes[k])))
                labelList.add(BigrGbossData.getDiagnosisDescription(codes[k]));
              } else {
                if(!labelList.contains(BigrGbossData.getTissueDescription(codes[k])))
                labelList.add(BigrGbossData.getTissueDescription(codes[k]));
              }
            }
          }
        }
      }
    } else {
      for (int i = 0; i < intI.intValue(); i++) {
        String codes[] = request.getParameterValues(i + "");
        if (request.getParameter(i + "_p") != null) {
          if(!labelList.contains(request.getParameter(i + "_p")))
            labelList.add(request.getParameter(i + "_p"));
        } else if (codes != null) {
          for (int k = 0; k < codes.length; k++) {
            if (type.equals("D")) {
              if(!labelList.contains(BigrGbossData.getDiagnosisDescription(codes[k])))
                labelList.add(BigrGbossData.getDiagnosisDescription(codes[k]));
            } else {
              if(!labelList.contains(BigrGbossData.getTissueDescription(codes[k])))
                labelList.add(BigrGbossData.getTissueDescription(codes[k]));
            }
          }
        }
      }
    }
      
    if (!ApiFunctions.isEmpty(process)) {
        request.getSession().setAttribute(id + ".selected." + txType, codeHash);
        request.getSession().setAttribute(id + ".label." + txType, labelList);
    }

    return (mapping.findForward("success"));
  }
}
