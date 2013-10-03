package com.ardais.bigr.report.storagereport;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.api.ApiLogger;
import com.ardais.bigr.report.storagereport.model.StorageReportModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Boris
 * @since 5/17/13
 */
public class StorageReportService
{
	public static StorageReportService SINGLETON = new StorageReportService();

	private StorageReportService()
	{
	}

	public List<StorageReportModel> getStorageReportInfo()
	{
		final List<StorageReportModel> result = new ArrayList<StorageReportModel>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			con = ApiFunctions.getDbConnection();
			pstmt = con.prepareStatement(
				"select loc.LOCATION_ADDRESS_ID, loc.UNIT_NAME, loc.STORAGE_TYPE_CID, loc.BOX_BARCODE_ID, ly.NUMBER_OF_COLUMNS, ly.NUMBER_OF_ROWS, count(*) IN_USE " +
				"from ILTDS_BOX_LOCATION loc " +
					"inner join ILTDS_SAMPLE_BOX sbox on sbox.BOX_BARCODE_ID = loc.BOX_BARCODE_ID " +
					"inner join ILTDS_BOX_LAYOUT ly on ly.BOX_LAYOUT_ID = sbox.BOX_LAYOUT_ID , ILTDS_SAMPLE smpl " +
				"where smpl.box_barcode_id = loc.BOX_BARCODE_ID " +
				"group by loc.LOCATION_ADDRESS_ID, loc.UNIT_NAME, loc.STORAGE_TYPE_CID, loc.BOX_BARCODE_ID, ly.NUMBER_OF_COLUMNS, ly.NUMBER_OF_ROWS");
			rs = ApiFunctions.queryDb(pstmt, con);
			while (rs.next())
			{
				final StorageReportModel model = new StorageReportModel();
				model.setLocationAddressId(rs.getString("LOCATION_ADDRESS_ID"));
				model.setStorageTypeCid(rs.getString("STORAGE_TYPE_CID"));
				model.setUnitName(rs.getString("UNIT_NAME"));
				model.setBoxId(rs.getString("BOX_BARCODE_ID"));
				model.setColumns(rs.getInt("NUMBER_OF_COLUMNS"));
				model.setRows(rs.getInt("NUMBER_OF_ROWS"));
				model.setInUse(rs.getInt("IN_USE"));
				result.add(model);
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
}
