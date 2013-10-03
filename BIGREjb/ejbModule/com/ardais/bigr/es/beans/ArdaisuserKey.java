package com.ardais.bigr.es.beans;

public class ArdaisuserKey implements java.io.Serializable {
	final static long serialVersionUID = 3206093459760846163L;

	/**
	 * Implementation field for persistent attribute: ardais_user_id
	 */
	public java.lang.String ardais_user_id;
  /**
   * Implementation field for persistent attribute: ardaisaccount_ardais_acct_key
   */
  public java.lang.String ardaisaccount_ardais_acct_key;
  /**
   * Returns true if both keys are equal.
   */
  public boolean equals(java.lang.Object otherKey) {
    if (otherKey instanceof com.ardais.bigr.es.beans.ArdaisuserKey) {
      com.ardais.bigr.es.beans.ArdaisuserKey o = (com.ardais.bigr.es.beans.ArdaisuserKey) otherKey;
      return (
        (this.ardais_user_id.equals(o.ardais_user_id))
          && (this.ardaisaccount_ardais_acct_key.equals(o.ardaisaccount_ardais_acct_key)));
    }
    return false;
  }
  /**
   * Returns the hash code for the key.
   */
  public int hashCode() {
    return (ardais_user_id.hashCode() + ardaisaccount_ardais_acct_key.hashCode());
  }
	/**
	 * Creates a key for Entity Bean: Ardaisuser
	 */
	public ArdaisuserKey() {
	}
  /**
   * Creates a key for Entity Bean: Ardaisuser
   */
  public ArdaisuserKey(
    java.lang.String ardais_user_id,
    com.ardais.bigr.es.beans.ArdaisaccountKey argArdaisaccount) {
    this.ardais_user_id = ardais_user_id;
    privateSetArdaisaccountKey(argArdaisaccount);
  }
  /**
   * This method was generated for supporting the relationship role named ardaisaccount.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public com.ardais.bigr.es.beans.ArdaisaccountKey getArdaisaccountKey() {
    com.ardais.bigr.es.beans.ArdaisaccountKey temp =
      new com.ardais.bigr.es.beans.ArdaisaccountKey();
    boolean ardaisaccount_NULLTEST = true;
    ardaisaccount_NULLTEST &= (ardaisaccount_ardais_acct_key == null);
    temp.ardais_acct_key = ardaisaccount_ardais_acct_key;
    if (ardaisaccount_NULLTEST)
      temp = null;
    return temp;
  }
  /**
   * This method was generated for supporting the relationship role named ardaisaccount.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public void privateSetArdaisaccountKey(com.ardais.bigr.es.beans.ArdaisaccountKey inKey) {
    boolean ardaisaccount_NULLTEST = (inKey == null);
    ardaisaccount_ardais_acct_key = (ardaisaccount_NULLTEST) ? null : inKey.ardais_acct_key;
  }
}
