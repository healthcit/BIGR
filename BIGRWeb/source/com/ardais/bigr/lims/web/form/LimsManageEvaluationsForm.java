package com.ardais.bigr.lims.web.form;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.lims.btx.BTXDetailsCreatePathologyEvaluationPrepare;
import com.ardais.bigr.lims.btx.BTXDetailsGetSamplePathologyInfo;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.util.WebUtils;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author JThompson
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LimsManageEvaluationsForm extends BigrActionForm {
	private String _slideId;
	private String _sampleId;
	private String _asmPosition;
	private String _consentId;
	private String _grossAppearance;
	private String _sampleType;
	private String _microAppearance;
	private String _caseDxName;

	private String _invStatus;
	private String _pvStatus;
	private String _released;
	private String _pulled;
	private String _discordant;
	private String _reason;
	
	private String _reportedDxName;
	private String _reportedTissueOfFindingName;
	private String _reportedTissueOfOriginName;

	private String _dIPathReportPrimaryDxName;
	private String _primaryTissueOfFindingName;
	private String _primaryTissueOfOriginName;

	private List _evaluationsList;
	private List _transactionHistoryList;

	private String _pullReason;
	private String _editCopyId;
	private String _isEditCopy;
	private String _sourceEvaluationId;

	private List _slides;
	private List _pathologyEvaluations;
	
	private String _donorId;
	private String _pathReportId;
	
	private String _message;
	
	private String _editCopyButton;
    
    private String _popup;

	/**
	 * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
	 */
	protected void doReset(
		BigrActionMapping mapping,
		HttpServletRequest request) {
	}

	/**
	 * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
	 */
	public ActionErrors doValidate(
		BigrActionMapping mapping,
		HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();    
    	
		return errors;
	}

	/**
	 * Returns the slideId.
	 * @return String
	 */
	public String getSlideId() {
		return _slideId;
	}

	/**
	 * Sets the slideId.
	 * @param slideId The slideId to set
	 */
	public void setSlideId(String slideId) {
		this._slideId = slideId;
	}

	/**
	 * Returns the asmPosition.
	 * @return String
	 */
	public String getAsmPosition() {
		return _asmPosition;
	}

	/**
	 * Returns the consentId.
	 * @return String
	 */
	public String getConsentId() {
		return _consentId;
	}

	/**
	 * Returns the evaluationsList.
	 * @return List
	 */
	public List getEvaluationsList() {
		return _evaluationsList;
	}

	/**
	 * Returns the sample type.
	 * @return String
	 */
	public String getSampleType() {
		return _sampleType;
	}

	/**
	 * Returns the grossAppearance.
	 * @return String
	 */
	public String getGrossAppearance() {
		return _grossAppearance;
	}

	/**
	 * Returns the invStatus.
	 * @return String
	 */
	public String getInvStatus() {
		return _invStatus;
	}

	/**
	 * Returns the microAppearance.
	 * @return String
	 */
	public String getMicroAppearance() {
		return _microAppearance;
	}

	/**
	 * Returns the pullStatus.
	 * @return String
	 */
	public String getPulled() {
		return _pulled;
	}

	/**
	 * Returns the pvStatus.
	 * @return String
	 */
	public String getPvStatus() {
		return _pvStatus;
	}

	/**
	 * Returns the reason.
	 * @return String
	 */
	public String getReason() {
		return _reason;
	}

	/**
	 * Returns the releaseStatus.
	 * @return String
	 */
	public String getReleased() {
		return _released;
	}

	/**
	 * Returns the sampleId.
	 * @return String
	 */
	public String getSampleId() {
		return _sampleId;
	}

	/**
	 * Returns the transactionHistoryList.
	 * @return List
	 */
	public List getTransactionHistoryList() {
		return _transactionHistoryList;
	}

	/**
	 * Sets the asmPosition.
	 * @param asmPosition The asmPosition to set
	 */
	public void setAsmPosition(String asmPosition) {
		_asmPosition = asmPosition;
	}

	/**
	 * Sets the consentId.
	 * @param consentId The consentId to set
	 */
	public void setConsentId(String consentId) {
		_consentId = consentId;
	}

	/**
	 * Sets the evaluationsList.
	 * @param evaluationsList The evaluationsList to set
	 */
	public void setEvaluationsList(List evaluationsList) {
		_evaluationsList = evaluationsList;
	}

	/**
	 * Sets the sample type.
	 * @param sampleType The sample type to set
	 */
	public void setSampleType(String sampleType) {
		_sampleType = sampleType;
	}

	/**
	 * Sets the grossAppearance.
	 * @param grossAppearance The grossAppearance to set
	 */
	public void setGrossAppearance(String grossAppearance) {
		_grossAppearance = grossAppearance;
	}

	/**
	 * Sets the invStatus.
	 * @param invStatus The invStatus to set
	 */
	public void setInvStatus(String invStatus) {
		_invStatus = invStatus;
	}

	/**
	 * Sets the microAppearance.
	 * @param microAppearance The microAppearance to set
	 */
	public void setMicroAppearance(String microAppearance) {
		_microAppearance = microAppearance;
	}

	/**
	 * Sets the pullStatus.
	 * @param pullStatus The pullStatus to set
	 */
	public void setPulled(String pulled) {
		_pulled = pulled;
	}

	/**
	 * Sets the pvStatus.
	 * @param pvStatus The pvStatus to set
	 */
	public void setPvStatus(String pvStatus) {
		_pvStatus = pvStatus;
	}

	/**
	 * Sets the reason.
	 * @param reason The reason to set
	 */
	public void setReason(String reason) {
		_reason = reason;
	}

	/**
	 * Sets the releaseStatus.
	 * @param releaseStatus The releaseStatus to set
	 */
	public void setReleased(String released) {
		_released = released;
	}

	/**
	 * Sets the sampleId.
	 * @param sampleId The sampleId to set
	 */
	public void setSampleId(String sampleId) {
		_sampleId = sampleId;
	}

	/**
	 * Sets the transactionHistoryList.
	 * @param transactionHistoryList The transactionHistoryList to set
	 */
	public void setTransactionHistoryList(List transactionHistoryList) {
		_transactionHistoryList = transactionHistoryList;
	}
	/**
	 * Returns the pullReason.
	 * @return String
	 */
	public String getPullReason() {
		return _pullReason;
	}

	/**
	 * Sets the pullReason.
	 * @param pullReason The pullReason to set
	 */
	public void setPullReason(String pullReason) {
		_pullReason = pullReason;
	}

	/**
	 * Returns the slides.
	 * @return List
	 */
	public List getSlides() {
		return _slides;
	}

	/**
	 * Sets the slides.
	 * @param slides The slides to set
	 */
	public void setSlides(List slides) {
		_slides = slides;
	}

	/**
	 * Returns the pathologyEvaluations.
	 * @return List
	 */
	public List getPathologyEvaluations() {
		return _pathologyEvaluations;
	}

	/**
	 * Sets the pathologyEvaluations.
	 * @param pathologyEvaluations The pathologyEvaluations to set
	 */
	public void setPathologyEvaluations(List pathologyEvaluations) {
		_pathologyEvaluations = pathologyEvaluations;
	}

	/**
	 * Returns the editCopyId.
	 * @return String
	 */
	public String getEditCopyId() {
		return _editCopyId;
	}

	/**
	 * Returns the sourceEvaluationId.
	 * @return String
	 */
	public String getSourceEvaluationId() {
		return _sourceEvaluationId;
	}

	/**
	 * Sets the editCopyId.
	 * @param editCopyId The editCopyId to set
	 */
	public void setEditCopyId(String editCopyId) {
		_editCopyId = editCopyId;
	}

	/**
	 * Sets the sourceEvaluationId.
	 * @param sourceEvaluationId The sourceEvaluationId to set
	 */
	public void setSourceEvaluationId(String sourceEvaluationId) {
		_sourceEvaluationId = sourceEvaluationId;
	}

	/**
	 * Returns the isEditCopy.
	 * @return String
	 */
	public String getIsEditCopy() {
		return _isEditCopy;
	}

	/**
	 * Sets the isEditCopy.
	 * @param isEditCopy The isEditCopy to set
	 */
	public void setIsEditCopy(String isEditCopy) {
		_isEditCopy = isEditCopy;
	}

	/**
	 * Returns the caseDxName.
	 * @return String
	 */
	public String getCaseDxName() {
		return _caseDxName;
	}

	/**
	 * Sets the caseDxName.
	 * @param caseDxName The caseDxName to set
	 */
	public void setCaseDxName(String caseDxName) {
		_caseDxName = caseDxName;
	}

	/**
	 * Returns the reportedDxName.
	 * @return String
	 */
	public String getReportedDxName() {
		return _reportedDxName;
	}

	/**
	 * Returns the reportedTissueOfFindingName.
	 * @return String
	 */
	public String getReportedTissueOfFindingName() {
		return _reportedTissueOfFindingName;
	}

	/**
	 * Returns the reportedTissueOfOriginName.
	 * @return String
	 */
	public String getReportedTissueOfOriginName() {
		return _reportedTissueOfOriginName;
	}

	/**
	 * Sets the reportedDxName.
	 * @param reportedDxName The reportedDxName to set
	 */
	public void setReportedDxName(String reportedDxName) {
		_reportedDxName = reportedDxName;
	}

	/**
	 * Sets the reportedTissueOfFindingName.
	 * @param reportedTissueOfFindingName The reportedTissueOfFindingName to set
	 */
	public void setReportedTissueOfFindingName(String reportedTissueOfFindingName) {
		_reportedTissueOfFindingName = reportedTissueOfFindingName;
	}

	/**
	 * Sets the reportedTissueOfOriginName.
	 * @param reportedTissueOfOriginName The reportedTissueOfOriginName to set
	 */
	public void setReportedTissueOfOriginName(String reportedTissueOfOriginName) {
		_reportedTissueOfOriginName = reportedTissueOfOriginName;
	}

	/**
	 * Returns the dIPathReportPrimaryDxName.
	 * @return String
	 */
	public String getDIPathReportPrimaryDxName() {
		return _dIPathReportPrimaryDxName;
	}

	/**
	 * Returns the primaryTissueOfFindingName.
	 * @return String
	 */
	public String getPrimaryTissueOfFindingName() {
		return _primaryTissueOfFindingName;
	}

	/**
	 * Returns the primaryTissueOfOriginName.
	 * @return String
	 */
	public String getPrimaryTissueOfOriginName() {
		return _primaryTissueOfOriginName;
	}

	/**
	 * Sets the dIPathReportPrimaryDxName.
	 * @param dIPathReportPrimaryDxName The dIPathReportPrimaryDxName to set
	 */
	public void setDIPathReportPrimaryDxName(String dIPathReportPrimaryDxName) {
		_dIPathReportPrimaryDxName = dIPathReportPrimaryDxName;
	}

	/**
	 * Sets the primaryTissueOfFindingName.
	 * @param primaryTissueOfFindingName The primaryTissueOfFindingName to set
	 */
	public void setPrimaryTissueOfFindingName(String primaryTissueOfFindingName) {
		_primaryTissueOfFindingName = primaryTissueOfFindingName;
	}

	/**
	 * Sets the primaryTissueOfOriginName.
	 * @param primaryTissueOfOriginName The primaryTissueOfOriginName to set
	 */
	public void setPrimaryTissueOfOriginName(String primaryTissueOfOriginName) {
		_primaryTissueOfOriginName = primaryTissueOfOriginName;
	}

	/**
	 * Returns the discordant.
	 * @return String
	 */
	public String getDiscordant() {
		return _discordant;
	}

	/**
	 * Sets the discordant.
	 * @param discordant The discordant to set
	 */
	public void setDiscordant(String discordant) {
		_discordant = discordant;
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
	 * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(BTXDetails)
	 */
	public void populateFromBtxDetails(BTXDetails btxDetails) {
		super.populateFromBtxDetails(btxDetails);
		
		if(!btxDetails.isTransactionCompleted())
			return;
		
		if(btxDetails instanceof BTXDetailsGetSamplePathologyInfo){
		
		
		
		BTXDetailsGetSamplePathologyInfo btx = (BTXDetailsGetSamplePathologyInfo) btxDetails;
			
				setMessage(btx.getMessage());
		
				setTransactionHistoryList(btx.getBtxHistoryRecords());
				
				setSampleId(btx.getSampleId());
				setSlideId(btx.getSlideId());
				setAsmPosition(btx.
				    getPathologyEvaluationSampleData().
				    getAsmPosition());
				setConsentId(btx.
				    getPathologyEvaluationSampleData().
				    getCaseId()); 
				setGrossAppearance(btx.
				    getPathologyEvaluationSampleData().
				    getGrossAppearance());				    
				setSampleType(btx.
				    getPathologyEvaluationSampleData().
				    getSampleType());
				setMicroAppearance(btx.
				    getPathologyEvaluationSampleData().
				    getMicroscopicAppearance()); 
				setDonorId(btx.
				    getPathologyEvaluationSampleData().
				    getDonorId());
				setPathReportId(btx.
				    getPathologyEvaluationSampleData().
				    getPathReportId());
				String caseDxName = btx.
				                    getPathologyEvaluationSampleData().
				                    getCaseDxName();				                	    
				//if the caseDx is other,prepend "Other Diagnosis:" to it
				if (FormLogic.OTHER_DX.equals(btx.
				                              getPathologyEvaluationSampleData().
				                              getCaseDx())) {
					caseDxName = LimsConstants.LIMS_OTHER_DIAGNOSIS_PREFIX + caseDxName;
				} 
				setCaseDxName(caseDxName);   
				    
				setInvStatus(btx.
				    getPathologyEvaluationSampleData().
				    getInventoryStatus ());       
				setPvStatus(btx.
				    getPathologyEvaluationSampleData().
				    getIltdsQcStatus());    
				setReleased(btx.
				    getPathologyEvaluationSampleData().
				    getReleased());    
				setPulled(btx.
				    getPathologyEvaluationSampleData().
				    getPulled());    
				setReason(btx.
				    getPathologyEvaluationSampleData().
				    getPullOrDiscordantReason());
				setDiscordant(btx.
				    getPathologyEvaluationSampleData().
				    getDiscordant());    
				    
				
				String reportedDxName =  btx.
				                         getPathologyEvaluationSampleData().
				                         getReportedEvaluationDxName();   
				//if the reportedDx is other,prepend "Other Diagnosis:" to it        
				if (FormLogic.OTHER_DX.equals(btx.
				                              getPathologyEvaluationSampleData().
				                              getReportedEvaluationDx())) {
					reportedDxName = LimsConstants.LIMS_OTHER_DIAGNOSIS_PREFIX + 
					                 reportedDxName;
				}
				setReportedDxName(reportedDxName);
				
				 
				String reportedTissueOfFindingName =  btx.
				                                      getPathologyEvaluationSampleData().
				                                      getReportedEvaluationTissueOfFindingName();
				//if the reportedTissueOfFinding is other,prepend "Other Tissue:" to it 
				if (FormLogic.OTHER_TISSUE.equals(btx.
				                                  getPathologyEvaluationSampleData().
				                                  getReportedEvaluationTissueOfFinding())) {
				 reportedTissueOfFindingName =  LimsConstants.LIMS_OTHER_TISSUE_PREFIX + 
				                                reportedTissueOfFindingName;                                 	
				}                                   
				setReportedTissueOfFindingName(reportedTissueOfFindingName);
				
				String reportedTissueOfOriginName = btx.
				                                    getPathologyEvaluationSampleData().
				                                    getReportedEvaluationTissueOfOriginName();
				//if the reportedTissueOfFinding is other,prepend "Other Tissue:" to it 
				if (FormLogic.OTHER_TISSUE.equals(btx.
				                                  getPathologyEvaluationSampleData().
				                                  getReportedEvaluationTissueOfOrigin())) {
				     reportedTissueOfOriginName =  LimsConstants.LIMS_OTHER_TISSUE_PREFIX + 
				                                   reportedTissueOfOriginName;                                 	
				}                                    
				setReportedTissueOfOriginName(reportedTissueOfOriginName);
				
				
				String dIPathReportPrimaryDxName = btx.
				                                   getPathologyEvaluationSampleData().
				                                   getDIPathReportPrimaryDxName();
				//if the dIPathReportPrimaryDx is other,prepend "Other Diagnosis:" to it 
				if (FormLogic.OTHER_DX.equals(btx.
				                              getPathologyEvaluationSampleData().
				                              getDIPathReportPrimaryDx())) {
				   dIPathReportPrimaryDxName = LimsConstants.LIMS_OTHER_DIAGNOSIS_PREFIX + 
				                               dIPathReportPrimaryDxName;                           	
				}                                    
				setDIPathReportPrimaryDxName(dIPathReportPrimaryDxName);
				
				
				String primaryTissueOfFindingName = btx.
				                                    getPathologyEvaluationSampleData().
				                                    getCaseTissueOfFindingName();
				//if the primaryTissueOfFinding is other,prepend "Other Tissue:" to it
				if (FormLogic.OTHER_TISSUE.equals(btx.
				                                  getPathologyEvaluationSampleData().
				                                  getCaseTissueOfFinding())) {
				   primaryTissueOfFindingName = LimsConstants.LIMS_OTHER_TISSUE_PREFIX + 
				                                primaryTissueOfFindingName; 
				}                                    
				setPrimaryTissueOfFindingName(primaryTissueOfFindingName);
				
				String primaryTissueOfOriginName = btx.
				                                   getPathologyEvaluationSampleData().
				                                   getCaseTissueOfOriginName();
				//if the primaryTissueOfOrigin is other,prepend "Other Tissue:" to it
				if (FormLogic.OTHER_TISSUE.equals(btx.
				                                  getPathologyEvaluationSampleData().
				                                  getCaseTissueOfOrigin())) {
				  primaryTissueOfOriginName = LimsConstants.LIMS_OTHER_TISSUE_PREFIX + 
				                              primaryTissueOfOriginName;                                	
				}                                  
				setPrimaryTissueOfOriginName(primaryTissueOfOriginName);
				
				    
				        
				setSlides(btx.
				    getPathologyEvaluationSampleData().
				    getSlides());
				setPathologyEvaluations(btx.
				 	getPathologyEvaluations());	
		}
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
	
	private BTXDetails addBtxActionsErrors(BTXDetails target, BTXDetails source) {
		Iterator properties = source.getActionErrors().properties();
        
        while (properties.hasNext()) {
            String property = (String) properties.next();
            Iterator errorsForProperty = source.getActionErrors().get(property);
            while (errorsForProperty.hasNext()) {
                BtxActionError btxError =
                    (BtxActionError) errorsForProperty.next();
                target.addActionError(btxError);
            }
        }
        
        return target;	
	}


	/**
	 * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(BTXDetails, BigrActionMapping, HttpServletRequest)
	 */
	public void describeIntoBtxDetails(
		BTXDetails btxDetails0,
		BigrActionMapping mapping,
		HttpServletRequest request) {
		super.describeIntoBtxDetails(btxDetails0, mapping, request);
		
		BTXDetails resultDetail = (BTXDetails) request.getAttribute("btxDetails");
		if(resultDetail != null) {
			btxDetails0 = (BTXDetailsGetSamplePathologyInfo) addBtxActionsErrors(btxDetails0, resultDetail); 
		} 
			
		
		if(btxDetails0 instanceof BTXDetailsCreatePathologyEvaluationPrepare) {
			BTXDetailsCreatePathologyEvaluationPrepare btx = 
					(BTXDetailsCreatePathologyEvaluationPrepare) btxDetails0;
			btx.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
        	//check for Edit/copy
        	if (!ApiFunctions.isEmpty(getEditCopyButton())) {
        		btx.setSlideId(getEditCopyId());
        		btx.setSourceEvaluationId(getSourceEvaluationId());
        	}else {
        		//Add evaluation
        		btx.setSlideId(getSlideId());
        		btx.setSourceEvaluationId(null);
        	}
        	btx.setBeginTimestamp(new java.sql.Timestamp(new java.util.Date().getTime()));
            
            if (!ApiFunctions.isEmpty(getPopup())) {
                request.setAttribute("popup", "true");
            }
		}
	}

	/**
	 * Returns the editCopyButton.
	 * @return String
	 */
	public String getEditCopyButton() {
		return _editCopyButton;
	}

	/**
	 * Sets the editCopyButton.
	 * @param editCopyButton The editCopyButton to set
	 */
	public void setEditCopyButton(String editCopyButton) {
		_editCopyButton = editCopyButton;
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

}
