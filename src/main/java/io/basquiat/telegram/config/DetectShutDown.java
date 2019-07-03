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

	/**
	 * 서버가 내려갈때 실행한다.
	 * @throws Exception
	 */
	@PreDestroy
	public void onDestroy() throws Exception {
		telegramService.sendMessage(SLEEP_MESSAGE);
		//telegramService.stop(); 하지만 해당 라이브러리 이슈에 보면 버그로 규정하고 있다. 아직 해결은 못한듯...너무 느려서 쓸수 없다..
	}

}
