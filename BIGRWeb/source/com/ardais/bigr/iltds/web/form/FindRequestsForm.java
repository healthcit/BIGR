/*
 * Created on Mar 2, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ardais.bigr.iltds.web.form;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.iltds.btx.BtxDetailsFindRequests;
import com.ardais.bigr.iltds.helpers.RequestFilter;
import com.ardais.bigr.util.BigrValidator;
import com.ardais.bigr.web.action.BigrActionMapping;
import com.ardais.bigr.web.form.BigrActionForm;

/**
 * @author jesielionis
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FindRequestsForm extends BigrActionForm  {
  
  private static String MANAGE_REQUESTS_FILTER = "manageRequestsFilter";
  private static String VIEW_REQUESTS_FILTER = "viewRequestsFilter";
  
  private RequestFilter _requestFilter;
  private List _openRequestDtos;
  private List _closedRequestDtos;
  private LegalValueSet _requesters;
  private String _startDate;
  private String _endDate;

  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#doReset(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected void doReset(BigrActionMapping mapping, HttpServletRequest request) {
    //try to retrieve the appropriate RequestFilter from the session
    RequestFilter filter;
    if (MANAGE_REQUESTS_FILTER.equalsIgnoreCase(mapping.getParameter())) {
      filter = (RequestFilter) request.getSession(false).getAttribute(MANAGE_REQUESTS_FILTER);
      if (filter == null) {
        filter = new RequestFilter();
      }
    }
    else if (VIEW_REQUESTS_FILTER.equalsIgnoreCase(mapping.getParameter())) {
      filter = (RequestFilter) request.getSession(false).getAttribute(VIEW_REQUESTS_FILTER);
      if (filter == null) {
        filter = new RequestFilter();
      }
    }
    else {
      filter = new RequestFilter();
    }
    _requestFilter = filter;
    _openRequestDtos = null;
    _closedRequestDtos = null;
    _requesters = null;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    Date startDate = filter.getMinRequestCreateDate();
    if (startDate != null) {
      _startDate = formatter.format(startDate);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(startDate);
    }
    else {
      _startDate = null;
    }
    Date endDate = filter.getMaxRequestCreateDate();
    if (endDate != null) {
      _endDate = formatter.format(endDate);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(endDate);
    }
    else {
      _endDate = null;
    }
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#doValidate(com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  protected ActionErrors doValidate(BigrActionMapping mapping, HttpServletRequest request) {
    return null;
  }
  
  /* (non-Javadoc)
   * @see com.ardais.bigr.web.form.BigrActionForm#describeIntoBtxDetails(com.ardais.bigr.iltds.btx.BTXDetails, com.ardais.bigr.web.action.BigrActionMapping, javax.servlet.http.HttpServletRequest)
   */
  public void describeIntoBtxDetails(
      BTXDetails btxDetails0,
      BigrActionMapping mapping,
      HttpServletRequest request) {
    super.describeIntoBtxDetails(btxDetails0, mapping, request);
    //translate any start/end date information from the form into the filter
    BtxDetailsFindRequests btxDetails = (BtxDetailsFindRequests)btxDetails0;
    btxDetails.getRequestFilter().setMinRequestCreateDate(getRequestSubmissionStartDate());
    btxDetails.getRequestFilter().setMaxRequestCreateDate(getRequestSubmissionEndDate());
    //store the filter in the session, so it can be retrieved and "remembered" when
    //the user returns to the view or manage requests page
    String key = mapping.getParameter();
    if (MANAGE_REQUESTS_FILTER.equalsIgnoreCase(key) ||
        VIEW_REQUESTS_FILTER.equalsIgnoreCase(key)) {
          request.getSession(false).setAttribute(key,btxDetails.getRequestFilter());
    }
  }

  /**
   * @return
   */
  public List getClosedRequestDtos() {
    return _closedRequestDtos;
  }

  /**
   * @return
   */
  public List getOpenRequestDtos() {
    return _openRequestDtos;
  }

  /**
   * @return
   */
  public LegalValueSet getRequesters() {
    return _requesters;
  }

  /**
   * @return
   */
  public RequestFilter getRequestFilter() {
    return _requestFilter;
  }

  /**
   * @param list
   */
  public void setClosedRequestDtos(List list) {
    _closedRequestDtos = list;
  }

  /**
   * @param list
   */
  public void setOpenRequestDtos(List list) {
    _openRequestDtos = list;
  }

  /**
   * @param set
   */
  public void setRequesters(LegalValueSet set) {
    _requesters = set;
  }

  /**
   * @param filter
   */
  public void setRequestFilter(RequestFilter filter) {
    _requestFilter = filter;
  }

  /**
   * @return
   */
  public String getEndDate() {
    return _endDate;
  }


  /**
   * @return
   */
  public String getStartDate() {
    return _startDate;
  }

  /**
   * @param string
   */
  public void setEndDate(String string) {
    _endDate = string;
  }

  /**
   * @param string
   */
  public void setStartDate(String string) {
    _startDate = string;
  }

  private Date getRequestSubmissionStartDate() {
    if (ApiFunctions.isEmpty(getStartDate())) return null;
    return new Date(BigrValidator.formatDate(getStartDate(), "MM/dd/yyyy", true).getTime());
  }

  private Date getRequestSubmissionEndDate() {
    if (ApiFunctions.isEmpty(getEndDate())) return null;
    return new Date(BigrValidator.formatDate(getEndDate(), "MM/dd/yyyy", true).getTime());
  }

}
