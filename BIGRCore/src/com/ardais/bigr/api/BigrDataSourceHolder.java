package com.ardais.bigr.api;

import javax.sql.DataSource;

/**
 * @author Roman Boris
 * @since 12/11/12
 */
public abstract class BigrDataSourceHolder
{
	public abstract void setDataSource(DataSource dataSource);

	public abstract DataSource getDataSource();

	public abstract String getDataSourcePropertyKey();
}
