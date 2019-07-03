package io.basquiat.common.code;

import java.util.Arrays;

/**
 * 
 * telegram bot request code
 * 
 * created by basquiat
 *
 */
public enum AlgorithmEnum {

	SHA256("SHA-256"),
	
	SHA512("SHA-512");
	
	public String algorithmName;
	
	/**
	 * constructor
	 * @param algorithmName
	 */
	AlgorithmEnum(String algorithmName) {
		this.algorithmName = algorithmName;
	}
	
	/**
	 * from character, get ENUM
	 * @param character
	 * @return AlgorithmEnum
	 */
	public static AlgorithmEnum fromCharacter(String character) {
		return Arrays.asList(AlgorithmEnum.values()).stream()
											   	 	.filter( algorithmEnum -> algorithmEnum.algorithmName.equalsIgnoreCase(character) )
											   	 	.map(algorithmEnumCode -> algorithmEnumCode)
											   	 	.findFirst().orElse(null);
    }
	
}
