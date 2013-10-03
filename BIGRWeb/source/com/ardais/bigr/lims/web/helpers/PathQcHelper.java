package com.ardais.bigr.lims.web.helpers;

import java.util.Date;
import java.util.List;

import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.javabeans.ProductDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.library.web.helper.SampleSelectionHelper;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationData;
import com.ardais.bigr.lims.javabeans.PathologyEvaluationSampleData;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionData;

/**
 * Helper class for the PathQC screen
 */
public class PathQcHelper {
  
  private PathologyEvaluationSampleData _peSampleData;
  private static final int MAX_LOGICAL_REPOS_LENGTH = 30;

  /**
   * Constructor for PathQcHelper.
   */
  public PathQcHelper(PathologyEvaluationSampleData peSampleData) {
    super();
    _peSampleData = peSampleData;
  }
  
  //method to check is sample released
  public boolean isReleased() {
    return _peSampleData.isReleased();
  }
  
  //method to check is sample pulled
  public boolean isPulled() {
    return _peSampleData.isPulled();
  }
  
  //method to check if the sample is QCPosted
  public boolean isQCPosted() {
    return _peSampleData.isQCPosted();
  }
  
  //method to check is sample posted.
  public boolean isPved() {
    return _peSampleData.isPved();
  }
  
  //method to check is sample unpv'd.
  public boolean isUnPved() {
    return _peSampleData.isUnPved();
  }
  
  //method to get the sample id
  public String getSampleId() {
    return _peSampleData.getSampleId();
  }
  
  /**
   * Returns the reportedEvaluation.
   * @return PathologyEvaluationData
   */
  public PathologyEvaluationData getReportedEvaluation() {
    return _peSampleData.getReportedEvaluation();
  }
  
  //method to get the Pathologist who reported evaluation
  public String getReportedEvaluationPathologist() {
    return _peSampleData.getReportedEvaluationPathologist();
  }
  
  //method to get slide Id from the reported evaluation
  public String getReportedEvaluationSlideId() {
    return _peSampleData.getReportedEvaluationSlideId();
  }
  
  //method to get pe Id from the reported evaluation
  public String getReportedEvaluationId() {
    return _peSampleData.getReportedEvaluationId();
  }
  
  //method to get Date evaluation was created.
  public Date getReportedEvaluationCreateDate() {
    return _peSampleData.getReportedEvaluationCreateDate();
  }
  
  //method to get Date evaluation was reported.
  public Date getReportedEvaluationDate() {
    return _peSampleData.getReportedEvaluationDate();
  }
  
  //method to get value of the Normal Cells for reported evaluation.
  public String getReportedEvaluationNormalCells() {
    return _peSampleData.getReportedEvaluationNormalCells();
  }
  
  //method to get value of the Lesion Cells for reported evaluation.
  public String getReportedEvaluationLesionCells() {
    return _peSampleData.getReportedEvaluationLesionCells();
  }
  
  //method to get value of the Tumor Cells for reported evaluation.
  public String getReportedEvaluationTumorCells() {
    return _peSampleData.getReportedEvaluationTumorCells();
  }
  
  //method to get value of the CellularStroma Cells for reported evaluation.
  public String getReportedEvaluationCellularStromaCells() {
    return _peSampleData.getReportedEvaluationCellularStromaCells();
  }
  
  //method to get value of the Hypo AcellularStroma Cells for reported evaluation.
  public String getReportedEvaluationHypoacellularStromaCells() {
    return _peSampleData.getReportedEvaluationHypoacellularStromaCells();
  }
  
  //method to get value of the Necrosis Cells for reported evaluation.
  public String getReportedEvaluationNecrosisCells() {
    return _peSampleData.getReportedEvaluationNecrosisCells();
  }
  
  //method to get the text for the primary diagnosis from the reported evaluation, regardless
  //of the code
  public String getReportedEvaluationDxName() {
    return _peSampleData.getReportedEvaluationDxName();
  }
  
