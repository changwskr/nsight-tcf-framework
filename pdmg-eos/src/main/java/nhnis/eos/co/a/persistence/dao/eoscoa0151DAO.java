package nhnis.eos.co.a.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface eoscoa0151DAO {
    List<Map<String, Object>> search(Map<String, Object> param);
    int count(Map<String, Object> param);
    Map<String, Object> detail(Map<String, Object> param);
}
