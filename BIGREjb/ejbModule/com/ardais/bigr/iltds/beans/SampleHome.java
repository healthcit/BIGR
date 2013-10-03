package com.ardais.bigr.iltds.beans;

import java.rmi.RemoteException;
import java.util.Enumeration;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

/**
 * This is a Home interface for the Entity Bean
 */
public interface SampleHome extends javax.ejb.EJBHome {

  /**
   * Creates an instance from a key for Entity Bean: Sample
   */
  public Sample create(
    String sample_barcode_id,
    String importedYN,
    String sampleTypeCid)
    throws CreateException, RemoteException;
  /**
   * findByPrimaryKey method comment
   * @return com.ardais.bigr.iltds.beans.Sample
   * @param key com.ardais.bigr.iltds.beans.SampleKey
   * @exception javax.ejb.FinderException The exception description.
   */
  Sample findByPrimaryKey(SampleKey key) throws RemoteException, FinderException;
  /**
   * This method was generated for supporting the association named Sample REFILTDS_ASM7 Asm.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return java.util.Enumeration
   * @param inKey com.ardais.bigr.iltds.beans.AsmKey
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  Enumeration findSampleByAsm(AsmKey inKey) throws RemoteException, FinderException;
  /**
   * This method was generated for supporting the association named Sample REFILTDS_SAMPLE_BOX10 Samplebox.  
   * 	It will be deleted/edited when the association is deleted/edited.
   * @return java.util.Enumeration
   * @param inKey com.ardais.bigr.iltds.beans.SampleboxKey
   * @exception javax.ejb.FinderException The exception description.
   */
  /* WARNING: THIS METHOD WILL BE REGENERATED. */
  Enumeration findSampleBySamplebox(SampleboxKey inKey) throws RemoteException, FinderException;
  /**
   * Insert the method's description here.
   * Creation date: (3/04/03 5:11:27 PM)
   * @return java.util.Enumeration
   * @exception javax.ejb.FinderException The exception description.
   */
  Enumeration findAll() throws RemoteException, FinderException;
}
