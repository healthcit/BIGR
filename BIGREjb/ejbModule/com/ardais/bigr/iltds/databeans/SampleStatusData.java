package com.ardais.bigr.iltds.databeans;

import com.ardais.bigr.beanutils.BigrBeanUtilsBean;

/**
 * Insert the type's description here.
 * Creation date: (3/28/2002 2:40:11 PM)
 * @author: Jake Thompson
 */
public class SampleStatusData implements java.io.Serializable {
	private String sample_id;
	private String code;
	private DateData date;
/**
 * SampleStatusData constructor comment.
 */
public SampleStatusData() {
	super();
}
/**
 * SampleStatusData constructor comment.
 */
public SampleStatusData(SampleStatusData sampleStatusData) {
  this();
  BigrBeanUtilsBean.SINGLETON.copyProperties(this, sampleStatusData);
  //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
  //does a shallow copy.
  if (sampleStatusData.getDate() != null) {
    setDate(new DateData(sampleStatusData.getDate()));
  }
}
/**
 * Insert the method's description here.
 * Creation date: (3/28/2002 2:42:50 PM)
 * @return java.lang.String
 */
public java.lang.String getCode() {
	return code;
}
/**
 * Insert the method's description here.
 * Creation date: (3/28/2002 2:42:50 PM)
 * @return com.ardais.bigr.iltds.databeans.DateData
 */
public DateData getDate() {
	return date;
}
/**
 * Insert the method's description here.
 * Creation date: (3/28/2002 2:42:50 PM)
 * @return java.lang.String
 */
public java.lang.String getSample_id() {
	return sample_id;
}
/**
 * Insert the method's description here.
 * Creation date: (3/28/2002 2:42:50 PM)
 * @param newCode java.lang.String
 */
public void setCode(java.lang.String newCode) {
	code = newCode;
}
/**
 * Insert the method's description here.
 * Creation date: (3/28/2002 2:42:50 PM)
 * @param newDate com.ardais.bigr.iltds.databeans.DateData
 */
public void setDate(DateData newDate) {
	date = newDate;
}
/**
 * Insert the method's description here.
 * Creation date: (3/28/2002 2:42:50 PM)
 * @param newSample_id java.lang.String
 */
public void setSample_id(java.lang.String newSample_id) {
	sample_id = newSample_id;
}
}
