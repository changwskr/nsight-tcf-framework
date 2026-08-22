package nhnis.infra.in.a.persistence.dao;

import java.util.List;
import java.util.Map;
import nhnis.infra.in.a.config.RDWMapper;

@RDWMapper
public interface ifina2400DAO {
    List<Map<String, Object>> ifina2400S0_systems(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_domains(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_units(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_details(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_detailsBySystem(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_apps(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_groups(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_maps(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_assetsByGroup(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_networkByApp(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_assetsBySystem(Map<String, Object> input) throws Exception;
    Map<String, Object> ifina2400S0_asset(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_mw(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_mwBySystem(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_db(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_dbBySystem(Map<String, Object> input) throws Exception;
    Map<String, Object> ifina2400S0_session(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_bizAppMaps(Map<String, Object> input) throws Exception;
    List<Map<String, Object>> ifina2400S0_bizAppMapCounts(Map<String, Object> input) throws Exception;
    int ifina2400C0_exists(Map<String, Object> input) throws Exception;
    int ifina2400C0_insert(Map<String, Object> input) throws Exception;
    int ifina2400C0_bizAppExists(Map<String, Object> input) throws Exception;
    int ifina2400C0_bizAppInsert(Map<String, Object> input) throws Exception;
    int ifina2400D0_delete(Map<String, Object> input) throws Exception;
    int ifina2400D0_bizAppDelete(Map<String, Object> input) throws Exception;
    int ifina2400U0_sessionExists(Map<String, Object> input) throws Exception;
    int ifina2400U0_insertSession(Map<String, Object> input) throws Exception;
    int ifina2400U0_updateSession(Map<String, Object> input) throws Exception;
    int ifina2400U0_countMaps(Map<String, Object> input) throws Exception;
    int ifina2400U0_countPrimaryServer(Map<String, Object> input) throws Exception;
    int ifina2400U0_updateBizAppStatus(Map<String, Object> input) throws Exception;
    int ifina2400U0_countPrimaryBizApp(Map<String, Object> input) throws Exception;
    int ifina2400U0_countDeletedServerMaps(Map<String, Object> input) throws Exception;
    Map<String, Object> ifina2400S0_app(Map<String, Object> input) throws Exception;
}
