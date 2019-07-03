package io.basquiat.telegram.service;

import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import io.basquiat.telegram.action.BotAction;

/**
 * 
 * 특정 문구가 들어올 때을 체크해서 틱 정보를 봇으로 던진다.
 * 
 * 버그인지 모르겠지만 @Component, @Service같은 어노테이션을 달면 봇 등록시 에러가 난다.
 * 따라서 외부로부터 AppicationContext를 생성자로 받는다.
 * 
 * created by basquiat
 *
 */
public class BasquiatTelegramBot extends TelegramLongPollingBot implements TelegramMessageInterface  {

	private String telegramToken;
	
	private String telegramBotName;
	
	private String chatId;
	
	private ApplicationContext context;

	/**
	 * constructor
	 * @param token
	 * @param name
	 * @param tickerService
	 */
	public BasquiatTelegramBot(String telegramToken, String telegramBotName, String chatId, ApplicationContext context) {
		super();
		this.telegramToken = telegramToken;
		this.telegramBotName = telegramBotName;
		this.chatId = chatId;
		this.context = context;
	}
	
	@Override
	public void onUpdateReceived(Update update) {
		if(update.hasMessage() && update.getMessage().hasText()) {
			BotAction.reply(update, context);
		}
	}

	@Override
	public String getBotUsername() {
		return telegramBotName;
	}

	@Override
	public String getBotToken() {
		return telegramToken;
	}

	/**
	 * 텔레그램 기존 방식은 onUpdateReceived에서 update가 들어올 때 아래와 같은 방식으로 보내게 되어 있다.
	 * TelegramMessageInterface를 통해서 어떤 상황에서도 메세지를 보낼 수 있게 인터페이스 구현으로 빼낸다.
	 */
	@Override
	public void sendMessage(String message) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
		sendMessage.setText(message);
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
}
