package com.nh.nsight.auth.jwt;

import com.nh.nsight.tcf.web.boot.NsightWarBootstrap;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.auth.jwt.mapper")
public class NsightJwtServiceApplication extends NsightWarBootstrap {
    public NsightJwtServiceApplication() {
        super(NsightJwtServiceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(NsightJwtServiceApplication.class, args);
    }
}
