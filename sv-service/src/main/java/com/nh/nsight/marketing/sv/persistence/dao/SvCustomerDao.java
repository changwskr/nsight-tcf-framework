package com.nh.nsight.marketing.sv.persistence.dao;

import com.nh.nsight.marketing.sv.persistence.mapper.SvCustomerMapper;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class SvCustomerDao {
    private final SvCustomerMapper mapper;

    public SvCustomerDao(SvCustomerMapper mapper) {
        this.mapper = mapper;
    }

    public Map<String, Object> selectCustomerSummary(Map<String, Object> criteria) {
        return mapper.selectCustomerSummary(criteria);
    }
}
