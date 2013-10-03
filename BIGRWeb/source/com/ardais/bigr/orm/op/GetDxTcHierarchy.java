package com.ardais.bigr.orm.op;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.ArtsConstants;

/**
 * Insert the type's description here.
 * Creation date: (5/22/01 11:24:32 AM)
 * @author: Jake Thompson
 */
public class GetDxTcHierarchy extends com.ardais.bigr.orm.op.StandardOperation {
/**
 * GetDxTcHierarchy constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public GetDxTcHierarchy(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * Insert the method's description here.
 * Creation date: (5/22/01 11:24:32 AM)
 */
public void invoke() throws Exception, IOException {
	
	String typeCode = request.getParameter("type");

	if (typeCode.trim().equalsIgnoreCase("D")) {
		request.setAttribute("valueSet", BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_DIAGNOSIS_HIERARCHY));
		servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchy/PdcDxTcHierarchyDisease.jsp").forward(request, response);			
	}
	else if(typeCode.trim().equalsIgnoreCase("T")) {
    request.setAttribute("valueSet", BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_TISSUE_HIERARCHY));
		servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchy/PdcDxTcHierarchyTissue.jsp").forward(request, response);			
	}
	else if(typeCode.trim().equalsIgnoreCase("P")) {
    request.setAttribute("valueSet", BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_PROCEDURE_HIERARCHY));
		servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchy/PdcDxTcHierarchyProcedure.jsp").forward(request, response);			
	}				
}
}
