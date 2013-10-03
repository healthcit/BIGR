package com.ardais.bigr.pdc.javabeans;

import java.io.Serializable;
import java.io.InputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;


/**
 * This class represents attachment data.
 */
public class AttachmentData implements Serializable {
  private int _id;
  private String _ardaisId;
  private String _name;
  private byte[] _attachment;
  private String _comments;
  private String _createdBy;
  private Timestamp _createdDate;
  private String _consentId;
  private String _sampleBarCodeId;
  private String _isPHIYN = "N"; //default to N
  private String _ardaisAccountKey;
  private String _contentType;
 
 
 

  /**
   * Create a new <code>AttachmentData</code>.
   */
  public AttachmentData() {
    
  }
  /**
   * Create a new <code>AttachmentData</code>.
   */
  public AttachmentData(ResultSet rs) {
    this();
    
    //here is to mapping the resultset to the databean
    try {
      ResultSetMetaData meta = rs.getMetaData();
      int columnCount = meta.getColumnCount();
      HashMap lookup = new HashMap();

      for (int i = 0; i < columnCount; i++) {
        lookup.put(meta.getColumnName(i + 1).toLowerCase(), null);
      }

      if (lookup.containsKey("id"))
        setId(Integer.parseInt(rs.getString("id")));
      
      if (lookup.containsKey("ardais_acct_key"))
        setArdaisAccountKey(rs.getString("ardais_acct_key"));
      
      if (lookup.containsKey("ardais_id"))
        setArdaisId(rs.getString("ardais_id"));
      
      if (lookup.containsKey("consent_id"))
        setConsentId(rs.getString("consent_id"));

      if (lookup.containsKey("sample_barcode_id"))
        setSampleBarCodeId(rs.getString("sample_barcode_id"));

      if (lookup.containsKey("comments"))
        setComments(rs.getString("comments"));

      if (lookup.containsKey("name"))
        setName(rs.getString("name"));
   
      if (lookup.containsKey("created_by"))
        setCreatedBy(rs.getString("created_by"));

      //if (lookup.containsKey("created_date"))
      //  setCreatedDate(rs.getString("created_date"));
      if (lookup.containsKey("created_date"))
         setCreatedDate(rs.getTimestamp("created_date"));
      

       if (lookup.containsKey("attachment")) {
        setAttachmentFromBlob(rs.getBlob("attachment")); 
       }
        

      if (lookup.containsKey("is_phi_yn"))
        setIsPHIYN(rs.getString("is_phi_yn"));
      
      if (lookup.containsKey("contenttype"))
        setContentType(rs.getString("contenttype"));


    }
    catch (SQLException e) {
      throw new ApiException(e);
    }
    catch (IOException ie) {
      throw new ApiException(ie);
    }
  }
 
 
  
  
/**
 * 
 * @return  id
 */
 public int getId(){
   return _id;
 }
  
  /**
   * @return
   */
  public String getArdaisAccountKey() {
    return _ardaisAccountKey;
  }

  /**
   * Returns this donor's id.
   *
   * @return  The donor (Ardais) id.
   */
  public String getArdaisId() {
    return _ardaisId;
  }
 
  
  /**
   * Returns the creation user.
   *
   * @return  The id of the creation user.
   */
  public String getCreatedBy() {
    return _createdBy;
  }
  /**
   * 
   * Creation date: (7/23/2002 4:00:28 PM)
   * @return java.lang.String
   */
  public Timestamp getCreatedDate() {
    return _createdDate;
  }

  /**
   * @return _consentId
   */
  public String getConsentId() {
    return _consentId;
  }

  /**
   * Comments
   * @return java.lang.String
   */
  public String getComments() {
    return _comments;
  }

  /**
   * Returns this attachment's name.
   *
   * @return  The name.
   */
  public String getName() {
    return _name;
  }
  

  /**
   * @return
   */
  public String getIsPHIYN() {
    return _isPHIYN;
  }
  
  
  public byte[] getAttachment(){
    return _attachment;
  }
  
  public String getContentType() {
    return _contentType;
  }
  

 /**
  * 
  * @param id
  */
 public void setId(int id){
    this._id = id;
 }
  

  /**
   * @param string
   */
  public void setArdaisAccountKey(String string) {
    _ardaisAccountKey = string;
  }

  /**
   * Sets this attachment's (Ardais) id.
   *
   * @param id  The attachment's (Ardais) id.
   */
  public void setArdaisId(String id) {
    _ardaisId = id;
  }

 
  /**
   * Insert the method's description here.
   * Creation date: (7/23/2002 3:59:43 PM)
   * @param user java.lang.String
   */
  public void setCreatedBy(String user) {
    _createdBy = user;
  }

  /**
   * @param string
   */
  public void setConsentId(String string) {
    _consentId = string;
  }

  /**
   * set comments
   */
  public void setComments(String notes) {
    _comments = notes;

  }

  /**
   * Sets the creation date.
   *
   * @param date  The attachment's creation date.
   */
  public void setCreatedDate(Timestamp date) {
    _createdDate = date;
  }
  
  

  /**
   * @param string
   */
  public void setIsPHIYN(String string) {
    _isPHIYN = string;
  }

  
  /**
   * Sets this attachment.
   *
   * @param attachment  The body of the attachment.
   */
  public void setAttachment(byte[] attachment) {
    _attachment = attachment;
  }

 
  public void setAttachmentFromBlob(Blob bl) throws SQLException,IOException {
    
     InputStream is =  bl.getBinaryStream();
      _attachment = new byte[(int)bl.length()];
     is.read(_attachment);
     is.close();
   }
  
 
  /**
   * Sets this attachment's name
   *
   * @param  name  the attachment's name (or title)
   */
  public void setName(String name) {
    _name = name;
  }
 
  
  public String getSampleBarCodeId() {
    return _sampleBarCodeId;
  }
  
  public void setSampleBarCodeId(String id) {
    _sampleBarCodeId = id;
  }

  public void setContentType(String type) {
    _contentType = type;
  }
  
  public String toString() {
    return "Id ="+getId()+", Name="+getName();
  }
}
