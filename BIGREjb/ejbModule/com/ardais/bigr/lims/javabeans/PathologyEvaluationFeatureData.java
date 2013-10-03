package com.ardais.bigr.lims.javabeans;

import java.io.Serializable;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.lims.helpers.LimsConstants;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * Represents the raw data of a pathology evaluation feature.
 */
public class PathologyEvaluationFeatureData implements Serializable {
	private static final long serialVersionUID = 5252877083125363716L;
	private String _code;
	private String _otherText;
	private int _value;
	private String _compositionType;
	private String _peId;
	private String _id;
	private int _displayOrder = LimsConstants.LIMS_DEFAULT_FEATURE_DISPLAY_ORDER;

	/**
	 * Creates a new <code>PathologyEvaluationFeatureData</code>.
	 */
	public PathologyEvaluationFeatureData() {
	}

  /**
   * Creates a new <code>PathologyEvaluationFeatureData</code>, initialized
   * with the data contained in the PathologyEvaluationFeatureData passed in.
   *
   * @param  pathologyEvaluationFeatureData  the <code>PathologyEvaluationFeatureData</code>
   */
  public PathologyEvaluationFeatureData(PathologyEvaluationFeatureData pathologyEvaluationFeatureData) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, pathologyEvaluationFeatureData);
  }

	/**
	 * Returns the code.
	 * @return String
	 */
	public String getCode() {
		return _code;
	}

	/**
	 * Returns the otherText.
	 * @return String
	 */
	public String getOtherText() {
		return _otherText;
	}

	/**
	 * Returns the value.
	 * @return int
	 */
	public int getValue() {
		return _value;
	}

	/**
	 * Returns the compositionType.
	 * @return String
	 */
	public String getCompositionType() {
		return _compositionType;
	}

	/**
	 * Returns the id.
	 * @return String
	 */
	public String getId() {
		return _id;
	}

	/**
	 * Returns the peId.
	 * @return String
	 */
	public String getPeId() {
		return _peId;
	}

	/**
	 * Returns the displayOrder.
	 * @return int
	 */
	public int getDisplayOrder() {
		return _displayOrder;
	}

	/**
	 * Sets the code.
	 * @param code The code to set
	 */
	public void setCode(String code) {
		_code = code;
	}

	/**
	 * Sets the otherText.
	 * @param otherText The otherText to set
	 */
	public void setOtherText(String otherText) {
		_otherText = otherText;
	}

	/**
	 * Sets the value.
	 * @param value The value to set
	 */
	public void setValue(int value) {
		_value = value;
	}

	/**
	 * Sets the compositionType.
	 * @param compositionType The compositionType to set
	 */
	public void setCompositionType(String compositionType) {
		_compositionType = compositionType;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(String id) {
		_id = id;
	}

	/**
	 * Sets the peId.
	 * @param peId The peId to set
	 */
	public void setPeId(String peId) {
		_peId = peId;
	}

	/**
	 * Sets the displayOrder.
	 * @param displayOrder The displayOrder to set
	 */
	public void setDisplayOrder(int displayOrder) {
		_displayOrder = displayOrder;
	}
	
	/** translates the number value to text value for inflammations and possibly for other features */
	public String getFeatureValueName() {
		String type = getCompositionType();
		if (type == null || type.equals("")) {
			throw new ApiException("PathologyEvaluationFeatureData.getFeatureName() called with a blank composition type");
		}
		if(type.equals(LimsConstants.FEATURE_TYPE_INFLAMMATION)){
			switch (getValue()){
			
			case LimsConstants.INFLAMMATION_MILD:
				return LimsConstants.INFLAMMATION_MILD_TEXT;
			case LimsConstants.INFLAMMATION_MODERATE:
				return LimsConstants.INFLAMMATION_MODERATE_TEXT;
			case LimsConstants.INFLAMMATION_SEVERE:
				return LimsConstants.INFLAMMATION_SEVERE_TEXT;
			default:
				return "";
			}	
		}
				
		return "";
	}
  
  /** Returns the feature name, with any HTML characters replaced with their
   * entity equivalents **/
  public String getHTMLSafeFeatureName() {
    return Escaper.htmlEscape(getFeatureName());
  }
	
	/** Returns the feature name */
	public String getFeatureName() {
		String type = getCompositionType();
		if (type == null || type.equals("")) {
			throw new ApiException("PathologyEvaluationFeatureData.getFeatureName() called with a blank composition type");
		}
		String code = getCode();
		String text = null;
		//depending on the type of feature this is, figure out what to do
		if (type.equals(LimsConstants.FEATURE_TYPE_CELLULAR)) {
			if (code.equals(LimsConstants.OTHER_CELLULAR)) {
				text = getOtherText();
			}
			else {
				text = GbossFactory.getInstance().getDescription(/*LimsConstants.LIMS_ARTS_TUMOR_CELLULAR,*/ code);
			}
		}
		else if (type.equals(LimsConstants.FEATURE_TYPE_EXTERNAL)) {
			if (code.equals(LimsConstants.OTHER_EXTERNAL)) {
				text = getOtherText();
			}
			else {
				text = GbossFactory.getInstance().getDescription(/*LimsConstants.LIMS_ARTS_EXTERNAL,*/ code);
			}
		}
		else if (type.equals(LimsConstants.FEATURE_TYPE_HYPOACELLULAR)) {
			if (code.equals(LimsConstants.OTHER_HYPOACELLULAR)) {
				text = getOtherText();
			}
			else {
				text = GbossFactory.getInstance().getDescription(/*LimsConstants.LIMS_ARTS_TUMOR_HYPOACELLULAR,*/ code);
			}
		}
		else if (type.equals(LimsConstants.FEATURE_TYPE_INFLAMMATION)) {
			if (code.equals(LimsConstants.OTHER_INFLAMMATION)) {
				text = getOtherText();
			}
			else {
				text = GbossFactory.getInstance().getDescription(/*LimsConstants.LIMS_ARTS_INFLAMMATION,*/ code);
			}
		}
		else if (type.equals(LimsConstants.FEATURE_TYPE_INTERNAL)) {
			if (code.equals(LimsConstants.OTHER_INTERNAL)) {
				text = getOtherText();
			}
			else {
				text = GbossFactory.getInstance().getDescription(/*LimsConstants.LIMS_ARTS_INTERNAL,*/ code);
			}
		}
		else if (type.equals(LimsConstants.FEATURE_TYPE_LESION)) {
			if (code.equals(FormLogic.OTHER_DX)) {
				text = getOtherText();
			}
			else {
				text = BigrGbossData.getDiagnosisDescription(code);
			}
		}
		else if (type.equals(LimsConstants.FEATURE_TYPE_STRUCTURE)) {
			if (code.equals(LimsConstants.OTHER_STRUCTURE)) {
				text = getOtherText(); 
			}
			else {
				text = GbossFactory.getInstance().getDescription(code);
			}
		}
		else if (type.equals(LimsConstants.FEATURE_TYPE_TUMOR)) {
			if (code.equals(LimsConstants.OTHER_TUMOR)) {
				text = getOtherText();
			}
			else {
				text = GbossFactory.getInstance().getDescription(/*LimsConstants.LIMS_ARTS_TUMOR,*/ code);
			}
		}
		else {
			throw new ApiException("PathologyEvaluationFeatureData.getFeatureName() called with an unknown composition type (" + type + ")");
		}
		return ApiFunctions.safeTrim(text);
	}

}
