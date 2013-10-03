package com.ardais.bigr.pdc.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.JspHelper;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.beans.DDCPathology;
import com.ardais.bigr.pdc.beans.DDCPathologyHome;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.EjbHomes;
import com.gulfstreambio.gboss.GbossFactory;
import com.gulfstreambio.gboss.GbossValue;
import com.gulfstreambio.gboss.GbossValueSet;

/**
 */
public class PathReportSectionHelper extends JspHelper {
  private String _pathReportSectionId;
  private String _consentId;
  private String _cellInfiltrateAmount;
  private String _diagnosis;
  private String _diagnosisOther;
  private String _distantMetastasis;
  private String _distantMetastasisInd;
  private String _distantMetastasisOther;
  private String _extensiveIntraductalComp;
  private String _extranodalSpread;
  private String _gleasonPrimary;
  private String _gleasonSecondary;
  private String _gleasonTotal;
  private String _histologicalNuclearGrade;
  private String _histologicalNuclearGradeOther;
  private String _histologicalType;
  private String _histologicalTypeOther;
  private String _inflammCellInfiltrate;
  private String _jointComponent;
  private String _lymphaticVascularInvasion;
  private String _lymphNodeNotes;
  private String _lymphNodeStage;
  private String _lymphNodeStageInd;
  private String _lymphNodeStageOther;
  private String _marginsInvolved;
  private String _marginsUninvolved;
  private String _pathReportId;
  private String _perineuralInvasion;
  private String _primary;
  private String _sectionIdentifier;
  private String _sectionNotes;
  private String _sizeLargestNodalMets;
  private String _stageGrouping;
  private String _stageGroupingOther;
  private String _tissueFinding;
  private String _tissueFindingOther;
  private String _tissueOrigin;
  private String _tissueOriginOther;
  private String _totalNodesExamined;
  private String _totalNodesPositive;
  private String _tumorConfig;
  private String _tumorSize1;
  private String _tumorSize2;
  private String _tumorSize3;
  private boolean _tumorSizeNS;
  private String _tumorStageDesc;
  private String _tumorStageDescInd;
  private String _tumorStageDescOther;
  private String _tumorStageType;
  private String _tumorStageTypeOther;
  private String _tumorWeight;
  private boolean _tumorWeightNS;
  private String _venousVesselInvasion;
  
  private String _donorImportedYN;

  // The value that should be returned for most of the helper's get
  // methods if the actual value is null.  This can be used to display
  // a value other than the empty string when a property does not
  // have a value.
  private String _defaultString;

  // The account associated with the consent.  This is necessary since
  // the lookup lists for many of the DDC fields are based on the consent's
  // account.
  private String _consentAccount;

  // The path report's disease.  This is necessary since the lookup lists 
  // for many of the DDC fields are based on the path report disease.
  private String _disease;

  // The path report section data bean that holds data to be displayed
  // through this helper.
  private PathReportSectionData _dataBean;

  // The parent pathology report.
  private PathReportHelper _pathReport;

  // The list of additional findings for the path report.  Each item in the
  // list is a PathReportSectionFindingHelper.
  private List _findings;

  private Map _sectionLinkParams;
  private Map _editSectionLinkParams;
  private Map _newFindingLinkParams;
  private Map _editFindingLinkParams;
  private int _findingsIndex;

  // Holds the mapping of disease types to path report section fields that
  // are valid for the disease type.  Each entry in the _diseasePropertyMapping 
  // is keyed by disease type code, and its value is a Map.  The inner Maps are
  // keyed by the field names that correspond to the field names defined in this
  // helper class, and their values are all null, thus allowing a lookup using
  // containsKey().
  private static Map _diseasePropertyMapping;
  static {

    // Arteriosclerotic vascular disease
    Map fields72092001 = new HashMap();
    fields72092001.put("diagnosis", null);
    fields72092001.put("sectionNotes", null);
    fields72092001.put("tissueFinding", null);
    fields72092001.put("tissueOrigin", null);
    fields72092001.put("tumorSize", null);
    fields72092001.put("tumorWeight", null);

    // Disease of connective tissues
    Map fields105969002 = new HashMap();
    fields105969002.put("diagnosis", null);
    fields105969002.put("jointComponent", null);
    fields105969002.put("sectionNotes", null);
    fields105969002.put("tissueFinding", null);
    fields105969002.put("tissueOrigin", null);
    fields105969002.put("tumorSize", null);
    fields105969002.put("tumorWeight", null);

    // Inflammatory bowel disease
    Map fields24526004 = new HashMap();
    fields24526004.put("diagnosis", null);
    fields24526004.put("sectionNotes", null);
    fields24526004.put("tissueFinding", null);
    fields24526004.put("tissueOrigin", null);
    fields24526004.put("tumorSize", null);
    fields24526004.put("tumorWeight", null);

    // Neoplasm of breast
    Map fields126926005 = new HashMap();
    fields126926005.put("cellInfiltrate", null);
    fields126926005.put("diagnosis", null);
    fields126926005.put("distantMetastasis", null);
    fields126926005.put("extensiveIntraductalComp", null);
    fields126926005.put("extranodal", null);
    fields126926005.put("histologicalNuclearGrade", null);
    fields126926005.put("histologicalType", null);
    fields126926005.put("lymphNodeNotes", null);
    fields126926005.put("lymphNodeStage", null);
    fields126926005.put("lymphaticVascularInvasion", null);
    fields126926005.put("margins", null);
    fields126926005.put("perineuralInvasion", null);
    fields126926005.put("sectionNotes", null);
    fields126926005.put("stageGrouping", null);
    fields126926005.put("tissueFinding", null);
    fields126926005.put("tissueOrigin", null);
    fields126926005.put("totalNodes", null);
    fields126926005.put("tumorSize", null);
    fields126926005.put("tumorStageDesc", null);
    fields126926005.put("tumorStageType", null);
    fields126926005.put("tumorWeight", null);

    // Neoplasm of colon
    Map fields126838000 = new HashMap();
    fields126838000.put("cellInfiltrate", null);
    fields126838000.put("diagnosis", null);
    fields126838000.put("distantMetastasis", null);
    fields126838000.put("extranodal", null);
    fields126838000.put("histologicalNuclearGrade", null);
    fields126838000.put("histologicalType", null);
    fields126838000.put("lymphNodeNotes", null);
    fields126838000.put("lymphNodeStage", null);
    fields126838000.put("lymphaticVascularInvasion", null);
    fields126838000.put("margins", null);
    fields126838000.put("perineuralInvasion", null);
    fields126838000.put("sectionNotes", null);
    fields126838000.put("stageGrouping", null);
    fields126838000.put("tissueFinding", null);
    fields126838000.put("tissueOrigin", null);
    fields126838000.put("totalNodes", null);
    fields126838000.put("tumorConfig", null);
    fields126838000.put("tumorSize", null);
    fields126838000.put("tumorStageDesc", null);
    fields126838000.put("tumorStageType", null);
    fields126838000.put("tumorWeight", null);
    fields126838000.put("venousVesselInvasion", null);

    // Neoplasm of lung
    Map fields126713003 = new HashMap();
    fields126713003.put("cellInfiltrate", null);
    fields126713003.put("diagnosis", null);
    fields126713003.put("distantMetastasis", null);
    fields126713003.put("extranodal", null);
    fields126713003.put("histologicalNuclearGrade", null);
    fields126713003.put("histologicalType", null);
    fields126713003.put("lymphNodeNotes", null);
    fields126713003.put("lymphNodeStage", null);
    fields126713003.put("lymphaticVascularInvasion", null);
    fields126713003.put("margins", null);
    fields126713003.put("perineuralInvasion", null);
    fields126713003.put("sectionNotes", null);
    fields126713003.put("stageGrouping", null);
    fields126713003.put("tissueFinding", null);
    fields126713003.put("tissueOrigin", null);
    fields126713003.put("totalNodes", null);
    fields126713003.put("tumorSize", null);
    fields126713003.put("tumorStageDesc", null);
    fields126713003.put("tumorStageType", null);
    fields126713003.put("tumorWeight", null);

    // Neoplasm of ovary
    Map fields123843001 = new HashMap();
    fields123843001.put("cellInfiltrate", null);
    fields123843001.put("diagnosis", null);
    fields123843001.put("distantMetastasis", null);
    fields123843001.put("extranodal", null);
    fields123843001.put("histologicalNuclearGrade", null);
    fields123843001.put("histologicalType", null);
    fields123843001.put("lymphNodeNotes", null);
    fields123843001.put("lymphNodeStage", null);
    fields123843001.put("lymphaticVascularInvasion", null);
    fields123843001.put("margins", null);
    fields123843001.put("perineuralInvasion", null);
    fields123843001.put("sectionNotes", null);
    fields123843001.put("stageGrouping", null);
    fields123843001.put("tissueFinding", null);
    fields123843001.put("tissueOrigin", null);
    fields123843001.put("totalNodes", null);
    fields123843001.put("tumorConfig", null);
    fields123843001.put("tumorSize", null);
    fields123843001.put("tumorStageDesc", null);
    fields123843001.put("tumorStageType", null);
    fields123843001.put("tumorWeight", null);

    // Neoplasm of prostate
    Map fields126906006 = new HashMap();
    fields126906006.put("cellInfiltrate", null);
    fields126906006.put("diagnosis", null);
    fields126906006.put("distantMetastasis", null);
    fields126906006.put("extranodal", null);
    fields126906006.put("gleason", null);
    fields126906006.put("histologicalNuclearGrade", null);
    fields126906006.put("histologicalType", null);
    fields126906006.put("lymphNodeNotes", null);
    fields126906006.put("lymphNodeStage", null);
    fields126906006.put("lymphaticVascularInvasion", null);
    fields126906006.put("margins", null);
    fields126906006.put("perineuralInvasion", null);
    fields126906006.put("sectionNotes", null);
    fields126906006.put("stageGrouping", null);
    fields126906006.put("tissueFinding", null);
    fields126906006.put("tissueOrigin", null);
    fields126906006.put("totalNodes", null);
    fields126906006.put("tumorSize", null);
    fields126906006.put("tumorStageDesc", null);
    fields126906006.put("tumorStageType", null);
    fields126906006.put("tumorWeight", null);

    // Other Neoplastic Disease
    Map fields55342001 = new HashMap();
    fields55342001.put("cellInfiltrate", null);
    fields55342001.put("diagnosis", null);
    fields55342001.put("distantMetastasis", null);
    fields55342001.put("extranodal", null);
    fields55342001.put("histologicalNuclearGrade", null);
    fields55342001.put("histologicalType", null);
    fields55342001.put("lymphNodeNotes", null);
    fields55342001.put("lymphNodeStage", null);
    fields55342001.put("lymphaticVascularInvasion", null);
    fields55342001.put("margins", null);
    fields55342001.put("perineuralInvasion", null);
    fields55342001.put("stageGrouping", null);
    fields55342001.put("sectionNotes", null);
    fields55342001.put("tissueFinding", null);
    fields55342001.put("tissueOrigin", null);
    fields55342001.put("totalNodes", null);
    fields55342001.put("tumorSize", null);
    fields55342001.put("tumorStageDesc", null);
    fields55342001.put("tumorStageType", null);
    fields55342001.put("tumorWeight", null);

    // Other Non-Neoplastic Disease
    Map fields128999004 = new HashMap();
    fields128999004.put("diagnosis", null);
    fields128999004.put("sectionNotes", null);
    fields128999004.put("tissueFinding", null);
    fields128999004.put("tissueOrigin", null);
    fields128999004.put("tumorSize", null);
    fields128999004.put("tumorWeight", null);

    _diseasePropertyMapping = new HashMap();
    _diseasePropertyMapping.put("72092001", fields72092001);
    _diseasePropertyMapping.put("105969002", fields105969002);
    _diseasePropertyMapping.put("24526004", fields24526004);
    _diseasePropertyMapping.put("126926005", fields126926005);
    _diseasePropertyMapping.put("126838000", fields126838000);
    _diseasePropertyMapping.put("126713003", fields126713003);
    _diseasePropertyMapping.put("123843001", fields123843001);
    _diseasePropertyMapping.put("126906006", fields126906006);
    _diseasePropertyMapping.put("55342001", fields55342001);
    _diseasePropertyMapping.put("128999004", fields128999004);
  }

