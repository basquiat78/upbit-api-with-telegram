package io.basquiat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * 
 * created by basquiat
 * 
 * add telegram bot init
 * 
 */
@EnableWebFlux
@SpringBootApplication
public class UpbitApiWithTelegramApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpbitApiWithTelegramApplication.class, args);
	}

}
