package com.ardais.bigr.query.generator;

import java.util.ArrayList;
import java.util.List;

import com.gulfstreambio.kc.det.DatabaseElement;

class KcDetailQueryBuilderHelper {
  
  private DatabaseElement _tableInfo; 
  private ProductQueryBuilder _productQueryBuilder;
  private List _elements;
  private List _elementCuis;
  private String _queryTableKeyForElement;

  /**
   * Helper class for the KcDetailQueryBuilder
   */
  public KcDetailQueryBuilderHelper() {
    super();
  }

  public List getElementCuis() {
    if (_elementCuis == null) {
      _elementCuis = new ArrayList();
    }
    return _elementCuis;
  }
  
  public List getElements() {
    if (_elements == null) {
      _elements = new ArrayList();
    }
    return _elements;
  }
  
  public ProductQueryBuilder getProductQueryBuilder() {
    return _productQueryBuilder;
  }
  
  public void setElementCuis(List elementCuis) {
    _elementCuis = elementCuis;
  }
  
  public void setElements(List elements) {
    _elements = elements;
  }
  public void setProductQueryBuilder(ProductQueryBuilder productQueryBuilder) {
    _productQueryBuilder = productQueryBuilder;
  }
  
  public DatabaseElement getTableInfo() {
    return _tableInfo;
  }
  public void setTableInfo(DatabaseElement tableInfo) {
    _tableInfo = tableInfo;
  }

  /**
   * @return the query metadata element table key
   */
  public String getQueryTableKeyForElement() {
    return _queryTableKeyForElement;
  }

  /**
   * @param elementTableKey the query metadata element table key
   */
  public void setQueryTableKeyForElement(String elementTableKey) {
    _queryTableKeyForElement = elementTableKey;
  }
}
