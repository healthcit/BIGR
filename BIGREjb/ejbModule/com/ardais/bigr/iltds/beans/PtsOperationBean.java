package com.ardais.bigr.iltds.beans;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.NamingException;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.es.beans.ArdaisaccountKey;
import com.ardais.bigr.es.beans.ArdaisuserAccessBean;
import com.ardais.bigr.es.beans.ArdaisuserKey;
import com.ardais.bigr.es.beans.ShoppingcartAccessBean;
import com.ardais.bigr.es.beans.ShoppingcartKey;
import com.ardais.bigr.es.beans.ShoppingcartdetailAccessBean;
import com.ardais.bigr.es.beans.ShoppingcartdetailKey;
import com.ardais.bigr.es.javabeans.QueryBean;
import com.ardais.bigr.iltds.assistants.LineItemDataBean;
import com.ardais.bigr.iltds.assistants.ProjectDataBean;
import com.ardais.bigr.iltds.assistants.SampleDataBean;
import com.ardais.bigr.iltds.assistants.SampleQueryBuilder;
import com.ardais.bigr.iltds.assistants.WorkOrderDataBean;
import com.ardais.bigr.iltds.helpers.FormLogic;
import com.ardais.bigr.security.SecurityInfo;
import com.ibm.ivj.ejb.runtime.AccessBeanEnumeration;

/**
 * <code>PtsOperationBean</code> is the bean class for the 
 * <code>PtsOperation</code> bean.
 */
public class PtsOperationBean implements SessionBean {
  private SessionContext mySessionCtx = null;
  private final static long serialVersionUID = 3206093459760846163L;

  private static final String COMMON_WHERE_CLAUSE =
    "consent.CONSENT_ID  =  asm.CONSENT_ID                    \n"
      + "         AND sample.ASM_ID       =  asm.ASM_ID                        \n"
      + "         AND sample.SALES_STATUS =  'GENRELEASED'                     \n"
      + "         AND sample.pull_yn = 'N'                                     \n"
      + "         AND sample.INV_STATUS   <> 'RNDREQUEST'                      \n"
      + "         AND sample.INV_STATUS   <> 'CORND'                           \n"
      + "         AND sample.INV_STATUS   <> 'REQUESTED'                       \n"
      + "         AND sample.INV_STATUS   <> 'CHECKEDOUT'                      \n"
      + "         AND sample.INV_STATUS   <> 'ARCOCASEPULL'                    \n"
      + "         AND sample.INV_STATUS   <> 'ARCOCONSREV'                     \n"
      + "         AND sample.INV_STATUS   <> 'ARCOOTHER'                       \n"
      + "         AND sample.INV_STATUS   <> 'COCONSUMED'                      \n"
      + "         AND sample.BOX_BARCODE_ID         IS NOT NULL                \n"
      + "         AND consent.CONSENT_PULL_DATETIME IS NULL                    \n"
      + "         AND (                                                        \n"
      + "              (sample.ALLOCATION_IND  = 'U')                          \n"
      + "               OR                                                     \n"
      + "              ((sample.ALLOCATION_IND = 'R')                          \n"
      + "                AND                                                   \n"
      + "               (sample.ARDAIS_ACCT_KEY = ?)                           \n"
      + "             ))                                                       \n"
      + "         AND NOT EXISTS                                               \n"
      + "            (SELECT 1 FROM ILTDS_REVOKED_CONSENT_ARCHIVE revoked      \n"
      + "                      WHERE revoked.consent_id = consent.consent_id)  \n"
      + "         AND EXISTS                                                   \n"
      + "            (SELECT 1 FROM iltds_box_location y                       \n"
      + "                      WHERE sample.box_barcode_id = y.box_barcode_id) \n";

  // This comparator is used to order the samples in the sample
  // summary query, in the following order:
  // 1. frozen
  // 2. paraffin that have not been examined to determine whether they can be divided
  // 3. paraffin that can be divided but are not yet divided
  // 4. paraffin that cannot be divided
  //
  // Note: This comparator imposes orderings that are inconsistent with equals.
  static final Comparator SAMPLE_SUMMARY_ORDER = new Comparator() {
    private HashMap _divided = new HashMap();
    private HashMap _format = new HashMap();
    {
      _format.put(null, new Integer(0));
      _format.put(FormLogic.SMPL_FR_FORMAT, new Integer(1));
      _format.put(FormLogic.SMPL_PA_FORMAT, new Integer(2));

      _divided.put(null, new Integer(0));
      _divided.put(FormLogic.DIVIDED_UNKNOWN, new Integer(1));
      _divided.put(FormLogic.DIVIDED_YES, new Integer(2));
      _divided.put(FormLogic.DIVIDED_NO, new Integer(3));
    }
    public int compare(Object o1, Object o2) {
      SampleDataBean s1 = (SampleDataBean) o1;
      SampleDataBean s2 = (SampleDataBean) o2;

      // If the samples have a different format, then return
      // the result based on that comparison.
      Integer s1Format = (Integer) _format.get(s1.getFormat());
      Integer s2Format = (Integer) _format.get(s2.getFormat());
      int sameFormat = s1Format.compareTo(s2Format);
      if (sameFormat != 0)
        return sameFormat;

      // At this point we know we have 2 samples of the same format.
      // If they are both FROZEN, then return 0.
      if (s1Format.equals(FormLogic.SMPL_FR_FORMAT))
        return 0;

      // At this point we know we have 2 PARAFFIN samples.  Use whether
      // the sample can be divided to determine equality.
      Integer s1Divided = (Integer) _divided.get(s1.getCanBeDivided());
      Integer s2Divided = (Integer) _divided.get(s2.getCanBeDivided());
      return s1Divided.compareTo(s2Divided);
    }
  };

