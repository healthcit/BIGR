package com.ardais.bigr.iltds.helpers;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Date;

import com.ardais.bigr.api.ApiDateUtil;
import com.ardais.bigr.api.ApiFunctions;

/**
 * Instances of <code>RequestFilter</code> specify which Request objects should be
 * returned from a request query.  If multiple query filters are specified, they are
 * ANDed together.
 * 
 * @see com.ardais.bigr.iltds.bizlogic.RequestFinder
 * @see RequestSelect
 */
public class RequestFilter implements Serializable {
  private String _boxId = null;
  private String _destinationId = null;
  private boolean _includeClosedRequests = false;
  private Date _minRequestCreateDate = null;
  private Date _maxRequestCreateDate = null;
  private String _requesterUserId = null;
  private String _requesterAccount = null;
  private String _requestId = null;
  private RequestState _requestState = null;
  private RequestType _requestType = null;

  /**
   * Create an instance with default property settings.
   * By default, most dimensions are unrestricted.  The exceptions are:
   * <ul>
   * <li>Closed requests are excluded by default.  Use {@link #setIncludeClosedRequests(boolean)}
   *     to override this.</li>
   * </ul>
   */
  public RequestFilter() {
    super();
  }

  /**
   * Create an instance with that matches the specified request id and doesn't
   * include any other constraints.
   */
  public RequestFilter(String requestId) {
    this();

    setRequestId(requestId);

    // Clear any filters that constrain the results by default.  If we didn't do this,
    // then (for example) this would fail to return the request with the specified
    // id if that request happened to be closed.
    //
    setIncludeClosedRequests(true);
  }

  /**
   * @return Only return requests that involve the specified box.
   *   This is null by default, which means that there is no constraint on this aspect.
   */
  public String getBoxId() {
    return _boxId;
  }

  /**
   * @return The request destination to include requests for in the results.
   *   This is null by default, which means that there is no constraint on request destination.
   */
  public String getDestinationId() {
    return _destinationId;
  }

  /**
   * @return True if the results should include closed requests.  By default, only open
   *   requests are included.
   */
  public boolean isIncludeClosedRequests() {
    return _includeClosedRequests;
  }

  /**
   * @return The latest request creation date to include in the results.  The time part
   *   of this is ignored.  The results will only include requests whose creation date is
   *   less than or equal to the date part of this date.  This is null by default, which
   *   means that there is no constraint on the maximum creation date.
   */
  public Date getMaxRequestCreateDate() {
    return _maxRequestCreateDate;
  }

  /**
   * @return The earliest request creation date to include in the results.  The time part
   *   of this is ignored.  The results will only include requests whose creation date is
   *   greater than or equal to the date part of this date.  This is null by default, which
   *   means that there is no constraint on the minimum creation date.
   */
  public Date getMinRequestCreateDate() {
    return _minRequestCreateDate;
  }

  /**
   * @return The user id of the user whose requests should be included.  The results
   *   will only include requests that the specified user created.  This is null by default,
   *   which means that there is no constraint on who created the request.
   */
  public String getRequesterUserId() {
    return _requesterUserId;
  }

  /**
   * @return The account of the users whose requests should be included.  The results
   * will only include requests that users in the specified account created.  This is null
   * by default, which means that there is no constraint on who created the request.
   */
  public String getRequesterAccount() {
    return _requesterAccount;
  }

  /**
   * @return The id of the request to query for.  The results will include at most one
   *   request.  The result list may be empty even if a request with this id exists, if
   *   other filter parameters are specified and the request doesn't match them.
   *   This is null by default, which means that there is no constraint on the request id.
   */
  public String getRequestId() {
    return _requestId;
  }

  /**
   * @return The state of request to query for.  This is null by default, which means
   *   that there is no constraint on the request state.
   */
  public RequestState getRequestState() {
    return _requestState;
  }

  /**
   * @return The type of request to query for.  This is null by default, which means
   *   that there is no constraint on the request type.
   */
  public RequestType getRequestType() {
    return _requestType;
  }

  /**
   * @param string Only return requests that involve the specified box.
   *   This is null by default, which means that there is no constraint on this aspect.
   */
  public void setBoxId(String string) {
    _boxId = string;
  }

