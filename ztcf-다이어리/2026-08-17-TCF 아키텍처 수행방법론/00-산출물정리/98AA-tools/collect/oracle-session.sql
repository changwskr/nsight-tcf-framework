-- Requires appropriate read privileges on V$SESSION/V$TRANSACTION.
set pagesize 500 linesize 240 trimspool on feedback on
select systimestamp captured_at, status, username, machine, program, count(*) session_count
  from v$session
 where username is not null
 group by status, username, machine, program
 order by username, machine, program, status;

select systimestamp captured_at, count(*) active_tx_count
  from v$transaction;
