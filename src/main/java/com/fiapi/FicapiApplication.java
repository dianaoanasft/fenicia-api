package com.fiapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:ficapi-spring.xml")
public class FicapiApplication {

    private static final Logger logger = LoggerFactory.getLogger(FicapiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FicapiApplication.class, args);
    }

}
