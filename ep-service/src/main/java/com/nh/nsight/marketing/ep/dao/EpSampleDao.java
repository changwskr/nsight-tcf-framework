package com.nh.nsight.marketing.ep.dao;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class EpSampleDao {
    public Map<String, Object> selectSample(Map<String, Object> condition) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("sampleKey", condition.getOrDefault("sampleKey", "SAMPLE"));
        row.put("sampleName", "EventProcessing sample response");
        row.put("database", "RDW/ADW mapper hook");
        row.put("createdAt", OffsetDateTime.now().toString());
        return row;
    }
}
