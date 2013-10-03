/*
 * Created on Aug 23, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ardais.bigr.pdc.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.PrintWriter;
import java.io.IOException;

import  com.ardais.bigr.pdc.attachment.AttachmentDB;
import com.ardais.bigr.api.ApiLogger;

/**
 * @author Eric Zhang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AttachmentWriter extends HttpServlet {
  
  /** Handle the post request. */ 
  public void doPost(HttpServletRequest request, 
    HttpServletResponse response) throws ServletException { 
    doGet(request, response);
  }
  
  /** Handle the get request. */ 
  public void doGet(HttpServletRequest request, 
    HttpServletResponse response) throws ServletException { 
  
    /** A printwriter for getting the response. */ 
    PrintWriter out = null; 
    AttachmentDB dbAccessor = null;
    String fileId = null;
    String fileType = null;
    
    
    try { 
  
      out = new PrintWriter(response.getOutputStream(  )); 
      dbAccessor = new AttachmentDB();
      fileId = request.getParameter("fileId");
      fileType = request.getParameter("fileType");
    
      byte[] data = dbAccessor.getAttachmentBlob(fileId); 
    
      response.setContentType(fileType); 
     
      response.getOutputStream(  ).write(data);
      
    } catch (IOException e) { 
      ApiLogger.error("Error in writing file: "+ e.getMessage()); 
      
    } catch (Exception e) { 
      //warning(response, "pdflib error:", e); 

    } 

  }      
}
