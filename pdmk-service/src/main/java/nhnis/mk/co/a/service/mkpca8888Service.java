package nhnis.mk.co.a.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.fw.exception.BizException;
import nhnis.mk.co.a.dao.mkpca8888Dao;
import nhnis.mk.co.a.dto.mkpca8888DtoIn;
import nhnis.mk.co.a.dto.mkpca8888DtoOut;
import nhnis.mk.co.a.dto.mkpca8888ListResponseDto;
import nhnis.mk.co.common.MkCoTxLog;

/** 영업팁 실적 CRUD 서비스 (PDMK). */
@Service
@Transactional(readOnly = true)
public class mkpca8888Service {

    private static final Logger log = LoggerFactory.getLogger(mkpca8888Service.class);
    private static final String CODE_REQUIRED = "FW0001";
    private static final String CODE_NOT_FOUND = "MP0404";
    private static final String CODE_DUPLICATE = "MP0409";

    private final mkpca8888Dao dao;

    public mkpca8888Service(mkpca8888Dao dao) {
        this.dao = dao;
    }

    public mkpca8888ListResponseDto mkpca8888S0(mkpca8888DtoIn in) {
        MkCoTxLog.serviceStart(log, "mkpca8888S0");
        mkpca8888DtoIn param = new mkpca8888DtoIn();
        param.setSalzTipKdc(in == null ? null : trimToNull(in.getSalzTipKdc()));
        int pageNo = in == null || in.getPageNo() == null || in.getPageNo() <= 0 ? 1 : in.getPageNo();
        int pageSize = in == null || in.getPageSize() == null || in.getPageSize() <= 0 ? 20 : in.getPageSize();
        pageSize = Math.min(pageSize, 100);
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);
        param.setOffset((pageNo - 1) * pageSize);

        long totalCount = dao.mkpca8888S0_S0_count(param);
        List<mkpca8888DtoOut> records = dao.mkpca8888S0_S0(param);
        mkpca8888ListResponseDto response = new mkpca8888ListResponseDto();
        response.setRecords(records);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalCount(totalCount);
        response.setTotalPages((int) ((totalCount + pageSize - 1) / pageSize));
        MkCoTxLog.serviceEnd(log, "mkpca8888S0", totalCount);
        return response;
    }

    public mkpca8888DtoOut mkpca8888S1(mkpca8888DtoIn in) {
        MkCoTxLog.serviceStart(log, "mkpca8888S1");
        mkpca8888DtoOut result = dao.mkpca8888S0_S1(normalizedKey(in));
        if (result == null) {
            throw new BizException(CODE_NOT_FOUND);
        }
        MkCoTxLog.serviceEnd(log, "mkpca8888S1", 1);
        return result;
    }

    @Transactional(timeout = 4)
    public void mkpca8888I0(mkpca8888DtoIn in) {
        MkCoTxLog.serviceStart(log, "mkpca8888I0");
        mkpca8888DtoIn param = normalizedWriteInput(in);
        if (dao.mkpca8888S0_S1(param) != null) {
            throw new BizException(CODE_DUPLICATE);
        }
        dao.mkpca8888I0_I0(param);
        MkCoTxLog.serviceEnd(log, "mkpca8888I0", 1);
    }

    @Transactional(timeout = 4)
    public void mkpca8888U0(mkpca8888DtoIn in) {
        MkCoTxLog.serviceStart(log, "mkpca8888U0");
        if (dao.mkpca8888U0_U0(normalizedWriteInput(in)) == 0) {
            throw new BizException(CODE_NOT_FOUND);
        }
        MkCoTxLog.serviceEnd(log, "mkpca8888U0", 1);
    }

    @Transactional(timeout = 4)
    public void mkpca8888D0(mkpca8888DtoIn in) {
        MkCoTxLog.serviceStart(log, "mkpca8888D0");
        if (dao.mkpca8888D0_D0(normalizedKey(in)) == 0) {
            throw new BizException(CODE_NOT_FOUND);
        }
        MkCoTxLog.serviceEnd(log, "mkpca8888D0", 1);
    }

    private mkpca8888DtoIn normalizedWriteInput(mkpca8888DtoIn in) {
        mkpca8888DtoIn param = normalizedKey(in);
        param.setPrtoCn(trimToNull(in.getPrtoCn()));
        param.setInqCn(trimToNull(in.getInqCn()));
        param.setInpCn(trimToNull(in.getInpCn()));
        return param;
    }

    private mkpca8888DtoIn normalizedKey(mkpca8888DtoIn in) {
        if (in == null) {
            throw new BizException(CODE_REQUIRED, "요청 Body");
        }
        mkpca8888DtoIn param = new mkpca8888DtoIn();
        param.setTrtBrc(require(in.getTrtBrc(), "취급점코드"));
        param.setTrtmnEno(require(in.getTrtmnEno(), "취급자사번"));
        param.setSalzTipKdc(require(in.getSalzTipKdc(), "영업팁종류코드"));
        param.setBasDt(require(in.getBasDt(), "기준일자"));
        return param;
    }

    private String require(String value, String fieldName) {
        String result = trimToNull(value);
        if (result == null) {
            throw new BizException(CODE_REQUIRED, fieldName);
        }
        return result;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
