package com.ardais.bigr.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.ardais.bigr.api.ApiLogger;

public class DiagnosticTestTag extends TagSupport {

  private String _conceptId;
  private String _valueSet;
  private String _resultList;
  private String _label;
  
  public DiagnosticTestTag() {
    super();
  }

  public int doStartTag() throws JspException {
     try {
        pageContext.getRequest().setAttribute("diagnosticConceptId", getConceptId());
        pageContext.getRequest().setAttribute("diagnosticValueSet", getValueSet());
        pageContext.getRequest().setAttribute("diagnosticResultList", getResultList());
        pageContext.getRequest().setAttribute("testLabel", getLabel());
        pageContext.include("/hiddenJsps/library/DiagnosticTestTemplate.jsp");
      }
      catch (Exception e) {
        String msg = "Exception in JSP tag implementation.";
        ApiLogger.log(msg, e);
        throw new JspException(msg);
      }
    
    return EVAL_BODY_INCLUDE;
  }

  /**
   * @return
   */
  public String getValueSet() {
    return _valueSet;
  }

  /**
   * @return
   */
  public String getConceptId() {
    return _conceptId;
  }

  /**
   * @return
   */
  public String getLabel() {
    return _label;
  }

  /**
   * @return
   */
  public String getResultList() {
    return _resultList;
  }

  /**
   * @param string
   */
  public void setValueSet(String string) {
    _valueSet = string;
  }

  /**
   * @param string
   */
  public void setConceptId(String string) {
    _conceptId = string;
  }

  /**
   * @param string
   */
  public void setLabel(String string) {
    _label = string;
  }

  /**
   * @param string
   */
  public void setResultList(String string) {
    _resultList = string;
  }

}
