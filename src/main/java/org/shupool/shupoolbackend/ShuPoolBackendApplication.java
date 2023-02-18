package org.shupool.shupoolbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ShuPoolBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShuPoolBackendApplication.class, args);
    }

}
