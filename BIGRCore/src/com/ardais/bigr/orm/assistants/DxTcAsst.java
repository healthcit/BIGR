package com.ardais.bigr.orm.assistants;

import java.io.*;
import com.ardais.bigr.api.*;

public class DxTcAsst implements java.io.Serializable {
	private static final long serialVersionUID = -1396883356741945324L;

	// *** WARNING, PLEASE NOTE:  If you add fields here, you must also
	// *** update the constructor that takes an ObjectInput argument and
	// *** the writeExternal method.
	//
	private java.lang.String ownerCode;
	private java.lang.String fullName;
	private java.lang.String parentCode;
	private java.lang.String levelCode;
	private java.lang.String typeCode;
	private java.lang.String lowest_level_flag;
	private int step;
	//
	// *** WARNING, PLEASE NOTE:  If you add fields here, you must also
	// *** update the constructor that takes an ObjectInput argument and
	// *** the writeExternal method.
/**
 * DxTcAsst constructor comment.
 */
public DxTcAsst() {
	super();
}
/**
 * Reconstitute the object from its externalized representation.  See
 * {@link #writeExternal(ObjectOutput, StringExternalizer)} for a description
 * of the externalized format.  This is used when we are reading/writing these
 * objects in a custom way that doesn't rely on standard serialization.  We do
 * this in some places to improve performance when marshalling large sets of
 * objects to/from and EJB method call.  For example, this is used by
 * DxTcHierarchyVector's custom externalization methods.
 * 
 * <p>IMPORTANT:  When you add fields to this object, you need to make
 * corresponding changes here and in the writeExternal method.  If you don't,
 * the object won't be serialized/deserialized correctly in some situations.
 */
public DxTcAsst(ObjectInput in, StringInternalizer stringInternalizer) throws IOException, ClassNotFoundException {
	this();

	long readSerialVer = in.readLong();

	if (readSerialVer != serialVersionUID) {
		throw new StreamCorruptedException(
			"Invalid type identifier encountered: " + readSerialVer +
			", expected " + serialVersionUID);
	}

	fullName = stringInternalizer.readString(in);
	levelCode = stringInternalizer.readString(in);
	lowest_level_flag = stringInternalizer.readString(in);
	ownerCode = stringInternalizer.readString(in);
	parentCode = stringInternalizer.readString(in);
	step = in.readInt();
	typeCode = stringInternalizer.readString(in);
}
/**
 * Insert the method's description here.
 * Creation date: (4/17/01 12:25:54 PM)
 * @return java.lang.String
 */
public java.lang.String getFullName() {
	return fullName;
}
/**
 * Insert the method's description here.
 * Creation date: (4/17/01 12:26:28 PM)
 * @return java.lang.String
 */
public java.lang.String getLevelCode() {
	return levelCode;
}
/**
 * Insert the method's description here.
 * Creation date: (5/22/01 12:42:36 PM)
 * @return java.lang.String
 */
public java.lang.String getLowest_level_flag() {
	return lowest_level_flag;
}
/**
 * Insert the method's description here.
 * Creation date: (4/17/01 12:25:39 PM)
 * @return java.lang.String
 */
public java.lang.String getOwnerCode() {
	return ownerCode;
}
/**
 * Insert the method's description here.
 * Creation date: (4/17/01 12:26:11 PM)
 * @return java.lang.String
 */
public java.lang.String getParentCode() {
	return parentCode;
}
/**
 * Insert the method's description here.
 * Creation date: (5/23/01 2:24:24 PM)
 * @return int
 */
public int getStep() {
	return step;
}
/**
 * Insert the method's description here.
 * Creation date: (4/17/01 12:26:44 PM)
 * @return java.lang.String
 */
public java.lang.String getTypeCode() {
	return typeCode;
}
/**
 * Insert the method's description here.
 * Creation date: (4/17/01 12:25:54 PM)
 * @param newFullName java.lang.String
 */
public void setFullName(java.lang.String newFullName) {
	fullName = newFullName;
}
/**
 * Insert the method's description here.
 * Creation date: (4/17/01 12:26:28 PM)
 * @param newLevelCode java.lang.String
 */
public void setLevelCode(java.lang.String newLevelCode) {
	levelCode = newLevelCode;
}
/**
 * Insert the method's description here.
 * Creation date: (5/22/01 12:42:36 PM)
 * @param newLowest_level_flag java.lang.String
 */
public void setLowest_level_flag(java.lang.String newLowest_level_flag) {
	lowest_level_flag = newLowest_level_flag;
}
/**
 * Insert the method's description here.
 * Creation date: (4/17/01 12:25:39 PM)
 * @param newOwnerCode java.lang.String
 */
public void setOwnerCode(java.lang.String newOwnerCode) {
	ownerCode = newOwnerCode;
}
/**
 * Insert the method's description here.
 * Creation date: (4/17/01 12:26:11 PM)
 * @param newParentCode java.lang.String
 */
public void setParentCode(java.lang.String newParentCode) {
	parentCode = newParentCode;
}
/**
 * Insert the method's description here.
 * Creation date: (5/23/01 2:24:24 PM)
 * @param newStep int
 */
public void setStep(int newStep) {
	step = newStep;
}
/**
 * Insert the method's description here.
 * Creation date: (4/17/01 12:26:44 PM)
 * @param newTypeCode java.lang.String
 */
public void setTypeCode(java.lang.String newTypeCode) {
	typeCode = newTypeCode;
}
/**
 * The object implements the writeExternal method to save its contents
 * by calling the methods of DataOutput for its primitive values or
 * calling the writeObject method of ObjectOutput for objects, strings,
 * and arrays.
 * <p>
 * This is used when we are reading/writing these objects in
 * a custom way that doesn't rely on standard serialization.  We do this in
 * some places to improve performance when marshalling large sets of objects
 * to/from and EJB method call.  For example, this is used by DxTcHierarchyVector's
 * custom externalization methods.
 * <p>
 * IMPORTANT:  When you add fields to this object, you need to make corresponding
 * changes here and in the writeExternal method.  If you don't, the object won't
 * be serialized/deserialized correctly in some situations.
 * <p>
 * The format written is very simple: first the serialVersionUID is written,
 * then each of the data fiel value (see the code for the order in which
 * they're written).
 *
 * @exception IOException Includes any I/O exceptions that may occur
 */
public void writeExternal(ObjectOutput out, StringExternalizer stringExternalizer) throws IOException {
	out.writeLong(serialVersionUID);

	stringExternalizer.writeString(out, fullName);
	stringExternalizer.writeString(out, levelCode);
	stringExternalizer.writeString(out, lowest_level_flag);
	stringExternalizer.writeString(out, ownerCode);
	stringExternalizer.writeString(out, parentCode);
	out.writeInt(step);
	stringExternalizer.writeString(out, typeCode);
}
}
