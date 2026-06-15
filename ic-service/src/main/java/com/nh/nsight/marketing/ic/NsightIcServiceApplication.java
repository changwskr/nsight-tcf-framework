package com.nh.nsight.marketing.ic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.ic.mapper")
public class NsightIcServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NsightIcServiceApplication.class, args);
    }
}
