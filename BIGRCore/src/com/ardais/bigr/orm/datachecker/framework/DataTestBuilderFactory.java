package com.ardais.bigr.orm.datachecker.framework;

/**
 * Find or create instances of {@link DataTestBuilder} that can create
 * data test case objects corresponding to various XML test case descriptions.
 */
public interface DataTestBuilderFactory {
/**
 * Find or create and instance of {@link DataTestBuilder} that can create
 * data test case objects corresponding to an XML test case description
 * that is expressed by the specified XML tag name.
 *
 * @param testType the XML tag name
 *
 * @return the data test builder object
 */
public DataTestBuilder getInstance(String testType);
}
