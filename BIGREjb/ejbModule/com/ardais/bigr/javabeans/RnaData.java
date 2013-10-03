package com.ardais.bigr.javabeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.es.helpers.FormLogic;
import com.ardais.bigr.iltds.assistants.LogicalRepository;
import com.ardais.bigr.iltds.btx.BTXBoxLocation;
import com.ardais.bigr.security.SecurityInfo;
import com.ardais.bigr.util.ArtsConstants;
import com.ardais.bigr.util.DbUtils;
import com.ardais.bigr.util.gen.DbAliases;
import com.ardais.bigr.util.gen.DbConstants;

/**
 * Represents raw RNA data.
 */
public class RnaData implements Serializable {

  private String _consentId;
  private String _prep;
  private Integer _quality;
  private BigDecimal _ratio;
  private String _rnaVialId;
  private String _rnaStatus;
  private String _representativeSampleId;
  private Integer _rnaAmountAvailable; // available to promise, excluding on hold
  private Integer _rnaAmountRemaining; // amount in repository
  private Map _images = new HashMap(8); // key= image type, value = filename
  private boolean _bms = false;

  private Integer _tumorCells;
  private Integer _lesionCells;
  private Integer _normalCells;
  private Integer _acellularstromaCells;
  private Integer _cellularstromaCells;
  private Integer _necrosisCells;

  private String _externalComments;
  private String _internalComments;
  private String _notes;

  private String _rnaProject;
  private String _rnaHoldProject;
  private String _pooledTissue;
  private String _caseExhausted;
  
  // a link to a bean representing sample data
  private SampleData _representativeSample;
  private List _samples;
  
  private List _logicalRepositories;

  /**
   * Creates a new <code>RnaData</code>.
   */
  public RnaData() {
  }

  /**
   * Creates a new <code>RnaData</code>, initialized from
   * the data in the current row of the result set.
   *
   * @param  columns  a <code>Map</code> containing a key for each column in
   * 									 the <code>ResultSet</code>.  Each key must be one of the
   * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
   * 									 and the corresponding value is ignored.
   * @param  rs  the <code>ResultSet</code>
   */
  public RnaData(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
  }

