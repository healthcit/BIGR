package com.ardais.bigr.validation;

/**
 * An interface for creating and running a collection of validators.  Since this interface
 * extends {@link Validator}, a class implementing this collection interface is also itself a
 * validator.
 * <p>
 * The <code>ValidatorCollection<code> interface allows all <code>Validator</code>s in the
 * collection to share a common {@link ValidatorContext}.  Each <code>Validator</code> can
 * obtain inputs from or contribute outputs to the <code>ValidatorContext</code>, thus allowing
 * a chain of <code>Validator</code>s to be created such that the output of one 
 * <code>Validator</code> can serve as the input of one or more subsequent <code>Validator</code>s
 * in the chain.  The use of the <code>ValidatorContext</code> is optional, but if it not supplied
 * then none of the <code>addValidator*</code> methods that take one or more inputs or outputs
 * as parameters can be used, and 
 * {@link #addValidatorContextUpdater(ValidatorContextUpdater) addValidatorContextUpdater}
 * cannot be called as well. 
 * <p>
 * The <code>ValidatorCollection<code> interface allows <code>Validator</code>s to be added to the 
 * collection along with other <code>ValidatorCollection<code>s, for ultimate collection nesting
 * flexibility.  In addition, a {@link ValidatorContextUpdater} can be added to the collection to
 * manipulate the shared <code>ValidatorContext</code> without performing any validation.
 * <p>
 * Implementing classes must ensure that all <code>Validator</code>s, 
 * <code>ValidatorCollection<code>s and <code>ValidatorContextUpdater</code>s are executed in
 * the order in which they are added to the collection.
 * 
 * @see ValidatorInput
 * @see ValidatorOutput
 */
public interface ValidatorCollection extends Validator {

  /**
   * Returns the context to be used by all validators in this collection.
   * 
   * @return  The context.
   */
  public ValidatorContext getValidatorContext();

  /**
   * Sets the context to be used by all validators in this collection.
   * The context will be propagated to all sub-collections of this collection if they do not
   * already have a context. 
   * 
   * @param  context  the <code>ValidatorContext</code>
   */
  public void setValidatorContext(ValidatorContext context);

  /**
   * Adds the specified <code>Validator</code> to this collection without any inputs taken from
   * or outputs supplied to the <code>ValidatorContext</code>. 
   * 
   * @param  v  the <code>Validator</code>
   */
  public void addValidator(Validator v);

  /**
   * Adds the specified <code>Validator</code> to this collection with a single input taken from
   * the <code>ValidatorContext</code>. 
   * 
   * @param  v  the <code>Validator</code>
   * @param  input  the input as a <code>ValidatorInput</code>
   */
  public void addValidator(Validator v, ValidatorInput input);

  /**
   * Adds the specified <code>Validator</code> to this collection with more than one input taken 
   * from the <code>ValidatorContext</code>. 
   * 
   * @param  v  the <code>Validator</code>
   * @param  inputs  the inputs as an array of <code>ValidatorInput</code>
   */
  public void addValidator(Validator v, ValidatorInput[] inputs);

  /**
   * Adds the specified <code>Validator</code> to this collection with a single output supplied
   * to the <code>ValidatorContext</code>. 
   * 
   * @param  v  the <code>Validator</code>
   * @param  output  the output as a <code>ValidatorOutput</code>
   */
  public void addValidator(Validator v, ValidatorOutput output);

  /**
   * Adds the specified <code>Validator</code> to this collection with a more than one output 
   * supplied to the <code>ValidatorContext</code>. 
   * 
   * @param  v  the <code>Validator</code>
   * @param  outputs  the outputs as an array of <code>ValidatorOutput</code>
   */
  public void addValidator(Validator v, ValidatorOutput[] outputs);

  /**
   * Adds the specified <code>Validator</code> to this collection with a single input taken from
   * and a single output supplied to the <code>ValidatorContext</code>. 
   * 
   * @param  v  the <code>Validator</code>
   * @param  input  the input as a <code>ValidatorInput</code>
   * @param  output  the output as a <code>ValidatorOutput</code>
   */
  public void addValidator(Validator v, ValidatorInput input, ValidatorOutput output);

  /**
   * Adds the specified <code>Validator</code> to this collection with a single input taken from
   * and more than one output supplied to the <code>ValidatorContext</code>. 
   * 
   * @param  v  the <code>Validator</code>
   * @param  input  the input as a <code>ValidatorInput</code>
   * @param  outputs  the outputs as an array of <code>ValidatorOutput</code>
   */
  public void addValidator(Validator v, ValidatorInput input, ValidatorOutput[] outputs);

  /**
   * Adds the specified <code>Validator</code> to this collection with more than one input taken 
   * from and a single output supplied to the <code>ValidatorContext</code>. 
   * 
   * @param  v  the <code>Validator</code>
   * @param  inputs  the inputs as an array of <code>ValidatorInput</code>
   * @param  output  the output as a <code>ValidatorOutput</code>
   */
  public void addValidator(Validator v, ValidatorInput[] inputs, ValidatorOutput output);

  /**
   * Adds the specified <code>Validator</code> to this collection with more than one input taken 
   * from and more than one output supplied to the <code>ValidatorContext</code>. 
   * 
   * @param  v  the <code>Validator</code>
   * @param  inputs  the inputs as an array of <code>ValidatorInput</code>
   * @param  outputs  the outputs as an array of <code>ValidatorOutput</code>
   */
  public void addValidator(Validator v, ValidatorInput[] inputs, ValidatorOutput[] outputs);

  /**
   * Adds the specified <code>ValidatorCollection</code> as a sub-collection of this collection. 
   * 
   * @param  vc  the <code>ValidatorCollection</code>
   */
  public void addValidator(ValidatorCollection vc);

  /**
   * Adds the specified <code>ValidatorContextUpdater</code> to this collection. 
   * 
   * @param  updater  the <code>ValidatorContextUpdater</code>
   */
  public void addValidatorContextUpdater(ValidatorContextUpdater updater);
  
  /**
   * Returns an indication of whether this <code>ValidatorCollection</code> is empty, that is,
   * has no validators or validator collections added to it. 
   * 
   * @return  <code>true</code> if no validators or validator collections have been added to this
   *          collection; <code>false</code> otherwise
   */
  public boolean isEmpty();
}
