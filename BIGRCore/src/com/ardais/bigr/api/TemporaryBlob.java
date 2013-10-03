package com.ardais.bigr.api;


import java.io.OutputStream;
import java.sql.CallableStatement;
import oracle.sql.BLOB;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * This class represents an Oracle temporary BLOB object.  The intent of
 * this class is to make it simpler to write BLOB data to Oracle given its
 * non-compliant implementation of the JDBC BLOB apis in its Thin driver.
 * This class is only used when writing BLOBs to a database, not when reading
 * BLOBs from a database.
 * <p>
 * Please be aware the simply creating a new TemporaryBLOB object requires
 * communication with the server and allocates server-side resources that
 * you must release later using the {@link #free(Connection) free} method prior to
 * closing the database connection.  Therefore, TemporaryBLOB objects
 * should only be created when they are definitely going to be used.
 * <p>
 * Here's an example of how you would use a TemporaryBLOB to update a BLOB
 * column with a new value.  Similar code could be used to insert a new
 * row containing a BLOB column or to pass a BLOB value to a stored
 * procedure.
 * <pre>
 *	Connection con = null;
 *	PreparedStatement pstmt = null;
 *	TemporaryBLOB BLOB = null;
 *	try {
 *		con = ApiFunctions.getDbConnection();
 *    	BLOB = new TemporaryBLOB(con, "This is the new BLOB text value.");
 *    	pstmt = con.prepareStatement("update my_table set BLOB_column = ? where my_key = 1");
 *    	pstmt.setBLOB(1, BLOB.getSQLBLOB());
 *    	pstmt.executeUpdate();
 *  }
 *	catch (Exception e) {
 *		ApiFunctions.throwAsRuntimeException(e);
 *	}
 *	finally {
 *		ApiFunctions.close(BLOB, con);
 *		ApiFunctions.close(pstmt);
 *		ApiFunctions.close(con);
 *	}
 * </pre>
 */
public final class TemporaryBlob {
  private boolean _freed = false;
  private BLOB _BLOB = null;

 
  
  /**
   * Create a temporary BLOB with the given string contents.  If the string is
   * empty or null, no actual server-side BLOB is allocated and the
   * {@link #getSQLBlob() getSQLBlob} method will return null.  Otherwise, a
   * call is made to the database server using the specified collection and
   * a new temporary BLOB is allocated on the server with the specified contents.
   * A temporary BLOB object is only valid for the database connection that was
   * used to create it, and what happens if you attempt to with a different database
   * or after the connection has been closed is undefined.
   * <p>
   * Please be aware the simply creating a new TemporaryBlob object requires
   * communication with the server and allocates server-side resources that
   * you must release later using the {@link #free(Connection) free} method prior
   * to closing the database connection.  Therefore, TemporaryBLOB objects
   * should only be created when they are definitely going to be used.
   *
   * @param con the database connection on which to create the temporary BLOB.
   * @param contents the string that will be the BLOB's contents.
   */
  public TemporaryBlob(Connection con, byte[] binaryContent) {
    super();

    if (binaryContent == null || binaryContent.length == 0) {
      _BLOB = null;
    }
    else {
      CallableStatement cstmt = null;

      try {
        cstmt = con.prepareCall("{ call dbms_lob.createtemporary (?, TRUE) }");
        cstmt.registerOutParameter(1, Types.BLOB);
        cstmt.execute();
        BLOB BLOB = (BLOB) cstmt.getBlob(1);
         
        //this outputstream is for byte[]
        OutputStream os = BLOB.getBinaryOutputStream();

          os.write(binaryContent);
          os.flush();
          //os.close();

          ApiFunctions.close(os);

        _BLOB = BLOB;
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
   * Release the resources consumed by the temporary BLOB.  This releases both client and
   * server resources, and after calling this {@link #getSQLBLOB() getSQLBLOB} will return null.
   * You must pass in the same database connection that was used when initially creating
   * the temporary BLOB.  We require that it be passed in again to emphasize to the caller
   * that temporary BLOBs are only valid for the life of the connection they were created
   * on:  behavior is undefined when one is used after its connection has been closed.
   * It is good style to always explicitly free temporary BLOBs before closing the database
   * connection that they belong to.
   * <p>
   * Attempts to call <code>getSQLBLOB</code> after freeing a TemporaryBLOB will result
   * in runtime exception.
   *
   * @param con the SQL connection that was used when this temporary BLOB was created.
   */
  public void free(Connection con) {
    if (_BLOB != null) {
      CallableStatement cstmt = null;

      try {
        cstmt = con.prepareCall("{ call dbms_lob.freetemporary (?) }");
        cstmt.setBlob(1, _BLOB);
        cstmt.execute();
      }
      catch (SQLException e) {
        ApiFunctions.throwAsRuntimeException(e);
      }
      finally {
        ApiFunctions.close(cstmt);
      }

      _BLOB = null;
    }

    _freed = true;
  }

  /**
   * Return the temporary BLOB as a java.sql.BLOB.  This is useful for passing the
   * BLOB to JDBC methods.  If the {@link #free(Connection) free} method has
   * already been called, a runtime exception is thrown.
   *
   * @return the SQL Blob.
   */
  public BLOB getSQLBlob() {
    if (_freed) {
      throw new IllegalStateException("The TemporaryBLOB has already been freed.");
    }

    return _BLOB;
  }
}
