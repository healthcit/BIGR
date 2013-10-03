-------------------------------------------------------------------------------------------------
--	File:   GET_NEXT_DELIM.FNC
--	Author: Dean Marsh
--	Date:	10/30/02
--
--	Purpose: This function will return the position of the next delimiter in the string
--	  	 provided as parameter #1.
--
--	NOTE:  Currently, the delimiter is assumed to be a colon.  This can be changed later.
--
--	Parameters:	str - the string to process for the location of the delimiter.
--			delim1 - a numeric value for where to start in the search of the string.
--
-------------------------------------------------------------------------------------------------

create or replace function get_next_delim (str varchar2, delim_char varchar2, delim1 number)
return number
is
   next_delim   number;
begin
   next_delim := instr(str, delim_char, delim1+1);
   if next_delim = 0
   then
	  next_delim := length(str)+1;
   end if;
return next_delim;
end get_next_delim;
/