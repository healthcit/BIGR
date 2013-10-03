package com.ardais.bigr.web.taglib;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.javabeans.BoxLayoutDto;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.ardais.bigr.util.Constants;

/**
 * Implements the BIGR <code>box</code> tag. This tag assumes that it is the body of a table.
 */
public class BoxTag extends BaseHandlerTag {

  private final static String ZERO = "0";

  private final static String CELL_TYPE_EMPTY = "empty";
  private final static String CELL_TYPE_MIXED = "mixed";
  private final static String CELL_TYPE_READ_ONLY = "readonly";
  private final static String CELL_TYPE_INPUT = "input";
  private final static Set _validCellTypes;
  static {
    _validCellTypes = new HashSet();
    _validCellTypes.add(CELL_TYPE_EMPTY);
    _validCellTypes.add(CELL_TYPE_MIXED);
    _validCellTypes.add(CELL_TYPE_READ_ONLY);
    _validCellTypes.add(CELL_TYPE_INPUT);
  }

  private final static String NBSP =
    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

  private String _boxLayoutName;
  private String _boxLayoutProperty;
  private BoxLayoutDto _boxLayoutDto;

  private String _cellType;
  private boolean _grouped;
  private boolean _persistValue;

  private String _tabIndexStartPos;

  private String _boxIndex;
  private String _boxFormName;
  private String _boxFormVar;

  private String _boxCellContentVar;
  private String _sampleTypeVar;
  private String _sampleAliasVar;
  
  private List _highlightList;
  private boolean _printerFriendly;
  
  private String _inputSize;
  private String _inputMaxLength;
  
  private String _highlightMessage;
  private String _highlightColor;

	/**
	 * Creates a new <code>SelectListTag</code>.
	 */
	public BoxTag() {
		super();
	}

