package com.ardais.bigr.report.storagereport.model;

/**
 * @author Roman Boris
 * @since 5/17/13
 */
public class StorageReportModel
{
	protected String locationAddressId;
	protected String storageTypeCid;
	protected String unitName;
	protected String boxId;
	protected int columns;
	protected int rows;
	protected int inUse;

	public String getLocationAddressId()
	{
		return locationAddressId;
	}

	public void setLocationAddressId(String locationAddressId)
	{
		this.locationAddressId = locationAddressId;
	}

	public String getStorageTypeCid()
	{
		return storageTypeCid;
	}

	public void setStorageTypeCid(String storageTypeCid)
	{
		this.storageTypeCid = storageTypeCid;
	}

	public String getUnitName()
	{
		return unitName;
	}

	public void setUnitName(String unitName)
	{
		this.unitName = unitName;
	}

	public String getBoxId()
	{
		return boxId;
	}

	public void setBoxId(String boxId)
	{
		this.boxId = boxId;
	}

	public int getColumns()
	{
		return columns;
	}

	public void setColumns(int columns)
	{
		this.columns = columns;
	}

	public int getRows()
	{
		return rows;
	}

	public void setRows(int rows)
	{
		this.rows = rows;
	}

	public int getInUse()
	{
		return inUse;
	}

	public void setInUse(int inUse)
	{
		this.inUse = inUse;
	}
}
