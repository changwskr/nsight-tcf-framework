package nhnis.eos.co.a.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface eoscoa0150DAO {
    Map<String, Object> selectLatest(Map<String, Object> param);
    List<Map<String, Object>> selectScores(Map<String, Object> param);
    List<Map<String, Object>> selectHistory(Map<String, Object> param);
    int insertAssess(Map<String, Object> param);
    int insertScore(Map<String, Object> param);
    int updateResourceGrade(Map<String, Object> param);
    String selectPolicyVal(Map<String, Object> param);
    int existsResource(Map<String, Object> param);
}
