package io.basquiat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

/**
 * 
 * created by basquiat
 * 
 * add telegram bot init
 * 
 */
@SpringBootApplication
public class UpbitApiWithTelegramApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(UpbitApiWithTelegramApplication.class, args);
	}

}
