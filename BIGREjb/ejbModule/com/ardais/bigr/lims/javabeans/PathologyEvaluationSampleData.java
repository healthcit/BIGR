package com.ardais.bigr.lims.javabeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.assistants.ProjectDataBean;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.javabeans.AsmData;
import com.ardais.bigr.javabeans.ConsentData;
import com.ardais.bigr.javabeans.OrderData;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.pdc.javabeans.PathReportData;
import com.ardais.bigr.pdc.javabeans.PathReportSectionData;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Captures the information displayed about the sample to which a pathology evaluation belongs 
 * (or will belong, for a new evaluation).  Used in both CreateEvaluation and ManageEvaluation
 * transactions
 */
public class PathologyEvaluationSampleData implements Serializable{
	private static final long serialVersionUID = 8114997004410306348L;
	
	//to get sample id, asm position, format, qc status, released, pulled, pull reason
	private SampleData sample;
	
	//to get case Id, Case Dx at DI
	private ConsentData consent;
	
	//to get gross appearance, asm tissue of finding
	private AsmData asm;
	
	//to get case tissue of finding, case tissue of origin, DI Path report
	//primary diagnosis
	private PathReportSectionData primarySection;
	
	//to get all section diagnosis values
	private List pathReportSections;
	
	//to get microscopic appearance, sample path from Ardais PV, Tissue of Origin of Diagnosis,
	//Tissue of Site of Finding
	private PathologyEvaluationData reportedEvaluation;
	
	//to get information on the slides created for this sample
	private List slides;
    
    //to get information about path report
    private PathReportData _pathReport;
    
  //to get information about orders the sample in this bean is on
  private OrderData _order;
    
  //to get information about projects the sample in this bean is/was on
  private ProjectDataBean _project;

	/**
	 * Constructor for PathologyEvaluationSampleData.
	 */
	public PathologyEvaluationSampleData() {
		super();
	}

	/**
	 * Returns the asm.
	 * @return AsmData
	 */
	public AsmData getAsm() {
		return asm;
	}

	/**
	 * Returns the consent.
	 * @return ConsentData
	 */
	public ConsentData getConsent() {
		return consent;
	}

	/**
	 * Returns the pathReportSections.
	 * @return List
	 */
	public List getPathReportSections() {
		return pathReportSections;
	}

	/**
	 * Returns the primarySection.
	 * @return PathReportSectionData
	 */
	public PathReportSectionData getPrimarySection() {
		return primarySection;
	}

	/**
	 * Returns the sample.
	 * @return SampleData
	 */
	public SampleData getSample() {
		return sample;
	}
	
	/**
	 * Returns the reportedEvaluation.
	 * @return PathologyEvaluationData
	 */
	public PathologyEvaluationData getReportedEvaluation() {
		return reportedEvaluation;
	}

	/**
	 * Returns the slides.
	 * @return List
	 */
	public List getSlides() {
		return slides;
	}

	/**
	 * Sets the asm.
	 * @param asm The asm to set
	 */
	public void setAsm(AsmData asm) {
		this.asm = asm;
	}

	/**
	 * Sets the consent.
	 * @param consent The consent to set
	 */
	public void setConsent(ConsentData consent) {
		this.consent = consent;
	}

	/**
	 * Sets the pathReportSections.
	 * @param pathReportSections The pathReportSections to set
	 */
	public void setPathReportSections(List pathReportSections) {
		this.pathReportSections = pathReportSections;
	}

	/**
	 * Sets the primarySection.
	 * @param primarySection The primarySection to set
	 */
	public void setPrimarySection(PathReportSectionData primarySection) {
		this.primarySection = primarySection;
	}

	/**
	 * Sets the sample.
	 * @param sample The sample to set
	 */
	public void setSample(SampleData sample) {
		this.sample = sample;
	}

	/**
	 * Sets the reportedEvaluation.
	 * @param reportedEvaluation The reportedEvaluation to set
	 */
	public void setReportedEvaluation(PathologyEvaluationData reportedEvaluation) {
		this.reportedEvaluation = reportedEvaluation;
	}

	/**
	 * Sets the slides.
	 * @param slides The slides to set
	 */
	public void setSlides(List slides) {
		this.slides = slides;
	}
	
