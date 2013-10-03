/*
 * Created on Aug 19, 2010
 * an util class to handle db access for attachment
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ardais.bigr.pdc.attachment;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.pdc.helpers.AttachmentDataHelper;
import com.ardais.bigr.pdc.javabeans.AttachmentData;
import com.ardais.bigr.dataImport.web.form.CaseForm;
import com.ardais.bigr.dataImport.web.form.SampleForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;




/**
 * @author Eric Zhang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AttachmentDB {
       
      
       public AttachmentDB (){
             
       }
       
       
       public List convertAttachmentDataToHelper(List attachments) {
         
         if(attachments == null) return null;
         
         ArrayList attachHelpers = new ArrayList();
         AttachmentDataHelper aHelper = null;
         //convert the data bean to the helper
         for(Iterator itr =attachments.iterator(); itr.hasNext();){
             AttachmentData aData = (AttachmentData)itr.next();
             aHelper = new AttachmentDataHelper(aData);
             attachHelpers.add(aHelper);
         }
         return attachHelpers;
       }
       
       
       public List getAttachments(Object obj) {
         StringBuffer query = new StringBuffer();
         String[] params = {null,null};
         
         query.append("SELECT ID, ARDAIS_ACCT_KEY, ARDAIS_ID, CONSENT_ID, SAMPLE_BARCODE_ID, IS_PHI_YN, COMMENTS,NAME, CREATED_BY, CREATED_DATE, CONTENTTYPE ");
         query.append("FROM iltds_attachments ");
         query.append("WHERE ARDAIS_ACCT_KEY = ? ");
           
         if(obj instanceof CaseForm) {
           query.append("AND CONSENT_ID = ? ");
           params[0] =((CaseForm)obj).getArdaisAcctKey();
           params[1] =((CaseForm)obj).getConsentId();
           }
         
              
        if(obj instanceof SampleForm) {
           query.append("AND SAMPLE_BARCODE_ID = ? ");
           params[0] =((SampleForm)obj).getSampleData().getArdaisAcctKey();
           params[1] =((SampleForm)obj).getSampleData().getSampleId();
           }
         
         query.append(" order by CREATED_DATE desc");
          
       
         return ApiFunctions.runQuery(query.toString(), params, AttachmentData.class);
       }
       
       public byte[] getAttachmentBlob (String fileId){
       
           StringBuffer query = new StringBuffer();
           
           query.append("SELECT id, attachment ");
           query.append("FROM iltds_attachments ");
           query.append("WHERE ID = ? ");
            
           String[] params = { fileId};
         
           List results = ApiFunctions.runQuery(query.toString(), params, AttachmentData.class);
          
           if(results != null) {
             Iterator itr = results.iterator();
             AttachmentData attach = (AttachmentData)itr.next();
             //System.out.println("I am in attachment ="+ attach.getAttachment()); 
             return attach.getAttachment();
           }

             return null;
             //
       }
       
       public boolean deleteAttachment(String attachId){
         StringBuffer sql = new StringBuffer();
         
         sql.append("DELETE ");
         sql.append("FROM iltds_attachments ");
         sql.append("WHERE ID = ? ");
          
         
         Connection con = ApiFunctions.getDbConnection();
         PreparedStatement pstmt = null;
         boolean success = false;
         try {
           // delete the attachment
           pstmt = con.prepareStatement(sql.toString());
           pstmt.setString(1, attachId);
       
           pstmt.executeUpdate();
           success = true;
         }
         catch (Exception e) {
           ApiFunctions.throwAsRuntimeException(e);
         }
         finally {
           ApiFunctions.close(pstmt);
           ApiFunctions.closeDbConnection(con);
         }
       
         return success;
       

       } 
       
}
