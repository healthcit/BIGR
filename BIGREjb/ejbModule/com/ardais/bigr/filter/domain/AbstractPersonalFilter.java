package com.ardais.bigr.filter.domain;

/**
 * @author Roman Boris
 * @since 9/10/12
 */
public abstract class AbstractPersonalFilter implements PersonalFilter
{
	private String id;
	private String name;
	private String value;
	private PersonalFilterType type;
	private String accountKey;
	private String userId;

	protected AbstractPersonalFilter(String accountKey, String userId, PersonalFilterType type)
	{
		this.accountKey = accountKey;
		this.userId = userId;
		this.type = type;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public PersonalFilterType getType()
	{
		return type;
	}

	public String getAccountKey()
	{
		return accountKey;
	}

	public String getUserId()
	{
		return userId;
	}
}
