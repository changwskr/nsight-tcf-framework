package nhnis.eos.co.a.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface eoscoa0170DAO {
    List<Map<String, Object>> list(Map<String, Object> param);
    Map<String, Object> selectOne(Map<String, Object> param);
    int insert(Map<String, Object> param);
    int updateStatus(Map<String, Object> param);
    int insertAppr(Map<String, Object> param);
    int updateResourceExceptionYn(Map<String, Object> param);
    int insertMonthly(Map<String, Object> param);
    List<Map<String, Object>> listMonthly(Map<String, Object> param);
    List<Map<String, Object>> inbox(Map<String, Object> param);
}
