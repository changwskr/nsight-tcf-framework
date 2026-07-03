package com.nh.nsight.marketing.sv.persistence.mapper;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SvCustomerMapper {
    Map<String, Object> selectCustomerSummary(Map<String, Object> criteria);
}
