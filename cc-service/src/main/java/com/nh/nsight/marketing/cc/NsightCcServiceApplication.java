package com.nh.nsight.marketing.cc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.cc.mapper")
public class NsightCcServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NsightCcServiceApplication.class, args);
    }
}
