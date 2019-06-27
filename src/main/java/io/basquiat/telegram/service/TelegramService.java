package io.basquiat.telegram.service;

/**
 * 
 * TelegramService
 * 
 * 1. 응답에 대한 메세지를 보내는 부분 역시 이 서비스의 sendMessage를 통해 보낸다.
 * 2. 이 서비스는 TelegramConfiguration에서 빈으로 등
 * 
 * created by basquiat
 *
 */
public class TelegramService {

	private TelegramMessageInterface telegramMessageInterface;
	
	/**
	 * constructor
	 * @param telegramMessageInterface
	 */
	public TelegramService(TelegramMessageInterface telegramMessageInterface) {
		this.telegramMessageInterface = telegramMessageInterface;
	}
	
	/**
	 * send Message to Telegram
	 * @param message
	 */
	public void sendMessage(String message) {
		telegramMessageInterface.sendMessage(message);
	}
}