  /**
   * @param string The request destination to include requests for in the results.
   *   This is null by default, which means that there is no constraint on request destination.
   */
  public void setDestinationId(String string) {
    _destinationId = string;
  }

  /**
   * @param b True if the results should include closed requests.  By default, only open
   *   requests are included.
   */
  public void setIncludeClosedRequests(boolean b) {
    _includeClosedRequests = b;
  }

  /**
   * @param date The latest request creation date to include in the results.  The time part
   *   of this is ignored.  The results will only include requests whose creation date is
   *   less than or equal to the date part of this date.  This is null by default, which
   *   means that there is no constraint on the maximum creation date.
   */
  public void setMaxRequestCreateDate(Date date) {
    _maxRequestCreateDate = date;
  }

  /**
   * @param date The earliest request creation date to include in the results.  The time part
   *   of this is ignored.  The results will only include requests whose creation date is
   *   greater than or equal to the date part of this date.  This is null by default, which
   *   means that there is no constraint on the minimum creation date.
   */
  public void setMinRequestCreateDate(Date date) {
    _minRequestCreateDate = date;
  }

  /**
   * @param requester The user id of the user whose requests should be included.  The results
   *   will only include requests that the specified user created.  This is null by default,
   *   which means that there is no constraint on who created the request.
   */
  public void setRequesterUserId(String requester) {
    _requesterUserId = requester;
  }

  /**
   * @param account The account of the users whose requests should be included.  The results
   *   will only include requests that users in the specified account created.  This is null by default,
   *   which means that there is no constraint on who created the request.
   */
  public void setRequesterAccount(String account) {
    _requesterAccount = account;
  }

  /**
   * @param id The id of the request to query for.  The results will include at most one
   *   request.  The result list may be empty even if a request with this id exists, if
   *   other filter parameters are specified and the request doesn't match them.
   *   This is null by default, which means that there is no constraint on the request id.
   */
  public void setRequestId(String id) {
    _requestId = id;
  }

  /**
   * @param state The state of request to query for.  This is null by default, which means
   *   that there is no constraint on the request state.
   */
  public void setRequestState(RequestState state) {
    _requestState = state;
  }

  /**
   * @param type The type of request to query for.  This is null by default, which means
   *   that there is no constraint on the request type.
   */
  public void setRequestType(RequestType type) {
    _requestType = type;
  }

  /**
   * @param requestTableAlias
   * @return
   */
  public String getSqlWhereClauses(String requestTableAlias) {
    // If there are no where clauses to generate, this should return and empty string
    // or a string that contains at most whitespace so that callers can easily test this
    // to see if there are actually any conditions to merge into their overall query.

    // For bindSqlWhereClauses to work correctly, it needs to bind criteria in the same order
    // and number as the bind variables are included in the where clauses here.

    StringBuffer sql = new StringBuffer(256);
    boolean addedACondition = false;

    if (!ApiFunctions.isEmpty(getBoxId())) {
      if (addedACondition) {
        sql.append("\n  and ");
      }
      sql.append(requestTableAlias);
      sql.append(
        ".request_id in (select rb.request_id from iltds_request_box rb where rb.box_barcode_id = ?)");
      addedACondition = true;
    }

    if (!ApiFunctions.isEmpty(getDestinationId())) {
      if (addedACondition) {
        sql.append("\n  and ");
      }
      sql.append(requestTableAlias);
      sql.append(".destination_id = ?");
      addedACondition = true;
    }

    if (!isIncludeClosedRequests()) {
      if (addedACondition) {
        sql.append("\n  and ");
      }
      sql.append(requestTableAlias);
      sql.append(".closed_yn = 'N'");
      addedACondition = true;
    }

    if (getMinRequestCreateDate() != null) {
      if (addedACondition) {
        sql.append("\n  and ");
      }
      sql.append(requestTableAlias);
      sql.append(".create_date >= ?");
      addedACondition = true;
    }

    if (getMaxRequestCreateDate() != null) {
      // We test for create_date < ? below since bindSqlWhereClauses binds the ? to
      // midnight of the day *after* the date part of getMaxRequestCreateDate().  We do this
      // so that requests create at any time of the day will be included in the results.
      if (addedACondition) {
        sql.append("\n  and ");
      }
      sql.append(requestTableAlias);
      sql.append(".create_date < ?");
      addedACondition = true;
    }

    if (!ApiFunctions.isEmpty(getRequesterUserId())) {
      if (addedACondition) {
        sql.append("\n  and ");
      }
      sql.append(requestTableAlias);
      sql.append(".requester_user_id = ?");
      addedACondition = true;
    }

    if (!ApiFunctions.isEmpty(getRequesterAccount())) {
      if (addedACondition) {
        sql.append("\n  and ");
      }
      sql.append(requestTableAlias);
      sql.append(
        ".requester_user_id in (select u.ardais_user_id from es_ardais_user u where u.ardais_acct_key = ?)");
      addedACondition = true;
    }

    if (!ApiFunctions.isEmpty(getRequestId())) {
      if (addedACondition) {
        sql.append("\n  and ");
      }
      sql.append(requestTableAlias);
      sql.append(".request_id = ?");
      addedACondition = true;
    }

    if (getRequestState() != null) {
      if (addedACondition) {
        sql.append("\n  and ");
      }
      sql.append(requestTableAlias);
      sql.append(".state = ?");
      addedACondition = true;
    }

    if (getRequestType() != null) {
      if (addedACondition) {
        sql.append("\n  and ");
      }
      sql.append(requestTableAlias);
      sql.append(".request_type = ?");
      addedACondition = true;
    }

    if (addedACondition) {
      // Add a space to the end to separate these conditions from whatever they
      // might end up getting appended to by the caller.
      sql.append(' ');
    }

    return sql.toString();
  }

