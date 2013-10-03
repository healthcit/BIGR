package com.ardais.bigr.lims.web.form;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ValidateIds;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxActionError;
import com.ardais.bigr.iltds.btx.BtxActionMessage;
import com.ardais.bigr.lims.btx.BTXDetailsCreatePathologyEvaluation;
import com.ardais.bigr.lims.btx.BTXDetailsGetSamplePathologyInfo;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleDiscordant;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSamplePulled;
import com.ardais.bigr.lims.btx.BTXDetailsMarkSampleUnpulled;
import com.ardais.bigr.lims.btx.BTXDetailsReportPathologyEvaluation;
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
public class LimsSlideQueryForm extends BigrActionForm {

	private String _id;
	private String _sampleId;
	private String _slideId;
	private String _reason;
  private String _reportMostRecentEval;
	private String _sourceEvaluationId;
	private String _isReportEvaluation;
	private String _isUnPull;
	private String _isPull;

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(BigrActionMapping, HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
  }

  /**
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(BigrActionMapping, HttpServletRequest)
   */
  public ActionErrors doValidate(
    BigrActionMapping mapping,
    HttpServletRequest request) {
        
    ActionErrors errors = new ActionErrors();
    
    if (mapping.getPath().equals("/lims/limsSlideQueryStart")) {
      if (ApiFunctions.isEmpty(getId()) &&
          ApiFunctions.isEmpty(getSampleId()) &&
          ApiFunctions.isEmpty(getSlideId())) {
        errors.add("user", new ActionError("lims.error.invalidSlideOrSample", getId()));
        setId(null);  
      }
      else if (!ApiFunctions.isEmpty(getId()) && !ValidateIds.isValid(getId(), ValidateIds.TYPESET_SAMPLE_AND_SLIDE, false)) {
        errors.add("user", new ActionError("lims.error.invalidSlideOrSample", getId()));
        setId(null);  
      }
      else if (ApiFunctions.isEmpty(getId()) &&
               ApiFunctions.isEmpty(getSampleId()) &&
               ApiFunctions.isEmpty(getSlideId())) {
        errors.add("user", new ActionError("lims.error.invalidSlideOrSample", getId()));
        setId(null);  
      }
    }	
    return errors;
  }

	/**
	 * Returns the slideId.
	 * @return String
	 */
	public String getId() {
		if (!ApiFunctions.isEmpty(_id)) {
			return _id.trim();
		}else {		
			return _id;
		}
	}

	/**
	 * Sets the slideId.
	 * @param slideId The slideId to set
	 */
	public void setId(String Id) {
		_id = Id;
	}

	/**
	 * Returns the pullReason.
	 * @return String
	 */
	public String getReason() {
		return _reason;
	}

	/**
	 * Returns the sampleId.
	 * @return String
	 */
	public String getSampleId() {
		return _sampleId;
	}

	/**
	 * Returns the sourceEvaluationId.
	 * @return String
	 */
	public String getSourceEvaluationId() {
		return _sourceEvaluationId;
	}

	/**
	 * Sets the pullReason.
	 * @param pullReason The pullReason to set
	 */
	public void setReason(String reason) {
		_reason = reason;
	}

	/**
	 * Sets the sampleId.
	 * @param sampleId The sampleId to set
	 */
	public void setSampleId(String sampleId) {
		_sampleId = sampleId;
	}

	/**
	 * Sets the sourceEvaluationId.
	 * @param sourceEvaluationId The sourceEvaluationId to set
	 */
	public void setSourceEvaluationId(String sourceEvaluationId) {
		_sourceEvaluationId = sourceEvaluationId;
	}

	/**
	 * Returns the reportEvaluation.
	 * @return String
	 */
	public String getIsReportEvaluation() {
		return _isReportEvaluation;
	}

	/**
	 * Sets the reportEvaluation.
	 * @param reportEvaluation The reportEvaluation to set
	 */
	public void setIsReportEvaluation(String reportEvaluation) {
		_isReportEvaluation = reportEvaluation;
	}	

	/**
	 * Returns the isPull.
	 * @return String
	 */
	public String getIsPull() {
		return _isPull;
	}

	/**
	 * Returns the isUnPull.
	 * @return String
	 */
	public String getIsUnPull() {
		return _isUnPull;
	}

	/**
	 * Sets the isPull.
	 * @param isPull The isPull to set
	 */
	public void setIsPull(String isPull) {
		_isPull = isPull;
	}

