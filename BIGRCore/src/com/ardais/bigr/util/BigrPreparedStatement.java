package com.ardais.bigr.util;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * An implementation of <code>PreparedStatement</code> that allows the query
 * to be returned with bind variables replaced.
 */
public class BigrPreparedStatement implements PreparedStatement {

  private PreparedStatement _ps;
  private String _sql;
  private String _filteredSql;
  private Object[] _bindVariables;

  private int nextParam = 1; // start with 1

  /**
   * Reset the nextParam counter(s), for example the counter used by the setNextString method.
   */
  protected void resetNextParam() {
    nextParam = 1;
  }

  /**
   * Creates a new <code>BigrPreparedStatement</code> from the specified SQL
   * statement.
   * 
   * @param  con  the JDBC connection
   * @param  sqlStatement  the SQL statement
   */
  public BigrPreparedStatement(Connection con, String sqlStatement) throws SQLException {
    doPrepare(con, sqlStatement);
    _sql = sqlStatement;

    // Filter out all '?' in the statement that are not bind variables
    // by replacing each with a non-printable character, and save the
    // filtered statement.
    char[] sqlString = sqlStatement.toCharArray();
    boolean isString = false;
    int numBindVariables = 0;
    for (int i = 0; i < sqlString.length; i++) {
      if (sqlString[i] == '\'') {
        isString = !isString;
      }
      if (sqlString[i] == '?') {
        if (isString) {
          sqlString[i] = '\u0007';
        }
        else {
          numBindVariables++;
        }
      }
    }
    _filteredSql = new String(sqlString);

    // Create an array for the bind variables.  Add 1 to account for the
    // 1-based nature of setting PreparedStatement bind variables.  Thus,
    // the 0th element will not be used.
    _bindVariables = new Object[numBindVariables + 1];
  }

  /**
   * Returns the <code>PreparedStatement</code> associated with this 
   * <code>BigrPreparedStatement</code>.  Subclasses should override this to return a subclass
   * of <code>PreparedStatement</code> as appropriate.
   * 
   * @return  The underlying <code>PreparedStatement</code>.
   */
  protected PreparedStatement getStatement() {
    return _ps;
  }

  /**
   * Prepares the prepared statement for the specified SQL on the specified connection.  
   * Subclasses should override this to prepare the proper type of statement.
   * 
   * @param  con  the <code>Connection</code>
   * @param  sqlStatement  the SQL statement
   */
  protected void doPrepare(Connection con, String sqlStatement) throws SQLException {
    _ps = con.prepareStatement(sqlStatement);
  }

  /**
   * Returns the number of bind variables in this <code>PreparedStatement</code>.
   * 
   * @return  The number of bind variables.
   */
  public int getNumBindVariables() {
    return _bindVariables.length - 1;
  }

  /**
   * Returns the SQL statement, with all placeholders replaced by their bind
   * variables.  All of the appropriate <code>set</code> methods must have been
   * called to set the bind variables before this method is called.
   * 
   * @return  The SQL statement.
   */
  public String toString() {

    // Replace each ? encountered with the next bind variable.
    StringTokenizer st = new StringTokenizer(_filteredSql, "?");
    StringBuffer sql = new StringBuffer();
    for (int i = 1; st.hasMoreTokens(); i++) {
      sql.append(st.nextToken());
      if (i < _bindVariables.length) {
        Object o = _bindVariables[i];

        // TODO: deal with different data types better.
        if (o == null) {
          sql.append("NULL");
        }
        else if (o instanceof Integer) {
          sql.append(o.toString());
        }
        else {
          sql.append('\'');
          sql.append(o.toString());
          sql.append('\'');
        }
      }
    }

    // Unfilter the string to replace the non-printable character that
    // we initially substituted for all '?' characters that were not
    // bind variables.
    char[] filteredSql = sql.toString().toCharArray();
    for (int i = 0; i < filteredSql.length; i++) {
      if (filteredSql[i] == '\u0007')
        filteredSql[i] = '?';
    }

    return new String(filteredSql);
  }

