package com.nh.nsight.marketing.sv;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.sv.mapper")
public class NsightSvServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NsightSvServiceApplication.class, args);
    }
}
