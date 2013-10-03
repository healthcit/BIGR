package com.ardais.bigr.iltds.op;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.iltds.beans.ListGenerator;
import com.ardais.bigr.iltds.beans.ListGeneratorHome;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;

public class ShippingPrintManifests extends com.ardais.bigr.iltds.op.StandardOperation {

  public ShippingPrintManifests(
    HttpServletRequest req,
    HttpServletResponse res,
    ServletContext ctx) {
    super(req, res, ctx);
  }

  public void invoke() throws Exception {
    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);
    String userID = securityInfo.getUsername();

    ListGeneratorHome home = (ListGeneratorHome) EjbHomes.getHome(ListGeneratorHome.class);
    ListGenerator shipment = home.create();

    request.setAttribute("waybillList", shipment.findPrintableManifests(userID));

    servletCtx.getRequestDispatcher(
      "/hiddenJsps/iltds/shipping/shippingPrintManifests.jsp").forward(
      request,
      response);
  }
}
