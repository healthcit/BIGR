package com.ardais.bigr.orm.datachecker.framework;

/**
 * This class maintains context information about where a test resides
 * in a test hierarchy.  This information is sometimes needed to determine
 * the correct test case to build.  For example, the test-definition XML
 * document includes many test types that must appear within a
 * <code>&lt;table&gt;</code> tag that specifies what database table the
 * test applies to.  The <code>DataTestBuilderContext</code> contains
 * the id and name of the <code>&lt;table&gt;</code> tag that processing is
 * occurring within (if any), among other piece of context information.
 */
public class DataTestBuilderContext {
	/**
	 * The id attribute of the <code>&lt;DataTests&gt;</code> tag that encloses
	 * the node currently being processed, or null if none.
	 */
	private String _dataTestsId = null;

	/**
	 * The id attribute of the <code>&lt;TableGroup&gt;</code> tag that encloses
	 * the node currently being processed, or null if none.
	 */
	private String _tableGroupId = null;

	/**
	 * The id attribute of the <code>&lt;table&gt;</code> tag that encloses
	 * the node currently being processed, or null if none.
	 */
	private String _tableId = null;

	/**
	 * The name attribute of the <code>&lt;table&gt;</code> tag that encloses
	 * the node currently being processed, or null if none.
	 */
	private String _tableName = null;
public DataTestBuilderContext() {
	super();
}
/**
 * Return the id attribute value of the <code>&lt;DataTests&gt;</code> tag
 * that encloses the node currently being processed, or null if none.
 *
 * @return the id attribute value
 */
public String getDataTestsId() {
	return _dataTestsId;
}
/**
 * Return the id attribute value of the <code>&lt;TableGroup&gt;</code> tag
 * that encloses the node currently being processed, or null if none.
 *
 * @return the id attribute value
 */
public String getTableGroupId() {
	return _tableGroupId;
}
/**
 * Return the id attribute value of the <code>&lt;table&gt;</code> tag
 * that encloses the node currently being processed, or null if none.
 *
 * @return the id attribute value
 */
public String getTableId() {
	return _tableId;
}
/**
 * Return the name attribute value of the <code>&lt;table&gt;</code> tag
 * that encloses the node currently being processed, or null if none.
 *
 * @return the name attribute value
 */
public String getTableName() {
	return _tableName;
}
/**
 * Record the id attribute value of the <code>&lt;DataTests&gt;</code> tag
 * that encloses the node currently being processed, or null if none.
 *
 * @param newValue the id attribute value
 */
public void setDataTestsId(String newValue) {
	_dataTestsId = newValue;
}
/**
 * Record the id attribute value of the <code>&lt;TableGroup&gt;</code> tag
 * that encloses the node currently being processed, or null if none.
 *
 * @param newValue the id attribute value
 */
public void setTableGroupId(String newValue) {
	_tableGroupId = newValue;
}
/**
 * Record the id attribute value of the <code>&lt;table&gt;</code> tag
 * that encloses the node currently being processed, or null if none.
 *
 * @param newValue the id attribute value
 */
public void setTableId(String newValue) {
	_tableId = newValue;
}
/**
 * Record the name attribute value of the <code>&lt;table&gt;</code> tag
 * that encloses the node currently being processed, or null if none.
 *
 * @param newValue the name attribute value
 */
public void setTableName(String newValue) {
	_tableName = newValue;
}
}
