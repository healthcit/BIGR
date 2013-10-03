package com.gulfstreambio.kc.form.def;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ardais.bigr.api.ApiException;
import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.iltds.btx.BtxActionErrors;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.validation.AbstractValidator;
import com.ardais.bigr.validation.Validator;
import com.ardais.bigr.validation.ValidatorErrorListener;
import com.gulfstreambio.kc.det.DataElementTaxonomy;
import com.gulfstreambio.kc.form.def.data.DataFormDefinitionDataElement;
import com.gulfstreambio.kc.form.def.data.calculation.Calculation;
import com.gulfstreambio.kc.form.def.data.calculation.DataElementDefinitionReference;
import com.gulfstreambio.kc.form.def.data.calculation.Literal;
import com.gulfstreambio.kc.form.def.data.calculation.Operand;

/**
 * Validates that the data element's calculation (if any) is valid. 
 * <p>
 * This validator may return eight errors as follows, with insertion strings 
 * listed below the error:
 * <ul>
 * <li>{@link #ERROR_KEY1} - The calculation belongs to a data element having a type that does not
 * support calculations (i.e. a text field).
 *   <ol>
 *   <li>The CUI of the data element, as set in  
 *       {@link #setDataElement(DataFormDefinitionDataElement) setDataElement}.</li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY2} - The calculation has an unrecognized operation.
 *   <ol>
 *   <li>The CUI of the data element, as set in  
 *       {@link #setDataElement(DataFormDefinitionDataElement) setDataElement}.</li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY3} - The calculation has less than two operands.
 *   <ol>
 *   <li>The CUI of the data element, as set in  
 *       {@link #setDataElement(DataFormDefinitionDataElement) setDataElement}.</li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY4} - The calculation includes an invalid data element reference.
 *   <ol>
 *   <li>The CUI of the data element, as set in  
 *       {@link #setDataElement(DataFormDefinitionDataElement) setDataElement}.</li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY5} - The calculation includes an invalid literal.
 *   <ol>
 *   <li>The CUI of the data element, as set in  
 *       {@link #setDataElement(DataFormDefinitionDataElement) setDataElement}.</li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY6} - The calculation includes a circular reference (i.e. data element
 * A has a calculation that references data element B; data element B has a calculation that
 * references data element C; data element C has a calculation that references data element A).
 *   <ol>
 *   <li>The CUI of the data element, as set in  
 *       {@link #setDataElement(DataFormDefinitionDataElement) setDataElement}.</li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY7} - The calculation includes operands that are not valid for the operation
 * (i.e. the operation is multiply and one or more operands is a date).
 *   <ol>
 *   <li>The CUI of the data element, as set in  
 *       {@link #setDataElement(DataFormDefinitionDataElement) setDataElement}.</li>
 *   </ol>
 * </li>
 * <li>{@link #ERROR_KEY8} - The calculation returns a value that is not of the correct type (i.e.
 * the data element is a date, but the calculation returns an integer).
 *   <ol>
 *   <li>The CUI of the data element, as set in  
 *       {@link #setDataElement(DataFormDefinitionDataElement) setDataElement}.</li>
 *   </ol>
 * </li>
 * </ul> 
 */
public class ValidatorFormDefinitionCalculation extends AbstractValidator {

  private DataElementTaxonomy _det; 
  private DataFormDefinitionDataElement _deDef;

  /**
   * The keys of the errors returned from this validator.  
   */
  public final static String ERROR_KEY1 = "kc.error.formdef.calculation.unsupportedDataType";
  public final static String ERROR_KEY2 = "kc.error.formdef.calculation.unrecognizedOperation";
  public final static String ERROR_KEY3 = "kc.error.formdef.calculation.insufficientOperands";
  public final static String ERROR_KEY4 = "kc.error.formdef.calculation.invalidDataElementReference";
  public final static String ERROR_KEY5 = "kc.error.formdef.calculation.invalidLiteral";
  public final static String ERROR_KEY6 = "kc.error.formdef.calculation.circularReference";
  public final static String ERROR_KEY7 = "kc.error.formdef.calculation.invalidOperandsForOperation";
  public final static String ERROR_KEY8 = "kc.error.formdef.calculation.invalidReturnValue";
  
  // The default error listener.
  class DefaultErrorListener implements ValidatorErrorListener {
    public boolean validatorError(Validator v, String errorKey) {
      ValidatorFormDefinitionCalculation v1 = (ValidatorFormDefinitionCalculation) v;
      v1.addErrorMessage(errorKey, v1.getDataElement().getCui());
      return true;
    }
  }

