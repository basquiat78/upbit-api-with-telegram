package io.basquiat.quotation.domain.response.market;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/**
 * 
 * Upbit Market All Response Domain Immutable
 * 
 * created by basquiat
 *
 */
@Value
public class MarketAll {

	/** market name e.g. KRW-BTC */
	private String market;
	
    /** 한글 명 */
	@JsonProperty("korean_name")
	private String koreanName;
	
	/** 영어 명 */
	@JsonProperty("english_name")
	private String englishName;
	
}