  //method to get the text for the tissue of origin of the reported evaluation, regardless of the code
  public String getReportedEvaluationTissueOfOriginName() {
    return _peSampleData.getReportedEvaluationTissueOfOriginName();
  }
  
  //method to get the text for the tissue of finding of the reported evaluation, regardless of the code
  public String getReportedEvaluationTissueOfFindingName() {
    return _peSampleData.getReportedEvaluationTissueOfFindingName();
  }
    
  //method to get formatted Concatenated External Data for reported evaluation.
  public String getReportedEvaluationFormattedConcatenatedExternalData() {
    return _peSampleData.getReportedEvaluationFormattedConcatenatedExternalData();
  }
  
  //method to get Concatenated Internal Data for reported evaluation.
  public String getReportedEvaluationConcatenatedInternalData() {
    return _peSampleData.getReportedEvaluationConcatenatedInternalData();
  }

  //method to get Microscopic appearance value for reported evaluation.
  public String getReportedEvaluationMicroscopicAppearance() {
    return _peSampleData.getReportedEvaluationMicroscopicAppearance();
  }
  
  //Method to get PrimarySection Tumor stage desc name
  public String getPrimarySectionTumorStageDescName() {
    return _peSampleData.getPrimarySectionTumorStageDescName();
  }

  /**
   * Returns the primarySection.
   * @return PathReportSectionData
   */
  public PathReportSectionData getPrimarySection() {
    return _peSampleData.getPrimarySection();
  }
  
  //Method to get PrimarySection Lymph node stage desc name
  public String getPrimarySectionLymphNodeStageName() {
    return _peSampleData.getPrimarySectionLymphNodeStageName();
  }
  
  //Method to get PrimarySection Distant Metastasis name
  public String getPrimarySectionDistantMetastasisName(){
    return _peSampleData.getPrimarySectionDistantMetastasisName();
  }
  
  //Method to get PrimarySection Histological Nuclear Grade Name
  public String getPrimarySectionHistologicalNuclearGradeName(){
    return _peSampleData.getPrimarySectionHistologicalNuclearGradeName();
  }
  
  //Method to get PrimarySection Stage grouping name
  public String getPrimarySectionStageGroupingName(){
    return _peSampleData.getPrimarySectionStageGroupingName();
  }
  
  //Method to get Tumor Size
  public String getPrimarySectionTumorSize(){
    return _peSampleData.getPrimarySectionTumorSize();
  }
  
  //Method to get Tumor weight
  public String getPrimarySectionTumorWeight(){
    return _peSampleData.getPrimarySectionTumorWeight();
  }
  
  //method to get the pulled date for the sample
  public Date getPulledDate() {
    return _peSampleData.getPulledDate();
  }
  
  //method to get the PV Status.
  public String getPvStatus(){
    return _peSampleData.getPvStatus();
  }
  
  //method to get the case id
  public String getCaseId() {
    return _peSampleData.getCaseId();
  }
  
  public String getDonorId() {
    return _peSampleData.getDonorId();
  }
  
  //method to get the Age of Collection for the sample.
  public String getAgeAtCollection() {
    return IltdsUtils.getSampleAgeAtCollection(getSampleId());
  }
  
  //method to get the gender of donor.
  public String getDonorGender() {
    return _peSampleData.getDonorGender();
  }
  
  //method to get the ASM position
  public String getAsmPosition() {
    return _peSampleData.getAsmPosition();
  }
  
  //method to get the text for the ASM tissue of finding, regardless of the code
  public String getASMTissueOfFindingName() {
    return _peSampleData.getASMTissueOfFindingName();
  }
  
  //method to get the pull/discordant reason
  public String getPullOrDiscordantReason() {
    return _peSampleData.getPullOrDiscordantReason();
  }
  
  //method to get the text for the primary diagnosis from the DI Path report, regardless
  //of the code
  public String getDIPathReportPrimaryDxName() {
    return _peSampleData.getDIPathReportPrimaryDxName();
  }
  
