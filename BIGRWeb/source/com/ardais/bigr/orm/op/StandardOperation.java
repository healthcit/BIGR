package com.ardais.bigr.orm.op;

/*******************************************************************************************************
Author:Allez Software Inc.
*******************************************************************************************************/
/**
 * Insert the type's description here.
 * Creation date: (12/29/2000 12:15:36 PM)
 * @author: Jeremy Gilbert
 */

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.orm.helpers.FormLogic;

public abstract class StandardOperation {
  protected HttpServletRequest request = null;
  protected HttpServletResponse response = null;
  protected ServletContext servletCtx = null;
  
  /**
   * Insert the method's description here.
   * Creation date: (12/29/2000 12:18:19 PM)
   * @param param javax.servlet.ServletRequest
   * @param param2 javax.servlet.ServletResponse
   */
  public StandardOperation(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
    super();

    request = req;
    response = res;
    servletCtx = ctx;
  }
  
  /**
   * Insert the method's description here.
   * Creation date: (12/29/2000 1:40:15 PM)
   */
  public abstract void invoke() throws java.io.IOException, Exception;
}
