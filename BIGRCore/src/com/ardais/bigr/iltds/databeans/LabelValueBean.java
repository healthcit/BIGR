package com.ardais.bigr.iltds.databeans;

/**
 * Insert the type's description here.
 * Creation date: (3/29/2002 10:31:37 AM)
 * @author: Jake Thompson
 */
public class LabelValueBean implements java.io.Serializable {
	private java.lang.Object label;
	private java.lang.Object value;
/**
 * LabelValueBean constructor comment.
 */
public LabelValueBean() {
	super();
}
/**
 * LabelValueBean constructor comment.
 */
public LabelValueBean(Object newLabel, Object newValue) {
	super();
	label = newLabel;
	value = newValue;
}
/**
 * Insert the method's description here.
 * Creation date: (3/29/2002 10:32:58 AM)
 * @return java.lang.Object
 */
public java.lang.Object getLabel() {
	return label;
}
/**
 * Insert the method's description here.
 * Creation date: (3/29/2002 10:33:15 AM)
 * @return java.lang.Object
 */
public java.lang.Object getValue() {
	return value;
}
/**
 * Insert the method's description here.
 * Creation date: (3/29/2002 10:32:58 AM)
 * @param newLabel java.lang.Object
 */
public void setLabel(java.lang.Object newLabel) {
	label = newLabel;
}
/**
 * Insert the method's description here.
 * Creation date: (3/29/2002 10:33:15 AM)
 * @param newValue java.lang.Object
 */
public void setValue(java.lang.Object newValue) {
	value = newValue;
}
}
