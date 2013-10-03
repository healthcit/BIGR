package com.ardais.bigr.web.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import com.ardais.bigr.library.web.column.ColumnHelper;

/**
 * Initializes the request data object and saves it as a
 * REQUEST_SCOPE attribute.
 *
 * @author Jake Thompson
 */

public class ViewPullInfoTag extends org.apache.struts.taglib.html.BaseHandlerTag {
  private String _sampleId;
  private String _pullDetails;
  private String _image1;
  private String _image2;

  /**
   * Insert the method's description here.
   * Creation date: (3/29/2002 1:56:50 PM)
   */
  public ViewPullInfoTag() {
    super();
  }
  /**
  * End of Tag Processing
  *
  * @exception JspException if a JSP exception occurs
  */
  public int doEndTag() throws JspException {

    try {
      // Render this element to our writer
      JspWriter writer = pageContext.getOut();
      HttpServletResponse res = (HttpServletResponse) pageContext.getResponse();

      writeTagBody(
        writer,
        res,
        _sampleId,
        _pullDetails,
        _image1,
        _image2);
    } catch (IOException ioe) {
      throw new JspException("Error in ViewPullInfoTag doEndTag() " + ioe.getMessage());

    }
    return EVAL_PAGE;
  }

  public static void writeTagBody(
    JspWriter writer,
    HttpServletResponse res,
    String sampleId,
    String pulldetails,
    String image1,
    String image2)
    throws IOException {

    writer.write(
        ColumnHelper.generatePullInfoHtml(
        sampleId,
        pulldetails,
        image1,
        image2));
  }


  public int doStartTag() throws JspTagException {
    return EVAL_BODY_BUFFERED;
  }

  public void release() {
    super.release();
    _image1 = null;
    _image2 = null;
    _sampleId = null;
    _pullDetails = null;
  }
  
  /**
   * Sets the image1.
   * @param image1 The image1 to set
   */
  public void setImage1(String image1) {
    _image1 = image1;
  }

  /**
   * Sets the image2.
   * @param image2 The image2 to set
   */
  public void setImage2(String image2) {
    _image2 = image2;
  }

  /**
   * @param string
   */
  public void setSampleId(String string) {
    _sampleId = string;
  }

  /**
   * @param string
   */
  public void setPullDetails(String string) {
    _pullDetails = string;
  }

}