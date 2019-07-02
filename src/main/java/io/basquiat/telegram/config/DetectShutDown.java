package io.basquiat.telegram.config;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.basquiat.telegram.service.TelegramService;

/**
 * 
 * 서버를 끌때 메세지 날린다.
 * 
 * created by basquiat
 *
 */
@Component
public class DetectShutDown {

	@Value("${telegram.sleep.message}")
	private String SLEEP_MESSAGE;
	
	@Autowired
	private TelegramService telegramService;
	
	@PreDestroy
	public void onDestroy() throws Exception {
		telegramService.sendMessage(SLEEP_MESSAGE);
	}
	 
}
