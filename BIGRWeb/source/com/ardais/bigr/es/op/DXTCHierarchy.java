package com.ardais.bigr.es.op;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.ArtsConstants;
import com.gulfstreambio.gboss.GbossValueSet;

/**
 * Insert the type's description here.
 * Creation date: (10/9/2001 11:15:46 AM)
 * @author: Jake Thompson
 */
public class DXTCHierarchy extends com.ardais.bigr.es.op.StandardOperation {
/**
 * DXTCHierarchy constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public DXTCHierarchy(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * Insert the method's description here.
 * Creation date: (10/9/2001 11:15:46 AM)
 */
public void invoke() throws Exception, IOException {
    Hashtable codeHash = new Hashtable();
    ArrayList labelList = new ArrayList();

    String type = request.getParameter("type");
    String process = request.getParameter("process");
    String requested = request.getParameter("request");
    String lims = request.getParameter("lims");
    String clear = request.getParameter("clear");

    if (type.equals("D")) {
        GbossValueSet dxHierarchy = BigrGbossData.getReceivedDiagnosisValueSet();
        request.setAttribute("valueSet", dxHierarchy);

    } else if (type.equals("DCaseDx")) {
        GbossValueSet dxHierarchy = BigrGbossData.getReceivedDiagnosisValueSet();
        request.setAttribute("valueSet", dxHierarchy);

    } else if (type.equals("DSamplePathology")) {
        GbossValueSet dxHierarchy = BigrGbossData.getReceivedDiagnosisValueSet();
        request.setAttribute("valueSet", dxHierarchy);

    } else if (type.equals("T")) {
        GbossValueSet tcHierarchy = BigrGbossData.getReceivedTissueValueSet();
        request.setAttribute("valueSet", tcHierarchy);

    } else if (type.equals("L")) {
      GbossValueSet lHierarchy = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_DIAGNOSIS_HIERARCHY);
    	request.setAttribute("valueSet", lHierarchy);
    	try {
            servletCtx.getRequestDispatcher("/hiddenJsps/es/dxtc/LimsDxTcHierarchy.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    } else if (type.equals("S")) {
      GbossValueSet lHierarchy = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_LIMS_STRUCTURE);
    	
    	request.setAttribute("valueSet", lHierarchy);
    	//MR5497 - added a jsp to summarize all the structures, but kept the old jsp
    	//around (/hiddenJsps/es/dxtc/LimsGraph.jsp) in case we need a generic way
    	//to present a concept graph for Lims)
    	try {
            servletCtx.getRequestDispatcher("/hiddenJsps/lims/pathology_lims/LimsStructureSummary.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    } else if (type.equals("I")) {
    	LegalValueSet lHierarchy = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_LIMS_INFLAMMATION);
    	request.setAttribute("valueSet", lHierarchy);
    	try {
            servletCtx.getRequestDispatcher("/hiddenJsps/es/dxtc/LimsPdcLookup.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else if (type.equals("U")) {
    	LegalValueSet lHierarchy = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_LIMS_TUMOR_FEATURES);
    	request.setAttribute("valueSet", lHierarchy);
    	try {
            servletCtx.getRequestDispatcher("/hiddenJsps/es/dxtc/LimsPdcLookup.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else if (type.equals("C")) {
    	LegalValueSet lHierarchy = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_LIMS_TUMOR_STROMA_CELLULAR_FEATURES);
    	request.setAttribute("valueSet", lHierarchy);
    	try {
            servletCtx.getRequestDispatcher("/hiddenJsps/es/dxtc/LimsPdcLookup.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    } else if (type.equals("A")) {
    	LegalValueSet lHierarchy = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_LIMS_TUMOR_STROMA_HYPOACELLULAR_FEATURES);
    	request.setAttribute("valueSet", lHierarchy);
    	try {
            servletCtx.getRequestDispatcher("/hiddenJsps/es/dxtc/LimsPdcLookup.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    }

    if (clear != null && !clear.equals("")) {
        if (lims != null &&  !lims.equals("")) {
            request.getSession().removeAttribute("selectedCaseDx");
            request.getSession().removeAttribute("labelCaseDx");
            request.getSession().removeAttribute("selectedSamplePathology");
            request.getSession().removeAttribute("labelSamplePathology");
        } else if (requested == null || requested.equals("")) {
            if (type.equals("T")) {
                request.getSession().removeAttribute("selectedTC");
                request.getSession().removeAttribute("labelTC");
            } else if (type.equals("D")) {
                request.getSession().removeAttribute("selectedDX");
                request.getSession().removeAttribute("labelDX");
            }
        } else {
            if (type.equals("T")) {
                request.getSession().removeAttribute("requestTC");
                request.getSession().removeAttribute("requestlabelTC");
            } else if (type.equals("D")) {
                request.getSession().removeAttribute("requestDX");
                request.getSession().removeAttribute("requestlabelDX");
            }
        }
        try {
            servletCtx.getRequestDispatcher("/hiddenJsps/es/dxtc/DxTcHierarchy.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Integer intI = new Integer((request.getParameter("i") != null) ? request.getParameter("i") : "0");
    Integer intJ = new Integer((request.getParameter("j") != null) ? request.getParameter("j") : "0");

    //com.ardais.bigr.pdc.beans.BIGRLibraryOperationAccessBean bigrOp =
    //    new com.ardais.bigr.pdc.beans.BIGRLibraryOperationAccessBean();

    if (intJ.intValue() > 0) {
        for (int i = 0; i < intI.intValue(); i++) {
            for (int j = 0; j < intJ.intValue(); j++) {
                String codes[] = request.getParameterValues(i + "_" + j);
                if (codes != null) {
                    for (int k = 0; k < codes.length; k++) {
                        codeHash.put(codes[k], codes[k]);

                    }
                }
            }
        }
    } else {
        for (int i = 0; i < intI.intValue(); i++) {
            String codes[] = request.getParameterValues(i + "");
            if (codes != null) {
                for (int k = 0; k < codes.length; k++) {
                    codeHash.put(codes[k], codes[k]);

                }
            }
        }
    }

    if (intJ.intValue() > 0) {
        for (int i = 0; i < intI.intValue(); i++) {
            for (int j = 0; j < intJ.intValue(); j++) {
                String codes[] = request.getParameterValues(i + "_" + j);
                if (request.getParameter(i + "_" + j + "_p") != null) {
                    labelList.add(request.getParameter(i + "_" + j + "_p"));
                } else if (codes != null) {
                    for (int k = 0; k < codes.length; k++) {
                        if (type.startsWith("D")) {
                            labelList.add(BigrGbossData.getDiagnosisDescription(codes[k]));
                        } else {
                            labelList.add(BigrGbossData.getTissueDescription(codes[k]));
                        }
                    }
                }
            }
        }
    } else {
        for (int i = 0; i < intI.intValue(); i++) {
            String codes[] = request.getParameterValues(i + "");
            if (request.getParameter(i + "_p") != null) {
                labelList.add(request.getParameter(i + "_p"));
            } else if (codes != null) {
                for (int k = 0; k < codes.length; k++) {
                    if (type.startsWith("D")) {
                        labelList.add(BigrGbossData.getDiagnosisDescription(codes[k]));
                    } else {
                        labelList.add(BigrGbossData.getTissueDescription(codes[k]));
                    }
                }
            }
        }
    }

    if (process != null) {

        if (lims != null && !lims.equals("")) {
        	if (type.equals("DCaseDx")) {
                request.getSession().setAttribute("selectedCaseDx", codeHash);
                request.getSession().setAttribute("labelCaseDx", labelList);
            } else if (type.equals("DSamplePathology")) {
                request.getSession().setAttribute("selectedSamplePathology", codeHash);
                request.getSession().setAttribute("labelSamplePathology", labelList);
            }
        } else if (requested == null || requested.equals("")) {
            if (type.equals("T")) {
                request.getSession().setAttribute("selectedTC", codeHash);
                request.getSession().setAttribute("labelTC", labelList);
            } else if (type.equals("D")) {
                request.getSession().setAttribute("selectedDX", codeHash);
                request.getSession().setAttribute("labelDX", labelList);
            }  
        } else {
            if (type.equals("T")) {
                request.getSession().setAttribute("requestTC", codeHash);
                request.getSession().setAttribute("requestlabelTC", labelList);
            } else if (type.equals("D")) {
                request.getSession().setAttribute("requestDX", codeHash);
                request.getSession().setAttribute("requestlabelDX", labelList);
            } 
        }

        try {
            servletCtx.getRequestDispatcher("/hiddenJsps/es/dxtc/DxTcClose.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

    } else {
        if (type.equals("D")) {
            GbossValueSet dxHierarchy = BigrGbossData.getReceivedDiagnosisValueSet();
            request.setAttribute("valueSet", dxHierarchy);

        } else if (type.equals("T")) {
            GbossValueSet tcHierarchy = BigrGbossData.getReceivedTissueValueSet();
            request.setAttribute("valueSet", tcHierarchy);

        } 

        try {
            servletCtx.getRequestDispatcher("/hiddenJsps/es/dxtc/DxTcHierarchy.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
}
