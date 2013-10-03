package com.ardais.bigr.pdc.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.javabeans.OceData;
import com.ardais.bigr.pdc.oce.util.OceUtil;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.BigrValidator;
import com.gulfstreambio.gboss.GbossValueSet;

public class OceHelper extends JspHelper {
  //Stores the table names as keys. Use containsKey() method to 
  //check column mappings.
  private static Map _tableColumnMapping;
  //private static Map _statusMap;

  private String _tableName;
  private String _attribute;
  private String _status;
  private String _listOrder;
  private String _startDate;
  private String _endDate;
  private String _attributeName;
  private OceData _databean;
  private LegalValueSet _values = null; //used only in method getDropDownList()

  static {

    //ILTDS_ASM
    OceTableData iltdsAsm = new OceTableData("64", true, true, true);
    //ILTDS_ASM_FORM
    OceTableData iltdsAsmForm = new OceTableData("64", true, true, true);
    //ILTDS_INFORMED_CONSENT
    OceTableData iltdsInformedConsent = new OceTableData("64", true, true, true);
    //LIMS_PATHOLOGY_EVALUATION
    OceTableData limsPathologyEvaluation = new OceTableData("64", true, true, true);
    //PDC_PATH_REPORT_SECTION
    OceTableData pdcPathReportSection = new OceTableData("64", true, true, true);
    //PDC_PATH_REPORT_DX
    OceTableData pdcPathReportDx = new OceTableData("64", true, true, true);
    //PDC_PATHOLOGY_REPORT
    OceTableData pdcPathologyReport = new OceTableData("64", true, true, true);

    _tableColumnMapping = new HashMap();
    _tableColumnMapping.put("ILTDS_ASM", iltdsAsm);
    _tableColumnMapping.put("ILTDS_ASM_FORM", iltdsAsmForm);
    _tableColumnMapping.put("ILTDS_INFORMED_CONSENT", iltdsInformedConsent);
    _tableColumnMapping.put("LIMS_PATHOLOGY_EVALUATION", limsPathologyEvaluation);
    _tableColumnMapping.put("PDC_PATH_REPORT_SECTION", pdcPathReportSection);
    _tableColumnMapping.put("PDC_PATH_REPORT_DX", pdcPathReportDx);
    _tableColumnMapping.put("PDC_PATHOLOGY_REPORT", pdcPathologyReport);

  }
  /**
   * Creates new OceHelper.
   * @param request javax.servlet.http.HttpServletRequest
   */
  public OceHelper(javax.servlet.http.HttpServletRequest request) {
    _tableName = request.getParameter("tableName");
    _attribute = request.getParameter("attribute");
    _status = request.getParameter("status");
    //_attributeName = request.getParameter("AttributeName");
    _listOrder = request.getParameter("listOrder");
    _startDate = request.getParameter("startDate");
    _endDate = request.getParameter("endDate");
  }

  /**
   * Returns <code>Map</code> that contains all the column names for columnName.
   * 
   * @return the <code>Map</code> of column names, or <code>null</code>
   */
  private OceTableData getColumnMapping() {
    String table = getTableName();
    return (table != null) ? (OceTableData) _tableColumnMapping.get(table) : null;
  }