  /**
   * Populates this <code>RnaData</code> from the data in the current row of 
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
      if (columns.containsKey(DbAliases.RNA_CONSENT_ID)) {
        setConsentId(rs.getString(DbAliases.RNA_CONSENT_ID));
      }
      if (columns.containsKey(DbAliases.RNA_PREP)) {
        setPrep(rs.getString(DbAliases.RNA_PREP));
      }
      if (columns.containsKey(DbAliases.RNA_QUALITY)) {
        setQuality(DbUtils.convertStringToInteger(rs.getString(DbAliases.RNA_QUALITY)));
      }
      if (columns.containsKey(DbAliases.RNA_BIORATIO)) {
        setRatio(rs.getBigDecimal(DbAliases.RNA_BIORATIO));
      }
      if (columns.containsKey(DbAliases.RNA_RNAVIALID)) {
        setRnaVialId(rs.getString(DbAliases.RNA_RNAVIALID));
      }
      if (columns.containsKey(DbAliases.RNA_REMAINING_VOLUME)
        && columns.containsKey(DbAliases.RNA_CONCENTRATION)) {
        int tot = rs.getInt(DbAliases.RNA_REMAINING_VOLUME);
        float concentration = rs.getFloat(DbAliases.RNA_CONCENTRATION);
        int amtRemain = (int) (concentration * tot);
        setRnaAmountRemaining(new Integer(amtRemain));
        if (columns.containsKey(DbConstants.RNA_HOLD_AMOUNT)) {
          int hold = (int) Math.ceil((double)rs.getFloat(DbConstants.RNA_HOLD_AMOUNT));
          setRnaAmountAvailable(new Integer(amtRemain - hold));
        } 
        else {
          setRnaAmountAvailable(new Integer(amtRemain));
        }
      }
      if (columns.containsKey(DbAliases.RNA_STATUS)) {
        setRnaStatus(rs.getString(DbAliases.RNA_STATUS));
      }
      if (columns.containsKey(DbAliases.RNA_REP_SAMPLE)) {
        setRnaRepresentativeSampleId(rs.getString(DbAliases.RNA_REP_SAMPLE));
      }
      if (columns.containsKey(DbAliases.RNA_POOLED_TISSUE)) {
        setPooledTissue(rs.getString(DbAliases.RNA_POOLED_TISSUE));
      }
      if (columns.containsKey(DbAliases.RNA_CASE_EXHAUSTED)) {
        setCaseExhausted(rs.getString(DbAliases.RNA_CASE_EXHAUSTED));
      }
      // There's some potentially bad data in the database where some of the cellular
      // composition values have floating point numbers in them, though we really
      // expect them to be integers.  The code is written to be able to handle that
      // by rounding the floating point number towards zero.  See MR 7901.
      //
      if (columns.containsKey(DbAliases.RNA_PE_TAS)) {
        String s = rs.getString(DbAliases.RNA_PE_TAS);
        if (!ApiFunctions.isEmpty(s)) {
          setAcellularstromaCells(new Integer((int)(Double.parseDouble(s))));
        }
        else {
          setAcellularstromaCells(null);
        }
      }
      if (columns.containsKey(DbAliases.RNA_PE_NEC)) {
        String s = rs.getString(DbAliases.RNA_PE_NEC);
        if (!ApiFunctions.isEmpty(s)) {
          setNecrosisCells(new Integer((int)(Double.parseDouble(s))));
        }
        else {
          setNecrosisCells(null);
        }
      }
      if (columns.containsKey(DbAliases.RNA_PE_NRM)) {
        String s = rs.getString(DbAliases.RNA_PE_NRM);
        if (!ApiFunctions.isEmpty(s)) {
          setNormalCells(new Integer((int)(Double.parseDouble(s))));
        }
        else {
          setNormalCells(null);
        }
      }
      if (columns.containsKey(DbAliases.RNA_PE_TCS)) {
        String s = rs.getString(DbAliases.RNA_PE_TCS);
        if (!ApiFunctions.isEmpty(s)) {
          setCellularstromaCells(new Integer((int)(Double.parseDouble(s))));
        }
        else {
          setCellularstromaCells(null);
        }
      }
      if (columns.containsKey(DbAliases.RNA_PE_LSN)) {
        String s = rs.getString(DbAliases.RNA_PE_LSN);
        if (!ApiFunctions.isEmpty(s)) {
          setLesionCells(new Integer((int)(Double.parseDouble(s))));
        }
        else {
          setLesionCells(null);
        }
      }
      if (columns.containsKey(DbAliases.RNA_PE_TMR)) {
        String s = rs.getString(DbAliases.RNA_PE_TMR);
        if (!ApiFunctions.isEmpty(s)) {
          setTumorCells(new Integer((int)(Double.parseDouble(s))));
        }
        else {
          setTumorCells(null);
        }
      }
      if (columns.containsKey(DbAliases.RNA_EXTERNAL_COMMENTS)) {
        setExternalComments(rs.getString(DbAliases.RNA_EXTERNAL_COMMENTS));
      }
      if (columns.containsKey(DbAliases.RNA_INTERNAL_COMMENTS)) {
        setInternalComments(rs.getString(DbAliases.RNA_INTERNAL_COMMENTS));
      }
      if (columns.containsKey(DbAliases.RNA_NOTES)) {
        setNotes(rs.getString(DbAliases.RNA_NOTES));
      }
      if (columns.containsKey(DbAliases.RNA_BMS_YN)) {
        setBms("Y".equals(rs.getString(DbAliases.RNA_BMS_YN)));
      }

      
    }
    catch (SQLException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

 /**
   * By default, this object does not have image data.
   * Explicitly call this method to load URL's for the images
   * This does not access the image files themselves.
   */
  public void loadImagesFromDB() {

    String rnaId = getRnaVialId();

    final String comma = ", ";
    String[] selectLines =
      new String[] {
        "SELECT ",
        DbConstants.RNA_RNAVIALID,
        " AS ",
        DbAliases.RNA_RNAVIALID,
        comma,
        DbConstants.RNA_IMAGES_IMAGE_FILE_NAME,
        " AS ",
        DbAliases.RNA_IMAGES_IMAGE_FILE_NAME,
        comma,
        DbConstants.RNA_IMAGES_IMAGE_TYPE,
        " AS ",
        DbAliases.RNA_IMAGES_IMAGE_TYPE,
        " FROM ",
        DbConstants.TABLE_RNA_IMAGES,
        " WHERE ",
        DbConstants.RNA_RNAVIALID,
        " = ? " };

    StringBuffer query = new StringBuffer();
    for (int i = 0; i < selectLines.length; i++) {
      query.append(selectLines[i]);
    }

    String sql = query.toString();

    ResultSet rs = null;
    Connection con = null;
    PreparedStatement pstmt;

    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(sql);
      pstmt.setString(1, rnaId);
      rs = ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        String fileType = rs.getString(DbAliases.RNA_IMAGES_IMAGE_TYPE);
        String fileName = rs.getString(DbAliases.RNA_IMAGES_IMAGE_FILE_NAME);
        setImage(fileType, fileName); // corespond to FormLogic.IMAGE_PATH4X_ ... etc.
      }
    }
    catch (SQLException e) {
      ApiLogger.log("Error executing query.  Re-throwing exception.  SQL: " + sql);
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con);
    }

  }

  /**
   * Returns the consent id.
   * 
   * @return  The consent id.
   */
  public String getConsentId() {
    return _consentId;
  }

  /**
   * Returns the case prep number.
   * 
   * @return  The case prep number.
   */
  public String getPrep() {
    return _prep;
  }

  /**
   * Returns the quality.
   * 
   * @return  The quality
   */
  public Integer getQuality() {
    return _quality;
  }

  /**
   * Returns the RNA vial id.
   * 
   * @return  The RNA vial id.
   */
  public String getRnaVialId() {
    return _rnaVialId;
  }

  /**
   * Returns the RNA vial id.
   * 
   * @return  The RNA vial id.
   */
  public String getRepresentativeSampleId() {
    return _representativeSampleId;
  }

  /**
   * Returns the RNA amount available.
   * @return  The RNA amount available.
   */
  public Integer getRnaAmountAvailable() {
    return _rnaAmountAvailable;
  }

  public String getImageUrl(String imageType) {
    if (FormLogic.RNA_IMAGE_PATH4X_TYPE.equals(imageType)) {
      return getPath4xUrl();
    }
    else if (FormLogic.RNA_IMAGE_PATH20X_TYPE.equals(imageType)) {
      return getPath20xUrl();
    }
    else if (FormLogic.RNA_IMAGE_GEL_TYPE.equals(imageType)) {
      return getGelUrl();
    }
    else if (FormLogic.RNA_IMAGE_BIO_TYPE.equals(imageType)) {
      return getBioUrl();
    }
    else if (FormLogic.RNA_IMAGE_RTPCR_TYPE.equals(imageType)) {
      return getRtpcrUrl();
    }
    else {
      throw new ApiException("No such image type for RNA.  code requested=" + imageType);
    }
  }

  /**
   * Returns the name of the image file for Pathology 20x magnification.
   * @return  The file name relative to some base URL.
   */
  public String getPath20xFileName() {
    return (String) _images.get(FormLogic.RNA_IMAGE_PATH20X_TYPE);
  }
  /** Returns the URL for the image.**/
  public String getPath20xUrl() {
    return imageUrl(
      getPath20xFileName(),
      ApiProperties.getProperty(FormLogic.RNA_IMAGE_PATH20X_URL_DIRECTORY),
      ApiProperties.getProperty(FormLogic.RNA_IMAGE_URL_ROOT));
  }

  /**
   * Returns the name of the image file for Pathology 4x magnification.
   * @return  The file name relative to some base URL.
   */
  public String getPath4xFileName() {
    return (String) _images.get(FormLogic.RNA_IMAGE_PATH4X_TYPE);
  }

  /** Returns the URL for the image.**/
  public String getPath4xUrl() {
    return imageUrl(
      getPath4xFileName(),
      ApiProperties.getProperty(FormLogic.RNA_IMAGE_PATH4X_URL_DIRECTORY),
      ApiProperties.getProperty(FormLogic.RNA_IMAGE_URL_ROOT));
  }

  /**
   * Returns the name of the image file for Bio 
   * @return  The file name relative to some base URL.
   */
  public String getBioFileName() {
    return (String) _images.get(FormLogic.RNA_IMAGE_BIO_TYPE);
  }
  /** Returns the URL for the image.**/
  public String getBioUrl() {
    return imageUrl(
      getBioFileName(),
      ApiProperties.getProperty(FormLogic.RNA_IMAGE_BIO_URL_DIRECTORY),
      ApiProperties.getProperty(FormLogic.RNA_IMAGE_URL_ROOT));
  }

  /**
   * Returns the name of the image file for RTPCR
   * @return  The file name relative to some base URL.
   */
  public String getRtpcrFileName() {
    return (String) _images.get(FormLogic.RNA_IMAGE_RTPCR_TYPE);
  }
  /** Returns the URL for the image.**/
  public String getRtpcrUrl() {
    return imageUrl(
      getRtpcrFileName(),
      ApiProperties.getProperty(FormLogic.RNA_IMAGE_RTPCR_URL_DIRECTORY),
      ApiProperties.getProperty(FormLogic.RNA_IMAGE_URL_ROOT));
  }

  /**
   * Returns the name of the image file for GEL
   * @return  The file name relative to some base URL.
   */
  public String getGelFileName() {
    return (String) _images.get(FormLogic.RNA_IMAGE_GEL_TYPE);
  }
  /** Returns the URL for the image.**/
  public String getGelUrl() {
    return imageUrl(
      getGelFileName(),
      ApiProperties.getProperty(FormLogic.RNA_IMAGE_GEL_URL_DIRECTORY),
      ApiProperties.getProperty(FormLogic.RNA_IMAGE_URL_ROOT));
  }

  /**
   * @return Null for now, until we track RNA storage locations in BIGR.
   */
  public BTXBoxLocation getStorageLocation() {
    return null;
  }

  /**
   * @return True if this item's storage location is known and its location address id is the
   *    same as the specified user's location address id.
   * @param securityInfo The user's security information.
   */
  public boolean isStoredLocally(SecurityInfo securityInfo) {
    boolean result = false;
    BTXBoxLocation boxloc = getStorageLocation();
    if (boxloc != null) {
      String addressId = boxloc.getLocationAddressID();
      if (addressId != null && addressId.equals(securityInfo.getUserLocationId())) {
        result = true;
      }
    }
    return result;
  }

  /**
   * Sets the consent id.
   * @param  consentId  the consent id
   */
  public void setConsentId(String consentId) {
    _consentId = consentId;
  }

  /**
   * Sets the case prep number.
   * 
   * @param  prep  the case prep number
   */
  public void setPrep(String prep) {
    _prep = prep;
  }

  /**
   * Sets the quality.
   * 
   * @param  quality  the quality
   */
  public void setQuality(Integer quality) {
    _quality = quality;
  }

  /**
   * Sets the RNA vial id.
   * 
   * @param  rnaVialId  the RNA vial id
   */
  public void setRnaVialId(String rnaVialId) {
    _rnaVialId = rnaVialId;
  }

  /**
   * Sets the RNA vial id.
   * 
   * @param  rnaVialId  the RNA vial id
   */
  public void setRnaRepresentativeSampleId(String sampleId) {
    _representativeSampleId = sampleId;
  }

  /**
   * Sets the RNA amount available.
   * 
   * @param  amount  the RNA amount available.
   */
  public void setRnaAmountAvailable(Integer amount) {
    _rnaAmountAvailable = amount;
  }

  /**
   * Add the image filename corresponding to the image type.
   * @param filetype   the type of the image, from <code>DBAliases</code>.
   * @param dbfilename   the filename of the image, as stored in the database
   */
  public void setImage(String filetype, String dbfilename) {
    _images.put(filetype, dbfilename);
  }

  /**
   * Set the associated sampel for this (possibly pooled) RNA.
   * @param repSample
   */
  public void setRepresentativeSample(SampleData repSample) {
    _representativeSample = repSample;
  }

  /** get the Representative sample (<code>SampleData</code> object).
   * @returns the sample.
   */
  public SampleData getRepresentativeSample() {
    return _representativeSample;
  }

  private String imageUrl(String fNam, String urlDir, String rootDir) {
    StringBuffer urlBuf = new StringBuffer(20);
    urlBuf.append(rootDir);
    urlBuf.append(urlDir);
    urlBuf.append(fNam);
    urlBuf.append(".jpg");
    return urlBuf.toString();
  }
  /**
   * Returns the _cellularstromaCells.
   * @return Integer
   */
  public Integer getCellularstromaCells() {
    return _cellularstromaCells;
  }

  /**
   * Returns the _external_comments.
   * @return String
   */ // now on PathologyEvaluationData 
  //public String getExternalComments() {
  //	return _externalComments;
  //}

  /**
   * Returns the _lesionCells.
   * @return Integer
   */
  public Integer getLesionCells() {
    return _lesionCells;
  }

  /**
   * Returns the _necrosisCells.
   * @return Integer
   */
  public Integer getNecrosisCells() {
    return _necrosisCells;
  }

  /**
   * Returns the _normalCells.
   * @return Integer
   */
  public Integer getNormalCells() {
    return _normalCells;
  }

  /**
   * Returns the _tumorCells.
   * @return Integer
   */
  public Integer getTumorCells() {
    return _tumorCells;
  }

  /**
   * Sets the _cellularstromaCells.
   * @param _cellularstromaCells The _cellularstromaCells to set
   */
  public void setCellularstromaCells(Integer cellularstromaCells) {
    this._cellularstromaCells = cellularstromaCells;
  }

  /**
   * Sets the _external_comments.
   * @param _external_comments The _external_comments to set
   */
  // this data is populated on the PathologyEvaluationData
  //public void setExternalComments(String externalComments) {
  //	this._externalComments = externalComments;
  //}

  /**
   * Sets the _lesionCells.
   * @param _lesionCells The _lesionCells to set
   */
  public void setLesionCells(Integer lesionCells) {
    this._lesionCells = lesionCells;
  }

  /**
   * Sets the _necrosisCells.
   * @param _necrosisCells The _necrosisCells to set
   */
  public void setNecrosisCells(Integer necrosisCells) {
    this._necrosisCells = necrosisCells;
  }

  /**
   * Sets the _normalCells.
   * @param _normalCells The _normalCells to set
   */
  public void setNormalCells(Integer normalCells) {
    this._normalCells = normalCells;
  }

  /**
   * Sets the _tumorCells.
   * @param _tumorCells The _tumorCells to set
   */
  public void setTumorCells(Integer tumorCells) {
    this._tumorCells = tumorCells;
  }



  /**
   * Returns the rnaStatus.
   * @return String
   */
  public String getRnaStatus() {
    return _rnaStatus;
  }

  /**
   * Sets the rnaStatus.
   * @param rnaStatus The rnaStatus to set
   */
  public void setRnaStatus(String rnaStatus) {
    _rnaStatus = rnaStatus;
  }

  /**
   * Returns the rnaProject.
   * @return String
   */
  public String getRnaProject() {
    return _rnaProject;
  }

  /**
   * Sets the rnaProject.
   * @param rnaProject The rnaProject to set
   */
  public void setRnaProject(String rnaProject) {
    _rnaProject = rnaProject;
  }

  /**
   * Returns the rnaHoldProject.
   * @return String
   */
  public String getRnaHoldProject() {
    return _rnaHoldProject;
  }

  /**
   * Sets the rnaHoldProject.
   * @param rnaHoldProject The rnaHoldProject to set
   */
  public void setRnaHoldProject(String rnaHoldProject) {
    _rnaHoldProject = rnaHoldProject;
  }

  /**
   * Returns the ratio.
   * @return Float
   */
  public BigDecimal getRatio() {
    return _ratio;
  }

  /**
   * Sets the ratio.
   * @param ratio The ratio to set
   */
  public void setRatio(BigDecimal ratio) {
    _ratio = ratio;
  }

  /**
   * Returns the pooledTissue.
   * @return String
   */
  public String getPooledTissue() {
    return _pooledTissue;
  }

  /**
   * Sets the pooledTissue.
   * @param pooledTissue The pooledTissue to set
   */
  public void setPooledTissue(String pooledTissue) {
    _pooledTissue = pooledTissue;
  }

  /**
   * Returns the caseExhausted.
   * @return String
   */
  public String getCaseExhausted() {
    return _caseExhausted;
  }

  /**
   * Sets the caseExhausted.
   * @param caseExhausted The caseExhausted to set
   */
  public void setCaseExhausted(String caseExhausted) {
    _caseExhausted = caseExhausted;
  }

  /**
   * Returns the rnaAmountRemaining.
   * @return Integer
   */
  public Integer getRnaAmountRemaining() {
    return _rnaAmountRemaining;
  }

  /**
   * Sets the rnaAmountRemaining.
   * @param rnaAmountRemaining The rnaAmountRemaining to set
   */
  public void setRnaAmountRemaining(Integer rnaAmountRemaining) {
    _rnaAmountRemaining = rnaAmountRemaining;
  }

  /**
   * Returns the pooledSamples.
   * @return List
   */
  public List getSamples() {
    if(_samples == null){
      _samples = new ArrayList();
    }
    return _samples;
  }

  /**
   * Sets the pooledSamples.
   * @param pooledSamples The pooledSamples to set
   */
  public void setSamples(List samples) {
    _samples = samples;
  }

  /**
   * Returns the acellularstromaCells.
   * @return Integer
   */
  public Integer getAcellularstromaCells() {
    return _acellularstromaCells;
  }

  /**
   * Sets the acellularstromaCells.
   * @param acellularstromaCells The acellularstromaCells to set
   */
  public void setAcellularstromaCells(Integer acellularstromaCells) {
    _acellularstromaCells = acellularstromaCells;
  }

  /**
   * Returns the externalComments.
   * @return String
   */
  public String getExternalComments() {
    return _externalComments;
  }

  /**
   * Sets the externalComments.
   * @param externalComments The externalComments to set
   */
  public void setExternalComments(String externalComments) {
    _externalComments = externalComments;
  }

  /**
   * Returns the internalComments.
   * @return String
   */
  public String getInternalComments() {
    return _internalComments;
  }

  /**
   * Sets the internalComments.
   * @param internalComments The internalComments to set
   */
  public void setInternalComments(String internalComments) {
    _internalComments = internalComments;
  }

  /**
   * @return
   */
  public List getLogicalRepositories() {
    return _logicalRepositories;
  }

  /**
   * @param list
   */
  public void setLogicalRepositories(List list) {
    _logicalRepositories = list;
  }

  /**
   * @param True if this is a BMS inventory item.
   */
  public void setBms(boolean newValue) {
    _bms = newValue;
  }

  /**
   * @return True if the sample is in any BMS logical repository.  It is possible for a sample
   * to be in some BMS logical repository (so that
   * {@link #isBms()} will return true) but for the sample not to be a BMS sample from the user's
   * perspective (so that {@link #isBmsFromUsersPerspective()} will return false).  This happens
   * when a sample is in some
   * non-BMS logical repository that the user does have access to, but all of the BMS repositories
   * that the sample is part of are inaccessible to the user.
   * 
   * @see #isBmsFromUsersPerspective()
   */
  public boolean isBms() {
    return _bms;
  }

  /**
   * @return True if any of the logical repositories returned by {@link #getLogicalRepositories()}
   * is both a BMS logical repository and is accessible to the user represented by
   * <code>securityInfo</code>.  This tells us whether the sample is a BMS sample from this user's
   * perspective.  It is possible for a sample to be in some BMS logical repository (so that
   * {@link #isBms()} will return true) but for the sample not to be a BMS sample from the user's
   * perspective (so that this method will return false).  This happens when a sample is in some
   * non-BMS logical repository that the user does have access to, but all of the BMS repositories
   * that the sample is part of are inaccessible to the user.
   * 
   * @param securityInfo The user's security information.
   * @see #isBms()
   */
  public boolean isBmsFromUsersPerspective(SecurityInfo securityInfo) {
    // If this isn't a BMS sample, it can't be in any BMS logical repository at all, so
    // we don't need to do any more checking in that case.  Also, if the current user doesn't
    // have access to any BMS inventory groups at all, we can immediately return false.
    if (!securityInfo.isHasBmsAccess() || !isBms()) {
      return false;
    }

    List repositories = getLogicalRepositories();
    if (repositories == null) {
      return false;
    }

    Iterator iter = repositories.iterator();
    while (iter.hasNext()) {
      LogicalRepository repos = (LogicalRepository) iter.next();
      if (repos != null) {
        if (repos.isBms() && securityInfo.isLogicalRepositoryAccessible(repos)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * @return True if any of the logical repositories returned by {@link #getLogicalRepositories()}
   * is accessible to the user represented by <code>securityInfo</code>.  If the user
   * has the View All Logical Repositories privilege, this always returns true even if the
   * sample is not in any logical repositories.
   * 
   * @param securityInfo The user's security information.
   */
  public boolean isAccessibleToUser(SecurityInfo securityInfo) {
    boolean returnValue = false;
    if (securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
      returnValue = true;
    }
    else {
      returnValue = securityInfo.isAnyLogicalRepositoryAccessible(getLogicalRepositories());
    }
    return returnValue;
  }

  /**
   * Returns the sample type.
   * @return String
   */
  public String getSampleType() {
    return ArtsConstants.SAMPLE_TYPE_RNA;
  }

  public String getNotes() {
    return _notes;
  }

  public void setNotes(String notes) {
    _notes = notes;
  }

}
