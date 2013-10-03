set lines 100 pages 40
column object_name format a30
column owner format a15
column object_type format a10
column POOL format a15


prompt ***** Free memory in shared pool *****

select * from v$sgastat
order by pool, name
/

prompt ***** Data dictionary cache hit ratio *****

select sum(gets) "Gets", sum(getmisses) "Misses",
(1-(sum(getmisses)/(sum(gets) + sum(getmisses)))) * 100 "HitRate"
from v$rowcache
/

prompt ***** Library cache hit ratio *****

select sum(pins) Executions, sum(pinhits) "Execution Hits",
((sum(pinhits) / sum(pins)) * 100) phitrat,
sum(reloads) Misses,
((sum(pins) / (sum(pins) + sum(reloads))) * 100) hitrat
from v$librarycache
/

prompt ***** Check hit ratio on DB block buffers *****

select (1-(sum(decode(name,'physical reads',value,0))/
(sum(decode(name,'db block gets',value,0)) +
(sum(decode(name,'consistent gets',value,0)))))) * 100
"Read Hit Ratio"
from v$sysstat
/

prompt ***** Check the number of cursors per session ****

select user_name, sid, count(*)
from v$open_cursor
group by user_name, sid
order by count(*) desc;

prompt ***** Check status of DB block buffers in x$bh *****

select decode(status,'free','FREE',
	 'xcur','EXCLUSIVE',
	 'scur','SHARED CURRENT',
	 'cr','CONSISTANT READ',
	 'read','BEING READ',
	 'mrec','MEDIA RECOVERY',
	 'irec','INSTANCE RECOVERY',
	 'OTHER') "CURRENT STATUS", 
count(*) "COUNT"
from v_$bh
group by status order by 2 desc
/

--select decode(state,0,'FREE',1,decode(lrba_seq, 0, 'AVAILABLE','BEING USED'),
--3, 'BEING USED',state) "BLOCK STATUS", count(*) from x$bh
--group by decode(state,0,'FREE',1,decode(lrba_seq, 0, 'AVAILABLE','BEING USED'),
--3, 'BEING USED',state)
--/

prompt ***** Utilization of buffers by pool - summary (SYS and SYSTEM not included) *****

select bpd.bp_name "POOL",
           bpd.bp_size "POOL SIZE",
           obj.owner,
           count(*) "NUM BLOCKS",
           round((count(*)/bpd.bp_size)*100,2) "PCT USAGE"
from sys.x$kcbwds wds,
         sys.x$kcbwbpd bpd,
         sys.x$bh bh,
	 (select owner, data_object_id
	  from sys.dba_objects
	  group by owner, data_object_id) obj
where bh.set_ds = wds.addr
and wds.set_id between bpd.bp_lo_sid and bpd.bp_hi_sid
and bpd.bp_size > 0
and bh.obj = obj.data_object_id
group by rollup(bpd.bp_name, bpd.bp_size, obj.owner)
/

prompt ***** Utilization of buffers by pool - detail (SYS and SYSTEM not included) *****

select bpd.bp_name "POOL",
           obj.owner,
           obj.object_type,
           obj.object_name,
           count(*) "NUM BLOCKS",
	   sum(bh.tch) "TOUCH"
from sys.x$kcbwds wds,
         sys.x$kcbwbpd bpd,
         sys.x$bh bh,
         sys.dba_objects obj
where bh.obj <> 2
and bh.set_ds = wds.addr
and wds.set_id between bpd.bp_lo_sid and bpd.bp_hi_sid
and bpd.bp_size > 0
and bh.obj = obj.data_object_id
and obj.owner not in ('SYS','SYSTEM')
and bh.tch > 10
group by bpd.bp_name, obj.owner, obj.object_type, obj.object_name
order by 1,6
/

prompt ***** Gross-level instance stats on PGA memory usage *****

--This view provides instance-level statistics on the PGA memory usage and the automatic PGA memory manager. 

SELECT * FROM V$PGASTAT;

prompt ***** Work areas executed in PGA memory size levels - detail *****

--This view shows the number of work areas executed with optimal memory size, one- pass memory size, 
--and multi-pass memory size since instance start-up. Statistics in this view are subdivided into buckets 
--that are defined by the optimal memory requirement of the work area. Each bucket is identified by a range of optimal  
--memory requirements specified by the values of the columns LOW_OPTIMAL_SIZE and HIGH_OPTIMAL_SIZE.  

SELECT LOW_OPTIMAL_SIZE/1024 low_kb,
	   (HIGH_OPTIMAL_SIZE+1)/1024 high_kb,        
	   optimal_executions, 
	   onepass_executions, 
	   multipasses_executions 
FROM   v$sql_workarea_histogram 
WHERE  total_executions != 0; 

prompt ***** PGA_TARGET_ADVICE statistics *****

--V$PGA_TARGET_ADVICE view predicts how the statistics cache hit percentage and  over allocation count in V$PGASTAT 
--will be impacted if you change the value of  the initialization parameter PGA_AGGREGATE_TARGET.  The following select 
--statement can be used to find this information   

SELECT round(PGA_TARGET_FOR_ESTIMATE/1024/1024) target_mb,
       ESTD_PGA_CACHE_HIT_PERCENTAGE cache_hit_perc,
	   ESTD_OVERALLOC_COUNT 
FROM   v$pga_target_advice; 

prompt *****************************END OF SGA STATS*************************************

