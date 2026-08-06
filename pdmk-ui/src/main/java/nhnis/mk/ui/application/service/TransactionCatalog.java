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
                "mkpca5530S0",
                "안내항목 목록 조회",
                "mkpca5530",
                "POST",
                "/api/mk/co/a/5530/list",
                "TB_MK_CO_A_5530 목록 조회. 로컬 H2 시드 3건 → Total: 3.",
                readSample("mkpca5530-list.json")));

        register(new TransactionInfo(
                "mkpca9999S0",
                "영업팁 실적 목록 조회",
                "mkpca9999",
                "POST",
                "/api/mk/co/a/9999/list",
                "TB_CR_AH_SALES_TIP_RACT 목록 조회. salzTipKdc를 비우면 전체를 조회한다.",
                readSample("mkpca9999-list.json")));

        register(new TransactionInfo(
                "mkpca9999S1",
                "영업팁 실적 단건 조회",
                "mkpca9999",
                "POST",
                "/api/mk/co/a/9999/detail",
                "PK(취급점·취급자·영업팁종류·기준일자) 4개로 단건 조회. 누락 시 FW0001을 반환한다.",
                readSample("mkpca9999-detail.json")));

        register(new TransactionInfo(
                "mkpca8888S0",
                "영업팁 실적 목록 조회 (CRUD)",
                "mkpca8888",
                "POST",
                "/api/mk/co/a/8888/list",
                "CRUD 프로그램 목록 조회. JWT enabled=true 이면 인증 필요.",
                readSample("mkpca8888-list.json")));

        register(new TransactionInfo(
                "mkpca8888S1",
                "영업팁 실적 단건 조회 (CRUD)",
                "mkpca8888",
                "POST",
                "/api/mk/co/a/8888/detail",
                "CRUD 프로그램 단건 조회.",
                readSample("mkpca8888-detail.json")));
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
