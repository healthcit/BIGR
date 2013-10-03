package com.ardais.bigr.es.beans;

public class ArdaisorderstatusKey implements java.io.Serializable {
    public java.sql.Timestamp order_status_date;
    public java.lang.String order_status_id;
    private final static long serialVersionUID = 3206093459760846163L;
  /**
   * Implementation field for persistent attribute: ardaisorder_order_number
   */
  public java.math.BigDecimal ardaisorder_order_number;
    public String ardais_acct_key;
    public String ardais_user_id;
/**
 * Default constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ArdaisorderstatusKey() {
	super();
}
/**
 * Initialize a key from the passed values
 * @param argOrder_status_date java.sql.Timestamp
 * @param argOrder_status_id java.lang.String
 * @param argArdaisorder com.ardais.bigr.es.beans.ArdaisorderKey
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public ArdaisorderstatusKey(java.sql.Timestamp argOrder_status_date, java.lang.String argOrder_status_id, ArdaisorderKey argArdaisorder, java.lang.String argArdais_acct_key, java.lang.String argArdais_user_id) {
	order_status_date = argOrder_status_date;
	order_status_id = argOrder_status_id;
	privateSetArdaisorderKey(argArdaisorder);
	ardais_acct_key = argArdais_acct_key;
	ardais_user_id = argArdais_user_id;
}
  /**
   * Returns true if both keys are equal.
   */
  public boolean equals(java.lang.Object otherKey) {
    if (otherKey instanceof com.ardais.bigr.es.beans.ArdaisorderstatusKey) {
      com.ardais.bigr.es.beans.ArdaisorderstatusKey o =
        (com.ardais.bigr.es.beans.ArdaisorderstatusKey) otherKey;
      return (
        (this.ardais_acct_key.equals(o.ardais_acct_key))
          && (this.order_status_id.equals(o.order_status_id))
          && (this.order_status_date.equals(o.order_status_date))
          && (this.ardais_user_id.equals(o.ardais_user_id))
          && (this.ardaisorder_order_number.equals(o.ardaisorder_order_number)));
    }
    return false;
  }
  /**
   * This method was generated for supporting the relationship role named ardaisorder.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.es.beans.ArdaisorderKey getArdaisorderKey() {
    com.ardais.bigr.es.beans.ArdaisorderKey temp = new com.ardais.bigr.es.beans.ArdaisorderKey();
    boolean ardaisorder_NULLTEST = true;
    ardaisorder_NULLTEST &= (ardaisorder_order_number == null);
    temp.order_number = ardaisorder_order_number;
    if (ardaisorder_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * Returns the hash code for the key.
   */
  public int hashCode() {
    return (
      ardais_acct_key.hashCode()
        + order_status_id.hashCode()
        + order_status_date.hashCode()
        + ardais_user_id.hashCode()
        + ardaisorder_order_number.hashCode());
  }
  /**
   * This method was generated for supporting the relationship role named ardaisorder.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void privateSetArdaisorderKey(com.ardais.bigr.es.beans.ArdaisorderKey inKey) {
    boolean ardaisorder_NULLTEST = (inKey == null);
    ardaisorder_order_number = (ardaisorder_NULLTEST) ? null : inKey.order_number;
  }
	/**
	 * Creates a key for Entity Bean: Ardaisorderstatus
	 */
	public ArdaisorderstatusKey(
		java.sql.Timestamp order_status_date,
		java.lang.String ardais_acct_key,
		java.lang.String ardais_user_id,
		java.lang.String order_status_id,
		com.ardais.bigr.es.beans.ArdaisorderKey argArdaisorder) {
		this.order_status_date = order_status_date;
		this.ardais_acct_key = ardais_acct_key;
		this.ardais_user_id = ardais_user_id;
		this.order_status_id = order_status_id;
		privateSetArdaisorderKey(argArdaisorder);
	}
  /**
   * Creates a key for Entity Bean: Ardaisorderstatus
   */
  public ArdaisorderstatusKey(
    java.lang.String ardais_acct_key,
    java.lang.String order_status_id,
    java.sql.Timestamp order_status_date,
    java.lang.String ardais_user_id,
    com.ardais.bigr.es.beans.ArdaisorderKey argArdaisorder) {
    this.ardais_acct_key = ardais_acct_key;
    this.order_status_id = order_status_id;
    this.order_status_date = order_status_date;
    this.ardais_user_id = ardais_user_id;
    privateSetArdaisorderKey(argArdaisorder);
  }
}
