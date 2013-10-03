package com.ardais.bigr.iltds.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.assistants.LineItemDataBean;
import com.ardais.bigr.iltds.beans.PtsOperation;
import com.ardais.bigr.iltds.beans.PtsOperationHome;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.iltds.helpers.LineItemHelper;
import com.ardais.bigr.util.EjbHomes;

/**
 * Updates the line item specified in the HTTP request,
 * and then forwards to the JSP to allow managing of the
 * project that contains the line item.
 */
public class PtsLineItemEdit extends StandardOperation {
  /**
   * Creates the LineItemEdit op.
   *
   * @param  req  the HttpServletRequest
   * @param  res  the HttpServletResponse
   * @param  ctx  the ServletContext
   */
  public PtsLineItemEdit(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }
  /**
   */
  public void invoke() throws IOException, ServletException, ApiException {
    LineItemHelper helper = new LineItemHelper(request);
    if (helper.isValid()) {
      try {
        LineItemDataBean dataBean = helper.getDataBean();
        if (helper.isNew()) {
          PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
          PtsOperation ptsOp = home.create();
          ptsOp.createLineItem(dataBean);
        }
        else {
          PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
          PtsOperation ptsOp = home.create();
          ptsOp.updateLineItem(dataBean);
        }
      }
      catch (javax.ejb.CreateException e) {
        throw new ApiException("Error when creating/updating a line item", e);
      }
      catch (javax.naming.NamingException e) {
        throw new ApiException("Error when creating/updating a line item", e);
      }
      catch (ClassNotFoundException e) {
        throw new ApiException("Error when creating/updating a line item", e);
      }

      (new PtsProjectManagePrepare(request, response, servletCtx)).invoke();
    }
    else {
      request.setAttribute(JspHelper.ID_HELPER, helper);
      servletCtx.getRequestDispatcher("/hiddenJsps/iltds/pts/LineItemEdit.jsp").forward(
        request,
        response);
    }
  }
}
