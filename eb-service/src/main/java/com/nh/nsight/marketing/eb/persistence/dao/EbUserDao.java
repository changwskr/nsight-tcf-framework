package com.nh.nsight.marketing.eb.persistence.dao;

import com.nh.nsight.marketing.eb.persistence.mapper.EbUserMapper;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class EbUserDao {
    private final EbUserMapper mapper;

    public EbUserDao(EbUserMapper mapper) {
        this.mapper = mapper;
    }

    public int insertUser(Map<String, Object> row) {
        return mapper.insertUser(row);
    }

    public boolean existsByUserId(String userId) {
        return mapper.countByUserId(userId) > 0;
    }

    public List<Map<String, Object>> searchUsers(Map<String, Object> criteria) {
        return mapper.searchUsers(criteria);
    }

    public int countUsers(Map<String, Object> criteria) {
        return mapper.countUsers(criteria);
    }
}
