package com.nh.nsight.marketing.ss.persistence.mapper;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SsSampleMapper {
    Map<String, Object> selectSample(Map<String, Object> condition);
}
