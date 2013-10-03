package com.ardais.bigr.util;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import com.ardais.bigr.api.StringCanonicalizer;

/**
 * An implementation of <code>ResultSet</code> that allows strings to be
 * canonicalized.  If a {@link com.ardais.bigr.api.StringCanonicalizer} is set 
 * for an instance of <code>BigrResultSet</code>, then the 
 * <code>getString</code> methods will intern the string before returning it.
 * Otherwise <code>BigrResultSet</code> behaves exactly as 
 * <code>ResultSet</code>.
 */
public class BigrResultSet implements ResultSet {

  private StringCanonicalizer _canonicalizer;
  private ResultSet _rs;

  /**
   * Creates a new <code>BigrResultSet</code>.
   */
  public BigrResultSet() {
  }

  /**
   * Creates a new <code>BigrResultSet</code>, using the specified 
   * {@link com.ardais.bigr.api.StringCanonicalizer} to canonicalize each
   * string returned from the <code>getString</code> methods.
   * 
   * @param  canonicalizer  the <code>StringCanonicalizer</code>
   */
  public BigrResultSet(StringCanonicalizer canonicalizer) {
    setStringCanonicalizer(canonicalizer);
  }

  /**
   * Sets a {@link com.ardais.bigr.api.StringCanonicalizer} to use to 
   * canonicalize each string returned from the <code>getString</code> methods.
   * 
   * @param  canonicalizer  the <code>StringCanonicalizer</code>
   */
  public void setStringCanonicalizer(StringCanonicalizer canonicalizer) {
    _canonicalizer = canonicalizer;
  }

