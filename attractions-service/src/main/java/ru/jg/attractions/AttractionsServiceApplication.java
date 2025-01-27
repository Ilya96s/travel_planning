package ru.jg.attractions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AttractionsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttractionsServiceApplication.class, args);
	}

}
