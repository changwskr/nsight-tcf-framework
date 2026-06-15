package com.nh.nsight.marketing.ep;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.ep.mapper")
public class NsightEpServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NsightEpServiceApplication.class, args);
    }
}