  /**
   * Insert the method's description here.
   * Creation date: (2/18/2002 1:42:19 PM)
   * @param samples java.util.Collection
   */
  public void addSamplesToProject(String[] samples, String lineItemId, String projectId) {
    int count = samples.length;
    for (int i = 0; i < count; i++) {
      String sampleId = samples[i];
      try {
        SamplestatusAccessBean status = new SamplestatusAccessBean();
        status.setInit_argSample(new SampleKey(sampleId));
        status.setInit_argSample_status_datetime(
          new java.sql.Timestamp(new java.util.Date().getTime()));
        status.setInit_argStatus_type_code(FormLogic.PRJ_ADDED);
        status.commitCopyHelper();

        ProjectsampleAccessBean pSample = new ProjectsampleAccessBean();
        pSample.setInit_argLineitem(new LineitemKey(lineItemId));
        pSample.setInit_argProject(new ProjectKey(projectId));
        pSample.setInit_argSamplebarcodeid(sampleId);
        pSample.commitCopyHelper();
      }
      catch (javax.ejb.CreateException e) {
        throw new ApiException(e);
      }
      catch (java.rmi.RemoteException e) {
        throw new ApiException(e);
      }
      catch (javax.ejb.FinderException e) {
        throw new ApiException(e);
      }
      catch (javax.naming.NamingException e) {
        throw new ApiException(e);
      }
    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (2/15/2001 3:44:28 PM)
   * @return java.util.Vector
   */
  public List availableInvQuery(QueryBean query, SecurityInfo securityInfo) {

      SampleQueryBuilder queryBuilder = new SampleQueryBuilder();

      queryBuilder.selectQcVerified();
      queryBuilder.selectAllocation();
      queryBuilder.selectArdaisId();
      queryBuilder.selectConsentId();
      queryBuilder.selectAsmPosition();
      queryBuilder.selectSampleId();
      queryBuilder.selectSampleDiagnosisCode();
      queryBuilder.selectSampleTissueCode();
      queryBuilder.selectAsmAppearance();
      queryBuilder.selectLimsMicroscopic();
      queryBuilder.selectLimsComments();
      queryBuilder.selectLimsInternalComments();
  
      queryBuilder.whereConsentNotPulled();
      queryBuilder.whereConsentNotRevoked();
      queryBuilder.whereNotInProject();

      queryBuilder.whereInRepository();
      queryBuilder.wherePulledSample();
      //  note: the number in the next call must equal the number
      //  of inventory statuses that are specified as "badInvStatuses"...
      queryBuilder.whereInventoryStatusNotEqual(9); 
      List badInvStatuses = new ArrayList();
      badInvStatuses.add(FormLogic.SMPL_RNDREQUEST);
      badInvStatuses.add(FormLogic.SMPL_CORND);
      badInvStatuses.add(FormLogic.SMPL_ARCOCASEPULL);
      badInvStatuses.add(FormLogic.SMPL_ARCOCONSREV);
      badInvStatuses.add(FormLogic.SMPL_ARCOOTHER);
      badInvStatuses.add(FormLogic.SMPL_COCONSUMED);
      // next two inventory statuses added for MR 6972 -- remote repositories
      badInvStatuses.add(FormLogic.SMPL_REQUESTED);
      badInvStatuses.add(FormLogic.SMPL_CHECKEDOUT); 
      badInvStatuses.add(FormLogic.SMPL_TRANSFER);
      queryBuilder.bindInventoryStatusNotEqual(badInvStatuses);
      String client = query.getAccountID();
      if ((client != null) && (!client.equals("ARD0000001"))) { // leaving this since only PTS MR 8436
        queryBuilder.whereUnrestrictedOrRestrictedForAccount();
        queryBuilder.bindUnrestrictedOrRestrictedForAccount(client);
      }
      // MR 6693, item #2 logical repository included if appropriate
      if (!securityInfo.isHasPrivilege(SecurityInfo.PRIV_VIEW_ALL_LOGICAL_REPOS)) {
        queryBuilder.whereLogicalRepository();
        queryBuilder.bindLogicalRepository(securityInfo.getUsername());
      }

      // Shopping cart specified.
      if ((query.getShoppingCartUserId() != null) && (!query.getShoppingCartUserId().equals(""))) {
        queryBuilder.whereInShoppingCart();
        queryBuilder.bindInShoppingCart(query.getShoppingCartUserId());
      }
      else {      
        queryBuilder.whereSalesStatusEqual();
        queryBuilder.bindSalesStatusEqual(FormLogic.SMPL_GENRELEASED);
  
        // Case ID(s) specified.
        if (!query.getCaseList().isEmpty()) {
            queryBuilder.whereConsentEquals(query.getCaseList().size());
            queryBuilder.bindConsentEquals(query.getCaseList());
          }
  
        // Sample ID(s) specified.
        else if (!query.getSampleList().isEmpty()) {
            queryBuilder.whereSampleEquals(query.getSampleList().size());
            queryBuilder.bindSampleEquals(query.getSampleList());
            }
      }


    return queryBuilder.performQuery();

  }
  /**
   * Builds and returns the query used to get samples that can be removed
   * from a project.
   *
   * @return  A <code>SampleQueryBuilder</code> that contains the query.
   */
  public SampleQueryBuilder buildSamplesToRemoveQuery() {

    SampleQueryBuilder queryBuilder = new SampleQueryBuilder();

    queryBuilder.selectQcVerified();
    queryBuilder.selectAllocation();
    queryBuilder.selectArdaisId();
    queryBuilder.selectConsentId();
    queryBuilder.selectAsmPosition();
    queryBuilder.selectSampleId();
    queryBuilder.selectConsentDiagnosisCode();
    queryBuilder.selectAsmTissueCode();
    queryBuilder.selectAsmAppearance();
    queryBuilder.selectLimsMicroscopic();
    queryBuilder.selectLimsComments();
    queryBuilder.selectLimsInternalComments();
    queryBuilder.selectLineItemId();

    queryBuilder.whereProject();
    queryBuilder.whereNotOnHold();

    return queryBuilder;
  }
  /**
   * Creates a new line item, using the attributes specified in the
   * supplied <code>LineItemDataBean</code>.  The line item id is generated
   * from a sequence, prefixed by "LI".
   *
   * @param  dataBean  a <code>LineItemDataBean</code> that holds the
   *		attributes of the new line item
   * @return  A <code>LineItemDataBean</code> that holds the
   *		attributes of the line item that was created.
   */
  public LineItemDataBean createLineItem(LineItemDataBean dataBean) {
    // Get the next project id from the sequence.
    String nextId = null;
    BigDecimal nextLineItemNumber = null;
    String nextIdQuery =
      "SELECT LPAD(TO_CHAR(" + FormLogic.SEQ_PTS_LINEITEMID + ".NEXTVAL), 10, '0') FROM DUAL";
    String nextLineItemNumberQuery =
      "SELECT MAX(lineitemnumber) FROM pts_lineitem WHERE projectid = ?";
    Connection con = ApiFunctions.getDbConnection();
    try {
      PreparedStatement pstmt = con.prepareStatement(nextIdQuery);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        nextId = FormLogic.PREFIX_LINEITEM + rs.getString(1);
        rs.close();
      }
      else {
        throw new ApiException("Error getting next line item id.");
      }

      pstmt = con.prepareStatement(nextLineItemNumberQuery);
      pstmt.setString(1, dataBean.getProjectId());
      rs = pstmt.executeQuery();
      if (rs.next()) {
        nextLineItemNumber = rs.getBigDecimal(1);
        nextLineItemNumber =
          (nextLineItemNumber == null)
            ? new BigDecimal("1")
            : nextLineItemNumber.add(new BigDecimal("1"));
        rs.close();
      }
      else {
        throw new ApiException("Error getting next line item number.");
      }
    }
    catch (SQLException e) {
      throw new ApiException("Error getting next line item id or number.", e);
    }
    finally {
      ApiFunctions.closeDbConnection(con);
    }

    // Create the line item.
    LineitemAccessBean lineItem = new LineitemAccessBean();
    lineItem.setInit_argLineitemid(nextId);
    lineItem.setInit_argLineitemnumber(nextLineItemNumber);
    lineItem.setInit_argProjectid(dataBean.getProjectId());
    Integer quantity = dataBean.getQuantity();
    if (quantity != null) {
      lineItem.setInit_argQuantity(new BigDecimal(quantity.doubleValue()));
    }
    lineItem.setComments(dataBean.getComments());
    lineItem.setFormat(dataBean.getFormat());
    lineItem.setNotes(dataBean.getNotes());

    try {
      lineItem.commitCopyHelper();
    }
    catch (NamingException e) {
      throw new ApiException("Problem creating line item", e);
    }
    catch (RemoteException e) {
      throw new ApiException("Problem creating line item", e);
    }
    catch (CreateException e) {
      throw new ApiException("Problem creating line item", e);
    }
    catch (FinderException e) {
      throw new ApiException("Problem creating line item", e);
    }

    // Return a data bean that represents the new line item.
    LineItemDataBean newDataBean = new LineItemDataBean(dataBean);
    newDataBean.setId(nextId);
    return newDataBean;
  }
  /**
   * Creates a new project, using the attributes specified in the
   * supplied <code>ProjectDataBean</code>.  The project id is generated
   * from a sequence, prefixed by "PR".
   *
   * @param  dataBean  a <code>ProjectDataBean</code> that holds the
   *		attributes of the new project
   * @return  A <code>ProjectDataBean</code> that holds the
   *		attributes of the project that was created.
   */
  public ProjectDataBean createProject(ProjectDataBean dataBean) throws DuplicateKeyException {
    // Get the next project id from the sequence.
    String nextId = null;
    String nextIdQuery =
      "SELECT LPAD(TO_CHAR(" + FormLogic.SEQ_PTS_PROJECTID + ".NEXTVAL), 10, '0') FROM DUAL";
    Connection con = ApiFunctions.getDbConnection();
    try {
      PreparedStatement pstmt = con.prepareStatement(nextIdQuery);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        nextId = FormLogic.PREFIX_PROJECT + rs.getString(1);
        rs.close();
      }
      else {
        throw new ApiException("Error getting next project id.");
      }
    }
    catch (SQLException e) {
      throw new ApiException("Error getting next project id.", e);
    }
    finally {
      ApiFunctions.closeDbConnection(con);
    }

    // Create the project.
    ProjectAccessBean project = new ProjectAccessBean();
    project.setInit_argProjectid(nextId);
    project.setInit_projectName(dataBean.getProjectName());
    project.setInit_client(dataBean.getClient());
    project.setInit_requestedBy(dataBean.getRequestedBy());
    java.sql.Date date = dataBean.getRequestDate();
    if (date != null) {
      project.setInit_requestDate(new Timestamp(date.getTime()));
    }
    date = dataBean.getApprovedDate();
    if (date != null) {
      project.setDateapproved(new Timestamp(date.getTime()));
    }
    date = dataBean.getShippedDate();
    if (date != null) {
      project.setDateshipped(new Timestamp(date.getTime()));
    }
    project.setNotes(dataBean.getNotes());
    Integer percent = dataBean.getPercentComplete();
    if (percent != null) {
      project.setPercentcomplete(new BigDecimal(percent.doubleValue()));
    }
    project.setStatus(dataBean.getStatus());

    try {
      project.commitCopyHelper();
    }
    catch (DuplicateKeyException e) {
      throw e;
    }
    catch (NamingException e) {
      throw new ApiException("Problem creating/updating project", e);
    }
    catch (RemoteException e) {
      throw new ApiException("Problem creating/updating project", e);
    }
    catch (CreateException e) {
      throw new ApiException("Problem creating/updating project", e);
    }
    catch (FinderException e) {
      throw new ApiException("Problem creating/updating project", e);
    }

    // Create the standard workorders that are a part of every project.
    WorkOrderDataBean woBean = new WorkOrderDataBean();
    woBean.setType(FormLogic.WOTYPE_PD);
    woBean.setProjectId(nextId);
    woBean.setWorkOrderName("Project Definition");
    createWorkOrder(woBean);

    woBean.setType(FormLogic.WOTYPE_PR);
    woBean.setProjectId(nextId);
    woBean.setWorkOrderName("Project Review");
    createWorkOrder(woBean);

    woBean.setType(FormLogic.WOTYPE_POA);
    woBean.setProjectId(nextId);
    woBean.setWorkOrderName("PO Assembly");
    createWorkOrder(woBean);

    woBean.setType(FormLogic.WOTYPE_SHIP);
    woBean.setProjectId(nextId);
    woBean.setWorkOrderName("Shipment");
    createWorkOrder(woBean);

    woBean.setType(FormLogic.WOTYPE_INV);
    woBean.setProjectId(nextId);
    woBean.setWorkOrderName("Invoice");
    createWorkOrder(woBean);

    // Return a data bean representing the new project.
    ProjectDataBean newDataBean = new ProjectDataBean(dataBean);
    newDataBean.setId(nextId);
    return newDataBean;
  }
  /**
   * Creates a new work order, using the attributes specified in the
   * supplied <code>WorkOrderDataBean</code>.  The work order id is generated
   * from a sequence, prefixed by "WK".
   *
   * @param  dataBean  a <code>WorkOrderDataBean</code> that holds the
   *		attributes of the new work order
   * @return  A <code>WorkOrderDataBean</code> that holds the
   *		attributes of the work order that was created.
   */
  public WorkOrderDataBean createWorkOrder(WorkOrderDataBean dataBean) {
    // Get the next work order id from the sequence.
    String nextId = null;
    BigDecimal nextListOrder = null;
    String nextIdQuery =
      "SELECT LPAD(TO_CHAR(" + FormLogic.SEQ_PTS_WORKORDERID + ".NEXTVAL), 10, '0') FROM DUAL";
    String nextListOrderQuery =
      "SELECT MAX(listorder) FROM pts_workorder WHERE projectid = ? and workordertype = ?";
    Connection con = ApiFunctions.getDbConnection();
    try {
      PreparedStatement pstmt = con.prepareStatement(nextIdQuery);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        nextId = FormLogic.PREFIX_WORKORDER + rs.getString(1);
        rs.close();
      }
      else {
        throw new ApiException("Error getting next Work Order id.");
      }

      pstmt = con.prepareStatement(nextListOrderQuery);
      pstmt.setString(1, dataBean.getProjectId());
      pstmt.setString(2, dataBean.getType());
      rs = pstmt.executeQuery();
      if (rs.next()) {
        nextListOrder = rs.getBigDecimal(1);
        nextListOrder =
          (nextListOrder == null) ? new BigDecimal("1") : nextListOrder.add(new BigDecimal("1"));
        rs.close();
      }
      else {
        throw new ApiException("Error getting next list order number for work order.");
      }
    }
    catch (SQLException e) {
      throw new ApiException("Error getting next work order id.", e);
    }
    finally {
      ApiFunctions.closeDbConnection(con);
    }