	/**
	 * Writes the SELECT element to the HTTP response.
	 */
	public int doEndTag() throws JspException {
    
    // Validate attributes.
    validate();

    BoxLayoutDto boxLayoutDto = getBoxLayoutDto();

    int boxIndex = Integer.parseInt(getBoxIndex());
    int numberOfRows = boxLayoutDto.getNumberOfRows();
    int numberOfColumns = boxLayoutDto.getNumberOfColumns();
    
    String cellType = getCellType();
    boolean readOnly = CELL_TYPE_READ_ONLY.equals(cellType);
    boolean mixed = CELL_TYPE_MIXED.equals(cellType);
    boolean empty = CELL_TYPE_EMPTY.equals(cellType);
    boolean input = CELL_TYPE_INPUT.equals(cellType);
    
    ServletRequest request = pageContext.getRequest();
    
    List highlightList = getHighlightList();

    int cellRef = 1;

    
    int currentRow = 0;
    int currentColumn = 0;
    
    if (readOnly) {
      int lastColumnInLoop = 0;
      int columnsDisplayedSoFar = 0;
      int numberOfColumnsPerLine = numberOfColumns;
      
      //if the number of columns in the layout exceeds the maximum number of columns we can
      //print on one page and we are displayign in a printer friendly way, restrict the
      //number of columns per line to the maximum number that can be printed on a page.
      if (numberOfColumns > Constants.BOX_LAYOUT_MAX_COLUMN && isPrinterFriendly()) {
        numberOfColumnsPerLine = Constants.BOX_LAYOUT_MAX_COLUMN;
      }
    
      if (numberOfColumns > Constants.BOX_LAYOUT_MAX_COLUMN && isPrinterFriendly()) {
        ResponseUtils.write(pageContext, "<hr/>");
      }

      // MR 7749
      // If the number of columns in box layout is less than Constants.BOX_LAYOUT_MAX_COLUMN,
      // then this loop will be executed once. This will create a table with columns as defined
      // by the layout and one more column (1st) to display row identifier. 
      // The table will have two rows more than the row defined by the layout.
      // One extra row (1st) to display the column identifier as header, another row (last) to 
      // display the column identifier as footer. 
      // This loop will execute more than once, if the total number of columns in the box layout
      // exceeds the value defined by Constants.BOX_LAYOUT_MAX_COLUMN.
      // In each loop it creates a table with number of columns as defined by 
      // Constants.BOX_LAYOUT_MAX_COLUMN. In subsequest loop, it will display remaining columns.
      // Loop will continue until the last column of the last row is displayed.
      do {
        ResponseUtils.write(pageContext, "<table class=\"background\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" align=\"center\">");
        ResponseUtils.write(pageContext, "<tr");
        BigrTagUtils.writeAttribute(pageContext, "class", "white");
        ResponseUtils.write(pageContext, ">");
        //<tr class="white"> 

        ResponseUtils.write(pageContext, "<td");
        BigrTagUtils.writeAttribute(pageContext, "class", "grey");
        ResponseUtils.write(pageContext, ">&nbsp;</td>");
        //  <td class="grey">&nbsp;</td>
        currentColumn = 0;
        // display the header for the columns
        for (int column = 1; column <= numberOfColumnsPerLine && currentColumn < numberOfColumns; column++) {
          currentColumn = columnsDisplayedSoFar + column;
          ResponseUtils.write(pageContext, "<td");
          BigrTagUtils.writeAttribute(pageContext, "class", "grey");
          ResponseUtils.write(pageContext, "><div");
          BigrTagUtils.writeAttribute(pageContext, "align", "center");
          ResponseUtils.write(pageContext, "><font");
          BigrTagUtils.writeAttribute(pageContext, "size", "1");
          ResponseUtils.write(pageContext, ">");
          ResponseUtils.write(pageContext, BoxLayoutUtils.convertToXaxisLabel(currentColumn, boxLayoutDto));
          ResponseUtils.write(pageContext, "</font></div></td>");
        }
        ResponseUtils.write(pageContext, "</tr>");

        for (int row = 1; row <= numberOfRows; row++) {

          ResponseUtils.write(pageContext, "<tr");
          BigrTagUtils.writeAttribute(pageContext, "class", "white");
          ResponseUtils.write(pageContext, ">");
  
          ResponseUtils.write(pageContext, "<td");
          BigrTagUtils.writeAttribute(pageContext, "class", "grey");
          ResponseUtils.write(pageContext, "><div");
          BigrTagUtils.writeAttribute(pageContext, "align", "center");
          ResponseUtils.write(pageContext, "><font");
          BigrTagUtils.writeAttribute(pageContext, "size", "1");
          ResponseUtils.write(pageContext, ">");
          ResponseUtils.write(pageContext, BoxLayoutUtils.convertToYaxisLabel(row, boxLayoutDto));
          ResponseUtils.write(pageContext, "</font></td>");
          ResponseUtils.write(pageContext, "</div></td>");
          //    <td class="grey"><div align="center"><b><%=row%></b></div></td>
          currentColumn = 0;
          // display the number of columns as defined by Constants.BOX_LAYOUT_MAX_COLUMN, if the 
          // total number of columns for the layout is more than Constants.BOX_LAYOUT_MAX_COLUMN
          for (int column = 1; column <= numberOfColumnsPerLine && currentColumn < numberOfColumns; column++) {
            currentColumn = columnsDisplayedSoFar + column;
            cellRef = (row - 1) * numberOfColumns + currentColumn;
  
            String property = null;
            String value = null;
            if (isGrouped()) {
              property = getBoxFormVar() + "[" + boxIndex + "]." + getBoxCellContentVar() + "[" + cellRef + "]";
              value = (String)RequestUtils.lookup(pageContext, getBoxFormName(), property, null);
            }
            else {
              property = getBoxCellContentVar() + cellRef;
              value = request.getParameter(property);
              if (ApiFunctions.isEmpty(value)) {
                value = (String)request.getAttribute(property);
              }
            }
          
            // Check if the value needs to be persisted.
            if(isPersistValue()) {
              ResponseUtils.write(pageContext, "<input");
              BigrTagUtils.writeAttribute(pageContext, "type", "hidden");
              BigrTagUtils.writeAttribute(pageContext, "name", property);
              BigrTagUtils.writeAttribute(pageContext, "value", value);
              ResponseUtils.write(pageContext, "/>");
            }
            // <input type="hidden" name="" value=""/>">
          
            ResponseUtils.write(pageContext, "<td");
            BigrTagUtils.writeAttribute(pageContext, "align", "center");
            ResponseUtils.write(pageContext, "><font");
            BigrTagUtils.writeAttribute(pageContext, "size", "1");
  
            boolean highlight = false;
            if (!ApiFunctions.isEmpty(highlightList)) {
              highlight = highlightList.contains(value);
            }
          
            if (highlight) {
              BigrTagUtils.writeAttribute(pageContext, "color", getHighlightColor());
            }
            // end of <font
            ResponseUtils.write(pageContext, ">");
  
            // Check if NBSP is needed.
            if (!ApiFunctions.isEmpty(value)) {
              ResponseUtils.write(pageContext, Escaper.htmlEscapeAndPreserveWhitespace(value));
            }
            else {
              ResponseUtils.write(pageContext, NBSP);
            }
          
            lastColumnInLoop = column;
            ResponseUtils.write(pageContext, "</font>");
            
            if (!ApiFunctions.isEmpty(getSampleAliasVar())) {
              String sampleAliasProperty = null;
              String sampleAliasValue = null;

              if (isGrouped()) {
                sampleAliasProperty = getBoxFormVar() + "[" + boxIndex + "]." + getSampleAliasVar() + "[" + cellRef + "]";
                sampleAliasValue = (String)RequestUtils.lookup(pageContext, getBoxFormName(), sampleAliasProperty, null);
              }
              else {
                sampleAliasProperty = getSampleAliasVar() + cellRef;
                sampleAliasValue = request.getParameter(sampleAliasProperty);
                if (ApiFunctions.isEmpty(sampleAliasValue)) {
                  sampleAliasValue = (String)request.getAttribute(sampleAliasProperty);
                }
              }

              ResponseUtils.write(pageContext, "<br><font");
              BigrTagUtils.writeAttribute(pageContext, "size", "1");
              ResponseUtils.write(pageContext, ">");
              if (!ApiFunctions.isEmpty(sampleAliasValue)) {
                ResponseUtils.write(pageContext, "("+Escaper.htmlEscapeAndPreserveWhitespace(sampleAliasValue)+")");
              }
              else {
                ResponseUtils.write(pageContext, NBSP);
              }
              ResponseUtils.write(pageContext, "</font>");
            }
            
            if (!ApiFunctions.isEmpty(getSampleTypeVar())) {
              String sampleTypeProperty = null;
              String sampleTypeValue = null;

              if (isGrouped()) {
                sampleTypeProperty = getBoxFormVar() + "[" + boxIndex + "]." + getSampleTypeVar() + "[" + cellRef + "]";
                sampleTypeValue = (String)RequestUtils.lookup(pageContext, getBoxFormName(), sampleTypeProperty, null);
              }
              else {
                sampleTypeProperty = getSampleTypeVar() + cellRef;
                sampleTypeValue = request.getParameter(sampleTypeProperty);
                if (ApiFunctions.isEmpty(sampleTypeValue)) {
                  sampleTypeValue = (String)request.getAttribute(sampleTypeProperty);
                }
              }

              ResponseUtils.write(pageContext, "<br><font");
              BigrTagUtils.writeAttribute(pageContext, "size", "1");
              ResponseUtils.write(pageContext, ">");
              if (!ApiFunctions.isEmpty(sampleTypeValue)) {
                ResponseUtils.write(pageContext, "("+sampleTypeValue+")");
              }
              else {
                ResponseUtils.write(pageContext, NBSP);
              }
              ResponseUtils.write(pageContext, "</font>");
            }
            
            ResponseUtils.write(pageContext, "</td>");
          } 
          ResponseUtils.write(pageContext, "</tr>");
        }
        ResponseUtils.write(pageContext, "<tr");
        BigrTagUtils.writeAttribute(pageContext, "class", "white");
        ResponseUtils.write(pageContext, ">");
        //<tr class="white"> 
  
        ResponseUtils.write(pageContext, "<td");
        BigrTagUtils.writeAttribute(pageContext, "class", "grey");
        ResponseUtils.write(pageContext, ">&nbsp;</td>");
        //  <td class="grey">&nbsp;</td>
        currentColumn = 0;
        // display the footer for the columns
        for (int column = 1; column <= numberOfColumnsPerLine && currentColumn < numberOfColumns; column++) {
          currentColumn = columnsDisplayedSoFar + column;
          ResponseUtils.write(pageContext, "<td");
          BigrTagUtils.writeAttribute(pageContext, "class", "grey");
          ResponseUtils.write(pageContext, "><div");
          BigrTagUtils.writeAttribute(pageContext, "align", "center");
          ResponseUtils.write(pageContext, "><font");
          BigrTagUtils.writeAttribute(pageContext, "size", "1");
          ResponseUtils.write(pageContext, ">");
          ResponseUtils.write(pageContext, BoxLayoutUtils.convertToXaxisLabel(currentColumn, boxLayoutDto));
          ResponseUtils.write(pageContext, "</font></div></td>");
        }
        ResponseUtils.write(pageContext, "</tr>");
  
        columnsDisplayedSoFar += lastColumnInLoop; 
        
        //if there is a highlight message, display it
        if (!ApiFunctions.isEmpty(getHighlightMessage())) {
          ResponseUtils.write(pageContext, "<tr");
          BigrTagUtils.writeAttribute(pageContext, "class", "white");
          ResponseUtils.write(pageContext, ">");
          ResponseUtils.write(pageContext, "<td");
          BigrTagUtils.writeAttribute(pageContext, "colspan", (numberOfColumnsPerLine+1)+"");
          ResponseUtils.write(pageContext, "><div");
          BigrTagUtils.writeAttribute(pageContext, "align", "center");
          ResponseUtils.write(pageContext, "><font");
          BigrTagUtils.writeAttribute(pageContext, "size", "1");
          ResponseUtils.write(pageContext, ">");
          ResponseUtils.write(pageContext, getHighlightMessage());          
          ResponseUtils.write(pageContext, "</font></div></td>");
          ResponseUtils.write(pageContext, "</tr>");
        }

      ResponseUtils.write(pageContext, "</table>");
      } while ((currentRow < numberOfRows) && (columnsDisplayedSoFar < numberOfColumns));
      
      if (numberOfColumns > Constants.BOX_LAYOUT_MAX_COLUMN && isPrinterFriendly()) {
        ResponseUtils.write(pageContext, "<hr/>");
      }
    }
    else {  //not read-only
      for (int row = 1; row <= numberOfRows; row++) {
        ResponseUtils.write(pageContext, "<tr");
        BigrTagUtils.writeAttribute(pageContext, "class", "white");
        ResponseUtils.write(pageContext, ">");
        //  <tr class="white">

        ResponseUtils.write(pageContext, "<td");
        BigrTagUtils.writeAttribute(pageContext, "class", "grey");
        ResponseUtils.write(pageContext, "><div");
        BigrTagUtils.writeAttribute(pageContext, "align", "center");
        ResponseUtils.write(pageContext, "><b>");
        ResponseUtils.write(pageContext, BoxLayoutUtils.convertToYaxisLabel(row, boxLayoutDto));
        ResponseUtils.write(pageContext, "</b></div></td>");
        //    <td class="grey"><div align="center"><b><%=row%></b></div></td>

        for (int column = 1; column <= numberOfColumns; column++, cellRef++) {
          ResponseUtils.write(pageContext, "<td><div");
          BigrTagUtils.writeAttribute(pageContext, "align", "center");
          ResponseUtils.write(pageContext, ">");
          //  <td><div align="center">
        
          String property = null;
          String value = null;
          if (isGrouped()) {
            property = getBoxFormVar() + "[" + boxIndex + "]." + getBoxCellContentVar() + "[" + cellRef + "]";
            value = (String)RequestUtils.lookup(pageContext, getBoxFormName(), property, null);
          }
          else {
            property = getBoxCellContentVar() + cellRef;
            value = ApiFunctions.safeString(request.getParameter(property));
            if (ApiFunctions.isEmpty(value)) {
              value = (String)request.getAttribute(property);
            }
          }

          // If this is mixed-mode and the cell has a value, then display the sample barcode and
          // sample type and sample alias as read-only and generate a hidden input for the barcode.
          if (mixed && !ApiFunctions.isEmpty(value)) {
            ResponseUtils.write(pageContext, Escaper.htmlEscapeAndPreserveWhitespace(value));            
            if (!ApiFunctions.isEmpty(getSampleAliasVar())) {
              String sampleAliasProperty = null;
              String sampleAliasValue = null;

              if (isGrouped()) {
                sampleAliasProperty = getBoxFormVar() + "[" + boxIndex + "]." + getSampleAliasVar() + "[" + cellRef + "]";
                sampleAliasValue = (String)RequestUtils.lookup(pageContext, getBoxFormName(), sampleAliasProperty, null);
              }
              else {
                sampleAliasProperty = getSampleAliasVar() + cellRef;
                sampleAliasValue = request.getParameter(sampleAliasProperty);
                if (ApiFunctions.isEmpty(sampleAliasValue)) {
                  sampleAliasValue = (String)request.getAttribute(sampleAliasProperty);
                }
              }
              ResponseUtils.write(pageContext, "<br><font");
              BigrTagUtils.writeAttribute(pageContext, "size", "1");
              ResponseUtils.write(pageContext, ">");
              if (!ApiFunctions.isEmpty(sampleAliasValue)) {
                ResponseUtils.write(pageContext, "("+Escaper.htmlEscapeAndPreserveWhitespace(sampleAliasValue)+")");
              }
              else {
                ResponseUtils.write(pageContext, NBSP);
              }
              ResponseUtils.write(pageContext, "</font>");
            }
            if (!ApiFunctions.isEmpty(getSampleTypeVar())) {
              String sampleTypeProperty = null;
              String sampleTypeValue = null;

              if (isGrouped()) {
                sampleTypeProperty = getBoxFormVar() + "[" + boxIndex + "]." + getSampleTypeVar() + "[" + cellRef + "]";
                sampleTypeValue = (String)RequestUtils.lookup(pageContext, getBoxFormName(), sampleTypeProperty, null);
              }
              else {
                sampleTypeProperty = getSampleTypeVar() + cellRef;
                sampleTypeValue = request.getParameter(sampleTypeProperty);
                if (ApiFunctions.isEmpty(sampleTypeValue)) {
                  sampleTypeValue = (String)request.getAttribute(sampleTypeProperty);
                }
              }
              ResponseUtils.write(pageContext, "<br><font");
              BigrTagUtils.writeAttribute(pageContext, "size", "1");
              ResponseUtils.write(pageContext, ">");
              if (!ApiFunctions.isEmpty(sampleTypeValue)) {
                ResponseUtils.write(pageContext, "("+sampleTypeValue+")");
              }
              else {
                ResponseUtils.write(pageContext, NBSP);
              }
              ResponseUtils.write(pageContext, "</font>");
            }
            
            ResponseUtils.write(pageContext, "<input");
            BigrTagUtils.writeAttribute(pageContext, "type", "hidden");
            BigrTagUtils.writeAttribute(pageContext, "name", property);
            BigrTagUtils.writeAttribute(pageContext, "value", value);
            ResponseUtils.write(pageContext, "/>");
          }
          
          // Otherwise generate the input to capture a new value.
          else {
            ResponseUtils.write(pageContext, "<input");
            BigrTagUtils.writeAttribute(pageContext, "type", "text");
            BigrTagUtils.writeAttribute(pageContext, "name", property);
            
            //for SWP 1098
            if(empty) {
            BigrTagUtils.writeAttribute(pageContext, "value", "");
            } else   BigrTagUtils.writeAttribute(pageContext, "value", value); 
            
              
            BigrTagUtils.writeAttribute(pageContext, "maxlength", getInputMaxLength());
            BigrTagUtils.writeAttribute(pageContext, "size", getInputSize());
            BigrTagUtils.writeAttribute(pageContext, "tabindex", convertToTabIndex(column, row, boxLayoutDto));
            ResponseUtils.write(pageContext, "/>");            
          }
          //    <input type="text" name="requestBoxForm[0].cellContent[0]" value="" maxlength="10" size="10" tabindex="1"/>

          ResponseUtils.write(pageContext, "</div></td>");
          //</div></td>
        }
        ResponseUtils.write(pageContext, "</tr>");
        //  </tr>

        if (row == numberOfRows) {
          ResponseUtils.write(pageContext, "<tr");
          BigrTagUtils.writeAttribute(pageContext, "class", "white");
          ResponseUtils.write(pageContext, ">");
          //<tr class="white"> 

          ResponseUtils.write(pageContext, "<td");
          BigrTagUtils.writeAttribute(pageContext, "class", "grey");
          ResponseUtils.write(pageContext, ">&nbsp;</td>");
          //  <td class="grey">&nbsp;</td>

          for (int column = 1; column <= numberOfColumns; column++) {
            ResponseUtils.write(pageContext, "<td");
            BigrTagUtils.writeAttribute(pageContext, "class", "grey");
            ResponseUtils.write(pageContext, "><div");
            BigrTagUtils.writeAttribute(pageContext, "align", "center");
            ResponseUtils.write(pageContext, "><b>");
            ResponseUtils.write(pageContext, BoxLayoutUtils.convertToXaxisLabel(column, boxLayoutDto));
            ResponseUtils.write(pageContext, "</b></div></td>");
            //<td class="grey"><div align="center"><b>A</b></div></td>
          }
          ResponseUtils.write(pageContext, "</tr>");
          //</tr>
        }
      }
    }
    
		return EVAL_PAGE;
	}

