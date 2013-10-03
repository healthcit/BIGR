CREATE OR REPLACE procedure find_concept_except_cir(concept varchar2)
is


   	cursor search_tables 
   		is select table_name from user_tables where 
		(table_name not like 'CIR%');


	cursor search_columns(tname varchar2)
		is select column_name, data_type from user_tab_cols where
		table_name = tname;


   	search_table_names	search_tables%ROWTYPE;
  	search_column_names	search_columns%ROWTYPE;
 
   	query_statement   varchar2(500);
	tabname		  varchar2(100);
	colname		  varchar2(100);
	dtype		  varchar2(100);

  	v_rec_count 	  number;
 
 



BEGIN



   OPEN search_tables;
   LOOP
      -- get all the table names...
      FETCH search_tables INTO search_table_names;
	  EXIT WHEN NOT search_tables%FOUND;

	tabname := search_table_names.table_name;

	-- dbms_output.put_line ('(1) tabname is: ' || tabname );


  	-- get all the columns for each table...
   	OPEN search_columns(tabname);
   	LOOP
      	FETCH search_columns INTO search_column_names;
	  	EXIT WHEN NOT search_columns%FOUND;

		colname := search_column_names.column_name;
		dtype := search_column_names.data_type;
		-- dbms_output.put_line ('(2) colname, datatype is: ' || colname || ':' || dtype);


		-- for each column that is varchar2, see if the concept occurs
		if (dtype = 'VARCHAR2') then
		  query_statement := 'select count(*) from ' || tabname || ' where ' || colname || ' =''' || concept || '''';
		  -- dbms_output.put_line ('query statement is: ' || query_statement );
       		  execute immediate query_statement into v_rec_count;
		  if (v_rec_count > 0) then
			dbms_output.put_line('Found concept: ' || concept || ' in table.column:' || tabname || '.' || colname);
		  end if;
	        end if;



	end loop;
	close search_columns;



   end loop;
   close search_tables;


EXCEPTION

    WHEN OTHERS THEN
      raise_application_error(-20009, 'An error occured during processing find_concept_except_cir: ' || sqlerrm);
	rollback;

END find_concept_except_cir;
/