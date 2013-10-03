package com.ardais.bigr.iltds.beans;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

import javax.ejb.EJBException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.SessionBean;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.TemporaryBlob;
import com.ardais.bigr.iltds.databeans.IrbVersionData;
import com.ardais.bigr.iltds.helpers.IltdsUtils;
import com.ardais.bigr.pdc.javabeans.AttachmentData;
import com.ardais.bigr.security.SecurityInfo;

/**
 * This is a Session Bean Class
 */
public class ConsentOperationBean implements SessionBean {
  private javax.ejb.SessionContext mySessionCtx = null;
  final static long serialVersionUID = 3206093459760846163L;

  public void pullFromESHold(String sampleID) {
    String queryString = "delete from es_shopping_cart_detail where barcode_id = ?";

    Connection con = null;
    PreparedStatement pstmt = null;
    try {
      con = ApiFunctions.getDbConnection();

      pstmt = con.prepareStatement(queryString);
      pstmt.setString(1, sampleID);

      pstmt.executeUpdate();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
    finally {
      ApiFunctions.close(pstmt);
      ApiFunctions.close(con);
    }
  }

  public void revokeConsent(
    String consent_id,
    String ardais_id,
    String ardaisstaff_id,
    String reason) {

    try {
      RevokedconsentAccessBean revokedConsent;
      try {
        revokedConsent = new RevokedconsentAccessBean(new RevokedconsentKey(consent_id, ardais_id));
      }
      catch (ObjectNotFoundException e) {
        revokedConsent = new RevokedconsentAccessBean();
        revokedConsent.setInit_argConsent_id(consent_id);
        revokedConsent.setInit_argArdais_id(ardais_id);
      }

      revokedConsent.setRevoked_by_staff_id(ardaisstaff_id);
      revokedConsent.setRevoked_timestamp(new Timestamp(new java.util.Date().getTime()));
      revokedConsent.commitCopyHelper();
    }
    catch (Exception e) {
      ApiFunctions.throwAsRuntimeException(e);
    }
  }

  public String getCaseOrigin(String caseID) throws ApiException {
      Connection con = null;
      PreparedStatement prepStmt = null;
      ResultSet rs = null;
      try {
          String queryString =
              "select account.ARDAIS_ACCT_KEY "
                  + "from iltds_informed_consent consent, "
                  + "  es_ardais_account account "
                  + "where consent.CONSENT_LOCATION_ADDRESS_ID = account.PRIMARY_LOCATION "
                  + "and consent.consent_id = ? ";

          con = ApiFunctions.getDbConnection();
          prepStmt = con.prepareStatement(queryString);
          prepStmt.setString(1, caseID);

          rs = prepStmt.executeQuery();
          while (rs.next()) {
              return rs.getString(1);
          }
      }
      catch (Exception ex) {
          ApiFunctions.throwAsRuntimeException(ex);
      }
      finally {
          ApiFunctions.close(con, prepStmt, rs);
      }
      return null;
  }

  public AttachmentData insertCNTAttachment(AttachmentData attachData, SecurityInfo securityInfo) {
    
   
     StringBuffer sql = new StringBuffer(256);
     sql.append("INSERT INTO iltds_attachments");
     sql.append(" (ardais_acct_key,");
     sql.append(" consent_id,");
    
     sql.append(" is_phi_yn,");
     sql.append(" comments,");
     sql.append(" name,");
     sql.append(" attachment,");
     sql.append(" created_by,");
     sql.append(" created_date,");
     sql.append(" contenttype");
    
     sql.append(")");
     sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

     Connection con = ApiFunctions.getDbConnection();
     TemporaryBlob blob = null;
     PreparedStatement pstmt = null;
     boolean success = false;
     try {
       // Insert the new attachment.
       pstmt = con.prepareStatement(sql.toString());
       pstmt.setString(1, attachData.getArdaisAccountKey());
       pstmt.setString(2, attachData.getConsentId());
       pstmt.setString(3, attachData.getIsPHIYN());
       pstmt.setString(4, attachData.getComments());
       pstmt.setString(5, attachData.getName());

       blob = new TemporaryBlob(con, attachData.getAttachment());
       pstmt.setBlob(6, blob.getSQLBlob() );
       pstmt.setString(7, attachData.getCreatedBy());
       //pstmt.setTimestamp(8, new java.sql.Timestamp((new java.util.Date()).getTime()));
       pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
       pstmt.setString(9, attachData.getContentType());
    
       pstmt.executeUpdate();
       success = true;
     }
     catch (Exception e) {
       ApiFunctions.throwAsRuntimeException(e);
     }
     finally {
       ApiFunctions.close(blob, con);
       ApiFunctions.close(pstmt);
       ApiFunctions.closeDbConnection(con);
     }

   /***

     //create an ICP history record if the donor was successfully created
     if (success) {
       BTXDetailsCreateDonor btxDetails = new BTXDetailsCreateDonor();
       btxDetails.setLoggedInUserSecurityInfo(securityInfo);
       btxDetails.setBeginTimestamp(new Timestamp(System.currentTimeMillis()));
       btxDetails.setTransactionType("pdc_placeholder");
       btxDetails.setDonorData(donorData);
       BtxPerformerPlaceHolder btx = new BtxPerformerPlaceHolder();
       btx.perform(btxDetails);
     } ***/

     return attachData;
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
