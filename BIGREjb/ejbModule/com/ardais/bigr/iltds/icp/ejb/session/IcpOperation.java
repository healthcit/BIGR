package com.ardais.bigr.iltds.icp.ejb.session;

import com.ardais.bigr.iltds.databeans.AsmData;
import com.ardais.bigr.iltds.databeans.CaseData;
import com.ardais.bigr.iltds.databeans.LogicalRepositoryExtendedData;
import com.ardais.bigr.iltds.databeans.SampleData;
import com.ardais.bigr.javabeans.BoxDto;
import com.ardais.bigr.security.SecurityInfo;

/**
 * This is an Enterprise Java Bean Remote Interface
 */
public interface IcpOperation extends javax.ejb.EJBObject {

  AsmData getAsmData(
    AsmData asmData,
    SecurityInfo securityInfo,
    boolean getSub,
    boolean getParents)
    throws java.rmi.RemoteException;

  BoxDto getBoxData(BoxDto boxData, SecurityInfo securityInfo, boolean getContents)
    throws java.rmi.RemoteException;

  CaseData getCaseData(
    CaseData caseData,
    SecurityInfo securityInfo,
    boolean getParents)
    throws java.rmi.RemoteException;

  SampleData getSampleData(
    SampleData sampleData,
    SecurityInfo securityInfo,
    boolean getSub,
    boolean getParents)
    throws java.rmi.RemoteException;

  javax.ejb.SessionContext getSessionContext() throws java.rmi.RemoteException;

  LogicalRepositoryExtendedData getLogicalRepositoryData(
    String repositoryId,
    SecurityInfo securityInfo)
    throws java.rmi.RemoteException;
}
