##########################################################################################################################
##	Filename:	schema_compare.sh
##	Author:		Dean Marsh
##	Date:		07/09/2001
##	
##	Comments:	This shell script will, based on user input, compare numerous physical and logical attributes of
##			two schemas and output to a file, schema_compare_report.txt, what those differences are.
##	
##	Requirements:	This script should be run as the Oracle user or any other user that has access to the two Oracle
##			databases that are being compared.  In addition, sql*net connectivity is required to both
##			databases from the server that this script is being run from.
##
##	User Input:	Username, password, and db names of the two schemas to compare.
##	Script Output:	schema_compare_report.txt
##
##########################################################################################################################
#!/bin/ksh

echo "Please enter schema owner for database 1: "
read db1_user
echo "Please enter schema owner password for database 1: "
read db1_pwd
echo "Please enter the name for database 1: "
read db1_name
echo "Please enter schema owner for database 2: "
read db2_user
echo "Please enter schema owner password for database 2: "
read db2_pwd
echo "Please enter the name for database 2: "
read db2_name

echo "You have chosen to compare to $db1_user/$db1_pwd@$db1_name with $db2_user/$db2_pwd@$db2_name."
echo "Press [CTRL-C] to quit or any other key to continue..."
read dummy

sqlplus $db1_user/$db1_pwd@$db1_name <<! 1>/dev/null
set head off pages 0 lines 500 termout off verify off feedback off echo off trimspool on
spool db1_tab_list.lst
select table_name from user_tables order by 1;
spool off
spool db1_index_list.lst
select table_name, index_name from user_indexes where index_name not like 'SYS_%' order by 1, 2;
spool off
spool db1_views_list.lst
select view_name from user_views order by 1;
spool off
spool db1_tab_cols_list.lst
select table_name, column_name from user_tab_columns where table_name not in
(select view_name from user_views) order by 1, 2;
spool off
spool db1_tab_col_detail_list.lst
select table_name, column_name, data_type, data_length, decode(nullable,'Y','NULL','N','NOT-NULL',0) from user_tab_columns
where table_name not in (select view_name from user_views) order by 1, 2;
spool off
spool db1_constraints_list.lst
select table_name, constraint_name from user_constraints where constraint_name not like 'SYS_%' order by 1, 2;
spool off
exit
!

sqlplus $db2_user/$db2_pwd@$db2_name <<! 1>/dev/null
set head off pages 0 lines 500 termout off verify off feedback off echo off trimspool on
spool db2_tab_list.lst
select table_name from user_tables order by 1;
spool off
spool db2_index_list.lst
select table_name, index_name from user_indexes where index_name not like 'SYS_%' order by 1, 2;
spool off
spool db2_views_list.lst
select view_name from user_views order by 1;
spool off
spool db2_tab_cols_list.lst
select table_name, column_name from user_tab_columns 
where table_name not in (select view_name from user_views) order by 1, 2;
spool off
spool db2_tab_col_detail_list.lst
select table_name, column_name, data_type, data_length, decode(nullable,'Y','NULL','N','NOT-NULL',0) from user_tab_columns
where table_name not in (select view_name from user_views) order by 1, 2;
spool off
spool db2_constraints_list.lst
select table_name, constraint_name from user_constraints where constraint_name not like 'SYS_%' order by 1, 2;
spool off
exit
!

for file in *list.lst
do
	ed - $file <<-!
	g/select/d
	g/spool/d
	w
	q
	!
