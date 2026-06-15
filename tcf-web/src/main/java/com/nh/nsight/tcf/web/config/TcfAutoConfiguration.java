package com.nh.nsight.tcf.web.config;

import com.nh.nsight.tcf.core.config.TcfProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TcfProperties.class)
@ComponentScan(basePackages = "com.nh.nsight.tcf.web")
public class TcfAutoConfiguration {
}
