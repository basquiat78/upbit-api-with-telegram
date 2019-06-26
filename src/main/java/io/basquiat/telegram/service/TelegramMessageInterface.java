package io.basquiat.telegram.service;

/**
 * 
 * telegram messsage Interface
 * 
 * 인터페이스를 통해 메세지를 텔레그램으로 보내기 위한 인터페이스
 * 
 * created by basquiat
 *
 */
public interface TelegramMessageInterface {

	/**
	 * sendMessage
	 * @param message
	 */
	public abstract void sendMessage(String message);
	
}
