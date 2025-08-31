package com.ll.ch03_10.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/h2-console/**")
                                .permitAll()
                                .requestMatchers("/actuator/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(
                        formLogin ->
                                formLogin
                                        .permitAll()
                );

        return http.build();
    }
}