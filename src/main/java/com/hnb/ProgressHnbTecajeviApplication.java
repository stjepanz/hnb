package com.hnb;

import com.hnb.query.Queries;
import com.hnb.repository.HNBcrudRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ProgressHnbTecajeviApplication {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProgressHnbTecajeviApplication.class, args);

    }

}
