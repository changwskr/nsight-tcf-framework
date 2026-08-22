-- Read-only diagnostic query; tailor filters/privileges with the DBA.
set pagesize 500 linesize 260 trimspool on feedback on
select * from (
  select sql_id,
         executions,
         round(elapsed_time/1e6,3) elapsed_total_sec,
         round(case when executions=0 then null else elapsed_time/1e6/executions end,3) avg_elapsed_sec,
         substr(sql_text,1,160) sql_text
    from v$sql
   where executions > 0
   order by case when executions=0 then 0 else elapsed_time/executions end desc
) where rownum <= 50;
