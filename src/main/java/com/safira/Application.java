package com.safira;

/**
 * Created by Francisco on 21/02/2015.
 */

import com.safira.service.hibernate.QueryService;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        Configuration cfg = new Configuration().configure();
        QueryService queryService = new QueryService();
        SpringApplication.run(Application.class, args);
    }
}
