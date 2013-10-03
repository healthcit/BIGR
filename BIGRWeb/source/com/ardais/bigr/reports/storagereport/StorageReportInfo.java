package com.ardais.bigr.reports.storagereport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Roman Boris
 * @since 5/17/13
 */
public class StorageReportInfo implements Serializable
{
	private static final long serialVersionUID = 1L;

	protected final Map<String, StorageLocationInfo> locationMap = new HashMap<String, StorageLocationInfo>();

	public StorageLocationInfo findStorageLocationInfo(String location)
	{
		StorageLocationInfo info = locationMap.get(location);

		if (info == null)
		{
			info = new StorageLocationInfo(location);
			locationMap.put(location, info);
		}

		return info;
	}

	public List<StorageLocationInfo> getStorageLocationsSorted()
	{
		final List<StorageLocationInfo> result = new ArrayList<StorageLocationInfo>(locationMap.values());
		Collections.sort(result);
		return result;
	}
}
