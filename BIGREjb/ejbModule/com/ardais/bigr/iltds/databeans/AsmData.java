package com.ardais.bigr.iltds.databeans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Insert the type's description here.
 * Creation date: (3/20/2002 10:39:19 AM)
 * @author: Jake Thompson
 */
public class AsmData implements java.io.Serializable {
	private final static long serialVersionUID = 5251682274586382953L;
	private List frozen_samples = new ArrayList();
	private List paraffin_samples = new ArrayList();
  private String donor_id;
  private String case_id;
	private String asm_id;
	private CaseData parent;
	private String asm_form_id;
	private String tissue_type;
	private String other_tissue;
	private String appearance;
	private String module_letter;
	private DateData _timeOfRemoval;
	private DateData _timeOfGrossing;
	private String   _surgical_accuracy;
	private String   _pfin_meets_specs;
	private String   _module_weight;
	private String   _module_comments;
/**
 * AsmBean constructor comment.
 */
public AsmData() {
	super();
}
/**
 * AsmBean constructor comment.
 */
public AsmData(AsmData asmData) {
  this(asmData, true);
}
/**
 * AsmBean constructor comment.
 */
public AsmData(AsmData asmData, boolean copyParent) {
  this();
  BigrBeanUtilsBean.SINGLETON.copyProperties(this, asmData);
  //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
  //does a shallow copy.
  if (!ApiFunctions.isEmpty(asmData.getFrozen_samples())) {
    SampleData newSample = null;
    SampleData originalSample = null;
    frozen_samples.clear();
    Iterator iterator = asmData.getFrozen_samples().iterator();
    while (iterator.hasNext()) {
      //any original samples pointing back to the original asmData need
      //to be handled specially, to prevent an infinite loop of copying.
      originalSample = (SampleData)iterator.next();
      if (asmData.equals(originalSample.getParent())) {
        newSample = new SampleData(originalSample, false);
        newSample.setParent(this);
      }
      else {
        newSample = new SampleData(originalSample);
      }
      frozen_samples.add(newSample);
    }
  }
  if (!ApiFunctions.isEmpty(asmData.getParaffin_samples())) {
    SampleData newSample = null;
    SampleData originalSample = null;
    paraffin_samples.clear();
    Iterator iterator = asmData.getParaffin_samples().iterator();
    while (iterator.hasNext()) {
      //any original samples pointing back to the original asmData need
      //to be handled specially, to prevent an infinite loop of copying.
      originalSample = (SampleData)iterator.next();
      if (asmData.equals(originalSample.getParent())) {
        newSample = new SampleData(originalSample, false);
        newSample.setParent(this);
      }
      else {
        newSample = new SampleData(originalSample);
      }
      paraffin_samples.add(newSample);
    }
  }
  if (copyParent && asmData.getParent() != null) {
    setParent(new CaseData(asmData.getParent()));
  }
  if (asmData.getTimeOfRemoval() != null) {
    setTimeOfRemoval(new DateData(asmData.getTimeOfRemoval()));
  }
  if (asmData.getTimeOfGrossing() != null) {
    setTimeOfGrossing(new DateData(asmData.getTimeOfGrossing()));
  }
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:12:27 AM)
 * @param newFrozen_samples java.util.List
 */
public void addFrozen_sample(SampleData newFrozen_sample) {
	frozen_samples.add(newFrozen_sample);
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:12:27 AM)
 * @param newParaffin_samples java.util.List
 */
public void addParaffin_sample(SampleData newParaffin_sample) {
	paraffin_samples.add(newParaffin_sample);
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 12:08:34 PM)
 * @return java.lang.String
 */
public java.lang.String getAppearance() {
	return appearance;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 12:08:34 PM)
 * @return java.lang.String
 */
public java.lang.String getAsm_form_id() {
	return asm_form_id;
}
/**
 * Insert the method's description here.
 * Creation date: (3/20/2002 10:55:05 AM)
 * @return java.lang.String
 */
public java.lang.String getAsm_id() {
	return asm_id;
}
/**
 * Insert the method's description here.
 * Creation date: (3/21/2002 10:50:13 AM)
 * @return java.lang.String
 */
public java.lang.String getCase_id() {
	return case_id;
}
/**
 * Insert the method's description here.
 * Creation date: (3/21/2002 10:50:13 AM)
 * @return java.lang.String
 */
public java.lang.String getDonor_id() {
	return donor_id;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:12:27 AM)
 * @return java.util.List
 */
public java.util.List getFrozen_samples() {
    if (frozen_samples == null)
        frozen_samples = new ArrayList();
    return frozen_samples;
}
/**
 * Insert the method's description here.
 * Creation date: (12/26/2002 3:30 PM)
 * @return java.lang.String
 */
public java.lang.String getModule_comments() {
	return _module_comments;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 1:30:56 PM)
 * @return java.lang.String
 */
public java.lang.String getModule_letter() {
	return module_letter;
}
/**
 * Insert the method's description here.
 * Creation date: (12/26/2002 3:30 PM)
 * @return java.lang.String
 */
public java.lang.String getModule_weight() {
	return _module_weight;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 12:08:34 PM)
 * @return java.lang.String
 */
public java.lang.String getOther_tissue() {
	return other_tissue;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:12:27 AM)
 * @return java.util.List
 */
public java.util.List getParaffin_samples() {
    if (paraffin_samples == null)
        paraffin_samples = new ArrayList();
    return paraffin_samples;
}
/**
 * Insert the method's description here.
 * Creation date: (3/22/2002 7:25:23 AM)
 * @return com.ardais.bigr.iltds.databeans.CaseData
 */
public CaseData getParent() {
	return parent;
}
/**
 * Insert the method's description here.
 * Creation date: (12/26/2002 3:30 PM)
 * @return java.lang.String
 */
public java.lang.String getPfin_meets_specs() {
	return _pfin_meets_specs;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:12:27 AM)
 * @return java.util.List
 */
public java.util.List getSamples() {
    ArrayList samples = new ArrayList();
    samples.addAll(frozen_samples);
    samples.addAll(paraffin_samples);
    return samples;
}
/**
 * Insert the method's description here.
 * Creation date: (12/26/2002 3:00 PM)
 * @return java.lang.String
 */
public java.lang.String getSurgical_accuracy() {
	return _surgical_accuracy;
}
/**
 * Insert the method's description here.
 * Creation date: (12/26/2002 3:00 PM)
 * @return java.lang.String
 */
public java.lang.String getSurgical_accuracy_display() {
	return GbossFactory.getInstance().getDescription(/*"SURGICAL_REMOVAL_TIME_ACCURACY",*/ _surgical_accuracy);
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 12:08:34 PM)
 * @param newTissue_type java.lang.String
 */
public DateData getTimeOfGrossing() {
    return _timeOfGrossing;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 12:08:34 PM)
 * @param newTissue_type java.lang.String
 */
public String getTimeOfHourDelta() {
    int deltaHour = 0;
    if (getTimeOfGrossing() != null && getTimeOfRemoval() != null) {
        try {
            long start = getTimeOfRemoval().getTimestamp().getTime();
            long end = getTimeOfGrossing().getTimestamp().getTime();
            long delta = end - start;

            deltaHour = (int) (delta / 1000 / 60 / 60);

        } catch (Exception e) {
            return "00";
        }
    }
    return deltaHour + "";
    
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 12:08:34 PM)
 * @param newTissue_type java.lang.String
 */
public String getTimeOfMinDelta() {
    int deltaMin = 0;
    if (getTimeOfGrossing() != null && getTimeOfRemoval() != null) {
        try {
	        long start = getTimeOfRemoval().getTimestamp().getTime();
            long end = getTimeOfGrossing().getTimestamp().getTime();
			long delta = end - start;            

			deltaMin = (int) ((delta/1000/60)%60);
			
        } catch (Exception e) {
            return "00";
        }
    }
    String result = deltaMin + "";
    return ((result.length() == 1) ? "0" + result: result);
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 12:08:34 PM)
 * @param newTissue_type java.lang.String
 */
public DateData getTimeOfRemoval() {
    return _timeOfRemoval;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 12:08:34 PM)
 * @return java.lang.String
 */
public java.lang.String getTissue_type() {
	return tissue_type;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 12:08:34 PM)
 * @return java.lang.String
 */
public java.lang.String getTissue_type_display() {
	return BigrGbossData.getTissueDescription(tissue_type);
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 12:08:34 PM)
 * @param newAppearance java.lang.String
 */
public void setAppearance(java.lang.String newAppearance) {
	appearance = newAppearance;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 12:08:34 PM)
 * @param newAsm_form_id java.lang.String
 */
public void setAsm_form_id(java.lang.String newAsm_form_id) {
	asm_form_id = newAsm_form_id;
}
/**
 * Insert the method's description here.
 * Creation date: (3/20/2002 10:55:05 AM)
 * @param newAsm_id java.lang.String
 */
public void setAsm_id(java.lang.String newAsm_id) {
	asm_id = newAsm_id;
	module_letter = asm_id.substring(asm_id.length() - 1);
}
/**
 * Insert the method's description here.
 * Creation date: (3/21/2002 10:50:13 AM)
 * @param newCase_id java.lang.String
 */
public void setCase_id(java.lang.String newCase_id) {
	case_id = newCase_id;
}
/**
 * Insert the method's description here.
 * Creation date: (3/21/2002 10:50:13 AM)
 * @param newDonor_id java.lang.String
 */
public void setDonor_id(java.lang.String newDonor_id) {
	donor_id = newDonor_id;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:12:27 AM)
 * @param newFrozen_samples java.util.List
 */
public void setFrozen_samples(java.util.List newFrozen_samples) {
	frozen_samples = newFrozen_samples;
}
/**
 * Insert the method's description here.
 * Creation date: (12/26/2002 3:30 PM)
 * @param newModule_comments java.lang.String
 */
public void setModule_comments(java.lang.String newModule_comments) {
	_module_comments = newModule_comments;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 1:30:56 PM)
 * @param newModule_letter java.lang.String
 */
public void setModule_letter(java.lang.String newModule_letter) {
	module_letter = newModule_letter;
}
/**
 * Insert the method's description here.
 * Creation date: (12/26/2002 3:30 PM)
 * @param newModule_weight java.lang.String
 */
public void setModule_weight(java.lang.String newModule_weight) {
	_module_weight = newModule_weight;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 12:08:34 PM)
 * @param newOther_tissue java.lang.String
 */
public void setOther_tissue(java.lang.String newOther_tissue) {
	other_tissue = newOther_tissue;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 11:12:27 AM)
 * @param newParaffin_samples java.util.List
 */
public void setParaffin_samples(java.util.List newParaffin_samples) {
	paraffin_samples = newParaffin_samples;
}
/**
 * Insert the method's description here.
 * Creation date: (3/22/2002 7:25:23 AM)
 * @param newParent com.ardais.bigr.iltds.databeans.CaseData
 */
public void setParent(CaseData newParent) {
	parent = newParent;
}
/**
 * Insert the method's description here.
 * Creation date: (12/26/2002 3:30 PM)
 * @param newPfin_meets_specs java.lang.String
 */
public void setPfin_meets_specs(java.lang.String newPfin_meets_specs) {
	_pfin_meets_specs = newPfin_meets_specs;
}
/**
 * Insert the method's description here.
 * Creation date: (12/26/2002 3:00 PM)
 * @param newSurgical_accuracy java.lang.String
 */
public void setSurgical_accuracy(java.lang.String newSurgical_accuracy) {
	_surgical_accuracy = newSurgical_accuracy;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 12:08:34 PM)
 * @param newTissue_type java.lang.String
 */
public void setTimeOfGrossing(DateData timeOfGrossing) {
	_timeOfGrossing = timeOfGrossing;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 12:08:34 PM)
 * @param newTissue_type java.lang.String
 */
public void setTimeOfRemoval(DateData timeOfRemoval) {
	_timeOfRemoval = timeOfRemoval;
}
/**
 * Insert the method's description here.
 * Creation date: (3/27/2002 12:08:34 PM)
 * @param newTissue_type java.lang.String
 */
public void setTissue_type(java.lang.String newTissue_type) {
	tissue_type = newTissue_type;
}
}
