-- Replace TEST_TABLE and TEST_KEY only in an approved non-production fault-injection dataset.
-- Capture BEFORE, trigger the test, wait >= 2x online-timeout, then capture AFTER.
set pagesize 200 linesize 240 trimspool on
prompt === BEFORE/AFTER CONTROL QUERY TEMPLATE ===
select systimestamp captured_at, t.* from TEST_TABLE t where TEST_KEY = '&TEST_KEY';