  /**
   * Returns <code>LegalValueSet</code> which is used as argument for 
   * <code>SelectListTag</code> to generate dropdown list options. 
   * 
   * @return <code>Vector</code>
   */
  public LegalValueSet getDropdownList(String whereClause) {

    /* IMPORTANT NOTE: if you add any fields here that get their legal
     *                 values via a call to OceUtil.getOtherValueList (i.e.
     *                 an attribute that have their choices determined by the
     *                 the value of other attributes MAKE SURE TO UPDATE
     *                 OceUtil.filteredAttributes SO THAT OCE CLONING IS
     *                 DISABLED FOR THAT ATTRIBUTE */

    try {
      //_values is made class member variable for reusing values stored
      //in it. For DDC types this list is not used because it is 
      //different for each row. In all other cases list is same.             

      //Get Diagnosis/Lesion List
      //List for Lesion is same as Diagnosis list.
      // mr7625 -- note that lesions no longer oce-able...
      if ((getAttribute().equals(OceUtil.OCE_TYPECODE_DIAGNOSIS_IND)))
        {
        if (_values == null) {
          GbossValueSet valueSet = BigrGbossData.getValueSetAlphabetized(ArtsConstants.VALUE_SET_DIAGNOSIS_HIERARCHY, false);
          _values = BigrGbossData.getValueSetAsLegalValueSet(valueSet);
        }
      }
      //Get Tissue List
      else if (
        getAttribute().equals(OceUtil.OCE_TYPECODE_TISSUE_IND)
          || getAttribute().equals(OceUtil.OCE_TYPECODE_METASTASIS_IND)) {
        if (_values == null) {
          GbossValueSet valueSet = BigrGbossData.getValueSetAlphabetized(ArtsConstants.VALUE_SET_TISSUE_HIERARCHY, false);
          _values = BigrGbossData.getValueSetAsLegalValueSet(valueSet);
        }
      }
      //Get Procedure List
      else if (getAttribute().equals(OceUtil.OCE_TYPECODE_PROCEDURE_IND)) {
        if (_values == null) {
          GbossValueSet valueSet = BigrGbossData.getValueSetAlphabetized(ArtsConstants.VALUE_SET_PROCEDURE_HIERARCHY, false);
          _values = BigrGbossData.getValueSetAsLegalValueSet(valueSet);
        }
      }
      //Get Distant Metastasis List
      else if (getAttribute().equals(OceUtil.OCE_TYPECODE_DISTANT_METASTASIS_IND)) {
        _values = OceUtil.getOtherValueList(whereClause, ArtsConstants.VALUE_SET_DDC_DISTANT_METASTASIS);
      }
      //Get Histological Nuclear Grade List
      else if (getAttribute().equals(OceUtil.OCE_TYPECODE_HNG_IND)) {
        _values = OceUtil.getOtherValueList(whereClause, ArtsConstants.VALUE_SET_HISTOLOGICAL_NUCLEAR_GRADE);
      }
      //Get Histological Type List
      else if (getAttribute().equals(OceUtil.OCE_TYPECODE_HISTOLOGICAL_TYPE_IND)) {
        _values = OceUtil.getOtherValueList(whereClause, ArtsConstants.VALUE_SET_HISTOLOGICAL_TYPE);
      }
      //Get Lymph Node Stage List
      else if (getAttribute().equals(OceUtil.OCE_TYPECODE_LYMPH_NODE_IND)) {
        _values = OceUtil.getOtherValueList(whereClause, ArtsConstants.VALUE_SET_DDC_LYMPH_NODE_STAGE_DESC);
      }
      //Get Stage Groupings List
      else if (getAttribute().equals(OceUtil.OCE_TYPECODE_STAGE_GROUPING_IND)) {
        _values = OceUtil.getOtherValueList(whereClause, ArtsConstants.VALUE_SET_DDC_STAGE_GROUPING);
      }
      //Get Tumor Stage Desc List
      else if (getAttribute().equals(OceUtil.OCE_TYPECODE_TUMOR_STAGE_DESC_IND)) {
        _values = OceUtil.getOtherValueList(whereClause, ArtsConstants.VALUE_SET_DDC_TUMOR_STAGE_DESC);
      }
      //Get Tumor Stage Type List
      else if (getAttribute().equals(OceUtil.OCE_TYPECODE_TUMOR_STAGE_TYPE_IND)) {
        _values = OceUtil.getOtherValueList(whereClause, ArtsConstants.VALUE_SET_TUMOR_STAGE_TYPE);
      }

    }
    catch (ApiException ex) {
      _values = new LegalValueSet();
    }

    return _values;
  }

