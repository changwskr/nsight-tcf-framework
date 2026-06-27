package com.nh.nsight.auth.jwt.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableConfigurationProperties(JwtSecurityProperties.class)
public class JwtAutoConfiguration {

    @Bean
    public PasswordEncoder jwtPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtSchemaInitializer jwtSchemaInitializer(JdbcTemplate jdbcTemplate, JwtSecurityProperties properties) {
        JwtSchemaInitializer initializer = new JwtSchemaInitializer(jdbcTemplate, properties);
        initializer.init();
        return initializer;
    }
}
