package com.nh.nsight.marketing.mg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.mg.mapper")
public class NsightMgServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NsightMgServiceApplication.class, args);
    }
}
