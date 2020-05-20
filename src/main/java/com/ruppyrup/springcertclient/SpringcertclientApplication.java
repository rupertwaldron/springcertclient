package com.ruppyrup.springcertclient;

import com.ruppyrup.springcertclient.config.EndpointConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(EndpointConfiguration.class)
@SpringBootApplication
public class SpringcertclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcertclientApplication.class, args);
    }

}
