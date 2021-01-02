package com.sperekrestova.visitCount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Created by Svetlana
 * Date: 02.01.2021
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class VisitCountApplication {
    public static void main(String[] args) {
        SpringApplication.run(VisitCountApplication.class, args);
    }
}
