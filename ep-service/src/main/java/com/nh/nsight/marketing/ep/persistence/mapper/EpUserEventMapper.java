package com.nh.nsight.marketing.ep.persistence.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EpUserEventMapper {
    int insertReceivedEvent(Map<String, Object> row);

    int countByEventId(String eventId);

    List<Map<String, Object>> searchReceivedEvents(Map<String, Object> criteria);

    int countReceivedEvents(Map<String, Object> criteria);
}
