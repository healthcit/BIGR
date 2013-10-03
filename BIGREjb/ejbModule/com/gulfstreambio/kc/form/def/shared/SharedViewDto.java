package com.gulfstreambio.kc.form.def.shared;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author Roman Boris
 * @since 8/29/12
 */
public class SharedViewDto implements Serializable
{
	private static final long serialVersionUID = 3508081827167842013L;

	private String formDefinitionId;
	private String roleId;
	private ShareType shareType;

	public String getFormDefinitionId()
	{
		return formDefinitionId;
	}

	public void setFormDefinitionId(String formDefinitionId)
	{
		this.formDefinitionId = formDefinitionId;
	}

	public String getRoleId()
	{
		return roleId;
	}

	public void setRoleId(String roleId)
	{
		this.roleId = roleId;
	}

	public ShareType getShareType()
	{
		return shareType;
	}

	public void setShareType(ShareType shareType)
	{
		this.shareType = shareType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof SharedViewDto)) {
			return false;
		}

		SharedViewDto that = (SharedViewDto) o;

		return new EqualsBuilder()
			.append(formDefinitionId, that.formDefinitionId)
			.append(roleId, that.roleId)
			.append(shareType, that.shareType)
			.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31)
			.append(formDefinitionId)
			.append(roleId)
			.append(shareType)
			.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("formDefinitionId", formDefinitionId)
			.append("roleId", roleId)
			.append("shareType", shareType)
			.toString();
	}
}
