package com.gulfstreambio.kc.form.def.data.calculation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.kc.form.helpers.FormUtils;
import com.ardais.bigr.util.BigrXmlUtils;
import com.gulfstreambio.kc.form.def.results.ResultsFormDefinitionDataElement;

public class Calculation implements Serializable, Operand {
  
  private String _operation;
  private String _convertResultToIntMethod;
  private List _operands = new ArrayList();
  private int _xmlIndentLevel = 0;
  private String _dataType;
  private Collection _referencedDataElementCuis;
  
  public Calculation() {
    super();
  }
  
  /**
   * Add an operand to this calculation
   * @param operand
   */
  public void addOperand(Operand operand) {
    if (_operands == null) {
      _operands = new ArrayList();
    }
    _operands.add(operand);
    _dataType = null;
  }
  
  /**
   * @return Returns the operands.
   */
  public List getOperands() {
    return Collections.unmodifiableList(_operands);
  }
  
  /**
   * @return Returns the operation.
   */
  public String getOperation() {
    return _operation;
  }
  
  /**
   * @return Returns the convertResultToIntMethod.
   */
  public String getConvertResultToIntMethod() {
    return _convertResultToIntMethod;
  }
  
  /**
   * @param operation The operation to set.
   */
  public void setOperation(String operation) {
    _operation = operation;
    _dataType = null;
  }
  
  /**
   * @param convertResultToIntMethod The convertResultToIntMethod to set.
   */
  public void setConvertResultToIntMethod(String convertResultToIntMethod) {
    _convertResultToIntMethod = convertResultToIntMethod;
  }
  
  public String toXml() {
    StringBuffer buf = new StringBuffer(1024);
    toXml(buf);
    return buf.toString();
  }
 
  public void toXml(StringBuffer buf) {
    BigrXmlUtils.writeElementStartTag(buf, "calculation", getXmlIndentLevel());
    BigrXmlUtils.writeAttribute(buf, "operation", getOperation());
    if (!ApiFunctions.isEmpty(getConvertResultToIntMethod())) {
      BigrXmlUtils.writeAttribute(buf, "convertResultToIntMethod", getConvertResultToIntMethod());
    }
    buf.append(">");
    Iterator operandIterator = getOperands().iterator();
    while (operandIterator.hasNext()) {
      Operand operand = (Operand)operandIterator.next();
      operand.setXmlIndentLevel(this.getXmlIndentLevel() + 1);
      operand.toXml(buf);
    }
    BigrXmlUtils.writeElementEndTag(buf, "calculation", getXmlIndentLevel());
  }
  
  public int getXmlIndentLevel() {
    return _xmlIndentLevel;
  }

  public void setXmlIndentLevel(int indentLevel) {
    _xmlIndentLevel = indentLevel;
  }

