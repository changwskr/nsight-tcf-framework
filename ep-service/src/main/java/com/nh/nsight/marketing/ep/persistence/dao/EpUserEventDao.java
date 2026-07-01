package com.nh.nsight.marketing.ep.persistence.dao;

import com.nh.nsight.marketing.ep.persistence.mapper.EpUserEventMapper;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class EpUserEventDao {
    private final EpUserEventMapper mapper;

    public EpUserEventDao(EpUserEventMapper mapper) {
        this.mapper = mapper;
    }

    public int insertReceivedEvent(Map<String, Object> row) {
        return mapper.insertReceivedEvent(row);
    }

    public boolean existsByEventId(String eventId) {
        return mapper.countByEventId(eventId) > 0;
    }

    public List<Map<String, Object>> searchReceivedEvents(Map<String, Object> criteria) {
        return mapper.searchReceivedEvents(criteria);
    }

    public int countReceivedEvents(Map<String, Object> criteria) {
        return mapper.countReceivedEvents(criteria);
    }
}