  /**
   * Creates a new <code>ValidatorFormDefinitionCalculation</code> validator.
   */
  public ValidatorFormDefinitionCalculation() {
    super();
    addErrorKey(ERROR_KEY1);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY1);
    addErrorKey(ERROR_KEY2);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY2);
    addErrorKey(ERROR_KEY3);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY3);
    addErrorKey(ERROR_KEY4);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY4);
    addErrorKey(ERROR_KEY5);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY5);
    addErrorKey(ERROR_KEY6);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY6);
    addErrorKey(ERROR_KEY7);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY7);
    addErrorKey(ERROR_KEY8);
    addValidatorErrorListener(new DefaultErrorListener(), ERROR_KEY8);
    }

  /* (non-Javadoc)
   * @see com.ardais.bigr.validation.Validator#validate()
   */
  public BtxActionErrors validate() {
    if (getDet() == null) {
      throw new ApiException("ValidatorFormDefinitionCalculation: DET was not specified");
    }
      
    DataFormDefinitionDataElement deDef = getDataElement();
    if (deDef != null) {
      validateDataType();
      //only bother checking the calculation syntax if the data type is valid
      if (getActionErrors().empty()) {
        validateCalculationSyntax(deDef.getCalculation());
      }
      //only bother checking the calculation logic if the syntax is valid
      if (getActionErrors().empty()) {
        validateCalculationLogic(deDef.getCalculation());
      }
    }
    return getActionErrors();
  }
  
  //check that this data element is of a type for which calculations are supported
  private void validateDataType() {
    if (getDataElement().getCalculation() != null) {
      String dataType = getDet().getDataElement(getDataElement().getCui()).getDatatype();
      if (!FormUtils.VALID_CALCULATION_DATATYPES.contains(dataType)) {
        notifyValidatorErrorListener(ERROR_KEY1);  
      }
    }
  }
  
  //check that the calculation is syntactically valid
  private void validateCalculationSyntax(Calculation calculation) {
    if (calculation != null) {
      checkOperation(calculation);
      checkOperands(calculation);
    }
  }
  
  //check that the operation specific in the calculation is valid
  private void checkOperation(Calculation calculation) {
    String operation = calculation.getOperation();
    if (ApiFunctions.isEmpty(operation) ||
        !FormUtils.VALID_CALCULATION_OPERATIONS.contains(operation)) {
      notifyValidatorErrorListener(ERROR_KEY2);  
    }          
  }
  
  //check that the operands in the calculation are valid
  private void checkOperands(Calculation calculation) {
    checkForInsufficientOperands(calculation);
    checkForInvalidOperands(calculation);
  }
  
  //check that at least two operands have been specified.
  private void checkForInsufficientOperands(Calculation calculation) {
    List operands = calculation.getOperands();
    if (ApiFunctions.isEmpty(operands) || operands.size() < 2) {
      notifyValidatorErrorListener(ERROR_KEY3);  
    }          
  }
  
  //check that all operands are syntactically valid
  private void checkForInvalidOperands(Calculation calculation) {
    List operands = calculation.getOperands();
    if (!ApiFunctions.isEmpty(operands)) {
      Iterator operandIterator = operands.iterator();
      while (operandIterator.hasNext()) {
        Operand operand = (Operand)operandIterator.next();
        if (operand instanceof DataElementDefinitionReference) {
          checkForInvalidDataElementReference((DataElementDefinitionReference)operand);
        }
        else if (operand instanceof Literal) {
          checkForInvalidLiteral((Literal)operand);
        }
        else if (operand instanceof Calculation) {
          validateCalculationSyntax((Calculation)operand);
        }
      }
    }
  }    
  
  //check that a data element reference refers to a data element on the form with a supported
  //data type.
  private void checkForInvalidDataElementReference(DataElementDefinitionReference reference) {
    String cui = reference.getCui();
    if (ApiFunctions.isEmpty(cui) || 
        getDataElement().getForm().getDataElement(cui) == null) {
      notifyValidatorErrorListener(ERROR_KEY4);  
    }
    else {
      String dataType = getDet().getDataElement(cui).getDatatype();
      if (!FormUtils.VALID_CALCULATION_DATATYPES.contains(dataType)) {
        notifyValidatorErrorListener(ERROR_KEY4);  
      }
      
    }
  }
  
  //check that a literal is valid.
  private void checkForInvalidLiteral(Literal literal) {
    String dataType = literal.getDataType();
    String value = literal.getValue();
    if (ApiFunctions.isEmpty(dataType) || 
        !FormUtils.VALID_CALCULATION_DATATYPES.contains(dataType)) {
      notifyValidatorErrorListener(ERROR_KEY5);  
    }
    else {
      if (dataType.equals(FormUtils.CALCULATION_DATATYPE_DATE)) {
        //if the value is not the constant "today", verify it represents a date
        if (!FormUtils.CALCULATION_DATE_LITERAL_TODAY.equalsIgnoreCase(value)) {
          try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            sdf.setLenient(false);
            Date date = sdf.parse(value);
          }
          catch (ParseException e) {
            notifyValidatorErrorListener(ERROR_KEY5);  
          }
        }
      }
      else if (dataType.equals(FormUtils.CALCULATION_DATATYPE_FLOAT)) {
        //verify value represents a float
        try {
          Float floatValue = new Float(value);
        }
        catch (NumberFormatException nfe) {
          notifyValidatorErrorListener(ERROR_KEY5);  
        }
      }
      else if (dataType.equals(FormUtils.CALCULATION_DATATYPE_INTEGER)) {
        //verify value represents an integer
        try {
          Integer integerValue = new Integer(value);
        }
        catch (NumberFormatException nfe) {
          notifyValidatorErrorListener(ERROR_KEY5);  
        }
      }
    }
  }
  
  //check that the calculation is logically valid
  private void validateCalculationLogic(Calculation calculation) {
    if (calculation != null) {
      checkCircularReference(calculation);
      checkOperandTypesForOperation(calculation);
      //if the operands are of the appropriate type for the operation, make sure the
      //resultant data type can be placed in the target data element
      if (getActionErrors().empty()) {
        checkResultantDataType(calculation);
      }
    }
  }
  
  //check that the calculation does not contain any operands that themselves refer back to the
  //original data element.
  private void checkCircularReference(Calculation calculation) {
    Set dataElementReferenceCuis = getDataElementReferenceCuisInCalculation(calculation);
    boolean circularReferenceFound = processReferences(dataElementReferenceCuis);
    if (circularReferenceFound) {
      notifyValidatorErrorListener(ERROR_KEY6);  
    }
  }
  
  //private method to recursively check for a circular reference to the original data element
  private boolean processReferences(Set dataElementReferenceCuis) {
    dataElementReferenceCuis = ApiFunctions.safeSet(dataElementReferenceCuis);
    boolean referenceFound = false;
    if (dataElementReferenceCuis.contains(getDataElement().getCui())) {
      referenceFound = true;
    }
    else {
      Iterator cuiIterator = dataElementReferenceCuis.iterator();
      while (cuiIterator.hasNext() && !referenceFound) {
        String cui = (String)cuiIterator.next();
        DataFormDefinitionDataElement dataElement = (DataFormDefinitionDataElement)getDataElement().getForm().getDataElement(cui);
        Calculation calculation = dataElement.getCalculation();
        Set childReferences = getDataElementReferenceCuisInCalculation(calculation);
        //see if the child contains any references to the parent.  Only do this if the child doesn't
        //contain a reference to itself to avoid an infinite loop.  Note that the child's reference
        //to itself will be discovered when that element is checked for circular references
        if (!childReferences.contains(dataElement.getCui())) {
          referenceFound = processReferences(childReferences);
        }
      }
    }
    return referenceFound;
  }
  
  //private method that identifies the cuis of all data element references contained
  //in a calculation
  private Set getDataElementReferenceCuisInCalculation(Calculation calculation) {
    Set returnValue = new HashSet();
    if (calculation == null) {
      return returnValue;
    }
    List operands = calculation.getOperands();
    if (!ApiFunctions.isEmpty(operands)) {
      Iterator operandIterator = operands.iterator();
      while (operandIterator.hasNext()) {
        Operand operand = (Operand)operandIterator.next();
        if (operand instanceof DataElementDefinitionReference) {
          returnValue.add(((DataElementDefinitionReference)operand).getCui());
        }
        else if (operand instanceof Calculation) {
          returnValue.addAll(getDataElementReferenceCuisInCalculation((Calculation)operand));
        }
      }
    }
    return returnValue;
  }
  
  //check that the calculation has the correct type of operands for its operation
  private void checkOperandTypesForOperation(Calculation calculation) {
    if (calculation != null) {
      if (!FormUtils.VALID_CALCULATION_DATATYPES.contains(calculation.getDataType())) {
        notifyValidatorErrorListener(ERROR_KEY7);  
      }
    }
  }
  
  //check that the calculation returns the correct data type for it's data element
  private void checkResultantDataType(Calculation calculation) {
    if (calculation != null) {
      String dataType = getDet().getDataElement(getDataElement().getCui()).getDatatype();
      //if the calculation is for a date field, the calculation must return a date
      if (FormUtils.CALCULATION_DATATYPE_DATE.equals(dataType) &&
          !FormUtils.CALCULATION_DATATYPE_DATE.equals(calculation.getDataType())) {
        notifyValidatorErrorListener(ERROR_KEY8);  
      }
      //if the calculation is for a float field, the calculation must return an integer or a float
      else if (FormUtils.CALCULATION_DATATYPE_FLOAT.equals(dataType) &&
          !FormUtils.CALCULATION_DATATYPE_FLOAT.equals(calculation.getDataType()) &&
          !FormUtils.CALCULATION_DATATYPE_INTEGER.equals(calculation.getDataType())) {
        notifyValidatorErrorListener(ERROR_KEY8);  
      }
      //if the calculation is for an integer field, the calculation must return an integer
      else if (FormUtils.CALCULATION_DATATYPE_INTEGER.equals(dataType) &&
          !FormUtils.CALCULATION_DATATYPE_INTEGER.equals(calculation.getDataType())) {
        notifyValidatorErrorListener(ERROR_KEY8);  
      }
    }
  }

  public DataFormDefinitionDataElement getDataElement() {
    return _deDef;
  }

  public void setDataElement(DataFormDefinitionDataElement definition) {
    _deDef = definition;
  }

  /**
   * @return Returns the det.
   */
  public DataElementTaxonomy getDet() {
    return _det;
  }
  /**
   * @param det The det to set.
   */
  public void setDet(DataElementTaxonomy det) {
    _det = det;
  }
}