	/**
	 * Release all allocated resources.
	 */
	public void release() {
		super.release();
    
    _boxLayoutName = null;
    _boxLayoutProperty = null;
    _boxLayoutDto = null;
    
    _cellType = null;
    _grouped = false;
    
    _tabIndexStartPos = null;

    _boxIndex = null;
    _boxFormName = null;
    _boxFormVar = null;
    _boxCellContentVar = null;
    _sampleTypeVar = null;
    
    _highlightList = null;
	}

  private void validate() throws JspException {
    
    // Get the box layout dto (value).  This is required, so throw a
    // JspException if we can't determine the box layout dto.
    if (getBoxLayoutDto() == null) {
      if (ApiFunctions.isEmpty(getBoxLayoutProperty())) {
        throw new JspException("Error in tag box: one of attribute boxLayoutDto or boxLayoutProperty was not specified.");
      }
      else if (!ApiFunctions.isEmpty(getBoxLayoutName())) {
        setBoxLayoutDto((BoxLayoutDto)RequestUtils.lookup(pageContext, getBoxLayoutName(), getBoxLayoutProperty(), null));
      }
      else {
        throw new JspException("Error in tag box: one of attribute boxLayoutDto or boxLayoutName/boxLayoutProperty not specified.");
      }
    }

    if (!_validCellTypes.contains(getCellType())) {
      throw new JspException("Error in tag box: attribute cellType not set to a valid value.");      
    }
    
    if (isGrouped()) {
      if (ApiFunctions.isEmpty(getBoxIndex())) {
        throw new JspException("Error in tag box: the boxIndex attribute must be specified when grouped.");
      }
      
      if (CELL_TYPE_READ_ONLY.equals(getCellType())) {
        setTabIndexStartPos(ZERO);
      }
      else {
        if (ApiFunctions.isEmpty(getTabIndexStartPos())) {
          throw new JspException("Error in tag box: the tabIndexStartPos attribute must be specified when grouped.");
        }
      }

      if (ApiFunctions.isEmpty(getBoxFormName())) {
        throw new JspException("Error in tag box: the boxFormName attribute must be specified when grouped.");
      }

      if (ApiFunctions.isEmpty(getBoxFormVar())) {
        throw new JspException("Error in tag box: the boxFormVar attribute must be specified when grouped.");
      }

      if (ApiFunctions.isEmpty(getBoxCellContentVar())) {
        throw new JspException("Error in tag box: the boxCellContentVar attribute must be specified when grouped.");
      }
    }
    else {
      setBoxIndex(ZERO);
      setTabIndexStartPos(ZERO);
    }

    try {
      int boxIndex = Integer.parseInt(getBoxIndex());
      if (boxIndex < 0) {
        throw new JspException("Error in tag box: the boxIndex value must be a number greater than or equal to zero.");
      }
    }
    catch (NumberFormatException nfe) {
      throw new JspException("Error in tag box: the boxIndex value is not a number.");
    }

    try {
      int tabIndexStartPos = Integer.parseInt(getTabIndexStartPos());
      if (tabIndexStartPos < 0) {
        throw new JspException("Error in tag box: the tabIndexStartPos value must be a number greater than or equal to zero.");
      }
    }
    catch (NumberFormatException nfe) {
      throw new JspException("Error in tag box: the tabIndexStartPos value is not a number.");
    }
  }

