package nhnis.mk.co.a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import nhnis.mk.co.a.dto.mkpca8888DtoIn;
import nhnis.mk.co.a.dto.mkpca8888DtoOut;

/** 영업팁 실적 CRUD DAO. */
@Mapper
public interface mkpca8888Dao {

    List<mkpca8888DtoOut> mkpca8888S0_S0(mkpca8888DtoIn param);
    int mkpca8888S0_S0_count(mkpca8888DtoIn param);
    mkpca8888DtoOut mkpca8888S0_S1(mkpca8888DtoIn param);
    int mkpca8888I0_I0(mkpca8888DtoIn param);
    int mkpca8888U0_U0(mkpca8888DtoIn param);
    int mkpca8888D0_D0(mkpca8888DtoIn param);
}
