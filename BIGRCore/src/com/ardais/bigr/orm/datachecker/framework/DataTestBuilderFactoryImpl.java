package com.ardais.bigr.orm.datachecker.framework;

import java.util.*;

/**
 * An implementation of <code>DataTestBuilderFactory</code> for the Data Checker.
 * This is a singleton class and it can't be instantiated directly.  Instead,
 * callers can retrieve an instance using the static {@link #getFactoryInstance} method.
 */
public class DataTestBuilderFactoryImpl implements DataTestBuilderFactory {
	/**
	 * The singleton instance of this factory class.
	 */
	private static DataTestBuilderFactory _factoryInstance = new DataTestBuilderFactoryImpl();

	/**
	 * A mapping of XML tag names for the various data type types to data test
	 * builders of the appropriate type.
	 */
	private Map _builderMap = new HashMap();
/**
 * Create a factory instance for the Data Checker.  This is a singleton
 * class, so the constructor is private to enforce this.
 *
 * I populates a mapping of XML tag names for the various data type types to data test
 * builders of the appropriate type.  As new tag types are added to the Data Checker DTD,
 * the mapping here must be extended.
 */
private DataTestBuilderFactoryImpl() {
	_builderMap.put("DataTests", new DataTestsDataTestBuilder(this));
	_builderMap.put("TableGroup", new TableGroupDataTestBuilder(this));
	_builderMap.put("table", new TableDataTestBuilder(this));
	_builderMap.put("AlwaysNull", new AlwaysNullDataTestBuilder(this));
	_builderMap.put("AllNullOrNotNull", new AllNullOrNotNullDataTestBuilder(this));
	_builderMap.put("always", new AlwaysDataTestBuilder(this));
	_builderMap.put("ExtraTests", new ExtraTestsDataTestBuilder(this));
	_builderMap.put("ForeignKey", new ForeignKeyDataTestBuilder(this));
	_builderMap.put("never", new NeverDataTestBuilder(this));
	_builderMap.put("NeverNull", new NeverNullDataTestBuilder(this));
	_builderMap.put("QueryResultRelation", new QueryResultRelationDataTestBuilder(this));
	_builderMap.put("ValueSet", new ValueSetDataTestBuilder(this));
}
/**
 * Return an instance of this class.
 */
public static DataTestBuilderFactory getFactoryInstance() {
	return _factoryInstance;
}
public DataTestBuilder getInstance(String testType) {
	DataTestBuilder builder = (DataTestBuilder) _builderMap.get(testType);
	
	if (builder == null) {
		// If you get this exception, it could mean that you need to add
		// a new mapping for a new Data Checker XML tag type to the mapping
		// in constructor.  You may also need to define new builder and test-case
		// subclasses for the new tag type.
		//
		throw new IllegalArgumentException(
			"Unsupported data test type: " + testType);
	}
	
	return builder;
}
}
