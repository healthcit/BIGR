package com.ardais.bigr.orm.op;

import java.io.IOException;

import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.oce.util.OceUtil;
import com.ardais.bigr.util.ArtsConstants;
/**
 * Insert the type's description here.
 * Creation date: (6/1/01 11:03:44 AM)
 * @author: Jake Thompson
 */
public class GetDDCDxTcHierarchyOther extends com.ardais.bigr.orm.op.StandardOperation {

/**
 * Insert the method's description here.
 * Creation date: (6/1/01 11:03:44 AM)
 */
public void invoke() throws Exception, IOException  
{

	String typeCode = request.getParameter("type");
    String whereClause = request.getParameter("whereClause");
	
	if ((typeCode.equalsIgnoreCase(OceUtil.OCE_TYPECODE_DIAGNOSIS_IND))) {
		request.setAttribute("valueSet", BigrGbossData.getValueSetWithoutOthers(ArtsConstants.VALUE_SET_DIAGNOSIS_HIERARCHY));
		servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchyOther/PdcDxTcHierarchyDisease.jsp").forward(request, response);			
	}
	else if(typeCode.equalsIgnoreCase(OceUtil.OCE_TYPECODE_TISSUE_IND)) {
    request.setAttribute("valueSet", BigrGbossData.getValueSetWithoutOthers(ArtsConstants.VALUE_SET_TISSUE_HIERARCHY));
		servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchyOther/PdcDxTcHierarchyTissue.jsp").forward(request, response);			
	}
	else if(typeCode.equalsIgnoreCase(OceUtil.OCE_TYPECODE_PROCEDURE_IND)){
    request.setAttribute("valueSet", BigrGbossData.getValueSetWithoutOthers(ArtsConstants.VALUE_SET_PROCEDURE_HIERARCHY));
		servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchyOther/PdcDxTcHierarchyProcedure.jsp").forward(request, response);			
	}
	//Distant Metastasis	
	else if(typeCode.equalsIgnoreCase(OceUtil.OCE_TYPECODE_DISTANT_METASTASIS_IND)){
        request.setAttribute("PdcLookupHierarchy", OceUtil.getOtherValueList(whereClause, ArtsConstants.VALUE_SET_DDC_DISTANT_METASTASIS));
		servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchyOther/PdcLookupHierarchy.jsp").forward(request, response);			
	}
	//Histological Nuclear Grade
	else if(typeCode.equalsIgnoreCase(OceUtil.OCE_TYPECODE_HNG_IND)){
		request.setAttribute("PdcLookupHierarchy", OceUtil.getOtherValueList(whereClause, ArtsConstants.VALUE_SET_HISTOLOGICAL_NUCLEAR_GRADE));
        servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchyOther/PdcLookupHierarchy.jsp").forward(request, response);			
	}
	//Histological type
	else if(typeCode.equalsIgnoreCase(OceUtil.OCE_TYPECODE_HISTOLOGICAL_TYPE_IND)){
		request.setAttribute("PdcLookupHierarchy", OceUtil.getOtherValueList(whereClause, ArtsConstants.VALUE_SET_HISTOLOGICAL_TYPE));
		servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchyOther/PdcLookupHierarchy.jsp").forward(request, response);			
	}
	//Lymph Node Stage Desc
	else if(typeCode.equalsIgnoreCase(OceUtil.OCE_TYPECODE_LYMPH_NODE_IND)){
		request.setAttribute("PdcLookupHierarchy", OceUtil.getOtherValueList(whereClause,ArtsConstants.VALUE_SET_DDC_LYMPH_NODE_STAGE_DESC));
		servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchyOther/PdcLookupHierarchy.jsp").forward(request, response);			
	}
	//Lymph Stage Groupings
	else if(typeCode.equalsIgnoreCase(OceUtil.OCE_TYPECODE_STAGE_GROUPING_IND)){
		request.setAttribute("PdcLookupHierarchy", OceUtil.getOtherValueList(whereClause,ArtsConstants.VALUE_SET_DDC_STAGE_GROUPING));
		servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchyOther/PdcLookupHierarchy.jsp").forward(request, response);			
	}
	//Lymph Tumor Stage Desc
	else if(typeCode.equalsIgnoreCase(OceUtil.OCE_TYPECODE_TUMOR_STAGE_DESC_IND)){
		request.setAttribute("PdcLookupHierarchy", OceUtil.getOtherValueList(whereClause,ArtsConstants.VALUE_SET_DDC_TUMOR_STAGE_DESC));
		servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchyOther/PdcLookupHierarchy.jsp").forward(request, response);			
	}
	//Lymph Tumor Stage Type
	else if(typeCode.equalsIgnoreCase(OceUtil.OCE_TYPECODE_TUMOR_STAGE_TYPE_IND)){
		request.setAttribute("PdcLookupHierarchy", OceUtil.getOtherValueList(whereClause, ArtsConstants.VALUE_SET_TUMOR_STAGE_TYPE));
		servletCtx.getRequestDispatcher("/hiddenJsps/orm/DDCHierarchyOther/PdcLookupHierarchy.jsp").forward(request, response);			
	}
}

/**
 * GetDDCDxTcHierarchy constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public GetDDCDxTcHierarchyOther(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse res, javax.servlet.ServletContext ctx) {
	super(req, res, ctx);
}
}