	/** The following methods are provided as a convenience to users of this class.  Rather
	 * than requiring them to get the individual classes this class wraps and call various
	 * get methods on those objects, the callers can simply call the get methods on this
	 * class which serve as pass-through methods to the get methods on the contained classes */
	
	/** methods used by both manage and create evaluation */

	//method to get the sample id
	public String getSampleId() {
		if (getSample() == null)
			return "";
		else
			return getSample().getSampleId();
	}
	
	//method to get the case id
	public String getCaseId() {
		if (getConsent() == null)
			return "";
		else
			return getConsent().getConsentId();
	}
	
	//method to get the gender of donor.
	public String getDonorGender() {
		if (getConsent() == null)
			return "";
		else
			return getConsent().getGender();
	}
	
	//method to get the ASM position
	public String getAsmPosition() {
		if (getSample() == null)
			return "";
		else
			return getSample().getAsmPosition();
	}
	
	//method to get the code for the primary diagnosis from the DI Path report
	public String getDIPathReportPrimaryDx() {
		if (getPrimarySection() == null)
			return "";
		else
			return getPrimarySection().getDiagnosis();
	}
	
	//method to get the text for the primary diagnosis from the DI Path report when the
	//code is other
	public String getDIPathReportPrimaryDxOther() {
		if (getPrimarySection() == null)
			return "";
		else
			return getPrimarySection().getDiagnosisOther();
	}
	
	//method to get the text for the primary diagnosis from the DI Path report, regardless
	//of the code
	public String getDIPathReportPrimaryDxName() {
		if (getPrimarySection() == null)
			return "";
		else
			return getPrimarySection().getDiagnosisName();
	}
	
	//method to get the gross appearance
	public String getGrossAppearance() {
		if (getAsm() == null || ApiFunctions.isEmpty(getAsm().getGrossAppearance()))
			return "";
		else {
			String app = FormLogic.lookupAppearance(getAsm().getGrossAppearance());
			if (app == null)
				return "Unknown appearance code";
			else
				return app;
		}
	}
	
	//method to get the code for the tissue of finding of the case (i.e. the tissue of
	//finding on the primary section of the path report)
	public String getCaseTissueOfFinding() {
		if (getPrimarySection() == null)
			return "";
		else
			return getPrimarySection().getTissueFinding();
	}
	
	//method to get the text for the tissue of finding of the case (i.e. the tissue of
	//finding on the primary section of the path report) when the code is other
	public String getCaseTissueOfFindingOther() {
		if (getPrimarySection() == null)
			return "";
		else
			return getPrimarySection().getTissueFindingOther();
	}
	
	//method to get the text for the tissue of finding of the case (i.e. the tissue of
	//finding on the primary section of the path report), regardless of the code
	public String getCaseTissueOfFindingName() {
		if (getPrimarySection() == null)
			return "";
		else
			return getPrimarySection().getTissueFindingName();
	}
	
	//method to get the code for the tissue of origin of the case (i.e. the tissue of
	//origin on the primary section of the path report)
	public String getCaseTissueOfOrigin() {
		if (getPrimarySection() == null)
			return "";
		else
			return getPrimarySection().getTissueOrigin();
	}
	
	//method to get the text for the tissue of origin of the case (i.e. the tissue of
	//origin on the primary section of the path report) when the code is other
	public String getCaseTissueOfOriginOther() {
		if (getPrimarySection() == null)
			return "";
		else
			return getPrimarySection().getTissueOriginOther();
	}
	
	//method to get the text for the tissue of origin of the case (i.e. the tissue of
	//origin on the primary section of the path report), regardless of the code
	public String getCaseTissueOfOriginName() {
		if (getPrimarySection() == null)
			return "";
		else
			return getPrimarySection().getTissueOriginName();
	}

	
	/** methods used only by create evaluation */
	
	//method to get a list of section diagnosis names, regardless of their codes
	public String[] getSectionDxNames() {
		List returnValue = new Vector();
		List sectionDxs = getPathReportSections();
		if (sectionDxs != null) {
			Iterator iterator = sectionDxs.iterator();
			while (iterator.hasNext()) {
				returnValue.add(((PathReportSectionData)iterator.next()).getDiagnosisName());
			}
		}
		return (String[]) returnValue.toArray(new String[] {});
	}
	
