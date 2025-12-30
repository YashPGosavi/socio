package com.socio.socio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class SocioApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocioApplication.class, args);
	}

}
