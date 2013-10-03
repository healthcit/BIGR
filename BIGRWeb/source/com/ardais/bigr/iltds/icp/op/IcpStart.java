package com.ardais.bigr.iltds.icp.op;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.iltds.op.StandardOperation;

public class IcpStart extends StandardOperation {

    public IcpStart(
        HttpServletRequest req,
        HttpServletResponse res,
        ServletContext ctx) {
        super(req, res, ctx);
    }

    public void invoke() throws Exception {
        servletCtx.getRequestDispatcher(
            "/hiddenJsps/iltds/icp/icpQuery.jsp").forward(
            request,
            response);
    }
}