	//method to get the code for the ASM tissue of finding
	public String getASMTissueOfFinding() {
		if (getAsm() == null)
			return "";
		else
			return getAsm().getTissue();
	}
	
	//method to get the text for the ASM tissue of finding when the code is other
	public String getASMTissueOfFindingOther() {
		if (getAsm() == null)
			return "";
		else
			return getAsm().getTissueOther();
	}
	
	//method to get the text for the ASM tissue of finding, regardless of the code
	public String getASMTissueOfFindingName() {
		if (getAsm() == null)
			return "";
		else
			return getAsm().getTissueName();
	}

	
	/** methods used only by manage evaluation */
	
	//method to get the sample format
	public String getSampleType() {
		if (getSample() == null) {
      return "";
		}
		else {
      String sampleTypeCid =  getSample().getSampleTypeCui();
      if (sampleTypeCid == null) {
        return "Any";
      }
      else {
        return GbossFactory.getInstance().getDescription(sampleTypeCid);
      }
		}
	}
  
  //method to get the logical repositories
  public String getLogicalRepositoryShortNames() {
    if (getSample() == null)
      return "";
    else {
      List logicalRepositories = getSample().getLogicalRepositories();
      if (logicalRepositories == null || logicalRepositories.isEmpty()) {
        return "";
      }
      else {
        StringBuffer buff = new StringBuffer(50);
        Iterator iterator = logicalRepositories.iterator();
        boolean includeComma = false;
        while (iterator.hasNext()) {
          LogicalRepository lr = (LogicalRepository)iterator.next();
          if (includeComma) {
            buff.append(", ");
          }
          buff.append(lr.getShortName());
          includeComma = true;
        }
        return buff.toString();
      }
    }
  }
	
	//method to get the Inventory Status
	public String getInventoryStatus() {
		if (getSample() == null)
			return "";
		else
			return FormLogic.lookupInvStatus(getSample().getInvStatus());
	}
	
	//method to get Case diagnosis code
	public String getCaseDx() {
		if (getConsent() == null)
			return "";
		else
			return getConsent().getDiagnosis();
	}
	
	//method to get the text for the diagnosis from the case when the
	//code is other
	public String getCaseDxOther() {
		if (getConsent() == null)
			return "";
		else
			return getConsent().getDiagnosisOther();
	}
	
	//method to get the text for the diagnosis from the case, regardless
	//of the code
	public String getCaseDxName() {
		if (getConsent() == null)
			return "";
		else
			return getConsent().getDiagnosisName();
	}
	
	//method to get the microscopic appearance from the reported evaluation, if any
	public String getMicroscopicAppearance() {
		if (getReportedEvaluation() == null)
			return "";
		else {
			String app = FormLogic.lookupAppearance(getReportedEvaluation().getMicroscopicAppearance());
			if (app == null)
				return "Unknown appearance code";
			else
				return app;
		}
	}
	
	//method to check is sample released
	public boolean isReleased() {
		if (getSample() == null)
			return false;
		else {
			return (getSample().isReleased() && !isQCPosted());	
		}
	}
	
	//method to check is sample pulled
	public boolean isPulled() {
		if (getSample() == null)
			return false;
		else {
			return getSample().isPulled();	
		}
	}
	
	//method to check if the sample is QCPosted
	public boolean isQCPosted() {
		if (getSample() == null)
			return false;
		else {
			return getSample().isQCPosted();	
		}
	}
	
	//method to check is sample posted.
	public boolean isPved() {
		return (!isPulled() && !isReleased() && (getReportedEvaluation() != null) && !isQCPosted());
	}
	
	//method to check is sample unpv'd.
	public boolean isUnPved() {
		return (!isPulled() && !isReleased() && (getReportedEvaluation() == null));
	}
	
	//method to get the QC Status.
	public String getIltdsQcStatus() {
		if (getSample() == null) 
			return "";
		else {
			return FormLogic.lookupQCStatus(getSample().getQcStatus());
		}
	}
	
	//method to get the PV Status.
	public String getPvStatus(){
		if(isUnPved()){
			return LimsConstants.PV_STATUS_UNPVED;
		}
		if(isPved()){
			return LimsConstants.PV_STATUS_PVED;
		}
		if(isReleased()){
			return LimsConstants.PV_STATUS_RELEASED;	
		}
		if(isPulled()){
			return LimsConstants.PV_STATUS_PULLED;
		} 
		if(isQCPosted()){
			return LimsConstants.PV_STATUS_QCPOSTED;
		}
		else return "N/A";
	}
	
