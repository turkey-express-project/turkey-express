package com.currency.turkey_express;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class TurkeyExpressApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurkeyExpressApplication.class, args);
    }

}
