package com.ardais.bigr.reports.storagereport;

import com.ardais.bigr.configuration.SystemConfiguration;
import gov.nih.nci.logging.api.util.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Roman Boris
 * @since 5/16/13
 */
public class StorageLocationInfo implements Serializable, Comparable<StorageLocationInfo>
{
	private static final long serialVersionUID = 1L;

	protected String location;

	protected final Map<String, StorageInfo> storageMap = new HashMap<String, StorageInfo>();

	public StorageLocationInfo(String location)
	{
		this.location = location;
	}

	public String getLocation()
	{
		return location;
	}

	public StorageInfo findStorageInfo(String storageTypeCid, String freezer)
	{
		StorageInfo info = storageMap.get(storageTypeCid);

		if (info == null)
		{
			final String storageType = SystemConfiguration
				.getConceptList(SystemConfiguration.CONCEPT_LIST_STORAGE_TYPES)
				.toLegalValueSet()
				.getDisplayValue(storageTypeCid);

			info = new StorageInfo(freezer,
								   StringUtils.isBlank(storageType)
									   ? storageTypeCid
									   : storageType);

			storageMap.put(storageTypeCid, info);
		}

		return info;
	}

	public List<StorageInfo> getStoragesSorted()
	{
		final List<StorageInfo> result = new ArrayList<StorageInfo>(storageMap.values());
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

		if (!(o instanceof StorageLocationInfo))
		{
			return false;
		}

		StorageLocationInfo that = (StorageLocationInfo)o;

		return new EqualsBuilder()
			.append(location, that.location)
			.append(storageMap, that.storageMap)
			.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(17, 31)
			.append(location)
			.append(storageMap)
			.toHashCode();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this)
			.append("location", location)
			.append("storageMap", storageMap)
			.toString();
	}

	@Override
	public int compareTo(StorageLocationInfo o)
	{
		return location != null && o != null
			? location.compareTo(o.location)
			: 0;
	}
}
