package com.ardais.bigr.validation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.iltds.btx.BtxActionErrors;

/**
 * Implementation of a proceeding validator collection.  A <i>proceeding</i> validator collection
 * is one in which all supplied validators are executed, regardless of whether any previous
 * validators returned errors. 
 */
public class ValidatorCollectionProceeding implements ValidatorCollection {

  protected static Log _log = LogFactory.getLog(ApiLogger.BIGR_LOGNAME_VALIDATION);

  /**
   * The optional name of this validator collection.
   */
  private String _name;
  
  /**
   * The <code>ValidatorContext</code> shared by all validators, validator collections and context 
   * updaters.
   */
  private ValidatorContext _context;

  /**
   * The list of <code>ValidatorItem</code>s supplied to this validator collection.
   */
  private List _validators;

  /**
   * The collection of BTX action errors returned by all executed validators.
   */
  private BtxActionErrors _actionErrors;

  /**
   * A flag to indicate whether the optional context needs to be set.
   */
  private boolean _needContext = false;
  
  /**
   * A flag that is used to help in logging.  We only want to log root collections to make the log
   * easier to read.  By default we assume that a validator collection is a root, and then set it 
   * to not be a root if it is added to another collection.
   */
  private boolean _root = true;

  /**
   * An inner class that holds an item (validator, validator collection or context updater) 
   * supplied to the enclosing <code>ValidatorCollectionProceeding</code> instance.  This class
   * also holds the inputs and ouputs for validators and provides a method to determine if the
   * inputs and outputs were properly specified.
   */
  private class ValidatorItem {
    private Validator _v;
    private ValidatorInput[] _inputs;    
    private ValidatorOutput[] _outputs;
    private ValidatorCollection _vc;
    private ValidatorContextUpdater _updater;    
    
    private ValidatorItem(Validator v, ValidatorInput[] inputs, ValidatorOutput[] outputs) {
      _v = v;
      _inputs = inputs;
      _outputs = outputs;
      if ((inputs != null && inputs.length > 0) || (outputs != null && outputs.length > 0)) {
        _needContext = true;
      }
    }
    private ValidatorItem(ValidatorCollection vc) {
      _vc = vc;
    }
    private ValidatorItem(ValidatorContextUpdater updater) {
      _updater = updater;
      _needContext = true;
    }
    private Validator getValidator() { return _v; }
    private ValidatorCollection getValidatorCollection() { return _vc; }
    private ValidatorInput[] getValidatorInputs() { return _inputs; }
    private ValidatorOutput[] getValidatorOutputs() { return _outputs; }
    private ValidatorContextUpdater getUpdater() { return _updater; }
    private void validateInputsAndOutputs(ValidatorContext context) {
      //TODO MRC: check that the return type of the readable is assignable as the parameter of the writeable
      if (false) { 
        //TODO   the check above should be (context != null) 
        //We are skipping the following checks because 
        //we need to check the input nested property names are valid via Class reflection
        //PropertyUtils.isReadable and isWriteable do these checks on bean Instances
        //the problem is those instances might not exist yet
        if ((_inputs != null) && (_inputs.length > 0)) {
          for (int i = 0; i < _inputs.length; i++) {
            if (!PropertyUtils.isWriteable(_v, _inputs[i].getValidatorProperty())) {
              throw new ApiException("Attempt to add a validator input with a non-writable validator property (" + _inputs[i].getValidatorProperty() + ") to a validator (" + _v.toString() + ")"); 
            }
            if (!PropertyUtils.isReadable(context, _inputs[i].getContextProperty())) {
              throw new ApiException("Attempt to add a validator input with a non-readable context property (" + _inputs[i].getContextProperty() + ") to a validator (" + _v.toString() + ")"); 
            }        
          }
        }
        if ((_outputs != null) && (_outputs.length > 0)) {
          for (int i = 0; i < _outputs.length; i++) {
            if (!PropertyUtils.isReadable(_v, _outputs[i].getValidatorProperty())) {
              throw new ApiException("Attempt to add a validator output with a non-readable validator property (" + _outputs[i].getValidatorProperty() + ") to a validator (" + _v.toString() + ")"); 
            }
            if (!PropertyUtils.isWriteable(context, _outputs[i].getContextProperty())) {
              throw new ApiException("Attempt to add a validator output with a non-writeable context property (" + _outputs[i].getContextProperty() + ") to a validator (" + _v.toString() + ")"); 
            }        
          }
        }
      }
    }
  }

