package com.nh.nsight.marketing.cc;

import org.mybatis.spring.annotation.MapperScan;
import com.nh.nsight.tcf.web.boot.NsightWarBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.cc.mapper")
public class NsightCcServiceApplication extends NsightWarBootstrap {
    public NsightCcServiceApplication() {
        super(NsightCcServiceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(NsightCcServiceApplication.class, args);
    }
}