	//method to get Date evaluation was created.
	public Date getReportedEvaluationCreateDate() {
		if (getReportedEvaluation() == null)
			return null;
		else
			return getReportedEvaluation().getCreationDate();
	}
	
	//method to get Date evaluation was reported.
	public Date getReportedEvaluationDate() {
		if (getReportedEvaluation() == null)
			return null;
		else
			return getReportedEvaluation().getReportedDate();
	}
	
	//method to get the Pathologist who reported evaluation
	public String getReportedEvaluationPathologist() {
		if (getReportedEvaluation() == null)
			return "";
		else
			return getReportedEvaluation().getPathologist();
	}
	//method to get the code for the diagnosis from the reported evaluation
	public String getReportedEvaluationDx() {
		if (getReportedEvaluation() == null)
			return "";
		else
			return getReportedEvaluation().getDiagnosis();
	}
	
	//method to get slide Id from the reported evaluation
	public String getReportedEvaluationSlideId() {
		if (getReportedEvaluation() == null)
			return "";
		else
			return getReportedEvaluation().getSlideId();
	}
	
	//method to get images from the sample
	public List getSampleImages() {
		if (getSample() == null)
			return null;
		else {
			ArrayList images = getSample().getImages();
			if (images == null || images.size() == 0)
				return null;
			else
				return  images;
		}
	}
	
	//method to get pe Id from the reported evaluation
	public String getReportedEvaluationId() {
		if (getReportedEvaluation() == null)
			return "";
		else
			return getReportedEvaluation().getEvaluationId();
	}
	
	//method to get the text for the diagnosis from the reported evaluation when the
	//code is other
	public String getReportedEvaluationDxOther() {
		if (getReportedEvaluation() == null)
			return "";
		else
			return getReportedEvaluation().getDiagnosisOther();
	}
	
	//method to get the text for the primary diagnosis from the reported evaluation, regardless
	//of the code
	public String getReportedEvaluationDxName() {
		if (getReportedEvaluation() == null)
			return "";
		else
			return getReportedEvaluation().getDiagnosisName();
	}
	
	//method to get the released value for the sample
	public String getReleased() {
		if (getSample() == null)
			return "";
		else {
			if (getSample().isReleased())
				return LimsConstants.LIMS_DB_YES_TEXT;
			else
				return LimsConstants.LIMS_DB_NO_TEXT;
		}
	}
	
	//method to get the pulled value for the sample
	public String getPulled() {
		if (getSample() == null)
			return "";
		else {
			if (getSample().isPulled())
				return LimsConstants.LIMS_DB_YES_TEXT;
			else
				return LimsConstants.LIMS_DB_NO_TEXT;
		}
	}
	
	//method to get the pulled date for the sample
	public Date getPulledDate() {
		if (getSample() == null)
			return null;
		else {
			if (getSample().isPulled())
				return getSample().getPulledDate();
			else
				return null;
		}
	}
	
	//method to get the discordant value for the sample
	public String getDiscordant() {
		if (getSample() == null)
			return "";
		else {
			if (getSample().isDiscordant())
				return LimsConstants.LIMS_DB_YES_TEXT;
			else
				return LimsConstants.LIMS_DB_NO_TEXT;
		}
	}
	
	//method to get the code for the tissue of origin of the reported evaluation
	public String getReportedEvaluationTissueOfOrigin() {
		if (getReportedEvaluation() == null)
			return "";
		else
			return getReportedEvaluation().getTissueOfOrigin();
	}
	
	//method to get the text for the tissue of origin of the reported evaluation when the code is other
	public String getReportedEvaluationTissueOfOriginOther() {
		if (getReportedEvaluation() == null)
			return "";
		else
			return getReportedEvaluation().getTissueOfOriginOther();
	}
	
	//method to get value of the Normal Cells for reported evaluation.
	public String getReportedEvaluationNormalCells() {
		if (getReportedEvaluation() == null)
			return "";
		else{
			if (getReportedEvaluation().getNormalCells() == 0)
			 return "";
			else
			 return String.valueOf(getReportedEvaluation().getNormalCells());
		}
			
	}
	
