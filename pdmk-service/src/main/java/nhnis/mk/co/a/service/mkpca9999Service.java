package nhnis.mk.co.a.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.fw.commons.log.PdmkTxLog;
import nhnis.fw.exception.BizException;
import nhnis.mk.co.a.dao.mkpca9999Dao;
import nhnis.mk.co.a.dto.mkpca9999DtoIn;
import nhnis.mk.co.a.dto.mkpca9999DtoOut;
import nhnis.mk.co.a.dto.mkpca9999ListResponseDto;

/** ??? ?? ?? ??? (PDMK). */
@Service
@Transactional(readOnly = true)
public class mkpca9999Service {

    private static final Logger log = LoggerFactory.getLogger(mkpca9999Service.class);
    private static final String CODE_REQUIRED = "FW0001";

    private final mkpca9999Dao dao;

    public mkpca9999Service(mkpca9999Dao dao) {
        this.dao = dao;
    }

    public mkpca9999ListResponseDto mkpca9999S0(mkpca9999DtoIn in) {
        PdmkTxLog.serviceStart(log, "mkpca9999S0");
        mkpca9999DtoIn param = new mkpca9999DtoIn();
        param.setSalzTipKdc(in == null ? null : trimToNull(in.getSalzTipKdc()));

        int pageNo = in == null || in.getPageNo() == null || in.getPageNo() <= 0 ? 1 : in.getPageNo();
        int pageSize = in == null || in.getPageSize() == null || in.getPageSize() <= 0 ? 20 : in.getPageSize();
        if (pageSize > 100) {
            pageSize = 100;
        }
        int offset = (pageNo - 1) * pageSize;
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);
        param.setOffset(offset);

        long totalCount = dao.mkpca9999S0_S0_count(param);
        List<mkpca9999DtoOut> records = dao.mkpca9999S0_S0(param);

        mkpca9999ListResponseDto response = new mkpca9999ListResponseDto();
        response.setRecords(records);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalCount(totalCount);
        response.setTotalPages((int) ((totalCount + pageSize - 1) / pageSize));
        PdmkTxLog.serviceEnd(log, "mkpca9999S0", totalCount);
        return response;
    }

    public mkpca9999DtoOut mkpca9999S1(mkpca9999DtoIn in) {
        PdmkTxLog.serviceStart(log, "mkpca9999S1");
        if (in == null) {
            throw new BizException(CODE_REQUIRED, "?? Body");
        }
        mkpca9999DtoIn param = new mkpca9999DtoIn();
        param.setTrtBrc(require(in.getTrtBrc(), "?????"));
        param.setTrtmnEno(require(in.getTrtmnEno(), "?????"));
        param.setSalzTipKdc(require(in.getSalzTipKdc(), "???????"));
        param.setBasDt(require(in.getBasDt(), "????"));

        mkpca9999DtoOut row = dao.mkpca9999S0_S1(param);
        if (row == null) {
            throw new BizException("FW0003", "mkpca9999S0_S1", "?? ?? ??");
        }
        PdmkTxLog.serviceEnd(log, "mkpca9999S1", 1);
        return row;
    }

    private String require(String value, String fieldName) {
        String trimmed = trimToNull(value);
        if (trimmed == null) {
            throw new BizException(CODE_REQUIRED, fieldName);
        }
        return trimmed;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
