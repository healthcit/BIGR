------------------------------------------------------------------------------------------------------------
--	File:		GET_NEW_LINE.FCN
--	Author:		Dean Marsh
--	Date:		11/05/02
--	
--	Purpose:	This function is used to get a new line from a text file at the OS-level.  The line is
--		   	returned as a whole with no parsing or conversion.
--
--	Parameters:	p_file_in - the file pointer to get the line from.  
--
------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION get_new_line ( p_file_in IN UTL_FILE.FILE_TYPE )
 Return varchar2
IS
   v_file_in		    UTL_FILE.FILE_TYPE := p_file_in;
   v_buffer				varchar2(500);
BEGIN
   utl_file.get_line(v_file_in,v_buffer);
   RETURN v_buffer;
EXCEPTION
   WHEN NO_DATA_FOUND THEN
      v_buffer := 'END OF FILE';
   RETURN v_buffer;
   WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR (-20005, 'An error occured during file processing: ' || sqlerrm);
END get_new_line;
/
