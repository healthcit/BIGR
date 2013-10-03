package com.ardais.bigr.aperio;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.iltds.beans.SampleAccessBean;
import com.ardais.bigr.iltds.beans.SampleKey;
import com.gulfstreambio.bigr.error.BigrException;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Roman Boris
 * @since 12/6/12
 */
public class AperioImageService
{
	public static AperioImageService SINGLETON = new AperioImageService();

	private AperioImageService()
	{
		super();
	}

	public void addImage(String aperioImageID,
						 String bigrSampleId)
		throws BigrException
	{
		validateBlank("aperioImageID", aperioImageID);
		validateBlank("bigrSampleId", bigrSampleId);
		validateSampleId(bigrSampleId);

		Connection con = null;
		PreparedStatement pstmt = null;

		try
		{
			con = ApiFunctions.getDbConnectionV5();
			pstmt = con.prepareStatement("insert into APERIOIMAGE_BIGRSAMPLE(APERIO_IMAGE_ID, BIGR_SAMPLE_ID) values(?,?)");
			pstmt.setString(1, aperioImageID);
			pstmt.setString(2, bigrSampleId);
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

	public void deleteImage(String bigrSampleId)
		throws BigrException
	{
		validateBlank("bigrSampleId", bigrSampleId);
		validateSampleId(bigrSampleId);

		Connection con = null;
		PreparedStatement pstmt = null;

		try
		{
			con = ApiFunctions.getDbConnectionV5();
			pstmt = con.prepareStatement("delete from APERIOIMAGE_BIGRSAMPLE where BIGR_SAMPLE_ID = ?");
			pstmt.setString(1, bigrSampleId);
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

	public boolean hasImage(String bigrSampleId)
	{
		boolean result = false;
		try
		{
			result = StringUtils.isNotBlank(getImage(bigrSampleId));
		}
		catch (BigrException e)
		{
			ApiLogger.log(e);
		}
		return result;
	}

	public String getImage(String bigrSampleId)
		throws BigrException
	{
		validateBlank("bigrSampleId", bigrSampleId);
		validateSampleId(bigrSampleId);

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;

		try
		{
			con = ApiFunctions.getDbConnectionV5();
			pstmt = con.prepareStatement("select * from APERIOIMAGE_BIGRSAMPLE where BIGR_SAMPLE_ID = ?");
			pstmt.setString(1, bigrSampleId);

			rs = ApiFunctions.queryDb(pstmt, con);
			if (rs.next())
			{
				result = rs.getString("APERIO_IMAGE_ID");
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
		return result;
	}

	protected static void validateBlank(String propertyName, String value)
		throws BigrException
	{
		if (StringUtils.isBlank(value))
		{
			throw new BigrException(
				String.format("[%s=%s] is not valid", propertyName, value));
		}
	}

	protected static void validateSampleId(String bigrSampleId)
		throws BigrException
	{
		try{
			new SampleAccessBean(new SampleKey(bigrSampleId));
		}
		catch(Exception e)
		{
			throw new BigrException(
				String.format("[Sample with id=%s] not exists", bigrSampleId));
		}
	}
}
