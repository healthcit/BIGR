package com.ardais.bigr.query.filters;

/**
 * @author dfeldman
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class FilterUserAccountInfo extends Filter {

  private String _userId;
  private String _account;
  
  /**
   *  A filter that uses account, userid or both.  Either may be null.
   */
  public FilterUserAccountInfo(String key, String user, String acct) {
    super(key);
    this._userId = user;
    this._account = acct;
  }

  /**
   * @see com.ardais.bigr.query.filters.Filter#appendVals(StringBuffer)
   */
  protected void appendVals(StringBuffer buf) {
    if (_userId!=null)
      buf.append("CURRENT_USER");
    else
      buf.append('-'); // no user
      
    buf.append(',');
      
    if (_userId!=null)
      buf.append("CURRENT_ACCOUNT");
    else
      buf.append('-');
  }

  /**
   * Returns the account.
   * @return String
   */
  public String getAccount() {
    return _account;
  }

  /**
   * Returns the userId.
   * @return String
   */
  public String getUserId() {
    return _userId;
  }

}
