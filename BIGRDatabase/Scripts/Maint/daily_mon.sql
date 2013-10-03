set pages 60
--spool daily_stats.lst
col total_mb format 99999.99
col free_mb format 99999.99
col largest_free format 99999.99
col pct_avail format 999.99 heading "% Avail"

prompt ***********************************************************
prompt *  This section details the minimum, maximum, and total 
prompt *  amount of free space available per tablespace as well 
prompt *  as the number of pieces this free space is in. 
prompt ***********************************************************

select a.tablespace_name, sum(a.bytes)/1048576 total_mb,
b.tot_free/1048576 free_mb, b.max_free/1048576 largest_free,
100*b.tot_free/sum(a.bytes) pct_avail,
case
   when b.tot_free/sum(a.bytes) < .1 then '|********* |'
   when b.tot_free/sum(a.bytes) < .2 then '|********  |'
   when b.tot_free/sum(a.bytes) < .3 then '|*******   |'
   when b.tot_free/sum(a.bytes) < .4 then '|******    |'
   when b.tot_free/sum(a.bytes) < .5 then '|*****     |'
   when b.tot_free/sum(a.bytes) < .6 then '|****      |'
   when b.tot_free/sum(a.bytes) < .7 then '|***       |'
   when b.tot_free/sum(a.bytes) < .8 then '|**        |'
   when b.tot_free/sum(a.bytes) < .9 then '|*         |'
   else '|          |'
end pct_used
from dba_data_files a,
(select tablespace_name, sum(bytes) tot_free, max(bytes) max_free
from dba_free_space
group by tablespace_name) b
where a.tablespace_name = b.tablespace_name (+)
group by a.tablespace_name, b.tot_free, b.max_free
order by pct_used desc;

clear columns

prompt *********************************************************
prompt *  This section gives us backup status for tablespaces. 
prompt *********************************************************

select * from v$backup_set where completion_time > sysdate - 1;

prompt ********************************************************
prompt *  This section tells us any locks that are being held.
prompt *  Check the REQUEST column for values other than 0.  
prompt ********************************************************

select * from v$lock where request != 0;

prompt *********************************************************
prompt *  This section gives statistics on rollback segments.  
prompt *********************************************************

col segment_name format a10

select a.segment_name, a.status,
b.rssize, b.extends, b.shrinks, b.wraps
from dba_rollback_segs a, v$rollstat b
where a.segment_id = b.usn
order by a.segment_name;

clear columns

prompt *********************************************************
prompt *  Objects below have a next_extent value higher than   
prompt *  than the largest available piece of free space in   
prompt *  the tablespace.  THIS IS NOT A GOOD THING!!!!!     
prompt *********************************************************

column tablespace_name format a30
column segment_name format a30

select tablespace_name, segment_name
from dba_segments a
where next_extent >
(select max(bytes) from dba_free_space b
where a.tablespace_name = b.tablespace_name);

clear columns
--spool off
