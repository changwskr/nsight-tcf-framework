package nhnis.eos.co.a.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface eoscoa0120DAO {
    List<Map<String, Object>> eoscoa0120S0_S0(Map<String, Object> param);

    int eoscoa0120S0_S0_count(Map<String, Object> param);
}
