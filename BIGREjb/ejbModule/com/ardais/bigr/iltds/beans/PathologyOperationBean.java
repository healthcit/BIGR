package com.ardais.bigr.iltds.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Vector;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.databeans.ProjectLineItem;
import com.ardais.bigr.iltds.databeans.ShoppingCartLineItem;
import com.ardais.bigr.util.BoxLayoutUtils;
import com.gulfstreambio.gboss.GbossFactory;

/**
 * This is a Session Bean Class
 */
public class PathologyOperationBean implements SessionBean {
  private javax.ejb.SessionContext mySessionCtx = null;
  final static long serialVersionUID = 3206093459760846163L;

  private String getLast2Slides(String sample) throws com.ardais.bigr.api.ApiException {
    String queryString =
      "select slide_id from lims_slide where sample_barcode_id = ? order by slide_id desc";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    int numberOfQcSlides = 0;
    String firstSlide = "";
    String secondSlide = "";
    String tempSlideId;
    boolean done = false;

    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, sample);
      rs = ApiFunctions.queryDb(pstmt, con);

      while (rs.next() && !done) {
        tempSlideId = rs.getString(1);

        if (numberOfQcSlides == 0) {
          secondSlide = firstSlide;
          firstSlide = "<b>" + tempSlideId + "</b>";
          numberOfQcSlides++;
        }
        else if (numberOfQcSlides == 1) {
          secondSlide = "<b>" + tempSlideId + "</b>";
          done = true;
        }

      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    if (ApiFunctions.safeStringLength(firstSlide) > 0
      && ApiFunctions.safeStringLength(secondSlide) > 0) {
      return firstSlide + "; " + secondSlide;
    }
    else if (
      ApiFunctions.safeStringLength(firstSlide) > 0
        && ApiFunctions.safeStringLength(secondSlide) == 0) {
      return firstSlide;
    }
    else
      return "";

  }

  public Vector getSampleLocations(Vector samples) {

    String queryString =
      "SELECT A.BOX_BARCODE_ID, "
        + "A.ROOM_ID, A.UNIT_NAME, A.DRAWER_ID, A.SLOT_ID, B.CELL_REF_LOCATION, "
        + "B.SAMPLE_BARCODE_ID, C.CONSENT_ID, B.ASM_POSITION, E.LOOKUP_TYPE_CD_DESC, B.APPEARANCE_BEST, "
        + "C.CONSENT_LOCATION_ADDRESS_ID, B.BMS_YN, B.FORMAT_DETAIL_CID "
        + "FROM ILTDS_BOX_LOCATION A, ILTDS_SAMPLE B, ILTDS_INFORMED_CONSENT C, ILTDS_ASM D, PDC_LOOKUP E "
        + "WHERE A.BOX_BARCODE_ID = B.BOX_BARCODE_ID "
        + "AND C.CONSENT_ID = D.CONSENT_ID "
        + "AND D.ASM_ID = B.ASM_ID "
        + "AND E.LOOKUP_TYPE_CD (+) = D.ORGAN_SITE_CONCEPT_ID "
        + "AND B.SAMPLE_BARCODE_ID IN (";

    for (int i = 0; i < samples.size(); i++) {
      queryString += " ?";
      //queryString += " '" + samples.get(i) + "'";
      if (i != (samples.size() - 1)) {
        queryString += ",";
      }
    }

    queryString
      += ") ORDER BY C.CONSENT_ID, A.BOX_BARCODE_ID, to_number(B.CELL_REF_LOCATION) ";

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    Vector result = new Vector();
    Vector temp = new Vector();

    try {
      con = ApiFunctions.getDbConnection();
      pstmt = con.prepareStatement(queryString);
      for (int i = 1; i <= samples.size(); i++) {
        pstmt.setString(i, (String) samples.get(i - 1));
      }
      rs = pstmt.executeQuery(); //ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        String boxId = rs.getString(1);
        String cellRef = rs.getString(6);
        String cellName = BoxLayoutUtils.translateCellRef(cellRef, boxId);
        
        temp = new Vector();
        temp.add(boxId);
        temp.add(rs.getString(2));
        temp.add(rs.getString(3));
        temp.add(rs.getString(4));
        temp.add(rs.getString(5));
        temp.add(cellName);
        temp.add(rs.getString(7));
        temp.add(rs.getString(8));
        temp.add(rs.getString(9));
        temp.add(rs.getString(10));
        temp.add(rs.getString(11));
        temp.add(getLast2Slides((String) temp.get(6)));
        temp.add(rs.getString(12));
        temp.add(rs.getString(13));
        String formatCid = rs.getString(14);
        if (formatCid == null) {
          temp.add("N/A");
        }
        else {
          temp.add(GbossFactory.getInstance().getDescription(formatCid));
        }
        result.add(temp);
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(con, pstmt, rs);
    }

    return result;
  }

