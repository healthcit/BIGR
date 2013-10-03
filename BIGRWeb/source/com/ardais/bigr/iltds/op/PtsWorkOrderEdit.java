package com.ardais.bigr.iltds.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.iltds.assistants.WorkOrderDataBean;
import com.ardais.bigr.iltds.beans.PtsOperation;
import com.ardais.bigr.iltds.beans.PtsOperationHome;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.iltds.helpers.WorkOrderHelper;
import com.ardais.bigr.util.EjbHomes;

/**
 * Updates the work order specified in the HTTP request,
 * and then forwards to the JSP to allow managing of the
 * project that contains the work order.
 */
public class PtsWorkOrderEdit extends StandardOperation {
  /**
   * Creates the WorkOrderEdit op.
   *
   * @param  req  the HttpServletRequest
   * @param  res  the HttpServletResponse
   * @param  ctx  the ServletContext
   */
  public PtsWorkOrderEdit(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super(req, res, ctx);
  }
  /**
   */
  public void invoke() throws IOException, ServletException, ApiException {
    WorkOrderHelper helper = new WorkOrderHelper(request);
    if (helper.isValid()) {
      try {
        PtsOperationHome home = (PtsOperationHome) EjbHomes.getHome(PtsOperationHome.class);
        PtsOperation ptsOp = home.create();
        if (helper.isNew()) {
          ptsOp.createWorkOrder(helper.getDataBean());
        }
        else {
          ptsOp.updateWorkOrder(helper.getDataBean());
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
      servletCtx.getRequestDispatcher("/hiddenJsps/iltds/pts/WorkOrderEdit.jsp").forward(
        request,
        response);
    }
  }
}
