package io.basquiat.telegram.domain;

import lombok.Data;

/**
 * 
 * Telegram Message Domain
 * 
 * created by basquiat
 *
 */
@Data
public class Message {

	/** 메세지 제 */
	private String title;
	
	/** 내 */
	private String contents;
	
}