  public Vector getProjectsAndShoppingCartsForSample(String sampleID) {

    StringBuffer projectQuery = new StringBuffer(100);
    projectQuery.append(
      "select item.lineitemnumber, project.ardais_user_id, project.projectname, ");
    projectQuery.append(
      "project.ardais_acct_key, sample.sample_barcode_id, project.daterequested ");
    projectQuery.append("from pts_lineitem item, pts_project project, pts_sample sample ");
    projectQuery.append(
      "where project.projectid = item.projectid and item.lineitemid = sample.lineitemid ");
    projectQuery.append("and sample.sample_barcode_id = ?");

    StringBuffer shoppingCartQuery = new StringBuffer(100);
    shoppingCartQuery.append(
      "select b.shopping_cart_line_number, a.ardais_user_id, a.shopping_cart_id, ");
    shoppingCartQuery.append("a.ARDAIS_ACCT_KEY, b.BARCODE_ID, b.creation_dt ");
    shoppingCartQuery.append("from es_shopping_cart a, es_shopping_cart_detail b ");
    shoppingCartQuery.append("where a.ARDAIS_USER_ID = b.ARDAIS_USER_ID ");
    shoppingCartQuery.append("and a.SHOPPING_CART_ID = b.SHOPPING_CART_ID ");
    shoppingCartQuery.append("and a.ARDAIS_ACCT_KEY = b.ARDAIS_ACCT_KEY ");
    shoppingCartQuery.append("and b.barcode_id = ?");

    Connection con = null;
    PreparedStatement project = null;
    PreparedStatement shoppingCart = null;
    ResultSet rs = null;

    Vector result = new Vector();

    try {
      con = ApiFunctions.getDbConnection();
      project = con.prepareStatement(projectQuery.toString());
      project.setString(1, sampleID);

      rs = project.executeQuery(); //ApiFunctions.queryDb(pstmt, con);
      while (rs.next()) {
        ProjectLineItem projectItem = new ProjectLineItem();
        projectItem.setLineNumber(rs.getInt("lineitemnumber"));
        projectItem.setArdaisUserId(rs.getString("ardais_user_id"));
        projectItem.setProjectName(rs.getString("projectname"));
        projectItem.setArdaisAccountKey(rs.getString("ardais_acct_key"));
        projectItem.setSampleId(rs.getString("sample_barcode_id"));
        projectItem.setRequestDate(rs.getDate("daterequested"));
        result.add(projectItem);
      }
      rs.close();
      rs = null;
      project.close();
      project = null;

      shoppingCart = con.prepareStatement(shoppingCartQuery.toString());
      shoppingCart.setString(1, sampleID);
      rs = shoppingCart.executeQuery();
      while (rs.next()) {
        ShoppingCartLineItem cartItem = new ShoppingCartLineItem();
        cartItem.setLineNumber(rs.getInt("shopping_cart_line_number"));
        cartItem.setArdaisUserId(rs.getString("ardais_user_id"));
        cartItem.setShoppingCartId(rs.getInt("shopping_cart_id"));
        cartItem.setArdaisAccountKey(rs.getString("ardais_acct_key"));
        cartItem.setSampleId(rs.getString("barcode_id"));
        cartItem.setCreationDate(rs.getDate("creation_dt"));
        result.add(cartItem);
      }
      rs.close();
      rs = null;

    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(rs);
      ApiFunctions.close(project);
      ApiFunctions.close(shoppingCart);
      ApiFunctions.close(con);
    }

    return result;
  }

  public void updateSampleStatus(Vector samples, String status) {
    try {
      SamplestatusAccessBean statusBean;
      Timestamp now = new Timestamp(System.currentTimeMillis());
      for (int i = 0; i < samples.size(); i++) {
        statusBean = new SamplestatusAccessBean();
        statusBean.setInit_argSample(new SampleKey((String) samples.get(i)));
        statusBean.setInit_argSample_status_datetime(now);
        statusBean.setInit_argStatus_type_code(status);
        statusBean.commitCopyHelper();
      }
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  public void ejbActivate() throws java.rmi.RemoteException {
  }
  public void ejbCreate() throws javax.ejb.CreateException, EJBException {
  }
  public void ejbPassivate() throws java.rmi.RemoteException {
  }
  public void ejbRemove() throws java.rmi.RemoteException {
  }

  public javax.ejb.SessionContext getSessionContext() {
    return mySessionCtx;
  }

  public void setSessionContext(javax.ejb.SessionContext ctx) throws java.rmi.RemoteException {
    mySessionCtx = ctx;
  }
}