	//method to get value of the Lesion Cells for reported evaluation.
	public String getReportedEvaluationLesionCells() {
		if (getReportedEvaluation() == null)
			return "";
		else {
			if (getReportedEvaluation().getLesionCells() == 0)
			  return "";
			else 
			  return String.valueOf(getReportedEvaluation().getLesionCells());  
		}
	}
	
	//method to get value of the Tumor Cells for reported evaluation.
	public String getReportedEvaluationTumorCells() {
		if (getReportedEvaluation() == null)
			return "";
		else {
			if (getReportedEvaluation().getTumorCells() == 0)
			  return "";
			else
			  return String.valueOf(getReportedEvaluation().getTumorCells());
		}
			
	}
	
	//method to get value of the CellularStroma Cells for reported evaluation.
	public String getReportedEvaluationCellularStromaCells() {
		if (getReportedEvaluation() == null)
			return "";
		else {
			if (getReportedEvaluation().getCellularStromaCells() == 0)
			  return "";
			else
			  return String.valueOf(getReportedEvaluation().getCellularStromaCells());
		}
			
	}
	
	//method to get value of the Hypo AcellularStroma Cells for reported evaluation.
	public String getReportedEvaluationHypoacellularStromaCells() {
		if (getReportedEvaluation() == null)
			return "";
		else {
			if (getReportedEvaluation().getHypoacellularStromaCells() == 0)
			 return "";
			else
			 return String.valueOf(getReportedEvaluation().getHypoacellularStromaCells());  
		}			
	}
	
	//method to get value of the Necrosis Cells for reported evaluation.
	public String getReportedEvaluationNecrosisCells() {
		if (getReportedEvaluation() == null)
			return "";
		else {
			if (getReportedEvaluation().getNecrosisCells() == 0)
			  return "";
			else
			  return String.valueOf(getReportedEvaluation().getNecrosisCells());  
		}			
	}
	
	//method to get Concatenated Internal Data for reported evaluation.
	public String getReportedEvaluationConcatenatedInternalData() {
		if (getReportedEvaluation() == null)
			return "";
		else
			return String.valueOf(getReportedEvaluation().getConcatenatedInternalData());
	}
	
	//method to get Concatenated External Data for reported evaluation.
	public String getReportedEvaluationConcatenatedExternalData() {
		if (getReportedEvaluation() == null)
			return "";
		else
			return String.valueOf(getReportedEvaluation().getConcatenatedExternalData());
	}  
    
  //method to get formatted Concatenated External Data for reported evaluation.
  public String getReportedEvaluationFormattedConcatenatedExternalData() {
    if (getReportedEvaluation() == null)
      return "";
    else
      return String.valueOf(getReportedEvaluation().getFormattedConcatenatedExternalData());
  }

	//method to get Microscopic appearance value for reported evaluation.
	public String getReportedEvaluationMicroscopicAppearance() {
		if (getReportedEvaluation() == null)
			return "";
		else
			return String.valueOf(getReportedEvaluation().getMicroscopicAppearanceName());
	}
	
	//method to get the text for the tissue of origin of the reported evaluation, regardless of the code
	public String getReportedEvaluationTissueOfOriginName() {
		if (getReportedEvaluation() == null)
			return "";
		else
			return getReportedEvaluation().getTissueOfOriginName();
	}
	
	//method to get the code for the tissue of finding of the reported evaluation
	public String getReportedEvaluationTissueOfFinding() {
		if (getReportedEvaluation() == null)
			return "";
		else
			return getReportedEvaluation().getTissueOfFinding();
	}
	
	//method to get the text for the tissue of finding of the reported evaluation when the code is other
	public String getReportedEvaluationTissueOfFindingOther() {
		if (getReportedEvaluation() == null)
			return "";
		else
			return getReportedEvaluation().getTissueOfFindingOther();
	}
	
	//method to get the text for the tissue of finding of the reported evaluation, regardless of the code
	public String getReportedEvaluationTissueOfFindingName() {
		if (getReportedEvaluation() == null)
			return "";
		else
			return getReportedEvaluation().getTissueOfFindingName();
	}
	
