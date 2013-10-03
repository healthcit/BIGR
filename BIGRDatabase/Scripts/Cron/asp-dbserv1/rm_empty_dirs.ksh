#!/bin/ksh

# --------------------------------------------------------------------------
#   Author: Phil DiPrima
# Original: Srikanth Pulikonda
#     Date: 02/20/06
#  Purpose: This script will check empty directories
#           in archive destination and clean them up.
#  WARNING: Some directories like for archivelogs are created but not filed
#           yet and may not need to be removed.
# Modified: For GSB 11/14/06
# -------------------------------------------------------------------------

#########################################################################################
#
# HELPMSG - Display utility syntax
#
#########################################################################################
function HELPMSG {
  echo " "
  echo "Usage: rm_empty_dirs.ksh  <path>  <delete> "
  echo " "
  echo "Where:            path - is the full path name for the top level dir structure to search"
  echo " "
  echo "                  Should the script delete the empty directories it finds empty? Y or N "
  echo " "
  #
  return 0
}
#
# Check command line arguments
#
if [ $# -ne 2 ]
 then HELPMSG
 exit 1
fi

unalias rm

# Process command line arguments
#
# Set DIR to first argument
DIR=${1}
# Set DEL to second argument
DEL=${2}

#echo DIR = ${DIR}
#echo DEL = ${DEL}

echo
echo Analyzing directory:
echo ${DIR}/...
echo " "

# Loop through all directories and check for empty directories
cd ${DIR}
for i in `ls -l ${DIR} | grep -v rm_empty_dirs.ksh  | awk '{print $9}'`
do
fname=`ls $i`
files=`ls $i | wc | awk '{print $1}'`
if [ $files -eq 0 ];
then
 echo "No files in directory: /$i"
 echo "Files Count : $files"
 echo "Filename(s): $fname"
 if [ $DEL = 'Y' ];
 then
  echo "Now removing empty directory /$i..."
  rmdir $i
 else
  echo "Action : You can delete /$i directory"
 fi
 echo " "
else
 echo "There are files in /$i"
 echo "Files Count : $files"
 echo "Filename(s):
 $fname"
 echo "Action : Directory /$i CANNOT be deleted."
 echo " "
fi
done 
