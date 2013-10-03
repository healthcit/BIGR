package com.ardais.bigr.iltds.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateBoxLocation
    extends com.ardais.bigr.iltds.op.StandardOperation {

    public UpdateBoxLocation(
        HttpServletRequest req,
        HttpServletResponse res,
        ServletContext ctx) {
        super(req, res, ctx);
    }

    public void invoke() throws Exception, IOException {
        servletCtx
            .getRequestDispatcher("/hiddenJsps/iltds/locationManagement/barcodeLookup.jsp")
            .forward(request, response);
    }
}
