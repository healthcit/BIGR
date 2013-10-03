package com.ardais.bigr.iltds.beans;

public class BoxlocationKey implements java.io.Serializable {
	public java.lang.String drawer_id;
	/**
	 * Implementation field for persistent attribute: geolocation_location_address_id
	 */
	public java.lang.String geolocation_location_address_id;
	public java.lang.String room_id;
	final static long serialVersionUID = 3206093459760846163L;
	public java.lang.String slot_id;
  /**
   * Implementation field for persistent attribute: unitName
   */
  public java.lang.String unitName;
/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public BoxlocationKey() {
	super();
}
  /**
   * Creates a key for Entity Bean: Boxlocation
   */
  public BoxlocationKey(
    java.lang.String drawer_id,
    java.lang.String room_id,
    java.lang.String slot_id,
    java.lang.String unitName,
    com.ardais.bigr.iltds.beans.GeolocationKey argGeolocation) {
    this.drawer_id = drawer_id;
    this.room_id = room_id;
    this.slot_id = slot_id;
    this.unitName = unitName;
    privateSetGeolocationKey(argGeolocation);
  }
  /**
   * Returns true if both keys are equal.
   */
  public boolean equals(java.lang.Object otherKey) {
    if (otherKey instanceof com.ardais.bigr.iltds.beans.BoxlocationKey) {
      com.ardais.bigr.iltds.beans.BoxlocationKey o =
        (com.ardais.bigr.iltds.beans.BoxlocationKey) otherKey;
      return (
        (this.drawer_id.equals(o.drawer_id))
          && (this.room_id.equals(o.room_id))
          && (this.slot_id.equals(o.slot_id))
          && (this.unitName.equals(o.unitName))
          && (this.geolocation_location_address_id.equals(o.geolocation_location_address_id)));
    }
    return false;
  }
	/**
	 * This method was generated for supporting the relationship role named geolocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public com.ardais.bigr.iltds.beans.GeolocationKey getGeolocationKey() {
		com.ardais.bigr.iltds.beans.GeolocationKey temp =
			new com.ardais.bigr.iltds.beans.GeolocationKey();
		boolean geolocation_NULLTEST = true;
		geolocation_NULLTEST &= (geolocation_location_address_id == null);
		temp.location_address_id = geolocation_location_address_id;
		if (geolocation_NULLTEST)
			temp = null;
		return temp;
	}
  /**
   * Returns the hash code for the key.
   */
  public int hashCode() {
    return (
      drawer_id.hashCode()
        + room_id.hashCode()
        + slot_id.hashCode()
        + unitName.hashCode()
        + geolocation_location_address_id.hashCode());
  }
	/**
	 * This method was generated for supporting the relationship role named geolocation.
	 * It will be deleted/edited when the relationship is deleted/edited.
	 */
	public void privateSetGeolocationKey(
		com.ardais.bigr.iltds.beans.GeolocationKey inKey) {
		boolean geolocation_NULLTEST = (inKey == null);
		geolocation_location_address_id =
			(geolocation_NULLTEST) ? null : inKey.location_address_id;
	}
}