  private String convertToTabIndex(int column, int row, BoxLayoutDto boxLayoutDto) {
    int tabIndex = Integer.parseInt(getTabIndexStartPos());

    String tabIndexCid = boxLayoutDto.getTabIndexCid();
    int numberOfColumns = boxLayoutDto.getNumberOfColumns();
    int numberOfRows = boxLayoutDto.getNumberOfRows();
    
    if (ArtsConstants.BOX_LAYOUT_TAB_DOWN.equals(tabIndexCid)) {
      tabIndex += (row + ((column - 1) * numberOfRows));
    }
    else if (ArtsConstants.BOX_LAYOUT_TAB_RIGHT.equals(tabIndexCid)) {
      tabIndex += (((row - 1) * numberOfColumns) + column);
    }
    return Integer.toString(tabIndex);
  }

  /**
   * @return
   */
  public String getBoxCellContentVar() {
    return _boxCellContentVar;
  }

  /**
   * @return
   */
  public String getBoxFormName() {
    return _boxFormName;
  }

  /**
   * @return
   */
  public String getBoxFormVar() {
    return _boxFormVar;
  }

  /**
   * @return
   */
  public String getBoxIndex() {
    return _boxIndex;
  }

  /**
   * @return
   */
  public BoxLayoutDto getBoxLayoutDto() {
    return _boxLayoutDto;
  }

