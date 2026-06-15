package com.nh.nsight.marketing.ms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.ms.mapper")
public class NsightMsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NsightMsServiceApplication.class, args);
    }
}
