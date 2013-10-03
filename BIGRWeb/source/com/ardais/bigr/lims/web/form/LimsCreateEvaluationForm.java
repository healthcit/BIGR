package com.ardais.bigr.lims.web.form;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.lims.btx.BTXDetailsCreatePathologyEvaluation;
import com.ardais.bigr.lims.btx.BTXDetailsCreatePathologyEvaluationPrepare;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.lims.javabeans.IncidentReportLineItem;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationFeatureData;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationSampleData;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionData;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;
import com.gulfstreambio.gboss.GbossValue;
import com.gulfstreambio.gboss.GbossValueSet;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LimsCreateEvaluationForm extends BigrActionForm {
  private String _slideId;
  private String _sampleId;
  private String _diagnosis;
  private String _diagnosisOther;
  private String _tissueOfOrigin;
  private String _tissueOfOriginOther;
  private String _tissueOfFinding;
  private String _tissueOfFindingOther;
  private String[] _lesionCodeList;
  private String[] _lesionTextList;
  private String[] _lesionValueList;
  private String _lesionValue;
  private String[] _lesionIsOtherList;
  private String[] _structureCodeList;
  private String[] _structureTextList;
  private String[] _structureValueList;
  private String[] _structureIsOtherList;
  private String[] _inflammationCodeList;
  private String[] _inflammationTextList;
  private String[] _inflammationValueList;
  private String[] _inflammationIsOtherList;
  private String[] _internalFeaturesList;
  private String[] _internalOtherList;
  private String[] _externalFeaturesList;
  private String[] _externalOtherList;
  private String[] _tumorFeatureCodeList;
  private String[] _tumorFeatureTextList;
  private String[] _tumorFeatureIsOtherList;
  private String[] _cellularFeatureCodeList;
  private String[] _cellularFeatureTextList;
  private String[] _cellularFeatureIsOtherList;
  private String[] _hypoCellularFeatureCodeList;
  private String[] _hypoCellularFeatureTextList;
  private String[] _hypoCellularFeatureIsOtherList;
  private String _reason;
  private String _consentId;
  private String _asmPosition;
  private String _invStatus;
  private String _primaryPathReportDx;
  private String[] _pathReportDxList;
  private String _asmTissue;
  private String _primaryTissueOfFinding;
  private String _primaryTissueOfOrigin;
  private String _grossAppearance;
  private String _normalValue;
  private String _tumorValue;
  private String _hcstromaValue;
  private String _hastromaValue;
  private String _necrosisValue;
  private String _donorId;
  private String _pathReportId;
  private String _rawPathReport;
  private String _reportEvaluation;
  private String _createMethod;
  private boolean _showWarning;
  private boolean _userWarned;
  private String _warningMessage;
  private String _viewPvReport;
  private String _sourceEvaluationId;
  private boolean _reviewedRawPathReport;

  private String _popup;

  private String _message;

  private String _tissueDropDown;
  private String _structureDropDown;
  private String _structureJavaScript;

  private String _lesionDropDown;
  private String _lesionJavaScript;
  
  private String[] _incidentList;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {

    setPopup(null);
  }

  private void populateStructureInfo() {
    TreeMap tissue = new TreeMap();
    TreeMap structure = new TreeMap();
    StringBuffer javascript = new StringBuffer(2048);
    StringBuffer javascriptStruct = new StringBuffer(2048);
    StringBuffer tissueSelect = new StringBuffer(2048);
    StringBuffer structureSelect = new StringBuffer(2048);

    GbossValueSet structures = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_LIMS_STRUCTURE);
    Iterator iterator = structures.getValues().iterator();

    javascript.append("function addStructuresByTissue(obj){\n");
    javascriptStruct.append("function addStructuresByStructure(obj){\n");
    
    while (iterator.hasNext()) {
      GbossValue root = (GbossValue)iterator.next();
      tissue.put(root.getDescription(), root.getCui());
      Iterator children = root.getValues().iterator();

      javascript.append("if (obj == '" + root.getCui() + "'){\n");
      while (children.hasNext()) {
        GbossValue child = (GbossValue) children.next();
        if (LimsConstants.OTHER_STRUCTURE.equals(child.getCui())) {
          continue;
        }
        structure.put(child.getDescription(), child.getCui());
        javascriptStruct.append("if (obj == '" + child.getCui() + "'){\n");
        javascript.append("addStructure('");
        javascriptStruct.append("addStructure('");
        javascript.append(child.getDescription());
        javascriptStruct.append(child.getDescription());
        javascript.append("', '");
        javascriptStruct.append("', '");
        javascript.append(child.getCui());
        javascriptStruct.append(child.getCui());
        javascript.append("', false, 0);\n");
        javascriptStruct.append("', false, 0);\n");
        javascriptStruct.append("return true;}\n");

      }
      javascript.append("return true;}\n");
    }

    javascript.append("}\n");
    javascriptStruct.append("}\n");

    javascript.append(javascriptStruct);

    tissueSelect.append("<select name=tissueDrop tabindex=\"16\">");
    tissueSelect.append("<option></option>");
    Iterator i = tissue.keySet().iterator();
    while (i.hasNext()) {
      String key = (String) i.next();
      String d = (String) tissue.get(key);
      tissueSelect.append("<option value='" + d + "'>");
      tissueSelect.append(key);
      tissueSelect.append("</option>\n");
    }
    tissueSelect.append("</select>");

    structureSelect.append("<select name=structureDrop tabindex=\"16\">");
    structureSelect.append("<option></option>");
    i = structure.keySet().iterator();
    while (i.hasNext()) {
      String key = (String) i.next();
      String d = (String) structure.get(key);
      if (LimsConstants.OTHER_STRUCTURE.equals(d)) {
        continue;
      }
      structureSelect.append("<option value='" + d + "'>");
      structureSelect.append(key);
      structureSelect.append("</option>\n");
    }
    structureSelect.append("</select>");

    setTissueDropDown(tissueSelect.toString());
    setStructureDropDown(structureSelect.toString());
    setStructureJavaScript(javascript.toString());

  }

  private void populateLesionInfo() {
    StringBuffer javascript = new StringBuffer(2048);
    StringBuffer lesionSelect = new StringBuffer(2048);

    GbossValueSet lesions = BigrGbossData.getValueSet(ArtsConstants.VALUE_SET_LESIONS);

    Iterator iter = lesions.getValues().iterator();

    javascript.append("function addLesionFromDropDown(obj){\n");
    lesionSelect.append("<select name=lesionDrop tabindex=\"16\">");
    lesionSelect.append("<option></option>");

    while (iter.hasNext()) {
      GbossValue lesion = (GbossValue) iter.next();
      String name = lesion.getDescription();
      String code = lesion.getCui();

      lesionSelect.append("<option value='" + code + "'>");
      lesionSelect.append(name);
      lesionSelect.append("</option>\n");

      javascript.append("if (obj == '" + code + "'){\n");
      javascript.append("addLesion('");
      javascript.append(name);
      javascript.append("', '");
      javascript.append(code);
      javascript.append("', false, 0);\n");
      javascript.append("return true;}\n");

    }

    lesionSelect.append("</select>");

    javascript.append("}");

    setLesionDropDown(lesionSelect.toString());
    setLesionJavaScript(javascript.toString());

  }

  /**
   * Returns <code>PathologyEvaluationData</code> databean with fields
   * initialized from <code>BTXDetailsCreatePathologyEvaluationPrepare</code>.
   * @return BTXDetailsCreatePathologyEvaluationPrepare
   */
  public void setLimsCreateEvaluationForm(BTXDetailsCreatePathologyEvaluationPrepare btxDetails) {
    PathologyEvaluationSampleData peSampleData = btxDetails.getPathologyEvaluationSampleData();
    PathologyEvaluationData peData = btxDetails.getPathologyEvaluationData();
    List incidents = btxDetails.getIncidents();

    //Set attributes to Form.  First populate the "Sample Information" fields.
    setConsentId(peSampleData.getCaseId());
    setAsmPosition(peSampleData.getAsmPosition());
    setGrossAppearance(peSampleData.getGrossAppearance());
    setInvStatus(peSampleData.getInventoryStatus());
    //if the ASM tissue of finding is other, prepend "Other Tissue:" to it
    String asmTissueName = peSampleData.getASMTissueOfFindingName();
    String asmTissueCode = peSampleData.getASMTissueOfFinding();
    if (FormLogic.OTHER_TISSUE.equals(asmTissueCode)) {
      asmTissueName = LimsConstants.LIMS_OTHER_TISSUE_PREFIX + asmTissueName;
    }
    setAsmTissue(asmTissueName);
    //if the case primary tissue of finding is other, prepend "Other Tissue:" to it
    String primaryTissueFindingName = peSampleData.getCaseTissueOfFindingName();
    String primaryTissueFindingCode = peSampleData.getCaseTissueOfFinding();
    if (FormLogic.OTHER_TISSUE.equals(primaryTissueFindingCode)) {
      primaryTissueFindingName = LimsConstants.LIMS_OTHER_TISSUE_PREFIX + primaryTissueFindingName;
    }
    setPrimaryTissueOfFinding(primaryTissueFindingName);
    //if the case primary tissue of origin is other, prepend "Other Tissue:" to it
    String primaryTissueOriginName = peSampleData.getCaseTissueOfOriginName();
    String primaryTissueOriginCode = peSampleData.getCaseTissueOfOrigin();
    if (FormLogic.OTHER_TISSUE.equals(primaryTissueOriginCode)) {
      primaryTissueOriginName = LimsConstants.LIMS_OTHER_TISSUE_PREFIX + primaryTissueOriginName;
    }
    setPrimaryTissueOfOrigin(primaryTissueOriginName);
    //if the primary diagnosis on the DI Path Report is other, prepend "Other Diagnosis:" to it
    String primaryPathReportDxName = peSampleData.getDIPathReportPrimaryDxName();
    String primaryPathReportDxCode = peSampleData.getDIPathReportPrimaryDx();
    if (FormLogic.OTHER_DX.equals(primaryPathReportDxCode)) {
      primaryPathReportDxName = LimsConstants.LIMS_OTHER_DIAGNOSIS_PREFIX + primaryPathReportDxName;
    }
    setPrimaryPathReportDx(primaryPathReportDxName);
    //for all the sections on the DI Path Report, if the diagnosis is other,
    //prepend "Other Diagnosis:" to it
    List sections = peSampleData.getPathReportSections();
    String[] sectionDiagnosisNames = new String[sections.size()];
    Iterator sectionIterator = sections.iterator();
    int index = 0;
    while (sectionIterator.hasNext()) {
      PathReportSectionData section = (PathReportSectionData) sectionIterator.next();
      String sectionDiagnosisName = section.getDiagnosisName();
      String sectionDiagnosisCode = section.getDiagnosis();
      if (FormLogic.OTHER_DX.equals(sectionDiagnosisCode)) {
        sectionDiagnosisName = LimsConstants.LIMS_OTHER_DIAGNOSIS_PREFIX + sectionDiagnosisName;
      }
      sectionDiagnosisNames[index] = sectionDiagnosisName;
      index = index + 1;
    }
    setPathReportDxList(sectionDiagnosisNames);
    setDonorId(peSampleData.getDonorId());
    setPathReportId(peSampleData.getPathReportId());
    setRawPathReport(peSampleData.getRawPathReport());
    //Now populate the evaluation fields. The PathologyEvaluationData data bean returned
    //from the server should be used to initialize all display fields.
    setSlideId(btxDetails.getSlideId());
    setSampleId(peData.getSampleId());
    setDiagnosis(peData.getDiagnosis());
    setDiagnosisOther(peData.getDiagnosisOther());
    setTissueOfFinding(peData.getTissueOfFinding());
    setTissueOfFindingOther(peData.getTissueOfFindingOther());
    setTissueOfOrigin(peData.getTissueOfOrigin());
    setTissueOfOriginOther(peData.getTissueOfOriginOther());
    setCreateMethod(peData.getCreateMethod());
    //edits of existing evaluations should have the "Reviewed Path report" radio
    //button preselected as yes
    if (LimsConstants.LIMS_PE_CREATE_TYPE_EDIT.equals(peData.getCreateMethod())) {
      setReviewedRawPathReport(true);
    }
    setSourceEvaluationId(btxDetails.getSourceEvaluationId());
    //For the "cell" fields, default to 0 if there is no valid value provided
    //by the data bean.
    int cellValue = peData.getLesionCells();
    if (cellValue >= 0) {
      setLesionValue(String.valueOf(cellValue));
    }
    else {
      setLesionValue("0");
    }
    cellValue = peData.getNormalCells();
    if (cellValue >= 0) {
      setNormalValue(String.valueOf(cellValue));
    }
    else {
      setNormalValue("0");
    }
    cellValue = peData.getTumorCells();
    if (cellValue >= 0) {
      setTumorValue(String.valueOf(cellValue));
    }
    else {
      setTumorValue("0");
    }
    cellValue = peData.getCellularStromaCells();
    if (cellValue >= 0) {
      setHcstromaValue(String.valueOf(cellValue));
    }
    else {
      setHcstromaValue("0");
    }
    cellValue = peData.getHypoacellularStromaCells();
    if (cellValue >= 0) {
      setHastromaValue(String.valueOf(cellValue));
    }
    else {
      setHastromaValue("0");
    }
    cellValue = peData.getNecrosisCells();
    if (cellValue >= 0) {
      setNecrosisValue(String.valueOf(cellValue));
    }
    else {
      setNecrosisValue("0");
    }
    //now do all the feature lists
    { //LESIONS
      List list = peData.getLesions();
      if ((list != null) && (list.size() > 0)) {
        ArrayList code = new ArrayList();
        ArrayList text = new ArrayList();
        ArrayList value = new ArrayList();
        ArrayList isOther = new ArrayList();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
          PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData) iterator.next();
          code.add(feature.getCode());
          text.add(feature.getFeatureName());
          value.add(String.valueOf(feature.getValue()));
          if (FormLogic.OTHER_DX.equals(feature.getCode())) {
            isOther.add("true");
          }
          else {
            isOther.add("false");
          }
        }
        setLesionCodeList((String[]) code.toArray(new String[0]));
        setLesionTextList((String[]) text.toArray(new String[0]));
        setLesionValueList((String[]) value.toArray(new String[0]));
        setLesionIsOtherList((String[]) isOther.toArray(new String[0]));
      }
    }
    { //INFLAMMATIONS
      List list = peData.getInflammations();
      if ((list != null) && (list.size() > 0)) {
        ArrayList code = new ArrayList();
        ArrayList text = new ArrayList();
        ArrayList value = new ArrayList();
        ArrayList isOther = new ArrayList();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
          PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData) iterator.next();
          code.add(feature.getCode());
          text.add(feature.getFeatureName());
          value.add(String.valueOf(feature.getValue()));
          if (LimsConstants.OTHER_INFLAMMATION.equals(feature.getCode())) {
            isOther.add("true");
          }
          else {
            isOther.add("false");
          }
        }
        setInflammationCodeList((String[]) code.toArray(new String[0]));
        setInflammationTextList((String[]) text.toArray(new String[0]));
        setInflammationValueList((String[]) value.toArray(new String[0]));
        setInflammationIsOtherList((String[]) isOther.toArray(new String[0]));
      }
    }
    { //STRUCTURES
      List list = peData.getStructures();
      if ((list != null) && (list.size() > 0)) {
        ArrayList code = new ArrayList();
        ArrayList text = new ArrayList();
        ArrayList value = new ArrayList();
        ArrayList isOther = new ArrayList();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
          PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData) iterator.next();
          code.add(feature.getCode());
          text.add(feature.getFeatureName());
          value.add(String.valueOf(feature.getValue()));
          if (LimsConstants.OTHER_STRUCTURE.equals(feature.getCode())) {
            isOther.add("true");
          }
          else {
            isOther.add("false");
          }
        }
        setStructureCodeList((String[]) code.toArray(new String[0]));
        setStructureTextList((String[]) text.toArray(new String[0]));
        setStructureValueList((String[]) value.toArray(new String[0]));
        setStructureIsOtherList((String[]) isOther.toArray(new String[0]));
      }
    }
    { //TUMORS
      List list = peData.getTumorFeatures();
      if ((list != null) && (list.size() > 0)) {
        ArrayList code = new ArrayList();
        ArrayList text = new ArrayList();
        //tumors have no value data
        ArrayList isOther = new ArrayList();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
          PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData) iterator.next();
          code.add(feature.getCode());
          text.add(feature.getFeatureName());
          if (LimsConstants.OTHER_TUMOR.equals(feature.getCode())) {
            isOther.add("true");
          }
          else {
            isOther.add("false");
          }
        }
        setTumorFeatureCodeList((String[]) code.toArray(new String[0]));
        setTumorFeatureTextList((String[]) text.toArray(new String[0]));
        setTumorFeatureIsOtherList((String[]) isOther.toArray(new String[0]));
      }
    }
    { //CELLULAR STROMAS
      List list = peData.getCellularStromaFeatures();
      if ((list != null) && (list.size() > 0)) {
        ArrayList code = new ArrayList();
        ArrayList text = new ArrayList();
        //cellular stromas have no value data
        ArrayList isOther = new ArrayList();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
          PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData) iterator.next();
          code.add(feature.getCode());
          text.add(feature.getFeatureName());
          if (LimsConstants.OTHER_CELLULAR.equals(feature.getCode())) {
            isOther.add("true");
          }
          else {
            isOther.add("false");
          }
        }
        setCellularFeatureCodeList((String[]) code.toArray(new String[0]));
        setCellularFeatureTextList((String[]) text.toArray(new String[0]));
        setCellularFeatureIsOtherList((String[]) isOther.toArray(new String[0]));
      }
    }
    { //HYPOACELLULAR STROMAS
      List list = peData.getHypoacellularStromaFeatures();
      if ((list != null) && (list.size() > 0)) {
        ArrayList code = new ArrayList();
        ArrayList text = new ArrayList();
        //hypoacellular stromas have no value data
        ArrayList isOther = new ArrayList();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
          PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData) iterator.next();
          code.add(feature.getCode());
          text.add(feature.getFeatureName());
          if (LimsConstants.OTHER_HYPOACELLULAR.equals(feature.getCode())) {
            isOther.add("true");
          }
          else {
            isOther.add("false");
          }
        }
        setHypoCellularFeatureCodeList((String[]) code.toArray(new String[0]));
        setHypoCellularFeatureTextList((String[]) text.toArray(new String[0]));
        setHypoCellularFeatureIsOtherList((String[]) isOther.toArray(new String[0]));
      }
    }
    { //EXTERNAL FEATURES.  To populate these lists, we pass the non-Other features
      //via their ARTS codes, and Other features via the text entered by the user
      List list = peData.getExternalFeatures();
      if ((list != null) && (list.size() > 0)) {
        ArrayList code = new ArrayList();
        ArrayList text = new ArrayList();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
          PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData) iterator.next();
          if (LimsConstants.OTHER_EXTERNAL.equals(feature.getCode())) {
            text.add(feature.getFeatureName());
          }
          else {
            code.add(feature.getCode());
          }
        }
        setExternalFeaturesList((String[]) code.toArray(new String[0]));
        setExternalOtherList((String[]) text.toArray(new String[0]));
      }
    }
    { //INTERNAL FEATURES.  To populate these lists, we pass the non-Other features
      //via their ARTS codes, and Other features via the text entered by the user
      List list = peData.getInternalFeatures();
      if ((list != null) && (list.size() > 0)) {
        ArrayList code = new ArrayList();
        ArrayList text = new ArrayList();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
          PathologyEvaluationFeatureData feature = (PathologyEvaluationFeatureData) iterator.next();
          if (LimsConstants.OTHER_INTERNAL.equals(feature.getCode())) {
            text.add(feature.getFeatureName());
          }
          else {
            code.add(feature.getCode());
          }
        }
        setInternalFeaturesList((String[]) code.toArray(new String[0]));
        setInternalOtherList((String[]) text.toArray(new String[0]));
      }
    }
    //for any incidents, compose the string to represent them.
    String[] incidentInfo = null;
    if (incidents != null && incidents.size() > 0) {
      incidentInfo = new String[incidents.size()];
      Iterator iterator = incidents.iterator();
      int count = 0;
      while (iterator.hasNext()) {
        IncidentReportLineItem incident = (IncidentReportLineItem) iterator.next();
        StringBuffer info = new StringBuffer(100);
        info.append(Escaper.htmlEscape(incident.getReason()));
        info.append("&nbsp;&nbsp");
        info.append("(");
        info.append(incident.getCreateUser());
        info.append("&nbsp-&nbsp");
        info.append(DateFormat.getDateTimeInstance().format(incident.getCreateDate()));
        info.append(")");
        incidentInfo[count] = info.toString();
        count = count + 1;
      }
    }
    setIncidentList(incidentInfo);
  }
  /**
   * Returns <code>PathologyEvaluationData</code> databean.
   */
  public PathologyEvaluationData getPathologyEvaluationData() {
    PathologyEvaluationData databean = new PathologyEvaluationData();
    databean.setSlideId(getSlideId());
    databean.setSampleId(getSampleId());
    databean.setDiagnosis(getDiagnosis());
    databean.setDiagnosisOther(getDiagnosisOther());
    databean.setTissueOfOrigin(getTissueOfOrigin());
    databean.setTissueOfOriginOther(getTissueOfOriginOther());
    databean.setTissueOfFinding(getTissueOfFinding());
    databean.setTissueOfFindingOther(getTissueOfFindingOther());
    databean.setLesions(
      getPeFeaturesList(
        getLesionCodeList(),
        getLesionValueList(),
        getLesionTextList(),
        LimsConstants.FEATURE_TYPE_LESION));
    databean.setInflammations(
      getPeFeaturesList(
        getInflammationCodeList(),
        getInflammationValueList(),
        getInflammationTextList(),
        LimsConstants.FEATURE_TYPE_INFLAMMATION));
    databean.setStructures(
      getPeFeaturesList(
        getStructureCodeList(),
        getStructureValueList(),
        getStructureTextList(),
        LimsConstants.FEATURE_TYPE_STRUCTURE));
    databean.setTumorFeatures(
      getPeFeaturesList(
        getTumorFeatureCodeList(),
        null,
        getTumorFeatureTextList(),
        LimsConstants.FEATURE_TYPE_TUMOR));
    databean.setCellularStromaFeatures(
      getPeFeaturesList(
        getCellularFeatureCodeList(),
        null,
        getCellularFeatureTextList(),
        LimsConstants.FEATURE_TYPE_CELLULAR));
    databean.setHypoacellularStromaFeatures(
      getPeFeaturesList(
        getHypoCellularFeatureCodeList(),
        null,
        getHypoCellularFeatureTextList(),
        LimsConstants.FEATURE_TYPE_HYPOACELLULAR));
    databean.setLesionCells(ApiFunctions.safeInteger(getLesionValue(), 0));
    databean.setTumorCells(ApiFunctions.safeInteger(getTumorValue(), 0));
    databean.setNecrosisCells(ApiFunctions.safeInteger(getNecrosisValue(), 0));
    databean.setNormalCells(ApiFunctions.safeInteger(getNormalValue(), 0));
    databean.setHypoacellularStromaCells(ApiFunctions.safeInteger(getHastromaValue(), 0));
    databean.setCellularStromaCells(ApiFunctions.safeInteger(getHcstromaValue(), 0));

    List extFeatures =
      getPeFeaturesList(getExternalFeaturesList(), null, null, LimsConstants.FEATURE_TYPE_EXTERNAL);
    if (ApiFunctions.isEmpty(extFeatures))
      extFeatures = new ArrayList();
    if (!ApiFunctions.isEmpty(getExternalOtherList())) {
      for (int i = 0; i < getExternalOtherList().length; i++) {
        //Create new databean
        PathologyEvaluationFeatureData feature = new PathologyEvaluationFeatureData();
        feature.setCode(LimsConstants.OTHER_EXTERNAL);
        feature.setOtherText(getExternalOtherList()[i]);
        feature.setCompositionType(LimsConstants.FEATURE_TYPE_EXTERNAL);
        extFeatures.add(feature);
      }
    }
    databean.setExternalFeatures(extFeatures);

    List intFeatures =
      getPeFeaturesList(getInternalFeaturesList(), null, null, LimsConstants.FEATURE_TYPE_INTERNAL);
    if (ApiFunctions.isEmpty(intFeatures))
      intFeatures = new ArrayList();
    if (!ApiFunctions.isEmpty(getInternalOtherList())) {
      for (int i = 0; i < getInternalOtherList().length; i++) {
        //Create new databean
        PathologyEvaluationFeatureData feature = new PathologyEvaluationFeatureData();
        feature.setCode(LimsConstants.OTHER_INTERNAL);
        feature.setOtherText(getInternalOtherList()[i]);
        feature.setCompositionType(LimsConstants.FEATURE_TYPE_INTERNAL);
        intFeatures.add(feature);
      }
    }
    databean.setInternalFeatures(intFeatures);
    databean.setCreateMethod(getCreateMethod());
    databean.setSourceEvaluationId(getSourceEvaluationId());
    return databean;

  }
  /**
   * Creates a new <code>List</code> of <code>PathologyEvaluationFeatureData</code>
   * databeans. 
   * @param <code>String[]</code> of codes.
   * @param <code>String[]</code> of values.
   * @param <code>String[]</code> of text.
   * @return List
   */
  private List getPeFeaturesList(String[] code, String[] value, String[] text, String type) {
    List list = new ArrayList();
    String otherCode = null;
    if (ApiFunctions.safeEquals(type, LimsConstants.FEATURE_TYPE_LESION))
      otherCode = FormLogic.OTHER_DX;
    else if (ApiFunctions.safeEquals(type, LimsConstants.FEATURE_TYPE_INFLAMMATION))
      otherCode = LimsConstants.OTHER_INFLAMMATION;
    else if (ApiFunctions.safeEquals(type, LimsConstants.FEATURE_TYPE_STRUCTURE))
      otherCode = LimsConstants.OTHER_STRUCTURE;
    else if (ApiFunctions.safeEquals(type, LimsConstants.FEATURE_TYPE_TUMOR))
      otherCode = LimsConstants.OTHER_TUMOR;
    else if (ApiFunctions.safeEquals(type, LimsConstants.FEATURE_TYPE_CELLULAR))
      otherCode = LimsConstants.OTHER_CELLULAR;
    else if (ApiFunctions.safeEquals(type, LimsConstants.FEATURE_TYPE_HYPOACELLULAR))
      otherCode = LimsConstants.OTHER_HYPOACELLULAR;
    else if (ApiFunctions.safeEquals(type, LimsConstants.FEATURE_TYPE_EXTERNAL))
      otherCode = LimsConstants.OTHER_EXTERNAL;
    else if (ApiFunctions.safeEquals(type, LimsConstants.FEATURE_TYPE_INTERNAL))
      otherCode = LimsConstants.OTHER_INTERNAL;

    if (code != null) {
      for (int i = 0; i < code.length; i++) {
        //Create new databean
        PathologyEvaluationFeatureData feature = new PathologyEvaluationFeatureData();
        feature.setCode(code[i]);
        if (value != null)
          feature.setValue(ApiFunctions.safeInteger(value[i], 0));
        //Add other Text if code is "Other Code" only
        if ((text != null) && (ApiFunctions.safeEquals(code[i], otherCode)))
          feature.setOtherText(text[i]);
        feature.setCompositionType(type);
        //Add to list
        list.add(feature);
      }
    }
    return list;
  }

  /**
    * Returns sum of feature's values.
    * databeans. 
    * @param <code>String[]</code> of values.
    * @return int
    */
  private int getFeatureSum(String[] list) {
    int sum = 0;
    if (!ApiFunctions.isEmpty(list)) {
      for (int i = 0; i < list.length; i++) {
        if (list[i] != null)
          sum = sum + Integer.parseInt(list[i]);
      }
    }
    return sum;
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  public ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {

    ActionErrors errors = new ActionErrors();
    if (!ApiFunctions.isEmpty(getViewPvReport())) {
      return errors;
    }

    if ("/lims/limsCreateEvaluation".equals(mapping.getPath())
      && ApiFunctions.isEmpty(getReason())) {
      boolean calculateTotal = true;
      int total = 0;
      boolean calculateStructureTotal = true;
      boolean calculateLesionTotal = true;
      int structureTotal = 0;
      int lesionTotal = 0;

      if (ApiFunctions.isEmpty(getDiagnosis())) {
        errors.add("diagnosis", new ActionError("lims.error.enterSamplePathology", getDiagnosis()));
      }
      else if (FormLogic.OTHER_DX.equals(getDiagnosis())
          && (ApiFunctions.isEmpty(getDiagnosisOther()))) {
        errors.add(
          "diagnosis",
          new ActionError("lims.error.enterSamplePathologyOther", getDiagnosis()));
      }

      if (ApiFunctions.isEmpty(getTissueOfFinding())) {
        errors.add(
          "finding",
          new ActionError("lims.error.enterTissueOfFinding", getTissueOfFinding()));
      }
      else if (FormLogic.OTHER_TISSUE.equals(getTissueOfFinding())
          && (ApiFunctions.isEmpty(getTissueOfFindingOther()))) {
        errors.add(
          "finding",
          new ActionError("lims.error.enterTissueOfFindingOther", getTissueOfFindingOther()));
      }

      if (ApiFunctions.isEmpty(getTissueOfOrigin())) {
        errors.add(
          "origin",
          new ActionError("lims.error.enterTissueOfOrigin", getTissueOfOrigin()));
      }
      else if (FormLogic.OTHER_TISSUE.equals(getTissueOfOrigin())
          && (ApiFunctions.isEmpty(getTissueOfOriginOther()))) {
        errors.add(
          "origin",
          new ActionError("lims.error.enterTissueOfOriginOther", getTissueOfOriginOther()));
      }

      if (!ApiFunctions.isInteger(getNormalValue())) {
        errors.add("normal", new ActionError("lims.error.invalidNumberValue", "Normal"));
        calculateTotal = false;
      }
      else {
        total += getNumber(getNormalValue());
      }

      if (!ApiFunctions.isInteger(getLesionValue())) {
        errors.add("lesion", new ActionError("lims.error.invalidNumberValue", "Lesion"));
        calculateTotal = false;
      }
      else {
        total += getNumber(getLesionValue());
      }

      if (getLesionValueList() != null) {
        for (int i = 0; i < getLesionValueList().length; i++) {
          if (!ApiFunctions.isInteger(getLesionValueList()[i])) {
            errors.add(
              "lesion" + i,
              new ActionError("lims.error.invalidNumberValue", getLesionTextList()[i]));
            calculateLesionTotal = false;
          }
          else {
            lesionTotal += getNumber(getLesionValueList()[i]);
          }
        }
      }

      if (getStructureValueList() != null) {
        for (int i = 0; i < getStructureValueList().length; i++) {
          if (!ApiFunctions.isInteger(getStructureValueList()[i])) {
            errors.add(
              "structure" + i,
              new ActionError("lims.error.invalidNumberValue", getStructureTextList()[i]));
            calculateStructureTotal = false;
          }
          else {
            structureTotal += getNumber(getStructureValueList()[i]);
          }
        }
      }

      if (!ApiFunctions.isInteger(getTumorValue())) {
        errors.add("tumor", new ActionError("lims.error.invalidNumberValue", "Tumor"));
        calculateTotal = false;
      }
      else {
        total += getNumber(getTumorValue());
      }

      if (!ApiFunctions.isInteger(getHcstromaValue())) {
        errors.add("hcstroma", new ActionError("lims.error.invalidNumberValue", "Cellular Stroma"));
        calculateTotal = false;
      }
      else {
        total += getNumber(getHcstromaValue());
      }

      if (!ApiFunctions.isInteger(getHastromaValue())) {
        errors.add(
          "hastroma",
          new ActionError("lims.error.invalidNumberValue", "Hypo Acellular Stroma"));
        calculateTotal = false;
      }
      else {
        total += getNumber(getHastromaValue());
      }

      if (!ApiFunctions.isInteger(getNecrosisValue())) {
        errors.add("necrosis", new ActionError("lims.error.invalidNumberValue", "Necrosis"));
        calculateTotal = false;
      }
      else {
        total += getNumber(getNecrosisValue());
      }

      if (calculateTotal && total != 100) {
        errors.add("total", new ActionError("lims.error.invalidTotal"));
      }

      if (calculateStructureTotal
        && getStructureValueList() != null
        && getStructureValueList().length > 1
        && structureTotal != 100) {
        errors.add("structureTotal", new ActionError("lims.error.invalidStructureTotal"));
      }

      if (calculateLesionTotal
        && getLesionValueList() != null
        && getLesionValueList().length > 1
        && lesionTotal != 100) {
        errors.add("lesionTotal", new ActionError("lims.error.invalidLesionTotal"));
      }

    }

    return errors;
  }
  /**
   * Returns int value of String <code>value</code>.
   * @return int
   */
  private int getNumber(String value) {
    try {
      int temp = Integer.parseInt(value);
      return temp;
    }
    catch (NumberFormatException e) {
      return -1;
    }
  }

  /**
   * Returns the samplePathology.
   * @return String
   */
  public String getDiagnosis() {
    return _diagnosis;
  }

  /**
   * Returns the tissueOfFinding.
   * @return String
   */
  public String getTissueOfFinding() {
    return _tissueOfFinding;
  }

  /**
   * Returns the tissueOfOrigin.
   * @return String
   */
  public String getTissueOfOrigin() {
    return _tissueOfOrigin;
  }

  /**
   * Sets the samplePathology.
   * @param samplePathology The samplePathology to set
   */
  public void setDiagnosis(String diagnosis) {
    _diagnosis = diagnosis;
  }

  /**
   * Sets the tissueOfFinding.
   * @param tissueOfFinding The tissueOfFinding to set
   */
  public void setTissueOfFinding(String tissueOfFinding) {
    _tissueOfFinding = tissueOfFinding;
  }

  /**
   * Sets the tissueOfOrigin.
   * @param tissueOfOrigin The tissueOfOrigin to set
   */
  public void setTissueOfOrigin(String tissueOfOrigin) {
    _tissueOfOrigin = tissueOfOrigin;
  }

  /**
   * Returns the lesionList.
   * @return String[]
   */
  public String[] getLesionCodeList() {
    return _lesionCodeList;
  }

  /**
   * Returns the lesionValueList.
   * @return String[]
   */
  public String[] getLesionValueList() {
    return _lesionValueList;
  }

  /**
   * Returns the structureList.
   * @return String[]
   */
  public String[] getStructureCodeList() {
    return _structureCodeList;
  }

  /**
   * Returns the structureValueList.
   * @return String[]
   */
  public String[] getStructureValueList() {
    return _structureValueList;
  }

  /**
   * Sets the lesionList.
   * @param lesionList The lesionList to set
   */
  public void setLesionCodeList(String[] lesionCodeList) {
    _lesionCodeList = lesionCodeList;
  }

  /**
   * Sets the lesionValueList.
   * @param lesionValueList The lesionValueList to set
   */
  public void setLesionValueList(String[] lesionValueList) {
    _lesionValueList = lesionValueList;
  }

  /**
   * Sets the structureList.
   * @param structureList The structureList to set
   */
  public void setStructureCodeList(String[] structureCodeList) {
    _structureCodeList = structureCodeList;
  }

  /**
   * Sets the structureValueList.
   * @param structureValueList The structureValueList to set
   */
  public void setStructureValueList(String[] structureValueList) {
    _structureValueList = structureValueList;
  }

  /**
   * Returns the inflammationCodeList.
   * @return String[]
   */
  public String[] getInflammationCodeList() {
    return _inflammationCodeList;
  }

  /**
   * Returns the inflammationValueList.
   * @return String[]
   */
  public String[] getInflammationValueList() {
    return _inflammationValueList;
  }

  /**
   * Sets the inflammationCodeList.
   * @param inflammationList The inflammationList to set
   */
  public void setInflammationList(String[] inflammationCodeList) {
    _inflammationCodeList = inflammationCodeList;
  }

  /**
   * Sets the inflammationValueList.
   * @param inflammationValueList The inflammationValueList to set
   */
  public void setInflammationValueList(String[] inflammationValueList) {
    _inflammationValueList = inflammationValueList;
  }

  /**
   * Returns the externalFeaturesList.
   * @return String[]
   */
  public String[] getExternalFeaturesList() {
    return _externalFeaturesList;
  }

  /**
   * Returns the internalFeaturesList.
   * @return String[]
   */
  public String[] getInternalFeaturesList() {
    return _internalFeaturesList;
  }

  /**
   * Sets the externalFeaturesList.
   * @param externalFeaturesList The externalFeaturesList to set
   */
  public void setExternalFeaturesList(String[] externalFeaturesList) {
    _externalFeaturesList = externalFeaturesList;
  }

  /**
   * Sets the internalFeaturesList.
   * @param internalFeaturesList The internalFeaturesList to set
   */
  public void setInternalFeaturesList(String[] internalFeaturesList) {
    _internalFeaturesList = internalFeaturesList;
  }

  /**
   * Returns the lesionTextList.
   * @return String[]
   */
  public String[] getLesionTextList() {
    return _lesionTextList;
  }

  /**
   * Returns the structureTextList.
   * @return String[]
   */
  public String[] getStructureTextList() {
    return _structureTextList;
  }

  /**
   * Sets the lesionTextList.
   * @param lesionTextList The lesionTextList to set
   */
  public void setLesionTextList(String[] lesionTextList) {
    _lesionTextList = lesionTextList;
  }

  /**
   * Sets the structureTextList.
   * @param structureTextList The structureTextList to set
   */
  public void setStructureTextList(String[] structureTextList) {
    _structureTextList = structureTextList;
  }

  /**
   * Returns the cellularFeatureCodeList.
   * @return String[]
   */
  public String[] getCellularFeatureCodeList() {
    return _cellularFeatureCodeList;
  }

  /**
   * Returns the cellularFeatureTextList.
   * @return String[]
   */
  public String[] getCellularFeatureTextList() {
    return _cellularFeatureTextList;
  }

  /**
   * Returns the hypoCellularFeatureCodeList.
   * @return String[]
   */
  public String[] getHypoCellularFeatureCodeList() {
    return _hypoCellularFeatureCodeList;
  }

  /**
   * Returns the hypoCellularFeatureTextList.
   * @return String[]
   */
  public String[] getHypoCellularFeatureTextList() {
    return _hypoCellularFeatureTextList;
  }

  /**
   * Returns the inflammationTextList.
   * @return String[]
   */
  public String[] getInflammationTextList() {
    return _inflammationTextList;
  }

  /**
   * Returns the tumorFeatureCodeList.
   * @return String[]
   */
  public String[] getTumorFeatureCodeList() {
    return _tumorFeatureCodeList;
  }

  /**
   * Returns the tumorFeatureTextList.
   * @return String[]
   */
  public String[] getTumorFeatureTextList() {
    return _tumorFeatureTextList;
  }

  /**
   * Sets the cellularFeatureCodeList.
   * @param cellularFeatureCodeList The cellularFeatureCodeList to set
   */
  public void setCellularFeatureCodeList(String[] cellularFeatureCodeList) {
    _cellularFeatureCodeList = cellularFeatureCodeList;
  }

  /**
   * Sets the cellularFeatureTextList.
   * @param cellularFeatureTextList The cellularFeatureTextList to set
   */
  public void setCellularFeatureTextList(String[] cellularFeatureTextList) {
    _cellularFeatureTextList = cellularFeatureTextList;
  }

  /**
   * Sets the hypoCellularFeatureCodeList.
   * @param hypoCellularFeatureCodeList The hypoCellularFeatureCodeList to set
   */
  public void setHypoCellularFeatureCodeList(String[] hypoCellularFeatureCodeList) {
    _hypoCellularFeatureCodeList = hypoCellularFeatureCodeList;
  }

  /**
   * Sets the hypoCellularFeatureTextList.
   * @param hypoCellularFeatureTextList The hypoCellularFeatureTextList to set
   */
  public void setHypoCellularFeatureTextList(String[] hypoCellularFeatureTextList) {
    _hypoCellularFeatureTextList = hypoCellularFeatureTextList;
  }

  /**
   * Sets the inflammationCodeList.
   * @param inflammationCodeList The inflammationCodeList to set
   */
  public void setInflammationCodeList(String[] inflammationCodeList) {
    _inflammationCodeList = inflammationCodeList;
  }

  /**
   * Sets the inflammationTextList.
   * @param inflammationTextList The inflammationTextList to set
   */
  public void setInflammationTextList(String[] inflammationTextList) {
    _inflammationTextList = inflammationTextList;
  }

  /**
   * Sets the tumorFeatureCodeList.
   * @param tumorFeatureCodeList The tumorFeatureCodeList to set
   */
  public void setTumorFeatureCodeList(String[] tumorFeatureCodeList) {
    _tumorFeatureCodeList = tumorFeatureCodeList;
  }

  /**
   * Sets the tumorFeatureTextList.
   * @param tumorFeatureTextList The tumorFeatureTextList to set
   */
  public void setTumorFeatureTextList(String[] tumorFeatureTextList) {
    _tumorFeatureTextList = tumorFeatureTextList;
  }

  /**
   * Returns the diagnosisOther.
   * @return String
   */
  public String getDiagnosisOther() {
    return _diagnosisOther;
  }

  /**
   * Returns the tissueOfFindingOther.
   * @return String
   */
  public String getTissueOfFindingOther() {
    return _tissueOfFindingOther;
  }

  /**
   * Returns the tissueOfOriginOther.
   * @return String
   */
  public String getTissueOfOriginOther() {
    return _tissueOfOriginOther;
  }

  /**
   * Sets the diagnosisOther.
   * @param diagnosisOther The diagnosisOther to set
   */
  public void setDiagnosisOther(String diagnosisOther) {
    _diagnosisOther = diagnosisOther;
  }

  /**
   * Sets the tissueOfFindingOther.
   * @param tissueOfFindingOther The tissueOfFindingOther to set
   */
  public void setTissueOfFindingOther(String tissueOfFindingOther) {
    _tissueOfFindingOther = tissueOfFindingOther;
  }

  /**
   * Sets the tissueOfOriginOther.
   * @param tissueOfOriginOther The tissueOfOriginOther to set
   */
  public void setTissueOfOriginOther(String tissueOfOriginOther) {
    _tissueOfOriginOther = tissueOfOriginOther;
  }

  /**
   * Returns the asmPosition.
   * @return String
   */
  public String getAsmPosition() {
    return _asmPosition;
  }

  /**
   * Returns the asmTissue.
   * @return String
   */
  public String getAsmTissue() {
    return _asmTissue;
  }

  /**
   * Returns the consentId.
   * @return String
   */
  public String getConsentId() {
    return _consentId;
  }

  /**
   * Returns the grossAppearance.
   * @return String
   */
  public String getGrossAppearance() {
    return _grossAppearance;
  }

  /**
   * Returns the pathReportDxList.
   * @return String[]
   */
  public String[] getPathReportDxList() {
    return _pathReportDxList;
  }

  /**
   * Returns the primaryPathReportDx.
   * @return String
   */
  public String getPrimaryPathReportDx() {
    return _primaryPathReportDx;
  }

  /**
   * Returns the primaryTissueOfFinding.
   * @return String
   */
  public String getPrimaryTissueOfFinding() {
    return _primaryTissueOfFinding;
  }

  /**
   * Returns the primaryTissueOfOrigin.
   * @return String
   */
  public String getPrimaryTissueOfOrigin() {
    return _primaryTissueOfOrigin;
  }

  /**
   * Sets the asmPosition.
   * @param asmPosition The asmPosition to set
   */
  public void setAsmPosition(String asmPosition) {
    _asmPosition = asmPosition;
  }

  /**
   * Sets the asmTissue.
   * @param asmTissue The asmTissue to set
   */
  public void setAsmTissue(String asmTissue) {
    _asmTissue = asmTissue;
  }

  /**
   * Sets the consentId.
   * @param consentId The consentId to set
   */
  public void setConsentId(String consentId) {
    _consentId = consentId;
  }

  /**
   * Sets the grossAppearance.
   * @param grossAppearance The grossAppearance to set
   */
  public void setGrossAppearance(String grossAppearance) {
    _grossAppearance = grossAppearance;
  }

  /**
   * Sets the pathReportDxList.
   * @param pathReportDxList The pathReportDxList to set
   */
  public void setPathReportDxList(String[] pathReportDxList) {
    _pathReportDxList = pathReportDxList;
  }

  /**
   * Sets the primaryPathReportDx.
   * @param primaryPathReportDx The primaryPathReportDx to set
   */
  public void setPrimaryPathReportDx(String primaryPathReportDx) {
    _primaryPathReportDx = primaryPathReportDx;
  }

  /**
   * Sets the primaryTissueOfFinding.
   * @param primaryTissueOfFinding The primaryTissueOfFinding to set
   */
  public void setPrimaryTissueOfFinding(String primaryTissueOfFinding) {
    _primaryTissueOfFinding = primaryTissueOfFinding;
  }

  /**
   * Sets the primaryTissueOfOrigin.
   * @param primaryTissueOfOrigin The primaryTissueOfOrigin to set
   */
  public void setPrimaryTissueOfOrigin(String primaryTissueOfOrigin) {
    _primaryTissueOfOrigin = primaryTissueOfOrigin;
  }

  /**
   * Returns the sampleId.
   * @return String
   */
  public String getSampleId() {
    return _sampleId;
  }

  /**
   * Returns the slideId.
   * @return String
   */
  public String getSlideId() {
    return _slideId;
  }

  /**
   * Sets the sampleId.
   * @param sampleId The sampleId to set
   */
  public void setSampleId(String sampleId) {
    _sampleId = sampleId;
  }

  /**
   * Sets the slideId.
   * @param slideId The slideId to set
   */
  public void setSlideId(String slideId) {
    _slideId = slideId;
  }

  /**
   * Returns the inflammationIsOtherList.
   * @return String[]
   */
  public String[] getInflammationIsOtherList() {
    return _inflammationIsOtherList;
  }

  /**
   * Returns the lesionIsOtherList.
   * @return String[]
   */
  public String[] getLesionIsOtherList() {
    return _lesionIsOtherList;
  }

  /**
   * Returns the structureIsOtherList.
   * @return String[]
   */
  public String[] getStructureIsOtherList() {
    return _structureIsOtherList;
  }

  /**
   * Sets the inflammationIsOtherList.
   * @param inflammationIsOtherList The inflammationIsOtherList to set
   */
  public void setInflammationIsOtherList(String[] inflammationIsOtherList) {
    _inflammationIsOtherList = inflammationIsOtherList;
  }

  /**
   * Sets the lesionIsOtherList.
   * @param lesionIsOtherList The lesionIsOtherList to set
   */
  public void setLesionIsOtherList(String[] lesionIsOtherList) {
    _lesionIsOtherList = lesionIsOtherList;
  }

  /**
   * Sets the structureIsOtherList.
   * @param structureIsOtherList The structureIsOtherList to set
   */
  public void setStructureIsOtherList(String[] structureIsOtherList) {
    _structureIsOtherList = structureIsOtherList;
  }

  /**
   * Returns the cellularFeatureIsOtherList.
   * @return String[]
   */
  public String[] getCellularFeatureIsOtherList() {
    return _cellularFeatureIsOtherList;
  }

  /**
   * Returns the hypoCellularFeatureIsOtherList.
   * @return String[]
   */
  public String[] getHypoCellularFeatureIsOtherList() {
    return _hypoCellularFeatureIsOtherList;
  }

  /**
   * Returns the tumorFeatureIsOtherList.
   * @return String[]
   */
  public String[] getTumorFeatureIsOtherList() {
    return _tumorFeatureIsOtherList;
  }

  /**
   * Sets the cellularFeatureIsOtherList.
   * @param cellularFeatureIsOtherList The cellularFeatureIsOtherList to set
   */
  public void setCellularFeatureIsOtherList(String[] cellularFeatureIsOtherList) {
    _cellularFeatureIsOtherList = cellularFeatureIsOtherList;
  }

  /**
   * Sets the hypoCellularFeatureIsOtherList.
   * @param hypoCellularFeatureIsOtherList The hypoCellularFeatureIsOtherList to set
   */
  public void setHypoCellularFeatureIsOtherList(String[] hypoCellularFeatureIsOtherList) {
    _hypoCellularFeatureIsOtherList = hypoCellularFeatureIsOtherList;
  }

  /**
   * Sets the tumorFeatureIsOtherList.
   * @param tumorFeatureIsOtherList The tumorFeatureIsOtherList to set
   */
  public void setTumorFeatureIsOtherList(String[] tumorFeatureIsOtherList) {
    _tumorFeatureIsOtherList = tumorFeatureIsOtherList;
  }

  /**
   * Returns the hastromaValue.
   * @return String
   */
  public String getHastromaValue() {
    return _hastromaValue;
  }

  /**
   * Returns the hcstromaValue.
   * @return String
   */
  public String getHcstromaValue() {
    return _hcstromaValue;
  }

  /**
   * Returns the necrosisValue.
   * @return String
   */
  public String getNecrosisValue() {
    return _necrosisValue;
  }

  /**
   * Returns the normalValue.
   * @return String
   */
  public String getNormalValue() {
    return _normalValue;
  }

  /**
   * Returns the tumorValue.
   * @return String
   */
  public String getTumorValue() {
    return _tumorValue;
  }

  /**
   * Sets the hastromaValue.
   * @param hastromaValue The hastromaValue to set
   */
  public void setHastromaValue(String hastromaValue) {
    _hastromaValue = hastromaValue;
  }

  /**
   * Sets the hcstromaValue.
   * @param hcstromaValue The hcstromaValue to set
   */
  public void setHcstromaValue(String hcstromaValue) {
    _hcstromaValue = hcstromaValue;
  }

  /**
   * Sets the necrosisValue.
   * @param necrosisValue The necrosisValue to set
   */
  public void setNecrosisValue(String necrosisValue) {
    _necrosisValue = necrosisValue;
  }

  /**
   * Sets the normalValue.
   * @param normalValue The normalValue to set
   */
  public void setNormalValue(String normalValue) {
    _normalValue = normalValue;
  }

  /**
   * Sets the tumorValue.
   * @param tumorValue The tumorValue to set
   */
  public void setTumorValue(String tumorValue) {
    _tumorValue = tumorValue;
  }

  /**
   * Returns the externalOtherList.
   * @return String[]
   */
  public String[] getExternalOtherList() {
    return _externalOtherList;
  }

  /**
   * Returns the internalOtherList.
   * @return String[]
   */
  public String[] getInternalOtherList() {
    return _internalOtherList;
  }

  /**
   * Sets the externalOtherList.
   * @param externalOtherList The externalOtherList to set
   */
  public void setExternalOtherList(String[] externalOtherList) {
    _externalOtherList = externalOtherList;
  }

  /**
   * Sets the internalOtherList.
   * @param internalOtherList The internalOtherList to set
   */
  public void setInternalOtherList(String[] internalOtherList) {
    _internalOtherList = internalOtherList;
  }

  /**
   * Returns the donorId.
   * @return String
   */
  public String getDonorId() {
    return _donorId;
  }

  /**
   * Returns the pathReportId.
   * @return String
   */
  public String getPathReportId() {
    return _pathReportId;
  }

  /**
   * Returns the rawPathReport.
   * @return String
   */
  public String getRawPathReport() {
    return _rawPathReport;
  }

  /**
   * Sets the donorId.
   * @param donorId The donorId to set
   */
  public void setDonorId(String donorId) {
    _donorId = donorId;
  }

  /**
   * Sets the pathReportId.
   * @param pathReportId The pathReportId to set
   */
  public void setPathReportId(String pathReportId) {
    _pathReportId = pathReportId;
  }

  /**
   * Sets the rawPathReport.
   * @param rawPathReport The rawPathReport to set
   */
  public void setRawPathReport(String rawPathReport) {
    _rawPathReport = rawPathReport;
  }

  /**
   * Returns the reportEvaluation.
   * @return String
   */
  public String getReportEvaluation() {
    return _reportEvaluation;
  }

  /**
   * Sets the reportEvaluation.
   * @param reportEvaluation The reportEvaluation to set
   */
  public void setReportEvaluation(String reportEvaluation) {
    _reportEvaluation = reportEvaluation;
  }
  /**
   * Returns the externalFeaturesOptions.
   * @return LegalValueSet
   */
  public LegalValueSet getExternalFeaturesOptions() {
    LegalValueSet temp = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_LIMS_EXTERNAL_FEATURES);
    for (int i = 0; i < temp.getCount(); i++) {
      if (LimsConstants.OTHER_EXTERNAL.equals(temp.getValue(i))) {
        temp.removeLegalValue(i);
        return temp;
      }
    }

    return temp;
  }

  /**
   * Returns the internalQualityIssuesOptions.
   * @return LegalValueSet
   */
  public LegalValueSet getInternalQualityIssuesOptions() {
    LegalValueSet temp = BigrGbossData.getValueSetAsLegalValueSet(ArtsConstants.VALUE_SET_LIMS_INTERNAL_QUALITY_ISSUES);
    for (int i = 0; i < temp.getCount(); i++) {
      if (LimsConstants.OTHER_INTERNAL.equals(temp.getValue(i))) {
        temp.removeLegalValue(i);
        return temp;
      }
    }

    return temp;
  }

  /**
   * Returns the createMethod.
   * @return String
   */
  public String getCreateMethod() {
    return _createMethod;
  }

  /**
   * Sets the createMethod.
   * @param createMethod The createMethod to set
   */
  public void setCreateMethod(String createMethod) {
    _createMethod = createMethod;
  }

  /**
   * Returns the showWarning.
   * @return boolean
   */
  public boolean isShowWarning() {
    return _showWarning;
  }

  /**
   * Sets the showWarning.
   * @param showWarning The showWarning to set
   */
  public void setShowWarning(boolean showWarning) {
    _showWarning = showWarning;
  }
  /**
   * Returns the warningMessage.
   * @return String
   */
  public String getWarningMessage() {
    return _warningMessage;
  }

  /**
   * Sets the warningMessage.
   * @param warningMessage The warningMessage to set
   */
  public void setWarningMessage(String warningMessage) {
    _warningMessage = warningMessage;
  }

  /**
   * Returns the viewPvReport.
   * @return String
   */
  public String getViewPvReport() {
    return _viewPvReport;
  }

  /**
   * Sets the viewPvReport.
   * @param viewPvReport The viewPvReport to set
   */
  public void setViewPvReport(String viewPvReport) {
    _viewPvReport = viewPvReport;
  }

  /**
   * Returns the sourceEvaluationId.
   * @return String
   */
  public String getSourceEvaluationId() {
    return _sourceEvaluationId;
  }

  /**
   * Sets the sourceEvaluationId.
   * @param sourceEvaluationId The sourceEvaluationId to set
   */
  public void setSourceEvaluationId(String sourceEvaluationId) {
    _sourceEvaluationId = sourceEvaluationId;
  }

  /**
   * Returns the lesionValue.
   * @return String
   */
  public String getLesionValue() {
    return _lesionValue;
  }

  /**
   * Sets the lesionValue.
   * @param lesionValue The lesionValue to set
   */
  public void setLesionValue(String lesionValue) {
    _lesionValue = lesionValue;
  }

  /**
   * Returns the popup.
   * @return String
   */
  public String getPopup() {
    return _popup;
  }

  /**
   * Sets the popup.
   * @param popup The popup to set
   */
  public void setPopup(String popup) {
    _popup = popup;
  }

  /**
   * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(BTXDetails)
   */
  public void populateFromBtxDetails(BTXDetails btxDetails) {
    super.populateFromBtxDetails(btxDetails);

    populateStructureInfo();
    populateLesionInfo();

    if (btxDetails instanceof BTXDetailsCreatePathologyEvaluationPrepare) {
      BTXDetailsCreatePathologyEvaluationPrepare btx =
        (BTXDetailsCreatePathologyEvaluationPrepare) btxDetails;

      if (((Iterator) btx.getActionErrors().properties()).hasNext()) {
        return;
      }

      if (ApiFunctions.isEmpty(getViewPvReport())) {
        setLimsCreateEvaluationForm(btx);

      }
    }
    else if (btxDetails instanceof BTXDetailsCreatePathologyEvaluation) {
      BTXDetailsCreatePathologyEvaluation btx = (BTXDetailsCreatePathologyEvaluation) btxDetails;
      if (btx.isSampleWillBeDiscordant() || btx.isSampleWillBePulled()) {
        //Send Warning to the user that if proceeding with this data causes 
        //sample will get pulled.		
        if (btx.isSampleWillBePulled()) {
          setShowWarning(true);
          //Set appropriate warning message based on condition
          if (ApiFunctions.isEmpty(getReportEvaluation()))
            setWarningMessage(LimsConstants.LIMS_WARNING_PULL);
          else
            setWarningMessage(LimsConstants.LIMS_WARNING_PULL_NOTREPORT);
        }
        if (btx.isSampleWillBeDiscordant()) {
          setShowWarning(true);
          //Set appropriate warning message based on condition
          if (ApiFunctions.isEmpty(getReportEvaluation()))
            setWarningMessage(LimsConstants.LIMS_WARNING_DISC);
          else
            setWarningMessage(LimsConstants.LIMS_WARNING_DISC_NOTREPORT);
        }
      }
    }
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(BTXDetails, BigrActionMapping, HttpServletRequest)
   */
  public void describeIntoBtxDetails(
    BTXDetails btxDetails0,
    BigrActionMapping mapping,
    HttpServletRequest request) {

    //handle the page in the popup from Path QC
    String popup = request.getParameter("popup");
    if (!ApiFunctions.isEmpty(popup)) {
      request.setAttribute("popup", popup);
    }

    if (!"/lims/limsCreateEvaluationPvReport".equals(mapping.getPath())) {
      super.describeIntoBtxDetails(btxDetails0, mapping, request);
    }

    if ("/lims/limsCreateEvaluationPvReport".equals(mapping.getPath())) {
      PathologyEvaluationData ped = getPathologyEvaluationData();

      request.setAttribute("evaluation", ped);
      BTXDetailsCreatePathologyEvaluation btx = (BTXDetailsCreatePathologyEvaluation) btxDetails0;
      btx.setPvReport(true);
      setViewPvReport("true");
    }
    else if (btxDetails0 instanceof BTXDetailsCreatePathologyEvaluation) {

      BTXDetailsCreatePathologyEvaluation btx = (BTXDetailsCreatePathologyEvaluation) btxDetails0;
      btx.setPathologyEvaluationData(getPathologyEvaluationData());

    }
  }

  /**
   * Returns the reason.
   * @return String
   */
  public String getReason() {
    return _reason;
  }

  /**
   * Sets the reason.
   * @param reason The reason to set
   */
  public void setReason(String reason) {
    _reason = reason;
  }

  /**
   * Returns the userWarned.
   * @return boolean
   */
  public boolean isUserWarned() {
    return _userWarned;
  }

  /**
   * Returns the userWarned.
   * @return String
   */
  public String getUserWarned() {
    return String.valueOf(_userWarned);
  }

  /**
   * Sets the userWarned.
   * @param userWarned The userWarned to set
   */
  public void setUserWarned(boolean userWarned) {
    _userWarned = userWarned;
  }

  /**
   * Returns the message.
   * @return String
   */
  public String getMessage() {
    return _message;
  }

  /**
   * Sets the message.
   * @param message The message to set
   */
  public void setMessage(String message) {
    _message = message;
  }

  /**
   * Returns the structureDropDown.
   * @return String
   */
  public String getStructureDropDown() {
    if (_structureDropDown == null) {
      populateStructureInfo();
    }
    return _structureDropDown;
  }

  /**
   * Returns the structureJavaScript.
   * @return String
   */
  public String getStructureJavaScript() {
    if (_structureJavaScript == null) {
      populateStructureInfo();
    }
    return _structureJavaScript;
  }

  /**
   * Returns the tissueDropDown.
   * @return String
   */
  public String getTissueDropDown() {
    if (_tissueDropDown == null) {
      populateStructureInfo();
    }
    return _tissueDropDown;
  }

  /**
   * Sets the structureDropDown.
   * @param structureDropDown The structureDropDown to set
   */
  public void setStructureDropDown(String structureDropDown) {
    _structureDropDown = structureDropDown;
  }

  /**
   * Sets the structureJavaScript.
   * @param structureJavaScript The structureJavaScript to set
   */
  public void setStructureJavaScript(String structureJavaScript) {

    _structureJavaScript = structureJavaScript;
  }

  /**
   * Sets the tissueDropDown.
   * @param tissueDropDown The tissueDropDown to set
   */
  public void setTissueDropDown(String tissueDropDown) {
    _tissueDropDown = tissueDropDown;
  }

  /**
   * Returns the lesionDropDown.
   * @return String
   */
  public String getLesionDropDown() {
    return _lesionDropDown;
  }

  /**
   * Returns the lesionJavaScript.
   * @return String
   */
  public String getLesionJavaScript() {
    return _lesionJavaScript;
  }

  /**
   * Sets the lesionDropDown.
   * @param lesionDropDown The lesionDropDown to set
   */
  public void setLesionDropDown(String lesionDropDown) {
    _lesionDropDown = lesionDropDown;
  }

  /**
   * Sets the lesionJavaScript.
   * @param lesionJavaScript The lesionJavaScript to set
   */
  public void setLesionJavaScript(String lesionJavaScript) {
    _lesionJavaScript = lesionJavaScript;
  }

  /**
   * Returns the invStatus.
   * @return String
   */
  public String getInvStatus() {
    return _invStatus;
  }

  /**
   * Sets the invStatus.
   * @param invStatus The invStatus to set
   */
  public void setInvStatus(String invStatus) {
    _invStatus = invStatus;
  }

  /**
   * Returns the reviewedRawPathReport.
   * @return boolean
   */
  public boolean isReviewedRawPathReport() {
    return _reviewedRawPathReport;
  }

  /**
   * Sets the reviewedRawPathReport.
   * @param reviewedRawPathReport The reviewedRawPathReport to set
   */
  public void setReviewedRawPathReport(boolean reviewedRawPathReport) {
    _reviewedRawPathReport = reviewedRawPathReport;
  }

  /**
   * Returns the incidentList.
   * @return String[]
   */
  public String[] getIncidentList() {
    return _incidentList;
  }

  /**
   * Sets the incidentList.
   * @param incidentList The incidentList to set
   */
  public void setIncidentList(String[] incidentList) {
    _incidentList = incidentList;
  }

}
