package nhnis.mk.ui.application.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import nhnis.mk.ui.support.TransactionInfo;

/**
 * pdmk-service가 제공하는 거래 목록.
 *
 * <p>요청 Body는 {@code {"hdr_nhnis":{"sys_comm":{...}},"dto":{...}}} 형식이다.
 */
@Service
public class TransactionCatalog {

    private final ObjectMapper objectMapper;
    private final Map<String, TransactionInfo> transactions = new LinkedHashMap<>();

    public TransactionCatalog(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    void load() {
        register(new TransactionInfo(
                "mkcoa6666S0",
                "OM Service Catalog 목록",
                "mkcoa6666",
                "POST",
                "/mkcoa6666S0",
                "pdmk-om TB_OM_SVC_CATALOG 조회. 요건 §26 거래통제 기준정보.",
                readSample("mkcoa6666-list.json")));

        register(new TransactionInfo(
                "mkcoa6666I0",
                "OM Service Catalog 등록",
                "mkcoa6666",
                "POST",
                "/mkcoa6666I0",
                "serviceCode(rms_svc_c) 기준 카탈로그 등록.",
                readSample("mkcoa6666-insert.json")));

        register(new TransactionInfo(
                "mkcoa6666U0",
                "OM Service Catalog 수정",
                "mkcoa6666",
                "POST",
                "/mkcoa6666U0",
                "serviceCode 기준 카탈로그 수정.",
                readSample("mkcoa6666-update.json")));

        register(new TransactionInfo(
                "mkcoa6666D0",
                "OM Service Catalog 삭제",
                "mkcoa6666",
                "POST",
                "/mkcoa6666D0",
                "serviceCode 기준 카탈로그 삭제.",
                readSample("mkcoa6666-delete.json")));

        register(new TransactionInfo(
                "mkcoa6666E0",
                "OM sys_comm 거래통제 평가",
                "mkcoa6666",
                "POST",
                "/mkcoa6666E0",
                "sys_comm → ALLOW/REJECT/BLOCK (TCF-CTL-*). 관리 화면(/txcontrol) 시뮬레이션.",
                readSample("mkcoa6666-evaluate.json")));

        register(new TransactionInfo(
                "mkcoa6666S2",
                "OM Catalog 상태 집계",
                "mkcoa6666",
                "POST",
                "/mkcoa6666S2",
                "대시보드 Card용 NORMAL/MAINT/STOP/disabled 집계.",
                readSample("mkcoa6666-summary.json")));

        register(new TransactionInfo(
                "mkcoa6666S1",
                "OM 서비스별 통제 상세",
                "mkcoa6666",
                "POST",
                "/mkcoa6666S1",
                "02화면 기본정보+POLICY_JSON+런타임 요약.",
                readSample("mkcoa6666-detail.json")));

        register(new TransactionInfo(
                "mkcoa6666S3",
                "OM 최근 통제 결과",
                "mkcoa6666",
                "POST",
                "/mkcoa6666S3",
                "TB_OM_TX_CTRL_RESULT 조회.",
                readSample("mkcoa6666-results.json")));

        register(new TransactionInfo(
                "mkcoa6666U1",
                "OM Catalog 상태 변경",
                "mkcoa6666",
                "POST",
                "/mkcoa6666U1",
                "중지/점검/재개 (status·enabled·reason).",
                readSample("mkcoa6666-status.json")));

        register(new TransactionInfo(
                "mkcoa7777S0",
                "OM 이미지로그 목록 조회",
                "mkcoa7777",
                "POST",
                "/mkcoa7777S0",
                "pdmk-om TB_FW_IMAGE_LOG 조회(페이징). withinSeconds·예외여부 지원. 관리 화면(/imagelog)도 동일 API.",
                readSample("mkcoa7777-list.json")));

        register(new TransactionInfo(
                "mkcoa7777D0",
                "OM 이미지로그 삭제",
                "mkcoa7777",
                "POST",
                "/mkcoa7777D0",
                "pdmk-om TB_FW_IMAGE_LOG 삭제. dto.guidList 로 다건 삭제한다.",
                readSample("mkcoa7777-delete.json")));

        register(new TransactionInfo(
                "mkcoa8888S0",
                "이미지로그 목록 조회",
                "mkcoa8888",
                "POST",
                "/mkcoa8888S0",
                "pdmk-service TB_FW_IMAGE_LOG 조회(페이징).",
                readSample("mkcoa8888-list.json")));

        register(new TransactionInfo(
                "mkcoa8888D0",
                "이미지로그 삭제",
                "mkcoa8888",
                "POST",
                "/mkcoa8888D0",
                "pdmk-service TB_FW_IMAGE_LOG 삭제. dto.guidList 로 다건 삭제한다.",
                readSample("mkcoa8888-delete.json")));

        register(new TransactionInfo(
                "mkcoa5530S0",
                "안내항목 목록 조회",
                "mkcoa5530",
                "POST",
                "/mkcoa5530S0",
                "TB_MK_CO_A_5530 목록 조회(페이징).",
                readSample("mkcoa5530-list.json")));

        register(new TransactionInfo(
                "mkcoa9999S0",
                "영업팁 실적 목록 조회",
                "mkcoa9999",
                "POST",
                "/mkcoa9999S0",
                "TB_CR_AH_SALES_TIP_RACT 목록 조회. dto.salzTipKdc 를 비우면 전체를 조회한다.",
                readSample("mkcoa9999-list.json")));
    }

    public List<TransactionInfo> findAll() {
        return List.copyOf(transactions.values());
    }

    public TransactionInfo findById(String id) {
        TransactionInfo info = transactions.get(id);
        if (info == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "등록되지 않은 거래입니다: " + id);
        }
        return info;
    }

    private void register(TransactionInfo info) {
        transactions.put(info.id(), info);
    }

    private JsonNode readSample(String fileName) {
        ClassPathResource resource = new ClassPathResource("sample-requests/" + fileName);
        try (InputStream in = resource.getInputStream()) {
            return objectMapper.readTree(in);
        } catch (IOException e) {
            throw new IllegalStateException("샘플 전문을 읽지 못했습니다: " + fileName, e);
        }
    }
}
