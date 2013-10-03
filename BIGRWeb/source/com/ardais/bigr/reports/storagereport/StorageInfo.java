package com.ardais.bigr.reports.storagereport;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Roman Boris
 * @since 5/16/13
 */
public class StorageInfo implements Serializable, Comparable<StorageInfo>
{
	private static final long serialVersionUID = 1L;

	protected String freezer;

	protected String storageType;

	protected Set<BoxInfo> boxInfos = new HashSet<BoxInfo>();

	public StorageInfo(String freezer, String storageType)
	{
		this.freezer = freezer;
		this.storageType = storageType;
	}

	public String getFreezer()
	{
		return freezer;
	}

	public String getStorageType()
	{
		return storageType;
	}

	public Set<BoxInfo> getBoxInfos()
	{
		return boxInfos;
	}

	public List<BoxInfo> getBoxInfosSorted()
	{
		final List<BoxInfo> result = new ArrayList<BoxInfo>();
		result.addAll(boxInfos);
		Collections.sort(result);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}

		if (!(o instanceof StorageInfo))
		{
			return false;
		}

		StorageInfo that = (StorageInfo)o;

		return new EqualsBuilder()
			.append(freezer, that.freezer)
			.append(storageType, that.storageType)
			.append(boxInfos, that.boxInfos)
			.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(17, 31)
			.append(freezer)
			.append(storageType)
			.append(boxInfos)
			.toHashCode();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this)
			.append("freezer", freezer)
			.append("storageType", storageType)
			.append("boxInfos", boxInfos)
			.toString();
	}

	@Override
	public int compareTo(StorageInfo o)
	{
		return freezer != null && o != null
			? freezer.compareTo(o.freezer)
			: 0;
	}
}
