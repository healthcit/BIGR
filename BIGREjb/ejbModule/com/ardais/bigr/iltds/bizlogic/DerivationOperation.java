package com.ardais.bigr.iltds.bizlogic;

import java.util.List;

import com.ardais.bigr.javabeans.DerivationDto;
import com.ardais.bigr.javabeans.SampleData;
import com.ardais.bigr.security.SecurityInfo;

/**
 * An interface that allows business rules for specific derivation operations to be implemented.
 * The callers of this interface should first call {@link addParentSample}and 
 * {@link addChildSample} if necessary to add all parent and child samples before calling any of 
 * the methods that enforce operation-specific rules, since the methods that enforce these rules 
 * rely on the parent and/or child samples.  
 */
public interface DerivationOperation {

  /**
   * Adds a parent sample to the list of parents.
   * 
   * @param  sample  the parent sample
   */
  public void addParentSample(SampleData sample); 

  /**
   * Clears the list of parent samples.
   */
  public void clearParentSamples(); 

  /**
   * Adds a child sample to the list of children.
   * 
   * @param  sample  the child sample
   */
  public void addChildSample(SampleData sample);

  /**
   * Clears the list of child samples.
   */
  public void clearChildSamples(); 

  /**
   * Populates the values of inherited child attributes from the parent(s).  If an inherited
   * attribute already has a value, then it will not be overwritten.  If there are no 
   * inherited attributes for this derivation operation, then simply returns the list of children 
   * unaltered.  The list of parent and child samples must be set before this method is called.
   *  
   * @param  securityInfo  the securityInfo of the currently logged in user
   * @return  A list of child samples as {@link SampleData} objects.  All child 
   *          samples that were set by calls to {@link #addChildSample(SampleData)} will be
   *          returned in the order they were set, regardless of whether they were altered or not.
   * @see #addParentSample(SampleData)
   * @see #addChildSample(SampleData)
   */
  public List populateChildren(SecurityInfo securityInfo, DerivationDto dto); 

  /**
   * Returns a SampleData populated with the values of any attributes having a non-empty, common 
   * value across parents.  Attributes that have either an empty or non-common value are not 
   * populated.  This can be used to implement inheritance rules between parents and children.
   * 
   * @return  The SampleData.
   */
  public SampleData findCommonParentValues();

  /**
   * Returns a list of valid child sample types for the specified parents.  The list of parent 
   * samples must be set before this method is called.
   * 
   * @return  A list of child sample type CUIs.
   */
  public List findValidChildSampleTypes();

  /**
   * Adjusts the amount remaining in each parent or marks each parent consumed as appropriate for
   * this derivation operation.  The list of parent and child samples must be set before this 
   * method is called.
   * 
   * @return  The list of parent samples, as {@link SampleData} objects, that were altered.  If
   *          no parent samples were altered then an empty list is returned.
   * @see #addParentSample(SampleData)
   * @see #addChildSample(SampleData)
   */
  public List adjustParentAmounts(); 
}