  /**
   * @return
   */
  public String getBoxLayoutName() {
    return _boxLayoutName;
  }

  /**
   * @return
   */
  public String getBoxLayoutProperty() {
    return _boxLayoutProperty;
  }

  /**
   * @return
   */
  public boolean isGrouped() {
    return _grouped;
  }

  /**
   * @return
   */
  public List getHighlightList() {
    return _highlightList;
  }

  /**
   * @return
   */
  public boolean isPersistValue() {
    return _persistValue;
  }

  /**
   * @return
   */
  public String getCellType() {
    return _cellType;
  }

  public boolean isPrinterFriendly() {
    return _printerFriendly;
  }

  /**
   * @return
   */
  public String getTabIndexStartPos() {
    return _tabIndexStartPos;
  }

  /**
   * @param string
   */
  public void setBoxCellContentVar(String string) {
    _boxCellContentVar = string;
  }

  /**
   * @param string
   */
  public void setBoxFormName(String string) {
    _boxFormName = string;
  }

  /**
   * @param string
   */
  public void setBoxFormVar(String string) {
    _boxFormVar = string;
  }

  /**
   * @param string
   */
  public void setBoxIndex(String string) {
    _boxIndex = string;
  }

  /**
   * @param dto
   */
  public void setBoxLayoutDto(BoxLayoutDto dto) {
    _boxLayoutDto = dto;
  }

