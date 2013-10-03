package com.ardais.bigr.api;

/*******************************************************************************************************
Author:Arthur Andersen LLP
Class:Parent Api Class
Create Date:10-Aug-2000
*******************************************************************************************************
Modified by 	Date					Modification
C. Satyanarayan 18-Sep-2000             Javadoc changes
C. Satyanarayan 18-Sep-2000             VSS Demo
*******************************************************************************************************/
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

/**
 * Parent Api Servlet class, extends HttpServlet, and may contain generic Servlet behavior in future releases
 */
public class ApiParent extends HttpServlet
{ 
    /**
     * Parent Constructor to call the HttpServlet
     */
    public ApiParent()
    {
		super();
    }
}
