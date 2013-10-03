package com.ardais.bigr.pdc.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ardais.bigr.api.ApiException;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;


/**
 * for SWP-1110
 * <code>MultipartDispatch</code> is the controller servlet for DDC like Dispatch except that it 
 * only handle requests from Multipart form
 */
public class MultipartDispatch extends Dispatch {
  /**
   * Creates a new <code>MultipartDispatch</code> servlet.
   */
  public MultipartDispatch() {
    super();
  }

 
  protected void doDispatch(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    
    // Parse multipart
    // Check that we have a file upload request
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);

  //  Create a factory for disk-based file items
    FileItemFactory factory = new DiskFileItemFactory();

  //     Create a new file upload handler
    ServletFileUpload upload = new ServletFileUpload(factory);
    
//  Create a progress listener
    ProgressListener progressListener = new ProgressListener(){
       public void update(long pBytesRead, long pContentLength, int pItems) {
           System.out.println("We are currently reading item " + pItems);
           if (pContentLength == -1) {
               System.out.println("So far, " + pBytesRead + " bytes have been read.");
           } else {
               System.out.println("So far, " + pBytesRead + " of " + pContentLength
                                  + " bytes have been read.");
           }
       }
    };
    upload.setProgressListener(progressListener);

    
  //holds the form fields and attached file
    List  fileItems =null;
    
    //Parse the request
    try {
    
      if(isMultipart)
      fileItems  = upload.parseRequest(request);
          
     super.doDispatch(request, response, fileItems);
     
     
    } catch (FileUploadException fe) {
        throw new ApiException("Error happening in uploading file ");
    }
    
  }
  
  /**
   * Process all POST requests by dispatching to the appropriate op.
   * 
   * @param  request  the servlet request
   * @param  response  the servlet response
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

     doDispatch(request, response);
  }
  
  
}
