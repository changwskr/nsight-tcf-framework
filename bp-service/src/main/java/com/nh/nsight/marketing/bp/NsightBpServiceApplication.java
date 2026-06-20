package com.nh.nsight.marketing.bp;

import org.mybatis.spring.annotation.MapperScan;
import com.nh.nsight.tcf.web.boot.NsightWarBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.bp.mapper")
public class NsightBpServiceApplication extends NsightWarBootstrap {
    public NsightBpServiceApplication() {
        super(NsightBpServiceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(NsightBpServiceApplication.class, args);
    }
}
