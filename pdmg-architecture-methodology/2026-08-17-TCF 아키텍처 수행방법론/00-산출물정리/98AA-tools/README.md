# NSIGHT Wave 3B Runtime Execution Automation

This package prepares and collects runtime evidence. It does **not** make a production run safe by itself and never promotes synthetic/reference output to runtime approval.

## Safety defaults

- Operator actions are `DRY_RUN` unless `EXECUTE=true`.
- `PROD` execution also requires `APPROVAL_TOKEN=APPROVED:<change-id>`.
- Environment-specific stop/start/failover commands are intentionally not hard-coded.
- Use only approved test-only ServiceIds/data for timeout fault injection.

## Load generation

JMeter is the canonical runnable template in this package. Gatling is provided as an equivalent reference template and must be compiled in the approved project environment.

```bash
export JMETER_HOME=/opt/jmeter
export BASE_URL=https://perf.example
export SERVICE_ID=MG.TEST.sample
export REQUEST_BODY_FILE=request.json
export TARGET_TPS=600
./load/jmeter/run-jmeter.sh
```

Then create a concrete Run bundle and ingest the JTL:

```bash
python3 tools/nsight_run_automation.py prepare-bundle --root ./evidence --run-id RUN-P600 --identity identity.json
python3 tools/nsight_run_automation.py ingest-jmeter --bundle ./evidence/RUN-P600 --jtl result.jtl --resource-metrics resource-metrics.json
python3 tools/nsight_runtime_evidence.py evaluate-bundle ./evidence/RUN-P600
```

## Metrics

- `collect/collect-host.sh`: OS snapshot
- `collect/collect-jvm.sh`: jcmd VM/heap/thread snapshot
- `collect/collect-micrometer.sh`: Spring Boot/Micrometer raw metrics
- `collect/oracle-session.sql`: Oracle session/transaction snapshot
- `collect/oracle-slow-sql.sql`: read-only slow SQL diagnostic query

## Failure/action runs

The action wrappers delegate to `operator-hook.sh`. They require `OPERATOR_COMMAND` from the authorized environment owner. They do not embed L4/Tomcat/GSLB destructive commands.
