package com.ardais.bigr.lims.javabeans;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the raw data needed for the act of preparing to create slides.
 */
public class CreateSlidesPrepareData implements Serializable {
	private static final long serialVersionUID = -6008358526457545566L;

	private String _sampleId;
	private int _numberOfSlides;
	private List _slideData;

	/**
	 * Creates a new <code>CreateSlidesPrepareData</code>.
	 */
	public CreateSlidesPrepareData() {
	}
	
	/**
	 * Returns the numberOfSlides.
	 * @return int
	 */
	public int getNumberOfSlides() {
		return _numberOfSlides;
	}

	/**
	 * Returns the sampleId.
	 * @return String
	 */
	public String getSampleId() {
		return _sampleId;
	}

	/**
	 * Returns the slideData.
	 * @return List
	 */
	public List getSlideData() {
		return _slideData;
	}

	/**
	 * Sets the numberOfSlides.
	 * @param numberOfSlides The numberOfSlides to set
	 */
	public void setNumberOfSlides(int numberOfSlides) {
		_numberOfSlides = numberOfSlides;
	}

	/**
	 * Sets the sampleId.
	 * @param sampleId The sampleId to set
	 */
	public void setSampleId(String sampleId) {
		_sampleId = sampleId;
	}

	/**
	 * Sets the slideData.
	 * @param slideData The slideData to set
	 */
	public void setSlideData(List slideData) {
		_slideData = slideData;
	}

}
