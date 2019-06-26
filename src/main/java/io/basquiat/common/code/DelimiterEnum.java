package io.basquiat.common.code;

import java.util.Arrays;

/**
 * 
 * 구분자 enum type 정보
 * 
 * Character | Html Entity
 * 
 * created by basquiat
 *
 */
public enum DelimiterEnum {

	SPACE(" ", "&nbsp;"),
	
	COMMA(",", "&#44;"),
	
	SLASH("/", "&#47;"),
	
	BACKSLASH("\\", "&#92;"),
	
	AMPERSAND("&", "&amp;"),
	
	EQUALS_SIGN("=", "&#61;"),
	
	COLON(":", "&#58;"),
	
	SEMICOLON(";", "&#59;"),
	
	QUESTION_MARK("?", "&#63;"),
	
	AT_SIGN("@", "&#64;"),
	
	VERTICAL_LINE("|", "&vert;");
	
	public String character;
	
	public String htmlEntity;
	
	/**
	 * constructor
	 * @param character
	 * @param htmlEntity
	 */
	DelimiterEnum(String character, String htmlEntity) {
		this.character = character;
		this.htmlEntity = htmlEntity;
	}
	
	/**
	 * from character, get ENUM
	 * @param character
	 * @return DelimiterEnum
	 */
	public static DelimiterEnum fromCharacter(String character) {
		return Arrays.asList(DelimiterEnum.values()).stream()
											   		.filter( delimiterEnum -> delimiterEnum.character.equalsIgnoreCase(character) )
											   		.map(delimiterEnumCode -> delimiterEnumCode)
											   		.findFirst().orElse(null);
    }
	
}
