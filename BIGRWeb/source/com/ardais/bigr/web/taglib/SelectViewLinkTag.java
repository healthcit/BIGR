package com.ardais.bigr.web.taglib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.ResponseUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.kc.form.def.BigrFormDefinition;
import com.ardais.bigr.kc.web.form.def.ResultsFormDefinitionForm;
import com.ardais.bigr.library.SampleViewData;
import com.ardais.bigr.library.web.column.SampleColumn;
import com.ardais.bigr.util.Constants;
import com.ardais.bigr.util.UniqueIdGenerator;
import com.gulfstreambio.kc.det.DetService;
import com.gulfstreambio.kc.form.def.data.DataFormDefinition;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionCategory;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionElement;

/**
 * Tag handler for the <code>selectViewLink</code> tag.  This tag
 * generates a link showing the results view currently in use, and allows the user to 
 * specify a different results view or go to the Manage Views screen.
 * 
 * <p>The context in which this tag is used must satisfy several requirements:
 * <ul>
 * <li>The page must include an accompanying SelectViewLinkMenu tag.  Due to issues
 *     that can arise with the menu display, it was necessary to place the code that
 *     generates the menu shown when this link is clicked in a seperate tag to allow
 *     for greater control over where that html is rendered.</li>
 * <li>The page must include bigr.css and common.js.</li>
 * </ul>
 */
public class SelectViewLinkTag extends TagSupport {

  private List _resultsFormDefinitions = null;
  private String _currentResultsFormDefinitionId = null;
  private String _menuId = null;

  public SelectViewLinkTag() {
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
    StringBuffer buf = new StringBuffer(1024);
    //output the javascript this tag needs
    buf.append("<script language=\"JavaScript\">\n");
    buf.append("  function doViewLinkOnClick() {\n");
    buf.append("    if (!isLinkEnabled()) {\n");
    buf.append("      return false;\n");
    buf.append("    }\n");
    buf.append("    var eSrc = event.srcElement;\n");
    buf.append("    if (eSrc.viewMenu != null) {\n");
    buf.append("      var viewMenu = document.all[eSrc.viewMenu];\n");
    buf.append("      var offParent = viewMenu.offsetParent;\n");
    buf.append("      var menuWidth = viewMenu.offsetWidth;\n");
    buf.append("      var menuHeight = viewMenu.offsetHeight;\n");
    buf.append("      var newClientX = event.clientX - 30;\n");
    buf.append("      var newClientY = event.clientY - 30;\n");
    buf.append("      if (newClientX + menuWidth > offParent.clientWidth) {\n");
    buf.append("        newClientX = offParent.clientWidth - menuWidth;\n");
    buf.append("      }\n");
    buf.append("      if (newClientX < 0) {\n");
    buf.append("        newClientX = 0;\n");
    buf.append("      }\n");
    buf.append("      if (newClientY + menuHeight > offParent.clientHeight) {\n");
    buf.append("        newClientY = offParent.clientHeight - menuHeight;\n");
    buf.append("      }\n");
    buf.append("      if (newClientY < 0) newClientY = 0;\n");
    buf.append("      viewMenu.style.left = newClientX + offParent.scrollLeft;\n");
    buf.append("      viewMenu.style.top = newClientY + offParent.scrollTop;\n");
    buf.append("      viewMenu.style.visibility = 'visible';\n");
    buf.append("      viewMenu.style.zIndex = 1000;\n");
    buf.append("      viewMenu.setCapture();\n");
    buf.append("      event.returnValue = false;\n");
    buf.append("    }\n");
    buf.append("  }\n\n");
    buf.append("</script>\n");
    //output the link
    buf.append("<small><span class=\"fakeLink\" onclick=\"doViewLinkOnClick()\"");
    buf.append("  viewMenu=\"");
    buf.append(getMenuId());
    buf.append("\">");
    buf.append("View: ");
    buf.append(getCurrentViewName());
    buf.append("</span></small> &nbsp;&nbsp;&nbsp;\n");
    ResponseUtils.write(pageContext, buf.toString());    
    return SKIP_BODY;
  }
  
  private String getCurrentViewName() {
    String returnValue = "Unknown";
    String formId = getCurrentResultsFormDefinitionId();
    if (!ApiFunctions.isEmpty(formId)) {
      if (Constants.DEFAULT_RESULTS_VIEW_ID.equalsIgnoreCase(formId)) {
        returnValue = BigrFormDefinition.SYSTEM_DEFAULT_RESULTS_FORM_NAME;
      }
      else {
        if (!ApiFunctions.isEmpty(getResultsFormDefinitions())) {
          Iterator iterator = getResultsFormDefinitions().iterator();
          boolean found = false;
          while (!found && iterator.hasNext()) {
            BigrFormDefinition formDef = (BigrFormDefinition)iterator.next();
            if (formId.equalsIgnoreCase(formDef.getFormDefinitionId())) {
              returnValue = Escaper.htmlEscapeAndPreserveWhitespace(formDef.getShortName());
              found = true;
            }
          }
        }
      }
    }
    return returnValue;
  }

}