  /**
   * Returns the URL for the popup window.
   * @return String
   */
  public String getPopupUrl() {
    String hierarchyUrl = "/orm/Dispatch?op=GetDDCDxTcHierarchyOther&type=";
    if ((ApiFunctions.safeEquals(getAttribute(), OceUtil.OCE_TYPECODE_DIAGNOSIS_IND)))
    {
      hierarchyUrl = hierarchyUrl + OceUtil.OCE_TYPECODE_DIAGNOSIS_IND;
    }
    else if (
      (ApiFunctions.safeEquals(getAttribute(), OceUtil.OCE_TYPECODE_TISSUE_IND))
        || (ApiFunctions.safeEquals(getAttribute(), OceUtil.OCE_TYPECODE_METASTASIS_IND))) {
      hierarchyUrl = hierarchyUrl + OceUtil.OCE_TYPECODE_TISSUE_IND;
    }
    else if (ApiFunctions.safeEquals(getAttribute(), OceUtil.OCE_TYPECODE_PROCEDURE_IND)) {
      hierarchyUrl = hierarchyUrl + OceUtil.OCE_TYPECODE_PROCEDURE_IND;
    }
    else if (
      ApiFunctions.safeEquals(getAttribute(), OceUtil.OCE_TYPECODE_DISTANT_METASTASIS_IND)) {
      hierarchyUrl = hierarchyUrl + OceUtil.OCE_TYPECODE_DISTANT_METASTASIS_IND;
    }
    else if (ApiFunctions.safeEquals(getAttribute(), OceUtil.OCE_TYPECODE_HNG_IND)) {
      hierarchyUrl = hierarchyUrl + OceUtil.OCE_TYPECODE_HNG_IND;
    }
    else if (ApiFunctions.safeEquals(getAttribute(), OceUtil.OCE_TYPECODE_HISTOLOGICAL_TYPE_IND)) {
      hierarchyUrl = hierarchyUrl + OceUtil.OCE_TYPECODE_HISTOLOGICAL_TYPE_IND;
    }
    else if (ApiFunctions.safeEquals(getAttribute(), OceUtil.OCE_TYPECODE_LYMPH_NODE_IND)) {
      hierarchyUrl = hierarchyUrl + OceUtil.OCE_TYPECODE_LYMPH_NODE_IND;
    }
    else if (ApiFunctions.safeEquals(getAttribute(), OceUtil.OCE_TYPECODE_STAGE_GROUPING_IND)) {
      hierarchyUrl = hierarchyUrl + OceUtil.OCE_TYPECODE_STAGE_GROUPING_IND;
    }
    else if (ApiFunctions.safeEquals(getAttribute(), OceUtil.OCE_TYPECODE_TUMOR_STAGE_DESC_IND)) {
      hierarchyUrl = hierarchyUrl + OceUtil.OCE_TYPECODE_TUMOR_STAGE_DESC_IND;
    }
    else if (ApiFunctions.safeEquals(getAttribute(), OceUtil.OCE_TYPECODE_TUMOR_STAGE_TYPE_IND)) {
      hierarchyUrl = hierarchyUrl + OceUtil.OCE_TYPECODE_TUMOR_STAGE_TYPE_IND;
    }
    return hierarchyUrl;
  }

  /**
   * Returns the Data bean.
   * @return String
   */
  public OceData getDataBean() {
    if (_databean == null) {
      _databean = new OceData();
      _databean.setListOrder(_listOrder);
      _databean.setAttribute(_attribute);
      _databean.setStatus(_status);
      _databean.setTableName(_tableName);
      _databean.setStartDate(_startDate);
      _databean.setEndDate(_endDate);
    }
    return _databean;

  }
  /**
   * Returns <code>boolean</code> if there are no form validation errors.
   * @return boolean
   */
  public boolean isValid() {
    boolean isValid = true;

    // Make sure that all required fields are filled in.
    //Table Name cannot be null.
    if (ApiFunctions.isEmpty(getTableName())) {
      getMessageHelper().addMessage("Table Name must be specified.");
      isValid = false;
    }

    //Attribute cannot be null.
    if (ApiFunctions.isEmpty(getAttribute())) {
      getMessageHelper().addMessage("Attribute must be specified.");
      isValid = false;
    }

    //Status cannot be null.
    if (ApiFunctions.isEmpty(getStatus())) {
      getMessageHelper().addMessage("Status must be specified.");
      isValid = false;
    }

    //List Order cannot be null.
    if (ApiFunctions.isEmpty(getListOrder())) {
      getMessageHelper().addMessage("List order must be specified.");
      isValid = false;
    }
    
    //if start date is specified, make sure it is the current date or before
    if (!ApiFunctions.isEmpty(getStartDate())) {
      Date minDate = new Date(BigrValidator.formatDate(getStartDate(), "MM/dd/yyyy", true).getTime());
      if (minDate.after(new Date())) {
        getMessageHelper().addMessage("Start date must not be later than the current date.");
        isValid = false;
      }
    }
    
    //if both start date and end date are specified, make sure start date is on or before
    //end date
    if (!ApiFunctions.isEmpty(getStartDate()) &&
        !ApiFunctions.isEmpty(getEndDate())) {
      Date minDate = new Date(BigrValidator.formatDate(getStartDate(), "MM/dd/yyyy", true).getTime());
      Date maxDate = new Date(BigrValidator.formatDate(getEndDate(), "MM/dd/yyyy", true).getTime());
      if (minDate.after(maxDate)) {
        getMessageHelper().addMessage("Start date must not be after End Date.");
        isValid = false;
      }
    }

    return isValid;

  }
  /**
   * Gets the listOrder
   * @return Returns a String
   */
  public String getListOrder() {
    if (_listOrder != null)
      return _listOrder;
    else
      return (_databean != null) ? _databean.getListOrder() : null;
  }
  /**
   * Insert the method's description here.
   * Creation date: (9/23/2002 1:33:57 PM)
   * @return java.lang.String
   */
  public String getOtherTextWidth() {
    OceTableData tableData = getColumnMapping();
    return (tableData != null) ? tableData.getOtherTextWidth() : ApiFunctions.EMPTY_STRING;

  }

