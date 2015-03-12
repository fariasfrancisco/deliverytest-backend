package com.safira;

/**
 * Created by Francisco on 21/02/2015.
 */

import com.safira.service.hibernate.QueryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        QueryService.initialize();
        SpringApplication.run(Application.class, args);
    }
}
