package com.ardais.bigr.iltds.btx;

/**
 * Objects that implement this interface can set their own internal state
 * based on information in a BTXDetails object, typically by copying
 * information from fields in the BTXDetails object.
 */
public interface PopulatableFromBtxDetails {

    /**
     * Populate the fields of this object with information contained in a
     * business transaction details object (BTXDetails).  To assist with
     * copying fields, you may want to use the
     * {@link com.ardais.bigr.beanutils.BigrBeanUtilsBean#copyProperties(Object, Object)} method.
     *
     * @param btxDetails the BTXDetails object that will be used as the
     *    information source.  This may be null, and when it is null
     *    implementations of this method must not throw an exception.
     */
    public void populateFromBtxDetails(BTXDetails btxDetails);
}
