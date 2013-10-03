package com.ardais.bigr.iltds.beans;

import java.util.Vector;
/**
 * This is an Enterprise Java Bean Remote Interface
 */
public interface PathologyOperation extends javax.ejb.EJBObject {

  Vector getSampleLocations(Vector samples) throws java.rmi.RemoteException;

  Vector getProjectsAndShoppingCartsForSample(String sampleID) throws java.rmi.RemoteException;

  void updateSampleStatus(Vector samples, String status) throws java.rmi.RemoteException;
}
