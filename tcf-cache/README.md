# tcf-cache — TCF 공통 EhCache 모듈

Spring Cache + EhCache 3(JCache) 기반 캐시 환경을 제공합니다.

## 의존성 추가

```gradle
dependencies {
    implementation project(':tcf-cache')
}
```

## 기본 설정

`tcf-cache.jar`에 포함된 기본값:

```yaml
spring:
  cache:
    type: jcache
    jcache:
      config: classpath:ehcache.xml

nsight:
  tcf:
    cache:
      enabled: true
```

비활성화:

```yaml
nsight:
  tcf:
    cache:
      enabled: false
```

## 캐시 영역 (ehcache.xml)

| alias | 용도 | TTL |
|-------|------|-----|
| `commonCode` | 공통코드 | 30분 |
| `serviceCatalog` | ServiceId 카탈로그 | 60분 |
| `sessionRegion` | 세션/리전 | 10분 |

상수: `com.nh.nsight.tcf.cache.support.TcfCacheNames`

## 사용 예

```java
@Cacheable(cacheNames = TcfCacheNames.COMMON_CODE, key = "#codeGroup")
public List<Map<String, Object>> findByGroup(String codeGroup) { ... }

@CacheEvict(cacheNames = TcfCacheNames.COMMON_CODE, allEntries = true)
public void save(...) { ... }
```

프로그램 방식 제어: `TcfCacheSupport.evict()`, `evictAll()`, `snapshot()`
