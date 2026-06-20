package com.nh.nsight.marketing.cm;

import org.mybatis.spring.annotation.MapperScan;
import com.nh.nsight.tcf.web.boot.NsightWarBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.cm.mapper")
public class NsightCmServiceApplication extends NsightWarBootstrap {
    public NsightCmServiceApplication() {
        super(NsightCmServiceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(NsightCmServiceApplication.class, args);
    }
}
