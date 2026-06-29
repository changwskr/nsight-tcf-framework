package com.nh.nsight.marketing.eb.persistence.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EbUserMapper {
    int insertUser(Map<String, Object> row);

    int countByUserId(String userId);

    List<Map<String, Object>> searchUsers(Map<String, Object> criteria);

    int countUsers(Map<String, Object> criteria);
}
