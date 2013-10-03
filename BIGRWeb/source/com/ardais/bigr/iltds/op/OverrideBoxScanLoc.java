package com.ardais.bigr.iltds.op;

import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.iltds.beans.SampleboxAccessBean;
import com.ardais.bigr.iltds.beans.SampleboxKey;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.util.EjbHomes;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

public class OverrideBoxScanLoc extends com.ardais.bigr.iltds.op.StandardOperation {

  public OverrideBoxScanLoc(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }

  public void invoke() throws Exception {
    String boxID = request.getParameter("boxID");
    Vector results = new Vector();
    ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean();
    AccessBeanEnumeration staffEnum;
    request.getSession(false).removeAttribute("roomlist");
    request.getSession(false).removeAttribute("freezerlist");
    request.getSession(false).removeAttribute("drawerlist");
    request.getSession(false).removeAttribute("slotlist");

    String location = request.getParameter("loc");
    if (location == null || location.equals("")) {
      String user = (String) request.getSession(false).getAttribute("user");
      String account = (String) request.getSession(false).getAttribute("account");
      staffEnum = (AccessBeanEnumeration) staff.findLocByUserProf(user, account);
      staff = (ArdaisstaffAccessBean) staffEnum.nextElement();
      location = staff.getGeolocation_location_address_id();
    }

    request.setAttribute("loc", location);

    if (boxID == null) {
      ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
      ListGenerator list = home.create();
      results = list.findAvailableBoxLocationAll(location);
    }
    else {
      SampleboxAccessBean box = new SampleboxAccessBean(new SampleboxKey(boxID));
      String storageTypeCid = box.getStorageTypeCid();
      request.setAttribute("storageTypeCid", storageTypeCid);
      try {
        ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
        ListGenerator list = home.create();
        results =
          list.findNextLocationDropDown(location, null, null, null, storageTypeCid);
      }
      catch (Exception e) {
        retry("Error obtaining new locations.");
        return;
      }
      request.getSession(false).setAttribute("roomlist", results);
    }

    if (results != null && results.size() == 0) {
      request.setAttribute("popup", request.getParameter("popup"));
      retry("The system cannot find any other valid locations for the box");
      return;
    }
    else {
      servletCtx.getRequestDispatcher(
        "/hiddenJsps/iltds/storage/boxScanLocationOverride.jsp").forward(
        request,
        response);
    }
  }

  private void retry(String myError) throws Exception {
    request.setAttribute("myError", myError);
    servletCtx.getRequestDispatcher(
      "/hiddenJsps/iltds/storage/boxScanLocationOverride.jsp").forward(
      request,
      response);
  }
}
