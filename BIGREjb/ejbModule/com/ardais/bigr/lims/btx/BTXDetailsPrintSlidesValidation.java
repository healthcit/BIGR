package com.ardais.bigr.lims.btx;

import com.ardais.bigr.iltds.btx.BTXDetails;

/**
 * Represent the details of a print slides business transaction. This class is 
 * basically used for vlaidating the user entered information during scan user,
 * scan slide transaction. This class helps to validate the information at the 
 * ejb layer and not to log the BTX history.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 *
 * <h4>Required input fields</h4>
 * <ul>
 * <li>{@link #setUserId(String) limsUserId}: The id of the user performing the transaction.</li>
 * <li>{@link #setSlidesList(List) slideData}: A list of Slides.</li>
 * <li>{@link #setPrintAllSlides(boolean) printAllSlides}: A boolean which specifies to print all slides.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>{@link #setMessage(String) message}: The message which specifies the status 
 * of printing label(s).</li>
 * </ul>
 */
public class BTXDetailsPrintSlidesValidation extends BTXDetailsPrintSlides {

  /**
   * Constructor for BTXDetailsPrintSlidesValidation.
   */
  public BTXDetailsPrintSlidesValidation() {
    super();
  }
  
  /**
   * @see com.ardais.bigr.iltds.btx.BTXDetails#getBTXType()
   */
  public String getBTXType() {
    return BTXDetails.BTX_TYPE_PRINT_SLIDES_VALIDATION;
  }

}
