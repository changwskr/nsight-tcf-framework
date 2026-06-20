package com.nh.nsight.marketing.bc;

import org.mybatis.spring.annotation.MapperScan;
import com.nh.nsight.tcf.web.boot.NsightWarBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nh.nsight")
@MapperScan("com.nh.nsight.marketing.bc.mapper")
public class NsightBcServiceApplication extends NsightWarBootstrap {
    public NsightBcServiceApplication() {
        super(NsightBcServiceApplication.class);
    }

    public static void main(String[] args) {
        System.out.println("\n ==============================================[NsightBcServiceApplication.main] start");
        System.out.println(" ==============================================[NsightBcServiceApplication.main] scanBasePackages=com.nh.nsight");
        System.out.println(" ==============================================[NsightBcServiceApplication.main] mapperScan=com.nh.nsight.marketing.bc.mapper");

        SpringApplication.run(NsightBcServiceApplication.class, args);

        System.out.println(" ==============================================[NsightBcServiceApplication.main] end");
    }
}
