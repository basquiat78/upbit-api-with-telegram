package io.basquiat.telegram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.basquiat.common.util.CommonUtils;
import io.basquiat.telegram.domain.Message;
import io.basquiat.telegram.service.TelegramService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 
 * Functional Route방식이 아닌 Rest방식으로 작성한다.
 * 
 * 차트 데이터를 요청하는 컨트롤러
 * 
 * created by basquiat
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/messages")
@Api(value = "TelegramMessage Controller", tags = {"Telegram"})
public class TelegramMessageController {

	@Autowired
	private TelegramService telegramService;
	
	/**
	 * 
	 * @param message
	 * @return Mono<String>
	 */
	@ApiOperation(value = "텔레그램 봇 메세지 보내기")
	@PostMapping("/send")
	public Mono<String> sendMessage(@RequestBody Message message) {
		log.info("send Message :" + CommonUtils.convertJsonStringFromObject(message));
		telegramService.sendMessage(CommonUtils.convertJsonStringFromObject(message));
		return Mono.just("Message send successful").onErrorResume(fallback -> Mono.just("Message Send fail"));
	}
	
}