	/**
	 * Sets the isUnPull.
	 * @param isUnPull The isUnPull to set
	 */
	public void setIsUnPull(String isUnPull) {
		_isUnPull = isUnPull;
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
		_slideId = slideId;
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
  
  private void addBtxActionMessages(BTXDetails target, BTXDetails source) {
    Iterator iterator = source.getActionMessages().get();
    while (iterator.hasNext()) {
      BtxActionMessage message = (BtxActionMessage) iterator.next();
      target.addActionMessage(message);
    }
  }

	/**
	 * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(BTXDetails, BigrActionMapping, HttpServletRequest)
	 */
	public void describeIntoBtxDetails(
		BTXDetails btxDetails0,
		BigrActionMapping mapping,
		HttpServletRequest request) {
		super.describeIntoBtxDetails(btxDetails0, mapping, request);
		
		//handle the page in the popup from Path QC
        String popup = (String) request.getAttribute("popup");
        if(!ApiFunctions.isEmpty(popup)) {
        	request.setAttribute("popup", popup);	
        } else {
        	popup = request.getParameter("popup");
        	if(!ApiFunctions.isEmpty(popup)) {
        		request.setAttribute("popup", popup);	
        	}	
        }
		
		if(btxDetails0 instanceof BTXDetailsGetSamplePathologyInfo) {
			
		
			BTXDetailsGetSamplePathologyInfo btx = (BTXDetailsGetSamplePathologyInfo) btxDetails0;
			
			BTXDetails resultDetail = (BTXDetails) request.getAttribute("btxDetails");
			if(resultDetail != null) {
				btx = (BTXDetailsGetSamplePathologyInfo) addBtxActionsErrors(btx, resultDetail); 
        addBtxActionMessages(btx, resultDetail); 
			} 
			
			btx.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
			//get id for retrieving information.				
			String id = getId();
			
			if(!ApiFunctions.isEmpty(getId())){
				setSampleId(null);
				setSlideId(null);	
				btx.setSlideId(null);
				btx.setSampleId(null);
				
			}
			if ((id == null) || (ApiFunctions.isEmpty(id))) {
				//Get slide ID from form.
				if (!ApiFunctions.isEmpty(getSlideId())) {
					id = getSlideId();
				}
				//If ID is still empty get ID from form.
				else if (ApiFunctions.isEmpty(id)) {
				    id = getSampleId();
				}
			}
			//Check for sample
      if (ValidateIds.isValid(id, ValidateIds.TYPESET_SAMPLE, false)) {
        btx.setSampleId(id);
      }

			//Check for slide	
      if (ValidateIds.isValid(id, ValidateIds.TYPESET_SLIDE, false)) {
        btx.setSlideId(id);
      }
      
			btx.setBeginTimestamp(
                    new java.sql.Timestamp(new java.util.Date().getTime()));
			
		} else if(btxDetails0 instanceof BTXDetailsMarkSamplePulled) {
			BTXDetailsMarkSamplePulled btx = (BTXDetailsMarkSamplePulled) btxDetails0;
			btx.setReason(getReason());
			btx.setSampleId(getSampleId());
			btx.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
				
			//add btx details
				
		} else if(btxDetails0 instanceof BTXDetailsMarkSampleDiscordant) {
			BTXDetailsMarkSampleDiscordant btx = (BTXDetailsMarkSampleDiscordant) btxDetails0;
			btx.setReason(getReason());
			btx.setSampleId(getSampleId());
			btx.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
				
		} else if(btxDetails0 instanceof BTXDetailsMarkSampleUnpulled) {
			BTXDetailsMarkSampleUnpulled btx = (BTXDetailsMarkSampleUnpulled) btxDetails0;
			btx.setLoggedInUserSecurityInfo(WebUtils.getSecurityInfo(request));
			btx.setSampleId(getSampleId());
		    btx.setReason(getReason());	
		} else if(btxDetails0 instanceof BTXDetailsReportPathologyEvaluation) {
			BTXDetailsReportPathologyEvaluation btx = (BTXDetailsReportPathologyEvaluation) btxDetails0;
			BTXDetailsCreatePathologyEvaluation oldBtx = 
				(BTXDetailsCreatePathologyEvaluation) request.getAttribute("btxDetails");			
			if(oldBtx != null){
				btx.setPathologyEvaluationId(oldBtx.getPathologyEvaluationData().getEvaluationId());
			} else {
				btx.setPathologyEvaluationId(getSourceEvaluationId());
			}
			
			BTXDetails resultDetail = (BTXDetails) request.getAttribute("btxDetails");
			if(resultDetail != null) {
				btx = (BTXDetailsReportPathologyEvaluation) addBtxActionsErrors(btx, resultDetail); 
			} 
		}
	}

	/**
	 * @see com.ardais.bigr.iltds.btx.PopulatableFromBtxDetails#populateFromBtxDetails(BTXDetails)
	 */
	public void populateFromBtxDetails(BTXDetails btxDetails) {
		
		super.populateFromBtxDetails(btxDetails);
		
		if(btxDetails instanceof BTXDetailsCreatePathologyEvaluation){
			BTXDetailsCreatePathologyEvaluation btx = 
					(BTXDetailsCreatePathologyEvaluation) btxDetails;
					
			setSampleId(btx.getPathologyEvaluationData().getSampleId());
			setSlideId(btx.getPathologyEvaluationData().getSlideId());	
			setSourceEvaluationId(btx.getPathologyEvaluationData().getEvaluationId());
		}
	}

  /**
   * @return
   */
  public String getReportMostRecentEval() {
    return _reportMostRecentEval;
  }

  /**
   * @param string
   */
  public void setReportMostRecentEval(String string) {
    _reportMostRecentEval = string;
  }

}
