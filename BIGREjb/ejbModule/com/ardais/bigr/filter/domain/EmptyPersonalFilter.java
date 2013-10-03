package com.ardais.bigr.filter.domain;

/**
 * @author Roman Boris
 * @since 9/18/12
 */
public class EmptyPersonalFilter implements PersonalFilter
{
	@Override
	public String getId()
	{
		return "";
	}

	@Override
	public void setId(String id)
	{
		// blank
	}

	@Override
	public String getName()
	{
		return "";
	}

	@Override
	public void setName(String name)
	{
		// blank
	}

	@Override
	public String getValue()
	{
		return "";
	}

	@Override
	public void setValue(String value)
	{
		// blank
	}

	@Override
	public PersonalFilterType getType()
	{
		return null;
	}

	@Override
	public String getAccountKey()
	{
		return null;
	}

	@Override
	public String getUserId()
	{
		return null;
	}
}