  /**
   * Creates new OceHelper.
   * @param request javax.servlet.http.HttpServletRequest
   */
  public OceHelper(OceData databean) {
    _databean = databean;
  }

  /**
   * Gets the attribute
   * @return Returns a String
   */
  public String getAttribute() {
    if (_attribute != null)
      return _attribute;
    else
      return (_databean != null) ? _databean.getAttribute() : null;

  }
  /**
   * Insert the method's description here.
   * Creation date: (9/27/2002 10:23:52 AM)
   * @return java.lang.String
   */
  public String getAttributeName() {
    return (getAttribute() != null) ? OceUtil.lookupOceConstant(getAttribute()) : null;
  }

  /**
   * Gets the status
   * @return Returns a String
   */
  public String getStatus() {
    if (_status != null)
      return _status;
    else
      return (_databean != null) ? _databean.getStatus() : null;
  }
  /**
   * 	Gets the TableName
   *  @return String
   */
  public String getTableName() {
    if (_tableName != null)
      return _tableName;
    else
      return (_databean != null) ? _databean.getTableName() : null;
  }
  /**
   * Returns true if PATH_REPORT column should be displayed or not
   * for table name
   * 
   * @return <code>true</code> if PATH_REPORT column should displayed, 
   * or <code>false</code> otherwise.
   */
  public boolean isDisplayPathReport() {
    OceTableData tableData = getColumnMapping();
    return (tableData != null) ? tableData.isDisplayPathReport() : false;
  }
  /**
   *
   * or <code>false</code> otherwise.
   */
  public boolean isDisplayWarning() {
    if ((getList() != null) && (getList().size() > 0))
      return true;
    else
      return false;
  }
  /**
   * Sets the attribute
   * @param attribute The attribute to set
   */
  public void setAttribute(String attribute) {
    _attribute = attribute;
  }
  /**
   * Sets the listOrder
   * @param listOrder The listOrder to set
   */
  public void setListOrder(String listOrder) {
    _listOrder = listOrder;
  }
  /**
   * Sets the status
   * @param status The status to set
   */
  public void setStatus(String status) {
    _status = status;
  }
  /**
   * Sets the tableName
   * @param tableName The tableName to set
   */
  public void setTableName(String tableName) {
    _tableName = tableName;
  }
  /**
   * Returns the _statusName.
   * @return String
   */
  public String getStatusName() {
    return (getStatus() != null) ? OceUtil.lookupOceConstant(getStatus()) : null;
  }
  /**
   * Returns.
   * @return List
   */
  public List getList() {
    return (_databean != null) ? _databean.getList() : null;
  }

  private static class OceTableData {
    private boolean _displayOtherText;
    private String _otherTextWidth;
    private boolean _displayPathReport;
    private boolean _displayUpdate;

    OceTableData(
      String otherTextWidth,
      boolean displayOtherText,
      boolean displayPathReport,
      boolean displayUpdate) {
      if (otherTextWidth == null)
        throw new IllegalArgumentException("OceTableData: Value for otherTextWidth must not be null or empty.");

      _otherTextWidth = otherTextWidth;
      _displayOtherText = displayOtherText;
      _displayPathReport = displayPathReport;
      _displayUpdate = displayUpdate;

    }
    /**
     * Returns the _displayOtherText.
     * @return boolean
     */
    public boolean isDisplayOtherText() {
      return _displayOtherText;
    }

    /**
     * Returns the _displayPathReport.
     * @return boolean
     */
    public boolean isDisplayPathReport() {
      return _displayPathReport;
    }

    /**
     * Returns the _displayUpdate.
     * @return boolean
     */
    public boolean isDisplayUpdate() {
      return _displayUpdate;
    }

    /**
     * Returns the _otherTextWidth.
     * @return String
     */
    public String getOtherTextWidth() {
      return "width=\"" + _otherTextWidth + "%\"";
    }

  }
  /**
   * @return
   */
  public String getEndDate() {
    return _endDate;
  }

  /**
   * @return
   */
  public String getStartDate() {
    return _startDate;
  }

  /**
   * @param string
   */
  public void setEndDate(String string) {
    _endDate = string;
  }

  /**
   * @param string
   */
  public void setStartDate(String string) {
    _startDate = string;
  }

}
