package com.ardais.bigr.iltds.beans;

import java.util.Vector;

import com.ardais.bigr.iltds.databeans.AsmData;
import com.ardais.bigr.security.SecurityInfo;

/**
 * This is an Enterprise Java Bean Remote Interface
 */
public interface ASMOperation extends javax.ejb.EJBObject {

  boolean asmFormExistInRange(String asmFormID, String consentID) throws java.rmi.RemoteException;

  void associateASMForm(String consentID, String asmFormID, SecurityInfo secInfo)
    throws java.rmi.RemoteException;

  Vector nonAsmFormSamples(String asmID, Vector samples) throws java.rmi.RemoteException;

  void updateASMFormInfo(
    String asmFormID,
    String surgicalProcedure,
    java.sql.Date timeOfRemoval,
    java.sql.Date timeOfGrossing,
    String employeeName)
    throws java.rmi.RemoteException;

  void updateASMModuleInfo(AsmData asmData, SecurityInfo secInfo) throws java.rmi.RemoteException;
}
