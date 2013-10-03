package com.ardais.bigr.iltds.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.iltds.beans.BoxlocationAccessBean;
import com.ardais.bigr.iltds.beans.BoxlocationKey;
import com.ardais.bigr.iltds.beans.GeolocationKey;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.util.EjbHomes;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;
/**
 * Insert the type's description here.
 * Creation date: (2/13/01 11:26:05 AM)
 * @author: Jake Thompson
 */
public class OverrideBoxLocInsert extends com.ardais.bigr.iltds.op.StandardOperation {
  /**
   * OverrideBoxLocInsert constructor comment.
   * @param req javax.servlet.http.HttpServletRequest
   * @param res javax.servlet.http.HttpServletResponse
   * @param ctx javax.servlet.ServletContext
   */
  public OverrideBoxLocInsert(
    HttpServletRequest req,
    HttpServletResponse res,
    ServletContext ctx) {
    super(req, res, ctx);
  }
  /**
   * Insert the method's description here.
   * Creation date: (2/13/01 11:26:05 AM)
   */
  public void invoke() throws Exception, IOException {
    String newLocation = request.getParameter("loc");
    String boxID = request.getParameter("boxID");
    if ((boxID == null) || !FormLogic.validBoxID(boxID) || boxID.equals("")) {
      retry("Invalid Box Id");
      return;
    }

    String room = request.getParameter("roomlist").trim();
    String freezer = request.getParameter("freezerlist").trim();
    String drawer = request.getParameter("drawerlist").trim();
    String slot = request.getParameter("slotlist").trim();
    String storageTypeCid = request.getParameter("storageTypeCid").trim();
    BoxlocationAccessBean boxLoc = null;
    String update = request.getParameter("update");

    boolean notFinished = false;

    request.setAttribute("storageTypeCid", storageTypeCid);
    if (room == null || room.equals("")) {
      try {
        ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
        ListGenerator list = home.create();
        //We have to get the room list.  shouldn't happen, but just in case.
        request.getSession(false).setAttribute(
          "roomlist",
          list.findNextLocationDropDown(newLocation, null, null, null, storageTypeCid));
        request.getSession(false).removeAttribute("freezerlist");
        request.getSession(false).removeAttribute("drawerlist");
        request.getSession(false).removeAttribute("slotlist");
      }
      catch (Exception e) {
        retry("Error obtaining room list.");
        return;
      }
      notFinished = true;
    }
    else if ((update != null && update.equals("room")) || freezer == null || freezer.equals("")) {
      try {
        //we have to get the storage unit list
        ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
        ListGenerator list = home.create();
        request.getSession(false).setAttribute(
          "freezerlist",
          list.findNextLocationDropDown(newLocation, room, null, null, storageTypeCid));
        request.getSession(false).removeAttribute("drawerlist");
        request.getSession(false).removeAttribute("slotlist");
      }
      catch (Exception e) {
        retry("Error obtaining storage unit list.");
        return;
      }
      notFinished = true;
    }
    else if ((update != null && update.equals("freezer")) || drawer == null || drawer.equals("")) {
      try {
        //We have to get the drawer list
        ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
        ListGenerator list = home.create();
        request.getSession(false).setAttribute(
          "drawerlist",
          list.findNextLocationDropDown(newLocation, room, freezer, null, storageTypeCid));
        request.getSession(false).removeAttribute("slotlist");
      }
      catch (Exception e) {
        retry("Error obtaining drawer list.");
        return;
      }
      notFinished = true;
    }
    else if ((update != null && update.equals("drawer")) || slot == null || slot.equals("")) {
      //We need to get the slot list.   
      try {
        ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
        ListGenerator list = home.create();
        request.getSession(false).setAttribute(
          "slotlist",
          list.findNextLocationDropDown(newLocation, room, freezer, drawer, storageTypeCid));
      }
      catch (Exception e) {
        retry("Error obtaining slot list.");
        return;
      }
      notFinished = true;
    }

    //if we have a value for all 5 fields, set the location.
    if (!notFinished) {
      try {
        boxLoc =
          new BoxlocationAccessBean(
            new BoxlocationKey(drawer, room, slot, freezer, new GeolocationKey(newLocation)));

      }
      catch (Exception e) {
      }
      //the location does not exist.
      if (boxLoc == null) {
        request.setAttribute("myError", "Please enter a valid location");
      }
      else
        //there is already a box in the location.
        if (boxLoc.getSamplebox_box_barcode_id() != null) {
          request.setAttribute("myError", "Please select an available location");
        }
        else {

          try {
            BoxlocationAccessBean oldLoc = new BoxlocationAccessBean();
            AccessBeanEnumeration locationEnum =
              (AccessBeanEnumeration) oldLoc.findBoxLocationByBoxId(boxID);
            if (locationEnum.hasMoreElements()) {
              oldLoc = (BoxlocationAccessBean) locationEnum.nextElement();
              oldLoc.setSamplebox_box_barcode_id(null);
              oldLoc.setAvailable_ind("Y");
              oldLoc.commitCopyHelper();
            }

            boxLoc.setSamplebox_box_barcode_id(boxID);
            boxLoc.setAvailable_ind("N");
            boxLoc.commitCopyHelper();

            request.setAttribute("myError", "The box location has been updated");
          }
          catch (Exception e) {
            retry("Error removing box from previous position.");
            return;
          }
        }
      //close the window    
      servletCtx.getRequestDispatcher("/hiddenJsps/iltds/storage/close.jsp").forward(
        request,
        response);
      return;

    }
    try {

      servletCtx.getRequestDispatcher(
        "/hiddenJsps/iltds/storage/boxScanLocationOverride.jsp").forward(
        request,
        response);
    }
    catch (Exception e) {
      e.printStackTrace();
      retry("There was an error.");

    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (2/14/01 10:45:06 AM)
   * @param myError java.lang.String
   */
  public void retry(String myError) {
    try {
      request.setAttribute("myError", myError);
      if (request.getParameter("popup").equals("false")) {
        UpdateBoxLocation updBoxLoc = new UpdateBoxLocation(request, response, servletCtx);
        updBoxLoc.invoke();
      }
      else {
        OverrideBoxScanLoc blah = new OverrideBoxScanLoc(request, response, servletCtx);
        blah.invoke();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      ReportError err = new ReportError(request, response, servletCtx);
      err.setFromOp(this.getClass().getName());
      err.setErrorMessage(e.toString());
      try {
        err.invoke();
      }
      catch (Exception _axxx) {
      }
      return;
    }
  }
}
