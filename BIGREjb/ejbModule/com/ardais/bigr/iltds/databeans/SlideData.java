package com.ardais.bigr.iltds.databeans;

import com.ardais.bigr.beanutils.BigrBeanUtilsBean;

/**
 * Insert the type's description here.
 * Creation date: (3/20/2002 10:41:59 AM)
 * @author: Jake Thompson
 */
public class SlideData implements java.io.Serializable {
	private final static long serialVersionUID = -1683390420552227088L;
	private String slide_id;
	private SampleData parent;
	private DateData creation_date;
	private String qc_ind;
/**
 * SlideBean constructor comment.
 */
public SlideData() {
	super();
}
/**
 * SlideBean constructor comment.
 */
public SlideData(SlideData slideData) {
  this(slideData, true);
}

/**
 * SlideBean constructor comment.
 */
public SlideData(SlideData slideData, boolean copyParent) {
  this();
  BigrBeanUtilsBean.SINGLETON.copyProperties(this, slideData);
  //any mutable objects must be handled seperately (BigrBeanUtilsBean.copyProperties
  //does a shallow copy.
  if (copyParent && slideData.getParent() != null) {
    setParent(new SampleData(slideData.getParent()));
  }
  if (slideData.getCreation_date() != null) {
    setCreation_date(new DateData(slideData.getCreation_date()));
  }
}

/**
 * Insert the method's description here.
 * Creation date: (6/13/2002 9:23:39 AM)
 * @return com.ardais.bigr.iltds.databeans.DateData
 */
public DateData getCreation_date() {
	return creation_date;
}
/**
 * Insert the method's description here.
 * Creation date: (3/22/2002 7:26:15 AM)
 * @return com.ardais.bigr.iltds.databeans.SampleData
 */
public SampleData getParent() {
	return parent;
}
/**
 * Insert the method's description here.
 * Creation date: (6/13/2002 9:23:58 AM)
 * @return java.lang.String
 */
public java.lang.String getQc_ind() {
	return qc_ind;
}
/**
 * Insert the method's description here.
 * Creation date: (3/20/2002 10:57:56 AM)
 * @return java.lang.String
 */
public java.lang.String getSlide_id() {
	return slide_id;
}
/**
 * Insert the method's description here.
 * Creation date: (6/13/2002 9:23:39 AM)
 * @param newCreation_date com.ardais.bigr.iltds.databeans.DateData
 */
public void setCreation_date(DateData newCreation_date) {
	creation_date = newCreation_date;
}
/**
 * Insert the method's description here.
 * Creation date: (3/22/2002 7:26:15 AM)
 * @param newParent com.ardais.bigr.iltds.databeans.SampleData
 */
public void setParent(SampleData newParent) {
	parent = newParent;
}
/**
 * Insert the method's description here.
 * Creation date: (6/13/2002 9:23:58 AM)
 * @param newQc_ind java.lang.String
 */
public void setQc_ind(java.lang.String newQc_ind) {
	qc_ind = newQc_ind;
}
/**
 * Insert the method's description here.
 * Creation date: (3/20/2002 10:57:56 AM)
 * @param newSlide_id java.lang.String
 */
public void setSlide_id(java.lang.String newSlide_id) {
	slide_id = newSlide_id;
}
}
