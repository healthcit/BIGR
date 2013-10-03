##########################################################################################################################
##	Filename:	compare_image_list.sh
##	Author:		Dean Marsh
##	Date:		08/05/2002
##	
##	Comments:	This shell script will, based on user input, compare the current list of image entries in a
##			schema's ads_imaget table to the list of filenames in the supplied directory.
##	
##	User Input:	Username, password, and db name of the location of the image table as well as the fully-qualified
##			name of the image directory at the OS level where the image files are located.
##
##	Script Output:	image_compare_report.txt
##
##########################################################################################################################
#!/bin/ksh

echo "Please enter owner name for the image table to query: "
read db1_user
echo "Please enter the owner's password: "
read db1_pwd
echo "Please enter the name for database where this owner exists: "
read db1_name
echo "Please enter the fully qualified directory name that contains the image filenames to compare to: "
read dir_name

the_host=`hostname`

echo  "You have chosen to compare $db1_user in $db1_name to the file list of $dir_name."
echo "Press [CTRL-C] to quit or any other key to continue..."
read dummy

sqlplus $db1_user/$db1_pwd@$db1_name <<! 1>/dev/null
set head off pages 0 lines 500 termout off verify off feedback off echo off trimspool on
spool db1_image_list.lst
select imagefilename from ads_imaget order by 1;
spool off
exit;
!

ls -1 $dir_name > os_images_list.lst

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
echo "				Image file comparison report for "
echo "			  $db1_name.$db1_user to directory dir_name on $the_host "
echo "				        Date = `date` "
echo ""
echo "	This report lists image differences between the database table in $db1_name and the list of files in "
echo "  $dir_name on $the_host."
echo ""
echo "-----------------------------------------------------------------------------------------------------------------"
echo ""
echo ""
echo "-----------------------------------------------------------------------------------------------------------------"
echo "			Image differences "
echo "-----------------------------------------------------------------------------------------------------------------"
echo ""
echo ""
echo "IMAGE NAME                         COMMENTS"
echo "---------------------------        --------------------------------------------------------"
echo ""
diff db1_image_list.lst os_images_list.lst > image_list.diff
while read line
do
   which_file=`echo $line | cut -d ' ' -f1`
   image_name=`echo $line | cut -d ' ' -f2`
   for file in *list.lst
   do
      ed - $file <<!
      g/$image_name/d
      w
      q
!
   done
   if [ "$which_file" = "<" ]
   then
      printf "%-35sThis image is missing at the OS level.\n" $image_name 
   elif [ "$which_file" = ">" ]
   then
      printf "%-35sThis table is missing in the database.\n" $image_name
   fi
done <image_list.diff

) 2>&1 | (tee -ia image_compare_report.txt 2>/dev/null || cat)

rm *list.lst *list.diff

