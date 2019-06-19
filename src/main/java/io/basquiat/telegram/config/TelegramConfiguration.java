package io.basquiat.telegram.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import io.basquiat.telegram.service.BasquiatTelegramBot;
import io.basquiat.telegram.service.TelegramService;

/**
 * 
 * telegram bot configuration
 * 
 * created by basquiat
 *
 */
@Configuration
public class TelegramConfiguration {
	
	private TelegramService telegramService;
	
	/**
	 * constructor
	 * @param TELEGRAM_API_TOKEN
	 * @param TELEGRAM_BOT_NAME
	 * @param CHAT_ID
	 * @param context
	 */
	public TelegramConfiguration(@Value("${telegram.api.token}") final String TELEGRAM_API_TOKEN, 
								 @Value("${telegram.bot.name}") final String TELEGRAM_BOT_NAME,
								 @Value("${telegram.chat.id}") final String CHAT_ID,
								 @Autowired final ApplicationContext context
								) {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			BasquiatTelegramBot basquiatTelegramBot = new BasquiatTelegramBot(TELEGRAM_API_TOKEN, TELEGRAM_BOT_NAME, CHAT_ID, context);
			telegramBotsApi.registerBot(basquiatTelegramBot);
			TelegramService service = new TelegramService(basquiatTelegramBot);
			telegramService = service;
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Bean 설정
	 * @return TelegramService
	 */
	@Bean(name = "telegram")
	public TelegramService test() {
		return telegramService;
	}

}