    // Create the work order.
    WorkorderAccessBean workOrder = new WorkorderAccessBean();
    workOrder.setInit_argWorkorderid(nextId);
    workOrder.setInit_argListorder(nextListOrder);
    workOrder.setInit_argProjectid(dataBean.getProjectId());
    workOrder.setInit_argWorkordername(dataBean.getWorkOrderName());
    workOrder.setInit_argWorkordertype(dataBean.getType());
    workOrder.setNotes(dataBean.getNotes());
    java.sql.Date date = dataBean.getEndDate();
    if (date != null) {
      workOrder.setEnddate(new Timestamp(date.getTime()));
    }
    Integer numSamples = dataBean.getNumberOfSamples();
    if (numSamples != null) {
      workOrder.setNumberofsamples(new BigDecimal(numSamples.doubleValue()));
    }
    Integer percentComplete = dataBean.getPercentComplete();
    if (percentComplete != null) {
      workOrder.setPercentcomplete(new BigDecimal(percentComplete.doubleValue()));
    }
    date = dataBean.getStartDate();
    if (date != null) {
      workOrder.setStartdate(new Timestamp(date.getTime()));
    }
    workOrder.setStatus(dataBean.getStatus());

    try {
      workOrder.commitCopyHelper();
    }
    catch (NamingException e) {
      throw new ApiException("Problem creating/updating work order", e);
    }
    catch (RemoteException e) {
      throw new ApiException("Problem creating/updating work order", e);
    }
    catch (CreateException e) {
      throw new ApiException("Problem creating/updating work order", e);
    }
    catch (FinderException e) {
      throw new ApiException("Problem creating/updating work order", e);
    }