  /**
   * @param pstmt
   * @param startingBindIndex
   * @return
   */
  public int bindSqlWhereClauses(PreparedStatement pstmt, int startingBindIndex) {
    // If there are no where clauses to generate, this should return and empty string
    // or a string that contains at most whitespace so that callers can easily test this
    // to see if there are actually any conditions to merge into their overall query.

    // For bindSqlWhereClauses to work correctly, it needs to bind criteria in the same order
    // and number as the bind variables are included in the where clauses by getSqlWhereClauses.

    int bindIndex = startingBindIndex;

    try {
      if (!ApiFunctions.isEmpty(getBoxId())) {
        pstmt.setString(bindIndex++, getBoxId());
      }

      if (!ApiFunctions.isEmpty(getDestinationId())) {
        pstmt.setString(bindIndex++, getDestinationId());
      }

      //if (!isIncludeClosedRequests()) {
      // ... Nothing to bind for this criteria
      //}

      if (getMinRequestCreateDate() != null) {
        // Using setDate/java.sql.Date automatically truncates/ignores any time part that
        // might be present in getMinRequestCreateDate(), which is what we want.
        // See the javadoc that describes the definition of the getMinRequestCreateDate() filter.
        //
        pstmt.setDate(bindIndex++, new java.sql.Date(getMinRequestCreateDate().getTime()));
      }

      if (getMaxRequestCreateDate() != null) {
        // We test for create_date < ? in the condition we are binding here,
        // so the value that we must bind is midnight of the day *after* the date part of
        // getMaxRequestCreateDate().  We do this so that requests create at any time of the
        // day will be included in the results.
        //
        // Using setDate/java.sql.Date automatically truncates/ignores any time part that
        // might be present in getMinRequestCreateDate(), which is what we want.
        // See the javadoc that describes the definition of the getMinRequestCreateDate() filter.
        //
        java.sql.Date adjustedMaxDate =
          new java.sql.Date(ApiDateUtil.add(getMaxRequestCreateDate(), Calendar.DATE, 1).getTime());
        pstmt.setDate(bindIndex++, adjustedMaxDate);
      }

      if (!ApiFunctions.isEmpty(getRequesterUserId())) {
        pstmt.setString(bindIndex++, getRequesterUserId());
      }

      if (!ApiFunctions.isEmpty(getRequesterAccount())) {
        pstmt.setString(bindIndex++, getRequesterAccount());
      }

      if (!ApiFunctions.isEmpty(getRequestId())) {
        pstmt.setString(bindIndex++, getRequestId());
      }

      if (getRequestState() != null) {
        pstmt.setString(bindIndex++, getRequestState().toString());
      }

      if (getRequestType() != null) {
        pstmt.setString(bindIndex++, getRequestType().toString());
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    return bindIndex;
  }

}
