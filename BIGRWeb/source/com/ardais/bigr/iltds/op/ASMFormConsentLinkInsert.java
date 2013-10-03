package com.ardais.bigr.iltds.op;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.iltds.beans.ASMOperation;
import com.ardais.bigr.iltds.beans.ASMOperationHome;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.EjbHomes;
import com.ardais.bigr.util.WebUtils;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

public class ASMFormConsentLinkInsert extends StandardOperation {

  public ASMFormConsentLinkInsert(
    HttpServletRequest req,
    HttpServletResponse res,
    ServletContext ctx) {
    super(req, res, ctx);
  }

  public void invoke() throws Exception {
    String myError = null;

    SecurityInfo securityInfo = WebUtils.getSecurityInfo(request);

    String myConsentID = request.getParameter("consentID_1");
    String myASMFormID = request.getParameter("asmID_1");

    //create the asm form id, asm id's and all the samples associated.
    ASMOperationHome home = (ASMOperationHome) EjbHomes.getHome(ASMOperationHome.class);
    ASMOperation myASMForm = home.create();
    myASMForm.associateASMForm(myConsentID, myASMFormID, securityInfo);

    request.setAttribute("myError", myError);
    servletCtx.getRequestDispatcher("/hiddenJsps/iltds/asm/asmFormConsentLinkInsert.jsp").forward(
      request,
      response);
  }
}