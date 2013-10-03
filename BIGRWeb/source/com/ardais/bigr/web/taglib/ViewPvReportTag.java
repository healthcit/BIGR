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

public class ViewPvReportTag extends org.apache.struts.taglib.html.BaseHandlerTag {
  private String _sample;
  private String _evaluation;
  private String _linktext;
  private String _buttontext;
  private String _image1;
  private String _image2;
  private String _showImageReport;

  /**
   * Insert the method's description here.
   * Creation date: (3/29/2002 1:56:50 PM)
   */
  public ViewPvReportTag() {
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
        _buttontext,
        _evaluation,
        _sample,
        _linktext,
        _image1,
        _image2,
        _showImageReport);
    } catch (IOException ioe) {
      throw new JspException("Error in ViewPvReportTag doEndTag() " + ioe.getMessage());

    }
    return EVAL_PAGE;
  }

  public static void writeTagBody(
    JspWriter writer,
    HttpServletResponse res,
    String buttontext,
    String evaluation,
    String sample,
    String linktext,
    String image1,
    String image2,
    String showImageReport)
    throws IOException {

    writer.write(
      ColumnHelper.generatePvReportHtml(
        buttontext,
        evaluation,
        sample,
        linktext,
        image1,
        image2,
        showImageReport));
  }


  public int doStartTag() throws JspTagException {
    return EVAL_BODY_BUFFERED;
  }

  public void release() {
    super.release();
    _image1 = null;
    _image2 = null;
    _evaluation = null;
    _sample = null;
    _linktext = null;
    _buttontext = null;
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
   * Sets the linktext.
   * @param linktext The linktext to set
   */
  public void setLinktext(String linktext) {
    _linktext = linktext;
  }

  /**
   * Sets the sample.
   * @param sample The sample to set
   */
  public void setSample(String sample) {
    _sample = sample;
  }

  /**
   * Sets the evaluation.
   * @param evaluation The evaluation to set
   */
  public void setEvaluation(String evaluation) {
    _evaluation = evaluation;
  }

  /**
   * Sets the buttontext.
   * @param buttontext The buttontext to set
   */
  public void setButtontext(String buttontext) {
    _buttontext = buttontext;
  }

  /**
   * Sets the showImageReport.
   * @param showImageReport The showImageReport to set
   */
  public void setShowImageReport(String showImageReport) {
    _showImageReport = showImageReport;
  }

}