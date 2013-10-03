package com.ardais.bigr.iltds.databeans;

import java.util.List;

import com.ardais.bigr.iltds.assistants.LogicalRepository;

/**
 * This class represents details of a logical repository that go beyond what is stored
 * directly in the ARD_LOGICAL_REPOS table.  It is used, for example, to deliver logical
 * repository information to ICP.
 */
public class LogicalRepositoryExtendedData extends LogicalRepository {
  private boolean _exists = false;
  private int _itemCount = 0;
  private List _usersWithExplicitAccess = null;
  private List _usersWithImplicitAccess = null;

  public LogicalRepositoryExtendedData() {
    super();
  }

  /**
   * @return true if the logical repository indicate by the {@link #getId()} property
   *     exists in the database.  This doesn't actually query the database, it just returns
   *     the value defined by calling the {@link #setExists(boolean)} method (or false if that
   *     method hasn't been called).
   */
  public boolean isExists() {
    return _exists;
  }

  /**
   * @return The number of distinct items in this logical repository.
   */
  public int getItemCount() {
    return _itemCount;
  }

  /**
   * @see #isExists()
   * 
   * @param b the new value.
   */
  public void setExists(boolean b) {
    _exists = b;
  }

  /**
   * Set the number of distinct items in this logical repository.
   * 
   * @param i The item count.
   */
  public void setItemCount(int i) {
    _itemCount = i;
  }

  /**
   * @return
   */
  public List getUsersWithExplicitAccess() {
    return _usersWithExplicitAccess;
  }

  /**
   * @return
   */
  public List getUsersWithImplicitAccess() {
    return _usersWithImplicitAccess;
  }

  /**
   * @param list
   */
  public void setUsersWithExplicitAccess(List list) {
    _usersWithExplicitAccess = list;
  }

  /**
   * @param list
   */
  public void setUsersWithImplicitAccess(List list) {
    _usersWithImplicitAccess = list;
  }

}
