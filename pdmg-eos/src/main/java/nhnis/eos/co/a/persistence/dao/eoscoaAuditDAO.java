package nhnis.eos.co.a.persistence.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface eoscoaAuditDAO {
    int insert(Map<String, Object> param);
}