  // Holds the mapping of disease types to path report section fields that
  // are part of the minimum pathology data requirements for the disease type.
  // Each entry in the _diseaseMinPathMapping is keyed by disease type code,
  // and its value is a Map.  The inner Maps are keyed by the field names that
  // correspond to the field names defined in this helper class, and their 
  // values are all null, thus allowing a lookup using containsKey().
  private static Map _diseaseMinPathMapping;
  static {

    // Arteriosclerotic vascular disease
    Map fields72092001 = new HashMap();
    fields72092001.put("diagnosis", null);
    fields72092001.put("tissueFinding", null);
    fields72092001.put("tissueOrigin", null);
    fields72092001.put("tumorSize", null);
    fields72092001.put("tumorWeight", null);

    // Disease of connective tissues
    Map fields105969002 = new HashMap();
    fields105969002.put("diagnosis", null);
    fields105969002.put("jointComponent", null);
    fields105969002.put("tissueFinding", null);
    fields105969002.put("tissueOrigin", null);
    fields105969002.put("tumorSize", null);
    fields105969002.put("tumorWeight", null);

    // Inflammatory bowel disease
    Map fields24526004 = new HashMap();
    fields24526004.put("diagnosis", null);
    fields24526004.put("tissueFinding", null);
    fields24526004.put("tissueOrigin", null);
    fields24526004.put("tumorSize", null);
    fields24526004.put("tumorWeight", null);

    // Neoplasm of breast
    Map fields126926005 = new HashMap();
    fields126926005.put("diagnosis", null);
    fields126926005.put("distantMetastasis", null);
    fields126926005.put("extensiveIntraductalComp", null);
    fields126926005.put("histologicalNuclearGrade", null);
    fields126926005.put("histologicalType", null);
    fields126926005.put("tissueFinding", null);
    fields126926005.put("tissueOrigin", null);
    fields126926005.put("lymphNodeStage", null);
    fields126926005.put("stageGrouping", null);
    fields126926005.put("totalNodesExamined", null);
    fields126926005.put("totalNodesPositive", null);
    fields126926005.put("tumorSize", null);
    fields126926005.put("tumorStageDesc", null);
    fields126926005.put("tumorStageType", null);
    fields126926005.put("tumorWeight", null);

    // Neoplasm of colon
    Map fields126838000 = new HashMap();
    fields126838000.put("diagnosis", null);
    fields126838000.put("distantMetastasis", null);
    fields126838000.put("histologicalNuclearGrade", null);
    fields126838000.put("histologicalType", null);
    fields126838000.put("tissueFinding", null);
    fields126838000.put("tissueOrigin", null);
    fields126838000.put("lymphNodeStage", null);
    fields126838000.put("stageGrouping", null);
    fields126838000.put("totalNodesExamined", null);
    fields126838000.put("totalNodesPositive", null);
    fields126838000.put("tumorSize", null);
    fields126838000.put("tumorStageDesc", null);
    fields126838000.put("tumorStageType", null);
    fields126838000.put("tumorWeight", null);

    // Neoplasm of lung
    Map fields126713003 = new HashMap();
    fields126713003.put("diagnosis", null);
    fields126713003.put("distantMetastasis", null);
    fields126713003.put("histologicalNuclearGrade", null);
    fields126713003.put("histologicalType", null);
    fields126713003.put("tissueFinding", null);
    fields126713003.put("tissueOrigin", null);
    fields126713003.put("lymphNodeStage", null);
    fields126713003.put("stageGrouping", null);
    fields126713003.put("totalNodesExamined", null);
    fields126713003.put("totalNodesPositive", null);
    fields126713003.put("tumorSize", null);
    fields126713003.put("tumorStageDesc", null);
    fields126713003.put("tumorStageType", null);
    fields126713003.put("tumorWeight", null);

    // Neoplasm of ovary
    Map fields123843001 = new HashMap();
    fields123843001.put("diagnosis", null);
    fields123843001.put("distantMetastasis", null);
    fields123843001.put("histologicalNuclearGrade", null);
    fields123843001.put("histologicalType", null);
    fields123843001.put("tissueFinding", null);
    fields123843001.put("tissueOrigin", null);
    fields123843001.put("lymphNodeStage", null);
    fields123843001.put("stageGrouping", null);
    fields123843001.put("totalNodesExamined", null);
    fields123843001.put("totalNodesPositive", null);
    fields123843001.put("tumorSize", null);
    fields123843001.put("tumorStageDesc", null);
    fields123843001.put("tumorStageType", null);
    fields123843001.put("tumorWeight", null);

    // Neoplasm of prostate
    Map fields126906006 = new HashMap();
    fields126906006.put("diagnosis", null);
    fields126906006.put("distantMetastasis", null);
    fields126906006.put("histologicalNuclearGrade", null);
    fields126906006.put("gleason", null);
    fields126906006.put("histologicalType", null);
    fields126906006.put("tissueFinding", null);
    fields126906006.put("tissueOrigin", null);
    fields126906006.put("lymphNodeStage", null);
    fields126906006.put("perineuralInvasion", null);
    fields126906006.put("stageGrouping", null);
    fields126906006.put("totalNodesExamined", null);
    fields126906006.put("totalNodesPositive", null);
    fields126906006.put("tumorSize", null);
    fields126906006.put("tumorStageDesc", null);
    fields126906006.put("tumorStageType", null);
    fields126906006.put("tumorWeight", null);

    // Other Neoplastic Disease
    Map fields55342001 = new HashMap();
    fields55342001.put("diagnosis", null);
    fields55342001.put("distantMetastasis", null);
    fields55342001.put("histologicalNuclearGrade", null);
    fields55342001.put("histologicalType", null);
    fields55342001.put("tissueFinding", null);
    fields55342001.put("tissueOrigin", null);
    fields55342001.put("lymphNodeStage", null);
    fields55342001.put("stageGrouping", null);
    fields55342001.put("totalNodesExamined", null);
    fields55342001.put("totalNodesPositive", null);
    fields55342001.put("tumorSize", null);
    fields55342001.put("tumorStageDesc", null);
    fields55342001.put("tumorStageType", null);
    fields55342001.put("tumorWeight", null);

    // Other Non-Neoplastic Disease
    Map fields128999004 = new HashMap();
    fields128999004.put("diagnosis", null);
    fields128999004.put("tissueFinding", null);
    fields128999004.put("tissueOrigin", null);
    fields128999004.put("tumorSize", null);
    fields128999004.put("tumorWeight", null);

    _diseaseMinPathMapping = new HashMap();
    _diseaseMinPathMapping.put("72092001", fields72092001);
    _diseaseMinPathMapping.put("105969002", fields105969002);
    _diseaseMinPathMapping.put("24526004", fields24526004);
    _diseaseMinPathMapping.put("126926005", fields126926005);
    _diseaseMinPathMapping.put("126838000", fields126838000);
    _diseaseMinPathMapping.put("126713003", fields126713003);
    _diseaseMinPathMapping.put("123843001", fields123843001);
    _diseaseMinPathMapping.put("126906006", fields126906006);
    _diseaseMinPathMapping.put("55342001", fields55342001);
    _diseaseMinPathMapping.put("128999004", fields128999004);
  }
  /**
   * Creates a <code>PathReportSectionHelper</code>, initializing its properties 
   * from a <code>PathReportSectionData</code> bean.
   *
   * @param  dataBean  a <code>PathReportSectionData</code> bean
   */
  public PathReportSectionHelper(PathReportSectionData dataBean) {
    this(dataBean, null);
  }
  /**
   * Creates a <code>PathReportSectionHelper</code>, initializing its properties 
   * from a <code>PathReportSectionData</code> bean.
   *
   * @param  dataBean  a <code>PathReportSectionData</code> bean
   */
  public PathReportSectionHelper(PathReportSectionData dataBean, String defaultString) {
    _dataBean = dataBean;
    _defaultString = defaultString;
    parseTumorSize();
    parseTumorWeight();

    if (_dataBean.getFindings() != null) {
      List findings = _dataBean.getFindings();
      for (int i = 0; i < findings.size(); i++) {
        PathReportSectionFindingData finding = (PathReportSectionFindingData) findings.get(i);
        addFinding(new PathReportSectionFindingHelper(finding, _defaultString));
      }
    }

  }
  /**
   * Creates a <code>PathReportSectionHelper</code> from an HTTP request,
   * initializing its properties from the request parameters.
   *
   * @param  request  the HTTP request
   */
  public PathReportSectionHelper(HttpServletRequest request) {
    _pathReportSectionId = safeTrim(request.getParameter("pathReportSectionId"));
    if (isEmpty(_pathReportSectionId))
      _pathReportSectionId = null;
    _consentId = safeTrim(request.getParameter("consentId"));
    _cellInfiltrateAmount = safeTrim(request.getParameter("cellInfiltrateAmount"));
    _diagnosis = safeTrim(request.getParameter("diagnosis"));
    _diagnosisOther = safeTrim(request.getParameter("diagnosisOther"));
    _distantMetastasis = safeTrim(request.getParameter("distantMetastasis"));
    _distantMetastasisInd = safeTrim(request.getParameter("distantMetastasisInd"));
    _distantMetastasisOther = safeTrim(request.getParameter("distantMetastasisOther"));
    _extensiveIntraductalComp = safeTrim(request.getParameter("extensiveIntraductalComp"));
    _extranodalSpread = safeTrim(request.getParameter("extranodalSpread"));
    _gleasonPrimary = safeTrim(request.getParameter("gleasonPrimary"));
    _gleasonSecondary = safeTrim(request.getParameter("gleasonSecondary"));
    _gleasonTotal = safeTrim(request.getParameter("gleasonTotal"));
    _histologicalNuclearGrade = safeTrim(request.getParameter("histologicalNuclearGrade"));
    _histologicalNuclearGradeOther =
      safeTrim(request.getParameter("histologicalNuclearGradeOther"));
    _histologicalType = safeTrim(request.getParameter("histologicalType"));
    _histologicalTypeOther = safeTrim(request.getParameter("histologicalTypeOther"));
    _inflammCellInfiltrate = safeTrim(request.getParameter("inflammCellInfiltrate"));
    _jointComponent = safeTrim(request.getParameter("jointComponent"));
    _lymphaticVascularInvasion = safeTrim(request.getParameter("lymphaticVascularInvasion"));
    _lymphNodeNotes = safeTrim(request.getParameter("lymphNodeNotes"));
    _lymphNodeStageInd = safeTrim(request.getParameter("lymphNodeStageInd"));
    _lymphNodeStageOther = safeTrim(request.getParameter("lymphNodeStageOther"));
    _marginsInvolved = safeTrim(request.getParameter("marginsInvolved"));
    _marginsUninvolved = safeTrim(request.getParameter("marginsUninvolved"));
    _pathReportId = safeTrim(request.getParameter("pathReportId"));
    _perineuralInvasion = safeTrim(request.getParameter("perineuralInvasion"));
    _primary = safeTrim(request.getParameter("primary"));
    _sectionIdentifier = safeTrim(request.getParameter("sectionIdentifier"));
    _sectionNotes = safeTrim(request.getParameter("sectionNotes"));
    _sizeLargestNodalMets = safeTrim(request.getParameter("sizeLargestNodalMets"));
    _stageGrouping = safeTrim(request.getParameter("stageGrouping"));
    _stageGroupingOther = safeTrim(request.getParameter("stageGroupingOther"));
    _tissueFinding = safeTrim(request.getParameter("tissueFinding"));
    _tissueFindingOther = safeTrim(request.getParameter("tissueFindingOther"));
    _tissueOrigin = safeTrim(request.getParameter("tissueOrigin"));
    _tissueOriginOther = safeTrim(request.getParameter("tissueOriginOther"));
    _totalNodesPositive = safeTrim(request.getParameter("totalNodesPositive"));
    _totalNodesExamined = safeTrim(request.getParameter("totalNodesExamined"));
    _tumorConfig = safeTrim(request.getParameter("tumorConfig"));
    _tumorSize1 = safeTrim(request.getParameter("tumorSize1"));
    _tumorSize2 = safeTrim(request.getParameter("tumorSize2"));
    _tumorSize3 = safeTrim(request.getParameter("tumorSize3"));
    String tumorSizeNS = safeTrim(request.getParameter("tumorSizeNS"));
    _tumorSizeNS = ((tumorSizeNS != null) && (tumorSizeNS.equals("true")));
    _tumorStageDesc = safeTrim(request.getParameter("tumorStageDesc"));
    _tumorStageDescInd = safeTrim(request.getParameter("tumorStageDescInd"));
    _tumorStageDescOther = safeTrim(request.getParameter("tumorStageDescOther"));
    _tumorStageType = safeTrim(request.getParameter("tumorStageType"));
    _tumorStageTypeOther = safeTrim(request.getParameter("tumorStageTypeOther"));
    _tumorWeight = safeTrim(request.getParameter("tumorWeight"));
    String tumorWeightNS = safeTrim(request.getParameter("tumorWeightNS"));
    _tumorWeightNS = ((tumorWeightNS != null) && (tumorWeightNS.equals("true")));
    _venousVesselInvasion = safeTrim(request.getParameter("venousVesselInvasion"));
    _donorImportedYN = safeTrim(request.getParameter("donorImportedYN"));

    if (ApiFunctions.isInteger(_totalNodesPositive)
      && (Integer.valueOf(_totalNodesPositive).intValue() > 0)) {
      _lymphNodeStage = safeTrim(request.getParameter("lymphNodeStageG0"));
    }
    else {
      _lymphNodeStage = safeTrim(request.getParameter("lymphNodeStage"));
    }

  }
  /**
   * Adds a <code>PathReportSectionFindingHelper</code> to this <code>PathReportSectionHelper</code>.
   * The additional finding is added to the end of an ordered list.
   *
   * @param  helper  the <code>PathReportSectionFindingHelper</code>
   */
  public void addFinding(PathReportSectionFindingHelper helper) {
    if (_findings == null)
      _findings = new ArrayList();
    _findings.add(helper);
  }
  /**
   * Returns this path report section's cell infiltrate amount.
   *
   * @return  The cell infiltrate amount.
   */
  public String getCellInfiltrateAmount() {
    if (_cellInfiltrateAmount != null)
      return _cellInfiltrateAmount;
    else if (_dataBean != null)
      return _dataBean.getCellInfiltrateAmount();

    return _defaultString;
  }
  /**
   * Returns the display value of this path report section's cell infiltrate amount.
   *
   * @return  The cell infiltrate amount display value.
   */
  public String getCellInfiltrateAmountDisplay() {
    String code = getCellInfiltrateAmount();
    return (code != null) ? GbossFactory.getInstance().getDescription(/*"CELL_INFILTRATE_AMT",*/
    code) : _defaultString;
  }
  /**
   * Returns the list of possible values for cell infiltrate amount.
   *
   * @return  A <code>LegalValueSet</code> of cell infiltrate amount values.
   */
  public LegalValueSet getCellInfiltrateAmountList() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_CELL_INFILTRATE_AMT);
  }

  /**
   * Returns the account associated with the path report's consent id.
   *
   * @return  The consent's account.
   */
  private String getConsentAccount() {
    if (_consentAccount == null) {
      _consentAccount = getPathReport().getConsentAccount();
    }
    return _consentAccount;
  }
  /**
   * Returns this path report section's consent id.
   *
   * @return  The consent id.
   */
  public String getConsentId() {
    if (_consentId != null)
      return _consentId;
    if (_dataBean != null)
      return _dataBean.getConsentId();
    return _defaultString;
  }
  /**
   * Returns the <code>PathReportSectionData</code> bean that contains
   * the path report section data fields associated with this 
   * <code>PathReportSectionHelper</code>.
   * 
   * @return  The <code>PathReportSectionData</code> bean.
   */
  public PathReportSectionData getDataBean() {
    if (_dataBean == null) {
      _dataBean = new PathReportSectionData();
      _dataBean.setCellInfiltrateAmount(_cellInfiltrateAmount);
      _dataBean.setConsentId(_consentId);
      _dataBean.setDiagnosis(_diagnosis);
      if ((_diagnosis != null)
        && (_diagnosis.equals(com.ardais.bigr.iltds.helpers.FormLogic.OTHER_DX)))
        _dataBean.setDiagnosisOther(_diagnosisOther);
      _dataBean.setDistantMetastasis(_distantMetastasis);
      _dataBean.setDistantMetastasisInd(_distantMetastasisInd);
      _dataBean.setDistantMetastasisOther(_distantMetastasisOther);
      _dataBean.setExtensiveIntraductalComp(_extensiveIntraductalComp);
      _dataBean.setExtranodalSpread(_extranodalSpread);
      if (!ApiFunctions.isEmpty(_gleasonPrimary))
        _dataBean.setGleasonPrimary(new Integer(_gleasonPrimary));
      _dataBean.setPrimary(_primary);
      if (!ApiFunctions.isEmpty(_gleasonSecondary))
        _dataBean.setGleasonSecondary(new Integer(_gleasonSecondary));
      if (!ApiFunctions.isEmpty(_gleasonTotal))
        _dataBean.setGleasonTotal(new Integer(_gleasonTotal));
      _dataBean.setHistologicalNuclearGrade(_histologicalNuclearGrade);
      _dataBean.setHistologicalNuclearGradeOther(_histologicalNuclearGradeOther);
      _dataBean.setHistologicalType(_histologicalType);
      _dataBean.setHistologicalTypeOther(_histologicalTypeOther);
      _dataBean.setInflammCellInfiltrate(_inflammCellInfiltrate);
      _dataBean.setJointComponent(_jointComponent);
      _dataBean.setLymphaticVascularInvasion(_lymphaticVascularInvasion);
      _dataBean.setLymphNodeNotes(_lymphNodeNotes);
      _dataBean.setLymphNodeStage(_lymphNodeStage);
      _dataBean.setLymphNodeStageInd(_lymphNodeStageInd);
      _dataBean.setLymphNodeStageOther(_lymphNodeStageOther);
      _dataBean.setMarginsInvolved(_marginsInvolved);
      _dataBean.setMarginsUninvolved(_marginsUninvolved);
      _dataBean.setPathReportId(_pathReportId);
      _dataBean.setPathReportSectionId(_pathReportSectionId);
      _dataBean.setPerineuralInvasion(_perineuralInvasion);
      _dataBean.setSectionIdentifier(_sectionIdentifier);
      _dataBean.setSizeLargestNodalMets(_sizeLargestNodalMets);
      _dataBean.setSectionNotes(_sectionNotes);
      _dataBean.setStageGrouping(_stageGrouping);
      _dataBean.setStageGroupingOther(_stageGroupingOther);
      _dataBean.setTissueFinding(_tissueFinding);
      if ((_tissueFinding != null)
        && (_tissueFinding.equals(com.ardais.bigr.iltds.helpers.FormLogic.OTHER_TISSUE)))
        _dataBean.setTissueFindingOther(_tissueFindingOther);
      _dataBean.setTissueOrigin(_tissueOrigin);
      if ((_tissueOrigin != null)
        && (_tissueOrigin.equals(com.ardais.bigr.iltds.helpers.FormLogic.OTHER_TISSUE)))
        _dataBean.setTissueOriginOther(_tissueOriginOther);
      _dataBean.setTotalNodesExamined(_totalNodesExamined);
      _dataBean.setTotalNodesPositive(_totalNodesPositive);
      _dataBean.setTumorConfiguration(_tumorConfig);
      if (_tumorSizeNS) {
        _dataBean.setTumorSize("NS");
      }
      else if (
        !ApiFunctions.isEmpty(_tumorSize1)
          || !ApiFunctions.isEmpty(_tumorSize2)
          || !ApiFunctions.isEmpty(_tumorSize3)) {
        _dataBean.setTumorSize(_tumorSize1 + "," + _tumorSize2 + "," + _tumorSize3);
      }
      _dataBean.setTumorStageDesc(_tumorStageDesc);
      _dataBean.setTumorStageDescInd(_tumorStageDescInd);
      _dataBean.setTumorStageDescOther(_tumorStageDescOther);
      _dataBean.setTumorStageType(_tumorStageType);
      _dataBean.setTumorStageTypeOther(_tumorStageTypeOther);
      if (_tumorWeightNS)
        _dataBean.setTumorWeight("NS");
      else
        _dataBean.setTumorWeight(_tumorWeight);
      _dataBean.setVenousVesselInvasion(_venousVesselInvasion);
    }
    return _dataBean;
  }
  /**
   * Returns the diagnosis of this path report section.
   *
   * @return  The diagnosis.
   */
  public String getDiagnosis() {
    if (_diagnosis != null)
      return _diagnosis;
    else if (_dataBean != null)
      return _dataBean.getDiagnosis();

    return _defaultString;
  }
  /**
   * Returns the display value of the diagnosis of this path report section.
   *
   * @return  The diagnosis display value.
   */
  public String getDiagnosisDisplay() {
    String code = getDiagnosis();
    return (code != null)
      ? BigrGbossData.getDiagnosisDescription(code)
      : _defaultString;
  }
  /**
   * Returns this path report section's "other" diagnosis.
   *
   * @return  the "other" diagnosis
   */
  public String getDiagnosisOther() {
    if (_diagnosisOther != null)
      return _diagnosisOther;
    if (_dataBean != null)
      return _dataBean.getDiagnosisOther();
    return _defaultString;
  }
  /**
   * Returns the parent path report's disease type.
   *
   * @return  The disease type, or <code>null</code> if the parent path 
   *			report is not available or has a null disease type.
   */
  private String getDisease() {
    if (_disease == null)
      _disease = getPathReport().getDisease();
    return _disease;
  }
  /**
   * Returns the <code>Map</code> containing all properties that are valid 
   * based on the parent path report's disease type.
   *
   * @return  the <code>Map</code> of valid properties, or <code>null</code>
   *			if the parent path report is not available or has a null disease type
   */
  private Map getDiseaseMapping() {
    String disease = getDisease();
    return (disease != null) ? (Map) _diseasePropertyMapping.get(disease) : null;
  }
  /**
   * Returns the distant metastasis of this path report section.
   *
   * @return  The distant metastasis.
   */
  public String getDistantMetastasis() {
    if (_distantMetastasis != null)
      return _distantMetastasis;
    else if (_dataBean != null)
      return _dataBean.getDistantMetastasis();

    return _defaultString;
  }
  /**
   * Returns the display value of the distant metastasis of this path report section.
   *
   * @return  The distant metastasis display value
   */
  public String getDistantMetastasisDisplay() {
    String code = getDistantMetastasis();
    return (code != null) ? GbossFactory.getInstance().getDescription(/*"DISTANT_METASTASIS",*/
    code) : _defaultString;
  }
  /**
   * Returns the distant metastasis indicator of this path report section.
   *
   * @return  The distant metastasis indicator.
   */
  public String getDistantMetastasisInd() {
    if (_distantMetastasisInd != null)
      return _distantMetastasisInd;
    else if (_dataBean != null)
      return _dataBean.getDistantMetastasisInd();

    return _defaultString;
  }
  /**
   * Returns the list of possible values for distant metastasis.
   *
   * @return  A <code>LegalValueSet</code> of distant metastasis values.
   */
  public LegalValueSet getDistantMetastasisList() {
    return BigrGbossData.getPdcStageHierarchyLookupList(ArtsConstants.VALUE_SET_DDC_DISTANT_METASTASIS, getDisease());
  }
  /**
   * Returns the "other" distant metastasis of this path report section.
   *
   * @return  The "other" distant metastasis.
   */
  public String getDistantMetastasisOther() {
    if (_distantMetastasisOther != null)
      return _distantMetastasisOther;
    else if (_dataBean != null)
      return _dataBean.getDistantMetastasisOther();

    return _defaultString;
  }
  /**
   * Returns this path report section's extensive intraductal component.
   *
   * @return  The extensive intraductal component.
   */
  public String getExtensiveIntraductalComp() {
    if (_extensiveIntraductalComp != null)
      return _extensiveIntraductalComp;
    else if (_dataBean != null)
      return _dataBean.getExtensiveIntraductalComp();

    return _defaultString;
  }
  /**
   * Returns the display value of this path report section's extensive intraductal component.
   *
   * @return  The extensive intraductal component display value.
   */
  public String getExtensiveIntraductalCompDisplay() {
    String code = getExtensiveIntraductalComp();
    return (code != null)
      ? GbossFactory.getInstance().getDescription(/*"EXTENSIVE_INTRADUCTAL_COMPONENT",*/
    code) : _defaultString;
  }
  /**
   * Returns the list of possible values for extensive intraductal component.
   *
   * @return  A <code>LegalValueSet</code> of extensive intraductal component values.
   */
  public LegalValueSet getExtensiveIntraductalCompList() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_EXTENSIVE_INTRADUCTAL_COMPONENT); 
  }
  /**
   * Returns this path report section's extranodal spread.
   *
   * @return  The extranodal spread.
   */
  public String getExtranodalSpread() {
    if (_extranodalSpread != null)
      return _extranodalSpread;
    if (_dataBean != null)
      return _dataBean.getExtranodalSpread();
    return _defaultString;
  }
  /**
   * Returns this path report section's extranodal spread display value.
   *
   * @return  The extranodal spread display value.
   */
  public String getExtranodalSpreadDisplay() {
    String code = getExtranodalSpread();
    return (code != null) ? GbossFactory.getInstance().getDescription(/*"EXTRANODAL_SPREAD",*/
    code) : _defaultString;
  }
  /**
   * Returns the list of possible values for extranodal spread.
   *
   * @return  A <code>LegalValueSet</code> of extranodal spread values.
   */
  public LegalValueSet getExtranodalSpreadList() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_EXTRANODAL_SPREAD); 
  }
  /**
   * Returns the list of additional findings ({@link PathReportSectionFindingHelper}s) that were
   * added to this <code>PathReportSectionHelper</code>.  If there are no additional findings,
   * an empty list is returned.
   *
   * @return  The list of {@link PathReportSectionFindingHelper}s
   */
  public List getFindings() {
    return _findings;
  }
  /**
   * Returns this path report section's primary gleason score.
   *
   * @return  The primary gleason score.
   */
  public String getGleasonPrimary() {
    if (_gleasonPrimary != null)
      return _gleasonPrimary;
    if (_dataBean != null) {
      Integer gleason = _dataBean.getGleasonPrimary();
      if (gleason != null)
        return gleason.toString();
    }
    return _defaultString;
  }
  /**
   * Returns the <code>LegalValueSet</code> of legal values for this path 
   * report section's primary gleason score.
   *
   * @return  The <code>LegalValueSet</code>.
   */
  public LegalValueSet getGleasonPrimaryList() {
    LegalValueSet score = new LegalValueSet();
    for (int i = 1; i <= 5; i++) {
      score.addLegalValue(String.valueOf(i));
    }
    return score;
  }
  /**
   * Returns this path report section's secondary gleason score.
   *
   * @return  The secondary gleason score.
   */
  public String getGleasonSecondary() {
    if (_gleasonSecondary != null)
      return _gleasonSecondary;
    if (_dataBean != null) {
      Integer gleason = _dataBean.getGleasonSecondary();
      if (gleason != null)
        return gleason.toString();
    }
    return _defaultString;
  }
  /**
   * Returns the <code>LegalValueSet</code> of legal values for this path 
   * report section's secondary gleason score.
   *
   * @return  The <code>LegalValueSet</code>.
   */
  public LegalValueSet getGleasonSecondaryList() {
    LegalValueSet score = new LegalValueSet();
    for (int i = 1; i <= 5; i++) {
      score.addLegalValue(String.valueOf(i));
    }
    return score;
  }
  /**
   * Returns this path report section's total gleason score.
   *
   * @return  The total gleason score.
   */
  public String getGleasonTotal() {
    if (_gleasonTotal != null)
      return _gleasonTotal;
    if (_dataBean != null) {
      Integer gleason = _dataBean.getGleasonTotal();
      if (gleason != null)
        return gleason.toString();
    }
    return _defaultString;
  }
  /**
   * Returns this path report section's histological nuclear grade.
   *
   * @return  The histological nuclear grade.
   */
  public String getHistologicalNuclearGrade() {
    if (_histologicalNuclearGrade != null)
      return _histologicalNuclearGrade;
    else if (_dataBean != null)
      return _dataBean.getHistologicalNuclearGrade();

    return _defaultString;
  }
  /**
   * Returns the display value of this path report section's histological nuclear grade.
   *
   * @return  The histological nuclear grade display value.
   */
  public String getHistologicalNuclearGradeDisplay() {
    String code = getHistologicalNuclearGrade();
    return (code != null) ? GbossFactory.getInstance().getDescription(/*"HISTOLOGICAL_NUCLEAR_GRADE",*/
    code) : _defaultString;
  }
  /**
   * Returns the list of possible values for histological nuclear grade.
   *
   * @return  A <code>LegalValueSet</code> of histological nuclear grade values.
   */
  public LegalValueSet getHistologicalNuclearGradeList() {
    /*
     * This method modified for MR 5527 & 6058 by Nagaraja & GYost.  Requirements:
     *   - For DIs accounts other than Duke (DI00000002), use the HNG list from ARTS for
     *     the specified disease
     *   - For Duke:
     *     - If either (A) a new path section is being created or (B) a path section doesn't
     *       currently have an HNG value or (C) the existing HNG value is in the ARD_CAP values
     *       for the disease, then use only the values in the ARD_CAP list (Nottingham grades).
     *     - Otherwise (the existing HNG value is in the ARTS Duke list for the disease
     *       (NSABP values)), use a list that contains both the Duke values and the ARD_CAP values.
     *     The intent of the special cases for Duke is that for new sections or existing sections
     *     that already use the Nottingham values, don't let the user select the old NSABP values.
     *     But, for existing sections that are currently using NSABP value, allow both NSABP
     *     and Nottingham values to be selected when someone edits the section.
     */
    GbossValueSet vs = BigrGbossData.getValueSetByDiagnosis(ArtsConstants.VALUE_SET_HISTOLOGICAL_NUCLEAR_GRADE, getDisease());
    LegalValueSet nonSpecialDukeValuesLVS = BigrGbossData.getValueSetAsLegalValueSet(vs);

    //return above list for DIs other than DUKE(DI00000002)
    if (!ApiFunctions.safeEquals(getConsentAccount(), "DI00000002")) {
      return nonSpecialDukeValuesLVS;
    }
    else {
      //For DUKE (DI00000002) implement the special conditions described above..

      //In the Duke special cases described above, don't return list which contains
      //DI00000002 specific values. Just return the ARD_CAP values for the disease type.
      if (isNew()
        || ApiFunctions.isEmpty(getHistologicalNuclearGrade())
        || (nonSpecialDukeValuesLVS.getDisplayValue(getHistologicalNuclearGrade()) != null)) {
        return nonSpecialDukeValuesLVS;
      }
      else {
        //DI00000002 and the current value is in the Duke list (NSABP values)
        LegalValueSet specialDukeValuesLVS = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_HISTOLOGICAL_NUCLEAR_GRADE_DUKE);
        
        //create a new LVS and add both the lists above to it.                       
        LegalValueSet hngList = new LegalValueSet();
        for (int i = 0; i < nonSpecialDukeValuesLVS.getCount(); i++) {
          hngList.addLegalValue(nonSpecialDukeValuesLVS.getValue(i), nonSpecialDukeValuesLVS.getDisplayValue(i));
        }
        for (int i = 0; i < specialDukeValuesLVS.getCount(); i++) {
          //check to avoid duplicates.  
          if (hngList.getDisplayValue(specialDukeValuesLVS.getValue(i)) == null) {
            hngList.addLegalValue(specialDukeValuesLVS.getValue(i), specialDukeValuesLVS.getDisplayValue(i));
          }
        }
        return hngList;
      }
    }
  }
  /**
   * Returns this path report section's "other" histological nuclear grade.
   *
   * @return  The "other" histological nuclear grade.
   */
  public String getHistologicalNuclearGradeOther() {
    if (_histologicalNuclearGradeOther != null)
      return _histologicalNuclearGradeOther;
    else if (_dataBean != null)
      return _dataBean.getHistologicalNuclearGradeOther();

    return _defaultString;
  }
  /**
   * Returns this path report section's histological type.
   *
   * @return  The histological type.
   */
  public String getHistologicalType() {
    if (_histologicalType != null)
      return _histologicalType;
    else if (_dataBean != null)
      return _dataBean.getHistologicalType();

    return _defaultString;
  }
  /**
   * Returns the display value of this path report section's histological type.
   *
   * @return  The histological type display value.
   */
  public String getHistologicalTypeDisplay() {
    String code = getHistologicalType();
    return (code != null) ? GbossFactory.getInstance().getDescription(/*"HISTOLOGICAL_TYPE",*/
    code) : _defaultString;
  }
  /**
   * Returns the list of possible values for histological type.
   *
   * @return  A <code>LegalValueSet</code> of histological type values.
   */
  public LegalValueSet getHistologicalTypeList() {
    GbossValueSet vs = BigrGbossData.getValueSetByDiagnosis(ArtsConstants.VALUE_SET_HISTOLOGICAL_TYPE, getDisease());
    return BigrGbossData.getValueSetAsLegalValueSet(vs);
  }
  /**
   * Returns this path report section's "other" histological type.
   *
   * @return  The "other" histological type.
   */
  public String getHistologicalTypeOther() {
    if (_histologicalTypeOther != null)
      return _histologicalTypeOther;
    else if (_dataBean != null)
      return _dataBean.getHistologicalTypeOther();

    return _defaultString;
  }
  /**
   * Returns this path report section's inflammatory cell infiltrate.
   *
   * @return  The inflammatory cell infiltrate.
   */
  public String getInflammCellInfiltrate() {
    if (_inflammCellInfiltrate != null)
      return _inflammCellInfiltrate;
    else if (_dataBean != null)
      return _dataBean.getInflammCellInfiltrate();

    return _defaultString;
  }
  /**
   * Returns the display value of this path report section's inflammatory cell infiltrate.
   *
   * @return  The inflammatory cell infiltrate display value.
   */
  public String getInflammCellInfiltrateDisplay() {
    String code = getInflammCellInfiltrate();
    return (code != null)
      ? GbossFactory.getInstance().getDescription(/*"INFLAMMATORY_CELL_INFILTRATE",*/
    code) : _defaultString;
  }
  /**
   * Returns the list of possible values for inflammatory cell infiltrate.
   *
   * @return  A <code>LegalValueSet</code> of inflammatory cell infiltrate values.
   */
  public LegalValueSet getInflammCellInfiltrateList() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_INFLAMMATORY_CELL_INFILTRATE);
  }
  /**
   * Returns this path report section's joint component.
   *
   * @return  The joint component.
   */
  public String getJointComponent() {
    if (_jointComponent != null)
      return _jointComponent;
    else if (_dataBean != null)
      return _dataBean.getJointComponent();

    return _defaultString;
  }
  /**
   * Returns the display value of this path report section's joint component.
   *
   * @return  The joint component display value.
   */
  public String getJointComponentDisplay() {
    String code = getJointComponent();
    return (code != null) ? GbossFactory.getInstance().getDescription(/*"JOINT_COMPONENT",*/
    code) : _defaultString;
  }
  /**
   * Returns the list of possible values for joint component.
   *
   * @return  A <code>LegalValueSet</code> of joint component values.
   */
  public LegalValueSet getJointComponentList() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_JOINT_COMPONENT); 
  }
  /**
   * Returns this path report section's lymphatic vascular invasion.
   *
   * @return  The lymphatic vascular invasion.
   */
  public String getLymphaticVascularInvasion() {
    if (_lymphaticVascularInvasion != null)
      return _lymphaticVascularInvasion;
    else if (_dataBean != null)
      return _dataBean.getLymphaticVascularInvasion();

    return _defaultString;
  }
  /**
   * Returns the display value of this path report section's lymphatic vascular invasion.
   *
   * @return  The lymphatic vascular invasion display value.
   */
  public String getLymphaticVascularInvasionDisplay() {
    String code = getLymphaticVascularInvasion();
    return (code != null)
      ? GbossFactory.getInstance().getDescription(/*"LYMPHATIC_VASCULAR_INVASION",*/
    code) : _defaultString;
  }
  /**
   * Returns the list of possible values for lymphatic vascular invasion.
   *
   * @return  A <code>LegalValueSet</code> of lymphatic vascular invasion values.
   */
  public LegalValueSet getLymphaticVascularInvasionList() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_LYMPHATIC_VASCULAR_INVASION);
  }
  /**
   * Returns this path report section's lymph node notes.
   *
   * @return  The lymph node notes.
   */
  public String getLymphNodeNotes() {
    if (_lymphNodeNotes != null)
      return _lymphNodeNotes;
    if (_dataBean != null)
      return _dataBean.getLymphNodeNotes();
    return _defaultString;
  }
  /**
   * Returns this path report section's lymph node stage.
   *
   * @return  The lymph node stage.
   */
  public String getLymphNodeStage() {
    if (_lymphNodeStage != null)
      return _lymphNodeStage;
    else if (_dataBean != null)
      return _dataBean.getLymphNodeStage();

    return _defaultString;
  }
  /**
   * Returns the display value of this path report section's lymph node stage.
   *
   * @return  The lymph node stage display value.
   */
  public String getLymphNodeStageDisplay() {
    String code = getLymphNodeStage();
    return (code != null) ? GbossFactory.getInstance().getDescription(/*"LYMPH_NODE_STAGE_DESC",*/
    code) : _defaultString;
  }
  /**
   * Returns this path report section's lymph node stage.
   *
   * @return  The lymph node stage.
   */
  public String getLymphNodeStageG0() {
    return getLymphNodeStage();
  }
  /**
   * Returns this path report section's lymph node stage indicator.
   *
   * @return  The lymph node stage indicator.
   */
  public String getLymphNodeStageInd() {
    if (_lymphNodeStageInd != null)
      return _lymphNodeStageInd;
    else if (_dataBean != null)
      return _dataBean.getLymphNodeStageInd();

    return _defaultString;
  }
  /**
   * Returns the list of possible values for lymph node stage.
   *
   * @return  A <code>LegalValueSet</code> of lymph node stage values.
   */
  public LegalValueSet getLymphNodeStageList() {
    return BigrGbossData.getPdcStageHierarchyLookupList(ArtsConstants.VALUE_SET_DDC_LYMPH_NODE_STAGE_DESC, getDisease());
  }
  /**
   * Returns the list of possible values for lymph node stage if the total
   * number of positive nodes is > 0.
   *
   * @return  A <code>LegalValueSet</code> of lymph node stage values.
   */
  public LegalValueSet getLymphNodeStageListG0() {
    LegalValueSet goList = new LegalValueSet(getLymphNodeStageList());
    for (int i = 0; i < goList.getCount(); i++) {
      LegalValueSet sub = goList.getSubLegalValueSet(i);
      for (int j = sub.getCount() - 1; j >= 0; j--) {
        String value = sub.getValue(j);
        if (value.equals("79420006")
          || value.equals("62455006")
          || value.equals("CA00530C")
          || value.equals("CA00516C")
          || value.equals("CA00435C")
          || value.equals("CA00436C")
          || value.equals("CA00437C")
          || value.equals("CA00438C")
          || value.equals("CA00541C")
          || value.equals("CA00531C")) {
          sub.removeLegalValue(j);
        }
      }
    }
    return goList;
  }
  /**
   * Returns this path report section's "other" lymph node stage.
   *
   * @return  The "other" lymph node stage.
   */
  public String getLymphNodeStageOther() {
    if (_lymphNodeStageOther != null)
      return _lymphNodeStageOther;
    else if (_dataBean != null)
      return _dataBean.getLymphNodeStageOther();

    return _defaultString;
  }
  /**
   * Returns this path report section's involved margins.
   *
   * @return  The involved margins.
   */
  public String getMarginsInvolved() {
    if (_marginsInvolved != null)
      return _marginsInvolved;
    if (_dataBean != null)
      return _dataBean.getMarginsInvolved();
    return _defaultString;
  }
  /**
   * Returns this path report section's uninvolved margins.
   *
   * @return  The uninvolved margins.
   */
  public String getMarginsUninvolved() {
    if (_marginsUninvolved != null)
      return _marginsUninvolved;
    if (_dataBean != null)
      return _dataBean.getMarginsUninvolved();
    return _defaultString;
  }
  /**
   * Returns the <code>Map</code> containing all properties that are part of
   * the minimum pathology data requirements based on the parent path report's
   * disease type.
   *
   * @return  the <code>Map</code> of minimum pathology data properties, or <code>null</code>
   *			if the parent path report is not available or has a null disease type
   */
  private Map getMinPathMapping() {
    String disease = getDisease();
    return (disease != null) ? (Map) _diseaseMinPathMapping.get(disease) : null;
  }
  /**
   * Returns the {@link PathReportHelper} for the parent pathology report of this
   * section.
   *
   * @return  The parent {@link PathReportHelper}.
   */
  public PathReportHelper getPathReport() {
    if (_pathReport == null) {
      String id = getPathReportId();
      if (id == null) {
        _pathReport = new PathReportHelper(new PathReportData());
      }
      else {
        try {
          PathReportData pathData = new PathReportData();
          pathData.setPathReportId(getPathReportId());
          DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
          DDCPathology pathOperation = pathHome.create();
          pathData = pathOperation.getPathReport(pathData);
          setPathReport(new PathReportHelper(pathData));
        }
        catch (Exception e) {
          throw new ApiException(e);
        }
      }
    }
    return _pathReport;
  }
  /**
   * Returns the id of the path report with which this path report section
   * is associated.
   *
   * @return  The path report id.
   */
  public String getPathReportId() {
    if (_pathReportId != null)
      return _pathReportId;
    if (_dataBean != null)
      return _dataBean.getPathReportId();
    return _defaultString;
  }
  /**
   * Returns this path report section's id.
   *
   * @return  The section id.
   */
  public String getPathReportSectionId() {
    if (_pathReportSectionId != null)
      return _pathReportSectionId;
    if (_dataBean != null)
      return _dataBean.getPathReportSectionId();
    return _defaultString;
  }
  /**
   * Returns this path report section's perineural invasion.
   *
   * @return  The perineural invasion.
   */
  public String getPerineuralInvasion() {
    if (_perineuralInvasion != null)
      return _perineuralInvasion;
    if (_dataBean != null)
      return _dataBean.getPerineuralInvasion();
    return _defaultString;
  }
  /**
   * Returns this path report section's perineural invasion display value.
   *
   * @return  The perineural invasion display value.
   */
  public String getPerineuralInvasionDisplay() {
    String code = getPerineuralInvasion();
    return (code != null) ? GbossFactory.getInstance().getDescription(/*"PERINEURAL_INVASION_IND",*/
    code) : _defaultString;
  }
  /**
   * Returns the list of possible values for perineural invasion.
   *
   * @return  A <code>LegalValueSet</code> of perineural invasion values.
   */
  public LegalValueSet getPerineuralInvasionList() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_PERINEURAL_INVASION_IND);
  }
  /**
   * Returns an indicator of wheter this path report section is the
   * primary section or not.
   *
   * @return  'Y' if this is the primary section; 'N' otherwise
   */
  public String getPrimary() {
    if (_primary != null)
      return _primary;
    if (_dataBean != null)
      return _dataBean.getPrimary();
    return _defaultString;
  }
  /**
   * Returns this path report section's identifier.
   *
   * @return  The section identifier.
   */
  public String getSectionIdentifier() {
    if (_sectionIdentifier != null)
      return _sectionIdentifier;
    else if (_dataBean != null)
      return _dataBean.getSectionIdentifier();

    return _defaultString;
  }
  /**
   * Returns the display value of this path report section's identifier.
   *
   * @return  The section identifier display value.
   */
  public String getSectionIdentifierDisplay() {
    String code = getSectionIdentifier();
    return (code != null) ? GbossFactory.getInstance().getDescription(/*"PATH_SECTION_ID",*/
    code) : _defaultString;
  }
  /**
   * Returns the list of possible values for section identifier.
   *
   * @return  A <code>LegalValueSet</code> of section identifier values.
   */
  public LegalValueSet getSectionIdentifierList() {
    LegalValueSet lSet = null;

    try {
      String reportId = getPathReportId();
      String reportSectionId = getPathReportSectionId();
      DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
      DDCPathology pathBean = pathHome.create();
      lSet = pathBean.getSectionIdentifierList(reportId, reportSectionId);
    }
    catch (Exception ex) {
      throw new ApiException(ex);
    }

    return lSet;
  }
  /**
   * Returns this path report section's notes.
   *
   * @return  The section notes.
   */
  public String getSectionNotes() {
    if (_sectionNotes != null)
      return _sectionNotes;
    if (_dataBean != null)
      return _dataBean.getSectionNotes();
    return _defaultString;
  }
  /**
   * Returns this path report section's size of the largest nodal metastasis.
   *
   * @return  The size of the largest nodal metastasis.
   */
  public String getSizeLargestNodalMets() {
    if (_sizeLargestNodalMets != null)
      return _sizeLargestNodalMets;
    if (_dataBean != null)
      return _dataBean.getSizeLargestNodalMets();
    return _defaultString;
  }
  /**
   * Returns this path report section's stage grouping.
   *
   * @return  The stage grouping.
   */
  public String getStageGrouping() {
    if (_stageGrouping != null)
      return _stageGrouping;
    else if (_dataBean != null)
      return _dataBean.getStageGrouping();

    return _defaultString;
  }
  /**
   * Returns this path report section's stage grouping display value.
   *
   * @return  The stage grouping display value.
   */
  public String getStageGroupingDisplay() {
    String code = getStageGrouping();
    return (code != null) ? GbossFactory.getInstance().getDescription(/*"STAGE_GROUPINGS",*/
    code) : _defaultString;
  }
  /**
   * Returns the list of possible values for stage grouping.
   *
   * @return  A <code>LegalValueSet</code> of stage grouping values.
   */
  public LegalValueSet getStageGroupingList() {
    return BigrGbossData.getPdcStageHierarchyLookupList(ArtsConstants.VALUE_SET_DDC_STAGE_GROUPING, getDisease());
  }
  /**
   * Returns this path report section's "other" stage grouping.
   *
   * @return  The "other" stage grouping.
   */
  public String getStageGroupingOther() {
    if (_stageGroupingOther != null)
      return _stageGroupingOther;
    else if (_dataBean != null)
      return _dataBean.getStageGroupingOther();

    return _defaultString;
  }
  /**
   * Returns the finding tissue of this path report section.
   *
   * @return  The finding tissue.
   */
  public String getTissueFinding() {
    if (_tissueFinding != null)
      return _tissueFinding;
    else if (_dataBean != null)
      return _dataBean.getTissueFinding();

    return _defaultString;
  }
  /**
   * Returns the display value of the finding tissue of this path report section.
   *
   * @return  The finding tissue display value.
   */
  public String getTissueFindingDisplay() {
    String code = getTissueFinding();
    return (code != null)
      ? BigrGbossData.getTissueDescription(code)
      : _defaultString;
  }
  /**
   * Returns this path report section's "other" finding tissue.
   *
   * @return  The "other" finding tissue.
   */
  public String getTissueFindingOther() {
    if (_tissueFindingOther != null)
      return _tissueFindingOther;
    if (_dataBean != null)
      return _dataBean.getTissueFindingOther();
    return _defaultString;
  }
  /**
   * Returns the origin tissue of this path report section.
   *
   * @return  The origin tissue.
   */
  public String getTissueOrigin() {
    if (_tissueOrigin != null)
      return _tissueOrigin;
    else if (_dataBean != null)
      return _dataBean.getTissueOrigin();

    return _defaultString;
  }
  /**
   * Returns the display value of the origin tissue of this path report section.
   *
   * @return  The origin tissue display value.
   */
  public String getTissueOriginDisplay() {
    String code = getTissueOrigin();
    return (code != null)
      ? BigrGbossData.getTissueDescription(code)
      : _defaultString;
  }
  /**
   * Returns this path report section's "other" origin tissue.
   *
   * @return  The "other" origin tissue.
   */
  public String getTissueOriginOther() {
    if (_tissueOriginOther != null)
      return _tissueOriginOther;
    if (_dataBean != null)
      return _dataBean.getTissueOriginOther();
    return _defaultString;
  }

  /**
   * Returns the display value of the total number of nodes that were examined.
   *
   * @return  The total number of nodes that were examined display value.
   */
  public String getTotalNodesExaminedDisplay() {
    String code = getTotalNodesExamined();
    return (code != null) ? GbossFactory.getInstance().getDescription(code) : _defaultString;
  }
  /**
   * Returns this path report section's total number of nodes that were examined.
   *
   * @return  The total number of nodes that were examined.
   */
  public String getTotalNodesExamined() {
    if (_totalNodesExamined != null)
      return _totalNodesExamined;
    if (_dataBean != null)
      return _dataBean.getTotalNodesExamined();
    return _defaultString;
  }
  /**
   * Returns the <code>LegalValueSet</code> of legal values for this path 
   * report section's total number of nodes that were examined.
   *
   * @return  The <code>LegalValueSet</code>.
   */
  public LegalValueSet getTotalNodesExaminedList() {
    LegalValueSet total = getLymphNodeCountList();
    total.removeLegalValue("CA01042C");
    return total;
  }
  /**
   * Returns the display value of the total number of nodes that are positive.
   *
   * @return  The total number of nodes that are positive display value.
   */
  public String getTotalNodesPositiveDisplay() {
    String code = getTotalNodesPositive();
    return (code != null) ? GbossFactory.getInstance().getDescription(code) : _defaultString;
  }
  /**
   * Returns this path report section's total number of nodes that are positive.
   *
   * @return  The total number of nodes that are positive.
   */
  public String getTotalNodesPositive() {
    if (_totalNodesPositive != null)
      return _totalNodesPositive;
    if (_dataBean != null)
      return _dataBean.getTotalNodesPositive();
    return _defaultString;
  }
  /**
   * Returns the <code>LegalValueSet</code> of legal values for this path 
   * report section's total number of nodes that were positive.
   *
   * @return  The <code>LegalValueSet</code>.
   */
  public LegalValueSet getTotalNodesPositiveList() {
    return getLymphNodeCountList();
  }
  /**
   * Returns the <code>LegalValueSet</code> of legal values for this path 
   * report section's total number of lymph nodes .
   *
   * @return  The <code>LegalValueSet</code>.
   */
  private LegalValueSet getLymphNodeCountList() {
    LegalValueSet lymphNodes = new LegalValueSet();
    //Get value set for DDC_COUNT_LYMPH_NODES.
    GbossValueSet nodes = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_DDC_COUNT_LYMPH_NODES);
    Iterator roots = nodes.getValues().iterator();
    Iterator children = null;

    while (roots.hasNext()) {
      GbossValue root = (GbossValue) roots.next();
      children = root.getValues().iterator();

      while (children.hasNext()) {
        GbossValue child = (GbossValue) children.next();
        lymphNodes.addLegalValue(child.getCui(), child.getDescription());
      }
    }
    return lymphNodes;
  }
  /**
   * Returns this path report section's tumor configuration.
   *
   * @return  The tumor configuration.
   */
  public String getTumorConfig() {
    if (_tumorConfig != null)
      return _tumorConfig;
    else if (_dataBean != null)
      return _dataBean.getTumorConfig();

    return _defaultString;
  }
  /**
   * Returns the display value of this path report section's tumor configuration.
   *
   * @return  The tumor configuration display value.
   */
  public String getTumorConfigDisplay() {
    String code = getTumorConfig();
    return (code != null) ? GbossFactory.getInstance().getDescription(/*"TUMOR_CONFIGURATION",*/
    code) : _defaultString;
  }
  /**
   * Returns the list of possible values for tumor configuration.
   *
   * @return  A <code>LegalValueSet</code> of tumor configuration values.
   */
  public LegalValueSet getTumorConfigList() {
    GbossValueSet vs = BigrGbossData.getValueSetByDiagnosis(ArtsConstants.VALUE_SET_TUMOR_CONFIGURATION, getDisease());
    return BigrGbossData.getValueSetAsLegalValueSet(vs);
  }
  /**
   * Returns this path report section's first tumor size dimension.
   *
   * @return  The tumor size dimension.
   */
  public String getTumorSize1() {
    return _tumorSize1;
  }
  /**
   * Returns this path report section's second tumor size dimension.
   *
   * @return  The tumor size dimension.
   */
  public String getTumorSize2() {
    return _tumorSize2;
  }
  /**
   * Returns this path report section's third tumor size dimension.
   *
   * @return  The tumor size dimension.
   */
  public String getTumorSize3() {
    return _tumorSize3;
  }
  /**
   * Returns this path report section's tumor size.
   *
   * @return  The tumor size.
   */
  public String getTumorSizeDisplay() {
    if (_tumorSizeNS)
      return "Not Specified";
    else if ((_tumorSize1 != null) || (_tumorSize2 != null) || (_tumorSize3 != null)) {
      StringBuffer buf = new StringBuffer();
      if (_tumorSize1 != null)
        buf.append(_tumorSize1);
      else
        buf.append("&nbsp;");
      buf.append(" x ");
      if (_tumorSize2 != null)
        buf.append(_tumorSize2);
      else
        buf.append("&nbsp;");
      buf.append(" x ");
      if (_tumorSize3 != null)
        buf.append(_tumorSize3);
      else
        buf.append("&nbsp;");
      return buf.toString();
    }
    else
      return _defaultString;
  }
  /**
   * Returns this path report section's tumor stage description.
   *
   * @return  The tumor stage description.
   */
  public String getTumorStageDesc() {
    if (_tumorStageDesc != null)
      return _tumorStageDesc;
    else if (_dataBean != null)
      return _dataBean.getTumorStageDesc();

    return _defaultString;
  }
  /**
   * Returns the display value of this path report section's tumor stage description.
   *
   * @return  The tumor stage description display value.
   */
  public String getTumorStageDescDisplay() {
    String code = getTumorStageDesc();
    return (code != null) ? GbossFactory.getInstance().getDescription(/*"TUMOR_STAGE_DESC",*/
    code) : _defaultString;
  }
  /**
   * Returns this path report section's tumor stage description indicator.
   *
   * @return  The tumor stage description indicator.
   */
  public String getTumorStageDescInd() {
    if (_tumorStageDescInd != null)
      return _tumorStageDescInd;
    else if (_dataBean != null)
      return _dataBean.getTumorStageDescInd();

    return _defaultString;
  }
  /**
   * Returns the list of possible values for tumor stage description.
   *
   * @return  A <code>LegalValueSet</code> of tumor stage description values.
   */
  public LegalValueSet getTumorStageDescList() {
    return BigrGbossData.getPdcStageHierarchyLookupList(ArtsConstants.VALUE_SET_DDC_TUMOR_STAGE_DESC, getDisease());
  }
  /**
   * Returns this path report section's "other" tumor stage description.
   *
   * @return  The "other" tumor stage description.
   */
  public String getTumorStageDescOther() {
    if (_tumorStageDescOther != null)
      return _tumorStageDescOther;
    else if (_dataBean != null)
      return _dataBean.getTumorStageDescOther();

    return _defaultString;
  }
  /**
   * Returns this path report section's tumor stage type.
   *
   * @return  The tumor stage type.
   */
  public String getTumorStageType() {
    if (_tumorStageType != null)
      return _tumorStageType;
    else if (_dataBean != null)
      return _dataBean.getTumorStageType();

    return _defaultString;
  }
  /**
   * Returns the display value of this path report section's tumor stage type.
   *
   * @return  The tumor stage type display value.
   */
  public String getTumorStageTypeDisplay() {
    String code = getTumorStageType();
    return (code != null) ? GbossFactory.getInstance().getDescription(/*"TUMOR_STAGE_TYPE",*/
    code) : _defaultString;
  }
  /**
   * Returns the list of possible values for tumor stage type.
   *
   * @return  A <code>LegalValueSet</code> of tumor stage type values.
   */
  public LegalValueSet getTumorStageTypeList() {
    GbossValueSet vs = BigrGbossData.getValueSetByDiagnosis(ArtsConstants.VALUE_SET_TUMOR_STAGE_TYPE, getDisease());
    return BigrGbossData.getValueSetAsLegalValueSet(vs);
  }
  /**
   * Returns this path report section's "other" tumor stage type.
   *
   * @return  The "other" tumor stage type.
   */
  public String getTumorStageTypeOther() {
    if (_tumorStageTypeOther != null)
      return _tumorStageTypeOther;
    else if (_dataBean != null)
      return _dataBean.getTumorStageTypeOther();

    return _defaultString;
  }
  /**
   * Returns this path report section's tumor weight.
   *
   * @return  The tumor weight.
   */
  public String getTumorWeight() {
    return _tumorWeight;
  }
  /**
   * Returns this path report section's tumor weight.
   *
   * @return  The tumor weight.
   */
  public String getTumorWeightDisplay() {
    if (_tumorWeightNS)
      return "Not Specified";
    else if (_tumorWeight != null)
      return _tumorWeight;
    else
      return _defaultString;
  }
  /**
   * Returns this path report section's venous vessel invasion.
   *
   * @return  The venous vessel invasion.
   */
  public String getVenousVesselInvasion() {
    if (_venousVesselInvasion != null)
      return _venousVesselInvasion;
    else if (_dataBean != null)
      return _dataBean.getVenousVesselInvasion();

    return _defaultString;
  }
  /**
   * Returns the display value of this path report section's venous vessel invasion.
   *
   * @return  The venous vessel invasion display value.
   */
  public String getVenousVesselInvasionDisplay() {
    String code = getVenousVesselInvasion();
    return (code != null) ? GbossFactory.getInstance().getDescription(/*"VENOUS_VESSEL_INVASION",*/
    code) : _defaultString;
  }
  /**
   * Returns the list of possible values for venous vessel invasion.
   *
   * @return  A <code>LegalValueSet</code> of venous vessel invasion values.
   */
  public LegalValueSet getVenousVesselInvasionList() {
    return BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_VENOUS_VESSEL_INVASION);
  }
  /**
   * Returns <code>true</code> if this path report section's cell infiltrate amount
   * and type of inflammatory cell infiltrate fields should be displayed, based 
   * on the parent path report's disease type.
   *
   * @return  <code>true</code> if cell infiltrate fields should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayCellInfiltrate() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("cellInfiltrate") : false;
  }

  /**
   * Returns <code>true</code> if this path report section's distant metastasis
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if distant metastasis should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayDistantMetastasis() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("distantMetastasis") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's extensive intraductal component
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if extensive intraductal component should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayExtensiveIntraductalComp() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("extensiveIntraductalComp") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's extranodal spread
   * and size of largest nodal metastasis fields should be displayed, based on 
   * the parent path report's disease type.
   *
   * @return  <code>true</code> if extranodal fields should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayExtranodal() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("extranodal") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's primary, secondary
   * and total gleason score fields should be displayed, based on the parent 
   * path report's disease type.
   *
   * @return  <code>true</code> if gleason scores should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayGleason() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("gleason") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's histological nuclear grade
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if histological nuclear grade should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayHistologicalNuclearGrade() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("histologicalNuclearGrade") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's "other" histological nuclear grade
   * field should be displayed.
   *
   * @return  <code>true</code> if "other" histological nuclear grade should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayHistologicalNuclearGradeOther() {
    /*
    if (isDisplayHistologicalNuclearGrade()) {
    return (("DI00000002".equals(getConsentAccount())) && ("12692005".equals(getDisease()))) ? false : true;
    }
    return false;
    */
    //Modified for MR 5527 by Nagaraja. 
    //Above rule is no longer valid because for HNG list for DI00000002
    //is modified. 
    return isDisplayHistologicalNuclearGrade();
  }
  /**
   * Returns <code>true</code> if this path report section's histological type
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if histological type should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayHistologicalType() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("histologicalType") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's joint component
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if joint component should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayJointComponent() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("jointComponent") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's lymphatic vascular invasion
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if lymphatic vascular invasion should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayLymphaticVascularInvasion() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("lymphaticVascularInvasion") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's lymph node notes
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if lymph node notes should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayLymphNodeNotes() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("lymphNodeNotes") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's lymph node stage
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if lymph node stage should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayLymphNodeStage() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("lymphNodeStage") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's margins involved and
   * margins uninvolved fields should be displayed, based on the parent path 
   * report's disease type.
   *
   * @return  <code>true</code> if margins should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayMargins() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("margins") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's perineural invasion
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if perineural invasion should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayPerineuralInvasion() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("perineuralInvasion") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's stage grouping
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if stage grouping should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayStageGrouping() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("stageGrouping") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's total nodes examined
   * and positive fields should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if total nodes should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayTotalNodes() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("totalNodes") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's tumor configuration
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if tumor configuration should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayTumorConfig() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("tumorConfig") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's tumor size
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if tumor size should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayTumorSize() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("tumorSize") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's tumor stage description
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if tumor stage description should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayTumorStageDesc() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("tumorStageDesc") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's tumor stage type
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if tumor stage type should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayTumorStageType() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("tumorStageType") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's tumor weight
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if tumor weight should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayTumorWeight() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("tumorWeight") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's venous vessel invasion
   * field should be displayed, based on the parent path report's disease type.
   *
   * @return  <code>true</code> if venous vessel invasion should be displayed;
   *			<code>false</code> otherwise.
   */
  public boolean isDisplayVenousVesselInvasion() {
    Map fields = getDiseaseMapping();
    return (fields != null) ? fields.containsKey("venousVesselInvasion") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's cell infiltrate amount
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if cell infiltrate amount is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathCellInfiltrateAmount() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("cellInfiltrateAmount") : false;
  }

  /**
   * Returns <code>true</code> if this path report section's diagnosis
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if diagnosis is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathDiagnosis() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("diagnosis") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's "other" diagnosis
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if "other" diagnosis is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathDiagnosisOther() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("diagnosisOther") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's distant metastasis
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if distant metastasis is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathDistantMetastasis() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("distantMetastasis") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's "other" distant metastasis
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if "other" distant metastasis is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathDistantMetastasisOther() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("distantMetastasisOther") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's extensive intraductal component
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if extensive intraductal component is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathExtensiveIntraductalComp() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("extensiveIntraductalComp") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's extranodal spread
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if extranodal spread is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathExtranodalSpread() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("extranodalSpread") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's gleason score
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if gleason score is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathGleason() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("gleason") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's histological nuclear grade
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if histological nuclear grade is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathHistologicalNuclearGrade() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("histologicalNuclearGrade") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's "other" histological nuclear grade
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if "other" histological nuclear grade is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathHistologicalNuclearGradeOther() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("histologicalNuclearGradeOther") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's histological type
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if histological type is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathHistologicalType() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("histologicalType") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's "other" histological type
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if "other" histological type is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathHistologicalTypeOther() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("histologicalTypeOther") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's inflammatory cell infiltrate
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if inflammatory cell infiltrate is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathInflammCellInfiltrate() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("inflammCellInfiltrate") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's joint component
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if joint component is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathJointComponent() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("jointComponent") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's lymphatic vascular invasion
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if lymphatic vascular invasion is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathLymphaticVascularInvasion() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("lymphaticVascularInvasion") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's lymph node notes
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if lymph node notes is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathLymphNodeNotes() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("lymphNodeNotes") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's lymph node stage
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if lymph node stage is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathLymphNodeStage() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("lymphNodeStage") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's "other" lymph node stage
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if "other" lymph node stage is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathLymphNodeStageOther() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("lymphNodeStageOther") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's involved margins
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if involved margins is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathMarginsInvolved() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("marginsInvolved") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's uninvolved margins
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if uninvolved margins is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathMarginsUninvolved() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("marginsUninvolved") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's perineural invasion
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if perineural invasion is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathPerineuralInvasion() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("perineuralInvasion") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's notes
   * are part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if notes is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathSectionNotes() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("sectionNotes") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's size of the largest nodal metastasis
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if size of the largest nodal metastasis is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathSizeLargestNodalMets() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("sizeLargestNodalMets") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's stage grouping
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if stage grouping is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathStageGrouping() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("stageGrouping") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's "other" stage grouping
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if "other" stage grouping is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathStageGroupingOther() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("stageGroupingOther") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's finding tissue 
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if finding tissue is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathTissueFinding() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("tissueFinding") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's "other" finding tissue 
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if "other" finding tissue is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathTissueFindingOther() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("tissueFindingOther") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's origin tissue 
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if origin tissue is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathTissueOrigin() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("tissueOrigin") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's "other" origin tissue 
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if "other" origin tissue is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathTissueOriginOther() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("tissueOriginOther") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's total number of nodes that were examined
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if total number of nodes that were examined is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathTotalNodesExamined() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("totalNodesExamined") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's total number of nodes that were positive
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if total number of nodes that were positive is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathTotalNodesPositive() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("totalNodesPositive") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's tumor configuration
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if tumor configuration is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathTumorConfig() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("tumorConfig") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's tumor size
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if tumor size is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathTumorSize() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("tumorSize") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's tumor stage description
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if tumor stage description is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathTumorStageDesc() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("tumorStageDesc") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's "other" tumor stage description
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if "other" tumor stage description is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathTumorStageDescOther() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("tumorStageDescOther") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's tumor stage type
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if tumor stage type is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathTumorStageType() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("tumorStageType") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's "other" tumor stage type
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if "other" tumor stage type is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathTumorStageTypeOther() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("tumorStageTypeOther") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's tumor weight
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if tumor weight is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathTumorWeight() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("tumorWeight") : false;
  }
  /**
   * Returns <code>true</code> if this path report section's venous vessel invasion
   * is part of the minimum pathology data, based on the parent path report's 
   * disease type.
   *
   * @return  <code>true</code> if venous vessel invasion is part of min path data;
   *			<code>false</code> otherwise.
   */
  public boolean isMinPathVenousVesselInvasion() {
    Map fields = getMinPathMapping();
    return (fields != null) ? fields.containsKey("venousVesselInvasion") : false;
  }
  /**
   * Returns <code>true</code> if this helper is for a new
   * path report section; <code>false</code> otherwise.
   * 
   * @return  A boolean indicating whether this path report section is new.
   */
  public boolean isNew() {
    return (isEmpty(getPathReportSectionId()));
  }
  /**
   * Returns <code>true</code> if this path report section's tumor size is not specified;
   * <code>false</code> otherwise.
   *
   * @return  The tumor size not specified indication.
   */
  public boolean isTumorSizeNS() {
    return _tumorSizeNS;
  }
  /**
   * Returns <code>true</code> if this path report section's tumor weight is not specified;
   * <code>false</code> otherwise.
   *
   * @return  The tumor weight not specified indication.
   */
  public boolean isTumorWeightNS() {
    return _tumorWeightNS;
  }
  /**
   * Returns <code>true</code> if the pathology section data associated 
   * with this helper is valid; otherwise returns <code>false</code>.
   * If validation fails, the {@link #getMessages()} method can be called to
   * display a message to the user indicating the problem(s).
   *
   * @return  <code>true</code> if all parameters are valid;
   *			<code>false</code> otherwise.
   */
  public boolean isValid() {

    boolean isValid = true;

    String identifier = getSectionIdentifier();
    if (ApiFunctions.isEmpty(identifier)) {
      getMessageHelper().addMessage("Section Identifier must be specified");
      isValid = false;
    }

    if (ApiFunctions.isEmpty(getPrimary())) {
      getMessageHelper().addMessage("Section Type must be specified");
      isValid = false;
    }

    String diagnosis = getDiagnosis();
    if (ApiFunctions.isEmpty(diagnosis)) {
      getMessageHelper().addMessage("Section Diagnosis from DI Pathology Report must be specified");
      isValid = false;
    }
    else if (FormLogic.OTHER_DX.equals(diagnosis) && ApiFunctions.isEmpty(getDiagnosisOther())) {
      getMessageHelper().addMessage(
        "Other Section Diagnosis from DI Pathology Report must be specified");
      isValid = false;
    }

    String tissue = getTissueOrigin();
    if (ApiFunctions.isEmpty(tissue)) {
      getMessageHelper().addMessage("Section Tissue of Origin of Diagnosis must be specified");
      isValid = false;
    }
    else if (
      FormLogic.OTHER_TISSUE.equals(tissue) && ApiFunctions.isEmpty(getTissueOriginOther())) {
      getMessageHelper().addMessage(
        "Other Section Tissue of Origin of Diagnosis must be specified");
      isValid = false;
    }

    tissue = getTissueFinding();
    if (ApiFunctions.isEmpty(tissue)) {
      getMessageHelper().addMessage("Section Site of Finding must be specified");
      isValid = false;
    }
    else if (
      FormLogic.OTHER_TISSUE.equals(tissue) && ApiFunctions.isEmpty(getTissueFindingOther())) {
      getMessageHelper().addMessage("Other Section Site of Finding must be specified");
      isValid = false;
    }

    if (FormLogic.OTHER_HISTOLOGICAL_TYPE.equals(getHistologicalType())
      && ApiFunctions.isEmpty(getHistologicalTypeOther())) {
      getMessageHelper().addMessage("Other Histologic Type must be specified");
      isValid = false;
    }

    if (FormLogic.OTHER_HISTOLOGICAL_NUCLEAR_GRADE.equals(getHistologicalNuclearGrade())
      && isDisplayHistologicalNuclearGradeOther()
      && ApiFunctions.isEmpty(getHistologicalNuclearGradeOther())) {
      getMessageHelper().addMessage("Other Histologic Nuclear Grade must be specified");
      isValid = false;
    }
    //Added following validation for MR 5846 only for HNG with Neoplasm of Prostate.
    if (ApiFunctions.safeEquals(getDisease(), "126906006")) {
      //Add validation if Total Gleason Score is not null and HNG is not null.
      if (!ApiFunctions.isEmpty(getGleasonTotal())) {
        int totalGleason = ApiFunctions.safeInteger(getGleasonTotal(), 0);

        if ((totalGleason != 0) && (ApiFunctions.isEmpty(getHistologicalNuclearGrade()))) {
          getMessageHelper().addMessage("Histologic Nuclear Grade must be entered.");
          isValid = false;
        }
        else {

          //If Total Gleason Score = 2-4, then only G1 is valid 
          if ((totalGleason >= 2) && (totalGleason <= 4)) {
            if (!ApiFunctions.safeEquals(getHistologicalNuclearGrade(), "CA00175C")) {
              getMessageHelper().addMessage(
                "Total Gleason Score = 2-4. Histologic Nuclear Grade must = "
                  + GbossFactory.getInstance().getDescription("CA00175C")
                  + ".");
              isValid = false;
            }
          }
          //If Total Gleason Score = 5-6, then only G2 is valid 
          if ((totalGleason >= 5) && (totalGleason <= 6)) {
            if (!ApiFunctions.safeEquals(getHistologicalNuclearGrade(), "CA00176C")) {
              getMessageHelper().addMessage(
                "Total Gleason Score = 5-6. Histologic Nuclear Grade must = "
                  + GbossFactory.getInstance().getDescription("CA00176C")
                  + ".");
              isValid = false;
            }
          }
          //If Total Gleason Score = 7-10, then only G3-4 is valid 
          if ((totalGleason >= 7) && (totalGleason <= 10)) {
            if (!ApiFunctions.safeEquals(getHistologicalNuclearGrade(), "369727006")) {
              getMessageHelper().addMessage(
                "Total Gleason Score = 7-10. Histologic Nuclear Grade must = "
                  + GbossFactory.getInstance().getDescription("369727006")
                  + ".");
              isValid = false;
            }
          }

        }
      }
    }

    if (FormLogic.OTHER_TUMOR_STAGE_TYPE.equals(getTumorStageType())
      && ApiFunctions.isEmpty(getTumorStageTypeOther())) {
      getMessageHelper().addMessage("Other Tumor Stage Classification must be specified");
      isValid = false;
    }

    if (FormLogic.OTHER_TUMOR_STAGE_DESC.equals(getTumorStageDesc())
      && ApiFunctions.isEmpty(getTumorStageDescOther())) {
      getMessageHelper().addMessage("Other Tumor Stage must be specified");
      isValid = false;
    }

    if (!ApiFunctions.isEmpty(getTumorStageDesc())
      && !FormLogic.NOT_REPORTED_TUMOR_STAGE_DESC.equals(getTumorStageDesc())
      && ApiFunctions.isEmpty(getTumorStageDescInd())
      && !ArtsConstants.CODE_TUMOR_STAGE_TYPE_ANNARBOR.equals(getTumorStageType())) {
      getMessageHelper().addMessage("Reported or Derived must be specified for Tumor Stage");
      isValid = false;
    }

    if (FormLogic.OTHER_LYMPH_NODE_STAGE_DESC.equals(getLymphNodeStage())
      && ApiFunctions.isEmpty(getLymphNodeStageOther())) {
      getMessageHelper().addMessage("Other Lymph Node Stage must be specified");
      isValid = false;
    }

    if (!ApiFunctions.isEmpty(getLymphNodeStage())
      && !FormLogic.NOT_REPORTED_LYMPH_NODE_STAGE_DESC.equals(getLymphNodeStage())
      && ApiFunctions.isEmpty(getLymphNodeStageInd())
      && !ArtsConstants.CODE_TUMOR_STAGE_TYPE_ANNARBOR.equals(getTumorStageType())) {
      getMessageHelper().addMessage("Reported or Derived must be specified for Lymph Node Stage");
      isValid = false;
    }

    if (FormLogic.OTHER_DISTANT_METASTASIS.equals(getDistantMetastasis())
      && ApiFunctions.isEmpty(getDistantMetastasisOther())) {
      getMessageHelper().addMessage("Other Distant Metastasis must be specified");
      isValid = false;
    }

    if (!ApiFunctions.isEmpty(getDistantMetastasis())
      && !FormLogic.NOT_REPORTED_DISTANT_METASTASIS.equals(getDistantMetastasis())
      && ApiFunctions.isEmpty(getDistantMetastasisInd())
      && !ArtsConstants.CODE_TUMOR_STAGE_TYPE_ANNARBOR.equals(getTumorStageType())) {
      getMessageHelper().addMessage("Reported or Derived must be specified for Distant Metastasis");
      isValid = false;
    }

    if (FormLogic.OTHER_STAGE_GROUPING.equals(getStageGrouping())
      && ApiFunctions.isEmpty(getStageGroupingOther())) {
      getMessageHelper().addMessage("Other Minimum Stage Grouping must be specified");
      isValid = false;
    }

    String margins = getMarginsInvolved();
    if (!ApiFunctions.isEmpty(margins) && (margins.length() > 200)) {
      getMessageHelper().addMessage(
        "Margins Involved by Tumor must not be greater than 200 characters");
      isValid = false;
    }

    margins = getMarginsUninvolved();
    if (!ApiFunctions.isEmpty(margins) && (margins.length() > 200)) {
      getMessageHelper().addMessage(
        "Margins Uninvolved (Include Distance) must not be greater than 200 characters");
      isValid = false;
    }

    // A section with the same section identifier cannot already exist for this path report.
    if (!ApiFunctions.isEmpty(identifier)) {
      try {
        PathReportSectionData dataBean = new PathReportSectionData();
        dataBean.setPathReportId(getPathReportId());
        dataBean.setPathReportSectionId(getPathReportSectionId());
        dataBean.setSectionIdentifier(identifier);
        DDCPathologyHome pathHome = (DDCPathologyHome) EjbHomes.getHome(DDCPathologyHome.class);
        DDCPathology pathologyOp = pathHome.create();
        if (pathologyOp.isExistsPathReportSection(dataBean)) {
          getMessageHelper().addMessage(
            "A section with the specified Section Identifier already exists for this pathology report.");
          isValid = false;
        }
      }
      catch (Exception e) {
        throw new ApiException(e);
      }
    }

    if (!isValid) {
      getMessageHelper().setError(true);
    }
    return isValid;
  }
  /**
   * Parses the tumor size obtained from the data bean appropriately.
   * The tumor size will either be in the form dimension1, dimension2, 
   * dimension3, or will be the string NS to indicate not specified.
   * In addition, any dimension may or may not be specified, and not
   * all dimensions have to be specified.  This method populates the 
   * member variables that hold the 3 separate  dimensions and the 
   * not specified flag.
   */
  private void parseTumorSize() {
    if (_dataBean != null) {
      String size = _dataBean.getTumorSize();
      if (size != null) {
        if (size.equals("NS"))
          _tumorSizeNS = true;
        else {
          StringTokenizer tokens = new StringTokenizer(size, ",", true);
          if (tokens.countTokens() > 0) {
            int i = 1;
            while (tokens.hasMoreTokens()) {
              String dimension = tokens.nextToken();
              if (dimension.equals(",")) {
                i++;
              }
              else {
                switch (i) {
                  case 1 :
                    _tumorSize1 = dimension;
                    break;
                  case 2 :
                    _tumorSize2 = dimension;
                    break;
                  case 3 :
                    _tumorSize3 = dimension;
                    break;
                }
              }
            }
          }
        }
      }
    }
  }
  /**
   * Parses the tumor weight obtained from the data bean appropriately.
   * The tumor size will either be a number that is the weight,
   * or will be the string NS to indicate not specified.  This method 
   * populates the member variables that hold the weight and the
   * not specified flag.
   */
  private void parseTumorWeight() {
    if (_dataBean != null) {
      String weight = _dataBean.getTumorWeight();
      if (weight != null) {
        if (weight.equals("NS"))
          _tumorWeightNS = true;
        else
          _tumorWeight = weight;
      }
    }
  }
  /**
   * Sets the {@link PathReportHelper} of the parent pathology report of this
   * section.
   *
   * @param  helper  the parent {@link PathReportHelper}.
   */
  public void setPathReport(PathReportHelper helper) {
    _pathReport = helper;
  }
  /**
   * Returns a <code>Map</code> of URL parameters for the section
   * link, in support of the Struts html:link tag.
   * 
   * @return  The URL parameters.
   */
  public Map getSectionLinkParams() {
    if (_sectionLinkParams == null) {
      _sectionLinkParams = new HashMap();
      _sectionLinkParams.put("op", "PathSectionSummaryPrepare");
      _sectionLinkParams.put("pathReportId", getPathReportId());
      _sectionLinkParams.put("pathReportSectionId", getPathReportSectionId());
      _sectionLinkParams.put("donorImportedYN", getDonorImportedYN());
    }
    return _sectionLinkParams;
  }
  /**
   */
  public Map getEditSectionLinkParams() {
    if (_editSectionLinkParams == null) {
      _editSectionLinkParams = new HashMap();
      _editSectionLinkParams.put("op", "PathSectionPrepare");
      _editSectionLinkParams.put("pathReportId", getPathReportId());
      _editSectionLinkParams.put("pathReportSectionId", getPathReportSectionId());
      _editSectionLinkParams.put("donorImportedYN", getDonorImportedYN());
    }
    return _editSectionLinkParams;
  }
  /**
   */
  public Map getNewFindingLinkParams() {
    if (_newFindingLinkParams == null) {
      _newFindingLinkParams = new HashMap();
      _newFindingLinkParams.put("op", "AdditionalFindingPrepare");
      _newFindingLinkParams.put("pathReportId", getPathReportId());
      _newFindingLinkParams.put("pathReportSectionId", getPathReportSectionId());
      _newFindingLinkParams.put("donorImportedYN", getDonorImportedYN());
      _newFindingLinkParams.put("consentId", getConsentId());
    }
    return _newFindingLinkParams;
  }
  /**
   */
  public String getStartFindings() {
    _findingsIndex = 0;
    _editFindingLinkParams = new HashMap();
    _editFindingLinkParams.put("op", "AdditionalFindingPrepare");
    _editFindingLinkParams.put("pathReportId", getPathReportId());
    return null;
  }
  /**
   */
  public String getNextFinding() {
    _findingsIndex++;
    return null;
  }
  /**
   */
  private PathReportSectionFindingHelper getCurrentFinding() {
    return (PathReportSectionFindingHelper) _findings.get(_findingsIndex);
  }
  public Map getCurrentFindingLinkParams() {
    PathReportSectionFindingHelper finding = getCurrentFinding();
    _editFindingLinkParams.put("findingId", finding.getFindingId());
    _editFindingLinkParams.put("donorImportedYN", getDonorImportedYN());
    _editFindingLinkParams.put("consentId", getConsentId());
    return _editFindingLinkParams;
  }
  /**
   * @return
   */
  public String getDonorImportedYN() {
    return _donorImportedYN;
  }

  /**
   * @param string
   */
  public void setDonorImportedYN(String string) {
    _donorImportedYN = string;
  }

}
