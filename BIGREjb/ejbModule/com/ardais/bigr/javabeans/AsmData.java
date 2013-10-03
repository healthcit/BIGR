package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffAccessBean;
import com.ardais.bigr.iltds.beans.ArdaisstaffKey;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.orm.helpers.BigrGbossData;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.gen.DbAliases;

/**
 * Represents the raw data of an ASM.
 */
public class AsmData implements Serializable {

	private String _asmId;
	private String _asmFormId;
	private String _grossAppearance;
	private String _tissue;
	private String _tissueOther;
  private String _moduleComments;

  private String _preparedBy;
  private String _preparedByName;
  private String _procedure;
  private String _procedureOther;

	private ConsentData _consentData;

	/**
	 * Creates a new <code>AsmData</code>.
	 */
	public AsmData() {
	}

  /**
   * Creates a new <code>AsmData</code>, initialized from
   * the data in the AsmData passed in.
   *
   * @param  asmData  the <code>AsmData</code>
   */
  public AsmData(AsmData asmData) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, asmData);
    //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
    //does a shallow copy.
    if (asmData.getConsentData() != null) {
      setConsentData(new ConsentData(asmData.getConsentData()));
    }
  }

	/**
	 * Creates a new <code>AsmData</code>, initialized from
	 * the data in the current row of the result set.
	 *
	 * @param  columns  a <code>Map</code> containing a key for each column in
	 * 									 the <code>ResultSet</code>.  Each key must be one of the
	 * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
	 * 									 and the corresponding value is ignored.
	 * @param  rs  the <code>ResultSet</code>
	 */
	public AsmData(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
	}

	/**
	 * Populates this <code>AsmData</code> from the data in the current row
	 * of the result set.
	 * 
	 * @param  columns  a <code>Map</code> containing a key for each column in
	 * 									 the <code>ResultSet</code>.  Each key must be one of the
	 * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
	 * 									 and the corresponding value is ignored.
	 * @param  rs  the <code>ResultSet</code>
	 */
	public void populate(Map columns, ResultSet rs) {
    try {
	    if (columns.containsKey(DbAliases.ASM_APPEARANCE)) {
	    	setGrossAppearance(rs.getString(DbAliases.ASM_APPEARANCE));
	    }
	    if (columns.containsKey(DbAliases.ASM_ID)) {
	    	setAsmId(rs.getString(DbAliases.ASM_ID));
	    }
	    if (columns.containsKey(DbAliases.ASM_FORM_ID)) {
	    	setAsmFormId(rs.getString(DbAliases.ASM_FORM_ID));
	    }
	    if (columns.containsKey(DbAliases.ASM_TISSUE)) {
	    	setTissue(rs.getString(DbAliases.ASM_TISSUE));
	    }
	    if (columns.containsKey(DbAliases.ASM_TISSUE_OTHER)) {
	    	setTissueOther(rs.getString(DbAliases.ASM_TISSUE_OTHER));
	    }
	    if (columns.containsKey(DbAliases.SAMPLE_ASM_ID)) {
	    	setAsmId(rs.getString(DbAliases.SAMPLE_ASM_ID));
	    }
      if (columns.containsKey(DbAliases.ASM_MODULE_COMMENTS)) {
         setModuleComments(rs.getString(DbAliases.ASM_MODULE_COMMENTS)); 
      }
      if (columns.containsKey(DbAliases.ASM_FORM_PREPARED_BY)) {
        setPreparedBy(rs.getString(DbAliases.ASM_FORM_PREPARED_BY));
      }
      if (columns.containsKey(DbAliases.ASM_FORM_PROCEDURE)) {
        setProcedure(rs.getString(DbAliases.ASM_FORM_PROCEDURE));
      }
      if (columns.containsKey(DbAliases.ASM_FORM_PROCEDURE_OTHER)) {
        setProcedureOther(rs.getString(DbAliases.ASM_FORM_PROCEDURE_OTHER));
      }
    } catch (SQLException e) {
			ApiFunctions.throwAsRuntimeException(e);
    }
	}

	/**
	 * Returns the ASM ID.
	 * @return String
	 */
	public String getAsmId() {
		return _asmId;
	}

	/**
	 * Returns the ASM FORM ID.
	 * @return String
	 */
	public String getAsmFormId() {
		return _asmFormId;
	}

	/**
	 * Returns the grossAppearance.
	 * @return String
	 */
	public String getGrossAppearance() {
		return _grossAppearance;
	}

	/**
	 * Returns the tissue.
	 * @return String
	 */
	public String getTissue() {
		return _tissue;
	}

	/**
	 * Returns the other tissue.
	 * @return String
	 */
	public String getTissueOther() {
		return _tissueOther;
	}

	/**
	 * Sets the ASM ID.
	 * @param  asmId  The ASM ID.
	 */
	public void setAsmId(String asmId) {
		_asmId = asmId;
	}

	/**
	 * Sets the ASM FORM ID.
	 * @param  asmFormId  The ASM FORM ID.
	 */
	public void setAsmFormId(String asmFormId) {
		_asmFormId = asmFormId;
	}

	/**
	 * Sets the grossAppearance.
	 * @param grossAppearance The grossAppearance to set
	 */
	public void setGrossAppearance(String grossAppearance) {
		_grossAppearance = grossAppearance;
	}

	/**
	 * Sets the tissue.
	 * @param tissue  The tissue to set
	 */
	public void setTissue(String tissue) {
		_tissue = tissue;
	}

	/**
	 * Sets the other tissue.
	 * @param tissueOther The other tissue.
	 */
	public void setTissueOther(String tissueOther) {
		_tissueOther = tissueOther;
	}

	/**
	 * Returns the consent data bean for the consent associated with this ASM.
	 * 
	 * @return  The ConsentData bean.
	 */
	public ConsentData getConsentData() {
		return _consentData;
	}

	/**
	 * Sets the consent data bean for the consent associated with this ASM.
	 * 
	 * @param  consentData  the ConsentData bean
	 */
	public void setConsentData(ConsentData consentData) {
		_consentData = consentData;
	}
		
	/** Returns the tissue name */
	public String getTissueName() {
		String returnValue = "";
		String code = getTissue();
		if (!ApiFunctions.isEmpty(code)) {
  		if (code.equals(FormLogic.OTHER_TISSUE)) {
  			returnValue = getTissueOther();
  		}
  		else {
  			returnValue = BigrGbossData.getTissueDescription(code);
  		}
    }
		return returnValue;
	}

  /**
   * Returns the moduleComments.
   * @return String
   */
  public String getModuleComments() {
    return _moduleComments;
  }

  /**
   * Sets the moduleComments.
   * @param moduleComments The moduleComments to set
   */
  public void setModuleComments(String moduleComments) {
    _moduleComments = moduleComments;
  }

  /**
   * @return
   */
  public String getPreparedBy() {
    return _preparedBy;
  }

  public String getPreparedByName() {
    if (_preparedByName == null) {
      if (!ApiFunctions.isEmpty(_preparedBy)) {
        try {
          ArdaisstaffAccessBean staff = new ArdaisstaffAccessBean(new ArdaisstaffKey(_preparedBy));
          _preparedByName = (staff.getArdais_staff_fname() + " " + staff.getArdais_staff_lname());
        }
        catch (Exception e) {
          _preparedByName = "";
        }
      }
      else {
        _preparedByName = "";
      }
    }
    return _preparedByName;
  }

  /**
   * @return
   */
  public String getProcedure() {
    return _procedure;
  }

  /**
   * @return
   */
  public String getProcedureOther() {
    return _procedureOther;
  }

  public String getProcedureName() {
    String returnValue = "";
    String code = getProcedure();
    if (!ApiFunctions.isEmpty(code)) {
      if (ArtsConstants.OTHER_PROCEDURE.equals(code)) {
        returnValue = "Other";
        if (!ApiFunctions.isEmpty(getProcedureOther())) {
          returnValue = returnValue + ": " + getProcedureOther();
        }
      }
      else {
        returnValue = BigrGbossData.getProcedureDescription(code);
      }
    }
    return returnValue;
  }

  /**
   * @param string
   */
  public void setPreparedBy(String string) {
    _preparedBy = string;
  }

  /**
   * @param string
   */
  public void setProcedure(String string) {
    _procedure = string;
  }

  /**
   * @param string
   */
  public void setProcedureOther(String string) {
    _procedureOther = string;
  }

}
