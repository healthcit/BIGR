#!/bin/ksh
#============================================================================
# File:         chk_disk_space.ksh
# Type:         UNIX korn-shell script
# Author:       Phil DiPrima
# Date:         01/18/07
#
# Description:
#       Using Linux df -h, cech if the disk space is greater than
#       user defined thresholds.
#       The first param is the critical threshold the second is
#       a warning threshold.  
#
# Syntax:
#       chk_disk_space.ksh <THRESHOLD_critical> <THRESHOLD_warning>
#
#============================================================================
# Configuration
# Who do I email, etc
export RCPT=dba@healthcit.com
touch /tmp/.thedf
export THEFILE=/tmp/.thedf
#============================================================================
function HELPMSG {
  echo " "
  echo "Usage: chk_disk_space.ksh <THRESHOLD_critical> <THRESHOLD_warning> "
  echo " "
  echo "Where:            THRESHOLD_critical - Dangerously low disk space"
  echo "                  THRESHOLD_warning  - Be concerned about disk space"
  echo " "
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

# Process command line arguments
# Set THRESHOLD to first and second arguments
THRESHOLD_HIGH=${1}
export THRESHOLD_HIGH
THRESHOLD_LOW=${2}
export THRESHOLD_LOW

fs=`mount|egrep '^/dev'|grep -iv cdrom| awk '{print $3}'`

for i in $fs
do
    skip=0
    typeset -i used=`df -h $i|tail -1|awk '{print $5}'|cut -d "%" -f 1`
    AVAIL=`df -h $i|tail -1|awk '{print $4}'|cut -d "%" -f 1`
    if [ "$used" -ge "$THRESHOLD_HIGH" ]; then
        echo "CRITICAL: filesystem $i is $used% full; $AVAIL available." >> $THEFILE
    fi
    if [ "$used" -ge "$THRESHOLD_LOW" -a "$used" -le "$THRESHOLD_HIGH" ]; then
        echo " WARNING: filesystem $i is $used %full; $AVAIL available."  >> $THEFILE
    fi
done

if [ -s $THEFILE ]; then
   cat $THEFILE|mail -s "chk_disk_space" $RCPT
fi

rm $THEFILE
