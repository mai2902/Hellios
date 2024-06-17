package service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

class quanLyPhongTroApplication {
    public static void main(String[] args) {
        SpringApplication.run(quanLyPhongTroApplication.class, args);
    }
}