    // Return a data bean representing the new work order.
    WorkOrderDataBean newDataBean = new WorkOrderDataBean(dataBean);
    newDataBean.setId(nextId);
    return newDataBean;
  }

  /**
   * Insert the method's description here.
   * Creation date: (5/15/2001 2:46:36 PM)
   * @return java.util.Vector
   * @param geoLoc java.lang.String
   * @exception com.ardais.bigr.api.ApiException The exception description.
   */
  public List getCompleteSampleInfo(String lineItemId) {

    SampleQueryBuilder queryBuilder = new SampleQueryBuilder();

    queryBuilder.selectQcVerified();
    queryBuilder.selectAllocation();
    queryBuilder.selectArdaisId();
    queryBuilder.selectConsentId();
    queryBuilder.selectAsmPosition();
    queryBuilder.selectSampleId();
    queryBuilder.selectConsentDiagnosisCode();
    queryBuilder.selectAsmTissueCode();
    queryBuilder.selectAsmAppearance();
    queryBuilder.selectLimsMicroscopic();
    queryBuilder.selectLimsComments();
    queryBuilder.selectLimsInternalComments();

    queryBuilder.whereLineItem();

    queryBuilder.bindLineItemId(lineItemId);

    return queryBuilder.performQuery();

  }
  /**
   * Retrieves a line item given its id.  If there is no line item with
   * the given id, then returns <code>null</code>.
   *
   * @param  lineItemId  the id of the line item
   * @return  A <code>LineItemDataBean</code> that holds the
   *		attributes of the line item that was retrieved.
   */
  public LineItemDataBean getLineItem(String lineItemId) {
    String sql = "SELECT * FROM PTS_LINEITEM l WHERE l.lineitemid = ?";
    Connection con = ApiFunctions.getDbConnection();
    try {
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, lineItemId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        LineItemDataBean dataBean = new LineItemDataBean(rs);
        rs.close();
        return dataBean;
      }
      else {
        return null;
      }
    }
    catch (SQLException e) {
      throw new ApiException("Error getting line item " + lineItemId, e);
    }
    finally {
      ApiFunctions.closeDbConnection(con);
    }
  }
  /**
   * Retrieves a project's line items, given the project's id.
   * If there are no line items in the project, an empty list
   * is returned.
   *
   * @param  projectId  the id of the project containing the line items
   * @return  A <code>List</code> of line item data beans.
   */
  public List getLineItems(String projectId) {
    String sql = "SELECT * FROM PTS_LINEITEM m WHERE m.projectid = ? ORDER BY m.lineitemnumber";

    StringBuffer countSql = new StringBuffer(256);
    countSql.append("SELECT m.lineitemnumber, COUNT(s.sample_barcode_id)");
    countSql.append(" FROM pts_sample s, pts_lineitem m");
    countSql.append(" WHERE m.projectid = ?");
    countSql.append(" AND m.lineitemid = s.lineitemid(+)");
    countSql.append(" GROUP BY m.lineitemnumber");

    Connection con = ApiFunctions.getDbConnection();
    try {
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, projectId);
      ResultSet rs = pstmt.executeQuery();
      ArrayList lineItems = new ArrayList();
      while (rs.next()) {
        LineItemDataBean dataBean = new LineItemDataBean(rs);
        lineItems.add(dataBean);
      }
      rs.close();

      pstmt = con.prepareStatement(countSql.toString());
      pstmt.setString(1, projectId);
      rs = pstmt.executeQuery();
      for (int i = 0; rs.next(); i++) {
        LineItemDataBean dataBean = (LineItemDataBean) lineItems.get(i);
        dataBean.setSelectedQuantity(new Integer(rs.getInt(2)));
      }
      rs.close();

      return lineItems;
    }
    catch (SQLException e) {
      throw new ApiException("Error getting line items for project " + projectId, e);
    }
    finally {
      ApiFunctions.closeDbConnection(con);
    }
  }
  /**
   * Returns a project given its id.  If there is no project with
   * the given id, then returns <code>null</code>.
   *
   * @param  projectId  the id of the project
   * @return  A <code>ProjectDataBean</code> representing the project.
   */
  public ProjectDataBean getProject(String projectId) {
    String sql = "SELECT * FROM PTS_PROJECT p WHERE p.projectid = ?";
    Connection con = ApiFunctions.getDbConnection();
    try {
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, projectId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        ProjectDataBean dataBean = new ProjectDataBean(rs);
        rs.close();
        return dataBean;
      }
      else {
        return null;
      }
    }
    catch (SQLException e) {
      throw new ApiException("Error getting project " + projectId, e);
    }
    finally {
      ApiFunctions.closeDbConnection(con);
    }
  }
  /**
   * Returns a project given its name.  If there is no project with
   * the given name, then returns <code>null</code>.
   *
   * @param  projectName  the name of the project
   * @return  A <code>ProjectDataBean</code> representing the project.
   */
  public ProjectDataBean getProjectByName(String projectName) {
    String sql = "SELECT * FROM PTS_PROJECT p WHERE p.projectname = ?";
    Connection con = ApiFunctions.getDbConnection();
    try {
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, projectName);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        ProjectDataBean dataBean = new ProjectDataBean(rs);
        rs.close();
        return dataBean;
      }
      else {
        return null;
      }
    }
    catch (SQLException e) {
      throw new ApiException("Error getting project " + projectName, e);
    }
    finally {
      ApiFunctions.closeDbConnection(con);
    }
  }
  /**
   * Retrieves a list of projects for the account specified by the
   * account key.  If there are no such projects, an empty list
   * is returned.
   *
   * @param  accountKey  the Ardais account key of the client
   * @return  A <code>List</code> of projects.
   */
  public List getProjects(String accountKey) {
    String sql = "SELECT * FROM pts_project p WHERE p.ardais_acct_key = ? ORDER BY p.projectid";
    Connection con = ApiFunctions.getDbConnection();
    try {
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, accountKey);
      ResultSet rs = pstmt.executeQuery();
      ArrayList projects = new ArrayList();
      while (rs.next()) {
        ProjectDataBean dataBean = new ProjectDataBean(rs);
        projects.add(dataBean);
      }
      rs.close();
      return projects;
    }
    catch (SQLException e) {
      throw new ApiException("Error getting projects for client " + accountKey, e);
    }
    finally {
      ApiFunctions.closeDbConnection(con);
    }
  }
  /**
   * Retrieves all of project's samples that are on hold, given the 
   * project's id.  If there are no such samples in the project, an empty list
   * is returned.  Full information is returned for each sample.
   *
   * @param  projectId  the id of the project
   * @return  A <code>List</code> of <code>SampleDataBean</code>s.
   */
  public List getSamplesOnHold(String projectId) {

    SampleQueryBuilder queryBuilder = new SampleQueryBuilder();

    queryBuilder.selectQcVerified();
    queryBuilder.selectAllocation();
    queryBuilder.selectArdaisId();
    queryBuilder.selectConsentId();
    queryBuilder.selectAsmPosition();
    queryBuilder.selectSampleId();
    queryBuilder.selectConsentDiagnosisCode();
    queryBuilder.selectAsmTissueCode();
    queryBuilder.selectAsmAppearance();
    queryBuilder.selectLimsMicroscopic();
    queryBuilder.selectLimsComments();
    queryBuilder.selectLimsInternalComments();
    queryBuilder.selectCartAccount();
    queryBuilder.selectCartUser();

    queryBuilder.whereProject();
    queryBuilder.whereOnHold();

    queryBuilder.bindProjectId(projectId);

    return queryBuilder.performQuery();

  }
  /**
   * Retrieves all of project's samples that are not on hold, given the 
   * project's id.  If there are no such samples in the project, an empty list
   * is returned.  Full information is returned for each sample.
   *
   * @param  projectId  the id of the project
   * @return  A <code>List</code> of <code>SampleDataBean</code>s.
   */
  public List getSamplesToAddToHold(String projectId) {
    SampleQueryBuilder queryBuilder = new SampleQueryBuilder();

    queryBuilder.selectQcVerified();
    queryBuilder.selectAllocation();
    queryBuilder.selectArdaisId();
    queryBuilder.selectConsentId();
    queryBuilder.selectAsmPosition();
    queryBuilder.selectSampleId();
    queryBuilder.selectConsentDiagnosisCode();
    queryBuilder.selectAsmTissueCode();
    queryBuilder.selectAsmAppearance();
    queryBuilder.selectLimsMicroscopic();
    queryBuilder.selectLimsComments();
    queryBuilder.selectLimsInternalComments();
    queryBuilder.selectLineItemId();

    queryBuilder.whereProject();
    queryBuilder.whereInventoryStatusNotEqual();
    queryBuilder.whereSalesStatusEqual();
    queryBuilder.whereNotOnHold();
    queryBuilder.whereInRepository();

    queryBuilder.bindProjectId(projectId);
    queryBuilder.bindInventoryStatusNotEqual(FormLogic.SMPL_RNDREQUEST);
    // inventory statuses added for MR 6972 -- remote repositories
    queryBuilder.bindInventoryStatusNotEqual(FormLogic.SMPL_REQUESTED);
    queryBuilder.bindSalesStatusEqual(FormLogic.SMPL_GENRELEASED);

    return queryBuilder.performQuery();

  }
  /**
   * Retrieves all of project's samples that are not on hold, given the 
   * project's id.  If there are no such samples in the project, an empty list
   * is returned.  Full information is returned for each sample.
   *
   * @param  projectId  the id of the project
   * @return  A <code>List</code> of <code>SampleDataBean</code>s.
   */
  public List getSamplesToRemove(String projectId) {

    SampleQueryBuilder queryBuilder = buildSamplesToRemoveQuery();
    queryBuilder.bindProjectId(projectId);
    return queryBuilder.performQuery();
  }

  /**
   * Returns the total number of samples in a project, given the project's id.
   *
   * @param  projectId  the id of the project
   * @return  The total number of samples in the project.
   */
  public int getTotalSamples(String projectId) {
    StringBuffer sql = new StringBuffer(512);
    sql.append("SELECT count(ps.sample_barcode_id)");
    sql.append(" FROM pts_sample ps");
    sql.append(" WHERE ps.projectid = ?");

    Connection con = ApiFunctions.getDbConnection();
    try {
      PreparedStatement pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, projectId);
      ResultSet rs = pstmt.executeQuery();
      int count = 0;
      while (rs.next()) {
        count = rs.getInt(1);
      }
      rs.close();
      return count;
    }
    catch (SQLException e) {
      throw new ApiException("Error getting sample count for project " + projectId, e);
    }
    finally {
      ApiFunctions.closeDbConnection(con);
    }
  }
  /**
   * Returns the total number of samples in a project that are on hold, 
   * given the project's id.
   *
   * @param  projectId  the id of the project
   * @return  The total number of samples on hold in the project.
   */
  public int getTotalSamplesOnHold(String projectId) {
    StringBuffer sql = new StringBuffer(512);
    sql.append("SELECT count(ps.sample_barcode_id)");
    sql.append(" FROM pts_sample ps,");
    sql.append("es_shopping_cart_detail cart");
    sql.append(" WHERE ps.projectid = ?");
    sql.append(" AND ps.sample_barcode_id = cart.barcode_id");

    Connection con = ApiFunctions.getDbConnection();
    try {
      PreparedStatement pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, projectId);
      ResultSet rs = pstmt.executeQuery();
      int count = 0;
      while (rs.next()) {
        count = rs.getInt(1);
      }
      rs.close();
      return count;
    }
    catch (SQLException e) {
      throw new ApiException("Error getting sample count for project " + projectId, e);
    }
    finally {
      ApiFunctions.closeDbConnection(con);
    }
  }
  /**
   * Retrieves a work order given its id.  If there is no work order with
   * the given id, then returns <code>null</code>.
   *
   * @param  workOrderId  the id of the work order
   * @return  A <code>WorkOrderDataBean</code> that holds the
   *		attributes of the work order that was retrieved.
   */
  public WorkOrderDataBean getWorkOrder(String workOrderId) {
    String sql = "SELECT * FROM PTS_WORKORDER w WHERE w.workorderid = ?";
    Connection con = ApiFunctions.getDbConnection();
    try {
      PreparedStatement pstmt = con.prepareStatement(sql);
      pstmt.setString(1, workOrderId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        WorkOrderDataBean dataBean = new WorkOrderDataBean(rs);
        rs.close();
        return dataBean;
      }
      else {
        return null;
      }
    }
    catch (SQLException e) {
      throw new ApiException("Error getting work order " + workOrderId, e);
    }
    finally {
      ApiFunctions.closeDbConnection(con);
    }
  }
  /**
   * Retrieves a project's work orders, given the project's id.
   * If there are no work orders in the project, an empty list
   * is returned.
   *
   * @param  projectId  the id of the project containing the work orders
   * @return  A <code>List</code> of work order data beans.
   */
  public List getWorkOrders(String projectId) {
    StringBuffer sql = new StringBuffer(128);
    sql.append("SELECT w.*");
    sql.append(" FROM pts_workorder w, ard_lookup_all a");
    sql.append(" WHERE w.projectid = ?");
    sql.append(" AND w.workordertype = a.lookup_cd");
    sql.append(" ORDER BY a.lookup_cd_list_order, w.listorder");

    Connection con = ApiFunctions.getDbConnection();
    try {
      PreparedStatement pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, projectId);
      ResultSet rs = pstmt.executeQuery();
      ArrayList workOrders = new ArrayList();
      while (rs.next()) {
        WorkOrderDataBean dataBean = new WorkOrderDataBean(rs);
        workOrders.add(dataBean);
      }
      rs.close();
      return workOrders;
    }
    catch (SQLException e) {
      throw new ApiException("Error getting line items for project " + projectId, e);
    }
    finally {
      ApiFunctions.closeDbConnection(con);
    }
  }

  /**
   * Add the specified samples to the specified user's hold list.  If there is a problem
   * adding one or more of the samples, the returned string will be non-empty and will
   * contain a message to display to the user.
   * 
   * @param samples
   * @param user
   * @param account
   * @return
   */
  public String addSamplesToHoldForProject(String samples[], String user, String account) {
    boolean error = false;
    String errorSamples = "";

    try {
      // Find or create user's shopping cart
      ShoppingcartAccessBean shoppingCart = retrieveCart(user, account);
      ArdaisuserKey userKey = shoppingCart.getArdaisuserKey();

      // Get next hold list line number
      int nextLine = getNextHoldListLine(user, account);

      // Place all samples into the hold list.
      for (int i = 0; i < samples.length; i++) {
        boolean isSampleAvailable = sampleAvailableForProject(account, samples[i]);
        if (isSampleAvailable) {
          // Place samples on hold
          insertAddToCartStatus(samples[i]);

          // Populate the detial rows.
          ShoppingcartdetailAccessBean shoppingCartDetail = new ShoppingcartdetailAccessBean();

          String productType =
            samples[i].startsWith("RN")
              ? com.ardais.bigr.es.helpers.FormLogic.RNA_PRODUCT_TYPE
              : com.ardais.bigr.es.helpers.FormLogic.TISSUE_PRODUCT_TYPE;
          shoppingCartDetail.setInit_shopping_cart_line_number(new BigDecimal(nextLine++));
          shoppingCartDetail.setInit_userId(userKey.ardais_user_id);
          shoppingCartDetail.setInit_acctId(userKey.ardaisaccount_ardais_acct_key);
          shoppingCartDetail.setInit_barcode_id(samples[i]);
          ShoppingcartKey cartkey = (ShoppingcartKey) shoppingCart.getEJBRef().getPrimaryKey();
          shoppingCartDetail.setInit_cartNo(cartkey.shopping_cart_id);
          shoppingCartDetail.setInit_productType(productType);

          shoppingCartDetail.setCreation_dt(new java.sql.Timestamp((new Date()).getTime()));

          // Find current estimated price for the sample.  We no longer do pricing and
          // set price = 0 to indicate "price not available".
          shoppingCartDetail.setShopping_cart_line_amount(BigDecimal.valueOf(0));

          shoppingCartDetail.commitCopyHelper();
        }
        else {
          errorSamples += samples[i] + " ";
          error = true;
        }
      }

    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    if (error)
      return errorSamples;
    else
      return null;
  }

  /**
   * Return the next available hold-list line number for the specified user/account.
   * 
   * @param user
   * @param account
   * @return
   */
  private int getNextHoldListLine(String user, String account) throws Exception {
    int nextLine = 0;
    // Find current cart items and line numbers.
    ShoppingcartdetailAccessBean shoppingCartDetail = new ShoppingcartdetailAccessBean();
    AccessBeanEnumeration scdEnum =
      (AccessBeanEnumeration) shoppingCartDetail.findByUserAccountOrderByLineNumber(user, account);

    if (scdEnum.hasMoreElements()) {
      ShoppingcartdetailAccessBean tempDetail =
        (ShoppingcartdetailAccessBean) scdEnum.nextElement();
      nextLine =
        ((ShoppingcartdetailKey) tempDetail.__getKey()).shopping_cart_line_number.intValue() + 1;
    }
    return nextLine;
  }

  private ShoppingcartAccessBean retrieveCart(String user, String account) throws Exception {
    ShoppingcartAccessBean shoppingCart = null;

    try {
      // Find User's Shopping Cart
      shoppingCart =
        new ShoppingcartAccessBean(
          new ShoppingcartKey(
            new java.math.BigDecimal(1),
            new ArdaisuserKey(user, new ArdaisaccountKey(account))));
      shoppingCart.setLast_update_dt(new java.sql.Timestamp((new Date()).getTime()));
      shoppingCart.commitCopyHelper();
    }
    catch (ObjectNotFoundException onf) {
      // If Cart is not found then create one.
      ArdaisuserAccessBean userAB =
        new ArdaisuserAccessBean(new ArdaisuserKey(user, new ArdaisaccountKey(account)));

      shoppingCart = new ShoppingcartAccessBean();

      ArdaisuserKey userKey = (ArdaisuserKey) userAB.getEJBRef().getPrimaryKey();
      shoppingCart.setInit_acctId(userKey.ardaisaccount_ardais_acct_key);
      shoppingCart.setInit_userId(userKey.ardais_user_id);

      shoppingCart.setInit_shopping_cart_id(new java.math.BigDecimal(1));
      shoppingCart.setCart_create_date(new java.sql.Timestamp((new Date()).getTime()));
      shoppingCart.setLast_update_dt(new java.sql.Timestamp((new Date()).getTime()));
      shoppingCart.commitCopyHelper();
    }
    return shoppingCart;
  }

  /**
   * Removes all samples specified by the sample ids in the
   * <code>samples</code> input array from "on hold".
   *
   * @param  samples  an array of the ids of the samples to be taken off hold
   * @param  accounts  an array of the account keys of the samples
   * @param  users  an array of the user ids of the samples
   * @return  The number of samples removed.
   */
  public int removeSamplesFromHold(String[] samples, String[] accounts, String[] users) {
    try {
      int count = samples.length;
      for (int i = 0; i < count; i++) {
        insertGenreleasedStatus(samples[i]);
        ShoppingcartdetailAccessBean shoppingCartDetail = new ShoppingcartdetailAccessBean();
        AccessBeanEnumeration shoppingCartEnum =
          (AccessBeanEnumeration) shoppingCartDetail.findByBarcodeUserAccount(
            samples[i],
            users[i],
            accounts[i]);
        shoppingCartDetail = (ShoppingcartdetailAccessBean) shoppingCartEnum.nextElement();
        shoppingCartDetail.getEJBRef().remove();
      }
      return count;
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
      return 0; // not actually reached, make compiler happy
    }
  }
  /**
   * Removes all samples specified by the sample ids in the
   * <code>sampleIds</code> input array from the project.
   *
   * @param  projectId  the id of the project holding the samples
   * @param  lineItemIds  an array of the ids of the line item for each sample
   * @param  sampleIds  an array of the ids of the samples to be removed
   * @return  The number of samples removed.
   */
  public int removeSamplesFromProject(String projectId, String[] lineItemIds, String[] sampleIds) {
    ProjectKey projKey = new ProjectKey(projectId);
    HashMap liKeys = new HashMap();
    try {
      int count = sampleIds.length;
      for (int i = 0; i < count; i++) {
        LineitemKey liKey = (LineitemKey) liKeys.get(lineItemIds[i]);
        if (liKey == null) {
          liKey = new LineitemKey(lineItemIds[i]);
          liKeys.put(lineItemIds[i], liKey);
        }
        ProjectsampleKey sampleKey = new ProjectsampleKey(sampleIds[i], liKey, projKey);
        ProjectsampleAccessBean sample = new ProjectsampleAccessBean(sampleKey);
        sample.getEJBRef().remove();

        SamplestatusAccessBean statusBean = new SamplestatusAccessBean();
        statusBean.setInit_argSample(new SampleKey(sampleIds[i]));
        statusBean.setInit_argSample_status_datetime(new Timestamp(new java.util.Date().getTime()));
        statusBean.setInit_argStatus_type_code(FormLogic.PRJ_REMOVED);
        statusBean.commitCopyHelper();
      }
      return count;
    }
    catch (javax.ejb.CreateException e) {
      throw new ApiException(e);
    }
    catch (javax.ejb.FinderException e) {
      throw new ApiException(e);
    }
    catch (javax.ejb.RemoveException e) {
      throw new ApiException(e);
    }
    catch (javax.naming.NamingException e) {
      throw new ApiException(e);
    }
    catch (java.rmi.RemoteException e) {
      throw new ApiException(e);
    }
  }

  /**
   * Updates an existing line item, using the attributes specified in the
   * supplied <code>LineItemDataBean</code>.  The <code>LineItemDataBean</code>
   * must specify the key of the line item to be updated.
   *
   * @param  dataBean  a <code>LineItemDataBean</code> that holds the
   *		attributes of the line item to update
   * @return  A <code>LineItemDataBean</code> that holds the
   *		attributes of the project that was updated.
   */
  public LineItemDataBean updateLineItem(LineItemDataBean dataBean) {

    String lineItemId = dataBean.getId();
    if ((lineItemId == null) || (lineItemId.equals(""))) {
      throw new ApiException("Cannot update line item.  Line item id was not specified.");
    }

    LineitemAccessBean lineItem = null;
    try {
      lineItem = new LineitemAccessBean(new LineitemKey(lineItemId));
      lineItem.setInit_argProjectid(dataBean.getProjectId());
      lineItem.setInit_argLineitemid(lineItemId);
      Integer lineItemNumber = dataBean.getLineItemNumber();
      if (lineItemNumber != null) {
        lineItem.setInit_argLineitemnumber(new BigDecimal(lineItemNumber.doubleValue()));
      }
      else {
        lineItem.setInit_argLineitemnumber(null);
      }
      Integer quantity = dataBean.getQuantity();
      if (quantity != null) {
        lineItem.setInit_argQuantity(new BigDecimal(quantity.doubleValue()));
        lineItem.setQuantity(new BigDecimal(quantity.doubleValue()));
      }
      else {
        lineItem.setInit_argQuantity(null);
        lineItem.setQuantity(null);
      }
      lineItem.setComments(dataBean.getComments());
      lineItem.setFormat(dataBean.getFormat());
      lineItem.setNotes(dataBean.getNotes());
      lineItem.commitCopyHelper();
    }
    catch (NamingException e) {
      throw new ApiException("Problem updating line item", e);
    }
    catch (RemoteException e) {
      throw new ApiException("Problem updating line item", e);
    }
    catch (FinderException e) {
      throw new ApiException("Problem updating line item", e);
    }
    catch (CreateException e) {
      throw new ApiException("Problem updating line item", e);
    }

    // Return a data bean representing the line item that was updated.
    return new LineItemDataBean(dataBean);
  }
  /**
   * Updates an existing project, using the attributes specified in the
   * supplied <code>ProjectDataBean</code>.  The <code>ProjectDataBean</code>
   * must specify the key of the project to be updated.
   *
   * @param  dataBean  a <code>ProjectDataBean</code> that holds the
   *		attributes of the project to be updated
   * @return  A <code>ProjectDataBean</code> that holds the
   *		attributes of the project that was updated.
   */
  public ProjectDataBean updateProject(ProjectDataBean dataBean) {

    String projectId = dataBean.getId();
    if ((projectId == null) || (projectId.equals(""))) {
      throw new ApiException("Cannot update project.  Project id was not specified.");
    }

    ProjectAccessBean project = null;
    try {
      project = new ProjectAccessBean(new ProjectKey(projectId));
      project.setInit_argProjectid(projectId);
      project.setInit_projectName(dataBean.getProjectName());
      project.setProjectname(dataBean.getProjectName());
      project.setInit_client(dataBean.getClient());
      project.setArdaisaccountkey(dataBean.getClient());
      project.setInit_requestedBy(dataBean.getRequestedBy());
      project.setArdaisuserid(dataBean.getRequestedBy());
      java.sql.Date date = dataBean.getRequestDate();
      if (date != null) {
        project.setInit_requestDate(new Timestamp(date.getTime()));
        project.setDaterequested(new Timestamp(date.getTime()));
      }
      else {
        project.setInit_requestDate(null);
        project.setDaterequested(null);
      }
      date = dataBean.getApprovedDate();
      if (date != null) {
        project.setDateapproved(new Timestamp(date.getTime()));
      }
      else {
        project.setDateapproved(null);
      }
      date = dataBean.getShippedDate();
      if (date != null) {
        project.setDateshipped(new Timestamp(date.getTime()));
      }
      else {
        project.setDateshipped(null);
      }
      project.setNotes(dataBean.getNotes());
      Integer percent = dataBean.getPercentComplete();
      if (percent != null) {
        project.setPercentcomplete(new BigDecimal(percent.doubleValue()));
      }
      else {
        project.setPercentcomplete(null);
      }
      project.setStatus(dataBean.getStatus());
      project.commitCopyHelper();
    }
    catch (NamingException e) {
      throw new ApiException("Problem updating project", e);
    }
    catch (RemoteException e) {
      throw new ApiException("Problem updating project", e);
    }
    catch (FinderException e) {
      throw new ApiException("Problem updating project", e);
    }
    catch (CreateException e) {
      throw new ApiException("Problem updating project", e);
    }

    // Return a data bean representing the updated project.
    return new ProjectDataBean(dataBean);
  }
  /**
   * Updates an existing work order, using the attributes specified in the
   * supplied <code>WorkOrderDataBean</code>.  The <code>WorkOrderDataBean</code>
   * must specify the key of the work order to be updated.
   *
   * @param  dataBean  a <code>WorkOrderDataBean</code> that holds the
   *		attributes of the work order to be updated
   * @return  A <code>WorkOrderDataBean</code> that holds the
   *		attributes of the work order that was updated.
   */
  public WorkOrderDataBean updateWorkOrder(WorkOrderDataBean dataBean) {

    String workOrderId = dataBean.getId();
    if ((workOrderId == null) || (workOrderId.equals(""))) {
      throw new ApiException("Cannot update work order.  Work order id was not specified.");
    }

    WorkorderAccessBean workOrder = null;
    try {
      workOrder = new WorkorderAccessBean(new WorkorderKey(workOrderId));
      workOrder.setInit_argWorkorderid(workOrderId);
      Integer listOrder = dataBean.getListOrder();
      if (listOrder != null) {
        workOrder.setInit_argListorder(new BigDecimal(listOrder.doubleValue()));
      }
      else {
        workOrder.setInit_argListorder(null);
      }
      workOrder.setInit_argProjectid(dataBean.getProjectId());
      workOrder.setInit_argWorkordername(dataBean.getWorkOrderName());
      workOrder.setWorkordername(dataBean.getWorkOrderName());
      workOrder.setInit_argWorkordertype(dataBean.getType());
      workOrder.setWorkordertype(dataBean.getType());
      workOrder.setNotes(dataBean.getNotes());
      java.sql.Date date = dataBean.getEndDate();
      if (date != null) {
        workOrder.setEnddate(new Timestamp(date.getTime()));
      }
      else {
        workOrder.setEnddate(null);
      }
      Integer numSamples = dataBean.getNumberOfSamples();
      if (numSamples != null) {
        workOrder.setNumberofsamples(new BigDecimal(numSamples.doubleValue()));
      }
      else {
        workOrder.setNumberofsamples(null);
      }
      Integer percentComplete = dataBean.getPercentComplete();
      if (percentComplete != null) {
        workOrder.setPercentcomplete(new BigDecimal(percentComplete.doubleValue()));
      }
      else {
        workOrder.setPercentcomplete(null);
      }
      date = dataBean.getStartDate();
      if (date != null) {
        workOrder.setStartdate(new Timestamp(date.getTime()));
      }
      else {
        workOrder.setStartdate(null);
      }
      workOrder.setStatus(dataBean.getStatus());
      workOrder.commitCopyHelper();
    }
    catch (NamingException e) {
      throw new ApiException("Problem updating work order", e);
    }
    catch (RemoteException e) {
      throw new ApiException("Problem updating work order", e);
    }
    catch (FinderException e) {
      throw new ApiException("Problem updating work order", e);
    }
    catch (CreateException e) {
      throw new ApiException("Problem updating work order", e);
    }

    // Return a data bean representing the updated order.
    return new WorkOrderDataBean(dataBean);
  }

  private void insertAddToCartStatus(String barcode) throws java.lang.Exception {
    try {
      new SampleAccessBean(new SampleKey(barcode));
    }
    catch (ObjectNotFoundException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    SamplestatusAccessBean sampleStatus = new SamplestatusAccessBean();
    sampleStatus.setInit_argSample(new SampleKey(barcode));
    sampleStatus.setInit_argSample_status_datetime(new Timestamp((new java.util.Date()).getTime()));
    sampleStatus.setInit_argStatus_type_code(FormLogic.SMPL_ADDTOCART);
    sampleStatus.commitCopyHelper();
  }

  private void insertGenreleasedStatus(String barcode) throws java.lang.Exception {
    try {
      new SampleAccessBean(new SampleKey(barcode));
    }
    catch (ObjectNotFoundException e) {
      ApiFunctions.throwAsRuntimeException(e);
    }

    SamplestatusAccessBean sampleStatus = new SamplestatusAccessBean();
    sampleStatus.setInit_argSample(new SampleKey(barcode));
    sampleStatus.setInit_argSample_status_datetime(new Timestamp((new java.util.Date()).getTime()));
    sampleStatus.setInit_argStatus_type_code(FormLogic.SMPL_GENRELEASED);
    sampleStatus.commitCopyHelper();
  }

  private boolean sampleAvailableForProject(String account_id, String sample_id) {
    String queryString =
      "SELECT sample.SAMPLE_BARCODE_ID "
        + "FROM iltds_informed_consent consent, iltds_asm asm, iltds_sample sample "
        + "WHERE "
        + COMMON_WHERE_CLAUSE
        + " AND sample.SAMPLE_BARCODE_ID = ? ";

    boolean available = false;

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, account_id);
      pstmt.setString(2, sample_id);
      rs = ApiFunctions.queryDb(pstmt, con);
      if (rs.next()) {
        available = true;
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return available;
  }

  public void ejbActivate() throws RemoteException {
  }

  public void ejbCreate() throws CreateException, EJBException {
  }

  public void ejbPassivate() throws RemoteException {
  }

  public void ejbRemove() throws RemoteException {
  }

  public SessionContext getSessionContext() {
    return mySessionCtx;
  }

  public void setSessionContext(SessionContext ctx) throws RemoteException {
    mySessionCtx = ctx;
  }
}
