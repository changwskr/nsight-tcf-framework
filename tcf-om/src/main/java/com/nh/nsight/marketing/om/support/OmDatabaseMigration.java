package com.nh.nsight.marketing.om.support;

import com.nh.nsight.tcf.util.DateTimeUtil;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class OmDatabaseMigration implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(OmDatabaseMigration.class);

    private final JdbcTemplate jdbcTemplate;

    public OmDatabaseMigration(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        jdbcTemplate.execute("ALTER TABLE OM_USER ADD COLUMN IF NOT EXISTS PASSWORD_HASH VARCHAR(200)");
        ensureUdFileMetaTable();
        jdbcTemplate.update("""
                MERGE INTO OM_BATCH_JOB (JOB_ID, JOB_NAME, BUSINESS_CODE, CRON_EXPR, USE_YN, DESCRIPTION) KEY (JOB_ID)
                VALUES (?, ?, ?, ?, ?, ?)
                """,
                "BAT-OM-002", "OM 세션 정리", "OM", "*/10 * * * * *", "Y",
                "SPRING_SESSION 만료 세션 10초 주기 정리");
        jdbcTemplate.update("""
                MERGE INTO OM_BATCH_JOB (JOB_ID, JOB_NAME, BUSINESS_CODE, CRON_EXPR, USE_YN, DESCRIPTION) KEY (JOB_ID)
                VALUES (?, ?, ?, ?, ?, ?)
                """,
                "BAT-BATCH-001", "대시보드 AP 상태 수집", "BATCH", "0 */5 * * * *", "Y",
                "Actuator AP CPU/Heap/Thread → OM_AP_STATUS (tcf-batch)");
        jdbcTemplate.update("""
                MERGE INTO OM_BATCH_JOB (JOB_ID, JOB_NAME, BUSINESS_CODE, CRON_EXPR, USE_YN, DESCRIPTION) KEY (JOB_ID)
                VALUES (?, ?, ?, ?, ?, ?)
                """,
                "BAT-BATCH-002", "대시보드 DB 상태 수집", "BATCH", "30 */5 * * * *", "Y",
                "Actuator/JDBC DB Pool → OM_DB_STATUS (tcf-batch)");
        jdbcTemplate.update("""
                MERGE INTO OM_BATCH_JOB (JOB_ID, JOB_NAME, BUSINESS_CODE, CRON_EXPR, USE_YN, DESCRIPTION) KEY (JOB_ID)
                VALUES (?, ?, ?, ?, ?, ?)
                """,
                "BAT-BATCH-003", "대시보드 세션 현황 수집", "BATCH", "45 */5 * * * *", "Y",
                "Spring Session/Actuator → OM_SESSION_STATUS (tcf-batch)");
        jdbcTemplate.update("""
                MERGE INTO OM_BATCH_JOB (JOB_ID, JOB_NAME, BUSINESS_CODE, CRON_EXPR, USE_YN, DESCRIPTION) KEY (JOB_ID)
                VALUES (?, ?, ?, ?, ?, ?)
                """,
                "BAT-BATCH-004", "대시보드 배포 현황 수집", "BATCH", "55 */5 * * * *", "Y",
                "Actuator/HTTP 배포 상태 → OM_DEPLOY_STATUS (tcf-batch)");
        ensureSessionStatusTable();
        removeLegacyApSeedApStatus();
        removeEmptySessionStatus();
        removeEmptyDbStatus();
        removeEmptyDeployStatus();
        jdbcTemplate.update("""
                MERGE INTO OM_MENU (MENU_ID, MENU_NAME, MENU_URL, PARENT_MENU_ID, SORT_ORDER, USE_YN) KEY (MENU_ID)
                VALUES (?, ?, ?, NULL, ?, ?)
                """, "OM_FIL", "파일 관리", "/om/admin/file-management.html", 10, "Y");
        jdbcTemplate.update("""
                MERGE INTO OM_SERVICE_CATALOG (CATALOG_ID, BUSINESS_CODE, SERVICE_ID, TRANSACTION_CODE,
                                               PROCESSING_TYPE, HANDLER_CLASS, AUTH_CODE, AUDIT_YN,
                                               TIMEOUT_SEC, USE_YN, DESCRIPTION)
                KEY (CATALOG_ID)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """,
                "CAT-036", "OM", "OM.TransactionLog.deleteAll", "OM-TXL-0002", "DELETE",
                "OmTransactionLogDeleteAllHandler", "ROLE_OM_TXL", "Y", 30, "Y", "거래로그 전체 삭제");
        mergeServiceCatalog("CAT-037", "OM.ServiceCatalog.save", "OM-SVC-0002", "UPDATE",
                "OmServiceCatalogSaveHandler", "ServiceId 등록");
        mergeServiceCatalog("CAT-038", "OM.ServiceCatalog.detail", "OM-SVC-0003", "INQUIRY",
                "OmServiceCatalogDetailHandler", "ServiceId 상세");
        mergeServiceCatalog("CAT-039", "OM.ServiceCatalog.update", "OM-SVC-0004", "UPDATE",
                "OmServiceCatalogUpdateHandler", "ServiceId 수정");
        mergeServiceCatalog("CAT-040", "OM.ServiceCatalog.delete", "OM-SVC-0005", "DELETE",
                "OmServiceCatalogDeleteHandler", "ServiceId 삭제");
        mergeServiceCatalog("CAT-041", "OM.ErrorCode.detail", "OM-ERR-0003", "INQUIRY",
                "OmErrorCodeDetailHandler", "오류코드 상세");
        mergeServiceCatalog("CAT-042", "OM.ErrorCode.update", "OM-ERR-0004", "UPDATE",
                "OmErrorCodeUpdateHandler", "오류코드 수정");
        mergeServiceCatalog("CAT-043", "OM.ErrorCode.delete", "OM-ERR-0005", "DELETE",
                "OmErrorCodeDeleteHandler", "오류코드 삭제");
        seedAuthCodeCommonCodes();
        seedCacheNameCommonCodes();
        repairCorruptedUtf8SeedData();
        log.debug("OM schema migration applied.");
    }

    /** data.sql 이 MS949 등으로 적재된 H2 파일의 한글 깨짐을 Java UTF-16 문자열로 복구한다. */
    private void repairCorruptedUtf8SeedData() {
        Map<String, String> catalogDescriptions = Map.ofEntries(
                Map.entry("CAT-001", "SV 샘플 조회"),
                Map.entry("CAT-002", "고객요약 조회"),
                Map.entry("CAT-003", "운영 대시보드"),
                Map.entry("CAT-004", "거래로그 조회"),
                Map.entry("CAT-005", "ServiceId 카탈로그"),
                Map.entry("CAT-006", "사용자 조회"),
                Map.entry("CAT-007", "메뉴 조회"),
                Map.entry("CAT-008", "권한그룹 조회"),
                Map.entry("CAT-009", "감사로그 조회"),
                Map.entry("CAT-010", "오류코드 조회"),
                Map.entry("CAT-011", "배치/스케줄 조회"),
                Map.entry("CAT-012", "Health Check 조회"),
                Map.entry("CAT-013", "환경설정 조회"),
                Map.entry("CAT-014", "파일 다운로드 이력"),
                Map.entry("CAT-015", "공통코드 목록 조회"),
                Map.entry("CAT-016", "공통코드 등록"),
                Map.entry("CAT-017", "공통코드 단건 조회"),
                Map.entry("CAT-018", "공통코드 수정"),
                Map.entry("CAT-019", "공통코드 삭제"),
                Map.entry("CAT-020", "오류코드 등록"),
                Map.entry("CAT-041", "오류코드 상세"),
                Map.entry("CAT-042", "오류코드 수정"),
                Map.entry("CAT-043", "오류코드 삭제"),
                Map.entry("CAT-021", "배치 재실행"),
                Map.entry("CAT-022", "기능권한 조회"),
                Map.entry("CAT-023", "데이터권한 조회"),
                Map.entry("CAT-024", "권한이력 조회"),
                Map.entry("CAT-025", "Cache 조회"),
                Map.entry("CAT-026", "Cache 삭제"),
                Map.entry("CAT-027", "OM 로그인"),
                Map.entry("CAT-028", "OM 로그아웃"),
                Map.entry("CAT-029", "OM 세션 조회"),
                Map.entry("CAT-030", "세션 목록 조회"),
                Map.entry("CAT-031", "세션 강제 종료"),
                Map.entry("CAT-032", "사용자 상세"),
                Map.entry("CAT-033", "사용자 등록"),
                Map.entry("CAT-034", "사용자 수정"),
                Map.entry("CAT-035", "사용자 삭제"),
                Map.entry("CAT-036", "거래로그 전체 삭제"),
                Map.entry("CAT-037", "ServiceId 등록"),
                Map.entry("CAT-038", "ServiceId 상세"),
                Map.entry("CAT-039", "ServiceId 수정"),
                Map.entry("CAT-040", "ServiceId 삭제")
        );
        catalogDescriptions.forEach((catalogId, description) ->
                jdbcTemplate.update(
                        "UPDATE OM_SERVICE_CATALOG SET DESCRIPTION = ? WHERE CATALOG_ID = ?",
                        description, catalogId));

        String ts = DateTimeUtil.nowKst();
        mergeCommonCode("BUSINESS_CODE", "SV", "Single View", 1, "통합고객 조회", ts);
        mergeCommonCode("BUSINESS_CODE", "CM", "Campaign", 2, "캠페인", ts);
        mergeCommonCode("BUSINESS_CODE", "OM", "Operation Mgmt", 3, "운영관리", ts);
        mergeCommonCode("BUSINESS_CODE", "BC", "Business Customer", 4, "기업고객", ts);
        mergeCommonCode("BUSINESS_CODE", "BD", "Business Data", 5, "마케팅 데이터", ts);
        mergeCommonCode("BUSINESS_CODE", "CC", "Contact Center", 6, "컨택센터", ts);
        mergeCommonCode("BUSINESS_CODE", "MG", "Message", 7, "메시지 발송", ts);
        mergeCommonCode("BUSINESS_CODE", "ET", "Enterprise", 8, "엔터프라이즈(미사용)", ts);
    }

    private void seedAuthCodeCommonCodes() {
        String ts = DateTimeUtil.nowKst();
        mergeCommonCode("AUTH_CODE", "ROLE_OM_AUTH", "OM 인증/권한", 1, "사용자·메뉴·세션·권한", ts);
        mergeCommonCode("AUTH_CODE", "ROLE_OM_SVC", "ServiceId 관리", 2, "서비스 카탈로그", ts);
        mergeCommonCode("AUTH_CODE", "ROLE_OM_DSH", "운영 대시보드", 3, "대시보드 조회", ts);
        mergeCommonCode("AUTH_CODE", "ROLE_OM_TXL", "거래로그", 4, "거래로그 조회/삭제", ts);
        mergeCommonCode("AUTH_CODE", "ROLE_OM_AUD", "감사로그", 5, "감사로그 조회", ts);
        mergeCommonCode("AUTH_CODE", "ROLE_OM_ERR", "오류코드", 6, "오류코드 관리", ts);
        mergeCommonCode("AUTH_CODE", "ROLE_OM_BAT", "배치", 7, "배치/스케줄", ts);
        mergeCommonCode("AUTH_CODE", "ROLE_OM_HLT", "Health Check", 8, "AP/DB 헬스", ts);
        mergeCommonCode("AUTH_CODE", "ROLE_OM_CFG", "환경설정", 9, "시스템 설정 조회", ts);
        mergeCommonCode("AUTH_CODE", "ROLE_OM_FIL", "파일", 10, "파일 다운로드 이력", ts);
        mergeCommonCode("AUTH_CODE", "ROLE_OM_CDC", "공통코드", 11, "공통코드 CRUD", ts);
        mergeCommonCode("AUTH_CODE", "ROLE_OM_CACHE", "Cache", 12, "캐시 조회/삭제", ts);
        mergeCommonCode("AUTH_CODE", "ROLE_SV_INQ", "SV 조회", 13, "Single View 조회", ts);
    }

    private void seedCacheNameCommonCodes() {
        String ts = DateTimeUtil.nowKst();
        mergeCommonCode("CACHE_NAME", "commonCode", "공통코드", 1, "공통코드 Cache (키=코드그룹)", ts);
        mergeCommonCode("CACHE_NAME", "serviceCatalog", "ServiceId 카탈로그", 2, "ServiceId 카탈로그 Cache", ts);
        mergeCommonCode("CACHE_NAME", "sessionRegion", "세션 영역", 3, "세션 Cache", ts);
    }

    private void mergeCommonCode(String codeGroup, String code, String codeName, int sortOrder,
                                 String description, String timestamp) {
        jdbcTemplate.update("""
                MERGE INTO OM_COMMON_CODE (CODE_GROUP, CODE, CODE_NAME, SORT_ORDER, USE_YN,
                                           DESCRIPTION, CREATED_AT, UPDATED_AT)
                KEY (CODE_GROUP, CODE)
                VALUES (?, ?, ?, ?, 'Y', ?, ?, ?)
                """,
                codeGroup, code, codeName, sortOrder, description, timestamp, timestamp);
    }

    private void mergeServiceCatalog(String catalogId, String serviceId, String transactionCode,
                                     String processingType, String handlerClass, String description) {
        jdbcTemplate.update("""
                MERGE INTO OM_SERVICE_CATALOG (CATALOG_ID, BUSINESS_CODE, SERVICE_ID, TRANSACTION_CODE,
                                               PROCESSING_TYPE, HANDLER_CLASS, AUTH_CODE, AUDIT_YN,
                                               TIMEOUT_SEC, USE_YN, DESCRIPTION)
                KEY (CATALOG_ID)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """,
                catalogId, "OM", serviceId, transactionCode, processingType,
                handlerClass, "ROLE_OM_SVC", "Y", 5, "Y", description);
    }

    private void ensureSessionStatusTable() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS OM_SESSION_STATUS (
                    SCOPE_ID VARCHAR(50) NOT NULL,
                    SCOPE_NAME VARCHAR(100),
                    ACTIVE_COUNT INT,
                    EXPIRED_COUNT INT,
                    TOTAL_COUNT INT,
                    UNIQUE_USER_COUNT INT,
                    HEALTH_STATUS VARCHAR(20),
                    CHECKED_AT VARCHAR(40),
                    PRIMARY KEY (SCOPE_ID)
                )
                """);
    }

    private void ensureUdFileMetaTable() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS UD_FILE_META (
                    FILE_ID VARCHAR(36) NOT NULL,
                    ORIGINAL_NAME VARCHAR(255) NOT NULL,
                    CONTENT_TYPE VARCHAR(100),
                    FILE_SIZE BIGINT NOT NULL,
                    DESCRIPTION VARCHAR(500),
                    UPLOAD_USER VARCHAR(50),
                    UPLOAD_TIME VARCHAR(40) NOT NULL,
                    BUSINESS_CODE VARCHAR(10) DEFAULT 'UD',
                    USE_YN CHAR(1) DEFAULT 'Y',
                    PRIMARY KEY (FILE_ID)
                )
                """);
        jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS UD_FILE_META_IX1 ON UD_FILE_META (UPLOAD_TIME)");
        jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS UD_FILE_META_IX2 ON UD_FILE_META (UPLOAD_USER)");
        jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS UD_FILE_META_IX3 ON UD_FILE_META (ORIGINAL_NAME)");
    }

    private void removeEmptyDbStatus() {
        int deleted = jdbcTemplate.update("""
                DELETE FROM OM_DB_STATUS
                 WHERE DB_ID IN ('RDW', 'ADW', 'SESSIONDB')
                    OR (
                        HEALTH_STATUS IN ('FAIL', 'DOWN')
                        AND COALESCE(POOL_USAGE_PCT, 0) = 0
                    )
                """);
        if (deleted > 0) {
            log.info("Removed empty or legacy DB status rows: {}", deleted);
        }
    }

    private void removeEmptyDeployStatus() {
        int deleted = jdbcTemplate.update("""
                DELETE FROM OM_DEPLOY_STATUS
                 WHERE HEALTH_STATUS IN ('FAIL', 'DOWN')
                    OR BUSINESS_CODE = 'ET'
                """);
        if (deleted > 0) {
            log.info("Removed empty or legacy deploy status rows: {}", deleted);
        }
    }

    private void removeEmptySessionStatus() {
        int deleted = jdbcTemplate.update("""
                DELETE FROM OM_SESSION_STATUS
                 WHERE HEALTH_STATUS IN ('FAIL', 'DOWN')
                   AND COALESCE(ACTIVE_COUNT, 0) = 0
                   AND COALESCE(EXPIRED_COUNT, 0) = 0
                   AND COALESCE(TOTAL_COUNT, 0) = 0
                   AND COALESCE(UNIQUE_USER_COUNT, 0) = 0
                """);
        if (deleted > 0) {
            log.info("Removed empty session status rows: {}", deleted);
        }
    }

    private void removeLegacyApSeedApStatus() {
        int deleted = jdbcTemplate.update("""
                DELETE FROM OM_AP_STATUS
                 WHERE AP_ID IN ('ap01', 'ap02', 'om-local')
                    OR (
                        HEALTH_STATUS IN ('FAIL', 'DOWN')
                        AND COALESCE(CPU_USAGE_PCT, 0) = 0
                        AND COALESCE(HEAP_USAGE_PCT, 0) = 0
                        AND COALESCE(THREAD_COUNT, 0) = 0
                    )
                """);
        if (deleted > 0) {
            log.info("Removed empty or legacy AP status rows: {}", deleted);
        }
    }

}
