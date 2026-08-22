package nhnis.eos.co.a.persistence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface eoscoa0100DAO {
    List<Map<String, Object>> eoscoa0100S0_S0(Map<String, Object> param);
}
