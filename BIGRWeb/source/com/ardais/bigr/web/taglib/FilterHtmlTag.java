package com.ardais.bigr.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.ardais.bigr.api.Escaper;

/**
 */
public class FilterHtmlTag extends BodyTagSupport {
  private boolean _filter = false;
  private boolean _whitespace = false;
  /**
   * Creates an <code>FilterHtmlTag</code>.
   */
  public FilterHtmlTag() {
    super();
  }
  /**
   */
  public int doAfterBody() throws JspException {
    BodyContent body = getBodyContent();
    if (body != null) {
      JspWriter out = body.getEnclosingWriter();
      try {
        if ((_filter) && (_whitespace))
          out.write(Escaper.htmlEscapeAndPreserveWhitespace(body.getString()));
        else if (_filter)
          out.write(Escaper.htmlEscape(body.getString()));
        else if (_whitespace)
          out.write(Escaper.htmlPreserveWhitespace(body.getString()));
        else
          out.write(body.getString());
      } catch (IOException e) {
        throw new JspException("IOException in FilterHtmlTag.doAfterBody");
      }
    }
    return SKIP_BODY;
  }
  /**
   * Returns whether the body is to be filtered or not.
   */
  public boolean getFilter() {
    return _filter;
  }
  /**
   * Returns whether whitespace in the body is to be preserved or not.
   */
  public boolean getWhitespace() {
    return _whitespace;
  }
  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
    _filter = false;
    _whitespace = false;
  }
  /**
   * Sets whether the body is to be filtered or not.
   *
   * @param  filter  <code>true</code> if the body is to be filtered; <code>false</code> otherwise
   */
  public void setFilter(boolean filter) {
    _filter = filter;
  }
  /**
   * Sets whether whitespace in the body is to be preserved or not.
   *
   * @param  preserve  <code>true</code> if whitespace is to be preserved; <code>false</code> otherwise
   */
  public void setWhitespace(boolean preserve) {
    _whitespace = preserve;
  }
}
