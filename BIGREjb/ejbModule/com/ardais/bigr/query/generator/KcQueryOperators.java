package com.ardais.bigr.query.generator;

import java.util.HashSet;
import java.util.Set;

import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueComparison;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinitionValueSet;

public class KcQueryOperators {

  public static final String AND = QueryFormDefinitionValueSet.OPERATOR_AND;
  public static final String OR = QueryFormDefinitionValueSet.OPERATOR_OR;
  
  public static final String ANY = "any";
  public static final String EQ = QueryFormDefinitionValueComparison.OPERATOR_EQ;
  public static final String NE = QueryFormDefinitionValueComparison.OPERATOR_NE;
  public static final String GT = QueryFormDefinitionValueComparison.OPERATOR_GT;
  public static final String GE = QueryFormDefinitionValueComparison.OPERATOR_GE;
  public static final String LT = QueryFormDefinitionValueComparison.OPERATOR_LT;
  public static final String LE = QueryFormDefinitionValueComparison.OPERATOR_LE;
  public static final String ORLTGT = "orltgt";
  public static final String ORLEGT = "orlegt";
  public static final String ORLTGE = "orltge";
  public static final String ORLEGE = "orlege";
  public static final String ORGTLT = "orgtlt";
  public static final String ORGTLE = "orgtle";
  public static final String ORGELT = "orgelt";
  public static final String ORGELE = "orgele";
  public static final String ANDLTGT = "andltgt";
  public static final String ANDLEGT = "andlegt";
  public static final String ANDLTGE = "andltge";
  public static final String ANDLEGE = "andlege";
  public static final String ANDGELE = "andgele";
  public static final String ANDGTLT = "andgtlt";
  public static final String ANDGTLE = "andgtle";
  public static final String ANDGELT = "andgelt";
  
  private static Set _kcLogicalOperators;
  static {
    _kcLogicalOperators = new HashSet();
    _kcLogicalOperators.add(AND);
    _kcLogicalOperators.add(OR);
  }

  private static Set _kcOperators;
  static {
    _kcOperators = new HashSet();
    _kcOperators.add(ANY);
    _kcOperators.add(EQ);
    _kcOperators.add(NE);
    _kcOperators.add(GT);
    _kcOperators.add(GE);
    _kcOperators.add(LT);
    _kcOperators.add(LE);
    _kcOperators.add(ORLTGT);
    _kcOperators.add(ORLEGT);
    _kcOperators.add(ORLTGE);
    _kcOperators.add(ORLEGE);
    _kcOperators.add(ORGTLT);
    _kcOperators.add(ORGTLE);
    _kcOperators.add(ORGELT);
    _kcOperators.add(ORGELE);
    _kcOperators.add(ANDLTGT);
    _kcOperators.add(ANDLEGT);
    _kcOperators.add(ANDLTGE);
    _kcOperators.add(ANDLEGE);
    _kcOperators.add(ANDGTLT);
    _kcOperators.add(ANDGTLE);
    _kcOperators.add(ANDGELT);
    _kcOperators.add(ANDGELE);
  }

  private static Set _kcComparisonOperators;
  static {
    _kcComparisonOperators = new HashSet();
    _kcComparisonOperators.add(EQ);
    _kcComparisonOperators.add(NE);
    _kcComparisonOperators.add(GT);
    _kcComparisonOperators.add(GE);
    _kcComparisonOperators.add(LT);
    _kcComparisonOperators.add(LE);
  }

  private static Set _kcDiscreteOperators;
  static {
    _kcDiscreteOperators = new HashSet();
    _kcDiscreteOperators.add(EQ);
    _kcDiscreteOperators.add(NE);
  }

  private static Set _kcRangeOperators;
  static {
    _kcRangeOperators = new HashSet();
    _kcRangeOperators.add(ORLTGT);
    _kcRangeOperators.add(ORLEGT);
    _kcRangeOperators.add(ORLTGE);
    _kcRangeOperators.add(ORLEGE);
    _kcRangeOperators.add(ORGTLT);
    _kcRangeOperators.add(ORGTLE);
    _kcRangeOperators.add(ORGELT);
    _kcRangeOperators.add(ORGELE);
    _kcRangeOperators.add(ANDLTGT);
    _kcRangeOperators.add(ANDLEGT);
    _kcRangeOperators.add(ANDLTGE);
    _kcRangeOperators.add(ANDLEGE);
    _kcRangeOperators.add(ANDGTLT);
    _kcRangeOperators.add(ANDGTLE);
    _kcRangeOperators.add(ANDGELT);
    _kcRangeOperators.add(ANDGELE);
  }

  public static boolean isValidLogicalOperator(String operator) {
    return _kcLogicalOperators.contains(operator);
  }
  
  public static boolean isValidOperator(String operator) {
    return _kcOperators.contains(operator);
  }
  
  public static boolean isValidComparisonOperator(String operator) {
    return _kcComparisonOperators.contains(operator);
  }
  
  public static boolean isValidRangeOperator(String operator) {
    return _kcRangeOperators.contains(operator);
  }
  
  public static boolean isValidDiscreteOperator(String operator) {
    return _kcDiscreteOperators.contains(operator);
  }
  
  private KcQueryOperators() {
    super();
  }

}
