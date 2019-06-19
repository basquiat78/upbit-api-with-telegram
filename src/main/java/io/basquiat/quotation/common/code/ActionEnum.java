package io.basquiat.quotation.common.code;

import java.util.Arrays;

/**
 * 
 * telegram bot request code
 * created by basquiat
 *
 */
public enum ActionEnum {

	VALID("valid", "valid"),
	
	INVALID("invalid", "invalid"),
	
	TELEGRAM("telegram", "telegram"),

	TICKER("//ticker", "ticker");
	
	public String command;
	
	public String serviceName;
	
	/**
	 * constructor
	 * @param command
	 * @param serviceName
	 */
	ActionEnum(String command, String serviceName) {
		this.command = command;
		this.serviceName = serviceName;
	}
	
	/**
	 * from character, get ENUM
	 * @param character
	 * @return ActionEnum
	 */
	public static ActionEnum fromCharacter(String character) {
		return Arrays.asList(ActionEnum.values()).stream()
											   	 .filter( actionEnum -> actionEnum.command.equalsIgnoreCase(character) )
											   	 .map(actionEnumCode -> actionEnumCode)
											   	 .findFirst().orElse(null);
    }
	
}
