package com.nh.nsight.marketing.bc.dao;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class BcSampleDao {
    public Map<String, Object> selectSample(Map<String, Object> condition) {
        System.out.println("\n ==============================================[BcSampleDao.selectSample] start");
        System.out.println(" ==============================================[BcSampleDao.selectSample] condition=" + condition);
        System.out.println(" ==============================================[BcSampleDao.selectSample] sampleKey="
                + condition.getOrDefault("sampleKey", "SAMPLE"));

        Map<String, Object> row = new LinkedHashMap<>();
        row.put("sampleKey", condition.getOrDefault("sampleKey", "SAMPLE"));
        row.put("sampleName", "BusinessCustomer sample response");
        row.put("database", "RDW/ADW mapper hook");
        row.put("createdAt", OffsetDateTime.now().toString());

        System.out.println(" ==============================================[BcSampleDao.selectSample] row=" + row);
        System.out.println(" ==============================================[BcSampleDao.selectSample] end");
        return row;
    }
}
