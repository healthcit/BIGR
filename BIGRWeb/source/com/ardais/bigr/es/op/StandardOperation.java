package com.ardais.bigr.es.op;

/*******************************************************************************************************
Author:Allez Software Inc.
*******************************************************************************************************/
/**
 * Insert the type's description here.
 * Creation date: (12/29/2000 12:15:36 PM)
 * @author: Jeremy Gilbert
 */

import javax.servlet.http.*;
import javax.naming.*;
import java.util.*;
import com.ardais.bigr.es.*;
import com.ardais.bigr.es.beans.*;
import com.ardais.bigr.es.op.*;
import com.ardais.bigr.es.servlets.*;
import com.ardais.bigr.es.helpers.*;
import java.io.*;
import java.io.PrintWriter;
import javax.naming.InitialContext;
import javax.servlet.*;

public abstract class StandardOperation {
	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;
	protected ServletContext servletCtx = null;
	protected FormLogic formLogic = null;
/**
 * Insert the method's description here.
 * Creation date: (12/29/2000 12:18:19 PM)
 * @param param javax.servlet.ServletRequest
 * @param param2 javax.servlet.ServletResponse
 */

public StandardOperation(
	HttpServletRequest req,
	HttpServletResponse res,
	ServletContext ctx)
{

	super();

	request = req;
	response = res;
	servletCtx = ctx;
	formLogic = new FormLogic();

}
/**
 * Insert the method's description here.
 * Creation date: (12/29/2000 1:40:15 PM)
 */
public abstract void invoke() throws java.io.IOException, Exception;
}
