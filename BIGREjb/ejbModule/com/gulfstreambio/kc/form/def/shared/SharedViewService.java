package com.gulfstreambio.kc.form.def.shared;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.gulfstreambio.kc.form.def.FormDefinition;
import com.gulfstreambio.kc.form.def.FormDefinitionQueryCriteria;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Roman Boris
 * @since 8/28/12
 */
public class SharedViewService
{
	public static SharedViewService SINGLETON = new SharedViewService();

	private SharedViewService()
	{
		super();
	}

	public String getDefaultForRoles(Set<String> roleIds)
	{
		if (roleIds == null || roleIds.size() == 0) return null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			con = ApiFunctions.getDbConnection();
			pstmt = con.prepareStatement("select distinct sv.FORM_DEFINITION_ID from SHARED_VIEWS sv where sv.SHARED_TYPE = 'DEFAULT' and sv.ROLE_ID in " + ApiFunctions.makeBindParameterList(roleIds.size()));
			ApiFunctions.bindBindParameterList(pstmt, 1, roleIds);

			rs = ApiFunctions.queryDb(pstmt, con);

			if (rs.next())
			{
				return rs.getString("FORM_DEFINITION_ID");
			}
		}
		catch (Exception e)
		{
			ApiLogger.log(e);
			ApiFunctions.throwAsRuntimeException(e);
		}
		finally
		{
			ApiFunctions.close(con, pstmt, rs);
		}
		return null;
	}

	public void saveSharedViews(String formDefId, String[] roleIds, String[] defaultForRoleIds)
	{
		final Set<String> roleIdsSet = roleIds != null
			? new HashSet<String>(Arrays.asList(roleIds))
			: new HashSet<String>();

		final Set<String> defaultIdsSet = defaultForRoleIds != null
			? new HashSet<String>(Arrays.asList(defaultForRoleIds))
			: new HashSet<String>();

		saveSharedViews(formDefId, roleIdsSet, defaultIdsSet);
	}

	public void saveSharedViews(String formDefId, Set<String> roleIds, Set<String> defaultForRoleIds)
	{
		for (SharedViewDto dto : getRolesSharedTo(formDefId))
		{
			if (!roleIds.contains(dto.getRoleId()))
			{
				removeSharedViewRecord(dto.getFormDefinitionId(), dto.getRoleId());
			}
			else
			{
				dto.setShareType(retrieveShareType(dto.getRoleId(), defaultForRoleIds));
				updateSharedView(dto);
				roleIds.remove(dto.getRoleId());
			}
		}

		for (String roleToAdd : roleIds)
		{
			final SharedViewDto dto = new SharedViewDto();
			dto.setFormDefinitionId(formDefId);
			dto.setRoleId(roleToAdd);
			dto.setShareType(retrieveShareType(roleToAdd, defaultForRoleIds));

			createSharedView(dto);
		}
	}

	public List<SharedViewDto> getRolesSharedTo(String formDefId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final List<SharedViewDto> results = new ArrayList<SharedViewDto>();

		try
		{
			con = ApiFunctions.getDbConnection();
			pstmt = con.prepareStatement("select * from SHARED_VIEWS sv where sv.FORM_DEFINITION_ID = ?");
			pstmt.setString(1, formDefId);

			rs = ApiFunctions.queryDb(pstmt, con);

			while (rs.next())
			{
				final SharedViewDto dto = new SharedViewDto();
				dto.setFormDefinitionId(rs.getString("FORM_DEFINITION_ID"));
				dto.setRoleId(rs.getString("ROLE_ID"));
				dto.setShareType(ShareType.valueOf(rs.getString("SHARED_TYPE")));
				results.add(dto);
			}
		}
		catch (Exception e)
		{
			ApiLogger.log(e);
			ApiFunctions.throwAsRuntimeException(e);
		}
		finally
		{
			ApiFunctions.close(con, pstmt, rs);
		}
		return results;
	}

	public void populateSharedViews(FormDefinition formDefinition)
	{
		final List<String> roleIds = new ArrayList<String>();
		final List<String> defaultIds = new ArrayList<String>();
		for (SharedViewDto dto : getRolesSharedTo(formDefinition.getFormDefinitionId()))
		{
			roleIds.add(dto.getRoleId());
			if (ShareType.DEFAULT.equals(dto.getShareType()))
			{
				defaultIds.add(dto.getRoleId());
			}
		}
		if (!CollectionUtils.isEmpty(roleIds))
		{
			formDefinition.setRolesSharedTo(roleIds.toArray(new String[roleIds.size()]));
		}
		if (!CollectionUtils.isEmpty(defaultIds))
		{
			formDefinition.setDefaultSharedViews(defaultIds.toArray(new String[defaultIds.size()]));
		}
	}

	public void createSharedView(SharedViewDto dto)
	{
		Connection con = null;
		PreparedStatement pstmt = null;

		try
		{
			con = ApiFunctions.getDbConnection();
			pstmt = con.prepareStatement("insert into SHARED_VIEWS(FORM_DEFINITION_ID, ROLE_ID, SHARED_TYPE) values(?,?,?)");
			pstmt.setString(1, dto.getFormDefinitionId());
			pstmt.setString(2, dto.getRoleId());
			pstmt.setString(3, dto.getShareType().name());
			pstmt.execute();
		}
		catch (Exception e)
		{
			ApiLogger.log(e);
			ApiFunctions.throwAsRuntimeException(e);
		}
		finally
		{
			ApiFunctions.close(con, pstmt, null);
		}
	}

	public void updateSharedView(SharedViewDto dto)
	{
		Connection con = null;
		PreparedStatement pstmt = null;

		try
		{
			con = ApiFunctions.getDbConnection();
			pstmt = con.prepareStatement("update SHARED_VIEWS set SHARED_TYPE = ? where FORM_DEFINITION_ID = ? and ROLE_ID = ?");
			pstmt.setString(1, dto.getShareType().name());
			pstmt.setString(2, dto.getFormDefinitionId());
			pstmt.setString(3, dto.getRoleId());
			pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			ApiLogger.log(e);
			ApiFunctions.throwAsRuntimeException(e);
		}
		finally
		{
			ApiFunctions.close(con, pstmt, null);
		}
	}

	public void removeSharedViewRecord(String formDefId, String roleId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;

		try
		{
			con = ApiFunctions.getDbConnection();
			pstmt = con.prepareStatement("delete from SHARED_VIEWS where FORM_DEFINITION_ID = ? and ROLE_ID = ?");
			pstmt.setString(1, formDefId);
			pstmt.setString(2, roleId);
			pstmt.execute();
		}
		catch (Exception e)
		{
			ApiLogger.log(e);
			ApiFunctions.throwAsRuntimeException(e);
		}
		finally
		{
			ApiFunctions.close(con, pstmt, null);
		}
	}

	public void resetDefaultViewForRole(String roleId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;

		try
		{
			con = ApiFunctions.getDbConnection();
			pstmt = con.prepareStatement("update SHARED_VIEWS set SHARED_TYPE = 'SHARED' where ROLE_ID = ?");
			pstmt.setString(1, roleId);
			pstmt.execute();
		}
		catch (Exception e)
		{
			ApiLogger.log(e);
			ApiFunctions.throwAsRuntimeException(e);
		}
		finally
		{
			ApiFunctions.close(con, pstmt, null);
		}
	}

	public Set<String> findSharedFormDefinitionIds(FormDefinitionQueryCriteria criteria)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final Set<String> ids = new HashSet<String>();
		try
		{
			con = ApiFunctions.getDbConnection();
			final String sql = buildIdsQuery(criteria);
			pstmt = con.prepareStatement(sql);
			ApiFunctions.bindBindParameterList(pstmt, 1, criteria.getRoleIds());
			rs = ApiFunctions.queryDb(pstmt, con);

			while (rs.next())
			{
				ids.add(rs.getString("FORM_DEFINITION_ID"));
			}
		}
		catch (Exception e)
		{
			ApiFunctions.throwAsRuntimeException(e);
		}
		finally
		{
			ApiFunctions.close(con, pstmt, rs);
		}

		return ids;
	}

	protected String buildIdsQuery(FormDefinitionQueryCriteria criteria)
	{
		final StringBuilder sb = new StringBuilder();

		sb.append(" select distinct sv.FORM_DEFINITION_ID ")
			.append(" from SHARED_VIEWS sv ")
			.append(" where ")
			.append(" sv.SHARED_TYPE is not null ");

		if (!CollectionUtils.isEmpty(criteria.getRoleIds()))
		{
			sb.append(" and sv.ROLE_ID in ")
				.append(ApiFunctions.makeBindParameterList(criteria.getRoleIds().size()));
		}

		return sb.toString();
	}

	protected ShareType retrieveShareType(String roleId, Set<String> defaultIds)
	{
		final ShareType result = defaultIds != null && defaultIds.contains(roleId)
			? ShareType.DEFAULT
			: ShareType.SHARED;

		if (ShareType.DEFAULT.equals(result))
		{
			resetDefaultViewForRole(roleId);
		}

		return result;
	}
}
