package org.example.lotsandlots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories("org.example.lotsandlots.persistence.repo")
@EntityScan("org.example.lotsandlots.persistence.model")
@SpringBootApplication
@EnableScheduling
public class LotsandlotsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LotsandlotsApplication.class, args);
	}
}
