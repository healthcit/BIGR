package com.ardais.bigr.filter.domain;

import com.ardais.bigr.security.SecurityInfo;

/**
 * @author Roman Boris
 * @since 9/10/12
 */
public class SamplePersonalFilter extends AbstractPersonalFilter
{
	public SamplePersonalFilter(String accountKey, String userId)
	{
		super(accountKey, userId, PersonalFilterType.SAMPLE);
	}

	public SamplePersonalFilter(SecurityInfo info)
	{
		this(info.getAccount(), info.getUsername());
	}
}
