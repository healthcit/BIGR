package com.ardais.bigr.filter;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.api.TemporaryClob;
import com.ardais.bigr.filter.domain.PersonalFilter;
import com.ardais.bigr.filter.domain.SamplePersonalFilter;
import org.apache.commons.lang.StringUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Boris
 * @since 9/10/12
 */
public class PersonalFilterService
{
	public static PersonalFilterService SINGLETON = new PersonalFilterService();

	private PersonalFilterService()
	{
		super();
	}

	public List<PersonalFilter> getUserFilters(String accountKey, String userId, Class<? extends PersonalFilter> clazz)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final List<PersonalFilter> results = new ArrayList<PersonalFilter>();

		try
		{
			con = ApiFunctions.getDbConnection();
			pstmt = con.prepareStatement("select * from PERSONAL_FILTERS where ARDAIS_ACCT_KEY = ? and ARDAIS_USER_ID = ?");
			pstmt.setString(1, accountKey);
			pstmt.setString(2, userId);

			rs = ApiFunctions.queryDb(pstmt, con);

			while (rs.next())
			{
				results.add(populateFilter(rs, clazz));
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

	public String savePersonalFilter(PersonalFilter filter)
	{
		if (StringUtils.isBlank(filter.getId()))
		{
			return createFilter(filter);
		}
		else
		{
			return updateFilter(filter);
		}
	}

	public PersonalFilter getPersonalFilter(String id, Class<? extends PersonalFilter> clazz)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PersonalFilter result = null;

		try
		{
			con = ApiFunctions.getDbConnection();
			pstmt = con.prepareStatement("select * from PERSONAL_FILTERS where ID = ?");
			pstmt.setString(1, id);
			rs = ApiFunctions.queryDb(pstmt, con);
			rs.next();
			result = populateFilter(rs, clazz);
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
		return result;
	}

	public void deletePersonalFilter(String id)
	{
		Connection con = null;
		PreparedStatement pstmt = null;

		try
		{
			con = ApiFunctions.getDbConnection();
			pstmt = con.prepareStatement("delete from PERSONAL_FILTERS where ID = ?");
			pstmt.setString(1, id);
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

	protected String createFilter(PersonalFilter filter)
	{
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		String result = null;

		try
		{
			con = ApiFunctions.getDbConnection();
			stmt = con.prepareCall("begin insert into PERSONAL_FILTERS(ARDAIS_ACCT_KEY, ARDAIS_USER_ID, TYPE, NAME, VALUE) values(?,?,?,?,?) returning id into ?; end;");
			stmt.setString(1, filter.getAccountKey());
			stmt.setString(2, filter.getUserId());
			stmt.setString(3, filter.getType().name());
			stmt.setString(4, filter.getName());
			final TemporaryClob clob = new TemporaryClob(con, filter.getValue());
			stmt.setClob(5, clob.getSQLClob());
			stmt.registerOutParameter(6, Types.NUMERIC);
			stmt.execute();

			result = stmt.getString(6);
		}
		catch (Exception e)
		{
			ApiLogger.log(e);
			ApiFunctions.throwAsRuntimeException(e);
		}
		finally
		{
			ApiFunctions.close(con, stmt, rs);
		}
		return result;
	}

	protected String updateFilter(PersonalFilter filter)
	{
		Connection con = null;
		PreparedStatement pstmt = null;

		try
		{
			con = ApiFunctions.getDbConnection();
			pstmt = con.prepareStatement("update PERSONAL_FILTERS set TYPE = ?, NAME = ?, VALUE = ? where ID = ?");
			pstmt.setString(1, filter.getType().name());
			pstmt.setString(2, filter.getName());
			final TemporaryClob clob = new TemporaryClob(con, filter.getValue());
			pstmt.setClob(3, clob.getSQLClob());
			pstmt.setString(4, filter.getId());
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
		return filter.getId();
	}

	protected PersonalFilter populateFilter(ResultSet rs, Class<? extends PersonalFilter> clazz) throws SQLException
	{
		if (clazz == SamplePersonalFilter.class)
		{
			final SamplePersonalFilter result = new SamplePersonalFilter(
				rs.getString("ARDAIS_ACCT_KEY"),
				rs.getString("ARDAIS_USER_ID"));
			result.setId(rs.getString("ID"));
			result.setName(rs.getString("NAME"));
			result.setValue(ApiFunctions.getStringFromClob(rs.getClob("VALUE")));
			return result;
		}

		return null;
	}
}
