package com.nh.nsight.tcf.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NsightTcfUiApplication {
    public static void main(String[] args) {
        // IDE 멀티모듈 classpath에서 다른 업무 application.yml이 섞이는 경우를 방지
        System.setProperty("server.port", "8099");
        SpringApplication.run(NsightTcfUiApplication.class, args);
    }
}