  /**
   * @param string
   */
  public void setBoxLayoutName(String string) {
    _boxLayoutName = string;
  }

  /**
   * @param string
   */
  public void setBoxLayoutProperty(String string) {
    _boxLayoutProperty = string;
  }

  /**
   * @param b
   */
  public void setGrouped(boolean b) {
    _grouped = b;
  }

  /**
   * @param list
   */
  public void setHighlightList(List list) {
    _highlightList = list;
  }

  /**
   * @param b
   */
  public void setPersistValue(boolean b) {
    _persistValue = b;
  }

  /**
   * @param b
   */
  public void setCellType(String s) {
    _cellType = s;
  }
  
  public void setPrinterFriendly(boolean printerFriendly) {
    _printerFriendly = printerFriendly;
  }
  
  /**
   * @param string
   */
  public void setTabIndexStartPos(String string) {
    _tabIndexStartPos = string;
  }

  /**
   * @return
   */
  public String getSampleTypeVar() {
    return _sampleTypeVar;
  }

  /**
   * @return
   */
  public String getSampleAliasVar() {
    return _sampleAliasVar;
  }

  /**
   * @param string
   */
  public void setSampleTypeVar(String string) {
    _sampleTypeVar = string;
  }

  /**
   * @param string
   */
  public void setSampleAliasVar(String string) {
    _sampleAliasVar = string;
  }
  
