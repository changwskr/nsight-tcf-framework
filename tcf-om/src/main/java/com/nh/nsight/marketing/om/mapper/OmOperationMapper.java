package com.nh.nsight.marketing.om.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OmOperationMapper {
    Map<String, Object> selectTxSummary(Map<String, Object> params);

    List<Map<String, Object>> selectErrorTop(Map<String, Object> params);

    List<Map<String, Object>> selectSlowTransactionsTop(Map<String, Object> params);

    List<Map<String, Object>> selectApStatus();

    List<Map<String, Object>> selectDbStatus();

    List<Map<String, Object>> selectSessionStatus();

    int sumSessionStatusActiveCount();

    int sumSessionStatusExpiredCount();

    int sumSessionStatusUniqueUsers();

    List<Map<String, Object>> selectDeployStatus();

    Integer selectActiveSessionCount();

    List<Map<String, Object>> searchTransactionLogs(Map<String, Object> params);

    int countTransactionLogs(Map<String, Object> params);

    Map<String, Object> summarizeTransactionLogs(Map<String, Object> params);

    int deleteAllTransactionLogs();

    List<Map<String, Object>> searchServiceCatalog(Map<String, Object> params);

    int countServiceCatalog(Map<String, Object> params);

    Map<String, Object> selectServiceCatalogByKey(Map<String, Object> params);

    Map<String, Object> selectServiceCatalogByServiceId(String serviceId);

    int insertServiceCatalog(Map<String, Object> params);

    int updateServiceCatalog(Map<String, Object> params);

    int disableServiceCatalog(Map<String, Object> params);

    List<Map<String, Object>> searchUsers(Map<String, Object> params);

    int countUsers(Map<String, Object> params);

    Map<String, Object> selectUserById(String userId);

    int insertUser(Map<String, Object> params);

    int updateUser(Map<String, Object> params);

    int updateUserWithPassword(Map<String, Object> params);

    int disableUser(Map<String, Object> params);

    List<Map<String, Object>> searchMenus(Map<String, Object> params);

    List<Map<String, Object>> searchAuthGroups(Map<String, Object> params);

    List<Map<String, Object>> searchAuditLogs(Map<String, Object> params);

    int countAuditLogs(Map<String, Object> params);

    List<Map<String, Object>> searchErrorCodes(Map<String, Object> params);

    int countErrorCodes(Map<String, Object> params);

    List<Map<String, Object>> searchBatchJobs(Map<String, Object> params);

    List<Map<String, Object>> searchBatchHistories(Map<String, Object> params);

    int countBatchHistories(Map<String, Object> params);

    List<Map<String, Object>> searchSystemConfigs(Map<String, Object> params);

    List<Map<String, Object>> searchFileDownloadLogs(Map<String, Object> params);

    int countFileDownloadLogs(Map<String, Object> params);

    int insertFileDownloadLog(Map<String, Object> params);

    List<Map<String, Object>> searchCommonCodes(Map<String, Object> params);

    int countCommonCodes(Map<String, Object> params);

    List<String> selectDistinctCodeGroups();

    Map<String, Object> selectCommonCodeByKey(Map<String, Object> params);

    int insertCommonCode(Map<String, Object> params);

    int updateCommonCode(Map<String, Object> params);

    int disableCommonCode(Map<String, Object> params);

    List<Map<String, Object>> searchFunctionAuths(Map<String, Object> params);

    List<Map<String, Object>> searchDataAuths(Map<String, Object> params);

    List<Map<String, Object>> searchAuthHistories(Map<String, Object> params);

    int countAuthHistories(Map<String, Object> params);

    int insertAuthHistory(Map<String, Object> params);

    Map<String, Object> selectErrorCodeByCode(String errorCode);

    int mergeErrorCode(Map<String, Object> params);

    int insertErrorCode(Map<String, Object> params);

    int updateErrorCode(Map<String, Object> params);

    int disableErrorCode(Map<String, Object> params);

    Map<String, Object> selectBatchJobById(String jobId);

    int insertBatchHistory(Map<String, Object> params);

    List<Map<String, Object>> searchCacheStatus(Map<String, Object> params);

    int deleteCache(Map<String, Object> params);

    int insertAuditLog(Map<String, Object> params);

    Map<String, Object> selectUserForLogin(String userId);

    int updateUserLastLoginTime(Map<String, Object> params);

    List<Map<String, Object>> selectUsersWithoutPasswordHash();

    int updateUserPasswordHash(Map<String, Object> params);

    List<Map<String, Object>> searchSpringSessions(Map<String, Object> params);

    int countSpringSessions(Map<String, Object> params);

    int countActiveSpringSessions(long now);

    Map<String, Object> selectSpringSessionById(String sessionId);

    int deleteSpringSession(String sessionId);

    int countExpiredSpringSessions(long now);

    int deleteExpiredSpringSessions(long now);
}

