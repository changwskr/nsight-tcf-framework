package com.nh.nsight.marketing.om;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.om.mapper")
public class NsightOmServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NsightOmServiceApplication.class, args);
    }
}
