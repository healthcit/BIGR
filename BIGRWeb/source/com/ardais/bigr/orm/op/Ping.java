package com.ardais.bigr.orm.op;

import com.ardais.bigr.orm.*;
import com.ardais.bigr.orm.beans.*;
import com.ardais.bigr.orm.helpers.*;
import com.ardais.bigr.orm.op.*;
import com.ardais.bigr.orm.servlets.*;
import java.io.*;
import java.io.PrintWriter;
import java.util.*;
import javax.naming.*;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 * Insert the type's description here.
 * Creation date: (3/8/2001 2:00:43 PM)
 * @author: Jake Thompson
 */
public class Ping extends com.ardais.bigr.orm.op.StandardOperation {
/**
 * Ping constructor comment.
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 * @param ctx javax.servlet.ServletContext
 */
public Ping(HttpServletRequest req, HttpServletResponse res, ServletContext ctx) {
	super(req, res, ctx);
}
/**
 * Insert the method's description here.
 * Creation date: (3/8/2001 2:00:43 PM)
 */
public void invoke() throws Exception, IOException {
    java.io.PrintWriter out = response.getWriter();
    out.println("Welcome to ping");
    return;
}
}
