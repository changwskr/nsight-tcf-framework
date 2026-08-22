# NSIGHT Production Configuration Evidence Bundle Template

이 디렉터리를 Hostname 단위로 복제해서 사용한다.

```text
<ENV>-<HOSTNAME>/
├─ evidence-manifest.json
├─ apache/
│  ├─ httpd.conf
│  └─ conf.d/*.conf
├─ tomcat/
│  └─ <JVM_ID>/
│     ├─ conf/server.xml
│     └─ bin/setenv.sh
├─ app/
│  └─ <APPLICATION>/application-prod.yml
└─ runtime/
   ├─ ps-ef.txt
   ├─ catalina-base.txt
   └─ webapps.txt
```

## Acceptance rule

Production Evidence로 인정하려면 최소한 `environment + hostname + captured_at + source path + SHA-256 + evidence_class=PRODUCTION_RUNTIME`가 있어야 한다.

파일을 복사한 뒤 실제 파일에 대해 SHA-256을 계산하고 manifest에 기록한다. 설정 원문에 비밀번호/Private Key가 포함되어 있으면 무단 반출하지 말고 보안 절차에 따른 sanitized evidence 또는 내부 검증 방식으로 처리한다. Parser 결과는 민감 키 이름을 자동 Redaction한다.

## 실행

```bash
python 98M-tools/tools/nsight_config_ingest.py scan-dir <bundle-dir> \
  --json validation-result.json
```

Exit code `0`은 Evidence Contract를 만족함을 뜻한다. 이는 Architecture Gate PASS 자체를 뜻하지 않는다.
