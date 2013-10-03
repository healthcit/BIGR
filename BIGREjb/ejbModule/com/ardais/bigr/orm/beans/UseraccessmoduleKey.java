package com.ardais.bigr.orm.beans;

public class UseraccessmoduleKey implements java.io.Serializable {
	public java.lang.String ardais_acct_key;
	public java.lang.String ardais_user_id;
	public java.lang.String objects_object_id;
	final static long serialVersionUID = 3206093459760846163L;

/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public UseraccessmoduleKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argArdais_acct_key java.lang.String
 * @param argArdais_user_id java.lang.String
 * @param argObjects com.ardais.bigr.orm.beans.ObjectsKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public UseraccessmoduleKey(java.lang.String argArdais_acct_key, java.lang.String argArdais_user_id, com.ardais.bigr.orm.beans.ObjectsKey argObjects) {
	ardais_acct_key = argArdais_acct_key;
	ardais_user_id = argArdais_user_id;
	privateSetObjectsKey(argObjects);
}
/**
 * equals method
 * @return boolean
 * @param o java.lang.Object
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public boolean equals(Object o) {
	if (o instanceof UseraccessmoduleKey) {
		UseraccessmoduleKey otherKey = (UseraccessmoduleKey) o;
		return ((this.ardais_acct_key.equals(otherKey.ardais_acct_key)
		 && this.ardais_user_id.equals(otherKey.ardais_user_id)
		 && this.objects_object_id.equals(otherKey.objects_object_id)));
	}
	else
		return false;
}
/**
 * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @return com.ardais.bigr.orm.beans.ObjectsKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public com.ardais.bigr.orm.beans.ObjectsKey getObjectsKey() {
	com.ardais.bigr.orm.beans.ObjectsKey temp = null;
	temp = new com.ardais.bigr.orm.beans.ObjectsKey();
	boolean objects_NULLTEST = true;
	objects_NULLTEST &= (objects_object_id == null);
	temp.object_id = objects_object_id;
	if (objects_NULLTEST) temp = null;
	return temp;
}
/**
 * hashCode method
 * @return int
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public int hashCode() {
	return (ardais_acct_key.hashCode()
		 + ardais_user_id.hashCode()
		 + objects_object_id.hashCode());
}
/**
 * This method was generated for supporting the association named Useraccessmodule SYS_C005047 Objects.  
 * 	It will be deleted/edited when the association is deleted/edited.
 * @param inKey com.ardais.bigr.orm.beans.ObjectsKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void privateSetObjectsKey(com.ardais.bigr.orm.beans.ObjectsKey inKey) {
	boolean objects_NULLTEST = (inKey == null);
	if (objects_NULLTEST) objects_object_id = null; else objects_object_id = inKey.object_id;
}
}
