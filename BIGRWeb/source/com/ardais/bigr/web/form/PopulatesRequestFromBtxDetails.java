package com.ardais.bigr.web.form;

import javax.servlet.http.HttpServletRequest;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.web.action.BigrActionMapping;

/**
 * Interface to indicate ability to populate request related objects (Request, Session, etc)
 * from a BigrActionForm 
 */
public interface PopulatesRequestFromBtxDetails {
  
  void populateRequestFromBtxDetails(BTXDetails btxDetails, 
                                     BigrActionMapping mapping,
                                     HttpServletRequest request);
                                            
}
