package com.ardais.bigr.web.taglib;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.WebUtils;
import org.apache.struts.util.ResponseUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;

/**
 * Tag handler for the <code>selectViewLinkMenu</code> tag.  This tag
 * generates the menu shown when a user clicks a select view link (see the
 * SelectViewLinkTag for more details.
 * 
 * <p>The context in which this tag is used must satisfy several requirements:
 * <ul>
 * <li>The page must include bigr.css.</li>
 * <li>The page must set the variable _isPageReadyForInteraction to true as the very last thing that
 *     the onload handler does.</li>
 * <li>The page must have a javascript function named reloadTable that takes two parameters, which
 *     are the id of the selected results form definition and the window from which
 *     that definition was chosen.</li>
 * </ul>
 */
public class SelectViewLinkMenuTag extends TagSupport {

  private List _resultsFormDefinitions = null;
  private String _currentResultsFormDefinitionId = null;
  private String _menuId = null;

  public SelectViewLinkMenuTag() {
    super();
  }

  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
    _resultsFormDefinitions = null;
  }
  
  /**
   * @return Returns the currentResultsFormDefinitionId.
   */
  public String getCurrentResultsFormDefinitionId() {
    return _currentResultsFormDefinitionId;
  }
  
  /**
   * @return Returns the resultsFormDefinitions.
   */
  public List getResultsFormDefinitions() {
    return _resultsFormDefinitions;
  }

  public String getMenuId() {
    return _menuId;
  }

  /**
   * @param currentResultsFormDefinitionId The currentResultsFormDefinitionId to set.
   */
  public void setCurrentResultsFormDefinitionId(String currentResultsFormDefinitionId) {
    _currentResultsFormDefinitionId = currentResultsFormDefinitionId;
  }
  
  /**
   * @param resultsFormDefinitions The resultsFormDefinitions to set.
   */
  public void setResultsFormDefinitions(List resultsFormDefinitions) {
    _resultsFormDefinitions = resultsFormDefinitions;
  }
  
  public void setMenuId(String menuId) {
    _menuId = menuId;
  }
  
  /**
   */
  public int doStartTag() throws JspException {

	  final SecurityInfo securityInfo = WebUtils.getSecurityInfo((HttpServletRequest)pageContext.getRequest());

    StringBuffer buf = new StringBuffer(1024);
    //output the javascript this tag needs
    buf.append("<script language=\"JavaScript\">\n");
    buf.append("  // Highlight an item in the view menu.\n");
    buf.append("  function highlightViewMenuItem() {\n");
    buf.append("    if ((event.clientX > 0) && (event.clientY > 0) && (event.clientX < document.body.offsetWidth) && (event.clientY<document.body.offsetHeight)) {\n");
    buf.append("      if (event.srcElement.getAttribute(\"menuFunction\") != null) {\n");
    buf.append("        var theStyle = event.srcElement.style;\n");
    buf.append("        theStyle.backgroundColor=\"#000066\";\n");
    buf.append("        theStyle.color=\"#FFFFFF\";\n");
    buf.append("      }\n");
    buf.append("    }\n");
    buf.append("  }\n\n");
    buf.append("  // Unhighlight an item in the view menu.\n");
    buf.append("  function unHighlightViewMenuItem() {\n");
    buf.append("    if ((event.clientX > 0) && (event.clientY > 0) && (event.clientX < document.body.offsetWidth) && (event.clientY<document.body.offsetHeight)) {\n");
    buf.append("      if (event.srcElement.getAttribute(\"menuFunction\") != null) {\n");
    buf.append("        var theStyle = event.srcElement.style;\n");
    buf.append("        theStyle.backgroundColor=\"#E0E0E0\";\n");
    buf.append("        theStyle.color=\"#000000\";\n");
    buf.append("      }\n");
    buf.append("    }\n");
    buf.append("  }\n\n");
    buf.append("  function onClickViewMenu() {\n");
    buf.append("    if (!_isPageReadyForInteraction) return;\n");
    buf.append("    var eSrc = event.srcElement;\n");
    buf.append("    if (eSrc.menuFunction != null) {\n");
    buf.append("      eval(eSrc.menuFunction);\n");
    buf.append("      event.returnValue = false;\n");
    buf.append("    }\n");
    buf.append("    unHighlightViewMenuItem();\n");
    buf.append("    var viewMenu = document.all['");
    buf.append(getMenuId());
    buf.append("'];\n");
    buf.append("    viewMenu.style.visibility = 'hidden';\n");
    buf.append("    viewMenu.releaseCapture();\n");
    buf.append("  }\n\n");
    buf.append("  function onClickManageViews() {\n");
    buf.append("    var w = window.open('");
    buf.append("/BIGR/kc/resultsformdef/start.do");
    buf.append("?formDefinition.formDefinitionId=");
    buf.append(getCurrentResultsFormDefinitionId());
    buf.append("','ManageViews','scrollbars=yes,resizable=yes,status=yes,width=845,height=695,left=0,top=0');\n");
    buf.append("    w.focus();\n");
    buf.append("  }\n\n");
    buf.append("</script>\n");
    //now output the html for this tag
    buf.append("<table id=\"");
    buf.append(getMenuId());
    buf.append("\" class=\"contextMenu\"\n");
    buf.append("  onmouseover=\"highlightViewMenuItem();\" onmouseout=\"unHighlightViewMenuItem();\"\n");
    buf.append("  onclick=\"onClickViewMenu();\"\n");
    buf.append("  border=\"0\" cellspacing=\"0\" cellpadding=\"3\">\n");
    //for every results form definition passed in, create a menu choice
    if (!ApiFunctions.isEmpty(getResultsFormDefinitions())) {
      Iterator iterator = getResultsFormDefinitions().iterator();
      while (iterator.hasNext()) {
        BigrFormDefinition formDef = (BigrFormDefinition)iterator.next();
        buf.append("  <tr><td menuFunction=\"reloadTable('");
        buf.append(formDef.getFormDefinitionId());
        buf.append("');\">");
        includeIndicator(buf, formDef.getFormDefinitionId());
        buf.append("View: ");
        buf.append(Escaper.htmlEscapeAndPreserveWhitespace(formDef.getName()));
        buf.append("</td></tr>\n");
      }
    }
    //include a choice for editing views
	  if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_ORM_ACCESS_VIEW_MANAGEMENT))
	  {
		  buf.append("  <tr><td menuFunction=\"onClickManageViews();\">");
		  includeIndicator(buf, null);
		  buf.append("Manage Results Views...</td></tr>\n");
	  }
    buf.append("</table>\n");
    ResponseUtils.write(pageContext, buf.toString());    
    return SKIP_BODY;
  }
  
  //method to include a visual indicator of the currently selected menu choice
  private void includeIndicator(StringBuffer buf, String id) {
    if (getCurrentResultsFormDefinitionId().equalsIgnoreCase(id)) {
      HttpServletRequest request = (HttpServletRequest) pageContext.getRequest(); 
      String contextPath = request.getContextPath(); 
      String imgSource =  contextPath + "/images/rarrow.gif";
      buf.append("<img src=\"");
      buf.append(imgSource);
      buf.append("\"/>&nbsp;");
    }
    else {
      buf.append("&nbsp;");
      buf.append("&nbsp;");
      buf.append("&nbsp;");
      buf.append("&nbsp;");
      buf.append("&nbsp;");
    }
  }

}
