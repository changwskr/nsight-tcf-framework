package com.nh.nsight.marketing.ct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.ct.mapper")
public class NsightCtServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NsightCtServiceApplication.class, args);
    }
}
