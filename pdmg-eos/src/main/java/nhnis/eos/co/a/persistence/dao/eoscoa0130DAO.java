package nhnis.eos.co.a.persistence.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface eoscoa0130DAO {
    Map<String, Object> eoscoa0130S0_S0(Map<String, Object> param);

    Map<String, Object> selectCurrentLfc(Map<String, Object> param);

    int countOpenAction(Map<String, Object> param);

    int countActiveException(Map<String, Object> param);

    int countRisk(Map<String, Object> param);

    int countAction(Map<String, Object> param);

    int countException(Map<String, Object> param);

    int existsVersion(Map<String, Object> param);

    int eoscoa0130C0_C0(Map<String, Object> param);

    int eoscoa0130U0_U0(Map<String, Object> param);

    int eoscoa0130D0_D0(Map<String, Object> param);

    String selectPolicyVal(Map<String, Object> param);
}
