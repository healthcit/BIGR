package com.ardais.bigr.pdc.beans;

import com.ardais.bigr.pdc.javabeans.PathReportData;
/**
 * This is an Enterprise Java Bean Remote Interface
 */
public interface DDCPathology extends javax.ejb.EJBObject {

/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportData
 * @param pathReport com.ardais.bigr.pdc.javabeans.PathReportData
 */
com.ardais.bigr.pdc.javabeans.PathReportData buildPathReport(com.ardais.bigr.pdc.javabeans.PathReportData pathReport) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportData
 * @param pathReport com.ardais.bigr.pdc.javabeans.PathReportData
 */
com.ardais.bigr.pdc.javabeans.PathReportData createPathReport(com.ardais.bigr.pdc.javabeans.PathReportData pathReport, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData
 * @param pathReportDiagnostic com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData
 */
com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData createPathReportDiagnostic(com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData pathReportDiagnostic, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportSectionData
 * @param pathReportSection com.ardais.bigr.pdc.javabeans.PathReportSectionData
 */
com.ardais.bigr.pdc.javabeans.PathReportSectionData createPathReportSection(com.ardais.bigr.pdc.javabeans.PathReportSectionData pathReportSection, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData
 * @param dataBean com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData
 */
com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData createPathReportSectionFinding(com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData dataBean, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportData
 * @param pathReport com.ardais.bigr.pdc.javabeans.PathReportData
 */
com.ardais.bigr.pdc.javabeans.PathReportData createRawPathReport(com.ardais.bigr.pdc.javabeans.PathReportData pathReport, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException;

/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportData
 * @param pathReport com.ardais.bigr.pdc.javabeans.PathReportData
 */
com.ardais.bigr.pdc.javabeans.PathReportData getPathReport(com.ardais.bigr.pdc.javabeans.PathReportData pathReport) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData
 * @param pathReportDiagnostic com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData
 */
com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData getPathReportDiagnostic(com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData pathReportDiagnostic) throws java.rmi.RemoteException;
/**
 * 
 * @return java.util.List
 * @param pathReport com.ardais.bigr.pdc.javabeans.PathReportData
 */
java.util.List getPathReportDiagnostics(com.ardais.bigr.pdc.javabeans.PathReportData pathReport) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportSectionData
 * @param pathReportSection com.ardais.bigr.pdc.javabeans.PathReportSectionData
 */
com.ardais.bigr.pdc.javabeans.PathReportSectionData getPathReportSection(com.ardais.bigr.pdc.javabeans.PathReportSectionData pathReportSection) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData
 * @param dataBean com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData
 */
com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData getPathReportSectionFinding(com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData dataBean) throws java.rmi.RemoteException;
/**
 * 
 * @return java.util.List
 * @param dataBean com.ardais.bigr.pdc.javabeans.PathReportSectionData
 */
java.util.List getPathReportSectionFindings(com.ardais.bigr.pdc.javabeans.PathReportSectionData dataBean) throws java.rmi.RemoteException;
/**
 * 
 * @return java.util.List
 * @param pathReport com.ardais.bigr.pdc.javabeans.PathReportData
 */
java.util.List getPathReportSections(com.ardais.bigr.pdc.javabeans.PathReportData pathReport) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportData
 * @param pathReport com.ardais.bigr.pdc.javabeans.PathReportData
 */
com.ardais.bigr.pdc.javabeans.PathReportData getPathReportSummary(com.ardais.bigr.pdc.javabeans.PathReportData pathReport) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportData
 * @param pathReport com.ardais.bigr.pdc.javabeans.PathReportData
 */
com.ardais.bigr.pdc.javabeans.PathReportData getRawPathReport(com.ardais.bigr.pdc.javabeans.PathReportData pathReport) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.iltds.assistants.LegalValueSet
 * @param pathReportId java.lang.String
 * @param pathReportSecId java.lang.String
 */
com.ardais.bigr.iltds.assistants.LegalValueSet getSectionIdentifierList(java.lang.String pathReportId, java.lang.String pathReportSecId) throws java.rmi.RemoteException;
/**
 * 
 * @return boolean
 * @param dataBean com.ardais.bigr.pdc.javabeans.PathReportSectionData
 */
boolean isExistsPathReportSection(com.ardais.bigr.pdc.javabeans.PathReportSectionData dataBean) throws java.rmi.RemoteException;
/**
 * 
 * @return boolean
 * @param dataBean com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData
 */
boolean isExistsPathReportSectionFinding(com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData dataBean) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportData
 * @param pathReport com.ardais.bigr.pdc.javabeans.PathReportData
 */
com.ardais.bigr.pdc.javabeans.PathReportData updatePathReport(com.ardais.bigr.pdc.javabeans.PathReportData pathReport, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData
 * @param pathReportDiagnostic com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData
 */
com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData updatePathReportDiagnostic(com.ardais.bigr.pdc.javabeans.PathReportDiagnosticData pathReportDiagnostic, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportSectionData
 * @param pathReportSection com.ardais.bigr.pdc.javabeans.PathReportSectionData
 */
com.ardais.bigr.pdc.javabeans.PathReportSectionData updatePathReportSection(com.ardais.bigr.pdc.javabeans.PathReportSectionData pathReportSection, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData
 * @param dataBean com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData
 */
com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData updatePathReportSectionFinding(com.ardais.bigr.pdc.javabeans.PathReportSectionFindingData dataBean, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException;
/**
 * 
 * @return com.ardais.bigr.pdc.javabeans.PathReportData
 * @param pathReport com.ardais.bigr.pdc.javabeans.PathReportData
 */
com.ardais.bigr.pdc.javabeans.PathReportData updateRawPathReport(com.ardais.bigr.pdc.javabeans.PathReportData pathReport, com.ardais.bigr.security.SecurityInfo securityInfo) throws java.rmi.RemoteException;
	/**
	 */
	public PathReportData getRawPathReport(String lineId)
		throws java.rmi.RemoteException;
}
