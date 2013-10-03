package com.ardais.bigr.lims.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.util.gen.DbAliases;

/**
 * Represents the raw data of a slide.
 */
public class SlideData implements Serializable {
	private static final long serialVersionUID = -3403104854019933381L;

	private String _slideId;
	private String _sampleId;
	private String _location;
	private String _procedure;
	private int _cutLevel;
	private int _slideNumber;
	private boolean _new;
	private boolean _destroyed;
	private String _userId;
	private Date _createDate;
	private Date _destroyDate;
	private String _donorInstitutionName;
	private List _images;
	private String _sampleASMPosition;
	private String _caseId;
	private String _sampleTypeCid;
  private String _bmsYN;

	/**
	 * Creates a new <code>SlideData</code>.
	 */
	public SlideData() {
	}
	
	/**
	 * Creates a new <code>SlideData</code>, initialized from
	 * the data in the current row of the result set.
	 *
	 * @param  columns  a <code>Map</code> containing a key for each column in
	 * 									 the <code>ResultSet</code>.  Each key must be one of the
	 * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
	 * 									 and the corresponding value is ignored.
	 * @param  rs  the <code>ResultSet</code>
	 */
	public SlideData(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
	}
	
	/**
	 * Populates this <code>SlideData</code> from the data in the current row of 
	 * the result set.
	 * 
	 * @param  columns  a <code>Map</code> containing a key for each column in
	 * 									 the <code>ResultSet</code>.  Each key must be one of the
	 * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
	 * 									 and the corresponding value is ignored.
	 * @param  rs  the <code>ResultSet</code>
	 */
	public void populate(Map columns, ResultSet rs) {
	    try {
		    if (columns.containsKey(DbAliases.SLIDE_ID)) {
		    	setSlideId(rs.getString(DbAliases.SLIDE_ID));
		    }
		    if (columns.containsKey(DbAliases.CREATE_DATE)) {
		    	setCreateDate(rs.getDate(DbAliases.CREATE_DATE));
		    }
		    if (columns.containsKey(DbAliases.DESTROY_DATE)) {
		    	setDestroyDate(rs.getDate(DbAliases.DESTROY_DATE));
		    }
		    if (columns.containsKey(DbAliases.SLIDE_SECTION_NUMBER)) {
		    	if (rs.getString(DbAliases.SLIDE_SECTION_NUMBER) != null) {
			    	setSlideNumber(Integer.parseInt(rs.getString(DbAliases.SLIDE_SECTION_NUMBER)));
		    	}
		    }
		    if (columns.containsKey(DbAliases.SLIDE_SECTION_LEVEL)) {
		    	if (rs.getString(DbAliases.SLIDE_SECTION_LEVEL) != null) {
			    	setCutLevel(Integer.parseInt(rs.getString(DbAliases.SLIDE_SECTION_LEVEL)));
		    	}
		    }
		    if (columns.containsKey(DbAliases.SLIDE_CURRENT_LOCATION)) {
		    	setLocation(rs.getString(DbAliases.SLIDE_CURRENT_LOCATION));
		    }
		    if (columns.containsKey(DbAliases.SLIDE_SAMPLE_ID)) {
		    	setSampleId(rs.getString(DbAliases.SLIDE_SAMPLE_ID));
		    }
		    if (columns.containsKey(DbAliases.CREATE_USER)) {
		    	setUserId(rs.getString(DbAliases.CREATE_USER));
		    }
		    if (columns.containsKey(DbAliases.SLIDE_SECTION_PROCEDURE)) {
		    	setProcedure(rs.getString(DbAliases.SLIDE_SECTION_PROCEDURE));
		    }
        if (columns.containsKey(DbAliases.SAMPLE_BMS_YN)) {
          setBmsYN(rs.getString(DbAliases.SAMPLE_BMS_YN));
        }
	    } 
	    catch (SQLException e) {
	        ApiFunctions.throwAsRuntimeException(e);
	    }
	}
	
	/**
	 * Returns the caseId.
	 * @return String
	 */
	public String getCaseId() {
		return _caseId;
	}

	/**
	 * Returns the createDate.
	 * @return Date
	 */
	public Date getCreateDate() {
		return _createDate;
	}

	/**
	 * Returns the cutLevel.
	 * @return int
	 */
	public int getCutLevel() {
		return _cutLevel;
	}

	/**
	 * Returns the destroyDate.
	 * @return Date
	 */
	public Date getDestroyDate() {
		return _destroyDate;
	}

	/**
	 * Returns the donorInstitutionName.
	 * @return String
	 */
	public String getDonorInstitutionName() {
		return _donorInstitutionName;
	}

