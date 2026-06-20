package com.nh.nsight.marketing.cs;

import org.mybatis.spring.annotation.MapperScan;
import com.nh.nsight.tcf.web.boot.NsightWarBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.cs.mapper")
public class NsightCsServiceApplication extends NsightWarBootstrap {
    public NsightCsServiceApplication() {
        super(NsightCsServiceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(NsightCsServiceApplication.class, args);
    }
}