  /**
   * Sets the actual <code>ResultSet</code> object returned by a JDBC driver 
   * that is used to implement this <code>BigrResultSet</code>.
   * 
   * @param  rs  the actual <code>ResultSet</code>
   */
  public void setResultSet(ResultSet rs) {
    _rs = rs;
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#absolute(int)
   */
  public boolean absolute(int row) throws SQLException {
    return _rs.absolute(row);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#afterLast()
   */
  public void afterLast() throws SQLException {
    _rs.afterLast();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#beforeFirst()
   */
  public void beforeFirst() throws SQLException {
    _rs.beforeFirst();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#cancelRowUpdates()
   */
  public void cancelRowUpdates() throws SQLException {
    _rs.cancelRowUpdates();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#clearWarnings()
   */
  public void clearWarnings() throws SQLException {
    _rs.clearWarnings();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#close()
   */
  public void close() throws SQLException {
    if (_rs != null) {
      _rs.close();
    }
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#deleteRow()
   */
  public void deleteRow() throws SQLException {
    _rs.deleteRow();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#findColumn(String)
   */
  public int findColumn(String columnName) throws SQLException {
    return _rs.findColumn(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#first()
   */
  public boolean first() throws SQLException {
    return _rs.first();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getArray(int)
   */
  public Array getArray(int i) throws SQLException {
    return _rs.getArray(i);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getArray(String)
   */
  public Array getArray(String colName) throws SQLException {
    return _rs.getArray(colName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getAsciiStream(int)
   */
  public InputStream getAsciiStream(int columnIndex) throws SQLException {
    return _rs.getAsciiStream(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getAsciiStream(String)
   */
  public InputStream getAsciiStream(String columnName) throws SQLException {
    return _rs.getAsciiStream(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getBigDecimal(int)
   */
  public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
    return _rs.getBigDecimal(columnIndex);
  }

  /**
   * @see java.sql.ResultSet#getBigDecimal(int, int)
   * @deprecated
   */
  public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
    return _rs.getBigDecimal(columnIndex, scale);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getBigDecimal(String)
   */
  public BigDecimal getBigDecimal(String columnName) throws SQLException {
    return _rs.getBigDecimal(columnName);
  }

  /**
   * @see java.sql.ResultSet#getBigDecimal(String, int)
   * @deprecated
   */
  public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException {
    return _rs.getBigDecimal(columnName, scale);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getBinaryStream(int)
   */
  public InputStream getBinaryStream(int columnIndex) throws SQLException {
    return _rs.getBinaryStream(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getBinaryStream(String)
   */
  public InputStream getBinaryStream(String columnName) throws SQLException {
    return _rs.getBinaryStream(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getBlob(int)
   */
  public Blob getBlob(int i) throws SQLException {
    return _rs.getBlob(i);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getBlob(String)
   */
  public Blob getBlob(String colName) throws SQLException {
    return _rs.getBlob(colName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getBoolean(int)
   */
  public boolean getBoolean(int columnIndex) throws SQLException {
    return _rs.getBoolean(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getBoolean(String)
   */
  public boolean getBoolean(String columnName) throws SQLException {
    return _rs.getBoolean(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getByte(int)
   */
  public byte getByte(int columnIndex) throws SQLException {
    return _rs.getByte(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getByte(String)
   */
  public byte getByte(String columnName) throws SQLException {
    return _rs.getByte(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getBytes(int)
   */
  public byte[] getBytes(int columnIndex) throws SQLException {
    return _rs.getBytes(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getBytes(String)
   */
  public byte[] getBytes(String columnName) throws SQLException {
    return _rs.getBytes(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getCharacterStream(int)
   */
  public Reader getCharacterStream(int columnIndex) throws SQLException {
    return _rs.getCharacterStream(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getCharacterStream(String)
   */
  public Reader getCharacterStream(String columnName) throws SQLException {
    return _rs.getCharacterStream(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getClob(int)
   */
  public Clob getClob(int i) throws SQLException {
    return _rs.getClob(i);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getClob(String)
   */
  public Clob getClob(String colName) throws SQLException {
    return _rs.getClob(colName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getConcurrency()
   */
  public int getConcurrency() throws SQLException {
    return _rs.getConcurrency();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getCursorName()
   */
  public String getCursorName() throws SQLException {
    return _rs.getCursorName();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getDate(int)
   */
  public Date getDate(int columnIndex) throws SQLException {
    return _rs.getDate(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getDate(int, Calendar)
   */
  public Date getDate(int columnIndex, Calendar cal) throws SQLException {
    return _rs.getDate(columnIndex, cal);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getDate(String)
   */
  public Date getDate(String columnName) throws SQLException {
    return _rs.getDate(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getDate(String, Calendar)
   */
  public Date getDate(String columnName, Calendar cal) throws SQLException {
    return _rs.getDate(columnName, cal);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getDouble(int)
   */
  public double getDouble(int columnIndex) throws SQLException {
    return _rs.getDouble(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getDouble(String)
   */
  public double getDouble(String columnName) throws SQLException {
    return _rs.getDouble(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getFetchDirection()
   */
  public int getFetchDirection() throws SQLException {
    return _rs.getFetchDirection();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getFetchSize()
   */
  public int getFetchSize() throws SQLException {
    return _rs.getFetchSize();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getFloat(int)
   */
  public float getFloat(int columnIndex) throws SQLException {
    return _rs.getFloat(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getFloat(String)
   */
  public float getFloat(String columnName) throws SQLException {
    return _rs.getFloat(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getInt(int)
   */
  public int getInt(int columnIndex) throws SQLException {
    return _rs.getInt(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getInt(String)
   */
  public int getInt(String columnName) throws SQLException {
    return _rs.getInt(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getLong(int)
   */
  public long getLong(int columnIndex) throws SQLException {
    return _rs.getLong(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getLong(String)
   */
  public long getLong(String columnName) throws SQLException {
    return _rs.getLong(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getMetaData()
   */
  public ResultSetMetaData getMetaData() throws SQLException {
    return _rs.getMetaData();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getObject(int)
   */
  public Object getObject(int columnIndex) throws SQLException {
    return _rs.getObject(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getObject(int, Map)
   */
//  public Object getObject(int i, Map map) throws SQLException {
//    return _rs.getObject(i, map);
//  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getObject(String)
   */
  public Object getObject(String columnName) throws SQLException {
    return _rs.getObject(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getObject(String, Map)
   */
//  public Object getObject(String colName, Map map) throws SQLException {
//    return _rs.getObject(colName, map);
//  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getRef(int)
   */
  public Ref getRef(int i) throws SQLException {
    return _rs.getRef(i);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getRef(String)
   */
  public Ref getRef(String colName) throws SQLException {
    return _rs.getRef(colName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getRow()
   */
  public int getRow() throws SQLException {
    return _rs.getRow();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getShort(int)
   */
  public short getShort(int columnIndex) throws SQLException {
    return _rs.getShort(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getShort(String)
   */
  public short getShort(String columnName) throws SQLException {
    return _rs.getShort(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getStatement()
   */
  public Statement getStatement() throws SQLException {
    return _rs.getStatement();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getString(int)
   */
  public String getString(int columnIndex) throws SQLException {
    if (_canonicalizer != null) {
      return _canonicalizer.intern(_rs.getString(columnIndex));
    }
    else {
      return _rs.getString(columnIndex);
    }
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getString(String)
   */
  public String getString(String columnName) throws SQLException {
    if (_canonicalizer != null) {
      return _canonicalizer.intern(_rs.getString(columnName));
    }
    else {
      return _rs.getString(columnName);
    }
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getTime(int)
   */
  public Time getTime(int columnIndex) throws SQLException {
    return _rs.getTime(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getTime(int, Calendar)
   */
  public Time getTime(int columnIndex, Calendar cal) throws SQLException {
    return _rs.getTime(columnIndex, cal);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getTime(String)
   */
  public Time getTime(String columnName) throws SQLException {
    return _rs.getTime(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getTime(String, Calendar)
   */
  public Time getTime(String columnName, Calendar cal) throws SQLException {
    return _rs.getTime(columnName, cal);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getTimestamp(int)
   */
  public Timestamp getTimestamp(int columnIndex) throws SQLException {
    return _rs.getTimestamp(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getTimestamp(int, Calendar)
   */
  public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
    return _rs.getTimestamp(columnIndex, cal);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getTimestamp(String)
   */
  public Timestamp getTimestamp(String columnName) throws SQLException {
    return _rs.getTimestamp(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getTimestamp(String, Calendar)
   */
  public Timestamp getTimestamp(String columnName, Calendar cal) throws SQLException {
    return _rs.getTimestamp(columnName, cal);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getType()
   */
  public int getType() throws SQLException {
    return _rs.getType();
  }

  /**
   * @see java.sql.ResultSet#getUnicodeStream(int)
   * @deprecated
   */
  public InputStream getUnicodeStream(int columnIndex) throws SQLException {
    return _rs.getUnicodeStream(columnIndex);
  }

  /**
   * @see java.sql.ResultSet#getUnicodeStream(String)
   * @deprecated
   */
  public InputStream getUnicodeStream(String columnName) throws SQLException {
    return _rs.getUnicodeStream(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getWarnings()
   */
  public SQLWarning getWarnings() throws SQLException {
    return _rs.getWarnings();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#insertRow()
   */
  public void insertRow() throws SQLException {
    _rs.insertRow();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#isAfterLast()
   */
  public boolean isAfterLast() throws SQLException {
    return _rs.isAfterLast();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#isBeforeFirst()
   */
  public boolean isBeforeFirst() throws SQLException {
    return _rs.isBeforeFirst();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#isFirst()
   */
  public boolean isFirst() throws SQLException {
    return _rs.isFirst();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#isLast()
   */
  public boolean isLast() throws SQLException {
    return _rs.isLast();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#last()
   */
  public boolean last() throws SQLException {
    return _rs.last();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#moveToCurrentRow()
   */
  public void moveToCurrentRow() throws SQLException {
    _rs.moveToCurrentRow();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#moveToInsertRow()
   */
  public void moveToInsertRow() throws SQLException {
    _rs.moveToInsertRow();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#next()
   */
  public boolean next() throws SQLException {
    return _rs.next();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#previous()
   */
  public boolean previous() throws SQLException {
    return _rs.previous();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#refreshRow()
   */
  public void refreshRow() throws SQLException {
    _rs.refreshRow();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#relative(int)
   */
  public boolean relative(int rows) throws SQLException {
    return _rs.relative(rows);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#rowDeleted()
   */
  public boolean rowDeleted() throws SQLException {
    return _rs.rowDeleted();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#rowInserted()
   */
  public boolean rowInserted() throws SQLException {
    return _rs.rowInserted();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#rowUpdated()
   */
  public boolean rowUpdated() throws SQLException {
    return _rs.rowUpdated();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#setFetchDirection(int)
   */
  public void setFetchDirection(int direction) throws SQLException {
    _rs.setFetchDirection(direction);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#setFetchSize(int)
   */
  public void setFetchSize(int rows) throws SQLException {
    _rs.setFetchSize(rows);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateAsciiStream(int, InputStream, int)
   */
  public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
    _rs.updateAsciiStream(columnIndex, x, length);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateAsciiStream(String, InputStream, int)
   */
  public void updateAsciiStream(String columnName, InputStream x, int length) throws SQLException {
    _rs.updateAsciiStream(columnName, x, length);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateBigDecimal(int, BigDecimal)
   */
  public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
    _rs.updateBigDecimal(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateBigDecimal(String, BigDecimal)
   */
  public void updateBigDecimal(String columnName, BigDecimal x) throws SQLException {
    _rs.updateBigDecimal(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateBinaryStream(int, InputStream, int)
   */
  public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
    _rs.updateBinaryStream(columnIndex, x, length);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateBinaryStream(String, InputStream, int)
   */
  public void updateBinaryStream(String columnName, InputStream x, int length)
    throws SQLException {
    _rs.updateBinaryStream(columnName, x, length);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateBoolean(int, boolean)
   */
  public void updateBoolean(int columnIndex, boolean x) throws SQLException {
    _rs.updateBoolean(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateBoolean(String, boolean)
   */
  public void updateBoolean(String columnName, boolean x) throws SQLException {
    _rs.updateBoolean(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateByte(int, byte)
   */
  public void updateByte(int columnIndex, byte x) throws SQLException {
    _rs.updateByte(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateByte(String, byte)
   */
  public void updateByte(String columnName, byte x) throws SQLException {
    _rs.updateByte(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateBytes(int, byte[])
   */
  public void updateBytes(int columnIndex, byte[] x) throws SQLException {
    _rs.updateBytes(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateBytes(String, byte[])
   */
  public void updateBytes(String columnName, byte[] x) throws SQLException {
    _rs.updateBytes(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateCharacterStream(int, Reader, int)
   */
  public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
    _rs.updateCharacterStream(columnIndex, x, length);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateCharacterStream(String, Reader, int)
   */
  public void updateCharacterStream(String columnName, Reader reader, int length)
    throws SQLException {
    _rs.updateCharacterStream(columnName, reader, length);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateDate(int, Date)
   */
  public void updateDate(int columnIndex, Date x) throws SQLException {
    _rs.updateDate(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateDate(String, Date)
   */
  public void updateDate(String columnName, Date x) throws SQLException {
    _rs.updateDate(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateDouble(int, double)
   */
  public void updateDouble(int columnIndex, double x) throws SQLException {
    _rs.updateDouble(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateDouble(String, double)
   */
  public void updateDouble(String columnName, double x) throws SQLException {
    _rs.updateDouble(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateFloat(int, float)
   */
  public void updateFloat(int columnIndex, float x) throws SQLException {
    _rs.updateFloat(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateFloat(String, float)
   */
  public void updateFloat(String columnName, float x) throws SQLException {
    _rs.updateFloat(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateInt(int, int)
   */
  public void updateInt(int columnIndex, int x) throws SQLException {
    _rs.updateInt(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateInt(String, int)
   */
  public void updateInt(String columnName, int x) throws SQLException {
    _rs.updateInt(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateLong(int, long)
   */
  public void updateLong(int columnIndex, long x) throws SQLException {
    _rs.updateLong(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateLong(String, long)
   */
  public void updateLong(String columnName, long x) throws SQLException {
    _rs.updateLong(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateNull(int)
   */
  public void updateNull(int columnIndex) throws SQLException {
    _rs.updateNull(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateNull(String)
   */
  public void updateNull(String columnName) throws SQLException {
    _rs.updateNull(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateObject(int, Object)
   */
  public void updateObject(int columnIndex, Object x) throws SQLException {
    _rs.updateObject(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateObject(int, Object, int)
   */
  public void updateObject(int columnIndex, Object x, int scale) throws SQLException {
    _rs.updateObject(columnIndex, x, scale);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateObject(String, Object)
   */
  public void updateObject(String columnName, Object x) throws SQLException {
    _rs.updateObject(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateObject(String, Object, int)
   */
  public void updateObject(String columnName, Object x, int scale) throws SQLException {
    _rs.updateObject(columnName, x, scale);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateRow()
   */
  public void updateRow() throws SQLException {
    _rs.updateRow();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateShort(int, short)
   */
  public void updateShort(int columnIndex, short x) throws SQLException {
    _rs.updateShort(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateShort(String, short)
   */
  public void updateShort(String columnName, short x) throws SQLException {
    _rs.updateShort(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateString(int, String)
   */
  public void updateString(int columnIndex, String x) throws SQLException {
    _rs.updateString(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateString(String, String)
   */
  public void updateString(String columnName, String x) throws SQLException {
    _rs.updateString(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateTime(int, Time)
   */
  public void updateTime(int columnIndex, Time x) throws SQLException {
    _rs.updateTime(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateTime(String, Time)
   */
  public void updateTime(String columnName, Time x) throws SQLException {
    _rs.updateTime(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateTimestamp(int, Timestamp)
   */
  public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
    _rs.updateTimestamp(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateTimestamp(String, Timestamp)
   */
  public void updateTimestamp(String columnName, Timestamp x) throws SQLException {
    _rs.updateTimestamp(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#wasNull()
   */
  public boolean wasNull() throws SQLException {
    return _rs.wasNull();
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getURL(int)
   */
  public URL getURL(int columnIndex) throws SQLException {
    return _rs.getURL(columnIndex);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#getURL(java.lang.String)
   */
  public URL getURL(String columnName) throws SQLException {
    return _rs.getURL(columnName);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateArray(int, java.sql.Array)
   */
  public void updateArray(int columnIndex, Array x) throws SQLException {
    _rs.updateArray(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateArray(java.lang.String, java.sql.Array)
   */
  public void updateArray(String columnName, Array x) throws SQLException {
    _rs.updateArray(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateBlob(int, java.sql.Blob)
   */
  public void updateBlob(int columnIndex, Blob x) throws SQLException {
    _rs.updateBlob(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateBlob(java.lang.String, java.sql.Blob)
   */
  public void updateBlob(String columnName, Blob x) throws SQLException {
    _rs.updateBlob(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateClob(int, java.sql.Clob)
   */
  public void updateClob(int columnIndex, Clob x) throws SQLException {
    _rs.updateClob(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateClob(java.lang.String, java.sql.Clob)
   */
  public void updateClob(String columnName, Clob x) throws SQLException {
    _rs.updateClob(columnName, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateRef(int, java.sql.Ref)
   */
  public void updateRef(int columnIndex, Ref x) throws SQLException {
    _rs.updateRef(columnIndex, x);
  }

  /* (non-Javadoc)
   * @see java.sql.ResultSet#updateRef(java.lang.String, java.sql.Ref)
   */
  public void updateRef(String columnName, Ref x) throws SQLException {
    _rs.updateRef(columnName, x);
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
public Object getObject(int columnIndex, Map<String, Class<?>> map)
		throws SQLException {
	   return _rs.getObject(columnIndex, map);
}

@Override
public Object getObject(String columnName, Map<String, Class<?>> map)
		throws SQLException {
	   return _rs.getObject(columnName, map);
}

@Override
public RowId getRowId(int columnIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public RowId getRowId(String columnLabel) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void updateRowId(int columnIndex, RowId x) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateRowId(String columnLabel, RowId x) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public int getHoldability() throws SQLException {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public boolean isClosed() throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void updateNString(int columnIndex, String nString) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateNString(String columnLabel, String nString)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public NClob getNClob(int columnIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public NClob getNClob(String columnLabel) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public SQLXML getSQLXML(int columnIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public SQLXML getSQLXML(String columnLabel) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateSQLXML(String columnLabel, SQLXML xmlObject)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public String getNString(int columnIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getNString(String columnLabel) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Reader getNCharacterStream(int columnIndex) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Reader getNCharacterStream(String columnLabel) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void updateNCharacterStream(int columnIndex, Reader x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateNCharacterStream(String columnLabel, Reader reader,
		long length) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateAsciiStream(int columnIndex, InputStream x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateBinaryStream(int columnIndex, InputStream x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateCharacterStream(int columnIndex, Reader x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateAsciiStream(String columnLabel, InputStream x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateBinaryStream(String columnLabel, InputStream x, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateCharacterStream(String columnLabel, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateBlob(int columnIndex, InputStream inputStream, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateBlob(String columnLabel, InputStream inputStream, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateClob(int columnIndex, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateClob(String columnLabel, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateNClob(int columnIndex, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateNClob(String columnLabel, Reader reader, long length)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateNCharacterStream(int columnIndex, Reader x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateNCharacterStream(String columnLabel, Reader reader)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateAsciiStream(int columnIndex, InputStream x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateBinaryStream(int columnIndex, InputStream x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateCharacterStream(int columnIndex, Reader x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateAsciiStream(String columnLabel, InputStream x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateBinaryStream(String columnLabel, InputStream x)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateCharacterStream(String columnLabel, Reader reader)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateBlob(int columnIndex, InputStream inputStream)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateBlob(String columnLabel, InputStream inputStream)
		throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateClob(int columnIndex, Reader reader) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateClob(String columnLabel, Reader reader) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateNClob(int columnIndex, Reader reader) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public void updateNClob(String columnLabel, Reader reader) throws SQLException {
	// TODO Auto-generated method stub
	
}

}
