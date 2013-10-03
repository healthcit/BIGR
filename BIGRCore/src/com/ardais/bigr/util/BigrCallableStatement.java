package com.ardais.bigr.util;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * An implementation of <code>CallableStatement</code> that allows the query
 * to be returned with bind variables replaced.
 */
public class BigrCallableStatement extends BigrPreparedStatement implements CallableStatement {

  private CallableStatement _cs;

  /**
   * Creates a new <code>BigrCallableStatement</code> from the specified SQL
   * statement.
   * 
   * @param  con  the JDBC connection
   * @param  sqlStatement  the SQL statement
   */
  public BigrCallableStatement(Connection con, String sqlStatement) throws SQLException {
    super(con, sqlStatement);
  }

  /**
   * Returns the <code>CallableStatement</code> associated with this 
   * <code>BigrCallableStatement</code>.
   * 
   * @return  The underlying <code>CallableStatement</code>.
   */
  protected PreparedStatement getStatement() {
    return _cs;
  }

  /**
   * Prepares the callable statement for the specified SQL on the specified connection.
   * 
   * @param  con  the <code>Connection</code>
   * @param  sqlStatement  the SQL statement
   */
  protected void doPrepare(Connection con, String sqlStatement) throws SQLException {
    _cs = con.prepareCall(sqlStatement);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#registerOutParameter(int, int)
   */
  public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.registerOutParameter(parameterIndex, sqlType);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#registerOutParameter(int, int, int)
   */
  public void registerOutParameter(int parameterIndex, int sqlType, int scale)
    throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.registerOutParameter(parameterIndex, sqlType, scale);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#wasNull()
   */
  public boolean wasNull() throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.wasNull();
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getString(int)
   */
  public String getString(int parameterIndex) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getString(parameterIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getBoolean(int)
   */
  public boolean getBoolean(int parameterIndex) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getBoolean(parameterIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getByte(int)
   */
  public byte getByte(int parameterIndex) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getByte(parameterIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getShort(int)
   */
  public short getShort(int parameterIndex) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getShort(parameterIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getInt(int)
   */
  public int getInt(int parameterIndex) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getInt(parameterIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getLong(int)
   */
  public long getLong(int parameterIndex) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getLong(parameterIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getFloat(int)
   */
  public float getFloat(int parameterIndex) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getFloat(parameterIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getDouble(int)
   */
  public double getDouble(int parameterIndex) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getDouble(parameterIndex);
  }

  /**
   * @see java.sql.CallableStatement#getBigDecimal(int, int)
   * @deprecated
   */
  public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getBigDecimal(parameterIndex, scale);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getBytes(int)
   */
  public byte[] getBytes(int parameterIndex) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getBytes(parameterIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getDate(int)
   */
  public Date getDate(int parameterIndex) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getDate(parameterIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getTime(int)
   */
  public Time getTime(int parameterIndex) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getTime(parameterIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getTimestamp(int)
   */
  public Timestamp getTimestamp(int parameterIndex) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getTimestamp(parameterIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getObject(int)
   */
  public Object getObject(int parameterIndex) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getObject(parameterIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getBigDecimal(int)
   */
  public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getBigDecimal(parameterIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getObject(int, Map)
   */
//  public Object getObject(int i, Map map) throws SQLException {
//    CallableStatement cs = (CallableStatement) getStatement();
//    return cs.getObject(i, map);
//  }
//
  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getRef(int)
   */
  public Ref getRef(int i) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getRef(i);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getBlob(int)
   */
  public Blob getBlob(int i) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getBlob(i);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getClob(int)
   */
  public Clob getClob(int i) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getClob(i);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getArray(int)
   */
  public Array getArray(int i) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getArray(i);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getDate(int, Calendar)
   */
  public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getDate(parameterIndex, cal);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getTime(int, Calendar)
   */
  public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getTime(parameterIndex, cal);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getTimestamp(int, Calendar)
   */
  public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getTimestamp(parameterIndex, cal);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#registerOutParameter(int, int, String)
   */
  public void registerOutParameter(int paramIndex, int sqlType, String typeName)
    throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.registerOutParameter(paramIndex, sqlType, typeName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getArray(java.lang.String)
   */
  public Array getArray(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getArray(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getBigDecimal(java.lang.String)
   */
  public BigDecimal getBigDecimal(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getBigDecimal(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getBlob(java.lang.String)
   */
  public Blob getBlob(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getBlob(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getBoolean(java.lang.String)
   */
  public boolean getBoolean(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getBoolean(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getByte(java.lang.String)
   */
  public byte getByte(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getByte(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getBytes(java.lang.String)
   */
  public byte[] getBytes(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getBytes(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getClob(java.lang.String)
   */
  public Clob getClob(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getClob(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getDate(java.lang.String, java.util.Calendar)
   */
  public Date getDate(String parameterName, Calendar cal) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getDate(parameterName, cal);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getDate(java.lang.String)
   */
  public Date getDate(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getDate(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getDouble(java.lang.String)
   */
  public double getDouble(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getDouble(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getFloat(java.lang.String)
   */
  public float getFloat(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getFloat(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getInt(java.lang.String)
   */
  public int getInt(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getInt(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getLong(java.lang.String)
   */
  public long getLong(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getLong(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getObject(java.lang.String, java.util.Map)
   */
//  public Object getObject(String parameterName, Map map) throws SQLException {
//    CallableStatement cs = (CallableStatement) getStatement();
//    return cs.getObject(parameterName, map);
//  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getObject(java.lang.String)
   */
  public Object getObject(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getObject(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getRef(java.lang.String)
   */
  public Ref getRef(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getRef(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getShort(java.lang.String)
   */
  public short getShort(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getShort(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getString(java.lang.String)
   */
  public String getString(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getString(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getTime(java.lang.String, java.util.Calendar)
   */
  public Time getTime(String parameterName, Calendar cal) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getTime(parameterName, cal);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getTime(java.lang.String)
   */
  public Time getTime(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getTime(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getTimestamp(java.lang.String, java.util.Calendar)
   */
  public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getTimestamp(parameterName, cal);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getTimestamp(java.lang.String)
   */
  public Timestamp getTimestamp(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getTimestamp(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getURL(int)
   */
  public URL getURL(int parameterIndex) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getURL(parameterIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#getURL(java.lang.String)
   */
  public URL getURL(String parameterName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getURL(parameterName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#registerOutParameter(java.lang.String, int, int)
   */
  public void registerOutParameter(String parameterName, int sqlType, int scale)
    throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.registerOutParameter(parameterName, sqlType, scale);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#registerOutParameter(java.lang.String, int, java.lang.String)
   */
  public void registerOutParameter(String parameterName, int sqlType, String typeName)
    throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.registerOutParameter(parameterName, sqlType, typeName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#registerOutParameter(java.lang.String, int)
   */
  public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.registerOutParameter(parameterName, sqlType);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setAsciiStream(java.lang.String, java.io.InputStream, int)
   */
  public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setAsciiStream(parameterName, x, length);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setBigDecimal(java.lang.String, java.math.BigDecimal)
   */
  public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setBigDecimal(parameterName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setBinaryStream(java.lang.String, java.io.InputStream, int)
   */
  public void setBinaryStream(String parameterName, InputStream x, int length)
    throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setBinaryStream(parameterName, x, length);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setBoolean(java.lang.String, boolean)
   */
  public void setBoolean(String parameterName, boolean x) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setBoolean(parameterName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setByte(java.lang.String, byte)
   */
  public void setByte(String parameterName, byte x) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setByte(parameterName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setBytes(java.lang.String, byte[])
   */
  public void setBytes(String parameterName, byte[] x) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setBytes(parameterName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setCharacterStream(java.lang.String, java.io.Reader, int)
   */
  public void setCharacterStream(String parameterName, Reader reader, int length)
    throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setCharacterStream(parameterName, reader, length);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setDate(java.lang.String, java.sql.Date, java.util.Calendar)
   */
  public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setDate(parameterName, x, cal);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setDate(java.lang.String, java.sql.Date)
   */
  public void setDate(String parameterName, Date x) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setDate(parameterName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setDouble(java.lang.String, double)
   */
  public void setDouble(String parameterName, double x) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setDouble(parameterName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setFloat(java.lang.String, float)
   */
  public void setFloat(String parameterName, float x) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setFloat(parameterName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setInt(java.lang.String, int)
   */
  public void setInt(String parameterName, int x) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setInt(parameterName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setLong(java.lang.String, long)
   */
  public void setLong(String parameterName, long x) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setLong(parameterName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setNull(java.lang.String, int, java.lang.String)
   */
  public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setNull(parameterName, sqlType, typeName);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setNull(java.lang.String, int)
   */
  public void setNull(String parameterName, int sqlType) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setNull(parameterName, sqlType);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setObject(java.lang.String, java.lang.Object, int, int)
   */
  public void setObject(String parameterName, Object x, int targetSqlType, int scale)
    throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setObject(parameterName, x, targetSqlType, scale);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setObject(java.lang.String, java.lang.Object, int)
   */
  public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setObject(parameterName, x, targetSqlType);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setObject(java.lang.String, java.lang.Object)
   */
  public void setObject(String parameterName, Object x) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setObject(parameterName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setShort(java.lang.String, short)
   */
  public void setShort(String parameterName, short x) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setShort(parameterName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setString(java.lang.String, java.lang.String)
   */
  public void setString(String parameterName, String x) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setString(parameterName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setTime(java.lang.String, java.sql.Time, java.util.Calendar)
   */
  public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setTime(parameterName, x, cal);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setTime(java.lang.String, java.sql.Time)
   */
  public void setTime(String parameterName, Time x) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setTime(parameterName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setTimestamp(java.lang.String, java.sql.Timestamp, java.util.Calendar)
   */
  public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setTimestamp(parameterName, x, cal);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setTimestamp(java.lang.String, java.sql.Timestamp)
   */
  public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setTimestamp(parameterName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.CallableStatement#setURL(java.lang.String, java.net.URL)
   */
  public void setURL(String parameterName, URL val) throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    cs.setURL(parameterName, val);
  }

@Override
public Object getObject(int parameterIndex, Map<String, Class<?>> map)
		throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getObject(parameterIndex, map);
}

@Override
public Object getObject(String parameterName, Map<String, Class<?>> map)
		throws SQLException {
    CallableStatement cs = (CallableStatement) getStatement();
    return cs.getObject(parameterName, map);
}

@Override
public RowId getRowId(int parameterIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public RowId getRowId(String parameterName) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void setRowId(String parameterName, RowId x) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNString(String parameterName, String value) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNCharacterStream(String parameterName, Reader value, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNClob(String parameterName, NClob value) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setClob(String parameterName, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setBlob(String parameterName, InputStream inputStream, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNClob(String parameterName, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public NClob getNClob(int parameterIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public NClob getNClob(String parameterName) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void setSQLXML(String parameterName, SQLXML xmlObject)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public SQLXML getSQLXML(int parameterIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public SQLXML getSQLXML(String parameterName) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getNString(int parameterIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getNString(String parameterName) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Reader getNCharacterStream(int parameterIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Reader getNCharacterStream(String parameterName) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Reader getCharacterStream(int parameterIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Reader getCharacterStream(String parameterName) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void setBlob(String parameterName, Blob x) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setClob(String parameterName, Clob x) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setAsciiStream(String parameterName, InputStream x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setBinaryStream(String parameterName, InputStream x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setCharacterStream(String parameterName, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setAsciiStream(String parameterName, InputStream x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setBinaryStream(String parameterName, InputStream x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setCharacterStream(String parameterName, Reader reader)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNCharacterStream(String parameterName, Reader value)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setClob(String parameterName, Reader reader) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setBlob(String parameterName, InputStream inputStream)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNClob(String parameterName, Reader reader) throws SQLException {
	// TODO Auto-generated method stub
	
}

}
