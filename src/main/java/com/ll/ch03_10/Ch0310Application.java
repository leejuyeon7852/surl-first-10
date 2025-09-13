package com.ll.ch03_10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //해야 Article의 EntityListener 작동
public class Ch0310Application {

    public static void main(String[] args) {
        SpringApplication.run(Ch0310Application.class, args);
    }

}
