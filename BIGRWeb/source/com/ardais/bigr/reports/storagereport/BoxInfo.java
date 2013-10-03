package com.ardais.bigr.reports.storagereport;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author Roman Boris
 * @since 5/16/13
 */
public class BoxInfo implements Serializable, Comparable<BoxInfo>
{
	private static final long serialVersionUID = 1L;

	protected String boxId;

	protected String boxLayout;

	protected String emptySlotCount;

	public BoxInfo()
	{
	}

	public BoxInfo(String boxId, String boxLayout, String emptySlotCount)
	{
		this.boxId = boxId;
		this.boxLayout = boxLayout;
		this.emptySlotCount = emptySlotCount;
	}

	public String getBoxId()
	{
		return boxId;
	}

	public void setBoxId(String boxId)
	{
		this.boxId = boxId;
	}

	public String getBoxLayout()
	{
		return boxLayout;
	}

	public void setBoxLayout(String boxLayout)
	{
		this.boxLayout = boxLayout;
	}

	public String getEmptySlotCount()
	{
		return emptySlotCount;
	}

	public void setEmptySlotCount(String emptySlotCount)
	{
		this.emptySlotCount = emptySlotCount;
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

		if (!(o instanceof BoxInfo))
		{
			return false;
		}

		BoxInfo that = (BoxInfo)o;

		return new EqualsBuilder()
			.append(boxId, that.boxId)
			.append(boxLayout, that.boxLayout)
			.append(emptySlotCount, that.emptySlotCount)
			.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(17, 31)
			.append(boxId)
			.append(boxLayout)
			.append(emptySlotCount)
			.toHashCode();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this)
			.append("boxId", boxId)
			.append("boxLayout", boxLayout)
			.append("emptySlotCount", emptySlotCount)
			.toString();
	}

	@Override
	public int compareTo(BoxInfo o)
	{
		return boxLayout != null && o != null
			? boxLayout.compareTo(o.boxLayout)
			: 0;
	}
}
