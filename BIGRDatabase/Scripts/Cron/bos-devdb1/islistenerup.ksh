#! /bin/ksh
# -----------------------------------------------------------------------
# Filename:   IsListenerUp.ksh
# Purpose:    Check if TNS background processes are started
# Author:     based on Frank Naude (frank@ibi.co.za)
#
# Modified:   11/07/06
#              for GulfStreamBio and cron
# Author:     Phil DiPrima 
# -----------------------------------------------------------------------

FAILED=0;
RC=$(ps -ef | egrep '/bin/tnslsnr' | egrep -v 'grep') 
if [ "${RC}" = "" ] ; then
 FAILED=1 
 HN=$(hostname)
 # Send email to pager
 echo "TNS is DOWN"| /bin/mailx -s "ALERT-${HN}" dba@healthcit.com
 # echo "TNS is DOWN"| /bin/mailx -s "ALERT-${HN}" 8664146937@skytel.com
fi 