done
(
echo "-----------------------------------------------------------------------------------------------------------------"
echo ""
echo "					Schema comparison report for "
echo "				  $db1_name.$db1_user and $db2_name.$db2_user "
echo "				        Date = `date` "
echo ""
echo "	This report details the physical and logical differences between two databases in several different categories."
echo "	The categories examined in this report are:"
echo "		--Missing tables"
echo "		--Missing indexes (explicitly named and not Oracle generated)"
echo "		--Missing columns"
echo "		--Column datatypes that do not match"
echo "		--Missing constraints (explicitly named and not Oracle generated)"
echo ""
echo "-----------------------------------------------------------------------------------------------------------------"
echo ""
echo ""
echo "-----------------------------------------------------------------------------------------------------------------"
echo "						Missing Tables "
echo "	This section details what tables are missing in each of the databases."
echo "-----------------------------------------------------------------------------------------------------------------"
echo ""
echo ""
echo "TABLE_NAME                         COMMENTS"
echo "---------------------------        --------------------------------------------------------"
echo ""
diff db1_tab_list.lst db2_tab_list.lst > db_tab_list.diff
while read line
do
   which_file=`echo $line | cut -d ' ' -f1`
   tab_name=`echo $line | cut -d ' ' -f2`
   for file in *list.lst
   do
      ed - $file <<!
      g/$tab_name/d
      w
      q
!
   done
   if [ "$which_file" = "<" ]
   then
      printf "%-35sThis table is missing in %s.\n" $tab_name $db2_name.$db2_user
   elif [ "$which_file" = ">" ]
   then
      printf "%-35sThis table is missing in %s.\n" $tab_name $db1_name.$db1_user
   fi
done <db_tab_list.diff
echo "-----------------------------------------------------------------------------------------------------------------"
echo "                                          Missing Views "
echo "  This section details what views are missing in each of the databases."
echo "-----------------------------------------------------------------------------------------------------------------"
echo ""
echo ""
echo "VIEW_NAME                          COMMENTS"
echo "---------------------------        --------------------------------------------------------"
echo ""
diff db1_views_list.lst db2_views_list.lst > db_views_list.diff
while read line
do
   which_file=`echo $line | cut -d ' ' -f1`
   view_name=`echo $line | cut -d ' ' -f2`
   for file in *list.lst
   do
      ed - $file <<!
      g/$view_name/d
      w
      q
!
   done
   if [ "$which_file" = "<" ]
   then
      printf "%-35sThis view is missing in %s.\n" $view_name $db2_name.$db2_user
   elif [ "$which_file" = ">" ]
   then
      printf "%-35sThis view is missing in %s.\n" $view_name $db1_name.$db1_user
   fi
done <db_views_list.diff
echo ""
echo ""
echo "-----------------------------------------------------------------------------------------------------------------"
echo "                                          Missing indexes "
echo "  This section details what indexes are missing in each of the databases.  This list does not include the names"
echo "	of Oracle-generated indexes."
echo "-----------------------------------------------------------------------------------------------------------------"
echo ""
echo ""
echo "INDEX_NAME                              COMMENTS"
echo "----------------------------            -----------------------------------------"
echo ""
diff db1_index_list.lst db2_index_list.lst > db_index_list.diff
while read line
do
   which_file=`echo $line | cut -d ' ' -f1`
   tab_name=`echo $line | cut -d ' ' -f2`
   ind_name=`echo $line | cut -d ' ' -f3`
   for file in *list.lst
   do
      ed - $file <<!
      g/$view_name/d
      w
      q
!
   done
   if [ "$which_file" = "<" ]
   then
      printf "%-40sOn table %s missing in %s.\n" $ind_name $tab_name $db2_name.$db2_user
   elif [ "$which_file" = ">" ]
   then
      printf "%-40sOn table %s missing in %s.\n" $ind_name $tab_name $db1_name.$db1_user
   fi
done <db_index_list.diff
echo ""
echo ""
echo "-----------------------------------------------------------------------------------------------------------------"
echo "                                          Missing Columns "
echo "  This section details what table columns are missing in each of the databases."
echo "-----------------------------------------------------------------------------------------------------------------"
echo ""
echo ""
echo "TABLE_NAME.COLUMN_NAME                                           COMMENTS"
echo "----------------------------                                     ---------------------------"
echo ""
diff db1_tab_cols_list.lst db2_tab_cols_list.lst > db_tab_cols_list.diff
while read line
do
   which_file=`echo $line | cut -d ' ' -f1`
   tab_name=`echo $line | cut -d ' ' -f2`
   col_name=`echo $line | cut -d ' ' -f3`
   if [ "$which_file" = "<" ]
   then
      printf "%-65sMissing in %s.\n" $tab_name.$col_name $db2_name.$db2_user
   elif [ "$which_file" = ">" ]
   then
      printf "%-65sMissing in %s.\n" $tab_name.$col_name $db1_name.$db1_user
   fi
done <db_tab_cols_list.diff

echo ""
echo ""
echo "-----------------------------------------------------------------------------------------------------------------"
echo "                                      Differing datatypes or nullability "
echo "  This section details what columns have different datatypes in the two databases or have different nullability"
echo "	in the two databases."
echo "-----------------------------------------------------------------------------------------------------------------"
echo ""
echo ""
echo "TABLE_NAME.COLUMN_NAME                            COMMENTS"
echo "----------------------------                      -----------------------------------------"
echo ""
diff db1_tab_col_detail_list.lst db2_tab_col_detail_list.lst > db_tab_col_detail_list.diff
while read line1
do
   flag=0
   tab1=`echo $line1 | cut -d ' ' -f2`
   col1=`echo $line1 | cut -d ' ' -f3`
   while read line2
   do
      tab2=`echo $line2 | cut -d ' ' -f2`
      col2=`echo $line2 | cut -d ' ' -f3`
      if [ "$tab1" = "$tab2" ]
      then
         if [ "$col1" = "$col2" ]
         then
            flag=1
            break
         fi
      fi
   done <db_tab_cols_list.diff
   if [ $flag -ne 1 ]
   then
      which_file=`echo $line1 | cut -d ' ' -f1`
      tab_name=`echo $line1 | cut -d ' ' -f2`
      col_name=`echo $line1 | cut -d ' ' -f3`
      data_type=`echo $line1 | cut -d ' ' -f4`
      data_length=`echo $line1 | cut -d ' ' -f5`
      nullable=`echo $line1 | cut -d ' ' -f6`
      if [ "$which_file" = "<" ]
      then
         printf "%-50sThis column is a %s %s in %s.\n" $tab_name.$col_name $data_type\($data_length\) $nullable $db1_name.$db1_user
      elif [ "$which_file" = ">" ]
      then
         printf "%-50sThis column is a %s %s in %s.\n" $tab_name.$col_name $data_type\($data_length\) $nullable $db2_name.$db2_user
      fi
   fi
done <db_tab_col_detail_list.diff
echo ""
echo ""
echo "-----------------------------------------------------------------------------------------------------------------"
echo "                                          Missing constraints "
echo "  This section details what constraints are missing in each of the databases."
echo "-----------------------------------------------------------------------------------------------------------------"
echo ""
echo ""
echo "CONSTRAINT_NAME                         COMMENTS"
echo "----------------------------            -----------------------------------------"
echo ""
diff db1_constraints_list.lst db2_constraints_list.lst > db_constraints_list.diff
while read line
do
   which_file=`echo $line | cut -d ' ' -f1`
   tab_name=`echo $line | cut -d ' ' -f2`
   cons_name=`echo $line | cut -d ' ' -f3`
   if [ "$which_file" = "<" ]
   then
      printf "%-40sOn table %s missing in %s.\n" $cons_name $tab_name $db2_name.$db2_user
   elif [ "$which_file" = ">" ]
   then
      printf "%-40sOn table %s missing in %s.\n" $cons_name $tab_name $db1_name.$db1_user
   fi
done <db_constraints_list.diff

) 2>&1 | (tee -ia schema_compare_report.txt 2>/dev/null || cat)

rm db*list.lst db*list.diff