  /**
   * Method to determine the resultant data type of this calculation.  May return
   * "invalid" if the type cannot be determined - for example, a calculation that has
   * an operation of "multiply" but contains one or more date operands.
   */
  public String getDataType() {
    if (ApiFunctions.isEmpty(_dataType)) {
      boolean invalidOperandFound = false;
      String currentDataType = null;
      Iterator operandIterator = getOperands().iterator();
      //iterate over the operands and determine the resulting datatype of this
      //calculation.
      while (operandIterator.hasNext() && !invalidOperandFound) {
        String operandDataType = ((Operand)operandIterator.next()).getDataType();
        //if the operand we're working with is not of a valid datatype, stop processing
        if (!FormUtils.VALID_CALCULATION_DATATYPES.contains(operandDataType)) {
          invalidOperandFound = true;
        }
        else {
          //if this is the first operand we're processing, use it as the default data type of the
          //entire calculation
          if (ApiFunctions.isEmpty(currentDataType)) {
            currentDataType = operandDataType;
          }
          else {
            //if the data type determined so far is a date
            if (FormUtils.CALCULATION_DATATYPE_DATE.equals(currentDataType)) {
              //if the operation is either multiply or divide, it doesn't matter what
              //the data type of the current operand is - return invalid.  Date operands
              //are not valid for multiplication or division operations
              if (FormUtils.CALCULATION_OPERATION_MULTIPLY.equals(getOperation()) ||
                  FormUtils.CALCULATION_OPERATION_DIVIDE.equals(getOperation())) {
                invalidOperandFound = true;
              }
              //if the operation is add, then the current operand must be an integer
              //(only integers can be added to dates)
              else if (FormUtils.CALCULATION_OPERATION_ADD.equals(getOperation())) {
                if (FormUtils.CALCULATION_DATATYPE_INTEGER.equals(operandDataType)) {
                  //date + integer is a date
                  currentDataType = FormUtils.CALCULATION_DATATYPE_DATE;
                }
                else {
                  invalidOperandFound = true;
                }
              }
              //if the operation is subtract, then the current operand must be an 
              //integer or a date
              else if (FormUtils.CALCULATION_OPERATION_SUBTRACT.equals(getOperation())) {
                if (FormUtils.CALCULATION_DATATYPE_DATE.equals(operandDataType)) {
                  //date - date is an integer
                  currentDataType = FormUtils.CALCULATION_DATATYPE_INTEGER;
                }
                else if (FormUtils.CALCULATION_DATATYPE_INTEGER.equals(operandDataType)) {
                  //date - integer is a date
                  currentDataType = FormUtils.CALCULATION_DATATYPE_DATE;
                }
                else {
                  invalidOperandFound = true;
                }
              }
            }
            //if the data type determined so far is an integer
            else if (FormUtils.CALCULATION_DATATYPE_INTEGER.equals(currentDataType)) {
              //if the operation is multiply, the data type of the current 
              //operand must be integer or float
              if (FormUtils.CALCULATION_OPERATION_MULTIPLY.equals(getOperation())) {
                if (FormUtils.CALCULATION_DATATYPE_FLOAT.equals(operandDataType)) {
                  //integer * float is a float
                  currentDataType = FormUtils.CALCULATION_DATATYPE_FLOAT;
                }
                else if (FormUtils.CALCULATION_DATATYPE_INTEGER.equals(operandDataType)) {
                  //integer * integer is an integer
                  currentDataType = FormUtils.CALCULATION_DATATYPE_INTEGER;
                }
                else {
                  invalidOperandFound = true;
                }
              }
              //if the operation is divide, the data type of the current 
              //operand must be integer or float
              else if (FormUtils.CALCULATION_OPERATION_DIVIDE.equals(getOperation())) {
                if (FormUtils.CALCULATION_DATATYPE_FLOAT.equals(operandDataType)) {
                  //integer / float is a float
                  currentDataType = FormUtils.CALCULATION_DATATYPE_FLOAT;
                }
                else if (FormUtils.CALCULATION_DATATYPE_INTEGER.equals(operandDataType)) {
                  //integer / integer is a float (it could be an integer, if operand A is
                  //evenly divisible by operand B, but we have no way to determine if that
                  //if the case at this point so assume float)
                  currentDataType = FormUtils.CALCULATION_DATATYPE_FLOAT;
                }
                else {
                  invalidOperandFound = true;
                }
              }
              //if the operation is add, then any datatype is valid for the current operand
              else if (FormUtils.CALCULATION_OPERATION_ADD.equals(getOperation())) {
                if (FormUtils.CALCULATION_DATATYPE_DATE.equals(operandDataType)) {
                  //integer + date is a date
                  currentDataType = FormUtils.CALCULATION_DATATYPE_DATE;
                }
                else if (FormUtils.CALCULATION_DATATYPE_FLOAT.equals(operandDataType)) {
                  //integer + float is a float
                  currentDataType = FormUtils.CALCULATION_DATATYPE_FLOAT;
                }
                else if (FormUtils.CALCULATION_DATATYPE_INTEGER.equals(operandDataType)) {
                  //integer + integer is an integer
                  currentDataType = FormUtils.CALCULATION_DATATYPE_INTEGER;
                }
                else {
                  invalidOperandFound = true;
                }
              }
              //if the operation is subtract, then any datatype is valid for the current operand
              else if (FormUtils.CALCULATION_OPERATION_SUBTRACT.equals(getOperation())) {
                if (FormUtils.CALCULATION_DATATYPE_DATE.equals(operandDataType)) {
                  //integer - date is a date
                  currentDataType = FormUtils.CALCULATION_DATATYPE_DATE;
                }
                else if (FormUtils.CALCULATION_DATATYPE_FLOAT.equals(operandDataType)) {
                  //integer - float is a float
                  currentDataType = FormUtils.CALCULATION_DATATYPE_FLOAT;
                }
                else if (FormUtils.CALCULATION_DATATYPE_INTEGER.equals(operandDataType)) {
                  //integer - integer is an integer
                  currentDataType = FormUtils.CALCULATION_DATATYPE_INTEGER;
                }
                else {
                  invalidOperandFound = true;
                }
              }
            }
            //if the data type determined so far is a float
            else if (FormUtils.CALCULATION_DATATYPE_FLOAT.equals(currentDataType)) {
              //if the current operand is an integer or a float, the current data type
              //becomes a floats (any operation between in integer and a float results in 
              //a float). Otherwise an invalid operand (i.e. date) has been encountered.
              if (FormUtils.CALCULATION_DATATYPE_INTEGER.equals(operandDataType) ||
                  FormUtils.CALCULATION_DATATYPE_FLOAT.equals(operandDataType)) {
                currentDataType = FormUtils.CALCULATION_DATATYPE_FLOAT;
              }
              else {
                invalidOperandFound = true;
              }
            }
          }
        }
      }
      if (invalidOperandFound) {
        _dataType = "invalid";
      }
      else {
        _dataType = currentDataType;
        //if a method to convert the calculation result to an int has been specified, then
        //return int.  We do this here instead of at the beginning of the method because if 
        //the datatype is determined to be invalid we don't want to mask that fact.  In other
        //words, if somebody has created a calculation that multiplies two dates we should return
        //"invalid" as the datatype even if they have specified a conversion method for the result
        //since that operation is not allowed.
        if (!ApiFunctions.isEmpty(getConvertResultToIntMethod())) {
          _dataType = FormUtils.CALCULATION_DATATYPE_INTEGER;
        }
      }
    }
    return _dataType;
  }
  
