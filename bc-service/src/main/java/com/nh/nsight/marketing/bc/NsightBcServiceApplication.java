package com.nh.nsight.marketing.bc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.bc.mapper")
public class NsightBcServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NsightBcServiceApplication.class, args);
    }
}