  /**
   * @return Returns the inputMaxLength.
   */
  public String getInputMaxLength() {
    if (ApiFunctions.isEmpty(_inputMaxLength)) {
      setInputMaxLength(Integer.toString(ValidateIds.LENGTH_SAMPLE_ID));
    }
    return _inputMaxLength;
  }
  
  /**
   * @return Returns the inputSize.
   */
  public String getInputSize() {
    if (ApiFunctions.isEmpty(_inputSize)) {
      setInputSize(Integer.toString(ValidateIds.LENGTH_SAMPLE_ID));
    }
    return _inputSize;
  }
  
  /**
   * @param inputMaxLength The inputMaxLength to set.
   */
  public void setInputMaxLength(String inputMaxLength) {
    _inputMaxLength = inputMaxLength;
  }
  
  /**
   * @param inputSize The inputSize to set.
   */
  public void setInputSize(String inputSize) {
    _inputSize = inputSize;
  }
  
  /**
   * @return Returns the highlightColor.
   */
  public String getHighlightColor() {
    if (ApiFunctions.isEmpty(_highlightColor)) {
      //setHighlightColor("#FF0000");
      setHighlightColor("red");
    }
    return _highlightColor;
  }
  
  /**
   * @return Returns the highlightMessage.
   */
  public String getHighlightMessage() {
    return _highlightMessage;
  }
  
  /**
   * @param highlightColor The highlightColor to set.
   */
  public void setHighlightColor(String highlightColor) {
    _highlightColor = highlightColor;
  }
  
  /**
   * @param highlightMessage The highlightMessage to set.
   */
  public void setHighlightMessage(String highlightMessage) {
    _highlightMessage = highlightMessage;
  }
}
