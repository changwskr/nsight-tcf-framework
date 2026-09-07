package nhnis.mg.by.u.persistence.dao;

import nhnis.mg.by.config.RDWMapper;

import java.util.Map;

/**
 * mgbyu1000 Profile RDW Mapper Interface.
 *
 * <p>Mapper namespace는 이 인터페이스 FQCN과 완전히 일치해야 한다.</p>
 */
@RDWMapper
public interface mgbyu1000DAO {

    Map<String, Object> mgbyu1000S0_S0(Map<String, Object> input) throws Exception;

    int mgbyu1000C0_exists(Map<String, Object> input) throws Exception;

    int mgbyu1000C0_C0(Map<String, Object> input) throws Exception;

    int mgbyu1000U0_U0(Map<String, Object> input) throws Exception;
}
