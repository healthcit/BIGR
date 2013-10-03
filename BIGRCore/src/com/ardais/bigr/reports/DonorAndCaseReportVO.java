package com.ardais.bigr.reports;

import java.io.Serializable;
import java.util.List;


public class DonorAndCaseReportVO implements Serializable{
  
  public DonorAndCaseReportVO() {
    super();
  }
  
private String action="";
private String reportType="";
private String donorId="";
private String caseId="";
private String dnrCtStDt="";
private String dnrCtEnDt="";
private String csCtStDt="";
private String csCtEnDt="";
private String[] irbProtocol=new String[0];
private String[] policy= new String[0];
private String sortOrder="";



 
/**
 * @return Returns the action.
 */
public String getAction() {
  return action;
}
/**
 * @param action The action to set.
 */
public void setAction(String action) {
  this.action = action;
}
/**
 * @return Returns the reportType.
 */
public String getReportType() {
  return reportType;
}
/**
 * @param reportType The reportType to set.
 */
public void setReportType(String reportType) {
  this.reportType = reportType;
}

/**
 * @return Returns the caseId.
 */
public String getCaseId() {
  return caseId;
}
/**
 * @param caseId The caseId to set.
 */
public void setCaseId(String caseId) {
  this.caseId = caseId;
}
/**
 * @return Returns the csCtEnDt.
 */
public String getCsCtEnDt() {
  return csCtEnDt;
}
/**
 * @param csCtEnDt The csCtEnDt to set.
 */
public void setCsCtEnDt(String csCtEnDt) {
  this.csCtEnDt = csCtEnDt;
}
/**
 * @return Returns the csCtStDt.
 */
public String getCsCtStDt() {
  return csCtStDt;
}
/**
 * @param csCtStDt The csCtStDt to set.
 */
public void setCsCtStDt(String csCtStDt) {
  this.csCtStDt = csCtStDt;
}
/**
 * @return Returns the dnrCtEnDt.
 */
public String getDnrCtEnDt() {
  return dnrCtEnDt;
}
/**
 * @param dnrCtEnDt The dnrCtEnDt to set.
 */
public void setDnrCtEnDt(String dnrCtEnDt) {
  this.dnrCtEnDt = dnrCtEnDt;
}
/**
 * @return Returns the dnrCtStDt.
 */
public String getDnrCtStDt() {
  return dnrCtStDt;
}
/**
 * @param dnrCtStDt The dnrCtStDt to set.
 */
public void setDnrCtStDt(String dnrCtStDt) {
  this.dnrCtStDt = dnrCtStDt;
}
/**
 * @return Returns the donorId.
 */
public String getDonorId() {
  return donorId;
}
/**
 * @param donorId The donorId to set.
 */
public void setDonorId(String donorId) {
  this.donorId = donorId;
}
/**
 * @return Returns the irbProtocol.
 */
public String[] getIrbProtocol() {
  return irbProtocol;
}
/**
 * @param irbProtocol The irbProtocol to set.
 */
public void setIrbProtocol(String[] irbProtocol) {
  this.irbProtocol = irbProtocol;
}
/**
 * @return Returns the policy.
 */
public String[] getPolicy() {
  return policy;
}
/**
 * @param policy The policy to set.
 */
public void setPolicy(String[] policy) {
  this.policy = policy;
}
/**
 * @return Returns the sortOrder.
 */
public String getSortOrder() {
  return sortOrder;
}
/**
 * @param sortOrder The sortOrder to set.
 */
public void setSortOrder(String sortOrder) {
  this.sortOrder = sortOrder;
}
}
