package nhnis.eos.co.a.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface eoscoa0160DAO {
    List<Map<String, Object>> listByResource(Map<String, Object> param);
    Map<String, Object> selectOne(Map<String, Object> param);
    int insert(Map<String, Object> param);
    int update(Map<String, Object> param);
    int updateStatus(Map<String, Object> param);
    int insertHist(Map<String, Object> param);
    int insertEvid(Map<String, Object> param);
}