  //Method to return the cuis of all data elements referenced anywhere in this calculation
  public Collection getReferencedDataElementCuis() {
    if (_referencedDataElementCuis == null) {
      _referencedDataElementCuis = new ArrayList();
      Iterator operandIterator = getOperands().iterator();
      while (operandIterator.hasNext()) {
        Operand operand = (Operand)operandIterator.next();
        if (operand instanceof DataElementDefinitionReference) {
          DataElementDefinitionReference reference = (DataElementDefinitionReference)operand;
          _referencedDataElementCuis.add(reference.getCui());
        }
        else if (operand instanceof Calculation) {
          Calculation calculation = (Calculation)operand;
          _referencedDataElementCuis.addAll(calculation.getReferencedDataElementCuis());
        }
      }
    }
    return _referencedDataElementCuis;
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Calculation)) {
      return false;
    }
    
    Calculation calculation = (Calculation) obj;
        
    boolean result = true;
    result = result && ((_operation == null) ? calculation._operation == null : _operation.equals(calculation._operation));  
    result = result && ((_operands == null) ? calculation._operands == null : _operands.equals(calculation._operands));  
    return result;
    
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    int result = 17;
    
    result = 37*result + ((_operation == null) ? 0 : _operation.hashCode());
    result = 37*result + ((_operands == null) ? 0 : _operands.hashCode());
    
    return result;
  }
  
}
