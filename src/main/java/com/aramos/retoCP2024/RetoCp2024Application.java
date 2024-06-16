package com.aramos.retoCP2024;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class RetoCp2024Application {

	public static void main(String[] args) {
		SpringApplication.run(RetoCp2024Application.class, args);
		log.info(" ********************************************");
		log.info("*                                           *");
		log.info("*             RETO CINE PLANET 2024         *");
		log.info("*                                           *");
		log.info(" ********************************************");

	}

}
