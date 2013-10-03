package com.ardais.bigr.javabeans;

import com.ardais.bigr.util.VariablePrecisionDateTime;

public class SampleDataForUI extends SampleData {

  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.SampleData#getCollectionDateTime()
   */
  public VariablePrecisionDateTime getCollectionDateTime() {
    if (super.getCollectionDateTime() == null) {
      setCollectionDateTime(new VariablePrecisionDateTime());
    }
    return super.getCollectionDateTime();
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.javabeans.SampleData#getPreservationDateTime()
   */
  public VariablePrecisionDateTime getPreservationDateTime() {
    if (super.getPreservationDateTime() == null) {
      setPreservationDateTime(new VariablePrecisionDateTime());
    }
    return super.getPreservationDateTime();
  }

}
