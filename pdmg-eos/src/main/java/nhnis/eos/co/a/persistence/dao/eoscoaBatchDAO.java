package nhnis.eos.co.a.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface eoscoaBatchDAO {

    List<Map<String, Object>> listActiveResourcesWithLfc();

    int updateResourceStatus(Map<String, Object> param);

    String selectPolicyVal(Map<String, Object> param);

    List<Map<String, Object>> listExpiredExceptions(Map<String, Object> param);

    int expireException(Map<String, Object> param);

    int clearResourceExceptionYn(Map<String, Object> param);

    int countActionOverdue(Map<String, Object> param);

    int countMonthlyCheckMiss(Map<String, Object> param);

    int insertBatchRun(Map<String, Object> param);

    int finishBatchRun(Map<String, Object> param);
}
