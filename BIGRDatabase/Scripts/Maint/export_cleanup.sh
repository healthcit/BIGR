#!/usr/bin/ksh

export ORACLE_HOME=/u01/app/oracle/product/9.2.0
export SCRIPT_LOC=/u01/app/oracle/scripts
export BACKUP_LOC=/u05/backup/LEXPROD1
export FILE_DATE=`date '+%m%d%y'`

/usr/local/bin/scp oracle@172.16.1.3:/u01/app/oracle/admin/GENPROD1/exp/GENPROD1_export_${FILE_DATE}.dmp ${BACKUP_LOC} >> /dev/null

if [ -f "${BACKUP_LOC}/GENPROD1_export_${FILE_DATE}.dmp" ] && [ -f "${BACKUP_LOC}/LEXPROD1_export_${FILE_DATE}.dmp" ]
then
   /usr/local/bin/sftp oracle@172.16.1.3 << EOF
   rm /u01/app/oracle/admin/GENPROD1/exp/GENPROD1_export_${FILE_DATE}.dmp
   bye
EOF
   /usr/bin/mailx -s "Nightly production database export status - both exports completed successfully." -v sysadmin@healthcit.com
else
   /usr/bin/mailx -s "Nightly production database export status - one or more exports failed." -v sysadmin@healthcit.com
fi

