package com.ardais.bigr.lims.javabeans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.beanutils.BigrBeanUtilsBean;
import com.ardais.bigr.es.helpers.FormLogic;
import com.ardais.bigr.util.gen.DbAliases;

/**
 * Represents the raw data of an image.
 */
public class ImageData implements Serializable {
	private static final long serialVersionUID = 2362940170367474223L;

	private String _imageFilename;
	private String _slideId;
	private String _imageType;
	private String _magnification;
	private Date _captureDate;
	private String _notes;

	/**
	 * Creates a new <code>ImageData</code>.
	 */
	public ImageData() {
	}
  
  /**
   * Creates a new <code>ImageData</code>, initialized from
   * the data in the ImageData passed in.
   *
   * @param  imageData  the <code>ImageData</code>
   */
  public ImageData(ImageData imageData) {
    this();
    BigrBeanUtilsBean.SINGLETON.copyProperties(this, imageData);
    //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
    //does a shallow copy.
    if (imageData.getCaptureDate() != null) {
      setCaptureDate((Date) imageData.getCaptureDate().clone());
    }
  }
	
	/**
	 * Creates a new <code>ImageData</code>, initialized from
	 * the data in the current row of the result set.
	 *
	 * @param  columns  a <code>Map</code> containing a key for each column in
	 * 									 the <code>ResultSet</code>.  Each key must be one of the
	 * 									 column alias constants in {@link com.ardais.bigr.util.DbAliases}
	 * 									 and the corresponding value is ignored.
	 * @param  rs  the <code>ResultSet</code>
	 */
	public ImageData(Map columns, ResultSet rs) {
    this();
    populate(columns, rs);
	}
	
	/**
	 * Populates this <code>ImageData</code> from the data in the current row of 
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
		    if (columns.containsKey(DbAliases.IMAGES_IMAGE_FILE_NAME)) {
		    	setImageFilename(rs.getString(DbAliases.IMAGES_IMAGE_FILE_NAME));
		    }
		    if (columns.containsKey(DbAliases.IMAGES_SLIDE_ID)) {
		    	setSlideId(rs.getString(DbAliases.IMAGES_SLIDE_ID));
		    }
		    if (columns.containsKey(DbAliases.IMAGES_IMAGE_TYPE)) {
		    	setImageType(rs.getString(DbAliases.IMAGES_IMAGE_TYPE));
		    }
		    if (columns.containsKey(DbAliases.IMAGES_MAGNIFICATION)) {
		    	setMagnification(rs.getString(DbAliases.IMAGES_MAGNIFICATION));
		    }
		    if (columns.containsKey(DbAliases.IMAGES_CAPTURE_DATE)) {
		    	setCaptureDate(rs.getDate(DbAliases.IMAGES_CAPTURE_DATE));
		    }
		    if (columns.containsKey(DbAliases.IMAGES_IMAGE_NOTES)) {
		    	setNotes(rs.getString(DbAliases.IMAGES_IMAGE_NOTES));
		    }
	    } 
	    catch (SQLException e) {
	        ApiFunctions.throwAsRuntimeException(e);
	    }
	}
	
	/**
	 * Returns the captureDate.
	 * @return Date
	 */
	public Date getCaptureDate() {
		return _captureDate;
	}

	/**
	 * Returns the imageFilename.
	 * @return String
	 */
	public String getImageFilename() {
		return _imageFilename;
	}

	/**
	 * Returns the url for the imageFilename.
	 * @return String
	 */
	public String getImageUrl() {
		return ApiProperties.getProperty(FormLogic.IMAGEURL_KEY) + _imageFilename;
	}
	
	/**
	 * Returns the url for the imageFilename.
	 * @return String
	 */
	public String getThumbNailUrl() {
		return ApiProperties.getProperty(FormLogic.THUMBURL_KEY) + _imageFilename;
	}

	/**
	 * Returns the imageType.
	 * @return String
	 */
	public String getImageType() {
		return _imageType;
	}

	/**
	 * Returns the magnification.
	 * @return String
	 */
	public String getMagnification() {
		return _magnification;
	}

	/**
	 * Returns the notes.
	 * @return String
	 */
	public String getNotes() {
		return _notes;
	}

	/**
	 * Returns the slideId.
	 * @return String
	 */
	public String getSlideId() {
		return _slideId;
	}

	/**
	 * Sets the captureDate.
	 * @param captureDate The captureDate to set
	 */
	public void setCaptureDate(Date captureDate) {
		_captureDate = captureDate;
	}

	/**
	 * Sets the imageFilename.
	 * @param imageFilename The imageFilename to set
	 */
	public void setImageFilename(String imageFilename) {
		_imageFilename = imageFilename;
	}

	/**
	 * Sets the imageType.
	 * @param imageType The imageType to set
	 */
	public void setImageType(String imageType) {
		_imageType = imageType;
	}

	/**
	 * Sets the magnification.
	 * @param magnification The magnification to set
	 */
	public void setMagnification(String magnification) {
		_magnification = magnification;
	}

	/**
	 * Sets the notes.
	 * @param notes The notes to set
	 */
	public void setNotes(String notes) {
		_notes = notes;
	}

	/**
	 * Sets the slideId.
	 * @param slideId The slideId to set
	 */
	public void setSlideId(String slideId) {
		_slideId = slideId;
	}

}
