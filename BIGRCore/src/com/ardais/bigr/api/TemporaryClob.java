package com.ardais.bigr.api;

import java.io.BufferedWriter;
import java.io.Writer;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * This class represents an Oracle temporary CLOB object.  The intent of
 * this class is to make it simpler to write CLOB data to Oracle given its
 * non-compliant implementation of the JDBC CLOB apis in its Thin driver.
 * This class is only used when writing CLOBs to a database, not when reading
 * CLOBs from a database.
 * <p>
 * Please be aware the simply creating a new TemporaryClob object requires
 * communication with the server and allocates server-side resources that
 * you must release later using the {@link #free(Connection) free} method prior to
 * closing the database connection.  Therefore, TemporaryClob objects
 * should only be created when they are definitely going to be used.
 * <p>
 * Here's an example of how you would use a TemporaryClob to update a clob
 * column with a new value.  Similar code could be used to insert a new
 * row containing a CLOB column or to pass a CLOB value to a stored
 * procedure.
 * <pre>
 *	Connection con = null;
 *	PreparedStatement pstmt = null;
 *	TemporaryClob clob = null;
 *	try {
 *		con = ApiFunctions.getDbConnection();
 *    	clob = new TemporaryClob(con, "This is the new CLOB text value.");
 *    	pstmt = con.prepareStatement("update my_table set clob_column = ? where my_key = 1");
 *    	pstmt.setClob(1, clob.getSQLClob());
 *    	pstmt.executeUpdate();
 *  }
 *	catch (Exception e) {
 *		ApiFunctions.throwAsRuntimeException(e);
 *	}
 *	finally {
 *		ApiFunctions.close(clob, con);
 *		ApiFunctions.close(pstmt);
 *		ApiFunctions.close(con);
 *	}
 * </pre>
 */
public final class TemporaryClob {
  private boolean _freed = false;
  private Clob _clob = null;

  /**
   * Create a temporary CLOB with the given string contents.  If the string is
   * empty or null, no actual server-side CLOB is allocated and the
   * {@link #getSQLClob() getSQLClob} method will return null.  Otherwise, a
   * call is made to the database server using the specified collection and
   * a new temporary CLOB is allocated on the server with the specified contents.
   * A temporary CLOB object is only valid for the database connection that was
   * used to create it, and what happens if you attempt to with a different database
   * or after the connection has been closed is undefined.
   * <p>
   * Please be aware the simply creating a new TemporaryClob object requires
   * communication with the server and allocates server-side resources that
   * you must release later using the {@link #free(Connection) free} method prior
   * to closing the database connection.  Therefore, TemporaryClob objects
   * should only be created when they are definitely going to be used.
   *
   * @param con the database connection on which to create the temporary CLOB.
   * @param contents the string that will be the CLOB's contents.
   */
  public TemporaryClob(Connection con, String contents) {
    super();

    if (contents == null || contents.length() == 0) {
      _clob = null;
    }
    else {
      CallableStatement cstmt = null;

      try {
        cstmt = con.prepareCall("{ call dbms_lob.createtemporary (?, TRUE) }");
        cstmt.registerOutParameter(1, Types.CLOB);
        cstmt.execute();
        oracle.sql.CLOB clob = (oracle.sql.CLOB) cstmt.getClob(1);

        Writer writer = new BufferedWriter(clob.getCharacterOutputStream(), clob.getBufferSize());
        writer.write(contents);
        writer.flush();
        ApiFunctions.close(writer);

        _clob = clob;
      }
      catch (Exception e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(cstmt);
      }
    }
  }
  

  /**
   * Release the resources consumed by the temporary CLOB.  This releases both client and
   * server resources, and after calling this {@link #getSQLClob() getSQLClob} will return null.
   * You must pass in the same database connection that was used when initially creating
   * the temporary CLOB.  We require that it be passed in again to emphasize to the caller
   * that temporary CLOBs are only valid for the life of the connection they were created
   * on:  behavior is undefined when one is used after its connection has been closed.
   * It is good style to always explicitly free temporary CLOBs before closing the database
   * connection that they belong to.
   * <p>
   * Attempts to call <code>getSQLClob</code> after freeing a TemporaryClob will result
   * in runtime exception.
   *
   * @param con the SQL connection that was used when this temporary clob was created.
   */
  public void free(Connection con) {
    if (_clob != null) {
      CallableStatement cstmt = null;

      try {
        cstmt = con.prepareCall("{ call dbms_lob.freetemporary (?) }");
        cstmt.setClob(1, _clob);
        cstmt.execute();
      }
      catch (SQLException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(cstmt);
      }

      _clob = null;
    }

    _freed = true;
  }

  /**
   * Return the temporary CLOB as a java.sql.Clob.  This is useful for passing the
   * CLOB to JDBC methods.  If the {@link #free(Connection) free} method has
   * already been called, a runtime exception is thrown.
   *
   * @return the SQL Clob.
   */
  public Clob getSQLClob() {
    if (_freed) {
      throw new IllegalStateException("The TemporaryClob has already been freed.");
    }

    return _clob;
  }
}
