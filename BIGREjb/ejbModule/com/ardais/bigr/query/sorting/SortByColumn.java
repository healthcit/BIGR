package com.ardais.bigr.query.sorting;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author Roman Boris
 * @since 8/8/12
 */
public class SortByColumn implements Serializable
{
	private static final long serialVersionUID = -7328680645664334445L;

	private String column;

	private SortOrder order;

	public SortByColumn()
	{
	}

	public SortByColumn(String column, SortOrder order)
	{
		this.column = column;
		this.order = order;
	}

	public String getColumn()
	{
		return column;
	}

	public void setColumn(String column)
	{
		this.column = column;
	}

	public SortOrder getOrder()
	{
		return order;
	}

	public void setOrder(SortOrder order)
	{
		this.order = order;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof SortByColumn)) {
			return false;
		}

		SortByColumn that = (SortByColumn) o;

		return new EqualsBuilder()
			.append(column, that.column)
			.append(order, that.order)
			.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
			.append(column)
			.append(order)
			.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("column", column)
			.append("order", order)
			.toString();
	}
}
