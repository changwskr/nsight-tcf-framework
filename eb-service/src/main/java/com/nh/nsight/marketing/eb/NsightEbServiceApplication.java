package com.nh.nsight.marketing.eb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.eb.mapper")
public class NsightEbServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NsightEbServiceApplication.class, args);
    }
}
