package com.ardais.bigr.filter.domain;

/**
 * @author Roman Boris
 * @since 9/10/12
 */
public interface PersonalFilter
{
	String getId();

	void setId(String id);

	String getName();

	void setName(String name);

	String getValue();

	void setValue(String value);

	PersonalFilterType getType();

	String getAccountKey();

	String getUserId();
}
