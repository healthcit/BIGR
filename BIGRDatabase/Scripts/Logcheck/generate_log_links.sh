#!/usr/bin/ksh

#################################################################################################################
#	File:		generate_log_links.sh
#	Author:		Dean Marsh
#	Date:		11/06/2002
#
#	Purpose:	In order to take a list of files, insert links for the files into an email, then email 
#			that output.
#
##################################################################################################################

export LOG_DIR=/u01/app/oracle/scripts/log

cd ${LOG_DIR}

/usr/local/bin/scp oracle@172.16.1.3:/u01/app/oracle/scripts/log/GENPROD1*html ./ > /dev/null

for FILE in *html
do
  cat >> /tmp/DB_logs_url.txt <<!
http://healall.ardais.com/logs/${FILE}
!
  cp ${FILE} /opt/IBMHTTPD/htdocs/en_US/logs/${FILE}
done

/usr/bin/mailx -s "Database logs for ${DATE} " -v dba@healthcit.com < /tmp/DB_logs_url.txt > /dev/null

rm /tmp/DB_logs_url.txt
rm ${LOG_DIR}/*html
rm ${LOG_DIR}/*msglog
rm ${LOG_DIR}/*log