  /**
   * Set the string, internally maintaining the index of what parameter to bind.
   */
  public void setNextString(String x) throws SQLException {
    setString(nextParam++, x);
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#executeQuery()
   */
  public ResultSet executeQuery() throws SQLException {
    PreparedStatement ps = getStatement();
    ResultSet result = ps.executeQuery();
    resetNextParam();
    return result;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#executeUpdate()
   */
  public int executeUpdate() throws SQLException {
    PreparedStatement ps = getStatement();
    int result = ps.executeUpdate();
    resetNextParam();
    return result;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setNull(int, int)
   */
  public void setNull(int parameterIndex, int sqlType) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setNull(parameterIndex, sqlType);
    _bindVariables[parameterIndex] = null;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setBoolean(int, boolean)
   */
  public void setBoolean(int parameterIndex, boolean x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setBoolean(parameterIndex, x);
    _bindVariables[parameterIndex] = new Boolean(x);
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setByte(int, byte)
   */
  public void setByte(int parameterIndex, byte x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setByte(parameterIndex, x);
    _bindVariables[parameterIndex] = new Byte(x);
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setShort(int, short)
   */
  public void setShort(int parameterIndex, short x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setShort(parameterIndex, x);
    _bindVariables[parameterIndex] = new Short(x);
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setInt(int, int)
   */
  public void setInt(int parameterIndex, int x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setInt(parameterIndex, x);
    _bindVariables[parameterIndex] = new Integer(x);
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setLong(int, long)
   */
  public void setLong(int parameterIndex, long x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setLong(parameterIndex, x);
    _bindVariables[parameterIndex] = new Long(x);
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setFloat(int, float)
   */
  public void setFloat(int parameterIndex, float x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setFloat(parameterIndex, x);
    _bindVariables[parameterIndex] = new Float(x);
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setDouble(int, double)
   */
  public void setDouble(int parameterIndex, double x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setDouble(parameterIndex, x);
    _bindVariables[parameterIndex] = new Double(x);
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setBigDecimal(int, BigDecimal)
   */
  public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setBigDecimal(parameterIndex, x);
    _bindVariables[parameterIndex] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setString(int, String)
   */
  public void setString(int parameterIndex, String x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setString(parameterIndex, x);
    _bindVariables[parameterIndex] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setBytes(int, byte[])
   */
  public void setBytes(int parameterIndex, byte[] x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setBytes(parameterIndex, x);
    _bindVariables[parameterIndex] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setDate(int, Date)
   */
  public void setDate(int parameterIndex, Date x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setDate(parameterIndex, x);
    _bindVariables[parameterIndex] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setTime(int, Time)
   */
  public void setTime(int parameterIndex, Time x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setTime(parameterIndex, x);
    _bindVariables[parameterIndex] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setTimestamp(int, Timestamp)
   */
  public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setTimestamp(parameterIndex, x);
    _bindVariables[parameterIndex] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setAsciiStream(int, InputStream, int)
   */
  public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setAsciiStream(parameterIndex, x, length);
    _bindVariables[parameterIndex] = x;
  }

  /**
   * @see java.sql.PreparedStatement#setUnicodeStream(int, InputStream, int)
   * @deprecated
   */
  public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setUnicodeStream(parameterIndex, x, length);
    _bindVariables[parameterIndex] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setBinaryStream(int, InputStream, int)
   */
  public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setBinaryStream(parameterIndex, x, length);
    _bindVariables[parameterIndex] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#clearParameters()
   */
  public void clearParameters() throws SQLException {
    PreparedStatement ps = getStatement();
    ps.clearParameters();
    Arrays.fill(_bindVariables, null);
    resetNextParam();
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setObject(int, Object, int, int)
   */
  public void setObject(int parameterIndex, Object x, int targetSqlType, int scale)
    throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setObject(parameterIndex, x, targetSqlType, scale);
    _bindVariables[parameterIndex] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setObject(int, Object, int)
   */
  public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setObject(parameterIndex, x, targetSqlType);
    _bindVariables[parameterIndex] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setObject(int, Object)
   */
  public void setObject(int parameterIndex, Object x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setObject(parameterIndex, x);
    _bindVariables[parameterIndex] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#execute()
   */
  public boolean execute() throws SQLException {
    PreparedStatement ps = getStatement();
    boolean result = ps.execute();
    resetNextParam();
    return result;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#addBatch()
   */
  public void addBatch() throws SQLException {
    PreparedStatement ps = getStatement();
    ps.addBatch();
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setCharacterStream(int, Reader, int)
   */
  public void setCharacterStream(int parameterIndex, Reader reader, int length)
    throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setCharacterStream(parameterIndex, reader, length);
    _bindVariables[parameterIndex] = reader;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setRef(int, Ref)
   */
  public void setRef(int i, Ref x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setRef(i, x);
    _bindVariables[i] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setBlob(int, Blob)
   */
  public void setBlob(int i, Blob x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setBlob(i, x);
    _bindVariables[i] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setClob(int, Clob)
   */
  public void setClob(int i, Clob x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setClob(i, x);
    _bindVariables[i] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setArray(int, Array)
   */
  public void setArray(int i, Array x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setArray(i, x);
    _bindVariables[i] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#getMetaData()
   */
  public ResultSetMetaData getMetaData() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getMetaData();
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setDate(int, Date, Calendar)
   */
  public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setDate(parameterIndex, x, cal);
    _bindVariables[parameterIndex] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setTime(int, Time, Calendar)
   */
  public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setTime(parameterIndex, x, cal);
    _bindVariables[parameterIndex] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setTimestamp(int, Timestamp, Calendar)
   */
  public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setTimestamp(parameterIndex, x, cal);
    _bindVariables[parameterIndex] = x;
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setNull(int, int, String)
   */
  public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setNull(paramIndex, sqlType, typeName);
    _bindVariables[paramIndex] = null;
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#executeQuery(String)
   */
  public ResultSet executeQuery(String sql) throws SQLException {
    PreparedStatement ps = getStatement();
    ResultSet result = ps.executeQuery(sql);
    resetNextParam();
    return result;
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#executeUpdate(String)
   */
  public int executeUpdate(String sql) throws SQLException {
    PreparedStatement ps = getStatement();
    int result = ps.executeUpdate(sql);
    resetNextParam();
    return result;
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#close()
   */
  public void close() throws SQLException {
    PreparedStatement ps = getStatement();
    ps.close();
    Arrays.fill(_bindVariables, null);
    resetNextParam();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#getMaxFieldSize()
   */
  public int getMaxFieldSize() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getMaxFieldSize();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#setMaxFieldSize(int)
   */
  public void setMaxFieldSize(int max) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setMaxFieldSize(max);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#getMaxRows()
   */
  public int getMaxRows() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getMaxRows();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#setMaxRows(int)
   */
  public void setMaxRows(int max) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setMaxRows(max);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#setEscapeProcessing(boolean)
   */
  public void setEscapeProcessing(boolean enable) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setEscapeProcessing(enable);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#getQueryTimeout()
   */
  public int getQueryTimeout() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getQueryTimeout();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#setQueryTimeout(int)
   */
  public void setQueryTimeout(int seconds) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setQueryTimeout(seconds);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#cancel()
   */
  public void cancel() throws SQLException {
    PreparedStatement ps = getStatement();
    ps.cancel();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#getWarnings()
   */
  public SQLWarning getWarnings() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getWarnings();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#clearWarnings()
   */
  public void clearWarnings() throws SQLException {
    PreparedStatement ps = getStatement();
    ps.clearWarnings();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#setCursorName(String)
   */
  public void setCursorName(String name) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setCursorName(name);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#execute(String)
   */
  public boolean execute(String sql) throws SQLException {
    PreparedStatement ps = getStatement();
    boolean result = ps.execute(sql);
    resetNextParam();
    return result;
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#getResultSet()
   */
  public ResultSet getResultSet() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getResultSet();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#getUpdateCount()
   */
  public int getUpdateCount() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getUpdateCount();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#getMoreResults()
   */
  public boolean getMoreResults() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getMoreResults();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#setFetchDirection(int)
   */
  public void setFetchDirection(int direction) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setFetchDirection(direction);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#getFetchDirection()
   */
  public int getFetchDirection() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getFetchDirection();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#setFetchSize(int)
   */
  public void setFetchSize(int rows) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setFetchSize(rows);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#getFetchSize()
   */
  public int getFetchSize() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getFetchSize();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#getResultSetConcurrency()
   */
  public int getResultSetConcurrency() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getResultSetConcurrency();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#getResultSetType()
   */
  public int getResultSetType() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getResultSetType();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#addBatch(String)
   */
  public void addBatch(String sql) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.addBatch(sql);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#clearBatch()
   */
  public void clearBatch() throws SQLException {
    PreparedStatement ps = getStatement();
    ps.clearBatch();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#executeBatch()
   */
  public int[] executeBatch() throws SQLException {
    PreparedStatement ps = getStatement();
    int[] result = ps.executeBatch();
    resetNextParam();
    return result;
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#getConnection()
   */
  public Connection getConnection() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getConnection();
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#getParameterMetaData()
   */
  public ParameterMetaData getParameterMetaData() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getParameterMetaData();
  }

  /* (non-Javadoc)
   * @see java.sql.PreparedStatement#setURL(int, java.net.URL)
   */
  public void setURL(int parameterIndex, URL x) throws SQLException {
    PreparedStatement ps = getStatement();
    ps.setURL(parameterIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#execute(java.lang.String, int)
   */
  public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.execute(sql, autoGeneratedKeys);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#execute(java.lang.String, int[])
   */
  public boolean execute(String sql, int[] columnIndexes) throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.execute(sql, columnIndexes);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#execute(java.lang.String, java.lang.String[])
   */
  public boolean execute(String sql, String[] columnNames) throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.execute(sql, columnNames);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#executeUpdate(java.lang.String, int)
   */
  public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.executeUpdate(sql, autoGeneratedKeys);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#executeUpdate(java.lang.String, int[])
   */
  public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.executeUpdate(sql, columnIndexes);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#executeUpdate(java.lang.String, java.lang.String[])
   */
  public int executeUpdate(String sql, String[] columnNames) throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.executeUpdate(sql, columnNames);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#getGeneratedKeys()
   */
  public ResultSet getGeneratedKeys() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getGeneratedKeys();
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#getMoreResults(int)
   */
  public boolean getMoreResults(int current) throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getMoreResults(current);
  }

  /* (non-Javadoc)
   * @see java.sql.Statement#getResultSetHoldability()
   */
  public int getResultSetHoldability() throws SQLException {
    PreparedStatement ps = getStatement();
    return ps.getResultSetHoldability();
  }

@Override
public boolean isClosed() throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void setPoolable(boolean poolable) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public boolean isPoolable() throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public <T> T unwrap(Class<T> iface) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean isWrapperFor(Class<?> iface) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void setRowId(int parameterIndex, RowId x) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNString(int parameterIndex, String value) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNCharacterStream(int parameterIndex, Reader value, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNClob(int parameterIndex, NClob value) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setClob(int parameterIndex, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setBlob(int parameterIndex, InputStream inputStream, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNClob(int parameterIndex, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setAsciiStream(int parameterIndex, InputStream x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setBinaryStream(int parameterIndex, InputStream x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setCharacterStream(int parameterIndex, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setAsciiStream(int parameterIndex, InputStream x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setBinaryStream(int parameterIndex, InputStream x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setCharacterStream(int parameterIndex, Reader reader)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNCharacterStream(int parameterIndex, Reader value)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setClob(int parameterIndex, Reader reader) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setBlob(int parameterIndex, InputStream inputStream)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void setNClob(int parameterIndex, Reader reader) throws SQLException {
	// TODO Auto-generated method stub
	
}

}
