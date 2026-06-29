package com.nh.nsight.marketing.eb.persistence.dao;

import com.nh.nsight.marketing.eb.persistence.mapper.EbEventMapper;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class EbEventDao {
    private final EbEventMapper mapper;

    public EbEventDao(EbEventMapper mapper) {
        this.mapper = mapper;
    }

    public int insertEvent(Map<String, Object> row) {
        return mapper.insertEvent(row);
    }

    public int updateEventStatus(String eventId, String eventStatus) {
        return mapper.updateEventStatus(eventId, eventStatus);
    }

    public List<Map<String, Object>> selectReadyEvents(int limit) {
        return mapper.selectReadyEvents(limit);
    }

    public List<Map<String, Object>> searchEvents(Map<String, Object> criteria) {
        return mapper.searchEvents(criteria);
    }

    public int countEvents(Map<String, Object> criteria) {
        return mapper.countEvents(criteria);
    }

    public List<Map<String, Object>> countEventsByStatus() {
        return mapper.countEventsByStatus();
    }
}
