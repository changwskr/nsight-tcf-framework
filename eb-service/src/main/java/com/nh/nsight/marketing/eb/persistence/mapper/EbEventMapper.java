package com.nh.nsight.marketing.eb.persistence.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EbEventMapper {

    int insertEvent(Map<String, Object> row);

    int updateEventStatus(@Param("eventId") String eventId,
                          @Param("eventStatus") String eventStatus);

    List<Map<String, Object>> selectReadyEvents(@Param("limit") int limit);

    List<Map<String, Object>> searchEvents(Map<String, Object> criteria);

    int countEvents(Map<String, Object> criteria);

    List<Map<String, Object>> countEventsByStatus();
}
