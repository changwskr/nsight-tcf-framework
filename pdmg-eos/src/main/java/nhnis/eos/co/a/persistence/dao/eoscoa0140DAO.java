package nhnis.eos.co.a.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface eoscoa0140DAO {
    List<Map<String, Object>> eoscoa0140S0_S0(Map<String, Object> param);
    int eoscoa0140S0_S0_count(Map<String, Object> param);
    String selectOpenLfcId(Map<String, Object> param);
    int closeLfc(Map<String, Object> param);
    int insertLfc(Map<String, Object> param);
    List<Map<String, Object>> listResourcesByVersion(Map<String, Object> param);
    int updateResourceStatus(Map<String, Object> param);
    String selectPolicyVal(Map<String, Object> param);
}
