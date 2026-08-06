package nhnis.mk.co.a.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.mk.co.a.dao.mkpca5530Dao;
import nhnis.mk.co.a.dto.mkpca5530DtoIn;
import nhnis.mk.co.a.dto.mkpca5530DtoOut;
import nhnis.mk.co.a.dto.mkpca5530ListResponseDto;
import nhnis.mk.co.common.MkCoTxLog;

/**
 * mkpca5530 안내항목 목록 조회 서비스.
 *
 * <p>운영 로그: {@code ▶▶▶▶▶▶▶ mkpca5530S0 Service Start!} /
 * {@code ◀◀◀◀◀◀◀ mkpca5530S0 Service End! - Total: n}
 */
@Service
@Transactional(readOnly = true)
public class mkpca5530Service {

    private static final Logger log = LoggerFactory.getLogger(mkpca5530Service.class);

    private final mkpca5530Dao dao;

    public mkpca5530Service(mkpca5530Dao dao) {
        this.dao = dao;
    }

    public mkpca5530ListResponseDto mkpca5530S0(mkpca5530DtoIn in) {
        MkCoTxLog.serviceStart(log, "mkpca5530S0");

        mkpca5530DtoIn param = new mkpca5530DtoIn();
        param.setTrtBrc(in == null ? null : trimToNull(in.getTrtBrc()));
        param.setBasDt(in == null ? null : trimToNull(in.getBasDt()));

        int pageNo = in == null || in.getPageNo() == null || in.getPageNo() <= 0 ? 1 : in.getPageNo();
        int pageSize = in == null || in.getPageSize() == null || in.getPageSize() <= 0 ? 20 : in.getPageSize();
        pageSize = Math.min(pageSize, 100);
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);
        param.setOffset((pageNo - 1) * pageSize);

        long totalCount = dao.mkpca5530S0_S0_count(param);
        List<mkpca5530DtoOut> records = dao.mkpca5530S0_S0(param);

        mkpca5530ListResponseDto response = new mkpca5530ListResponseDto();
        response.setRecords(records);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalCount(totalCount);
        response.setTotalPages((int) ((totalCount + pageSize - 1) / pageSize));

        MkCoTxLog.serviceEnd(log, "mkpca5530S0", totalCount);
        return response;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
