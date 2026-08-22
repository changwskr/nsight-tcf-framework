package nhnis.eos.co.a.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface eoscoa0141DAO {
    List<Map<String, Object>> listGroups();
    List<Map<String, Object>> listCodes(Map<String, Object> param);
    int insertGroup(Map<String, Object> param);
    int insertCode(Map<String, Object> param);
    int updateCode(Map<String, Object> param);
    List<Map<String, Object>> listPolicies(Map<String, Object> param);
    int insertPolicy(Map<String, Object> param);
    int closePolicy(Map<String, Object> param);
}
