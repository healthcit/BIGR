package com.ardais.bigr.iltds.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.orm.helpers.BoxScanData;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.WebUtils;

/**
 * Insert the type's description here.
 * Creation date: (2/2/2001 12:48:01 PM)
 * @author: Jake Thompson
 */
public class CheckOutBoxStart extends StandardOperation {

  private static final String RETRY_PATH = "/hiddenJsps/iltds/storage/checkOutBox/checkOutBox.jsp";

  /**
   * CheckOutBoxStart constructor comment.
   * @param req javax.servlet.http.HttpServletRequest
   * @param res javax.servlet.http.HttpServletResponse
   * @param ctx javax.servlet.ServletContext
   */
  public CheckOutBoxStart(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }

  /**
   * Insert the method's description here.
   * Creation date: (2/2/2001 12:48:01 PM)
   */
  public void invoke() throws Exception {
    BoxScanData bsd = BoxLayoutUtils.prepareForBoxScan(WebUtils.getSecurityInfo(request));
    if (bsd.isEmpty()) {
      retry("The account does not have a box layout defined. Please contact customer service.");
      return;
    }
    request.setAttribute("boxScanData", bsd);

    String pageName = "/hiddenJsps/iltds/storage/checkOutBox/checkOutBox.jsp";
    servletCtx.getRequestDispatcher(pageName).forward(request, response);
  }

  private void retry(String myError) throws IOException, ServletException, Exception {
    BoxScanData bsd = BoxLayoutUtils.prepareForBoxScan(WebUtils.getSecurityInfo(request));
    request.setAttribute("boxScanData", bsd);

    retry(myError, RETRY_PATH);
  }
}
