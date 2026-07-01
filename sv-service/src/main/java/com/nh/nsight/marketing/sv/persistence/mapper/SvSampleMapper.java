package com.nh.nsight.marketing.sv.persistence.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SvSampleMapper {
    List<Map<String, Object>> searchSamples(Map<String, Object> criteria);

    int countSamples(Map<String, Object> criteria);
}
