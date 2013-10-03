package com.ardais.bigr.iltds.databeans;

/**
 * Insert the type's description here.
 * Creation date: (8/16/2002 1:56:53 PM)
 * @author: Jake Thompson
 */
public class SampleImageBean implements java.io.Serializable {
    private String _slideid;
    private String _imagefilename;
    private String _imagetype;
    private String _magnification;
    private String _imageNotes;
    private String _sectionProcedure;
/**
 * SampleImageBean constructor comment.
 */
public SampleImageBean() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (10/25/2001 2:14:42 PM)
 * @return java.lang.String
 */
public java.lang.String getImagefilename() {
	return _imagefilename;
}
/**
 * Insert the method's description here.
 * Creation date: (10/25/2001 2:14:42 PM)
 * @return java.lang.String
 */
public java.lang.String getImagefilenameThumb() {
	return  _imagefilename;
}
/**
 * Insert the method's description here.
 * Creation date: (10/25/2001 2:14:42 PM)
 * @return java.lang.String
 */
public java.lang.String getImagefilenameThumbUrl() {
	return (com.ardais.bigr.api.ApiProperties.getProperty(com.ardais.bigr.es.helpers.FormLogic.THUMBURL_KEY) + _imagefilename);
}
/**
 * Insert the method's description here.
 * Creation date: (10/25/2001 2:14:42 PM)
 * @return java.lang.String
 */
public java.lang.String getImagefilenameUrl() {
	return (com.ardais.bigr.api.ApiProperties.getProperty(com.ardais.bigr.es.helpers.FormLogic.IMAGEURL_KEY) + _imagefilename);
}
/**
 * Insert the method's description here.
 * Creation date: (07/16/2002 3:31:42 PM)
 * @return java.lang.String
 */
public java.lang.String getImageNotes() {
	return _imageNotes;
}
/**
 * Insert the method's description here.
 * Creation date: (10/25/2001 2:14:42 PM)
 * @return java.lang.String
 */
public java.lang.String getImagetype() {
	return _imagetype;
}
/**
 * Insert the method's description here.
 * Creation date: (10/25/2001 2:14:42 PM)
 * @return java.lang.String
 */
public java.lang.String getMagnification() {
	return _magnification;
}
/**
 * Insert the method's description here.
 * Creation date: (07/16/2002 3:31:42 PM)
 * @return java.lang.String
 */
public java.lang.String getSectionProcedure() {
	return _sectionProcedure;
}
/**
 * Insert the method's description here.
 * Creation date: (10/25/2001 2:14:42 PM)
 * @return java.lang.String
 */
public java.lang.String getSlideid() {
	return _slideid;
}
/**
 * Insert the method's description here.
 * Creation date: (10/25/2001 2:14:42 PM)
 * @param newImagefilename java.lang.String
 */
public void setImagefilename(java.lang.String newImagefilename) {
	_imagefilename = newImagefilename;
}
/**
 * Insert the method's description here.
 * Creation date: (07/16/2002 3:37:17 PM)
 * @param newImageNotes java.lang.String
 */
public void setImageNotes(java.lang.String newImageNotes) {
	_imageNotes = newImageNotes;
}
/**
 * Insert the method's description here.
 * Creation date: (10/25/2001 2:14:42 PM)
 * @param newImagetype java.lang.String
 */
public void setImagetype(java.lang.String newImagetype) {
	_imagetype = newImagetype;
}
/**
 * Insert the method's description here.
 * Creation date: (10/25/2001 2:14:42 PM)
 * @param newMagnification java.lang.String
 */
public void setMagnification(java.lang.String newMagnification) {
	_magnification = newMagnification;
}
/**
 * Insert the method's description here.
 * Creation date: (07/16/2002 3:37:17 PM)
 * @param newSectionProcedure java.lang.String
 */
public void setSectionProcedure(java.lang.String newSectionProcedure) {
	_sectionProcedure = newSectionProcedure;
}
/**
 * Insert the method's description here.
 * Creation date: (10/25/2001 2:14:42 PM)
 * @param newSlideid java.lang.String
 */
public void setSlideid(java.lang.String newSlideid) {
	_slideid = newSlideid;
}
}
