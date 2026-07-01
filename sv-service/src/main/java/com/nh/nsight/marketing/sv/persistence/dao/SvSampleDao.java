package com.nh.nsight.marketing.sv.persistence.dao;

import com.nh.nsight.marketing.sv.persistence.mapper.SvSampleMapper;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class SvSampleDao {
    private final SvSampleMapper mapper;

    public SvSampleDao(SvSampleMapper mapper) {
        this.mapper = mapper;
    }

    public List<Map<String, Object>> searchSamples(Map<String, Object> criteria) {
        return mapper.searchSamples(criteria);
    }

    public int countSamples(Map<String, Object> criteria) {
        return mapper.countSamples(criteria);
    }
}