  /**
   * Creates a new <code>ValidatorCollectionProceeding</code>.
   */
  public ValidatorCollectionProceeding() {
    super();
  }

  /**
   * Creates a new <code>ValidatorCollectionProceeding</code> with the specified name.
   * 
   * @param  name  the name
   */
  public ValidatorCollectionProceeding(String name) {
    this();
    setName(name);
  }

  public String getName() {
    return _name;
  }
  
  /**
   * Assigns a name to this validator collection.
   * 
   * @param name  the name
   */
  public void setName(String name) {
    _name = name;
  }

  public ValidatorContext getValidatorContext() {
    return _context;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorCollectionI#setValidatorContext(com.ardais.bigr.validation.ValidatorContext)
   */
  public void setValidatorContext(ValidatorContext context) {
    _context = context;

    Iterator i = getValidators().iterator();
    while (i.hasNext()) {
      ValidatorItem item = (ValidatorItem) i.next();
      ValidatorCollection vc = item.getValidatorCollection();
      Validator v = item.getValidator();

      // Propagate the context to each sub-collection.
      if (vc != null) {
        if (vc.getValidatorContext() == null) {
          vc.setValidatorContext(context);          
        }
      }

      // Validate the inputs and outputs of each validator.
      else if (v != null) {
        item.validateInputsAndOutputs(context);
      }
    }
    
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorCollectionI#addValidator(com.ardais.bigr.validation.Validator)
   */
  public void addValidator(Validator v) {
    addValidator(v, new ValidatorInput[0], new ValidatorOutput[0]);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorCollectionI#addValidator(com.ardais.bigr.validation.Validator, com.ardais.bigr.validation.ValidatorContextInput)
   */
  public void addValidator(Validator v, ValidatorInput input) {
    addValidator(v, new ValidatorInput[] { input }, new ValidatorOutput[0]);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorCollectionI#addValidator(com.ardais.bigr.validation.Validator, com.ardais.bigr.validation.ValidatorContextInput[])
   */
  public void addValidator(Validator v, ValidatorInput[] inputs) {
    addValidator(v, inputs, new ValidatorOutput[0]);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorCollectionI#addValidator(com.ardais.bigr.validation.Validator, com.ardais.bigr.validation.ValidatorContextOutput)
   */
  public void addValidator(Validator v, ValidatorOutput output) {
    addValidator(v, new ValidatorInput[0], new ValidatorOutput[] { output });
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorCollectionI#addValidator(com.ardais.bigr.validation.Validator, com.ardais.bigr.validation.ValidatorContextOutput[])
   */
  public void addValidator(Validator v, ValidatorOutput[] outputs) {
    addValidator(v, new ValidatorInput[0], outputs);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorCollectionI#addValidator(com.ardais.bigr.validation.Validator, com.ardais.bigr.validation.ValidatorContextInput, com.ardais.bigr.validation.ValidatorContextOutput)
   */
  public void addValidator(
    Validator v,
    ValidatorInput input,
    ValidatorOutput output) {
    addValidator(v, new ValidatorInput[] { input }, new ValidatorOutput[] { output });
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorCollectionI#addValidator(com.ardais.bigr.validation.Validator, com.ardais.bigr.validation.ValidatorContextInput, com.ardais.bigr.validation.ValidatorContextOutput[])
   */
  public void addValidator(
    Validator v,
    ValidatorInput input,
    ValidatorOutput[] outputs) {
    addValidator(v, new ValidatorInput[] { input }, outputs);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorCollectionI#addValidator(com.ardais.bigr.validation.Validator, com.ardais.bigr.validation.ValidatorContextInput[], com.ardais.bigr.validation.ValidatorContextOutput)
   */
  public void addValidator(
    Validator v,
    ValidatorInput[] inputs,
    ValidatorOutput output) {
    addValidator(v, inputs, new ValidatorOutput[] { output });
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorCollectionI#addValidator(com.ardais.bigr.validation.Validator, com.ardais.bigr.validation.ValidatorContextInput[], com.ardais.bigr.validation.ValidatorContextOutput[])
   */
  public void addValidator(
    Validator v,
    ValidatorInput[] inputs,
    ValidatorOutput[] outputs) {

    // Create the mapping and validate the inputs and outputs if the context has already been
    // set.  If it has not been set yet, then we'll validate all input and outputs when the 
    // context is set.
    ValidatorItem item = new ValidatorItem(v, inputs, outputs);
    ValidatorContext context = getValidatorContext();
    if (context != null) {
      item.validateInputsAndOutputs(context);
    }
    getValidators().add(item);
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorCollectionI#addValidatorCollection(com.ardais.bigr.validation.ValidatorCollection)
   */
  public void addValidator(ValidatorCollection vc) {
    ValidatorItem item = new ValidatorItem(vc);
    getValidators().add(item);

    // If the collection being added is a loggable validator collection, then set it to not
    // be the root collection, since it is being added to this collection.
    if (vc instanceof ValidatorCollectionProceeding) {
      ((ValidatorCollectionProceeding) vc).setRoot(false);
    }
    
    // Propagate this collection's context to the sub-collection, if the sub-collection does not
    // have a validator context.
    if (vc.getValidatorContext() == null) {
      vc.setValidatorContext(getValidatorContext());      
    }
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorCollectionI#addValidatorContextUpdater(com.ardais.bigr.validation.ValidatorContextUpdater)
   */
  public void addValidatorContextUpdater(ValidatorContextUpdater updater) {
    getValidators().add(new ValidatorItem(updater));
  }

  /**
   * Returns all of the accumulated <code>BtxActionError</code>s.
   *  
   * @return  The <code>BtxActionErrors</code> containing all of the errors.  If there are no
   *          errors an empty <code>BtxActionErrors</code> is returned.
   */
  protected BtxActionErrors getActionErrors() {
    if (_actionErrors == null) {
      _actionErrors = new BtxActionErrors();
    }
    return _actionErrors;
  }

  /**
   * Returns an indication of whether the {@link #validate() validate} method should proceed
   * when it encounters an error.  This can be useful for implementing subclasses that want to
   * change the default behavior in this class.
   *  
   * @return  <code>true</code> 
   */
  protected boolean isProceedOnError() {
    return true;
  }
  
  private List getValidators() {
    if (_validators == null) {
      _validators = new ArrayList();
    }
    return _validators;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.ValidatorCollectionI#isEmpty()
   */
  public boolean isEmpty() {
    boolean empty = true;
    Iterator i = getValidators().iterator();
    while (i.hasNext() && empty) {
      ValidatorItem item = (ValidatorItem) i.next();
      Validator v = item.getValidator();
      if (v == null) {
        v = item.getValidatorCollection();
      }
      if (v != null) {
        empty = false;
      }
    }
    return empty;
  }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    // If this is the root collection, then log information on the configuration of the collection.
    if (isRoot()) {
      logCollection();
    }

    // Log the execution of this collection.
    logExecution(this);
    
    // First make sure that the context was set at some point if there are any validators
    // that require it or any context updaters.
    ValidatorContext context = getValidatorContext();
    if (context == null && _needContext) {
      throw new ApiException("In ValidatorCollectionProceeding.validate: Attempt to validate a collection of validators that needs a context without setting the context.");
    }

    // Iterate over all validators, executing each and accumulating the errors, setting inputs
    // from the context and setting outputs to the context.
    BtxActionErrors errors = getActionErrors();
    boolean proceed = true;
    Iterator i = getValidators().iterator();
    while (i.hasNext() && proceed) {

      // Get the next item from the list of validators in this collection.  If it's a validator 
      // then set its inputs, call its validate method and get its outputs. 
      ValidatorItem item = (ValidatorItem) i.next();
      Validator v = item.getValidator();
      if (v != null) {        
        logExecution(v);
        
        ValidatorInput[] inputs = item.getValidatorInputs();
        if (inputs != null) {
          for (int j = 0; j < inputs.length; j++) {
            try {
              String contextProperty = inputs[j].getContextProperty(); 
              Object contextValue = PropertyUtils.getProperty(context, contextProperty);
              String validatorProperty = inputs[j].getValidatorProperty(); 
              PropertyUtils.setProperty(v, validatorProperty, contextValue);            
            }
            catch (Exception e) {
              ApiFunctions.throwAsRuntimeException(e);
            }
          }
        }
        
        BtxActionErrors newErrors = v.validate();
        if ((newErrors != null) && !newErrors.empty()) {
          errors.addErrors(newErrors);
          if (!isProceedOnError()) {
            proceed = false;
          }
        }
        else {
          ValidatorOutput[] outputs = item.getValidatorOutputs();
          if (outputs != null) { 
            for (int j = 0; j < outputs.length; j++) {
              try {
                String validatorProperty = outputs[j].getValidatorProperty(); 
                Object validatorValue = PropertyUtils.getProperty(v, validatorProperty);
                String contextProperty = outputs[j].getContextProperty(); 
                PropertyUtils.setProperty(context, contextProperty, validatorValue);            
              }
              catch (Exception e) {
                ApiFunctions.throwAsRuntimeException(e);
              }
            }
          }
        }
      }

      // If we did not get a validator from the list, then check if it's a validator 
      // collection.  If it is execute the validators in the collection.
      else {
        ValidatorCollection vc = item.getValidatorCollection();
        if (vc != null) {
          BtxActionErrors newErrors = vc.validate();
          if ((newErrors != null) && !newErrors.empty()) {
            errors.addErrors(newErrors);
            if (!isProceedOnError()) {
              proceed = false;
            }
          }          
        }
        
        // The item must be a context updater, so get the updater and call its update method.        
        else {
          ValidatorContextUpdater updater = item.getUpdater();
          logExecution(updater);
          updater.update(context);          
        }
      }
    }
    return errors;
  }

  private boolean isRoot() {
    return _root;
  }

  private void setRoot(boolean root) {
    _root = root;
  }

  /**
   * Logs a representation of this validator collection and all of its sub-collections to
   * the validation logger.  
   */
  private void logCollection() {
    _log.info("-------------------------------------------------------------");
    _log.info("----- validator configuration (" + (new Timestamp(System.currentTimeMillis())).toString() + ") -----");
    _log.info("-------------------------------------------------------------");
    logCollection(0);
    _log.info("-------------------------------------------------------------");
  }

  private void logCollection(int level) {
    logCollection(level, this);
    
    // Log the context, if any, and all of its properties.
    if (isRoot()) {
      ValidatorContext context = getValidatorContext();
      if (context != null) {
        logCollection(level + 1, context);
      }
    }

    // Iterate over all collection items, logging suitable output for each.
    Iterator i = getValidators().iterator();
    while (i.hasNext()) {

      // Get the item from the mapping.  It will be either a validator, a validator collection
      // or a context updater.  We handle logging of each of these differently.
      ValidatorItem item = (ValidatorItem) i.next();

      // The item is a validator. 
      Validator v = item.getValidator();
      if (v != null) {
        logCollection(level + 1, v);
        logCollection(level + 1, item.getValidatorInputs());
        logCollection(level + 1, item.getValidatorOutputs());
      }
      
      else {

        // The item is a validator collection. 
        v = item.getValidatorCollection();
        if (v != null) {
          ((ValidatorCollectionProceeding)v).logCollection(level + 1);
        }
        
        else {

          // The item is a context updater. 
          logCollection(level + 1, item.getUpdater());          
        }
      }
    }
  }

  private void logCollection(int level, ValidatorContext v) {
    StringBuffer buf = new StringBuffer();
    buf.append("- ");
    int numSpaces = level*3;
    for (int i = 0; i < numSpaces; i++) {
      buf.append(" ");
    }
    buf.append("context: ");
    buf.append(ApiFunctions.shortClassName(v.getClass().getName()));
    _log.info(buf.toString());
    
    try {
      // Sort the properties before logging them.
      SortedSet properties = new TreeSet(); 
      Iterator i = BeanUtils.describe(v).keySet().iterator();
      while (i.hasNext()) {
        properties.add((String) i.next());
      }
      
      i = properties.iterator();
      while (i.hasNext()) {
        String property = (String) i.next();
        if (!property.equals("class")) {
          buf = new StringBuffer();
          buf.append("- ");
          numSpaces = (level+1)*3;
          for (int j = 0; j < numSpaces; j++) {
            buf.append(" ");
          }
          buf.append("property: ");
          buf.append(property);
          _log.info(buf.toString());
        }
      }
    }
    catch (Exception e) {
      // ignore
    }
  }
  
  private void logCollection(int level, Validator v) {
    logCollection(level, v.getName(), ApiFunctions.shortClassName(v.getClass().getName()));
  }

  private void logCollection(int level, ValidatorContextUpdater v) {
    logCollection(level, v.getName(), ApiFunctions.shortClassName(v.getClass().getName()));
  }

  private void logCollection(int level, String name, String className) {
    StringBuffer buf = new StringBuffer();
    buf.append("- ");
    int numSpaces = level*3;
    for (int i = 0; i < numSpaces; i++) {
      buf.append(" ");
    }
    if (ApiFunctions.isEmpty(name)) {
      buf.append("<unnamed>");      
    }
    else {
      buf.append(name);      
    }
    buf.append(" (");
    buf.append(className);
    buf.append(")");
    
    _log.info(buf.toString());
  }

  private void logCollection(int level, ValidatorInput[] inputs) {
    if (inputs != null) {
      int numSpaces = (level+1)*3;
      for (int j = 0; j < inputs.length; j++) {
        StringBuffer buf = new StringBuffer();
        buf.append("- ");
        for (int i = 0; i < numSpaces; i++) {
          buf.append(" ");
        }
        buf.append("input[");
        buf.append(j);
        buf.append("]: contextProperty=");
        buf.append(inputs[j].getContextProperty());
        buf.append("; validatorProperty=");
        buf.append(inputs[j].getValidatorProperty());
        _log.info(buf.toString());
      }
    }
  }

  private void logCollection(int level, ValidatorOutput[] outputs) {
    if (outputs != null) {
      int numSpaces = (level+1)*3;
      for (int j = 0; j < outputs.length; j++) {
        StringBuffer buf = new StringBuffer();
        buf.append("- ");
        for (int i = 0; i < numSpaces; i++) {
          buf.append(" ");
        }
        buf.append("output[");
        buf.append(j);
        buf.append("]: contextProperty=");
        buf.append(outputs[j].getContextProperty());
        buf.append("; validatorProperty=");
        buf.append(outputs[j].getValidatorProperty());
        _log.info(buf.toString());
      }
    }
  }

  private void logExecution(String type, String name, String className) {
    StringBuffer buf = new StringBuffer();
    buf.append("... ");      
    buf.append(type);      
    buf.append(": ");      
    if (ApiFunctions.isEmpty(name)) {
      buf.append("<unnamed>");      
    }
    else {
      buf.append(name);      
    }
    buf.append(" (");
    buf.append(className);
    buf.append(")");
    
    _log.debug(buf.toString());
  }
  
  
  private void logExecution(Validator v) {
    logExecution("validator", v.getName(), ApiFunctions.shortClassName(v.getClass().getName()));
  }

  private void logExecution(ValidatorCollection v) {
    logExecution("collection", v.getName(), ApiFunctions.shortClassName(v.getClass().getName()));
  }

  private void logExecution(ValidatorContextUpdater v) {
    logExecution("context updater", v.getName(), ApiFunctions.shortClassName(v.getClass().getName()));
  }
}
