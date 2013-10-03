package com.ardais.bigr.web.taglib;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiLogger;

/**
 */
public class DebugEnabledTag extends BodyTagSupport {

  private boolean _useCommonRoot = true;
  private String _loggerName;

	/**
	 * Creates a <code>DebugEnabledTag</code>.
	 */
	public DebugEnabledTag() {
		super();
	}

  /**
   */
  public int doStartTag() throws JspException {
		Log log = LogFactory.getLog(getLoggerName());
		if (log.isDebugEnabled()) {
	    return EVAL_BODY_BUFFERED;
		}
		else {
	    return SKIP_BODY;
		}
  }

  /**
   */
  public int doAfterBody() throws JspException {
    BodyContent body = getBodyContent();
    if (body != null) {
      JspWriter out = body.getEnclosingWriter();
      try {
         out.write(body.getString());
      } catch (IOException e) {
        throw new JspException("IOException in FilterHtmlTag.doAfterBody");
      }
    }
    return SKIP_BODY;
  }

  /**
   * Returns the logger name to check whether debug is enabled.  The
   * common root will be appended if appropriate.
   * 
   * @return  The logger name.
   */
  protected String getLoggerName() {
  	if (getUseCommonRoot()) {
  		return ApiLogger.getLoggerName(ApiLogger.BIGR_LOGGER_ROOT, _loggerName);
  	}
  	else {
	    return _loggerName;
  	}
  }

  /**
   * Returns whether the common logger root is to be used.
   * 
   * @return  <code>true</code> if the common logger root is to be used;
   * 					 <code>false</code> otherwise
   */
  protected boolean getUseCommonRoot() {
    return _useCommonRoot;
  }

  /**
   * Sets the logger name to check whether debug is enabled.
   * 
   * @param  loggerName  the logger name
   */
  public void setLoggerName(String loggerName) {
    _loggerName = loggerName;
  }

  /**
   * Sets whether the common logger root is to be used.
   *
   * @param  useCommonRoot  <code>true</code> if the common logger root
   * 												 is to be used; <code>false</code> otherwise
   */
  public void setUseCommonRoot(boolean useCommonRoot) {
    _useCommonRoot = useCommonRoot;
  }

  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
	  _useCommonRoot = true;
  	_loggerName = null;
  }
}
