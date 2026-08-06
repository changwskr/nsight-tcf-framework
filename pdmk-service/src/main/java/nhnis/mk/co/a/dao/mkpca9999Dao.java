package nhnis.mk.co.a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import nhnis.mk.co.a.dto.mkpca9999DtoIn;
import nhnis.mk.co.a.dto.mkpca9999DtoOut;

/**
 * 영업팁 실적(TB_CR_AH_SALES_TIP_RACT) 조회 DAO.
 *
 * <p>
 * 인터페이스 FQCN이 rdw.mk.co.a/mkpca9999-ORA.xml의 namespace와 일치해야 하므로
 * 클래스명은 전문 ID 표기를 그대로 따른다.
 */
@Mapper
public interface mkpca9999Dao {

    /** 영업팁 실적 목록 조회. */
    List<mkpca9999DtoOut> mkpca9999S0_S0(mkpca9999DtoIn param);

    /** 영업팁 실적 목록 전체 건수 조회. */
    int mkpca9999S0_S0_count(mkpca9999DtoIn param);

    /** 영업팁 실적 단건 조회. */
    mkpca9999DtoOut mkpca9999S0_S1(mkpca9999DtoIn param);
}
