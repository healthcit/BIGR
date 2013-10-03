package com.gulfstreambio.gboss.web.taglib;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.ResponseUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.gulfstreambio.gboss.Gboss;
import com.gulfstreambio.gboss.GbossAde;
import com.gulfstreambio.gboss.GbossAdeElement;
import com.gulfstreambio.gboss.GbossAdes;
import com.gulfstreambio.gboss.GbossCategory;
import com.gulfstreambio.gboss.GbossDataElement;
import com.gulfstreambio.gboss.GbossDataElementTaxonomy;
import com.gulfstreambio.gboss.GbossUnit;
import com.gulfstreambio.gboss.GbossUnits;
import com.gulfstreambio.gboss.GbossValue;
import com.gulfstreambio.gboss.GbossValueSet;
import com.gulfstreambio.gboss.GbossValueSets;
import com.gulfstreambio.gboss.GbossValues;

public class GbossTag extends TagSupport {

  private Gboss _gboss = null;
  private final String _INDENTATION = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
  
  public GbossTag() {
    super();
  }

  /**
   * @return Returns the gboss.
   */
  public Gboss getGboss() {
    return _gboss;
  }
  
  /**
   * @param gboss The gboss to set.
   */
  public void setGboss(Gboss gboss) {
    _gboss = gboss;
  }

  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
    _gboss = null;
  }
  
  /**
   */
  public int doStartTag() throws JspException {
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest(); 
    String contextPath = request.getContextPath(); 
    String closedImgSource =  contextPath + "/images/MenuClosed.gif";
    String openImgSource =  contextPath + "/images/MenuOpened.gif";
    
    StringBuffer buf = new StringBuffer(4096);
    renderJavascript(buf, closedImgSource, openImgSource);
    renderDataElementTaxonomy(buf, closedImgSource);
    buf.append("<br>\n");
    renderAdes(buf, closedImgSource);
    buf.append("<br>\n");
    renderUnits(buf, closedImgSource);
    buf.append("<br>\n");
    renderValues(buf, closedImgSource);
    buf.append("<br>\n");
    renderValueSets(buf, closedImgSource);
    ResponseUtils.write(pageContext, buf.toString());    
    return SKIP_BODY;
  }
  
  private void renderJavascript(StringBuffer buf, String closedImg, String openImg) {
    buf.append("<script type=\"text/javascript\">\n");
    buf.append("//expand or collapse one of the simple categories\n");
    buf.append("function showOrHideCategory(category){\n");
    buf.append("  var image;\n");
    buf.append("  var displayValue;\n");
    buf.append("  var table = document.all(category);\n");
    buf.append("  var img = document.all(category + 'Img');\n");
    buf.append("  var alt = img.alt.substring(img.alt.indexOf(' '));\n");
    buf.append("  var isClosed = (table.style.display == 'none');\n");
    buf.append("  if (isClosed) {\n");
    buf.append("    image = '");
    buf.append(openImg);
    buf.append("';\n");
    buf.append("    alt = 'Close' + alt;\n");
    buf.append("    displayValue = 'inline';\n");
    buf.append("  }\n");
    buf.append("  else {\n");
    buf.append("    image = '");
    buf.append(closedImg);
    buf.append("';\n");
    buf.append("    alt = 'Open' + alt;\n");
    buf.append("    displayValue = 'none';\n");
    buf.append("  }\n");
    buf.append("  table.style.display = displayValue;\n");
    buf.append("  img.src=image;\n");
    buf.append("  img.alt=alt;\n");
    buf.append("}\n");
    buf.append("</script>\n\n");
  }

  private void renderDataElementTaxonomy(StringBuffer buf, String closedImg) {
    List categoryList = null;
    GbossDataElementTaxonomy det = getGboss().getDataElementTaxonomy();
    if (det != null) {
      categoryList = det.getCategories();
    }
    String headerInfo = "Data Element Taxonomy";
    buf.append("<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\" class=\"foreground\">\n");
    buf.append("<tr class=\"white\">\n");
    buf.append("<td>\n");
    buf.append("<span onClick=\"showOrHideCategory('det')\" onKeyPress=\"showOrHideCategory('det')\">\n");
    buf.append("<img id=\"detImg\" alt=\"Open Data Element Taxonomy\" src=\"");
    buf.append(closedImg);
    buf.append("\"/>&nbsp;<b>");
    buf.append(headerInfo);
    buf.append("</b>\n");
    buf.append("</span>\n");
    buf.append("</td>\n");
    buf.append("</tr>\n");
    buf.append("<tr>\n");
    buf.append("<td>\n");
    buf.append("<table style=\"display: none;\" id=\"det\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\" class=\"foreground\">\n");
    if (ApiFunctions.isEmpty(categoryList)) {
      buf.append("<tr class=\"grey\">\n");
      buf.append("<td>\n");
      buf.append(_INDENTATION);
      buf.append("No Categories were specified.\n");
      buf.append("</td>\n");
      buf.append("</tr>\n");
    }
    else {
      Iterator categoryIterator = categoryList.iterator();
      String categoryRowClass = "grey";
      String categoryId = null;
      while (categoryIterator.hasNext()) {
        GbossCategory category = (GbossCategory)categoryIterator.next();
        buf.append("<tr class=\"");
        buf.append(categoryRowClass);
        buf.append("\">\n");
        buf.append("<td>\n");
        categoryId = "category" + category.getCui() + "CategoriesOrElements";
        buf.append("<span onClick=\"showOrHideCategory('");
        buf.append(categoryId);
        buf.append("')\" onKeyPress=\"showOrHideCategory('");
        buf.append(categoryId);
        buf.append("')\">\n");
        buf.append(_INDENTATION);
        buf.append("<img id=\"");
        buf.append(categoryId + "Img");
        buf.append("\" alt=\"Open Category\" src=\"");
        buf.append(closedImg);
        buf.append("\"/>&nbsp;");
        buf.append(category.toHtml());
        buf.append("\n</span>\n");
        buf.append("</td>\n");
        buf.append("</tr>\n");
        renderChildCategoriesOrDataElements(buf, category.getCategories(), 
            category.getDataElements(), closedImg, categoryId, 0, categoryRowClass);
        if (categoryRowClass.equalsIgnoreCase("white")) {
          categoryRowClass = "grey";
        }
        else {
          categoryRowClass = "white";
        }
      }
    }
    buf.append("</table>\n");
    buf.append("</td>\n");
    buf.append("</tr>\n");
    buf.append("</table>\n");
  }

  private void renderChildCategoriesOrDataElements(StringBuffer buf, List categoryList, 
                                                   List dataElementList, String closedImg, 
                                                   String parentId, int level, String rowClass) {
    buf.append("<tr>\n");
    buf.append("<td>\n");
    buf.append("<table style=\"display: none;\" id=\"");
    buf.append(parentId);
    buf.append("\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\" class=\"foreground\">\n");
    if (ApiFunctions.isEmpty(categoryList) && ApiFunctions.isEmpty(dataElementList)) {
      buf.append("<tr class=\"grey\">\n");
      buf.append("<td>\n");
      buf.append(_INDENTATION);
      buf.append("No Categories or Data Elements were specified.\n");
      buf.append("</td>\n");
      buf.append("</tr>\n");
    }
    else {
      if (!ApiFunctions.isEmpty(categoryList)) {
        Iterator categoryIterator = categoryList.iterator();
        while (categoryIterator.hasNext()) {
          GbossCategory category = (GbossCategory)categoryIterator.next();
          buf.append("<tr class=\"");
          buf.append(rowClass);
          buf.append("\">\n");
          buf.append("<td>\n");
          String childId = parentId + category.getCui() + "CategoriesOrElements";
          for (int i=0; i<=level; i++) {
            buf.append(_INDENTATION);
          }
          buf.append("\n<span onClick=\"showOrHideCategory('");
          buf.append(childId);
          buf.append("')\" onKeyPress=\"showOrHideCategory('");
          buf.append(childId);
          buf.append("')\">\n");
          buf.append(_INDENTATION);
          buf.append("<img id=\"");
          buf.append(childId + "Img");
          buf.append("\" alt=\"Open Category\" src=\"");
          buf.append(closedImg);
          buf.append("\"/>&nbsp;");
          buf.append(category.toHtml());
          buf.append("\n</span>\n");
          buf.append("</td>\n");
          buf.append("</tr>\n");
          renderChildCategoriesOrDataElements(buf, category.getCategories(), 
              category.getDataElements(), closedImg, childId, level+1, rowClass);
        }
      }
      if (!ApiFunctions.isEmpty(dataElementList)) {
        Iterator dataElementIterator = dataElementList.iterator();
        while (dataElementIterator.hasNext()) {
          GbossDataElement dataElement = (GbossDataElement)dataElementIterator.next();
          buf.append("<tr class=\"");
          buf.append(rowClass);
          buf.append("\">\n");
          buf.append("<td>\n");
          for (int i=0; i<=level; i++) {
            buf.append(_INDENTATION);
          }
          buf.append(_INDENTATION);
          buf.append(dataElement.toHtml());
          buf.append("\n");
          buf.append("</td>\n");
          buf.append("</tr>\n");
        }
      }
    }
    buf.append("</table>\n");
    buf.append("</td>\n");
    buf.append("</tr>\n");
  }


  private void renderAdes(StringBuffer buf, String closedImg) {
    List adeList = null;
    GbossAdes ades = getGboss().getAncillaryDataElements();
    if (ades != null) {
      adeList = ades.getAncillaryDataElements();
    }
    String headerInfo = "Ancillary Data Elements (";
    if (ApiFunctions.isEmpty(adeList)) {
      headerInfo = headerInfo + "0)";
    }
    else {
      headerInfo = headerInfo + adeList.size() + ")";
    }
    buf.append("<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\" class=\"foreground\">\n");
    buf.append("<tr class=\"white\">\n");
    buf.append("<td>\n");
    buf.append("<span onClick=\"showOrHideCategory('ades')\" onKeyPress=\"showOrHideCategory('ades')\">\n");
    buf.append("<img id=\"adesImg\" alt=\"Open Ancillary Data Elements\" src=\"");
    buf.append(closedImg);
    buf.append("\"/>&nbsp;<b>");
    buf.append(headerInfo);
    buf.append("</b>\n");
    buf.append("</span>\n");
    buf.append("</td>\n");
    buf.append("</tr>\n");
    buf.append("<tr>\n");
    buf.append("<td>\n");
    buf.append("<table style=\"display: none;\" id=\"ades\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\" class=\"foreground\">\n");
    if (ApiFunctions.isEmpty(adeList)) {
      buf.append("<tr class=\"grey\">\n");
      buf.append("<td>\n");
      buf.append(_INDENTATION);
      buf.append("No Ancillary Data Elements were specified.\n");
      buf.append("</td>\n");
      buf.append("</tr>\n");
    }
    else {
      Iterator adeIterator = adeList.iterator();
      String adeRowClass = "grey";
      String adeElementsId = null;
      while (adeIterator.hasNext()) {
        GbossAde ade = (GbossAde)adeIterator.next();
        buf.append("<tr class=\"");
        buf.append(adeRowClass);
        buf.append("\">\n");
        buf.append("<td>\n");
        adeElementsId = "ade" + ade.getCui() + "Elements";
        buf.append("<span onClick=\"showOrHideCategory('");
        buf.append(adeElementsId);
        buf.append("')\" onKeyPress=\"showOrHideCategory('");
        buf.append(adeElementsId);
        buf.append("')\">\n");
        buf.append(_INDENTATION);
        buf.append("<img id=\"");
        buf.append(adeElementsId + "Img");
        buf.append("\" alt=\"Open Value Set\" src=\"");
        buf.append(closedImg);
        buf.append("\"/>&nbsp;");
        buf.append(ade.toHtml());
        buf.append("\n</span>\n");
        buf.append("</td>\n");
        buf.append("</tr>\n");
        renderChildAdeElements(buf, ade.getAncillaryDataElementElements(), closedImg, adeElementsId, adeRowClass);
        if (adeRowClass.equalsIgnoreCase("white")) {
          adeRowClass = "grey";
        }
        else {
          adeRowClass = "white";
        }
      }
    }
    buf.append("</table>\n");
    buf.append("</td>\n");
    buf.append("</tr>\n");
    buf.append("</table>\n");
  }

  private void renderChildAdeElements(StringBuffer buf, List adeElementList, String closedImg, 
                                 String parentId, String rowClass) {
    buf.append("<tr>\n");
    buf.append("<td>\n");
    buf.append("<table style=\"display: none;\" id=\"");
    buf.append(parentId);
    buf.append("\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\" class=\"foreground\">\n");
    if (ApiFunctions.isEmpty(adeElementList)) {
      buf.append("<tr class=\"grey\">\n");
      buf.append("<td>\n");
      buf.append(_INDENTATION);
      buf.append("No Ancillary Data Element Elements were specified.\n");
      buf.append("</td>\n");
      buf.append("</tr>\n");
    }
    else {
      Iterator adeElementIterator = adeElementList.iterator();
      while (adeElementIterator.hasNext()) {
        GbossAdeElement adeElement = (GbossAdeElement)adeElementIterator.next();
        buf.append("<tr class=\"");
        buf.append(rowClass);
        buf.append("\">\n");
        buf.append("<td>\n");
        buf.append(_INDENTATION);
        buf.append(_INDENTATION);
        buf.append(adeElement.toHtml());
        buf.append("\n");
        buf.append("</td>\n");
        buf.append("</tr>\n");
      }
    }
    buf.append("</table>\n");
    buf.append("</td>\n");
    buf.append("</tr>\n");
  }
  
  private void renderUnits(StringBuffer buf, String closedImg) {
    List unitList = null;
    GbossUnits units = getGboss().getUnits();
    if (units != null) {
      unitList = units.getUnits();
    }
    String headerInfo = "GbossUnits (";
    if (ApiFunctions.isEmpty(unitList)) {
      headerInfo = headerInfo + "0)";
    }
    else {
      headerInfo = headerInfo + unitList.size() + ")";
    }
    buf.append("<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\" class=\"foreground\">\n");
    buf.append("<tr class=\"white\">\n");
    buf.append("<td>\n");
    buf.append("<span onClick=\"showOrHideCategory('units')\" onKeyPress=\"showOrHideCategory('units')\">\n");
    buf.append("<img id=\"unitsImg\" alt=\"Open GbossUnits\" src=\"");
    buf.append(closedImg);
    buf.append("\"/>&nbsp;<b>");
    buf.append(headerInfo);
    buf.append("</b>\n");
    buf.append("</span>\n");
    buf.append("</td>\n");
    buf.append("</tr>\n");
    buf.append("<tr>\n");
    buf.append("<td>\n");
    buf.append("<table style=\"display: none;\" id=\"units\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\" class=\"foreground\">\n");
    if (ApiFunctions.isEmpty(unitList)) {
      buf.append("<tr class=\"grey\">\n");
      buf.append("<td>\n");
      buf.append(_INDENTATION);
      buf.append("No units were specified.\n");
      buf.append("</td>\n");
      buf.append("</tr>\n");
    }
    else {
      Iterator unitIterator = unitList.iterator();
      String unitRowClass = "grey";
      while (unitIterator.hasNext()) {
        GbossUnit unit = (GbossUnit)unitIterator.next();
        buf.append("<tr class=\"");
        buf.append(unitRowClass);
        buf.append("\">\n");
        buf.append("<td>\n");
        buf.append(_INDENTATION);
        buf.append(unit.toHtml());
        buf.append("\n");
        buf.append("</td>\n");
        buf.append("</tr>\n");
        if (unitRowClass.equalsIgnoreCase("white")) {
          unitRowClass = "grey";
        }
        else {
          unitRowClass = "white";
        }
      }
    }
    buf.append("</table>\n");
    buf.append("</td>\n");
    buf.append("</tr>\n");
    buf.append("</table>\n");
  }
  
  private void renderValues(StringBuffer buf, String closedImg) {
    List valueList = null;
    GbossValues values = getGboss().getValues();
    if (values != null) {
      valueList = values.getValues();
    }
    String headerInfo = "Values (";
    if (ApiFunctions.isEmpty(valueList)) {
      headerInfo = headerInfo + "0)";
    }
    else {
      headerInfo = headerInfo + valueList.size() + ")";
    }
    buf.append("<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\" class=\"foreground\">\n");
    buf.append("<tr class=\"white\">\n");
    buf.append("<td>\n");
    buf.append("<span onClick=\"showOrHideCategory('values')\" onKeyPress=\"showOrHideCategory('values')\">\n");
    buf.append("<img id=\"valuesImg\" alt=\"Open Values\" src=\"");
    buf.append(closedImg);
    buf.append("\"/>&nbsp;<b>");
    buf.append(headerInfo);
    buf.append("</b>\n");
    buf.append("</span>\n");
    buf.append("</td>\n");
    buf.append("</tr>\n");
    renderChildValues(buf, valueList, closedImg, "values", 0, "grey", true);
    buf.append("</table>\n");
  }

  private void renderValueSets(StringBuffer buf, String closedImg) {
    List valueSetList = null;
    GbossValueSets valueSets = getGboss().getValueSets();
    if (valueSets != null) {
      valueSetList = valueSets.getValueSets();
    }
    String headerInfo = "Value Sets (";
    if (ApiFunctions.isEmpty(valueSetList)) {
      headerInfo = headerInfo + "0)";
    }
    else {
      headerInfo = headerInfo + valueSetList.size() + ")";
    }
    buf.append("<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\" class=\"foreground\">\n");
    buf.append("<tr class=\"white\">\n");
    buf.append("<td>\n");
    buf.append("<span onClick=\"showOrHideCategory('valueSets')\" onKeyPress=\"showOrHideCategory('valueSets')\">\n");
    buf.append("<img id=\"valueSetsImg\" alt=\"Open Value Sets\" src=\"");
    buf.append(closedImg);
    buf.append("\"/>&nbsp;<b>");
    buf.append(headerInfo);
    buf.append("</b>\n");
    buf.append("</span>\n");
    buf.append("</td>\n");
    buf.append("</tr>\n");
    buf.append("<tr>\n");
    buf.append("<td>\n");
    buf.append("<table style=\"display: none;\" id=\"valueSets\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\" class=\"foreground\">\n");
    if (ApiFunctions.isEmpty(valueSetList)) {
      buf.append("<tr class=\"grey\">\n");
      buf.append("<td>\n");
      buf.append(_INDENTATION);
      buf.append("No Value Sets were specified.\n");
      buf.append("</td>\n");
      buf.append("</tr>\n");
    }
    else {
      Iterator valueSetIterator = valueSetList.iterator();
      String valueSetRowClass = "grey";
      String valueSetValuesId = null;
      while (valueSetIterator.hasNext()) {
        GbossValueSet valueSet = (GbossValueSet)valueSetIterator.next();
        buf.append("<tr class=\"");
        buf.append(valueSetRowClass);
        buf.append("\">\n");
        buf.append("<td>\n");
        valueSetValuesId = "valueSet" + valueSet.getCui() + "Values";
        buf.append("<span onClick=\"showOrHideCategory('");
        buf.append(valueSetValuesId);
        buf.append("')\" onKeyPress=\"showOrHideCategory('");
        buf.append(valueSetValuesId);
        buf.append("')\">\n");
        buf.append(_INDENTATION);
        buf.append("<img id=\"");
        buf.append(valueSetValuesId + "Img");
        buf.append("\" alt=\"Open Value Set\" src=\"");
        buf.append(closedImg);
        buf.append("\"/>&nbsp;");
        buf.append(valueSet.toHtml());
        buf.append("\n</span>\n");
        buf.append("</td>\n");
        buf.append("</tr>\n");
        renderChildValues(buf, valueSet.getValues(), closedImg, valueSetValuesId, 0, valueSetRowClass, false);
        if (valueSetRowClass.equalsIgnoreCase("white")) {
          valueSetRowClass = "grey";
        }
        else {
          valueSetRowClass = "white";
        }
      }
    }
    buf.append("</table>\n");
    buf.append("</td>\n");
    buf.append("</tr>\n");
    buf.append("</table>\n");
  }

  private void renderChildValues(StringBuffer buf, List valueList, String closedImg, 
                                 String parentId, int level, String rowClass, boolean alternateRowClass) {
    buf.append("<tr>\n");
    buf.append("<td>\n");
    buf.append("<table style=\"display: none;\" id=\"");
    buf.append(parentId);
    buf.append("\" border=\"0\" cellspacing=\"1\" cellpadding=\"3\" class=\"foreground\">\n");
    if (ApiFunctions.isEmpty(valueList)) {
      buf.append("<tr class=\"grey\">\n");
      buf.append("<td>\n");
      buf.append(_INDENTATION);
      buf.append("No Values were specified.\n");
      buf.append("</td>\n");
      buf.append("</tr>\n");
    }
    else {
      Iterator valueIterator = valueList.iterator();
      while (valueIterator.hasNext()) {
        GbossValue value = (GbossValue)valueIterator.next();
        buf.append("<tr class=\"");
        buf.append(rowClass);
        buf.append("\">\n");
        buf.append("<td>\n");
        if (!ApiFunctions.isEmpty(value.getValues())) {
          String childId = parentId + value.getCui() + "Values";
          buf.append("<span onClick=\"showOrHideCategory('");
          buf.append(childId);
          buf.append("')\" onKeyPress=\"showOrHideCategory('");
          buf.append(childId);
          buf.append("')\">\n");
          for (int i=0; i<=level; i++) {
            buf.append(_INDENTATION);
          }
          buf.append(_INDENTATION);
          buf.append("<img id=\"");
          buf.append(childId + "Img");
          buf.append("\" alt=\"Open Value\" src=\"");
          buf.append(closedImg);
          buf.append("\"/>&nbsp;");
          buf.append(value.toHtml());
          buf.append("\n</span>\n");
          buf.append("</td>\n");
          buf.append("</tr>\n");
          renderChildValues(buf, value.getValues(), closedImg, childId, level+1, rowClass, alternateRowClass);
        }
        else {
          for (int i=0; i<=level; i++) {
            buf.append(_INDENTATION);
          }
          buf.append(_INDENTATION);
          buf.append(value.toHtml());
          buf.append("\n");
          buf.append("</td>\n");
          buf.append("</tr>\n");
        }
        if (alternateRowClass) {
          if (rowClass.equalsIgnoreCase("white")) {
            rowClass = "grey";
          }
          else {
            rowClass = "white";
          }
        }
      }
    }
    buf.append("</table>\n");
    buf.append("</td>\n");
    buf.append("</tr>\n");
  }

}
