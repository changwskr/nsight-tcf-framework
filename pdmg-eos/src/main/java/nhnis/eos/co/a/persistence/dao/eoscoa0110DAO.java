package nhnis.eos.co.a.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface eoscoa0110DAO {
    int countTotal();
    int countByRisk(Map<String, Object> param);
    int countByStatusIn(Map<String, Object> param);
    int countExceptionNeed();
    int countActionInProgress();
    List<Map<String, Object>> topPriority(Map<String, Object> param);
    String selectKpiFormula();
}
