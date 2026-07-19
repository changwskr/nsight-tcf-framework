# tcf-docker

NSIGHT TCF Framework Docker 자산 루트.  
모듈별 Docker 파일(`Dockerfile`, compose, 스크립트)을 이 아래에 둡니다.

## 구조

`settings.gradle` include 모듈과 동일한 디렉터리:

```
tcf-docker/
  tcf-util/
  tcf-core/
  tcf-web/
  tcf-eai/
  tcf-cache/
  tcf-ui/
  tcf-uj/
  tcf-batch/
  tcf-om/
  tcf-jwt/
  tcf-gateway/
  tcf-oc/
  tcf-help/
  ic-service/
  pc-service/
  ms-service/
  sv-service/
  pd-service/
  eb-service/
  ep-service/
  ss-service/
  mg-service/
  om-service/
```

## 참고

이미 각 모듈에 Docker가 있는 경우:

- `eb-service/scripts/docker/`
- `sv-service/scripts/docker/`
- `tcf-ui/scripts/docker/`

중앙 관리로 옮기거나 여기서 신규 모듈 Docker를 추가할 때 이 트리를 사용합니다.
