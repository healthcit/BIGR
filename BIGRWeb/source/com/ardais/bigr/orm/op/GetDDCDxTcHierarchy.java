package com.ardais.bigr.orm.op;

import java.io.IOException;

import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.ArtsConstants;

/**
 * Insert the type's description here.
 * Creation date: (6/1/01 11:03:44 AM)
 * @author: Jake Thompson
 */
public class GetDDCDxTcHierarchy extends com.ardais.bigr.orm.op.StandardOperation {	
    /**
	 * GetDDCDxTcHierarchy constructor comment.
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param res javax.servlet.http.HttpServletResponse
	 * @param ctx javax.servlet.ServletContext
	 */ 
	public GetDDCDxTcHierarchy(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res, javax.servlet.ServletContext ctx) {
		super(req, res, ctx);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (6/1/01 11:03:44 AM)
	 */
	public void invoke() throws Exception, IOException  
	{
		String typeCode = request.getParameter("type");		

		if (typeCode.trim().equalsIgnoreCase("D")) {
			request.setAttribute("valueSet", BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_DIAGNOSIS_HIERARCHY));
			servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchyOther/PdcDxTcHierarchyDisease.jsp").forward(request, response);			
		}
		else if(typeCode.trim().equalsIgnoreCase("T")) {
      request.setAttribute("valueSet", BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_TISSUE_HIERARCHY));
			servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchyOther/PdcDxTcHierarchyTissue.jsp").forward(request, response);			
		}
		else if(typeCode.trim().equalsIgnoreCase("P")) {
      request.setAttribute("valueSet", BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_PROCEDURE_HIERARCHY));
			servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchyOther/PdcDxTcHierarchyProcedure.jsp").forward(request, response);			
		}	
	}
}