	/**
	 * Returns a List of ImageData java beans.
	 * @return List
	 */
	public List getImages() {
		return _images;
	}

	/**
	 * Returns the new.
	 * @return boolean
	 */
	public boolean isNew() {
		return _new;
	}

	/**
	 * Returns the destroyed.
	 * @return boolean
	 */
	public boolean isDestroyed() {
		return _destroyed;
	}

	/**
	 * Returns the location.
	 * @return String
	 */
	public String getLocation() {
		return _location;
	}

	/**
	 * Returns the procedure.
	 * @return String
	 */
	public String getProcedure() {
		return _procedure;
	}

	/**
	 * Returns the sampleASMPosition.
	 * @return String
	 */
	public String getSampleASMPosition() {
		return _sampleASMPosition;
	}

	/**
	 * Returns the sampleId.
	 * @return String
	 */
	public String getSampleId() {
		return _sampleId;
	}

	/**
	 * Returns the sampleTypeCid.
	 * @return String
	 */
	public String getSampleTypeCid() {
		return _sampleTypeCid;
	}

	/**
	 * Returns the slideId.
	 * @return String
	 */
	public String getSlideId() {
		return _slideId;
	}

	/**
	 * Returns the slideNumber.
	 * @return int
	 */
	public int getSlideNumber() {
		return _slideNumber;
	}

	/**
	 * Returns the userId.
	 * @return String
	 */
	public String getUserId() {
		return _userId;
	}

	/**
	 * Sets the caseId.
	 * @param caseId The caseId to set
	 */
	public void setCaseId(String caseId) {
		_caseId = caseId;
	}

	/**
	 * Sets the createDate.
	 * @param createDate The createDate to set
	 */
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	/**
	 * Sets the cutLevel.
	 * @param cutLevel The cutLevel to set
	 */
	public void setCutLevel(int cutLevel) {
		_cutLevel = cutLevel;
	}

	/**
	 * Sets the destroyDate.
	 * @param destroyDate The destroyDate to set
	 */
	public void setDestroyDate(Date destroyDate) {
		_destroyDate = destroyDate;
	}

	/**
	 * Sets the donorInstitutionName.
	 * @param donorInstitutionName The donorInstitutionName to set
	 */
	public void setDonorInstitutionName(String donorInstitutionName) {
		_donorInstitutionName = donorInstitutionName;
	}

	/**
	 * Sets the images.
	 * @param images A List of ImageData java beans
	 */
	public void setImages(List images) {
		_images = images;
	}

	/**
	 * Sets the new.
	 * @param New The New to set
	 */
	public void setNew(boolean newValue) {
		_new = newValue;
	}

	/**
	 * Sets the destroyed.
	 * @param destroyed The destroyed to set
	 */
	public void setDestroyed(boolean destroyed) {
		_destroyed = destroyed;
	}

	/**
	 * Sets the location.
	 * @param location The location to set
	 */
	public void setLocation(String location) {
		_location = location;
	}

	/**
	 * Sets the procedure.
	 * @param procedure The procedure to set
	 */
	public void setProcedure(String procedure) {
		_procedure = procedure;
	}

	/**
	 * Sets the sampleASMPosition.
	 * @param sampleASMPosition The sampleASMPosition to set
	 */
	public void setSampleASMPosition(String sampleASMPosition) {
		_sampleASMPosition = sampleASMPosition;
	}

	/**
	 * Sets the sampleId.
	 * @param sampleId The sampleId to set
	 */
	public void setSampleId(String sampleId) {
		_sampleId = sampleId;
	}

	/**
	 * Sets the sampleTypeCid.
	 * @param sampleTypeCid The sampleTypeCid to set
	 */
	public void setSampleTypeCid(String sampleTypeCid) {
    _sampleTypeCid = sampleTypeCid;
	}

	/**
	 * Sets the slideId.
	 * @param slideId The slideId to set
	 */
	public void setSlideId(String slideId) {
		_slideId = slideId;
	}

	/**
	 * Sets the slideNumber.
	 * @param slideNumber The slideNumber to set
	 */
	public void setSlideNumber(int slideNumber) {
		_slideNumber = slideNumber;
	}

	/**
	 * Sets the userId.
	 * @param userId The userId to set
	 */
	public void setUserId(String userId) {
		_userId = userId;
	}

  /**
   * @return
   */
  public String getBmsYN() {
    return _bmsYN;
  }

  /**
   * @param string
   */
  public void setBmsYN(String string) {
    _bmsYN = string;
  }

}
