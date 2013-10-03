package com.ardais.bigr.lims.btx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.iltds.btx.BTXDetails;
import com.ardais.bigr.lims.javabeans.SlideData;

/**
 * Represent the details of a path lab slides business transaction.
 * <p>
 * The transaction-specific fields are described below.  See also
 * {@link BTXDetails BTXDetails} for fields that are shared by all
 * business transactions.
 * <p>
 * <h4>Required input fields for operation type = scan user</h4>
 * <ul>
 * <li>{@link #setLimsUserId(String) limsUserId}: The id of the LIMS user performing the transaction.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 * 
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 * 
 * <p>
 * <h4>Required input fields for operation type = add slide</h4>
 * <ul>
 * <li>{@link #setLimsUserId(String) limsUserId}: The id of the LIMS user performing the transaction.</li>
 * <li>{@link #setOperationType(String) operationType}: The slide id to be validated.</li> 
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields for operation type = add slide</h4>
 * <ul>
 * <li>{@link #getCurrentBmsValue(String)}: The BMS value of the sample that owns the slide being added.</li>
 * </ul>
 * 
 * <p>
 * <h4>Required input fields for operation type = get Report</h4>
 * <ul>
 * <li>{@link #setLimsUserId(String) limsUserId}: The id of the LIMS user performing the transaction.</li>
 * <li>{@link #setSlideData(List) slideData}: A list of SlideData data beans, each of which
 * holds a slide id.</li>
 * </ul>
 *
 * <h4>Optional input fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 *
 * <h4>Output fields</h4>
 * <ul>
 * <li>None.</li>
 * </ul>
 */
public class BTXDetailsPathLabSlides extends BTXDetails implements Serializable {
	private static final long serialVersionUID = -4194128066165342431L;
    public static final String OP_TYPE_SCAN_USER = "scanUser";
    public static final String OP_TYPE_ADD_SLIDE = "addSlide";
    public static final String OP_TYPE_GET_REPORT = "getReport";

	private String _limsUserId;
	private List _slideData;
    private String _operationType;
    private String _currentSlide;
    private String _currentBmsValue;

	
	/** 
	 * Constructor
	 */
	public BTXDetailsPathLabSlides() {
		super();
	}	
	
	/**
	 * Returns the limsUserId.
	 * @return String
	 */
	public String getLimsUserId() {
		return _limsUserId;
	}

	/**
	 * Returns the slideData.
	 * @return List
	 */
	public List getSlideData() {
		return _slideData;
	}

	/**
	 * Sets the limsUserId.
	 * @param limsUserId The limsUserId to set
	 */
	public void setLimsUserId(String limsUserId) {
		_limsUserId = limsUserId;
	}

	/**
	 * Sets the slideData.
	 * @param slideData The slideData to set
	 */
	public void setSlideData(List slideData) {
		_slideData = slideData;
	}

	/**
	 * This method is called from the BTXDetails base class when its getDetailsAsHTML() 
	 * method has been called from the BTXHistoryReaderBean.getHistoryDisplayLines 
	 * method.  Since this transaction is not logged, there won't be any history so this 
	 * method should never be called.  If for some reason this method is called it 
	 * returns a message to the effect that it was called in error.
	 *
	 * @return an HTML string that defines how the transaction details are presented
	 * in a transaction-history web page
	 */
  protected String doGetDetailsAsHTML() {
		String msg = "BTXDetailsPathLabSlides.doGetDetailsAsHTML() method called in error!";
		return msg;
	}
	
	/**
	 * Return the business transaction type code for the transaction that this
	 * class represents.
	 *
	 * @return the transaction type code.
	 */
	public String getBTXType() {
		return BTXDetails.BTX_TYPE_PATHLAB_SLIDES;
	}
	
	/**
	 * Return a set of the object ids of the objects that are directly involved
	 * in this transaction.  This set does not contain the ids of objects that
	 * are considered to be indirectly involved in the transaction, and it does
	 * not include the user id of the user who performed the transaction.
	 * <p>
	 * For example, a transaction that scans a box of samples directly involves the box
	 * object and each of the sample objects, and indirectly involves the
	 * asm, asm form, case and donor objects for each sample.
	 *
	 * @return the set of directly involved object ids.
	 */
	public Set getDirectlyInvolvedObjects() {
		Set set = new HashSet();

		if (getSlideData() != null) {
			List slides = getSlideData();
			Iterator iterator = slides.iterator();
			while (iterator.hasNext()) {
				SlideData slide = (SlideData)iterator.next();
				set.add(slide.getSlideId());
			}
		}
		
		return set;
	}	

	/**
	 * Returns the operationType.
	 * @return String
	 */
	public String getOperationType() {
		return _operationType;
	}
	

	/**
	 * Sets the operationType.
	 * @param operationType The operationType to set
	 */
	public void setOperationType(String operationType) {
		_operationType = operationType;
	}

	/**
	 * Returns the currentSlide.
	 * @return String
	 */
	public String getCurrentSlide() {
		return _currentSlide;
	}

	/**
	 * Sets the currentSlide.
	 * @param currentSlide The currentSlide to set
	 */
	public void setCurrentSlide(String currentSlide) {
		_currentSlide = currentSlide;
	}

    /**
     * @return
     */
    public String getCurrentBmsValue() {
      return _currentBmsValue;
    }

    /**
     * @param string
     */
    public void setCurrentBmsValue(String string) {
      _currentBmsValue = string;
    }

}