  //method to get images from the sample
  public List getSampleImages() {
    return _peSampleData.getSampleImages();
  }
  
  //method to get logical repository short names from the sample
  public String getSampleLogicalRepositoryShortNamesShort() {
    String value = "";
    value = _peSampleData.getLogicalRepositoryShortNames();
    if (value.length() > MAX_LOGICAL_REPOS_LENGTH) {
      return value.substring(0, MAX_LOGICAL_REPOS_LENGTH) + " ...";
    }
    else {
      return value.trim();
    }
  }
  
  //method to get logical repository short names from the sample
  public String getSampleLogicalRepositoryShortNames() {
    return _peSampleData.getLogicalRepositoryShortNames();
  }

  /**
   * Returns the pathReport.
   * @return PathReportData
   */
  public PathReportData getPathReport() {
    return _peSampleData.getPathReport();
  }
    
  public String getPathReportId() {
    return _peSampleData.getPathReportId();
  }
  
  //method to get the Inventory Status.  Howie wants the information to appear
  //exactly as it does in Sample Selection, so we use a SampleSelectionHelper
  //class to do that.
  public String getInventoryStatus() {
    ProductDto ssd = new ProductDto();
    ssd.setSampleData(_peSampleData.getSample());
    SampleSelectionHelper ssh = new SampleSelectionHelper(ssd);
    return ssh.getInventoryStatus();
  }
  
  //method to get the Sales Status.  Howie wants the information to appear
  //exactly as it does in Sample Selection, so we use a SampleSelectionHelper
  //class to do that.
  public String getSalesStatus() {
    ProductDto ssd = new ProductDto();
    SampleData sampleData = _peSampleData.getSample();
    sampleData.setOrderData(_peSampleData.getOrder());
    ssd.setSampleData(sampleData);
    SampleSelectionHelper ssh = new SampleSelectionHelper(ssd);
    return ssh.getOrderDescription();
  }
  
  //method to get the title for the Project Status column, which shows as a tooltip.  
  //Howie wants the information to appear exactly as it does in Sample Selection, 
  //so we use a SampleSelectionHelper class to do that.
  public String getProjectStatusTitle() {
    ProductDto ssd = new ProductDto();
    ssd.setSampleData(_peSampleData.getSample());
    SampleSelectionHelper ssh = new SampleSelectionHelper(ssd);

    String userAndDate = ssh.getCartEntriesString();
    
    if (ssh.getProject() != "") {
      return ssh.getProject() + " - " + ssh.getProjectRequestDate();
    }
    else if (userAndDate.length() > 0) {
      return userAndDate;
    }
    else if (ssh.getSalesStatus().equals(FormLogic.SMPL_ADDTOCART)) {
      return "Orphan";
    }
    else if (ssh.getProjectStatus().equals("PRJADDED")) {
      return "Orphan";
    }
    else {
      return "";
    }
  }
  
  //method to get the Project Status.  Howie wants the information to appear
  //exactly as it does in Sample Selection, so we use a SampleSelectionHelper
  //class to do that.
  public String getProjectStatus() {
    ProductDto ssd = new ProductDto();
    SampleData sampleData = _peSampleData.getSample();
    sampleData.setProjectData(_peSampleData.getProject());
    ssd.setSampleData(sampleData);
    SampleSelectionHelper ssh = new SampleSelectionHelper(ssd);
    StringBuffer result = new StringBuffer(128);

    String userAndDate = ssh.getCartEntriesString();
    
    if (ssh.getProject() != "") {
      result.append("P");
      result.append(ssh.getProjectDaysSinceRequestDate());
    }
    else if (userAndDate.length() > 0) {
      result.append("H");
      result.append(ssh.getCartDaysSinceCreationDate());      
    }
    else if (ssh.getSalesStatus().equals(FormLogic.SMPL_ADDTOCART)) {
      result.append("H");
    }
    else if (ssh.getProjectStatus().equals("PRJADDED")) {
      result.append("P");
    }
    return result.toString();
  }

}
