package com.ardais.bigr.validation;

/**
 * Indicates that the implementing class can update a {@link ValidatorContext}.  Such classes
 * can be used in {@link ValidatorCollection} classes to update the shared context 
 * during the course of validation.
 */
public interface ValidatorContextUpdater {

  /**
   * Updates the specified <code>ValidatorContext</code>.  Since <code>ValidatorContext</code>
   * is a marker interface implementors will typically be written for a specific concrete
   * implementation of <code>ValidatorContext</code> and will cast to that implementation in
   * the <code>update</code> method.
   *  
   * @param  context  the <code>ValidatorContext</code>
   */
  public void update(ValidatorContext context);
  
  /**
   * Returns the name of this <code>ValidatorContextUpdater</code>, which is simply an optional 
   * string that is assigned to the context updater by its creator.  The name is useful when 
   * debugging context updaters, and has no bearing on the operation of the context updater itself.
   * 
   * @return  The name of the <code>ValidatorContextUpdater</code>, or <code>null</code> if it was never 
   *          assigned a name.
   */
  public String getName();
  
}
