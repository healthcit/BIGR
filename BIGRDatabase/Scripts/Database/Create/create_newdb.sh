#  ***********************************************************
#  * File:  create_newdb.sh
#  * Author:  Dean Marsh
#  * Date:  12/02/99
#  *
#  * Purpose:   This script runs most of the administrative
#  * .sql scripts to create the database catalog as well as
#  * controlling the run position of the creation scripts.
#  * This script is designed to be run with the create_newdb.sh
#  * script and should be edited appropriately if run independantly.
#  *
#  * Comments:
#  *
#  ***********************************************************


#  * Start the <sid> instance (ORACLE_SID here must be set to <sid>).
#  *

export ORACLE_SID=LEXPROD1

cd $ORACLE_BASE/admin/LEXPROD1/create
$ORACLE_HOME/bin/sqlplus /nolog <<!
spool $ORACLE_BASE/admin/LEXPROD1/create/crdb1.log
connect internal
startup nomount pfile=$ORACLE_BASE/admin/LEXPROD1/pfile/initLEXPROD1.ora
@$ORACLE_BASE/admin/LEXPROD1/create/crdb1.sql
spool off
exit
!

#  * Run script catalog.sql to install data dictionary views.
#  *

cd $ORACLE_BASE/admin/LEXPROD1/create
$ORACLE_HOME/bin/sqlplus /nolog <<!
spool $ORACLE_BASE/admin/LEXPROD1/create/catalog.log
connect internal
alter database mount;
alter database open;
@$ORACLE_HOME/rdbms/admin/catalog.sql
spool off
exit
!


#  * Run script crdb2.sql to create all Oracle-use tablespaces.
#  *

cd $ORACLE_BASE/admin/LEXPROD1/create
$ORACLE_HOME/bin/sqlplus /nolog  <<!
spool $ORACLE_BASE/admin/LEXPROD1/create/crdb2.log
connect internal
@$ORACLE_BASE/admin/LEXPROD1/create/crdb2.sql
spool off
exit
!

#  * Run catproc.sql
#  *

cd $ORACLE_HOME/rdbms/admin
$ORACLE_HOME/bin/sqlplus /nolog  <<!
connect internal
spool $ORACLE_BASE/admin/LEXPROD1/create/catproc.log
@$ORACLE_HOME/rdbms/admin/catproc.sql
spool off
exit
!

#  * Run catblock.sql
#  *

cd $ORACLE_HOME/rdbms/admin
$ORACLE_HOME/bin/sqlplus /nolog  <<!
connect internal
spool $ORACLE_BASE/admin/LEXPROD1/create/catblock.log
@$ORACLE_HOME/rdbms/admin/catblock.sql
spool off
exit
!

#  * Run catexp.sql
#  *

cd $ORACLE_HOME/rdbms/admin
$ORACLE_HOME/bin/sqlplus /nolog  <<!
connect internal
spool $ORACLE_BASE/admin/LEXPROD1/create/catexp.log
@$ORACLE_HOME/rdbms/admin/catexp.sql
spool off
exit
!

#  * Run caths.sql
#  *

cd $ORACLE_HOME/rdbms/admin
$ORACLE_HOME/bin/sqlplus /nolog <<!
connect internal
spool $ORACLE_BASE/admin/LEXPROD1/create/caths.log
@$ORACLE_HOME/rdbms/admin/caths.sql
spool off
exit
!


#  * Run catdbsyn.sql
#  *

cd $ORACLE_HOME/rdbms/admin
$ORACLE_HOME/bin/sqlplus system/manager <<!
spool $ORACLE_BASE/admin/LEXPROD1/create/catdbsyn.log
@$ORACLE_HOME/rdbms/admin/catdbsyn.sql
spool off
exit
!

#  * Run pupbld.sql
#  *

cd $ORACLE_HOME/sqlplus/admin
$ORACLE_HOME/bin/sqlplus system/manager <<!
spool $ORACLE_BASE/admin/LEXPROD1/create/pupbld.log
@$ORACLE_HOME/sqlplus/admin/pupbld.sql
spool off
exit
!

#  * Run crdb3.sql to create application tablespaces.
#  *

cd $ORACLE_BASE/admin/LEXPROD1/create
$ORACLE_HOME/bin/sqlplus /nolog <<!
spool $ORACLE_BASE/admin/LEXPROD1/create/crdb3.log
connect internal
@$ORACLE_BASE/admin/LEXPROD1/create/crdb3.sql
spool off
exit
!

#  * Create Oracle help tables.
#  *

cd $ORACLE_HOME/sqlplus/admin/help
$ORACLE_HOME/bin/sqlplus system/manager << !
@$ORACLE_HOME/sqlplus/admin/help/helpbld.sql helpus.sql
exit
!

#  * Turn archiving on.
#  *

#$ORACLE_HOME/bin/sqlplus /nolog << !
#connect internal
#shutdown normal;
#startup mount pfile='$ORACLE_BASE/admin/LEXPROD1/pfile/initLEXPROD1.ora'
#alter database archivelog;
#alter database open;
#alter system archive log start;
#exit
#!

#  * Edit the init.ora file to enable automatic start of redo logs
#  * upon next instance restart.

cd $ORACLE_BASE/admin/LEXPROD1/pfile
ed - init"LEXPROD1".ora << eof!
   g/# %%/s// /g
   w
   q
eof!

#  * Start the listener.
#  *

#lsnrctl start listener
