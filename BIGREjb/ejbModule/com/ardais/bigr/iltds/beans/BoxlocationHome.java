package com.ardais.bigr.iltds.beans;

import java.rmi.RemoteException;
import java.util.Enumeration;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.ardais.bigr.iltds.assistants.StorageLocAsst;

/**
 * This is a Home interface for the Entity Bean
 */
public interface BoxlocationHome extends javax.ejb.EJBHome {

  /**
   * 
   * @return com.ardais.bigr.iltds.beans.Boxlocation
   * @param asst com.ardais.bigr.iltds.assistants.StorageLocAsst
   */
  Boxlocation create(StorageLocAsst asst) throws CreateException, RemoteException;
  /**
   * Insert the method's description here.
   * Creation date: (2/22/01 5:40:09 PM)
   * @return java.util.Enumeration
   * @param box java.lang.String
   * @exception javax.ejb.FinderException The exception description.
   */
  Enumeration findBoxLocationByBoxId(String box)
    throws RemoteException, FinderException;
  /**
   * This method was generated for supporting the relationship role named geolocation.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public Enumeration findBoxlocationByGeolocation(GeolocationKey inKey)
    throws RemoteException, FinderException;
  /**
   * Finds an instance using a key for Entity Bean: Boxlocation
   */
  public Boxlocation findByPrimaryKey(BoxlocationKey primaryKey)
    throws FinderException, RemoteException;
  /**
   * This method was generated for supporting the relationship role named samplebox.
   * It will be deleted/edited when the relationship is deleted/edited.
   */
  public Enumeration findBoxlocationBySamplebox(SampleboxKey inKey)
    throws RemoteException, FinderException;
  public Enumeration findBoxLocationByStorageTypeCid(
    String locationAddressId,
    String storageTypeCid,
    String availableInd)
    throws FinderException, RemoteException;
}
