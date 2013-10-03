package com.ardais.bigr.pdc.helpers;

import java.io.Serializable;

import com.ardais.bigr.pdc.javabeans.AttachmentData;


import java.util.Date;
import java.text.SimpleDateFormat;


/**
 * This class represents attachment data helper.
 */
public class AttachmentDataHelper implements Serializable {
  private int _id;
  private String _ardaisId;
  private String _name;
  private String _attachment;
  private String _comments;
  private String _createdBy;
  private String _createdDate;
  private String _consentId;
  private String _sampleBarCodeId;
  private String _isPHIYN;
  private String _ardaisAccountKey;
  private String _contentType;
  private AttachmentData _attachmentData;
 
 
 

  /**
   * Create a new <code>AttachmentDataHelper</code>.
   */
  public AttachmentDataHelper(AttachmentData data) {
    this._attachmentData = data;
  }
 
  
/**
 * 
 * @return  id
 */
 public int getId(){
   return _attachmentData.getId();
 }
  
  /**
   * @return
   */
  public String getArdaisAccountKey() {
    return _attachmentData.getArdaisAccountKey();
  }

  /**
   * Returns this donor's id.
   *
   * @return  The donor (Ardais) id.
   */
  public String getArdaisId() {
    return _attachmentData.getArdaisId();
  }
 
  
  /**
   * Returns the creation user.
   *
   * @return  The id of the creation user.
   */
  public String getCreatedBy() {
    return _attachmentData.getCreatedBy();
  }
  /**
   * 
   * Creation date: (7/23/2002 4:00:28 PM)
   * @return java.lang.String
   */
  public String getCreatedDate() throws Exception {
    SimpleDateFormat sft = new SimpleDateFormat("MM/dd/yyyy h:mm a");
    Date date = new Date(_attachmentData.getCreatedDate().getTime());
        
    return sft.format(date);
  }

  /**
   * @return _consentId
   */
  public String getConsentId() {
    return _attachmentData.getConsentId();
  }

  /**
   * Comments
   * @return java.lang.String
   */
  public String getComments() {
    return _attachmentData.getComments();
  }

  /**
   * Returns this attachment's name.
   *
   * @return  The name.
   */
  public String getName() {
    return _attachmentData.getName();
  }
  

  /**
   * @return
   */
  public String getIsPHIYN() {
    
    if("Y".equals(_attachmentData.getIsPHIYN()))
    return "Yes";
    else return "No";
  }
  
  
 /* public String getAttachment(){
    return _attachmentData.getAttachment();
  } */

 
  public String getContentType(){
    return _attachmentData.getContentType();
  }

  
  public String getSampleBarCodeId() {
    return _attachmentData.getSampleBarCodeId();
  }
  
 
}
