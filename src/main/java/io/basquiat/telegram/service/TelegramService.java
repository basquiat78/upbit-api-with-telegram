package io.basquiat.telegram.service;

/**
 * 
 * TelegramService
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
