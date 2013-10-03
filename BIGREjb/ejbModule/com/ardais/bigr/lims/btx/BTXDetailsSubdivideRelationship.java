package com.ardais.bigr.lims.btx;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ardais.bigr.javabeans.IdList;

public interface BTXDetailsSubdivideRelationship {

  /**
   * Return the id of the parent sample (the sample that is being divided).
   *
   * @return the parent sample id.
   */
  public String getParentId();

  /**
   * Returns the consentIdList.
   * 
   * @return String
   */
  public String getConsentId();

  /**
   * Returns the asmPosition.
   * 
   * @return String
   */
  public String getAsmPosition();

  /**
   * Returns the warningInfo map.
   * 
   * @returns Map
   */
  public Map getWarningInfo();

  /**
   * 
   */
  public Timestamp getSubdivisionTimestamp();

  /**
   * Return the list of the child sample ids produced by the subdivision.
   *
   * @return the list of child sample ids.
   */
  public IdList getChildIdList();

  /**
   * 
   */
  public HashMap getChildAsmPositions();

  /**
   * 
   */
  public Date getQCInProcess();

  /**
   * 
   */
  public Date getRNDRequested();
}
