package com.gulfstreambio.bigr.id;

import com.ardais.bigr.btx.framework.Btx;
import com.ardais.bigr.id.btx.BtxDetailsGenerateSampleId;
import com.ardais.bigr.security.SecurityInfo;
import com.gulfstreambio.bigr.error.BigrValidationErrors;
import com.gulfstreambio.bigr.error.BigrValidationException;

/**
 * The <code>SampleIdService</code> generates and returns the next sample id using BIGR's standard
 * sample id format. 
 */
public class SampleIdService {

  // The singleton instance of this class.
  private static SampleIdService _instance;

  /**
   * Generates and returns the next sample id from the specified user's namespace.  This method 
   * is synchronized since it uses and updates a shared data structure, which in this case is a 
   * database table.  In addition, since the database is used, a call to this method should be
   * executed in its own transaction and not within a transaction that will used the id, since
   * the results of generating the id must be visible to all transactions.   
   * 
   * @param securityInfo the <code>SecurityInfo</code> of the user
   * @return The sample id.
   */
  public synchronized String generateId(SecurityInfo securityInfo) throws BigrValidationException {
    BtxDetailsGenerateSampleId btxDetails = new BtxDetailsGenerateSampleId();
    btxDetails.setBeginTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
    btxDetails.setLoggedInUserSecurityInfo(securityInfo);
    btxDetails.setTransactionType("generate_sample_id");
    btxDetails = (BtxDetailsGenerateSampleId) Btx.perform(btxDetails);

    String sampleId = null;
    if (btxDetails.isTransactionCompleted()) {
      sampleId = btxDetails.getSampleId();
    }
    else {
      BigrValidationErrors errors = btxDetails.getValidationErrors();
      throw new BigrValidationException(btxDetails.getValidationErrors());
    }
    
    return sampleId;
  }

  public static synchronized SampleIdService getInstance() {
    if (_instance == null) {
      _instance = new SampleIdService();
    }
    return _instance;
  }
  
  // Private - create an instance via getInstance.
  private SampleIdService() {
    super();
  }
}
