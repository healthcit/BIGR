package com.ardais.bigr.iltds.op;

import java.io.IOException;

import javax.servlet.ServletException;

import com.ardais.bigr.orm.helpers.BoxScanData;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.WebUtils;

/**
 * Insert the type's description here.
 * Creation date: (10/15/02 11:33:55 AM)
 * @author: John Esielionis
 */
public class BoxScan extends StandardOperation {

  private static final String RETRY_PATH = "/hiddenJsps/iltds/storage/boxScan.jsp";

  /**
   * BoxScan constructor comment.
   * @param req javax.servlet.http.HttpServletRequest
   * @param res javax.servlet.http.HttpServletResponse
   * @param ctx javax.servlet.ServletContext
   */
  public BoxScan(
    javax.servlet.http.HttpServletRequest req,
    javax.servlet.http.HttpServletResponse res,
    javax.servlet.ServletContext ctx) {
    super(req, res, ctx);
  }

  /**
   * Insert the method's description here.
   * Creation date: (10/15/02 11:33:55 AM)
   */
  public void invoke() throws Exception, java.io.IOException {

    BoxScanData bsd = BoxLayoutUtils.prepareForBoxScan(WebUtils.getSecurityInfo(request));
    if (bsd.isEmpty()) {
      retry("The account does not have a box layout defined. Please contact customer service.");
      return;
    }
    request.setAttribute("boxScanData", bsd);

    String pageName = "/hiddenJsps/iltds/storage/boxScan.jsp";
    servletCtx.getRequestDispatcher(pageName).forward(request, response);
  }

  private void retry(String myError) throws IOException, ServletException, Exception {
    BoxScanData bsd = BoxLayoutUtils.prepareForBoxScan(WebUtils.getSecurityInfo(request));
    request.setAttribute("boxScanData", bsd);

    retry(myError, RETRY_PATH);
  }
}
