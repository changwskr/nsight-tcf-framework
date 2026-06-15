package com.nh.nsight.marketing.pc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.pc.mapper")
public class NsightPcServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NsightPcServiceApplication.class, args);
    }
}