	//method to get the pull/discordant reason
	public String getPullOrDiscordantReason() {
		if (getSample() == null) 
			return "";
		else
			return getSample().getPullReason();
	}
	
	//Method to get PrimarySection Tumor stage desc
	public String getPrimarySectionTumorStageDesc() {
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getTumorStageDesc();
		}
	}
	
	//Method to get PrimarySection Tumor stage desc other
	public String getPrimarySectionTumorStageDescOther() {
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getTumorStageDescOther();
		}
	}
	
	//Method to get PrimarySection Tumor stage desc name
	public String getPrimarySectionTumorStageDescName() {
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getTumorStageDescName();
		}
	}
	
	//Method to get PrimarySection Lymph node stage desc
	public String getPrimarySectionLymphNodeStage() {
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getLymphNodeStage();
		}
	}
	
	//Method to get PrimarySection Lymph node stage desc other
	public String getPrimarySectionLymphNodeStageOther() {
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getLymphNodeStageOther();
		}
	}
	
	//Method to get PrimarySection Lymph node stage desc name
	public String getPrimarySectionLymphNodeStageName() {
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getLymphNodeStageName();
		}
	}
	
	//Method to get PrimarySection Distant Metastasis
	public String getPrimarySectionDistantMetastasis(){
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getDistantMetastasis();
		}
	}
	
	//Method to get PrimarySection Distant Metastasis other
	public String getPrimarySectionDistantMetastasisOther(){
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getDistantMetastasisOther();
		}
	}
	
	//Method to get PrimarySection Distant Metastasis name
	public String getPrimarySectionDistantMetastasisName(){
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getDistantMetastasisName();
		}
	}
	
	//Method to get PrimarySection Stage grouping
	public String getPrimarySectionStageGrouping(){
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getStageGrouping();
		}
	}
	
	//Method to get PrimarySection Stage grouping other
	public String getPrimarySectionStageGroupingOther(){
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getStageGroupingOther();
		}
	}
	
	//Method to get PrimarySection Stage grouping name
	public String getPrimarySectionStageGroupingName(){
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getStageGroupingName();
		}
	}
	
	//Method to get PrimarySection Histological Nuclear Grade
	public String getPrimarySectionHistologicalNuclearGrade(){
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getHistologicalNuclearGrade();
		}
	}
	
	//Method to get PrimarySection Histological Nuclear Grade Other
	public String getPrimarySectionHistologicalNuclearGradeOther(){
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getHistologicalNuclearGradeOther();
		}
	}
	
	//Method to get PrimarySection Histological Nuclear Grade Name
	public String getPrimarySectionHistologicalNuclearGradeName(){
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getHistologicalNuclearGradeName();
		}
	}
	
	//Method to get Tumor Size
	public String getPrimarySectionTumorSize(){
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getTumorSize();
		}
	}
	
	//Method to get Tumor weight
	public String getPrimarySectionTumorWeight(){
		if (primarySection == null) {
			return "";
		} else {
			return primarySection.getTumorWeight();
		}
	}
	
	/** methods required for other reasons */
    
  public String getPathReportId() {
    PathReportData report = getPathReport();
    if (report == null)
      return "";
    else
      return report.getPathReportId();
  }
    
  public String getRawPathReport() {
    PathReportData report = getPathReport();
    if (report == null)
      return "";
    else
      return report.getRawPathReport();
  }
	
	public String getDonorId() {
		if (getConsent() == null)
			return "";
		else
			return getConsent().getDonorId();
	}

	/**
	 * Returns the pathReport.
	 * @return PathReportData
	 */
	public PathReportData getPathReport() {
		return _pathReport;
	}

	/**
	 * Sets the pathReport.
	 * @param pathReport The pathReport to set
	 */
	public void setPathReport(PathReportData pathReport) {
		_pathReport = pathReport;
	}

  /**
   * Returns the order.
   * @return OrderData
   */
  public OrderData getOrder() {
    return _order;
  }

  /**
   * Sets the order.
   * @param order The order to set
   */
  public void setOrder(OrderData order) {
    _order = order;
  }

  /**
   * Returns the project.
   * @return ProjectDataBean
   */
  public ProjectDataBean getProject() {
    return _project;
  }

  /**
   * Sets the project.
   * @param project The project to set
   */
  public void setProject(ProjectDataBean project) {
    _project = project;
  }

}
