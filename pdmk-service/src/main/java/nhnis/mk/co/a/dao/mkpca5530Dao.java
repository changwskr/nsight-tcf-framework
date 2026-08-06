package nhnis.mk.co.a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import nhnis.mk.co.a.dto.mkpca5530DtoIn;
import nhnis.mk.co.a.dto.mkpca5530DtoOut;

/**
 * mkpca5530 안내항목 조회 DAO.
 *
 * <p>인터페이스 FQCN이 {@code rdw.mk.co.a/mkpca5530-ORA.xml} namespace와 일치해야 한다.
 */
@Mapper
public interface mkpca5530Dao {

    List<mkpca5530DtoOut> mkpca5530S0_S0(mkpca5530DtoIn param);

    int mkpca5530S0_S0_count(mkpca5530DtoIn param);
}
